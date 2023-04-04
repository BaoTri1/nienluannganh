package com.example.shopproject.view.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject.R;
import com.example.shopproject.mode.Size;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;

import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.SizeViewHolder>{

    private Context mContext;
    private List<Size> mList;
    private clickListener listener;
    private int positionSelected = 0;
    private boolean isFirstClick = false;

    public SizeAdapter(Context mContext, List<Size> mList, clickListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }

    public void setData(List<Size> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_layout, parent, false);
        return new SizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeViewHolder holder, int position) {
        Size size = mList.get(position);
        if(size == null)
            return;

        holder.txtSize.setText(size.getName());

        if(positionSelected == position){
            holder.layoutSize.setBackgroundResource(R.drawable.custom_bg_size_selected);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.txtSize.setTextColor(mContext.getColor(R.color.white));
            }
        }
        else {
            holder.layoutSize.setBackgroundResource(R.drawable.custom_bg_size_unselected);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.txtSize.setTextColor(mContext.getColor(R.color.black));
            }
        }

        if(size.getCountSize() == 0){
            holder.layoutSize.setBackgroundResource(R.drawable.bg_lock);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.txtSize.setTextColor(mContext.getColor(R.color.gray));
            }
        }else {
            holder.layoutSize.setOnClickListener(view ->{
                listener.itemSizeClick(position, size);
                int previousPosition = positionSelected;
                positionSelected = holder.getAbsoluteAdapterPosition();
                notifyItemChanged(previousPosition);
                notifyItemChanged(positionSelected);
            });
        }

        if(position == 0 && !isFirstClick){
            holder.itemView.post(new Runnable() {
                @Override
                public void run() {
                    holder.layoutSize.performClick();
                    isFirstClick = true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        return 0;
    }

    public static class SizeViewHolder extends RecyclerView.ViewHolder{


        private final TextView txtSize;
        private final RelativeLayout layoutSize;

        public SizeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSize = itemView.findViewById(R.id.txtSize);
            layoutSize = itemView.findViewById(R.id.layoutSize);
        }
    }

}
