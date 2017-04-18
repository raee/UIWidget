package com.rae.widget.dialog.impl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.rae.widget.dialog.R;


/**
 * 下拉弹出窗口
 * Created by ChenRui on 2017/3/30 0030 23:45.
 */
public abstract class AppPopupWindow extends PopupWindow {

    private final Context mContext;
    private View mAlphaView;
    private View mContentView;
    private long mLastDismissTime;
    private View mArrowView;

    public AppPopupWindow(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    protected void initView() {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setAnimationStyle(0);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (System.currentTimeMillis() - mLastDismissTime < 300) {
            return;
        }
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        mAlphaView.startAnimation(AnimationUtils.loadAnimation(anchor.getContext(), android.R.anim.fade_in));
//        mContentView.startAnimation(AnimationUtils.loadAnimation(anchor.getContext(), android.R.anim.fade_in));
        toggleFilterAnim();
    }

    @Override
    public void setContentView(View contentView) {
        FrameLayout view = new FrameLayout(mContext);
        mAlphaView = new ImageView(mContext);
        mAlphaView.setBackgroundColor(Color.BLACK);
        mAlphaView.setAlpha(0.5f);
        mAlphaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mAlphaView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.addView(mAlphaView);
        view.addView(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mContentView = contentView;
        super.setContentView(view);
    }

    private void dismissSupport() {
        super.dismiss();
        mLastDismissTime = System.currentTimeMillis();
    }

    @Override
    public void dismiss() {
        Animation anim = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out);
        mAlphaView.startAnimation(anim);
        dismissSupport();
        toggleFilterAnim();
    }

    public void setArrowView(View arrowView) {
        mArrowView = arrowView;
    }

    private void toggleFilterAnim() {
        if (mArrowView == null) return;

        Animation anim;

        if (isShowing()) {
            // 逆时针动画
            anim = AnimationUtils.loadAnimation(mContext, R.anim.widget_dialog_arrow_rotate_up);
        } else {
            // 顺时针动画
            anim = AnimationUtils.loadAnimation(mContext, R.anim.widget_dialog_arrow_rotate_down);
        }

        anim.setDuration(300);
        anim.setFillAfter(true);
        mArrowView.startAnimation(anim);
    }


    public View findViewById(int id) {
        return mContentView.findViewById(id);
    }
}
