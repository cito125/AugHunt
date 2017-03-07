package com.example.andresarango.aughunt.snapshot_callback;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.snapshot.LocationResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

/**
 * Created by Millochka on 3/5/17.
 */

public class SnapshotHelper {


    final SnapshotListener mListener;

    public SnapshotHelper(SnapshotListener mListener) {
        this.mListener = mListener;
    }

    public void runSnapshot(Context context){
        GoogleApiClient client = new GoogleApiClient.Builder(context)
                .addApi(Awareness.API)
                .build();
        client.connect();

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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
                        mListener.run(locationResult);
                    }
                });

    }
}
