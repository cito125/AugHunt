package com.example.andresarango.aughunt.create;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.HomeScreenActivity;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.ChallengePhoto;
import com.example.andresarango.aughunt._models.ChallengePhotoCompleted;
import com.example.andresarango.aughunt._models.ChallengePhotoSubmitted;
import com.example.andresarango.aughunt._models.DAMLocation;
import com.example.andresarango.aughunt._models.User;
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

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dannylui on 3/18/17.
 */

public class PictureTakenActivity extends AppCompatActivity {
    @BindView(R.id.iv_edit_photo)
    ImageView photoIv;

    public static String DESTINATION_KEY = "YOMAMA";

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private ChallengePhoto mChallengePhoto;
    private byte[] picByteArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_taken);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        mChallengePhoto = UnityPlayerActivity.mChallengePhoto;

        System.out.println("GET HERE FROM UNITY ACTIVITY");
        if (intent.hasExtra(DESTINATION_KEY)) {

//            System.out.println("GET INSIDE FROM UNITY ACTIVITY");
//            System.out.println(DESTINATION_KEY);
            String filePath = intent.getStringExtra(DESTINATION_KEY);
//            System.out.println(filePath);
//            String[] array = filePath.split(" ");
//            System.out.println("ARRAY SIZE: " + array.length);
//            String challengeId = array[0];
//            String hint = array[1];
//            String ownerId = array[2];
//            String photoUrl = array[3];
//            String lat = array[4];
//            String lng = array[5];
//            String timestamp = array[6];
//            String arObjectStr = array[7];

//            DAMLocation location = new DAMLocation(Double.valueOf(lat), Double.valueOf(lng));
//
//            mChallengePhoto = new ChallengePhoto(challengeId, ownerId, location, photoUrl, hint, Long.valueOf(timestamp), arObjectStr);

            Bitmap bmp = BitmapFactory.decodeFile(filePath);
            photoIv.setImageBitmap(bmp);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            picByteArray = stream.toByteArray();
        }
/*
03-20 14:07:32.651 27722-27722/com.example.andresarango.aughunt I/System.out: -KfgsqIKVGVUf3WcQXxz fghj 3uAcOjBgcPZb0Rs8EggRsNXNBmK2 https://firebasestorage.googleapis.com/v0/b/capstoneaughunt.appspot.com/o/challenges%2F-KfgsqIKVGVUf3WcQXxz?alt=media&token=05f8dea8-c2cd-48fd-a329-d3d6c5a6ff44 40.7416373 -73.9352932 1490033012 kitten YOMAMA


 */

        submitCompletedChallenge();
        Toast.makeText(getApplicationContext(), "Sending...", Toast.LENGTH_SHORT).show();
    }

    private void submitCompletedChallenge() {
        final String pushId = rootRef.child("completed-challenges").child(mChallengePhoto.getChallengeId()).push().getKey(); // Get a unique id for the completed challenge

        UploadTask uploadTask = storageRef.child("challenges").child(mChallengePhoto.getChallengeId()).child(pushId).putBytes(picByteArray); // Upload photo taken to firebase storage
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url = taskSnapshot.getDownloadUrl().toString();
                final ChallengePhotoCompleted completedChallenge = new ChallengePhotoCompleted(pushId, mChallengePhoto.getChallengeId(), auth.getCurrentUser().getUid(), url);
                rootRef.child("completed-challenges").child(mChallengePhoto.getChallengeId()).child(pushId).setValue(completedChallenge);

                // Create submitted challenge object and push to firebase
                ChallengePhotoSubmitted submittedChallenge = new ChallengePhotoSubmitted(mChallengePhoto.getChallengeId(), mChallengePhoto.getOwnerId(), mChallengePhoto.getHint(), url, mChallengePhoto.getPhotoUrl(), System.currentTimeMillis()/1000);
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
                Toast.makeText(getApplicationContext(), "Challenge sent to be reviewed!", Toast.LENGTH_SHORT)
                        .show();
                Toast.makeText(getApplicationContext(), "Sent photo for review!", Toast.LENGTH_LONG).show();

                // Will cause unity player to call finish on resume
                UnityPlayerActivity.hasSubmitted = true;
                finish();
            }
        });
    }
}
