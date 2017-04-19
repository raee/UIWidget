package com.rae.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 占位符
 * Created by ChenRui on 2017/4/18 0018 15:54.
 */
public class RaePlaceHolderLayout extends FrameLayout {

    View mPlaceHolderView;
    private TextView mTitleView;
    private ImageView mIconView;
    private View mEmptyView;
    private View mLoadingView;
    private TextView mLoadingTextView;
    private TextView mRetryView;
    private TextView mSubTitleView;
    private CharSequence mEmptyText;
    private Drawable mNetWorkErrorIcon;
    private CharSequence mNetWorkErrorText;
    private Drawable mEmptyIcon;


    public RaePlaceHolderLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public RaePlaceHolderLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(context, attrs, 0);
    }

    public RaePlaceHolderLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttrs(context, attrs, defStyleAttr);
    }

    private void init() {
        mPlaceHolderView = LayoutInflater.from(getContext()).inflate(R.layout.rae_place_holder_layout, this, false);
        mTitleView = (TextView) mPlaceHolderView.findViewById(R.id.tv_empty_view_text);
        mIconView = (ImageView) mPlaceHolderView.findViewById(R.id.img_empty_view);
        mEmptyView = mPlaceHolderView.findViewById(R.id.layout_empty_view);
        mLoadingView = mPlaceHolderView.findViewById(R.id.layout_empty_loading);
        mLoadingTextView = (TextView) mPlaceHolderView.findViewById(R.id.tv_empty_loading_text);
        mRetryView = (TextView) mPlaceHolderView.findViewById(R.id.tv_empty_view_retry);
        mSubTitleView = (TextView) mPlaceHolderView.findViewById(R.id.tv_empty_view_sub_text);
        setViewType(1);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RaePlaceHolderLayout, R.attr.RaePlaceHolderStyle, defStyleAttr);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.RaePlaceHolderLayout_emptyText) {
                mEmptyText = a.getText(attr);
                setText(mEmptyText);
            } else if (attr == R.styleable.RaePlaceHolderLayout_loadingText) {
                setLoadingText(a.getText(attr));
            } else if (attr == R.styleable.RaePlaceHolderLayout_emptyTextColor) {
                mTitleView.setTextColor(a.getColor(attr, Color.parseColor("#555555")));
            } else if (attr == R.styleable.RaePlaceHolderLayout_emptyImage) {
                mEmptyIcon = a.getDrawable(attr);
                mIconView.setImageDrawable(mEmptyIcon);
            } else if (attr == R.styleable.RaePlaceHolderLayout_viewType) {
                int viewType = a.getInteger(attr, 1);
                setViewType(viewType);
            } else if (attr == R.styleable.RaePlaceHolderLayout_retryText) {
                setRetryText(a.getText(attr));
            } else if (attr == R.styleable.RaePlaceHolderLayout_subText) {
                setSubText(a.getText(attr));
            } else if (attr == R.styleable.RaePlaceHolderLayout_textSize) {
                float textSize = a.getDimension(attr, 14);
                mTitleView.setTextSize(textSize);
            } else if (attr == R.styleable.RaePlaceHolderLayout_networkErrorIcon) {
                mNetWorkErrorIcon = a.getDrawable(attr);
            } else if (attr == R.styleable.RaePlaceHolderLayout_networkErrorText) {
                mNetWorkErrorText = a.getText(attr);
            }
        }
        a.recycle();
    }

    public void setSubText(CharSequence subText) {
        if (subText != null) {
            mSubTitleView.setText(subText);
            mSubTitleView.setVisibility(View.VISIBLE);
        }
    }

    public void setViewType(int viewType) {
        if (viewType == 0) {
            onEmpty();
        }
        if (viewType == 1) {
            onLoading();
        }
    }

    /**
     * 重试按钮点击
     */
    public void setRetryClickListener(final OnClickListener retryClickListener) {
        mRetryView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoading();
                retryClickListener.onClick(v);
            }
        });
        mRetryView.setVisibility(View.VISIBLE);
    }

    /**
     * 数据监听
     */
    private class AdapterDataSetObserver extends DataSetObserver {
        private Adapter mAdapter;

        private Handler mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (mAdapter.getCount() > 0) {
                    dismiss();
                } else {
                    onEmpty();
                }
                return false;
            }
        });

        AdapterDataSetObserver(Adapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public void onChanged() {
            super.onChanged();
            mHandler.removeMessages(21);
            mHandler.sendEmptyMessageDelayed(21, 100);
        }

        @Override
        public void onInvalidated() {
            onChanged();
        }
    }

    public void onLoading() {
        mEmptyView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);
    }

    public void onEmpty() {
        mPlaceHolderView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.VISIBLE);
        mTitleView.setText(mEmptyText);
        if (mEmptyIcon != null) {
            mIconView.setImageDrawable(mEmptyIcon);
        }
        mLoadingView.setVisibility(View.GONE);
    }

    public void onNetWorkError() {
        onEmpty();
        String message = mNetWorkErrorText == null ? getContext().getString(R.string.rae_network_error) : mNetWorkErrorText.toString();
        mTitleView.setText(message);
        if (mNetWorkErrorIcon != null) {
            mEmptyIcon = mIconView.getDrawable();
            mIconView.setImageDrawable(mNetWorkErrorIcon);
        } else {
            mIconView.setImageResource(R.drawable.rae_network_error);
        }
        mRetryView.setVisibility(View.VISIBLE);
    }

    public void dismiss() {
        if (mPlaceHolderView.getVisibility() != View.GONE) {
            mPlaceHolderView.setVisibility(View.GONE);
        }
    }


    /**
     * 设置标题
     */
    public void setText(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            mEmptyText = text;
            mTitleView.setText(text);
        }
    }

    public void setLoadingText(CharSequence loadingText) {
        mLoadingTextView.setText(loadingText);
    }

    public void setRetryText(CharSequence text) {
        if (text != null) {
            mRetryView.setText(text);
            mRetryView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addView(mPlaceHolderView);
    }

    /**
     * 订阅监听
     */
    public void subscribe(Adapter adapter) {
        adapter.registerDataSetObserver(new AdapterDataSetObserver(adapter));
    }

    /**
     * 订阅监听
     */
    public void subscribe(final RecyclerView.Adapter adapter) {
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (adapter.getItemCount() > 0) {
                    dismiss();
                } else {
                    onEmpty();
                }
            }
        });
    }

}
