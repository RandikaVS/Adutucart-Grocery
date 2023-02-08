package com.example.adutucart5.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

 

public class MyTextViewInterBold extends androidx.appcompat.widget.AppCompatTextView {

    public MyTextViewInterBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextViewInterBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextViewInterBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
          Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Inter-Bold.otf");
          setTypeface(tf);
        }
    }

}