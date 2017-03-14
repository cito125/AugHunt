package com.example.andresarango.aughunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Millochka on 3/14/17.
 */

public class LeaderBoardActivity extends AppCompatActivity{

    @BindView(R.id.bottom_navigation) BottomNavigationView mBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        ButterKnife.bind(this);
        setupBottomNavigation();
//        requestPermission();
//        retrieveUserFromFirebaseAndSetProfile();
//        setupBottomNavigation();
    }

    private void setupBottomNavigation() {

        mBottomNav.getMenu().getItem(3).setChecked(false);// Search item
        mBottomNav.getMenu().getItem(2).setChecked(true);// Leaderboard
        mBottomNav.getMenu().getItem(1).setChecked(false); // Create item
        mBottomNav.getMenu().getItem(0).setChecked(false); // Profile item

        BottomNavigationViewHelper.disableShiftMode(mBottomNav);


        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.create_challenge:
                        Intent createChallenge = new Intent(getApplicationContext(), ChallengeTemplateActivity.class);
                        startActivity(createChallenge);
                        break;
                    case R.id.user_profile:
                        Intent userProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(userProfile);
                        break;
                    case R.id.search_challenge:
                        Intent searchChallenge = new Intent(getApplicationContext(), SearchChallengeActivity.class);
                        startActivity(searchChallenge);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBottomNav.getMenu().getItem(3).setChecked(false);// Search item
        mBottomNav.getMenu().getItem(2).setChecked(true);// Leaderboard
        mBottomNav.getMenu().getItem(1).setChecked(false); // Create item
        mBottomNav.getMenu().getItem(0).setChecked(false); // Profile item
    }
}
