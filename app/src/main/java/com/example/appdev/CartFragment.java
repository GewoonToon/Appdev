package com.example.appdev;

import static com.example.appdev.DatabaseHelper.CART_CONTENT_URI;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CartFragment extends Fragment {

    TextView title;

    public CartFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_cart);
        recyclerView.setHasFixedSize(true);
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false));
        }
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 4, GridLayoutManager.VERTICAL, false));
        }

        title = getActivity().findViewById(R.id.AppTitle);
        title.setText("Cart");

        String projection[] = {"*"};
        Cursor cartData = getActivity().getContentResolver().query(CART_CONTENT_URI, projection, null, null, null);

        ArrayList<Cart> cartList = new ArrayList<>();

        if(cartData.moveToFirst()) {
            do {
                @SuppressLint("Range") String ID = cartData.getString(cartData.getColumnIndex(DatabaseHelper.CART_ID));
                @SuppressLint("Range") String NAME = cartData.getString(cartData.getColumnIndex(DatabaseHelper.CARTPRODUCT_NAME));
                @SuppressLint("Range") String PRICE = cartData.getString(cartData.getColumnIndex(DatabaseHelper.CARTPRODUCT_PRICE));
                @SuppressLint("Range") String AMOUNT = cartData.getString(cartData.getColumnIndex(DatabaseHelper.CART_AMOUNT));
                Log.i("DATABASE_TAG", "I have read ID: " + ID + " NAME: " + NAME + "  PRICE: " + PRICE + "   AMOUNT: " + AMOUNT);
                cartList.add(new Cart(ID, NAME, PRICE, AMOUNT));
            } while (cartData.moveToNext());
        }

        cartData.close();

        CartAdapter adapter = new CartAdapter(cartList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}