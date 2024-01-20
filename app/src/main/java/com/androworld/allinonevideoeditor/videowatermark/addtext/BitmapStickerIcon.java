package com.androworld.allinonevideoeditor.videowatermark.addtext;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class BitmapStickerIcon extends DrawableSticker {
    public static final float DEFAULT_ICON_EXTRA_RADIUS = 10.0f;
    public static final float DEFAULT_ICON_RADIUS = 10.0f;
    private float a = 10.0f;
    private float b = 10.0f;
    private float c;
    private float d;

    public BitmapStickerIcon(Drawable drawable) {
        super(drawable);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawCircle(this.c, this.d, this.b, paint);
        super.draw(canvas);
    }

    public float getX() {
        return this.c;
    }

    public void setX(float f) {
        this.c = f;
    }

    public float getY() {
        return this.d;
    }

    public void setY(float f) {
        this.d = f;
    }

    public float getIconRadius() {
        return this.b;
    }

    public void setIconRadius(float f) {
        this.b = f;
    }

    public float getIconExtraRadius() {
        return this.a;
    }

    public void setIconExtraRadius(float f) {
        this.a = f;
    }
}
