package com.example.shopproject.view.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shopproject.R;
import com.example.shopproject.mode.Province;
import com.example.shopproject.mode.ShippingAddress;
import com.example.shopproject.presenter.editAddressPresenter;
import com.example.shopproject.view.EditAddressView;
import com.example.shopproject.view.adapter.DistrictSpinnerAdapter;
import com.example.shopproject.view.adapter.ProvinceSpinnerAdapter;
import com.example.shopproject.view.adapter.WardSpinnerAdapter;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class EditAddressActivity extends AppCompatActivity implements EditAddressView {

    private static final String SHIPPING_ADDRESS_KEY = "shipping_address_key";

    private Toolbar toolbar;
    private TextInputLayout tip_Name;
    private EditText editName;
    private TextInputLayout tip_SDT;
    private EditText editSDT;
    private TextInputLayout tip_Address;
    private EditText editAddress;
    private Spinner spinnerProvince;
    private Spinner spinnerDistrict;
    private Spinner spinnerWards;
    private AppCompatButton btnConfrim;
    private ProvinceSpinnerAdapter provinceSpinnerAdapter;
    private DistrictSpinnerAdapter districtSpinnerAdapter;
    private WardSpinnerAdapter wardSpinnerAdapter;
    private String province;
    private String district;
    private String ward;

    private editAddressPresenter addressPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address_layout);

        initView();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Nhập địa chỉ nhận hàng");
        actionBar.setDisplayHomeAsUpEnabled(true);

        addressPresenter = new editAddressPresenter(this, this);

        addressPresenter.getAddress();

        btnConfrim.setOnClickListener(view -> {
            String name = editName.getText().toString().trim();
            String sdt = editSDT.getText().toString().trim();
            String numApartment_streetName = editAddress.getText().toString().trim();
            String strArea = ", " + ward + ", " + district + ", " + province;
            addressPresenter.createShippingAddress(name, numApartment_streetName, province, district, ward, sdt, strArea);
        });

    }

    private void initView(){
        toolbar = findViewById(R.id.ToolBar_edAddress);
        tip_Name = findViewById(R.id.tip_Name);
        editName = findViewById(R.id.editName);
        tip_SDT = findViewById(R.id.tip_SDT);
        editSDT = findViewById(R.id.editSDT);
        tip_Address = findViewById(R.id.tip_address);
        editAddress = findViewById(R.id.editAddress);
        spinnerProvince = findViewById(R.id.spinnerProvince);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        spinnerWards = findViewById(R.id.spinnerWards);
        btnConfrim = findViewById(R.id.btnConfrim);

    }

    @Override
    public void DisplayProvinceSuccess(List<Province> mList) {
        provinceSpinnerAdapter = new ProvinceSpinnerAdapter(this, R.layout.item_selected, mList);
        spinnerProvince.setAdapter(provinceSpinnerAdapter);
        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                province = provinceSpinnerAdapter.getItem(position).getName();
                districtSpinnerAdapter = new DistrictSpinnerAdapter(EditAddressActivity.this, R.layout.item_selected, provinceSpinnerAdapter.getItem(position).getDistricts());
                spinnerDistrict.setAdapter(districtSpinnerAdapter);
                spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        district = districtSpinnerAdapter.getItem(position).getName();
                        wardSpinnerAdapter = new WardSpinnerAdapter(EditAddressActivity.this, R.layout.item_selected, districtSpinnerAdapter.getItem(position).getWards());
                        spinnerWards.setAdapter(wardSpinnerAdapter);
                        spinnerWards.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                ward = wardSpinnerAdapter.getItem(position).getName();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void DisplayErrorName(String error) {
        tip_Name.setError(error);
    }

    @Override
    public void DisplayErrorSDT(String error) {
        tip_SDT.setError(error);
    }

    @Override
    public void DisplayErrorAddress(String error) {
        tip_Address.setError(error);
    }

    @Override
    public void PassshippingAddress(ShippingAddress shippingAddress) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận thông tin địa chỉ");
        builder.setIcon(R.drawable.ic_baseline_location_on_24);
        builder.setPositiveButton("Xác thực", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EditAddressActivity.this, PayMentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(SHIPPING_ADDRESS_KEY, shippingAddress);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setMessage(shippingAddress.getFullName() + " | " + shippingAddress.getSDT() + "\n" + shippingAddress.getAddress());
        builder.create().show();
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