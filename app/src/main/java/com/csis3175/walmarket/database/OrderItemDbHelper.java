package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.csis3175.walmarket.entity.Item;
import com.csis3175.walmarket.entity.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderItemDbHelper {

    public final static String TABLE_ORDER_ITEM = "ORDER_ITEM";
    public final static String TABLE_ORDER_ITEM_ORDER_ID = "ORDER_ID";
    public final static String TABLE_ORDER_ITEM_ITEM_ID = "ITEM_ID";
    public final static String TABLE_ORDER_ITEM_PRICE = "PRICE";
    public final static String TABLE_ORDER_ITEM_QTY = "QUANTITY";

    private DatabaseHelper db;

    public OrderItemDbHelper(Context context) {
        db = new DatabaseHelper(context);
    }

    public static String getCreation() {
        return "CREATE TABLE " + TABLE_ORDER_ITEM + "(" +
                TABLE_ORDER_ITEM_ORDER_ID + " INTEGER," +
                TABLE_ORDER_ITEM_ITEM_ID + " INTEGER," +
                TABLE_ORDER_ITEM_PRICE + " REAL," +
                TABLE_ORDER_ITEM_QTY + " INTEGER," +
                " PRIMARY KEY (" +
                TABLE_ORDER_ITEM_ORDER_ID + ", " +
                TABLE_ORDER_ITEM_ITEM_ID + ") " +
                "FOREIGN KEY(" + TABLE_ORDER_ITEM_ORDER_ID + ") " +
                "  REFERENCES " + OrderDbHelper.TABLE_ORDER + "(" + OrderDbHelper.TABLE_ORDER_ID + ")" +

                "FOREIGN KEY(" + TABLE_ORDER_ITEM_ITEM_ID + ") " +
                "  REFERENCES " + ItemDbHelper.TABLE_ITEM + "(" + ItemDbHelper.TABLE_ITEM_ID + ")" +
                ") WITHOUT ROWID";

    }

    public long addItem(OrderItem orderItem) {
        ContentValues values = new ContentValues();
        values.put(TABLE_ORDER_ITEM_ORDER_ID, orderItem.getOrderId());
        values.put(TABLE_ORDER_ITEM_ITEM_ID, orderItem.getItemId());
        values.put(TABLE_ORDER_ITEM_PRICE, orderItem.getPrice());
        values.put(TABLE_ORDER_ITEM_QTY, orderItem.getQuantity());

        return db.addRecord(TABLE_ORDER_ITEM, values);
    }

    public boolean updateItem(OrderItem orderItem) {
        ContentValues values = new ContentValues();
        values.put(TABLE_ORDER_ITEM_ORDER_ID, orderItem.getOrderId());
        values.put(TABLE_ORDER_ITEM_ITEM_ID, orderItem.getItemId());
        values.put(TABLE_ORDER_ITEM_PRICE, orderItem.getPrice());
        values.put(TABLE_ORDER_ITEM_QTY, orderItem.getQuantity());

        String[] params = new String[]{String.valueOf(orderItem.getOrderId()), String.valueOf(orderItem.getItemId())};

        String where = TABLE_ORDER_ITEM_ORDER_ID + " = ? AND " + TABLE_ORDER_ITEM_ITEM_ID + " = ?";

        return db.updateRecord(TABLE_ORDER_ITEM, values, where, params);
    }

    public OrderItem getOrderItemById(Integer orderId, Integer itemId) {
        OrderItem orderItem = null;

        String query = "SELECT * " +
                " FROM " + TABLE_ORDER_ITEM +
                " WHERE " + TABLE_ORDER_ITEM_ORDER_ID + " = ? AND " + TABLE_ORDER_ITEM_ITEM_ID + " = ?";
        String[] params = new String[]{String.valueOf(orderId), String.valueOf(itemId)};
        Cursor data = db.getData(query, params);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                orderItem = new OrderItem();
                int i = 0;
                orderItem.setOrderId(data.getInt(i++));
                orderItem.setItemId(data.getInt(i++));
                orderItem.setPrice(data.getDouble(i++));
                orderItem.setQuantity(data.getInt(i++));
            }
        }
        return orderItem;
    }

    public List<OrderItem> getOrderItems(Integer orderId) {
        List<OrderItem> list = new ArrayList<>();

        String query = "SELECT ORDER_ITEM.*, ITEM.DESCRIPTION, ITEM.IMAGE " +
                        " FROM ORDER_ITEM " +
                        " INNER JOIN ITEM ON ITEM.ITEM_ID = ORDER_ITEM.ITEM_ID " +
                        " WHERE ORDER_ITEM.ORDER_ID = ?";
        String[] params = new String[]{String.valueOf(orderId)};
        Cursor data = db.getData(query, params);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                OrderItem orderItem = new OrderItem();
                int i = 0;
                orderItem.setOrderId(data.getInt(i++));
                orderItem.setItemId(data.getInt(i++));
                orderItem.setPrice(data.getDouble(i++));
                orderItem.setQuantity(data.getInt(i++));
                orderItem.setItemDescription(data.getString(i++));
                orderItem.setItemImage(data.getBlob(i++));

                list.add(orderItem);
            }
        }


        return list;
    }
}
