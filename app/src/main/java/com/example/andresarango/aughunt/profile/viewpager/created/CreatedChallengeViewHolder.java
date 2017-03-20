package com.example.andresarango.aughunt.profile.viewpager.created;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.ChallengePhoto;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Millochka on 3/5/17.
 */

public class CreatedChallengeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_created_challenge_image) ImageView mChallengeImageIv;
    @BindView(R.id.tv_created_challenge_players) TextView mPursuingTv;
    @BindView(R.id.tv_created_challenge_submissions) TextView mSubmission;

    @BindView(R.id.tv_pending_review) TextView mPendingReviewTv;

    @BindView(R.id.ar_object) ImageView arObjectIv;

    public CreatedChallengeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(ChallengePhoto challenge) {
        mPendingReviewTv.setVisibility(View.INVISIBLE);
        Glide.with(itemView.getContext()).load(challenge.getPhotoUrl()).into(mChallengeImageIv);

        mSubmission.setText(String.valueOf(challenge.getCompleted()));
        mPursuingTv.setText(String.valueOf(challenge.getPursuing()));

        if (challenge.getPendingReviews() > 0) {
            mPendingReviewTv.setVisibility(View.VISIBLE);
            mPendingReviewTv.setText(String.valueOf(challenge.getPendingReviews()));
        }

        if (challenge.getArObjectStr().equals("kitten")) {

        }
    }

}
