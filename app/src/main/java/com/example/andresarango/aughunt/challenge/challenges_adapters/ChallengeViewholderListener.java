package com.example.andresarango.aughunt.challenge.challenges_adapters;

import com.example.andresarango.aughunt.challenge.Challenge;

/**
 * Created by Millochka on 3/6/17.
 */

public interface ChallengeViewholderListener<T> {
    void onChallengeClicked(Challenge<T> challenge);
}
