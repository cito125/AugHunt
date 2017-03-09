package com.example.andresarango.aughunt;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
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
import android.widget.FrameLayout;

import com.example.andresarango.aughunt.camera.AspectRatioFragment;
import com.example.andresarango.aughunt.camera.CameraCallback;
import com.example.andresarango.aughunt.snapshot_callback.SnapshotHelper;
import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.CameraView;
import com.google.android.gms.awareness.snapshot.LocationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dannylui on 3/9/17.
 */

public class AcceptedChallengeActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,
        AspectRatioFragment.Listener, ViewGroup.OnClickListener,
        SnapshotHelper.SnapshotListener {

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final int LOCATION_PERMISSION = 1245;

    @BindView(R.id.cam_accepted_challenge) CameraView mCameraView;
    @BindView(R.id.btn_accepted_take_photo) Button mTakePhotoBtn;
    @BindView(R.id.accepted_photo) FrameLayout mPhoto;
    @BindView(R.id.btn_accepted_submit_challenge) Button submitChallengeBtn;

    private CameraCallback mCameraCallback;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_accepted);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        mTakePhotoBtn.setOnClickListener(this);
        mPhoto.setOnClickListener(this);
        submitChallengeBtn.setOnClickListener(this);

        requestPermission();
        initializeCamera();

    }
    private void initializeCamera() {
        mCameraCallback = new CameraCallback(this, mPhoto, mTakePhotoBtn);
        if (mCameraView != null) {
            mCameraView.addCallback(mCameraCallback);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_accepted_take_photo:
                System.out.println("TAKING PHOTO");
                break;
            case R.id.accepted_photo:
                System.out.println("RESETTING PHOTO");
                break;
            case R.id.btn_accepted_submit_challenge:
                System.out.println("SUBMITTING PHOTO");
                break;
        }
    }

    private void requestPermission() {
        int locationPermission = ContextCompat.checkSelfPermission(AcceptedChallengeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        boolean locationPermissionIsNotGranted = locationPermission != PackageManager.PERMISSION_GRANTED;
        boolean APILevelIsTwentyThreeOrHigher = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        if (locationPermissionIsNotGranted && APILevelIsTwentyThreeOrHigher) {
            marshamallowRequestPermission();
        }
        if (locationPermissionIsNotGranted) {
            ActivityCompat.requestPermissions(AcceptedChallengeActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION);
        }
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

    @TargetApi(Build.VERSION_CODES.M)
    private void marshamallowRequestPermission() {
        boolean userHasAlreadyRejectedPermission = !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
        if (userHasAlreadyRejectedPermission) {
            showMessageOKCancel("We need your location to find nearby challenges, is this too much trouble ?",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(AcceptedChallengeActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    LOCATION_PERMISSION);
                        }
                    });
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(AcceptedChallengeActivity.this)
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
    public void run(LocationResult locationResult) {

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
}
