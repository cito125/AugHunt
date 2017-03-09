package com.example.andresarango.aughunt.challenge.challenges_adapters.review;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.ChallengePhotoCompleted;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Millochka on 3/7/17.
 */

class ReviewChallengeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_review_challenge_id) TextView mChallengeIdTv;
    @BindView(R.id.tv_review_challenge_player_id) TextView mPlayerIdTv;


    public ReviewChallengeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bind(ChallengePhotoCompleted challengePhotoCompleted) {
        mChallengeIdTv.setText("Challenge ID: " + challengePhotoCompleted.getChallengeId());
        mPlayerIdTv.setText(challengePhotoCompleted.getPlayerId());
    }
}
