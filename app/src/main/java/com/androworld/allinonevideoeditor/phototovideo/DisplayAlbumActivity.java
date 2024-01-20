package com.androworld.allinonevideoeditor.phototovideo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.phototovideo.adapter.RecyclerAlbumGridAdapter;
import com.androworld.allinonevideoeditor.phototovideo.dbhelper.AssetsDataBaseHelper;
import com.androworld.allinonevideoeditor.phototovideo.model.AlbumImages;
import com.androworld.allinonevideoeditor.phototovideo.model.SelectBucketImage;
import com.androworld.allinonevideoeditor.phototovideo.tablayout.HomeTab;
import com.androworld.allinonevideoeditor.phototovideo.util.Utils;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

@SuppressLint({"WrongConstant"})
public class DisplayAlbumActivity extends AppCompatActivity {
    static final boolean j = true;
    RecyclerAlbumGridAdapter a;
    int b = 0;
    int c;
    int d = 0;
    AssetsDataBaseHelper e = null;
    LayoutManager f;
    ImageLoader g = ImageLoader.getInstance();
    Context h;
    RecyclerView i;


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.phototovideo_gridalbum);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Select Photo");
        toolbar.setTitle((int) R.string.app_name);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (j || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(j);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.h = this;
            this.i = (RecyclerView) findViewById(R.id.recycler_view);
            this.f = new GridLayoutManager(this.h, 3);
            this.i.setLayoutManager(this.f);
            this.c = getIntent().getIntExtra("bucketid", 0);
            b();
            a();
            return;
        }
        throw new AssertionError();
    }

    @Override public void onStop() {
        super.onStop();
        if (this.g != null) {
            this.g.clearDiskCache();
            this.g.clearMemoryCache();
        }
    }

    private void a() {
        int i2 = 0;
        while (true) {
            if (i2 >= Utils.imageUri.size()) {
                break;
            } else if (((SelectBucketImage) Utils.imageUri.get(i2)).bucketid.equals(Integer.valueOf(this.c))) {
                this.b = i2;
                break;
            } else {
                i2++;
            }
        }
        this.a = new RecyclerAlbumGridAdapter(this.h, this.c, this.g);
        this.i.setAdapter(this.a);
    }


    @Override public void onResume() {
        super.onResume();
        try {
            String str = ((SelectBucketImage) Utils.imageUri.get(this.b)).bucketid;
        } catch (Exception unused) {
            Intent intent = new Intent(this, HomeTab.class);
            intent.addFlags(335544320);
            startActivity(intent);
        }
    }

    @Override public void onBackPressed() {
        finish();
    }


    @Override public void onDestroy() {
        getWindow().clearFlags(128);
        super.onDestroy();
        if (this.g != null) {
            this.g.clearDiskCache();
            this.g.clearMemoryCache();
        }
    }

    private void b() {
        ImageLoaderConfiguration build = new Builder(getApplicationContext()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory(j).cacheOnDisk(j).bitmapConfig(Config.RGB_565).displayer(new FadeInBitmapDisplayer(400)).build()).build();
        this.g = ImageLoader.getInstance();
        this.g.init(build);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return j;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            int size = ((SelectBucketImage) Utils.imageUri.get(this.c)).imgUri.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                if (((AlbumImages) ((SelectBucketImage) Utils.imageUri.get(this.c)).imgUri.get(i3)).getImgPos() >= 0) {
                    i2++;
                }
            }
            ((SelectBucketImage) Utils.imageUri.get(this.c)).count = i2;
            finish();
            return j;
        }
        if (menuItem.getItemId() == R.id.Done) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
