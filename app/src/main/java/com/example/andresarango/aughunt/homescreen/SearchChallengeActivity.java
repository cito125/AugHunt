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
import com.example.andresarango.aughunt.challenge.challenge_dialog_fragment.ChallengeDialogFragment;
import com.example.andresarango.aughunt.challenge.challenges_adapter.ChallengesAdapter;
import com.example.andresarango.aughunt.challenge.challenge_dialog_fragment.DialogFragmentListener;
import com.example.andresarango.aughunt.location.Location;

import java.util.ArrayList;
import java.util.List;

public class SearchChallengeActivity extends AppCompatActivity implements DialogFragmentListener {

    private ImageView mChallengeImage;
    private static final String IMAGE_DATA = "image_data";
    private static final String HINT_DATA = "hint_data";
    private TextView mHint;
    private List<Challenge<String>> mChallengeList;
    private RecyclerView mRecyclerView;
    private ChallengesAdapter<String> mNearbyChallengesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_challenge);
        mHint = (TextView) findViewById(R.id.viewholder_challenge_hint);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_challenge_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mNearbyChallengesAdapter = new ChallengesAdapter<>(this);
        mRecyclerView.setAdapter(mNearbyChallengesAdapter);
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
        Location firstLocation = new Location(40.822770, -73.941915);
        Location secondLocation = new Location(40.822859, -73.942108);
        Location thirdLocation = new Location(40.822737, -73.942022);
        Location fourthLocation = new Location(40.822916, -73.941915);
        Location fifthLocation = new Location(40.822770, -73.941818);
        Location sixthLocation = new Location(40.822940, -73.941957);
        Location seventhLocation = new Location(40.822705, -73.941893);
        Location wrongLocation = new Location(40.821349, -73.938213);

        Challenge<String> firstChallenge = new Challenge<>();
        firstChallenge.setChallenge("first Challenge");
        firstChallenge.setLocation(firstLocation);
        firstChallenge.setHint(firstChallenge.getChallenge());

        Challenge<String> secondChallenge = new Challenge<>();
        secondChallenge.setChallenge("second Challenge");
        secondChallenge.setLocation(secondLocation);
        secondChallenge.setHint(secondChallenge.getChallenge());


        Challenge<String> thirdChallenge = new Challenge<>();
        thirdChallenge.setChallenge("third Challenge");
        thirdChallenge.setLocation(thirdLocation);
        thirdChallenge.setHint(thirdChallenge.getChallenge());

        Challenge<String> fourthChallenge = new Challenge<>();
        fourthChallenge.setChallenge("fourth Challenge");
        fourthChallenge.setLocation(fourthLocation);
        fourthChallenge.setHint(fourthChallenge.getChallenge());

        Challenge<String> fifthChallenge = new Challenge<>();
        fifthChallenge.setChallenge("fifth Challenge");
        fifthChallenge.setLocation(fifthLocation);
        fifthChallenge.setHint(fifthChallenge.getChallenge());

        Challenge<String> sixthChallenge = new Challenge<>();
        sixthChallenge.setChallenge("sixth Challenge");
        sixthChallenge.setLocation(sixthLocation);
        sixthChallenge.setHint(sixthChallenge.getChallenge());

        Challenge<String> seventhChallenge = new Challenge<>();
        seventhChallenge.setChallenge("seventh Challenge");
        seventhChallenge.setLocation(seventhLocation);
        seventhChallenge.setHint(seventhChallenge.getChallenge());

        Challenge<String> badChallenge = new Challenge<>();
        badChallenge.setChallenge("wrong");
        badChallenge.setLocation(wrongLocation);
        badChallenge.setHint(badChallenge.getChallenge());

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
    public <T> void startDialogueFragment(Challenge<T> challenge) {
        DialogFragment dialogFragment = ChallengeDialogFragment.getInstance(challenge);
        dialogFragment.show(getSupportFragmentManager(), "challenge_fragment");
    }
}
