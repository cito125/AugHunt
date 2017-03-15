package com.example.andresarango.aughunt.challenge.challenges_adapters.swipe_review;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.models.ChallengePhotoCompleted;

import java.util.ArrayList;
import java.util.List;

public class ReviewSwipeAdapter extends BaseAdapter {


    private List<ChallengePhotoCompleted> mCompletedChallengeList = new ArrayList<>();

    @Override
    public int getCount() {
        return mCompletedChallengeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCompletedChallengeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ChallengePhotoCompleted completedChallenge = mCompletedChallengeList.get(position);

        View view = convertView;
        if (view == null) {
            view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.vh_review_challenge_swipe, parent, false);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.offer_image);
        Glide.with(view.getContext()).load(completedChallenge.getPhotoUrl()).fitCenter().into(imageView);

//        TextView textView = (TextView) view.findViewById(R.id.sample_text);
//        textView.setText(completedChallenge.getPlayerId());
        return view;
    }

    public void setCompletedChallengeList(List<ChallengePhotoCompleted> completedChallengelist) {
        mCompletedChallengeList.clear();
        mCompletedChallengeList.addAll(completedChallengelist);
        notifyDataSetChanged();
    }


    public void addChallenge(ChallengePhotoCompleted challenge) {
        mCompletedChallengeList.add(challenge);
        notifyDataSetChanged();
    }
}
