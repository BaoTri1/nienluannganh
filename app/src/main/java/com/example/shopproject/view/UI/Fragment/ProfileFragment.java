package com.example.shopproject.view.UI.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.shopproject.R;
import com.example.shopproject.view.UI.ChiTIetHoSo_Activity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View myView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private CircleImageView imgUser;
    private TextView txtNameUser;
    private TextView txtEmail;
    private AppCompatButton btnMoThongTin;
    private AppCompatButton btnMoDiaChi;
    private AppCompatButton btnMoLSMH;
    private AppCompatButton btnMoCaiDatThongBao;
    private AppCompatButton btnMoGioiThieu;
    private AppCompatButton btnDangXuat;
    private TextView txtVersion;

    /*Thong bao cho tai khoản admin khong co quyen han*/


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profile_fragment_layout, container, false);

        initView();

        setupToolBar();

        //set version
        setupVersion();

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_gia));

        //Lấy dữ liệu của người dùng
        getUsertoServer();

        btnMoThongTin.setOnClickListener(view -> {
            Toast.makeText(getActivity(), "Mo activity thong tin user", Toast.LENGTH_SHORT).show();
            Intent intentChiTietHoSo = new Intent(getActivity(), ChiTIetHoSo_Activity.class);
            startActivity(intentChiTietHoSo);
        });


        return myView;
    }

    private void initView(){
        toolbar = myView.findViewById(R.id.Toolbar_hoso);
        swipeRefreshLayout = myView.findViewById(R.id.SwipeRefreshLayout_hoso);
        imgUser = myView.findViewById(R.id.img_user_hoso);
        txtNameUser = myView.findViewById(R.id.txtNameUser_hoso);
        txtEmail = myView.findViewById(R.id.txtEmailUser_hoso);
        btnMoThongTin = myView.findViewById(R.id.btnThongTinUser_hoso);
        btnMoDiaChi  = myView.findViewById(R.id.btnDiaChi_hoso);
        btnMoLSMH = myView.findViewById(R.id.btnLichSuMuaHang_hoso);
        btnMoCaiDatThongBao = myView.findViewById(R.id.btnThongBao_hoso);
        btnMoGioiThieu = myView.findViewById(R.id.btnGioiThieu_hoso);
        btnDangXuat = myView.findViewById(R.id.btnLogout_in);
        txtVersion = myView.findViewById(R.id.lbl_phienban_hoso);
    }

    private void setupToolBar(){
        if(toolbar != null){
            toolbar.setTitle("Hồ sơ");
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

    @SuppressLint("SetTextI18n")
    private void setupVersion(){
        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            String versionName = info.versionName;
            int versionCode = info.versionCode;
            txtVersion.setText(getResources().getString(R.string.app_name) + " v " + versionName);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }

    }

    private void getUsertoServer() {
        //Lay du lieu tren server

        //setImageUser

        //setEmailUser

    }

    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(), "Refresh Data", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
}
