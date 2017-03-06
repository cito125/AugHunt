package com.example.andresarango.aughunt.challenge.challenge_dialog_fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.ChallengeActivity;

public class ChallengeDialogFragment<T> extends DialogFragment {

    private static final String CHALLENGE_HINT = "challenge hint";

    public ChallengeDialogFragment() {
    }


    public static <T> ChallengeDialogFragment<T> getInstance(Challenge<T> challenge) {
        ChallengeDialogFragment<T> fragment = new ChallengeDialogFragment<>();
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
                Intent intent = new Intent(getContext(), ChallengeActivity.class);
                startActivity(intent);
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }
}
