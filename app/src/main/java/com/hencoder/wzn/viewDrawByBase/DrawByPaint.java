package com.hencoder.wzn.viewDrawByBase;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.wzn.R;

/**
 * Created by wzn on 2019/5/15.
 * 主要用于使用Paint
 */

public class DrawByPaint extends View {
    public DrawByPaint(Context context) {
        this(context, null);
    }

    public DrawByPaint(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawByPaint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*绘制圆角矩形*/

        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.parseColor("#5534a1"));
        rectPaint.setStrokeWidth(5.0f);
        rectPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(100, 100, 500, 300, 70, 70, rectPaint);
    }
}
