package com.example.andresarango.aughunt.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.snapshot.LocationResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

/**
 * Created by andresarango on 2/26/17.
 */

public class DAMLocation {
    private Double mLat;
    private Double mLng;
    private Double mElevation;


    public DAMLocation(Double lat, Double lng ) {
        this(lat,lng,0.0);

    }

    public DAMLocation(Double lat, Double lng, Double elevation) {
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


    private void setLocation(Context context) {

        GoogleApiClient client = new GoogleApiClient.Builder(context)
                .addApi(Awareness.API)
                .build();
        client.connect();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Awareness.SnapshotApi.getLocation(client)
                .setResultCallback(new ResultCallback<LocationResult>() {
                    @Override
                    public void onResult(@NonNull LocationResult locationResult) {

                        if (!locationResult.getStatus().isSuccess()) {
                            System.out.println("dont work");
                            return;
                        }
                        setLat(locationResult.getLocation().getLatitude());
                        setLng(locationResult.getLocation().getLongitude());
                        System.out.println("Lat: " + getLat() + ", Lng: " + getLng());


                    }
                });

        System.out.println("made it");
    }

    }
