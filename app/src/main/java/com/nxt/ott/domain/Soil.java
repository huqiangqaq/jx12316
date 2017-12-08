package com.nxt.ott.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huqiang on 2017/5/23 11:00.
 */

public class Soil implements Parcelable {

    private double longitude; //经度
    private double latitude;   //纬度
    private String province;  //省
    private String city;  //市
    private String district;   //县
    private String street;
    private String streetNumber;
    private float radius;

    public Soil() {
    }

    public Soil(double longitude, double latitude,String province, String city, String district, String street, String streetNumber, float radius) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.province = province;
        this.city = city;
        this.district = district;
        this.street = street;
        this.streetNumber = streetNumber;
        this.radius = radius;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.district);
        dest.writeString(this.street);
        dest.writeString(this.streetNumber);
        dest.writeFloat(this.radius);
    }

    protected Soil(Parcel in) {
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.province = in.readString();
        this.city = in.readString();
        this.district = in.readString();
        this.street = in.readString();
        this.streetNumber = in.readString();
        this.radius = in.readFloat();
    }

    public static final Parcelable.Creator<Soil> CREATOR = new Parcelable.Creator<Soil>() {
        @Override
        public Soil createFromParcel(Parcel source) {
            return new Soil(source);
        }

        @Override
        public Soil[] newArray(int size) {
            return new Soil[size];
        }
    };
}
