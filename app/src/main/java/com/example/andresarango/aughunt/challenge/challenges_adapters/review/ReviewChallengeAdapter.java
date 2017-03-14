package com.example.andresarango.aughunt.challenge.challenges_adapters.review;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.models.ChallengePhotoCompleted;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Millochka on 3/7/17.
 */

public class ReviewChallengeAdapter extends RecyclerView.Adapter<ReviewChallengeViewHolder> {

    private List<ChallengePhotoCompleted> mCompletedChallangesList = new ArrayList<>();
    private CompletedChallengeListener mListener;

    public ReviewChallengeAdapter(CompletedChallengeListener listener) {
        this.mListener = listener;
    }

    @Override
    public ReviewChallengeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_review_challenge, parent, false);

        return new ReviewChallengeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewChallengeViewHolder holder, int position) {
        final int viewHolderPosition = position;

        holder.bind(mCompletedChallangesList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onCompletedChallengeClicked(mCompletedChallangesList.get(viewHolderPosition));

            }
        });

    }

    @Override
    public int getItemCount() {
        return mCompletedChallangesList.size();
    }

    public void setCompletedChallangesList(List<ChallengePhotoCompleted> completedChallangesList) {
        mCompletedChallangesList.clear();
        mCompletedChallangesList.addAll(completedChallangesList);
        notifyDataSetChanged();
    }


    public void addChallengeToList(ChallengePhotoCompleted challenge) {
        mCompletedChallangesList.add(challenge);
        notifyItemInserted(mCompletedChallangesList.size() - 1);
    }
}
