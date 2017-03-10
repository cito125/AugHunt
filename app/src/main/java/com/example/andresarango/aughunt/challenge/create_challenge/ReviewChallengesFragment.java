package com.example.andresarango.aughunt.challenge.create_challenge;


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
import com.example.andresarango.aughunt.challenge.challenges_adapters.review.CompletedChallengeViewholderListener;
import com.example.andresarango.aughunt.challenge.challenges_adapters.review.ReviewChallengeAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;

/**
 * Created by Millochka on 3/6/17.
 */

public class ReviewChallengesFragment extends Fragment {
    @BindView(R.id.challanges_for_review)
    RecyclerView mRecyclerView;

    private ChallengePhoto mChallengeToReview;
    private CompletedChallengeViewholderListener mListener;

    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    private Map<String, ChallengePhotoCompleted> challengeMap = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        initializeRecyclerView(view);

    }

    public void setChallengeToReview(ChallengePhoto challengeToReview) {
        mChallengeToReview = challengeToReview;
    }

    public void initializeViews(View view) {
        ImageView challengePhoto = (ImageView) view.findViewById(R.id.review_challenge_picture);
        TextView hint = (TextView) view.findViewById(R.id.review_challenge_hit);
        TextView usersAccepted = (TextView) view.findViewById(R.id.usersaccepted);

        Glide.with(getContext()).load(mChallengeToReview.getPhotoUrl()).into(challengePhoto);
        hint.setText("Challenge Hint: " + mChallengeToReview.getHint());
        usersAccepted.setText("Users Accepted: " + String.valueOf(mChallengeToReview.getPursuing()));

    }

    public void initializeRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.challanges_for_review);
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

    public void setmListener(CompletedChallengeViewholderListener mListener) {
        this.mListener = mListener;
    }
}
