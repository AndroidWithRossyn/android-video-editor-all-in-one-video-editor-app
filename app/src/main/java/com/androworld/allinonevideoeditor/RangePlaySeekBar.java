package com.androworld.allinonevideoeditor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.appcompat.widget.AppCompatImageView;

import java.math.BigDecimal;

public class RangePlaySeekBar<T extends Number> extends AppCompatImageView {
    public final boolean IS_MULTI_COLORED;
    public final int LEFT_COLOR;
    public final int MIDDLE_COLOR;
    public final int RIGHT_COLOR;
    public final int SINGLE_COLOR;
    private final T as;
    private final double bs;
    private final T c;
    private final double d;
    private final float e;
    private OnRangeSeekBarChangeListener<T> f;
    private int g = 255;
    private float h;
    private boolean i;
    private int j;
    private double k = 1.0d;
    private double l = 0.0d;
    private boolean m = true;
    private final a n;
    private final float o;
    private final Paint p = new Paint(1);
    private b q = null;
    private final float r;
    private final float s;
    private Bitmap t;
    private Bitmap u;
    private final float v;

    public interface OnRangeSeekBarChangeListener<T> {
        void onRangeSeekBarValuesChanged(RangePlaySeekBar<?> rangePlaySeekBar, T t, T t2);
    }

    private enum a {
        LONG,
        DOUBLE,
        INTEGER,
        FLOAT,
        SHORT,
        BYTE,
        BIG_DECIMAL;

        public static <E extends Number> a a(E e) throws IllegalArgumentException {
            if (e instanceof Long) {
                return LONG;
            }
            if (e instanceof Double) {
                return DOUBLE;
            }
            if (e instanceof Integer) {
                return INTEGER;
            }
            if (e instanceof Float) {
                return FLOAT;
            }
            if (e instanceof Short) {
                return SHORT;
            }
            if (e instanceof Byte) {
                return BYTE;
            }
            if (e instanceof BigDecimal) {
                return BIG_DECIMAL;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Number class '");
            sb.append(e.getClass().getName());
            sb.append("' is not supported");
            throw new IllegalArgumentException(sb.toString());
        }

        public Number a(double d) {
            switch (this) {
                case LONG:
                    return new Long((long) d);
                case DOUBLE:
                    return Double.valueOf(d);
                case INTEGER:
                    return new Integer((int) d);
                case FLOAT:
                    return new Float(d);
                case SHORT:
                    return new Short((short) ((int) d));
                case BYTE:
                    return new Byte((byte) ((int) d));
                case BIG_DECIMAL:
                    return new BigDecimal(d);
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("can't convert ");
                    sb.append(this);
                    sb.append(" to a Number object");
                    throw new InstantiationError(sb.toString());
            }
        }
    }

    private enum b {
        MIN,
        MAX
    }

    public void setOnRangeSeekBarChangeListener(OnRangeSeekBarChangeListener<T> onRangeSeekBarChangeListener) {
        this.f = onRangeSeekBarChangeListener;
    }

    public RangePlaySeekBar(T t2, T t3, Context context) throws IllegalArgumentException {
        super(context);
        this.c = t2;
        this.as = t3;
        this.d = t2.doubleValue();
        this.bs = t3.doubleValue();
        this.n = a.a(t2);
        this.IS_MULTI_COLORED = false;
        this.SINGLE_COLOR = 0;
        this.LEFT_COLOR = 0;
        this.MIDDLE_COLOR = 0;
        this.RIGHT_COLOR = 0;
        this.t = BitmapFactory.decodeResource(getResources(), R.drawable.seek_thumb_normal_play);
        this.u = BitmapFactory.decodeResource(getResources(), R.drawable.seek_thumb_normal_play);
        if ((getResources().getConfiguration().screenLayout & 15) == 3) {
            this.t = scaleCenterCrop(this.t, 50, 20);
            this.u = scaleCenterCrop(this.u, 50, 20);
        } else {
            this.t = scaleCenterCrop(this.t, 50, 20);
            this.u = scaleCenterCrop(this.u, 50, 20);
        }
        this.v = (float) this.t.getWidth();
        this.s = this.v * 0.5f;
        this.r = ((float) this.t.getHeight()) * 0.5f;
        this.e = 0.3f * this.r;
        this.o = this.s;
        setFocusable(true);
        setFocusableInTouchMode(true);
        c();
    }

    public RangePlaySeekBar(T t2, T t3, Context context, int i2, int i3, int i4) throws IllegalArgumentException {
        super(context);
        this.c = t2;
        this.as = t3;
        this.d = t2.doubleValue();
        this.bs = t3.doubleValue();
        this.n = a.a(t2);
        this.IS_MULTI_COLORED = false;
        if (i2 >= 0) {
            i2 = 0;
        }
        this.SINGLE_COLOR = i2;
        this.LEFT_COLOR = 0;
        this.MIDDLE_COLOR = 0;
        this.RIGHT_COLOR = 0;
        this.t = BitmapFactory.decodeResource(getResources(), i3);
        this.u = BitmapFactory.decodeResource(getResources(), i4);
        this.v = (float) this.t.getWidth();
        this.s = this.v * 0.5f;
        this.r = ((float) this.t.getHeight()) * 0.5f;
        this.e = 0.3f * this.r;
        this.o = this.s;
        setFocusable(true);
        setFocusableInTouchMode(true);
        c();
    }

