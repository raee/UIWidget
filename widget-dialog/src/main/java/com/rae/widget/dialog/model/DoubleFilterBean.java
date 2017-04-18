package com.rae.widget.dialog.model;

import java.util.List;

/**
 * Created by ChenRui on 2017/3/31 0031 1:06.
 */
public class DoubleFilterBean {
    private FilterParamsBean mParent;
    private List<FilterParamsBean> mChildren;

    public FilterParamsBean getParent() {
        return mParent;
    }

    public void setParent(FilterParamsBean parent) {
        mParent = parent;
    }

    public List<FilterParamsBean> getChildren() {
        return mChildren;
    }

    public void setChildren(List<FilterParamsBean> children) {
        mChildren = children;
    }
}
