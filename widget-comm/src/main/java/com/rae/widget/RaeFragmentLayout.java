package com.rae.widget;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Pull To Refresh Fragment Layout
 * Created by ChenRui on 2017/4/18 0018 14:48.
 */
public class RaeFragmentLayout extends PtrClassicFrameLayout {

    public RaeFragmentLayout(Context context) {
        super(context);
        init();
    }

    public RaeFragmentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RaeFragmentLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    protected void init() {
    }
}
