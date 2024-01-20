package com.androworld.allinonevideoeditor.videowatermark.addtext;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class a {
    public static String a(File file, Bitmap bitmap) {
        if (bitmap == null) {
            Log.e("StickerView", "saveImageToGallery: the bitmap is null");
            return "";
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("saveImageToGallery: the path of bmp is ");
        sb.append(file.getAbsolutePath());
        Log.e("StickerView", sb.toString());
        return file.getAbsolutePath();
    }

    public static void a(Context context, File file) {
        if (file == null || !file.exists()) {
            Log.e("StickerView", "notifySystemGallery: the file do not exist.");
            return;
        }
        try {
            Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
    }

    public static RectF a(float[] fArr) {
        RectF rectF = new RectF(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        for (int i = 1; i < fArr.length; i += 2) {
            try {
                float round = ((float) Math.round(fArr[i - 1] * 10.0f)) / 10.0f;
                float round2 = ((float) Math.round(fArr[i] * 10.0f)) / 10.0f;
                rectF.left = round < rectF.left ? round : rectF.left;
                rectF.top = round2 < rectF.top ? round2 : rectF.top;
                if (round <= rectF.right) {
                    round = rectF.right;
                }
                rectF.right = round;
                if (round2 <= rectF.bottom) {
                    round2 = rectF.bottom;
                }
                rectF.bottom = round2;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rectF.sort();
        return rectF;
    }
}
