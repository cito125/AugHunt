package com.example.andresarango.aughunt.search;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andresarango.aughunt.HomeScreenActivity;
import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt._models.ChallengePhoto;
import com.example.andresarango.aughunt._models.DAMLocation;
import com.example.andresarango.aughunt._models.User;
import com.example.andresarango.aughunt.profile.ProfileFragment;
import com.example.andresarango.aughunt.util.challenge_dialog_fragment.ChallengeDialogFragment;
import com.example.andresarango.aughunt.util.snapshot_callback.SnapshotHelper;
import com.google.android.gms.awareness.snapshot.LocationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Danny on 3/16/2017.
 */

public class SearchChallengeFragment extends Fragment implements SnapshotHelper.SnapshotListener, SearchChallengeHelper {
    private View mRootView;
    @BindView(R.id.search_challenge_recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.tv_user_points) TextView mUserPointsTv;
    @BindView(R.id.review_number) TextView mPendingReview;
    @BindView(R.id.pending_review) TextView mPending;

    private static final int LOCATION_PERMISSION = 1245;

    private ChallengesAdapter mNearbyChallengesAdapter;

    private DAMLocation userLocation;
    private Double radius = 100.0;
    private int mPendingReviewIndicator = 0;

    private Map<String, ChallengePhoto> challengeMap = new HashMap<>();
    private List<ChallengePhoto> challengeList = new ArrayList<>();
    private Set<String> submittedChallengeSet = new HashSet<>();

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_challenges_searched, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        requestPermission();
        retrieveUserFromFirebaseAndSetProfile();

        mPendingReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPendingReview();
            }
        });

        mPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPendingReview();
            }
        });
    }

    private void getUserLocation() {
        SnapshotHelper snapshotHelper = new SnapshotHelper(this);
        snapshotHelper.runSnapshot(getContext());
    }

    @Override
    public void run(LocationResult locationResult) {
        userLocation = new DAMLocation(locationResult.getLocation().getLatitude(), locationResult.getLocation().getLongitude());
        System.out.println(userLocation.getLat() + " " + userLocation.getLng() + " <--- USER LOCATION");
        initialize();
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mNearbyChallengesAdapter = new ChallengesAdapter(this);
        mRecyclerView.setAdapter(mNearbyChallengesAdapter);
    }

    private void retrieveUserFromFirebaseAndSetProfile() {
        rootRef.child("users").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                System.out.println("USER: " + user.getProfileName());
                mUserPointsTv.setText(user.getUserPoints() + " PTS");
                mPendingReview.setTypeface(mPendingReview.getTypeface(), Typeface.BOLD);
                mPendingReview.setText(String.valueOf(mPendingReviewIndicator));
                mPending.setTypeface(mPending.getTypeface(), Typeface.BOLD);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initialize() {
        rootRef.child("challenges").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addChallengeToRecyclerView(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                updateRecyclerView(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //removeChallengeFromRecyclerView(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addChallengeToRecyclerView(DataSnapshot dataSnapshot) {
        ChallengePhoto challenge = dataSnapshot.getValue(ChallengePhoto.class);

        if (challenge.getOwnerId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            mPendingReviewIndicator += challenge.getPendingReviews();
            mPendingReview.setText(String.valueOf(mPendingReviewIndicator));
            if (mPendingReviewIndicator > 0) {
                mPendingReview.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.colorAccent)); // For some reason crashes with getContext but not if i use root view to get context
                mPending.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.colorAccent));
            } else {
                mPendingReview.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.lightGrey));
                mPending.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.lightGrey));
            }
        }

        System.out.println("CALLED GETTING LIST OF SUBMITTED CHALLENGES");
        getListOfSubmittedChallenges(dataSnapshot);
    }

    private void getListOfSubmittedChallenges(final DataSnapshot dataSnapshot) {
        final String challengeKey = dataSnapshot.getKey();
        final ChallengePhoto challenge = dataSnapshot.getValue(ChallengePhoto.class);

        rootRef.child("submitted-challenges").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    submittedChallengeSet.add(snapshot.getKey());
                    System.out.println(snapshot.getKey());
                }

                if (challenge.getLocation().isWithinRadius(userLocation, radius) &&
                        !submittedChallengeSet.contains(challengeKey)) {
                    System.out.println("ADDING ONE TO RV");
                    challengeMap.put(challengeKey, challenge);
                    challengeList.add(challengeMap.get(challengeKey));

                    mNearbyChallengesAdapter.setChallengeList(challengeList);
                } else {
                    System.out.println("NOT WITHIN RADIUS / HAS SUBMITTED");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateRecyclerView(DataSnapshot dataSnapshot) {
        String challengeKey = dataSnapshot.getKey();

        Set<String> challengeKeys = challengeMap.keySet();
        if (challengeKeys.contains(challengeKey)) {
            challengeMap.put(challengeKey, dataSnapshot.getValue(ChallengePhoto.class));
        }

        challengeList.clear();
        for (String key : challengeKeys) {
            challengeList.add(challengeMap.get(key));
        }

        mNearbyChallengesAdapter.setChallengeList(challengeList);
    }

    public void openPendingReview(){
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ProfileFragment.VIEWPAGER_START_POSITION, 3);
        profileFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_screen_container, profileFragment)
                .commit();
    }

    @Override
    public void onSearchChallengeClicked(ChallengePhoto challenge) {
        DialogFragment dialogFragment = ChallengeDialogFragment.getInstance(challenge);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "challenge_fragment");
    }





    // PERMISSION STUFF
    private void requestPermission() {
        int locationPermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        boolean locationPermissionIsNotGranted = locationPermission != PackageManager.PERMISSION_GRANTED;
        boolean APILevelIsTwentyThreeOrHigher = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        if (locationPermissionIsNotGranted && APILevelIsTwentyThreeOrHigher) {
            marshamallowRequestPermission();
        }
        if (locationPermissionIsNotGranted) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION);
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void marshamallowRequestPermission() {
        boolean userHasAlreadyRejectedPermission = !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
        if (userHasAlreadyRejectedPermission) {
            showMessageOKCancel("We need your location to find nearby challenges, is this too much trouble ?",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    LOCATION_PERMISSION);
                        }
                    });
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("NO", onClickListener)
                .setNegativeButton("YES", null)
                .create()
                .show();
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("SEARCH ON START IS CALLED");
        ((HomeScreenActivity)getActivity()).setBottomNavFocusSearch();
        mPendingReviewIndicator = 0;
        challengeList.clear();
        challengeMap.clear();
        submittedChallengeSet.clear();
        getUserLocation();
        setUpRecyclerView();
    }
}
