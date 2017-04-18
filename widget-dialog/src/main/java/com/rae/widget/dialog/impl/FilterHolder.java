package com.rae.widget.dialog.impl;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rae.widget.dialog.R;


/**
 * Created by ChenRui on 2017/3/31 0031 0:55.
 */
public class FilterHolder extends RecyclerView.ViewHolder {

    TextView mNameView;
    View mDividerView;
    ImageView mSelectedView;

    public TextView getNameView() {
        return mNameView;
    }

    public View getDividerView() {
        return mDividerView;
    }

    public ImageView getSelectedView() {
        return mSelectedView;
    }

    public FilterHolder(View itemView) {
        super(itemView);
        mNameView = (TextView) itemView.findViewById(R.id.tv_name);
        mDividerView = itemView.findViewById(R.id.view_divider);
        mSelectedView = (ImageView) itemView.findViewById(R.id.img_filter_selected);
    }
}
