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
import com.example.andresarango.aughunt.ChallengeReviewListener;

/**
 * Created by Millochka on 3/6/17.
 */

public class ChallangeReviewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Challenge<Bitmap> mChallengeToReview;
    private Context mContext;
    private ImageView mChallengePhoto;
    private TextView mHint;
    private TextView mUsersAccepted;
    private ChallengeReviewListener<Bitmap> mListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

       initSetViews(view);
        initRecyclerView(view);

    }

    public void setmCgallengeToReview(Challenge<Bitmap> cgallengeToReview) {
        this.mChallengeToReview = cgallengeToReview;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void initRecyclerView(View view){
        mRecyclerView=(RecyclerView) view.findViewById(R.id.challanges_for_review);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(new ReviewChallengeAdapter(mChallengeToReview, mListener));

    }

    public void initSetViews(View view){
        mChallengePhoto=(ImageView) view.findViewById(R.id.review_challenge_picture);
        mHint=(TextView) view.findViewById(R.id.review_challenge_hit);
        mUsersAccepted=(TextView) view.findViewById(R.id.usersaccepted);
        BitmapDrawable d= new BitmapDrawable(mContext.getResources(), mChallengeToReview.getmChallenge());
        mChallengePhoto.setImageDrawable(d);
        mHint.setText("Challenge Hint: "+mChallengeToReview.getmHint());
        mUsersAccepted.setText("Users Accepted: "+String.valueOf(mChallengeToReview.getUsersAccepted()));

    }

    public void setmListener(ChallengeReviewListener<Bitmap> mListener) {
        this.mListener = mListener;
    }
}
