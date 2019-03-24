package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.csis3175.walmarket.entity.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartItemDbHelper {
    public final static String TABLE_CART_ITEM = "CART_ITEM";
    public final static String TABLE_CART_ITEM_CART_ID = "CART_ID";
    public final static String TABLE_CART_ITEM_ITEM_ID = "ITEM_ID";
    public final static String TABLE_CART_ITEM_PRICE = "PRICE";
    public final static String TABLE_CART_ITEM_QUANTITY = "QUANTITY";

    private DatabaseHelper db;

    public CartItemDbHelper(Context context) {
        db = new DatabaseHelper(context);
    }

    public static String getCreation() {
        return "CREATE TABLE " + TABLE_CART_ITEM + "(" +
                TABLE_CART_ITEM_CART_ID + " INTEGER," +
                TABLE_CART_ITEM_ITEM_ID + " INTEGER," +
                TABLE_CART_ITEM_PRICE + " REAL," +
                TABLE_CART_ITEM_QUANTITY + " INTEGER," +
                " PRIMARY KEY (" +
                TABLE_CART_ITEM_CART_ID + ", " +
                TABLE_CART_ITEM_ITEM_ID + ") " +
                ") WITHOUT ROWID";
    }

    public boolean addCartItem(CartItem cartItem) {
        ContentValues values = new ContentValues();
        values.put(TABLE_CART_ITEM_CART_ID, cartItem.getCartId());
        values.put(TABLE_CART_ITEM_ITEM_ID, cartItem.getItemId());
        values.put(TABLE_CART_ITEM_PRICE, cartItem.getPrice());
        values.put(TABLE_CART_ITEM_QUANTITY, cartItem.getQuantity());

        return db.addRecord(TABLE_CART_ITEM, values);
    }

    public boolean updateCart(CartItem cartItem) {
        ContentValues values = new ContentValues();
        values.put(TABLE_CART_ITEM_PRICE, cartItem.getPrice());
        values.put(TABLE_CART_ITEM_QUANTITY, cartItem.getQuantity());

        String[] params = new String[]{String.valueOf(cartItem.getCartId()),
                String.valueOf(cartItem.getItemId())};

        String where = TABLE_CART_ITEM_CART_ID + " = ? " +
                "AND " + TABLE_CART_ITEM_ITEM_ID + " = ? ";

        return db.updateRecord(TABLE_CART_ITEM, values, where, params);
    }

    public CartItem getCartItemById(Integer cartId, Integer itemId) {
        CartItem cartItem = null;

        String query = "SELECT * FROM " + TABLE_CART_ITEM +
                " WHERE " + TABLE_CART_ITEM_CART_ID + " = ? " +
                " AND " + TABLE_CART_ITEM_ITEM_ID + " = ? ";
        String[] params = new String[]{String.valueOf(cartId), String.valueOf(itemId)};
        Cursor data = db.getData(query, params);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                cartItem = new CartItem();
                int i = 0;
                cartItem.setCartId(data.getInt(i++));
                cartItem.setItemId(data.getInt(i++));
                cartItem.setPrice(data.getDouble(i++));
                cartItem.setQuantity(data.getInt(i++));

                return cartItem;
            }
        }
        return cartItem;
    }

    public List<CartItem> getCartItemsByCartId(Integer cartId) {
        List<CartItem> list = new ArrayList<>();

        CartItem cartItem = null;

        String query = "SELECT CART_ITEM.*, ITEM.DESCRIPTION, ITEM.IMAGE " +
                " FROM " + TABLE_CART_ITEM +
                " INNER JOIN ITEM ON ITEM.ITEM_ID = CART_ITEM.ITEM_ID " +
                " WHERE " + TABLE_CART_ITEM_CART_ID + " = ? ";
        String[] params = new String[]{String.valueOf(cartId)};
        Cursor data = db.getData(query, params);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                cartItem = new CartItem();
                int i = 0;
                cartItem.setCartId(data.getInt(i++));
                cartItem.setItemId(data.getInt(i++));
                cartItem.setPrice(data.getDouble(i++));
                cartItem.setQuantity(data.getInt(i++));
                cartItem.setItemDescription(data.getString(i++));
                cartItem.setItemImage(data.getBlob(i++));

                list.add(cartItem);
            }
        }

        return list;
    }
}
