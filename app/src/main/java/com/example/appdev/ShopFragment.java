package com.example.appdev;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ShopFragment extends Fragment {

    DatabaseManager dbManager;

    public ShopFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false));
        }
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 4, GridLayoutManager.VERTICAL, false));
        }


        //Add all products from database to an arraylist
        dbManager = new DatabaseManager(view.getContext());
        try {
            dbManager.open();
            Log.i("dbmanager.open", "done");
        }
        catch (Exception e){
            e.printStackTrace();
            Log.i("dbmanager.open", "failed");
        }

        ArrayList<Product> productList = new ArrayList<>();
        Cursor cursor = dbManager.fetchProducts();

        if(cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String ID = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_ID));
                @SuppressLint("Range") String NAME = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_NAME));
                @SuppressLint("Range") String PRICE = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_PRICE));
                Log.i("DATABASE_TAG", "I have read ID: " + ID + " USERNAME: " + NAME + "  PRICE: " + PRICE);
                productList.add(new Product(ID, NAME, PRICE));
            } while (cursor.moveToNext());
        }


        ProductAdapter adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}