package com.androworld.allinonevideoeditor.videowatermark.addtext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.core.content.ContextCompat;
import androidx.core.view.MotionEventCompat;

import com.androworld.allinonevideoeditor.R;

import java.util.ArrayList;
import java.util.List;

public class StickerView extends FrameLayout {
    public static boolean isStickerTouch = false;
    Canvas as;
    private Paint b;
    private boolean c;
    private a d;
    private BitmapStickerIcon e;
    private Matrix f;
    private float g;
    private float h;
    public Sticker handlingSticker;
    private BitmapStickerIcon i;
    private long j;
    private boolean k;
    private PointF l;
    private int m;
    private Matrix n;
    private float o;
    private float p;
    private OnStickerOperationListener q;
    private Matrix r;
    private RectF s;
    public boolean stickerSelected;
    private List<Sticker> t;
    private int u;
    private BitmapStickerIcon v;

    public interface OnStickerOperationListener {
        void onStickerClicked(Sticker sticker);

        void onStickerDeleted(Sticker sticker);

        void onStickerDoubleTapped(Sticker sticker);

        void onStickerDragFinished(Sticker sticker);

        void onStickerFlipped(Sticker sticker);

        void onStickerZoomFinished(Sticker sticker);
    }

    private enum a {
        NONE,
        DRAG,
        ZOOM_WITH_TWO_FINGER,
        ZOOM_WITH_ICON,
        DELETE,
        FLIP_HORIZONTAL,
        CLICK
    }

    public StickerView(Context context) {
        this(context, null);
    }

