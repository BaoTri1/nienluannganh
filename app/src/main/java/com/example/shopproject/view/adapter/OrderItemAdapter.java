package com.example.shopproject.view.adapter;

import android.provider.Telephony;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderItemAdapter {

    public class OrderItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgProduct;
        private TextView txtNameProduct;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
