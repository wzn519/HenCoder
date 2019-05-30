package com.hencoder.wzn.viewDrawByBase;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hencoder.wzn.R;

/**
 * Created by wzn on 2019/5/13.
 */

public class OnDrawActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_by_java);
    }
}
