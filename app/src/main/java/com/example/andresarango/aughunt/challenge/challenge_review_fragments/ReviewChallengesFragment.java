package com.example.andresarango.aughunt.challenge.challenge_review_fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daprlabs.aaron.swipedeck.SwipeDeck;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.challenges_adapters.swipe_review.ReviewSwipeAdapter;
import com.example.andresarango.aughunt.models.ChallengePhoto;
import com.example.andresarango.aughunt.models.ChallengePhotoCompleted;
import com.example.andresarango.aughunt.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Millochka on 3/6/17.
 */

public class ReviewChallengesFragment extends Fragment implements SwipeDeck.SwipeDeckCallback {


    private ChallengePhoto mChallengeToReview;

    private ReviewSwipeAdapter mSwipeAdapter = new ReviewSwipeAdapter();

    Deque<ChallengePhotoCompleted> mCompletedChallengeDeck = new LinkedList<>();

    @BindView(R.id.swipe_deck) SwipeDeck mSwipeDeck;
    @BindView(R.id.tv_user_points) TextView mUserPointsTv;
    @BindView(R.id.review_number) TextView mPendingReview;
    @BindView(R.id.pending_review) TextView mPending;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();


    private Map<String, ChallengePhotoCompleted> challengeMap = new HashMap<>();
    private PopFragmentListener mListener;
    private int mPendingReviewIndicator = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review_challenges, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initializeSwiperView();
        retrieveUserFromFirebaseAndSetProfile();

    }

    public void setChallengeToReview(ChallengePhoto challengeToReview) {
        mChallengeToReview = challengeToReview;
    }


    public void initializeSwiperView() {

        mSwipeDeck.setCallback(this);
        mSwipeDeck.setLeftImage(R.id.left_image);
        mSwipeDeck.setRightImage(R.id.right_image);

        rootRef.child("completed-challenges").child(mChallengeToReview.getChallengeId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addChallengeToSwiperView(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                updateSwiperView(dataSnapshot);
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

        mSwipeDeck.setAdapter(mSwipeAdapter);
    }

    private void retrieveUserFromFirebaseAndSetProfile() {
        rootRef.child("users").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                System.out.println("USER: " + user.getProfileName());
                mUserPointsTv.setText(user.getUserPoints() + " PTS");
                mPendingReview.setTypeface(mPendingReview.getTypeface(), Typeface.BOLD);
                mPendingReview.setText("2");
                mPending.setTypeface(mPending.getTypeface(), Typeface.BOLD);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        rootRef.child("challenges").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChallengePhoto challenge = dataSnapshot.getValue(ChallengePhoto.class);
                if (challenge.getOwnerId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    mPendingReviewIndicator += challenge.getPendingReviews();
                    mPendingReview.setText(String.valueOf(mPendingReviewIndicator));
                    if (mPendingReviewIndicator > 0) {
                        mPendingReview.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                        mPending.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                    } else {
                        mPendingReview.setTextColor(ContextCompat.getColor(getContext(), R.color.lightGrey));
                        mPending.setTextColor(ContextCompat.getColor(getContext(), R.color.lightGrey));
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
    }

    private void updateSwiperView(DataSnapshot dataSnapshot) {
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

        mCompletedChallengeDeck.clear();
        mCompletedChallengeDeck.addAll(challengeList);

        mSwipeAdapter.setCompletedChallengeList(challengeList);
    }

    private void addChallengeToSwiperView(DataSnapshot dataSnapshot) {
        // Key - value
        String challengeKey = dataSnapshot.getKey();
        ChallengePhotoCompleted challenge = dataSnapshot.getValue(ChallengePhotoCompleted.class);


        System.out.println("BOOM BOOM");
        // Put in challenge map
        challengeMap.put(challengeKey, challenge);
        mCompletedChallengeDeck.addFirst(challenge);
        mSwipeAdapter.addChallenge(challenge);


    }

    @Override
    public void cardSwipedLeft(long stableId) {
        removeCompletedChallengeFromFirebase(mCompletedChallengeDeck.removeLast());
        decrementPendingReviewCounter();
        if(mCompletedChallengeDeck.isEmpty()){
            mListener.popFragment(this);
        }
    }

    @Override
    public void cardSwipedRight(long stableId) {
        removeCompletedChallengeFromFirebase(mCompletedChallengeDeck.removeLast());
        decrementPendingReviewCounter();
        if(mCompletedChallengeDeck.isEmpty()){
            mListener.popFragment(this);
        }
    }

    private void removeCompletedChallengeFromFirebase(ChallengePhotoCompleted completedChallenge) {
        // Delete database value
        rootRef.child("completed-challenges")
                .child(mChallengeToReview.getChallengeId())
                .child(completedChallenge.getCompletedChallengeId())
                .removeValue();

        // Delete storage image
        storageRef.child("challenges")
                .child(mChallengeToReview.getChallengeId())
                .child(completedChallenge.getCompletedChallengeId())
                .delete();
    }

    private void decrementPendingReviewCounter() {
        rootRef.child("challenges").child(mChallengeToReview.getChallengeId()).runTransaction(new Transaction.Handler() {
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

    public void setPopFragmentListener(PopFragmentListener listener) {
        mListener = listener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener.setTabLayoutVisibile();
    }
}
