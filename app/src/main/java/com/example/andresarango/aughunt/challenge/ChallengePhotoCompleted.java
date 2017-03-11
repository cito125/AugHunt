package com.example.andresarango.aughunt.challenge;

import java.io.Serializable;

/**
 * Created by dannylui on 3/8/17.
 */

public class ChallengePhotoCompleted implements Serializable {
    private String mCompletedChallengeId;
    private String mPlayerId;
    private String mPhotoUrl;


    public ChallengePhotoCompleted(String completedChallengeId, String playerId, String photoUrl) {
        this.mCompletedChallengeId = completedChallengeId;
        this.mPlayerId = playerId;
        this.mPhotoUrl = photoUrl;
    }

    public String getCompletedChallengeId() {
        return mCompletedChallengeId;
    }

    public String getPlayerId() {
        return mPlayerId;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }
}
