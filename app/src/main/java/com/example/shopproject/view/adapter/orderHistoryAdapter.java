package com.example.shopproject.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject.R;
import com.example.shopproject.mode.Orders;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;

import java.util.List;

public class orderHistoryAdapter extends RecyclerView.Adapter<orderHistoryAdapter.orderHistoryViewHolder>{

    private Context mContext;
    private List<Orders> mList;
    private clickListener listener;

    public orderHistoryAdapter(Context mContext, List<Orders> mList, clickListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }

    public void setData(List<Orders> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public orderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_history_layout, parent, false);
        return new orderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderHistoryViewHolder holder, int position) {
        Orders orders = mList.get(position);
        if(orders == null)
            return;

        holder.txtSTT.setText("Hóa đơn " + (position + 1));
        OrderItemAdapter adapter = new OrderItemAdapter(mContext, orders.getOrderItems());
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        holder.rcv_item.setLayoutManager(layoutManager);
        holder.rcv_item.addItemDecoration(new DividerItemDecoration(mContext, RecyclerView.VERTICAL));
        holder.rcv_item.setAdapter(adapter);

        holder.txtToltalBill.setText(Publics.formatGia(orders.getTotalPrice()));
        if(orders.isDelivered()){
            holder.txtDelivered.setText("Đã giao hàng");
        }else {
            holder.txtDelivered.setText("Chưa giao hàng");
        }

        if(orders.getPaymentMethod().equals("Thanh toán khi nhận hàng")){
            holder.txtPayment.setText("Thanh toán khi nhận hàng");
        }else {
            holder.txtPayment.setText("Đã thanh toán bằng thẻ tín dụng");
        }

        holder.btnDetail.setOnClickListener(v -> {
            listener.onClickDetailOrders(orders);
        });
    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        return 0;
    }

    public class orderHistoryViewHolder extends RecyclerView.ViewHolder{

        private TextView txtSTT;
        private RecyclerView rcv_item;
        private TextView txtToltalBill;
        private TextView txtDelivered;
        private TextView txtPayment;
        private AppCompatButton btnDetail;

        public orderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSTT = itemView.findViewById(R.id.txtSTT);
            rcv_item = itemView.findViewById(R.id.rcv_item);
            txtToltalBill = itemView.findViewById(R.id.txtToltalBill);
            txtDelivered = itemView.findViewById(R.id.txtDelivered);
            txtPayment = itemView.findViewById(R.id.txtPayment);
            btnDetail = itemView.findViewById(R.id.btnDetail);
        }
    }
}
