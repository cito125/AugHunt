package com.example.andresarango.aughunt.homescreen;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.CompletedChallenges;

import java.util.List;

/**
 * Created by Millochka on 3/7/17.
 */

class ReviewChallengeAdapter extends RecyclerView.Adapter {

    private List<CompletedChallenges<Bitmap>> mCompletedChallanges;

    ReviewChallengeAdapter(Challenge<Bitmap> challenge){
        this.mCompletedChallanges=challenge.getmCompletedChallenges();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_review_challenge,parent,false);

        return new ReviewChallengeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mCompletedChallanges.size();
    }
}
