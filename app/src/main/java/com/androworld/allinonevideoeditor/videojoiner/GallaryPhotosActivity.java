package com.androworld.allinonevideoeditor.videojoiner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.StartActivity;
import com.androworld.allinonevideoeditor.videojoiner.adapter.RecyclerAlbumGridAdapter;
import com.androworld.allinonevideoeditor.videojoiner.model.AlbumImages;
import com.androworld.allinonevideoeditor.videojoiner.model.SelectBucketImage;
import com.androworld.allinonevideoeditor.videojoiner.util.FileUtils;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

@SuppressLint({"WrongConstant"})
public class GallaryPhotosActivity extends AppCompatActivity {
    static final boolean j = true;
    GridView a;
    RecyclerAlbumGridAdapter b;
    int c = 0;
    int d;
    int e = 0;
    ArrayList<AlbumImages> f = new ArrayList<>();
    ImageLoader g = ImageLoader.getInstance();
    Context h;
    OnClickListener i = new OnClickListener() {
        @Override public void onClick(View view) {
            int size = ((SelectBucketImage) FileUtils.imageUri.get(GallaryPhotosActivity.this.d)).imgUri.size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                if (((AlbumImages) ((SelectBucketImage) FileUtils.imageUri.get(GallaryPhotosActivity.this.d)).imgUri.get(i2)).getImgPos() >= 0) {
                    i++;
                }
            }
            ((SelectBucketImage) FileUtils.imageUri.get(GallaryPhotosActivity.this.d)).count = i;
            GallaryPhotosActivity.this.finish();
        }
    };


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.gallaryphotosactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Select Video");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (j || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(j);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.h = this;
            this.a = (GridView) findViewById(R.id.GridViewPhoto);
            this.d = getIntent().getIntExtra("bucketid", 0);
            b();
            a();
            return;
        }
        throw new AssertionError();
    }

    private void a() {
        int i2 = 0;
        while (true) {
            if (i2 >= FileUtils.imageUri.size()) {
                break;
            } else if (((SelectBucketImage) FileUtils.imageUri.get(i2)).bucketid.equals(Integer.valueOf(this.d))) {
                this.c = i2;
                break;
            } else {
                i2++;
            }
        }
        this.b = new RecyclerAlbumGridAdapter(this.h, this.d, this.g);
        this.a.setAdapter(this.b);
    }


    @Override public void onResume() {
        super.onResume();
        try {
            String str = ((SelectBucketImage) FileUtils.imageUri.get(this.c)).bucketid;
        } catch (Exception unused) {
            Intent intent = new Intent(this, StartActivity.class);
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
            finish();
            return j;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (FileUtils.myUri.size() > 1) {
                Intent intent = new Intent(this, VideoJoinerActivity.class);
                intent.addFlags(335544320);
                startActivity(intent);
            }
            Toast.makeText(getApplicationContext(), "Select Maximum two Videos", 0).show();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
