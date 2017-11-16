package com.rae.widget.dialog.impl;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.rae.widget.dialog.IDialogItemClickListener;
import com.rae.widget.dialog.R;
import com.rae.widget.dialog.model.FilterParamsBean;

import java.util.List;


/**
 * 单栏筛选
 * Created by ChenRui on 2017/3/30 0030 23:45.
 */
public class SingleFilterDialog extends AppPopupWindow implements IDialogItemClickListener<FilterParamsBean> {

    private final FilterAdapter mAdapter;

    RecyclerView mRecyclerView;
    private IDialogItemClickListener<FilterParamsBean> mItemClickListener;

    public SingleFilterDialog(Context context) {
        super(context);
        setContentView(LayoutInflater.from(context).inflate(R.layout.widget_dialog_pw_signle_filter, null));
        mRecyclerView = (RecyclerView) findViewById(R.id.rec_filter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new FilterAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    public FilterAdapter getAdapter() {
        return mAdapter;
    }

    public void notifyDataSetChange(List<FilterParamsBean> data) {
        mAdapter.invalidate(data);
        mAdapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(IDialogItemClickListener<FilterParamsBean> listener) {
        mItemClickListener = listener;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void onItemClick(View view, FilterParamsBean item, int position) {
        dismiss();
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(view, item, position);
        }
    }

    public void setGridLayoutManager(int count) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(), count));
        mAdapter.setGridLayoutCount(count);
    }

    public void setSelectedItem(FilterParamsBean item) {
        mAdapter.setSelectedItem(item);
    }


    public FilterParamsBean getSelectedItem() {
        return mAdapter.getSelectedItem();
    }
}
