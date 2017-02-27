package com.example.andresarango.aughunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {


    private double mStationaryLat;
    private double mStationaryLng;
    private double mMovingLat;
    private double mMovingLng;
    private double mRadius; //in meters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStationaryLat = 40.742974;
        mStationaryLng = -73.935016;
        mMovingLat = 40.742970;
        mMovingLng = -73.934978;
        mRadius = 10;
        if (isWithinRadius(mMovingLat, mMovingLng, mStationaryLat, mStationaryLng, mRadius)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }


    }

    private boolean isWithinRadius(Double movingLat,
                                   Double movingLng,
                                   Double stationaryLat,
                                   Double stationaryLng,
                                   Double radius) {

        double distance = getDistance(movingLat, movingLng, stationaryLat, stationaryLng, 0.0, 0.0);

        return distance <= radius;
    }

    private double getDistance(Double movingLat,
                               Double movingLng,
                               Double stationaryLat,
                               Double stationaryLng,
                               Double movingHeight,
                               Double stationaryHeight) {
        final Double R = 6371.0; // Radius of the earth in meters

        Double latDistance = Math.toRadians(movingLat - stationaryLat);
        Double lonDistance = Math.toRadians(movingLng - stationaryLng);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(stationaryLat)) * Math.cos(Math.toRadians(movingLat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distance = R * c * 1000; // convert to meters

        Double height = stationaryHeight - movingHeight;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        System.out.println(Double.toString(Math.sqrt(distance)));

        return Math.sqrt(distance); // returns distance in meters
    }
}
