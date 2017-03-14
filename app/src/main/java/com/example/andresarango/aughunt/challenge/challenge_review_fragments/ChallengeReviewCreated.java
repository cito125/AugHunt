package com.example.andresarango.aughunt.challenge.challenge_review_fragments;

import android.support.v7.app.AppCompatActivity;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.ChallengePhoto;
import com.example.andresarango.aughunt.challenge.ChallengePhotoCompleted;
import com.example.andresarango.aughunt.challenge.challenges_adapters.created.CreatedChallengeListener;
import com.example.andresarango.aughunt.challenge.challenges_adapters.review.CompletedChallengeListener;

/**
 * Created by Millochka on 3/13/17.
 */

public class ChallengeReviewCreated implements CreatedChallengeListener, CompletedChallengeListener{


    private ChallengePhoto mSelectedChallenge;
    private ReviewChallengesFragment mReviewChallengesFragment;

    private AppCompatActivity mActivity;
    private CompareChallengesFragment mCompareChallengesFragment;

    public ChallengeReviewCreated(AppCompatActivity activity){

        this.mActivity=activity;

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

        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.search_challenge, mReviewChallengesFragment)
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
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.search_challenge, mCompareChallengesFragment)
                .addToBackStack(null)
                .commit();
    }
}
