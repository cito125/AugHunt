package com.example.andresarango.aughunt.challenge.challenges_adapters.swipe_review;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.swipe_adapter.SwipeAdapter;

public class ReviewSwipeAdapter extends SwipeAdapter<Integer, ReviewViewHolder<Integer>> {


    @Override
    public int getCount() {
        return  Integer.MAX_VALUE;
    }


    @Override
    public ReviewViewHolder<Integer> onCreateViewholder(int position, ReviewViewHolder<Integer> viewHolder, ViewGroup viewGroup) {
        if(viewHolder == null){
            viewHolder = new ReviewViewHolder<>(
                    LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.vh_review_challenge_swipe,viewGroup,false
                            )
            );
        }

        return viewHolder.onBindViewholder(position);
    }
}
