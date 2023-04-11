package com.example.shopproject.view.UI.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject.R;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.BottomFilterPresenter;
import com.example.shopproject.view.BottomFilterView;
import com.example.shopproject.view.UI.Fragment.callback.CallbackFragment;
import com.example.shopproject.view.adapter.CatalogAdapter;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BottomFilterFragment extends BottomSheetDialogFragment implements BottomFilterView {

    private Context mContext;
    private String catalog;
    private String price;
    private String rate;
    private NestedScrollView nestedScrollView;
    private TextView txtCategory;
    private TextView txtPrice;
    private TextView txtRate;
    private RecyclerView rcv_category;
    private AppCompatButton btnPrice_all;
    private AppCompatButton btnPrice_100_300;
    private AppCompatButton btnPrice_300_500;
    private AppCompatButton btnPrice_500_1000;
    private ImageButton btnRate5;
    private ImageButton btnRate4;
    private ImageButton btnRate3;
    private ImageButton btnRate2;
    private ImageButton btnRate1;
    private ImageButton btnRate0;
    private AppCompatButton btnReset;
    private AppCompatButton btnApply;
    private SharedPreferences pfrFilter;
    private BottomFilterPresenter bottomFilterPresenter;
    private CallbackFragment callbackFragment;

    public BottomFilterFragment(Context mContext) {
        this.mContext = mContext;
        this.bottomFilterPresenter = new BottomFilterPresenter(mContext, this);
        pfrFilter = mContext.getSharedPreferences("Filter", Context.MODE_PRIVATE);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbackFragment = (CallbackFragment) context;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_filter, null);
        dialog.setContentView(view);

        initView(view);

        btnPrice_100_300.setText(Publics.formatGia(100000) + "-" + Publics.formatGia(300000));
        btnPrice_300_500.setText(Publics.formatGia(300000) + "-" + Publics.formatGia(500000));
        btnPrice_500_1000.setText(Publics.formatGia(500000) + "-" + Publics.formatGia(1000000));

        bottomFilterPresenter.createListCatalog();

        bottomFilterPresenter.getFilter();

        btnPrice_all.setOnClickListener(v -> {
            txtPrice.setText("Giá: " + "tất cả");
            price = "all";
        });

        btnPrice_100_300.setOnClickListener(v -> {
            txtPrice.setText("Giá: " + Publics.formatGia(100000) + "-" + Publics.formatGia(300000));
            price = "100000-300000";
        });

        btnPrice_300_500.setOnClickListener(v -> {
            txtPrice.setText("Giá: " + Publics.formatGia(300000) + "-" + Publics.formatGia(500000));
            price = "300000-500000";
        });

        btnPrice_500_1000.setOnClickListener(v -> {
            txtPrice.setText("Giá: " + Publics.formatGia(500000) + "-" + Publics.formatGia(1000000));
            price = "500000-1000000";
        });

        btnRate0.setOnClickListener(v ->{
            txtRate.setText("Đánh giá từ 0 sao trở lên");
            rate = "0";
        });

        btnRate1.setOnClickListener(v ->{
            txtRate.setText("Đánh giá từ 1 sao trở lên");
            rate = "1";
        });

        btnRate2.setOnClickListener(v ->{
            txtRate.setText("Đánh giá từ 2 sao trở lên");
            rate = "2";
        });

        btnRate3.setOnClickListener(v ->{
            txtRate.setText("Đánh giá từ 3 sao trở lên");
            rate = "3";
        });

        btnRate4.setOnClickListener(v ->{
            txtRate.setText("Đánh giá từ 4 sao trở lên");
            rate = "4";
        });

        btnRate5.setOnClickListener(v ->{
            txtRate.setText("Đánh giá từ 5 sao trở lên");
            rate = "5";
        });

        btnReset.setOnClickListener(v ->{
            txtCategory.setText("");
            txtPrice.setText("");
            txtRate.setText("");
            bottomFilterPresenter.clearFilter();
            callbackFragment.getListProductFilter(catalog, price, rate);
            dialog.dismiss();
        });

        btnApply.setOnClickListener(v -> {
            bottomFilterPresenter.saveFilter(catalog, price, rate);
            callbackFragment.getListProductFilter(catalog, price, rate);
            dialog.dismiss();
        });

        return dialog;
    }

    private void initView(View view){
        nestedScrollView = view.findViewById(R.id.NestedScrollView_filter);
        txtCategory = view.findViewById(R.id.txtCategory);
        txtPrice = view.findViewById(R.id.txtPrice);
        txtRate = view.findViewById(R.id.txtRate);
        rcv_category = view.findViewById(R.id.rcv_category);
        btnPrice_all = view.findViewById(R.id.btnPrice_all);
        btnPrice_100_300 = view.findViewById(R.id.btnPrice_100_300);
        btnPrice_300_500 = view.findViewById(R.id.btnPrice_300_500);
        btnPrice_500_1000 = view.findViewById(R.id.btnPrice_500_1000);
        btnRate0 = view.findViewById(R.id.imgRate0);
        btnRate1 = view.findViewById(R.id.imgRate1);
        btnRate2 = view.findViewById(R.id.imgRate2);
        btnRate3 = view.findViewById(R.id.imgRate3);
        btnRate4 = view.findViewById(R.id.imgRate4);
        btnRate5 = view.findViewById(R.id.imgRate5);
        btnReset = view.findViewById(R.id.btnReset);
        btnApply = view.findViewById(R.id.btnApply);
    }

    @Override
    public void DisplayCategorySuccess(List<String> mList) {
        CatalogAdapter adapter = new CatalogAdapter(getActivity(), mList, new clickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void itemCatalogClick(String category) {
                txtCategory.setText("Danh mục: " + category);
                 catalog = category;
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rcv_category.setLayoutManager(manager);
        rcv_category.setAdapter(adapter);
        rcv_category.setHasFixedSize(true);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void DisplayFilter(String category, String price, String rate) {
        if(category.equals("")){
            txtCategory.setText("");
            Log.e("Tri", "category: " + category);
        }else{
            txtCategory.setText("Danh mục: " + category);
        }

        if(price.equals("")){
            txtPrice.setText("");
        }else {
            txtPrice.setText("Giá: " + price);
        }

        if(rate.equals("")){
            txtRate.setText("");
        }else {
            txtRate.setText("Đánh giá từ " + rate + " sao trở lên");
        }
    }

    @Override
    public void DisplayError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Tri", "destroy");
    }
}
