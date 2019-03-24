package com.csis3175.walmarket.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class CartItem {
    private Integer cartId;
    private Integer itemId;
    private Double price;
    private Integer quantity;

    private String itemDescription;
    private byte[] itemImage;

    public Double getTotal() {
        Double total = price * quantity;
        return new BigDecimal(total.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public String getTotalStr() {
        return NumberFormat.getCurrencyInstance().format(getTotal());
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
