package com.example.andresarango.aughunt.models;

/**
 * Created by Danny on 3/14/2017.
 */

public class ChallengePhotoSubmitted {
    private String challengeId;
    private String ownerId;
    private String hint;
    private String photoUrl;
    private boolean isAccepted;
    private boolean isReviewed;

    public ChallengePhotoSubmitted() {

    }

    public ChallengePhotoSubmitted(String challengeId, String ownerId, String hint, String photoUrl) {
        this.challengeId = challengeId;
        this.ownerId = ownerId;
        this.hint = hint;
        this.photoUrl = photoUrl;
        this.isAccepted = false;
        this.isReviewed = false;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getHint() {
        return hint;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }
}
