package com.example.andresarango.aughunt.challenge;

import android.graphics.Bitmap;

import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.CompletedChallenge;

/**
 * Created by Millochka on 3/6/17.
 */

public interface ChallengeViewholderListener<T> {
    void onChallengeClicked(Challenge<T> challenge);

    void onChallengeClicked(CompletedChallenge<T> completedChallenge, Challenge<Bitmap> challenge);
}
