package com.example.andresarango.aughunt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.andresarango.aughunt.challenge.ChallengePhoto;
import com.example.andresarango.aughunt.challenge.ChallengePhotoCompleted;
import com.example.andresarango.aughunt.challenge.challenge_review_fragments.CompareChallengesFragment;
import com.example.andresarango.aughunt.challenge.challenge_review_fragments.CreatedChallengesFragment;
import com.example.andresarango.aughunt.challenge.challenge_review_fragments.ReviewChallengesFragment;
import com.example.andresarango.aughunt.challenge.challenges_adapters.created.CreatedChallengeListener;
import com.example.andresarango.aughunt.challenge.challenges_adapters.review.CompletedChallengeListener;
import com.example.andresarango.aughunt.profile.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dannylui on 3/11/17.
 */

public class ProfileActivity extends AppCompatActivity implements CreatedChallengeListener, CompletedChallengeListener {
    @BindView(R.id.tab_layout) TabLayout tablayout; // Import design in build.gradle
    @BindView(R.id.viewpager) ViewPager pager;

    private CreatedChallengesFragment mCreatedChallengesFragment;
    private ReviewChallengesFragment mReviewChallengesFragment;
    private CompareChallengesFragment mCompareChallengesFragment;
    private ChallengePhoto mSelectedChallenge;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setupTabLayout(tablayout);
        setupViewPager(pager);
        tablayout.setupWithViewPager(pager);

    }

    private void setupViewPager(ViewPager pager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        mCreatedChallengesFragment = new CreatedChallengesFragment();
        mCreatedChallengesFragment.setListener(this);

        adapter.addFragment(mCreatedChallengesFragment, "Created");
        adapter.addFragment(new ChallengeHistoryFragment(), "Submitted");
        pager.setAdapter(adapter);
    }

    private void setupTabLayout(TabLayout tablayout) {
        tablayout.setTabTextColors(0xFFFFFFFF, 0xFFFFFFFF);
        tablayout.setSelectedTabIndicatorColor(0xFFFFFFFF);
    }


    @Override
    public void onCreatedChallengeClicked(ChallengePhoto challenge) {
        mSelectedChallenge = challenge;
        startReviewChallengeFragment(challenge);
    }

    private void startReviewChallengeFragment(ChallengePhoto challenge) {
        mReviewChallengesFragment = new ReviewChallengesFragment();
        mReviewChallengesFragment.setChallengeToReview(challenge);
        mReviewChallengesFragment.setmListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.profile_activity, mReviewChallengesFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCompletedChallengeClicked(ChallengePhotoCompleted completedChallenge) {
        startCompareChallengeFragment(completedChallenge, mSelectedChallenge);
    }

    private void startCompareChallengeFragment(ChallengePhotoCompleted completedChallenge, ChallengePhoto challenge) {
        mCompareChallengesFragment = new CompareChallengesFragment();
        mCompareChallengesFragment.setCompletedChallenge(completedChallenge);
        mCompareChallengesFragment.setCurrentChallenge(challenge);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.profile_activity, mCompareChallengesFragment)
                .addToBackStack(null)
                .commit();
    }
}

