package com.example.andresarango.aughunt.challenge.create_challenge;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
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
    private CardView mCompletedChallenge;


    public ReviewChallengeViewHolder(View itemView) {
        super(itemView);

        mPlayerName=(TextView) itemView.findViewById(R.id.player_name);
        mCompletedChallenge=(CardView) itemView.findViewById(R.id.completed_challenge);

    }

    public void bind (CompletedChallenge<Bitmap> challenge){
      mPlayerName.setText(challenge.getmOwnerId());

    }

    public CardView getmCompletedChallenge() {
        return mCompletedChallenge;
    }
}
