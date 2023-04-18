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
import com.example.shopproject.mode.Ward;

import java.util.List;

public class WardSpinnerAdapter extends ArrayAdapter<Ward> {


    public WardSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Ward> objects) {
        super(context, resource, objects);
    }

    @Nullable
    @Override
    public Ward getItem(int position) {
        return super.getItem(position);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent, false);
        TextView txtSelect = convertView.findViewById(R.id.tv_selected);

        Ward ward = this.getItem(position);
        if(ward != null){
            txtSelect.setText(ward.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_spinner, parent, false);
        TextView txtItem = convertView.findViewById(R.id.itemSpinner);

        Ward ward = this.getItem(position);
        if(ward != null){
            txtItem.setText(ward.getName());
        }
        return convertView;
    }
}
