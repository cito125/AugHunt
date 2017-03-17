package com.example.andresarango.aughunt.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.HomeScreenActivity;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.ChallengePhoto;
import com.example.andresarango.aughunt.profile.viewpager.account.ProfileTabFragment;
import com.example.andresarango.aughunt.profile.viewpager.created.CreatedChallengeFragment;
import com.example.andresarango.aughunt.profile.viewpager.created.CreatedChallengeListener;
import com.example.andresarango.aughunt.profile.viewpager.pending.PendingChallengeFragment;
import com.example.andresarango.aughunt.profile.viewpager.pending.PendingChallengeListener;
import com.example.andresarango.aughunt.profile.viewpager.submitted.SubmittedChallengeFragment;
import com.example.andresarango.aughunt.review.PopFragmentListener;
import com.example.andresarango.aughunt.review.ReviewChallengesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Danny on 3/17/2017.
 */

public class ProfileFragment extends Fragment implements CreatedChallengeListener, PendingChallengeListener, PopFragmentListener {
    @BindView(R.id.tab_layout) TabLayout tablayout;
    @BindView(R.id.viewpager) ViewPager pager;

    public static final String VIEWPAGER_START_POSITION = "view.pager.start.position";

    private ProfileTabFragment mProfileTabFragment;
    private SubmittedChallengeFragment mSubmittedChallengesTabFragment;
    private CreatedChallengeFragment mCreatedChallengeTabFragment;
    private ReviewChallengesFragment mReviewChallengeTabFragment;

    private PendingChallengeFragment mPendingChallengeFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        int startPos = 0;
        if (getArguments() != null) {
            startPos = getArguments().getInt(VIEWPAGER_START_POSITION);
        }

        setupTabLayout(tablayout);
        setupViewPager(pager);
        tablayout.setupWithViewPager(pager);

        pager.setCurrentItem(startPos);

    }

    public void setupTabLayout(TabLayout tablayout) {
        tablayout.setTabTextColors(ContextCompat.getColor(getContext(), R.color.lightGrey), ContextCompat.getColor(getContext(), R.color.lightGrey));
        tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.lightGrey));

    }

    private void setupViewPager(ViewPager pager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        mProfileTabFragment = new ProfileTabFragment();
        mCreatedChallengeTabFragment = new CreatedChallengeFragment();
        mCreatedChallengeTabFragment.setListener(this);

        mSubmittedChallengesTabFragment = new SubmittedChallengeFragment();

        mPendingChallengeFragment = new PendingChallengeFragment();
        mPendingChallengeFragment.setListener(this);

        adapter.addFragment(mProfileTabFragment, "Profile");
        adapter.addFragment(mCreatedChallengeTabFragment, "Created");
        adapter.addFragment(mSubmittedChallengesTabFragment, "Submitted");
        adapter.addFragment(mPendingChallengeFragment, "Pending");
        pager.setAdapter(adapter);
    }

    @Override
    public void popFragment(Fragment fragment) {
        popFragmentFromBackStack(fragment);

    }

    public void popFragmentFromBackStack(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .remove(fragment)
                .commit();
        tablayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void setTabLayoutVisibile() {
        tablayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshPendingFragment() {
        mPendingChallengeFragment.refreshPendingList();
    }

    @Override
    public void onPendingChallengeClicked(ChallengePhoto challenge) {
        startReviewChallengeFragment(challenge);
    }

    private void startReviewChallengeFragment(ChallengePhoto challenge) {
        tablayout.setVisibility(View.INVISIBLE);
        mReviewChallengeTabFragment = new ReviewChallengesFragment();
        mReviewChallengeTabFragment.setChallengeToReview(challenge);
        mReviewChallengeTabFragment.setPopFragmentListener(this);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.profile_activity, mReviewChallengeTabFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCreatedChallengeClicked(ChallengePhoto challenge) {
        if (challenge.getPendingReviews() > 0) {
            startReviewChallengeFragment(challenge);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("CALLED ON START PROFILE");
        ((HomeScreenActivity) getActivity()).setBottomNavFocusProfile();
    }

}
