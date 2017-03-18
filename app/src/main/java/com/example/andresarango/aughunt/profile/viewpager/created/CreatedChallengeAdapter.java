package com.example.andresarango.aughunt.profile.viewpager.created;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.ChallengePhoto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Millochka on 3/5/17.
 */

public class CreatedChallengeAdapter extends RecyclerView.Adapter<CreatedChallengeViewHolder> {

    private List<ChallengePhoto> mChallengeList = new ArrayList<>();
    private CreatedChallengeListener mListener;

    public CreatedChallengeAdapter(CreatedChallengeListener listener) {
        this.mListener = listener;
    }

    @Override
    public CreatedChallengeViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_challenge_created, parent, false);
        return new CreatedChallengeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CreatedChallengeViewHolder holder, int position) {
        final int viewHolderPosition = position;

        holder.bind(mChallengeList.get(viewHolderPosition));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCreatedChallengeClicked(mChallengeList.get(viewHolderPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChallengeList.size();
    }

    public void setChallengeList(List<ChallengePhoto> challengeList) {

        Comparator<ChallengePhoto> pendingComparator = new Comparator<ChallengePhoto>() {
            @Override
            public int compare(ChallengePhoto challengePhotoOne, ChallengePhoto challengePhotoTwo) {
                if (challengePhotoTwo.getPendingReviews() != challengePhotoOne.getPendingReviews()) {
                    return Integer.valueOf(challengePhotoTwo.getPendingReviews()).compareTo(challengePhotoOne.getPendingReviews());
                } else {
                    return Long.valueOf(challengePhotoTwo.getTimestamp()).compareTo(challengePhotoOne.getTimestamp());
                }
            }
        };

        Collections.sort(challengeList, pendingComparator);

        this.mChallengeList.clear();
        this.mChallengeList.addAll(challengeList);

        notifyDataSetChanged();
    }

    public void addChallengeToList(final ChallengePhoto challenge) {
        mChallengeList.add(challenge);
        Comparator<ChallengePhoto> pendingComparator = new Comparator<ChallengePhoto>() {
            @Override
            public int compare(ChallengePhoto challengePhotoOne, ChallengePhoto challengePhotoTwo) {
                if (challengePhotoTwo.getPendingReviews() != challengePhotoOne.getPendingReviews()) {
                    return Integer.valueOf(challengePhotoTwo.getPendingReviews()).compareTo(challengePhotoOne.getPendingReviews());
                } else {
                    return Long.valueOf(challengePhotoTwo.getTimestamp()).compareTo(challengePhotoOne.getTimestamp());
                }
            }
        };

        Collections.sort(mChallengeList, pendingComparator);


        notifyDataSetChanged();


    }
}
