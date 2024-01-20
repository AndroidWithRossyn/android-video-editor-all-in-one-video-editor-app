package com.androworld.allinonevideoeditor.listvideoandmyvideo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap.Config;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore.Video.Media;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.androworld.allinonevideoeditor.Helper;
import com.androworld.allinonevideoeditor.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class MyVideoFragment extends Fragment {
    MyVideoAdapter a;
    ImageLoader b;
    ArrayList<VideoData> c = new ArrayList<>();
    ListView d;
    String e;
    String[] f;
    private PowerManager g;
    private WakeLock h;

    @SuppressLint({"NewApi"})
    private class a extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog a;

        private a() {
            this.a = null;
        }


        public void onPreExecute() {
            this.a = new ProgressDialog(MyVideoFragment.this.getActivity());
            this.a.setMessage("Loading...");
            this.a.setCancelable(false);
            this.a.show();
        }



        public Boolean doInBackground(Void... voidArr) {
            return Boolean.valueOf(MyVideoFragment.this.b());
        }



        public void onPostExecute(Boolean bool) {
            this.a.dismiss();
            if (bool.booleanValue()) {
                MyVideoFragment.this.a = new MyVideoAdapter(MyVideoFragment.this.getActivity(), MyVideoFragment.this.c, MyVideoFragment.this.b);
                MyVideoFragment.this.d.setAdapter(MyVideoFragment.this.a);
            }
        }
    }

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @SuppressLint("InvalidWakeLockTag")
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.videomyfragment, viewGroup, false);
        this.g = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        this.h = this.g.newWakeLock(6, "My Tag");
        a();
        this.d = (ListView) inflate.findViewById(R.id.VideogridView);
        new a().execute(new Void[0]);
        return inflate;
    }

    private void a() {
        ImageLoaderConfiguration build = new Builder(getActivity()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().bitmapConfig(Config.RGB_565).displayer(new FadeInBitmapDisplayer(400)).build()).build();
        this.b = ImageLoader.getInstance();
        this.b.init(build);
    }


    @SuppressLint({"NewApi"})
    public boolean b() {
        if (Helper.ModuleId == 1) {
            this.e = "_data like?";
            StringBuilder sb = new StringBuilder();
            sb.append("%");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.VideoCutter));
            sb.append("%");
            this.f = new String[]{sb.toString()};
        } else if (Helper.ModuleId == 2) {
            this.e = "_data like?";
            StringBuilder sb2 = new StringBuilder();
            sb2.append("%");
            sb2.append(getResources().getString(R.string.MainFolderName));
            sb2.append("/");
            sb2.append(getResources().getString(R.string.VideoCompressor));
            sb2.append("%");
            this.f = new String[]{sb2.toString()};
        } else if (Helper.ModuleId == 4) {
            this.e = "_data like?";
            StringBuilder sb3 = new StringBuilder();
            sb3.append("%");
            sb3.append(getResources().getString(R.string.MainFolderName));
            sb3.append("/");
            sb3.append(getResources().getString(R.string.AudioVideoMixer));
            sb3.append("%");
            this.f = new String[]{sb3.toString()};
        } else if (Helper.ModuleId == 5) {
            this.e = "_data like?";
            StringBuilder sb4 = new StringBuilder();
            sb4.append("%");
            sb4.append(getResources().getString(R.string.MainFolderName));
            sb4.append("/");
            sb4.append(getResources().getString(R.string.VideoMute));
            sb4.append("%");
            this.f = new String[]{sb4.toString()};
        } else if (Helper.ModuleId == 8) {
            this.e = "_data like?";
            StringBuilder sb5 = new StringBuilder();
            sb5.append("%");
            sb5.append(getResources().getString(R.string.MainFolderName));
            sb5.append("/");
            sb5.append(getResources().getString(R.string.VideoConverter));
            sb5.append("%");
            this.f = new String[]{sb5.toString()};
        } else if (Helper.ModuleId == 9) {
            this.e = "_data like?";
            StringBuilder sb6 = new StringBuilder();
            sb6.append("%");
            sb6.append(getResources().getString(R.string.MainFolderName));
            sb6.append("/");
            sb6.append(getResources().getString(R.string.FastMotionVideo));
            sb6.append("%");
            this.f = new String[]{sb6.toString()};
        } else if (Helper.ModuleId == 10) {
            this.e = "_data like?";
            StringBuilder sb7 = new StringBuilder();
            sb7.append("%");
            sb7.append(getResources().getString(R.string.MainFolderName));
            sb7.append("/");
            sb7.append(getResources().getString(R.string.SlowMotionVideo));
            sb7.append("%");
            this.f = new String[]{sb7.toString()};
        } else if (Helper.ModuleId == 11) {
            this.e = "_data like?";
            StringBuilder sb8 = new StringBuilder();
            sb8.append("%");
            sb8.append(getResources().getString(R.string.MainFolderName));
            sb8.append("/");
            sb8.append(getResources().getString(R.string.VideoCroper));
            sb8.append("%");
            this.f = new String[]{sb8.toString()};
        } else if (Helper.ModuleId == 13) {
            this.e = "_data like?";
            StringBuilder sb9 = new StringBuilder();
            sb9.append("%");
            sb9.append(getResources().getString(R.string.MainFolderName));
            sb9.append("/");
            sb9.append(getResources().getString(R.string.VideoRotate));
            sb9.append("%");
            this.f = new String[]{sb9.toString()};
        } else if (Helper.ModuleId == 14) {
            this.e = "_data like?";
            StringBuilder sb10 = new StringBuilder();
            sb10.append("%");
            sb10.append(getResources().getString(R.string.MainFolderName));
            sb10.append("/");
            sb10.append(getResources().getString(R.string.VideoMirror));
            sb10.append("%");
            this.f = new String[]{sb10.toString()};
        } else if (Helper.ModuleId == 15) {
            this.e = "_data like?";
            StringBuilder sb11 = new StringBuilder();
            sb11.append("%");
            sb11.append(getResources().getString(R.string.MainFolderName));
            sb11.append("/");
            sb11.append(getResources().getString(R.string.VideoSplitter));
            sb11.append("%");
            this.f = new String[]{sb11.toString()};
        } else if (Helper.ModuleId == 16) {
            this.e = "_data like?";
            StringBuilder sb12 = new StringBuilder();
            sb12.append("%");
            sb12.append(getResources().getString(R.string.MainFolderName));
            sb12.append("/");
            sb12.append(getResources().getString(R.string.VideoReverse));
            sb12.append("%");
            this.f = new String[]{sb12.toString()};
        } else if (Helper.ModuleId == 22) {
            this.e = "_data like?";
            StringBuilder sb13 = new StringBuilder();
            sb13.append("%");
            sb13.append(getResources().getString(R.string.MainFolderName));
            sb13.append("/");
            sb13.append(getResources().getString(R.string.VideoWatermark));
            sb13.append("%");
            this.f = new String[]{sb13.toString()};
        }
        Cursor managedQuery = getActivity().managedQuery(Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "_id", "_display_name", "duration"}, this.e, this.f, " _id DESC");
        int count = managedQuery.getCount();
        if (count <= 0) {
            return false;
        }
        managedQuery.moveToFirst();
        for (int i = 0; i < count; i++) {
            Uri withAppendedPath = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, ContentUtill.getLong(managedQuery));
            this.c.add(new VideoData(managedQuery.getString(managedQuery.getColumnIndexOrThrow("_display_name")), withAppendedPath, managedQuery.getString(managedQuery.getColumnIndex("_data")), ContentUtill.getTime(managedQuery, "duration")));
            managedQuery.moveToNext();
        }
        return true;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
