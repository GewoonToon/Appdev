package com.example.appdev;

import static com.example.appdev.HomeFragment.mMasterDetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class ProductDetailsFragment extends Fragment {

    TextView mName;
    TextView mPrice;
    TextView mAmount;
    Button mAmountDown;
    Button mAmountUp;
    Button mAddToCart;
    DatabaseManager dbManager;
    private int amount = 1;
    Product productData;


    public ProductDetailsFragment() { }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_details, container, false);

        mName = rootView.findViewById(R.id.frDetailName);
        mPrice = rootView.findViewById(R.id.frDetailPrice);
        mAmount = rootView.findViewById(R.id.frDetailAmount);
        mAmountDown = rootView.findViewById(R.id.frDetailAmountDown);
        mAmountUp = rootView.findViewById(R.id.frDetailAmountUp);
        mAddToCart = rootView.findViewById(R.id.frDetailAddToCart);

        if(!mMasterDetail){
            String id = getActivity().getIntent().getStringExtra("id");

            dbManager = new DatabaseManager(getContext());
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
                    productData = product;
                }
            }
        }

        mAmountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountDown(v);
                Log.i("amount", "" + amount);
            }
        });

        mAmountUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountUp(v);
                Log.i("amount", "" + amount);
            }
        });
        mAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(v);
            }
        });
        mName.setText(productData.name);
        mPrice.setText(productData.price);


        return rootView;
    }

    public void setProduct(Product lData){
        productData = lData;
    }


    public void addToCart(View view) {
        dbManager = new DatabaseManager(getContext());
        try {
            dbManager.open();
            Log.i("dbmanager.open", "done");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("dbmanager.open", "failed");
        }
        dbManager.insertIntoCart(productData.id, String.valueOf(this.amount), productData.name, productData.price);
    }

    public void amountUp(View view) {
        this.amount++;
        TextView amountview = getView().findViewById(R.id.frDetailAmount);
        amountview.setText(String.valueOf(amount));
    }

    public void amountDown(View view) {
        if (this.amount > 1) {
            this.amount--;
            TextView amountview = getView().findViewById(R.id.frDetailAmount);
            amountview.setText(String.valueOf(amount));
        }
    }


}