package com.example.andresarango.aughunt._models;

import java.io.Serializable;

/**
 * Created by dannylui on 3/8/17.
 */

public class ChallengePhoto implements Serializable {
    private String challengeId;
    private String ownerId;
    private DAMLocation location;
    private String photoUrl;
    private String hint;
    private int pursuing;
    private int completed;
    private Long timestamp;
    private int pendingReviews;
    private String arObjectStr;

    public ChallengePhoto() {

    }

    public ChallengePhoto(String challengeId, String ownerId, DAMLocation location, String photoUrl, String hint, Long timestamp, String arObjectStr) {
        this.challengeId = challengeId;
        this.ownerId = ownerId;
        this.location = location;
        this.photoUrl = photoUrl;
        this.hint = hint;
        this.timestamp = timestamp;
        this.pursuing = 0;
        this.completed = 0;
        this.pendingReviews = 0;
        this.arObjectStr = arObjectStr;

    }

    public String getChallengeId() {
        return challengeId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public DAMLocation getLocation() {
        return location;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getHint() {
        return hint;
    }

    public int getPursuing() {
        return pursuing;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public int getCompleted() {
        return completed;
    }

    public int getPendingReviews() {
        return pendingReviews;
    }

    public String getArObjectStr() {
        return arObjectStr;
    }

    public void setPursuing(int pursuing) {
        this.pursuing = pursuing;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public void setPendingReviews(int pendingReviews) {
        this.pendingReviews = pendingReviews;
    }
}
