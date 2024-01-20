package com.androworld.allinonevideoeditor.phototovideo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.StartActivity;
import com.androworld.allinonevideoeditor.phototovideo.adapter.SelectionListAdapter;
import com.androworld.allinonevideoeditor.phototovideo.dbhelper.AssetsDataBaseHelper;
import com.androworld.allinonevideoeditor.phototovideo.tablayout.HomeTab;
import com.androworld.allinonevideoeditor.phototovideo.util.Utils;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.ads.dynamite.ModuleDescriptor;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.askerov.dynamicgrid.DynamicGridView;
import org.askerov.dynamicgrid.DynamicGridView.OnDragListener;
import org.askerov.dynamicgrid.DynamicGridView.OnDropListener;

import java.io.File;
import java.io.IOException;

@SuppressLint({"WrongConstant"})
public class SelectionListActivity extends AppCompatActivity {
    protected static final String TAG = "main";
    public static Activity act = null;
    static final boolean k = true;
    SelectionListAdapter a;
    AssetsDataBaseHelper b = null;
    boolean c = false;
    TextView d;
    ImageLoader e;
    boolean f = false;
    boolean g = k;
    boolean h;
    TextView i;
    ProgressDialog j = null;

    public DynamicGridView l;
    private AdView m;
    private Ads ads;

    class a extends AsyncTask<Void, Void, Boolean> {
        a() {
        }



