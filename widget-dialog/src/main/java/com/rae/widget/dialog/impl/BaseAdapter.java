package com.rae.widget.dialog.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * ITEM 适配器
 * Created by ChenRui on 2016/12/15 22:50.
 */
abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public interface onItemClickListener<T> {
        void onItemClick(View view, T item, int position);
    }

    protected List<T> mDataList;
    private LayoutInflater mLayoutInflater;
    protected Context mContext;

    private onItemClickListener<T> mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener<T> listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }

        return onCreateViewHolder(mLayoutInflater, parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final T dataItem = getDataItem(position);
        onBindViewHolder(holder, position, dataItem);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = mDataList.indexOf(dataItem);
                    onItemClick(v, dataItem, index);
                    mOnItemClickListener.onItemClick(v, dataItem, index);
                }
            });
        }
    }

    protected void onItemClick(View view, T item, int position) {

    }

    public abstract VH onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(VH holder, int position, T m);

    protected T getDataItem(int position) {
        return mDataList == null ? null : mDataList.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public void invalidate(List<T> data) {
        mDataList = data;
    }


    public void remove(T item) {
        mDataList.remove(item);
    }

    protected View inflateView(ViewGroup parent, int layoutId) {
        return mLayoutInflater.inflate(layoutId, parent, false);
    }

    public int getItemPosition(T item) {
        return mDataList.indexOf(item);
    }
}
