package com.example.shopproject.view.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopproject.R;
import com.example.shopproject.mode.User;
import com.example.shopproject.presenter.ReviewPresenter;
import com.example.shopproject.view.ReviewView;

public class ReviewActivity extends AppCompatActivity implements ReviewView {

    private Toolbar toolbar;
    private CheckBox cboxRate1;
    private CheckBox cboxRate2;
    private CheckBox cboxRate3;
    private CheckBox cboxRate4;
    private CheckBox cboxRate5;
    private EditText edComment;
    private AppCompatButton btnSaveReview;

    private int Rating;

    private ReviewPresenter reviewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_layout);

        initView();

        reviewPresenter = new ReviewPresenter(this, this);
        reviewPresenter.getUser();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            reviewPresenter.ReceiveData(bundle.getString("ID_KEY"));
        }

        Rating = 0;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Đánh giá sản phẩm");

        cboxRate5.setOnClickListener(v -> {
            cboxRate5.setChecked(true);
            cboxRate4.setChecked(true);
            cboxRate3.setChecked(true);
            cboxRate2.setChecked(true);
            cboxRate1.setChecked(true);
            Rating = 5;
        });

        cboxRate4.setOnClickListener(v -> {
            cboxRate5.setChecked(false);
            cboxRate4.setChecked(true);
            cboxRate3.setChecked(true);
            cboxRate2.setChecked(true);
            cboxRate1.setChecked(true);
            Rating = 4;
        });

        cboxRate3.setOnClickListener(v -> {
            cboxRate5.setChecked(false);
            cboxRate4.setChecked(false);
            cboxRate3.setChecked(true);
            cboxRate2.setChecked(true);
            cboxRate1.setChecked(true);
            Rating = 3;
        });

        cboxRate2.setOnClickListener(v -> {
            cboxRate5.setChecked(false);
            cboxRate4.setChecked(false);
            cboxRate3.setChecked(false);
            cboxRate2.setChecked(true);
            cboxRate1.setChecked(true);
            Rating = 2;
        });

        cboxRate2.setOnClickListener(v -> {
            cboxRate5.setChecked(false);
            cboxRate4.setChecked(false);
            cboxRate3.setChecked(false);
            cboxRate2.setChecked(false);
            cboxRate1.setChecked(true);
            Rating = 1;
        });

        btnSaveReview.setOnClickListener(v -> {
            reviewPresenter.SaveReview(edComment.getText().toString().trim(), Rating);
        });
    }

    private void initView(){
        toolbar = findViewById(R.id.ToolBar_review);
        cboxRate1 = findViewById(R.id.btn_rate_1);
        cboxRate2 = findViewById(R.id.btn_rate_2);
        cboxRate3 = findViewById(R.id.btn_rate_3);
        cboxRate4 = findViewById(R.id.btn_rate_4);
        cboxRate5 = findViewById(R.id.btn_rate_5);
        edComment = findViewById(R.id.editCommment);
        btnSaveReview = findViewById(R.id.btnSaveReview);
    }

    @Override
    public void SaveReviewSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void DisplayError(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage(error);

        AlertDialog dialog = builder.create();
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1500);
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
}