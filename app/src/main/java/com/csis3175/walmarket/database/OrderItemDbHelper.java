package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.csis3175.walmarket.entity.Item;
import com.csis3175.walmarket.entity.OrderItem;

public class OrderItemDbHelper {

    public final static String TABLE_ORDER_ITEM = "ORDER_ITEM";
    public final static String TABLE_ORDER_ITEM_ORDER_ID = "ORDER_ID";
    public final static String TABLE_ORDER_ITEM_ITEM_ID = "ITEM_ID";
    public final static String TABLE_ORDER_ITEM_PRICE= "PRICE";
    public final static String TABLE_ORDER_ITEM_QTY = "QUANTITY";

    private DatabaseHelper db;

    public OrderItemDbHelper(Context context){
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
                ") WITHOUT ROWID";

    }

    public long addItem(OrderItem orderItem){
        ContentValues values = new ContentValues();
        values.put(TABLE_ORDER_ITEM_ORDER_ID, orderItem.getOrderId());
        values.put(TABLE_ORDER_ITEM_ITEM_ID, orderItem.getItemId());
        values.put(TABLE_ORDER_ITEM_PRICE, orderItem.getPrice());
        values.put(TABLE_ORDER_ITEM_QTY, orderItem.getQuantity());

        return db.addRecord(TABLE_ORDER_ITEM, values);
    }

    public boolean updateItem(OrderItem orderItem){
        ContentValues values = new ContentValues();
        values.put(TABLE_ORDER_ITEM_ORDER_ID, orderItem.getOrderId());
        values.put(TABLE_ORDER_ITEM_ITEM_ID, orderItem.getItemId());
        values.put(TABLE_ORDER_ITEM_PRICE, orderItem.getPrice());
        values.put(TABLE_ORDER_ITEM_QTY, orderItem.getQuantity());

        String[] params = new String[]{ String.valueOf(orderItem.getOrderId()),String.valueOf(orderItem.getItemId()) };

        String where =  TABLE_ORDER_ITEM_ORDER_ID + " = ? AND "+TABLE_ORDER_ITEM_ITEM_ID+" = ?";

        return db.updateRecord(TABLE_ORDER_ITEM,values,where,params);
    }

    public OrderItem getOrderItemById(Integer orderId, Integer itemId){
        OrderItem orderItem = null;

        String query = "SELECT * "+
                " FROM " + TABLE_ORDER_ITEM +
                " WHERE "+ TABLE_ORDER_ITEM_ORDER_ID + " = ? AND "+TABLE_ORDER_ITEM_ITEM_ID+" = ?";
        String[] params = new String[]{ String.valueOf(orderId),String.valueOf(itemId) };
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

}
