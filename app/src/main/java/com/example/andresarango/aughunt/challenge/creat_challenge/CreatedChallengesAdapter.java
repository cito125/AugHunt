package com.example.andresarango.aughunt.challenge.creat_challenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.homescreen.ChallengeReviewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Millochka on 3/5/17.
 */

public class CreatedChallengesAdapter extends RecyclerView.Adapter {

    private List<Challenge<Bitmap>> mChallenges=new ArrayList<>();
    private Context mContext;
    private ChallengeReviewHelper<Bitmap> mListener;

    public CreatedChallengesAdapter(List<Challenge<Bitmap>> challenges, Context context,ChallengeReviewHelper<Bitmap> listener ){
        this.mChallenges=challenges;
        this.mContext= context;
        this.mListener=listener;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.created_challenge,parent,false);

        return new CreatedChallengesViewHolder(view, mContext);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CreatedChallengesViewHolder createdChallengesViewHolder = (CreatedChallengesViewHolder) holder;
        createdChallengesViewHolder.bind(mChallenges.get(position));
        createdChallengesViewHolder.getmCreatedChallenge().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.passingChallange(mChallenges.get(position));


            }
        });

    }

    @Override
    public int getItemCount() {
        return mChallenges.size();
    }
}
