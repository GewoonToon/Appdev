package com.example.appdev;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdev.databinding.ActivityProductDetailsBinding;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.appdev.DatabaseHelper.CARTPRODUCT_ID;
import static com.example.appdev.DatabaseHelper.CARTPRODUCT_NAME;
import static com.example.appdev.DatabaseHelper.CARTPRODUCT_PRICE;
import static com.example.appdev.DatabaseHelper.CART_AMOUNT;
import static com.example.appdev.DatabaseHelper.CART_CONTENT_URI;
import static com.example.appdev.DatabaseHelper.PRODUCT_CONTENT_URI;

public class productDetailsActivity extends AppCompatActivity {

    private ActivityProductDetailsBinding binding;
    DatabaseManager dbManager;
    private int amount = 1;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String id = getIntent().getStringExtra("id");

        String selection = "PRODUCT_ID = ?";
        String selectionArgs[] = {id};
        Uri selectedProduct = PRODUCT_CONTENT_URI.buildUpon().appendPath(id).build();
        Cursor productData = getContentResolver().query(selectedProduct, null, selection, selectionArgs, null);
        Log.i("product Data in details", DatabaseUtils.dumpCursorToString(productData));

        productData.moveToFirst();

        TextView title = this.findViewById(R.id.Title);
        title.setText(productData.getString(productData.getColumnIndex(DatabaseHelper.PRODUCT_NAME)));
        TextView price = this.findViewById(R.id.Price);
        price.setText(productData.getString(productData.getColumnIndex(DatabaseHelper.PRODUCT_PRICE)));

        productData.close();
    }

    @SuppressLint("Range")
    public void addToCart(View view) {
        String id = getIntent().getStringExtra("id");

        String selection = "PRODUCT_ID = ?";
        String selectionArgs[] = {id};
        Uri selectedProduct = PRODUCT_CONTENT_URI.buildUpon().appendPath(id).build();
        Cursor productData = getContentResolver().query(selectedProduct, null, selection, selectionArgs, null);
        Log.i("product Data in details", DatabaseUtils.dumpCursorToString(productData));

        productData.moveToFirst();

        ContentValues cv = new ContentValues();
        cv.put(CARTPRODUCT_ID, productData.getString(productData.getColumnIndex(DatabaseHelper.PRODUCT_ID)));
        cv.put(CARTPRODUCT_NAME, productData.getString(productData.getColumnIndex(DatabaseHelper.PRODUCT_NAME)));
        cv.put(CARTPRODUCT_PRICE, productData.getString(productData.getColumnIndex(DatabaseHelper.PRODUCT_PRICE)));
        cv.put(CART_AMOUNT, String.valueOf(this.amount));
        getContentResolver().insert(CART_CONTENT_URI, cv);


    }

    public void amountUp(View view) {
        this.amount++;
        TextView amountview = this.findViewById(R.id.amount);
        amountview.setText(String.valueOf(amount));
    }

    public void amountDown(View view) {
        if (this.amount > 1) {
            this.amount--;
            TextView amountview = this.findViewById(R.id.amount);
            amountview.setText(String.valueOf(amount));
        }
    }


}