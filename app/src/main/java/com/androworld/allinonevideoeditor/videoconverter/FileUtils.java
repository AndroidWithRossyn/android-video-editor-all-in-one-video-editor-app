package com.androworld.allinonevideoeditor.videoconverter;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.androworld.allinonevideoeditor.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class FileUtils {
    public static String getExtension(String str) {
        if (str == null) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf(".");
        return lastIndexOf >= 0 ? str.substring(lastIndexOf).toLowerCase() : "";
    }

    public static String getTargetFileName(Context context, String str) {
        final String name = new File(str).getAbsoluteFile().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(context.getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(context.getResources().getString(R.string.VideoConverter));
        List asList = Arrays.asList(new File(sb.toString()).getAbsoluteFile().list(new FilenameFilter() {
            public boolean accept(File file, String str) {
                if (str != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(VideoConverteractivity.outputformat);
                    sb.append("-");
                    if (str.startsWith(sb.toString())) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(name.substring(0, name.lastIndexOf(".")));
                        sb2.append(".");
                        sb2.append(VideoConverteractivity.outputformat);
                        if (str.endsWith(sb2.toString())) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }));
        int i = 0;
        while (true) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(VideoConverteractivity.outputformat);
            sb2.append("-");
            int i2 = i + 1;
            sb2.append(String.format("%03d", new Object[]{Integer.valueOf(i)}));
            sb2.append("-");
            sb2.append(str.substring(str.lastIndexOf("/") + 1, str.lastIndexOf(".")));
            sb2.append(".");
            sb2.append(VideoConverteractivity.outputformat);
            String sb3 = sb2.toString();
            if (!asList.contains(sb3)) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                sb4.append("/");
                sb4.append(context.getResources().getString(R.string.MainFolderName));
                sb4.append("/");
                sb4.append(context.getResources().getString(R.string.VideoConverter));
                return new File(sb4.toString(), sb3).getPath();
            }
            i = i2;
        }
    }

    public static Uri getUri(File file) {
        if (file != null) {
            return Uri.fromFile(file);
        }
        return null;
    }
}
