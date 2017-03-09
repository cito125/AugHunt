package com.example.andresarango.aughunt.challenge.challenges_adapters.review;


import com.example.andresarango.aughunt.challenge.CompletedChallenge;

public interface CompletedChallengeViewholderListener<T> {
    void onCompletedChallengeClicked(CompletedChallenge<T> completedChallenge);

}
