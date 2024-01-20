package com.androworld.allinonevideoeditor.audiocutter.cutter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

public class MarkerView extends AppCompatImageView {
    private int a = 0;
    private MarkerListener b = null;

    public interface MarkerListener {
        void markerDraw();

        void markerEnter(MarkerView markerView);

        void markerFocus(MarkerView markerView);

        void markerKeyUp();

        void markerLeft(MarkerView markerView, int i);

        void markerRight(MarkerView markerView, int i);

        void markerTouchEnd(MarkerView markerView);

        void markerTouchMove(MarkerView markerView, float f);

        void markerTouchStart(MarkerView markerView, float f);
    }

    public MarkerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setFocusable(true);
    }

    public void setListener(MarkerListener markerListener) {
        this.b = markerListener;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                requestFocus();
                this.b.markerTouchStart(this, motionEvent.getRawX());
                break;
            case 1:
                this.b.markerTouchEnd(this);
                break;
            case 2:
                this.b.markerTouchMove(this, motionEvent.getRawX());
                break;
        }
        return true;
    }


    public void onFocusChanged(boolean z, int i, Rect rect) {
        if (z && this.b != null) {
            this.b.markerFocus(this);
        }
        super.onFocusChanged(z, i, rect);
    }


    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.b != null) {
            this.b.markerDraw();
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        this.a++;
        int sqrt = (int) Math.sqrt((double) ((this.a / 2) + 1));
        if (this.b != null) {
            if (i == 21) {
                this.b.markerLeft(this, sqrt);
                return true;
            } else if (i == 22) {
                this.b.markerRight(this, sqrt);
                return true;
            } else if (i == 23) {
                this.b.markerEnter(this);
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        this.a = 0;
        if (this.b != null) {
            this.b.markerKeyUp();
        }
        return super.onKeyDown(i, keyEvent);
    }
}