    public RangePlaySeekBar(T t2, T t3, Context context, int i2, int i3, int i4, int i5, int i6) throws IllegalArgumentException {
        super(context);
        this.c = t2;
        this.as = t3;
        this.d = t2.doubleValue();
        this.bs = t3.doubleValue();
        this.n = a.a(t2);
        this.IS_MULTI_COLORED = true;
        this.SINGLE_COLOR = 0;
        if (i2 >= 0) {
            i2 = Color.argb(255, 255, 0, 0);
        }
        this.LEFT_COLOR = i2;
        if (i3 >= 0) {
            i3 = Color.argb(255, 0, 255, 0);
        }
        this.MIDDLE_COLOR = i3;
        if (i4 >= 0) {
            i4 = Color.argb(255, 0, 0, 255);
        }
        this.RIGHT_COLOR = i4;
        this.t = BitmapFactory.decodeResource(getResources(), i5);
        this.u = BitmapFactory.decodeResource(getResources(), i6);
        this.v = (float) this.t.getWidth();
        this.s = this.v * 0.5f;
        this.r = ((float) this.t.getHeight()) * 0.5f;
        this.e = 0.3f * this.r;
        this.o = this.s;
        setFocusable(true);
        setFocusableInTouchMode(true);
        c();
    }

    private final void c() {
        this.j = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public boolean isNotifyWhileDragging() {
        return this.m;
    }

    public void setNotifyWhileDragging(boolean z) {
        this.m = z;
    }

    public T getAbsoluteMinValue() {
        return this.c;
    }

    public T getAbsoluteMaxValue() {
        return this.as;
    }

    public T getSelectedMinValue() {
        return aa(this.l);
    }

    public void setSelectedMinValue(T t2) {
        if (0.0d == this.bs - this.d) {
            setNormalizedMinValue(0.0d);
        } else {
            setNormalizedMinValue(a(t2));
        }
    }

    public T getSelectedMaxValue() {
        return aa(this.k);
    }

    public void setSelectedMaxValue(T t2) {
        if (0.0d == this.bs - this.d) {
            setNormalizedMaxValue(1.0d);
        } else {
            setNormalizedMaxValue(a(t2));
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.g = motionEvent.getPointerId(motionEvent.getPointerCount() - 1);
                this.h = motionEvent.getX(motionEvent.findPointerIndex(this.g));
                this.q = a(this.h);
                if (this.q != null) {
                    setPressed(true);
                    invalidate();
                    a();
                    b(motionEvent);
                    d();
                    break;
                } else {
                    return super.onTouchEvent(motionEvent);
                }
            case 1:
                if (this.i) {
                    b(motionEvent);
                    b();
                    setPressed(false);
                } else {
                    a();
                    b(motionEvent);
                    b();
                }
                this.q = null;
                invalidate();
                if (this.f != null) {
                    this.f.onRangeSeekBarValuesChanged(this, getSelectedMinValue(), getSelectedMaxValue());
                    break;
                }
                break;
            case 2:
                if (this.q != null) {
                    if (this.i) {
                        b(motionEvent);
                    } else if (Math.abs(motionEvent.getX(motionEvent.findPointerIndex(this.g)) - this.h) > ((float) this.j)) {
                        setPressed(true);
                        invalidate();
                        a();
                        b(motionEvent);
                        d();
                    }
                    if (this.m && this.f != null) {
                        this.f.onRangeSeekBarValuesChanged(this, getSelectedMinValue(), getSelectedMaxValue());
                        break;
                    }
                }
                break;
            case 3:
                if (this.i) {
                    b();
                    setPressed(false);
                }
                invalidate();
                break;
            case 5:
                int pointerCount = motionEvent.getPointerCount() - 1;
                this.h = motionEvent.getX(pointerCount);
                this.g = motionEvent.getPointerId(pointerCount);
                invalidate();
                break;
            case 6:
                a(motionEvent);
                invalidate();
                break;
        }
        return true;
    }

    private final void a(MotionEvent motionEvent) {
        int action = (motionEvent.getAction() & 65280) >> 8;
        if (motionEvent.getPointerId(action) == this.g) {
            int i2 = action == 0 ? 1 : 0;
            this.h = motionEvent.getX(i2);
            this.g = motionEvent.getPointerId(i2);
        }
    }

    private final void b(MotionEvent motionEvent) {
        float x = motionEvent.getX(motionEvent.findPointerIndex(this.g));
        if (b.MIN.equals(this.q)) {
            setNormalizedMinValue(b(x));
        } else if (b.MAX.equals(this.q)) {
            setNormalizedMaxValue(b(x));
        }
    }

    private void d() {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }


    public void a() {
        this.i = true;
    }


    public void b() {
        this.i = false;
    }


    @SuppressLint({"WrongConstant"})
    public synchronized void onMeasure(int i2, int i3) {
        int i4 = 200;
        if (MeasureSpec.getMode(i2) != 0) {
            i4 = MeasureSpec.getSize(i2);
        }
        int height = this.t.getHeight();
        if (MeasureSpec.getMode(i3) != 0) {
            height = Math.min(height, MeasureSpec.getSize(i3));
        }
        setMeasuredDimension(i4, height);
    }


    public synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.p.setStyle(Style.FILL);
        this.p.setAntiAlias(true);
        if (this.IS_MULTI_COLORED) {
            RectF rectF = new RectF(this.o, (((float) getHeight()) - this.e) * 0.5f, b(this.l), (((float) getHeight()) + this.e) * 0.5f);
            this.p.setColor(this.LEFT_COLOR);
            canvas.drawRect(rectF, this.p);
            RectF rectF2 = new RectF(this.o, (((float) getHeight()) - this.e) * 0.5f, ((float) getWidth()) - this.o, (((float) getHeight()) + this.e) * 0.5f);
            rectF2.left = b(this.l);
            rectF2.right = b(this.k);
            this.p.setColor(this.MIDDLE_COLOR);
            canvas.drawRect(rectF2, this.p);
            RectF rectF3 = new RectF(b(this.k), (((float) getHeight()) - this.e) * 0.5f, ((float) getWidth()) - this.o, (((float) getHeight()) + this.e) * 0.5f);
            this.p.setColor(this.RIGHT_COLOR);
            canvas.drawRect(rectF3, this.p);
        } else {
            RectF rectF4 = new RectF(this.o, (((float) getHeight()) - this.e) * 0.5f, ((float) getWidth()) - this.o, (((float) getHeight()) + this.e) * 0.5f);
            this.p.setColor(0);
            canvas.drawRect(rectF4, this.p);
            rectF4.left = b(this.l);
            rectF4.right = b(this.k);
            this.p.setColor(this.SINGLE_COLOR);
            canvas.drawRect(rectF4, this.p);
        }
        a(b(this.k), b.MAX.equals(this.q), canvas);
    }


    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUPER", super.onSaveInstanceState());
        bundle.putDouble("MIN", this.l);
        bundle.putDouble("MAX", this.k);
        return bundle;
    }


