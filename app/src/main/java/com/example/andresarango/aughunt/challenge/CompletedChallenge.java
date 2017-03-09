package com.example.andresarango.aughunt.challenge;

/**
 * Created by Millochka on 3/5/17.
 */
public class CompletedChallenge<T> {

    private final T mCompletedChallenge;
    private final String mOwnerId;


    public CompletedChallenge(T mCompletedChallenge, String mOwnerId) {
        this.mCompletedChallenge = mCompletedChallenge;
        this.mOwnerId = mOwnerId;
    }

    public T getCompletedChallenge() {
        return mCompletedChallenge;
    }

    public String getOwnerId() {
        return mOwnerId;
    }
}
