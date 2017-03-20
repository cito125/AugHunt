package com.example.andresarango.aughunt.create;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dannylui on 3/18/17.
 */

public class PictureTakenActivity extends AppCompatActivity {
    @BindView(R.id.iv_edit_photo)
    ImageView photoIv;

    public static final String DESTINATION_KEY = "YOMAMA";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_taken);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        if (intent.hasExtra(DESTINATION_KEY)) {
            String filePath = intent.getStringExtra(DESTINATION_KEY);
            photoIv.setImageBitmap(BitmapFactory.decodeFile(filePath));
        }


    }
}
