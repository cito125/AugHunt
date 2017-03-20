package com.example.andresarango.aughunt.create;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.andresarango.aughunt.HomeScreenActivity;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.ChallengePhoto;
import com.example.andresarango.aughunt._models.DAMLocation;
import com.example.andresarango.aughunt.util.challenge_dialog_fragment.ChallengeDialogFragment;
import com.example.andresarango.aughunt.util.snapshot_callback.SnapshotHelper;
import com.google.android.gms.awareness.snapshot.LocationResult;
import com.unity3d.player.*;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnityPlayerActivity extends AppCompatActivity implements SnapshotHelper.SnapshotListener{
    @BindView(R.id.btn_cat_image) Button catImageBtn;
    @BindView(R.id.btn_camera_image) Button cameraBtn;
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

    public static String GIMME;
    public static ChallengePhoto mChallengePhoto;
    public static boolean hasSubmitted = false;

    // Setup activity layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

        mUnityPlayer = new UnityPlayer(this);
        setContentView(R.layout.unity_player_activity);
        FrameLayout unityFrame = (FrameLayout) findViewById(R.id.unity_frame);
        unityFrame.addView(mUnityPlayer);
        mUnityPlayer.requestFocus();

        // New Code starts here
        ButterKnife.bind(this);

        mChallengePhoto = (ChallengePhoto) getIntent().getSerializableExtra(ChallengeDialogFragment.CHALLENGE);
        mUnityPlayer.UnitySendMessage("3DCanvas", "deactivate", "swag");

        cameraBtn.setEnabled(false);

        catImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnapshotHelper snapshotHelper = new SnapshotHelper(UnityPlayerActivity.this);
                snapshotHelper.runSnapshot(getApplicationContext());
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String strToSend = deserializeChallengePhoto();
//                strToSend += PictureTakenActivity.DESTINATION_KEY;
//                PictureTakenActivity.DESTINATION_KEY = strToSend;
//                System.out.println(strToSend);
                mUnityPlayer.UnitySendMessage("Main Camera", "takeScreenShotAndShare", PictureTakenActivity.DESTINATION_KEY);
            }
        });

//        Button activateButton = (Button) findViewById(R.id.activate_button);
//        Button deactivateButton = (Button) findViewById(R.id.deactivate_button);
//        Button photoButton = (Button) findViewById(R.id.photo_button);



//        activateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mUnityPlayer.UnitySendMessage("3DCanvas", "activate", "swag");
//            }
//        });
//
//        deactivateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mUnityPlayer.UnitySendMessage("3DCanvas", "deactivate", "swag");
//            }
//        });
//
//        photoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath());
//                mUnityPlayer.UnitySendMessage("Main Camera", "takeScreenShotAndShare", PictureTakenActivity.DESTINATION_KEY);
//            }
//        });



    }

    public String deserializeChallengePhoto() {
        String result = "";
        result += mChallengePhoto.getChallengeId() + " ";
        result += mChallengePhoto.getHint() + " ";
        result += mChallengePhoto.getOwnerId() + " ";
        result += mChallengePhoto.getPhotoUrl() + " ";
        result += mChallengePhoto.getLocation().getLat() + " ";
        result += mChallengePhoto.getLocation().getLng() + " ";
        result += mChallengePhoto.getTimestamp() + " ";
        result += mChallengePhoto.getArObjectStr() + " ";


        return result;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // To support deep linking, we need to make sure that the client can get access to
        // the last sent intent. The clients access this through a JNI api that allows them
        // to get the intent set on launch. To update that after launch we have to manually
        // replace the intent with the one caught here.
        setIntent(intent);
    }

    // Quit Unity
    @Override
    protected void onDestroy() {
        mUnityPlayer.quit();
        super.onDestroy();
    }

    // Pause Unity
    @Override
    protected void onPause() {
        super.onPause();
//        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Called on resume unity player");
        if (hasSubmitted) {
            Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
            startActivity(intent);
            onBackPressed();
        } else {
            mUnityPlayer.resume();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Called on start unity player");
    }

    // Low Memory Unity
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL) {
            mUnityPlayer.lowMemory();
        }
    }

    // This ensures the layout will be correct.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    /*API12*/
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public void run(LocationResult locationResult) {
        DAMLocation currentLocation = new DAMLocation(locationResult.getLocation().getLatitude(), locationResult.getLocation().getLongitude());
        if (currentLocation.isWithinRadius(mChallengePhoto.getLocation(), 100.0)) {
            Toast.makeText(getApplicationContext(), "Spotted a kitten, find and capture it!", Toast.LENGTH_SHORT).show();
            mUnityPlayer.UnitySendMessage("3DCanvas", "activate", "swag");
            catImageBtn.setEnabled(false);
            catImageBtn.setVisibility(View.INVISIBLE);
            cameraBtn.setEnabled(true);
            cameraBtn.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getApplicationContext(), "No kitten nearby, keep looking!", Toast.LENGTH_SHORT).show();
        }
    }
}
