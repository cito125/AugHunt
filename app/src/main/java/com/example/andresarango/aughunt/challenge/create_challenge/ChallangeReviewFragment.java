package com.example.andresarango.aughunt.challenge.create_challenge;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.ChallengeViewholderListener;

/**
 * Created by Millochka on 3/6/17.
 */

public class ChallangeReviewFragment extends Fragment {

    private Challenge<Bitmap> mChallengeToReview;
    private ChallengeViewholderListener<Bitmap> mListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        initializeRecyclerView(view);

    }

    public void setChallengeToReview(Challenge<Bitmap> challengeToReview) {
        mChallengeToReview = challengeToReview;
    }


    public void initializeRecyclerView(View view) {
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.challanges_for_review);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new ReviewChallengeAdapter(mChallengeToReview, mListener));

    }

    public void initializeViews(View view) {
        ImageView mChallengePhoto = (ImageView) view.findViewById(R.id.review_challenge_picture);
        TextView mHint = (TextView) view.findViewById(R.id.review_challenge_hit);
        TextView mUsersAccepted = (TextView) view.findViewById(R.id.usersaccepted);
        BitmapDrawable d = new BitmapDrawable(getContext().getResources(), mChallengeToReview.getChallenge());
        mChallengePhoto.setImageDrawable(d);
        mHint.setText("Challenge Hint: " + mChallengeToReview.getmHint());
        mUsersAccepted.setText("Users Accepted: " + String.valueOf(mChallengeToReview.getUsersAccepted()));

    }

    public void setmListener(ChallengeViewholderListener<Bitmap> mListener) {
        this.mListener = mListener;
    }
}
