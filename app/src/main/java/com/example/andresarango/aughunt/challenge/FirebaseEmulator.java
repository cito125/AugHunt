package com.example.andresarango.aughunt.challenge;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.example.andresarango.aughunt.location.DAMLocation;

import java.io.ByteArrayOutputStream;

/**
 * Created by Millochka on 3/1/17.
 */

public class FbEmulator {

    private static final String HINT_DATA = "hint_data";
    private Challenge<Bitmap> mChallenge;
    private Context mContext;
    private static final String IMAGE_DATA ="image_data" ;

    public  FbEmulator(Context context){

        this.mContext=context;

    }

    public  FbEmulator(Challenge challenge, Context context){
        this.mChallenge=challenge;
        this.mContext=context;

    }

    public void saveToDB( ){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mChallenge.getmChallenge().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        saveToShPref(byteArray, mChallenge.getmHint(),mChallenge.getmLocation());

    }

    public void saveToShPref(final byte[] image, final String hint, DAMLocation location){
        String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);

        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor edit=shre.edit();
        edit.putString(IMAGE_DATA,encodedImage);
        edit.putString(HINT_DATA,hint);
        edit.putLong("lat",Double.doubleToRawLongBits(location.getLat()));
        edit.putLong("lng",Double.doubleToRawLongBits(location.getLng()));
        edit.apply();


    }

    public Challenge getmChallenge(){

        DAMLocation location = new DAMLocation(retrieveLLFrSh()[0],retrieveLLFrSh()[1]);

        Challenge<Bitmap> challenge= new Challenge(retrieveBFrSh(),location);
        challenge.setmHint(retrieveHFrSh());




     return challenge;
    }

    public Bitmap retrieveBFrSh(){


        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(mContext);
        String previouslyEncodedImage = shre.getString(IMAGE_DATA, "");

        if( !previouslyEncodedImage.equalsIgnoreCase("") ){
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            return bitmap;
        }
        return null;
    }

    public String retrieveHFrSh(){


        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(mContext);
        String hint = shre.getString(HINT_DATA, "");

        if( !hint.equalsIgnoreCase("") ){

            return hint;
        }
        return null;
    }

    public double[] retrieveLLFrSh(){

        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(mContext);
        double[] latLong = new double[2];

            latLong[0] =   Double.longBitsToDouble(shre.getLong("lat", 0));
        latLong[1]=   Double.longBitsToDouble(shre.getLong("lng", 0));


            return latLong;

    }


}
