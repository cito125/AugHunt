package com.example.andresarango.aughunt.challenge;

import com.example.andresarango.aughunt.location.DAMLocation;
import com.example.andresarango.aughunt.models.ChallengePhoto;

import java.util.ArrayList;
import java.util.List;

public class ChallengeFilter<T> {

    public List<ChallengePhoto> filterChallengesByProximity(List<ChallengePhoto> mChallengeList, DAMLocation userLocation, Double radius) {
        List<ChallengePhoto> nearbyChallenges = new ArrayList<>();
        for (int i = 0; i < mChallengeList.size(); i++) {
            ChallengePhoto challenge = mChallengeList.get(i);
            boolean challengeIsNearUser = userLocation.isWithinRadius(challenge.getLocation(), radius);
            if (challengeIsNearUser) {
                nearbyChallenges.add(challenge);
            }
        }
        return nearbyChallenges;

    }
}
