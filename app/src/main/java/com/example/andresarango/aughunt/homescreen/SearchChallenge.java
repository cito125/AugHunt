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

public class SearchChallenge extends AppCompatActivity {

    private ImageView mChallImage;
    private static final String IMAGE_DATA ="image_data" ;
    private static final String HINT_DATA ="hint_data" ;
    private TextView mHint;
    private FirebaseEmulator mFirebaseEmulator;
    private TextView mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_list);
        mChallImage = (ImageView) findViewById(R.id.existing_challenge);
        mHint=(TextView) findViewById(R.id.challenge_hint);
        mFirebaseEmulator =new FirebaseEmulator(this);
       mLocation=(TextView) findViewById(R.id.challenge_location);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Challenge<Bitmap> challenge = mFirebaseEmulator.getmChallenge();
      Bitmap image=challenge.getChallenge();

        String hint = challenge.getmHint();

        Double lat=challenge.getLocation().getLat();
        Double lng=challenge.getLocation().getLng();
        mLocation.setText(lat+" " +lng);
        Drawable d = new BitmapDrawable(getResources(), image);
        mChallImage.setImageDrawable(d);
        mHint.setText(hint);


    }

}
