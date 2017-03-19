package com.example.andresarango.aughunt.util.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.cameraview.CameraView;

/**
 * Created by andresarango on 2/27/17.
 */

public class CameraCallback extends CameraView.Callback {

    private final Context mContext;
    private Handler mBackgroundHandler;
    private FrameLayout mPhoto;
    private FloatingActionButton mTakePicture;

    private final String TAG = "ActivityPicture";
    private Bitmap mBitmap;
    private byte[] picData;


    public CameraCallback(Context context, FrameLayout photo, FloatingActionButton takepicture) {
        mContext = context;
        this.mPhoto = photo;
        this.mTakePicture = takepicture;
    }

    @Override
    public void onCameraOpened(CameraView cameraView) {
        System.out.println("camera opened");
    }

    @Override
    public void onCameraClosed(CameraView cameraView) {
        System.out.println("camera closed");
    }

    @Override
    public void onPictureTaken(CameraView cameraView, final byte[] data) {

        Log.d(TAG, "onPictureTaken " + data.length);
        picData = data;

        Toast.makeText(cameraView.getContext(), "Picture taken", Toast.LENGTH_SHORT)
                .show();

//        MyTask myTask = new MyTask();
//        myTask.execute(data);


//        mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);



//        Drawable d = new BitmapDrawable(mContext.getResources(), mBitmap);


//        mPhoto.setVisibility(View.VISIBLE);
//        mPhoto.setBackground(d);
        mTakePicture.setEnabled(false);

    }

    public void destroyHandler() {
        if (mBackgroundHandler != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mBackgroundHandler.getLooper().quitSafely();
            } else {
                mBackgroundHandler.getLooper().quit();
            }
            mBackgroundHandler = null;
        }
    }


    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public byte[] getPicData() {
        return picData;
    }

    public void setPicData(byte[] data) {
        picData = data;
    }

    private class MyTask extends AsyncTask<byte[], Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(byte[]... bytes) {
            byte[] data = bytes[0];
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Drawable d = new BitmapDrawable(mContext.getResources(), bitmap);
            mPhoto.setVisibility(View.VISIBLE);
            mPhoto.setBackground(d);
        }
    }
}
