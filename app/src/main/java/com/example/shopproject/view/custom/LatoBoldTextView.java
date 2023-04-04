package com.example.shopproject.view.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class LatoBoldTextView extends AppCompatTextView {

    public LatoBoldTextView(@NonNull Context context) {
        super(context);
        setFontsTextView();
    }

    public LatoBoldTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFontsTextView();
    }

    public LatoBoldTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontsTextView();
    }

    private void setFontsTextView(){
        Typeface typeface = Untils.getLatoBoldTypeface(getContext());
        setTypeface(typeface);
    }
}
