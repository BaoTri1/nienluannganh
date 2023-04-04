package com.example.shopproject.view.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class LatoBlackTextView extends AppCompatTextView {

    public LatoBlackTextView(@NonNull Context context) {
        super(context);
        setFontsTextView();
    }

    public LatoBlackTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFontsTextView();
    }

    public LatoBlackTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontsTextView();
    }

    private void setFontsTextView(){
        Typeface typeface = Untils.getLatoBlackTypeface(getContext());
        setTypeface(typeface);
    }
}
