package com.example.shopproject.view.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.shopproject.R;
import com.example.shopproject.mode.orderResponse;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.view.adapter.OrderItemAdapter;

import java.io.Serializable;

public class DetailOrderActicity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rcv_items;
    private OrderItemAdapter adapter;
    private TextView txttoltalItems;
    private TextView txtPriceShipping;
    private TextView txtToltalBill;
    private TextView txtDateCreate;
    private TextView txtPayment;
    private TextView txtDelivered;
    private TextView txtID;
    private TextView txtName_phone;
    private TextView txtAddress;
    private AppCompatButton btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order_acticity);

        initView();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Chi tiết hóa đơn");
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnClose.setOnClickListener(v -> {
            finish();
        });

        Bundle bundleReceice = getIntent().getExtras();
        if(bundleReceice != null){
            String nameMethodPayment = bundleReceice.getString("PAYMENT_METHOD");
            DisplayDetailOrder(nameMethodPayment, (orderResponse) bundleReceice.getSerializable("ORDER_RESPONSE"));
        }
    }

    private void DisplayDetailOrder(String nameMethodPayment, orderResponse response) {
        adapter = new OrderItemAdapter(this, response.getOrder().getOrderItems());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_items.setLayoutManager(layoutManager);
        rcv_items.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        rcv_items.setAdapter(adapter);

        txttoltalItems.setText(Publics.formatGia(response.getOrder().getItemsPrice()));
        txtPriceShipping.setText(Publics.formatGia(response.getOrder().getShippingPrice()));
        txtToltalBill.setText(Publics.formatGia(response.getOrder().getTotalPrice()));
        txtPayment.setText(nameMethodPayment);

        txtID.setText(response.getOrder().get_id());
        txtName_phone.setText(response.getOrder().getShippingAddress().getFullName());
        txtAddress.setText(response.getOrder().getShippingAddress().getAddress());

        txtDateCreate.setText(Publics.formatDate(response.getOrder().getCreateAt()));

        if(response.getOrder().isDelivered()){
            txtDelivered.setText("Đã giao hàng");
        }else {
            txtDelivered.setText("Chưa giao hàng");
        }
    }

    private void initView(){
        toolbar = findViewById(R.id.ToolBar_detailOrder);
        rcv_items = findViewById(R.id.rcv_item);
        txttoltalItems = findViewById(R.id.txttoltalItems);
        txtPriceShipping = findViewById(R.id.txtPriceShipping);
        txtToltalBill = findViewById(R.id.txtToltalBill);
        txtDateCreate = findViewById(R.id.txtDateCreate);
        txtPayment = findViewById(R.id.txtPayment);
        txtDelivered = findViewById(R.id.txtDelivered);
        txtName_phone = findViewById(R.id.txtName_phone);
        txtAddress = findViewById(R.id.txtAddress);
        txtID = findViewById(R.id.txtId);
        btnClose = findViewById(R.id.btnClose);
    }
}