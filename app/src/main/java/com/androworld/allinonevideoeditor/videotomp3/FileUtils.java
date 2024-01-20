package com.androworld.allinonevideoeditor.videotomp3;

import android.content.Context;
import android.os.Environment;

import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.videoconverter.VideoConverteractivity;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class FileUtils {
    public static int Bitrate;

    static class a implements FilenameFilter {
        private final String a;

        a(String str) {
            this.a = str;
        }

        public boolean accept(File file, String str) {
            if (str != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(VideoConverteractivity.outputformat);
                sb.append("-");
                if (str.startsWith(sb.toString())) {
                    StringBuilder sb2 = new StringBuilder(String.valueOf(this.a.substring(0, this.a.lastIndexOf("."))));
                    sb2.append(".");
                    sb2.append(VideoConverteractivity.outputformat);
                    if (str.endsWith(sb2.toString())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static String getTargetFileName(Context context, String str) {
        int i;
        Exception e;
        String name = new File(str).getAbsoluteFile().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(context.getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(context.getResources().getString(R.string.VideoToMP3));
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
                            sb5.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                            sb5.append("/");
                            sb5.append(context.getResources().getString(R.string.MainFolderName));
                            sb5.append("/");
                            sb5.append(context.getResources().getString(R.string.VideoToMP3));
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
