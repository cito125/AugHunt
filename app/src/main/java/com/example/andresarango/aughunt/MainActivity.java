package com.example.andresarango.aughunt;


import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.andresarango.aughunt.camera.AspectRatioFragment;
import com.example.andresarango.aughunt.camera.CameraCallback;
import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.CameraView;


public class MainActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,
        AspectRatioFragment.Listener {

    private static final int REQUEST_CAMERA_PERMISSION = 1;

    private CameraView mCameraView;
    private Button mTakePhotoButton;
    private CameraCallback mCameraCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeCamera();
        initializeTakePhotoButton();
    }

    private void initializeCamera() {
        mCameraView = (CameraView) findViewById(R.id.activity_main_camera);
        mCameraCallback = new CameraCallback(getApplicationContext());
        if (mCameraView != null) {
            mCameraView.addCallback(mCameraCallback);
        }
    }

    private void initializeTakePhotoButton() {
        mTakePhotoButton = (Button) findViewById(R.id.activity_main_button_take_photo);
        mTakePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraView.takePicture();
            }
        });
    }

    @Override
    public void onAspectRatioSelected(@NonNull AspectRatio ratio) {
        if (mCameraView != null) {
            mCameraView.setAspectRatio(ratio);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCameraPermission();
    }

    @Override
    protected void onPause() {
        mCameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraCallback.destroyHandler();
    }


    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            mCameraView.start();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (permissions.length != 1 || grantResults.length != 1) {
                    throw new RuntimeException("Error on requesting camera permission.");
                }
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                }
                // No need to start camera here; it is handled by onResume
                break;
        }
    }



}
