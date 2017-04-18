package com.rae.widget.dialog.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationCityAreaInfoBean implements Parcelable {
    public static final Creator<LocationCityAreaInfoBean> CREATOR = new Creator<LocationCityAreaInfoBean>() {
        @Override
        public LocationCityAreaInfoBean createFromParcel(Parcel source) {
            LocationCityAreaInfoBean var = new LocationCityAreaInfoBean();
            var.id = source.readString();
            var.name = source.readString();
            return var;
        }

        @Override
        public LocationCityAreaInfoBean[] newArray(int size) {
            return new LocationCityAreaInfoBean[size];
        }
    };
    private String id;
    private String name;

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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
