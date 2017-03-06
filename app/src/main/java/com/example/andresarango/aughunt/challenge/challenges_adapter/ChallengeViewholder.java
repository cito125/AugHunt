package com.example.andresarango.aughunt.challenge.challenges_adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.Challenge;

class ChallengeViewholder<T> extends RecyclerView.ViewHolder {
    public ChallengeViewholder(View itemView) {
        super(itemView);
        itemView.findViewById(R.id.viewholder_challenge_hint);
        itemView.findViewById(R.id.viewholder_challenge_picture);
    }

    public void bind(Challenge<T> challenge) {

    }
}
