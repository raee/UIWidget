package com.rae.widget.dialog.impl;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rae.widget.dialog.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 弹出选择框，记得调用{@link #setDataList(List)}来设置数据源。
 * Created by ChenRui on 2016/5/25 0025.
 */
public class SelectionDialog extends SlideDialog {

    private final ListView mListView;
    private SelectionInfo mCurrentItem;
    private SelectionListener mOnSelectionListener;
    private SelectionInfo mCancelInfo;
    private boolean mCancelable;

    public interface SelectionListener {
        void onItemSelected(SelectionInfo item);
    }

    /**
     * 数据源，可以通过{@link SelectionInfo#toArrayList(String)}来获取实例
     */
    public static class SelectionInfo {
        public String title; // 标题
        public String fn; // 方法名称
        public String color; // 颜色
        public int imageResId; // 图标

        public SelectionInfo(String title) {
            this.title = title;
        }

        public SelectionInfo(String title, String fn) {
            this.title = title;
            this.fn = fn;
        }

        public SelectionInfo(String title, String fn, String color) {
            this.title = title;
            this.fn = fn;
            this.color = color;
        }

        public SelectionInfo(JSONObject obj) throws JSONException {

            title = obj.getString("title");
            fn = obj.getString("fn");

            if (obj.has("color")) {
                color = obj.getString("color");
            }
        }

        public static List<SelectionInfo> toArrayList(String json) {
            List<SelectionInfo> result = new ArrayList<>();
            try {
                // array
                if (json.startsWith("[")) {
                    JSONArray arr = new JSONArray(json);
                    for (int i = 0; i < arr.length(); i++) {
                        result.add(new SelectionInfo(arr.getJSONObject(i)));
                    }
                } else {
                    result.add(new SelectionInfo(new JSONObject(json)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    /**
     * 获取当前值
     *
     * @return
     */
    public SelectionInfo getCurrentItem() {
        return mCurrentItem;
    }


    public SelectionDialog(Context context) {
        super(context);
        setContentView(R.layout.view_selection_window);
        mListView = (ListView) findViewById(R.id.lv_selection_window);
    }

    public void setOnSelectionListener(SelectionListener selectionListener) {
        mOnSelectionListener = selectionListener;
    }

    public void setDataList(List<SelectionInfo> data) {
        if (mCancelInfo == null && mCancelable) {
            mCancelInfo = new SelectionInfo(getContext().getString(R.string.widget_dialog_cancel), "app.dialog.cancel()");
            mCancelInfo.color = "#555555";
        }
        SelectionWindowAdapter adapter = new SelectionWindowAdapter(data);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(adapter);
        adapter.notifyDataSetChanged();
    }

    public List<SelectionInfo> getDataList() {
        if (mListView.getAdapter() == null) return null;
        return ((SelectionWindowAdapter) mListView.getAdapter()).getDataList();
    }

    public boolean currentItemIsCancel() {
        return getCurrentItem() != null && getCurrentItem().title.equalsIgnoreCase(getContext().getString(R.string.widget_dialog_cancel));
    }


    @Override
    public void show() {
        if (mCancelable && mCancelInfo != null && getDataList() != null && getDataList().size() > 0 && !getDataList().contains(mCancelInfo)) {
            getDataList().add(mCancelInfo);
        }
        super.show();
        mCurrentItem = null;
    }

    @Override
    public void setCancelable(boolean flag) {
        mCancelable = flag;
    }

    private class SelectionWindowAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

        private final List<SelectionInfo> mDataList;
        private ColorStateList mColorStateList;

        public SelectionWindowAdapter(List<SelectionInfo> data) {
            this.mDataList = data;
        }

        @Override
        public int getCount() {
            if (mDataList == null) return 0;
            return mDataList.size();
        }

        public List<SelectionInfo> getDataList() {
            return mDataList;
        }

        @Override
        public Object getItem(int i) {
            return mDataList.get(i % getCount());
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            SelectionInfo item = (SelectionInfo) getItem(i);
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.widget_dialog_item_selection_window, viewGroup, false);
            }
            TextView titleView = view.findViewById(R.id.tv_selection_window_title);
            titleView.setText(item.title);
            View spanView = view.findViewById(R.id.view_item_selection_span);

            if (!TextUtils.isEmpty(item.color)) {
                titleView.setTextColor(Color.parseColor(item.color));
            } else {

                if (mColorStateList == null) {
                    mColorStateList = titleView.getTextColors();
                }
                // 设置默认的颜色
                titleView.setTextColor(mColorStateList);
            }
            if (item.imageResId > 0) {
                titleView.setCompoundDrawablesWithIntrinsicBounds(item.imageResId, 0, 0, 0);
            }

            // 设置背景颜色

//            // 如果是第一个
//            if (i == 0 && getCount() == 2) { // 只有一个记录
//                titleView.setBackgroundResource(R.drawable.selector_selection_window);
//            } else if (i == 0) {
//                titleView.setBackgroundResource(R.drawable.selector_selection_window_top);
//            } else if (i + 2 == getCount()) {
//                titleView.setBackgroundResource(R.drawable.selector_selection_window_bottom);
//            } else {
//                titleView.setBackgroundResource(R.drawable.selector_selection_window_middle);
//            }


            // 最后一个是取消，要有一定的间距
            if (i + 1 == getCount()) {
                spanView.setVisibility(View.VISIBLE);
//                titleView.setBackgroundResource(R.drawable.selector_selection_window);
            } else {
                spanView.setVisibility(View.GONE);
            }

            return view;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            mCurrentItem = (SelectionInfo) getItem(i);
            // 最后一个为取消
            if (currentItemIsCancel()) {
                mCurrentItem = null;
                dismiss();
            } else {
                onCurrentItemClick(mCurrentItem);
            }
        }
    }

    protected void onCurrentItemClick(SelectionInfo currentItem) {
        if (mOnSelectionListener != null) {
            mOnSelectionListener.onItemSelected(currentItem);
        }
        dismiss();
    }
}
