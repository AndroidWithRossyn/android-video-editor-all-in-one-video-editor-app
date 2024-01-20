package com.androworld.allinonevideoeditor.audiocutter.cutter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import com.androworld.allinonevideoeditor.R;

public class WaveformView extends View {
    private Paint a = new Paint();
    private Paint b;
    private Paint c;
    private Paint d;
    private Paint e;
    private Paint f;
    private Paint g;
    private CheapSoundFile h;
    private int[] i;
    private double[][] j;
    private double[] k;
    private int[] l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private float u;

    private WaveformListener v;
    private GestureDetector detector;
    private boolean aBoolean;

    public interface WaveformListener {
        void waveformDraw();

        void waveformFling(float f);

        void waveformTouchEnd();

        void waveformTouchMove(float f);

        void waveformTouchStart(float f);
    }

    @SuppressLint({"ResourceType"})
    public WaveformView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setFocusable(false);
        this.a.setAntiAlias(false);
        this.a.setColor(getResources().getColor(R.color.colorAccent));
        this.b = new Paint();
        this.b.setAntiAlias(false);
        this.b.setColor(getResources().getColor(R.color.colorAccent));
        this.c = new Paint();
        this.c.setAntiAlias(false);
        this.c.setColor(getResources().getColor(R.color.waveform_unselected));
        this.d = new Paint();
        this.d.setAntiAlias(false);
        this.d.setColor(getResources().getColor(R.color.waveform_unselected_bkgnd_overlay));
        this.e = new Paint();
        this.e.setAntiAlias(true);
        this.e.setStrokeWidth(1.5f);
        this.e.setPathEffect(new DashPathEffect(new float[]{3.0f, 2.0f}, 0.0f));
        this.e.setColor(getResources().getColor(R.color.selection_border));
        this.f = new Paint();
        this.f.setAntiAlias(false);
        this.f.setColor(getResources().getColor(R.color.playback_indicator));
        this.g = new Paint();
        this.g.setTextSize(12.0f);
        this.g.setAntiAlias(true);
        this.g.setColor(getResources().getColor(R.color.timecode));
        this.g.setShadowLayer(2.0f, 1.0f, 1.0f, getResources().getColor(R.color.timecode_shadow));
        this.detector = new GestureDetector(context, new SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                WaveformView.this.v.waveformFling(f);
                return true;
            }
        });
        this.h = null;
        this.i = null;
        this.j = null;
        this.l = null;
        this.q = 0;
        this.t = -1;
        this.r = 0;
        this.s = 0;
        this.u = 1.0f;
        this.aBoolean = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.detector.onTouchEvent(motionEvent)) {
            return true;
        }
        switch (motionEvent.getAction()) {
            case 0:
                this.v.waveformTouchStart(motionEvent.getX());
                break;
            case 1:
                this.v.waveformTouchEnd();
                break;
            case 2:
                this.v.waveformTouchMove(motionEvent.getX());
                break;
        }
        return true;
    }

    public void setSoundFile(CheapSoundFile cheapSoundFile) {
        this.h = cheapSoundFile;
        this.o = this.h.getSampleRate();
        this.p = this.h.getSamplesPerFrame();
        a();
        this.l = null;
    }

    public boolean isInitialized() {
        return this.aBoolean;
    }

    public int getZoomLevel() {
        return this.m;
    }

    public void setZoomLevel(int i2) {
        while (this.m > i2) {
            zoomIn();
        }
        while (this.m < i2) {
            zoomOut();
        }
    }

    public boolean canZoomIn() {
        return this.m > 0;
    }

    public void zoomIn() {
        if (canZoomIn()) {
            this.m--;
            this.r *= 2;
            this.s *= 2;
            this.l = null;
            this.q = ((this.q + (getMeasuredWidth() / 2)) * 2) - (getMeasuredWidth() / 2);
            if (this.q < 0) {
                this.q = 0;
            }
            invalidate();
        }
    }

    public boolean canZoomOut() {
        return this.m < this.n - 1;
    }

    public void zoomOut() {
        if (canZoomOut()) {
            this.m++;
            this.r /= 2;
            this.s /= 2;
            this.q = ((this.q + (getMeasuredWidth() / 2)) / 2) - (getMeasuredWidth() / 2);
            if (this.q < 0) {
                this.q = 0;
            }
            this.l = null;
            invalidate();
        }
    }

    public int maxPos() {
        return this.i[this.m];
    }

    public int secondsToFrames(double d2) {
        return (int) ((((1.0d * d2) * ((double) this.o)) / ((double) this.p)) + 0.5d);
    }

    public int secondsToPixels(double d2) {
        return (int) ((((this.k[this.m] * d2) * ((double) this.o)) / ((double) this.p)) + 0.5d);
    }

    public double pixelsToSeconds(int i2) {
        return (((double) i2) * ((double) this.p)) / (((double) this.o) * this.k[this.m]);
    }

    public int millisecsToPixels(int i2) {
        return (int) (((((((double) i2) * 1.0d) * ((double) this.o)) * this.k[this.m]) / (1000.0d * ((double) this.p))) + 0.5d);
    }

    public int pixelsToMillisecs(int i2) {
        return (int) (((((double) i2) * (1000.0d * ((double) this.p))) / (((double) this.o) * this.k[this.m])) + 0.5d);
    }

    public void setParameters(int i2, int i3, int i4) {
        this.r = i2;
        this.s = i3;
        this.q = i4;
    }

    public int getStart() {
        return this.r;
    }

    public int getEnd() {
        return this.s;
    }

    public int getOffset() {
        return this.q;
    }

    public void setPlayback(int i2) {
        this.t = i2;
    }

    public void setListener(WaveformListener waveformListener) {
        this.v = waveformListener;
    }

    public void recomputeHeights(float f2) {
        this.l = null;
        this.u = f2;
        this.g.setTextSize((float) ((int) (12.0f * f2)));
        invalidate();
    }


    public void drawWaveformLine(Canvas canvas, int i2, int i3, int i4, Paint paint) {
        float f2 = (float) i2;
        canvas.drawLine(f2, (float) i3, f2, (float) i4, paint);
    }

    @Override
    public void onDraw(Canvas canvas) {
        double d2;
        Paint paint;
        super.onDraw(canvas);
        if (this.h != null) {
            if (this.l == null) {
                b();
            }
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            int i2 = this.q;
            int length = this.l.length - i2;
            int i3 = measuredHeight / 2;
            int i4 = length > measuredWidth ? measuredWidth : length;
            double pixelsToSeconds = pixelsToSeconds(1);
            boolean z = pixelsToSeconds > 0.02d;
            double d3 = ((double) this.q) * pixelsToSeconds;
            int i5 = (int) d3;
            double d4 = d3;
            int i6 = 0;
            while (i6 < i4) {
                i6++;
                d4 += pixelsToSeconds;
                int i7 = (int) d4;
                if (i7 != i5) {
                    if (!z || i7 % 5 == 0) {
                        float f2 = (float) i6;
                        canvas.drawLine(f2, 0.0f, f2, (float) measuredHeight, this.a);
                    }
                    i5 = i7;
                }
            }
            for (int i8 = 0; i8 < i4; i8++) {
                int i9 = i8 + i2;
                if (i9 < this.r || i9 >= this.s) {
                    drawWaveformLine(canvas, i8, 0, measuredHeight, this.d);
                    paint = this.c;
                } else {
                    paint = this.b;
                }
                drawWaveformLine(canvas, i8, i3 - this.l[i9], i3 + 1 + this.l[i9], paint);
                if (i9 == this.t) {
                    float f3 = (float) i8;
                    canvas.drawLine(f3, 0.0f, f3, (float) measuredHeight, this.f);
                }
            }
            for (int i10 = i4; i10 < measuredWidth; i10++) {
                drawWaveformLine(canvas, i10, 0, measuredHeight, this.d);
            }
            Canvas canvas2 = canvas;
            canvas2.drawLine(((float) (this.r - this.q)) + 0.5f, 30.0f, ((float) (this.r - this.q)) + 0.5f, (float) measuredHeight, this.e);
            canvas2.drawLine(((float) (this.s - this.q)) + 0.5f, 0.0f, ((float) (this.s - this.q)) + 0.5f, (float) (measuredHeight - 30), this.e);
            double d5 = 1.0d;
            if (1.0d / pixelsToSeconds < 50.0d) {
                d5 = 5.0d;
            }
            if (d5 / pixelsToSeconds < 50.0d) {
                d5 = 15.0d;
            }
            double d6 = ((double) this.q) * pixelsToSeconds;
            int i11 = (int) (d6 / d5);
            double d7 = d6;
            int i12 = 0;
            while (i12 < i4) {
                i12++;
                d7 += pixelsToSeconds;
                int i13 = (int) d7;
                int i14 = (int) (d7 / d5);
                if (i14 != i11) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(i13 / 60);
                    String sb2 = sb.toString();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("");
                    int i15 = i13 % 60;
                    sb3.append(i15);
                    String sb4 = sb3.toString();
                    if (i15 < 10) {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("0");
                        sb5.append(sb4);
                        sb4 = sb5.toString();
                    }
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(sb2);
                    sb6.append(":");
                    sb6.append(sb4);
                    String sb7 = sb6.toString();
                    d2 = d5;
                    canvas.drawText(sb7, ((float) i12) - ((float) (0.5d * ((double) this.g.measureText(sb7)))), (float) ((int) (12.0f * this.u)), this.g);
                    i11 = i14;
                } else {
                    d2 = d5;
                }
                d5 = d2;
            }
            if (this.v != null) {
                this.v.waveformDraw();
            }
        }
    }

    private void a() {
        int i2;
        int numFrames = this.h.getNumFrames();
        int[] frameGains = this.h.getFrameGains();
        double[] dArr = new double[numFrames];
        if (numFrames == 1) {
            dArr[0] = (double) frameGains[0];
        } else if (numFrames == 2) {
            dArr[0] = (double) frameGains[0];
            dArr[1] = (double) frameGains[1];
        } else if (numFrames > 2) {
            dArr[0] = (((double) frameGains[0]) / 2.0d) + (((double) frameGains[1]) / 2.0d);
            int i3 = 1;
            while (true) {
                i2 = numFrames - 1;
                if (i3 >= i2) {
                    break;
                }
                int i4 = i3 + 1;
                dArr[i3] = (((double) frameGains[i3 - 1]) / 3.0d) + (((double) frameGains[i3]) / 3.0d) + (((double) frameGains[i4]) / 3.0d);
                i3 = i4;
            }
            dArr[i2] = (((double) frameGains[numFrames - 2]) / 2.0d) + (((double) frameGains[i2]) / 2.0d);
        }
        double d2 = 1.0d;
        for (int i5 = 0; i5 < numFrames; i5++) {
            if (dArr[i5] > d2) {
                d2 = dArr[i5];
            }
        }
        double d3 = d2 > 255.0d ? 255.0d / d2 : 1.0d;
        int[] iArr = new int[256];
        double d4 = 0.0d;
        for (int i6 = 0; i6 < numFrames; i6++) {
            int i7 = (int) (dArr[i6] * d3);
            if (i7 < 0) {
                i7 = 0;
            }
            if (i7 > 255) {
                i7 = 255;
            }
            double d5 = (double) i7;
            if (d5 > d4) {
                d4 = d5;
            }
            iArr[i7] = iArr[i7] + 1;
        }
        double d6 = 0.0d;
        int i8 = 0;
        while (d6 < 255.0d && i8 < numFrames / 20) {
            i8 += iArr[(int) d6];
            d6 += 1.0d;
        }
        double d7 = d4;
        int i9 = 0;
        while (d7 > 2.0d && i9 < numFrames / 100) {
            i9 += iArr[(int) d7];
            d7 -= 1.0d;
        }
        double[] dArr2 = new double[numFrames];
        double d8 = d7 - d6;
        for (int i10 = 0; i10 < numFrames; i10++) {
            double d9 = ((dArr[i10] * d3) - d6) / d8;
            if (d9 < 0.0d) {
                d9 = 0.0d;
            }
            if (d9 > 1.0d) {
                d9 = 1.0d;
            }
            dArr2[i10] = d9 * d9;
        }
        this.n = 5;
        this.i = new int[5];
        this.k = new double[5];
        this.j = new double[5][];
        char c2 = 0;
        this.i[0] = numFrames * 2;
        this.k[0] = 2.0d;
        this.j[0] = new double[this.i[0]];
        if (numFrames > 0) {
            this.j[0][0] = dArr2[0] * 0.5d;
            this.j[0][1] = dArr2[0];
        }
        int i11 = 1;
        while (i11 < numFrames) {
            int i12 = 2 * i11;
            this.j[c2][i12] = (dArr2[i11 - 1] + dArr2[i11]) * 0.5d;
            this.j[c2][i12 + 1] = dArr2[i11];
            i11++;
            c2 = 0;
        }
        this.i[1] = numFrames;
        this.j[1] = new double[this.i[1]];
        this.k[1] = 1.0d;
        for (int i13 = 0; i13 < this.i[1]; i13++) {
            this.j[1][i13] = dArr2[i13];
        }
        for (int i14 = 2; i14 < 5; i14++) {
            int i15 = i14 - 1;
            this.i[i14] = this.i[i15] / 2;
            this.j[i14] = new double[this.i[i14]];
            this.k[i14] = this.k[i15] / 2.0d;
            for (int i16 = 0; i16 < this.i[i14]; i16++) {
                int i17 = 2 * i16;
                this.j[i14][i16] = (this.j[i15][i17] + this.j[i15][i17 + 1]) * 0.5d;
            }
        }
        if (numFrames > 5000) {
            this.m = 3;
        } else if (numFrames > 1000) {
            this.m = 2;
        } else if (numFrames > 300) {
            this.m = 1;
        } else {
            this.m = 0;
        }
        this.aBoolean = true;
    }

    private void b() {
        int measuredHeight = (getMeasuredHeight() / 2) - 1;
        this.l = new int[this.i[this.m]];
        for (int i2 = 0; i2 < this.i[this.m]; i2++) {
            this.l[i2] = (int) (this.j[this.m][i2] * ((double) measuredHeight));
        }
    }
}
