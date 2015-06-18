package com.example.mthomsen.airhockitygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by MaritaHolm on 18/06/15.
 */
public class Puck extends View {
    private final Paint mPainter = new Paint();
    private float xPos;
    private float yPos;
    private float xVel;
    private float yVel;
    private float radius;
    private Game game;
    private Bitmap mScaledBitmap;
    private static final int REFRESH_RATE = 40;
    private static final String TAG = "Tag-AirHockity";
    private View mFrame;


    public Puck(Context context, float x, float y, Bitmap bitmap, View frame,Game game) {
        super(context);
        this.xPos = x;
        this.yPos = y;
        this.xVel = 0;
        this.yVel = 0;
        this.radius = 32;
        this.mFrame = frame;
        this.game = game;
        this.mScaledBitmap = Bitmap.createScaledBitmap(bitmap,  2 * (int)radius, 2 * (int)radius, false);
    }
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.drawBitmap(mScaledBitmap, xPos, yPos, mPainter);

    }
    public float getRadius() {
        return radius;
    }
    public float getX() {
        return xPos;
    }
    public float getY() {
        return yPos;
    }
    public void setVelocity(float x, float y) {
        xVel = x;
        yVel = y;
    }
    public void start() {

        // Creates a WorkerThread
        ScheduledExecutorService executor = Executors
                .newScheduledThreadPool(1);

        // Execute the run() in Worker Thread every REFRESH_RATE
        // milliseconds
        // Save reference to this job in mMoverFuture
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {

                move();
                postInvalidate();
            }
        }, 0, REFRESH_RATE, TimeUnit.MILLISECONDS);
    }
    private void move() {
        if (intersectsHorizontalEdge()) {
            yVel = yVel * (-1);
        }
        if (intersectsVerticalEdge()) {
            xVel = xVel * (-1);
        }
        if (intersectsPlayer() != null) {
            Player p = intersectsPlayer();
            double playerCentrumX = p.getX()+p.getRadius();
            double playerCentrumY = p.getY()+p.getRadius();
            double centrumX = xPos + radius;
            double centrumY = yPos + radius;
            double x =  ((playerCentrumX) * p.getRadius() + (centrumX)*radius)/(p.getRadius() + radius);
            double y = ((playerCentrumY) * p.getRadius() + (centrumY)*radius)/(p.getRadius() + radius);
            double length = Math.sqrt(Math.pow(playerCentrumX* x,2) + Math.pow(playerCentrumY* y,2));
            xVel = (float) (xVel - 2*(xVel * ((playerCentrumX* x)/length)) * (((playerCentrumX)* x)/length));
            yVel = (float) (yVel - 2*(yVel * ((playerCentrumY* y)/length)) * (((playerCentrumY)* y)/length));
            Log.d(TAG, "Velocoty: x: " + xVel + " y: " + yVel);
        }

        xPos += xVel/REFRESH_RATE;
        yPos += yVel/REFRESH_RATE;

    }

    private boolean intersectsVerticalEdge() {
        return (xPos < mFrame.getLeft() || xPos + 2 * radius > mFrame.getRight());
    }
    private boolean intersectsHorizontalEdge() {
        return (yPos < mFrame.getTop() || yPos + 2 * radius > mFrame.getBottom());
    }

    private Player intersectsPlayer() {
        Player[] players = game.getPlayers();
        for (Player p: players) {
            if (p.intersects(this)) {
                return p;
            }
        }
        return null;
    }

}