    public StickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StickerView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.stickerSelected = false;
        this.o = 0.0f;
        this.p = 0.0f;
        this.d = a.NONE;
        this.t = new ArrayList();
        this.u = 3;
        this.j = 0;
        this.m = 200;
        this.b = new Paint();
        this.b.setAntiAlias(true);
        this.b.setColor(getResources().getColor(R.color.Sticker_Border_Color));
        this.b.setStrokeWidth(2.0f);
        this.r = new Matrix();
        this.f = new Matrix();
        this.n = new Matrix();
        this.s = new RectF();
        this.e = new BitmapStickerIcon(ContextCompat.getDrawable(getContext(), R.mipmap.cancel_icon));
        this.v = new BitmapStickerIcon(ContextCompat.getDrawable(getContext(), R.mipmap.move_icon));
        this.i = new BitmapStickerIcon(ContextCompat.getDrawable(getContext(), R.mipmap.flip_icon));
    }


    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        super.onLayout(z, i2, i3, i4, i5);
        if (z) {
            this.s.left = (float) i2;
            this.s.top = (float) i3;
            this.s.right = (float) i4;
            this.s.bottom = (float) i5;
        }
    }


    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        a(canvas);
    }

    private void a(Canvas canvas) {
        Canvas canvas2 = canvas;
        for (int i2 = 0; i2 < this.t.size(); i2++) {
            try {
                Sticker sticker = (Sticker) this.t.get(i2);
                if (sticker != null) {
                    try {
                        sticker.draw(canvas2);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (this.handlingSticker != null && !this.k) {
            try {
                float[] stickerPoints = getStickerPoints(this.handlingSticker);
                float f2 = stickerPoints[0];
                float f3 = stickerPoints[1];
                float f4 = stickerPoints[2];
                float f5 = stickerPoints[3];
                float f6 = stickerPoints[4];
                float f7 = stickerPoints[5];
                float f8 = stickerPoints[6];
                float f9 = stickerPoints[7];
                float f10 = f9;
                canvas2.drawLine(f2, f3, f4, f5, this.b);
                canvas2.drawLine(f2, f3, f6, f7, this.b);
                canvas2.drawLine(f4, f5, f8, f10, this.b);
                canvas2.drawLine(f8, f10, f6, f7, this.b);
                float f11 = f10;
                float a2 = a(f6, f7, f8, f11);
                a(this.e, f4, f5, a2);
                this.e.draw(canvas2, this.b);
                a(this.i, f6, f7, a2);
                this.i.draw(canvas2, this.b);
                a(this.v, f8, f11, a2);
                this.v.draw(canvas2, this.b);
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
    }

    private void a(BitmapStickerIcon bitmapStickerIcon, float f2, float f3, float f4) {
        bitmapStickerIcon.setX(f2);
        bitmapStickerIcon.setY(f3);
        bitmapStickerIcon.getMatrix().reset();
        bitmapStickerIcon.getMatrix().postRotate(f4, (float) (bitmapStickerIcon.getWidth() / 2), (float) (bitmapStickerIcon.getHeight() / 2));
        bitmapStickerIcon.getMatrix().postTranslate(f2 - ((float) (bitmapStickerIcon.getWidth() / 2)), f3 - ((float) (bitmapStickerIcon.getHeight() / 2)));
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.k) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        if (motionEvent.getAction() != 0) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        this.g = motionEvent.getX();
        this.h = motionEvent.getY();
        return a(this.e) || a(this.v) || a(this.i) || b() != null;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.k) {
            return super.onTouchEvent(motionEvent);
        }
        switch (MotionEventCompat.getActionMasked(motionEvent)) {
            case 0:
                this.d = a.DRAG;
                this.g = motionEvent.getX();
                this.h = motionEvent.getY();
                if (a(this.e)) {
                    this.d = a.DELETE;
                } else if (a(this.i)) {
                    this.d = a.FLIP_HORIZONTAL;
                } else if (!a(this.v) || this.handlingSticker == null) {
                    this.handlingSticker = b();
                } else {
                    this.d = a.ZOOM_WITH_ICON;
                    this.l = c();
                    this.o = b(this.l.x, this.l.y, this.g, this.h);
                    this.p = a(this.l.x, this.l.y, this.g, this.h);
                }
                if (this.handlingSticker != null) {
                    this.f.set(this.handlingSticker.getMatrix());
                }
                invalidate();
                return true;
            case 1:
                long uptimeMillis = SystemClock.uptimeMillis();
                if (this.d == a.DELETE && this.handlingSticker != null) {
                    if (this.q != null) {
                        this.q.onStickerDeleted(this.handlingSticker);
                    }
                    this.t.remove(this.handlingSticker);
                    this.handlingSticker.release();
                    this.handlingSticker = null;
                    invalidate();
                }
                if (this.d == a.FLIP_HORIZONTAL && this.handlingSticker != null) {
                    this.handlingSticker.getMatrix().preScale(-1.0f, 1.0f, this.handlingSticker.getCenterPoint().x, this.handlingSticker.getCenterPoint().y);
                    this.handlingSticker.setFlipped(!this.handlingSticker.isFlipped());
                    if (this.q != null) {
                        this.q.onStickerFlipped(this.handlingSticker);
                    }
                    invalidate();
                }
                if (!((this.d != a.ZOOM_WITH_ICON && this.d != a.ZOOM_WITH_TWO_FINGER) || this.handlingSticker == null || this.q == null)) {
                    this.q.onStickerZoomFinished(this.handlingSticker);
                }
                if (this.d == a.DRAG && Math.abs(motionEvent.getX() - this.g) < ((float) this.u) && Math.abs(motionEvent.getY() - this.h) < ((float) this.u) && this.handlingSticker != null) {
                    this.d = a.CLICK;
                    if (this.q != null) {
                        this.q.onStickerClicked(this.handlingSticker);
                    }
                    if (uptimeMillis - this.j < ((long) this.m) && this.q != null) {
                        this.q.onStickerDoubleTapped(this.handlingSticker);
                    }
                }
                if (!(this.d != a.DRAG || this.handlingSticker == null || this.q == null)) {
                    this.q.onStickerDragFinished(this.handlingSticker);
                }
                this.d = a.NONE;
                this.j = uptimeMillis;
                return true;
            case 2:
                a(motionEvent);
                invalidate();
                return true;
            case 5:
                this.o = d(motionEvent);
                this.p = c(motionEvent);
                this.l = b(motionEvent);
                if (this.handlingSticker == null || !isInStickerArea(this.handlingSticker, motionEvent.getX(1), motionEvent.getY(1)) || a(this.e)) {
                    return true;
                }
                this.d = a.ZOOM_WITH_TWO_FINGER;
                return true;
            case 6:
                if (!(this.d != a.ZOOM_WITH_TWO_FINGER || this.handlingSticker == null || this.q == null)) {
                    this.q.onStickerDragFinished(this.handlingSticker);
                }
                this.d = a.NONE;
                return true;
            default:
                return true;
        }
    }

    private void a(MotionEvent motionEvent) {
        switch (this.d) {
            case DRAG:
                if (this.handlingSticker != null) {
                    motionEvent.getX();
                    float f2 = this.g;
                    motionEvent.getY();
                    float f3 = this.h;
                    this.n.set(this.f);
                    this.n.postTranslate(motionEvent.getX() - this.g, motionEvent.getY() - this.h);
                    this.handlingSticker.getMatrix().set(this.n);
                    if (this.c) {
                        a();
                        return;
                    }
                    return;
                }
                return;
            case ZOOM_WITH_TWO_FINGER:
                if (this.handlingSticker != null) {
                    float d2 = d(motionEvent);
                    float c2 = c(motionEvent);
                    this.n.set(this.f);
                    this.n.postScale(d2 / this.o, d2 / this.o, this.l.x, this.l.y);
                    this.n.postRotate(c2 - this.p, this.l.x, this.l.y);
                    this.handlingSticker.getMatrix().set(this.n);
                    return;
                }
                return;
            case ZOOM_WITH_ICON:
                if (this.handlingSticker != null) {
                    float b2 = b(this.l.x, this.l.y, motionEvent.getX(), motionEvent.getY());
                    float a2 = a(this.l.x, this.l.y, motionEvent.getX(), motionEvent.getY());
                    this.n.set(this.f);
                    this.n.postScale(b2 / this.o, b2 / this.o, this.l.x, this.l.y);
                    this.n.postRotate(a2 - this.p, this.l.x, this.l.y);
                    this.handlingSticker.getMatrix().set(this.n);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void a() {
        PointF mappedCenterPoint = this.handlingSticker.getMappedCenterPoint();
        float f2 = 0.0f;
        float f3 = mappedCenterPoint.x < 0.0f ? -mappedCenterPoint.x : 0.0f;
        if (mappedCenterPoint.x > ((float) getWidth())) {
            f3 = ((float) getWidth()) - mappedCenterPoint.x;
        }
        if (mappedCenterPoint.y < 0.0f) {
            f2 = -mappedCenterPoint.y;
        }
        if (mappedCenterPoint.y > ((float) getHeight())) {
            f2 = ((float) getHeight()) - mappedCenterPoint.y;
        }
        this.handlingSticker.getMatrix().postTranslate(f3, f2);
    }

    private boolean a(BitmapStickerIcon bitmapStickerIcon) {
        float x = bitmapStickerIcon.getX() - this.g;
        float y = bitmapStickerIcon.getY() - this.h;
        return ((double) ((x * x) + (y * y))) <= Math.pow((double) (bitmapStickerIcon.getIconRadius() + bitmapStickerIcon.getIconRadius()), 2.0d);
    }

    private Sticker b() {
        for (int size = this.t.size() - 1; size >= 0; size--) {
            if (isInStickerArea((Sticker) this.t.get(size), this.g, this.h)) {
                return (Sticker) this.t.get(size);
            }
        }
        return null;
    }

    public boolean isInStickerArea(Sticker sticker, float f2, float f3) {
        this.stickerSelected = sticker.contains(f2, f3);
        if (this.handlingSticker instanceof TextSticker) {
            isStickerTouch = this.stickerSelected;
        }
        return sticker.contains(f2, f3);
    }

    private PointF b(MotionEvent motionEvent) {
        if (motionEvent == null || motionEvent.getPointerCount() < 2) {
            return new PointF();
        }
        return new PointF((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
    }

    private PointF c() {
        if (this.handlingSticker == null) {
            return new PointF();
        }
        return this.handlingSticker.getMappedCenterPoint();
    }

    private float c(MotionEvent motionEvent) {
        if (motionEvent == null || motionEvent.getPointerCount() < 2) {
            return 0.0f;
        }
        return (float) Math.toDegrees(Math.atan2((double) (motionEvent.getY(0) - motionEvent.getY(1)), (double) (motionEvent.getX(0) - motionEvent.getX(1))));
    }

    private float a(float f2, float f3, float f4, float f5) {
        return (float) Math.toDegrees(Math.atan2((double) (f3 - f5), (double) (f2 - f4)));
    }

    private float d(MotionEvent motionEvent) {
        if (motionEvent == null || motionEvent.getPointerCount() < 2) {
            return 0.0f;
        }
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    private float b(float f2, float f3, float f4, float f5) {
        double d2 = (double) (f2 - f4);
        double d3 = (double) (f3 - f5);
        return (float) Math.sqrt((d2 * d2) + (d3 * d3));
    }


    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        for (int i6 = 0; i6 < this.t.size(); i6++) {
            try {
                Sticker sticker = (Sticker) this.t.get(i6);
                if (sticker != null) {
                    try {
                        a(sticker);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    private void a(Sticker sticker) {
        float f2;
        if (sticker == null) {
            Log.e("StickerView", "transformSticker: the bitmapSticker is null or the bitmapSticker bitmap is null");
            return;
        }
        if (this.r != null) {
            this.r.reset();
        }
        this.r.postTranslate((float) ((getWidth() - sticker.getWidth()) / 2), (float) ((getHeight() - sticker.getHeight()) / 2));
        if (getWidth() < getHeight()) {
            f2 = ((float) getWidth()) / ((float) sticker.getWidth());
        } else {
            f2 = ((float) getHeight()) / ((float) sticker.getHeight());
        }
        float f3 = f2 / 2.0f;
        this.r.postScale(f3, f3, (float) (getWidth() / 2), (float) (getHeight() / 2));
        sticker.getMatrix().reset();
        sticker.getMatrix().set(this.r);
        invalidate();
    }

    public boolean replace(Sticker sticker) {
        return replace(sticker, true);
    }

    public boolean replace(Sticker sticker, boolean z) {
        float f2;
        if (this.handlingSticker == null || sticker == null) {
            return false;
        }
        if (z) {
            try {
                sticker.getMatrix().set(this.handlingSticker.getMatrix());
                sticker.setFlipped(this.handlingSticker.isFlipped());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            try {
                this.handlingSticker.getMatrix().reset();
                sticker.getMatrix().postTranslate((float) ((getWidth() - this.handlingSticker.getWidth()) / 2), (float) ((getHeight() - this.handlingSticker.getHeight()) / 2));
                if (getWidth() < getHeight()) {
                    f2 = ((float) getWidth()) / ((float) this.handlingSticker.getDrawable().getIntrinsicWidth());
                } else {
                    f2 = ((float) getHeight()) / ((float) this.handlingSticker.getDrawable().getIntrinsicHeight());
                }
                float f3 = f2 / 2.0f;
                sticker.getMatrix().postScale(f3, f3, (float) (getWidth() / 2), (float) (getHeight() / 2));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        this.t.set(this.t.indexOf(this.handlingSticker), sticker);
        this.handlingSticker = sticker;
        invalidate();
        return true;
    }

    public boolean remove(Sticker sticker) {
        if (this.t.contains(sticker)) {
            try {
                this.t.remove(sticker);
                if (this.q != null) {
                    this.q.onStickerDeleted(sticker);
                }
                if (this.handlingSticker == sticker) {
                    this.handlingSticker = null;
                }
                invalidate();
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        Log.d("StickerView", "remove: the sticker is not in this StickerView");
        return false;
    }

    public void addSticker(Sticker sticker) {
        float f2;
        if (sticker == null) {
            Log.e("StickerView", "StickerData to be added is null!");
            return;
        }
        sticker.getMatrix().postTranslate((float) ((getWidth() - sticker.getWidth()) / 2), (float) ((getHeight() - sticker.getHeight()) / 2));
        if (getWidth() < getHeight()) {
            f2 = ((float) getWidth()) / ((float) sticker.getDrawable().getIntrinsicWidth());
        } else {
            f2 = ((float) getHeight()) / ((float) sticker.getDrawable().getIntrinsicHeight());
        }
        float f3 = f2 / 2.0f;
        sticker.getMatrix().postScale(f3, f3, (float) (getWidth() / 2), (float) (getHeight() / 2));
        this.handlingSticker = sticker;
        this.t.add(sticker);
        invalidate();
    }

    public float[] getStickerPoints(Sticker sticker) {
        if (sticker == null) {
            return new float[8];
        }
        return sticker.getMappedBoundPoints();
    }






    public Bitmap createBitmap() {
        this.handlingSticker = null;
        Bitmap createBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
        this.as = new Canvas(createBitmap);
        draw(this.as);
        return createBitmap;
    }

    public int getStickerCount() {
        return this.t.size();
    }

    public boolean isLocked() {
        return this.k;
    }

    public void setLocked(boolean z) {
        this.k = z;
        invalidate();
    }

    public void setOnStickerOperationListener(OnStickerOperationListener onStickerOperationListener) {
        this.q = onStickerOperationListener;
    }
}
