package com.example.shopproject.presenter;

import android.content.Context;

import com.example.shopproject.mode.SearchHistory;
import com.example.shopproject.sqlite.ShopProjectDatabase;
import com.example.shopproject.view.HistorySearchView;

import java.util.List;

public class SearchPresenter {

    private Context mContext;
    private HistorySearchView historySearchView;

    public SearchPresenter(Context mContext, HistorySearchView historySearchView) {
        this.mContext = mContext;
        this.historySearchView = historySearchView;
    }

    public boolean isKeyWork(String keywork){
        List<SearchHistory> list = ShopProjectDatabase.getInstance(mContext).searchHistoryDAO().checkSearchHistory(keywork);
        return list != null && !list.isEmpty();
    }

    public void saveHistorySearch(SearchHistory searchHistory){
        ShopProjectDatabase.getInstance(mContext).searchHistoryDAO().insertSearchHistory(searchHistory);
    }

    public void getListSearchHistory(){
        List<SearchHistory> list = ShopProjectDatabase.getInstance(mContext).searchHistoryDAO().getListSearchHistory();
        historySearchView.DisplayListHistorySearch(list);
    }

    public void deleteSearchHistory(){
        ShopProjectDatabase.getInstance(mContext).searchHistoryDAO().deleteAllSearchHistory();
        List<SearchHistory> list = ShopProjectDatabase.getInstance(mContext).searchHistoryDAO().getListSearchHistory();
        historySearchView.DisplayRefresh(list);
    }
}
