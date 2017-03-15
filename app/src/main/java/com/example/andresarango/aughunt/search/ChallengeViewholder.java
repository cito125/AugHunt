package com.example.andresarango.aughunt.search;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.ChallengePhoto;
import com.example.andresarango.aughunt._models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChallengeViewholder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_search_challenge_picture)
    ImageView mChallengePictureIv;
    //@BindView(R.id.tv_search_challenge_id) TextView mChallengeIdTv;
    @BindView(R.id.tv_search_challenge_owner_id)
    TextView mChallengeOwnerIdTv;
    @BindView(R.id.tv_search_challenge_hint)
    TextView mHintTextView;
    @BindView(R.id.tv_search_challenge_pursuing)
    TextView mPursuingTv;
    @BindView(R.id.tv_hint)
    TextView mHint;

    @BindView(R.id.btn_search_challenge_find)
    Button findChallengeBtn;

    private DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference();

    private final SearchChallengeHelper mListener;
    private String mProfileName = "";

    public ChallengeViewholder(View itemView, SearchChallengeHelper listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mListener = listener;


    }

    public void bind(ChallengePhoto challenge, String profileName) {
        Glide.with(itemView.getContext()).load(challenge.getPhotoUrl()).into(mChallengePictureIv);
        mChallengeOwnerIdTv.setTypeface(mChallengeOwnerIdTv.getTypeface(), Typeface.BOLD);
        mChallengeOwnerIdTv.setText("Created by " + profileName);
        mHint.setTypeface(mHint.getTypeface(), Typeface.BOLD);
//        mHint.setText("Hint: ");
        mHintTextView.setText(challenge.getHint());
        mPursuingTv.setText("People pursuing: " + challenge.getPursuing());

        findChallengeBtn.setOnClickListener(onClick(challenge));
    }

    private View.OnClickListener onClick(final ChallengePhoto challenge) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onSearchChallengeClicked(challenge);
            }
        };
    }

    public void getUserName(final ChallengePhoto challenge) {


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
