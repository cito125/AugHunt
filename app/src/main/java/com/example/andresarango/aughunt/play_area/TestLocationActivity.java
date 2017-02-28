package com.example.andresarango.aughunt.play_area;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.andresarango.aughunt.R;


public class TestLocationActivity extends AppCompatActivity {


    private double mRadius; //in meters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Location firstLocation = new Location(40.742974,-73.935016);
        Location secondLocation = new Location(40.742970,-73.934978);
        LocationChecker locationChecker = new LocationChecker();
        mRadius = 10;
        if (locationChecker.areLocationsWithinRadius(firstLocation,secondLocation,mRadius)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }


    }
}
