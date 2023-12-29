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

    public Cursor fetchCart(){
        String [] columns = new String[] {DatabaseHelper.CART_ID, DatabaseHelper.CARTPRODUCT_NAME, DatabaseHelper.CARTPRODUCT_PRICE, DatabaseHelper.CART_AMOUNT};
        Cursor cursor = database.query(DatabaseHelper.CART_TABLE, columns, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            Log.i("Cursor is not null : ", cursor.toString());
        }
        return cursor;
    }


}
