package com.example.mthomsen.airhockitygame;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private Field mField;
    private Puck puck;
    private static final int REFRESH_RATE = 40;
    private int pointsToWin = 10;
    private static final String TAG = "Tag-AirHockity";
    private Player[] players;
    SharedPreferences prefs = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        // Set up user interface

        mFrame = (ViewGroup) findViewById(R.id.frame);
        mFrame.setOnTouchListener(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int k = prefs.getInt("points",0);

         Toast test = Toast.makeText(getApplicationContext(), Integer.toString(k), Toast.LENGTH_LONG);
         test.show();

        mField = new Field(getApplicationContext(),mFrame);
        mFrame.addView(mField);

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
        puck = new Puck(getApplicationContext(),350, 600,mBitmap3, mFrame,this);
        mFrame.addView(puck);
        start(puck,mField,mFrame);





    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    public void start(final Puck puck, final Field mField,final ViewGroup mFrame) {

        // Creates a WorkerThread
        ScheduledExecutorService executor = Executors
                .newScheduledThreadPool(1);

        // Execute the run() in Worker Thread every REFRESH_RATE
        // milliseconds
        // Save reference to this job in mMoverFuture
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                puck.move(REFRESH_RATE);
                puck.deaccelerate();
                puck.postInvalidate();
                if (puck.topGoal()) {
                    mField.setScoreBot(mField.getScoreBot() + 1);
                    resetPlayerPuck();
                }
                if (puck.botGoal()) {
                    mField.setScoreTop(mField.getScoreTop() + 1);
                    resetPlayerPuck();
                }
                if (mField.getScoreBot() == 10) {
                    mField.setBotWins(mField.getBotWins() + 1);
                }
                if (mField.getScoreTop() == 10) {
                    mField.setTopWins(mField.getTopWins() + 1);
                }
            }
        }, 0, REFRESH_RATE, TimeUnit.MILLISECONDS);
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

    private void resetPlayerPuck(){
        puck.resetVelocity();
        puck.setX(mFrame.getRight() / 2);
        puck.setY(mFrame.getBottom() / 2);
        player1.moveTo(mFrame.getRight() / 2, mFrame.getBottom() / 4);
        player2.moveTo(mFrame.getRight()/2,mFrame.getBottom()*(3/4));


    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        VelocityTracker tracker = VelocityTracker.obtain();
        tracker.addMovement(event);
        tracker.computeCurrentVelocity(1000);
        if (event.getAction() == (MotionEvent.ACTION_MOVE)) {
            Player p = getPlayerAt(event.getX(), event.getY());
            if (p != null) {

                float x = event.getX();
                float y = event.getY();

                p.moveTo(x, y);
                if (p.intersects(puck)) {
                    puck.setVelocity(VelocityTrackerCompat.getXVelocity(tracker,event.getPointerId(event.getActionIndex())), VelocityTrackerCompat.getYVelocity(tracker, event.getPointerId(event.getActionIndex())));
                    return true;
                }
            }
        }
        if (event.getAction() == (MotionEvent.ACTION_POINTER_DOWN)) {
            Player p = getPlayerAt(event.getX(), event.getY());
            if (p != null) {

                float x = event.getX();
                float y = event.getY();

                p.moveTo(x, y);
                if (p.intersects(puck)) {
                    puck.setVelocity(VelocityTrackerCompat.getXVelocity(tracker,event.getPointerId(event.getActionIndex())), VelocityTrackerCompat.getYVelocity(tracker, event.getPointerId(event.getActionIndex())));
                    return true;
                }
            }
        }
        return true;
    }

    public Player[] getPlayers() {
        return players;
    }

}
