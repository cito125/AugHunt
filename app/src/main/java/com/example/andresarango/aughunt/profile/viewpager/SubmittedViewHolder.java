package com.example.andresarango.aughunt.profile.viewpager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.R;

/**
 * Created by dannylui on 3/12/17.
 */

class SubmittedViewHolder extends RecyclerView.ViewHolder {


    public SubmittedViewHolder(ViewGroup parent) {
        super(inflate(parent));
    }

    private static View inflate(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_submitted_challenge, parent, false);
        return view;
    }


}
