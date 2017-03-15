package com.example.andresarango.aughunt.leaderboard;

import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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

    @BindView(R.id.user_pic) ImageView mUserPic;
    @BindView(R.id.user_profile_name)
    TextView mUserName;
    @BindView(R.id.user_points) TextView mUserPoints;
    @BindView(R.id.number_of_challenges) TextView mNumberOfCHallenges;

    public LeaderBoardViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(User user) {

        mUserName.setText(user.getProfileName());
        mUserPoints.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        mUserPoints.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorAccent));
        mUserPoints.setText(String.valueOf(user.getUserPoints()));
        mNumberOfCHallenges.setText("Challenges completed: " + String.valueOf(user.getNumberOfSubmittedChallenges()));
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

    }
}
