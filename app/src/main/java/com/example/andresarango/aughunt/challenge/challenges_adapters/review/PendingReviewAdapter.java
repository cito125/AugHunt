package com.example.andresarango.aughunt.challenge.challenges_adapters.review;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.ChallengePhoto;
import com.example.andresarango.aughunt.challenge.challenges_adapters.created.CreatedChallengeListener;
import com.example.andresarango.aughunt.challenge.challenges_adapters.created.CreatedChallengeViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Millochka on 3/12/17.
 */

public class PendingReviewAdapter extends RecyclerView.Adapter <CreatedChallengeViewHolder>{

    private List<ChallengePhoto> mChallengeList = new ArrayList<>();
    private CreatedChallengeListener mListener;

    public PendingReviewAdapter(CreatedChallengeListener listener) {
        this.mListener = listener;
    }
    @Override
    public CreatedChallengeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_created_challenge, parent, false);
        return new CreatedChallengeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CreatedChallengeViewHolder holder, int position) {

        final int viewHolderPosition = position;

        holder.bind(mChallengeList.get(viewHolderPosition));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCreatedChallengeClicked(mChallengeList.get(viewHolderPosition));
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

    public void addChallengeToList(ChallengePhoto challenge) {
        mChallengeList.add(challenge);
        notifyItemInserted(mChallengeList.size() - 1);
    }
}
