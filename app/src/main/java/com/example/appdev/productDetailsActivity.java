package com.example.appdev;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.appdev.databinding.ActivityProductDetailsBinding;

import java.util.ArrayList;
import java.util.Objects;

public class productDetailsActivity extends AppCompatActivity {

    private ActivityProductDetailsBinding binding;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String id = getIntent().getStringExtra("id");


        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
            Log.i("dbmanager.open", "done");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("dbmanager.open", "failed");
        }

        ArrayList<Product> productList = new ArrayList<>();
        Cursor cursor = dbManager.fetchProducts();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String ID = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_ID));
                @SuppressLint("Range") String NAME = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_NAME));
                @SuppressLint("Range") String PRICE = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_PRICE));
                Log.i("DATABASE_TAG", "I have read ID: " + ID + " USERNAME: " + NAME + "  PRICE: " + PRICE);
                productList.add(new Product(ID, NAME, PRICE));
            } while (cursor.moveToNext());
        }

        for (Product product : productList) {
            if (Objects.equals(product.id, id)) {
                TextView title = this.findViewById(R.id.Title);
                title.setText(product.name);
                TextView price = this.findViewById(R.id.Price);
                price.setText(product.price);
                //set all the text correct
                Log.i("Correct product found", product.toString());
            }
        }

    }
}