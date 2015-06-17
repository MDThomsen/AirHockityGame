package com.example.mthomsen.airhockitygame;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.lang.reflect.Array;

/**
 * Created by MaritaHolm on 17/06/15.
 */
public class Game extends Activity{

    private RelativeLayout mFrame;
    private Bitmap mBitmap1;
    private Bitmap mBitmap2;
    private Player player1;
    private Player player2;
    private int pointsToWin = 10;
    private static final String TAG = "AirHockity-tag";
    private Player[] players;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_game);

        // Set up user interface
        mFrame = (RelativeLayout) findViewById(R.id.frame);

        players = new Player[2];
        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.player1);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.player2);
        player1 = new Player(getApplicationContext(), 100,100, mBitmap1);
        players[0]=player1;
        mFrame.addView(player1);
        player2 = new Player(getApplicationContext(), 100, 800, mBitmap2);
        players[1]=player2;
        mFrame.addView(player2);
        Log.d(TAG, "height" + mFrame.getBottom());
        Log.d(TAG,"width" + mFrame.getWidth());
    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (intersects(event.getRawX(), event.getRawY())) {

        }
        return true;

    }

    private boolean intersects(float x, float y) {
        for (Player p: players) {
            if (p.intersects(x,y)) {
                return true;
            }
        }
        return false;
    }


}
