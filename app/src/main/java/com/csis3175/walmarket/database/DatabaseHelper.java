package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    final static String DATABASE_NAME = "walmarket.db";
    final static int DATABASE_VERSION = 4;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = UserDbHelper.getCreation();
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserDbHelper.TABLE_USER);
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
