package com.rae.widget.dialog.model;

import android.text.TextUtils;

/**
 * 参数筛选
 * Created by ChenRui on 2017/3/30 0030 23:43.
 */
public class FilterParamsBean {

    private String id;

    private String pid;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FilterParamsBean() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public FilterParamsBean(String name) {
        this.name = name;
    }

    public FilterParamsBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FilterParamsBean) {
            FilterParamsBean m = (FilterParamsBean) obj;
            return TextUtils.equals(m.getName(), this.name) && TextUtils.equals(m.getId(), this.id);
        }
        return super.equals(obj);
    }
}
