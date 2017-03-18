package com.example.andresarango.aughunt.leaderboard;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Millochka on 3/14/17.
 */

class LeaderBoardViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_leader_profile_pic) ImageView mUserPic;
    @BindView(R.id.tv_leader_profile_name) TextView mUserName;
    @BindView(R.id.tv_leader_user_points) TextView mUserPoints;
    @BindView(R.id.tv_leader_completed_challenges) TextView mNumberOfSubmitted;
    @BindView(R.id.tv_leader_created_challenges) TextView mNumberOfCreated;
    @BindView(R.id.tv_leader_reviewed_challenges) TextView mNumberOfReviewed;

    public LeaderBoardViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(User user) {

        Glide.with(itemView.getContext())
                .load("http://clipart-library.com/images/rcLojMEni.jpg")
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(mUserPic) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(itemView.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        mUserPic.setImageDrawable(circularBitmapDrawable);
                    }
                });

        mUserName.setText(user.getProfileName());



        int points = user.getUserPoints() / 100;
        mUserPoints.setText(String.valueOf(points));

        mNumberOfSubmitted.setText(String.valueOf(user.getNumberOfSubmittedChallenges()));
        mNumberOfCreated.setText(String.valueOf(user.getNumberOfCreatedChallenges()));
        mNumberOfReviewed.setText(String.valueOf(user.getNumberOfReviewedChallenges()));


    }
}
