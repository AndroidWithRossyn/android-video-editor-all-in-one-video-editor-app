package com.androworld.allinonevideoeditor.videocollage.stickers;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout.LayoutParams;

import com.androworld.allinonevideoeditor.videocollage.utils.Utils;

class a implements OnTouchListener {
    int a;
    int b;
    int c;
    int d;
    int e;
    int f;
    LayoutParams g;
    LayoutParams h;
    Point i;
    LayoutParams j;
    private View k;
    private View l;

    a(View view, View view2) {
        this.k = view;
        this.l = view2;
    }

    @SuppressLint({"WrongConstant"})
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            for (int i2 = 0; i2 < Utils.clgstickerviewsList.size(); i2++) {
                try {
                    ((StickerData) Utils.clgstickerviewsList.get(i2)).singlefview.hidePushView();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            ClgTagView clgTagView = (ClgTagView) ((ClgSingleFingerView) view.getParent().getParent()).getTag();
            Utils.selectedPos = clgTagView.getPos();
            Utils.selectedText = clgTagView.getText();
            this.k.setVisibility(0);
            this.l.setVisibility(0);
            if (this.j == null) {
                try {
                    this.j = (LayoutParams) view.getLayoutParams();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            if (this.h == null) {
                try {
                    this.h = (LayoutParams) this.k.getLayoutParams();
                    this.g = (LayoutParams) this.l.getLayoutParams();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
            this.i = a(motionEvent);
            this.f = this.j.leftMargin;
            this.e = this.j.topMargin;
            this.d = this.h.leftMargin;
            this.c = this.h.topMargin;
            this.b = this.g.leftMargin;
            this.a = this.g.topMargin;
        } else if (action == 2) {
            Point a2 = a(motionEvent);
            float f2 = a2.a - this.i.a;
            float f3 = a2.b - this.i.b;
            this.j.leftMargin = (int) (((float) this.f) + f2);
            this.j.topMargin = (int) (((float) this.e) + f3);
            view.setLayoutParams(this.j);
            this.h.leftMargin = (int) (((float) this.d) + f2);
            this.h.topMargin = (int) (((float) this.c) + f3);
            this.k.setLayoutParams(this.h);
            this.g.leftMargin = (int) (((float) this.b) + f2);
            this.g.topMargin = (int) (((float) this.a) + f3);
            this.l.setLayoutParams(this.g);
        }
        return false;
    }

    private Point a(MotionEvent motionEvent) {
        return new Point((float) ((int) motionEvent.getRawX()), (float) ((int) motionEvent.getRawY()));
    }
}
