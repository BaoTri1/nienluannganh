package com.example.shopproject.orther_handle;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
* class dùng để set khoảng các giữa các item trong RecycleView
* Ở đây, CharacterItemDecoration mất 50px như là bù đắp trong constructor của nó
* và nó ghi đè lên getItemOffsets (...). Bên trong getItemOffsets () mỗi trường outRectchỉ rõ số lượng các điểm ảnh
* mà chế độ xem từng mục phải được lồng vào nhau.
* Vì tôi đã sử dụng GridLayoutManager và muốn đặt các khoảng cách bằng nhau giữa các item lưới,
* tôi đặt đúng chiều rộng là 25px (tức là offset / 2) cho mỗi mục tại vị trí cân bằng
* và trái 25px cho mỗi mục ở vị trí lẻ trong khi vẫn giữ nguyên trên cùng.*/

public class CharacterItemDecoration extends RecyclerView.ItemDecoration {
    private int offset;

    public CharacterItemDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();

        if(layoutParams.getSpanIndex() % 2 == 0){
            outRect.top = offset;
            outRect.left = offset;
            outRect.right = offset / 2;
        }
        else {
            outRect.top = offset;
            outRect.right = offset;
            outRect.left = offset / 2;
        }

    /*    int position = parent.getChildLayoutPosition(view);

        GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();

        if (position < manager.getSpanCount())
            outRect.top = offset;

        if (position % 2 != 0) {
            outRect.right = offset;
        }

        outRect.left = offset;
        outRect.bottom = offset;*/
    }
}
