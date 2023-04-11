package com.example.shopproject.view;

import java.util.List;

public interface BottomFilterView {
    void DisplayCategorySuccess(List<String> mList);
    void DisplayFilter(String category, String price, String rate);
    void DisplayError(String message);

}
