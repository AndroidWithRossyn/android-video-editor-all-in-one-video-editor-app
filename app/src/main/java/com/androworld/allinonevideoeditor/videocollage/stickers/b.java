package com.androworld.allinonevideoeditor.videocollage.stickers;

import android.annotation.TargetApi;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout.LayoutParams;

@TargetApi(11)
class b implements OnTouchListener {
    double a;
    int b;
    int c;
    int d;
    int e;
    int f;
    int g;
    int h;
    int i;
    int j;
    int k;
    int l;
    float m = -1.0f;
    float n = -1.0f;
    Point o;
    private LayoutParams p;
    private View q;
    private Point r;
    private LayoutParams s;
    private LayoutParams t;

    public b(View view) {
        this.q = view;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.t = (LayoutParams) view.getLayoutParams();
            this.p = (LayoutParams) this.q.getLayoutParams();
            this.s = (LayoutParams) ((ClgSingleFingerView) view.getParent().getParent()).mPushDelete.getLayoutParams();
            this.o = a(this.t, motionEvent);
            this.h = this.p.width;
            this.k = this.p.height;
            this.j = this.p.leftMargin;
            this.i = this.p.topMargin;
            this.l = (int) this.q.getRotation();
            this.g = this.t.leftMargin;
            this.f = this.t.topMargin;
            this.e = this.s.leftMargin;
            this.d = this.s.topMargin;
            this.b = this.t.width;
            this.c = this.t.height;
            this.m = motionEvent.getRawX();
            this.n = motionEvent.getRawY();
            a();
        } else if (action == 2) {
            float rawX = motionEvent.getRawX();
            float rawY = motionEvent.getRawY();
            if (this.m == -1.0f || Math.abs(rawX - this.m) >= 5.0f || Math.abs(rawY - this.n) >= 5.0f) {
                try {
                    this.m = rawX;
                    this.n = rawY;
                    Point point = this.r;
                    Point point2 = this.o;
                    Point a2 = a(this.t, motionEvent);
                    float a3 = a(point, point2);
                    float a4 = a(point, a2);
                    float f2 = a4 / a3;
                    int i2 = (int) (((float) this.h) * f2);
                    int i3 = (int) (((float) this.k) * f2);
                    this.p.leftMargin = this.j - ((i2 - this.h) / 2);
                    this.p.topMargin = this.i - ((i3 - this.k) / 2);
                    this.p.width = i2;
                    this.p.height = i3;
                    this.q.setLayoutParams(this.p);
                    double acos = (180.0d * Math.acos((double) ((((point2.a - point.a) * (a2.a - point.a)) + ((point2.b - point.b) * (a2.b - point.b))) / (a3 * a4)))) / 3.14159265359d;
                    if (Double.isNaN(acos)) {
                        try {
                            acos = (double) ((this.a < 90.0d || this.a > 270.0d) ? 0 : 180);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    } else if ((a2.b - point.b) * (point2.a - point.a) < (point2.b - point.b) * (a2.a - point.a)) {
                        acos = 360.0d - acos;
                    }
                    this.a = acos;
                    float f3 = ((float) (((double) this.l) + acos)) % 360.0f;
                    this.q.setRotation(f3);
                    Point a5 = a(point, new Point((float) (this.q.getLeft() + this.q.getWidth()), (float) (this.q.getTop() + this.q.getHeight())), f3);
                    this.t.leftMargin = (int) (a5.a - ((float) (this.b / 2)));
                    this.t.topMargin = (int) (a5.b - ((float) (this.c / 2)));
                    this.s.leftMargin = this.q.getRight();
                    this.s.topMargin = this.q.getTop();
                    view.setLayoutParams(this.t);
                    ((ClgSingleFingerView) view.getParent().getParent()).mPushDelete.setLayoutParams(this.s);
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            return false;
        }
        return false;
    }

    private void a() {
        this.r = new Point((float) (this.q.getLeft() + (this.q.getWidth() / 2)), (float) (this.q.getTop() + (this.q.getHeight() / 2)));
    }

    private Point a(LayoutParams layoutParams, MotionEvent motionEvent) {
        return new Point((float) (layoutParams.leftMargin + ((int) motionEvent.getX())), (float) (layoutParams.topMargin + ((int) motionEvent.getY())));
    }

    private float a(Point point, Point point2) {
        return ((float) ((int) (Math.sqrt((double) (((point.a - point2.a) * (point.a - point2.a)) + ((point.b - point2.b) * (point.b - point2.b)))) * 100.0d))) / 100.0f;
    }

    private Point a(Point point, Point point2, float f2) {
        float a2 = a(point, point2);
        double d2 = (((double) f2) * 3.14159265359d) / 180.0d;
        double d3 = (double) a2;
        return new Point((float) ((int) (((double) point.a) + (Math.cos(Math.acos((double) ((point2.a - point.a) / a2)) + d2) * d3))), (float) ((int) (((double) point.b) + (d3 * Math.sin(d2 + Math.acos((double) ((point2.a - point.a) / a2)))))));
    }
}
