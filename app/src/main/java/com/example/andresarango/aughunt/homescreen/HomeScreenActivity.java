package com.example.andresarango.aughunt.homescreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.andresarango.aughunt.R;

public class HomeScreenActivity extends AppCompatActivity implements ViewGroup.OnClickListener {

    private Button mSearchForChall;
    private Button mCreatChall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mSearchForChall = (Button) findViewById(R.id.search_for_challenge);
        mSearchForChall.setOnClickListener(this);
        mCreatChall = (Button) findViewById(R.id.create_challenge);
        mCreatChall.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.search_for_challenge:

                // getSupportFragmentManager().beginTransaction().add(R.id.activity_home_screen,new SearchChallFragment()).commit();
                Intent intent = new Intent(getApplicationContext(), SearchChallengeActivity.class);
                startActivity(intent);
                break;
            case R.id.create_challenge:
                Intent otherIntent = new Intent(getApplicationContext(), CreateChallengeActivity.class);
                startActivity(otherIntent);
                break;
        }
    }
}
