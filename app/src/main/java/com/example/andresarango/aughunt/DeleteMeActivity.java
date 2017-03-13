package com.example.andresarango.aughunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.daprlabs.aaron.swipedeck.SwipeDeck;
import com.example.andresarango.aughunt.challenge.challenges_adapters.swipe_review.ReviewSwipeAdapter;

import java.util.ArrayList;

public class DeleteMeActivity extends AppCompatActivity
        implements SwipeDeck.SwipeDeckCallback{

    private SwipeDeck cardStack;
    private ReviewSwipeAdapter adapter;
    private ArrayList<String> testData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_me);
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        testData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            testData.add(String.valueOf(i));
        }

        adapter = new ReviewSwipeAdapter();
//        adapter.setCompletedChallengeList(testData);
        if(cardStack != null){
            cardStack.setAdapter(adapter);
        }
        cardStack.setCallback(this);
        cardStack.setLeftImage(R.id.left_image);
        cardStack.setRightImage(R.id.right_image);

        Button leftButton = (Button) findViewById(R.id.button_left);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardLeft(500);

            }
        });
        Button rightButton = (Button) findViewById(R.id.button_right);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardRight(180);
            }
        });


    }

    @Override
    public void cardSwipedLeft(long stableId) {

    }

    @Override
    public void cardSwipedRight(long stableId) {

    }
}
