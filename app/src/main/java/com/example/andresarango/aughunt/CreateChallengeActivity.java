package com.example.andresarango.aughunt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.andresarango.aughunt.challenge.ChallengePhoto;
import com.example.andresarango.aughunt.challenge.ChallengePhotoCompleted;
import com.example.andresarango.aughunt.challenge.challenges_adapters.created.ChallengeViewholderListener;
import com.example.andresarango.aughunt.challenge.challenges_adapters.review.CompletedChallengeViewholderListener;
import com.example.andresarango.aughunt.challenge.create_challenge.CompareChallengesFragment;
import com.example.andresarango.aughunt.challenge.create_challenge.CreatedChallengesFragment;
import com.example.andresarango.aughunt.challenge.create_challenge.ReviewChallengesFragment;

import butterknife.ButterKnife;


public class CreateChallengeActivity extends AppCompatActivity implements
        ChallengeViewholderListener,
        CompletedChallengeViewholderListener {

    private CreatedChallengesFragment mCreatedChallengesFragment;
    private ReviewChallengesFragment mReviewChallengesFragment;
    private CompareChallengesFragment mCompareChallengesFragment;
    private ChallengePhoto mSelectedChallenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);
        ButterKnife.bind(this);

        mCreatedChallengesFragment = new CreatedChallengesFragment();
        mCreatedChallengesFragment.setListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_for_review, mCreatedChallengesFragment)
                .commit();

    }

    @Override
    public void onChallengeClicked(ChallengePhoto challenge) {
        mSelectedChallenge = challenge;
        startReviewChallengeFragment(challenge);
    }

    @Override
    public void onCompletedChallengeClicked(ChallengePhotoCompleted completedChallenge) {
        startCompareChallengeFragment(completedChallenge, mSelectedChallenge);
    }

    private void startReviewChallengeFragment(ChallengePhoto challenge) {
        mReviewChallengesFragment = new ReviewChallengesFragment();
        mReviewChallengesFragment.setChallengeToReview(challenge);
        mReviewChallengesFragment.setmListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_for_review, mReviewChallengesFragment)
                .addToBackStack(null)
                .commit();
    }


    private void startCompareChallengeFragment(ChallengePhotoCompleted completedChallenge, ChallengePhoto challenge) {
        mCompareChallengesFragment = new CompareChallengesFragment();
        mCompareChallengesFragment.setCompletedChallenge(completedChallenge);
        mCompareChallengesFragment.setCurrentChallenge(challenge);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_for_review, mCompareChallengesFragment)
                .addToBackStack(null)
                .commit();
    }

    public void popFragmentFromBackStack() {
        getSupportFragmentManager().popBackStack();
    }

}
