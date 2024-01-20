package com.androworld.allinonevideoeditor.phototovideo.model;

import android.net.Uri;

public class AlbumImages {
    Integer imgId;
    int imgPos = -1;
    Uri imgUri;

    public AlbumImages(Uri uri, Integer num, int i) {
        this.imgUri = uri;
        this.imgId = num;
        this.imgPos = i;
    }

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public int getImgPos() {
        return imgPos;
    }

    public void setImgPos(int imgPos) {
        this.imgPos = imgPos;
    }

    public Uri getImgUri() {
        return imgUri;
    }

    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }
}
