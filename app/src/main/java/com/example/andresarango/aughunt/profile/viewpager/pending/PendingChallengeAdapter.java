package com.example.andresarango.aughunt.profile.viewpager.pending;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.ChallengePhoto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danny on 3/16/2017.
 */

class PendingChallengeAdapter extends RecyclerView.Adapter<PendingChallengeHolder> {
    private List<ChallengePhoto> mChallengeList = new ArrayList<>();
    private PendingChallengeListener mListener;

    public PendingChallengeAdapter(PendingChallengeListener listener) {
        this.mListener = listener;
    }

    @Override
    public PendingChallengeHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_challenge_pending, parent, false);
        return new PendingChallengeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PendingChallengeHolder holder, int position) {
        final int pos = position;
        holder.bind(mChallengeList.get(pos));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPendingChallengeClicked(mChallengeList.get(pos));
            }
        });
    }


    @Override
    public int getItemCount() {
        return mChallengeList.size();
    }

    public void setChallengeList(List<ChallengePhoto> challengeList) {
        this.mChallengeList.clear();
        this.mChallengeList.addAll(challengeList);
        notifyDataSetChanged();
    }
}
