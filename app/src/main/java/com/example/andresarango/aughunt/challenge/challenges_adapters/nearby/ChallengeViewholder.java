package com.example.andresarango.aughunt.challenge.challenges_adapters.nearby;


import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.models.ChallengePhoto;
import com.example.andresarango.aughunt.challenge.challenges_adapters.created.CreatedChallengeListener;
import com.example.andresarango.aughunt.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

class ChallengeViewholder<T> extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_search_challenge_picture) ImageView mChallengePictureIv;
    //@BindView(R.id.tv_search_challenge_id) TextView mChallengeIdTv;
    @BindView(R.id.tv_search_challenge_owner_id) TextView mChallengeOwnerIdTv;
    @BindView(R.id.tv_search_challenge_hint) TextView mHintTextView;
    @BindView(R.id.tv_search_challenge_pursuing) TextView mPursuingTv;
    @BindView(R.id.tv_hint) TextView mHint;

    private DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference();

    private final CreatedChallengeListener mListener;
    private String mProfileName="";

    public ChallengeViewholder(View itemView, CreatedChallengeListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mListener = listener;


    }

    public void bind(ChallengePhoto challenge, String profileName) {


        Glide.with(itemView.getContext()).load(challenge.getPhotoUrl()).into(mChallengePictureIv);
        mChallengeOwnerIdTv.setTypeface(mChallengeOwnerIdTv.getTypeface(), Typeface.BOLD);
        mChallengeOwnerIdTv.setText("Created by  " + profileName);
        mHint.setTypeface(mHint.getTypeface(), Typeface.BOLD);
        mHint.setText("Hint: ");
        mHintTextView.setText(challenge.getHint());
        mPursuingTv.setText("# Pursuing: " + challenge.getPursuing());
        itemView.setOnClickListener(onClick(challenge));
    }

    private View.OnClickListener onClick(final ChallengePhoto challenge) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCreatedChallengeClicked(challenge);
            }
        };
    }

public void getUserName(final ChallengePhoto challenge){


    mDataBase.child("users").child(challenge.getOwnerId()).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            User user = dataSnapshot.getValue(User.class);

            bind(challenge, user.getProfileName());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });


}
}
