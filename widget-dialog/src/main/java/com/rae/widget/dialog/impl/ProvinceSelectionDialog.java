package com.rae.widget.dialog.impl;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.rae.widget.ScrollerNumberPicker;
import com.rae.widget.dialog.R;
import com.rae.widget.dialog.model.LocationCityAreaInfoBean;
import com.rae.widget.dialog.model.LocationCityInfoBean;
import com.rae.widget.dialog.model.LocationProvinceInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 省份三级联动选择
 * Created by ChenRui on 2016/8/15 0015.
 */
public class ProvinceSelectionDialog extends SlideDialog {

    public interface ProvinceSelectionDismissListener {

        /**
         * 选择成功
         *
         * @param provinceName
         * @param cityName
         * @param areasName
         * @param provinceId
         * @param cityId
         * @param areasId
         */
        void onDismiss(String provinceName, String cityName, String areasName, String provinceId, String cityId, String areasId);
    }

    private final List<LocationProvinceInfoBean> mProvinceDataList;

    private ScrollerNumberPicker mProvinceView;
    private ScrollerNumberPicker mCityView;
    private ScrollerNumberPicker mAreasView;
    private ProvinceSelectionDismissListener mListener;

    public void setOnDismissListener(ProvinceSelectionDismissListener onDismissListener) {
        mListener = onDismissListener;
    }

    public ProvinceSelectionDialog(Context context, List<LocationProvinceInfoBean> data) {
        super(context);
        mProvinceDataList = data;

        setContentView(R.layout.widget_dialog_select_city);
        mProvinceView = (ScrollerNumberPicker) findViewById(R.id.snp_province);
        mCityView = (ScrollerNumberPicker) findViewById(R.id.snp_city);
        mAreasView = (ScrollerNumberPicker) findViewById(R.id.snp_areas);


        // 关闭
        findViewById(R.id.tv_province_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // 确定
        findViewById(R.id.tv_province_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                notifyDismiss();

            }
        });

        // 省份选择
        mProvinceView.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {
            @Override
            public void endSelect(int index, String text) {
                List<LocationCityInfoBean> cityList = mProvinceDataList.get(index).getCity(); // 城市列表
                ArrayList<String> cityData = toCityArrayString(cityList);

                mCityView.setData(cityData);
                mCityView.setDefault(0);

                // 清除地区的数据
                ArrayList<String> areasData;
                if (cityList.size() > 0 && cityList.get(0) != null) {
                    areasData = toAreasArrayString(cityList.get(0).getArea());
                } else {
                    areasData = new ArrayList<>();
                    areasData.add("");
                }
                mAreasView.setData(areasData);
                mAreasView.setDefault(0);
            }

            @Override
            public void selecting(int id, String text) {

            }
        });


        // 城市选择
        mCityView.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {
            @Override
            public void endSelect(int index, String text) {
                LocationCityInfoBean city = mProvinceDataList.get(mProvinceView.getSelected()).getCity().get(index); // 选择的城市
                ArrayList<String> cityData = toAreasArrayString(city.getArea());

                mAreasView.setData(cityData);
                mAreasView.setDefault(0);

            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        load();
    }

    private void notifyDismiss() {
        if (mListener != null) {
            mListener.onDismiss(
                    mProvinceView.getSelectedText(),
                    mCityView.getSelectedText(),
                    mAreasView.getSelectedText(),
                    mProvinceDataList.get(mProvinceView.getSelected()).getId(),
                    mProvinceDataList.get(mProvinceView.getSelected()).getCity().get(mCityView.getSelected()).getId(),
                    mProvinceDataList.get(mProvinceView.getSelected()).getCity().get(mCityView.getSelected()).getArea().get(mAreasView.getSelected()).getId()
            );
        }
    }


    /**
     * 第一次加载数据
     */
    private void load() {
        LocationProvinceInfoBean defaultProvince = mProvinceDataList.get(0);
        List<LocationCityInfoBean> defaultCityInfoBeenList = defaultProvince.getCity();
        List<LocationCityAreaInfoBean> defaultAreaInfoBeenList = defaultProvince.getCity().get(0).getArea();
        mProvinceView.setData(toProvinceArrayString(mProvinceDataList));
        mProvinceView.setDefault(0);

        mCityView.setData(toCityArrayString(defaultCityInfoBeenList));
        mCityView.setDefault(0);

        mAreasView.setData(toAreasArrayString(defaultAreaInfoBeenList));
        mAreasView.setDefault(0);

    }

    private ArrayList<String> toProvinceArrayString(List<LocationProvinceInfoBean> data) {
        ArrayList<String> result = new ArrayList<>();
        for (LocationProvinceInfoBean item : data) {
            result.add(item.getName());
        }
        return result;
    }

    private ArrayList<String> toCityArrayString(List<LocationCityInfoBean> data) {
        ArrayList<String> result = new ArrayList<>();
        for (LocationCityInfoBean item : data) {
            result.add(item.getName());
        }
        return result;
    }

    private ArrayList<String> toAreasArrayString(List<LocationCityAreaInfoBean> data) {
        ArrayList<String> result = new ArrayList<>();
        for (LocationCityAreaInfoBean item : data) {
            result.add(item.getName());
        }
        return result;
    }


    /**
     * 重新加载数据，三级联动
     *
     * @param provinceName 省份
     * @param cityName     城市
     * @param areasName    地区
     */
    public void invalidate(String provinceName, String cityName, String areasName) {
        if (TextUtils.isEmpty(provinceName) || TextUtils.isEmpty(cityName)) {
            return;
        }

        ArrayList<String> provinceData = new ArrayList<>();
        ArrayList<String> cityData = new ArrayList<>();
        ArrayList<String> areasData = new ArrayList<>();

        int defaultProvinceIndex = 0;
        int defaultCityIndex = 0;
        int defaultAreasIndex = 0;

        for (int index = 0; index < mProvinceDataList.size(); index++) {
            LocationProvinceInfoBean province = mProvinceDataList.get(index);
            provinceData.add(province.getName());
            if (provinceName != null && TextUtils.equals(provinceName, province.getName())) {
                defaultProvinceIndex = index;

                // 加载城市
                if (!TextUtils.isEmpty(cityName)) {
                    for (int cityIndex = 0; cityIndex < province.getCity().size(); cityIndex++) {
                        LocationCityInfoBean city = province.getCity().get(cityIndex);
                        cityData.add(city.getName());
                        if (TextUtils.equals(cityName, city.getName())) {
                            defaultCityIndex = cityIndex;
                            // 加载地区
                            for (int areasIndex = 0; areasIndex < city.getArea().size(); areasIndex++) {
                                LocationCityAreaInfoBean areas = city.getArea().get(areasIndex);
                                areasData.add(areas.getName());
                                if (TextUtils.equals(areasName, areas.getName())) {
                                    defaultAreasIndex = areasIndex;
                                }
                            }
                        }
                    }
                }
            }
        }


        mProvinceView.setData(provinceData);
        mCityView.setData(cityData);
        mAreasView.setData(areasData);

        try {
            mProvinceView.setDefault(defaultProvinceIndex);
            mCityView.setDefault(defaultCityIndex);
            mAreasView.setDefault(defaultAreasIndex);
        } catch (Exception e) {
            mProvinceView.setDefault(0);
            mCityView.setDefault(0);
            mAreasView.setDefault(0);
        }

    }

    /**
     * 自动设置数据，并回调
     */
    public void auto(String provinceName, String cityName, String areasName) {
        invalidate(provinceName, cityName, areasName);
        dismiss();
        notifyDismiss();
    }
}
