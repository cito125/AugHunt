package com.example.andresarango.aughunt;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.ChallengePhoto;
import com.example.andresarango.aughunt.challenge.challenge_dialog_fragment.ChallengeDialogFragment;
import com.example.andresarango.aughunt.challenge.challenges_adapters.ChallengeViewholderListener;
import com.example.andresarango.aughunt.challenge.challenges_adapters.nearby.ChallengesAdapter;
import com.example.andresarango.aughunt.location.DAMLocation;
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

public class SearchChallengeActivity extends AppCompatActivity implements ChallengeViewholderListener {

    private ImageView mChallengeImage;
    private TextView mHint;
    private List<Challenge<String>> mChallengeList;
//    private FirebaseEmulator mFirebaseEmulator;
    private TextView mLocation;
    private RecyclerView mRecyclerView;
    private ChallengesAdapter mNearbyChallengesAdapter;
    private static final String IMAGE_DATA = "image_data";
    private static final String HINT_DATA = "hint_data";

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private DAMLocation userLocation = new DAMLocation(40.7262399, -73.8641891);
    private Double radius = 30.0;
    private Map<String, ChallengePhoto> challengeMap = new HashMap<>();
    private List<ChallengePhoto> challengeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_challenge);
        initialize();

    }

    private void initialize() {
        initializeViews();
        setUpRecyclerView();
//        mFirebaseEmulator = new FirebaseEmulator(this);
//        makeListofChallenges();
        //setChallengesToAdaper();

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
        if (challenge.getLocation().isWithinRadius(userLocation, radius)) {
            // Put in challenge map
            challengeMap.put(challengeKey, challenge);
            challengeList.add(challengeMap.get(challengeKey));

            mNearbyChallengesAdapter.setChallengeList(challengeList);
        } else {
            System.out.println("NOT WITHIN RADIUS");
        }
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mNearbyChallengesAdapter = new ChallengesAdapter(this);
        mRecyclerView.setAdapter(mNearbyChallengesAdapter);
    }

    private void initializeViews() {
        mChallengeImage = (ImageView) findViewById(R.id.existing_challenge);
        mHint = (TextView) findViewById(R.id.challenge_hint);
        mLocation = (TextView) findViewById(R.id.challenge_location);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_challenge_recyclerview);
    }

    @Override
    public void onChallengeClicked(ChallengePhoto challenge) {
        DialogFragment dialogFragment = ChallengeDialogFragment.getInstance(challenge);
        dialogFragment.show(getSupportFragmentManager(), "challenge_fragment");
    }
}
