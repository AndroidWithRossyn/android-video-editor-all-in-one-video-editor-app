package com.androworld.allinonevideoeditor.audiocutter.cutter;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.Random;

public class SeekTest {
    public static final String PREF_SEEK_TEST_DATE = "seek_test_date";
    public static final String PREF_SEEK_TEST_RESULT = "seek_test_result";
    static long a;
    static long b;
    private static byte[] c = {-1, -5, 16, -60, 0, 3, -127, -12, 1, 38, 96, 0, 64, 32, 89, Byte.MIN_VALUE, 35, 72, 0, 9, 116, 0, 1, 18, 3, -1, -1, -1, -1, -2, -97, 99, -65, -47, 122, 63, 93, 1, -1, -1, -1, -1, -2, -115, -83, 108, 49, 66, -61, 2, -57, 12, 9, -122, -125, -88, 122, 58, 104, 76, 65, 77, 69, 51, 46, 57, 56, 46, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static boolean CanSeekAccurately(SharedPreferences sharedPreferences) {
        boolean z;
        SharedPreferences sharedPreferences2 = sharedPreferences;
        Log.i("Ringdroid", "Running CanSeekAccurately");
        boolean z2 = sharedPreferences2.getBoolean(PREF_SEEK_TEST_RESULT, false);
        long j = sharedPreferences2.getLong(PREF_SEEK_TEST_DATE, 0);
        long time = new Date().getTime();
        if (time - j < 604800000) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fast MP3 seek result cached: ");
            sb.append(z2);
            Log.i("Ringdroid", sb.toString());
            return z2;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("/sdcard/silence");
        sb2.append(new Random().nextLong());
        sb2.append(".mp3");
        String sb3 = sb2.toString();
        File file = new File(sb3);
        try {
            new RandomAccessFile(file, "r");
            z = false;
        } catch (Exception unused) {
            z = true;
        }
        if (!z) {
            Log.i("Ringdroid", "Couldn't find temporary filename");
            return false;
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append("Writing ");
        sb4.append(sb3);
        Log.i("Ringdroid", sb4.toString());
        try {
            file.createNewFile();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                for (int i = 0; i < 80; i++) {
                    fileOutputStream.write(c, 0, c.length);
                }
                try {
                    Log.i("Ringdroid", "File written, starting to play");
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(3);
                    long length = (long) (70 * c.length);
                    mediaPlayer.setDataSource(new FileInputStream(sb3).getFD(), length, (long) (10 * c.length));
                    Log.i("Ringdroid", "Preparing");
                    mediaPlayer.prepare();
                    a = 0;
                    b = 0;
                    mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                        public synchronized void onCompletion(MediaPlayer mediaPlayer) {
                            Log.i("Ringdroid", "Got callback");
                            SeekTest.b = System.currentTimeMillis();
                        }
                    });
                    Log.i("Ringdroid", "Starting");
                    mediaPlayer.start();
                    for (int i2 = 0; i2 < 200 && a == 0; i2++) {
                        if (mediaPlayer.getCurrentPosition() > 0) {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append("Started playing after ");
                            sb5.append(i2 * 10);
                            sb5.append(" ms");
                            Log.i("Ringdroid", sb5.toString());
                            a = System.currentTimeMillis();
                        }
                        Thread.sleep(10);
                    }
                    if (a == 0) {
                        Log.i("Ringdroid", "Never started playing.");
                        Log.i("Ringdroid", "Fast MP3 seek disabled by default");
                        file.delete();
                        Editor edit = sharedPreferences.edit();
                        edit.putLong(PREF_SEEK_TEST_DATE, time);
                        edit.putBoolean(PREF_SEEK_TEST_RESULT, z2);
                        edit.commit();
                        return false;
                    }
                    Log.i("Ringdroid", "Sleeping");
                    for (int i3 = 0; i3 < 300 && b == 0; i3++) {
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("Pos: ");
                        sb6.append(mediaPlayer.getCurrentPosition());
                        Log.i("Ringdroid", sb6.toString());
                        Thread.sleep(10);
                    }
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append("Result: ");
                    sb7.append(a);
                    sb7.append(", ");
                    sb7.append(b);
                    Log.i("Ringdroid", sb7.toString());
                    if (b <= a || b >= a + 2000) {
                        Log.i("Ringdroid", "Fast MP3 seek disabled");
                    } else {
                        long j2 = b > a ? b - a : -1;
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("Fast MP3 seek enabled: ");
                        sb8.append(j2);
                        Log.i("Ringdroid", sb8.toString());
                        z2 = true;
                    }
                    Editor edit2 = sharedPreferences.edit();
                    edit2.putLong(PREF_SEEK_TEST_DATE, time);
                    edit2.putBoolean(PREF_SEEK_TEST_RESULT, z2);
                    edit2.commit();
                    try {
                        file.delete();
                    } catch (Exception unused2) {
                    }
                    return z2;
                } catch (Exception e) {
                    Exception exc = e;
                    exc.printStackTrace();
                    StringBuilder sb9 = new StringBuilder();
                    sb9.append("Couldn't play: ");
                    sb9.append(exc.toString());
                    Log.i("Ringdroid", sb9.toString());
                    Log.i("Ringdroid", "Fast MP3 seek disabled by default");
                    try {
                        file.delete();
                    } catch (Exception unused3) {
                    }
                    Editor edit3 = sharedPreferences.edit();
                    edit3.putLong(PREF_SEEK_TEST_DATE, time);
                    edit3.putBoolean(PREF_SEEK_TEST_RESULT, z2);
                    edit3.commit();
                    return false;
                }
            } catch (Exception unused4) {
                Log.i("Ringdroid", "Couldn't write temp silence file");
                try {
                    file.delete();
                } catch (Exception unused5) {
                }
                return false;
            }
        } catch (Exception unused6) {
            Log.i("Ringdroid", "Couldn't output for writing");
            return false;
        }
    }
}
