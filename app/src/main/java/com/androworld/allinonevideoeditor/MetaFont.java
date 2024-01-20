package com.androworld.allinonevideoeditor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class MetaFont extends AppCompatTextView {
    private static final int[] a = {16842901, 16843087, 16842904};

    public MetaFont(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(attributeSet);
    }

    public MetaFont(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(attributeSet);
    }

    public MetaFont(Context context) {
        super(context);
        a(null);
    }

    private void a(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, a);
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "AVENIRLTSTD-MEDIUM.OTF"));
            obtainStyledAttributes.recycle();
        }
    }
}
