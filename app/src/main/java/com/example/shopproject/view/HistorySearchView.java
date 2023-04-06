package com.example.shopproject.view;

import com.example.shopproject.sqlite.Entity.SearchHistory;

import java.util.List;

public interface HistorySearchView {
    void DisplayListHistorySearch(List<SearchHistory> list);
    void DisplayRefresh(List<SearchHistory> list);
}
