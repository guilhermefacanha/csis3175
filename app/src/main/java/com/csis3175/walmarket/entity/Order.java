package com.csis3175.walmarket.entity;

public class Order {
    private Integer orderId;
    private Integer userId;
    private Integer storeId;
    private String date;
    private String status;
    private String deliverPickupOpt;
    private Double deliverCharge;
    private Double friendDiscount;
    private Double total;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliverPickupOpt() {
        return deliverPickupOpt;
    }

    public void setDeliverPickupOpt(String deliverPickupOpt) {
        this.deliverPickupOpt = deliverPickupOpt;
    }

    public Double getDeliverCharge() {
        return deliverCharge;
    }

    public void setDeliverCharge(Double deliverCharge) {
        this.deliverCharge = deliverCharge;
    }

    public Double getFriendDiscount() {
        return friendDiscount;
    }

    public void setFriendDiscount(Double friendDiscount) {
        this.friendDiscount = friendDiscount;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
