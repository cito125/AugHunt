package com.example.andresarango.aughunt.challenge.challenges_adapters.nearby;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.SearchChallengeHelper;
import com.example.andresarango.aughunt.challenge.ChallengePhoto;
import com.example.andresarango.aughunt.challenge.challenges_adapters.created.CreatedChallengeListener;

import java.util.ArrayList;
import java.util.List;

public class ChallengesAdapter extends RecyclerView.Adapter<ChallengeViewholder> {
    List<ChallengePhoto> mChallengeList = new ArrayList<>();
    private final SearchChallengeHelper mChallengeViewholderListener;

    public ChallengesAdapter( SearchChallengeHelper challengeViewholderListener) {
        mChallengeViewholderListener = challengeViewholderListener;
    }

    @Override
    public ChallengeViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChallengeViewholder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.vh_searched_challenge, parent, false),
                mChallengeViewholderListener);
    }

    public void setChallengeList(List<ChallengePhoto> challengeList) {
        mChallengeList.clear();
        mChallengeList.addAll(challengeList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ChallengeViewholder holder, int position) {
        holder.getUserName(mChallengeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mChallengeList.size();
    }
}
