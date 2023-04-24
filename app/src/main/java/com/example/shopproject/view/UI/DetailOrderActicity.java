package com.example.shopproject.view.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.shopproject.R;
import com.example.shopproject.mode.Orders;
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
            String type = bundleReceice.getString("TYPE_RECEIVE_KEY");
            String nameMethodPayment = bundleReceice.getString("PAYMENT_METHOD");
            if(type.equals("ORDERS_RESPONSE")){
                orderResponse response = (orderResponse) bundleReceice.getSerializable("ORDER_RESPONSE");
                DisplayDetailOrder(nameMethodPayment, response.getOrder());
            }else if(type.equals("ORDERS_DETAIL")){
                Orders orders = (Orders) bundleReceice.getSerializable("ORDERS");
                DisplayDetailOrder(nameMethodPayment, orders);
            }
        }
    }

    private void DisplayDetailOrder(String nameMethodPayment, Orders orders) {
        adapter = new OrderItemAdapter(this, orders.getOrderItems());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_items.setLayoutManager(layoutManager);
        rcv_items.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        rcv_items.setAdapter(adapter);

        txttoltalItems.setText(Publics.formatGia(orders.getItemsPrice()));
        txtPriceShipping.setText(Publics.formatGia(orders.getShippingPrice()));
        txtToltalBill.setText(Publics.formatGia(orders.getTotalPrice()));
        txtPayment.setText(nameMethodPayment);

        txtID.setText(orders.get_id());
        txtName_phone.setText(orders.getShippingAddress().getFullName() + " | " + orders.getShippingAddress().getPhone());
        txtAddress.setText(orders.getShippingAddress().getAddress());

        txtDateCreate.setText(Publics.formatDate(orders.getCreateAt()));

        if(orders.isDelivered()){
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}