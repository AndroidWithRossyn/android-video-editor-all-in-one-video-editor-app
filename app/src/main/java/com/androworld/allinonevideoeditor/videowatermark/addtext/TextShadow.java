package com.androworld.allinonevideoeditor.videowatermark.addtext;

public class TextShadow {
    int a;
    int b;
    int c;

    public TextShadow(int i, int i2, int i3) {
        this.b = i;
        this.a = i2;
        this.c = i3;
    }

    public int getRadius() {
        return this.b;
    }

    public void setRadius(int i) {
        this.b = i;
    }

    public int getLeft() {
        return this.a;
    }

    public void setLeft(int i) {
        this.a = i;
    }

    public int getRight() {
        return this.c;
    }

    public void setRight(int i) {
        this.c = i;
    }
}
