package com.androworld.allinonevideoeditor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class CustomEditText extends EditText {
    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    public CustomEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet);
    }

    private void a(Context context, AttributeSet attributeSet) {
        setTypeface(a(context, attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "textStyle", 0)));
    }

    private Typeface a(Context context, int i) {
        switch (i) {
            case 0:
                return Fontcache.getTypeface(Helper.FontStyle, context);
            case 1:
                return Fontcache.getTypeface(Helper.FontStyle, context);
            default:
                return Fontcache.getTypeface(Helper.FontStyle, context);
        }
    }
}
