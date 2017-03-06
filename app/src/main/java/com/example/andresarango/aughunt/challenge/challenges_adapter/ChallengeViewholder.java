package com.example.andresarango.aughunt.challenge.challenges_adapter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.ChallengeActivity;

class ChallengeViewholder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView mHintTextView;
    private final ImageView mChallengeImageView;

    public ChallengeViewholder(View itemView) {
        super(itemView);
        mHintTextView = (TextView) itemView.findViewById(R.id.viewholder_challenge_hint);
        mChallengeImageView = (ImageView) itemView.findViewById(R.id.viewholder_challenge_picture);
    }

    public void bind(Challenge<T> challenge) {
        mHintTextView.setText(challenge.getHint());
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        
    }
}
