package com.androworld.allinonevideoeditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

public class VideoSliceSeekBar extends AppCompatImageView {
    private Bitmap A = BitmapFactory.decodeResource(getResources(), R.drawable.cutter);
    private boolean a;
    private boolean b;
    private int c = 100;
    private Paint d = new Paint();
    private Paint e = new Paint();
    private int f;
    private int g = getResources().getColor(R.color.seekbargray);
    private int h = 3;
    private int i = 15;
    private int j;
    private int k;
    private SeekBarChangeListener l;
    private int m = getResources().getColor(R.color.colorPrimary);
    private int n;
    private Bitmap o = BitmapFactory.decodeResource(getResources(), R.drawable.seekbar_thumb);
    private int p;
    private int q;
    private int r;
    private int s = getResources().getDimensionPixelOffset(R.dimen.default_margin);
    private Bitmap t = BitmapFactory.decodeResource(getResources(), R.drawable.cutter);
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;

    public interface SeekBarChangeListener {
        void SeekBarValueChanged(int i, int i2);
    }

    public VideoSliceSeekBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    public VideoSliceSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public VideoSliceSeekBar(Context context) {
        super(context);
    }

    public void onWindowFocusChanged(boolean z2) {
        super.onWindowFocusChanged(z2);
        if (!isInEditMode()) {
            a();
        }
    }

    private void a() {
        if (this.t.getHeight() > getHeight()) {
            getLayoutParams().height = this.t.getHeight();
        }
        this.z = (getHeight() / 2) - (this.t.getHeight() / 2);
        this.r = (getHeight() / 2) - (this.o.getHeight() / 2);
        this.u = this.t.getWidth() / 2;
        this.p = this.o.getWidth() / 2;
        if (this.w == 0 || this.y == 0) {
            this.w = this.s;
            this.y = getWidth() - this.s;
        }
        this.j = a(this.i) - (this.s * 2);
        this.k = (getHeight() / 2) - this.h;
        this.f = (getHeight() / 2) + this.h;
        invalidate();
    }

    public void setSeekBarChangeListener(SeekBarChangeListener seekBarChangeListener) {
        this.l = seekBarChangeListener;
    }


    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.d.setColor(this.g);
        canvas.drawRect(new Rect(this.s, this.k, this.w, this.f), this.d);
        canvas.drawRect(new Rect(this.y, this.k, getWidth() - this.s, this.f), this.d);
        this.d.setColor(this.m);
        canvas.drawRect(new Rect(this.w, this.k, this.y, this.f), this.d);
        if (!this.a) {
            canvas.drawBitmap(this.t, (float) (this.w - this.u), (float) this.z, this.e);
            canvas.drawBitmap(this.A, (float) (this.y - this.u), (float) this.z, this.e);
        }
        if (this.b) {
            canvas.drawBitmap(this.o, (float) (this.q - this.p), (float) this.r, this.e);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.a) {
            int x2 = (int) motionEvent.getX();
            switch (motionEvent.getAction()) {
                case 0:
                    if ((x2 < this.w - this.u || x2 > this.w + this.u) && x2 >= this.w - this.u) {
                        if ((x2 < this.y - this.u || x2 > this.y + this.u) && x2 <= this.y + this.u) {
                            if ((x2 - this.w) + this.u >= (this.y - this.u) - x2 && (x2 - this.w) + this.u > (this.y - this.u) - x2) {
                                this.n = 2;
                                break;
                            } else {
                                this.n = 1;
                                break;
                            }
                        } else {
                            this.n = 2;
                            break;
                        }
                    } else {
                        this.n = 1;
                        break;
                    }
                case 1:
                    this.n = 0;
                    break;
                case 2:
                    if ((x2 <= this.w + this.u + 0 && this.n == 2) || (x2 >= (this.y - this.u) + 0 && this.n == 1)) {
                        this.n = 0;
                    }
                    if (this.n != 1 && this.n == 2) {
                        this.y = x2;
                        break;
                    } else {
                        this.w = x2;
                        break;
                    }
            }
            b();
        }
        return true;
    }

    private void b() {
        if (this.w < this.s) {
            this.w = this.s;
        }
        if (this.y < this.s) {
            this.y = this.s;
        }
        if (this.w > getWidth() - this.s) {
            this.w = getWidth() - this.s;
        }
        if (this.y > getWidth() - this.s) {
            this.y = getWidth() - this.s;
        }
        invalidate();
        if (this.l != null) {
            c();
            this.l.SeekBarValueChanged(this.v, this.x);
        }
    }

    private void c() {
        this.v = (this.c * (this.w - this.s)) / (getWidth() - (this.s * 2));
        this.x = (this.c * (this.y - this.s)) / (getWidth() - (this.s * 2));
    }

    private int a(int i2) {
        return ((int) (((((double) getWidth()) - (2.0d * ((double) this.s))) / ((double) this.c)) * ((double) i2))) + this.s;
    }

    public void setLeftProgress(int i2) {
        if (i2 < this.x - this.i) {
            this.w = a(i2);
        }
        b();
    }

    public void setRightProgress(int i2) {
        if (i2 > this.v + this.i) {
            this.y = a(i2);
        }
        b();
    }

    public int getSelectedThumb() {
        return this.n;
    }

    public int getLeftProgress() {
        return this.v;
    }

    public int getRightProgress() {
        return this.x;
    }

    public void setProgress(int i2, int i3) {
        if (i3 - i2 > this.i) {
            this.w = a(i2);
            this.y = a(i3);
        }
        b();
    }

    public void videoPlayingProgress(int i2) {
        this.b = true;
        this.q = a(i2);
        invalidate();
    }

    public void removeVideoStatusThumb() {
        this.b = false;
        invalidate();
    }

    public void setSliceBlocked(boolean z2) {
        this.a = z2;
        invalidate();
    }

    public void setMaxValue(int i2) {
        this.c = i2;
    }

    public void setProgressMinDiff(int i2) {
        this.i = i2;
        this.j = a(i2);
    }

    public void setProgressHeight(int i2) {
        this.h /= 2;
        invalidate();
    }

    public void setProgressColor(int i2) {
        this.g = i2;
        invalidate();
    }

    public void setSecondaryProgressColor(int i2) {
        this.m = i2;
        invalidate();
    }

    public void setThumbSlice(Bitmap bitmap) {
        this.t = bitmap;
        a();
    }

    public void setThumbCurrentVideoPosition(Bitmap bitmap) {
        this.o = bitmap;
        a();
    }

    public void setThumbPadding(int i2) {
        this.s = i2;
        invalidate();
    }
}
