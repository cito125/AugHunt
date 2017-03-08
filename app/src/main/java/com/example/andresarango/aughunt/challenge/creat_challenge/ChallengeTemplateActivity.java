package com.example.andresarango.aughunt.challenge.creat_challenge;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
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
import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.FirebaseEmulator;
import com.example.andresarango.aughunt.location.DAMLocation;
import com.example.andresarango.aughunt.snapshot_callback.SnapshotHelper;
import com.example.andresarango.aughunt.snapshot_callback.SnapshotListener;
import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.CameraView;
import com.google.android.gms.awareness.snapshot.LocationResult;


public class ChallengeTemplateActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,
        AspectRatioFragment.Listener, ViewGroup.OnClickListener,
        SnapshotListener {

    private static final int REQUEST_CAMERA_PERMISSION = 1;

    private static final int LOCATION_PERMISSION = 1245;

    private CameraView mCameraView;
    private Button mTakePhotoButton;
    private CameraCallback mCameraCallback;

    private final String TAG = "ActivityPicture";
    private FrameLayout mPhoto;
    private Button mHint;
    private Button mSubmit;
    private String mHintText = "";
    private FirebaseEmulator mFirebaseEmulator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_template);
        mPhoto = (FrameLayout) findViewById(R.id.photo);
        mPhoto.setOnClickListener(this);
        mHint = (Button) findViewById(R.id.leave_hint);
        mHint.setOnClickListener(this);
        mSubmit = (Button) findViewById(R.id.submit_challenge);
        mSubmit.setOnClickListener(this);

        initializeTakePhotoButton();
        initializeCamera();
        requestPermission();
    }




    private void initializeCamera() {

        mCameraCallback = new CameraCallback(this, mPhoto, mTakePhotoButton);


        if (mCameraView != null) {
            mCameraView.addCallback(mCameraCallback);
        }
    }

    private void initializeTakePhotoButton() {
        mCameraView = (CameraView) findViewById(R.id.activity_main_camera);
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

    private void requestPermission() {
        int locationPermission = ContextCompat.checkSelfPermission(ChallengeTemplateActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        boolean locationPermissionIsNotGranted = locationPermission != PackageManager.PERMISSION_GRANTED;
        boolean APILevelIsTwentyThreeOrHigher = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        if (locationPermissionIsNotGranted && APILevelIsTwentyThreeOrHigher) {
            marshamallowRequestPermission();
        }
        if (locationPermissionIsNotGranted) {
            ActivityCompat.requestPermissions(ChallengeTemplateActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION);
        }

    }


    @TargetApi(Build.VERSION_CODES.M)
    private void marshamallowRequestPermission() {
        boolean userHasAlreadyRejectedPermission = !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
        if (userHasAlreadyRejectedPermission) {
            showMessageOKCancel("We need your location to find nearby challenges, is this too much trouble ?",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ChallengeTemplateActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    LOCATION_PERMISSION);
                        }
                    });
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(ChallengeTemplateActivity.this)
                .setMessage(message)
                .setPositiveButton("NO", onClickListener)
                .setNegativeButton("YES", null)
                .create()
                .show();
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

        switch (v.getId()) {

            case R.id.photo:

                mPhoto.setVisibility(View.INVISIBLE);
                mTakePhotoButton.setEnabled(true);
                mCameraCallback.setmBitmap(null);

                break;
            case R.id.leave_hint:

                createDialog();
                break;

            case R.id.submit_challenge:
              if(mCameraCallback.getmBitmap()!= null&&!mHintText.equals("")){
                submitChallenge();
              }
                else {
                  Toast.makeText(this, "Hint or photo is missing", Toast.LENGTH_SHORT)
                          .show();
              }




                break;
        }

    }

    private void submitChallenge() {
        SnapshotHelper snapshotHelper = new SnapshotHelper(this);
        snapshotHelper.runSnapshot(getApplicationContext());

    }

    public void createDialog() {

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

    @Override
    public void run(LocationResult locationResult) {
        DAMLocation damLocation = new DAMLocation(0.0, 0.0);
        damLocation.setLat(locationResult.getLocation().getLatitude());
        damLocation.setLng(locationResult.getLocation().getLongitude());
       Challenge<Bitmap> challenge = new Challenge<>(mCameraCallback.getmBitmap(), damLocation);
        challenge.setmHint(mHintText);
       mFirebaseEmulator = new FirebaseEmulator(this);
       mFirebaseEmulator.saveChallenge(challenge);

        Toast.makeText(getApplicationContext(), "Challenge submitted", Toast.LENGTH_SHORT)
                .show();

        finish();
    }
}
