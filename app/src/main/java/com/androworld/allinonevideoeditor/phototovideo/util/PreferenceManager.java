package com.androworld.allinonevideoeditor.phototovideo.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.core.view.ViewCompat;

import java.io.File;

public class PreferenceManager extends Application {
    static Editor a;
    static SharedPreferences b;
    private static PreferenceManager c;

    @Override public void onCreate() {
        super.onCreate();
        c = this;
        b = getSharedPreferences("videomaker", 0);
        a = b.edit();
        a.commit();
    }

    @Override public void onTerminate() {
        super.onTerminate();
        trimCache(getApplicationContext());
    }

    public static void setBackgroundColor(int i) {
        a.putInt("backgrondcolor", i);
        a.commit();
    }

    public static void setLibraryFlag(boolean z) {
        a.putBoolean("libFlag", z);
        a.commit();
    }

    public static void setExceptionFlag(boolean z) {
        a.putBoolean("exception", z);
        a.commit();
    }

    public static int getBackColor() {
        return b.getInt("backgrondcolor", ViewCompat.MEASURED_STATE_MASK);
    }

    public static boolean getLibStatus() {
        return b.getBoolean("libFlag", true);
    }

    public static boolean getExceptionFlag() {
        return b.getBoolean("exception", false);
    }

    public static void setMusicExtension(String str) {
        a.putString("musicextension", str);
        a.commit();
    }

    public static String getMusicExtension() {
        return b.getString("musicextension", "");
    }

    public static boolean getShowcaseFlag() {
        return b.getBoolean("showcase", false);
    }

    public static void setisMusic(Boolean bool) {
        a.putBoolean("ismusic", bool.booleanValue());
        a.commit();
    }

    public static void setShowcaseFlag(Boolean bool) {
        a.putBoolean("showcase", bool.booleanValue());
        a.commit();
    }

    public static Boolean getisMusic() {
        return Boolean.valueOf(b.getBoolean("ismusic", false));
    }

    public static void setCounter(int i) {
        a.putInt("counter", i);
        a.commit();
    }

    public static int getCounter() {
        return b.getInt("counter", 0);
    }

    public static void setCropIndex(int i) {
        a.putInt("cropindex", i);
        a.commit();
    }

    public static int getCropIndex() {
        return b.getInt("cropindex", 0);
    }

    public static void setIndexId(int i) {
        a.putInt("indexid", i);
        a.commit();
    }

    public static void setRegistered(boolean z) {
        a.putBoolean("isRegistered", z);
        a.commit();
    }

    public static int getIndexId() {
        return b.getInt("indexid", 0);
    }

    public static boolean getRegisteredStatus() {
        return b.getBoolean("isRegistered", false);
    }

    public static void trimCache(Context context) {
        try {
            File cacheDir = context.getCacheDir();
            if (cacheDir != null && cacheDir.isDirectory()) {
                deleteDir(cacheDir);
            }
        } catch (Exception unused) {
        }
    }

    public static boolean deleteDir(File file) {
        if (file != null && file.isDirectory()) {
            for (String file2 : file.list()) {
                if (!deleteDir(new File(file, file2))) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static synchronized PreferenceManager getInstance() {
        PreferenceManager preferenceManager;
        synchronized (PreferenceManager.class) {
            synchronized (PreferenceManager.class) {
                preferenceManager = c;
            }
        }
        return preferenceManager;
    }
}
