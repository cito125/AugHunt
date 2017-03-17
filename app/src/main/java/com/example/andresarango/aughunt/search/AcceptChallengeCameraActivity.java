package com.example.andresarango.aughunt.search;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.util.camera.AspectRatioFragment;
import com.example.andresarango.aughunt.util.camera.CameraCallback;
import com.example.andresarango.aughunt.util.challenge_dialog_fragment.ChallengeDialogFragment;
import com.example.andresarango.aughunt._models.ChallengePhoto;
import com.example.andresarango.aughunt._models.ChallengePhotoCompleted;
import com.example.andresarango.aughunt._models.ChallengePhotoSubmitted;
import com.example.andresarango.aughunt._models.User;
import com.example.andresarango.aughunt.util.snapshot_callback.SnapshotHelper;
import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.CameraView;
import com.google.android.gms.awareness.snapshot.LocationResult;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dannylui on 3/9/17.
 */

public class AcceptChallengeCameraActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,
        AspectRatioFragment.Listener, ViewGroup.OnClickListener,
        SnapshotHelper.SnapshotListener {

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final int LOCATION_PERMISSION = 1245;

    @BindView(R.id.cam_accepted_challenge) CameraView mCameraView;
    @BindView(R.id.btn_accepted_take_photo)
    FloatingActionButton mTakePhotoBtn;
    @BindView(R.id.accepted_photo) FrameLayout mPhoto;
    @BindView(R.id.btn_accepted_submit_challenge) Button submitChallengeBtn;

    private CameraCallback mCameraCallback;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private ProgressDialog progressDialog;

    private ChallengePhoto mChallengePhoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_search);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        mTakePhotoBtn.setOnClickListener(this);
        mPhoto.setOnClickListener(this);
        submitChallengeBtn.setOnClickListener(this);

        requestPermission();
        initializeCamera();

        mChallengePhoto = (ChallengePhoto) getIntent().getSerializableExtra(ChallengeDialogFragment.CHALLENGE);
        System.out.println("GOT SERIALIZABLE: " + mChallengePhoto.getChallengeId());

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
                mCameraView.takePicture();
                break;
            case R.id.accepted_photo:
                System.out.println("RESETTING PHOTO");
                mPhoto.setVisibility(View.INVISIBLE);
                mTakePhotoBtn.setEnabled(true);
                mCameraCallback.setPicData(null);
                break;
            case R.id.btn_accepted_submit_challenge:
                System.out.println("SUBMITTING PHOTO");
                if (mCameraCallback.getPicData() != null) {
                    progressDialog.setMessage("Submitting");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    submitCompletedChallenge();
                } else {
                    Toast.makeText(this, "No picture taken", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void submitCompletedChallenge() {
        final String pushId = rootRef.child("completed-challenges").child(mChallengePhoto.getChallengeId()).push().getKey(); // Get a unique id for the completed challenge

        UploadTask uploadTask = storageRef.child("challenges").child(mChallengePhoto.getChallengeId()).child(pushId).putBytes(mCameraCallback.getPicData()); // Upload photo taken to firebase storage
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url = taskSnapshot.getDownloadUrl().toString();
                final ChallengePhotoCompleted completedChallenge = new ChallengePhotoCompleted(pushId, mChallengePhoto.getChallengeId(), auth.getCurrentUser().getUid(), url);
                rootRef.child("completed-challenges").child(mChallengePhoto.getChallengeId()).child(pushId).setValue(completedChallenge);

                // Create submitted challenge object and push to firebase
                ChallengePhotoSubmitted submittedChallenge = new ChallengePhotoSubmitted(mChallengePhoto.getChallengeId(), mChallengePhoto.getOwnerId(), mChallengePhoto.getHint(), url, mChallengePhoto.getPhotoUrl());
                rootRef.child("submitted-challenges").child(auth.getCurrentUser().getUid()).child(mChallengePhoto.getChallengeId()).setValue(submittedChallenge);

                incrementCompletedCounter();
                incrementSubmittedCounter();
            }
        });


    }

    private void incrementSubmittedCounter() {
        rootRef.child("users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                currentUser.setNumberOfSubmittedChallenges(currentUser.getNumberOfSubmittedChallenges() + 1);
                rootRef.child("users").child(auth.getCurrentUser().getUid()).setValue(currentUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void incrementCompletedCounter() {
        rootRef.child("challenges").child(mChallengePhoto.getChallengeId()).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ChallengePhoto challenge = mutableData.getValue(ChallengePhoto.class);
                challenge.setCompleted(challenge.getCompleted() + 1);
                challenge.setPendingReviews(challenge.getPendingReviews()+1);
                mutableData.setValue(challenge);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                decrementPursuingCounter(); // Chaining these methods to finish properly

            }
        });
    }

    private void decrementPursuingCounter() {
        rootRef.child("challenges").child(mChallengePhoto.getChallengeId()).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ChallengePhoto challenge = mutableData.getValue(ChallengePhoto.class);
                challenge.setPursuing(challenge.getPursuing() - 1);
                mutableData.setValue(challenge);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), "Challenge submitted", Toast.LENGTH_SHORT)
                        .show();
                progressDialog.dismiss();
                finish();
            }
        });
    }

    private void requestPermission() {
        int locationPermission = ContextCompat.checkSelfPermission(AcceptChallengeCameraActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        boolean locationPermissionIsNotGranted = locationPermission != PackageManager.PERMISSION_GRANTED;
        boolean APILevelIsTwentyThreeOrHigher = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        if (locationPermissionIsNotGranted && APILevelIsTwentyThreeOrHigher) {
            marshamallowRequestPermission();
        }
        if (locationPermissionIsNotGranted) {
            ActivityCompat.requestPermissions(AcceptChallengeCameraActivity.this,
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
                            ActivityCompat.requestPermissions(AcceptChallengeCameraActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    LOCATION_PERMISSION);
                        }
                    });
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(AcceptChallengeCameraActivity.this)
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
