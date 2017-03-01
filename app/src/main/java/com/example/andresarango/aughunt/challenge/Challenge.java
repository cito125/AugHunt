package com.example.andresarango.aughunt.challenge;

import com.example.andresarango.aughunt.location.Location;

/**
 * Created by andresarango on 3/1/17.
 */

public class Challenge<T> {
    private T mChallenge;
    private Location mLocation;

    public void setmChallenge(T mChallenge) {
        this.mChallenge = mChallenge;
    }

    public void setmLocation(Location mLocation) {
        this.mLocation = mLocation;
    }

    public T getmChallenge() {
        return mChallenge;
    }

    public Location getmLocation() {
        return mLocation;
    }
}
