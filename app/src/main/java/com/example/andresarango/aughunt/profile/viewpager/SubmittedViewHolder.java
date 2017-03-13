package com.example.andresarango.aughunt.profile.viewpager;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.ChallengePhotoSubmitted;
import com.example.andresarango.aughunt.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dannylui on 3/12/17.
 */

class SubmittedViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_submitted_hint)
    TextView submittedHintTv;

    @BindView(R.id.tv_submitted_owner_id)
    TextView submittedOwnerIdTv;

    @BindView(R.id.iv_submitted_challenge_image)
    ImageView submittedChallengeImageIv;

    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();


    public SubmittedViewHolder(ViewGroup parent) {
        super(inflate(parent));
        ButterKnife.bind(this, itemView);
    }

    private static View inflate(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_submitted_challenge, parent, false);
        return view;
    }

    public void bind(ChallengePhotoSubmitted submittedChallenge) {
        submittedHintTv.setText("Hint: " + submittedChallenge.getHint());
        Glide.with(itemView.getContext()).load(submittedChallenge.getPhotoUrl()).into(submittedChallengeImageIv);

        itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.progressGrey));

        if (submittedChallenge.isAccepted()) {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.acceptedGreen));
        }

        if (submittedChallenge.isDeclined()) {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.declinedRed));
        }

        rootRef.child("users").child(submittedChallenge.getOwnerId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                submittedOwnerIdTv.setText("Created by: " + user.getProfileName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
