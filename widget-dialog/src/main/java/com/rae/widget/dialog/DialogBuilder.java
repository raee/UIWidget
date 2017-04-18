package com.rae.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.rae.widget.dialog.impl.DefaultDialog;
import com.rae.widget.dialog.impl.LoadingDialog;
import com.rae.widget.dialog.impl.MediaSelectionDialog;
import com.rae.widget.dialog.impl.SelectionDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * 对话框提供者
 * <p>
 * 如果要初始化媒体
 * Created by ChenRui on 2017/1/24 0024 14:39.
 */
public final class DialogBuilder {

    // 博客分享对话框
    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_LOADING = 2;
    /**
     * 媒体选择
     */
    public static final int TYPE_MEDIA_SELECTION = 3;
    /**
     * 列表项
     */
    public static final int TYPE_LIST_ITEM = 4;
    private final Context mContext;

    private IAppDialog mDialog;
    private int mType;


    public DialogBuilder(Context context) {
        this(context, TYPE_DEFAULT);
    }

    public DialogBuilder(Context context, int type) {
        mContext = context;
        mType = type;
        switch (type) {
            case TYPE_LIST_ITEM:
                mDialog = new SelectionDialog(mContext);
                break;
            case TYPE_MEDIA_SELECTION:
                mDialog = new MediaSelectionDialog((Activity) mContext);
                break;
            case TYPE_LOADING:
                mDialog = new LoadingDialog(mContext);
            case TYPE_DEFAULT:
            default:
                mDialog = new DefaultDialog(mContext);
        }
    }

    /**
     * loading 要在构造函数之后调用，其他地方调用会导致之前的设置无效。
     * 示例：
     * <code> new DialogBuilder(context).loading().show(); </code>
     */
    public DialogBuilder loading() {
        mDialog = new LoadingDialog(mContext);
        return this;
    }

    public DialogBuilder setMessage(String message) {
        mDialog.setMessage(message);
        return this;
    }

    public DialogBuilder setMessage(int resId) {
        setMessage(mContext.getString(resId));
        return this;
    }

    public DialogBuilder setTitle(String title) {
        mDialog.setTitle(title);
        return this;
    }

    public DialogBuilder setTitle(int resId) {
        setTitle(mContext.getString(resId));
        return this;
    }

    /**
     * 消极的按钮类型，如：取消、放弃、返回 、关闭
     *
     * @param text 文本
     */
    public DialogBuilder negativeButtonText(String text) {
        mDialog.setButtonText(IAppDialog.BUTTON_NEGATIVE, text);
        return this;
    }

    public DialogBuilder negativeButtonText(int resId) {
        negativeButtonText(mContext.getString(resId));
        return this;
    }

    /**
     * 积极的按钮类型，如：确定
     *
     * @param text 文本
     */
    public DialogBuilder positiveButtonText(String text) {
        mDialog.setButtonText(IAppDialog.BUTTON_POSITIVE, text);
        return this;
    }

    public DialogBuilder positiveButtonText(int resId) {
        positiveButtonText(mContext.getString(resId));
        return this;
    }

    public DialogBuilder positiveButtonHidden(boolean hidden) {
        mDialog.setButtonVisibility(IAppDialog.BUTTON_POSITIVE, hidden ? View.GONE : View.VISIBLE);
        return this;
    }

    public DialogBuilder positiveButtonHidden() {
        positiveButtonHidden(true);
        return this;
    }

    public DialogBuilder negativeButtonHidden(boolean hidden) {
        mDialog.setButtonVisibility(IAppDialog.BUTTON_NEGATIVE, hidden ? View.GONE : View.VISIBLE);
        return this;
    }

    public DialogBuilder negativeButtonHidden() {
        negativeButtonHidden(true);
        return this;
    }

    public DialogBuilder negativeButtonClickListener(IAppDialogClickListener listener) {
        mDialog.setOnClickListener(IAppDialog.BUTTON_NEGATIVE, listener);
        return this;
    }

    public DialogBuilder positiveButtonClickListener(IAppDialogClickListener listener) {
        mDialog.setOnClickListener(IAppDialog.BUTTON_POSITIVE, listener);
        return this;
    }

    public DialogBuilder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        mDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        return this;
    }

    public DialogBuilder setOnDismissListener(DialogInterface.OnDismissListener listener) {
        mDialog.setOnDismissListener(listener);
        return this;
    }

    public DialogBuilder addItem(List<SelectionDialog.SelectionInfo> items) {
        if (mType == TYPE_LIST_ITEM) {
            SelectionDialog dialog = (SelectionDialog) mDialog;
            dialog.setDataList(items);
        }
        return this;
    }

    public DialogBuilder setCancelable(boolean cancelable) {
        mDialog.setCancelable(cancelable);
        return this;
    }

    public DialogBuilder addItem(SelectionDialog.SelectionInfo item) {
        if (mType == TYPE_LIST_ITEM) {
            SelectionDialog dialog = (SelectionDialog) mDialog;
            if (dialog.getDataList() == null) {
                dialog.setDataList(new ArrayList<SelectionDialog.SelectionInfo>());
            }

            dialog.getDataList().add(item);
        }

        return this;
    }

    public DialogBuilder addItem(String item) {
        return addItem(new SelectionDialog.SelectionInfo(item));
    }

    public DialogBuilder setOnItemSelectedListener(SelectionDialog.SelectionListener listener) {
        if (mType == TYPE_LIST_ITEM) {
            SelectionDialog dialog = (SelectionDialog) mDialog;
            dialog.setOnSelectionListener(listener);
        }
        return this;
    }

    public IAppDialog build() {
        return mDialog;
    }

    public IAppDialog show() {
        mDialog.show();
        return mDialog;
    }
}
