package com.example.andresarango.aughunt.challenge;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Millochka on 2/28/17.
 */

public class Challenge {


    private Map<Double,Double> location= new HashMap<>();
    private Map<Bitmap,Map<Double, Double>> challenge = new HashMap<>();


    public void setLocation(Map<Double, Double> location) {
        this.location = location;
    }

    public void setChallenge(Bitmap image) {

        this.challenge.put(image,this.location);
    }
}
