package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.csis3175.walmarket.entity.ItemStore;

public class ItemStoreDbHelper {
    public final static String TABLE_ITEM_STORE = "ITEM_STORE";
    public final static String TABLE_ITEM_STORE_ITEM_ID = "ITEM_ID";
    public final static String TABLE_ITEM_STORE_STORE_ID = "STORE_ID";
    public final static String TABLE_ITEM_STORE_PRICE = "PRICE";
    public final static String TABLE_ITEM_STORE_DISC_RATE = "DISC_RATE";

    private DatabaseHelper db;

    public ItemStoreDbHelper(Context context){
        db = new DatabaseHelper(context);
    }

    public static String getCreation() {
        return "CREATE TABLE " + TABLE_ITEM_STORE + "(" +
                TABLE_ITEM_STORE_ITEM_ID + " INTEGER," +
                TABLE_ITEM_STORE_STORE_ID + " INTEGER," +
                TABLE_ITEM_STORE_PRICE + " REAL," +
                TABLE_ITEM_STORE_DISC_RATE + " REAL," +
                " PRIMARY KEY (" +
                TABLE_ITEM_STORE_ITEM_ID + ", " +
                TABLE_ITEM_STORE_STORE_ID + ") " +
                ") WITHOUT ROWID";
    }

    public boolean addItemStore(ItemStore itemStore){
        ContentValues values = new ContentValues();
        values.put(TABLE_ITEM_STORE_ITEM_ID, itemStore.getItemId());
        values.put(TABLE_ITEM_STORE_STORE_ID, itemStore.getStoreId());
        values.put(TABLE_ITEM_STORE_PRICE, itemStore.getPrice());
        values.put(TABLE_ITEM_STORE_DISC_RATE, itemStore.getDiscRate());

        return db.addRecord(TABLE_ITEM_STORE, values);
    }

    public boolean updateItemStore(ItemStore itemStore){
        ContentValues values = new ContentValues();
        values.put(TABLE_ITEM_STORE_PRICE, itemStore.getPrice());
        values.put(TABLE_ITEM_STORE_DISC_RATE, itemStore.getDiscRate());

        String[] params = new String[]{String.valueOf(itemStore.getItemId()),
                String.valueOf(itemStore.getStoreId())};

        String where =  TABLE_ITEM_STORE_ITEM_ID + " = ? AND " +
                TABLE_ITEM_STORE_STORE_ID + " = ? ";

        return db.updateRecord(TABLE_ITEM_STORE,values,where,params);
    }

    public ItemStore getItemStoreById(Integer itemId, Integer storeId){
        ItemStore itemStore = null;

        String query = "SELECT " +
                TABLE_ITEM_STORE_PRICE + ", " +
                TABLE_ITEM_STORE_DISC_RATE +
                " FROM " + TABLE_ITEM_STORE +
                " WHERE " + TABLE_ITEM_STORE_ITEM_ID + " = ?" +
                " AND " + TABLE_ITEM_STORE_STORE_ID + " = ?";
        String[] params = new String[]{String.valueOf(itemStore.getItemId()),
                String.valueOf(itemStore.getStoreId())};
        Cursor data = db.getData(query, params);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                itemStore = new ItemStore();
                int i = 0;
                itemStore.setItemId(itemId);
                itemStore.setStoreId(storeId);
                itemStore.setPrice(data.getDouble(i++));
                itemStore.setDiscRate(data.getDouble(i++));
            }
        }
        return itemStore;
    }

}
