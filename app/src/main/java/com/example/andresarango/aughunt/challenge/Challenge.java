package com.example.andresarango.aughunt.challenge;

import android.graphics.Bitmap;

import com.example.andresarango.aughunt.location.Location;

/**
 * Created by Millochka on 2/28/17.
 */

public class Challenge {
    private Bitmap mChallenge;
    private Location mLocation;
    private String mHint;

    public Challenge(Bitmap mChallenge, Location mLocation) {
        this.mChallenge = mChallenge;
        this.mLocation = mLocation;
    }

    public Bitmap getmChallenge() {
        return mChallenge;
    }

    public Location getmLocation() {
        return mLocation;
    }

    public void setmHint(String mHint) {
        this.mHint = mHint;
    }

    public String getmHint() {
        return mHint;
    }
}