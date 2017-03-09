package com.example.andresarango.aughunt.challenge.challenges_adapters.created;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.ChallengePhoto;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Millochka on 3/5/17.
 */

class CreatedChallengeViewHolder extends RecyclerView.ViewHolder {

    private ImageView mChallengePicture;
    private TextView mChallengeStatus;
    @BindView(R.id.iv_created_challenge_image) ImageView mChallengeImageIv;
    @BindView(R.id.tv_created_challenge_id) TextView mChallengeIdTv;
    @BindView(R.id.tv_created_challenge_owner_id) TextView mOwnerIdTv;
    @BindView(R.id.tv_created_challenge_hint) TextView mHintTv;
    @BindView(R.id.tv_created_challenge_pursuing) TextView mPursuingTv;

    public CreatedChallengeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(ChallengePhoto challenge) {
        Glide.with(itemView.getContext()).load(challenge.getPhotoUrl()).into(mChallengeImageIv);
        mChallengeIdTv.setText("Challenge ID: " + challenge.getChallengeId());
        mOwnerIdTv.setText("Owner ID: " + challenge.getOwnerId());
        mHintTv.setText("Hint: " + challenge.getHint());
        mPursuingTv.setText("Pursuing: " + challenge.getPursuing());

    }

}
