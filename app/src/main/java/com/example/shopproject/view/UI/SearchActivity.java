package com.example.shopproject.view.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopproject.R;
import com.example.shopproject.sqlite.Entity.SearchHistory;
import com.example.shopproject.presenter.SearchPresenter;
import com.example.shopproject.view.HistorySearchView;
import com.example.shopproject.view.adapter.HistorySearchAdapter;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements HistorySearchView {

    private Toolbar toolbar;
    private AutoCompleteTextView editSearch;
    private Button btnSearch;
    private HistorySearchAdapter adapter;
    private RecyclerView rcv_historySearch;
    private TextView txtAction;
    private SearchPresenter searchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout);

        init_view();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        searchPresenter = new SearchPresenter(SearchActivity.this, this);
        searchPresenter.getListSearchHistory();

        btnSearch.setOnClickListener(view -> {
            hideSoftKeyboard();
            HandlerSearch();
            Toast.makeText(this, "Đang tìm kiếm", Toast.LENGTH_SHORT).show();
        });

        txtAction.setOnClickListener(view -> {
            searchPresenter.deleteSearchHistory();
            txtAction.setVisibility(View.GONE);
        });

        editSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            hideSoftKeyboard();
            HandlerSearch();
            Toast.makeText(this, "Đang tìm kiếm", Toast.LENGTH_SHORT).show();
            return true;
        });

    }

    private void init_view(){
        toolbar = findViewById(R.id.toolbar_search);
        btnSearch = findViewById(R.id.btnSearch);
        editSearch = findViewById(R.id.autocompletetxt);
        rcv_historySearch = findViewById(R.id.rcv_HistorySearch);
        txtAction = findViewById(R.id.txtMoRong_XoaLS);
    }

    private void HandlerSearch() {
        //Lưu lịch lstk
        String keywork = editSearch.getText().toString().trim();
        if(TextUtils.isEmpty(keywork))
            return;

        if(searchPresenter.isKeyWork(keywork))
            return;

        searchPresenter.saveHistorySearch(new SearchHistory(keywork));

        //Mở Activity Theo keywork
        Toast.makeText(this, "Mở Activity " + keywork, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("TYPE_KEY", "Query");
        bundle.putString("KEYWORK_KEY", keywork);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    //Hàm ẩn bạn phím khi nhập xong
    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void DisplayListHistorySearch(List<SearchHistory> list) {
        if(list == null || list.isEmpty()){
            txtAction.setVisibility(View.GONE);
            return;
        }

        adapter = new HistorySearchAdapter(list, this, new clickListener() {
            @Override
            public void onClickHistorySearch(String search) {
                Toast.makeText(SearchActivity.this, search, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchActivity.this, ProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TYPE_KEY", "Query");
                bundle.putString("KEYWORK_KEY", search);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_historySearch.setLayoutManager(manager);
        rcv_historySearch.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        adapter.setData(list);
        rcv_historySearch.setAdapter(adapter);
    }

    @Override
    public void DisplayRefresh(List<SearchHistory> list) {
        adapter.setData(list);
    }

}