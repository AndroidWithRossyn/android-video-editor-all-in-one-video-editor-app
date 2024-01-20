package com.androworld.allinonevideoeditor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.ParseException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class MusicAdapter extends SimpleCursorAdapter {
    static Context a;
    public static int mills;
    int b;
    private Cursor c;
    private final LayoutInflater d;
    private int e;

    public MusicAdapter(Context context, int i, Cursor cursor, String[] strArr, int[] iArr) {
        super(context, i, cursor, strArr, iArr);
        this.e = i;
        a = context;
        this.d = LayoutInflater.from(context);
        this.c = cursor;
    }

    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.d.inflate(this.e, null);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        this.b++;
        TextView textView = (TextView) view.findViewById(R.id.row_album);
        TextView textView2 = (TextView) view.findViewById(R.id.row_artist);
        TextView textView3 = (TextView) view.findViewById(R.id.row_title);
        TextView textView4 = (TextView) view.findViewById(R.id.row_Size);
        TextView textView5 = (TextView) view.findViewById(R.id.row_Duration);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.rowLayout);
        int columnIndexOrThrow = cursor.getColumnIndexOrThrow("artist");
        int columnIndexOrThrow2 = cursor.getColumnIndexOrThrow("album");
        FileUtils.Title_index = cursor.getColumnIndexOrThrow("title");
        int columnIndexOrThrow3 = cursor.getColumnIndexOrThrow("duration");
        int columnIndexOrThrow4 = cursor.getColumnIndexOrThrow("_size");
        if (cursor.getString(columnIndexOrThrow3) != null) {
            try {
                mills = Integer.parseInt(cursor.getString(columnIndexOrThrow3));
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
        }
        textView5.setText(formatTimeUnit((long) mills));
        float round = round((((float) Integer.parseInt(cursor.getString(columnIndexOrThrow4))) / 1024.0f) / 1024.0f, 2);
        textView.setText(cursor.getString(columnIndexOrThrow2));
        textView2.setText(cursor.getString(columnIndexOrThrow));
        textView3.setText(cursor.getString(FileUtils.Title_index));
        StringBuilder sb = new StringBuilder();
        sb.append("Size - ");
        sb.append(round);
        sb.append(" MB");
        textView4.setText(sb.toString());
    }

    public static float round(float f, int i) {
        return new BigDecimal(Float.toString(f)).setScale(i, 4).floatValue();
    }

    @SuppressLint({"NewApi"})
    public static String formatTimeUnit(long j) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j)))});
    }
}
