package com.example.shopproject.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopproject.R;
import com.example.shopproject.mode.Items;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    private Context mContext;
    private List<Items> mList;
    private clickListener listener;
    private String color;
    private String size;

    public CartAdapter(Context mContext, List<Items> mList, clickListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Items> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_layout, parent, false);
        return new CartViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Items items = mList.get(position);
        if(items == null)
            return;

        Glide.with(mContext)
                .load(items.getImage())
                .placeholder(R.mipmap.anh2)
                .into(holder.imgSP);

        holder.txtNameSP.setText(items.getName());

        if(items.getIndexColor() == -1){
            holder.txtPhanLoaiSP.setVisibility(View.GONE);
        }else {
            if(items.getIndexSize() == -1){
                holder.txtPhanLoaiSP.setText("Màu sắc: " + items.getColor());
            }else {
                holder.txtPhanLoaiSP.setText("Màu sắc: " + items.getColor() + ", Size: " + items.getSize());
            }
        }

        holder.txtGiaSP.setText(Publics.formatGia(items.getPrice()));

        holder.editSoLuong.setText(String.valueOf(items.getQuantity()));

        holder.btnCong.setOnClickListener(view -> {
            listener.increment(items);
        });

        holder.btnTru.setOnClickListener(view -> {
            listener.decrement(items);
        });

        holder.btnMoMenu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(mContext, holder.btnMoMenu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_giohang_fragment, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.action_ThemYeuThich:
                            listener.addYeuThich(items);
                            break;

                        case R.id.action_XoaKhoiGioHang:
                            listener.deleteItem(items);
                            break;
                    }
                    return true;
                }
            });
            popupMenu.show();
        });

    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        return 0;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgSP;
        private TextView txtNameSP;
        private TextView txtPhanLoaiSP;
        private TextView txtGiaSP;
        private Button btnTru;
        private Button btnCong;
        private EditText editSoLuong;
        private ImageButton btnMoMenu;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSP = itemView.findViewById(R.id.imgSP_giohang);
            txtNameSP = itemView.findViewById(R.id.txtNameSP_giohang);
            txtPhanLoaiSP = itemView.findViewById(R.id.txtPhanLoai_giohang);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP_giohang);
            btnTru = itemView.findViewById(R.id.decrement);
            btnCong = itemView.findViewById(R.id.increment);
            editSoLuong = itemView.findViewById(R.id.display);
            btnMoMenu = itemView.findViewById(R.id.btnMoMenu_giohang);
        }
    }
}
