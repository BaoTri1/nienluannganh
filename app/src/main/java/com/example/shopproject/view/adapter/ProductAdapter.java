package com.example.shopproject.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopproject.R;
import com.example.shopproject.mode.Product;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private List<Product> mList;
    private clickListener listener;
    private Context mContext;

    public ProductAdapter(Context mContext, List<Product> mList, clickListener listener) {
        this.mList = mList;
        this.listener = listener;
        this.mContext = mContext;
    }

    public void setData(List<Product> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mList.get(position);
        if(product == null)
            return;

         Glide.with(mContext)
                 .load(product.getImage())
                 .placeholder(R.mipmap.adirunner2)
                 .into(holder.imgProduct);

         holder.txtNameProduct.setText(product.getName());
         holder.txtPriceProduct.setText(Publics.formatGia(product.getPrice()));
         holder.productLayout.setOnClickListener(view -> {
             listener.onClickDetailProduct(product);
         });

         holder.btnLike.setOnClickListener(view -> {
             listener.onClickLike(product);
         });
    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        return 0;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgProduct;
        private TextView txtNameProduct;
        private TextView txtPriceProduct;
        private CheckBox btnLike;
        private RelativeLayout productLayout;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtNameProduct = itemView.findViewById(R.id.txtnameSanPham);
            txtPriceProduct = itemView.findViewById(R.id.txtPriceProduct);
            btnLike = itemView.findViewById(R.id.btnLikeProduct);
            productLayout = itemView.findViewById(R.id.itemProduct_layout);
        }
    }
}