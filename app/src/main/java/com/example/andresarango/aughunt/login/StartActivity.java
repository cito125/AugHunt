package com.example.andresarango.aughunt.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.andresarango.aughunt.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_start, new LoginFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_start, new LoginFragment())
                .commit();
    }

}
