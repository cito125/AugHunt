package com.example.andresarango.aughunt.challenge;

/**
 * Created by Millochka on 3/5/17.
 */
public class CompletedChallenges <T> {

    private final T mCompletedChallenge;
    private final String mOwnerId;


    public CompletedChallenges(T mCompletedChallenge, String mOwnerId) {
        this.mCompletedChallenge = mCompletedChallenge;
        this.mOwnerId = mOwnerId;
    }

    public T getmCompletedChallenge() {
        return mCompletedChallenge;
    }

    public String getmOwnerId() {
        return mOwnerId;
    }
}
