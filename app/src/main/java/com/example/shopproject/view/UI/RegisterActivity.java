package com.example.shopproject.view.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.shopproject.R;
import com.example.shopproject.mode.User;
import com.example.shopproject.orther_handle.AccountManagement;
import com.example.shopproject.presenter.RegisterPresenter;
import com.example.shopproject.view.RegisterView;
import com.example.shopproject.view.custom.LatoBoldTextView;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private TextInputLayout LayoutInputName;
    private EditText editName;
    private TextInputLayout LayoutInputEmail;
    private EditText editEmail;
    private TextInputLayout LayoutInputPasswd;
    private EditText editPasswd;
    private TextInputLayout LayoutInputConfirmPasswd;
    private EditText editConfirmPasswd;
    private LatoBoldTextView txtMessage;
    private AppCompatButton btnSignup;
    private LatoBoldTextView btnSignin;
    private ImageButton btnGoogle;
    private ImageButton btnFacebook;

    private RegisterPresenter mRegisterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);

        initView();

        mRegisterPresenter = new RegisterPresenter(RegisterActivity.this, this);

        btnSignup.setOnClickListener(view -> {
            Register();
        });

        btnSignin.setOnClickListener(view -> {
            Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intentLogin);
            finish();
        });

        btnGoogle.setOnClickListener(view -> {

        });

        btnFacebook.setOnClickListener(view -> {

        });


    }

    private void initView(){
        LayoutInputName = findViewById(R.id.TextInputLayout_HoTen_signup);
        editName = findViewById(R.id.editHoTen_signup);
        LayoutInputEmail = findViewById(R.id.TextInputLayout_email_signup);
        editEmail = findViewById(R.id.editEmail_signup);
        LayoutInputPasswd = findViewById(R.id.TextInputLayout_password_signup);
        editPasswd = findViewById(R.id.editPassword_signup);
        LayoutInputConfirmPasswd = findViewById(R.id.TextInputLayout_confirm_password_signup);
        editConfirmPasswd = findViewById(R.id.editConfirmPassword_signup);
        txtMessage = findViewById(R.id.lblMemo_signup);
        btnSignup = findViewById(R.id.btnSignup);
        btnSignin = findViewById(R.id.btnDangNhap);
        btnGoogle = findViewById(R.id.btnGoogle_signup);
        btnFacebook = findViewById(R.id.btnFacebook_signup);
    }

    private void Register(){
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String passwd = editPasswd.getText().toString().trim();
        String confirmPasswd = editConfirmPasswd.getText().toString().trim();
        mRegisterPresenter.Register(name, email, passwd, confirmPasswd);
    }

    @Override
    public void DisplayRegisterSuccess(User user) {
        AccountManagement.deleteAccount(this);
        String email = editEmail.getText().toString().trim();
        String passwd = editPasswd.getText().toString().trim();
        AccountManagement.saveAccount(this, email, passwd, true);
        txtMessage.setVisibility(View.GONE);
        Intent intentMainActivity = new Intent(RegisterActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("User", user);
        intentMainActivity.putExtras(bundle);
        startActivity(intentMainActivity);
        finish();
    }

    @Override
    public void DisplayRegisterFailue(String message) {
        txtMessage.setVisibility(View.VISIBLE);
        txtMessage.setText(message);
    }

    @Override
    public void DisplayMistakeOfName(String erorr) {
        LayoutInputName.setError(erorr);
    }

    @Override
    public void DisplayMistakeOfEmail(String erorr) {
        LayoutInputEmail.setError(erorr);
    }

    @Override
    public void DisplayMistakeOfPasswd(String erorr) {
        LayoutInputPasswd.setError(erorr);
    }

    @Override
    public void DisplayMistakeOfConfirmPasswd(String erorr) {
        LayoutInputConfirmPasswd.setError(erorr);
    }
}