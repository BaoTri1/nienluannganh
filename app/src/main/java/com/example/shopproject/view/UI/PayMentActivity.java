package com.example.shopproject.view.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopproject.R;
import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.ShippingAddress;
import com.example.shopproject.mode.ShippingMethod;
import com.example.shopproject.mode.orderResponse;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.PaymentPresenter;
import com.example.shopproject.view.PayMentView;
import com.example.shopproject.view.UI.Fragment.BottomSheetPaymentMethod;
import com.example.shopproject.view.UI.Fragment.BottomSheetShippingMethod;
import com.example.shopproject.view.UI.Fragment.callback.CallbackFragment;
import com.example.shopproject.view.adapter.OrderItemAdapter;
import com.google.android.material.appbar.AppBarLayout;

import java.io.Serializable;
import java.util.List;

public class PayMentActivity extends AppCompatActivity implements PayMentView, CallbackFragment {

    private static final int SHIPPING_ADDRESS_REQUEST = 1;
    private static final String SHIPPING_ADDRESS_KEY = "shipping_address_key";
    private static final String LIST_ITEMS_KEY = "list_items_key";

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private NestedScrollView nestedScrollView;
    private TextView txtName_phone;
    private TextView txtAddress;
    private TextView lblMemo_address;
    private AppCompatButton btnFillInAddress;
    private RecyclerView rcv_order;
    private OrderItemAdapter adapterOrder;
    private TextView txtshippingMethod;
    private TextView txtTime;
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

    private PaymentPresenter paymentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ment);

        initView();

        paymentPresenter = new PaymentPresenter(this, this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Thanh toán");

        paymentPresenter.ReceiveListItemOrder(getIntent().getBooleanExtra("PAYMENT_KEY", false) ,(List<Items>) getIntent().getSerializableExtra(LIST_ITEMS_KEY));

        btnFillInAddress.setOnClickListener(view ->{
            Intent intent = new Intent(this, EditAddressActivity.class);
            startActivityForResult(intent, SHIPPING_ADDRESS_REQUEST);
        });

        btnSelectShippingMethod.setOnClickListener(v -> {
            BottomSheetShippingMethod bottomSheetShippingMethod = new BottomSheetShippingMethod();
            bottomSheetShippingMethod.show(getSupportFragmentManager(), bottomSheetShippingMethod.getTag());
        });

        btnSelectPayment.setOnClickListener(v -> {
            BottomSheetPaymentMethod bottomSheetPaymentMethod = new BottomSheetPaymentMethod();
            bottomSheetPaymentMethod.show(getSupportFragmentManager(), bottomSheetPaymentMethod.getTag());
        });

        btnOrder.setOnClickListener(v -> {
            if(txtName_phone.getText().toString().trim().isEmpty() || txtAddress.getText().toString().trim().isEmpty()){
                DisplayMessage("Hãy nhập địa chỉ nhận hàng trước khi thanh toán", R.layout.custom_toast_payment_layout, R.drawable.ic_baseline_location_on_24);
                return;
            }
            if(txtshippingMethod.getText().toString().trim().isEmpty()) {
                DisplayMessage("Hãy chọn phương thức vận chuyển trước khi thanh toán", R.layout.custom_toast_payment_layout, R.drawable.ic_baseline_local_shipping_24);
                return;
            }
            if(txtPayment.getText().toString().trim().isEmpty()){
                DisplayMessage("Hãy chọn phương thức thanh toán", R.layout.custom_toast_payment_layout, R.drawable.ic_baseline_payments_24);
                return;
            }
            paymentPresenter.makePayments(txtPayment.getText().toString().trim());
            Toast.makeText(this, "thanh toán", Toast.LENGTH_SHORT).show();
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == SHIPPING_ADDRESS_REQUEST){
            Bundle bundle = data.getExtras();
            if(bundle != null){
                paymentPresenter.ReceiveShippingAddress((ShippingAddress) bundle.getSerializable(SHIPPING_ADDRESS_KEY));
            }
        }
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
        txtTime = findViewById(R.id.txttime);
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

    private void DisplayMessage(String mesage, int layoutid, int idImg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(layoutid, null);

        TextView txtMessage = layout.findViewById(R.id.txtMessageToast_payment);
        ImageView img = layout.findViewById(R.id.imgToast_payment);

        txtMessage.setText(mesage);
        img.setImageResource(idImg);
        builder.setView(layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1700);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void DisplayShippingAddress(String fullName, String sdt, String address) {
        lblMemo_address.setVisibility(View.GONE);
        txtName_phone.setText(fullName + " | " + sdt);
        txtAddress.setText(address);
    }

    @Override
    public void DisplayListItemOrder(List<Items> items) {
        adapterOrder = new OrderItemAdapter(this, items);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_order.setLayoutManager(manager);
        rcv_order.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        rcv_order.setAdapter(adapterOrder);
    }

    @Override
    public void DisplayToltalPriceProduct(String toltalPriceProduct) {
        txtToltalProduct.setText(toltalPriceProduct);
    }

    @Override
    public void DisplayToltalPayment(String toltalPricePayment) {
        txtToltalPayment.setText(toltalPricePayment);
        txt_ToltalPayment.setText(toltalPricePayment);
    }

    @Override
    public void OpenDetailOrder(String namePaymentMethod, orderResponse orderResponse) {
        finish();
        Intent intent = new Intent(this, DetailOrderActicity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PAYMENT_METHOD", namePaymentMethod);
        bundle.putSerializable("ORDER_RESPONSE", orderResponse);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void DisplayNoNetwork(String message) {
        AlertDialog.Builder dialogNetWork = new AlertDialog.Builder(this);
        dialogNetWork.setTitle("Thông báo");
        dialogNetWork.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dialogNetWork.setMessage(message);
        dialogNetWork.create().show();
    }

    @Override
    public void passShippingMethod(ShippingMethod shippingMethod) {
        lblmemo_shippingMethod.setVisibility(View.GONE);
        txtToltalShipping.setText(Publics.formatGia(shippingMethod.getPriceMethod()));
        txtshippingMethod.setText(shippingMethod.getNameMethod() + " - " + Publics.formatGia(shippingMethod.getPriceMethod()));
        txtTime.setText("Thời gian nhận hàng dự kiến khoảng " + shippingMethod.getTimeOrders() + " ngày kể từ ngày đặt hàng.");
        paymentPresenter.ToltalPayment(shippingMethod.getPriceMethod());
    }

    @Override
    public void passPaymentMethod(String nameMethod) {
        lblmemo_Payment.setVisibility(View.GONE);
        txtPayment.setText(nameMethod);
    }
}