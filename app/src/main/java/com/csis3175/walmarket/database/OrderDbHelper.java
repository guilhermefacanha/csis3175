package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.csis3175.walmarket.entity.CartItem;
import com.csis3175.walmarket.entity.Order;
import com.csis3175.walmarket.entity.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderDbHelper {
    public final static String TABLE_ORDER = "ORDER_TB";
    public final static String TABLE_ORDER_ID = "ORDER_ID";
    public final static String TABLE_ORDER_USER_ID = "USER_ID";
    public final static String TABLE_ORDER_STORE_ID = "STORE_ID";
    public final static String TABLE_ORDER_DATE = "DATE";
    public final static String TABLE_ORDER_STATUS = "STATUS";
    public final static String TABLE_ORDER_DELIVER_PICKUP_OPT = "DELIVER_PICKUP_OPT";
    public final static String TABLE_ORDER_DELIVER_CHARGE = "DELIVER_CHARGE";
    public final static String TABLE_ORDER_FRIEND_DISCOUNT = "FRIEND_DISCOUNT";
    public final static String TABLE_ORDER_TOTAL = "TOTAL";

    private DatabaseHelper db;

    public OrderDbHelper(Context context) {
        db = new DatabaseHelper(context);
    }

    public static String getCreation() {
        return "CREATE TABLE " + TABLE_ORDER + "(" +
                TABLE_ORDER_ID + " INTEGER PRIMARY KEY," +
                TABLE_ORDER_USER_ID + " INTEGER," +
                TABLE_ORDER_STORE_ID + " INTEGER," +
                TABLE_ORDER_DATE + " TEXT," +
                TABLE_ORDER_STATUS + " TEXT," +
                TABLE_ORDER_DELIVER_PICKUP_OPT + " TEXT, " +
                TABLE_ORDER_DELIVER_CHARGE + " REAL, " +
                TABLE_ORDER_FRIEND_DISCOUNT + " REAL, " +
                TABLE_ORDER_TOTAL + " REAL " +
                ")";
    }

    public long addOrder(Order order) {
        ContentValues values = new ContentValues();
        values.put(TABLE_ORDER_USER_ID, order.getUserId());
        values.put(TABLE_ORDER_STORE_ID, order.getStoreId());
        values.put(TABLE_ORDER_DATE, order.getDate());
        values.put(TABLE_ORDER_STATUS, order.getStatus());
        values.put(TABLE_ORDER_DELIVER_PICKUP_OPT, order.getDeliverPickupOpt());
        values.put(TABLE_ORDER_DELIVER_CHARGE, order.getDeliverCharge());
        values.put(TABLE_ORDER_FRIEND_DISCOUNT, order.getFriendDiscount());
        values.put(TABLE_ORDER_TOTAL, order.getTotal());

        return db.addRecord(TABLE_ORDER, values);
    }

    public boolean updateOrder(Order order) {
        ContentValues values = new ContentValues();
        values.put(TABLE_ORDER_USER_ID, order.getUserId());
        values.put(TABLE_ORDER_STORE_ID, order.getStoreId());
        values.put(TABLE_ORDER_DATE, order.getDate());
        values.put(TABLE_ORDER_STATUS, order.getStatus());
        values.put(TABLE_ORDER_DELIVER_PICKUP_OPT, order.getDeliverPickupOpt());
        values.put(TABLE_ORDER_DELIVER_CHARGE, order.getDeliverCharge());
        values.put(TABLE_ORDER_FRIEND_DISCOUNT, order.getFriendDiscount());
        values.put(TABLE_ORDER_TOTAL, order.getTotal());

        String[] params = new String[]{String.valueOf(order.getOrderId())};

        String where = TABLE_ORDER_ID + " = ?";

        return db.updateRecord(TABLE_ORDER, values, where, params);
    }

    public Order getOrderById(Integer orderId) {
        Order order = null;

        String query = "SELECT " +
                TABLE_ORDER_USER_ID + ", " +
                TABLE_ORDER_STORE_ID + ", " +
                TABLE_ORDER_DATE + ", " +
                TABLE_ORDER_STATUS + ", " +
                TABLE_ORDER_DELIVER_PICKUP_OPT + ", " +
                TABLE_ORDER_DELIVER_CHARGE + ", " +
                TABLE_ORDER_FRIEND_DISCOUNT + ", " +
                TABLE_ORDER_TOTAL +
                " FROM " + TABLE_ORDER +
                " WHERE " + TABLE_ORDER_ID + " = ?";
        String[] params = new String[]{String.valueOf(orderId)};
        Cursor data = db.getData(query, params);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                order = new Order();
                int i = 0;
                order.setOrderId(orderId);
                order.setUserId(data.getInt(i++));
                order.setStoreId(data.getInt(i++));
                order.setDate(data.getString(i++));
                order.setStatus(data.getString(i++));
                order.setDeliverPickupOpt(data.getString(i++));
                order.setDeliverCharge(data.getDouble(i++));
                order.setFriendDiscount(data.getDouble(i++));
                order.setTotal(data.getDouble(i++));
            }
        }
        return order;
    }

    public void deleteOrder(int orderid) {
        db.deleteRecord(TABLE_ORDER, orderid);
    }

    public List<Order> getOrdersByUser(Integer userId) {
        List<Order> list = new ArrayList<>();

        String query = "SELECT " +
                TABLE_ORDER_ID + "," +
                TABLE_ORDER_USER_ID + ", " +
                TABLE_ORDER_STORE_ID + ", " +
                TABLE_ORDER_DATE + ", " +
                TABLE_ORDER_STATUS + ", " +
                TABLE_ORDER_DELIVER_PICKUP_OPT + ", " +
                TABLE_ORDER_DELIVER_CHARGE + ", " +
                TABLE_ORDER_FRIEND_DISCOUNT + ", " +
                TABLE_ORDER_TOTAL +
                " FROM " + TABLE_ORDER +
                " WHERE " + TABLE_ORDER_USER_ID + " = ?";
        String[] params = new String[]{String.valueOf(userId)};
        Cursor data = db.getData(query, params);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                Order order = new Order();
                int i = 0;
                order.setOrderId(data.getInt(i++));
                order.setUserId(data.getInt(i++));
                order.setStoreId(data.getInt(i++));
                order.setDate(data.getString(i++));
                order.setStatus(data.getString(i++));
                order.setDeliverPickupOpt(data.getString(i++));
                order.setDeliverCharge(data.getDouble(i++));
                order.setFriendDiscount(data.getDouble(i++));
                order.setTotal(data.getDouble(i++));

                list.add(order);
            }
        }


        return list;
    }
}
