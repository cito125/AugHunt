package com.example.andresarango.aughunt.challenge;

/**
 * Created by dannylui on 3/12/17.
 */

public class ChallengePhotoSubmitted {
    private String challengeId;
    private String ownerId;
    private String photoUrl;
    private boolean isAccepted;
    private boolean isInReview;

    public ChallengePhotoSubmitted(String challengeId, String ownerId, String photoUrl) {
        this.challengeId = challengeId;
        this.ownerId = ownerId;
        this.photoUrl = photoUrl;
        this.isAccepted = false;
        this.isInReview = true;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public boolean isInReview() {
        return isInReview;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public void setInReview(boolean inReview) {
        isInReview = inReview;
    }
}
