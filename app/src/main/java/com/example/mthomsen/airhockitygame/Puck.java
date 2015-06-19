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
        Log.d(TAG, "intersectsHorizontalEdge: " + intersectsHorizontalEdge());
        if (intersectsHorizontalEdge()) {
            yVel = yVel * (-1);
        }
        Log.d(TAG, "intersectsVerticalEdge: " + intersectsVerticalEdge());
        if (intersectsVerticalEdge()) {
            xVel = xVel * (-1);
        }
        if (intersectsPlayer() != null) {
            /*Player p = intersectsPlayer();
            double playerCentrumX = p.getX()+p.getRadius();
            double playerCentrumY = p.getY()+p.getRadius();
            double centrumX = xPos + radius;
            double centrumY = yPos + radius;

            Vector radiusVector = new Vector((( p.getRadius()/(p.getRadius()+radius)) * (playerCentrumX - centrumX)),
                    (( p.getRadius()/(p.getRadius()+radius)) * (playerCentrumY - centrumY)));
            Log.d(TAG, "radiusVector " + radiusVector);
            Vector tangentVector = new Vector((-1)*radiusVector.getY(),radiusVector.getX());
            Log.d(TAG, "tangentVector " + tangentVector);
            Vector velVector = new Vector(xVel,yVel);
            Log.d(TAG, "velVector " + velVector);

            double inAngle = Math.acos(dotProduct(tangentVector, velVector) / (length(tangentVector) * length(velVector)));
            Log.d(TAG, "inAngle " + inAngle);

            double u = Math.asin(tangentVector.getY() / tangentVector.getX());
            Log.d(TAG, "u " + u);

            double w = (180-inAngle-u);
            Log.d(TAG, "w " + w);

            float newVelx = (float) Math.sin(w);
            Log.d(TAG, "newVelx " + newVelx);

            float newVely = (float) Math.sin(180-(w+90));
            Log.d(TAG, "newVely " + newVely);

            xVel = newVelx;
            yVel = newVely;
            Log.d(TAG, "xVel: " + xVel +" yVel: " + yVel);*/
        }

        /*
            Player p = intersectsPlayer();
            double playerCentrumX = p.getX()+p.getRadius();
            double playerCentrumY = p.getY()+p.getRadius();
            double centrumX = xPos + radius;
            double centrumY = yPos + radius;
            double x =  (playerCentrumX * p.getRadius() + centrumX*radius)/(p.getRadius() + radius);
            double y = (playerCentrumY * p.getRadius() + centrumY*radius)/(p.getRadius() + radius);
            double Nx = (x-playerCentrumX)/p.getRadius();
            double Ny = (y-playerCentrumY)/p.getRadius();
            float newXVel = (float) (xVel - 2*(Nx * xVel + Ny * yVel) * Nx);
            float newYVel = (float) (yVel - 2*(Ny * yVel+ Ny * yVel) * Ny);
            xVel = newXVel;
            yVel = newYVel;
            Log.d(TAG, "Velocity: x: " + xVel + " y: " + yVel);
        }*/

        xPos += xVel/rate;
        yPos += yVel/rate;

    }
    protected boolean topGoal(){
        return (((xPos >= ((mFrame.getRight()/2)-100)))&&
                (xPos+2*radius <= ((mFrame.getRight()/2)+100))&&(yPos <= ((mFrame.getTop()+10))));
    }

    private double dotProduct(Vector a, Vector b) {
        return (a.getX()*b.getX() + a.getY() * b.getY());
    }
    private double length(Vector a) {
        return Math.sqrt(Math.pow(a.getX(),2)+Math.pow(a.getY(),2));
    }
    protected boolean botGoal(){
        return (((xPos >= ((mFrame.getRight()/2)-100)))&&
                (xPos+2*radius <= ((mFrame.getRight()/2)+100))&&(yPos+2*radius >= ((mFrame.getBottom()-10))));
    }
    private boolean intersectsVerticalEdge() {
        return (xPos <= mFrame.getLeft()+11 || xPos + 2 * radius >= mFrame.getRight()-15);
    }

    private boolean intersectsHorizontalEdge() {
        return (yPos <= mFrame.getTop()+11 || yPos + 2 * radius >= mFrame.getBottom()-11)&&!((xPos>=(mFrame.getRight()/2)-100)&&(xPos+2*radius<=(mFrame.getRight()/2)+100));
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
