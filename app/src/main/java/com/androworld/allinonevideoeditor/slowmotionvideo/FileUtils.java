package com.androworld.allinonevideoeditor.slowmotionvideo;

import android.content.Context;
import android.os.Environment;

import com.androworld.allinonevideoeditor.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    static class a implements FilenameFilter {
        private final String a;

        a(String str) {
            this.a = str;
        }

        public boolean accept(File file, String str) {
            return str != null && str.startsWith(this.a.substring(0, this.a.lastIndexOf("."))) && str.endsWith(this.a.substring(this.a.lastIndexOf(".")));
        }
    }

    public static String getTargetFileName(Context context, String str) {
        String name = new File(str).getAbsoluteFile().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(context.getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(context.getResources().getString(R.string.SlowMotionVideo));
        File absoluteFile = new File(sb.toString()).getAbsoluteFile();
        if (!absoluteFile.isDirectory()) {
            absoluteFile.mkdirs();
        }
        List asList = Arrays.asList(absoluteFile.list(new a(name)));
        int i = 0;
        while (true) {
            StringBuilder sb2 = new StringBuilder("slow-");
            int i2 = i + 1;
            sb2.append(String.format("%d", new Object[]{i}));
            sb2.append(name.substring(name.lastIndexOf(".")));
            String sb3 = sb2.toString();
            if (!asList.contains(sb3)) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                sb4.append("/");
                sb4.append(context.getResources().getString(R.string.MainFolderName));
                sb4.append("/");
                sb4.append(context.getResources().getString(R.string.SlowMotionVideo));
                return new File(sb4.toString(), sb3).getPath();
            }
            i = i2;
        }
    }
}
