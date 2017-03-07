package com.example.andresarango.aughunt.homescreen;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.Challenge;

/**
 * Created by Millochka on 3/6/17.
 */

public class ChallangeReviewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Challenge<Bitmap> mCgallengeToReview;
    private Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        mRecyclerView=(RecyclerView) view.findViewById(R.id.challanges_for_review);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(new ReviewChallengeAdapter(mCgallengeToReview));

    }

    public void setmCgallengeToReview(Challenge<Bitmap> mCgallengeToReview) {
        this.mCgallengeToReview = mCgallengeToReview;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}
