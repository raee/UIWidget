package com.rae.widget.dialog.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class LocationProvinceInfoBean implements Parcelable {
    private String id;
    private String name;
    private List<LocationCityInfoBean> city;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LocationCityInfoBean> getCity() {
        return this.city;
    }

    public void setCity(List<LocationCityInfoBean> city) {
        this.city = city;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.city);
    }

    public LocationProvinceInfoBean() {
    }

    protected LocationProvinceInfoBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.city = in.createTypedArrayList(LocationCityInfoBean.CREATOR);
    }

    public static final Creator<LocationProvinceInfoBean> CREATOR = new Creator<LocationProvinceInfoBean>() {
        @Override
        public LocationProvinceInfoBean createFromParcel(Parcel source) {
            return new LocationProvinceInfoBean(source);
        }

        @Override
        public LocationProvinceInfoBean[] newArray(int size) {
            return new LocationProvinceInfoBean[size];
        }
    };
}
