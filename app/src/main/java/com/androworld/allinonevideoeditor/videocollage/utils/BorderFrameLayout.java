package com.androworld.allinonevideoeditor.videocollage.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.androworld.allinonevideoeditor.videocollage.model.BorderAttribute;

public class BorderFrameLayout extends FrameLayout {
    Paint a;
    Paint b = new Paint();
    int c;

    public BorderFrameLayout(Context context) {
        super(context);
        this.b.setColor(-7829368);
        this.b.setStyle(Style.STROKE);
        this.b.setStrokeWidth(10.0f);
        this.b.setAlpha(255);
        this.a = new Paint();
        this.a.setColor(-7829368);
        this.a.setStyle(Style.STROKE);
        this.a.setStrokeWidth(10.0f);
        this.a.setAlpha(255);
    }

    public BorderFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.b.setColor(-7829368);
        this.b.setStyle(Style.STROKE);
        this.b.setStrokeWidth(10.0f);
        this.b.setAlpha(255);
        this.a = new Paint();
        this.a.setColor(-7829368);
        this.a.setStyle(Style.STROKE);
        this.a.setStrokeWidth(10.0f);
        this.a.setAlpha(255);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int parseInt = Integer.parseInt(String.valueOf(getTag()));
        if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderTop()) {
            try {
                this.a.setStrokeWidth((float) (this.c / 2));
                if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderLeft() && ((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderRight()) {
                        canvas.drawLine((float) (this.c / 4), 1.0f, (float) (getWidth() - (this.c / 4)), 0.0f, this.a);
                } else if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderLeft()) {
                        canvas.drawLine((float) (this.c / 4), 1.0f, (float) (getWidth() - (this.c / 2)), 0.0f, this.a);
                } else if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderRight()) {
                        canvas.drawLine((float) (this.c / 2), 1.0f, (float) (getWidth() - (this.c / 4)), 0.0f, this.a);
                } else {
                        canvas.drawLine((float) (this.c / 2), 1.0f, (float) (getWidth() - (this.c / 2)), 0.0f, this.a);
                }
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        } else if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderLeft() && ((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderRight()) {
            try {
                canvas.drawLine((float) (this.c / 4), 1.0f, (float) (getWidth() - (this.c / 4)), 0.0f, this.b);
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        } else if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderLeft()) {
            try {
                canvas.drawLine((float) (this.c / 4), 1.0f, (float) (getWidth() - (this.c / 2)), 0.0f, this.b);
            } catch (Exception e7) {
                e7.printStackTrace();
            }
        } else if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderRight()) {
            try {
                canvas.drawLine((float) (this.c / 2), 1.0f, (float) (getWidth() - (this.c / 4)), 0.0f, this.b);
            } catch (Exception e8) {
                e8.printStackTrace();
            }
        } else {
            try {
                canvas.drawLine((float) (this.c / 2), 1.0f, (float) (getWidth() - (this.c / 2)), 0.0f, this.b);
            } catch (Exception e9) {
                e9.printStackTrace();
            }
        }
        if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderLeft()) {
            try {
                this.a.setStrokeWidth((float) (this.c / 2));
                canvas.drawLine(1.0f, 0.0f, 0.0f, (float) getHeight(), this.a);
            } catch (Exception e10) {
                e10.printStackTrace();
            }
        } else {
            try {
                canvas.drawLine(1.0f, 0.0f, 0.0f, (float) getHeight(), this.b);
            } catch (Exception e11) {
                e11.printStackTrace();
            }
        }
        if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderRight()) {
            try {
                this.a.setStrokeWidth((float) (this.c / 2));
                canvas.drawLine((float) (getWidth() - 1), 0.0f, (float) (getWidth() - 1), (float) getHeight(), this.a);
            } catch (Exception e12) {
                e12.printStackTrace();
            }
        } else {
            try {
                canvas.drawLine((float) (getWidth() - 1), 0.0f, (float) (getWidth() - 1), (float) getHeight(), this.b);
            } catch (Exception e13) {
                e13.printStackTrace();
            }
        }
        if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderBottom()) {
            try {
                this.a.setStrokeWidth((float) (this.c / 2));
                if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderLeft() && ((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderRight()) {
                        canvas.drawLine((float) (this.c / 4), (float) (getHeight() - 1), (float) (getWidth() - (this.c / 4)), (float) (getHeight() - 1), this.a);
                } else if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderLeft()) {
                        canvas.drawLine((float) (this.c / 4), (float) (getHeight() - 1), (float) (getWidth() - (this.c / 2)), (float) (getHeight() - 1), this.a);
                } else if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderRight()) {
                        canvas.drawLine((float) (this.c / 2), (float) (getHeight() - 1), (float) (getWidth() - (this.c / 4)), (float) (getHeight() - 1), this.a);
                } else {
                        canvas.drawLine((float) (this.c / 2), (float) (getHeight() - 1), (float) (getWidth() - (this.c / 2)), (float) (getHeight() - 1), this.a);
                }
            } catch (Exception e18) {
                e18.printStackTrace();
            }
        } else if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderLeft() && ((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderRight()) {
            try {
                canvas.drawLine((float) (this.c / 4), (float) (getHeight() - 1), (float) (getWidth() - (this.c / 4)), (float) (getHeight() - 1), this.b);
            } catch (Exception e19) {
                e19.printStackTrace();
            }
        } else if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderLeft()) {
            try {
                canvas.drawLine((float) (this.c / 4), (float) (getHeight() - 1), (float) (getWidth() - (this.c / 2)), (float) (getHeight() - 1), this.b);
            } catch (Exception e20) {
                e20.printStackTrace();
            }
        } else if (((BorderAttribute) Utils.borderParam.get(parseInt)).getBorderRight()) {
            try {
                canvas.drawLine((float) (this.c / 2), (float) (getHeight() - 1), (float) (getWidth() - (this.c / 4)), (float) (getHeight() - 1), this.b);
            } catch (Exception e21) {
                e21.printStackTrace();
            }
        } else {
            try {
                canvas.drawLine((float) (this.c / 2), (float) (getHeight() - 1), (float) (getWidth() - (this.c / 2)), (float) (getHeight() - 1), this.b);
            } catch (Exception e22) {
                e22.printStackTrace();
            }
        }
    }

    public void setColor(int i) {
        this.b.setColor(i);
        this.a.setColor(i);
        invalidate();
    }

    public int getColor() {
        return this.b.getColor();
    }

    public void setColorAlpha(int i) {
        this.b.setAlpha(i);
        this.a.setAlpha(i);
        invalidate();
    }

    public int getColorAlpha() {
        return this.b.getAlpha();
    }

    public void setStrokeWidth(int i) {
        this.c = i;
        float f = (float) i;
        this.b.setStrokeWidth(f);
        this.a.setStrokeWidth(f);
        invalidate();
    }

    public int getStrokeWidth() {
        return (int) this.b.getStrokeWidth();
    }
}
