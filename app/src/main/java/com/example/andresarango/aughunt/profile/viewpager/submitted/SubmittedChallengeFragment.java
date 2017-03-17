package com.example.andresarango.aughunt.profile.viewpager.submitted;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.ChallengePhotoSubmitted;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Danny on 3/14/2017.
 */

public class SubmittedChallengeFragment extends Fragment {
    @BindView(R.id.rv_submitted_challenges)
    RecyclerView mRecyclerView;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    private List<ChallengePhotoSubmitted> submittedList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_challenges_submitted, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(new SubmittedAdapter());

        populateSubmittedChallengesFromFirebase();
    }

    private void populateSubmittedChallengesFromFirebase() {
        rootRef.child("submitted-challenges").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                submittedList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    submittedList.add(snapshot.getValue(ChallengePhotoSubmitted.class));
                }
                System.out.println("SIZE: " + submittedList.size());

                SubmittedAdapter adapter = (SubmittedAdapter) mRecyclerView.getAdapter();
                adapter.setSubmittedList(submittedList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
