package com.example.andresarango.aughunt;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.andresarango.aughunt.models.ChallengePhoto;
import com.example.andresarango.aughunt.models.User;
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
 * Created by Millochka on 3/14/17.
 */

public class LeaderBoardActivity extends AppCompatActivity{

    @BindView(R.id.tv_user_points) TextView mUserPointsTv;
    @BindView(R.id.review_number) TextView mPendingReview;
    @BindView(R.id.pending_review)
    TextView mPending;
    @BindView(R.id.list_of_users) RecyclerView mRecyclerView;
    @BindView(R.id.bottom_navigation) BottomNavigationView mBottomNav;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private Map<String, User> mUserMap = new HashMap<>();
    private List<User> mUserList = new ArrayList<>();
    private LeaderBoardAdapter mLeaderBoardAdapter;
    private int mPendingReviewIndicator = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        ButterKnife.bind(this);
        setUpRecyclerView();
        retrieveUserFromFirebaseAndSetProfile();
        initializePoints();
        setupBottomNavigation();
        initialize();

    }

    private void setupBottomNavigation() {



        BottomNavigationViewHelper.disableShiftMode(mBottomNav);
        mBottomNav.getMenu().getItem(0).setChecked(false);
        mBottomNav.getMenu().getItem(3).setChecked(false);
        mBottomNav.getMenu().getItem(2).setChecked(true);// Profile item
        mBottomNav.getMenu().getItem(1).setChecked(false);// Create item
        // Leaderboard
        // Search item




        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.create_challenge:
                        Intent createChallenge = new Intent(getApplicationContext(), ChallengeTemplateActivity.class);
                        startActivity(createChallenge);
                        break;
                    case R.id.user_profile:
                        Intent userProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(userProfile);
                        break;
                    case R.id.search_challenge:
                        Intent searchChallenge = new Intent(getApplicationContext(), SearchChallengeActivity.class);
                        startActivity(searchChallenge);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBottomNav.getMenu().getItem(3).setChecked(false);// Search item
        mBottomNav.getMenu().getItem(2).setChecked(true);// Leaderboard
        mBottomNav.getMenu().getItem(1).setChecked(false); // Create item
        mBottomNav.getMenu().getItem(0).setChecked(false); // Profile item
        mPendingReviewIndicator = 0;
    }

    private void initialize() {
        rootRef.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addUserToRecyclerView(dataSnapshot);
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

    private void updateRecyclerView(DataSnapshot dataSnapshot) {
        String userKey = dataSnapshot.getKey();

        Set<String> userKeys = mUserMap.keySet();

        mUserMap.put(userKey, dataSnapshot.getValue(User.class));


        mUserList.clear();
        for (String key : userKeys) {
            mUserList.add(mUserMap.get(key));
        }

        mLeaderBoardAdapter.setUserList(mUserList);
    }

    private void addUserToRecyclerView(DataSnapshot dataSnapshot) {
        // Key - value
        String userKey = dataSnapshot.getKey();
        User user = dataSnapshot.getValue(User.class);


            mUserMap.put(userKey, user);

            LeaderBoardAdapter adapter = (LeaderBoardAdapter) mRecyclerView.getAdapter();
            adapter.addUserToList(user);
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLeaderBoardAdapter = new LeaderBoardAdapter();
        mRecyclerView.setAdapter(mLeaderBoardAdapter);
    }

    private void retrieveUserFromFirebaseAndSetProfile() {
        rootRef.child("users").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                System.out.println("USER: " + user.getProfileName());
                mUserPointsTv.setText(user.getUserPoints() + " PTS");
                mPendingReview.setTypeface(mPendingReview.getTypeface(), Typeface.BOLD);
                mPendingReview.setText(String.valueOf(mPendingReviewIndicator));
                mPending.setTypeface(mPending.getTypeface(), Typeface.BOLD);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void initializePoints() {
        rootRef.child("challenges").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addChallengeToRecyclerView(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //updateRecyclerView(dataSnapshot);

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
        ChallengePhoto challenge = dataSnapshot.getValue(ChallengePhoto.class);

        if (challenge.getOwnerId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            mPendingReviewIndicator += challenge.getPendingReviews();
            mPendingReview.setText(String.valueOf(mPendingReviewIndicator));
            if (mPendingReviewIndicator > 0) {
                mPendingReview.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                mPending.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            } else {
                mPendingReview.setTextColor(ContextCompat.getColor(this, R.color.lightGrey));
                mPending.setTextColor(ContextCompat.getColor(this, R.color.lightGrey));
            }
        }

    }

}
