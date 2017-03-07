package com.example.andresarango.aughunt.user;

/**
 * Created by Millochka on 3/6/17.
 */

public class User {

    private final String mUserId;
    private Integer mUserPoints;

    public User(){

        this.mUserId="Mila";
    }

    public String getUserId() {
        return mUserId;
    }

    public void setmUserPoints(Integer mUserPoints) {
        this.mUserPoints = mUserPoints;
    }

}
