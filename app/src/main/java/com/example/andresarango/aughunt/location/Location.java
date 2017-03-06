package com.example.andresarango.aughunt.location;

public class Location {
    private Double mLat;
    private Double mLng;
    private Double mElevation;

    public Location(Double lat, Double lng) {
        this(lat,lng,0.0);
    }
    public Location(Double lat, Double lng, Double elevation) {
        this.mLat = lat;
        this.mLng = lng;
        this.mElevation = elevation;
    }

    public Double getLat() {
        return mLat;
    }

    public void setLat(Double lat) {
        this.mLat = lat;
    }

    public Double getLng() {
        return mLng;
    }

    public void setLng(Double lng) {
        this.mLng = lng;
    }

    public Double getElevation() {
        return mElevation;
    }

    public void setElevation(Double elevation) {
        this.mElevation = elevation;
    }

    public boolean isWithinRadius(Location secondLocation, Double radius){
        return isWithinRadius(getLat(), getLng(), getElevation(),
                secondLocation.getLat(),
                secondLocation.getLng(),
                secondLocation.getElevation(),
                radius);
    }

    public Double distanceTo(Location secondLocation){
        return getDistance(getLat(), getLng(), getElevation(),
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
