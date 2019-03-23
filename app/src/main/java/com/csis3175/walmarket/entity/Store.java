package com.csis3175.walmarket.entity;

import java.text.NumberFormat;

public class Store implements Comparable<Store> {

    public static String DISTANCE_MEASURE = "meters";

    private Integer storeId;
    private String name;
    private Double latitude;
    private Double longitude;
    private String address;

    private Float distance;

    public String getFormatedDistance(){
        float distKm = distance / 1000;
        NumberFormat fm = NumberFormat.getNumberInstance();
        fm.setMaximumFractionDigits(2);
        return fm.format(distKm) +" km";
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Store o) {
        return this.getDistance().compareTo(o.getDistance());
    }
}
