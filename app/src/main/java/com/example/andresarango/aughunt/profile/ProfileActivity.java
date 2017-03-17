package com.example.andresarango.aughunt.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.ChallengePhoto;
import com.example.andresarango.aughunt.create.CreateChallengeCameraActivity;
import com.example.andresarango.aughunt.leaderboard.LeaderBoardActivity;
import com.example.andresarango.aughunt.profile.viewpager.account.ProfileTabFragment;
import com.example.andresarango.aughunt.profile.viewpager.created.CreatedChallengeFragment;
import com.example.andresarango.aughunt.profile.viewpager.created.CreatedChallengeListener;
import com.example.andresarango.aughunt.profile.viewpager.pending.PendingChallengeFragment;
import com.example.andresarango.aughunt.profile.viewpager.pending.PendingChallengeListener;
import com.example.andresarango.aughunt.profile.viewpager.submitted.SubmittedChallengeFragment;
import com.example.andresarango.aughunt.review.PopFragmentListener;
import com.example.andresarango.aughunt.review.ReviewChallengesFragment;
import com.example.andresarango.aughunt.search.SearchChallengeActivity;
import com.example.andresarango.aughunt.util.bottom_nav_helper.BottomNavigationViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dannylui on 3/11/17.
 */

public class ProfileActivity extends AppCompatActivity implements CreatedChallengeListener, PendingChallengeListener, PopFragmentListener {
    @BindView(R.id.tab_layout) TabLayout tablayout;
    @BindView(R.id.viewpager) ViewPager pager;
    @BindView(R.id.bottom_navigation) BottomNavigationView mBottomNav;

    public static final String VIEWPAGER_START_POSITION = "view.pager.start.position";

    private ProfileTabFragment mProfileTabFragment;
    private SubmittedChallengeFragment mSubmittedChallengesFragment;
    private CreatedChallengeFragment mCreatedChallengeFragment;
    private ReviewChallengesFragment mReviewChallengesFragment;

    private PendingChallengeFragment mPendingChallengeFragment;

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        int startPos = getIntent().getIntExtra(VIEWPAGER_START_POSITION, 0);

        setupTabLayout(tablayout);
        setupViewPager(pager);
        tablayout.setupWithViewPager(pager);

        pager.setCurrentItem(startPos);

        setupBottomNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBottomNav.getMenu().getItem(0).setChecked(true);
        mBottomNav.getMenu().getItem(1).setChecked(false);
        mBottomNav.getMenu().getItem(2).setChecked(false);
        mBottomNav.getMenu().getItem(3).setChecked(false);

    }

    private void setupBottomNavigation() {

        mBottomNav.getMenu().getItem(3).setChecked(false);
        mBottomNav.getMenu().getItem(2).setChecked(false);// Leaderboard
        mBottomNav.getMenu().getItem(1).setChecked(false);
        mBottomNav.getMenu().getItem(0).setChecked(true);// Create item
        // Profile item
        BottomNavigationViewHelper.disableShiftMode(mBottomNav);

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.create_challenge:
                        Intent createChallenge = new Intent(getApplicationContext(), CreateChallengeCameraActivity.class);
                        startActivity(createChallenge);

                        break;
                    case R.id.search_challenge:
                        Intent searchChallenge = new Intent(getApplicationContext(), SearchChallengeActivity.class);
                        startActivity(searchChallenge);
                        break;
                    case R.id.leaderboard:
                        Intent leadeBoard = new Intent(getApplicationContext(), LeaderBoardActivity.class);
                        startActivity(leadeBoard);
                        break;
                }
                return true;
            }
        });
    }


    private void setupViewPager(ViewPager pager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        mProfileTabFragment = new ProfileTabFragment();
        mCreatedChallengeFragment = new CreatedChallengeFragment();
        mCreatedChallengeFragment.setListener(this);

        mSubmittedChallengesFragment = new SubmittedChallengeFragment();

        mPendingChallengeFragment = new PendingChallengeFragment();
        mPendingChallengeFragment.setListener(this);

        adapter.addFragment(mProfileTabFragment, "Profile");
        adapter.addFragment(mCreatedChallengeFragment, "Created");
        adapter.addFragment(mSubmittedChallengesFragment, "Submitted");
        adapter.addFragment(mPendingChallengeFragment, "Pending");
        pager.setAdapter(adapter);
    }

    private void setupTabLayout(TabLayout tablayout) {
        tablayout.setTabTextColors(ContextCompat.getColor(this, R.color.lightGrey), ContextCompat.getColor(this, R.color.lightGrey));
        tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.lightGrey));
    }


    @Override
    public void onCreatedChallengeClicked(ChallengePhoto challenge) {
        if (challenge.getPendingReviews() > 0) {
            startReviewChallengeFragment(challenge);
        }
    }

    private void startReviewChallengeFragment(ChallengePhoto challenge) {
        tablayout.setVisibility(View.INVISIBLE);
        mReviewChallengesFragment = new ReviewChallengesFragment();
        mReviewChallengesFragment.setChallengeToReview(challenge);
        mReviewChallengesFragment.setPopFragmentListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.profile_activity, mReviewChallengesFragment)
                .addToBackStack(null)
                .commit();
    }

    public void popFragmentFromBackStack(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .remove(fragment)
                .commit();
        tablayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void popFragment(Fragment fragment) {
        popFragmentFromBackStack(fragment);
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

}

