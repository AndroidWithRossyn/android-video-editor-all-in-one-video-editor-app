package com.androworld.allinonevideoeditor.phototovideo.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.androworld.allinonevideoeditor.phototovideo.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssetsDataBaseHelper extends SQLiteOpenHelper {
    private static String a = "imgdata.sqlite";
    private static String b = "imgInfo";
    private String c = "";
    private Context d;
    private SQLiteDatabase myDataBase;

    @Override
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        //add
    }

    public AssetsDataBaseHelper(Context context) throws IOException {
        super(context, a, null, 1);
        this.d = context;
        StringBuilder sb = new StringBuilder();
        sb.append("/data/data/");
        sb.append(this.d.getApplicationContext().getPackageName());
        sb.append("/databases/");
        this.c = sb.toString();
        if (a()) {
            opendatabase();
        } else {
            createdatabase();
        }
    }

    public void createdatabase() throws IOException {
        if (!a()) {
            getReadableDatabase();
            try {
                b();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean a() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(this.c);
            sb.append(a);
            return new File(sb.toString()).exists();
        } catch (SQLiteException unused) {
            return false;
        }
    }

    private void b() throws IOException {
        InputStream open = this.d.getAssets().open(a);
        StringBuilder sb = new StringBuilder();
        sb.append(this.c);
        sb.append(a);
        FileOutputStream fileOutputStream = new FileOutputStream(sb.toString());
        byte[] bArr = new byte[1024];
        while (true) {
            int read = open.read(bArr);
            if (read <= 0) {
                fileOutputStream.flush();
                fileOutputStream.close();
                open.close();
                return;
            }
            fileOutputStream.write(bArr, 0, read);
        }
    }

    public void opendatabase()  {
        StringBuilder sb = new StringBuilder();
        sb.append(this.c);
        sb.append(a);
        this.myDataBase = SQLiteDatabase.openDatabase(sb.toString(), null, 0);
    }

    @Override
    public synchronized void close() {
        if (this.myDataBase != null) {
            this.myDataBase.close();
        }
        super.close();
    }

    @Override public void onCreate(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(b);
        sb.append("(Name VARCHAR)");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public void addImageDetail(String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", str);
        writableDatabase.insert(b, null, contentValues);
        writableDatabase.close();
    }

    public void getAllImageDetail() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  * FROM ");
        sb.append(b);
        String sb2 = sb.toString();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor rawQuery = writableDatabase.rawQuery(sb2, null);
        if (rawQuery.moveToFirst()) {
            do {
                Utils.createImageList.add(rawQuery.getString(0));
            } while (rawQuery.moveToNext());
        }
        writableDatabase.close();
    }

    public void deleteAll() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(b, null, null);
        writableDatabase.close();
    }
}
