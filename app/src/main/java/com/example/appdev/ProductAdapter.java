package com.example.appdev;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("Test for productAdapter On Create", "done");
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
        return new ProductViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Log.i("Test for productAdapter on Bind", "done");
    }

    @Override
    public int getItemCount() {

        return 20;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage = itemView.findViewById(R.id.product_image);
        TextView productTitle = itemView.findViewById(R.id.cardTitle);
        TextView productPrice = itemView.findViewById(R.id.cardPrice);

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.i("Test for productAdapter constructor", "done");
        }
    }
}
