package com.androworld.allinonevideoeditor.videowatermark.addtext;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class DrawableSticker extends Sticker {
    private Drawable a;
    private Rect b;

    public DrawableSticker(Drawable drawable) {
        this.a = drawable;
        this.matrix = new Matrix();
        this.b = new Rect(0, 0, getWidth(), getHeight());
    }

    public Drawable getDrawable() {
        return this.a;
    }

    public void setDrawable(Drawable drawable) {
        this.a = drawable;
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.concat(this.matrix);
        this.a.setBounds(this.b);
        this.a.draw(canvas);
        canvas.restore();
    }

    public int getWidth() {
        return this.a.getIntrinsicWidth();
    }

    public int getHeight() {
        return this.a.getIntrinsicHeight();
    }

    public void release() {
        super.release();
        if (this.a != null) {
            this.a = null;
        }
    }
}
