package com.example.andresarango.aughunt.challenge;

import android.graphics.Bitmap;

import com.example.andresarango.aughunt.location.DAMLocation;


/**
 * Created by Millochka on 2/28/17.
 */

public class Challenge<T> {
    private T mChallenge;
    private DAMLocation mLocation;
    private String mHint;

    public Challenge(T mChallenge, DAMLocation mLocation) {
        this.mChallenge = mChallenge;
        this.mLocation = mLocation;
    }

    public T getmChallenge() {
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

    public void setChallenge(T mChallenge) {
        this.mChallenge = mChallenge;
    }

    public void setLocation(DAMLocation mLocation) {
        this.mLocation = mLocation;
    }

    public void setHint(String mHint) {
        this.mHint = mHint;
    }
}