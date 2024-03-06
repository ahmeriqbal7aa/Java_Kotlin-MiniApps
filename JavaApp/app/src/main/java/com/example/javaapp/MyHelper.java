package com.example.javaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Product";
    private static final int DATABASE_VERSION = 1;

    public MyHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // table create
        String sql = "CREATE TABLE PRODUCTS(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, NAME TEXT, DESCRIPTION TEXT)";
        sqLiteDatabase.execSQL(sql);

        /*insertData("Bread", "Brown Bread", sqLiteDatabase);
        insertData("Pop Corn", "Salt Flavour", sqLiteDatabase);
        insertData("Jam", "Fruit Jam", sqLiteDatabase);*/
    }

    // TODO Insert Data Function
    void insertData(String name, String desc, SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("DESCRIPTION", desc);
        sqLiteDatabase.insert("PRODUCTS", null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PRODUCTS");
//        onCreate(sqLiteDatabase);
    }
}
