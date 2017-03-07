package com.example.andresarango.aughunt.snapshot_callback;

import com.google.android.gms.awareness.snapshot.LocationResult;

/**
 * Created by Millochka on 3/5/17.
 */
public interface SnapshotListener {
    void run(LocationResult locationResult);
}
