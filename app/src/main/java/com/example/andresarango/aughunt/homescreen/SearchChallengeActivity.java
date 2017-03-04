package com.example.andresarango.aughunt.homescreen;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.FirebaseEmulator;
import com.example.andresarango.aughunt.location.DAMLocation;
import com.example.andresarango.aughunt.location.LocationChecker;

import java.util.ArrayList;
import java.util.List;

public class SearchChallengeActivity extends AppCompatActivity {

    private ImageView mChallengeImage;
    private static final String IMAGE_DATA = "image_data";
    private static final String HINT_DATA = "hint_data";
    private TextView mHint;
    private List<Challenge<String>> mChallengeList;
    private FirebaseEmulator mFirebaseEmulator;
    private TextView mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_list);
        mChallengeImage = (ImageView) findViewById(R.id.existing_challenge);
        mHint = (TextView) findViewById(R.id.challenge_hint);
        mFirebaseEmulator =new FirebaseEmulator(this);
        mLocation=(TextView) findViewById(R.id.challenge_location);
        makeListofChallenges();
        checkListOfChallenges();

    }

    private void checkListOfChallenges() {
        LocationChecker locationChecker = new LocationChecker();
        DAMLocation userLocation = new DAMLocation(40.822827, -73.941979);
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
        DAMLocation firstLocation = new DAMLocation(40.822770, -73.941915);
        DAMLocation secondLocation = new DAMLocation(40.822859, -73.942108);
        DAMLocation thirdLocation = new DAMLocation(40.822737, -73.942022);
        DAMLocation fourthLocation = new DAMLocation(40.822916, -73.941915);
        DAMLocation fifthLocation = new DAMLocation(40.822770, -73.941818);
        DAMLocation sixthLocation = new DAMLocation(40.822940, -73.941957);
        DAMLocation seventhLocation = new DAMLocation(40.822705, -73.941893);
        DAMLocation wrongLocation = new DAMLocation(40.821349, -73.938213);

        Challenge<String> firstChallenge = new Challenge<>("first Challenge",firstLocation);
        Challenge<String> secondChallenge = new Challenge<>("second Challenge", secondLocation);

        Challenge<String> thirdChallenge = new Challenge<>("third Challenge", thirdLocation);

        Challenge<String> fourthChallenge = new Challenge<>("fourth Challenge", fourthLocation);


        Challenge<String> fifthChallenge = new Challenge<>("fifth Challenge", fifthLocation);


        Challenge<String> sixthChallenge = new Challenge<>("sixth Challenge",sixthLocation);

        Challenge<String> seventhChallenge = new Challenge<>("seventh Challenge", seventhLocation);

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

        Challenge<Bitmap> challenge = mFirebaseEmulator.getmChallenge();
        Bitmap image=challenge.getChallenge();

        String hint = challenge.getmHint();

        Double lat=challenge.getLocation().getLat();
        Double lng=challenge.getLocation().getLng();
        //mLocation.setText(lat+" " +lng);
        Drawable d = new BitmapDrawable(getResources(), image);
        //mChallengeImage.setImageDrawable(d);
        //mHint.setText(hint);

    }


}
