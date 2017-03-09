package com.example.andresarango.aughunt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.ChallengePhoto;
import com.example.andresarango.aughunt.challenge.ChallengePhotoCompleted;
import com.example.andresarango.aughunt.challenge.CompletedChallenge;
import com.example.andresarango.aughunt.challenge.challenges_adapters.ChallengeViewholderListener;
import com.example.andresarango.aughunt.challenge.challenges_adapters.created.CreatedChallengesAdapter;
import com.example.andresarango.aughunt.challenge.challenges_adapters.review.CompletedChallengeViewholderListener;
import com.example.andresarango.aughunt.challenge.create_challenge.CompareChallengesFragment;
import com.example.andresarango.aughunt.challenge.create_challenge.ReviewChallengesFragment;
import com.example.andresarango.aughunt.firebase.FirebaseEmulator;
import com.google.firebase.auth.FirebaseAuth;
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

import butterknife.BindView;
import butterknife.ButterKnife;


public class CreateChallengeActivity extends AppCompatActivity implements
        ChallengeViewholderListener,
        CompletedChallengeViewholderListener {

    @BindView(R.id.created_challenges) RecyclerView mRecyclerView;

    private FirebaseEmulator mFirebaseEmulator;
    private ReviewChallengesFragment mReviewChallengesFragment;
    private Boolean mIsInflated;
    private CompareChallengesFragment mCompareChallengesFragment;
    private ChallengePhoto mSelectedChallenge;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private Map<String, ChallengePhoto> challengeMap = new HashMap<>();
    private List<ChallengePhoto> challengeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);
        ButterKnife.bind(this);
        initialize();

    }

    private void initialize() {
        FloatingActionButton createChallengeBtn = (FloatingActionButton) findViewById(R.id.fab_create_challenge);
        createChallengeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChallengeTemplateActivity.class);
                startActivity(intent);
            }
        });
        mIsInflated = false;
//        mFirebaseEmulator = new FirebaseEmulator(this);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new CreatedChallengesAdapter(this));

        rootRef.child("challenges").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addChallengeToRecyclerView(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
            System.out.println("BOOM BOOM");
            // Put in challenge map
            challengeMap.put(challengeKey, challenge);
            challengeList.add(challengeMap.get(challengeKey));

            CreatedChallengesAdapter adapter = (CreatedChallengesAdapter) mRecyclerView.getAdapter();
            adapter.setChallengeList(challengeList);
            //System.out.println(challenge.getUserId() + " " + challenge.getLocation().getLat() + " " + challenge.getLocation().getLng());
        } else {
            System.out.println("NOT THE RIGHT USER");
        }
    }

//    public void initRecyclerView() {
//        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.created_challenges);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        List<Challenge<Bitmap>> currentChallengesList = historyOfCreatedChalalnges(
//                mFirebaseEmulator.getChallenges(),
//                mFirebaseEmulator.getCurrentUser().getUserId());
//        CreatedChallengesAdapter challengesAdapter = new CreatedChallengesAdapter(this);
//        challengesAdapter.setChallengeList(currentChallengesList);
//        mRecyclerView.setAdapter(challengesAdapter);
//
//    }


    public List<Challenge<Bitmap>> historyOfCreatedChalalnges(List<Challenge<Bitmap>> challengeList, String ownerID) {

        List<Challenge<Bitmap>> userChallengeList = new ArrayList<>();

        for (Challenge<Bitmap> challenge : challengeList) {
            if (challenge.getmOwnerId().equalsIgnoreCase(ownerID)) {
                userChallengeList.add(challenge);
            }

        }
        return userChallengeList;

    }


    @Override
    public void onChallengeClicked(ChallengePhoto challenge) {
        mSelectedChallenge = challenge;
        startReviewChallengeFragment(challenge);
    }

    @Override
    public void onCompletedChallengeClicked(ChallengePhotoCompleted completedChallenge) {
        // startCompareChallengeFragment(completedChallenge, mSelectedChallenge);
    }

    private void startReviewChallengeFragment(ChallengePhoto challenge) {
        mReviewChallengesFragment = new ReviewChallengesFragment();
        mReviewChallengesFragment.setChallengeToReview(challenge);
        mReviewChallengesFragment.setmListener(this);
        mIsInflated = !mIsInflated;

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_for_review, mReviewChallengesFragment)
                .commit();
    }


    private void startCompareChallengeFragment(CompletedChallenge<Bitmap> completedChallenge, Challenge<Bitmap> challenge) {
        mCompareChallengesFragment = new CompareChallengesFragment();
        mCompareChallengesFragment.setCompletedChallenge(completedChallenge);
        mCompareChallengesFragment.setCurrentChallenge(challenge);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_for_review, mCompareChallengesFragment)
                .commit();
    }


    @Override
    public void onBackPressed() {

        if (mIsInflated) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(mReviewChallengesFragment)
                    .commit();
            mIsInflated = !mIsInflated;

        } else {
            super.onBackPressed();
        }

    }

    public void reviewResult(View view) {

        switch (view.getId()) {

            case R.id.decline:

                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(mCompareChallengesFragment)
                        .commit();
                break;

            case R.id.accept:

                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(mCompareChallengesFragment)
                        .commit();
                break;

        }


    }

}
