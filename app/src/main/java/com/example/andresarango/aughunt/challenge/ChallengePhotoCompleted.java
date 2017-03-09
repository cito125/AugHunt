package com.example.andresarango.aughunt.challenge;

import java.io.Serializable;

/**
 * Created by dannylui on 3/8/17.
 */

public class ChallengePhotoCompleted implements Serializable {
    private String challengeId;
    private String playerId;
    private String photoUrl;

    public ChallengePhotoCompleted() {

    }

    public ChallengePhotoCompleted(String challengeId, String playerId, String photoUrl) {
        this.challengeId = challengeId;
        this.playerId = playerId;
        this.photoUrl = photoUrl;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
