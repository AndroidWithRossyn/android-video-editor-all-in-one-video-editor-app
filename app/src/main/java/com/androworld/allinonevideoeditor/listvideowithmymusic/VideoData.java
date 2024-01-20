package com.androworld.allinonevideoeditor.listvideowithmymusic;

import android.net.Uri;

public class VideoData {
    public String Duration;
    public Uri VideoUri;
    public long videoId;
    public String videoName;
    public String videoPath;

    public VideoData(String str, Uri uri, String str2, String str3) {
        this.videoName = str;
        this.VideoUri = uri;
        this.videoPath = str2;
        this.Duration = str3;
    }

    public VideoData(String str, Uri uri, String str2) {
        this.videoName = str;
        this.VideoUri = uri;
        this.videoPath = str2;
    }
}
