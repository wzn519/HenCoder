package com.hencoder.wzn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hencoder.wzn.learnapp.activity.SplashActivity;
import com.hencoder.wzn.viewDrawByBase.DrawByClipActivity;
import com.hencoder.wzn.viewDrawByBase.OnDrawActivity;
import com.hencoder.wzn.viewDrawByBase.drawHuaBeiView.DrawHuaBeiActivity;
import com.hencoder.wzn.viewDrawByBase.pathBeiSaiEr.PathQuatActivity;

/**
 * Created by wzn on 2019/5/13.
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOnDrawListener();
        setOnDrawByClipListener();
        setDrawHuaBeiListener();
        setPathQuatListener();
        setVideoViewListener();
    }

    private void setVideoViewListener() {
         findViewById(R.id.btn_video_view).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 /*要在主工程的添加引用*/
                 startActivity(new Intent(MainActivity.this, SplashActivity.class));
             }
         });
    }

    private void setPathQuatListener() {
        findViewById(R.id.btn_path_quat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PathQuatActivity.class));
            }
        });
    }

    //绘制花呗信用
    private void setDrawHuaBeiListener() {
        findViewById(R.id.btn_draw_hb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DrawHuaBeiActivity.class));
            }
        });
    }

    /**
     * 绘制的裁切和几何变换
     */
    private void setOnDrawByClipListener() {
        findViewById(R.id.btn_draw_clip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DrawByClipActivity.class));
            }
        });
    }


    //自定义View，onDraw方法的基本用法
    private void setOnDrawListener() {
        findViewById(R.id.btn_onDraw_base).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OnDrawActivity.class));
            }
        });
    }


}
