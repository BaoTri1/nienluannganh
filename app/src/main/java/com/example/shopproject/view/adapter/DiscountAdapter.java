package com.example.shopproject.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject.R;
import com.example.shopproject.mode.Discount;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;

import java.util.List;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.DiscountViewHolder>{

    private Context context;
    private List<Discount> mList;
    private clickListener listener;

    public DiscountAdapter(Context context, List<Discount> mList, clickListener listener) {
        this.context = context;
        this.mList = mList;
        this.listener = listener;
    }

    public void setData(List<Discount> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discount_layout, parent, false);
        return new DiscountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountViewHolder holder, int position) {
        Discount discount = mList.get(position);
        if(discount == null)
            return;

        holder.lblSTT.setText("Mã " + (position+1));
        holder.txtCode.setText(discount.getCode());
        holder.txtDescribe.setText("Giảm giá " + discount.getDiscountPercentage() + "% tồng giá trị đơn hàng.");
        String dateStart = Publics.formatDate(discount.getStartDate());
        String dateEnd = Publics.formatDate(discount.getEndDate());
        holder.txtTime.setText("Mã có giá trị từ " + dateStart + " đến " + dateEnd);
        holder.layout.setOnClickListener(v -> {
            listener.onClickDiscount(discount);

        });

    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        return 0;
    }

    public class DiscountViewHolder extends RecyclerView.ViewHolder{

        private TextView lblSTT;
        private TextView txtCode;
        private TextView txtDescribe;
        private TextView txtTime;
        private RelativeLayout layout;

        public DiscountViewHolder(@NonNull View itemView) {
            super(itemView);

            lblSTT = itemView.findViewById(R.id.lblSTT);
            txtCode = itemView.findViewById(R.id.txtCode);
            txtDescribe = itemView.findViewById(R.id.txtDescribe);
            txtTime = itemView.findViewById(R.id.txtTime);
            layout = itemView.findViewById(R.id.layout_discount);
        }
    }
}
