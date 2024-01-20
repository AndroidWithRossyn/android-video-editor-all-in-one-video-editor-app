package com.androworld.allinonevideoeditor;

public class VideoPlayerState {
    private int a = 0;
    private String b;
    private String c;
    private int d = 0;
    private int e = 0;

    public String getMessageText() {
        return this.c;
    }

    public void setMessageText(String str) {
        this.c = str;
    }

    public String getFilename() {
        return this.b;
    }

    public void setFilename(String str) {
        this.b = str;
    }

    public int getStart() {
        return this.d;
    }

    public void setStart(int i) {
        this.d = i;
    }

    public int getStop() {
        return this.e;
    }

    public void setStop(int i) {
        this.e = i;
    }

    public void reset() {
        this.e = 0;
        this.d = 0;
    }

    public int getDuration() {
        return this.e - this.d;
    }

    public int getCurrentTime() {
        return this.a;
    }

    public void setCurrentTime(int i) {
        this.a = i;
    }

    public boolean isValid() {
        return this.e > this.d;
    }
}
