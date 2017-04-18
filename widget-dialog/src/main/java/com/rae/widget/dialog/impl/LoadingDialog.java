package com.rae.widget.dialog.impl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.rae.widget.dialog.R;


/**
 * 正在加载中
 * Created by ChenRui on 2017/1/26 0026 17:13.
 */
public class LoadingDialog extends AppDialog {

    TextView mLoadingTextView;

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void initDialog() {
        Window window = getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.setDimAmount(0);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 设置透明
        }
        setContentView(R.layout.widget_dialog_loading);
        mLoadingTextView = (TextView) findViewById(R.id.tv_view_loading_title);
        super.initDialog();
    }

    @Override
    public void setTitle(String title) {
        mLoadingTextView.setVisibility(View.VISIBLE);
        mLoadingTextView.setText(title);
    }

    @Override
    public void setMessage(String message) {
        setTitle(message);
    }
}
