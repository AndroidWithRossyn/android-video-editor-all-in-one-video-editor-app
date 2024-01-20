package com.androworld.allinonevideoeditor.audiocutter.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class FileUtils {
    public static Context c;

    static class a implements FilenameFilter {
        private final String a;

        a(String str) {
            this.a = str;
        }

        public boolean accept(File file, String str) {
            if (str == null || !str.startsWith("mp3-")) {
                return false;
            }
            StringBuilder sb = new StringBuilder(String.valueOf(this.a.substring(0, this.a.lastIndexOf("."))));
            sb.append(".mp3");
            return str.endsWith(sb.toString());
        }
    }

    public static String getTargetFileName(String str) {
        int i;
        Exception e;
        String name = new File(str).getAbsoluteFile().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/VideoCutter");
        List asList = Arrays.asList(new File(sb.toString()).getAbsoluteFile().list(new a(name)));
        int i2 = 0;
        while (true) {
            try {
                StringBuilder sb2 = new StringBuilder("mp3-");
                int i3 = i2 + 1;
                sb2.append(String.format("%03d", new Object[]{Integer.valueOf(i2)}));
                sb2.append("-");
                sb2.append(name);
                String sb3 = sb2.toString();
                i = i3 - 1;
                try {
                    StringBuilder sb4 = new StringBuilder("mp3-");
                    int i4 = i + 1;
                    sb4.append(String.format("%03d", new Object[]{Integer.valueOf(i)}));
                    sb4.append("-");
                    sb4.append(name.substring(0, name.lastIndexOf(".")));
                    sb4.append(".mp3");
                    if (!asList.contains(sb4.toString())) {
                        try {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(Environment.getExternalStorageDirectory());
                            sb5.append("/VideoCutter");
                            return new File(sb5.toString(), sb3).getPath();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    i2 = i4;
                } catch (Exception e3) {
                    e = e3;
                    e.printStackTrace();
                    i2 = i;
                }
            } catch (Exception e4) {
                i = i2;
                e = e4;
                e.printStackTrace();
                i2 = i;
            }
        }
    }
}
