package com.example.shopproject.view.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopproject.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_layout);

        ImageView img = findViewById(R.id.imgLogoApp);
        TextView txtVersion = findViewById(R.id.txtVersion);
        Toolbar toolbar = findViewById(R.id.ToolBar_intro);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Giới thiệu");

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = info.versionName;
            int versionCode = info.versionCode;
            txtVersion.setText(getResources().getString(R.string.app_name) + " v " + versionName);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}