package com.example.shopproject.view.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopproject.R;
import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.Photo;
import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.Review;
import com.example.shopproject.mode.User;
import com.example.shopproject.orther_handle.AccountManagement;
import com.example.shopproject.orther_handle.CharacterItemDecoration;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.DetailProductPresenter;
import com.example.shopproject.view.DetailProductView;
import com.example.shopproject.view.UI.Fragment.BottomSheetProductFragment;
import com.example.shopproject.view.UI.Fragment.CartFragment;
import com.example.shopproject.view.UI.Fragment.callback.CallbackFragment;
import com.example.shopproject.view.adapter.PhotoAdapter;
import com.example.shopproject.view.adapter.ProductAdapter;
import com.example.shopproject.view.adapter.ReviewAdapter;
import com.example.shopproject.view.adapter.ViewPagerAdapter;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.relex.circleindicator.CircleIndicator;

public class DetailProductActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, DetailProductView, CallbackFragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private Toolbar toolbar;
    private ImageButton btnSearch;
    private NestedScrollView nestedScrollView;
    private TextView txtNameProduct;
    private TextView txtPriceProduct;
    private CheckBox btnLike;
    private AppCompatButton btnAddCart;
    private AppCompatButton btnBuyNow;
    private TextView txtDescribe;
    private View viewShadow;
    private AppCompatButton btnExpand_collaps;
    private TextView lblNotReview;
    private RelativeLayout layout_review;
    private ImageView imgRate;
    private TextView txtRate;
    private TextView txtNumReview;
    private RecyclerView rcv_Review;
    private RecyclerView rcv_listProduct;
    private DetailProductPresenter detailProductPresenter;
    private boolean isExpanded = false;
    private String slug;
    private Product product;
    private User user;
    private AppCompatButton btnReview;
    private TextView textCartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        initView();

        //Set ToolBar
        setToolbar();

        //Set Refresh Data
        setOnRefreshData();

        detailProductPresenter = new DetailProductPresenter(this, this);
        detailProductPresenter.getUser();

        //Receive Data from HomeFragment
        slug = getIntent().getExtras().getString("slug", "");
        user = (User) getIntent().getExtras().getSerializable("USER_KEY");
        getInforProduct(slug);

        //Create ListProduct
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Đang tải dữ liệu...");
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = detailProductPresenter.getListIdProductFavorite();
                for (int i = 0; i < list.size(); i++){
                    if(product.get_id().equals(list.get(i))){
                        btnLike.setChecked(true);
                    }
                }
                detailProductPresenter.createListProduct();
                dialog.dismiss();
            }
        }, 2000);
