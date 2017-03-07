package com.example.andresarango.aughunt.challenge;

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
    private Integer mNumUserAccepted;
    private Boolean mStatus;
    private  List<CompletedChallenges<T>> mCompletedChallenges;

    public Challenge(T mChallenge, DAMLocation damLocation) {
        this(mChallenge,damLocation,null);
    }

    public Challenge(T mChallenge, DAMLocation mLocation,String ownerId) {
        this.mChallenge = mChallenge;
        this.mLocation = mLocation;
        this.mOwnerId=ownerId;
        this.mCompletedChallenges = new ArrayList<>();


    }


    public T getmChallenge() {
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

    public Integer getmNumUserAccepted() {
        return mNumUserAccepted;
    }

    public List<CompletedChallenges<T>> getmCompletedChallenges() {
        return mCompletedChallenges;
    }

    public void setmHint(String mHint) {
        this.mHint = mHint;
    }

    public void setmNumUserAccepted(Integer mNumUserAccepted) {
        this.mNumUserAccepted = mNumUserAccepted;
    }

    public void setmCompletedChallenges(List<CompletedChallenges<T>> mCompletedChallenges) {
        this.mCompletedChallenges = mCompletedChallenges;
    }

    public Boolean getmStatus() {
        return mStatus;
    }

    public void setmStatus(Boolean mStatus) {
        this.mStatus = mStatus;
    }
}