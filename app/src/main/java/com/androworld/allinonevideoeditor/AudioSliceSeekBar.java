package com.androworld.allinonevideoeditor;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

public class AudioSliceSeekBar extends AppCompatImageView {
    private boolean a;
    private boolean b;
    private int c = 100;
    private Paint d = new Paint();
    private Paint e = new Paint();
    private int f = getResources().getColor(R.color.seekbargray);
    private int g = 2;
    private int h = 15;
    private SeekBarChangeListener i;
    private int j = getResources().getColor(R.color.colorPrimary);
    private Bitmap k = BitmapFactory.decodeResource(getResources(), R.drawable.seekbar_thumb);
    private int l;
    private int m;
    private int n;
    private int o = getResources().getDimensionPixelOffset(R.dimen.default_margin);
    private Bitmap p = BitmapFactory.decodeResource(getResources(), R.drawable.cutter);
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;

    public interface SeekBarChangeListener {
        void SeekBarValueChanged(int i, int i2);
    }

    public AudioSliceSeekBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    public AudioSliceSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AudioSliceSeekBar(Context context) {
        super(context);
    }

    @Override
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        a();
    }

    private void a() {
        if (this.p.getHeight() > getHeight()) {
            getLayoutParams().height = this.p.getHeight();
        }
        this.t = (getHeight() / 2) - (this.p.getHeight() / 2);
        this.l = (getHeight() / 2) - (this.k.getHeight() / 2);
        this.y = this.p.getWidth() / 2;
        this.n = this.k.getWidth() / 2;
        if (this.w == 0 || this.u == 0) {
            try {
                this.w = this.o;
                this.u = getWidth() - this.o;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        this.r = (getHeight() / 2) - this.g;
        this.s = (getHeight() / 2) + this.g;
        invalidate();
    }

    public void setSeekBarChangeListener(SeekBarChangeListener seekBarChangeListener) {
        this.i = seekBarChangeListener;
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.d.setColor(this.f);
        canvas.drawRect(new Rect(this.o, this.r, this.w, this.s), this.d);
        canvas.drawRect(new Rect(this.u, this.r, getWidth() - this.o, this.s), this.d);
        this.d.setColor(this.j);
        canvas.drawRect(new Rect(this.w, this.r, this.u, this.s), this.d);
        if (!this.b) {
            try {
                canvas.drawBitmap(this.p, (float) (this.w - this.y), (float) this.t, this.e);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (this.a) {
            try {
                canvas.drawBitmap(this.k, (float) (this.m - this.n), (float) this.l, this.e);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.b) {
            try {
                int x2 = (int) motionEvent.getX();
                switch (motionEvent.getAction()) {
                    case 0:
                        if ((x2 < this.w - this.y || x2 > this.w + this.y) && x2 >= this.w - this.y) {
                            try {
                                if ((x2 < this.u - this.y || x2 > this.u + this.y) && x2 <= this.u + this.y) {
                                    try {
                                        if ((x2 - this.w) + this.y >= (this.u - this.y) - x2) {
                                            try {
                                                if ((x2 - this.w) + this.y > (this.u - this.y) - x2) {
                                                    try {
                                                        this.q = 0;
                                                        break;
                                                    } catch (Exception e2) {
                                                        e2.printStackTrace();
                                                    }
                                                }
                                            } catch (Exception e8) {
                                                e8.printStackTrace();
                                            }
                                        }
                                        this.q = 1;
                                        break;
                                    } catch (Exception e14) {
                                        e14.printStackTrace();
                                    }
                                }
                                this.q = 0;
                                break;
                            } catch (Exception e20) {
                                e20.printStackTrace();
                            }
                        }
                        this.q = 1;
                        break;
                    case 1:
                        this.q = 0;
                        break;
                    case 2:
                        if ((x2 <= this.w + this.y + 0 && this.q == 2) || (x2 >= (this.u - this.y) + 0 && this.q == 1)) {
                            this.q = 0;
                        }
                        if (this.q == 2) {
                            this.u = x2;
                            break;
                        }
                        this.w = x2;
                        break;
                }
                b();
            } catch (Exception e29) {
                e29.printStackTrace();
            }
        }
        return true;
    }

    private void b() {
        if (this.w < this.o) {
            try {
                this.w = this.o;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (this.u < this.o) {
            try {
                this.u = this.o;
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (this.w > getWidth() - this.o) {
            try {
                this.w = getWidth() - this.o;
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        if (this.u > getWidth() - this.o) {
            try {
                this.u = getWidth() - this.o;
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
        invalidate();
        if (this.i != null) {
            try {
                c();
                this.i.SeekBarValueChanged(this.x, this.v);
            } catch (OutOfMemoryError e6) {
                e6.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e7) {
                e7.printStackTrace();
            } catch (ActivityNotFoundException e8) {
                e8.printStackTrace();
            } catch (NotFoundException e9) {
                e9.printStackTrace();
            } catch (NullPointerException e10) {
                e10.printStackTrace();
            } catch (StackOverflowError e11) {
                e11.printStackTrace();
            }
        }
    }

    private void c() {
        this.x = (this.c * (this.w - this.o)) / (getWidth() - (this.o * 2));
        this.v = (this.c * (this.u - this.o)) / (getWidth() - (this.o * 2));
    }

    private int a(int i2) {
        return ((int) (((((double) getWidth()) - (2.0d * ((double) this.o))) / ((double) this.c)) * ((double) i2))) + this.o;
    }

    public void setLeftProgress(int i2) {
        if (i2 < this.v - this.h) {
            try {
                this.w = a(i2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        b();
    }

    public void setRightProgress(int i2) {
        if (i2 > this.x + this.h) {
            try {
                this.u = a(i2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        b();
    }

    public int getSelectedThumb() {
        return this.q;
    }

    public int getLeftProgress() {
        return this.x;
    }

    public void videoPlayingProgress(int i2) {
        this.a = true;
        this.m = a(i2);
        invalidate();
    }

    public void removeVideoStatusThumb() {
        this.a = false;
        invalidate();
    }

    public void setSliceBlocked(boolean z) {
        this.b = z;
        invalidate();
    }

    public void setMaxValue(int i2) {
        this.c = i2;
    }

    public void setProgressMinDiff(int i2) {
        this.h = i2;
    }
}
