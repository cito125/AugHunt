package com.example.andresarango.aughunt.homescreen;

import android.graphics.Bitmap;

import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.CompletedChallenges;

/**
 * Created by Millochka on 3/6/17.
 */

public interface ChallengeReviewHelper<T> {

    void passingChallange(Challenge<T> c);
    void passingCompletedChallange(CompletedChallenges<T> cc, Challenge<Bitmap> c);
}
