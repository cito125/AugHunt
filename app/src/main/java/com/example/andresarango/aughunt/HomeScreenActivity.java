package com.example.andresarango.aughunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeScreenActivity extends AppCompatActivity implements ViewGroup.OnClickListener {


    @BindView(R.id.search_for_challenge) Button mSearchForChall;
    @BindView(R.id.create_challenge) Button mCreatChall;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);

        //retrieveUserFromFirebaseAndSetProfile();

        mSearchForChall.setOnClickListener(this);
        mCreatChall.setOnClickListener(this);

    }

//    private void retrieveUserFromFirebaseAndSetProfile() {
//        rootRef.child("users").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//                System.out.println("USER: " + user.getProfileName());
//                mUserPointsTv.setText(user.getUserPoints() + " PTS");
//                mProfileNameTv.setText(user.getProfileName());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_for_challenge:
                // getSupportFragmentManager().beginTransaction().add(R.id.activity_home_screen,new SearchChallFragment()).commit();
                Intent intent = new Intent(getApplicationContext(), SearchChallengeActivity.class);
                startActivity(intent);
                break;
            case R.id.create_challenge:
                Intent otherIntent = new Intent(getApplicationContext(), CreateChallengeActivity.class);
                startActivity(otherIntent);
                break;
        }
    }
}
