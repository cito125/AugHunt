package com.example.andresarango.aughunt.challenge.create_challenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.ChallengePhoto;
import com.example.andresarango.aughunt.challenge.ChallengePhotoCompleted;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Millochka on 3/7/17.
 */

public class CompareChallengesFragment extends Fragment {
    @BindView(R.id.initial_challenge) ImageView mChallengeCompareWith;
    @BindView(R.id.completed_challenge) ImageView mChallengeToCompare;
    @BindView(R.id.accept) Button acceptBtn;
    @BindView(R.id.decline) Button declineBtn;

    private ChallengePhoto mCurrentChallenge;
    private ChallengePhotoCompleted mCompletedChallenge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_completed_challenge, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Glide.with(view.getContext()).load(mCurrentChallenge.getPhotoUrl()).into(mChallengeCompareWith);
        Glide.with(view.getContext()).load(mCompletedChallenge.getPhotoUrl()).into(mChallengeToCompare);


    }

    public void setCurrentChallenge(ChallengePhoto mCurrentChallenge) {
        this.mCurrentChallenge = mCurrentChallenge;

    }

    public void setCompletedChallenge(ChallengePhotoCompleted mCompletedChallenge) {
        this.mCompletedChallenge = mCompletedChallenge;
    }
}
