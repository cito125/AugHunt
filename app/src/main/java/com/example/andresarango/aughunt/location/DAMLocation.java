package com.example.andresarango.aughunt.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
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
    private Context mContext;
    private Location mLocation;


    public DAMLocation( Context context) {
        this.mContext=context;
        getLocation();
    }

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

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    private void getLocation() {

        GoogleApiClient client = new GoogleApiClient.Builder(mContext)
                .addApi(Awareness.API)
                .build();
        client.connect();

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Awareness.SnapshotApi.getLocation(client)
                .setResultCallback(new ResultCallback<LocationResult>() {
                    @Override
                    public void onResult(@NonNull LocationResult locationResult) {
                       // System.out.println(locationResult.getStatus().getStatusMessage());
                        if (!locationResult.getStatus().isSuccess()) {
                            System.out.println("dont work");
                            return;
                        }
                       mLocation = locationResult.getLocation();

                        setLat(mLocation.getLatitude());
                        setLng(mLocation.getLongitude());
                        System.out.println("Lat: " + mLocation.getLatitude() + ", Lng: " + mLocation.getLongitude());
                    }
                });

        System.out.println("made it");
    }

    }
