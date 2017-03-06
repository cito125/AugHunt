package com.example.andresarango.aughunt.challenge;


import com.example.andresarango.aughunt.location.Location;

public class Challenge<T> {
    private T mChallenge;
    private Location mLocation;
    private String mHint;

    public T getChallenge() {
        return mChallenge;
    }

    public Location getLocation() {
        return mLocation;
    }

    public String getHint() {
        return mHint;
    }

    public void setChallenge(T mChallenge) {
        this.mChallenge = mChallenge;
    }

    public void setLocation(Location mLocation) {
        this.mLocation = mLocation;
    }

    public void setHint(String mHint) {
        this.mHint = mHint;
    }
}