package com.rae.widget.dialog.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rae.widget.dialog.R;
import com.rae.widget.dialog.model.FilterParamsBean;

/**
 * Created by ChenRui on 2017/3/31 0031 0:55.
 */
class FilterAdapter extends BaseAdapter<FilterParamsBean, FilterHolder> {

    private boolean isSubMenu; // 是否为二级菜单

    private FilterParamsBean mSelectedItem;
    private int mIsGridLayoutCount;
    private boolean mDisableSelectedView;
    private int mSelectedItemPosition;

    public void setSubMenu(boolean subMenu) {
        isSubMenu = subMenu;
    }

    @Override
    public FilterHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new FilterHolder(inflateView(parent, R.layout.widget_dialog_item_filter));
    }

    @Override
    public void onBindViewHolder(FilterHolder holder, int position, FilterParamsBean m) {
        holder.getNameView().setText(m.getName());
        if (isSubMenu) {
            holder.itemView.setBackgroundResource(R.drawable.widget_dialog_bg_divider_filter_item);
        }

        if (mIsGridLayoutCount > 0) {
            holder.getDividerView().setVisibility(View.VISIBLE);
        } else {
            holder.getDividerView().setVisibility(View.GONE);
        }

        holder.itemView.setSelected(m == mSelectedItem || mSelectedItemPosition == position);
        if (mDisableSelectedView) {
            holder.getSelectedView().setVisibility(View.GONE);
        } else {
            holder.getSelectedView().setVisibility((m == mSelectedItem) ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    protected void onItemClick(View view, FilterParamsBean item, int position) {
        super.onItemClick(view, item, position);
        mSelectedItem = item;
        notifyDataSetChanged();
    }

    public void setGridLayoutCount(int count) {
        mIsGridLayoutCount = count;
    }

    public void setDisableSelectedView(boolean disableSelectedView) {
        mDisableSelectedView = disableSelectedView;
    }

    public void setSelectedItem(int position) {
        mSelectedItemPosition = position;
    }
}
