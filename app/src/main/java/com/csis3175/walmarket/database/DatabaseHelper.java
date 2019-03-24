package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    final static String DATABASE_NAME = "walmarket.db";
    final static int DATABASE_VERSION = 7;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create tables
        String userTbCreateQuery = UserDbHelper.getCreation();
        db.execSQL(userTbCreateQuery);

        String storeTbCreateQuery = StoreDbHelper.getCreation();
        db.execSQL(storeTbCreateQuery);

        String categoryTbCreateQuery = CategoryDbHelper.getCreation();
        db.execSQL(categoryTbCreateQuery);

        String orderTbCreateQuery = OrderDbHelper.getCreation();
        db.execSQL(orderTbCreateQuery);

        String cartTbCreateQuery = CartDbHelper.getCreation();
        db.execSQL(cartTbCreateQuery);

        String itemTbCreateQuery = ItemDbHelper.getCreation();
        db.execSQL(itemTbCreateQuery);

        String itemStoreTbCreateQuery = ItemStoreDbHelper.getCreation();
        db.execSQL(itemStoreTbCreateQuery);

        String cartItemStoreTbCreateQuery = CartItemDbHelper.getCreation();
        db.execSQL(cartItemStoreTbCreateQuery);

        String orderItemStoreTbCreateQuery = OrderItemDbHelper.getCreation();
        db.execSQL(orderItemStoreTbCreateQuery);


        //insert data
        String[] strings = StoreDbHelper.insertInitialData();
        for(String s : strings)
            db.execSQL(s);

        strings = CategoryDbHelper.insertInitialData();
        for(String s : strings)
            db.execSQL(s);

        strings = UserDbHelper.insertInitialData();
        for(String s : strings)
            db.execSQL(s);

        for(ContentValues values : ItemDbHelper.insertInitialData())
            db.insert(ItemDbHelper.TABLE_ITEM,null,values);

        for(ContentValues values : ItemStoreDbHelper.insertInitialData())
            db.insert(ItemStoreDbHelper.TABLE_ITEM_STORE,null,values);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserDbHelper.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + StoreDbHelper.TABLE_STORE);
        db.execSQL("DROP TABLE IF EXISTS " + CategoryDbHelper.TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + OrderDbHelper.TABLE_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + CartDbHelper.TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + ItemDbHelper.TABLE_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + ItemStoreDbHelper.TABLE_ITEM_STORE);
        db.execSQL("DROP TABLE IF EXISTS " + CartItemDbHelper.TABLE_CART_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + OrderItemDbHelper.TABLE_ORDER_ITEM);
        onCreate(db);
    }

    public boolean addRecord(String table, ContentValues values) {
        SQLiteDatabase database = this.getWritableDatabase();
        long insert = database.insert(table, null, values);
        if (insert > 0)
            return true;
        else
            return false;

    }

    public boolean updateRecord(String table, ContentValues values, String where, String[] params) {
        SQLiteDatabase database = this.getWritableDatabase();
        long updated = database.update(table, values, where, params);
        if (updated > 0)
            return true;
        else
            return false;

    }

    public boolean deleteRecord(String table, int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DELETE FROM " + table + " WHERE id = ?";
        Object[] params = new Object[]{id};
        database.execSQL(query, params);

        return true;
    }

    public Cursor getData(String query, String[] params) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery(query, params);
    }

    public Cursor getAll(String table) {
        String query = "SELECT * FROM " + table;
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery(query, null);
    }
}
