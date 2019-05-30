package com.hencoder.wzn.viewDrawByBase.drawHuaBeiView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hencoder.wzn.R;
import com.hencoder.wzn.utils.DensityUtil;

/**
 * Created by wzn on 2019/5/19.
 * 自定义View实现花呗信用
 */

public class DrawHuaBeiView extends View {

    private final float mSweepInWidth;
    private int mMaxScore;
    private int mStartAngle;
    private int mSweepAngle;
    private float mSweepOutWidth;
    private Paint paint;
    private Paint paint_2;
    private Paint paint_3;
    private Paint paint_4;
    private int mWidth, mHeight;
    private int mRadius;
    private int currentNum = 400;

    public DrawHuaBeiView(Context context) {
        this(context, null);
    }

    public DrawHuaBeiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawHuaBeiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*获取自定义属性值*/
        /*获取属性集合的对象*/
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawHuaBeiView);
        /*获取具体的属性值*/
        /*最大分数*/
        mMaxScore = typedArray.getInteger(R.styleable.DrawHuaBeiView_maxStore, 500);
        /*起始角度*/
        mStartAngle = typedArray.getInteger(R.styleable.DrawHuaBeiView_startAngle, 160);
        /*圆环扫过的角度*/
        mSweepAngle = typedArray.getInteger(R.styleable.DrawHuaBeiView_sweepAngle, 220);
        typedArray.recycle();
        /*内外圆环的宽度*/
        mSweepInWidth = DensityUtil.dp2px(context, 8);
        mSweepOutWidth = DensityUtil.dp2px(context, 3);
        /*初始化画笔*/
        initPaint();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xffffffff);
        paint_2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_4 = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * 重新测量方法，确保屏幕的宽度和高度是300*400
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*获取宽度和高度的mode*/
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        /*获取宽度和高度的测量值*/
        int widthMeasure = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasure = MeasureSpec.getSize(heightMeasureSpec);

        /*根据测量的mode来获取View最终的宽度和高度*/
        /*测量的模式是确切的，就用它；不确切，就设置固定值*/
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthMeasure;
        } else {
            mWidth = DensityUtil.dp2px(getContext(), 300);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightMeasure;
        } else {
            mHeight = DensityUtil.dp2px(getContext(), 400);
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

    }

    /**
     * 绘制花呗信用
     *
     * @param canvas
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*获取圆环的半径*/
        /*已经测量过了，可以获取到View的宽度*/
        mRadius = getMeasuredWidth() / 4;
        canvas.save();
        /*将画布平移*/
        canvas.translate(mWidth / 2, mWidth / 2);
        //画内外圆弧
        drawRound(canvas);
        //画刻度---通过旋转画布的方式来实现
        drawScale(canvas);
        //画当前进度值
        drawIndicator(canvas);
        //画中间的文字
        drawCenterText(canvas);
        canvas.restore();
    }

    /**
     * 画内外圆
     *
     * @param canvas
     */
    private void drawRound(Canvas canvas) {
        /*先保存canvas的信息*/
        canvas.save();
         /*绘制内圆圆环*/
         /*设置环的Alpha值---->必须是16进制*/
        paint.setAlpha(0x40);
         /*设置宽度*/
        paint.setStrokeWidth(mSweepInWidth);
        /*绘制扇形的Rect对象*/
        RectF inRectF = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        /*绘制扇形*/
        canvas.drawArc(inRectF, mStartAngle, mSweepAngle, false, paint);
        /*重置canvas*/
        canvas.restore();
        canvas.save();
        /*绘制外扇形*/
        paint.setStrokeWidth(mSweepOutWidth);
        /*圆环的间隙*/
        int w = DensityUtil.dp2px(getContext(), 10);
        /*这个相当于绘制的扇形所在的圆的外切正方形*/
        RectF outRectF = new RectF(-mRadius - w, -mRadius - w, mRadius + w, mRadius + w);
        /*绘制扇形，不显示半径*/
        canvas.drawArc(outRectF, mStartAngle, mSweepAngle, false, paint);
        canvas.restore();
    }

    /*表示信用等级的文字*/
    private String[] text = {"较差", "中等", "良好", "优秀", "极好"};

    /**
     * 画刻度
     *
     * @param canvas
     */
    private void drawScale(Canvas canvas) {
        canvas.save();
        /*刻度之间的间隔*/
        int angle = mSweepAngle / 30;
        /*旋转画布---->将起始刻度旋转到正上方*/
        canvas.rotate(-270 + mStartAngle);
        for (int i = 0; i < 30; i++) {
        /*每隔6隔，绘制一个粗的刻度，并绘制文字*/
            if (i % 6 == 0) {
                /*设置粗刻度的描边宽度*/
                paint.setStrokeWidth(DensityUtil.dp2px(getContext(), 2));
                /*设置alpha值*/
                paint.setAlpha(0x70);
                /*绘制刻度线*/
                canvas.drawLine(0, -mRadius - mSweepInWidth / 2, 0,
                        -mRadius + mSweepInWidth / 2 + DensityUtil.dp2px(getContext(), 1), paint);
                /*绘制刻度文字*/
                drawText(canvas, i * mMaxScore / 30 + "", paint);
            } else {//绘制细刻度
                paint.setStrokeWidth(DensityUtil.dp2px(getContext(), 1));
                paint.setAlpha(0x50);
                canvas.drawLine(0, -mRadius - mSweepInWidth / 2, 0, -mRadius + mSweepInWidth / 2, paint);
            }
            /*绘制刻度区间文字*/
            if (i == 3 || i == 9 || i == 15 || i == 21 || i == 27) {
                paint.setStrokeWidth(DensityUtil.dp2px(getContext(), 2));
                paint.setAlpha(0x50);
                /*绘制文字*/
                drawText(canvas, text[(i - 3) / 6], paint);
            }
            /*旋转画布*/
            canvas.rotate(angle);
        }
        canvas.restore();
    }

    /**
     * 绘制文字
     *
     * @param canvas
     * @param s
     * @param paint
     */
    private void drawText(Canvas canvas, String s, Paint paint) {
        /*设置Paint的样式*/
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(DensityUtil.sp2px(getContext(), 8));
        /*获取要绘制的文字的宽度*/
        int textWidth = (int) paint.measureText(s);
        canvas.drawText(s, -textWidth / 2, -mRadius + DensityUtil.dp2px(getContext(), 15), paint);
        paint.setStyle(Paint.Style.STROKE);
    }

    /*梯度渐变的颜色值*/
    private int[] indicatorColor = {0xffffffff, 0x00ffffff, 0x99ffffff, 0xffffffff};

    /**
     * 画当前的进度值
     *
     * @param canvas
     */
    private void drawIndicator(Canvas canvas) {
        canvas.save();
        paint_2.setStyle(Paint.Style.STROKE);
        int sweep;
        if (currentNum <= mMaxScore) {
            sweep = (int) ((float) currentNum / (float) mMaxScore * mSweepAngle);
        } else {
            sweep = mSweepAngle;
        }
        paint_2.setStrokeWidth(mSweepOutWidth);
        Shader shader = new SweepGradient(0, 0, indicatorColor, null);
        paint_2.setShader(shader);
        int w = DensityUtil.dp2px(getContext(), 10);
        RectF rectf = new RectF(-mRadius - w, -mRadius - w, mRadius + w, mRadius + w);
        canvas.drawArc(rectf, mStartAngle, sweep, false, paint_2);
        float x = (float) ((mRadius + DensityUtil.dp2px(getContext(), 10)) * Math.cos(Math.toRadians(mStartAngle + sweep)));
        float y = (float) ((mRadius + DensityUtil.dp2px(getContext(), (10)) * Math.sin(Math.toRadians(mStartAngle + sweep))));
        paint_3.setStyle(Paint.Style.FILL);
        paint_3.setColor(0xffffffff);
        paint_3.setMaskFilter(new BlurMaskFilter(DensityUtil.dp2px(getContext(), 3), BlurMaskFilter.Blur.SOLID)); //需关闭硬件加速
        Log.e("进度", "drawIndicator: x--->" + x + ";y-->" + y);
        canvas.drawCircle(x, y, DensityUtil.dp2px(getContext(), 3), paint_3);
        canvas.restore();
    }

    /**
     * 画中间的文字
     *
     * @param canvas
     */
    private void drawCenterText(Canvas canvas) {
        canvas.save();
        paint_4.setStyle(Paint.Style.FILL);
        paint_4.setTextSize(mRadius / 2);
        paint_4.setColor(0xffffffff);
        canvas.drawText(currentNum + "", -paint_4.measureText(currentNum + "") / 2, 0, paint_4);
        paint_4.setTextSize(mRadius / 4);
        String content = "信用";
        if (currentNum < mMaxScore * 1 / 5) {
            content += text[0];
        } else if (currentNum >= mMaxScore * 1 / 5 && currentNum < mMaxScore * 2 / 5) {
            content += text[1];
        } else if (currentNum >= mMaxScore * 2 / 5 && currentNum < mMaxScore * 3 / 5) {
            content += text[2];
        } else if (currentNum >= mMaxScore * 3 / 5 && currentNum < mMaxScore * 4 / 5) {
            content += text[3];
        } else if (currentNum >= mMaxScore * 4 / 5) {
            content += text[4];
        }
        Rect r = new Rect();
        paint_4.getTextBounds(content, 0, content.length(), r);
        canvas.drawText(content, -r.width() / 2, r.height() + 20, paint_4);
        canvas.restore();
    }
}
