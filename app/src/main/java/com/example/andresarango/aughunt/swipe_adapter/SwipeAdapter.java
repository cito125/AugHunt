package com.example.andresarango.aughunt.swipe_adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class SwipeAdapter<T, VH extends SwipeAdapter.ViewHolder<T>>
        extends BaseAdapter {

    @Override
    public Integer getItem(int position) {
        return position;
    }

    public long getItemType(int position) {
        return 0;
    }

    public abstract VH onCreateViewholder(int position, ViewHolder viewHolder, ViewGroup viewGroup);

    public static abstract class ViewHolder<T> extends View {
        public ViewHolder(Context context) {
            super(context);
        }

        public abstract ViewHolder onBindViewholder(T object);
    }
}
