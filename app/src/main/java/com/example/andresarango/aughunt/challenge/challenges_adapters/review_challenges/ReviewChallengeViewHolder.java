package com.example.andresarango.aughunt.challenge.challenges_adapters.review_challenges;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.CompletedChallenge;

/**
 * Created by Millochka on 3/7/17.
 */

class ReviewChallengeViewHolder extends RecyclerView.ViewHolder {

    private TextView mPlayerName;


    public ReviewChallengeViewHolder(View itemView) {
        super(itemView);
        mPlayerName = (TextView) itemView.findViewById(R.id.player_name);

    }

    public void bind(CompletedChallenge<Bitmap> challenge) {
        mPlayerName.setText(challenge.getOwnerId());

    }
}
