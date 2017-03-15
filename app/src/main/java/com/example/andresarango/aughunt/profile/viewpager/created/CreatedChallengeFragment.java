package com.example.andresarango.aughunt.profile.viewpager.created;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.ChallengePhoto;
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
 * Created by dannylui on 3/10/17.
 */

public class CreatedChallengeFragment extends Fragment {
    @BindView(R.id.created_challenges) RecyclerView mRecyclerView;
//    @BindView(R.id.fab_create_challenge) FloatingActionButton mFloatingActionButton;

    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    private Map<String, ChallengePhoto> challengeMap = new HashMap<>();
    private CreatedChallengeListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_challenges_created, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view.getRootView());

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(new CreatedChallengeAdapter(mListener));
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
        if (challenge.getOwnerId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            challengeMap.put(challengeKey, challenge);

            CreatedChallengeAdapter adapter = (CreatedChallengeAdapter) mRecyclerView.getAdapter();
            adapter.addChallengeToList(challenge);

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
        CreatedChallengeAdapter adapter = (CreatedChallengeAdapter) mRecyclerView.getAdapter();
        adapter.setChallengeList(challengeList);
    }

    public void setListener(CreatedChallengeListener mListener) {
        this.mListener = mListener;
    }
}
