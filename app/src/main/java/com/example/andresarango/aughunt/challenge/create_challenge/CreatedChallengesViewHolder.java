package com.example.andresarango.aughunt.challenge.create_challenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.Challenge;

/**
 * Created by Millochka on 3/5/17.
 */

class CreatedChallengesViewHolder extends RecyclerView.ViewHolder {

    private ImageView mChallengePicture;
    private TextView mChallengeStatus;
    private Context mContext;
    private CardView mCreatedChallenge;
    public CreatedChallengesViewHolder(View itemView, Context context) {
        super(itemView);
        mChallengePicture=(ImageView) itemView.findViewById(R.id.image_of_challenge);
        mChallengeStatus=(TextView) itemView.findViewById(R.id.status_of_challenge);
        this.mContext=context;
        mCreatedChallenge=(CardView) itemView.findViewById(R.id.created_challenge_card);
    }

    public void bind (Challenge<Bitmap> challenge){

        String status="";

        if(challenge.getmStatus()){
            status="Active";
        }else {status="Completed";}

        mChallengeStatus.setText(status);
        BitmapDrawable d= new BitmapDrawable(mContext.getResources(), challenge.getmChallenge());
        mChallengePicture.setImageDrawable(d);

    }

    public CardView getmCreatedChallenge() {
        return mCreatedChallenge;
    }
}
