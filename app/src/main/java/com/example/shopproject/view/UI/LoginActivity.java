package com.example.shopproject.view.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.shopproject.R;
import com.example.shopproject.mode.LoginRequest;
import com.example.shopproject.mode.User;
import com.example.shopproject.presenter.LoginPresenter;
import com.example.shopproject.view.LoginView;
import com.example.shopproject.view.custom.LatoBoldTextView;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private TextInputLayout layoutInputEmail;
    private EditText editEmail;
    private TextInputLayout layoutInputPasswd;
    private EditText editPasswd;
    private LatoBoldTextView txtMessage;
    private AppCompatButton btnLogin;
    private LatoBoldTextView btnForgetPasswd;
    private LatoBoldTextView btnRegister;
    private ImageButton btnGoogle;
    private ImageButton btnFacebook;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);

        initView();

        loginPresenter = new LoginPresenter(this, LoginActivity.this);

        btnLogin.setOnClickListener(view -> {
            String email = editEmail.getText().toString().trim();
            String passwd = editPasswd.getText().toString().trim();
            loginPresenter.login(email, passwd);
        });

        btnRegister.setOnClickListener(view -> {
            Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intentRegister);
            finish();
        });

        btnForgetPasswd.setOnClickListener(view -> {
            Toast.makeText(LoginActivity.this, "Quên Mật Khẩu", Toast.LENGTH_SHORT).show();
        });

        btnGoogle.setOnClickListener(view -> {
            Toast.makeText(LoginActivity.this, "Đăng nhập với Google", Toast.LENGTH_SHORT).show();
        });

        btnFacebook.setOnClickListener(view ->{
            Toast.makeText(LoginActivity.this, "Đăng nhập với Facebook", Toast.LENGTH_SHORT).show();
        });
    }

    private void initView(){
        layoutInputEmail = findViewById(R.id.TextInputLayout_email);
        editEmail = findViewById(R.id.editEmail_login);
        layoutInputPasswd = findViewById(R.id.TextInputLayout_password);
        editPasswd = findViewById(R.id.editPassword_login);
        txtMessage = findViewById(R.id.lblMemo_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnDangKy);
        btnForgetPasswd = findViewById(R.id.btnQuenMK);
        btnGoogle = findViewById(R.id.btnGoogle);
        btnFacebook = findViewById(R.id.btnFacebook);
    }


    @Override
    public void DisplayLoginSuccess(User user) {
        txtMessage.setVisibility(View.GONE);
        Toast.makeText(LoginActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
        Intent intentMainActivity = new Intent(LoginActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("User", user);
        intentMainActivity.putExtras(bundle);
        startActivity(intentMainActivity);
    }

    @Override
    public void DisplayLoginFailue(String message) {
        txtMessage.setVisibility(View.VISIBLE);
        txtMessage.setText(message);
    }

    @Override
    public void DisplayMistakeOfEmail(String erorr) {
        layoutInputEmail.setError(erorr);
    }

    @Override
    public void DisplayMistakeOfPasswd(String erorr) {
        layoutInputPasswd.setError(erorr);
    }


}