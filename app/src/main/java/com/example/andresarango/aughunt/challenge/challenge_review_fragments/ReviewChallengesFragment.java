package com.example.andresarango.aughunt.challenge.challenge_review_fragments;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.ChallengePhoto;
import com.example.andresarango.aughunt.challenge.ChallengePhotoCompleted;
import com.example.andresarango.aughunt.challenge.challenges_adapters.review.CompletedChallengeListener;
import com.example.andresarango.aughunt.challenge.challenges_adapters.review.ReviewChallengeAdapter;
import com.example.andresarango.aughunt.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Millochka on 3/6/17.
 */

public class ReviewChallengesFragment extends Fragment {
    @BindView(R.id.challanges_for_review) RecyclerView mRecyclerView;
    @BindView(R.id.tv_user_points) TextView mUserPointsTv;
    @BindView(R.id.review_number) TextView mPendingReview;
    @BindView(R.id.pending_review) TextView mPending;

    private ChallengePhoto mChallengeToReview;
    private CompletedChallengeListener mListener;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    private Map<String, ChallengePhotoCompleted> challengeMap = new HashMap<>();
    private int mPendingReviewIndicator = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        initializeViews();
        initializeRecyclerView();
        retrieveUserFromFirebaseAndSetProfile();

    }

    public void setChallengeToReview(ChallengePhoto challengeToReview) {
        mChallengeToReview = challengeToReview;
    }

    public void initializeViews() {
        ImageView challengePhoto = (ImageView) getView().findViewById(R.id.review_challenge_picture);
        TextView hint = (TextView) getView().findViewById(R.id.review_challenge_hit);
        TextView usersAccepted = (TextView) getView().findViewById(R.id.usersaccepted);

        Glide.with(getContext()).load(mChallengeToReview.getPhotoUrl()).into(challengePhoto);
        hint.setText("Challenge Hint: " + mChallengeToReview.getHint());
        usersAccepted.setText("Users Accepted: " + String.valueOf(mChallengeToReview.getPursuing()));

    }

    public void initializeRecyclerView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.challanges_for_review);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ReviewChallengeAdapter reviewChallengesAdapter = new ReviewChallengeAdapter(mListener);

        rootRef.child("completed-challenges").child(mChallengeToReview.getChallengeId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addChallengeToRecyclerView(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                updateRecyclerView(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRecyclerView.setAdapter(reviewChallengesAdapter);

    }

    private void retrieveUserFromFirebaseAndSetProfile() {
        rootRef.child("users").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                System.out.println("USER: " + user.getProfileName());
                mUserPointsTv.setText(user.getUserPoints() + " PTS");
                mUserPointsTv.setTextColor(Color.parseColor("#D81B60"));
                mPendingReview.setTypeface(mPendingReview.getTypeface(), Typeface.BOLD);
                mPendingReview.setText("2");
                mPendingReview.setTextColor(Color.parseColor("#D81B60"));
                mPending.setTextColor(Color.parseColor("#D81B60"));
                mPending.setTypeface(mPending.getTypeface(), Typeface.BOLD);
                mPending.setText("Pending review: ");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateRecyclerView(DataSnapshot dataSnapshot) {
        String challengeKey = dataSnapshot.getKey();
        List<ChallengePhotoCompleted> challengeList = new ArrayList<>();

        Set<String> challengeKeys = challengeMap.keySet();
        if (challengeKeys.contains(challengeKey)) {
            challengeMap.put(challengeKey, dataSnapshot.getValue(ChallengePhotoCompleted.class));
        }

        challengeList.clear();
        for (String key : challengeKeys) {
            challengeList.add(challengeMap.get(key));
        }

        // update recycler view
        ReviewChallengeAdapter adapter = (ReviewChallengeAdapter) mRecyclerView.getAdapter();
        adapter.setCompletedChallangesList(challengeList);
    }

    private void addChallengeToRecyclerView(DataSnapshot dataSnapshot) {
        // Key - value
        String challengeKey = dataSnapshot.getKey();
        ChallengePhotoCompleted challenge = dataSnapshot.getValue(ChallengePhotoCompleted.class);


        System.out.println("BOOM BOOM");
        // Put in challenge map
        challengeMap.put(challengeKey, challenge);
        ReviewChallengeAdapter adapter = (ReviewChallengeAdapter) mRecyclerView.getAdapter();
        adapter.addChallengeToList(challenge);


    }

    public void setmListener(CompletedChallengeListener mListener) {
        this.mListener = mListener;
    }
}
