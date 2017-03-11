package com.example.andresarango.aughunt.challenge;

import com.example.andresarango.aughunt.location.DAMLocation;

import java.io.Serializable;

/**
 * Created by dannylui on 3/8/17.
 */

public class ChallengePhoto implements Serializable {
    private String mChallengeId;
    private String mOwnerId;
    private DAMLocation mLocation;
    private String mPhotoUrl;
    private String mHint;
    private int mPursuing;

    private int mCompleted;
    private long mTimestamp;


    public ChallengePhoto() {

    }

    public ChallengePhoto(String challengeId, String ownerId, DAMLocation location, String photoUrl, String hint, long timestamp) {
        this.mChallengeId = challengeId;
        this.mOwnerId = ownerId;
        this.mLocation = location;
        this.mPhotoUrl = photoUrl;
        this.mHint = hint;
        this.mTimestamp = timestamp;
        this.mPursuing = 0;
        this.mCompleted = 0;
    }

    public String getChallengeId() {
        return mChallengeId;
    }

    public String getOwnerId() {
        return mOwnerId;
    }

    public DAMLocation getLocation() {
        return mLocation;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public String getHint() {
        return mHint;
    }

    public int getPursuing() {
        return mPursuing;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public int getCompleted() {
        return mCompleted;
    }

    public void setPursuing(int pursuing) {
        this.mPursuing = pursuing;
    }

    public void setCompleted(int completed) {
        this.mCompleted = completed;
    }
}
