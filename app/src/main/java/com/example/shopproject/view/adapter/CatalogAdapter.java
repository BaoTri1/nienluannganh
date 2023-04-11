package com.example.shopproject.view.adapter;

import android.content.Context;
import android.os.Build;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject.R;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;

import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CategoryViewHolder>{

    private Context mContext;
    private List<String> mListCategory;
    private clickListener listener;

    public CatalogAdapter(Context mContext, List<String> mListCategory, clickListener clickListener) {
        this.mContext = mContext;
        this.mListCategory = mListCategory;
        this.listener = clickListener;
    }

    public void setDate(List<String> mList){
        this.mListCategory = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalog_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String category = mListCategory.get(position);
        if(category == null)
            return;

        holder.txtnameCategory.setText(category);
        holder.layoutCategory.setOnClickListener(view -> {
            listener.itemCatalogClick(category);

        });
    }

    @Override
    public int getItemCount() {
        if(mListCategory != null)
            return mListCategory.size();
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        private TextView txtnameCategory;
        private LinearLayout layoutCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtnameCategory = itemView.findViewById(R.id.txtNameCategory);
            layoutCategory = itemView.findViewById(R.id.Layout_Category);
        }
    }
}
