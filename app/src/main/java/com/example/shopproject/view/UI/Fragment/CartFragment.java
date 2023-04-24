package com.example.shopproject.view.UI.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.shopproject.R;
import com.example.shopproject.mode.Items;
import com.example.shopproject.orther_handle.AccountManagement;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.CartPresenter;
import com.example.shopproject.view.CartView;
import com.example.shopproject.view.UI.MainActivity;
import com.example.shopproject.view.UI.PayMentActivity;
import com.example.shopproject.view.adapter.CartAdapter;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;

import java.io.Serializable;
import java.util.List;

public class CartFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, CartView {

    private static final String ITEMS_KEY = "items_object_key";
    private static final String LIST_ITEMS_KEY = "list_items_key";

    private Toolbar toolbar;
    private RecyclerView rcv_giohang;
    private CartAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView txtSLGioHang;
    private TextView txtTongTien;
    private AppCompatButton btnThanhToan;
    private RelativeLayout layoutMain;
    private LinearLayout layoutMuaSam;
    private AppCompatButton btnGoHome;
    private ImageButton btnXoaALL;
    private View myView;

    private CartPresenter cartPresenter;
    private MainActivity mainActivity;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.cart_fragmet_layout, container, false);

        initView();
        mainActivity = (MainActivity) getActivity();

        cartPresenter = new CartPresenter(getActivity(), this);
        cartPresenter.getListCart();


        toolbar.setTitle("Giỏ hàng của bạn");
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_gia));

        //Đăng ký để nhận dữ liệu thông qua callbackFragment
        btnGoHome.setOnClickListener(view -> {
            mainActivity.GoHomeFragment();
        });

        btnThanhToan.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), PayMentActivity.class);
            startActivity(intent);
        });

        btnXoaALL.setOnClickListener(view -> {
            cartPresenter.deleteAllItems();
        });

        btnThanhToan.setOnClickListener(view -> {
            cartPresenter.CheckoutCart();
        });

        return myView;
    }

    private void initView(){
        toolbar = myView.findViewById(R.id.ToolBar);
        rcv_giohang = myView.findViewById(R.id.rcv_DSSP_GioHang);
        swipeRefreshLayout = myView.findViewById(R.id.SwipeRefreshLayout_GioHang);
        txtSLGioHang = myView.findViewById(R.id.txtSoLuongHienCo_giohang);
        layoutMain = myView.findViewById(R.id.layout_main_giohang);
        txtTongTien = myView.findViewById(R.id.txtTongTien_giohang);
        btnThanhToan = myView.findViewById(R.id.btnThanhToan);
        btnXoaALL = myView.findViewById(R.id.btnXoaALL);
        layoutMuaSam = myView.findViewById(R.id.layoutMuaSam);
        btnGoHome= myView.findViewById(R.id.btnGoHome);
    }

    public void ReceiverItemsCart(Items items){
        if(items != null){
            Log.e("Tri", items.getName() + " cart");
            cartPresenter.updateListCart(items);
        }

        //cartPresenter.ReceiverItems(items);
    }

    @Override
    public void onStart() {
        super.onStart();Log.e("Tri", "onStart Cart");
        cartPresenter.setIconNumItem();
        cartPresenter.getListCart();
    }

    @Override
    public void onRefresh() {
        adapter = null;
        cartPresenter.getListCart();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void DisplayListCartSuccess(List<Items> listItems) {
        if(listItems == null || listItems.isEmpty()){
            layoutMuaSam.setVisibility(View.VISIBLE);
            layoutMain.setVisibility(View.GONE);
            return;
        }
        layoutMuaSam.setVisibility(View.GONE);
        layoutMain.setVisibility(View.VISIBLE);
        adapter = new CartAdapter(getActivity(), listItems, new clickListener() {
            @Override
            public void deleteItem(Items items) {
                cartPresenter.deleteItemsCart(items);
            }

            @Override
            public void addYeuThich(Items items) {
                cartPresenter.addFavoriteProduct(items);
            }

            @Override
            public void decrement(Items items) {
                cartPresenter.decrementQuantity(items);
            }

            @Override
            public void increment(Items items) {
                cartPresenter.incrementQuantity(items);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcv_giohang.setLayoutManager(manager);
        rcv_giohang.addItemDecoration(new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL));
        rcv_giohang.setHasFixedSize(true);
        rcv_giohang.setAdapter(adapter);

    }

    @Override
    public void DisplayListCartUdated(List<Items> list) {
        if(list.isEmpty()){
            layoutMuaSam.setVisibility(View.VISIBLE);
            layoutMain.setVisibility(View.GONE);
            return;
        }else {
            layoutMuaSam.setVisibility(View.GONE);
            layoutMain.setVisibility(View.VISIBLE);
            if(adapter == null){
                adapter = new CartAdapter(getActivity(), list, new clickListener() {
                    @Override
                    public void deleteItem(Items items) {
                        cartPresenter.deleteItemsCart(items);
                    }

                    @Override
                    public void addYeuThich(Items items) {
                        cartPresenter.addFavoriteProduct(items);
                    }

                    @Override
                    public void decrement(Items items) {
                        cartPresenter.decrementQuantity(items);
                    }

                    @Override
                    public void increment(Items items) {
                        cartPresenter.incrementQuantity(items);
                    }
                });

                LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                rcv_giohang.setLayoutManager(manager);
                rcv_giohang.addItemDecoration(new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL));
                rcv_giohang.setHasFixedSize(true);
                rcv_giohang.setAdapter(adapter);
            }
            else {
                adapter.setData(list);
            }
        }
    }

    @Override
    public void DisplaytotlalPrice(int total) {
        txtTongTien.setText(Publics.formatGia(total));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void DisplayNumProduct(int num) {
        txtSLGioHang.setText(num + " sản phẩm");
    }

    @Override
    public void DisplayIconNumItem(int num) {
        mainActivity.setIconforItemBottomNavigation(2, num);
        Log.e("Tri", "set icon: " + num);
    }

    @Override
    public void CheckoutCart(List<Items> listItems) {
        Intent intent = new Intent(getActivity(), PayMentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("PAYMENT_KEY", true);
        bundle.putSerializable(LIST_ITEMS_KEY, (Serializable) listItems);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void DisplayQuantityError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setMessage(message);

        AlertDialog dialog = builder.create();
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1500);
    }

    @Override
    public void DisplayNoNetWork(String message) {
        AlertDialog.Builder dialogNetWork = new AlertDialog.Builder(getActivity());
        dialogNetWork.setTitle("Thông báo");
        dialogNetWork.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        });
        dialogNetWork.setMessage(message);
        dialogNetWork.create().show();
    }

    @Override
    public void DisplayError(String message) {
        AlertDialog.Builder dialogNetWork = new AlertDialog.Builder(getActivity());
        dialogNetWork.setTitle("Thông báo");
        dialogNetWork.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        });
        dialogNetWork.setMessage(message);
        dialogNetWork.create().show();
    }

}
