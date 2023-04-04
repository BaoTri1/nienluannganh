package com.example.shopproject.view.UI.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.shopproject.R;
import com.example.shopproject.mode.Product;
import com.example.shopproject.presenter.FavoriteProductPresenter;
import com.example.shopproject.view.FavoriteProductView;
import com.example.shopproject.view.UI.DetailProductActivity;
import com.example.shopproject.view.adapter.FavoriteProductAdapter;
import com.example.shopproject.view.adapter.ProductAdapter;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;


import java.util.List;

public class FavoriteProductFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, FavoriteProductView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private TextView txtNum;
    private TextView lblShopping;
    private ImageButton btnFilter;
    private FavoriteProductAdapter adapter;
    private RecyclerView rcv_ListProductFavorite;
    private View myView;
    private RelativeLayout layout_main;
    private FavoriteProductPresenter favoriteProductPresenter;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.favorite_product_fragment_layout, container, false);

        initView();
        setupToolBar();

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_gia));

        favoriteProductPresenter = new FavoriteProductPresenter(getActivity(), this);
        favoriteProductPresenter.getListFavoriteProduct();

        btnFilter.setOnClickListener(view -> {
            createPopupMenuFilter();
        });


        return myView;
    }

    private void initView(){
        swipeRefreshLayout = myView.findViewById(R.id.SwipeRefreshLayout_YeuThich);
        toolbar = myView.findViewById(R.id.ToolBar_yeuthich);
        txtNum = myView.findViewById(R.id.txtSoLuongHienCo);
        btnFilter = myView.findViewById(R.id.btnLoc);
        lblShopping = myView.findViewById(R.id.lblmuasam);
        layout_main = myView.findViewById(R.id.layout_main);
        rcv_ListProductFavorite = myView.findViewById(R.id.rcv_DSSP_YeuThich);
    }

    private void setupToolBar(){
        if(toolbar != null){
            toolbar.setTitle("Sản phẩm yêu thích");
            toolbar.inflateMenu(R.menu.contextmenu_home_fragment);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.action_thongbao:
                            Toast.makeText(getActivity(), "Thông Báo", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return true;
                }
            });
        }
    }

    private void createPopupMenuFilter(){
        PopupMenu popupMenu = new PopupMenu(getActivity(), btnFilter);
        popupMenu.getMenuInflater().inflate(R.menu.menu_loc_ds_yeuthich, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.action_GiaCaoThap:
                    favoriteProductPresenter.SortList("HIGH_LOW");
                    break;

                case R.id.action_GiaThapCao:
                    favoriteProductPresenter.SortList("LOW_HIGH");
                    break;
            }

            return true;
        });
        popupMenu.show();
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(), "Refresh data", Toast.LENGTH_SHORT).show();
        favoriteProductPresenter.getListFavoriteProduct();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void DisplayListFavoriteProduct(List<Product> mList) {
        if(mList != null)
            txtNum.setText(mList.size() + " sản phẩm");

        adapter = new FavoriteProductAdapter(mList, getActivity(), new clickListener(){
            @Override
            public void onClickAddCart(Product product) {
                Toast.makeText(getActivity(), "Thêm " + product.getName() + " vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickBuyNow(Product product) {
                Toast.makeText(getActivity(), "Mua " + product.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickDelete(Product product) {
                favoriteProductPresenter.deleteProdut(product);
            }

            @Override
            public void onClickDetailProduct(Product product) {
                Intent intentDetailProduct = new Intent(getActivity(), DetailProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("slug", product.getSlug());
                intentDetailProduct.putExtras(bundle);
                startActivity(intentDetailProduct);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcv_ListProductFavorite.setLayoutManager(manager);
        rcv_ListProductFavorite.setAdapter(adapter);
        rcv_ListProductFavorite.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rcv_ListProductFavorite.setHasFixedSize(true);

    }

    @Override
    public void DisplaySortList(List<Product> mList) {
        adapter.setData(mList);
    }

    @Override
    public void DisplayChangeList(List<Product> mList) {
        adapter.setData(mList);
    }

    @Override
    public void DisplayisListEmpty() {
        layout_main.setVisibility(View.GONE);
        lblShopping.setVisibility(View.VISIBLE);
    }

    @Override
    public void DisplayNoNetWork(String message) {
        AlertDialog.Builder dialogNetWork = new AlertDialog.Builder(getActivity());
        dialogNetWork.setTitle("Thông báo");
        dialogNetWork.setNegativeButton("Thoát", (dialogInterface, i) -> getActivity().finish());
        dialogNetWork.setMessage(message);
        dialogNetWork.create().show();
    }

    @Override
    public void DisplayError(String message) {
        AlertDialog.Builder dialogNetWork = new AlertDialog.Builder(getActivity());
        dialogNetWork.setTitle("Thông báo");
        dialogNetWork.setNegativeButton("Thoát", (dialogInterface, i) -> getActivity().finish());
        dialogNetWork.setMessage(message);
        dialogNetWork.create().show();
    }
}
