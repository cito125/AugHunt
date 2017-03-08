package com.example.andresarango.aughunt.challenge;

import com.example.andresarango.aughunt.location.Location;

import java.util.ArrayList;
import java.util.List;

public class ChallengeFilter<T> {

    public List<Challenge<T>> filterChallengesByProximity(List<Challenge<T>> mChallengeList, Location userLocation, Double radius) {
        List<Challenge<T>> nearbyChallenges = new ArrayList<>();
        for (int i = 0; i < mChallengeList.size(); i++) {
            Challenge<T> challenge = mChallengeList.get(i);
            boolean challengeIsNearUser = userLocation.isWithinRadius(challenge.getLocation(),radius);
            if(challengeIsNearUser){
                nearbyChallenges.add(challenge);
            }
        }
        return nearbyChallenges;

    }
}
