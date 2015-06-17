package com.example.mthomsen.airhockitygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.graphics.Bitmap;

/**
 * Created by MaritaHolm on 17/06/15.
 */

public class Player extends View{
    private final Paint mPainter = new Paint();
    private float xPos;
    private float yPos;
    private int radius;
    Bitmap mScaledBitmap;
    private static final String TAG = "AirHockity-tag";

    public Player(Context context, float x, float y, Bitmap bitmap) {
        super(context);
        this.xPos = x;
        this.yPos = y;
        this.radius = 64;
        this.mScaledBitmap = Bitmap.createScaledBitmap(bitmap, 2*radius, 2*radius, false);

    }
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw");
        canvas.save();

        canvas.drawBitmap(mScaledBitmap, xPos, yPos,mPainter);

        canvas.restore();

    }
    public boolean intersects(float x, float y) {
        return (xPos < x && xPos + 2*radius > y && yPos < y && yPos + 2*radius > y);
    }
}
