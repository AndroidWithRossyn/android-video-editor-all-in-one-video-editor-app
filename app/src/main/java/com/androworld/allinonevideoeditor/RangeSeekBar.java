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
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.appcompat.widget.AppCompatImageView;

import java.math.BigDecimal;

@SuppressLint({"WrongConstant"})
public class RangeSeekBar<T extends Number> extends AppCompatImageView {
    public static final int ACTION_POINTER_INDEX_MASK = 65280;
    public static final int ACTION_POINTER_INDEX_SHIFT = 8;
    public static final int ACTION_POINTER_UP = 6;
    public static final int BACKGROUND_COLOR = Color.parseColor("#17ce75");
    public static final int DEFAULT_COLOR = Color.parseColor("#4fa2f2");
    public static final int INVALID_POINTER_ID = 255;
    public final boolean IS_MULTI_COLORED;
    public final int LEFT_COLOR;
    public final int MIDDLE_COLOR;
    public final int RIGHT_COLOR;
    public int SINGLE_COLOR;
    float as;
    float bs;
    Bitmap c;
    Bitmap d;
    Bitmap e;
    Bitmap f;
    float g;
    private final T h;
    private final double i;
    private final T j;
    private final double k;
    private final float l;
    private OnRangeSeekBarChangeListener<T> m;
    private int n = 255;
    private float o;
    private boolean p;
    private int q;
    private double r = 1.0d;
    private double s = 0.0d;
    private boolean t = true;
    private final a u;
    private final float v;
    private final Paint w = new Paint(1);
    private b x = null;

    public interface OnRangeSeekBarChangeListener<T> {
        void onRangeSeekBarValuesChanged(RangeSeekBar<?> rangeSeekBar, T t, T t2, boolean z);
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
        this.m = onRangeSeekBarChangeListener;
    }

    public RangeSeekBar(T t2, T t3, Context context) throws IllegalArgumentException {
        super(context);
        this.j = t2;
        this.h = t3;
        this.k = t2.doubleValue();
        this.i = t3.doubleValue();
        this.u = a.a(t2);
        this.IS_MULTI_COLORED = false;
        this.SINGLE_COLOR = Color.parseColor("#0f9d58");
        this.LEFT_COLOR = 0;
        this.MIDDLE_COLOR = 0;
        this.RIGHT_COLOR = 0;
        this.SINGLE_COLOR = Color.parseColor("#0f9d58");
        this.c = BitmapFactory.decodeResource(getResources(), R.drawable.seekleft);
        this.e = BitmapFactory.decodeResource(getResources(), R.drawable.seekleft);
        this.d = BitmapFactory.decodeResource(getResources(), R.drawable.seekright);
        this.f = BitmapFactory.decodeResource(getResources(), R.drawable.seekright);
        if ((getResources().getConfiguration().screenLayout & 15) == 3) {
            this.c = scaleCenterCrop(this.c, 50, 64);
            this.e = scaleCenterCrop(this.e, 50, 64);
            this.d = scaleCenterCrop(this.d, 50, 64);
            this.f = scaleCenterCrop(this.f, 50, 64);
        } else {
            this.c = scaleCenterCrop(this.c, 50, 64);
            this.e = scaleCenterCrop(this.e, 50, 64);
            this.d = scaleCenterCrop(this.d, 50, 64);
            this.f = scaleCenterCrop(this.f, 50, 64);
        }
        Log.e("rangeSeekBar", "You are in  first rangeseek baar method");
        this.g = (float) this.c.getWidth();
        this.bs = this.g * 0.5f;
        this.as = ((float) this.c.getHeight()) * 0.5f;
        this.l = 0.3f * this.as;
        StringBuilder sb = new StringBuilder();
        sb.append("value of lineHeight is  ");
        sb.append(this.l);
        Log.e("RangeseekBar", sb.toString());
        this.v = this.bs;
        setFocusable(true);
        setFocusableInTouchMode(true);
        c();
    }

    private int a(int i2) {
        return (int) ((((float) i2) * getResources().getDisplayMetrics().density) + 0.5f);
    }

