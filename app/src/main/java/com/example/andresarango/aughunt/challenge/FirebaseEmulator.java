package com.example.andresarango.aughunt.challenge;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Millochka on 3/1/17.
 */

public class FirebaseEmulator {

    private static final String HINT_DATA = "hint_data";
    private Challenge<Bitmap> mChallenge;
    private Context mContext;
    private static final String IMAGE_DATA = "image_data";

    public FirebaseEmulator(Challenge challenge, Context context) {
        this.mChallenge = challenge;
        this.mContext = context;

    }

    public void bitmapToByte() {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mChallenge.getChallenge().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        saveToShPref(byteArray, mChallenge.getHint());
    }

    public void saveToShPref(final byte[] image, final String hint) {
        String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);

        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor edit = shre.edit();
        edit.putString(IMAGE_DATA, encodedImage);
        edit.putString(HINT_DATA, hint);
        edit.apply();


    }

}
