package com.example.andresarango.aughunt;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.cameraview.CameraView;


public class MainActivity extends AppCompatActivity {


    private CameraView mCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCameraView = (CameraView) findViewById(R.id.camera);


    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCameraView.stop();

    }
}
