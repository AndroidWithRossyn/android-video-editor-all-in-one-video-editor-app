package com.androworld.allinonevideoeditor.videojoiner.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Environment;

import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.videojoiner.model.ImageSelect;
import com.androworld.allinonevideoeditor.videojoiner.model.SelectBucketImage;

import java.util.ArrayList;

public class FileUtils {
    public static Bitmap bitmap;
    public static ArrayList<String> createImageList = new ArrayList<>();
    public static int height;
    public static ArrayList<SelectBucketImage> imageUri = new ArrayList<>();
    public static int imgCount;
    public static ArrayList<String> myUri = new ArrayList<>();
    public static ArrayList<ImageSelect> selectImageList = new ArrayList<>();
    public static int width;

    public static String getPath(Context context) {
        StringBuilder sb = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getPath()));
        sb.append("/");
        sb.append(context.getResources().getString(R.string.app_name));
        return sb.toString();
    }

    public static String getLong(Cursor cursor) {
        return String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow("_id")));
    }
}
