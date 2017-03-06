package com.example.andresarango.aughunt.homescreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.ChallengeTemplateActivity;

public class CreateChallengeActivity extends AppCompatActivity implements ViewGroup.OnClickListener{

    Button mCreateChall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);
        mCreateChall=(Button) findViewById(R.id.new_challenge);
        mCreateChall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(), ChallengeTemplateActivity.class);
        startActivity(intent);
        //getSupportFragmentManager().beginTransaction().add(R.id.activity_create_challenge, new ChallengeTemplateFragment()).commit();

    }
}
