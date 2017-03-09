package com.example.andresarango.aughunt.challenge.create_challenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaRecorder;
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

public class CompareChallengesFragment extends Fragment {

    private Challenge<Bitmap> mCurrentChallenge;
    private CompletedChallenge<Bitmap> mCompletedChallenge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_completed_challenge, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView mChallengeCompareWith = (ImageView) view.findViewById(R.id.initial_challenge);
        ImageView mChallengeToCompare = (ImageView) view.findViewById(R.id.completed_challenge);
        BitmapDrawable d = new BitmapDrawable(view.getContext().getResources(), mCurrentChallenge.getChallenge());
        mChallengeCompareWith.setImageDrawable(d);
        BitmapDrawable d1 = new BitmapDrawable(view.getContext().getResources(), mCompletedChallenge.getCompletedChallenge());
        mChallengeToCompare.setImageDrawable(d1);

    }

    public void setCurrentChallenge(Challenge<Bitmap> mCurrentChallenge) {
        this.mCurrentChallenge = mCurrentChallenge;

    }

    public void setCompletedChallenge(CompletedChallenge<Bitmap> mCompletedChallenge) {
        this.mCompletedChallenge = mCompletedChallenge;
    }
}
