package com.example.andresarango.aughunt.challenge.challenges_adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.challenge_dialog_fragment.DialogFragmentListener;

class ChallengeViewholder<T> extends RecyclerView.ViewHolder {

    private final TextView mHintTextView;
    private final ImageView mChallengeImageView;
    private final DialogFragmentListener mListener;

    public ChallengeViewholder(View itemView, DialogFragmentListener listener) {
        super(itemView);
        mHintTextView = (TextView) itemView.findViewById(R.id.viewholder_challenge_hint);
        mChallengeImageView = (ImageView) itemView.findViewById(R.id.viewholder_challenge_picture);
        mListener = listener;

    }

    public void bind(Challenge<T> challenge) {
        mHintTextView.setText(challenge.getHint());
        itemView.setOnClickListener(onClick(challenge));
    }

    private View.OnClickListener onClick(final Challenge<T> challenge) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.startDialogueFragment(challenge);
            }
        };
    }


}
