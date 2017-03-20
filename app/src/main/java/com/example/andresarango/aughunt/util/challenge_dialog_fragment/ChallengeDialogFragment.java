package com.example.andresarango.aughunt.util.challenge_dialog_fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.andresarango.aughunt.create.UnityPlayerActivity;
import com.example.andresarango.aughunt.search.AcceptChallengeCameraActivity;
import com.example.andresarango.aughunt._models.ChallengePhoto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;


public class ChallengeDialogFragment extends DialogFragment {
    public static final String CHALLENGE = "challenge";
    private static final String CHALLENGE_HINT = "challenge hint";
    private static ChallengePhoto mChallengePhoto;
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    public static ChallengeDialogFragment getInstance(ChallengePhoto challenge) {
        mChallengePhoto = challenge;
        ChallengeDialogFragment fragment = new ChallengeDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(CHALLENGE_HINT, challenge.getHint());
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString(CHALLENGE_HINT);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setPositiveButton("START CHALLENGE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // update pursuing counter
                rootRef.child("challenges").child(mChallengePhoto.getChallengeId()).runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        ChallengePhoto challenge = mutableData.getValue(ChallengePhoto.class);
                        challenge.setPursuing(challenge.getPursuing() + 1);
                        mutableData.setValue(challenge);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                    }
                });


//                Intent intent = new Intent(getContext(), AcceptChallengeCameraActivity.class);
//                intent.putExtra(CHALLENGE, mChallengePhoto);
//                startActivity(intent);
                Intent intent = new Intent(getContext(), UnityPlayerActivity.class);
                intent.putExtra(CHALLENGE, mChallengePhoto);
                startActivity(intent);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }
}
