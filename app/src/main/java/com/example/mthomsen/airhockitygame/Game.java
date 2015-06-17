package com.example.mthomsen.airhockitygame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by MaritaHolm on 17/06/15.
 */
public class Game extends Activity{

    private RelativeLayout mFrame;
    private View player1;
    private View player2;
    private int pointsToWin = 10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game);

        // Set up user interface
        mFrame = (RelativeLayout) findViewById(R.id.frame);
        player1 = findViewById(R.id.player1);
        player1 = findViewById(R.id.player2);


    }
    @Override
    protected void onResume() {
        super.onResume();

    }



}
