package com.example.andresarango.aughunt.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.andresarango.aughunt.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("SPLASHING WAITING... 5s");
                startActivity(new Intent(SplashScreen.this, StartActivity.class));
                finish();
            }
        }, 5000);
    }
}
