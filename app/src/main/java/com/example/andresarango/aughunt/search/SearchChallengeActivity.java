package com.example.andresarango.aughunt.search;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.ChallengePhoto;
import com.example.andresarango.aughunt._models.DAMLocation;
import com.example.andresarango.aughunt._models.User;
import com.example.andresarango.aughunt.create.CreateChallengeCameraActivity;
import com.example.andresarango.aughunt.leaderboard.LeaderBoardActivity;
import com.example.andresarango.aughunt.profile.ProfileActivity;
import com.example.andresarango.aughunt.profile.viewpager.created.CreatedChallengeListener;
import com.example.andresarango.aughunt.review.PendingReviewFragment;
import com.example.andresarango.aughunt.review.PopFragmentListener;
import com.example.andresarango.aughunt.review.ReviewChallengesFragment;
import com.example.andresarango.aughunt.util.bottom_nav_helper.BottomNavigationViewHelper;
import com.example.andresarango.aughunt.util.challenge_dialog_fragment.ChallengeDialogFragment;
import com.example.andresarango.aughunt.util.snapshot_callback.SnapshotHelper;
import com.google.android.gms.awareness.snapshot.LocationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchChallengeActivity extends AppCompatActivity implements SnapshotHelper.SnapshotListener, SearchChallengeHelper, CreatedChallengeListener, PopFragmentListener {
    private static final int LOCATION_PERMISSION = 1245;

    private ChallengesAdapter mNearbyChallengesAdapter;

    private DAMLocation userLocation;
    private Double radius = 100.0;

    private Map<String, ChallengePhoto> challengeMap = new HashMap<>();
    private List<ChallengePhoto> challengeList = new ArrayList<>();
    private Set<String> submittedChallengeSet = new HashSet<>();

    @BindView(R.id.search_challenge_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_user_points)
    TextView mUserPointsTv;
    @BindView(R.id.review_number)
    TextView mPendingReview;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNav;
    @BindView(R.id.pending_review)
    TextView mPending;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    private int mPendingReviewIndicator = 0;
    private ChallengePhoto mSelectedChallenge;
    private ReviewChallengesFragment mReviewChallengesFragment;
    private PendingReviewFragment mPendingReviewFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges_searched);
        ButterKnife.bind(this);

        requestPermission();
        retrieveUserFromFirebaseAndSetProfile();
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {

        mBottomNav.getMenu().getItem(3).setChecked(true);// Search item
        mBottomNav.getMenu().getItem(2).setChecked(false);// Leaderboard
        mBottomNav.getMenu().getItem(1).setChecked(false); // Create item
        mBottomNav.getMenu().getItem(0).setChecked(false); // Profile item
        BottomNavigationViewHelper.disableShiftMode(mBottomNav);


        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.create_challenge:
                        Intent createChallenge = new Intent(getApplicationContext(), CreateChallengeCameraActivity.class);
                        startActivity(createChallenge);
                        break;
                    case R.id.user_profile:
                        Intent userProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(userProfile);
                        break;
                    case R.id.leaderboard:
                        Intent leadeBoard = new Intent(getApplicationContext(), LeaderBoardActivity.class);
                        startActivity(leadeBoard);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("CALLED ON START");
        mBottomNav.getMenu().getItem(3).setChecked(true);
        mBottomNav.getMenu().getItem(2).setChecked(false);  // Search item
        mBottomNav.getMenu().getItem(1).setChecked(false); // Create item
        mBottomNav.getMenu().getItem(0).setChecked(false); // Profile item

        mPendingReviewIndicator = 0;
        challengeList.clear();
        challengeMap.clear();
        submittedChallengeSet.clear();
        SnapshotHelper snapshotHelper = new SnapshotHelper(this);
        snapshotHelper.runSnapshot(getApplicationContext());
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        mNearbyChallengesAdapter = new ChallengesAdapter(this);
        mRecyclerView.setAdapter(mNearbyChallengesAdapter);
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

    private void initialize() {
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

        System.out.println("CALLED GETTING LIST OF SUBMITTED CHALLENGES");
        getListOfSubmittedChallenges(dataSnapshot);
    }

    private void getListOfSubmittedChallenges(final DataSnapshot dataSnapshot) {
        final String challengeKey = dataSnapshot.getKey();
        final ChallengePhoto challenge = dataSnapshot.getValue(ChallengePhoto.class);

        rootRef.child("submitted-challenges").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    submittedChallengeSet.add(snapshot.getKey());
                    System.out.println(snapshot.getKey());
                }

                if (challenge.getLocation().isWithinRadius(userLocation, radius) &&
                        !submittedChallengeSet.contains(challengeKey)) {
                    System.out.println("ADDING ONE TO RV");
                    challengeMap.put(challengeKey, challenge);
                    challengeList.add(challengeMap.get(challengeKey));

                    mNearbyChallengesAdapter.setChallengeList(challengeList);
                } else {
                    System.out.println("NOT WITHIN RADIUS / HAS SUBMITTED");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateRecyclerView(DataSnapshot dataSnapshot) {
        String challengeKey = dataSnapshot.getKey();

        Set<String> challengeKeys = challengeMap.keySet();
        if (challengeKeys.contains(challengeKey)) {
            challengeMap.put(challengeKey, dataSnapshot.getValue(ChallengePhoto.class));
        }

        challengeList.clear();
        for (String key : challengeKeys) {
            challengeList.add(challengeMap.get(key));
        }

        mNearbyChallengesAdapter.setChallengeList(challengeList);
    }

    @Override
    public void onSearchChallengeClicked(ChallengePhoto challenge) {
        DialogFragment dialogFragment = ChallengeDialogFragment.getInstance(challenge);
        dialogFragment.show(getSupportFragmentManager(), "challenge_fragment");
    }

    @Override
    public void run(LocationResult locationResult) {
        userLocation = new DAMLocation(locationResult.getLocation().getLatitude(), locationResult.getLocation().getLongitude());
        System.out.println(userLocation.getLat() + " " + userLocation.getLng() + " <--- USER LOCATION");
        initialize();
    }

    //    PERMISSION STUFF
    private void requestPermission() {
        int locationPermission = ContextCompat.checkSelfPermission(SearchChallengeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        boolean locationPermissionIsNotGranted = locationPermission != PackageManager.PERMISSION_GRANTED;
        boolean APILevelIsTwentyThreeOrHigher = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        if (locationPermissionIsNotGranted && APILevelIsTwentyThreeOrHigher) {
            marshamallowRequestPermission();
        }
        if (locationPermissionIsNotGranted) {
            ActivityCompat.requestPermissions(SearchChallengeActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION);
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void marshamallowRequestPermission() {
        boolean userHasAlreadyRejectedPermission = !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
        if (userHasAlreadyRejectedPermission) {
            showMessageOKCancel("We need your location to find nearby challenges, is this too much trouble ?",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SearchChallengeActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    LOCATION_PERMISSION);
                        }
                    });
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(SearchChallengeActivity.this)
                .setMessage(message)
                .setPositiveButton("NO", onClickListener)
                .setNegativeButton("YES", null)
                .create()
                .show();
    }

    public void openPendingReview(View view){

        mPendingReviewFragment = new PendingReviewFragment();
        mPendingReviewFragment.setmListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.search_challenge, mPendingReviewFragment)
                .commit();

    }
    @Override
    public void onCreatedChallengeClicked(ChallengePhoto challenge) {
        mSelectedChallenge = challenge;
        startReviewChallengeFragment(challenge);

    }


//    @Override
//    public void onCompletedChallengeClicked(ChallengePhotoCompleted completedChallenge) {
//        startCompareChallengeFragment(completedChallenge, mSelectedChallenge);
//
//    }

    private void startReviewChallengeFragment(ChallengePhoto challenge) {
        mReviewChallengesFragment = new ReviewChallengesFragment();
        mReviewChallengesFragment.setChallengeToReview(challenge);
        //mReviewChallengesFragment.setmListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.search_challenge, mReviewChallengesFragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void popFragment(Fragment fragment) {
       // popFragmentFromBackStack(fragment);
    }

    @Override
    public void setTabLayoutVisibile() {

    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().beginTransaction()
                .remove(mReviewChallengesFragment)
                .commit();
    }
}
