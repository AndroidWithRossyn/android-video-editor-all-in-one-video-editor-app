package com.androworld.allinonevideoeditor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;

import com.androworld.allinonevideoeditor.audiovideomixer.AddAudio;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

@SuppressLint({"WrongConstant"})
public class SelectMusicActivity extends ListActivity {
    private static final String[] i;
    private static final String[] j;
    MusicAdapter a;
    ArrayList<String> b = new ArrayList<>();
    String[] c;
    Cursor d;
    ImageView e;
    SimpleCursorAdapter f;
    ArrayList<String> g = new ArrayList<>();
    String h;
    private boolean k;
    private AdView l;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(Media.EXTERNAL_CONTENT_URI);
        sb.append("\"");
        i = new String[]{"_id", "_data", "title", "artist", "album", "duration", "_size", "is_ringtone", "is_alarm", "is_notification", "is_music", sb.toString()};
        StringBuilder sb2 = new StringBuilder();
        sb2.append("\"");
        sb2.append(Media.INTERNAL_CONTENT_URI);
        sb2.append("\"");
        j = new String[]{"_id", "_data", "title", "artist", "album", "duration", "_size", "is_ringtone", "is_alarm", "is_notification", "is_music", sb2.toString()};
    }

    private Ads ads;

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.k = false;
        String externalStorageState = Environment.getExternalStorageState();
        if (externalStorageState.equals("mounted_ro")) {
            try {
                a(getResources().getText(R.string.sdcard_readonly));
            } catch (NotFoundException e2) {
                e2.printStackTrace();
            }
        } else if (externalStorageState.equals("shared")) {
            try {
                a(getResources().getText(R.string.sdcard_shared));
            } catch (NotFoundException e3) {
                e3.printStackTrace();
            }
        } else if (externalStorageState.equals("mounted")) {
            try {
                setContentView(R.layout.selectmusic);
                LinearLayout s = (LinearLayout) findViewById(R.id.banner_AdView);
                ads = new Ads();
                ads.BannerAd(SelectMusicActivity.this,s);

                this.e = (ImageView) findViewById(R.id.imageViewBack);
                this.e.setOnClickListener(new OnClickListener() {
                    @Override public void onClick(View view) {
                        SelectMusicActivity.this.finish();
                    }
                });
                try {
                    SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.selectmusic_row, ab(""), new String[]{"artist", "album", "title"}, new int[]{R.id.row_artist, R.id.row_album, R.id.row_title});
                    this.f = simpleCursorAdapter;
                    this.c = (String[]) this.b.toArray(new String[this.b.size()]);
                    int[] iArr = {R.id.row_artist, R.id.row_title, R.id.row_Duration};
                    MusicAdapter musicAdapter = new MusicAdapter(this, R.layout.selectmusic_row, ab(""), new String[]{"artist", "album", "title", "duration", "_size"}, iArr);
                    this.a = musicAdapter;
                    setListAdapter(this.a);
                    getListView().setOnItemClickListener(new OnItemClickListener() {
                        public void onItemClick(AdapterView adapterView, View view, int i, long j) {

                            Cursor cursor = a.getCursor();
                            String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                            try {
                                AddAudio.status = 1;
                                AddAudio.audioName = string;
                                AddAudio.audioPath = string;
                                SelectMusicActivity.this.finish();
                            } catch (Exception unused) {
                            }
                        }
                    });
                } catch (Exception unused) {
                    unused.printStackTrace();
                    Log.e("fdgklhfd",""+unused.getMessage());
                }
            } catch (OutOfMemoryError e4) {
                e4.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e5) {
                e5.printStackTrace();
            } catch (ActivityNotFoundException e6) {
                e6.printStackTrace();
            } catch (NotFoundException e7) {
                e7.printStackTrace();
            } catch (NullPointerException e8) {
                e8.printStackTrace();
            } catch (StackOverflowError e9) {
                e9.printStackTrace();
            }
        } else {
            a(getResources().getText(R.string.no_sdcard));
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }


    @Override public void onActivityResult(int i2, int i3, Intent intent) {
        if (i2 == 1) {
            if (i3 != -1) {
                try {
                    setResult(0);
                    finish();
                } catch (Exception e2) {
                    try {
                        e2.printStackTrace();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        return;
                    }
                }
            }
            setResult(-1, intent);
            finish();
        }
    }

    private void a(CharSequence charSequence) {
        new AlertDialog.Builder(this).setTitle(getResources().getText(R.string.alert_title_failure)).setMessage(charSequence).setPositiveButton(R.string.alert_ok_button, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                SelectMusicActivity.this.finish();
            }
        }).setCancelable(false).show();
    }

    private Cursor a(String str, String[] strArr) {
        return managedQuery(Media.INTERNAL_CONTENT_URI, j, str, strArr, "title_key");
    }

    private Cursor b(String str, String[] strArr) {
        return managedQuery(Media.EXTERNAL_CONTENT_URI, i, str, strArr, "title_key");
    }



    public Cursor ab(String str) {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.IS_RINGTONE,
                MediaStore.Audio.Media.IS_ALARM,
                MediaStore.Audio.Media.IS_NOTIFICATION,
                MediaStore.Audio.Media.IS_MUSIC
        };


        Cursor cursor = managedQuery(
                Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);
        return cursor;

