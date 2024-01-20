package com.androworld.allinonevideoeditor.videocollage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.videocollage.stickers.HsItem;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SelectStickerActivity extends AppCompatActivity implements OnClickListener, OnItemClickListener {
    public static Bitmap bmp = null;
    static final boolean p = true;
    ImageAdapter a;
    Button b;
    Button c;
    Button d;
    String e;
    String[] f;
    GridView g;
    int h;
    int i;
    ArrayList<HsItem> j = new ArrayList<>();
    String[] k;
    DisplayMetrics l;
    DisplayImageOptions m;
    DisplayImageOptions n;
    int o = 0;

    public ImageLoader q;
    private AdView r;
    private Ads ads;

    private class ImageAdapter extends BaseAdapter {
        public Activity a;
        int b;
        private LayoutInflater d = null;

        public class ViewHolder {
            ImageView a;
            LinearLayout b;

            public ViewHolder() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public ImageAdapter(Activity activity) {
            this.a = activity;
            this.d = LayoutInflater.from(this.a);
            this.d = this.a.getLayoutInflater();
            this.b = this.a.getResources().getDisplayMetrics().widthPixels / 4;
        }

        public int getCount() {
            return SelectStickerActivity.this.j.size();
        }

        public Object getItem(int i) {
            return SelectStickerActivity.this.j.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            HsItem hsItem;
            View view2;
            Exception e;
            if (view == null) {
                try {
                    viewHolder = new ViewHolder();
                    try {
                        view2 = this.d.inflate(R.layout.videowatermark_sub_sticker_img_raw, null);
                        try {
                            view2.setLayoutParams(new LayoutParams(this.b, this.b));
                            viewHolder.b = (LinearLayout) view2.findViewById(R.id.ll_border);
                            viewHolder.a = (ImageView) view2.findViewById(R.id.ivpip_tiny);
                            viewHolder.a.setScaleType(ScaleType.CENTER_INSIDE);
                            view2.setTag(viewHolder);
                        } catch (Exception e2) {
                            e = e2;
                        }
                    } catch (Exception e3) {
                        Exception exc = e3;
                        view2 = view;
                        e = exc;
                        e.printStackTrace();
                        view = view2;
                        hsItem = (HsItem) SelectStickerActivity.this.j.get(i);
                        if (hsItem.isAvailable) {
                        }
                        return view;
                    }
                } catch (Exception e4) {
                    view2 = view;
                    e = e4;
                    viewHolder = null;
                    e.printStackTrace();
                    view = view2;
                    hsItem = (HsItem) SelectStickerActivity.this.j.get(i);
                    if (hsItem.isAvailable) {
                    }
                    return view;
                }
                view = view2;
            } else {
                try {
                    viewHolder = (ViewHolder) view.getTag();
                } catch (Exception e5) {
                    e5.printStackTrace();
                    viewHolder = null;
                }
            }
            hsItem = (HsItem) SelectStickerActivity.this.j.get(i);
            if (hsItem.isAvailable) {
                try {
                    SelectStickerActivity.this.q.displayImage(hsItem.path, viewHolder.a, SelectStickerActivity.this.n);
                } catch (Exception e6) {
                    e6.printStackTrace();
                }
            } else {
                try {
                    SelectStickerActivity.this.q.displayImage(hsItem.path, viewHolder.a, SelectStickerActivity.this.m);
                } catch (Exception e7) {
                    e7.printStackTrace();
                }
            }
            return view;
        }
    }


    @Override public void onCreate(Bundle bundle) {
        String[] strArr;
        File[] listFiles;
        super.onCreate(bundle);
        setContentView( R.layout.videocollage_selectsticker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Select Sticker");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (p || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(p);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.l = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(this.l);
            this.i = this.l.widthPixels;
            this.h = this.l.heightPixels;
            Intent intent = getIntent();
            try {
                this.f = getAssets().list("stickers");
            } catch (IOException unused) {
            }
            this.e = intent.getStringExtra("folderName");
            this.d = (Button) findViewById(R.id.btn_animal);
            this.c = (Button) findViewById(R.id.btn_baby);
            this.b = (Button) findViewById(R.id.btn_birth);
            this.d.setOnClickListener(this);
            this.c.setOnClickListener(this);
            this.b.setOnClickListener(this);
            this.j.clear();
            StringBuilder sb = new StringBuilder();
            sb.append("stickers/");
            sb.append(this.f[0]);
            this.k = a(sb.toString());
            for (String str : this.k) {
                try {
                    ArrayList<HsItem> arrayList = this.j;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("assets://");
                    sb2.append(str);
                    arrayList.add(new HsItem(sb2.toString(), p));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(getFilesDir());
            sb3.append("/Stickers/");
            sb3.append(this.e);
            File file = new File(sb3.toString());
            if (!file.exists()) {
                try {
                    file.mkdirs();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            for (File file2 : file.listFiles()) {
                try {
                    ArrayList<HsItem> arrayList2 = this.j;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("file://");
                    sb4.append(file2.getAbsolutePath());
                    arrayList2.add(new HsItem(sb4.toString(), p));
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
            a();
            this.q = ImageLoader.getInstance();
            this.g = (GridView) findViewById(R.id.gridView1);
            this.a = new ImageAdapter(this);
            this.g.setAdapter(this.a);
            this.g.setOnItemClickListener(this);



            LinearLayout s = (LinearLayout) findViewById(R.id.banner_AdView);
            ads = new Ads();
            ads.BannerAd(SelectStickerActivity.this,s);

        } else {
            throw new AssertionError();
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
            return false;
        }
        return p;
    }

    @Override public void onStart() {
        super.onStart();
    }

    @Override public void onStop() {
        super.onStop();
    }


    public void onPause() {
        super.onPause();
    }

    @Override public void onBackPressed() {
        finish();
    }

    private String[] a(String str) {
        String[] strArr;
        try {
            strArr = getAssets().list(str);
        } catch (IOException e2) {
            e2.printStackTrace();
            strArr = null;
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            try {
                StringBuilder sb = new StringBuilder(String.valueOf(str));
                sb.append("/");
                sb.append(strArr[i2]);
                strArr[i2] = sb.toString();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        return strArr;
    }

    private void a() {
        this.n = new DisplayImageOptions.Builder().cacheOnDisk(p).imageScaleType(ImageScaleType.NONE).showImageOnLoading(0).bitmapConfig(Config.ARGB_4444).build();
        this.m = new DisplayImageOptions.Builder().cacheOnDisk(p).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Config.ARGB_4444).build();
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(this.m).memoryCache(new WeakMemoryCache()).build());
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
        HsItem hsItem = (HsItem) this.j.get(i2);
        new File(hsItem.path).getName();
        this.o = i2;
        this.a.notifyDataSetChanged();
        if (hsItem.isAvailable) {
            try {
                bmp = BitmapFactory.decodeStream(getAssets().open(this.k[i2]));
            } catch (Exception unused) {
                bmp = BitmapFactory.decodeFile(hsItem.path.replace("file://", ""));
            }
            setResult(-1);
            finish();
            overridePendingTransition(0, R.anim.dialog_close);
        }
    }

    @SuppressLint({"NewApi"})
    @Override public void onClick(View view) {
        String[] strArr;
        String[] strArr2;
        String[] strArr3;
        int i2 = 0;
        if (view == this.d) {
            try {
                this.j.clear();
                StringBuilder sb = new StringBuilder();
                sb.append("stickers/");
                sb.append(this.f[0]);
                this.k = a(sb.toString());
                for (String str : this.k) {
                    try {
                        ArrayList<HsItem> arrayList = this.j;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("assets://");
                        sb2.append(str);
                        arrayList.add(new HsItem(sb2.toString(), p));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append(getFilesDir());
                sb3.append("/Stickers/");
                sb3.append(this.e);
                File file = new File(sb3.toString());
                if (!file.exists()) {
                    try {
                        file.mkdirs();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                File[] listFiles = file.listFiles();
                int length = listFiles.length;
                while (i2 < length) {
                    try {
                        ArrayList<HsItem> arrayList2 = this.j;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("file://");
                        sb4.append(listFiles[i2].getAbsolutePath());
                        arrayList2.add(new HsItem(sb4.toString(), p));
                        i2++;
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
                a();
                this.q = ImageLoader.getInstance();
                this.g = (GridView) findViewById(R.id.gridView1);
                this.a = new ImageAdapter(this);
                this.g.setAdapter(this.a);
                this.a.notifyDataSetChanged();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        } else if (view == this.c) {
            try {
                this.j.clear();
                StringBuilder sb5 = new StringBuilder();
                sb5.append("stickers/");
                sb5.append(this.f[1]);
                this.k = a(sb5.toString());
                for (String str2 : this.k) {
                    try {
                        ArrayList<HsItem> arrayList3 = this.j;
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("assets://");
                        sb6.append(str2);
                        arrayList3.add(new HsItem(sb6.toString(), p));
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
                StringBuilder sb7 = new StringBuilder();
                sb7.append(getFilesDir());
                sb7.append("/Stickers/");
                sb7.append(this.e);
                File file2 = new File(sb7.toString());
                if (!file2.exists()) {
                    try {
                        file2.mkdirs();
                    } catch (Exception e7) {
                        e7.printStackTrace();
                    }
                }
                File[] listFiles2 = file2.listFiles();
                int length2 = listFiles2.length;
                while (i2 < length2) {
                    try {
                        ArrayList<HsItem> arrayList4 = this.j;
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("file://");
                        sb8.append(listFiles2[i2].getAbsolutePath());
                        arrayList4.add(new HsItem(sb8.toString(), p));
                        i2++;
                    } catch (Exception e8) {
                        e8.printStackTrace();
                    }
                }
                a();
                this.q = ImageLoader.getInstance();
                this.g = (GridView) findViewById(R.id.gridView1);
                this.a = new ImageAdapter(this);
                this.g.setAdapter(this.a);
                this.a.notifyDataSetChanged();
            } catch (Exception e9) {
                e9.printStackTrace();
            }
        } else if (view == this.b) {
            try {
                this.j.clear();
                StringBuilder sb9 = new StringBuilder();
                sb9.append("stickers/");
                sb9.append(this.f[2]);
                this.k = a(sb9.toString());
                for (String str3 : this.k) {
                    try {
                        ArrayList<HsItem> arrayList5 = this.j;
                        StringBuilder sb10 = new StringBuilder();
                        sb10.append("assets://");
                        sb10.append(str3);
                        arrayList5.add(new HsItem(sb10.toString(), p));
                    } catch (Exception e10) {
                        e10.printStackTrace();
                    }
                }
                StringBuilder sb11 = new StringBuilder();
                sb11.append(getFilesDir());
                sb11.append("/Stickers/");
                sb11.append(this.f[2]);
                File file3 = new File(sb11.toString());
                if (!file3.exists()) {
                    try {
                        file3.mkdirs();
                    } catch (Exception e11) {
                        e11.printStackTrace();
                    }
                }
                File[] listFiles3 = file3.listFiles();
                int length3 = listFiles3.length;
                while (i2 < length3) {
                    try {
                        ArrayList<HsItem> arrayList6 = this.j;
                        StringBuilder sb12 = new StringBuilder();
                        sb12.append("file://");
                        sb12.append(listFiles3[i2].getAbsolutePath());
                        arrayList6.add(new HsItem(sb12.toString(), p));
                        i2++;
                    } catch (Exception e12) {
                        e12.printStackTrace();
                    }
                }
                a();
                this.q = ImageLoader.getInstance();
                this.g = (GridView) findViewById(R.id.gridView1);
                this.a = new ImageAdapter(this);
                this.g.setAdapter(this.a);
                this.a.notifyDataSetChanged();
            } catch (Exception e13) {
                e13.printStackTrace();
            }
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return p;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return p;
        }
        if (menuItem.getItemId() == R.id.Done) {
            try {
                setResult(-1);
                finish();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
