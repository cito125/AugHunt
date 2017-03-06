package com.example.andresarango.aughunt;

import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.location.Location;
import com.example.andresarango.aughunt.location.LocationChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andresarango on 3/5/17.
 */

public class ChallengeFilter<T> {

    public List<Challenge<T>> filterChallengesByProximity(List<Challenge<T>> mChallengeList, Location userLocation, Double radius) {
        List<Challenge<T>> nearbyChallenges = new ArrayList<>();
        LocationChecker locationChecker = new LocationChecker();

        for (int i = 0; i < mChallengeList.size(); i++) {
            Challenge<T> challenge = mChallengeList.get(i);
            boolean challengeIsNearUser = locationChecker.areLocationsWithinRadius(
                    userLocation,
                    challenge.getLocation(),
                    radius);
            if(challengeIsNearUser){
                nearbyChallenges.add(challenge);
            }
        }
        return nearbyChallenges;

    }
}
