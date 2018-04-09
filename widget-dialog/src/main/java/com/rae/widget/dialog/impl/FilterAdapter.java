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
    private int mSelectedItemPosition = -1;
    private int mLayoutId = R.layout.widget_dialog_item_filter;

    public void setSubMenu(boolean subMenu) {
        isSubMenu = subMenu;
    }

    public void setLayoutId(int layoutId) {
        mLayoutId = layoutId;
    }

    @Override
    public FilterHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new FilterHolder(inflateView(parent, mLayoutId));
    }

    @Override
    public void onBindViewHolder(FilterHolder holder, int position, FilterParamsBean m) {
        holder.getNameView().setText(m.getName());

        // 背景颜色
        int backgroundResId;
        // 是否为最后一个
        boolean isLastItem = position == getItemCount() - 1;

        if (isSubMenu) {
            backgroundResId = isLastItem ? R.drawable.widget_dialog_bg_filter_item : R.drawable.widget_dialog_bg_divider_filter_item;
        } else {
            backgroundResId = isLastItem ? R.drawable.widget_dialog_public_bg : R.drawable.widget_dialog_public_divider_bg;
        }

        holder.itemView.setBackgroundResource(backgroundResId);

        if (mIsGridLayoutCount > 0) {
            holder.getDividerView().setVisibility(View.VISIBLE);
        } else {
            holder.getDividerView().setVisibility(View.GONE);
        }

        boolean isSelected = m.equals(mSelectedItem) || mSelectedItemPosition == position;
        holder.itemView.setSelected(isSelected);
        holder.getNameView().setSelected(isSelected);
        if (mDisableSelectedView) {
            holder.getSelectedView().setVisibility(View.GONE);
        } else {
            holder.getSelectedView().setVisibility((m.equals(mSelectedItem) ? View.VISIBLE : View.GONE));
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

    public void setSelectedItem(FilterParamsBean selectedItem) {
        mSelectedItem = selectedItem;
    }

    public FilterParamsBean getSelectedItem() {
        return mSelectedItem;
    }
}
