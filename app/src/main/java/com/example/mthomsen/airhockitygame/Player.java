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
    private float xPos;
    private float yPos;
    private float radius;
    Bitmap mScaledBitmap;
    private static final String TAG = "Tag-AirHockity";

    public Player(Context context, float x, float y, Bitmap bitmap) {
        super(context);
        this.xPos = x;
        this.yPos = y;
        this.radius = 64;
        this.mScaledBitmap = Bitmap.createScaledBitmap(bitmap,  2 * 64, 2 * 64, false);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.drawBitmap(mScaledBitmap, xPos, yPos, mPainter);

    }

    public boolean intersects(float x, float y) {
        return (Math.abs(x - (xPos + radius)) < radius && Math.abs(y - (yPos + radius)) < radius);
    }
    public boolean intersects(Puck puck) {
        return ((xPos - puck.getX()) < radius + puck.getRadius() &&
                yPos - puck.getY() < radius + puck.getRadius());
    }


    public void moveTo(float x, float y) {
        xPos = x - radius;
        yPos = y - radius;
        invalidate();
    }

    public double getRadius() {
        return radius;
    }
}
