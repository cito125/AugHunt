package com.example.andresarango.aughunt._models;

/**
 * Created by Danny on 3/14/2017.
 */

public class ChallengePhotoSubmitted {
    private String challengeId;
    private String ownerId;
    private String hint;
    private String submittedPhotoUrl;
    private String originalPhotoUrl;
    private Long timestamp;
    private boolean isAccepted;
    private boolean isReviewed;

    public ChallengePhotoSubmitted() {

    }

    public ChallengePhotoSubmitted(String challengeId, String ownerId, String hint, String submittedPhotoUrl, String originalPhotoUrl, Long timestamp) {
        this.challengeId = challengeId;
        this.ownerId = ownerId;
        this.hint = hint;
        this.submittedPhotoUrl = submittedPhotoUrl;
        this.originalPhotoUrl = originalPhotoUrl;
        this.isAccepted = false;
        this.isReviewed = false;
        this.timestamp = timestamp;
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

    public String getSubmittedPhotoUrl() {
        return submittedPhotoUrl;
    }

    public String getOriginalPhotoUrl() {
        return originalPhotoUrl;
    }

    public Long getTimestamp() {
        return timestamp;
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
