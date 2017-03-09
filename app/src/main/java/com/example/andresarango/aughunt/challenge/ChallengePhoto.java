package com.example.andresarango.aughunt.challenge;

import com.example.andresarango.aughunt.location.DAMLocation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private List<ChallengePhotoCompleted> completedPhotoChallenges;
    private long timestamp;

    public ChallengePhoto() {

    }

    public ChallengePhoto(String challengeId, String ownerId, DAMLocation location, String photoUrl, String hint, long timestamp) {
        this.challengeId = challengeId;
        this.ownerId = ownerId;
        this.location = location;
        this.photoUrl = photoUrl;
        this.hint = hint;
        this.timestamp = timestamp;
        this.pursuing = 0;
        completedPhotoChallenges = new ArrayList<>();
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

    public List<ChallengePhotoCompleted> getCompletedPhotoChallenges() {
        return completedPhotoChallenges;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setPursuing(int pursuing) {
        this.pursuing = pursuing;
    }

    public void addToCompletedPhotoChallenges(ChallengePhotoCompleted completedPhotoChallenge) {
        completedPhotoChallenges.add(completedPhotoChallenge);
    }
}