        public Boolean doInBackground(Void... voidArr) {
            return Boolean.valueOf(SelectionListActivity.k);
        }



        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if (SelectionListActivity.this.j != null && SelectionListActivity.this.j.isShowing()) {
                SelectionListActivity.this.j.dismiss();
            }
            SelectionListActivity.this.startActivity(new Intent(SelectionListActivity.act, MoiveMakerActivity.class));
        }
    }

    class b extends AsyncTask<Void, Void, Boolean> {
        b() {
        }



        public Boolean doInBackground(Void... voidArr) {
            try {
                SelectionListActivity.this.b.getAllImageDetail();
                return Boolean.valueOf(SelectionListActivity.k);
            } catch (Exception unused) {
                return Boolean.valueOf(false);
            }
        }



        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if (bool.booleanValue()) {
                SelectionListActivity.this.a = new SelectionListAdapter(SelectionListActivity.this, Utils.createImageList, 2);
                SelectionListActivity.this.l.setAdapter((ListAdapter) SelectionListActivity.this.a);
                return;
            }
            Intent intent = new Intent(SelectionListActivity.this, StartActivity.class);
            intent.addFlags(335544320);
            SelectionListActivity.this.startActivity(intent);
        }
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.phototovideo_selectimagelist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView textView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        StringBuilder sb = new StringBuilder();
        sb.append("Arrange Photos(");
        sb.append(Utils.myUri.size());
        sb.append(")");
        textView.setText(sb.toString());
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (k || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(k);
            supportActionBar.setDisplayShowTitleEnabled(false);
            act = this;
            this.d = (TextView) findViewById(R.id.fit_tv);
            this.i = (TextView) findViewById(R.id.original_tv);
            this.d.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    SelectionListActivity.this.b();
                }
            });
            this.i.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    SelectionListActivity.this.a();
                }
            });
            c();
            this.l = (DynamicGridView) findViewById(R.id.dynamic_grid);
            this.a = new SelectionListAdapter(this, Utils.createImageList, 2);
            this.l.setAdapter((ListAdapter) this.a);
            this.l.setOnDragListener(new OnDragListener() {
                public void onDragStarted(int i) {
                    String str = SelectionListActivity.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("drag started at position ");
                    sb.append(i);
                    Log.d(str, sb.toString());
                }

                public void onDragPositionsChanged(int i, int i2) {
                    Log.d(SelectionListActivity.TAG, String.format("drag item position changed from %d to %d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
                    String str = (String) Utils.createImageList.get(i2);
                    Utils.createImageList.set(i2, (String) Utils.createImageList.get(i));
                    Utils.createImageList.set(i, str);
                    String str2 = (String) Utils.myUri.get(i2);
                    Utils.myUri.set(i2, (String) Utils.myUri.get(i));
                    Utils.myUri.set(i, str2);
                    SelectionListActivity.this.a.notifyDataSetChanged();
                }
            });
            this.l.setOnItemLongClickListener(new OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                    SelectionListActivity.this.l.startEditMode(i);
                    return SelectionListActivity.k;
                }
            });
            this.l.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    Toast.makeText(SelectionListActivity.this, "Long press to change position up/down", 0).show();
                }
            });
            this.l.setOnDropListener(new OnDropListener() {
                public void onActionDrop() {
                    SelectionListActivity.this.l.stopEditMode();
                }
            });
            this.h = k;
            ((ViewStub) findViewById(R.id.stub_import)).inflate();
            findViewById(R.id.panel_import).setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    SelectionListActivity.this.h = false;
                    view.setVisibility(8);
                    ((Button) SelectionListActivity.this.findViewById(R.id.btnOK)).setVisibility(8);
                }
            });
            final ImageView imageView = (ImageView) findViewById(R.id.ivUp);
            final ImageView imageView2 = (ImageView) findViewById(R.id.ivDown);
            final ImageView imageView3 = (ImageView) findViewById(R.id.ivImage);
            ((ImageView) findViewById(R.id.ivHand)).startAnimation(AnimationUtils.loadAnimation(act, R.anim.hand_come));
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    imageView.startAnimation(AnimationUtils.loadAnimation(SelectionListActivity.act, R.anim.arrow_fadein_out));
                    imageView2.startAnimation(AnimationUtils.loadAnimation(SelectionListActivity.act, R.anim.arrow_fadein_out));
                    imageView3.startAnimation(AnimationUtils.loadAnimation(SelectionListActivity.act, R.anim.image_vibrate));
                    if (SelectionListActivity.this.h) {
                        ((Button) SelectionListActivity.this.findViewById(R.id.btnOK)).setVisibility(0);
                    }
                    ((Button) SelectionListActivity.this.findViewById(R.id.btnOK)).setOnClickListener(new OnClickListener() {
                        @Override public void onClick(View view) {
                            SelectionListActivity.this.findViewById(R.id.panel_import).setVisibility(8);
                            view.setVisibility(8);
                            SelectionListActivity.this.h = false;
                        }
                    });
                }
            }, 1500);


            LinearLayout s = (LinearLayout) findViewById(R.id.banner_AdView);
            ads = new Ads();
            ads.BannerAd(SelectionListActivity.this,s);

        } else {
            throw new AssertionError();
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
            return false;
        }
        return k;
    }


    @SuppressLint({"ResourceAsColor"})
    public void a() {
        if (!this.g) {
            this.i.setTextColor(Color.parseColor("#000000"));
            this.i.setTextColor(Color.parseColor("#000000"));
            this.d.setTextColor(Color.parseColor("#FFFFFF"));
            this.a.setScaleType(ScaleType.CENTER_INSIDE);
            this.a.notifyDataSetInvalidated();
            this.g = k;
            this.c = false;
            this.a.notifyDataSetChanged();
        }
    }


    @SuppressLint({"ResourceAsColor"})
    public void b() {
        if (!this.c) {
            this.i.setTextColor(Color.parseColor("#FFFFFF"));
            this.d.setTextColor(Color.parseColor("#000000"));
            this.a.setScaleType(ScaleType.CENTER_CROP);
            this.a.notifyDataSetInvalidated();
            this.c = k;
            this.g = false;
            this.a.notifyDataSetChanged();
        }
    }


    @Override public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i3 == -1 && i2 == 99) {
            this.a.refreshlist();
            Toast.makeText(act, "Edit Image Successfully", 0).show();
            Utils.pos = -1;
        }
        if (i2 == 98 && this.a != null) {
            this.a.notifyDataSetChanged();
        }
    }


    @Override public void onResume() {
        super.onResume();
        this.f = false;
        if (this.c) {
            this.c = false;
            this.g = k;
        } else {
            this.c = false;
            this.g = k;
        }
        try {
            if (this.b == null) {
                this.b = new AssetsDataBaseHelper(getApplicationContext());
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (Utils.createImageList.size() <= 0) {
            new b().execute(new Void[0]);
        }
    }


    @Override public void onDestroy() {
        super.onDestroy();
        if (Utils.bitmap != null) {
            Utils.bitmap.recycle();
        }
    }

    private void c() {
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(getApplicationContext()).diskCacheExtraOptions(ModuleDescriptor.MODULE_VERSION, 480, null).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(k).considerExifParams(k).bitmapConfig(Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build()).build();
        this.e = ImageLoader.getInstance();
        this.e.init(build);
    }

    @Override public void onBackPressed() {
        if (this.h) {
            findViewById(R.id.panel_import).setVisibility(8);
            ((Button) findViewById(R.id.btnOK)).setVisibility(8);
            this.h = false;
            return;
        }
        super.onBackPressed();
        d();
        Intent intent = new Intent(getApplicationContext(), HomeTab.class);
        intent.addFlags(67108864);
        startActivity(intent);
    }

    private void d() {
        StringBuilder sb = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getPath()));
        sb.append("/");
        sb.append(getResources().getString(R.string.app_name));
        File file = new File(sb.toString());
        if (file.exists()) {
            String str = ".jpg";
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.getName().endsWith(str)) {
                        file2.delete();
                    }
                }
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb2.append("/");
        sb2.append(getResources().getString(R.string.MainFolderName));
        sb2.append("/");
        sb2.append(getResources().getString(R.string.PhotoToVideo));
        StringBuilder sb3 = new StringBuilder(String.valueOf(sb2.toString()));
        sb3.append("/");
        sb3.append("/temp");
        File file3 = new File(sb3.toString());
        if (file3.exists()) {
            String str2 = ".jpg";
            File[] listFiles2 = file3.listFiles();
            if (listFiles2 != null) {
                for (File file4 : listFiles2) {
                    if (file4.getName().endsWith(str2)) {
                        file4.delete();
                    }
                }
            }
        }
        if (file3.exists()) {
            file3.delete();
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return k;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            Intent intent = new Intent(this, StartActivity.class);
            intent.setFlags(67108864);
            startActivity(intent);
            finish();
            return k;
        }
        if (menuItem.getItemId() == R.id.Done && !this.f) {
            this.f = k;
            if (Utils.myUri.size() > 0) {
                this.j = new ProgressDialog(act);
                this.j.setMessage("Processing images...");
                this.j.setCancelable(false);
                this.j.show();
                if (this.c) {
                    new SaveImageAsync(this, k).execute(new Void[0]);
                } else {
                    new SaveImageAsync(this, false).execute(new Void[0]);
                }
                new a().execute(new Void[0]);
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
