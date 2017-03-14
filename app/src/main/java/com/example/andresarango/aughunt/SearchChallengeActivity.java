package com.example.andresarango.aughunt;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andresarango.aughunt.challenge.challenge_dialog_fragment.ChallengeDialogFragment;
import com.example.andresarango.aughunt.challenge.challenges_adapters.created.CreatedChallengeListener;
import com.example.andresarango.aughunt.challenge.challenges_adapters.nearby.ChallengesAdapter;
import com.example.andresarango.aughunt.location.DAMLocation;
import com.example.andresarango.aughunt.models.ChallengePhoto;
import com.example.andresarango.aughunt.models.User;
import com.example.andresarango.aughunt.snapshot_callback.SnapshotHelper;
import com.google.android.gms.awareness.snapshot.LocationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchChallengeActivity extends AppCompatActivity implements CreatedChallengeListener, SnapshotHelper.SnapshotListener {
    private static final int LOCATION_PERMISSION = 1245;

    private ImageView mChallengeImage;
    private TextView mHint;
    private TextView mLocation;
    private RecyclerView mRecyclerView;
    private ChallengesAdapter mNearbyChallengesAdapter;

    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private DAMLocation userLocation;
    private Double radius = 100.0;
    private Map<String, ChallengePhoto> challengeMap = new HashMap<>();
    private List<ChallengePhoto> challengeList = new ArrayList<>();
    @BindView(R.id.tv_user_points) TextView mUserPointsTv;
    @BindView(R.id.review_number) TextView mPendingReview;
    @BindView(R.id.bottom_navigation) BottomNavigationView mBottomNav;
    @BindView(R.id.pending_review) TextView mPending;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_challenge);
        ButterKnife.bind(this);
        SnapshotHelper snapshotHelper = new SnapshotHelper(this);
        snapshotHelper.runSnapshot(getApplicationContext());
        retrieveUserFromFirebaseAndSetProfile();
        requestPermission();
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               switch (item.getItemId()){

                   case R.id.create_challenge:
                       Intent createChallenge=new Intent(getApplicationContext(), ChallengeTemplateActivity.class);
                       startActivity(createChallenge);


                       break;
                   case R.id.homepage:
                       Intent homePage=new Intent(getApplicationContext(), SearchChallengeActivity.class);
                       startActivity(homePage);

                       break;
                   case R.id.user_profile:
                       Intent userProfile=new Intent(getApplicationContext(),ProfileActivity.class);
                       startActivity(userProfile);
                       break;
                }
                return true;
            }
        });



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

    private void initialize() {
        initializeViews();
        setUpRecyclerView();

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
        if (challenge.getLocation().isWithinRadius(userLocation, radius)) {
            // Put in challenge map
            challengeMap.put(challengeKey, challenge);
            challengeList.add(challengeMap.get(challengeKey));

            mNearbyChallengesAdapter.setChallengeList(challengeList);
        } else {
            System.out.println("NOT WITHIN RADIUS");
        }
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

        // update recycler view
        mNearbyChallengesAdapter.setChallengeList(challengeList);
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mNearbyChallengesAdapter = new ChallengesAdapter(this);
        mRecyclerView.setAdapter(mNearbyChallengesAdapter);
    }

    private void initializeViews() {
//        mChallengeImage = (ImageView) findViewById(R.id.existing_challenge);
//        mHint = (TextView) findViewById(R.id.challenge_hint);
//        mLocation = (TextView) findViewById(R.id.challenge_location);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_challenge_recyclerview);
    }

    @Override
    public void onCreatedChallengeClicked(ChallengePhoto challenge) {
        DialogFragment dialogFragment = ChallengeDialogFragment.getInstance(challenge);
        dialogFragment.show(getSupportFragmentManager(), "challenge_fragment");
    }

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

    @Override
    public void run(LocationResult locationResult) {
        userLocation = new DAMLocation(locationResult.getLocation().getLatitude(), locationResult.getLocation().getLongitude());
        System.out.println(userLocation.getLat() + " " + userLocation.getLng() + " <--- USER LOCATION");
        initialize();
    }


}
