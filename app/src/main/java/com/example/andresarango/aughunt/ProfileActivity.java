package com.example.andresarango.aughunt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.andresarango.aughunt.challenge.challenge_review_fragments.CreatedChallengesFragment;
import com.example.andresarango.aughunt.challenge.challenge_review_fragments.PopFragmentListener;
import com.example.andresarango.aughunt.challenge.challenge_review_fragments.ReviewChallengesFragment;
import com.example.andresarango.aughunt.challenge.challenges_adapters.created.CreatedChallengeListener;
import com.example.andresarango.aughunt.models.ChallengePhoto;
import com.example.andresarango.aughunt.models.User;
import com.example.andresarango.aughunt.profile.SubmittedChallengeFragment;
import com.example.andresarango.aughunt.profile.ViewPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dannylui on 3/11/17.
 */

public class ProfileActivity extends AppCompatActivity implements CreatedChallengeListener, PopFragmentListener {
    @BindView(R.id.tab_layout) TabLayout tablayout;
    @BindView(R.id.viewpager) ViewPager pager;
    @BindView(R.id.bottom_navigation) BottomNavigationView mBottomNav;
    @BindView(R.id.iv_main_profile_pic) ImageView profilePicIv;
    @BindView(R.id.tv_main_profile_points) TextView userPointsTv;
    @BindView(R.id.tv_main_profile_total_created) TextView totalCreatedChallengesTv;
    @BindView(R.id.tv_main_profile_total_submitted) TextView totalSubmittedChallengesTv;


    private SubmittedChallengeFragment mSubmittedChallengesFragment;
    private CreatedChallengesFragment mCreatedChallengesFragment;
    private ReviewChallengesFragment mReviewChallengesFragment;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setupTabLayout(tablayout);
        setupViewPager(pager);
        tablayout.setupWithViewPager(pager);

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.create_challenge:
                        Intent createChallenge = new Intent(getApplicationContext(), ChallengeTemplateActivity.class);
                        startActivity(createChallenge);

                        break;
                    case R.id.search_challenge:
                        Intent homePage = new Intent(getApplicationContext(), SearchChallengeActivity.class);
                        startActivity(homePage);

                        break;
                    case R.id.user_profile:
                        item.setEnabled(true);
                        Intent userProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(userProfile);
                        break;
                }
                return true;
            }
        });

        Glide.with(getApplicationContext())
                .load("http://clipart-library.com/images/rcLojMEni.jpg")
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(profilePicIv) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        profilePicIv.setImageDrawable(circularBitmapDrawable);
                    }
                });

        setupProfileStatusBar();

    }

    private void setupProfileStatusBar() {
        rootRef.child("users").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);


                userPointsTv.setText(String.valueOf(currentUser.getUserPoints()));
                totalCreatedChallengesTv.setText(String.valueOf(currentUser.getNumberOfCreatedChallenges()));
                totalSubmittedChallengesTv.setText(String.valueOf(currentUser.getNumberOfSubmittedChallenges()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void setupViewPager(ViewPager pager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        mCreatedChallengesFragment = new CreatedChallengesFragment();
        mCreatedChallengesFragment.setListener(this);

        mSubmittedChallengesFragment = new SubmittedChallengeFragment();

        adapter.addFragment(mCreatedChallengesFragment, "Created");
        adapter.addFragment(mSubmittedChallengesFragment, "Submitted");
        pager.setAdapter(adapter);
    }

    private void setupTabLayout(TabLayout tablayout) {
        tablayout.setTabTextColors(0xFFFFFFFF, 0xFFFFFFFF);
        tablayout.setSelectedTabIndicatorColor(0xFFFFFFFF);
    }


    @Override
    public void onCreatedChallengeClicked(ChallengePhoto challenge) {
        startReviewChallengeFragment(challenge);
    }

    private void startReviewChallengeFragment(ChallengePhoto challenge) {
        mReviewChallengesFragment = new ReviewChallengesFragment();
        mReviewChallengesFragment.setChallengeToReview(challenge);
        mReviewChallengesFragment.setPopFragmentListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.profile_activity, mReviewChallengesFragment)
                .addToBackStack(null)
                .commit();
    }

    public void popFragmentFromBackStack(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .remove(fragment)
                .commit();
    }

    @Override
    public void popFragment(Fragment fragment) {
        popFragmentFromBackStack(fragment);
    }
}

