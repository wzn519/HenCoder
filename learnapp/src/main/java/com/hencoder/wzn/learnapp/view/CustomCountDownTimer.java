package com.hencoder.wzn.learnapp.view;

import android.os.Handler;
import android.util.Log;


/**
 * Created by wzn on 2019/6/3.
 * 自定义计时器
 * ----------需要实现的功能---------------
 * 1.每过一秒就更新显示时间，如何通知Activity中的TextView来时时更新文本显示，利用回调函数
 * 2.允许用户设置总时间----》直接通过构造函数
 * 3.倒计时为0时，需要做的动作，如何来完成回调
 * 4.每过一秒，总秒数-1，通知Activity中的文本更新时间显示
 */

public class CustomCountDownTimer implements Runnable {
    private Handler mHandler;
    /*倒计时的总时间*/
    private int totalSecondTime;
    /*当前时间*/
    private int currentSecondTime;
    /*监听倒计时发生变化的接口---》这类似于，在外部来“订阅”时间发生变化的这么一个“需求”,时时通知观察者更新显示*/
    private ICountDownTimeListener countDownTimeListener;
    /*计时器是否完成计时*/
    private boolean isRun = false;

    public CustomCountDownTimer(int totalSecondTime, ICountDownTimeListener countDownTimeListener) {
        this.totalSecondTime = totalSecondTime;
        this.currentSecondTime=totalSecondTime;
        this.countDownTimeListener = countDownTimeListener;
        mHandler = new Handler();
    }

    /*----------------控制计时器的几个方法----------------------*/
    public void start() {
        /*设置启动的标志*/
        isRun = true;
        mHandler.post(this);
    }

    /*用户点击跳过时，调用该方法，停掉计时器*/
    public void cancel() {
        isRun = false;
        mHandler.removeCallbacks(this);
    }

    @Override
    public void run() {
             /*当计时器未完成计时*/
        if (isRun) {
            if (countDownTimeListener != null) {
                countDownTimeListener.onTick(currentSecondTime);
            }
                /*计时器完成*/
            if (currentSecondTime == 0) {
                cancel();
                    /*通知外部，计时器完成*/
                if (countDownTimeListener != null) {
                    countDownTimeListener.onFinish();
                }
            } else {
                    /*计时器时间值，减1*/
                currentSecondTime = totalSecondTime--;
                mHandler.postDelayed(this, 1000);
            }
        }
    }

    /**
     * 监听倒计时数值，发生变化的接口
     * 倒计时为0时，该发生的动作
     */
    public interface ICountDownTimeListener {
        /*计时器递减的回调*/
        void onTick(int time);
        /*计时器完成的回调*/
        void onFinish();
    }

}
