package com.hencoder.wzn.viewDrawByBase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.hencoder.wzn.R;

/**
 * Created by wzn on 2019/5/13.
 * 自定义ViewGroup，用于测试绘制不同的View，查看绘制效果
 */


public class ViewByDraw extends View {

    private static String TAG = "绘制";
    private int circleRadius;
    private Context mContext;
    private int circleStrokeColor;
    private int circleStrokeWidth;

    public ViewByDraw(Context context) {
        this(context, null);
        Log.e(TAG, "ViewByDraw: 1");
    }

    public ViewByDraw(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        Log.e(TAG, "ViewByDraw: 2");
    }

    public ViewByDraw(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG, "ViewByDraw: 3");
        this.mContext = context;
        /*获取自定义的属性值*/
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.circleByDraw);
        /*圆的半径*/
        circleRadius = typedArray.getInt(R.styleable.circleByDraw_CircleRadius, 0);
        /*描边的颜色和宽度*/
        circleStrokeColor = typedArray.getColor(R.styleable.circleByDraw_CircleStrokeColor, Color.BLUE);
        circleStrokeWidth = typedArray.getInt(R.styleable.circleByDraw_CircleStrokeWidth, 1);
        /*回收资源*/
        typedArray.recycle();
        Log.e(TAG, "ViewByDraw:" + "circleRadius--->" + circleRadius + ";circleStrokeColor-->"
                + circleStrokeColor + ";circleStrokeWidth--->" + circleStrokeWidth);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Log.e(TAG, "onMeasure: ---》获取宽度和高度---width=" + getWidth() + ";height--->" + getHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //Log.e(TAG, "onLayout: ---》获取宽度和高度---width=" + getWidth() + ";height--->" + getHeight());
    }

    /**
     * 测试绘制图形
     *
     * @param canvas
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        //Log.e("onDraw", "onDraw: ----》重写onDraw方法，绘制圆形");
        super.onDraw(canvas);
        /*-------绘制圆形------------*/
        /*-----------Paint主要是设置被绘制图形的颜色和风格信息--------*/
        Paint circlePaint = new Paint();
        Paint innerPaint = new Paint();
        Paint roundRectPaint = new Paint();
        /*--------用于设置被绘制图形的颜色信息------*/
        /*注意Color.parseColor()这种写法*/
        circlePaint.setColor(circleStrokeColor);
        roundRectPaint.setColor(Color.parseColor("#33edff"));
        /*设置被绘制图形的模式*/
        /*STROKE--->绘制的是空心圆;FILL,表示实心圆;*/
        circlePaint.setStyle(Paint.Style.STROKE);
        innerPaint.setStyle(Paint.Style.FILL);
        roundRectPaint.setStyle(Paint.Style.STROKE);
        /*默认View的位置，默认是靠左边的，在布局中设置Layout_gravity无法让它居中*/
        /*设置空心圆的边线宽度*/
        circlePaint.setStrokeWidth(circleStrokeWidth);
        //innerPaint.setStrokeWidth(Color.BLUE);
        roundRectPaint.setStrokeWidth(6);
        /*canvas对象，主要用于绘制图形*/
        /*获取屏幕的中心点--->通过布局参数(LayoutParams)*/
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        this.setLayoutParams(layoutParams);
        /*获取测量后的宽度信息*/
        Log.e(TAG, "onDraw: ---getWidth=" + getWidth() + ";getHeight=" + getHeight());
          /*绘制内圆*/
          /*圆的坐标--->用于简化后面绘制图形的坐标计算*/
        int circleX = getWidth() / 2;
        int circleY = circleRadius + circleStrokeWidth / 2;
        canvas.drawCircle(circleX, circleY, circleRadius - circleStrokeWidth / 2, innerPaint);
        /*绘制圆环*/
        /*这里的X和Y参数，除非在引用该View的容器中，写死了高度，否则一定会居中的*/
        canvas.drawCircle(circleX, circleY, circleRadius, circlePaint);
         /*------------------------------------------------绘制roundRect----------------------------------------*/
        /*在圆环的外围绘制一个RoundRect--->重点是确定圆心的坐标*/
        canvas.drawRoundRect(circleX - circleRadius - circleStrokeWidth / 2 - 1,
                circleY - circleRadius - circleStrokeWidth / 2 - 1,
                circleX + circleRadius + circleStrokeWidth / 2 + 1,
                circleY + circleRadius + circleStrokeWidth / 2 + 1,
                50, 50, roundRectPaint);
        /*绘制RoundRect*/
         /*left(左),  top(顶点), right(右),bottom(底部),  rx(横轴上的半径),  ry(纵轴上的半径), Paint paint(左)*/
         /*left的位置关系 getWidth() / 2 - circleRadius - 30
         *getWidth/2--->获取屏幕中心点的位置，即圆心的位置，
         *-circleRadius--->刚好是圆形最左边切线
         * -30---》表示是这个切线还向左30单位的垂直的线
         * 这个线是RoundRect左边，在垂直方向上的
         * */
         /*Right的位置关系 getWidth() / 2 + circleRadius + 80
         *它是沿着屏幕向右的，
         * getWidth()/2---》获取圆心
         * +circleRadius---》在圆心右边的切线位置
         * +80----》将切线右移80个单位
         * */
         /*left和Right所在的点的两条垂直的线，就限定了要绘制的RoundRect的水平位置和宽度*/
         /*同理，top和bottom就限定了，要绘制的RoundRect的垂直距离和高度，*/
         /*四个点的连线，就组成了一个矩形*/
        /**
         * getHeight() / 2 + circleRadius + 60
         *
         */
        canvas.drawRoundRect(circleX - circleRadius - 30, circleY + circleRadius + 60, circleX + circleRadius + 80,
                circleY + circleRadius + 400, 30, 30, roundRectPaint);
        /*当圆是描边的模式，如何绘制多个不同的颜色值？*/
        /*如何让多种颜色的绘制，有动画效果？*/
        /*------------------------------------------------绘制扇形----------------------------------------*/
        Paint arcPaint = new Paint();
        arcPaint.setColor(Color.parseColor("#999999"));
        arcPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        /*设置抗锯齿*/
        arcPaint.setAntiAlias(true);
        /*left,  top, right,  bottom,  startAngle, sweepAngle,  useCenter, Paint paint*/
        canvas.drawArc(circleX - circleRadius - 150, circleY + circleRadius + 300,
                circleX - circleRadius + 400, circleY + circleRadius + 700,
                0, 95, true, arcPaint);

        /*----------------------绘制直线----------------------*/
        Paint linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#12eaea"));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);/*设置成抗锯齿*/
        linePaint.setStrokeWidth(5);
        Path linePath = new Path();
        /*从当前位置，向目标位置画一条线----》当前位置，是指最后一次调用画Path的方法的终点
        *初始化位置是(0,0)
        * */
        /*-由当前位置(0,0)向目标位置lineX,lineY画线*/
        float lineX = circleX - circleRadius - 150;
        float lineY = circleY + circleRadius + 800;
        linePath.lineTo(lineX, lineY);
        /*由当前位置(lineX,lineY)位置，向正方向200像素的位置，画线*/
        /*这里的位置，是相对位置*/
        linePath.rLineTo(200, 0);
        /*通过canvas绘制path*/
        canvas.drawPath(linePath, linePaint);
    }
}
