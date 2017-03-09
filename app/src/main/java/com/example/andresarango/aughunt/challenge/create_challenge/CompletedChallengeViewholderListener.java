package com.example.andresarango.aughunt.challenge.create_challenge;


import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.CompletedChallenge;

public interface CompletedChallengeViewholderListener<T> {
    void onCompletedChallengeClicked(CompletedChallenge<T> completedChallenge);

}
