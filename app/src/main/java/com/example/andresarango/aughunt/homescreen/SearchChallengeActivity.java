package com.example.andresarango.aughunt.homescreen;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andresarango.aughunt.R;

public class SearchChallengeActivity extends AppCompatActivity {

    private ImageView mChallImage;
    private static final String IMAGE_DATA ="image_data" ;
    private static final String HINT_DATA ="hint_data" ;
    private TextView mHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_list);
        mChallImage = (ImageView) findViewById(R.id.existing_challenge);
        mHint=(TextView) findViewById(R.id.challenge_hint);


    }

    @Override
    protected void onStart() {
        super.onStart();
      Bitmap image=retrieveFrSh();
        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
        String hint = shre.getString(HINT_DATA, "");

        Drawable d = new BitmapDrawable(getResources(), image);
        mChallImage.setImageDrawable(d);
        mHint.setText(hint);

    }

    public Bitmap retrieveFrSh(){


        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
        String previouslyEncodedImage = shre.getString(IMAGE_DATA, "");

        if( !previouslyEncodedImage.equalsIgnoreCase("") ){
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
          return bitmap;
        }
        return null;
    }

}
