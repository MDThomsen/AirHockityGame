package com.example.mthomsen.airhockitygame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
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


    public Boolean FLAG_PAUSE_PUCK;

    private ViewGroup mFrame;
    private Bitmap mBitmap1;
    private Bitmap mBitmap2;
    private Bitmap mBitmap3;
    private Player player1;
    private Player player2;
    private Puck puck;
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
        int k = prefs.getInt("points", 0);

        Toast test = Toast.makeText(getApplicationContext(), Integer.toString(k), Toast.LENGTH_LONG);
        test.show();


        players = new Player[2];

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;
        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.player1, opts);
        player1 = new Player(getApplicationContext(), 100, 100, mBitmap1);
        players[0] = player1;
        mFrame.addView(player1);

        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.player2, opts);
        player2 = new Player(getApplicationContext(), 100, 800, mBitmap2);
        players[1] = player2;
        mFrame.addView(player2);

        mBitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.puck);
        puck = new Puck(getApplicationContext(), 350, 600, mBitmap3, mFrame, this);
        mFrame.addView(puck);
        puck.start();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private boolean intersects(float x, float y) {
        for (Player p : players) {
            if (p.intersects(x, y)) {
                return true;
            }
        }
        return false;
    }

    private Player getPlayerAt(float x, float y) {
        for (Player p : players) {
            if (p.intersects(x, y)) {
                return p;
            }
        }
        return null;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Player p = getPlayerAt(event.getX(), event.getY());
        if (p != null) {

            if (event.getAction() == (MotionEvent.ACTION_MOVE)) {
                float x = event.getX();
                float y = event.getY();

                p.moveTo(x, y);
                if (p.intersects(puck)) {
                    VelocityTracker tracker = VelocityTracker.obtain();
                    tracker.addMovement(event);
                    tracker.computeCurrentVelocity(1000);
                    puck.setVelocity(tracker.getXVelocity(), tracker.getYVelocity());

                    return true;
                }
            }
        }
        return true;
    }

    public Player[] getPlayers() {
        return players;
    }

    @Override
    public void onBackPressed() {

        createDialog(puck.getXVel(),puck.getYVel()).show();




    }

    public Dialog createDialog(final float tempXVel, final float tempYVel) {


        puck.setVelocity(0,0);
       // FLAG_PAUSE_PUCK = true;

        CharSequence[] choices = new CharSequence[2];
        choices[0] = "Resume";
        choices[1] = "Main Menu";

        AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
        builder.setTitle("PAUSED")
                .setItems(choices, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 1) {
                            finish();
                        } else if (which == 0) {
                            // FLAG_PAUSE_PUCK = false;
                            puck.setVelocity(tempXVel, tempYVel);
                        }
                    }
                });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                puck.setVelocity(tempXVel, tempYVel);
                //FLAG_PAUSE_PUCK = false;
            }
        });
        return builder.create();
    }
}