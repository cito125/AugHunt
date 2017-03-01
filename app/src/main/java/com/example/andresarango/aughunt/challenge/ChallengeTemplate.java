package com.example.andresarango.aughunt.challenge;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.camera.AspectRatioFragment;
import com.example.andresarango.aughunt.camera.CameraCallback;
import com.example.andresarango.aughunt.homescreen.HomeScreenActivity;
import com.example.andresarango.aughunt.location.Location;
import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.CameraView;


public class ChallengeTemplate extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,
        AspectRatioFragment.Listener,ViewGroup.OnClickListener {

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final String IMAGE_DATA ="image_data" ;

    private CameraView mCameraView;
    private Button mTakePhotoButton;
    private CameraCallback mCameraCallback;
    private final String TAG="ActivityPicture";
    private FrameLayout mPhoto;
    private Button mHint;
    private Button mSubmit;
    private Challenge mChallenge;
    private Location mLocation;
    private  String  mHintText = "";
    private FbEmulator mFbEmulator;


    //private  Challenge mChallenge = new Challenge();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_template);
        mPhoto=(FrameLayout) findViewById(R.id.photo);
        mPhoto.setOnClickListener(this);
        mHint=(Button) findViewById(R.id.leave_hint);
        mHint.setOnClickListener(this);
        mSubmit=(Button) findViewById(R.id.submit_challenge);
        mSubmit.setOnClickListener(this);

        initializeCamera();
        initializeTakePhotoButton();

    }

    private void initializeCamera() {
        mCameraView = (CameraView) findViewById(R.id.activity_main_camera);
        mCameraCallback = new CameraCallback(this, mPhoto);


        if (mCameraView != null) {
            mCameraView.addCallback(mCameraCallback);
        }
    }

    private void initializeTakePhotoButton() {
        mTakePhotoButton = (Button) findViewById(R.id.take_photo);
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
                break;
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.photo:

            mPhoto.setVisibility(View.INVISIBLE);
            //mBitmap.recycle();

                break;
            case R.id.leave_hint:

                createDialog();
                break;

            case R.id.submit_challenge:



                mLocation = new Location(0.1,0.1);

                mChallenge=new Challenge(mCameraCallback.getmBitmap(),mLocation);
                mChallenge.setmHint(mHintText);
                mFbEmulator= new FbEmulator(mChallenge,this);
                mFbEmulator.bitmapToByte();



                Toast.makeText(getApplicationContext(), "Challenge submitted", Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                startActivity(intent);
                break;
        }

    }
    public  void createDialog(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);


        final EditText edittext = new EditText(getApplicationContext());
        alert.setMessage("Enter Your Hint");

        alert.setView(edittext);

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                mHintText = edittext.getText().toString();

            }
        });

        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        alert.show();

    }
}
