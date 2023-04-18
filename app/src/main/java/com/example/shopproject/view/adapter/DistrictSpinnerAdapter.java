package com.example.shopproject.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shopproject.R;
import com.example.shopproject.mode.District;
import com.example.shopproject.mode.Province;

import java.util.List;

public class DistrictSpinnerAdapter extends ArrayAdapter<District> {


    public DistrictSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<District> objects) {
        super(context, resource, objects);
    }

    @Nullable
    @Override
    public District getItem(int position) {
        return super.getItem(position);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent, false);
        TextView txtSelect = convertView.findViewById(R.id.tv_selected);

        District district = this.getItem(position);
        if(district != null){
            txtSelect.setText(district.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_spinner, parent, false);
        TextView txtItem = convertView.findViewById(R.id.itemSpinner);

        District district = this.getItem(position);
        if(district != null){
            txtItem.setText(district.getName());
        }
        return convertView;
    }
}
