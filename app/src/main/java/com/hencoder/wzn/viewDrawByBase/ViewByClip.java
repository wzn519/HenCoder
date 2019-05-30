package com.hencoder.wzn.viewDrawByBase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.wzn.R;
import com.hencoder.wzn.utils.DensityUtil;

/**
 * Created by wzn on 2019/5/18.
 * 绘制的辅助类，裁切和几何变换
 */

public class ViewByClip extends View {


    private float progress;

    public ViewByClip(Context context) {
        this(context, null);
    }

    public ViewByClip(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewByClip(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*View绘制方法中的裁切*/
        /*裁切的要点
        *1.先绘制被裁切的图形，比如Circle，Rect等
        * 2.使用canvas.drawBitmap()方法来绘制图形
        * */
        /*----------------用方形来裁切Bitmap-------------------*/
        //clipByRect(canvas);
        /*----------用Circle来裁切Bitmap--------------*/
        //clipByCircle(canvas);
        /*---------------canvas的几何变换-----------*/
        //clipTranslate(canvas);
        /*-----------------canvas实现对图片的错切---------*/
        //skewBitmap(canvas);
        /*-------使用矩阵(Matrix)来实现变换-----*/
        //skewBitmapByMatrix(canvas);
        /*-----------Camera.rotate()实现三维旋转-----------*/
        //rotateByCamera(canvas);
        /*----------绘制扇形------------------*/
        drawArc(canvas);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawArc(Canvas canvas) {
        canvas.save();
        Paint arcPaint = new Paint();
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(DensityUtil.dp2px(getContext(), 10));
        arcPaint.setColor(Color.parseColor("#999999"));
        canvas.translate(getWidth() / 4, getHeight() / 4);
        /*绘制360度的扇形，就相当于绘制了圆环*/
        canvas.drawArc(0, 0, getWidth() / 2, getWidth() / 2, -90, progress, false, arcPaint);
        canvas.restore();
    }

    private void rotateByCamera(Canvas canvas) {
        Path circlePath = new Path();
        circlePath.addCircle(700, 300, 200, Path.Direction.CW);
        canvas.clipPath(circlePath);
        canvas.save();
        Camera camera = new Camera();
        camera.save();
        /*旋转之后，把投影复位*/
        canvas.translate(getWidth() / 2, getHeight() / 2);
        /*旋转三维空间的X轴*/
        camera.rotateZ(10);
         /*旋转三维空间的Z轴，正的表示远离屏幕，负数表示靠近屏幕*/
        camera.rotateZ(10);
        /*旋转之前，把绘制内容移到轴心(原点)*/
        canvas.translate(-getWidth() / 2, -getHeight() / 2);

        /*把旋转投放到canvas上*/
        camera.applyToCanvas(canvas);
        canvas.drawBitmap(getBitmapByLocal(), 0, 0, new Paint());
        canvas.restore();
        camera.restore();
    }

    /**
     * Matrix实现几何变换的基本步骤：
     * 1. 创建 Matrix 对象;
     * 2.调用 Matrix 的 pre/postTranslate/Rotate/Scale/Skew() 方法来设置几何变换;
     * 3.使用 Canvas.setMatrix(matrix) 或 Canvas.concat(matrix) 来把几何变换应用到 Canvas
     *
     * @param canvas
     */
    private void skewBitmapByMatrix(Canvas canvas) {
        Matrix matrix = new Matrix();
        /*重置Matrix对象*/
        matrix.reset();
        /*旋转*/
        matrix.postRotate(250);
        /*缩放*/
        matrix.postScale(0.4f, 0.3f);
        /*为canvas对象设置matrix*/
        canvas.concat(matrix);
        canvas.drawBitmap(getBitmapByLocal(), 100, 400, new Paint());

    }

    private void skewBitmap(Canvas canvas) {
        canvas.save();
        Paint skewPaint = new Paint();
        /*实现错切*/
        canvas.skew(0.3f, 0.5f);
        canvas.scale(0.4f, 0.4f);
        canvas.drawBitmap(getBitmapByLocal(), 600, 600, skewPaint);
        canvas.restore();

    }

    private void clipTranslate(Canvas canvas) {
        canvas.save();
        /*旋转---》旋转的角度是0-360*/
        /* degrees 角度, float px x坐标,  py y坐标*/
        canvas.rotate(35, getWidth() / 2, getHeight() / 2);
        /*位移*/
        canvas.translate(0, 700);
        /*缩放*/
        /*如何实现手势的放大和缩小，并截图*/
        canvas.scale(0.3f, 0.3f);
        Paint translatePaint = new Paint();
        canvas.drawBitmap(getBitmapByLocal(), 200, 200, translatePaint);
        canvas.restore();
    }

    private void clipByCircle(Canvas canvas) {
        canvas.save();/*保存当前canvas的状态*/
        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.parseColor("#552211"));
        circlePaint.setStrokeWidth(2);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        Path circlePath = new Path();
        /*Path的Direction是什么？
        * 表示的是Path所绘制的Circle的方向，
        *CW顺时针
        * CCW逆时针
        * */
        /*----------确定裁切圆形的位置和形状------------*/
        circlePath.addCircle(700, 300, 200, Path.Direction.CW);
        canvas.clipPath(circlePath);
        /*-----为什么这样绘制的是扇形(圆形的四分之一)*/
        Bitmap bitmap = getBitmapByLocal();
        /*---原来和这个left和top有关，这个该如何确定-----*/
        /*left是距离控件左边缘的距离
        *top是距离控件上边缘的距离
        * */
         /*水平移动画布----》translate一定要写在drawBitmap前面*/
        canvas.translate(200, 0);
        canvas.drawBitmap(bitmap, 0, 0, circlePaint);
        canvas.restore();/*重置裁切范围*/
    }

    private void clipByRect(Canvas canvas) {
        canvas.save();
        canvas.clipRect(100, 100, 500, 500);
        /*如何从本地获取bitmap对象*/
        //使用BitmapFactory对象来读取本地的Bitmap
        Bitmap bitmap = getBitmapByLocal();
        Paint bitmapPaint = new Paint();
        bitmapPaint.setColor(Color.parseColor("#eeaadd"));
        canvas.drawBitmap(bitmap, 300, 300, bitmapPaint);
        canvas.restore();
    }

    private Bitmap getBitmapByLocal() {
        return BitmapFactory.decodeResource(getResources(), R.mipmap.sm);
    }
    public void setProgress(int progress) {
        if (progress<=100) {
            this.progress = (float) (progress*3.6);
            /*重新绘制*/
            postInvalidate();
        }

    }
}
