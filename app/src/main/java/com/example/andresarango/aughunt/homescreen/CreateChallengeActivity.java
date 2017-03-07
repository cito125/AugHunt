package com.example.andresarango.aughunt.homescreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.challenge.Challenge;
import com.example.andresarango.aughunt.challenge.ChallengeTemplateActivity;
import com.example.andresarango.aughunt.challenge.CompletedChallenges;
import com.example.andresarango.aughunt.challenge.FirebaseEmulator;

import java.util.ArrayList;
import java.util.List;


public class CreateChallengeActivity extends AppCompatActivity implements ViewGroup.OnClickListener, ChallengeReviewHelper<Bitmap>{

    private Button mCreateChallenge;
    private RecyclerView mRecyclerView;
    private List<Challenge<Bitmap>> mAllChallenges;
    private FirebaseEmulator mFirebaseEmulator;
    private List<Challenge<Bitmap>> mCurrentUserChallenges;
    private ChallangeReviewFragment mChallangeReviewFragment;
    private Boolean mIsInflated;
    private ReviewFragment mReviewFragment;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);
        mCreateChallenge=(Button) findViewById(R.id.new_challenge);
        mCreateChallenge.setOnClickListener(this);
        mCurrentUserChallenges=new ArrayList<>();
        mIsInflated=false;
        initFireBase();
        initRecyclerView();

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(), ChallengeTemplateActivity.class);
        startActivity(intent);


    }

    public void initRecyclerView(){
        mRecyclerView=(RecyclerView) findViewById(R.id.created_challenges);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        HistoryOfCreatedChalalnges(mAllChallenges,mFirebaseEmulator.getCurrentUser().getUserId());
        mRecyclerView.setAdapter(new CreatedChallengesAdapter(mCurrentUserChallenges,getApplicationContext(),this));

    }

    public void HistoryOfCreatedChalalnges(List<Challenge<Bitmap>> challenges, String ownerID){


        for(Challenge<Bitmap> challenge:challenges){
            if(challenge.getmOwnerId().equalsIgnoreCase(ownerID)){
                mCurrentUserChallenges.add(challenge);
            }

        }

    }

 void initFireBase(){
     mFirebaseEmulator=new FirebaseEmulator(this);

     mAllChallenges=new ArrayList<>();
     mAllChallenges= mFirebaseEmulator.getChallenges();

 }


    @Override
    public void passingChallange(Challenge<Bitmap> c) {
        mChallangeReviewFragment = new ChallangeReviewFragment();
        mChallangeReviewFragment .setmCgallengeToReview(c);
        mChallangeReviewFragment .setmContext(getApplicationContext());
        mChallangeReviewFragment.setmListener(this);
        mIsInflated=!mIsInflated;

        getSupportFragmentManager().beginTransaction().add(R.id.container_for_review,mChallangeReviewFragment ).commit();
    }

    @Override
    public void passingCompletedChallange(CompletedChallenges<Bitmap> cc, Challenge<Bitmap> c) {

        mReviewFragment= new ReviewFragment();
        mReviewFragment.setmCompletedChallenge(cc);
        mReviewFragment.setmCurrentChallenge(c);
        mReviewFragment.setmContext(this);
        getSupportFragmentManager().beginTransaction().add(R.id.container_for_review,mReviewFragment ).commit();
    }


    @Override
    public void onBackPressed() {

  if(mIsInflated){
      getSupportFragmentManager().beginTransaction().remove(mChallangeReviewFragment ).commit();
      mIsInflated=!mIsInflated;

  }else {
            super.onBackPressed();}

    }

    public void reviewResult(View view){

        switch (view.getId()){

            case R.id.decline:

                getSupportFragmentManager().beginTransaction().remove(mReviewFragment ).commit();

                break;
            case R.id.accept:
                getSupportFragmentManager().beginTransaction().remove(mReviewFragment ).commit();
            break;

        }


    }
}