    public void onRestoreInstanceState(Parcelable parcelable) {
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable("SUPER"));
        this.l = bundle.getDouble("MIN");
        this.k = bundle.getDouble("MAX");
    }

    private void a(float f2, boolean z, Canvas canvas) {
        canvas.drawBitmap(z ? this.u : this.t, f2 - this.s, (0.5f * ((float) getHeight())) - this.r, null);
    }

    private b a(float f2) {
        boolean a2 = a(f2, this.l);
        boolean a3 = a(f2, this.k);
        if (a2 && a3) {
            return f2 / ((float) getWidth()) > 0.5f ? b.MIN : b.MAX;
        } else if (a2) {
            return b.MIN;
        } else {
            if (a3) {
                return b.MAX;
            }
            return null;
        }
    }

    private boolean a(float f2, double d2) {
        return Math.abs(f2 - b(d2)) <= this.s;
    }

    public void setNormalizedMinValue(double d2) {
        this.l = Math.max(0.0d, Math.min(1.0d, Math.min(d2, this.k)));
        invalidate();
    }

    public void setNormalizedMaxValue(double d2) {
        this.k = Math.max(0.0d, Math.min(1.0d, Math.max(d2, this.l)));
        invalidate();
    }

    private T aa(double d2) {
        return (T) this.n.a(this.d + ((this.bs - this.d) * d2));
    }

    private double a(T t2) {
        if (0.0d == this.bs - this.d) {
            return 0.0d;
        }
        return (t2.doubleValue() - this.d) / (this.bs - this.d);
    }

    private float b(double d2) {
        return (float) (((double) this.o) + (((double) (((float) getWidth()) - (2.0f * this.o))) * d2));
    }

    private double b(float f2) {
        float width = (float) getWidth();
        if (width <= this.o * 2.0f) {
            return 0.0d;
        }
        return Math.min(1.0d, Math.max(0.0d, (double) ((f2 - this.o) / (width - (this.o * 2.0f)))));
    }

    public Bitmap scaleCenterCrop(Bitmap bitmap, int i2, int i3) {
        float f2 = (float) i3;
        float width = (float) bitmap.getWidth();
        float f3 = (float) i2;
        float height = (float) bitmap.getHeight();
        float max = Math.max(f2 / width, f3 / height);
        float f4 = width * max;
        float f5 = max * height;
        float f6 = (f2 - f4) / 2.0f;
        float f7 = (f3 - f5) / 2.0f;
        RectF rectF = new RectF(f6, f7, f4 + f6, f5 + f7);
        Bitmap createBitmap = Bitmap.createBitmap(i3, i2, bitmap.getConfig());
        new Canvas(createBitmap).drawBitmap(bitmap, null, rectF, null);
        return createBitmap;
    }
}
