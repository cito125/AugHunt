package com.example.andresarango.aughunt.models;

import com.example.andresarango.aughunt.challenge.CompletedChallenge;
import com.example.andresarango.aughunt.location.DAMLocation;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Millochka on 2/28/17.
 */

public final class Challenge<T> {
    private final T mChallenge;
    private final DAMLocation mLocation;
    private String mHint;
    private final String mOwnerId;
    private int mUsersAccepted;
    private Boolean mStatus;
    private  List<CompletedChallenge<T>> mCompletedChallenges;

    public Challenge(T mChallenge, DAMLocation damLocation) {
        this(mChallenge,damLocation,null);
    }

    public Challenge(T mChallenge, DAMLocation mLocation,String ownerId) {
        this.mChallenge = mChallenge;
        this.mLocation = mLocation;
        this.mOwnerId=ownerId;
        this.mCompletedChallenges = new ArrayList<>();



    }


    public T getChallenge() {
        return mChallenge;
    }

    public DAMLocation getmLocation() {
        return mLocation;
    }

    public String getmHint() {
        return mHint;
    }

    public String getmOwnerId() {
        return mOwnerId;
    }



    public List<CompletedChallenge<T>> getmCompletedChallenges() {
        return mCompletedChallenges;
    }

    public void setmHint(String mHint) {
        this.mHint = mHint;
    }


    public void setmCompletedChallenges(List<CompletedChallenge<T>> mCompletedChallenges) {
        this.mCompletedChallenges = mCompletedChallenges;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean mStatus) {
        this.mStatus = mStatus;
    }

    public Integer getUsersAccepted() {
        return mUsersAccepted;
    }

    public void setUsersAccepted(Integer usersAccepted) {
        this.mUsersAccepted = usersAccepted;
    }
}