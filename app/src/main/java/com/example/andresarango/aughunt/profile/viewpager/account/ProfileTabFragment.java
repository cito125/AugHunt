package com.example.andresarango.aughunt.profile.viewpager.account;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Danny on 3/16/2017.
 */

public class ProfileTabFragment extends Fragment {
    @BindView(R.id.iv_main_profile_pic) ImageView profilePicIv;
    @BindView(R.id.tv_main_profile_name) TextView profileNameTv;
    @BindView(R.id.tv_main_profile_points) TextView userPointsTv;
    @BindView(R.id.tv_main_profile_total_created) TextView totalCreatedChallengesTv;
    @BindView(R.id.tv_main_profile_total_submitted) TextView totalSubmittedChallengesTv;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        setupProfileStatusBar();

        Glide.with(getContext())
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
    }

    private void setupProfileStatusBar() {
        rootRef.child("users").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                profileNameTv.setText(currentUser.getProfileName());
                userPointsTv.setText(String.valueOf(currentUser.getUserPoints()));
                totalCreatedChallengesTv.setText(String.valueOf(currentUser.getNumberOfCreatedChallenges()));
                totalSubmittedChallengesTv.setText(String.valueOf(currentUser.getNumberOfSubmittedChallenges()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
