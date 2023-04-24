package com.example.shopproject.view.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.shopproject.R;
import com.example.shopproject.mode.Orders;
import com.example.shopproject.presenter.ListordersHistoryPresenter;
import com.example.shopproject.view.ListordersHistoryView;
import com.example.shopproject.view.adapter.AddressAdapter;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;
import com.example.shopproject.view.adapter.orderHistoryAdapter;

import java.util.List;

public class ListOrdersHistory extends AppCompatActivity implements ListordersHistoryView, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private RecyclerView rcv_ordersHistory;
    private TextView lblMemo;
    private orderHistoryAdapter adapter;

    private ListordersHistoryPresenter listordersHistoryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_orders_history_layout);

        initView();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Lịch sử mua hàng");

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_gia));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            swipeRefreshLayout.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(scrollY == 0){
                        swipeRefreshLayout.setEnabled(false);
                    }else{
                        swipeRefreshLayout.setEnabled(true);
                    }
                }
            });
        }

        listordersHistoryPresenter = new ListordersHistoryPresenter(this, this);
        listordersHistoryPresenter.getListOrderHistory();
    }

    private void initView(){
        swipeRefreshLayout = findViewById(R.id.SwipeRefreshLayout_orderHistory);
        toolbar = findViewById(R.id.ToolBar_orderHistory);
        rcv_ordersHistory = findViewById(R.id.rcv_orderHistory);
        lblMemo = findViewById(R.id.lblmemo_orderHistory);
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

    @Override
    public void DislayListOrders(List<Orders> mList) {
        if(mList == null || mList.isEmpty()){
            rcv_ordersHistory.setVisibility(View.GONE);
            lblMemo.setVisibility(View.VISIBLE);
            return;
        }
        rcv_ordersHistory.setVisibility(View.VISIBLE);
        lblMemo.setVisibility(View.GONE);
        adapter = new orderHistoryAdapter(this, mList, new clickListener() {
            @Override
            public void onClickDetailOrders(Orders orders) {
                String namePaymentMethod = "";
                if(!orders.getPaymentMethod().equals("Thanh toán khi nhận hàng")){
                    namePaymentMethod = "Thanh toán bằng thẻ tín dụng";
                }else {
                    namePaymentMethod = "Thanh toán khi nhận hàng";
                }
                Intent intent = new Intent(ListOrdersHistory.this, DetailOrderActicity.class);
                Bundle bundle = new Bundle();
                bundle.putString("PAYMENT_METHOD", namePaymentMethod);
                bundle.putString("TYPE_RECEIVE_KEY", "ORDERS_DETAIL");
                bundle.putSerializable("ORDERS", orders);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rcv_ordersHistory.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_ordersHistory.setLayoutManager(layoutManager);
        rcv_ordersHistory.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
    }

    @Override
    public void DisplayError(String error) {
        AlertDialog.Builder dialogNetWork = new AlertDialog.Builder(this);
        dialogNetWork.setTitle("Thông báo");
        dialogNetWork.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dialogNetWork.setMessage(error);
        dialogNetWork.create().show();
    }

    @Override
    public void onRefresh() {
        listordersHistoryPresenter.getListOrderHistory();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}