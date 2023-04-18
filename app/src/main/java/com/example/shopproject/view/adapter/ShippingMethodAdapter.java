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
import com.example.shopproject.mode.ShippingMethod;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;

import java.util.List;


public class ShippingMethodAdapter extends RecyclerView.Adapter<ShippingMethodAdapter.ShippingMethodViewHolder>{

    private Context context;
    private List<ShippingMethod> mList;
    private clickListener listener;

    public ShippingMethodAdapter(Context context, List<ShippingMethod> mList, clickListener listener) {
        this.context = context;
        this.mList = mList;
        this.listener = listener;
    }

    public void setData(List<ShippingMethod> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShippingMethodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shipping_method, parent, false);
        return new ShippingMethodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShippingMethodViewHolder holder, int position) {
        ShippingMethod method = mList.get(position);

        if(method == null)
            return;

        holder.txtName_PriceMethod.setText(method.getNameMethod() + " - " + Publics.formatGia(method.getPriceMethod()));
        holder.txtTimeOrder.setText("Thời gian nhận hàng dự kiến khoảng " + method.getTimeOrders() + " ngày kể từ ngày đặt hàng.");
        holder.layoutMethod.setOnClickListener(v ->{
            listener.onClickShippingMethod(method);
        });
    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        return 0;
    }

    public class ShippingMethodViewHolder extends RecyclerView.ViewHolder{

        private TextView txtName_PriceMethod;
        private TextView txtTimeOrder;
        private RelativeLayout layoutMethod;

        public ShippingMethodViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName_PriceMethod = itemView.findViewById(R.id.txtName_PriceMethod);
            txtTimeOrder = itemView.findViewById(R.id.txtTimeOrder);
            layoutMethod = itemView.findViewById(R.id.layoutMethod);
        }
    }
}
