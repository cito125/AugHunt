package com.example.andresarango.aughunt.profile.viewpager;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.challenge.ChallengePhotoSubmitted;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dannylui on 3/12/17.
 */

class SubmittedAdapter extends RecyclerView.Adapter<SubmittedViewHolder> {
    private List<ChallengePhotoSubmitted> submittedList = new ArrayList<>();

    @Override
    public SubmittedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SubmittedViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(SubmittedViewHolder holder, int position) {
        holder.bind(submittedList.get(position));
    }

    @Override
    public int getItemCount() {
        return submittedList.size();
    }

    public void setSubmittedList(List<ChallengePhotoSubmitted> submittedList) {
        this.submittedList.clear();
        this.submittedList.addAll(submittedList);
        notifyDataSetChanged();
    }
}
