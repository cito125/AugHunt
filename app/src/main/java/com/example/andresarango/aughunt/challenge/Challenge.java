package com.example.andresarango.aughunt.challenge;

import android.graphics.Bitmap;

import com.example.andresarango.aughunt.location.DAMLocation;


/**
 * Created by Millochka on 2/28/17.
 */

public class Challenge {
    private Bitmap mChallenge;
    private DAMLocation mLocation;
    private String mHint;

    public Challenge(Bitmap mChallenge, DAMLocation mLocation) {
        this.mChallenge = mChallenge;
        this.mLocation = mLocation;
    }

    public Bitmap getmChallenge() {
        return mChallenge;
    }

    public DAMLocation getmLocation() {
        return mLocation;
    }

    public void setmHint(String mHint) {
        this.mHint = mHint;
    }

    public String getmHint() {
        return mHint;
    }



}