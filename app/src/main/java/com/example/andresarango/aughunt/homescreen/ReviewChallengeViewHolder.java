package com.example.andresarango.aughunt.homescreen;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.CompletedChallenges;

/**
 * Created by Millochka on 3/7/17.
 */

class ReviewChallengeViewHolder extends RecyclerView.ViewHolder {

    private TextView mPlayerName;

    public ReviewChallengeViewHolder(View itemView) {
        super(itemView);

        mPlayerName=(TextView) itemView.findViewById(R.id.player_name);
    }

    public void bind (CompletedChallenges<Bitmap> challenge){
      mPlayerName.setText(challenge.getmOwnerId());

    }
}
