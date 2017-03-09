package com.example.andresarango.aughunt;

import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.CompletedChallenge;
import com.example.andresarango.aughunt.user.User;

import java.util.List;

/**
 * Created by Millochka on 3/5/17.
 */

public interface FirebaseHelper<T> {

     List<Challenge<T>> getChallenges();
    void saveChallenge(Challenge<T> c);
    void updateAcceptedChallenge(String challengeId);
    void completeChallenge(CompletedChallenge<T> c);
    void updateUserPoints(Integer points);
    User getCurrentUser();



}
