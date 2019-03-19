package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.csis3175.walmarket.entity.Category;

public class CategoryDbHelper {

    public final static String TABLE_CATEGORY = "CATEGORY";
    public final static String TABLE_CATEGORY_ID = "CATEGORY_ID";
    public final static String TABLE_CATEGORY_DESCRIPTION = "DESCRIPTION";

    private DatabaseHelper db;

    public CategoryDbHelper(Context context){
        db = new DatabaseHelper(context);
    }

    public static String getCreation() {
        return "CREATE TABLE " + TABLE_CATEGORY + "(" +
                TABLE_CATEGORY_ID + " INTEGER PRIMARY KEY," +
                TABLE_CATEGORY_DESCRIPTION + " TEXT" +
                ")";
    }

    public boolean addCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(TABLE_CATEGORY_DESCRIPTION, category.getDescription());

        return db.addRecord(TABLE_CATEGORY, values);
    }

    public boolean updateCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(TABLE_CATEGORY_DESCRIPTION, category.getDescription());

        String[] params = new String[]{String.valueOf(category.getCategoryId())};

        String where =  TABLE_CATEGORY_ID + " = ?";

        return db.updateRecord(TABLE_CATEGORY,values,where,params);
    }

    public Category getCategoryById(Integer categoryId){
        Category category = null;
        String query = "SELECT " +
                TABLE_CATEGORY_DESCRIPTION +
                " FROM " + TABLE_CATEGORY +
                " WHERE "+TABLE_CATEGORY_ID + " = ?";
        String[] params = new String[]{String.valueOf(categoryId)};
        Cursor data = db.getData(query, params);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                category = new Category();
                int i = 0;
                category.setCategoryId(categoryId);
                category.setDescription(data.getString(i++));
            }
        }
        return category;
    }
}
