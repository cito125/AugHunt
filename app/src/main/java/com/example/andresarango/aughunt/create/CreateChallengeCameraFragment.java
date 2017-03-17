package com.example.andresarango.aughunt.create;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.ChallengePhoto;
import com.example.andresarango.aughunt._models.DAMLocation;
import com.example.andresarango.aughunt._models.User;
import com.example.andresarango.aughunt.util.camera.AspectRatioFragment;
import com.example.andresarango.aughunt.util.camera.CameraCallback;
import com.example.andresarango.aughunt.util.snapshot_callback.SnapshotHelper;
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

/**
 * Created by Danny on 3/17/2017.
 */

public class CreateChallengeCameraFragment extends Fragment implements
        ActivityCompat.OnRequestPermissionsResultCallback,
        AspectRatioFragment.Listener, ViewGroup.OnClickListener,
        SnapshotHelper.SnapshotListener {

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final int LOCATION_PERMISSION = 1245;

    @BindView(R.id.cam_create_challenge)
    CameraView mCameraView;
    @BindView(R.id.btn_take_photo)
    FloatingActionButton mTakePhotoButton;
    @BindView(R.id.btn_leave_hint)
    Button mHint;
    @BindView(R.id.btn_submit_challenge) Button mSubmit;
    @BindView(R.id.photo)
    FrameLayout mPhoto;

    private CameraCallback mCameraCallback;

    private String mHintText;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera_create, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getContext());

        mTakePhotoButton.setOnClickListener(this);
        mPhoto.setOnClickListener(this);
        mHint.setOnClickListener(this);
        mSubmit.setOnClickListener(this);

        initializeCamera();
        requestPermission();
    }

    private void initializeCamera() {
        mCameraCallback = new CameraCallback(getContext(), mPhoto, mTakePhotoButton);
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
                    Toast.makeText(getContext(), "Hint or photo is missing", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
    }

    private void submitChallenge() {
        SnapshotHelper snapshotHelper = new SnapshotHelper(this);
        snapshotHelper.runSnapshot(getContext());
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

                incrementCreatedChallengeCounter();

                Toast.makeText(getContext(), "Challenge submitted", Toast.LENGTH_SHORT)
                        .show();
                progressDialog.dismiss();
//                finish();

                // pop this fragment for now but ideally go to the profile page
                getActivity().getSupportFragmentManager().popBackStack();

            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Failed to save image.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void incrementCreatedChallengeCounter() {
        rootRef.child("users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                currentUser.setNumberOfCreatedChallenges(currentUser.getNumberOfCreatedChallenges() + 1);
                rootRef.child("users").child(auth.getCurrentUser().getUid()).setValue(currentUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void createDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), R.style.HintDialog);

        final EditText edittext = new EditText(getContext());
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
    public void onAspectRatioSelected(@NonNull AspectRatio ratio) {
        if (mCameraView != null) {
            mCameraView.setAspectRatio(ratio);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkCameraPermission();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCameraView.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCameraCallback.destroyHandler();
    }

    // ==================== PERMISSIONS STUFF BELOW ====================
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            mCameraView.start();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {

        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        }
    }

    private void requestPermission() {
        int locationPermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        boolean locationPermissionIsNotGranted = locationPermission != PackageManager.PERMISSION_GRANTED;
        boolean APILevelIsTwentyThreeOrHigher = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        if (locationPermissionIsNotGranted && APILevelIsTwentyThreeOrHigher) {
            marshamallowRequestPermission();
        }
        if (locationPermissionIsNotGranted) {
            ActivityCompat.requestPermissions(getActivity(),
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
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    LOCATION_PERMISSION);
                        }
                    });
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("NO", onClickListener)
                .setNegativeButton("YES", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
}
