package com.example.shopproject.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject.R;
import com.example.shopproject.mode.Color;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder>{

    private Context mContext;
    private List<Color> mList;
    private clickListener listener;
    private int positionSelected = 0;
    private boolean isFirstClick = false;

    public ColorAdapter(Context mContext, List<Color> mList, clickListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }

    public void setData(List<Color> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_layout, parent, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        Color color = mList.get(position);
        if(color == null)
            return;


        switch (color.getName()){
            case "Do":
                holder.txtColor.setBackgroundResource(R.color.Do);
                break;

            case "Vang":
                holder.txtColor.setBackgroundResource(R.color.Vang);
                break;

            case "XanhDuong":
                holder.txtColor.setBackgroundResource(R.color.XanhDuong);
                break;

            case "Trang":
                holder.txtColor.setBackgroundResource(R.color.Trang);
                break;

            case "Den":
                holder.txtColor.setBackgroundResource(R.color.Den);
                break;
        }

        if(positionSelected == position) {
            holder.layoutColor.setBackgroundResource(R.drawable.bg_selected);
        }
        else {
            holder.layoutColor.setBackgroundResource(R.drawable.bg_unselected);
        }

        holder.layoutColor.setOnClickListener(view ->{
            listener.itemColorClick(position, color);
            int previousPosition = positionSelected;
            positionSelected = holder.getAbsoluteAdapterPosition();
            notifyItemChanged(previousPosition);
            notifyItemChanged(positionSelected);
        });

        if(position==0 && !isFirstClick){
            holder.itemView.post(new Runnable() {
                @Override
                public void run() {
                    holder.layoutColor.performClick();
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

    public class ColorViewHolder extends RecyclerView.ViewHolder{

        private TextView txtColor;
        private LinearLayout layoutColor;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            txtColor = itemView.findViewById(R.id.txtColor);
            layoutColor = itemView.findViewById(R.id.layoutColor);
        }
    }


}


