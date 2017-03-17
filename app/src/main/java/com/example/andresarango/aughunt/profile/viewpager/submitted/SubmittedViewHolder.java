package com.example.andresarango.aughunt.profile.viewpager.submitted;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.ChallengePhotoSubmitted;
import com.example.andresarango.aughunt._models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Danny on 3/14/2017.
 */

public class SubmittedViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.tv_submitted_hint)
    TextView submittedHintTv;

    @BindView(R.id.tv_submitted_owner_id)
    TextView submittedOwnerIdTv;

    @BindView(R.id.iv_submitted_challenge_image)
    ImageView submittedChallengeImageIv;

    @BindView(R.id.iv_original_challenge_image)
    ImageView originalChallengeImageIv;

    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();


    public SubmittedViewHolder(ViewGroup parent) {
        super(inflate(parent));
        ButterKnife.bind(this, itemView);
    }

    private static View inflate(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_challenge_submitted, parent, false);
        return view;
    }

    public void bind(ChallengePhotoSubmitted submittedChallenge) {
        submittedHintTv.setText("Hint: " + submittedChallenge.getHint());
        Glide.with(itemView.getContext()).load(submittedChallenge.getSubmittedPhotoUrl()).into(submittedChallengeImageIv);
        Glide.with(itemView.getContext()).load(submittedChallenge.getOriginalPhotoUrl()).into(originalChallengeImageIv);

        itemView.setBackgroundColor(0xAA000000);
        if (submittedChallenge.isAccepted()) {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.acceptedGreen));
        } else if (submittedChallenge.isReviewed() && !submittedChallenge.isAccepted()) {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.declinedRed));
        }

        rootRef.child("users").child(submittedChallenge.getOwnerId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                submittedOwnerIdTv.setText("Challenge made by: " + user.getProfileName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
