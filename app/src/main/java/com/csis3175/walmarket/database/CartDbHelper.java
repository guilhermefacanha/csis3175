package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.csis3175.walmarket.entity.Cart;

public class CartDbHelper {
    public final static String TABLE_CART = "CART";
    public final static String TABLE_CART_ID = "CART_ID";
    public final static String TABLE_CART_USER_ID = "USER_ID";
    public final static String TABLE_CART_STORE_ID = "STORE_ID";
    public final static String TABLE_CART_TOTAL = "TOTAL";

    private DatabaseHelper db;

    public CartDbHelper(Context context) {
        db = new DatabaseHelper(context);
    }

    public static String getCreation() {
        return "CREATE TABLE " + TABLE_CART + "(" +
                TABLE_CART_ID + " INTEGER PRIMARY KEY," +
                TABLE_CART_USER_ID + " INTEGER," +
                TABLE_CART_STORE_ID + " INTEGER," +
                TABLE_CART_TOTAL + " REAL " +
                ")";
    }

    public long addCart(Cart cart) {
        ContentValues values = new ContentValues();
        values.put(TABLE_CART_USER_ID, cart.getUserId());
        values.put(TABLE_CART_STORE_ID, cart.getStoreId());
        values.put(TABLE_CART_TOTAL, cart.getTotal());

        return db.addRecord(TABLE_CART, values);
    }

    public boolean updateCart(Cart cart) {
        ContentValues values = new ContentValues();
        values.put(TABLE_CART_USER_ID, cart.getUserId());
        values.put(TABLE_CART_STORE_ID, cart.getStoreId());
        values.put(TABLE_CART_TOTAL, cart.getTotal());

        String[] params = new String[]{String.valueOf(cart.getCartId())};

        String where = TABLE_CART_ID + " = ?";

        return db.updateRecord(TABLE_CART, values, where, params);
    }

    public Cart getCartById(Integer cartId) {
        Cart cart = null;

        String query = "SELECT " +
                TABLE_CART_USER_ID + ", " +
                TABLE_CART_STORE_ID + ", " +
                TABLE_CART_TOTAL +
                " FROM " + TABLE_CART +
                " WHERE " + TABLE_CART_ID + " = ?";
        String[] params = new String[]{String.valueOf(cart.getCartId())};
        Cursor data = db.getData(query, params);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                cart = new Cart();
                int i = 0;
                cart.setCartId(cartId);
                cart.setUserId(data.getInt(i++));
                cart.setStoreId(data.getInt(i++));
                cart.setTotal(data.getDouble(i++));
            }
        }
        return cart;
    }

    public Cart getLastCartByUserStore(Integer userId, Integer storeId) {
        Cart cart = null;

        String query = "SELECT * FROM " + TABLE_CART +
                " WHERE " + TABLE_CART_STORE_ID + " = ? " +
                " AND " + TABLE_CART_USER_ID + " = ? ORDER BY " + TABLE_CART_ID;
        String[] params = new String[]{String.valueOf(storeId), String.valueOf(userId)};
        Cursor data = db.getData(query, params);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                cart = new Cart();
                int i = 0;
                cart.setCartId(data.getInt(i++));
                cart.setUserId(data.getInt(i++));
                cart.setStoreId(data.getInt(i++));
                cart.setTotal(data.getDouble(i++));

                return cart;
            }
        }
        return cart;

    }

    public void deleteCart(Integer cartId) {
        String query = "DELETE FROM "+TABLE_CART+" WHERE "+TABLE_CART_ID+" = ?";
        Object[] params = {cartId};
        db.deleteRecord(query,params);
    }
}
