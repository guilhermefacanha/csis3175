package com.csis3175.walmarket.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class ItemStore {
    private Integer itemId;
    private Integer storeId;
    private Double price;
    private Double discRate;

    private String itemDescription;
    private byte[] itemImage;

    public String getFinalPrice() {
        NumberFormat fm = NumberFormat.getCurrencyInstance();
        double finalPrice = price;
        if (discRate > 0) {
            double rate = 1 - (discRate / 100);
            finalPrice = finalPrice * rate;
        }
        return fm.format(finalPrice);
    }

    public Double getFinalPriceDouble() {
        Double finalPrice = price;
        if (discRate > 0) {
            double rate = 1 - (discRate / 100);
            finalPrice = finalPrice * rate;
        }

        return new BigDecimal(finalPrice.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscRate() {
        return discRate;
    }

    public void setDiscRate(Double discRate) {
        this.discRate = discRate;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }


}
