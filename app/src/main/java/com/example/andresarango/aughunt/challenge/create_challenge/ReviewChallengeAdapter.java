package com.example.andresarango.aughunt.challenge.create_challenge;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.CompletedChallenge;
import com.example.andresarango.aughunt.challenge.ChallengeViewholderListener;

import java.util.List;

/**
 * Created by Millochka on 3/7/17.
 */

class ReviewChallengeAdapter extends RecyclerView.Adapter {

    private List<CompletedChallenge<Bitmap>> mCompletedChallanges;
    private Challenge<Bitmap> mCurrentChallenge;
    private ChallengeViewholderListener<Bitmap> mListener;

    ReviewChallengeAdapter(Challenge<Bitmap> challenge, ChallengeViewholderListener<Bitmap> listener){
        this.mCurrentChallenge=challenge;
        this.mCompletedChallanges=challenge.getmCompletedChallenges();
        this.mListener=listener;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_challenge,parent,false);

        return new ReviewChallengeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ReviewChallengeViewHolder reviewChallengeViewHolder=(ReviewChallengeViewHolder) holder;
        reviewChallengeViewHolder.bind(mCompletedChallanges.get(position));
        reviewChallengeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onChallengeClicked(mCompletedChallanges.get(position),mCurrentChallenge);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mCompletedChallanges.size();
    }
}