package com.example.shopproject.view.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shopproject.R;
import com.example.shopproject.mode.Items;
import com.example.shopproject.orther_handle.AccountManagement;
import com.example.shopproject.sqlite.Entity.Account;
import com.example.shopproject.view.UI.Fragment.CartFragment;
import com.example.shopproject.view.UI.Fragment.callback.CallbackFragment;
import com.example.shopproject.view.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity{

    private BottomNavigationView navigationView;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;
    private Toast toast;
    private long backPressedtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);

        //setup viewpager chứa Fragment
        viewPager2 = (ViewPager2) findViewById(R.id.contain_viewpager2);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);


        //thuoc tinh giúp load trước 4 fragment
        viewPager2.setOffscreenPageLimit(4);

        //setup animation vuốt ngang cho viewpager2
        //setupAnimationViewPager2();

        //tắt tính năng vuốt viewpager2
        viewPager2.setUserInputEnabled(false);

        //set sự kiện click cho BottomNavigation
        BottomNavigationClick();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String message = bundle.getString("MESSAGE_KEY");
            if(!message.equals("")){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Thông báo");
                builder.setNegativeButton("Thử lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setMessage(message);
                builder.create().show();
            }else{

            }
        }
    }

    /*private void setupAnimationViewPager2(){
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        navigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;

                    case 1:
                        navigationView.getMenu().findItem(R.id.action_favorite).setChecked(true);
                        break;

                    case 2:
                        navigationView.getMenu().findItem(R.id.action_cart).setChecked(true);
                        break;

                    case 3:
                        navigationView.getMenu().findItem(R.id.action_user).setChecked(true);
                        break;
                }
            }
        });
    }*/

    private void BottomNavigationClick(){
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        viewPager2.setCurrentItem(0);
                        Toast.makeText(MainActivity.this, "Trang chủ", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_favorite:
                        viewPager2.setCurrentItem(1);
                        Toast.makeText(MainActivity.this, "Yêu thích", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_cart:
                        viewPager2.setCurrentItem(2);
                        Toast.makeText(MainActivity.this, "Giỏ hàng", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_user:
                        viewPager2.setCurrentItem(3);
                        Toast.makeText(MainActivity.this, "Hồ sơ", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    public void setIconforItemBottomNavigation(int idItem, int number){
        navigationView.getOrCreateBadge(idItem).setNumber(number);
    }

    public void GoHomeFragment(){
        viewPager2.setCurrentItem(0);
        navigationView.getMenu().findItem(R.id.action_home).setChecked(true);
    }

    public void GoCartFragment(){
        viewPager2.setCurrentItem(2);
        navigationView.getMenu().findItem(R.id.action_cart).setChecked(true);
    }

    public void addItemCart(Items items){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + 2);
        if (fragment instanceof CartFragment) {
            CartFragment cartFragment = (CartFragment) fragment;
            cartFragment.ReceiverItemsCart(items);
        }else {
            Log.e("Tri", "null");
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Tri", "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onBackPressed() {
        if(backPressedtime + 2000 > System.currentTimeMillis()){
            toast.cancel();
            super.onBackPressed();
            return;
        }else {
            toast = Toast.makeText(MainActivity.this, "Nhấn back lại lần nữa để thoát khỏi ứng dụng", Toast.LENGTH_SHORT);
            toast.show();
        }
        backPressedtime = System.currentTimeMillis();
    }
}