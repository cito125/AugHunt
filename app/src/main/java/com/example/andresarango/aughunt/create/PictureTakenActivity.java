package com.example.andresarango.aughunt.create;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.R;

import butterknife.BindView;

/**
 * Created by dannylui on 3/18/17.
 */

public class PictureTakenActivity extends AppCompatActivity {
    @BindView(R.id.iv_edit_photo)
    ImageView photoIv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_taken);

        byte[] data = getIntent().getByteArrayExtra("CAMERA_PICTURE");

        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        Glide.with(this).load(bitmap).into(photoIv);

    }
}
