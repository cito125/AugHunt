package com.example.andresarango.aughunt.challenge.create_challenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.CompletedChallenge;

/**
 * Created by Millochka on 3/7/17.
 */

public class ReviewFragment extends Fragment {

    private Challenge<Bitmap> mCurrentChallenge;
    private CompletedChallenge<Bitmap> mCompletedChallenge;
    private ImageView mChallengeCompareWith;
    private ImageView mChallengeToCompare;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_completed_challenge, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        mChallengeCompareWith=(ImageView)view.findViewById(R.id.initial_challenge);
        mChallengeToCompare=(ImageView)view.findViewById(R.id.completed_challenge);
        BitmapDrawable d= new BitmapDrawable(mContext.getResources(), mCurrentChallenge.getmChallenge());
        mChallengeCompareWith.setImageDrawable(d);
        BitmapDrawable d1= new BitmapDrawable(mContext.getResources(), mCompletedChallenge.getCompletedChallenge());
        mChallengeToCompare.setImageDrawable(d1);

    }

    public void setmCurrentChallenge(Challenge<Bitmap> mCurrentChallenge) {
        this.mCurrentChallenge = mCurrentChallenge;

    }

    public void setmCompletedChallenge(CompletedChallenge<Bitmap> mCompletedChallenge) {
        this.mCompletedChallenge = mCompletedChallenge;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}
