package com.example.andresarango.aughunt;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.ChallengeFilter;
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
            //System.out.println(challenge.getUserId() + " " + challenge.getLocation().getLat() + " " + challenge.getLocation().getLng());
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

    private void setChallengesToAdaper() {
        DAMLocation userLocation = new DAMLocation(40.822827, -73.941979);
        Double radius = 30.0;
        ChallengeFilter<String> challengeFilter = new ChallengeFilter<>();
//        List<Challenge<String>> nearbyChallenges = challengeFilter.filterChallengesByProximity(
//                mChallengeList,
//                userLocation,
//                radius
//        );
//        mNearbyChallengesAdapter.setChallengeList(nearbyChallenges);


    }


    private void makeListofChallenges() {
        mChallengeList = new ArrayList<>();
        DAMLocation firstLocation = new DAMLocation(40.822770, -73.941915);
        DAMLocation secondLocation = new DAMLocation(40.822859, -73.942108);
        DAMLocation thirdLocation = new DAMLocation(40.822737, -73.942022);
        DAMLocation fourthLocation = new DAMLocation(40.822916, -73.941915);
        DAMLocation fifthLocation = new DAMLocation(40.822770, -73.941818);
        DAMLocation sixthLocation = new DAMLocation(40.822940, -73.941957);
        DAMLocation seventhLocation = new DAMLocation(40.822705, -73.941893);
        DAMLocation wrongLocation = new DAMLocation(40.821349, -73.938213);

        Challenge<String> firstChallenge = new Challenge<>("first Challenge", firstLocation);
        firstChallenge.setmHint(firstChallenge.getChallenge());
        Challenge<String> secondChallenge = new Challenge<>("second Challenge", secondLocation);
        secondChallenge.setmHint(secondChallenge.getChallenge());
        Challenge<String> thirdChallenge = new Challenge<>("third Challenge", thirdLocation);
        thirdChallenge.setmHint(thirdChallenge.getChallenge());
        Challenge<String> fourthChallenge = new Challenge<>("fourth Challenge", fourthLocation);
        fourthChallenge.setmHint(fourthChallenge.getChallenge());

        Challenge<String> fifthChallenge = new Challenge<>("fifth Challenge", fifthLocation);
        fifthChallenge.setmHint(fifthChallenge.getChallenge());

        Challenge<String> sixthChallenge = new Challenge<>("sixth Challenge", sixthLocation);
        sixthChallenge.setmHint(sixthChallenge.getChallenge());
        Challenge<String> seventhChallenge = new Challenge<>("seventh Challenge", seventhLocation);
        seventhChallenge.setmHint(seventhChallenge.getChallenge());

        Challenge<String> badChallenge = new Challenge<>("wrong", wrongLocation);

        mChallengeList.add(firstChallenge);
        mChallengeList.add(secondChallenge);
        mChallengeList.add(thirdChallenge);
        mChallengeList.add(fourthChallenge);
        mChallengeList.add(fifthChallenge);
        mChallengeList.add(sixthChallenge);
        mChallengeList.add(seventhChallenge);
        mChallengeList.add(badChallenge);


    }

    private void setChallengeImage() {
        Bitmap image = getBitmapFromSharedPreferences();
        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
        String hint = shre.getString(HINT_DATA, "");

        Drawable d = new BitmapDrawable(getResources(), image);
        mChallengeImage.setImageDrawable(d);
        mHint.setText(hint);
    }

    public Bitmap getBitmapFromSharedPreferences() {

        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
        String previouslyEncodedImage = shre.getString(IMAGE_DATA, "");

        if (!previouslyEncodedImage.equalsIgnoreCase("")) {
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            return bitmap;
        }
        return null;
    }

    @Override
    public void onChallengeClicked(ChallengePhoto challenge) {
        DialogFragment dialogFragment = ChallengeDialogFragment.getInstance(challenge);
        dialogFragment.show(getSupportFragmentManager(), "challenge_fragment");
    }
}