//        detailProductPresenter.createListProduct();

        btnSearch.setOnClickListener(view -> {
            Intent intent = new Intent(DetailProductActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        btnLike.setOnClickListener(view -> {
            if(btnLike.isChecked()){
                Toast.makeText(DetailProductActivity.this, "Đã like", Toast.LENGTH_SHORT).show();
                detailProductPresenter.addFavoriteProduct(this.product.get_id());
            }
            else {
                Toast.makeText(DetailProductActivity.this, "Đã unlike", Toast.LENGTH_SHORT).show();
            }
        });

        btnAddCart.setOnClickListener(view -> {
            if(AccountManagement.checkStateLogin(this)){
                BottomSheetProductFragment bottom = new BottomSheetProductFragment(DetailProductActivity.this, slug, "Thêm vào giỏ hàng");
                bottom.show(getSupportFragmentManager(), bottom.getTag());
            }else {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
                builder.setTitle("Thông báo");
                builder.setNegativeButton("Đăng nhập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DetailProductActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setMessage("Đăng nhập để tiếp tục thực hiện.");
                builder.create().show();
            }
        });

        btnBuyNow.setOnClickListener(view -> {
            if(AccountManagement.checkStateLogin(this)){
                BottomSheetProductFragment bottom = new BottomSheetProductFragment(DetailProductActivity.this, slug, "Mua ngay");
                bottom.show(getSupportFragmentManager(), bottom.getTag());
            }else {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
                builder.setTitle("Thông báo");
                builder.setNegativeButton("Đăng nhập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DetailProductActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setMessage("Đăng nhập để tiếp tục thực hiện.");
                builder.create().show();
            }
        });

        btnExpand_collaps.setOnClickListener(view -> {
            isExpanded = !isExpanded;
            if(isExpanded){
                txtDescribe.setMaxLines(Integer.MAX_VALUE);
                viewShadow.setVisibility(View.INVISIBLE);
                btnExpand_collaps.setText("Rút gọn");
            }
            else {
                txtDescribe.setMaxLines(1);
                viewShadow.setVisibility(View.VISIBLE);
                btnExpand_collaps.setText("Xem thêm");
            }
        });

        btnReview.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReviewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ID_KEY", this.product.get_id());
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void initView(){
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_detail);
        appBarLayout = findViewById(R.id.AppBarLayout_detail);
        collapsingToolbarLayout = findViewById(R.id.CollapsingToolbarLayout_detail);
        viewPager = findViewById(R.id.viewpager_detail);
        circleIndicator = findViewById(R.id.circleindicator_detail);
        toolbar = findViewById(R.id.toolbar_detail);
        btnSearch = findViewById(R.id.btnSearch_detail);
        nestedScrollView = findViewById(R.id.nestedScrollView_detail);
        txtNameProduct = findViewById(R.id.txtNameProduct_detail);
        txtPriceProduct = findViewById(R.id.txtPriceProduct_detail);
        btnLike = findViewById(R.id.btnlike_detail);
        btnAddCart = findViewById(R.id.btnAddCart_detail);
        btnBuyNow = findViewById(R.id.btnBuyNow_detail);
        txtDescribe = findViewById(R.id.txtDescribe);
        viewShadow = findViewById(R.id.viewshadow_detail);
        btnExpand_collaps = findViewById(R.id.btnExpand_collaps);
        lblNotReview = findViewById(R.id.lblNotReview);
        layout_review = findViewById(R.id.layout_review);
        imgRate = findViewById(R.id.img_rate);
        txtRate = findViewById(R.id.txtRate);
        txtNumReview = findViewById(R.id.txtSoRate);
        rcv_Review = findViewById(R.id.rcv_Review);
        rcv_listProduct = findViewById(R.id.rcv_listProduct_detail);
        btnReview = findViewById(R.id.btnReview);
    }

    public void getInforProduct(String slug){
        detailProductPresenter.getProduct(slug);
    }

    @SuppressLint("NonConstantResourceId")
    public void setToolbar() {

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Drawable nav = toolbar.getNavigationIcon();
        if(nav != null) {
            nav.setTint(getResources().getColor(R.color.black));
        }

        //Set Animation Collapsing for toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            collapsingToolbarLayout.setContentScrimColor(this.getColor(R.color.white));
            collapsingToolbarLayout.setStatusBarScrimColor(this.getColor(R.color.white));
        }

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) > 200)
                    btnSearch.setVisibility(View.VISIBLE);
                else btnSearch.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contextmenu_detail_product, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_giohang_detail);

        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        detailProductPresenter.setupBadge();

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
            case R.id.action_giohang_detail:
                backAction("OPENCART");
                break;

            case android.R.id.home:
                backAction("");
                break;
        }
        return true;
    }

    public void backAction(String action){
        Intent intent = new Intent(DetailProductActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ACTION_KEY", action);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void setOnRefreshData() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_gia));
        //set scoll refresh data khi lên đầu trang và load thêm data khi đến cuối trang
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == 0){
                    swipeRefreshLayout.setEnabled(true);
                }
                else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refresh Data", Toast.LENGTH_SHORT).show();
        getInforProduct(slug);
        detailProductPresenter.createListProduct();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void DisplayInforProduct(Product product) {
        this.product = product;
        Log.e("Tri1","detail: " + this.product.getName());
        txtNameProduct.setText(product.getName());
        txtPriceProduct.setText(Publics.formatGia(product.getPrice()));
        txtDescribe.setText(product.getDescription());
    }

    @Override
    public void DisplayImagesProduct(List<Photo> list) {
        PhotoAdapter adapter = new PhotoAdapter(this, list);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void DisplayReviews(float rate, int numRviews, List<Review> reviews) {
        if(reviews.size() == 0){
            lblNotReview.setVisibility(View.VISIBLE);
            layout_review.setVisibility(View.GONE);
            return;
        }
        lblNotReview.setVisibility(View.GONE);
        layout_review.setVisibility(View.VISIBLE);
        imgRate.setImageResource(Publics.getImgRateforId(rate));
        txtRate.setText("(" + rate + "/5)");
        txtNumReview.setText(String.valueOf(numRviews));
        ReviewAdapter adapterRview = new ReviewAdapter(reviews);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_Review.setLayoutManager(manager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        rcv_Review.addItemDecoration(dividerItemDecoration);
        rcv_Review.setAdapter(adapterRview);
        rcv_Review.setHasFixedSize(true);
    }

    @Override
    public void DisplayListProduct(List<Product> mList, List<String> listId) {
        ProductAdapter adapter = new ProductAdapter(this, mList, listId, new clickListener() {
            @Override
            public void onClickDetailProduct(Product product) {
                Intent intentDetailProduct = new Intent(DetailProductActivity.this, DetailProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("slug", product.getSlug());
                intentDetailProduct.putExtras(bundle);
                startActivity(intentDetailProduct);
            }

            @Override
            public void onClickLike(Product product) {
                detailProductPresenter.addFavoriteProduct(product.get_id());
            }
        });
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rcv_listProduct.setLayoutManager(manager);
        rcv_listProduct.setAdapter(adapter);
        rcv_listProduct.setHasFixedSize(true);
        rcv_listProduct.addItemDecoration(new CharacterItemDecoration(20));
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

    @Override
    public void UpdateBadge(int number) {
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        backAction("");
    }
}