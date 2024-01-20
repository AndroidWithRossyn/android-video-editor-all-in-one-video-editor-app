package com.androworld.allinonevideoeditor.videocollage.stickers;

public class Point {
    float a;
    float b;

    public Point(float f, float f2) {
        this.a = f;
        this.b = f2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("x: ");
        sb.append(this.a);
        sb.append(",y: ");
        sb.append(this.b);
        return sb.toString();
    }
}
