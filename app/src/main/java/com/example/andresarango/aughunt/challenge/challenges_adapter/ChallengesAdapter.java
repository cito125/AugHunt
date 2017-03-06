package com.example.andresarango.aughunt.challenge.challenges_adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.challenge_dialog_fragment.DialogFragmentListener;

import java.util.ArrayList;
import java.util.List;

public class ChallengesAdapter<T> extends RecyclerView.Adapter<ChallengeViewholder<T>> {
    List<Challenge<T>> mChallengeList = new ArrayList<>();
    private final DialogFragmentListener mDialogFragmentListener;

    public ChallengesAdapter(DialogFragmentListener dialogFragmentListener) {
        mDialogFragmentListener = dialogFragmentListener;
    }

    @Override
    public ChallengeViewholder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChallengeViewholder<>(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.viewholder_challenge, parent, false));
    }

    public void setChallengeList(List<Challenge<T>> challengeList) {
        mChallengeList.clear();
        mChallengeList.addAll(challengeList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ChallengeViewholder<T> holder, int position) {
        holder.bind(mChallengeList.get(position),mDialogFragmentListener);
    }

    @Override
    public int getItemCount() {
        return mChallengeList.size();
    }
}
