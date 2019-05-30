package com.hencoder.wzn.viewDrawByBase;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hencoder.wzn.R;

/**
 * Created by wzn on 2019/5/18.
 */

public class DrawByClipActivity extends Activity {

    private ViewByClip clipView;
    private int progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_by_clip);
        clipView = (ViewByClip)findViewById(R.id.view_clip);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        progress++;
                        clipView.setProgress(progress);
                        Thread.sleep(100);
                        if(progress==100){
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
}
