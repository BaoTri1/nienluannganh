package com.example.shopproject.view.UI.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shopproject.R;
import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.Photo;
import com.example.shopproject.mode.Product;
import com.example.shopproject.orther_handle.CharacterItemDecoration;
import com.example.shopproject.orther_handle.LoadMoreData;
import com.example.shopproject.presenter.HomePresenter;
import com.example.shopproject.view.HomeView;
import com.example.shopproject.view.UI.DetailProductActivity;
import com.example.shopproject.view.UI.MainActivity;
import com.example.shopproject.view.UI.ProductActivity;
import com.example.shopproject.view.UI.SearchActivity;
import com.example.shopproject.view.adapter.CatalogAdapter;
import com.example.shopproject.view.adapter.PhotoAdapter;
import com.example.shopproject.view.adapter.ProductAdapter;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment implements HomeView, SwipeRefreshLayout.OnRefreshListener{

    private View myView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private Toolbar toolbar;
    private ImageButton btnSearch;
    private NestedScrollView nestedScrollView;
    private CatalogAdapter catalogAdapter;
    private RecyclerView rcv_catalog;
    private ProductAdapter productAdapter;
    private RecyclerView rcv_product;
    private ProgressBar progressBar;
    private Timer timer;

    private HomePresenter homePresenter;

    private MainActivity mainActivity;

    private static final int ADD_CART_SUCCESS = 1;
    private static final int OPEN_CART = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_home_layout, container, false);
        initView();
        homePresenter = new HomePresenter(getContext(), this);
        setToolbar();

        //set RefreshDataListener
        setOnRefreshData();

        //Create Banner
        homePresenter.createBanner();

        //Create list Catalogs
        homePresenter.createListCatalog();

        //Create list Products
        homePresenter.createListProducts();

        btnSearch.setOnClickListener(view -> {
            Intent intentSearch = new Intent(getActivity(), SearchActivity.class);
            startActivity(intentSearch);
         });
        mainActivity = (MainActivity) getActivity();

        return myView;
    }

    private void initView(){
        swipeRefreshLayout = myView.findViewById(R.id.SwipeRefreshLayout_home);
        collapsingToolbarLayout = myView.findViewById(R.id.CollapsingToolbarLayout_home);
        viewPager = myView.findViewById(R.id.viewpager_home);
        circleIndicator = myView.findViewById(R.id.circleindicator_home);
        toolbar = myView.findViewById(R.id.toolbar_home);
        btnSearch = myView.findViewById(R.id.btnSearch_home);
        nestedScrollView = myView.findViewById(R.id.NestedScrollView_home);
        rcv_catalog = myView.findViewById(R.id.rcv_catalog);
        rcv_product = myView.findViewById(R.id.rcvListProduct_home);
        progressBar = myView.findViewById(R.id.ProgressBar_home);
    }

    @SuppressLint("NonConstantResourceId")
    public void setToolbar() {
        //Set Animation Collapsing for toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            collapsingToolbarLayout.setContentScrimColor(getActivity().getColor(R.color.white));
            collapsingToolbarLayout.setStatusBarScrimColor(getActivity().getColor(R.color.white));
        }

        //Set ContextMenu on Toolbar
        toolbar.inflateMenu(R.menu.contextmenu_home_fragment);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.action_thongbao:
                    Toast.makeText(getContext(), "Thong bao", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });
    }

    public void setOnRefreshData() {
        swipeRefreshLayout.setOnRefreshListener(HomeFragment.this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_gia));
        //set scoll refresh data khi lên đầu trang và load thêm data khi đến cuối trang
        nestedScrollView.setOnScrollChangeListener(new LoadMoreData(swipeRefreshLayout) {
            @Override
            public void loadMoreItem() {

            }

            @Override
            public boolean isLoading() {
                return false;
            }

            @Override
            public boolean isLeastPage() {
                return false;
            }
        });
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getContext(), "RefreshData", Toast.LENGTH_SHORT).show();
        //Create Banner
        homePresenter.createBanner();

        //Create list Catalogs
        homePresenter.createListCatalog();

        //Create list Products
        homePresenter.createListProducts();
        Handler handler = new Handler();
        handler.postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 3000);
    }

    @Override
    public void DisplayBanner(List<Photo> listPhoto) {
        PhotoAdapter adapter = new PhotoAdapter(getContext(), listPhoto);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideBanner(listPhoto);
    }

    private void autoSlideBanner(List<Photo> listPhoto){
        if(listPhoto == null || listPhoto.isEmpty() || viewPager == null)
            return;
        if(timer == null){
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currenItem = viewPager.getCurrentItem();
                        int totalItem = listPhoto.size() - 1;
                        if(currenItem < totalItem){
                            currenItem++;
                            viewPager.setCurrentItem(currenItem);
                        }
                        else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 2000, 4000);
    }

    @Override
    public void DisplayCatalog(List<String> listCatalog) {
        catalogAdapter = new CatalogAdapter(getActivity(), listCatalog, new clickListener() {
            @Override
            public void itemCatalogClick(String category) {
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TYPE_KEY", "Category");
                bundle.putString("KEYWORK_KEY", category);
                intent.putExtras(bundle);
                //startActivity(intent);
                startActivityForResult(intent, OPEN_CART);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcv_catalog.setLayoutManager(manager);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        rcv_catalog.addItemDecoration(decoration);
        rcv_catalog.setHasFixedSize(true);
        rcv_catalog.setAdapter(catalogAdapter);
    }

    @Override
    public void DisplayListProduct(List<Product> listProduct) {
        productAdapter = new ProductAdapter(getContext(), listProduct, new clickListener() {
            @Override
            public void onClickDetailProduct(Product product) {
                Intent intentDetailProduct = new Intent(getActivity(), DetailProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("slug", product.getSlug());
                intentDetailProduct.putExtras(bundle);
//                startActivity(intentDetailProduct);
                startActivityForResult(intentDetailProduct, ADD_CART_SUCCESS);
            }

            @Override
            public void onClickLike(Product product) {
                Toast.makeText(getContext(), "Đã Like", Toast.LENGTH_SHORT).show();
            }
        });
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        rcv_product.setLayoutManager(manager);
        rcv_product.setHasFixedSize(true);
        rcv_product.addItemDecoration(new CharacterItemDecoration(20));
        rcv_product.setAdapter(productAdapter);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_CART_SUCCESS && resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            if(bundle != null) {
                if((Items) bundle.getSerializable("ITEMS_KEY") != null)
                    mainActivity.addItemCart((Items) bundle.getSerializable("ITEMS_KEY"));
                String action = bundle.getString("Action", "");
                if(action.equals("AddItemAndOpenCart")){
                    mainActivity.GoCartFragment();
                }
            }
        }
        else if(requestCode == OPEN_CART && resultCode == Activity.RESULT_OK){
            mainActivity.GoCartFragment();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Tri", "Destroy");
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }
}