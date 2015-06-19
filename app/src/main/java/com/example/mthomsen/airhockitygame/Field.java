package com.example.mthomsen.airhockitygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by MThomsen on 18-06-2015.
 */
public class Field extends View {
    View mFrame;
    private Paint mPaint;
    int scoreTop = 0;
    int scoreBot = 0;

    public Field(Context context, View mFrame) {
        super(context);
        mPaint = new Paint();
        this.mFrame = mFrame;

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        mPaint.setColor(Color.YELLOW);
        //right
        canvas.drawRect(mFrame.getLeft(), mFrame.getTop(), mFrame.getLeft() + 10, mFrame.getBottom(), mPaint);

        //left
        canvas.drawRect(mFrame.getRight() - 10, mFrame.getTop(), mFrame.getRight(), mFrame.getBottom(), mPaint);

        //top
        canvas.drawRect(mFrame.getLeft(), mFrame.getTop(), (mFrame.getRight()/2)-100, mFrame.getTop()+10, mPaint);
        canvas.drawRect((mFrame.getRight() / 2) + 100, mFrame.getTop(), mFrame.getRight(), mFrame.getTop() + 10, mPaint);

        //bottom
        canvas.drawRect(mFrame.getLeft(), mFrame.getBottom() - 10, (mFrame.getRight() / 2) - 100, mFrame.getBottom(), mPaint);
        canvas.drawRect((mFrame.getRight() / 2) + 100, mFrame.getBottom() - 10, mFrame.getRight(), mFrame.getBottom(), mPaint);

        //centerline
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(mFrame.getLeft() + 10, (mFrame.getBottom() / 2) - 2, (mFrame.getRight() / 2) - 200, (mFrame.getBottom() / 2) + 2, mPaint);
        canvas.drawRect((mFrame.getRight() / 2) + 200, (mFrame.getBottom() / 2) - 2, (mFrame.getRight() - 10), (mFrame.getBottom() / 2) + 2, mPaint);

        //bottom score
        mPaint.setTextSize(50);
        canvas.drawText(Integer.toString(scoreBot), mFrame.getRight() / 2, (mFrame.getBottom() / 2)+100, mPaint);

        //top score
        canvas.rotate(180,mFrame.getRight() / 2, (mFrame.getBottom() / 2)-100);
        canvas.drawText(Integer.toString(scoreTop),((mFrame.getRight() / 2)-25), (mFrame.getBottom() / 2)-100, mPaint);

    }

    public void setScoreTop(int s){
        this.scoreTop=s;
    }

    public void setScoreBot(int s){
        this.scoreBot=s;
    }
}
