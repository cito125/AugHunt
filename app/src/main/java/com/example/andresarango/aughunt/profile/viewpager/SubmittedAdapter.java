package com.example.andresarango.aughunt.profile.viewpager;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by dannylui on 3/12/17.
 */

class SubmittedAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SubmittedViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
