package com.example.appdev;

import static com.example.appdev.DatabaseHelper.PRODUCT_CONTENT_URI;

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
import android.widget.TextView;

import java.util.ArrayList;


public class ShopFragment extends Fragment {

    TextView title;

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

        title = getActivity().findViewById(R.id.AppTitle);
        title.setText("Shop");

        String projection[] = {"*"};
        Cursor productData = getActivity().getContentResolver().query(PRODUCT_CONTENT_URI, projection, null, null, null);

        ArrayList<Product> productList = new ArrayList<>();

        productData.moveToFirst();

        do{
            @SuppressLint("Range") String ID = productData.getString(productData.getColumnIndex(DatabaseHelper.PRODUCT_ID));
            @SuppressLint("Range") String NAME = productData.getString(productData.getColumnIndex(DatabaseHelper.PRODUCT_NAME));
            @SuppressLint("Range") String PRICE = productData.getString(productData.getColumnIndex(DatabaseHelper.PRODUCT_PRICE));
            Log.i("DATABASE_TAG", "I have read ID: " + ID + " NAME: " + NAME + "  PRICE: " + PRICE);
            productList.add(new Product(ID, NAME, PRICE));
        } while (productData.moveToNext());

        productData.close();


        ProductAdapter adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}