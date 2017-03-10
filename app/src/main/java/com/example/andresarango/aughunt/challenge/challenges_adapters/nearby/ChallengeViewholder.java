package com.example.andresarango.aughunt.challenge.challenges_adapters.nearby;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.ChallengePhoto;
import com.example.andresarango.aughunt.challenge.challenges_adapters.created.ChallengeViewholderListener;

import butterknife.BindView;
import butterknife.ButterKnife;

class ChallengeViewholder<T> extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_search_challenge_picture) ImageView mChallengePictureIv;
    @BindView(R.id.tv_search_challenge_id) TextView mChallengeIdTv;
    @BindView(R.id.tv_search_challenge_owner_id) TextView mChallengeOwnerIdTv;
    @BindView(R.id.tv_search_challenge_hint) TextView mHintTextView;
    @BindView(R.id.tv_search_challenge_pursuing) TextView mPursuingTv;

    private final ChallengeViewholderListener mListener;

    public ChallengeViewholder(View itemView, ChallengeViewholderListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mListener = listener;

    }

    public void bind(ChallengePhoto challenge) {
        Glide.with(itemView.getContext()).load(challenge.getPhotoUrl()).into(mChallengePictureIv);
        mChallengeIdTv.setText("Challenge ID: " + challenge.getChallengeId());
        mChallengeOwnerIdTv.setText("Owner ID: " + challenge.getOwnerId());
        mHintTextView.setText("Hint: " + challenge.getHint());
        mPursuingTv.setText("# Pursuing: " + challenge.getPursuing());
        itemView.setOnClickListener(onClick(challenge));
    }

    private View.OnClickListener onClick(final ChallengePhoto challenge) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onChallengeClicked(challenge);
            }
        };
    }


}
