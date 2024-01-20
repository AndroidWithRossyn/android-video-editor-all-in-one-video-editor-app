package com.androworld.allinonevideoeditor.phototovideo.tablayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.view.InputDeviceCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.phototovideo.SelectImageAndMyVideoActivity;
import com.androworld.allinonevideoeditor.phototovideo.SelectionListActivity;
import com.androworld.allinonevideoeditor.phototovideo.dbhelper.AssetsDataBaseHelper;
import com.androworld.allinonevideoeditor.phototovideo.model.AlbumImages;
import com.androworld.allinonevideoeditor.phototovideo.model.ImageSelect;
import com.androworld.allinonevideoeditor.phototovideo.model.SelectBucketImage;
import com.androworld.allinonevideoeditor.phototovideo.tablayout.SlidingTabLayout.TabColorizer;
import com.androworld.allinonevideoeditor.phototovideo.util.BitmapCompression;
import com.androworld.allinonevideoeditor.phototovideo.util.PreferenceManager;
import com.androworld.allinonevideoeditor.phototovideo.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Locale;

@SuppressLint({"WrongConstant"})
public class HomeTab extends FragmentActivity {
    CharSequence[] a = {"ALBUM", "PHOTO"};
    ImageView b;
    ImageView c;
    int d = 0;
    AssetsDataBaseHelper e = null;
    ImageLoader f;
    boolean g;
    int h;
    ProgressDialog i;
    int j;
    int k;
    SlidingTabLayout l;
    ViewPager m;

    public class TabPagerAdapter extends FragmentPagerAdapter {
        final int a = 2;
        Context b;

        public int getCount() {
            return 2;
        }

        public int getItemPosition(Object obj) {
            return -2;
        }

        public TabPagerAdapter(FragmentManager fragmentManager, Context context) {
            super(fragmentManager);
            this.b = context;
        }

        public CharSequence getPageTitle(int i) {
            return HomeTab.this.a[i];
        }

