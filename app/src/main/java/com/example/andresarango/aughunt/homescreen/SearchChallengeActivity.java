package com.example.andresarango.aughunt.homescreen;

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

import com.example.andresarango.aughunt.challenge.ChallengeFilter;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.FirebaseEmulator;
import com.example.andresarango.aughunt.location.DAMLocation;
import com.example.andresarango.aughunt.challenge.challenge_dialog_fragment.ChallengeDialogFragment;
import com.example.andresarango.aughunt.challenge.challenges_adapter.ChallengesAdapter;
import com.example.andresarango.aughunt.challenge.challenge_dialog_fragment.DialogFragmentListener;
import com.example.andresarango.aughunt.location.Location;

import java.util.ArrayList;
import java.util.List;

public class SearchChallengeActivity extends AppCompatActivity implements DialogFragmentListener {

    private ImageView mChallengeImage;
    private TextView mHint;
    private List<Challenge<String>> mChallengeList;
    private FirebaseEmulator mFirebaseEmulator;
    private TextView mLocation;
    private RecyclerView mRecyclerView;
    private ChallengesAdapter<String> mNearbyChallengesAdapter;
    private static final String IMAGE_DATA ="image_data" ;
    private static final String HINT_DATA = "hint_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_list);
        mChallengeImage = (ImageView) findViewById(R.id.existing_challenge);
        mHint = (TextView) findViewById(R.id.challenge_hint);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_challenge_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mNearbyChallengesAdapter = new ChallengesAdapter<>(this);
        mRecyclerView.setAdapter(mNearbyChallengesAdapter);
        mFirebaseEmulator =new FirebaseEmulator(this);
        mLocation=(TextView) findViewById(R.id.challenge_location);
        makeListofChallenges();
        setChallengesToAdaper();

    }

    private void setChallengesToAdaper() {
        Location userLocation = new Location(40.822827, -73.941979);
        Double radius = 30.0;
        ChallengeFilter<String> challengeFilter = new ChallengeFilter<>();
        List<Challenge<String>> nearbyChallenges = challengeFilter.filterChallengesByProximity(
                mChallengeList,
                userLocation,
                radius
        );
        mNearbyChallengesAdapter.setChallengeList(nearbyChallenges);


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

        Challenge<String> firstChallenge = new Challenge<>("first Challenge",firstLocation);
        firstChallenge.setmHint(firstChallenge.getmChallenge());
        Challenge<String> secondChallenge = new Challenge<>("second Challenge", secondLocation);
        secondChallenge.setmHint(secondChallenge.getmChallenge());
        Challenge<String> thirdChallenge = new Challenge<>("third Challenge", thirdLocation);
        thirdChallenge.setmHint(thirdChallenge.getmChallenge());
        Challenge<String> fourthChallenge = new Challenge<>("fourth Challenge", fourthLocation);
        fourthChallenge.setmHint(fourthChallenge.getmChallenge());

        Challenge<String> fifthChallenge = new Challenge<>("fifth Challenge", fifthLocation);
        fifthChallenge.setmHint(fifthChallenge.getmChallenge());

        Challenge<String> sixthChallenge = new Challenge<>("sixth Challenge",sixthLocation);
        sixthChallenge.setmHint(sixthChallenge.getmChallenge());
        Challenge<String> seventhChallenge = new Challenge<>("seventh Challenge", seventhLocation);
        seventhChallenge.setmHint(seventhChallenge.getmChallenge());

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

    @Override
    protected void onStart() {
        super.onStart();
//        setChallengeImage();

        Challenge<Bitmap> challenge = mFirebaseEmulator.getmChallenge();
        Bitmap image=challenge.getmChallenge();}

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
    public <T> void startDialogueFragment(Challenge<T> challenge) {
        DialogFragment dialogFragment = ChallengeDialogFragment.getInstance(challenge);
        dialogFragment.show(getSupportFragmentManager(), "challenge_fragment");
    }
}
