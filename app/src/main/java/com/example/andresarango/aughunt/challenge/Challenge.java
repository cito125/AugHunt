package com.example.andresarango.aughunt.challenge;

import com.example.andresarango.aughunt.location.DAMLocation;


public class Challenge<T> {
    private T mChallenge;
    private DAMLocation mLocation;
    private String mHint;

    public Challenge(T mChallenge, DAMLocation mLocation) {
        this.mChallenge = mChallenge;
        this.mLocation = mLocation;
    }

    public T getChallenge() {
        return mChallenge;
    }

    public DAMLocation getLocation() {
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