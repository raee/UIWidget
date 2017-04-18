package com.rae.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 带阻尼效果的滑动布局
 * Created by ChenRui on 2017/4/18 0018 15:45.
 */
public class RaeDampingLayout extends ScrollView {
    public RaeDampingLayout(Context context) {
        super(context);
    }

    public RaeDampingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RaeDampingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
