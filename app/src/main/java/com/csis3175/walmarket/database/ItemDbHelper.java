package com.csis3175.walmarket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.csis3175.walmarket.entity.Item;
import com.csis3175.walmarket.entity.Store;
import com.csis3175.walmarket.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class ItemDbHelper {

    public final static String TABLE_ITEM = "ITEM";
    public final static String TABLE_ITEM_ID = "ITEM_ID";
    public final static String TABLE_ITEM_CATEGORY_ID = "CATEGORY_ID";
    public final static String TABLE_ITEM_DESCRIPTION = "DESCRIPTION";
    public final static String TABLE_ITEM_IMAGE = "IMAGE";

    private DatabaseHelper db;

    public static String getCreation() {
        return "CREATE TABLE " + TABLE_ITEM + "(" +
                TABLE_ITEM_ID + " INTEGER PRIMARY KEY," +
                TABLE_ITEM_CATEGORY_ID + " INTEGER," +
                TABLE_ITEM_DESCRIPTION + " TEXT," +
                TABLE_ITEM_IMAGE + " BLOB" +
                ")";

    }


    public boolean addItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(TABLE_ITEM_CATEGORY_ID, item.getCategoryId());
        values.put(TABLE_ITEM_DESCRIPTION, item.getDescription());

        return db.addRecord(TABLE_ITEM, values);
    }

    public boolean updateItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(TABLE_ITEM_CATEGORY_ID, item.getCategoryId());
        values.put(TABLE_ITEM_DESCRIPTION, item.getDescription());

        String[] params = new String[]{String.valueOf(item.getItemId())};

        String where = TABLE_ITEM_ID + " = ?";

        return db.updateRecord(TABLE_ITEM, values, where, params);
    }

    public Item getItemById(Integer itemId) {
        Item item = null;

        String query = "SELECT " +
                TABLE_ITEM_CATEGORY_ID + ", " +
                TABLE_ITEM_DESCRIPTION +
                " FROM " + TABLE_ITEM +
                " WHERE " + TABLE_ITEM_ID + " = ?";
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


    private static ContentValues getValuesFromItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(TABLE_ITEM_CATEGORY_ID, item.getCategoryId());
        values.put(TABLE_ITEM_DESCRIPTION, item.getDescription());
        values.put(TABLE_ITEM_IMAGE, item.getImage());

        return values;
    }

    //insert initial data
    public static List<ContentValues> insertInitialData() {
        List<ContentValues> inserts = new ArrayList<>();
        String imgUrl = "https://raw.githubusercontent.com/guilhermefacanha/csis3175/master/screenshots/imgs/";

        Item item = new Item(); //1
        item.setCategoryId(1); //Grocery
        item.setDescription("Nanaimo Bars"); //5.97
        item.setImage(ImageUtil.getImage(imgUrl+"nanaimo%20bars.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //2
        item.setCategoryId(1); //Grocery
        item.setDescription("Mini Donuts Chocolate Boston Cream"); //3.00
        item.setImage(ImageUtil.getImage(imgUrl+"mini%20donuts%20boston%20cream.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //3
        item.setCategoryId(1); //Grocery
        item.setDescription("Glazed Cinnamon Buns"); //2.80
        item.setImage(ImageUtil.getImage(imgUrl+"cinnamon%20buns.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //4
        item.setCategoryId(1); //Grocery
        item.setDescription("Glazed Donut Rings"); //1.20
        item.setImage(ImageUtil.getImage(imgUrl+"glazed%20donut%20rings.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //5
        item.setCategoryId(1); //Grocery
        item.setDescription("Tuxedo Cake"); //11.00
        item.setImage(ImageUtil.getImage(imgUrl+"tuxedo%20cake.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //6
        item.setCategoryId(1); //Grocery
        item.setDescription("Everything Bagels"); //3.97
        item.setImage(ImageUtil.getImage(imgUrl+"everything%20bagel.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //7
        item.setCategoryId(1); //Grocery
        item.setDescription("Lemon Meringue Pie"); //2.47
        item.setImage(ImageUtil.getImage(imgUrl+"lemon%20pie.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //8
        item.setCategoryId(1); //Grocery
        item.setDescription("Chocolate Chip Cookies"); //2.97
        item.setImage(ImageUtil.getImage(imgUrl+"chocolate%20cookie.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //9
        item.setCategoryId(1); //Grocery
        item.setDescription("Tiramisu Cake"); //7.50
        item.setImage(ImageUtil.getImage(imgUrl+"tiramisu%20cake.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //10
        item.setCategoryId(1); //Grocery
        item.setDescription("Chocolate Chunk Muffins"); //3.97
        item.setImage(ImageUtil.getImage(imgUrl+"chocolate%20muffin.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //11
        item.setCategoryId(2); //Snacks
        item.setDescription("Skittles Original Bite Size Candies"); //2.66
        item.setImage(ImageUtil.getImage(imgUrl+"skittle.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //12
        item.setCategoryId(2); //Snacks
        item.setDescription("Caramels Candy"); //2.00
        item.setImage(ImageUtil.getImage(imgUrl+"caramel%20candy.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //13
        item.setCategoryId(2); //Snacks
        item.setDescription("Chocolate Pudding Cups"); //1.47
        item.setImage(ImageUtil.getImage(imgUrl+"pudding%20cups.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //14
        item.setCategoryId(2); //Snacks
        item.setDescription("Popping Corn"); //4.47
        item.setImage(ImageUtil.getImage(imgUrl+"popping%20corn.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //15
        item.setCategoryId(2); //Snacks
        item.setDescription("Pretzel Sticks"); //3.77
        item.setImage(ImageUtil.getImage(imgUrl+"pretzel%20sticks.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //16
        item.setCategoryId(2); //Snacks
        item.setDescription("Cheddar Chips"); //3.77
        item.setImage(ImageUtil.getImage(imgUrl+"cheddar%20chips.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //17
        item.setCategoryId(2); //Snacks
        item.setDescription("Potato Chips"); //3.18
        item.setImage(ImageUtil.getImage(imgUrl+"potato%20chips.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //18
        item.setCategoryId(2); //Snacks
        item.setDescription("Milk Chocolate Bar"); //1.97
        item.setImage(ImageUtil.getImage(imgUrl+"milk%20chocolate%20bar.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //19
        item.setCategoryId(2); //Snacks
        item.setDescription("Hazelnut Chocolate Bar"); //1.97
        item.setImage(ImageUtil.getImage(imgUrl+"hazelnut%20chocolate%20bar.jpg"));
        inserts.add(getValuesFromItem(item));

        item = new Item(); //20
        item.setCategoryId(2); //Snacks
        item.setDescription("Mint Chocolate Bar"); //3.26
        item.setImage(ImageUtil.getImage(imgUrl+"mint%20chocolate%20bar.jpg"));
        inserts.add(getValuesFromItem(item));

        return inserts;
    }


}
