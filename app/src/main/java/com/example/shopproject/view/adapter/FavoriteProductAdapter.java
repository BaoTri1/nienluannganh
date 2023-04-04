package com.example.shopproject.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopproject.R;
import com.example.shopproject.mode.Product;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;

import java.util.List;

public class FavoriteProductAdapter extends RecyclerView.Adapter<FavoriteProductAdapter.FavoriteProductViewHolder>{

    private List<Product> mList;
    private clickListener listener;
    private Context mContext;

    public FavoriteProductAdapter(List<Product> mList,Context mContext, clickListener listener) {
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
    public FavoriteProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_favorite_layout, parent, false);
        return new FavoriteProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteProductViewHolder holder, int position) {
        Product product = mList.get(position);
        if(product == null)
            return;

        Glide.with(mContext)
                .load(product.getImage())
                .placeholder(R.mipmap.aohoodie1)
                .into(holder.imgProductFavorite);
        holder.txtNameProductFavorite.setText(product.getName());
        holder.txtPriceProductFavorite.setText(Publics.formatGia(product.getPrice()));
        holder.btnMenu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(mContext, holder.btnMenu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_yeuthich_fragment, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()){
                    case R.id.action_MuaNgay:
                        listener.onClickBuyNow(product);
                        break;

                    case R.id.action_Xoa:
                        listener.onClickDelete(product);
                        break;
                }
                return true;
            });
            popupMenu.show();
        });
        holder.btnAddCart.setOnClickListener(view -> {
            listener.onClickAddCart(product);
        });

        holder.layoutContain.setOnClickListener(view -> {
            listener.onClickDetailProduct(product);
        });

    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        return 0;
    }

    public class FavoriteProductViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout layoutContain;
        private ImageView imgProductFavorite;
        private TextView txtNameProductFavorite;
        private TextView txtPriceProductFavorite;
        private ImageButton btnMenu;
        private AppCompatButton btnAddCart;

        public FavoriteProductViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutContain = itemView.findViewById(R.id.layout_contain_yeuthich);
            imgProductFavorite = itemView.findViewById(R.id.imgSP_YeuThich);
            txtNameProductFavorite = itemView.findViewById(R.id.txtNameSP_YeuThich);
            txtPriceProductFavorite = itemView.findViewById(R.id.txtGiaSP_YeuThich);
            btnMenu = itemView.findViewById(R.id.btnMoMenu);
            btnAddCart = itemView.findViewById(R.id.btnThemGioHang);
        }
    }
}
