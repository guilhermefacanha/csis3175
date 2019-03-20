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

    public StoreDbHelper(Context context) {
        db = new DatabaseHelper(context);
    }

    public StoreDbHelper(DatabaseHelper db) {
        this.db = db;
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

    public void insertInitialData() {
        Store store = new Store();
        store.setName("New West Store");
        store.setAddress("800 Carnarvon St #220, New Westminster, BC V3M 0C3");
        store.setLatitude(49.201861);
        store.setLongitude(-122.913019);
        addStore(store);

        store = new Store();
        store.setName("Surrey Store");
        store.setAddress("10355 King George Blvd, Surrey, BC V3B 6S2");
        store.setLatitude(49.192427);
        store.setLongitude(-122.845748);
        addStore(store);

        store = new Store();
        store.setName("Collingwood Store");
        store.setAddress("3410 Kingsway, Vancouver, BC V5R 5L4");
        store.setLatitude(49.237770);
        store.setLongitude(-123.033112);
        addStore(store);

        store = new Store();
        store.setName("Broadway Store");
        store.setAddress("1780 E Broadway, Vancouver, BC V5N 1W3");
        store.setLatitude(49.267920);
        store.setLongitude(-123.066950);
        addStore(store);

        store = new Store();
        store.setName("Burnaby Store");
        store.setAddress("6564 E Hastings St, Burnaby, BC V5B 1S2");
        store.setLatitude(49.286229);
        store.setLongitude(-122.968736);
        addStore(store);
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
                store.setStoreId(storeId);
                store.setLatitude(data.getDouble(i++));
                store.setLongitude(data.getDouble(i++));
                store.setAddress(data.getString(i++));
            }
        }
        return store;
    }

}
