package com.example.andresarango.aughunt.challenge;

import java.io.Serializable;

/**
 * Created by dannylui on 3/8/17.
 */

public class ChallengePhotoCompleted implements Serializable {
    private String completedChallengeId;
    private String playerId;
    private String photoUrl;

    public ChallengePhotoCompleted() {

    }

    public ChallengePhotoCompleted(String completedChallengeId, String playerId, String photoUrl) {
        this.completedChallengeId = completedChallengeId;
        this.playerId = playerId;
        this.photoUrl = photoUrl;
    }

    public String getCompletedChallengeId() {
        return completedChallengeId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
