package com.example.shopproject.view.UI.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.shopproject.mode.User;
import com.example.shopproject.orther_handle.AccountManagement;
import com.example.shopproject.presenter.ProfilePresenter;
import com.example.shopproject.view.ProfileView;
import com.example.shopproject.view.UI.DetailProfile_Activity;
import com.example.shopproject.view.UI.IntroActivity;
import com.example.shopproject.view.UI.ListAddressActicity;
import com.example.shopproject.view.UI.ListDiscountActivity;
import com.example.shopproject.view.UI.ListOrdersHistory;
import com.example.shopproject.view.UI.LoginActivity;
import com.example.shopproject.view.UI.MainActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ProfileView {

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
    private AppCompatButton btnLogin_out;
    private TextView txtVersion;
    private ProfilePresenter profilePresenter;


    /*Thong bao cho tai khoản admin khong co quyen han*/


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profile_fragment_layout, container, false);

        initView();

        setupToolBar();

        //set version
        setupVersion();

        profilePresenter = new ProfilePresenter(getActivity(), this);
        profilePresenter.getUser();

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_gia));

//        btnMoThongTin.setOnClickListener(view -> {
//            Toast.makeText(getActivity(), "Mo activity thong tin user", Toast.LENGTH_SHORT).show();
//            Intent intentChiTietHoSo = new Intent(getActivity(), DetailProfile_Activity.class);
//            startActivity(intentChiTietHoSo);
//        });

        btnLogin_out.setOnClickListener(view ->{
            String typebutton = btnLogin_out.getText().toString().trim();
            if(typebutton.equals("Đăng xuất")){
                AccountManagement.deleteAccount(getActivity());
            }
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        if(AccountManagement.getAccount(getActivity()) != null){
            btnLogin_out.setText("Đăng xuất");
        }

        btnMoDiaChi.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ListAddressActicity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ACTION_KEY", "READ");
            intent.putExtras(bundle);
            startActivity(intent);
        });

        btnMoLSMH.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ListOrdersHistory.class);
            startActivity(intent);
        });

        btnMoGioiThieu.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), IntroActivity.class);
            startActivity(intent);
        });

        btnMoCaiDatThongBao.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ListDiscountActivity.class);
            startActivity(intent);
        });

        return myView;
    }

    private void initView(){
        toolbar = myView.findViewById(R.id.Toolbar_hoso);
        swipeRefreshLayout = myView.findViewById(R.id.SwipeRefreshLayout_hoso);
        imgUser = myView.findViewById(R.id.img_user_hoso);
        txtNameUser = myView.findViewById(R.id.txtNameUser_hoso);
        txtEmail = myView.findViewById(R.id.txtEmailUser_hoso);
//        btnMoThongTin = myView.findViewById(R.id.btnThongTinUser_hoso);
        btnMoDiaChi  = myView.findViewById(R.id.btnDiaChi_hoso);
        btnMoLSMH = myView.findViewById(R.id.btnLichSuMuaHang_hoso);
        btnMoCaiDatThongBao = myView.findViewById(R.id.btnThongBao_hoso);
        btnMoGioiThieu = myView.findViewById(R.id.btnGioiThieu_hoso);
        btnLogin_out = myView.findViewById(R.id.btnLogout_in);
        txtVersion = myView.findViewById(R.id.lbl_phienban_hoso);
    }

    private void setupToolBar(){
        if(toolbar != null){
            toolbar.setTitle("Hồ sơ");
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

    @Override
    public void onRefresh() {
        profilePresenter.getUser();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void DisplayUserSuccess(User user) {
        txtNameUser.setText(user.getName());
        txtEmail.setText(user.getEmail());
    }

    @Override
    public void DisplayError(String error) {
        AlertDialog.Builder dialogNetWork = new AlertDialog.Builder(getActivity());
        dialogNetWork.setTitle("Thông báo");
        dialogNetWork.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        });
        dialogNetWork.setMessage(error);
        dialogNetWork.create().show();
    }
}
