package com.example.andresarango.aughunt;

import android.graphics.Bitmap;

import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.CompletedChallenge;

/**
 * Created by Millochka on 3/6/17.
 */

public interface ChallengeReviewHelper<T> {
    void passingChallange(Challenge<T> challenge);
    void passingCompletedChallange(CompletedChallenge<T> completedChallenge, Challenge<Bitmap> challenge);
}
