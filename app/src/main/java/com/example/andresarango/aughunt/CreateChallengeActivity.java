package com.example.andresarango.aughunt;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.ChallengePhoto;
import com.example.andresarango.aughunt.challenge.ChallengePhotoCompleted;
import com.example.andresarango.aughunt.challenge.challenges_adapters.ChallengeViewholderListener;
import com.example.andresarango.aughunt.challenge.challenges_adapters.review.CompletedChallengeViewholderListener;
import com.example.andresarango.aughunt.challenge.create_challenge.CompareChallengesFragment;
import com.example.andresarango.aughunt.challenge.create_challenge.CreatedChallengesFragment;
import com.example.andresarango.aughunt.challenge.create_challenge.ReviewChallengesFragment;

import java.util.ArrayList;
import java.util.List;

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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_for_review, mCreatedChallengesFragment)
                .commit();

    }

    public List<Challenge<Bitmap>> historyOfCreatedChalalnges(List<Challenge<Bitmap>> challengeList, String ownerID) {

        List<Challenge<Bitmap>> userChallengeList = new ArrayList<>();

        for (Challenge<Bitmap> challenge : challengeList) {
            if (challenge.getmOwnerId().equalsIgnoreCase(ownerID)) {
                userChallengeList.add(challenge);
            }

        }
        return userChallengeList;

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

//    public void reviewResult(View view) {
//
//        switch (view.getId()) {
//
//            case R.id.decline:
//
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .remove(mCompareChallengesFragment)
//                        .commit();
//                break;
//
//            case R.id.accept:
//
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .remove(mCompareChallengesFragment)
//                        .commit();
//                break;
//
//        }
//    }

}
