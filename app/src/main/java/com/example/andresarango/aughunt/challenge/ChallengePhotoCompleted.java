package com.example.andresarango.aughunt.challenge;

/**
 * Created by dannylui on 3/8/17.
 */

public class ChallengePhotoCompleted {
    private String challengeId;
    private String playerId;
    private String photoUrl;

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
