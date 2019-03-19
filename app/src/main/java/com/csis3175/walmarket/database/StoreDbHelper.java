package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.csis3175.walmarket.entity.Store;

public class StoreDbHelper {
    public final static String TABLE_STORE = "STORE";
    public final static String TABLE_STORE_ID = "STORE_ID";
    public final static String TABLE_STORE_NAME = "STORE_NAME";
    public final static String TABLE_STORE_LATITUDE = "LATITUDE";
    public final static String TABLE_STORE_LONGITUDE = "LONGITUDE";
    public final static String TABLE_STORE_ADDRESS = "ADDRESS";

    private DatabaseHelper db;

    public StoreDbHelper(Context context){
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

    public boolean addStore(Store store){
        ContentValues values = new ContentValues();
        values.put(TABLE_STORE_NAME, store.getName());
        values.put(TABLE_STORE_LATITUDE, store.getLatitude());
        values.put(TABLE_STORE_LONGITUDE, store.getLongitude());
        values.put(TABLE_STORE_ADDRESS, store.getAddress());

        return db.addRecord(TABLE_STORE, values);
    }

    public boolean updateStore(Store store){
        ContentValues values = new ContentValues();
        values.put(TABLE_STORE_NAME, store.getName());
        values.put(TABLE_STORE_LATITUDE, store.getLatitude());
        values.put(TABLE_STORE_LONGITUDE, store.getLongitude());
        values.put(TABLE_STORE_ADDRESS, store.getAddress());

        String[] params = new String[]{String.valueOf(store.getStoreId())};

        String where =  TABLE_STORE_ID + " = ?";

        return db.updateRecord(TABLE_STORE,values,where,params);
    }

    public Store getStoreById(Integer storeId){
        Store store = null;

        String query = "SELECT " +
                TABLE_STORE_NAME + ", " +
                TABLE_STORE_LATITUDE + ", " +
                TABLE_STORE_LONGITUDE + ", " +
                TABLE_STORE_ADDRESS +
                " FROM " + TABLE_STORE +
                " WHERE "+TABLE_STORE_ID + " = ?";
        String[] params = new String[]{String.valueOf(storeId)};
        Cursor data = db.getData(query, params);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                store = new Store();
                int i = 0;
                store.setStoreId(storeId);
                store.setLatitude(data.getDouble(i++));
                store.setLongitude(data.getDouble(i++));
                store.setAddress(data.getString(i++));
            }
        }
        return store;
    }

}
