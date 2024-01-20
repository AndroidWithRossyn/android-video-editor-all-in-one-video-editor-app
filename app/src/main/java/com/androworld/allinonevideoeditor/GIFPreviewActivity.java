package com.androworld.allinonevideoeditor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.androworld.allinonevideoeditor.videotogif.ListVideoAndMyAlbumActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.io.File;

@SuppressLint({"SetJavaScriptEnabled", "NewApi", "WrongConstant"})
public class GIFPreviewActivity extends AppCompatActivity {
    static final boolean h = true;
    File a;
    ImageView b;
    Handler c = new Handler();
    boolean d = false;
    ProgressDialog e;
    TextView f;
    Runnable g = new Runnable() {
        public void run() {
        }
    };

    public String i;
    private Ads ads;

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.gifpreviewactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView textView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        if (Helper.ModuleId == 7) {
            textView.setText("Image Preview");
        } else if (Helper.ModuleId == 12) {
            textView.setText("GIF Preview");
        }
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (h || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(h);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.b = (ImageView) findViewById(R.id.imgGif);
            this.f = (TextView) findViewById(R.id.Filename);
            Intent intent = getIntent();
            this.i = intent.getStringExtra("videourl");
            this.f.setText(new File(this.i).getName());
            this.d = intent.getBooleanExtra("isfrommain", false);
            if (this.i == null) {
                Intent intent2 = new Intent(this, ListVideoAndMyAlbumActivity.class);
                intent2.setFlags(67108864);
                startActivity(intent2);
                finish();
            }
            new DisplayMetrics();
            getResources().getDisplayMetrics();
            File file = new File(this.i);
            if (file.exists()) {
                file.getName();
            }
            this.a = new File(this.i);
            LinearLayout s = (LinearLayout) findViewById(R.id.banner_AdView);
            ads = new Ads();
            ads.BannerAd(GIFPreviewActivity.this,s);

        } else {
            throw new AssertionError();
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
            return false;
        }
        return h;
    }


    @Override public void onBackPressed() {
        Intent intent = new Intent(this, ListVideoAndMyAlbumActivity.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }


    @Override public void onDestroy() {
        getWindow().clearFlags(128);
        super.onDestroy();
    }

    public static int pxToDp(int i2) {
        return (int) (((float) i2) / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int i2) {
        return (int) (((float) i2) * Resources.getSystem().getDisplayMetrics().density);
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        pxToDp(displayMetrics.widthPixels);
        int pxToDp = pxToDp(displayMetrics.heightPixels) / 2;
        RequestManager with = Glide.with((Activity) this);
        StringBuilder sb = new StringBuilder();
        sb.append("file://");
        sb.append(this.a.getAbsolutePath().toString());
        with.load(sb.toString()).into(this.b);
    }


    public void onPause() {
        super.onPause();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_deleteshare, menu);
        return h;
    }


   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            Intent intent = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent.setFlags(67108864);
            startActivity(intent);
            finish();
            return h;
        }
        if (menuItem.getItemId() == R.id.Delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure,You want to delete?");
            builder.setPositiveButton("Yes", new OnClickListener() {
                @Override public void onClick(final DialogInterface dialogInterface, int i) {
                    File file = new File(GIFPreviewActivity.this.i);
                    if (file.exists()) {
                        file.delete();
                    }
                    ContentResolver contentResolver = GIFPreviewActivity.this.getContentResolver();
                    Uri uri = Media.EXTERNAL_CONTENT_URI;
                    StringBuilder sb = new StringBuilder();
                    sb.append("_data='");
                    sb.append(GIFPreviewActivity.this.i);
                    sb.append("'");
                    contentResolver.delete(uri, sb.toString(), null);
                    GIFPreviewActivity.this.e = new ProgressDialog(GIFPreviewActivity.this);
                    GIFPreviewActivity.this.e.setCancelable(false);
                    if (VERSION.SDK_INT <= 19) {
                        GIFPreviewActivity.this.e.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        GIFPreviewActivity.this.e.setMessage("Please wait...");
                    } else {
                        GIFPreviewActivity.this.e.setMessage(Html.fromHtml("<font color='#f45677'> Please wait... </font>"));
                    }
                    GIFPreviewActivity.this.e.show();
                    GIFPreviewActivity.this.c.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogInterface.dismiss();
                            onBackPressed();
                        }
                    }, 2000);
                }
            });
            builder.setNegativeButton("No", new OnClickListener() {
                @Override public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        } else if (menuItem.getItemId() == R.id.Share) {
            Uri photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", new File(this.i));

            Intent intent2 = new Intent("android.intent.action.SEND");
            intent2.putExtra("android.intent.extra.SUBJECT", "Video Maker");
            intent2.setType("video/*");
            intent2.putExtra("android.intent.extra.STREAM", photoURI);
            intent2.putExtra("android.intent.extra.TEXT", "video");
            startActivity(Intent.createChooser(intent2, "Where to Share?"));
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
