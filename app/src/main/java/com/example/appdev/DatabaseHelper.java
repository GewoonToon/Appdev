package com.example.appdev;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String AUTHORITY = "com.example.appdev";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String CART_PATH = "cart";
    public static final String PRODUCT_PATH = "products";
    public static final Uri CART_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(CART_PATH).build();
    public static final Uri PRODUCT_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PRODUCT_PATH).build();

    static final String DATABASE_NAME = "Database.DB";
    static final int DATABASE_VERSION = 5;
    static final String PRODUCTS_TABLE = "PRODUCTS";
    static final String PRODUCT_ID = "PRODUCT_ID";
    static final String PRODUCT_NAME = "product_name";
    static final String PRODUCT_PRICE = "product_price";


    static final String CART_TABLE = "CART";
    static final String CART_ID = "CART_ID";
    static final String CARTPRODUCT_ID = "CARTPRODUCT_ID";
    static final String CARTPRODUCT_NAME = "CARTPRODUCT_NAME";
    static final String CARTPRODUCT_PRICE = "CARTPRODUCT_PRICE";
    static final String CART_AMOUNT = "CART_AMOUNT";



    private static final String CREATE_DB_QUERY_PRODUCTS = "CREATE TABLE " + PRODUCTS_TABLE + " ( " + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PRODUCT_NAME + " TEXT NOT NULL, " +  PRODUCT_PRICE + " TEXT NOT NULL);";
    private static final String CREATE_DB_QUERY_CART = "CREATE TABLE " + CART_TABLE + " ( " + CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CARTPRODUCT_ID + " TEXT NOT NULL, " + CARTPRODUCT_NAME + " TEXT NOT NULL, " + CARTPRODUCT_PRICE + " TEXT NOT NULL, " +  CART_AMOUNT + " TEXT NOT NULL);";

    private static final String INSERT_DUMMY_DATA_PRODUCTS = "INSERT INTO PRODUCTS (product_name, product_price) VALUES" +
            "('Test1', '15')," +
            "('Test2', '13')," +
            "('Test3', '8')," +
            "('Test4', '84')," +
            "('Test5', '60')," +
            "('Test6', '50')," +
            "('Test7', '80');";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_DB_QUERY_PRODUCTS);
        db.execSQL(CREATE_DB_QUERY_CART);
        db.execSQL(INSERT_DUMMY_DATA_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CART_TABLE);
        db.execSQL(CREATE_DB_QUERY_PRODUCTS);
        db.execSQL(CREATE_DB_QUERY_CART);
        db.execSQL(INSERT_DUMMY_DATA_PRODUCTS);

    }
}
