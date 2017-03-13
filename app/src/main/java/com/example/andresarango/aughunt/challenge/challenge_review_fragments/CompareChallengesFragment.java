package com.example.andresarango.aughunt.challenge.challenge_review_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.ProfileActivity;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.ChallengePhoto;
import com.example.andresarango.aughunt.challenge.ChallengePhotoCompleted;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Millochka on 3/7/17.
 */

public class CompareChallengesFragment extends Fragment {
    @BindView(R.id.initial_challenge) ImageView mChallengeCompareWith;
    @BindView(R.id.completed_challenge) ImageView mChallengeToCompare;
    @BindView(R.id.accept) Button acceptBtn;
    @BindView(R.id.decline) Button declineBtn;

    private ChallengePhoto mCurrentChallenge;
    private ChallengePhotoCompleted mCompletedChallenge;

    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compare_challenges, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Glide.with(view.getContext()).load(mCurrentChallenge.getPhotoUrl()).into(mChallengeCompareWith);
        Glide.with(view.getContext()).load(mCompletedChallenge.getPhotoUrl()).into(mChallengeToCompare);

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeCompletedChallengeFromFirebase();
                decrementPendingReviewCounter();
                ((ProfileActivity) getActivity()).popFragmentFromBackStack();
            }
        });

        declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeCompletedChallengeFromFirebase();
                decrementPendingReviewCounter();
                ((ProfileActivity) getActivity()).popFragmentFromBackStack();

            }
        });
    }

    private void removeCompletedChallengeFromFirebase() {
        // Delete database value
        rootRef.child("completed-challenges")
                .child(mCurrentChallenge.getChallengeId())
                .child(mCompletedChallenge.getCompletedChallengeId())
                .removeValue();

        // Delete storage image
        storageRef.child("challenges")
                .child(mCurrentChallenge.getChallengeId())
                .child(mCompletedChallenge.getCompletedChallengeId())
                .delete();
    }

    public void setCurrentChallenge(ChallengePhoto mCurrentChallenge) {
        this.mCurrentChallenge = mCurrentChallenge;

    }

    public void setCompletedChallenge(ChallengePhotoCompleted mCompletedChallenge) {
        this.mCompletedChallenge = mCompletedChallenge;
    }

    private void decrementPendingReviewCounter() {
        rootRef.child("challenges").child(mCurrentChallenge.getChallengeId()).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ChallengePhoto challenge = mutableData.getValue(ChallengePhoto.class);
                challenge.setPendingReviews(challenge.getPendingReviews()-1);
                mutableData.setValue(challenge);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

}
