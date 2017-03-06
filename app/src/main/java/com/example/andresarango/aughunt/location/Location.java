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
}
