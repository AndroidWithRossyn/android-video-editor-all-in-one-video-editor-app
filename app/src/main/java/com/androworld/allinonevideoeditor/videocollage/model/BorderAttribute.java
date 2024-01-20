package com.androworld.allinonevideoeditor.videocollage.model;

public class BorderAttribute {
    private boolean a = false;
    private boolean b = false;
    private boolean c = false;
    private boolean d = false;

    public void setBorderLeft(boolean z) {
        this.b = z;
    }

    public boolean getBorderLeft() {
        return this.b;
    }

    public void setBorderRight(boolean z) {
        this.c = z;
    }

    public boolean getBorderRight() {
        return this.c;
    }

    public void setBorderTop(boolean z) {
        this.d = z;
    }

    public boolean getBorderTop() {
        return this.d;
    }

    public void setBorderBottom(boolean z) {
        this.a = z;
    }

    public boolean getBorderBottom() {
        return this.a;
    }
}
