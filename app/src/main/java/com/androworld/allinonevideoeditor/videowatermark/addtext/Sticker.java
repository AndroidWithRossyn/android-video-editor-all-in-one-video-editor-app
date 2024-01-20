package com.androworld.allinonevideoeditor.videowatermark.addtext;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

public abstract class Sticker {
    protected static final String TAG = "StickerData";
    private float[] as = new float[9];
    protected boolean isFlipped;
    protected Matrix matrix;

    public abstract void draw(Canvas canvas);

    public abstract Drawable getDrawable();

    public abstract int getHeight();

    public abstract int getWidth();

    public void release() {
    }

    public abstract void setDrawable(Drawable drawable);

    public boolean isFlipped() {
        return this.isFlipped;
    }

    public void setFlipped(boolean z) {
        this.isFlipped = z;
    }

    public Matrix getMatrix() {
        return this.matrix;
    }

    public void setMatrix(Matrix matrix2) {
        this.matrix.set(matrix2);
    }

    public float[] getBoundPoints() {
        if (this.isFlipped) {
            return new float[]{(float) getWidth(), 0.0f, 0.0f, 0.0f, (float) getWidth(), (float) getHeight(), 0.0f, (float) getHeight()};
        }
        return new float[]{0.0f, 0.0f, (float) getWidth(), 0.0f, 0.0f, (float) getHeight(), (float) getWidth(), (float) getHeight()};
    }

    public float[] getMappedBoundPoints() {
        float[] fArr = new float[8];
        this.matrix.mapPoints(fArr, getBoundPoints());
        return fArr;
    }

    public float[] getMappedPoints(float[] fArr) {
        float[] fArr2 = new float[fArr.length];
        this.matrix.mapPoints(fArr2, fArr);
        return fArr2;
    }

    public RectF getBound() {
        return new RectF(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
    }

    public RectF getMappedBound() {
        RectF rectF = new RectF();
        this.matrix.mapRect(rectF, getBound());
        return rectF;
    }

    public PointF getCenterPoint() {
        return new PointF((float) (getWidth() / 2), (float) (getHeight() / 2));
    }

    public PointF getMappedCenterPoint() {
        PointF centerPoint = getCenterPoint();
        float[] mappedPoints = getMappedPoints(new float[]{centerPoint.x, centerPoint.y});
        return new PointF(mappedPoints[0], mappedPoints[1]);
    }

    public float getCurrentAngle() {
        return a(this.matrix);
    }

    private float a(@NonNull Matrix matrix2) {
        return (float) (-(Math.atan2((double) a(matrix2, 1), (double) a(matrix2, 0)) * 57.29577951308232d));
    }

    private float a(@NonNull Matrix matrix2, @IntRange(from = 0, to = 9) int i) {
        matrix2.getValues(this.as);
        return this.as[i];
    }

    public boolean contains(float f, float f2) {
        Matrix matrix2 = new Matrix();
        matrix2.setRotate(-getCurrentAngle());
        float[] fArr = new float[8];
        float[] fArr2 = new float[2];
        matrix2.mapPoints(fArr, getMappedBoundPoints());
        matrix2.mapPoints(fArr2, new float[]{f, f2});
        return a.a(fArr).contains(fArr2[0], fArr2[1]);
    }
}
