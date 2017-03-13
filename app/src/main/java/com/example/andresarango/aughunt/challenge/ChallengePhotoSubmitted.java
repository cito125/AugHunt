package com.example.andresarango.aughunt.challenge;

/**
 * Created by dannylui on 3/12/17.
 */

public class ChallengePhotoSubmitted {
    private String challengeId;
    private String ownerId;
    private String photoUrl;
    private String hint;
    private boolean isAccepted;
    private boolean isDeclined;
    private boolean isInReview;

    public ChallengePhotoSubmitted() {

    }

    public ChallengePhotoSubmitted(String challengeId, String ownerId, String hint, String photoUrl) {
        this.challengeId = challengeId;
        this.ownerId = ownerId;
        this.hint = hint;
        this.photoUrl = photoUrl;
        this.isAccepted = false;
        this.isDeclined = false;
        this.isInReview = true;
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

    public boolean isDeclined() {
        return isDeclined;
    }

    public boolean isInReview() {
        return isInReview;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public void setDeclined(boolean declined) {
        isDeclined = declined;
    }

    public void setInReview(boolean inReview) {
        isInReview = inReview;
    }
}
