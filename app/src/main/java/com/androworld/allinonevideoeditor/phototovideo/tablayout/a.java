package com.androworld.allinonevideoeditor.phototovideo.tablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androworld.allinonevideoeditor.phototovideo.tablayout.SlidingTabLayout.TabColorizer;

class a extends LinearLayout {
    private final Paint a;
    private final int b;
    private TabColorizer c;
    private final int d;
    private final C0033a e;
    private final float f;
    private final Paint g;
    private final Paint h;
    private final int i;
    private int j;
    private float k;

    private static class C0033a implements TabColorizer {
        private int[] a;
        private int[] b;

        private C0033a() {
        }

        public final int getIndicatorColor(int i) {
            return this.b[i % this.b.length];
        }

        public final int getDividerColor(int i) {
            return this.a[i % this.a.length];
        }


        public void a(int... iArr) {
            this.b = iArr;
        }


        public void b(int... iArr) {
            this.a = iArr;
        }
    }

    a(Context context) {
        this(context, null);
    }

    a(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setWillNotDraw(false);
        float f2 = getResources().getDisplayMetrics().density;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16842800, typedValue, true);
        this.d = as(typedValue.data, (byte) 38);
        this.e = new C0033a();
        this.e.a(-13388315);
        this.e.b(32);
        int i2 = (int) (2.0f * f2);
        this.b = i2;
        this.a = new Paint();
        this.a.setColor(this.d);
        this.i = i2;
        this.h = new Paint();
        this.f = 0.0f;
        this.g = new Paint();
        this.g.setStrokeWidth((float) ((int) (0.0f * f2)));
    }


    public void a(TabColorizer tabColorizer) {
        this.c = tabColorizer;
        invalidate();
    }


    public void a(int i2, float f2) {
        this.j = i2;
        this.k = f2;
        invalidate();
    }

    public void a(int i2) {
        int i3 = 0;
        while (i3 < getChildCount()) {
            if (TextView.class.isInstance(getChildAt(i3))) {
                ((TextView) getChildAt(i3)).setTextColor(Color.parseColor(i2 == i3 ? "#FFFFFF" : "#88FFFFFF"));
            }
            i3++;
        }
    }


    public void onDraw(Canvas canvas) {
        TabColorizer tabColorizer = this.c;
        int height = getHeight();
        int childCount = getChildCount();
        float f2 = (float) height;
        int min = (int) (Math.min(Math.max(0.0f, this.f), 1.0f) * f2);
        if (this.c != null) {
            tabColorizer = this.c;
        } else {
            C0033a aVar = this.e;
        }
        if (childCount > 0) {
            View childAt = getChildAt(this.j);
            int left = childAt.getLeft();
            int right = childAt.getRight();
            int indicatorColor = tabColorizer.getIndicatorColor(this.j);
            if (this.k > 0.0f && this.j < getChildCount() - 1) {
                int indicatorColor2 = tabColorizer.getIndicatorColor(this.j + 1);
                if (indicatorColor != indicatorColor2) {
                    indicatorColor = a(indicatorColor2, indicatorColor, this.k);
                }
                View childAt2 = getChildAt(this.j + 1);
                left = (int) ((this.k * ((float) childAt2.getLeft())) + ((1.0f - this.k) * ((float) left)));
                right = (int) ((this.k * ((float) childAt2.getRight())) + ((1.0f - this.k) * ((float) right)));
            }
            this.h.setColor(indicatorColor);
            canvas.drawRect((float) left, (float) (height - this.i), (float) right, f2, this.h);
        }
        canvas.drawRect(0.0f, (float) (height - this.b), (float) getWidth(), f2, this.a);
        int i2 = (height - min) / 2;
        for (int i3 = 0; i3 < childCount - 1; i3++) {
            View childAt3 = getChildAt(i3);
            this.g.setColor(tabColorizer.getDividerColor(i3));
            canvas.drawLine((float) childAt3.getRight(), (float) i2, (float) childAt3.getRight(), (float) (i2 + min), this.g);
        }
    }

    private static int as(int i2, byte b2) {
        int parseColor = Color.parseColor("#f4f3f3");
        return Color.argb(0, Color.red(parseColor), Color.green(parseColor), Color.blue(parseColor));
    }

    private static int a(int i2, int i3, float f2) {
        float f3 = 1.0f - f2;
        return Color.rgb((int) ((((float) Color.red(i2)) * f2) + (((float) Color.red(i3)) * f3)), (int) ((((float) Color.green(i2)) * f2) + (((float) Color.green(i3)) * f3)), (int) ((((float) Color.blue(i2)) * f2) + (((float) Color.blue(i3)) * f3)));
    }
}
