package com.androworld.allinonevideoeditor.videocollage.model;

public class CollageData {
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private Boolean h;
    private String i;
    private String j;
    private int k;
    private int l;

    public void setVideoUrl(String str) {
        this.j = str;
    }

    public String getVideoUrl() {
        return this.j;
    }

    public void setStartTime(String str) {
        this.i = str;
    }

    public String getStartTime() {
        return this.i;
    }

    public void setDurationTime(int i2) {
        this.g = i2;
    }

    public int getDurationTime() {
        return this.g;
    }

    public void setWidth(int i2) {
        this.b = i2;
    }

    public int getWidth() {
        return this.b;
    }

    public void setHeight(int i2) {
        this.a = i2;
    }

    public int getHeight() {
        return this.a;
    }

    public void setXPoint(int i2) {
        this.k = i2;
    }

    public int getXPoint() {
        return this.k;
    }

    public void setYPoint(int i2) {
        this.l = i2;
    }

    public int getYPoint() {
        return this.l;
    }

    public void setIsImage(Boolean bool) {
        this.h = bool;
    }

    public Boolean getIsImage() {
        return this.h;
    }

    public void setCrop_width(int i2) {
        this.d = i2;
    }

    public int getCrop_width() {
        return this.d;
    }

    public void setCrop_height(int i2) {
        this.c = i2;
    }

    public int getCrop_height() {
        return this.c;
    }

    public void setCrop_X(int i2) {
        this.e = i2;
    }

    public int getCrop_X() {
        return this.e;
    }

    public void setCrop_Y(int i2) {
        this.f = i2;
    }

    public int getCrop_Y() {
        return this.f;
    }
}
