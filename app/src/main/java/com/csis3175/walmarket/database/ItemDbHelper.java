package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.csis3175.walmarket.entity.Item;

public class ItemDbHelper {

    public final static String TABLE_ITEM = "ITEM";
    public final static String TABLE_ITEM_ID = "ITEM_ID";
    public final static String TABLE_ITEM_CATEGORY_ID = "CATEGORY_ID";
    public final static String TABLE_ITEM_DESCRIPTION = "DESCRIPTION";

    private DatabaseHelper db;

    public ItemDbHelper(Context context){
        db = new DatabaseHelper(context);
    }

    public static String getCreation() {
        return "CREATE TABLE " + TABLE_ITEM + "(" +
                TABLE_ITEM_ID + " INTEGER PRIMARY KEY," +
                TABLE_ITEM_CATEGORY_ID + " INTEGER," +
                TABLE_ITEM_DESCRIPTION + " TEXT" +
                ")";

    }

    public boolean addItem(Item item){
        ContentValues values = new ContentValues();
        values.put(TABLE_ITEM_CATEGORY_ID, item.getCategoryId());
        values.put(TABLE_ITEM_DESCRIPTION, item.getDescription());

        return db.addRecord(TABLE_ITEM, values);
    }

    public boolean updateItem(Item item){
        ContentValues values = new ContentValues();
        values.put(TABLE_ITEM_CATEGORY_ID, item.getCategoryId());
        values.put(TABLE_ITEM_DESCRIPTION, item.getDescription());

        String[] params = new String[]{String.valueOf(item.getItemId())};

        String where =  TABLE_ITEM_ID + " = ?";

        return db.updateRecord(TABLE_ITEM,values,where,params);
    }

    public Item getItemById(Integer itemId){
        Item item = null;

        String query = "SELECT " +
                TABLE_ITEM_CATEGORY_ID + ", " +
                TABLE_ITEM_DESCRIPTION +
                " FROM " + TABLE_ITEM +
                " WHERE "+TABLE_ITEM_ID + " = ?";
        String[] params = new String[]{String.valueOf(itemId)};
        Cursor data = db.getData(query, params);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                item = new Item();
                int i = 0;
                item.setItemId(itemId);
                item.setCategoryId(data.getInt(i++));
                item.setDescription(data.getString(i++));
            }
        }
        return item;
    }

}
