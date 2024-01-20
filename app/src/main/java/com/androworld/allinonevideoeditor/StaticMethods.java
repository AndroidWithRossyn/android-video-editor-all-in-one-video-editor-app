package com.androworld.allinonevideoeditor;

import android.media.MediaMetadataRetriever;
import android.util.Log;

import java.io.File;
import java.text.DecimalFormat;

public class StaticMethods {
    public static boolean logAreOpen = true;

    public static String bitRate(String str) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str);
        String extractMetadata = mediaMetadataRetriever.extractMetadata(20);
        if (logAreOpen) {
            StringBuilder sb = new StringBuilder();
            sb.append("path of input video");
            sb.append(str);
            Log.e("StaticMethods", sb.toString());
        }
        if (logAreOpen) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("value of BitRate");
            sb2.append(extractMetadata);
            Log.e("StaticMethods", sb2.toString());
        }
        return extractMetadata;
    }

    public static long bitSet(long j, int i) {
        if (logAreOpen) {
            StringBuilder sb = new StringBuilder();
            sb.append("value of bit in bitSet is ");
            sb.append(j);
            Log.e("bitSet", sb.toString());
        }
        double d = i == 6 ? (double) (((float) j) * 0.75f) : i == 7 ? (double) (((float) j) * 0.5f) : i == 8 ? (double) (((float) j) * 0.25f) : (double) j;
        if (logAreOpen) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("value of bitFinal");
            sb2.append(d);
            Log.e("bitSet", sb2.toString());
        }
        return (long) d;
    }

    public static String sizeInMBbyFilepath(String str) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        long length = new File(str).length() / 1024;
        double d = ((double) length) / 1024.0d;
        if (length >= 1024) {
            StringBuilder sb = new StringBuilder();
            sb.append(decimalFormat.format(d));
            sb.append("MB");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(length);
        sb2.append("KB");
        return sb2.toString();
    }

    static String a(Long l) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        long longValue = l.longValue() / 1024;
        double d = ((double) longValue) / 1024.0d;
        if (longValue >= 1024) {
            StringBuilder sb = new StringBuilder();
            sb.append(decimalFormat.format(d));
            sb.append("MB");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(longValue);
        sb2.append("KB");
        return sb2.toString();
    }

    public static String ExpectedOutputSize(String str, int i, int i2) {
        return a(Long.valueOf(Long.valueOf(bitSet(Long.valueOf(Long.parseLong(bitRate(str))).longValue(), i2) / 8).longValue() * ((long) i)));
    }

    public static String SelectedCompressPercentage(String str, int i, int i2) {
        return String.valueOf(Math.round(Float.valueOf(100.0f - ((Float.valueOf((float) (Long.valueOf(bitSet(Long.valueOf(Long.parseLong(bitRate(str))).longValue(), i2) / 8).longValue() * ((long) i))).floatValue() / Float.valueOf((float) new File(str).length()).floatValue()) * 100.0f)).floatValue()));
    }

    public static String FormatofVideo(String str) {
        return str.trim().substring(str.trim().lastIndexOf(".") + 1, str.trim().length());
    }
}