        public void startUpdate(ViewGroup viewGroup) {
            super.startUpdate(viewGroup);
        }

        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return FragementAlbum.newInstance(i, HomeTab.this);
                case 1:
                    return FragmentPhoto.newInstance(i, HomeTab.this);
                default:
                    return null;
            }
        }
    }

    class a extends AsyncTask<Void, Void, Boolean> {
        a() {
        }



        public Boolean doInBackground(Void... voidArr) {
            try {
                Utils.createImageList.add(HomeTab.this.a());
                HomeTab.this.e.addImageDetail((String) Utils.createImageList.get(HomeTab.this.d));
                return Boolean.valueOf(true);
            } catch (Exception unused) {
                return Boolean.valueOf(false);
            }
        }



        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if (HomeTab.this.d < HomeTab.this.h - 1) {
                HomeTab.this.i.setProgress((HomeTab.this.d * 100) / HomeTab.this.h);
                HomeTab.this.d++;
                new c().execute(new Boolean[0]);
                return;
            }
            HomeTab.this.d = 0;
            if (HomeTab.this.i != null && HomeTab.this.i.isShowing()) {
                HomeTab.this.i.dismiss();
            }
            if (Utils.bitmap != null) {
                Utils.bitmap.recycle();
            }
            if (Utils.selectImageList.size() > 0) {
                Utils.selectImageList.clear();
            }
            HomeTab.this.startActivityForResult(new Intent(HomeTab.this, SelectionListActivity.class), 69);
        }
    }

    private class b extends AsyncTask<Void, Void, Boolean> {
        int a;

        private b() {
        }


        public void onPreExecute() {
            try {
                HomeTab.this.e = new AssetsDataBaseHelper(HomeTab.this.getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            HomeTab.this.i = new ProgressDialog(HomeTab.this);
            HomeTab.this.i.setCancelable(false);
            HomeTab.this.i.setProgressStyle(1);
            HomeTab.this.i.show();
            this.a = Utils.imageUri.size();
        }



        public Boolean doInBackground(Void... voidArr) {
            for (int i = 0; i < this.a; i++) {
                int size = ((SelectBucketImage) Utils.imageUri.get(i)).imgUri.size();
                for (int i2 = 0; i2 < size; i2++) {
                    int i3 = ((AlbumImages) ((SelectBucketImage) Utils.imageUri.get(i)).imgUri.get(i2)).getImgPos();
                    if (i3 >= 0) {
                        ImageSelect imageSelect = new ImageSelect();
                        int indexId = PreferenceManager.getIndexId();
                        imageSelect.indexId = indexId;
                        PreferenceManager.setIndexId(indexId + 1);
                        imageSelect.imgId = ((AlbumImages) ((SelectBucketImage) Utils.imageUri.get(i)).imgUri.get(i2)).getImgId().intValue();
                        imageSelect.imgUri = ((AlbumImages) ((SelectBucketImage) Utils.imageUri.get(i)).imgUri.get(i2)).getImgUri().toString();
                        imageSelect.cropIndex = -1;
                        imageSelect.imgPos = i3;
                        Utils.selectImageList.add(imageSelect);
                    }
                }
            }
            HomeTab.this.h = Utils.myUri.size();
            if (HomeTab.this.h <= 0) {
                return Boolean.valueOf(false);
            }
            return Boolean.valueOf(true);
        }



        public void onPostExecute(Boolean bool) {
            if (bool.booleanValue()) {
                Utils.imgCount = Utils.createImageList.size() + 1;
                if (Utils.imgCount == 0) {
                    Utils.imgCount = 1;
                }
                HomeTab.this.d = 0;
                DisplayMetrics displayMetrics = new DisplayMetrics();
                HomeTab.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                HomeTab.this.k = displayMetrics.widthPixels;
                HomeTab.this.j = displayMetrics.heightPixels;
                new c().execute(new Boolean[0]);
                return;
            }
            if (!(HomeTab.this.i == null || HomeTab.this.i == null)) {
                HomeTab.this.i.dismiss();
            }
            Toast.makeText(HomeTab.this.getApplicationContext(), "Select At Least One Image", 0).show();
        }
    }

    class c extends AsyncTask<Boolean, Void, Boolean> {
        boolean a;
        String b = "";
        int c;
        int d;
        String e;
        int f;
        int g;

        c() {
        }


        public void onPreExecute() {
            this.e = (String) Utils.myUri.get(HomeTab.this.d);
            this.a = false;
        }



        public Boolean doInBackground(Boolean... boolArr) {
            if (this.a) {
                Log.i("isDuplicate", "TRUE");
                return Boolean.valueOf(false);
            }
            File file = new File(this.e);
            try {
                Options options = new Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(new FileInputStream(file), null, options);
                options.inSampleSize = BitmapCompression.calculateInSampleSize(options, HomeTab.this.k, HomeTab.this.k);
                options.inJustDecodeBounds = false;
                if (Utils.bitmap != null) {
                    Utils.bitmap.recycle();
                    Utils.bitmap = null;
                }
                Utils.bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
                BitmapCompression.adjustImageOrientationUri(HomeTab.this.getApplicationContext(), HomeTab.getImageContentUri(HomeTab.this.getApplicationContext(), new File((String) Utils.myUri.get(HomeTab.this.d))));
                if (Utils.bitmap == null) {
                    Utils.bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
                }
                int width = Utils.bitmap.getWidth();
                int height = Utils.bitmap.getHeight();
                if (width > height) {
                    this.d = HomeTab.this.k;
                    this.c = (HomeTab.this.k * height) / width;
                    this.f = 0;
                    this.g = (HomeTab.this.k - this.c) / 2;
                } else {
                    this.c = HomeTab.this.k;
                    this.d = (HomeTab.this.k * width) / height;
                    this.g = 0;
                    this.f = (HomeTab.this.k - this.d) / 2;
                }
                return Boolean.valueOf(true);
            } catch (Exception e2) {
                Log.i("Background", "TRUE");
                e2.printStackTrace();
                e2.getMessage();
                return Boolean.valueOf(false);
            }
        }



        public void onPostExecute(Boolean bool) {
            if (bool.booleanValue()) {
                new a().execute(new Void[0]);
                return;
            }
            if (!(HomeTab.this.i == null || HomeTab.this.i == null)) {
                HomeTab.this.i.dismiss();
            }
            Toast.makeText(HomeTab.this.getApplicationContext(), "Error in Creating Image ", 0).show();
            HomeTab.this.finish();
        }
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.phototovideo_hometabactivity);
        this.f = ImageLoader.getInstance();
        this.l = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        this.l.setCustomTabColorizer(new TabColorizer() {
            public int getDividerColor(int i) {
                return InputDeviceCompat.SOURCE_ANY;
            }

            public int getIndicatorColor(int i) {
                return InputDeviceCompat.SOURCE_ANY;
            }
        });
        this.m = (ViewPager) findViewById(R.id.pager);
        Universal.fadt = new TabPagerAdapter(getSupportFragmentManager(), this);
        this.m.setAdapter(Universal.fadt);
        Universal.vobj = this.m;
        this.l.setViewPager(this.m);
        this.c = (ImageView) findViewById(R.id.btnNext);
        this.b = (ImageView) findViewById(R.id.btn_back);
        this.b.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                Utils.myUri.clear();
                Utils.selectedImagesUri.clear();
                Utils.createImageList.clear();
                Utils.imageUri.clear();
                Intent intent = new Intent(HomeTab.this, SelectImageAndMyVideoActivity.class);
                intent.setFlags(67108864);
                HomeTab.this.startActivity(intent);
                HomeTab.this.finish();
            }
        });
        this.c.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                HomeTab.this.nextClick();
            }
        });
    }


    @Override public void onResume() {
        super.onResume();
    }

    public void nextClick() {
        if (!this.g) {
            if (Utils.createImageList != null && Utils.createImageList.size() > 0) {
                Utils.createImageList.clear();
            }
            if (Utils.selectedImagesUri != null && Utils.selectedImagesUri.size() > 0) {
                Utils.selectedImagesUri.clear();
            }
            if (Utils.selectImageList != null && Utils.selectImageList.size() > 0) {
                Utils.selectImageList.clear();
            }
            new b().execute(new Void[0]);
            this.f.clearDiskCache();
            this.f.clearMemoryCache();
            this.g = true;
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                HomeTab.this.g = false;
            }
        }, 2000);
    }

    public static Uri getImageContentUri(Context context, File file) {
        String absolutePath = file.getAbsolutePath();
        Cursor query = context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=? ", new String[]{absolutePath}, null);
        if (query != null && query.moveToFirst()) {
            int i2 = query.getInt(query.getColumnIndex("_id"));
            query.close();
            return Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, String.valueOf(i2));
        } else if (!file.exists()) {
            return null;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_data", absolutePath);
            return context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
        }
    }


    public String a() {
        StringBuilder sb = new StringBuilder(String.valueOf(Utils.getPath(getApplicationContext())));
        sb.append("/temp");
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("slide_");
        sb2.append(String.format(Locale.US, "%05d", new Object[]{Integer.valueOf(Utils.imgCount)}));
        sb2.append(".jpg");
        File file2 = new File(file, sb2.toString());
        Utils.imgCount++;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                Utils.bitmap.compress(CompressFormat.JPEG, 70, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                return file2.getAbsolutePath();
            } catch (Exception e2) {
                e2.printStackTrace();
                return file2.getAbsolutePath();
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            return file2.getAbsolutePath();
        }
    }

    public int dpToPx(int i2) {
        return Math.round(((float) i2) * (getResources().getDisplayMetrics().xdpi / 160.0f));
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        Utils.myUri.clear();
        Utils.selectedImagesUri.clear();
        Utils.createImageList.clear();
        Utils.imageUri.clear();
        Intent intent = new Intent(this, SelectImageAndMyVideoActivity.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }
}
