package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.csis3175.walmarket.entity.ItemStore;

import java.util.ArrayList;
import java.util.List;

public class ItemStoreDbHelper {
    public final static String TABLE_ITEM_STORE = "ITEM_STORE";
    public final static String TABLE_ITEM_STORE_ITEM_ID = "ITEM_ID";
    public final static String TABLE_ITEM_STORE_STORE_ID = "STORE_ID";
    public final static String TABLE_ITEM_STORE_PRICE = "PRICE";
    public final static String TABLE_ITEM_STORE_DISC_RATE = "DISC_RATE";

    private DatabaseHelper db;

    public ItemStoreDbHelper(Context context) {
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
                "FOREIGN KEY(" + TABLE_ITEM_STORE_ITEM_ID + ") " +
                "  REFERENCES " + ItemDbHelper.TABLE_ITEM + "(" + ItemDbHelper.TABLE_ITEM_ID + ")" +
                "FOREIGN KEY(" + TABLE_ITEM_STORE_STORE_ID + ") " +
                "  REFERENCES " + StoreDbHelper.TABLE_STORE + "(" + StoreDbHelper.TABLE_STORE_ID + ")" +
                ") WITHOUT ROWID";
    }

    public long addItemStore(ItemStore itemStore) {
        ContentValues values = new ContentValues();
        values.put(TABLE_ITEM_STORE_ITEM_ID, itemStore.getItemId());
        values.put(TABLE_ITEM_STORE_STORE_ID, itemStore.getStoreId());
        values.put(TABLE_ITEM_STORE_PRICE, itemStore.getPrice());
        values.put(TABLE_ITEM_STORE_DISC_RATE, itemStore.getDiscRate());

        return db.addRecord(TABLE_ITEM_STORE, values);
    }

    public boolean updateItemStore(ItemStore itemStore) {
        ContentValues values = new ContentValues();
        values.put(TABLE_ITEM_STORE_PRICE, itemStore.getPrice());
        values.put(TABLE_ITEM_STORE_DISC_RATE, itemStore.getDiscRate());

        String[] params = new String[]{String.valueOf(itemStore.getItemId()),
                String.valueOf(itemStore.getStoreId())};

        String where = TABLE_ITEM_STORE_ITEM_ID + " = ? AND " +
                TABLE_ITEM_STORE_STORE_ID + " = ? ";

        return db.updateRecord(TABLE_ITEM_STORE, values, where, params);
    }

    public ItemStore getItemStoreById(Integer itemId, Integer storeId) {
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

    private static ContentValues getValuesFromItemStore(ItemStore itemStore) {
        ContentValues values = new ContentValues();
        values.put(TABLE_ITEM_STORE_ITEM_ID, itemStore.getItemId());
        values.put(TABLE_ITEM_STORE_STORE_ID, itemStore.getStoreId());
        values.put(TABLE_ITEM_STORE_PRICE, itemStore.getPrice());
        values.put(TABLE_ITEM_STORE_DISC_RATE, itemStore.getDiscRate());

        return values;
    }

    //insert initial data
    public static List<ContentValues> insertInitialData() {
        List<ContentValues> inserts = new ArrayList<>();

        Double itemPrice[] = {0.0, 5.97, 3.00, 2.80, 1.20, 11.00, 3.97, 2.47, 2.97, 7.50, 3.97,
                2.66, 2.00, 1.47, 4.47, 3.77, 3.77, 3.18, 1.97, 1.97, 3.26};

        Double price;
        Double discRate;

        for (int storeId = 1; storeId <= 5; storeId++)
            for (int itemId = 1; itemId <= 20; itemId++) {
                price = (int) (((Math.random() * 0.10) + 0.95) * itemPrice[itemId] * 100.0) / 100.0; // -5% to +5% price variation

                discRate = (int) (Math.random() * 2000) / 100.0; // max 20% discount rate

                ItemStore itemStore = new ItemStore();
                itemStore.setItemId(itemId);
                itemStore.setStoreId(storeId);
                itemStore.setPrice(price);
                itemStore.setDiscRate(discRate);
                inserts.add(getValuesFromItemStore(itemStore));

            }
        return inserts;
    }

    public List<ItemStore> getStoreItems(Integer storeId) {
        List<ItemStore> list = new ArrayList<>();

        ItemStore itemStore = null;

        String query = "SELECT ITS.ITEM_ID, ITS.STORE_ID, ITS.PRICE, ITS.DISC_RATE, " +
                        " I.DESCRIPTION, I.IMAGE " +
                        " FROM ITEM_STORE AS ITS " +
                        " INNER JOIN ITEM I ON I.ITEM_ID = ITS.ITEM_ID " +
                        " WHERE " + TABLE_ITEM_STORE_STORE_ID + " = ? ORDER BY I.DESCRIPTION";
        String[] params = new String[]{String.valueOf(storeId)};
        Cursor data = db.getData(query, params);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                itemStore = new ItemStore();
                int i = 0;
                itemStore.setItemId(data.getInt(i++));
                itemStore.setStoreId(data.getInt(i++));
                itemStore.setPrice(data.getDouble(i++));
                itemStore.setDiscRate(data.getDouble(i++));
                itemStore.setItemDescription(data.getString(i++));
                itemStore.setItemImage(data.getBlob(i++));

                list.add(itemStore);
            }
        }

        return list;
    }
}
