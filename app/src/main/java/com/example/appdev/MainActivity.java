package com.example.appdev;

import static com.example.appdev.HomeFragment.mMasterDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.appdev.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

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
            Log.i("Two sides", "true");
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
            intent.putExtra("id", String.valueOf(view.getId()));
            startActivity(intent);
        } else{
            FragmentManager fragmentManager = getSupportFragmentManager();
            ProductDetailsFragment detailsFragment = new ProductDetailsFragment();

            dbManager = new DatabaseManager(this);
            try {
                dbManager.open();
                Log.i("dbmanager.open", "done");
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("dbmanager.open", "failed");
            }

            Product chosenProduct = null;
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
                if (Objects.equals(product.id, String.valueOf(view.getId()))) {
                    chosenProduct = product;
                }
            }

            detailsFragment.setProduct(chosenProduct);

            fragmentManager.beginTransaction().replace(R.id.secondaryFrameLayout, detailsFragment).commit();
        }
    }

    public void removeItem(View view) {
        dbManager = new DatabaseManager(view.getContext());
        try {
            dbManager.open();
            Log.i("dbmanager.open", "done");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("dbmanager.open", "failed");
        }
        int id = view.getId();
        dbManager.deleteCart((long) id);
        replaceFragment(new CartFragment(), false);
    }

    public void closeApp(View view){
        this.finishAffinity();
    }

    public void goSettings(View view){
        replaceFragment(new SettingsFragment(), false);
    }
}