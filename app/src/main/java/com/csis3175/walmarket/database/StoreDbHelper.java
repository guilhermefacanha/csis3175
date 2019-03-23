package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.csis3175.walmarket.entity.Store;

import java.util.ArrayList;
import java.util.List;

public class StoreDbHelper {
    public final static String TABLE_STORE = "STORE";
    public final static String TABLE_STORE_ID = "STORE_ID";
    public final static String TABLE_STORE_NAME = "STORE_NAME";
    public final static String TABLE_STORE_LATITUDE = "LATITUDE";
    public final static String TABLE_STORE_LONGITUDE = "LONGITUDE";
    public final static String TABLE_STORE_ADDRESS = "ADDRESS";

    private DatabaseHelper db;

    public StoreDbHelper(Context context) {
        db = new DatabaseHelper(context);
    }

    public static String getCreation() {
        return "CREATE TABLE " + TABLE_STORE + "(" +
                TABLE_STORE_ID + " INTEGER PRIMARY KEY," +
                TABLE_STORE_NAME + " TEXT," +
                TABLE_STORE_LATITUDE + " REAL," +
                TABLE_STORE_LONGITUDE + " REAL," +
                TABLE_STORE_ADDRESS + " TEXT" +
                ")";
    }

    public static String[] insertInitialData() {
        String insertBase = "INSERT INTO "+TABLE_STORE+" ("+TABLE_STORE_NAME+","+TABLE_STORE_ADDRESS+","+TABLE_STORE_LATITUDE+","+TABLE_STORE_LONGITUDE + ") VALUES (";
        String[] inserts = {
           insertBase + "'New West Store','800 Carnarvon St #220, New Westminster, BC V3M 0C3',49.201861,-122.913019);",
           insertBase + "'Surrey Store','10355 King George Blvd, Surrey, BC V3B 6S2',49.192427,-122.845748);",
           insertBase + "'Collingwood Store','3410 Kingsway, Vancouver, BC V5R 5L4', 49.237770, -123.033112);",
           insertBase + "'Broadway Store','1780 E Broadway, Vancouver, BC V5N 1W3', 49.267920, -123.066950);",
           insertBase + "'Burnaby Store','6564 E Hastings St, Burnaby, BC V5B 1S2', 49.286229, -122.968736);"
        };

        return inserts;
    }

    private static void addStore(Store store, DatabaseHelper dbh) {
        ContentValues values = new ContentValues();
        values.put(TABLE_STORE_NAME, store.getName());
        values.put(TABLE_STORE_LATITUDE, store.getLatitude());
        values.put(TABLE_STORE_LONGITUDE, store.getLongitude());
        values.put(TABLE_STORE_ADDRESS, store.getAddress());

        dbh.addRecord(TABLE_STORE, values);
    }


    public boolean addStore(Store store) {
        ContentValues values = new ContentValues();
        values.put(TABLE_STORE_NAME, store.getName());
        values.put(TABLE_STORE_LATITUDE, store.getLatitude());
        values.put(TABLE_STORE_LONGITUDE, store.getLongitude());
        values.put(TABLE_STORE_ADDRESS, store.getAddress());

        return db.addRecord(TABLE_STORE, values);
    }

    public boolean updateStore(Store store) {
        ContentValues values = new ContentValues();
        values.put(TABLE_STORE_NAME, store.getName());
        values.put(TABLE_STORE_LATITUDE, store.getLatitude());
        values.put(TABLE_STORE_LONGITUDE, store.getLongitude());
        values.put(TABLE_STORE_ADDRESS, store.getAddress());

        String[] params = new String[]{String.valueOf(store.getStoreId())};

        String where = TABLE_STORE_ID + " = ?";

        return db.updateRecord(TABLE_STORE, values, where, params);
    }

    public Store getStoreById(Integer storeId) {
        Store store = null;

        String query = "SELECT " +
                TABLE_STORE_NAME + ", " +
                TABLE_STORE_LATITUDE + ", " +
                TABLE_STORE_LONGITUDE + ", " +
                TABLE_STORE_ADDRESS +
                " FROM " + TABLE_STORE +
                " WHERE " + TABLE_STORE_ID + " = ?";
        String[] params = new String[]{String.valueOf(storeId)};
        Cursor data = db.getData(query, params);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                store = new Store();
                int i = 0;
                store.setStoreId(data.getInt(i++));
                store.setName(data.getString(i++));
                store.setLatitude(data.getDouble(i++));
                store.setLongitude(data.getDouble(i++));
                store.setAddress(data.getString(i++));
            }
        }
        return store;
    }

    public List<Store> getAll() {
        List<Store> stores = new ArrayList<>();
        Store store = null;
        Cursor data = db.getAll(TABLE_STORE);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                store = new Store();

                int i = 0;
                store.setStoreId(data.getInt(i++));
                store.setName(data.getString(i++));
                store.setLatitude(data.getDouble(i++));
                store.setLongitude(data.getDouble(i++));
                store.setAddress(data.getString(i++));

                stores.add(store);
            }
        }
        return stores;
    }
}
