package com.example.appdev;

import static com.example.appdev.DatabaseHelper.CARTPRODUCT_ID;
import static com.example.appdev.DatabaseHelper.CARTPRODUCT_NAME;
import static com.example.appdev.DatabaseHelper.CARTPRODUCT_PRICE;
import static com.example.appdev.DatabaseHelper.CART_AMOUNT;
import static com.example.appdev.DatabaseHelper.CART_CONTENT_URI;
import static com.example.appdev.DatabaseHelper.PRODUCT_CONTENT_URI;
import static com.example.appdev.MainActivity.mMasterDetail;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ProductDetailsFragment extends Fragment {

    TextView mName;
    TextView mPrice;
    TextView mAmount;
    Button mAmountDown;
    Button mAmountUp;
    Button mAddToCart;
    private int amount = 1;
    Product productData;


    public ProductDetailsFragment() { }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("Range")
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

            String selection = "PRODUCT_ID = ?";
            String selectionArgs[] = {id};
            Uri selectedProduct = PRODUCT_CONTENT_URI.buildUpon().appendPath(id).build();
            Cursor productCursor = getActivity().getContentResolver().query(selectedProduct, null, selection, selectionArgs, null);
            Log.i("product Data in details", DatabaseUtils.dumpCursorToString(productCursor));

            productCursor.moveToFirst();
            productData = new Product(
                    productCursor.getString(productCursor.getColumnIndex(DatabaseHelper.PRODUCT_ID)),
                    productCursor.getString(productCursor.getColumnIndex(DatabaseHelper.PRODUCT_NAME)),
                    productCursor.getString(productCursor.getColumnIndex(DatabaseHelper.PRODUCT_PRICE))
            );
            productCursor.close();
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

        ContentValues cv = new ContentValues();
        cv.put(CARTPRODUCT_ID, productData.id);
        cv.put(CARTPRODUCT_NAME, productData.name);
        cv.put(CARTPRODUCT_PRICE, productData.price);
        cv.put(CART_AMOUNT, String.valueOf(this.amount));
        getActivity().getContentResolver().insert(CART_CONTENT_URI, cv);
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