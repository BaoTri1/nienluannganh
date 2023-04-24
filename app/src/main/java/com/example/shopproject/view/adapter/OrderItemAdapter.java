package com.example.shopproject.view.adapter;

import android.content.Context;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopproject.R;
import com.example.shopproject.mode.Items;
import com.example.shopproject.orther_handle.Publics;

import java.util.List;
import java.util.concurrent.TransferQueue;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>{

    private Context context;
    private List<Items> mList;

    public OrderItemAdapter(Context context, List<Items> mList) {
        this.context = context;
        this.mList = mList;
    }

    public void setData(List<Items> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_layout, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        Items items = mList.get(position);
        if(items == null)
            return;

        Glide.with(context)
                .load(items.getImage())
                .placeholder(R.mipmap.imgloadwait)
                .into(holder.imgProduct);

        holder.txtNameProduct.setText(items.getName());

        if(items.getIndexColor() == -1){
            holder.txtDetailProduct.setVisibility(View.GONE);
        }else {
            if(items.getIndexSize() == -1){
                holder.txtDetailProduct.setText("Màu sắc: " + items.getColor());
            }else {
                holder.txtDetailProduct.setText("Màu sắc: " + items.getColor() + ", Size: " + items.getSize());
            }
        }

        holder.txtPriceProduct.setText(Publics.formatGia(items.getPrice()));
        holder.txtQuantityProduct.setText("x" + items.getQuantity());
    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        return 0;
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgProduct;
        private TextView txtNameProduct;
        private TextView txtDetailProduct;
        private TextView txtPriceProduct;
        private TextView txtQuantityProduct;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgSP_order);
            txtNameProduct = itemView.findViewById(R.id.txtNameSP_order);
            txtDetailProduct = itemView.findViewById(R.id.txtPhanLoai_order);
            txtPriceProduct = itemView.findViewById(R.id.txtPrice_order);
            txtQuantityProduct = itemView.findViewById(R.id.txtQuantity_order);
        }
    }
}
