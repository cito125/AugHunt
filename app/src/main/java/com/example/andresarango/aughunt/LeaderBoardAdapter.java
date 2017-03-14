package com.example.andresarango.aughunt;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.models.User;

import java.util.List;

/**
 * Created by Millochka on 3/14/17.
 */

class LeaderBoardAdapter extends RecyclerView.Adapter {

    private List<User> mUserList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public void setUserList(List<User> userList) {
        this.mUserList = userList;
    }

    public void addUserToList(User user) {
        mUserList.add(user);
        notifyItemInserted(mUserList.size() - 1);
    }
}
