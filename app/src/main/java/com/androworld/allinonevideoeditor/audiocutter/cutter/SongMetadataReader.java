package com.androworld.allinonevideoeditor.audiocutter.cutter;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Genres;
import android.provider.MediaStore.Audio.Media;

import java.util.HashMap;
import java.util.Iterator;

public class SongMetadataReader {
    public Uri GENRES_URI = Genres.EXTERNAL_CONTENT_URI;
    public Activity mActivity = null;
    public String mAlbum = "";
    public String mArtist = "";
    public String mFilename = "";
    public String mGenre = "";
    public String mTitle = "";
    public int mYear = -1;

    public SongMetadataReader(Activity activity, String str) {
        this.mActivity = activity;
        this.mFilename = str;
        this.mTitle = b(str);
        try {
            a();
        } catch (Exception unused) {
        }
    }

    private void a() {
        String str = "name";
        HashMap hashMap = new HashMap();
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME
        };
        Cursor managedQuery = this.mActivity.managedQuery(this.GENRES_URI, projection, null, null, null);
        managedQuery.moveToFirst();
        while (!managedQuery.isAfterLast()) {
            hashMap.put(managedQuery.getString(0), managedQuery.getString(1));
            managedQuery.moveToNext();
        }
        this.mGenre = "";
        Iterator it = hashMap.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String str2 = (String) it.next();
            Activity activity = this.mActivity;
            Uri a = a(str2);
            String[] strArr = {"_data"};
            StringBuilder sb = new StringBuilder();
            sb.append("_data LIKE \"");
            sb.append(this.mFilename);
            sb.append("\"");
            if (activity.managedQuery(a, strArr, sb.toString(), null, null).getCount() != 0) {
                this.mGenre = (String) hashMap.get(str2);
                break;
            }
        }
        Uri contentUriForPath = Media.getContentUriForPath(this.mFilename);
        Activity activity2 = this.mActivity;
        String[] strArr2 = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.YEAR,
                MediaStore.Audio.Media.DATA
        };
        StringBuilder sb2 = new StringBuilder();
        sb2.append("_data LIKE \"");
        sb2.append(this.mFilename);
        sb2.append("\"");
        Cursor managedQuery2 = activity2.managedQuery(contentUriForPath, strArr2, sb2.toString(), null, null);
        if (managedQuery2.getCount() == 0) {
            this.mTitle = b(this.mFilename);
            this.mArtist = "";
            this.mAlbum = "";
            this.mYear = -1;
            return;
        }
        managedQuery2.moveToFirst();
        this.mTitle = a(managedQuery2, "title");
        if (this.mTitle == null || this.mTitle.length() == 0) {
            this.mTitle = b(this.mFilename);
        }
        this.mArtist = a(managedQuery2, "artist");
        this.mAlbum = a(managedQuery2, "album");
        this.mYear = b(managedQuery2, "year");
    }

    private Uri a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.GENRES_URI.toString());
        sb.append("/");
        sb.append(str);
        sb.append("/");
        sb.append("members");
        return Uri.parse(sb.toString());
    }

    private String a(Cursor cursor, String str) {
        String string = cursor.getString(cursor.getColumnIndexOrThrow(str));
        if (string == null || string.length() <= 0) {
            return null;
        }
        return string;
    }

    private int b(Cursor cursor, String str) {
        Integer valueOf = Integer.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(str)));
        if (valueOf != null) {
            return valueOf.intValue();
        }
        return -1;
    }

    private String b(String str) {
        return str.substring(str.lastIndexOf(47) + 1, str.lastIndexOf(46));
    }
}
