package com.example.andresarango.aughunt.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by dannylui on 3/13/17.
 */

public class FirebaseHelper {
    public static FirebaseHelper instance;

    private FirebaseAuth auth;
    private DatabaseReference rootRef;
    private StorageReference storageRef;

    private FirebaseHelper() {
        auth = FirebaseAuth.getInstance();
        rootRef =FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();

    }

    public static FirebaseHelper getInstance() {
        if (instance == null) {
            instance = new FirebaseHelper();
        }
        return instance;
    }

//    public String getUsernameWithUid(String uid) {
//        String username = "";
//        rootRef.child("users").child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User user =  dataSnapshot.getValue(User.class);
//                username = user.getProfileName();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        return username;
//    }

}
