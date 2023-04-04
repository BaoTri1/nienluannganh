package com.example.shopproject.view.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopproject.R;
import com.example.shopproject.mode.Product;
import com.example.shopproject.orther_handle.CharacterItemDecoration;
import com.example.shopproject.presenter.ProductSearchPresenter;
import com.example.shopproject.view.ProductView;
import com.example.shopproject.view.adapter.ProductAdapter;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

public class ProductActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ProductView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private EditText editSearch;
    private Button btnSearch;
    private NestedScrollView nestedScrollView;
    private TextView txtNumProduct;
    private AppCompatButton btnFilter;
    private AppCompatButton btnSort;
    private ProductAdapter adapterProduct;
    private RecyclerView rcv_product;
    private TextView lblNotProduct;
    private ProductSearchPresenter productSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_layout);
        initView();
        productSearchPresenter = new ProductSearchPresenter(this, this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Drawable nav = toolbar.getNavigationIcon();
        if(nav != null) {
            nav.setTint(getResources().getColor(R.color.black));
        }

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_gia));

        Bundle bundleReceiver = getIntent().getExtras();
        if(bundleReceiver != null) {
            String category = bundleReceiver.getString("Category");
            editSearch.setText(category);
            productSearchPresenter.SearchProductsByCategory(category);
        }


        btnSearch.setOnClickListener(view -> {
            Toast.makeText(this, "mo search", Toast.LENGTH_SHORT).show();
        });


    }

    private void initView(){
        swipeRefreshLayout = findViewById(R.id.SwipeRefreshLayout_product);
        appBarLayout = findViewById(R.id.AppBarLayout_product);
        toolbar = findViewById(R.id.Toolbar_product);
        editSearch = findViewById(R.id.editSeach);
        btnSearch = findViewById(R.id.btnSearch);
        nestedScrollView = findViewById(R.id.NestedScrollView_product);
        txtNumProduct = findViewById(R.id.txtNumProduct);
        btnFilter = findViewById(R.id.btnFilter);
        btnSort = findViewById(R.id.btnSort);
        rcv_product = findViewById(R.id.rcv_product);
        lblNotProduct = findViewById(R.id.lblNotProduct);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextmenu_detail_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_thongbao_detail:
                Toast.makeText(ProductActivity.this, "Thong bao", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_giohang_detail:
                Intent intent = new Intent(ProductActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("MO_GIO_HANG", 2);
                intent.putExtras(bundle);
                startActivity(intent);
                ProductActivity.this.finish();
                Toast.makeText(ProductActivity.this, "Gio hang", Toast.LENGTH_SHORT).show();
                break;

            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refresh Data", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void DisplayListProduct(List<Product> listProduct) {
        Log.e("Tri", "no do");
        if(listProduct.isEmpty()){
            lblNotProduct.setVisibility(View.VISIBLE);
            return;
        }
        adapterProduct = new ProductAdapter(this, listProduct, new clickListener() {
            @Override
            public void onClickDetailProduct(Product product) {
                Intent intentDetailProduct = new Intent(ProductActivity.this, DetailProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("slug", product.getSlug());
                intentDetailProduct.putExtras(bundle);
                startActivity(intentDetailProduct);
            }
        });
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rcv_product.setLayoutManager(manager);
        rcv_product.addItemDecoration(new CharacterItemDecoration(20));
        rcv_product.setHasFixedSize(true);
        rcv_product.setAdapter(adapterProduct);
    }

    @Override
    public void DisplayNumProducts(String message) {
        txtNumProduct.setText(message);
    }

    @Override
    public void DisplayNoNetWork(String message) {
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
    public void DisplayError(String message) {
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
}