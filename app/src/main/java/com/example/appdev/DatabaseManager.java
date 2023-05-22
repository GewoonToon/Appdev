package com.example.appdev;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLDataException;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context ctx){
        context = ctx;
    }

    public DatabaseManager open() throws SQLDataException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        Log.i("Db opened: ", database.toString());
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public Cursor fetchProducts(){
        String [] columns = new String[] {DatabaseHelper.PRODUCT_ID, DatabaseHelper.PRODUCT_NAME, DatabaseHelper.PRODUCT_PRICE};
        Cursor cursor = database.query(DatabaseHelper.PRODUCTS_TABLE, columns, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            Log.i("Cursor is not null : ", cursor.toString());
        }
        return cursor;
    }

    public void insertIntoCart (String id, String amount, String name, String price){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CARTPRODUCT_ID, id);
        contentValues.put(DatabaseHelper.CARTPRODUCT_NAME, name);
        contentValues.put(DatabaseHelper.CARTPRODUCT_PRICE, price);
        contentValues.put(DatabaseHelper.CART_AMOUNT, amount);
        database.insert(DatabaseHelper.CART_TABLE, null, contentValues);
    }

    public int updateCart(long _id, String amount){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CART_AMOUNT, amount);
        int ret = database.update(DatabaseHelper.CART_TABLE, contentValues, DatabaseHelper.CART_ID + "=" + _id, null);
        return ret;
    }

    public void deleteCart(long _id){
        database.delete(DatabaseHelper.CART_TABLE, DatabaseHelper.CART_ID + "=" + _id, null);
    }

}
