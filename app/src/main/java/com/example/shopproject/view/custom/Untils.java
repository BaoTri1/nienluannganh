package com.example.shopproject.view.custom;

import android.content.Context;
import android.graphics.Typeface;

public class Untils {

    private static Typeface LatoBlackTypeface;
    private static Typeface LatoBoldTypeface;

    public static Typeface getLatoBlackTypeface(Context context) {
        if(LatoBlackTypeface == null){
            LatoBlackTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Black.ttf");
        }
        return LatoBlackTypeface;
    }

    public static Typeface getLatoBoldTypeface(Context context) {
        if(LatoBoldTypeface == null){
            LatoBoldTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf");
        }
        return LatoBoldTypeface;
    }

}
