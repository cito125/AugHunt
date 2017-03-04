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

import java.util.ArrayList;
import java.util.List;

public class SearchChallengeActivity extends AppCompatActivity {

    private ImageView mChallengeImage;
    private static final String IMAGE_DATA = "image_data";
    private static final String HINT_DATA = "hint_data";
    private TextView mHint;
    private List<Challenge<Bitmap>> mChallengeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_list);
        mChallengeImage = (ImageView) findViewById(R.id.existing_challenge);
        mHint = (TextView) findViewById(R.id.challenge_hint);
        makeListofChallenges();

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
        Challenge<String> firstChallenge = new Challenge<>();
        firstChallenge.setChallenge("first Challenge");
        firstChallenge.setLocation(firstLocation);
        Challenge<String> secondChallenge = new Challenge<>();
        firstChallenge.setChallenge("second Challenge");
        Challenge<String> thirdChallenge = new Challenge<>();
        firstChallenge.setChallenge("third Challenge");
        Challenge<String> fourthChallenge = new Challenge<>();
        firstChallenge.setChallenge("fourth Challenge");
        Challenge<String> fifthChallenge = new Challenge<>();
        firstChallenge.setChallenge("fifth Challenge");
        Challenge<String> sixthChallenge = new Challenge<>();
        firstChallenge.setChallenge("sixth Challenge");
        Challenge<String> seventhChallenge = new Challenge<>();
        firstChallenge.setChallenge("seventh Challenge");


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
