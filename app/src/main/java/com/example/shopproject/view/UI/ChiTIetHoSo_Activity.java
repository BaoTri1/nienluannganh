package com.example.shopproject.view.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopproject.R;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChiTIetHoSo_Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppCompatButton btnChinhSua;
    private AppCompatButton btnChinhSuaAnh;
    private CircleImageView imgUser;
    private EditText editTen;
    private EditText editTenDangNhap;
    private EditText editSDT;
    private TextView txtGioiTinh;
    private TextView txtNgaySinh;
    private EditText editEmail;
    private AppCompatButton btnThayDoiMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ho_so);

        initView();

        toolbar.setTitle("Chi tiết hồ sơ");

        //Nhan du lieu ve nguoi dung

        //Su kien nut Sua: dùng chỉnh sửa thông tin
        btnChinhSua.setOnClickListener(view -> {
            String Textbtn = btnChinhSua.getText().toString().trim();
            if(Textbtn.equals(getResources().getString(R.string.btnSua))){
                Edit(true);
                    btnChinhSua.setText(getResources().getString(R.string.btnXong));
            }
            else if(Textbtn.equals(getResources().getString(R.string.btnXong))){
                Edit(false);
                btnChinhSua.setText(getResources().getString(R.string.btnSua));
                //Luu lại thong tin lên server
                Toast.makeText(ChiTIetHoSo_Activity.this, "Đã lưu thông tin thành công.", Toast.LENGTH_SHORT).show();
            }
        });

        //Tạo sự kiện chọn giới tính
        txtGioiTinh.setOnClickListener(view -> {
            createSelectOptionDialog();
        });

        //Tạo sự kiện chọn ngày sinh
        txtNgaySinh.setOnClickListener(view -> {
            createSelectOptionDate();
        });

        //Sự kiện Thay đổi mật khẩu
        btnThayDoiMatKhau.setOnClickListener(view -> {
            Toast.makeText(ChiTIetHoSo_Activity.this, "Mở Thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
        });

    }

    private void initView(){
        toolbar = findViewById(R.id.Toolbar_cths);
        btnChinhSua = findViewById(R.id.btnChinhSua);
        btnChinhSuaAnh = findViewById(R.id.btnChinhSuaAnh);
        imgUser = findViewById(R.id.imgUser_cths);
        editTen = findViewById(R.id.editNameUser);
        editTenDangNhap = findViewById(R.id.editTenDangNhap);
        editSDT = findViewById(R.id.editSDT);
        txtGioiTinh = findViewById(R.id.txtGioiTinh);
        txtNgaySinh = findViewById(R.id.txtNgaySinh);
        editEmail = findViewById(R.id.editEmail);
        btnThayDoiMatKhau = findViewById(R.id.btnThayDoiMatKhau);
    }

    private void Edit(boolean state){
        editTen.setEnabled(state);
        editSDT.setEnabled(state);
        txtGioiTinh.setEnabled(state);
        txtNgaySinh.setEnabled(state);
        editEmail.setEnabled(state);

        editTen.requestFocus();
    }

    private void createSelectOptionDialog(){
        String[] arrayOption = getResources().getStringArray(R.array.optionGioiTinh);
        AlertDialog.Builder dialog = new AlertDialog.Builder(ChiTIetHoSo_Activity.this);
        dialog.setTitle("Giới tính");
        dialog.setItems(arrayOption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                txtGioiTinh.setText(arrayOption[i]);
            }
        });

        dialog.create().show();
    }

    private void createSelectOptionDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date = i2 + " - " + (i1+1) + " - " + i;
                txtNgaySinh.setText(date);
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(ChiTIetHoSo_Activity.this,
                                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar, listener, year, month, day);
        dialog.setTitle("Chọn ngày sinh");
        dialog.show();
    }

    private String maskedString(String str, int startIndex, int endIndex, char maskedChar){
        if(str == null || str.isEmpty())
            return "";
        if(startIndex < 0 || endIndex >= str.length())
            return str;
        if(endIndex < startIndex || endIndex >= str.length())
            return str;
        StringBuilder maskedStr = new StringBuilder(str);
        for (int i = startIndex; i <= endIndex; i++){
            maskedStr.setCharAt(i, maskedChar);
        }

        return maskedStr.toString();
    }
}