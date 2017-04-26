package com.rae.widget.dialog.impl;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.rae.widget.dialog.IDialogItemClickListener;
import com.rae.widget.dialog.R;
import com.rae.widget.dialog.model.DoubleFilterBean;
import com.rae.widget.dialog.model.FilterParamsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 双栏筛选
 * Created by ChenRui on 2017/3/30 0030 23:45.
 */
public class DoubleFilterDialog extends AppPopupWindow implements IDialogItemClickListener<FilterParamsBean> {
    private final FilterAdapter mLeftAdapter;
    private final FilterAdapter mRightAdapter;

    RecyclerView mLeftRecyclerView;

    RecyclerView mRightRecyclerView;

    private IDialogItemClickListener<FilterParamsBean> mItemClickListener;
    private int mLeftIndex = 0;
    private int mRightIndex = 0;
    private List<DoubleFilterBean> mDataList;

    public DoubleFilterDialog(Context context) {
        super(context);
        setContentView(LayoutInflater.from(context).inflate(R.layout.widget_dialog_pw_double_filter, null));

        mLeftRecyclerView = (RecyclerView) findViewById(R.id.rec_filter_left);
        mLeftRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mLeftAdapter = new FilterAdapter();
        mLeftRecyclerView.setAdapter(mLeftAdapter);
        mLeftAdapter.setDisableSelectedView(true);
        mLeftAdapter.setOnItemClickListener(new IDialogItemClickListener<FilterParamsBean>() {
            @Override
            public void onItemClick(View view, FilterParamsBean item, int position) {
                // 联动选择
                mRightAdapter.invalidate(mDataList.get(position).getChildren());
                mRightAdapter.notifyDataSetChanged();
            }
        });

        mRightRecyclerView = (RecyclerView) findViewById(R.id.rec_filter_right);
        mRightRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRightAdapter = new FilterAdapter();
        mRightAdapter.setSubMenu(true);
        mRightRecyclerView.setAdapter(mRightAdapter);
        mRightAdapter.setOnItemClickListener(this);
    }

    public void notifyDataSetChange(List<DoubleFilterBean> data) {
        mDataList = data;
        List<FilterParamsBean> leftData = new ArrayList<>();

        for (DoubleFilterBean bean : data) {
            leftData.add(bean.getParent());
        }

        mLeftAdapter.invalidate(leftData);
        mLeftAdapter.notifyDataSetChanged();

        mRightAdapter.invalidate(data.get(mLeftIndex).getChildren());
        mRightAdapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(IDialogItemClickListener<FilterParamsBean> listener) {
        mItemClickListener = listener;
    }

    @Override
    public void onItemClick(View view, FilterParamsBean item, int position) {
        dismiss();
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(view, item, position);
        }
    }

    public void setLeftSelectedItem(int position){
        mLeftAdapter.setSelectedItem(position);
    }

    public void setRightSelectedItem(int position){
        mRightAdapter.setSelectedItem(position);
    }

}
