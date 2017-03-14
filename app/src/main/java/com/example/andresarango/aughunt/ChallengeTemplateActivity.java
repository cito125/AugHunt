package com.example.andresarango.aughunt;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.andresarango.aughunt.camera.AspectRatioFragment;
import com.example.andresarango.aughunt.camera.CameraCallback;
import com.example.andresarango.aughunt.models.ChallengePhoto;
import com.example.andresarango.aughunt.location.DAMLocation;
import com.example.andresarango.aughunt.snapshot_callback.SnapshotHelper;
import com.example.andresarango.aughunt.models.User;
import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.CameraView;
import com.google.android.gms.awareness.snapshot.LocationResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChallengeTemplateActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,
        AspectRatioFragment.Listener, ViewGroup.OnClickListener,
        SnapshotHelper.SnapshotListener {

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final int LOCATION_PERMISSION = 1245;

    @BindView(R.id.cam_create_challenge) CameraView mCameraView;
    @BindView(R.id.btn_take_photo)
    FloatingActionButton mTakePhotoButton;
    @BindView(R.id.btn_leave_hint) Button mHint;
    @BindView(R.id.btn_submit_challenge) Button mSubmit;
    @BindView(R.id.photo) FrameLayout mPhoto;

    private CameraCallback mCameraCallback;

    private String mHintText;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_template);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);

        mTakePhotoButton.setOnClickListener(this);
        mPhoto.setOnClickListener(this);
        mHint.setOnClickListener(this);
        mSubmit.setOnClickListener(this);

        initializeCamera();
        requestPermission();
    }


    private void initializeCamera() {
        mCameraCallback = new CameraCallback(this, mPhoto, mTakePhotoButton);
        if (mCameraView != null) {
            mCameraView.addCallback(mCameraCallback);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_photo:
                mCameraView.takePicture();
                break;
            case R.id.photo:
                mPhoto.setVisibility(View.INVISIBLE);
                mTakePhotoButton.setEnabled(true);
                mCameraCallback.setPicData(null);
                break;
            case R.id.btn_leave_hint:
                createDialog();
                break;
            case R.id.btn_submit_challenge:
                if (mCameraCallback.getPicData() != null && !TextUtils.isEmpty(mHintText)) {
                    progressDialog.setMessage("Submitting");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    submitChallenge();
                } else {
                    Toast.makeText(this, "Hint or photo is missing", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
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


    private void submitChallenge() {
        SnapshotHelper snapshotHelper = new SnapshotHelper(this);
        snapshotHelper.runSnapshot(getApplicationContext());
    }

    public void createDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.AboutDialog);

        final EditText edittext = new EditText(getApplicationContext());
        alert.setView(edittext);

        alert.setMessage("Enter Your Hint");


        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mHintText = edittext.getText().toString().trim();
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
        double latitude = locationResult.getLocation().getLatitude();
        double longitude = locationResult.getLocation().getLongitude();
        final DAMLocation damLocation = new DAMLocation(latitude, longitude);

        final String pushId = rootRef.child("challenges").push().getKey(); // Get a unique id for the challenge
        UploadTask uploadTask = storageRef.child("challenges").child(pushId).putBytes(mCameraCallback.getPicData()); // Upload photo taken to firebase storage
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url = taskSnapshot.getDownloadUrl().toString();
                ChallengePhoto challenge = new ChallengePhoto(pushId, auth.getCurrentUser().getUid(), damLocation, url, mHintText, System.currentTimeMillis()/1000);
                rootRef.child("challenges").child(pushId).setValue(challenge); // Upload challenge object to firebase database

                updateNumberOfCompletedChallenges();

                Toast.makeText(getApplicationContext(), "Challenge submitted", Toast.LENGTH_SHORT)
                        .show();
                progressDialog.dismiss();
                finish();
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();

                progressDialog.dismiss();
                Toast.makeText(ChallengeTemplateActivity.this, "Failed to save image.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateNumberOfCompletedChallenges() {
        rootRef.child("users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                currentUser.setNumberOfCreatedChallenges(currentUser.getNumberOfSubmittedChallenges() + 1);
                rootRef.child("users").child(auth.getCurrentUser().getUid()).setValue(currentUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
}
