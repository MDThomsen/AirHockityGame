package com.example.mthomsen.airhockitygame;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by MaritaHolm on 17/06/15.
 */
public class Game extends Activity implements View.OnTouchListener{

    private ViewGroup mFrame;
    private Bitmap mBitmap1;
    private Bitmap mBitmap2;
    private Bitmap mBitmap3;
    private Player player1;
    private Player player2;
    private Puck puck;
    private int pointsToWin = 10;
    private static final String TAG = "AirHockity-tag";
    private Player[] players;
    SharedPreferences prefs = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_game);

        // Set up user interface
        mFrame = (ViewGroup) findViewById(R.id.frame);
        mFrame.setOnTouchListener(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int k = prefs.getInt("points",0);

         Toast test = Toast.makeText(getApplicationContext(), Integer.toString(k), Toast.LENGTH_LONG);
         test.show();



        players = new Player[2];

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;
        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.player1, opts);
        player1 = new Player(getApplicationContext(), 100,100, mBitmap1);
        players[0]=player1;
        mFrame.addView(player1);

        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.player2, opts);
        player2 = new Player(getApplicationContext(), 100, 800, mBitmap2);
        players[1]=player2;
        mFrame.addView(player2);

        mBitmap3 = BitmapFactory.decodeResource(getResources(),R.drawable.puck);
        puck = new Puck(getApplicationContext(),350, 600,mBitmap3, mFrame);
        mFrame.addView(puck);


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


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Player p = getPlayerAt(event.getX(), event.getY());
        Log.d(TAG, "get player: " + p);
        if (p != null) {

            if (event.getAction() == (MotionEvent.ACTION_MOVE)) {
                float x = event.getX();
                float y = event.getY();

                p.moveTo(x ,y);
                if (p.intersects(puck)) {
                    VelocityTracker tracker = VelocityTracker.obtain();
                    tracker.addMovement(event);
                    tracker.computeCurrentVelocity(10);
                    puck.setVelocity(tracker.getXVelocity(),tracker.getYVelocity());
                    puck.start();

                }
            }
        }
        return true;
    }

}
