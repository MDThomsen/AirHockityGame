package com.example.mthomsen.airhockitygame;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by MaritaHolm on 17/06/15.
 */
public class Game extends Activity{

    private RelativeLayout mFrame;
    private Bitmap mBitmap1;
    private Bitmap mBitmap2;
    private View player1;
    private View player2;
    private int pointsToWin = 10;
    private static final String TAG = "AirHockity-tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_game);

        // Set up user interface
        mFrame = (RelativeLayout) findViewById(R.id.frame);

        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        player1 = new Player(getApplicationContext(), 100,100, mBitmap1);
        mFrame.addView(player1);
        player2 = new Player(getApplicationContext(), 100, 1000, mBitmap2);
        mFrame.addView(player2);
        Log.d(TAG, "height" + mFrame.getBottom());
        Log.d(TAG,"width" + mFrame.getWidth());
    }
    @Override
    protected void onResume() {
        super.onResume();

    }



}
