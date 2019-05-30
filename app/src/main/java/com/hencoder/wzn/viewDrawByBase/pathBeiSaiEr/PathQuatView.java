package com.hencoder.wzn.viewDrawByBase.pathBeiSaiEr;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hencoder.wzn.utils.DensityUtil;

/**
 * Created by wzn on 2019/5/28.
 * 贝塞尔曲线的简单应用
 */

public class PathQuatView extends View {
    private Path path = new Path();
    private float mPreX;
    private float mPreY;

    public PathQuatView(Context context) {
        super(context);
    }

    public PathQuatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathQuatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*贝塞尔曲线的简单应用*/
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(DensityUtil.dp2px(getContext(), 3));
        //Path path = getPath();
        /*绘制路径*/
        canvas.drawPath(path, paint);
    }

    @NonNull
    private Path getPath() {
    /*创建Path路径对象*/
        Path path = new Path();
        /*设置贝塞尔曲线的起点*/
        path.moveTo(100, 300);
        /*设置贝塞尔曲线的终点和控制点*/
        path.quadTo(200, 200, 300, 300);
        path.quadTo(400, 400, 500, 300);
        return path;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            /*手指按下时，用于指定Path的起点为当前用户触摸的位置，并且消费掉当前的事件*/
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                /*用来记录路径的前一个终点，作为下一个的起点*/
                mPreX = event.getX();
                mPreY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
            /*手指滑动时----设置终点和控制点坐标*/
            /*path.lineTo()实现的绘制路径，最大的问题是，线段之间不够平滑*/
                //path.lineTo(event.getX(), event.getY());
                /*---终点是线段的中间点*/
                float endX = (mPreX + event.getX()) / 2;
                float endY = (mPreY + event.getY()) / 2;
                /*利用quaTo绘制平滑的曲线*/
                path.quadTo(mPreX, mPreY, endX, endY);
                mPreX = event.getX();
                mPreY = event.getY();
                /*重新绘制---
                postInvalidate表示在任何线程中绘制，会利用handler发消息给主线程，进行绘制
                invalidate表示在主线程中绘制
                */
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 用于外部重置的方法
     */
    public void reset() {
        path.reset();
        invalidate();
    }
}
