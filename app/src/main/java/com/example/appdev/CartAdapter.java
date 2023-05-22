package com.example.appdev;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    ArrayList<Cart> cartList;

    public CartAdapter(ArrayList<Cart> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card, parent, false);
        return new CartAdapter.CartViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        holder.cartClick.setId(Integer.parseInt(cartList.get(position).id));
        holder.cartTitle.setText(cartList.get(position).name);
        holder.cartPrice.setText(cartList.get(position).price);
        holder.cartAmount.setText(cartList.get(position).amount);
    }

    @Override
    public int getItemCount() {
        return this.cartList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        Button cartClick = itemView.findViewById(R.id.cardCartClick);
        TextView cartTitle = itemView.findViewById(R.id.cardCartTitle);
        TextView cartPrice = itemView.findViewById(R.id.cardCartPrice);
        TextView cartAmount = itemView.findViewById(R.id.cardCartAmount);

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
