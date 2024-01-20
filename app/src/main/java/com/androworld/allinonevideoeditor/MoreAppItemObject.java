package com.androworld.allinonevideoeditor;

public class MoreAppItemObject {
    private int a;
    private String b;
    private String c;

    public MoreAppItemObject(int i, String str, String str2) {
        this.b = str;
        this.a = i;
        this.c = str2;
    }

    public int getPhoto() {
        return this.a;
    }

    public void setPhoto(int i) {
        this.a = i;
    }

    public String getName() {
        return this.b;
    }

    public void setName(String str) {
        this.b = str;
    }

    public String getLink() {
        return this.c;
    }

    public void setLink(String str) {
        this.c = str;
    }
}
