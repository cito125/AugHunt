package com.example.andresarango.aughunt.challenge.challenge_review_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.ChallengePhoto;
import com.example.andresarango.aughunt.challenge.challenges_adapters.created.CreatedChallengesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Millochka on 3/12/17.
 */

public class PendingReviewFragment extends Fragment {

    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    private Map<String, ChallengePhoto> challengeMap = new HashMap<>();
    @BindView(R.id.pending_review_challenges) RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pending_review, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new PendingReviewChallengesAdapter());
        callFirebase();

    }

    private void callFirebase() {
        rootRef.child("challenges").addChildEventListener(new ChildEventListener() {
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
                //removeChallengeFromRecyclerView(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addChallengeToRecyclerView(DataSnapshot dataSnapshot) {
        // Key - value
        String challengeKey = dataSnapshot.getKey();
        ChallengePhoto challenge = dataSnapshot.getValue(ChallengePhoto.class);


        // Check location
        if (challenge.getOwnerId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())&& challenge.getPendingReviews()>0) {
            challengeMap.put(challengeKey, challenge);


        } else {
            System.out.println("NOT THE RIGHT USER");
        }
    }

    private void updateRecyclerView(DataSnapshot dataSnapshot) {
        String challengeKey = dataSnapshot.getKey();
        Set<String> challengeKeys = challengeMap.keySet();
        List<ChallengePhoto> challengeList = new ArrayList<>();

        if (challengeKeys.contains(challengeKey)) {
            challengeMap.put(challengeKey, dataSnapshot.getValue(ChallengePhoto.class));
        }

        for (String key : challengeKeys) {
            challengeList.add(challengeMap.get(key));
        }

        // update recycler view
        CreatedChallengesAdapter adapter = (CreatedChallengesAdapter) mRecyclerView.getAdapter();
        adapter.setChallengeList(challengeList);
    }


}
