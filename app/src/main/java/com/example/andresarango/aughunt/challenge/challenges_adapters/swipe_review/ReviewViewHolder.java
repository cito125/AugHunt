package com.example.andresarango.aughunt.challenge.challenges_adapters.swipe_review;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.swipe_adapter.SwipeAdapter;

class ReviewViewHolder<T> extends SwipeAdapter.ViewHolder<Integer> {

    private final TextView mTextView;

    public ReviewViewHolder(View view) {
        super(view);
        mTextView = (TextView) view.findViewById(R.id.textview_review_challenge);
    }

    @Override
    public ReviewViewHolder<T> onBindViewholder(Integer object) {
        mTextView.setText(object.toString());
        return this;
    }


}
