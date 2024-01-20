package com.androworld.allinonevideoeditor.audiovideomixer;

import android.app.Application;

public class AddAudio extends Application {
    public static String audioName;
    public static String audioPath;
    public static int status;

    public static String getAudioName() {
        return audioName;
    }

    public static void setAudioName(String audioName) {
        AddAudio.audioName = audioName;
    }

    public static String getAudioPath() {
        return audioPath;
    }

    public static void setAudioPath(String audioPath) {
        AddAudio.audioPath = audioPath;
    }

    public static int getStatus() {
        return status;
    }

    public static void setStatus(int status) {
        AddAudio.status = status;
    }
}
