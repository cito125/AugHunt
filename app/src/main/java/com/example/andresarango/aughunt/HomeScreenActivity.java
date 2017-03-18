package com.example.andresarango.aughunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.andresarango.aughunt.create.CreateChallengeCameraActivity;
import com.example.andresarango.aughunt.create.UnityCreateChallengeActivity;
import com.example.andresarango.aughunt.leaderboard.LeaderBoardFragment;
import com.example.andresarango.aughunt.profile.ProfileFragment;
import com.example.andresarango.aughunt.search.SearchChallengeFragment;
import com.example.andresarango.aughunt.util.bottom_nav_helper.BottomNavigationViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Danny on 3/16/2017.
 */

public class HomeScreenActivity extends AppCompatActivity {
    @BindView(R.id.bottom_navigation) BottomNavigationView mBottomNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_screen_container, new SearchChallengeFragment())
                .commit();

        setupBottomNav();
    }

    private void setupBottomNav() {
        setBottomNavFocusSearch();
        BottomNavigationViewHelper.disableShiftMode(mBottomNav);

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search_challenge:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_screen_container, new SearchChallengeFragment())
                                .commit();
                        break;
                    case R.id.create_challenge:
//                        Intent intent = new Intent(HomeScreenActivity.this, CreateChallengeCameraActivity.class);
                        Intent intent = new Intent(HomeScreenActivity.this, UnityCreateChallengeActivity.class);
                        startActivityForResult(intent, 1234);
                        break;
                    case R.id.user_profile:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_screen_container, new ProfileFragment())
                                .commit();
                        break;
                    case R.id.leaderboard:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_screen_container, new LeaderBoardFragment())
                                .commit();
                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // After opening up camera activity and taking a picture, it'll open up the created tab in profile
        switch (resultCode) {
            case 1234:
                startProfileCreatedTab();
                break;
        }
    }

    public void startProfileCreatedTab() {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ProfileFragment.VIEWPAGER_START_POSITION, 1);
        profileFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_screen_container, profileFragment)
                .commit();
    }

    public void setBottomNavFocusSearch() {
        mBottomNav.getMenu().getItem(3).setChecked(true);// Search item
        mBottomNav.getMenu().getItem(2).setChecked(false);// Leaderboard
        mBottomNav.getMenu().getItem(1).setChecked(false); // Create item
        mBottomNav.getMenu().getItem(0).setChecked(false); // Profile item
    }

    public void setBottomNavFocusLeaderBoard() {
        mBottomNav.getMenu().getItem(1).setChecked(false);
        mBottomNav.getMenu().getItem(0).setChecked(false);
        mBottomNav.getMenu().getItem(3).setChecked(true);
        mBottomNav.getMenu().getItem(2).setChecked(false);
    }

    public void setBottomNavFocusProfile() {
        mBottomNav.getMenu().getItem(0).setChecked(true); // Profile
        mBottomNav.getMenu().getItem(1).setChecked(false);
        mBottomNav.getMenu().getItem(2).setChecked(false);
        mBottomNav.getMenu().getItem(3).setChecked(false);
    }
}
