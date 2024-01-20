package com.androworld.allinonevideoeditor.videowatermark.addtext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;

import androidx.core.content.ContextCompat;

import com.androworld.allinonevideoeditor.R;

public class TextSticker extends Sticker {
    private Alignment a;
    private Context b;
    private Drawable c;
    private float d;
    private float e;
    private float f;
    private float g;
    private Rect h;
    private StaticLayout i;
    private String j;
    private TextPaint k;
    private Rect l;

    public TextSticker(Context context) {
        this(context, null);
    }

    public TextSticker(Context context, Drawable drawable) {
        this.f = 1.0f;
        this.g = 0.0f;
        this.b = context;
        this.c = drawable;
        if (drawable == null) {
            this.c = ContextCompat.getDrawable(context, R.drawable.transparent_background);
        }
        this.matrix = new Matrix();
        this.k = new TextPaint(1);
        this.h = new Rect(0, 0, getWidth(), getHeight());
        this.l = new Rect(0, 0, getWidth(), getHeight());
        this.d = a(30.0f);
        this.e = a(50.0f);
        this.a = Alignment.ALIGN_CENTER;
        this.k.setTextSize(this.e);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.concat(this.matrix);
        if (this.c != null) {
            this.c.setBounds(this.h);
            this.c.draw(canvas);
        }
        canvas.restore();
        canvas.save();
        canvas.concat(this.matrix);
        if (this.l.width() == getWidth()) {
            canvas.translate(0.0f, (float) ((getHeight() / 2) - (this.i.getHeight() / 2)));
        } else {
            canvas.translate((float) this.l.left, (float) ((this.l.top + (this.l.height() / 2)) - (this.i.getHeight() / 2)));
        }
        this.i.draw(canvas);
        canvas.restore();
    }

    public int getWidth() {
        return this.c.getIntrinsicWidth();
    }

    public int getHeight() {
        return this.c.getIntrinsicHeight();
    }

    public void release() {
        super.release();
        if (this.c != null) {
            this.c = null;
        }
    }

    public Drawable getDrawable() {
        return this.c;
    }

    public void setDrawable(Drawable drawable) {
        this.c = drawable;
        this.h.set(0, 0, getWidth(), getHeight());
        this.l.set(0, 0, getWidth(), getHeight());
    }

    public Typeface getTypeface() {
        return this.k.getTypeface();
    }

    public void setTypeface(Typeface typeface) {
        this.k.setTypeface(typeface);
    }

    public int getTextColor() {
        return this.k.getColor();
    }

    public void setTextColor(int i2) {
        this.k.setShader(null);
        this.k.setColor(i2);
    }

    public int getAlpha() {
        return this.k.getAlpha();
    }

    public void setAlpha(int i2) {
        this.k.setAlpha(i2);
    }

    public void setTextShadowColor(int i2, int i3, int i4, int i5) {
        this.k.setShadowLayer((float) i2, (float) i3, (float) i4, i5);
    }

    public String getText() {
        return this.j;
    }

    public void setText(String str) {
        this.j = str;
    }

    public float getTextSize() {
        return this.k.getTextSize();
    }

    public void resizeText() {
        int height = this.l.height();
        int width = this.l.width();
        String text = getText();
        if (text != null && text.length() > 0 && height > 0 && width > 0 && this.e > 0.0f) {
            try {
                float f2 = this.e;
                int textHeightPixels = getTextHeightPixels(text, width, f2);
                float f3 = f2;
                while (textHeightPixels > height && f3 > this.d) {
                    try {
                        float max = Math.max(f3 - 2.0f, this.d);
                        try {
                            f3 = max;
                            textHeightPixels = getTextHeightPixels(text, width, max);
                        } catch (Exception e2) {
                            f3 = max;
                        }
                    } catch (Exception e3) {
                    }
                }
                if (f3 == this.d && textHeightPixels > height) {
                    try {
                        TextPaint textPaint = new TextPaint(this.k);
                        textPaint.setTextSize(f3);
                        StaticLayout staticLayout = new StaticLayout(text, textPaint, width, Alignment.ALIGN_NORMAL, this.f, this.g, false);
                        if (staticLayout.getLineCount() > 0) {
                            try {
                                int lineForVertical = staticLayout.getLineForVertical(height) - 1;
                                if (lineForVertical >= 0) {
                                    try {
                                        int lineStart = staticLayout.getLineStart(lineForVertical);
                                        int lineEnd = staticLayout.getLineEnd(lineForVertical);
                                        float lineWidth = staticLayout.getLineWidth(lineForVertical);
                                        float measureText = textPaint.measureText("…");
                                        while (((float) width) < lineWidth + measureText) {
                                            lineEnd--;
                                            try {
                                                lineWidth = textPaint.measureText(text.subSequence(lineStart, lineEnd + 1).toString());
                                            } catch (Exception e4) {
                                                e4.printStackTrace();
                                            }
                                        }
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(text.subSequence(0, lineEnd));
                                        sb.append("…");
                                        setText(sb.toString());
                                    } catch (Exception e5) {
                                        e5.printStackTrace();
                                    }
                                }
                            } catch (Exception e6) {
                                e6.printStackTrace();
                            }
                        }
                    } catch (Exception e7) {
                        e7.printStackTrace();
                    }
                }
                this.k.setTextSize(f3);
                StaticLayout staticLayout2 = new StaticLayout(this.j, this.k, this.l.width(), this.a, this.f, this.g, true);
                this.i = staticLayout2;
            } catch (Exception e8) {
                e8.printStackTrace();
            }
        }
    }

    public int getTextHeightPixels(CharSequence charSequence, int i2, float f2) {
        this.k.setTextSize(f2);
        StaticLayout staticLayout = new StaticLayout(charSequence, this.k, i2, Alignment.ALIGN_NORMAL, this.f, this.g, true);
        return staticLayout.getHeight();
    }

    private float a(float f2) {
        return this.b.getResources().getDisplayMetrics().scaledDensity * f2;
    }
}
