package com.example.andresarango.aughunt.user;

/**
 * Created by Millochka on 3/6/17.
 */

public class User {
    private String userId;
    private String profileName;
    private int userPoints;
    private String profilePicUrl;

    public User() {

    }

    public User(String userId, String profileName) {
        this.userId = userId;
        this.profileName = profileName;
        this.userPoints = 0;
    }

    public String getUserId() {
        return userId;
    }

    public int getUserPoints() {
        return userPoints;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
}
