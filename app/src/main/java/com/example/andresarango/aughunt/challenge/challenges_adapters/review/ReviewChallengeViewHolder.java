package com.example.andresarango.aughunt.challenge.challenges_adapters.review;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.ChallengePhotoCompleted;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Millochka on 3/7/17.
 */

class ReviewChallengeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_review_challenge_image) ImageView mReviewPhotoIv;
    @BindView(R.id.tv_review_challenge_id) TextView mChallengeIdTv;
    @BindView(R.id.tv_review_challenge_player_id) TextView mPlayerIdTv;


    public ReviewChallengeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bind(ChallengePhotoCompleted challengePhotoCompleted) {
        Glide.with(itemView.getContext()).load(challengePhotoCompleted.getPhotoUrl()).into(mReviewPhotoIv);
        mChallengeIdTv.setText("Challenge ID: " + challengePhotoCompleted.getCompletedChallengeId());
        mPlayerIdTv.setText("Player ID: " + challengePhotoCompleted.getPlayerId());
    }
}
