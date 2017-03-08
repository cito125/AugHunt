package com.example.andresarango.aughunt.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
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

    private final String TAG="ActivityPicture";
    private Bitmap mBitmap;



    public CameraCallback(Context context, FrameLayout photo) {
        mContext = context;
        this.mPhoto=photo;
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
        mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        Toast.makeText(cameraView.getContext(), "Took a picture", Toast.LENGTH_SHORT)
                .show();
        Drawable d = new BitmapDrawable(mContext.getResources(), mBitmap);


        mPhoto.setVisibility(View.VISIBLE);
        mPhoto.setBackground(d);

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


}
