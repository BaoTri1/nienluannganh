package com.example.shopproject.view.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.shopproject.R;
import com.google.android.material.appbar.AppBarLayout;

public class PayMentActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private NestedScrollView nestedScrollView;
    private TextView txtName_phone;
    private TextView txtAddress;
    private TextView lblMemo_address;
    private AppCompatButton btnFillInAddress;
    private RecyclerView rcv_order;
    private TextView txtshippingMethod;
    private TextView lblmemo_shippingMethod;
    private AppCompatButton btnSelectShippingMethod;
    private TextView txtPayment;
    private TextView lblmemo_Payment;
    private AppCompatButton btnSelectPayment;
    private TextView txtToltalProduct;
    private TextView txtToltalShipping;
    private TextView txtToltalPayment;
    private TextView txt_ToltalPayment;
    private AppCompatButton btnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ment);

        initView();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Thanh toán");

    }

    private void initView(){
        appBarLayout = findViewById(R.id.AppBarLayout_payment);
        toolbar = findViewById(R.id.ToolBar_payment);
        nestedScrollView = findViewById(R.id.NestedScrollView_payment);
        txtName_phone = findViewById(R.id.txtName_phone);
        txtAddress = findViewById(R.id.txtAddress);
        lblMemo_address = findViewById(R.id.lblmemo_address);
        btnFillInAddress = findViewById(R.id.btnFillInAddress);
        rcv_order = findViewById(R.id.rcv_order);
        txtshippingMethod = findViewById(R.id.txtshippingMethod);
        lblmemo_shippingMethod = findViewById(R.id.lblmemo_shippingMethod);
        btnSelectShippingMethod = findViewById(R.id.btnSelectShippingMethod);
        txtPayment = findViewById(R.id.txtPayment);
        lblmemo_Payment = findViewById(R.id.lblmemo_payment);
        btnSelectPayment = findViewById(R.id.btnSelectPayment);
        txtToltalProduct = findViewById(R.id.txtToltalProduct);
        txtToltalShipping = findViewById(R.id.txtToltalShipping);
        txtToltalPayment = findViewById(R.id.txtToltalPayment);
        txt_ToltalPayment = findViewById(R.id.txt_ToltalPayment);
        btnOrder = findViewById(R.id.btnOrder);
    }
}