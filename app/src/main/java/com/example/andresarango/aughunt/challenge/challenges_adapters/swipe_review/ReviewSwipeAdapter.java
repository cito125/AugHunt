package com.example.andresarango.aughunt.challenge.challenges_adapters.swipe_review;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.andresarango.aughunt.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewSwipeAdapter extends BaseAdapter {


    private List<String> mDataList = new ArrayList<>();

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.vh_review_challenge_swipe, parent, false);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.offer_image);
        Glide.with(view.getContext()).load(R.drawable.cat2).centerCrop().into(imageView);
        TextView textView = (TextView) view.findViewById(R.id.sample_text);
        String item = (String)getItem(position);
        textView.setText(item);
        return view;
    }

    public void setData(List<String> data) {
        mDataList.clear();
        mDataList.addAll(data);
        notifyDataSetChanged();
    }
}
