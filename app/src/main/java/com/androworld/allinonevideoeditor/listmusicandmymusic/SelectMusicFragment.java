package com.androworld.allinonevideoeditor.listmusicandmymusic;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.androworld.allinonevideoeditor.Helper;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.audioJoin.AudioJoinerActivity;
import com.androworld.allinonevideoeditor.audiocompress.AudioCompressorActivity;
import com.androworld.allinonevideoeditor.audiocutter.MP3CutterActivity;
import com.androworld.allinonevideoeditor.audiocutter.cutter.CheapSoundFile;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SelectMusicFragment extends Fragment {
    private static final String[] d;
    private static final String[] e;
    public static int mills;
    ArrayList<String> a = new ArrayList<>();
    ListView b;
    View c;
    private SimpleCursorAdapter f;
    private boolean g;
    private OnItemClickListener h = new OnItemClickListener() {
        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
            SelectMusicFragment.this.a();
        }
    };

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(Media.EXTERNAL_CONTENT_URI);
        sb.append("\"");
        d = new String[]{"_id", "_data", "title", "artist", "album", "_size", "duration", "is_ringtone", "is_alarm", "is_notification", "is_music", sb.toString()};
        StringBuilder sb2 = new StringBuilder();
        sb2.append("\"");
        sb2.append(Media.INTERNAL_CONTENT_URI);
        sb2.append("\"");
        e = new String[]{"_id", "_data", "title", "artist", "album", "_size", "duration", "is_ringtone", "is_alarm", "is_notification", "is_music", sb2.toString()};
    }

    public static String formatTimeUnit(long j) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j)))});
    }

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.c = layoutInflater.inflate(R.layout.fragment_selectmusic, viewGroup, false);
        this.g = false;
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
                this.b = (ListView) this.c.findViewById(R.id.listmusic);
                try {
                    SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                            getActivity(), R.layout.audio_select_music_row,
                            ab(""),
                            new String[]{"artist", "title", "_size", "duration"},
                            new int[]{R.id.row_artist, R.id.row_title, R.id.row_Size, R.id.row_Duration}
                            );
                    this.f = simpleCursorAdapter;
                    this.f.setViewBinder(new ViewBinder() {
                        public boolean setViewValue(View view, Cursor cursor, int i) {
                            if (i != 6) {
                                return false;
                            }
                            if (cursor.getString(i) != null) {
                                try {
                                    SelectMusicFragment.mills = Integer.parseInt(cursor.getString(i));
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                TextView textView = (TextView) view;
                                StringBuilder sb = new StringBuilder();
                                sb.append("Length : ");
                                sb.append(SelectMusicFragment.formatTimeUnit((long) SelectMusicFragment.mills));
                                textView.setText(sb.toString());
                            } catch (ParseException e2) {
                                e2.printStackTrace();
                            }
                            return true;
                        }
                    });
                    this.b.setAdapter(this.f);
                    this.b.setOnItemClickListener(this.h);
                } catch (IllegalArgumentException | SecurityException unused) {
                    unused.printStackTrace();
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        } else {
            try {
                a(getResources().getText(R.string.no_sdcard));
            } catch (NotFoundException e5) {
                e5.printStackTrace();
            }
        }
        return this.c;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void a(CharSequence charSequence) {
        new Builder(getActivity()).setTitle(getResources().getText(R.string.alert_title_failure)).setMessage(charSequence).setPositiveButton(R.string.alert_ok_button, new OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                SelectMusicFragment.this.getActivity().finish();
            }
        }).setCancelable(false).show();
    }


    public void a() {
        Cursor cursor = this.f.getCursor();
        String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
        if (Helper.ModuleId == 18) {
            try {
                Helper.audiopath = string;
                Helper.audioname = string;
                startActivity(new Intent(getActivity(), AudioCompressorActivity.class));
            } catch (Exception unused) {
            }
        } else if (Helper.ModuleId == 19) {
            Helper.audiopath = string;
            Helper.audioname = string;
            startActivity(new Intent(getActivity(), AudioJoinerActivity.class));
        } else if (Helper.ModuleId == 20) {
            Helper.audiopath = string;
            Helper.audioname = string;
            startActivity(new Intent(getActivity(), MP3CutterActivity.class));
        }
    }

    private Cursor a(String str, String[] strArr) {
        try {
            return getActivity().managedQuery(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, e, str, strArr, "title_key");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Cursor b(String str, String[] strArr) {
        try {
            return getActivity().managedQuery(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    d,
                    str,
                    strArr,
                    "title_key"
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
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


        Cursor cursor = getActivity().managedQuery(
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
    public Cursor a(String str) {
        String str2;
        String sb;
        String str3;
        Exception e2;
        ArrayList arrayList = new ArrayList();
        if (this.g) {
            str2 = "(_DATA LIKE ?)";
            try {
                arrayList.add("%");
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        } else {
            String str4 = "(";
            try {
                String[] supportedExtensions = CheapSoundFile.getSupportedExtensions();
                int length = supportedExtensions.length;
                str3 = str4;
                int i = 0;
                while (i < length) {
                    try {
                        String str5 = supportedExtensions[i];
                        try {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("%.");
                            sb2.append(str5);
                            arrayList.add(sb2.toString());
                            if (str3.length() > 1) {
                                StringBuilder sb3 = new StringBuilder(String.valueOf(str3));
                                sb3.append(" OR ");
                                str3 = sb3.toString();
                            }
                            StringBuilder sb4 = new StringBuilder(String.valueOf(str3));
                            sb4.append("(_DATA LIKE ?)");
                            str3 = sb4.toString();
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                        i++;
                    } catch (Exception e5) {
                        e2 = e5;
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
                            } catch (Exception e6) {
                                str2 = sb;
                                String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
                                MergeCursor mergeCursor = new MergeCursor(new Cursor[]{b(str2, strArr), a(str2, strArr)});
                                getActivity().startManagingCursor(mergeCursor);
                                return mergeCursor;
                            }
                        } catch (Exception e7) {
                            String[] strArr2 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                            MergeCursor mergeCursor2 = new MergeCursor(new Cursor[]{b(str2, strArr2), a(str2, strArr2)});
                            getActivity().startManagingCursor(mergeCursor2);
                            return mergeCursor2;
                        }
                        String[] strArr22 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                        MergeCursor mergeCursor22 = new MergeCursor(new Cursor[]{b(str2, strArr22), a(str2, strArr22)});
                        getActivity().startManagingCursor(mergeCursor22);
                        return mergeCursor22;
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
                } catch (Exception e8) {
                    e2 = e8;
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
                    String[] strArr222 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                    MergeCursor mergeCursor222 = new MergeCursor(new Cursor[]{b(str2, strArr222), a(str2, strArr222)});
                    getActivity().startManagingCursor(mergeCursor222);
                    return mergeCursor222;
                }
            } catch (Exception e9) {
                str3 = str4;
                e2 = e9;
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
                String[] strArr2222 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                MergeCursor mergeCursor2222 = new MergeCursor(new Cursor[]{b(str2, strArr2222), a(str2, strArr2222)});
                getActivity().startManagingCursor(mergeCursor2222);
                return mergeCursor2222;
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
        String[] strArr22222 = (String[]) arrayList.toArray(new String[arrayList.size()]);
        MergeCursor mergeCursor22222 = new MergeCursor(new Cursor[]{b(str2, strArr22222), a(str2, strArr22222)});
        getActivity().startManagingCursor(mergeCursor22222);
        return mergeCursor22222;
    }
}
