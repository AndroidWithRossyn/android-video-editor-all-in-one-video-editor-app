package com.androworld.allinonevideoeditor.videocollage.utils;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ImageViewTouchListener implements OnTouchListener {
    private float a = 0.0f;
    private float[] b = null;
    private Matrix c = new Matrix();
    private PointF d = new PointF();
    private int e = 0;
    private float f = 0.0f;
    private float g = 1.0f;
    private Matrix h = new Matrix();
    private PointF i = new PointF();

    public ImageViewTouchListener(Context context) {
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        ImageView imageView = (ImageView) view;
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.h.set(this.c);
                this.i.set(motionEvent.getX(), motionEvent.getY());
                this.e = 1;
                this.b = null;
                break;
            case 1:
            case 6:
                this.e = 0;
                this.b = null;
                break;
            case 2:
                if (this.e != 1) {
                    try {
                        if (this.e == 2) {
                            try {
                                float a2 = a(motionEvent);
                                if (a2 > 10.0f) {
                                    try {
                                        this.c.set(this.h);
                                        float f2 = a2 / this.g;
                                        this.c.postScale(f2, f2, this.d.x, this.d.y);
                                    } catch (Exception e2) {
                                        e2.printStackTrace();
                                    }
                                }
                                if (this.b != null && motionEvent.getPointerCount() == 2) {
                                    try {
                                        this.f = b(motionEvent);
                                        float f3 = this.f - this.a;
                                        float[] fArr = new float[9];
                                        this.c.getValues(fArr);
                                        float f4 = fArr[2];
                                        float f5 = fArr[5];
                                        float f6 = fArr[0];
                                        this.c.postRotate(f3, f4 + (((float) (imageView.getWidth() / 2)) * f6), f5 + (((float) (imageView.getHeight() / 2)) * f6));
                                        break;
                                    } catch (Exception e3) {
                                        e3.printStackTrace();
                                    }
                                }
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                        }
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
                this.c.set(this.h);
                this.c.postTranslate(motionEvent.getX() - this.i.x, motionEvent.getY() - this.i.y);
                break;
            case 5:
                this.g = a(motionEvent);
                if (this.g > 10.0f) {
                    try {
                        this.h.set(this.c);
                        a(this.d, motionEvent);
                        this.e = 2;
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
                this.b = new float[4];
                this.b[0] = motionEvent.getX(0);
                this.b[1] = motionEvent.getX(1);
                this.b[2] = motionEvent.getY(0);
                this.b[3] = motionEvent.getY(1);
                this.a = b(motionEvent);
                break;
        }
        imageView.setImageMatrix(this.c);
        return true;
    }

    private float a(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    private void a(PointF pointF, MotionEvent motionEvent) {
        pointF.set((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
    }

    private float b(MotionEvent motionEvent) {
        return (float) Math.toDegrees(Math.atan2((double) (motionEvent.getY(0) - motionEvent.getY(1)), (double) (motionEvent.getX(0) - motionEvent.getX(1))));
    }
}
