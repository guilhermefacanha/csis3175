package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.csis3175.walmarket.entity.Item;
import com.csis3175.walmarket.entity.Store;

public class ItemDbHelper {

    public final static String TABLE_ITEM = "ITEM";
    public final static String TABLE_ITEM_ID = "ITEM_ID";
    public final static String TABLE_ITEM_CATEGORY_ID = "CATEGORY_ID";
    public final static String TABLE_ITEM_DESCRIPTION = "DESCRIPTION";
    public final static String TABLE_ITEM_IMAGE = "IMAGE";

    private DatabaseHelper db;

    public ItemDbHelper(Context context){
        db = new DatabaseHelper(context);
    }

    public static String getCreation() {
        return "CREATE TABLE " + TABLE_ITEM + "(" +
                TABLE_ITEM_ID + " INTEGER PRIMARY KEY," +
                TABLE_ITEM_CATEGORY_ID + " INTEGER," +
                TABLE_ITEM_DESCRIPTION + " TEXT," +
                TABLE_ITEM_IMAGE + " BLOB" +
                ")";

    }

    public void insertInitialData() {
        Item item = new Item(); //1
        item.setCategoryId(1); //Grocery
        item.setDescription("Nanaimo Bars"); //5.97
        item.setImage(null);
        addItem(item);

        item = new Item(); //2
        item.setCategoryId(1); //Grocery
        item.setDescription("Mini Donuts Chocolate Boston Cream"); //3.00
        item.setImage(null);
        addItem(item);

        item = new Item(); //3
        item.setCategoryId(1); //Grocery
        item.setDescription("Glazed Cinnamon Buns"); //2.80
        item.setImage(null);
        addItem(item);

        item = new Item(); //4
        item.setCategoryId(1); //Grocery
        item.setDescription("Glazed Donut Rings"); //1.20
        item.setImage(null);
        addItem(item);

        item = new Item(); //5
        item.setCategoryId(1); //Grocery
        item.setDescription("Tuxedo Cake"); //11.00
        item.setImage(null);
        addItem(item);

        item = new Item(); //6
        item.setCategoryId(1); //Grocery
        item.setDescription("Everything Bagels"); //3.97
        item.setImage(null);
        addItem(item);

        item = new Item(); //7
        item.setCategoryId(1); //Grocery
        item.setDescription("Lemon Meringue Pie"); //2.47
        item.setImage(null);
        addItem(item);

        item = new Item(); //8
        item.setCategoryId(1); //Grocery
        item.setDescription("Chocolate Chip Cookies"); //2.97
        item.setImage(null);
        addItem(item);

        item = new Item(); //9
        item.setCategoryId(1); //Grocery
        item.setDescription("Tiramisu Cake"); //7.50
        item.setImage(null);
        addItem(item);

        item = new Item(); //10
        item.setCategoryId(1); //Grocery
        item.setDescription("Chocolate Chunk Muffins"); //3.97
        item.setImage(null);
        addItem(item);

        item = new Item(); //11
        item.setCategoryId(2); //Snacks
        item.setDescription("Skittles Original Bite Size Candies"); //2.66
        item.setImage(null);
        addItem(item);

        item = new Item(); //12
        item.setCategoryId(2); //Snacks
        item.setDescription("Caramels Candy"); //2.00
        item.setImage(null);
        addItem(item);

        item = new Item(); //13
        item.setCategoryId(2); //Snacks
        item.setDescription("Chocolate Pudding Cups"); //1.47
        item.setImage(null);
        addItem(item);

        item = new Item(); //14
        item.setCategoryId(2); //Snacks
        item.setDescription("Popping Corn"); //4.47
        item.setImage(null);
        addItem(item);

        item = new Item(); //15
        item.setCategoryId(2); //Snacks
        item.setDescription("Pretzel Sticks"); //3.77
        item.setImage(null);
        addItem(item);

        item = new Item(); //16
        item.setCategoryId(2); //Snacks
        item.setDescription("Cheddar Chips"); //3.77
        item.setImage(null);
        addItem(item);

        item = new Item(); //17
        item.setCategoryId(2); //Snacks
        item.setDescription("Potato Chips"); //3.18
        item.setImage(null);
        addItem(item);

        item = new Item(); //18
        item.setCategoryId(2); //Snacks
        item.setDescription("Milk Chocolate Bar"); //1.97
        item.setImage(null);
        addItem(item);

        item = new Item(); //19
        item.setCategoryId(2); //Snacks
        item.setDescription("Hazelnut Chocolate Bar"); //1.97
        item.setImage(null);
        addItem(item);

        item = new Item(); //20
        item.setCategoryId(2); //Snacks
        item.setDescription("Mint Chocolate Bar"); //3.26
        item.setImage(null);
        addItem(item);


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
