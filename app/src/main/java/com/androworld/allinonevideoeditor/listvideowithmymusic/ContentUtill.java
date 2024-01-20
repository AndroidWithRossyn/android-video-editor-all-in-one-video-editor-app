package com.androworld.allinonevideoeditor.listvideowithmymusic;

import android.database.Cursor;

public class ContentUtill {
    public static String getLong(Cursor cursor) {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(cursor.getLong(cursor.getColumnIndexOrThrow("_id")));
        return sb.toString();
    }

    public static String getTime(Cursor cursor, String str) {
        return TimeUtils.toFormattedTime(getInt(cursor, str));
    }

    public static int getInt(Cursor cursor, String str) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(str));
    }
}
