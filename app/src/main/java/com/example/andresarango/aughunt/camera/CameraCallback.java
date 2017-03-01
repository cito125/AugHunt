package com.example.andresarango.aughunt.camera;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Base64;
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
    private static final String IMAGE_DATA ="image_data" ;
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

//        MediaStore.Images.Media.insertImage(mContext.getContentResolver(), mBitmap, "", "");
        Toast.makeText(cameraView.getContext(), "Took a picture", Toast.LENGTH_SHORT)
                .show();
        Drawable d = new BitmapDrawable(mContext.getResources(), mBitmap);


        mPhoto.setVisibility(View.VISIBLE);
        mPhoto.setBackground(d);
        saveToShPref(data);

//        System.out.println("picture taken");
//        getBackgroundHandler().post(new Runnable() {
//            @Override
//            public void run() {
//                File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
//                        "picture.jpg");
//                OutputStream os = null;
//                try {
//                    os = new FileOutputStream(file);
//                    os.write(data);
//                    os.close();
//                } catch (IOException e) {
//                    System.out.println("Cannot write to " + file);
//                } finally {
//                    if (os != null) {
//                        try {
//                            os.close();
//                        } catch (IOException e) {
//                            // Ignore
//                        }
//                    }
//                }
//            }
//        });

    }

//    private Handler getBackgroundHandler() {
//        if (mBackgroundHandler == null) {
//            HandlerThread thread = new HandlerThread("background");
//            thread.start();
//            mBackgroundHandler = new Handler(thread.getLooper());
//        }
//        return mBackgroundHandler;
//    }

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

    public void saveToShPref(final byte[] image){
        String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);

        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor edit=shre.edit();
        edit.putString(IMAGE_DATA,encodedImage);
        edit.apply();


    }
}
