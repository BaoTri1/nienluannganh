package com.example.shopproject.view.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.shopproject.R;
import com.example.shopproject.mode.ShippingAddress;
import com.example.shopproject.sqlite.Database.ShopProjectDatabase;
import com.example.shopproject.sqlite.Entity.Address;
import com.example.shopproject.view.adapter.AddressAdapter;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;

import java.util.List;

import javax.sql.StatementEvent;

public class ListAddressActicity extends AppCompatActivity {

    private static final String SHIPPING_ADDRESS_KEY = "shipping_address_key";

    private Toolbar toolbar;
    private RecyclerView rcv_address;
    private AddressAdapter addressAdapter;
    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_address_layout);

        toolbar = findViewById(R.id.ToolBar_address);
        rcv_address = findViewById(R.id.rcv_address);

        action = "";
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            action = bundle.getString("ACTION_KEY");
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Địa chỉ");
        actionBar.setDisplayHomeAsUpEnabled(true);

        String email = ShopProjectDatabase.getInstance(this).accountDAO().getEmail();
        List<Address> list = ShopProjectDatabase.getInstance(this).addressDAO().getListAddress(email);
        addressAdapter = new AddressAdapter(this, list, new clickListener() {
            @Override
            public void onClickAddress(Address address) {
                if(action.equals("READ")){
                    Intent intent = new Intent(ListAddressActicity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    ShippingAddress shippingAddress = new ShippingAddress(address.getName(), address.getAddress(), address.getProvince(), address.getDistrict(), address.getWard(), address.getSDT());
                    Intent intent = new Intent(ListAddressActicity.this, PayMentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SHIPPING_ADDRESS_KEY, shippingAddress);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        rcv_address.setAdapter(addressAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_address.setLayoutManager(layoutManager);
        rcv_address.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
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