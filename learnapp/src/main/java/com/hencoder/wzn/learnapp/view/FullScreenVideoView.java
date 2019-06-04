package com.hencoder.wzn.learnapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by wzn on 2019/6/2.
 */

public class FullScreenVideoView extends VideoView {
    public FullScreenVideoView(Context context) {
        super(context);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*获取用户在xml文件中设置的宽高值*/
        int defaultWidth=getDefaultSize(0,widthMeasureSpec);
        int defaultHeight=getDefaultSize(0,heightMeasureSpec);
        setMeasuredDimension(defaultWidth,defaultHeight);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
