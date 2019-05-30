package com.hencoder.wzn.viewDrawByBase.pathBeiSaiEr;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hencoder.wzn.R;

/**
 * Created by wzn on 2019/5/28.
 */

public class PathQuatActivity extends Activity {

    private PathQuatView pathQuatView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_quat);
        pathQuatView = findViewById(R.id.path_quat_view);
        findViewById(R.id.reset_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pathQuatView.reset();
            }
        });
    }
}
