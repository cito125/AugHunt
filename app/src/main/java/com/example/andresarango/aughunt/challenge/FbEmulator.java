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

public class FbEmulator {

    private Challenge mChallenge;
    private Context mContext;
    private static final String IMAGE_DATA ="image_data" ;

    public  FbEmulator(Challenge challenge, Context context){
        this.mChallenge=challenge;
        this.mContext=context;

    }

    public void bitmapToByte( ){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mChallenge.getmChallenge().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        saveToShPref(byteArray);
    }

    public void saveToShPref(final byte[] image){
        String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);

        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor edit=shre.edit();
        edit.putString(IMAGE_DATA,encodedImage);
        edit.apply();


    }

}