    private final void c() {
        this.q = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public boolean isNotifyWhileDragging() {
        return this.t;
    }

    public void setNotifyWhileDragging(boolean z) {
        this.t = z;
    }

    public T getAbsoluteMinValue() {
        return this.j;
    }

    public T getAbsoluteMaxValue() {
        return this.h;
    }

    public T getSelectedMinValue() {
        return a(this.s);
    }

    public void setSelectedMinValue(T t2) {
        if (0.0d == this.i - this.k) {
            setNormalizedMinValue(0.0d);
        } else {
            setNormalizedMinValue(a(t2));
        }
    }

    public T getSelectedMaxValue() {
        return a(this.r);
    }

    public void setSelectedMaxValue(T t2) {
        if (0.0d == this.i - this.k) {
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
                this.n = motionEvent.getPointerId(motionEvent.getPointerCount() - 1);
                this.o = motionEvent.getX(motionEvent.findPointerIndex(this.n));
                this.x = a(this.o);
                if (this.x != null) {
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
                if (this.p) {
                    b(motionEvent);
                    b();
                    setPressed(false);
                } else {
                    a();
                    b(motionEvent);
                    b();
                }
                this.x = null;
                invalidate();
                if (this.m != null) {
                    this.m.onRangeSeekBarValuesChanged(this, getSelectedMinValue(), getSelectedMaxValue(), true);
                    break;
                }
                break;
            case 2:
                if (this.x != null) {
                    if (this.p) {
                        b(motionEvent);
                    } else if (Math.abs(motionEvent.getX(motionEvent.findPointerIndex(this.n)) - this.o) > ((float) this.q)) {
                        setPressed(true);
                        invalidate();
                        a();
                        b(motionEvent);
                        d();
                    }
                    if (this.t && this.m != null) {
                        this.m.onRangeSeekBarValuesChanged(this, getSelectedMinValue(), getSelectedMaxValue(), false);
                        break;
                    }
                }
                break;
            case 3:
                if (this.p) {
                    b();
                    setPressed(false);
                }
                invalidate();
                break;
            case 5:
                int pointerCount = motionEvent.getPointerCount() - 1;
                this.o = motionEvent.getX(pointerCount);
                this.n = motionEvent.getPointerId(pointerCount);
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
        if (motionEvent.getPointerId(action) == this.n) {
            int i2 = action == 0 ? 1 : 0;
            this.o = motionEvent.getX(i2);
            this.n = motionEvent.getPointerId(i2);
        }
    }

    private final void b(MotionEvent motionEvent) {
        float x2 = motionEvent.getX(motionEvent.findPointerIndex(this.n));
        if (b.MIN.equals(this.x)) {
            setNormalizedMinValue(b(x2));
        } else if (b.MAX.equals(this.x)) {
            setNormalizedMaxValue(b(x2));
        }
    }

    private void d() {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }


    public void a() {
        this.p = true;
    }


    public void b() {
        this.p = false;
    }


    public synchronized void onMeasure(int i2, int i3) {
        int i4 = 200;
        if (MeasureSpec.getMode(i2) != 0) {
            i4 = MeasureSpec.getSize(i2);
        }
        int height = this.c.getHeight();
        if (MeasureSpec.getMode(i3) != 0) {
            height = Math.min(height, MeasureSpec.getSize(i3));
        }
        setMeasuredDimension(i4, height);
    }


    public synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.w.setStyle(Style.FILL);
        this.w.setAntiAlias(true);
        if (this.IS_MULTI_COLORED) {
            RectF rectF = new RectF(this.v, (((float) getHeight()) - this.l) * 0.5f, b(this.s), (((float) getHeight()) + this.l) * 0.5f);
            this.w.setColor(this.LEFT_COLOR);
            canvas.drawRect(rectF, this.w);
            RectF rectF2 = new RectF(this.v, (((float) getHeight()) - this.l) * 0.5f, ((float) getWidth()) - this.v, (((float) getHeight()) + this.l) * 0.5f);
            rectF2.left = b(this.s);
            rectF2.right = b(this.r);
            this.w.setColor(this.MIDDLE_COLOR);
            canvas.drawRect(rectF2, this.w);
            RectF rectF3 = new RectF(b(this.r), (((float) getHeight()) - this.l) * 0.5f, ((float) getWidth()) - this.v, (((float) getHeight()) + this.l) * 0.5f);
            this.w.setColor(this.RIGHT_COLOR);
            canvas.drawRect(rectF3, this.w);
        } else {
            RectF rectF4 = new RectF(this.v, ((float) (getHeight() - a(40))) * 0.5f, ((float) getWidth()) - this.v, ((float) (getHeight() + a(40))) * 0.5f);
            this.w.setColor(BACKGROUND_COLOR);
            canvas.drawRect(rectF4, this.w);
            rectF4.left = b(this.s);
            rectF4.right = b(this.r);
            this.w.setColor(this.SINGLE_COLOR);
            canvas.drawRect(rectF4, this.w);
        }
        a(b(this.s), b.MIN.equals(this.x), canvas);
        b(b(this.r), b.MAX.equals(this.x), canvas);
    }


    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUPER", super.onSaveInstanceState());
        bundle.putDouble("MIN", this.s);
        bundle.putDouble("MAX", this.r);
        return bundle;
    }


    public void onRestoreInstanceState(Parcelable parcelable) {
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable("SUPER"));
        this.s = bundle.getDouble("MIN");
        this.r = bundle.getDouble("MAX");
    }

    private void a(float f2, boolean z, Canvas canvas) {
        canvas.drawBitmap(z ? this.e : this.c, f2 - this.bs, (0.5f * ((float) getHeight())) - this.as, null);
    }

    private void b(float f2, boolean z, Canvas canvas) {
        canvas.drawBitmap(z ? this.f : this.d, f2 - this.bs, (0.5f * ((float) getHeight())) - this.as, null);
    }

    private b a(float f2) {
        boolean a2 = a(f2, this.s);
        boolean a3 = a(f2, this.r);
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
        return Math.abs(f2 - b(d2)) <= this.bs;
    }

    public void setNormalizedMinValue(double d2) {
        this.s = Math.max(0.0d, Math.min(1.0d, Math.min(d2, this.r)));
        invalidate();
    }

    public void setNormalizedMaxValue(double d2) {
        this.r = Math.max(0.0d, Math.min(1.0d, Math.max(d2, this.s)));
        invalidate();
    }

    private T a(double d2) {
        return (T) this.u.a(this.k + ((this.i - this.k) * d2));
    }

    private double a(T t2) {
        if (0.0d == this.i - this.k) {
            return 0.0d;
        }
        return (t2.doubleValue() - this.k) / (this.i - this.k);
    }

    private float b(double d2) {
        return (float) (((double) this.v) + (((double) (((float) getWidth()) - (2.0f * this.v))) * d2));
    }

    private double b(float f2) {
        float width = (float) getWidth();
        if (width <= this.v * 2.0f) {
            return 0.0d;
        }
        return Math.min(1.0d, Math.max(0.0d, (double) ((f2 - this.v) / (width - (this.v * 2.0f)))));
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
