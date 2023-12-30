package com.example.appdev;


import static com.example.appdev.DatabaseHelper.CART_CONTENT_URI;
import static com.example.appdev.DatabaseHelper.PRODUCT_CONTENT_URI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appdev.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static boolean mMasterDetail;
    ActivityMainBinding binding;

    DatabaseManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment(), false);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int width_dp = (int)(outMetrics.widthPixels / getResources().getDisplayMetrics().density);
        mMasterDetail = width_dp > 600;

        Log.i("Masterdetail", "" + mMasterDetail);
        Log.i("Width pixels", "" + width_dp);


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment(), false);
            }
            if (item.getItemId() == R.id.shop) {
                replaceFragment(new ShopFragment(), mMasterDetail);
            }

            if (item.getItemId() == R.id.cart) {
                replaceFragment(new CartFragment(), false);
            }
            if (item.getItemId() == R.id.settings) {
                replaceFragment(new SettingsFragment(), false);
            }
            return true;
        });


    }

    private void replaceFragment(Fragment fragment, boolean twoSides) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
        ConstraintLayout constraintLayout = findViewById(R.id.whole_app);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        if(twoSides){
            constraintSet.connect(R.id.frameLayout, ConstraintSet.END, R.id.secondaryFrameLayout, ConstraintSet.START);
            constraintSet.applyTo(constraintLayout);
        } else if(mMasterDetail){
            constraintSet.connect(R.id.frameLayout, ConstraintSet.END, R.id.secondaryFrameLayout, ConstraintSet.END);
            constraintSet.applyTo(constraintLayout);
        }
    }

    public void toDetails(View view) {
        if (!mMasterDetail) {
            Intent intent = new Intent(this, productDetailsActivity.class);
            Log.i("Id log in main", ""+view.getId());
            intent.putExtra("id", String.valueOf(view.getId()));
            startActivity(intent);
        } else{
            FragmentManager fragmentManager = getSupportFragmentManager();
            ProductDetailsFragment detailsFragment = new ProductDetailsFragment();
            String id = String.valueOf(view.getId());

            String selection = "PRODUCT_ID = ?";
            String selectionArgs[] = {id};
            Uri selectedProduct = PRODUCT_CONTENT_URI.buildUpon().appendPath(id).build();
            Cursor productData = getContentResolver().query(selectedProduct, null, selection, selectionArgs, null);
            Log.i("product Data in details", DatabaseUtils.dumpCursorToString(productData));

            productData.moveToFirst();
            @SuppressLint("Range") Product chosenProduct = new Product(
                    productData.getString(productData.getColumnIndex(DatabaseHelper.PRODUCT_ID)),
                    productData.getString(productData.getColumnIndex(DatabaseHelper.PRODUCT_NAME)),
                    productData.getString(productData.getColumnIndex(DatabaseHelper.PRODUCT_PRICE))
            );
            productData.close();

            detailsFragment.setProduct(chosenProduct);

            fragmentManager.beginTransaction().replace(R.id.secondaryFrameLayout, detailsFragment).commit();
        }
    }

    public void removeItem(View view) {
        int id = view.getId();
        String selection = "CART_ID = ?";
        String selectionArgs[] = {String.valueOf(id)};
        Uri selectedProduct = CART_CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();

        getContentResolver().delete(selectedProduct,selection, selectionArgs);
        replaceFragment(new CartFragment(), false);
    }

    public void closeApp(View view){
        this.finishAffinity();
    }

    public void goSettings(View view){
        replaceFragment(new SettingsFragment(), false);
    }
}