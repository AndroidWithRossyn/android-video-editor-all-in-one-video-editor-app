package com.androworld.allinonevideoeditor.videotogif;

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
import android.provider.MediaStore.Images.Media;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.Helper;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.listvideoandmyvideo.ContentUtill;
import com.androworld.allinonevideoeditor.listvideoandmyvideo.VideoData;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class MyGIFFragment extends Fragment {
    MyGIFAdapter a;
    ImageLoader b;
    ArrayList<VideoData> c = new ArrayList<>();
    ListView d;
    String e;
    String[] f;
    String[] g;
    private PowerManager h;
    private WakeLock i;
    private AdView j;
    private Ads ads;

    @SuppressLint({"NewApi"})
    private class a extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog a;

        private a() {
            this.a = null;
        }


        public void onPreExecute() {
            this.a = new ProgressDialog(MyGIFFragment.this.getActivity());
            this.a.setMessage("Loading...");
            this.a.setCancelable(false);
            this.a.show();
        }



        public Boolean doInBackground(Void... voidArr) {
            return Boolean.valueOf(MyGIFFragment.this.b());
        }



        public void onPostExecute(Boolean bool) {
            this.a.dismiss();
            if (bool.booleanValue()) {
                MyGIFFragment.this.a = new MyGIFAdapter(MyGIFFragment.this.getActivity(), MyGIFFragment.this.c, MyGIFFragment.this.b);
                MyGIFFragment.this.d.setAdapter(MyGIFFragment.this.a);
            }
        }
    }

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @SuppressLint("InvalidWakeLockTag")
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.videomyfragment, viewGroup, false);
        this.h = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        this.i = this.h.newWakeLock(6, "My Tag");
        a();
        this.d = (ListView) inflate.findViewById(R.id.VideogridView);
        new a().execute(new Void[0]);


        LinearLayout s = (LinearLayout) inflate.findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(getActivity(),s);

        return inflate;
    }

    private void a() {
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(getActivity()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().bitmapConfig(Config.RGB_565).displayer(new FadeInBitmapDisplayer(400)).build()).build();
        this.b = ImageLoader.getInstance();
        this.b.init(build);
    }


    @SuppressLint({"NewApi"})
    public boolean b() {
        this.g = new String[]{"_data", "_size", "_id"};
        if (Helper.ModuleId == 7) {
            this.e = "_data like?";
            StringBuilder sb = new StringBuilder();
            sb.append("%");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.VideoToImage));
            sb.append("%");
            this.f = new String[]{sb.toString()};
        } else if (Helper.ModuleId == 12) {
            this.e = "_data like?";
            StringBuilder sb2 = new StringBuilder();
            sb2.append("%");
            sb2.append(getResources().getString(R.string.MainFolderName));
            sb2.append("/");
            sb2.append(getResources().getString(R.string.VideoToGIF));
            sb2.append("%");
            this.f = new String[]{sb2.toString()};
        }
        Cursor managedQuery = getActivity().managedQuery(Media.EXTERNAL_CONTENT_URI, this.g, this.e, this.f, " _id DESC");
        int count = managedQuery.getCount();
        if (count <= 0) {
            return false;
        }
        managedQuery.moveToFirst();
        for (int i2 = 0; i2 < count; i2++) {
            Uri withAppendedPath = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, ContentUtill.getLong(managedQuery));
            String string = managedQuery.getString(managedQuery.getColumnIndexOrThrow("_data"));
            StringBuilder sb3 = new StringBuilder();
            sb3.append(managedQuery.getString(managedQuery.getColumnIndex("_size")));
            sb3.append(" KB");
            this.c.add(new VideoData(string, withAppendedPath, string, sb3.toString()));
            managedQuery.moveToNext();
        }
        return true;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
