package com.example.andresarango.aughunt.camera;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;

import com.google.android.cameraview.CameraView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by andresarango on 2/27/17.
 */

public class CameraCallback extends CameraView.Callback {

    private final Context mContext;
    private Handler mBackgroundHandler;


    public CameraCallback(Context context) {
        mContext = context;
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
        System.out.println("picture taken");
        getBackgroundHandler().post(new Runnable() {
            @Override
            public void run() {
                File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "picture.jpg");
                OutputStream os = null;
                try {
                    os = new FileOutputStream(file);
                    os.write(data);
                    os.close();
                } catch (IOException e) {
                    System.out.println("Cannot write to " + file);
                } finally {
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            // Ignore
                        }
                    }
                }
            }
        });

    }

    private Handler getBackgroundHandler() {
        if (mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
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
}
