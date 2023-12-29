package com.example.appdev;

import static com.example.appdev.DatabaseHelper.AUTHORITY;
import static com.example.appdev.DatabaseHelper.CART_CONTENT_URI;
import static com.example.appdev.DatabaseHelper.CART_PATH;
import static com.example.appdev.DatabaseHelper.CART_TABLE;
import static com.example.appdev.DatabaseHelper.PRODUCTS_TABLE;
import static com.example.appdev.DatabaseHelper.PRODUCT_PATH;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProductProvider extends ContentProvider {



    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DatabaseHelper dbHelper;
    private static final int ALL_CARTS = 100;
    private static final int CART_ID = 101;
    private static final int ALL_PRODUCTS = 102;
    private static final int PRODUCT_ID = 103;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, CART_PATH, ALL_CARTS);
        uriMatcher.addURI(AUTHORITY, CART_PATH + "/#", CART_ID);
        uriMatcher.addURI(AUTHORITY, PRODUCT_PATH, ALL_PRODUCTS);
        uriMatcher.addURI(AUTHORITY, PRODUCT_PATH + "/#", PRODUCT_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new DatabaseHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.i("query", "true");
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor productData;
        int match = sUriMatcher.match(uri);

        switch (match) {
            case ALL_CARTS:
                Log.i("All Carts", "true");
                productData = db.query(CART_TABLE, projection, null, null, null, null, null);
                break;
            case CART_ID:
                Log.i("One Cart", "true");
                productData = db.query(CART_TABLE, projection, selection, selectionArgs, null, null, null);
                break;
            case ALL_PRODUCTS:
                Log.i("All Products", "true");
                productData = db.query(PRODUCTS_TABLE, projection, null, null, null, null, null);
                break;
            case PRODUCT_ID:
                Log.i("One Product", "true");
                productData = db.query(PRODUCTS_TABLE, projection, selection, selectionArgs, null, null, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        return productData;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri = null;
        switch (match) {
            case ALL_CARTS:
                long id = db.insert(CART_TABLE, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(CART_CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deleted = 0;

        int match = sUriMatcher.match(uri);

        switch(match) {
            case ALL_CARTS:
                break;
            case CART_ID:
                deleted = db.delete(CART_TABLE, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}
