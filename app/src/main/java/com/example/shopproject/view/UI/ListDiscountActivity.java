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
import com.example.shopproject.mode.Discount;
import com.example.shopproject.presenter.ListDiscountPresenter;
import com.example.shopproject.view.ListDiscountView;
import com.example.shopproject.view.adapter.DiscountAdapter;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;
import com.example.shopproject.view.adapter.orderHistoryAdapter;

import java.util.List;

public class ListDiscountActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ListDiscountView {

    private static final String DISCOUNT_KEY = "discount_key";
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private RecyclerView rcvDiscount;
    private TextView lblMemo;
    private DiscountAdapter adapter;

    private ListDiscountPresenter listDiscountPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_discount_layout);

        initView();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Danh sách mã giảm giá");

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

        listDiscountPresenter = new ListDiscountPresenter(this, this);
        listDiscountPresenter.getListDiscount();
    }

    private void initView(){
        swipeRefreshLayout = findViewById(R.id.SwipeRefreshLayout_discount);
        toolbar = findViewById(R.id.ToolBar_discount);
        rcvDiscount = findViewById(R.id.rcv_discount);
        lblMemo = findViewById(R.id.lblmemo_discount);
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
    public void onRefresh() {
        listDiscountPresenter.getListDiscount();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void DislayListOrders(List<Discount> mList) {
        if(mList == null || mList.isEmpty()){
            rcvDiscount.setVisibility(View.GONE);
            lblMemo.setVisibility(View.VISIBLE);
            return;
        }
        rcvDiscount.setVisibility(View.VISIBLE);
        lblMemo.setVisibility(View.GONE);
        if(adapter == null){
            adapter = new DiscountAdapter(this, mList, new clickListener() {
                @Override
                public void onClickDiscount(Discount discount) {
                    Intent intent = new Intent(ListDiscountActivity.this, PayMentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(DISCOUNT_KEY, discount);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            rcvDiscount.setLayoutManager(layoutManager);
            rcvDiscount.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
            rcvDiscount.setAdapter(adapter);

        }else {
            adapter.setData(mList);
        }
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
}