/*        List<String> songs = new ArrayList<String>();
        while(cursor.moveToNext()){
            songs.add(cursor.getString(0) + "||" + cursor.getString(1) + "||" +   cursor.getString(2) + "||" +   cursor.getString(3) + "||" +  cursor.getString(4) + "||" +  cursor.getString(5));
        }*/
    }
/*
    public Cursor a(String str) {
        String str2;
        String sb;
        String str3;
        Exception e2;
        ArrayList arrayList = new ArrayList();
        if (this.k) {
            str2 = "(_DATA LIKE ?)";
            try {
                arrayList.add("%");
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        } else {
            String str4 = "(";
            try {
                String[] supportedExtensions = getSupportedExtensions();
                int length = supportedExtensions.length;
                str3 = str4;
                int i2 = 0;
                while (i2 < length) {
                    try {
                        String str5 = supportedExtensions[i2];
                        try {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("%.");
                            sb2.append(str5);
                            arrayList.add(sb2.toString());
                            if (str3.length() > 1) {
                                try {
                                    StringBuilder sb3 = new StringBuilder(String.valueOf(str3));
                                    sb3.append(" OR ");
                                    str3 = sb3.toString();
                                } catch (OutOfMemoryError e4) {
                                    e4.printStackTrace();
                                } catch (ArrayIndexOutOfBoundsException e5) {
                                    e5.printStackTrace();
                                } catch (ActivityNotFoundException e6) {
                                    e6.printStackTrace();
                                } catch (NotFoundException e7) {
                                    e7.printStackTrace();
                                } catch (NullPointerException e8) {
                                    e8.printStackTrace();
                                } catch (StackOverflowError e9) {
                                    e9.printStackTrace();
                                }
                            }
                            StringBuilder sb4 = new StringBuilder(String.valueOf(str3));
                            sb4.append("(_DATA LIKE ?)");
                            str3 = sb4.toString();
                        } catch (OutOfMemoryError e10) {
                            e10.printStackTrace();
                        } catch (ArrayIndexOutOfBoundsException e11) {
                            e11.printStackTrace();
                        } catch (ActivityNotFoundException e12) {
                            e12.printStackTrace();
                        } catch (NotFoundException e13) {
                            e13.printStackTrace();
                        } catch (NullPointerException e14) {
                            e14.printStackTrace();
                        } catch (StackOverflowError e15) {
                            e15.printStackTrace();
                        }
                        i2++;
                    } catch (Exception e16) {
                        e2 = e16;
                        e2.printStackTrace();
                        str2 = str3;
                        try {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append("%");
                            sb5.append(str);
                            sb5.append("%");
                            String sb6 = sb5.toString();
                            StringBuilder sb7 = new StringBuilder();
                            sb7.append("(");
                            sb7.append(str2);
                            sb7.append(" AND ((TITLE LIKE ?) OR (ARTIST LIKE ?) OR (ALBUM LIKE ?)))");
                            sb = sb7.toString();
                            try {
                                arrayList.add(sb6);
                                arrayList.add(sb6);
                                arrayList.add(sb6);
                                str2 = sb;
                            } catch (Exception e17) {
                                str2 = sb;
                                e17.printStackTrace();
                                String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
                                this.d = new MergeCursor(new Cursor[]{b(str2, strArr), a(str2, strArr)});
                                startManagingCursor(this.d);
                                return this.d;
                            }
                        } catch (Exception e23) {
                            e23.printStackTrace();
                            String[] strArr222222 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                            this.d = new MergeCursor(new Cursor[]{b(str2, strArr222222), a(str2, strArr222222)});
                            startManagingCursor(this.d);
                            return this.d;
                        }
                        String[] strArr222222222222 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                        this.d = new MergeCursor(new Cursor[]{b(str2, strArr222222222222), a(str2, strArr222222222222)});
                        startManagingCursor(this.d);
                        return this.d;
                    }
                }
                StringBuilder sb8 = new StringBuilder();
                sb8.append("(");
                StringBuilder sb9 = new StringBuilder(String.valueOf(str3));
                sb9.append(")");
                sb8.append(sb9.toString());
                sb8.append(") AND (_DATA NOT LIKE ?)");
                String sb10 = sb8.toString();
                try {
                    arrayList.add("%espeak-data/scratch%");
                    str2 = sb10;
                } catch (Exception e29) {
                    e2 = e29;
                    str3 = sb10;
                    e2.printStackTrace();
                    str2 = str3;
                    StringBuilder sb52 = new StringBuilder();
                    sb52.append("%");
                    sb52.append(str);
                    sb52.append("%");
                    String sb62 = sb52.toString();
                    StringBuilder sb72 = new StringBuilder();
                    sb72.append("(");
                    sb72.append(str2);
                    sb72.append(" AND ((TITLE LIKE ?) OR (ARTIST LIKE ?) OR (ALBUM LIKE ?)))");
                    sb = sb72.toString();
                    arrayList.add(sb62);
                    arrayList.add(sb62);
                    arrayList.add(sb62);
                    str2 = sb;
                    String[] strArr2222222222222 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                    this.d = new MergeCursor(new Cursor[]{b(str2, strArr2222222222222), a(str2, strArr2222222222222)});
                    startManagingCursor(this.d);
                    return this.d;
                }
            } catch (Exception e30) {
                str3 = str4;
                e2 = e30;
                e2.printStackTrace();
                str2 = str3;
                StringBuilder sb522 = new StringBuilder();
                sb522.append("%");
                sb522.append(str);
                sb522.append("%");
                String sb622 = sb522.toString();
                StringBuilder sb722 = new StringBuilder();
                sb722.append("(");
                sb722.append(str2);
                sb722.append(" AND ((TITLE LIKE ?) OR (ARTIST LIKE ?) OR (ALBUM LIKE ?)))");
                sb = sb722.toString();
                arrayList.add(sb622);
                arrayList.add(sb622);
                arrayList.add(sb622);
                str2 = sb;
                String[] strArr22222222222222 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                this.d = new MergeCursor(new Cursor[]{b(str2, strArr22222222222222), a(str2, strArr22222222222222)});
                startManagingCursor(this.d);
                return this.d;
            }
        }
        if (str != null && str.length() > 0) {
            StringBuilder sb5222 = new StringBuilder();
            sb5222.append("%");
            sb5222.append(str);
            sb5222.append("%");
            String sb6222 = sb5222.toString();
            StringBuilder sb7222 = new StringBuilder();
            sb7222.append("(");
            sb7222.append(str2);
            sb7222.append(" AND ((TITLE LIKE ?) OR (ARTIST LIKE ?) OR (ALBUM LIKE ?)))");
            sb = sb7222.toString();
            arrayList.add(sb6222);
            arrayList.add(sb6222);
            arrayList.add(sb6222);
            str2 = sb;
        }
        String[] strArr222222222222222 = (String[]) arrayList.toArray(new String[arrayList.size()]);
        this.d = new MergeCursor(new Cursor[]{b(str2, strArr222222222222222), a(str2, strArr222222222222222)});
        startManagingCursor(this.d);
        return this.d;
    }
*/

    @Override public void onBackPressed() {
        setResult(0);
        getWindow().clearFlags(128);
        finish();
    }


    @Override public void onDestroy() {
        getWindow().clearFlags(128);
        super.onDestroy();
    }

    public String[] getSupportedExtensions() {
        this.g = new ArrayList<>();
        this.g.add("wav");
        this.g.add("mp3");
        this.g.add("m4a");
        this.g.add("3gpp");
        this.g.add("3gp");
        this.g.add("amr");
        return (String[]) this.g.toArray(new String[this.g.size()]);
    }
}
