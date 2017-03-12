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

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public long getItemType(int position) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return onCreateViewholder(i,(VH) view,viewGroup);
    }

    public abstract VH onCreateViewholder(int position, VH viewHolder, ViewGroup viewGroup);

    public static abstract class ViewHolder<T> extends View {
        public ViewHolder(View view) {
            super(view.getContext());
        }

        public abstract ViewHolder onBindViewholder(T object);
    }
}
