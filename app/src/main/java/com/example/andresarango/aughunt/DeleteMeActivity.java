package com.example.andresarango.aughunt;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daprlabs.aaron.swipedeck.SwipeDeck;
import com.example.andresarango.aughunt.challenge.challenges_adapters.swipe_review.ReviewSwipeAdapter;
import com.example.andresarango.aughunt.challenge.challenges_adapters.swipe_review.ReviewSwipeCallback;

import java.util.ArrayList;
import java.util.List;

public class DeleteMeActivity extends AppCompatActivity {

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
        adapter.setData(testData);
        if(cardStack != null){
            cardStack.setAdapter(adapter);
        }
        cardStack.setCallback(new ReviewSwipeCallback());
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

}
