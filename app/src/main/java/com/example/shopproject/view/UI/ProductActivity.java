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
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopproject.R;
import com.example.shopproject.mode.Product;
import com.example.shopproject.orther_handle.CharacterItemDecoration;
import com.example.shopproject.presenter.ProductSearchPresenter;
import com.example.shopproject.view.ProductView;
import com.example.shopproject.view.UI.Fragment.BottomFilterFragment;
import com.example.shopproject.view.UI.Fragment.callback.CallbackFragment;
import com.example.shopproject.view.adapter.ProductAdapter;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

public class ProductActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ProductView, CallbackFragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private EditText editSearch;
    private Button btnSearch;
    private NestedScrollView nestedScrollView;
    private TextView txtNumProduct;
    private TextView textCartItemCount;
    private AppCompatButton btnFilter;
    private AppCompatButton btnSort;
    private ProductAdapter adapterProduct;
    private RecyclerView rcv_product;
    private TextView lblNotProduct;
    private ProductSearchPresenter productSearchPresenter;

    private String category;
    private String query;
    private String price;
    private String rate;
    private String order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_layout);
        initView();

        category = "all";
        query = "all";
        price = "all";
        rate = "all";
        order = "newest";

        Log.e("Tri", "category: " + category + "\n"
                                + "query: " + query + "\n"
                                + "price: " + price + "\n"
                                + "rate: " + rate + "\n"
                                + "order: " + order);

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
            String type = bundleReceiver.getString("TYPE_KEY");
            String keywork = bundleReceiver.getString("KEYWORK_KEY");
            editSearch.setText(keywork);
            if(type.equals("Category")){
                query = "all";
                category = keywork;
                productSearchPresenter.SearchProductsByCategory(keywork);
            }
            else if(type.equals("Query")){
                productSearchPresenter.SearchProductByQuery(keywork);
                query = keywork;
                Log.e("Tri", "category: " + category + "\n"
                        + "query: " + query + "\n"
                        + "price: " + price + "\n"
                        + "rate: " + rate + "\n"
                        + "order: " + order);
            }

        }

        btnSearch.setOnClickListener(view -> {
            Intent intentSearch = new Intent(this, SearchActivity.class);
            startActivity(intentSearch);
        });

        btnFilter.setOnClickListener(view ->{
            BottomFilterFragment fragment = new BottomFilterFragment(this);
            fragment.show(getSupportFragmentManager(), fragment.getTag());
        });

        btnSort.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, btnSort);
            popupMenu.getMenuInflater().inflate(R.menu.menu_order, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.Latest:
                            order = "newest";
                            break;

                        case R.id.PriceLowHight:
                            order = "lowest";
                            break;

                        case R.id.PriceHightLow:
                            order = "highest";
                            break;

                        case R.id.Rivews:
                            order = "toprated";
                            break;
                    }
                    Log.e("Tri", "category: " + category + "\n"
                            + "query: " + query + "\n"
                            + "price: " + price + "\n"
                            + "rate: " + rate + "\n"
                            + "order: " + order);
                    productSearchPresenter.getListProductFilter(category, query, price, rate, order);
                    return true;
                }
            });
            popupMenu.show();
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
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.contextmenu_detail_product, menu);
//        return true;
        getMenuInflater().inflate(R.menu.contextmenu_detail_product, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_giohang_detail);

        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        productSearchPresenter.setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_thongbao_detail:
                Toast.makeText(ProductActivity.this, "Thong bao", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_giohang_detail:
                backAction("OPENCART");
                Toast.makeText(ProductActivity.this, "Gio hang", Toast.LENGTH_SHORT).show();
                break;

            case android.R.id.home:
                backAction("");
                break;
        }
        return true;
    }

    public void backAction(String action){
        Intent intent = new Intent(ProductActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ACTION_KEY", action);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
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
        if(listProduct.isEmpty()){
            lblNotProduct.setVisibility(View.VISIBLE);
            rcv_product.setVisibility(View.GONE);
            return;
        }
        if(adapterProduct == null){
            adapterProduct = new ProductAdapter(this, listProduct, new clickListener() {
                @Override
                public void onClickDetailProduct(Product product) {
                    Intent intentDetailProduct = new Intent(ProductActivity.this, DetailProductActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("slug", product.getSlug());
                    intentDetailProduct.putExtras(bundle);
                    startActivity(intentDetailProduct);
                    ProductActivity.this.finish();
                }
            });
            GridLayoutManager manager = new GridLayoutManager(this, 2);
            rcv_product.setLayoutManager(manager);
            rcv_product.addItemDecoration(new CharacterItemDecoration(20));
            rcv_product.setHasFixedSize(true);
            rcv_product.setAdapter(adapterProduct);
        }else {
            adapterProduct.setData(listProduct);
        }
    }

    @Override
    public void DisplayNumProducts(String message) {
        txtNumProduct.setText(message);
    }

    @Override
    public void getListProductFilter(String catalog, String price, String rate) {
        this.category = catalog;
        this.price = price;
        this.rate = rate;
        Log.e("Tri", "category: " + category + "\n"
                + "query: " + query + "\n"
                + "price: " + price + "\n"
                + "rate: " + rate + "\n"
                + "order: " + order);
        productSearchPresenter.getListProductFilter(category, query, this.price, this.rate, order);
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
    public void DisplayBadge(int number) {
        if (textCartItemCount != null) {
            if (number == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            }
            else {
                textCartItemCount.setText(String.valueOf(Math.min(number, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
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