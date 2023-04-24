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
import com.example.shopproject.sqlite.Entity.Address;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder>{

    private Context context;
    private List<Address> mList;
    private clickListener listener;

    public AddressAdapter(Context context, List<Address> mList, clickListener listener) {
        this.context = context;
        this.mList = mList;
        this.listener = listener;
    }

    public void setData(List<Address> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_layout, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = mList.get(position);
        if (address == null)
            return;
        holder.txtID.setText("Địa chỉ " + (position + 1));
        holder.txtName_phone.setText(address.getName() + " | " + address.getSDT());
        holder.txtAddress.setText(address.getAddress());
        holder.layout.setOnClickListener(v -> {
            listener.onClickAddress(address);
        });
    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        return 0;
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder{

        private TextView txtID;
        private TextView txtName_phone;
        private TextView txtAddress;
        private RelativeLayout layout;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);

            txtID = itemView.findViewById(R.id.txtSTT);
            txtName_phone = itemView.findViewById(R.id.txtName_phone);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            layout = itemView.findViewById(R.id.layout_address);
        }
    }
}
