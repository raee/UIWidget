package com.rae.widget.dialog;

import android.view.View;

/**
 * 点击监听
 * Created by ChenRui on 2017/4/19 0019 13:02.
 */
public interface IDialogItemClickListener<T> {
    void onItemClick(View view, T item, int position);
}
