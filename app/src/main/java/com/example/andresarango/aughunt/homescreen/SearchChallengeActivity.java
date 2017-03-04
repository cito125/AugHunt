package com.example.andresarango.aughunt.homescreen;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.location.Location;
import com.example.andresarango.aughunt.location.LocationChecker;

import java.util.ArrayList;
import java.util.List;

public class SearchChallengeActivity extends AppCompatActivity {

    private ImageView mChallengeImage;
    private static final String IMAGE_DATA = "image_data";
    private static final String HINT_DATA = "hint_data";
    private TextView mHint;
    private List<Challenge<String>> mChallengeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_list);
        mChallengeImage = (ImageView) findViewById(R.id.existing_challenge);
        mHint = (TextView) findViewById(R.id.challenge_hint);
        makeListofChallenges();
        checkListOfChallenges();

    }

    private void checkListOfChallenges() {
        LocationChecker locationChecker = new LocationChecker();
        Location userLocation = new Location(40.822827, -73.941979);
        Double radius = 30.0;
        for (int i = 0; i < mChallengeList.size(); i++) {
            Challenge<String> challenge = mChallengeList.get(i);
            boolean challengeIsNearUser = locationChecker.areLocationsWithinRadius(
                    userLocation, challenge.getLocation(),
                    radius);
            if(challengeIsNearUser){
                System.out.println(challenge.getChallenge());
            }else{
                System.out.println("oh no");
            }
        }
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

        Challenge<String> secondChallenge = new Challenge<>();
        secondChallenge.setChallenge("second Challenge");
        secondChallenge.setLocation(secondLocation);

        Challenge<String> thirdChallenge = new Challenge<>();
        thirdChallenge.setChallenge("third Challenge");
        thirdChallenge.setLocation(thirdLocation);

        Challenge<String> fourthChallenge = new Challenge<>();
        fourthChallenge.setChallenge("fourth Challenge");
        fourthChallenge.setLocation(fourthLocation);

        Challenge<String> fifthChallenge = new Challenge<>();
        fifthChallenge.setChallenge("fifth Challenge");
        fifthChallenge.setLocation(fifthLocation);

        Challenge<String> sixthChallenge = new Challenge<>();
        sixthChallenge.setChallenge("sixth Challenge");
        sixthChallenge.setLocation(sixthLocation);

        Challenge<String> seventhChallenge = new Challenge<>();
        seventhChallenge.setChallenge("seventh Challenge");
        seventhChallenge.setLocation(seventhLocation);

        Challenge<String> badChallenge = new Challenge<>();
        badChallenge.setChallenge("wrong");
        badChallenge.setLocation(wrongLocation);

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
        setChallengeImage();

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

}
