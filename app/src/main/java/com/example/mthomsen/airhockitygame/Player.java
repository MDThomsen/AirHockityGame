package com.example.mthomsen.airhockitygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Bitmap;
import android.widget.RelativeLayout;

/**
 * Created by MaritaHolm on 17/06/15.
 */

public class Player extends View {
    private final Paint mPainter = new Paint();
    private double xPos;
    private double yPos;
    private double radius;
    Bitmap mScaledBitmap;
    private static final String TAG = "AirHockity-tag";

    public Player(Context context, float x, float y, Bitmap bitmap) {
        super(context);
        this.xPos = x;
        this.yPos = y;
        this.radius = 64f;
        this.mScaledBitmap = Bitmap.createScaledBitmap(bitmap,  2* (int) radius, 2* (int)radius, false);


    }
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw");
        canvas.save();

        canvas.drawBitmap(mScaledBitmap, (float) xPos, (float) yPos, mPainter);

        canvas.restore();

    }
    public boolean intersects(float x, float y) {
        Log.d(TAG, "xPos: "+ xPos + "yPos: " +yPos);
        Log.d(TAG, "Centrum: ("+ (xPos+radius) + "," +(yPos+radius)+ ")");
        Log.d(TAG, "x: " + (x) + " y: " + Math.abs(y) + " radius: " + radius);
        return (Math.abs(x-(xPos+radius)) < radius && Math.abs(y-(yPos+radius)) < radius);
    }

    public void moveTo(double x, double y) {
        xPos = x;
        yPos = y;
        invalidate();
    }
    public double getRadius() {
        return radius;
    }
/*    public boolean onTouch(View view, MotionEvent motionEvent) {
        int dx = 0;
        int dy = 0;
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dx = (int) motionEvent.getX();
                dy = (int) motionEvent.getY();


            case MotionEvent.ACTION_MOVE:
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
                int left = lp.leftMargin + (x - dx);
                int top = lp.topMargin + (y - dy);
                lp.leftMargin = left;
                lp.topMargin = top;
                view.setLayoutParams(lp);
                break;
        }
        return true;
    }*/
    /*public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == (MotionEvent.ACTION_MOVE)) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();

                xPos = x;
                yPos = y;
                invalidate();
            }


        return true;
    }*/
}
