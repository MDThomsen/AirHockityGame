package com.example.mthomsen.airhockitygame;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by MaritaHolm on 17/06/15.
 */
public class Game extends Activity {

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
      //    Bundle input = getIntent().getExtras();
       // if(input != null){
        //    int t = input.getInt("points");
         //   String k = Integer.toString(t);
          //  Toast lol = Toast.makeText(getApplicationContext(), t, Toast.LENGTH_LONG);
          //    lol.show();
       // }


        players = new Player[2];

        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.player1);
        player1 = new Player(getApplicationContext(), 100,100, mBitmap1);
        players[0]=player1;
        mFrame.addView(player1);

        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.player2);
        player2 = new Player(getApplicationContext(), 100, 800, mBitmap2);
        players[1]=player2;
        mFrame.addView(player2);

    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    private boolean intersects(float x, float y) {
        for (Player p: players) {
            if (p.intersects(x,y)) {
                return true;
            }
        }
        return false;
    }
    private Player getPlayerAt(float x, float y) {
        for (Player p: players) {
            if (p.intersects(x,y)) {
                return p;
            }
        }
        return null;
    }
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Player p = getPlayerAt(motionEvent.getRawX(), motionEvent.getRawY());
        Log.d(TAG, "get player: " + p);
        if (p != null) {

                if (motionEvent.getAction() == (MotionEvent.ACTION_MOVE)) {
                    float x = motionEvent.getRawX();
                    float y = motionEvent.getRawY();

                    p.moveTo(x - p.getRadius(), y - p.getRadius());
                }
        }
        return true;
    }
}
