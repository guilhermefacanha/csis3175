package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.csis3175.walmarket.entity.User;
import com.csis3175.walmarket.util.Md5Util;

public class UserDbHelper {

    public final static String TABLE_USER = "USER";
    public final static String TABLE_USER_ID = "USER_ID";
    public final static String TABLE_USER_FNAME = "FIRST_NAME";
    public final static String TABLE_USER_LNAME = "LAST_NAME";
    public final static String TABLE_USER_EMAIL = "EMAIL";
    public final static String TABLE_USER_ADDRESS = "ADDRESS";
    public final static String TABLE_USER_PASSWORD = "PASSWORD";
    public final static String TABLE_USER_APPLY_FRIEND_DISC = "APPLY_FRIEND_DISC";

    private DatabaseHelper db;

    public UserDbHelper(Context context){
        db = new DatabaseHelper(context);
    }

    public static String getCreation(){
        return "CREATE TABLE " + TABLE_USER + "(" +
                TABLE_USER_ID + " INTEGER PRIMARY KEY," +
                TABLE_USER_FNAME + " TEXT," +
                TABLE_USER_LNAME + " TEXT," +
                TABLE_USER_EMAIL + " TEXT," +
                TABLE_USER_ADDRESS + " TEXT, " +
                TABLE_USER_PASSWORD + " TEXT, " +
                TABLE_USER_APPLY_FRIEND_DISC + " INTEGER " +
                ")";
    }

    public long addUser(User user){
        ContentValues values = new ContentValues();
        values.put(TABLE_USER_FNAME, user.getfName());
        values.put(TABLE_USER_LNAME, user.getlName());
        values.put(TABLE_USER_EMAIL, user.getEmail());
        values.put(TABLE_USER_ADDRESS, user.getAddress());
        values.put(TABLE_USER_PASSWORD, Md5Util.getMd5(user.getPassword()));
        values.put(TABLE_USER_APPLY_FRIEND_DISC, user.getApplyFriendlyDisc());

        return db.addRecord(TABLE_USER,values);
    }

    public boolean updateRecord(User user){
        ContentValues values = new ContentValues();
        values.put(TABLE_USER_FNAME, user.getfName());
        values.put(TABLE_USER_LNAME, user.getlName());
        values.put(TABLE_USER_EMAIL, user.getEmail());
        values.put(TABLE_USER_ADDRESS, user.getAddress());
        values.put(TABLE_USER_PASSWORD, user.getPassword());
        values.put(TABLE_USER_APPLY_FRIEND_DISC, user.getApplyFriendlyDisc());

        String[] params = new String[]{String.valueOf(user.getUserId())};
        String where = TABLE_USER_ID + " = ?";

        return db.updateRecord(TABLE_USER,values,where,params);
    }

    public User getUserByEmail(String email){
        User user = null;
        String query = "SELECT * FROM "+TABLE_USER
                +" WHERE "+TABLE_USER_EMAIL+" = ?";
        String[] params = new String[]{email};
        Cursor data = db.getData(query, params);

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                user = new User();
                int i=0;
                user.setUserId(data.getInt(i++));
                user.setfName(data.getString(i++));
                user.setlName(data.getString(i++));
                user.setEmail(data.getString(i++));
                user.setAddress(data.getString(i++));
                user.setPassword(data.getString(i++));
                user.setApplyFriendlyDisc(data.getInt(i++));
            }
        }

        return user;
    }

    public static String[] insertInitialData() {
        String insertBase = "INSERT INTO "+TABLE_USER+" ("+
                TABLE_USER_FNAME+","+TABLE_USER_LNAME+","+TABLE_USER_EMAIL+","+
                TABLE_USER_ADDRESS+","+TABLE_USER_PASSWORD+","+TABLE_USER_APPLY_FRIEND_DISC+
                ") VALUES (";
        String[] inserts = {
                insertBase + "'Luke', 'Skywalker', 'Luke@StarWars.ca', '100, George Lucas St', '827ccb0eea8a706c4c34a16891f84e7b', null);",
                insertBase + "'Leia', 'Organa', 'Leia@StarWars.ca', '200, George Lucas St', '827ccb0eea8a706c4c34a16891f84e7b', null);",
                insertBase + "'Han', 'Solo', 'Han@StarWars.ca', '300, George Lucas St', '827ccb0eea8a706c4c34a16891f84e7b', null);"

        };

        return inserts;
    }

}
