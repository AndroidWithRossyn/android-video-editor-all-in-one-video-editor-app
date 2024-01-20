package com.androworld.allinonevideoeditor.videowatermark.addtext;

public class StickerData {
    boolean a;
    String b;

    public StickerData(String str, boolean z) {
        this.b = str;
        this.a = z;
    }

    public String getName() {
        return this.b;
    }

    public void setName(String str) {
        this.b = str;
    }

    public boolean isSelected() {
        return this.a;
    }

    public void setSelected(boolean z) {
        this.a = z;
    }
}
