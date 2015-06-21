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
    private static final String TAG = "Tag-AirHockity";
    private View mFrame;
    private double DEACCELATION = 0.975;


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

    public void setX(float x){
        this.xPos=x;
    }

    public void setY(float y){
        this.yPos=y;
    }

    public void setVelocity(float x, float y) {
        xVel += x;
        yVel += y;
    }

    public void resetVelocity(){
        xVel = 0;
        yVel = 0;
    }

    protected void move(int rate) {
        Log.d(TAG, "(" + xPos + ", " + yPos + ")");
        Log.d(TAG, "intersectsTop: " + intersectsTop());
        if (intersectsTop()) {
            yPos = mFrame.getTop() + 1;
            yVel = yVel * (-1);
        }
        Log.d(TAG, "intersectsBottom: " + intersectsBottom());
        if (intersectsBottom()) {
            yPos = mFrame.getBottom() - (2 * radius + 1);
            yVel = yVel * (-1);
        }
        Log.d(TAG, "intersectsLeft: " + intersectsLeft());
        if (intersectsLeft()) {
            xPos = mFrame.getLeft() + 1;
            xVel = xVel * (-1);
        }
        Log.d(TAG, "intersectsRight: " + intersectsRight());
        if (intersectsRight()) {
            xPos = mFrame.getRight()-(2 * radius + 1);
            xVel = xVel * (-1);
        }
        if (intersectsPlayer() != null) {
            Player p = intersectsPlayer();
            double playerCentrumX = p.getX()+p.getRadius();
            double playerCentrumY = p.getY()+p.getRadius();
            double centrumX = xPos + radius;
            double centrumY = yPos + radius;

            Vector radiusVector = new Vector((( p.getRadius()/(p.getRadius()+radius)) * (playerCentrumX - centrumX)),
                    (( p.getRadius()/(p.getRadius()+radius)) * (playerCentrumY - centrumY)));
            Vector velVector = new Vector(xVel,yVel);


            Vector radiusVectorNormed = new Vector(radiusVector.getX()/radiusVector.length(),
                    radiusVector.getY()/radiusVector.length());
            double dotProductVelocityRadius = 2 * dotProduct(velVector,radiusVectorNormed);
            Vector radiusNormScaled = new Vector(radiusVectorNormed.getX()*dotProductVelocityRadius,
                    radiusVectorNormed.getY()*dotProductVelocityRadius);

            Vector newVelocity = new Vector(velVector.getX()-radiusNormScaled.getX(),
                    velVector.getY()-radiusNormScaled.getY());
            xVel = (float) newVelocity.getX();
            yVel = (float) newVelocity.getY();



        }

        xPos += xVel/rate;
        yPos += yVel/rate;
        Log.d(TAG,"xvel " + xVel + " yvel" + yVel);

    }
    protected boolean topGoal(){
        return (((xPos >= ((mFrame.getRight()/2)-100)))&&
                (xPos+2*radius <= ((mFrame.getRight()/2)+100))&&(yPos <= ((mFrame.getTop()+10))));
    }

    private double dotProduct(Vector a, Vector b) {
        return a.getX()*b.getX() + a.getY() * b.getY();
    }
    private double length(Vector a) {
        return Math.sqrt(Math.pow(a.getX(),2)+Math.pow(a.getY(),2));
    }
    protected boolean botGoal(){
        return (((xPos >= ((mFrame.getRight()/2)-100)))&&
                (xPos+2*radius <= ((mFrame.getRight()/2)+100))&&(yPos+2*radius >= ((mFrame.getBottom()-10))));
    }
    private boolean intersectsLeft() {
        return (xPos <= mFrame.getLeft());
    }
    private boolean intersectsRight() {
        return (xPos + 2 * radius >= mFrame.getRight());
    }

    private boolean intersectsTop() {
        return (yPos <= mFrame.getTop() &&!((xPos>=(mFrame.getRight()/2)-100)&&(xPos+2*radius<=(mFrame.getRight()/2)+100)));
    }

    private boolean intersectsBottom() {
        return (yPos + 2 * radius >= mFrame.getBottom())&&!((xPos>=(mFrame.getRight()/2)-100)&&(xPos+2*radius<=(mFrame.getRight()/2)+100));
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
    public void deaccelerate() {
        xVel = xVel * (float) DEACCELATION;
        yVel = yVel * (float) DEACCELATION;
    }

}
