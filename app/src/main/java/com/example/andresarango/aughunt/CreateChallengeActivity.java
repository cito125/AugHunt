package com.example.andresarango.aughunt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.andresarango.aughunt.challenge.ChallengePhoto;
import com.example.andresarango.aughunt.challenge.ChallengePhotoCompleted;
import com.example.andresarango.aughunt.challenge.challenge_review_fragments.CreatedChallengesFragment;
import com.example.andresarango.aughunt.challenge.challenge_review_fragments.PopFragmentListener;
import com.example.andresarango.aughunt.challenge.challenge_review_fragments.ReviewChallengesFragment;
import com.example.andresarango.aughunt.challenge.challenges_adapters.created.CreatedChallengeListener;
import com.example.andresarango.aughunt.challenge.challenges_adapters.review.CompletedChallengeListener;

import butterknife.ButterKnife;


public class CreateChallengeActivity extends AppCompatActivity implements
        CreatedChallengeListener, PopFragmentListener {

    private CreatedChallengesFragment mCreatedChallengesFragment;
    private ReviewChallengesFragment mReviewChallengesFragment;

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
    public void onCreatedChallengeClicked(ChallengePhoto challenge) {
        startReviewChallengeFragment(challenge);
    }

    private void startReviewChallengeFragment(ChallengePhoto challenge) {
        mReviewChallengesFragment = new ReviewChallengesFragment();
        mReviewChallengesFragment.setChallengeToReview(challenge);
        mReviewChallengesFragment.setPopFragmentListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_for_review, mReviewChallengesFragment)
                .addToBackStack(null)
                .commit();
    }



    public void popFragmentFromBackStack() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void popFragment() {
        popFragmentFromBackStack();
    }


}
