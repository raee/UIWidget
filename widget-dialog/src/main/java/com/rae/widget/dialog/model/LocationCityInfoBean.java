package com.rae.widget.dialog.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class LocationCityInfoBean implements Parcelable {
    private String id;
    private List<LocationCityAreaInfoBean> area;
    private String name;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LocationCityAreaInfoBean> getArea() {
        return this.area;
    }

    public void setArea(List<LocationCityAreaInfoBean> area) {
        this.area = area;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeTypedList(this.area);
        dest.writeString(this.name);
    }

    public LocationCityInfoBean() {
    }

    protected LocationCityInfoBean(Parcel in) {
        this.id = in.readString();
        this.area = in.createTypedArrayList(LocationCityAreaInfoBean.CREATOR);
        this.name = in.readString();
    }

    public static final Creator<LocationCityInfoBean> CREATOR = new Creator<LocationCityInfoBean>() {
        @Override
        public LocationCityInfoBean createFromParcel(Parcel source) {
            return new LocationCityInfoBean(source);
        }

        @Override
        public LocationCityInfoBean[] newArray(int size) {
            return new LocationCityInfoBean[size];
        }
    };
}
