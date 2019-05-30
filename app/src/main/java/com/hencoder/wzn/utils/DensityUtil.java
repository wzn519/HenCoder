package com.hencoder.wzn.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * Created by wzn on 2019/5/19.
 * DP转PX，PX转DP的工具
 */

public class DensityUtil {

    /**
     * 将像素转换成DP值
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int PX2DP(Context context, int pxValue) {
        float density = getDensity(context);
        return (int) (pxValue / density + 0.5f);
    }


    public static int px2sp(Context context, int pxValue) {
        int scaleDensity = getScaleDensity(context);
        return (int) (pxValue / scaleDensity + 0.5f);
    }

    private static int getDensity(Context context) {
    /*和屏幕密度相关的对象*/
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        /*屏幕密度*/
        return (int) displayMetrics.density;
    }

    private static int getScaleDensity(Context context) {
    /*和屏幕密度相关的对象*/
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        /*屏幕密度*/
        return (int) displayMetrics.scaledDensity;
    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static DisplayMetrics getScreenMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
}
