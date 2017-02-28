package com.example.andresarango.aughunt.location;

/**
 * Created by andresarango on 2/26/17.
 */

public class LocationChecker {

    public boolean areLocationsWithinRadius(Location firstLocation, Location secondLocation, Double radius){
        return isWithinRadius(firstLocation.getLat(),
                firstLocation.getLng(),
                firstLocation.getElevation(),
                secondLocation.getLat(),
                secondLocation.getLng(),
                secondLocation.getElevation(),
                radius);
    }

    public Double distanceBetweenLocations(Location firstLocation, Location secondLocation){
        return getDistance(firstLocation.getLat(),
                firstLocation.getLng(),
                firstLocation.getElevation(),
                secondLocation.getLat(),
                secondLocation.getLng(),
                secondLocation.getElevation());
    }

    private boolean isWithinRadius(Double firstLat,
                                   Double firstLng,
                                   Double firstHeight,
                                   Double secondLat,
                                   Double secondLng,
                                   Double secondHeight,
                                   Double radius) {

        double distance = getDistance(firstLat, firstLng, firstHeight, secondLat, secondLng, secondHeight);

        return distance <= radius;
    }

    private double getDistance(Double firstLat,
                               Double firstLng,
                               Double firstHeight,
                               Double secondLat,
                               Double secondLng,
                               Double secondHeight) {
        final Double R = 6371.0; // Radius of the earth in km

        Double latDistance = Math.toRadians(firstLat - secondLat);
        Double lonDistance = Math.toRadians(firstLng - secondLng);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(secondLat)) * Math.cos(Math.toRadians(firstLat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distance = R * c * 1000; // convert to meters

        Double height = secondHeight - firstHeight;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance); // returns distance in meters
    }

}
