package com.androworld.allinonevideoeditor.phototovideo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore.Audio.Media;
import android.provider.MediaStore.Images;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androworld.allinonevideoeditor.Adclick;
import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.UtilCommand;
import com.androworld.allinonevideoeditor.VideoPlayer;
import com.androworld.allinonevideoeditor.phototovideo.model.FramePointSet;
import com.androworld.allinonevideoeditor.phototovideo.model.MusicModel;
import com.androworld.allinonevideoeditor.phototovideo.util.BitmapCompression;
import com.androworld.allinonevideoeditor.phototovideo.util.HsItem;
import com.androworld.allinonevideoeditor.phototovideo.util.Utils;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

@SuppressLint({"WrongConstant"})
public class MoiveMakerActivity extends AppCompatActivity implements OnSeekBarChangeListener {


    Animation Rs;
    static final boolean aj = true;
    public static int currentSeek;
    public static Activity mContext;
    int A;
    ArrayList<HsItem> B = new ArrayList<>();
    LinearLayout C;
    LinearLayout D;
    RelativeLayout E;
    MediaPlayer F = null;
    String G = null;
    String[] H;
    DisplayImageOptions I;
    RecyclerView J;
    SeekBar K;
    SeekBar L;
    SeekBar M;
    Button N;
    Button O;
    ImageView P;
    ImageView Q;
    TextView S;
    TextView T;
    TextView U;
    TextView V;
    TextView W;
    TextView X;
    TextView Y;
    String Z = "2";
    HorizontalScrollView a;
    String aa = "";
    Runnable ab = new Runnable() {
        public void run() {
            int progress = MoiveMakerActivity.this.L.getProgress();
            Log.d("textView", new StringBuilder(String.valueOf(progress)).toString());
            MoiveMakerActivity.this.L.setProgress(progress + 100);
            MoiveMakerActivity.this.n.postDelayed(this, 100);
        }
    };
    OnClickListener ac = new d();
    OnClickListener ad = new e();
    public boolean addEffectThumbsAsync;
    OnClickListener ae = new f();
    OnClickListener af = new g();
    OnClickListener ag = new c();
    OnClickListener ah = new OnClickListener() {
        @Override
        public void onClick(View view) {
            MoiveMakerActivity.this.c = false;
            MoiveMakerActivity.this.u = -1;
            Utils.audioSelected = -1;
            MoiveMakerActivity.this.a(MoiveMakerActivity.this.P, MoiveMakerActivity.this.u, false);
            MoiveMakerActivity.this.r = false;
            if (MoiveMakerActivity.this.F != null) {
                MoiveMakerActivity.this.F.stop();
            }
            MoiveMakerActivity.this.F = null;
            MoiveMakerActivity.this.G = null;
            MoiveMakerActivity.this.E.setVisibility(8);
            MoiveMakerActivity.this.N.setVisibility(0);
        }
    };
    AnimationListener ai = new AnimationListener() {
        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            MoiveMakerActivity.this.j.setVisibility(8);
        }
    };

    public boolean ak = aj;

    public SelectMusicAdapter al;
    float b;
    boolean c = false;
    ArrayList<Bitmap> d = new ArrayList<>();
    Button e;
    Button f;
    Button g;
    ArrayList<String> h;
    FrameLayout i;
    FrameLayout j;
    b k;
    Handler l = new Handler();
    ArrayList<FrameLayout> m = new ArrayList<>();
    Handler n = new Handler();
    ImageLoader o;
    boolean p = false;
    boolean q;
    boolean r = false;
    boolean s = false;
    ArrayList<LinearLayout> t;
    int u = -1;
    int v;
    int w = 0;
    int x;
    int y;
    int z;
    private Ads ads;

    public static class MyFileNameFilter implements FilenameFilter {
        public boolean accept(File file, String str) {
            return str.toLowerCase().startsWith("slide_00");
        }
    }

    class a extends AsyncTask<Void, Void, Boolean> {
        a() {
        }


        public Boolean doInBackground(Void... voidArr) {
            MoiveMakerActivity moiveMakerActivity = MoiveMakerActivity.this;
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb.append("/");
            sb.append(MoiveMakerActivity.this.getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(MoiveMakerActivity.this.getResources().getString(R.string.PhotoToVideo));
            StringBuilder sb2 = new StringBuilder(String.valueOf(sb.toString()));
            sb2.append("/temp");
            moiveMakerActivity.a(sb2.toString());
            int size = MoiveMakerActivity.this.h.size();
            int i = 0;
            while (i < size) {
                Options options = new Options();
                options.inJustDecodeBounds = MoiveMakerActivity.aj;
                BitmapFactory.decodeFile((String) MoiveMakerActivity.this.h.get(i), options);
                options.inSampleSize = BitmapCompression.calculateInSampleSize(options, MoiveMakerActivity.this.z, MoiveMakerActivity.this.z);
                options.inJustDecodeBounds = false;
                Utils.bitmap = BitmapFactory.decodeFile((String) MoiveMakerActivity.this.h.get(i), options);
                if (Utils.framePostion > -1 && Utils.filterIndex > -1) {
                    Utils.bitmap = MoiveMakerActivity.this.a((boolean) MoiveMakerActivity.aj, (boolean) MoiveMakerActivity.aj);
                } else if (Utils.framePostion > -1) {
                    Utils.bitmap = MoiveMakerActivity.this.a((boolean) MoiveMakerActivity.aj, false);
                } else if (Utils.filterIndex > -1) {
                    Utils.bitmap = MoiveMakerActivity.this.a(false, (boolean) MoiveMakerActivity.aj);
                } else {
                    Utils.bitmap = MoiveMakerActivity.this.a(false, false);
                }
                i++;
                MoiveMakerActivity.this.a(i, (boolean) MoiveMakerActivity.aj);
            }
            Options options2 = new Options();
            options2.inJustDecodeBounds = MoiveMakerActivity.aj;
            BitmapFactory.decodeFile((String) MoiveMakerActivity.this.h.get(0), options2);
            options2.inSampleSize = BitmapCompression.calculateInSampleSize(options2, MoiveMakerActivity.this.z, MoiveMakerActivity.this.z);
            options2.inJustDecodeBounds = false;
            Utils.bitmap = BitmapFactory.decodeFile((String) MoiveMakerActivity.this.h.get(0), options2);
            if (Utils.framePostion > -1 && Utils.filterIndex > -1) {
                Utils.bitmap = MoiveMakerActivity.this.a((boolean) MoiveMakerActivity.aj, (boolean) MoiveMakerActivity.aj);
            } else if (Utils.framePostion > -1) {
                Utils.bitmap = MoiveMakerActivity.this.a((boolean) MoiveMakerActivity.aj, false);
            } else if (Utils.filterIndex > -1) {
                Utils.bitmap = MoiveMakerActivity.this.a(false, (boolean) MoiveMakerActivity.aj);
            } else {
                Utils.bitmap = MoiveMakerActivity.this.a(false, false);
            }
            MoiveMakerActivity.this.a(0, (boolean) MoiveMakerActivity.aj);
            return null;
        }


        public void onPostExecute(Boolean bool) {
            MoiveMakerActivity.this.ffmpegcommand();
        }
    }

    private class b extends AsyncTask<Void, Void, Boolean> {


        public void onPostExecute(Boolean bool) {
        }

        private b() {
        }


        public void onPreExecute() {
            super.onPreExecute();
            MoiveMakerActivity.this.addEffectThumbsAsync = false;
            MoiveMakerActivity.this.t = new ArrayList<>();
            MoiveMakerActivity.this.B.clear();
        }


        public Boolean doInBackground(Void... voidArr) {
            String[] strArr;
            File[] listFiles;
            MoiveMakerActivity.this.H = MoiveMakerActivity.this.b("frame");
            for (String str : MoiveMakerActivity.this.H) {
                ArrayList<HsItem> arrayList = MoiveMakerActivity.this.B;
                StringBuilder sb = new StringBuilder();
                sb.append("assets://");
                sb.append(str);
                arrayList.add(new HsItem(sb.toString(), MoiveMakerActivity.aj));
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(MoiveMakerActivity.this.getFilesDir());
            sb2.append("/VideoFrame");
            File file = new File(sb2.toString());
            if (!file.exists()) {
                file.mkdirs();
            }
            int i = 0;
            for (File file2 : file.listFiles()) {
                if (Utils.isFromOnlineFrame && file2.getAbsolutePath().equals(Utils.onlineFramePath)) {
                    Utils.framePostion = i;
                    MoiveMakerActivity.this.x = MoiveMakerActivity.this.H.length + i;
                }
                ArrayList<HsItem> arrayList2 = MoiveMakerActivity.this.B;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("file://");
                sb3.append(file2.getAbsolutePath());
                sb3.append("/frame.png");
                arrayList2.add(new HsItem(sb3.toString(), MoiveMakerActivity.aj));
                i++;
            }
            if (Utils.isFromOnlineFrame) {
                MoiveMakerActivity.this.g();
                Utils.isFromOnlineFrame = false;
            }
            int size = MoiveMakerActivity.this.B.size();
            for (int i2 = 0; i2 < size; i2++) {
                final int i1 = i2;
                if (!MoiveMakerActivity.this.k.isCancelled()) {
                    MoiveMakerActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            LinearLayout linearLayout = new LinearLayout(MoiveMakerActivity.this.getApplicationContext());
                            linearLayout.setLayoutParams(new LayoutParams(-2, -1));
                            linearLayout.setGravity(17);
                            linearLayout.setPadding(5, 5, 5, 5);
                            View inflate = MoiveMakerActivity.this.getLayoutInflater().inflate(R.layout.lay_thumb, null);
                            LinearLayout linearLayout2 = (LinearLayout) inflate.findViewById(R.id.llThumb);
                            ImageView imageView = (ImageView) inflate.findViewById(R.id.ivThumb);
                            TextView textView = (TextView) inflate.findViewById(R.id.tvEffectName);
                            textView.setText("");
                            textView.setLayoutParams(new LayoutParams(1, 1));
                            MoiveMakerActivity.this.o.displayImage(((HsItem) MoiveMakerActivity.this.B.get(i1)).path, imageView, MoiveMakerActivity.this.I);
                            linearLayout2.setTag(Integer.valueOf(i1));
                            linearLayout2.setOnClickListener(MoiveMakerActivity.this.ac);
                            MoiveMakerActivity.this.t.add(linearLayout2);
                            linearLayout.addView(inflate);
                            MoiveMakerActivity.this.C.addView(linearLayout);
                            MoiveMakerActivity.this.m.add((FrameLayout) imageView.getParent());
                            if (i1 == MoiveMakerActivity.this.x) {
                                ((FrameLayout) imageView.getParent()).setBackgroundResource(R.drawable.selectframe);
                            }
                        }
                    });
                }
            }
            return Boolean.valueOf(MoiveMakerActivity.aj);
        }
    }

    class c implements OnClickListener {
        c() {
        }

        @Override
        public void onClick(View view) {
            MoiveMakerActivity.this.O.setBackgroundResource(R.drawable.play2);
            MoiveMakerActivity.this.a.setVisibility(8);
            MoiveMakerActivity.this.D.setVisibility(8);
            if (MoiveMakerActivity.this.c) {
                MoiveMakerActivity.this.E.setVisibility(0);
                return;
            }
            MoiveMakerActivity.this.q = false;
            if (MoiveMakerActivity.this.ak) {
                Toast.makeText(MoiveMakerActivity.mContext, "MyAudio list is loading..try again afater some seconds", Toast.LENGTH_LONG).show();
                return;
            }
            MoiveMakerActivity.this.f.setClickable(MoiveMakerActivity.aj);
            try {
                if (MoiveMakerActivity.this.F != null) {
                    MoiveMakerActivity.this.F.pause();
                }
                if (MoiveMakerActivity.this.r) {
                    MoiveMakerActivity.this.u = -1;
                    MoiveMakerActivity.this.a(MoiveMakerActivity.this.P, MoiveMakerActivity.this.u, false);
                    MoiveMakerActivity.this.r = false;
                    MoiveMakerActivity.this.n.removeCallbacks(MoiveMakerActivity.this.ab);
                    MoiveMakerActivity.this.L.setProgress(0);
                }
            } catch (Exception unused) {
            }
            if (MoiveMakerActivity.this.j.getVisibility() != 0) {
                MoiveMakerActivity.this.j();
                MoiveMakerActivity.this.N.setBackgroundResource(R.drawable.music_normal);
                MoiveMakerActivity.this.f();
                MoiveMakerActivity.this.al.notifyDataSetChanged();
                MoiveMakerActivity.this.j.setVisibility(0);
                if (MoiveMakerActivity.this.ak) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new MusicModel("", ""));
                    MoiveMakerActivity.this.J.setAdapter(new SelectMusicAdapter(MoiveMakerActivity.mContext, arrayList));
                    MoiveMakerActivity.this.J.setAdapter(MoiveMakerActivity.this.al);
                    return;
                }
                return;
            }
            MoiveMakerActivity.this.N.setBackgroundResource(R.drawable.music_normal);
        }
    }

    class d implements OnClickListener {
        d() {
        }

        @Override
        public void onClick(View view) {
            int i = 0;
            while (i < MoiveMakerActivity.this.t.size()) {
                if (view == MoiveMakerActivity.this.t.get(i)) {
                    MoiveMakerActivity.this.x = i;
                    for (int i2 = 0; i2 < MoiveMakerActivity.this.m.size(); i2++) {
                        if (i2 == MoiveMakerActivity.this.x) {
                            ((FrameLayout) MoiveMakerActivity.this.m.get(i2)).setBackgroundResource(R.drawable.selected_frame);
                        } else {
                            ((FrameLayout) MoiveMakerActivity.this.m.get(i2)).setBackgroundResource(R.drawable.filter_border);
                        }
                    }
                    MoiveMakerActivity.this.O.setBackgroundResource(R.drawable.play2);
                    if (MoiveMakerActivity.this.r) {
                        MoiveMakerActivity.this.u = -1;
                        MoiveMakerActivity.this.a(MoiveMakerActivity.this.P, MoiveMakerActivity.this.u, false);
                        MoiveMakerActivity.this.r = false;
                        MoiveMakerActivity.this.n.removeCallbacks(MoiveMakerActivity.this.ab);
                        MoiveMakerActivity.this.L.setProgress(0);
                    }
                    if (MoiveMakerActivity.this.F != null) {
                        MoiveMakerActivity.this.F.pause();
                        MoiveMakerActivity.this.F.seekTo(MoiveMakerActivity.this.w);
                    }
                    if (i == 0) {
                        Utils.framePostion = -1;
                        MoiveMakerActivity.this.P.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                        MoiveMakerActivity.this.P.setScaleType(ScaleType.FIT_CENTER);
                        MoiveMakerActivity.this.Q.setImageBitmap(null);
                        return;
                    }
                    Utils.framePostion = i;
                    HsItem hsItem = (HsItem) MoiveMakerActivity.this.B.get(i);
                    if (hsItem.isAvailable) {
                        FramePointSet a2 = MoiveMakerActivity.this.a(MoiveMakerActivity.this.i.getWidth(), MoiveMakerActivity.this.i.getHeight());
                        if (a2 == null) {
                            MoiveMakerActivity.this.P.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                            MoiveMakerActivity.this.P.setScaleType(ScaleType.FIT_CENTER);
                        } else {
                            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(a2.width, a2.height);
                            layoutParams.setMargins(a2.left, a2.top, 0, 0);
                            MoiveMakerActivity.this.P.setLayoutParams(layoutParams);
                            MoiveMakerActivity.this.P.setScaleType(ScaleType.FIT_XY);
                        }
                        try {
                            MoiveMakerActivity.this.Q.setImageBitmap(BitmapCompression.decodeSampledBitmapFromAssets(MoiveMakerActivity.mContext, hsItem.path.replace("assets://", ""), Utils.width, Utils.width));
                            return;
                        } catch (IOException unused) {
                            MoiveMakerActivity.this.g();
                            return;
                        }
                    } else {
                        return;
                    }
                } else {
                    i++;
                }
            }
        }
    }

    class e implements OnClickListener {
        e() {
        }

        @Override
        public void onClick(View view) {
            MoiveMakerActivity.this.O.setBackgroundResource(R.drawable.play2);
            view.setClickable(false);
            MoiveMakerActivity.this.a.setVisibility(0);
            MoiveMakerActivity.this.j.setVisibility(8);
            MoiveMakerActivity.this.D.setVisibility(8);
            MoiveMakerActivity.this.E.setVisibility(8);
            if (MoiveMakerActivity.this.r) {
                MoiveMakerActivity.this.u = -1;
                MoiveMakerActivity.this.a(MoiveMakerActivity.this.P, MoiveMakerActivity.this.u, false);
                MoiveMakerActivity.this.r = false;
                MoiveMakerActivity.this.n.removeCallbacks(MoiveMakerActivity.this.ab);
                MoiveMakerActivity.this.L.setProgress(0);
            }
            if (MoiveMakerActivity.this.F != null) {
                MoiveMakerActivity.this.F.pause();
                MoiveMakerActivity.this.F.seekTo(MoiveMakerActivity.this.w);
            }
            MoiveMakerActivity.this.j();
            MoiveMakerActivity.this.q = false;
            MoiveMakerActivity.this.f.setBackgroundResource(R.drawable.frame_presed);
        }
    }

    class f implements OnClickListener {
        f() {
        }

        @Override
        public void onClick(View view) {
            try {
                MoiveMakerActivity.this.p = false;
                if (!MoiveMakerActivity.this.r) {
                    MoiveMakerActivity.this.O.setBackgroundResource(R.drawable.pause2);
                    if (MoiveMakerActivity.this.d.size() <= 0) {
                        MoiveMakerActivity.this.h();
                    }
                    if (MoiveMakerActivity.this.d.size() > 0) {
                        MoiveMakerActivity.this.P.setImageBitmap((Bitmap) MoiveMakerActivity.this.d.get(0));
                        MoiveMakerActivity.this.a(MoiveMakerActivity.this.P, MoiveMakerActivity.this.u + 1, false);
                        if (MoiveMakerActivity.this.F != null) {
                            MoiveMakerActivity.this.F.start();
                        }
                        MoiveMakerActivity.this.L.setMax(Math.round(MoiveMakerActivity.this.b * 1000.0f));
                        MoiveMakerActivity.this.L.setProgress(0);
                        MoiveMakerActivity.this.n.postDelayed(MoiveMakerActivity.this.ab, 100);
                    } else {
                        Toast.makeText(MoiveMakerActivity.this.getApplicationContext(), "image copy failed", 0).show();
                    }
                } else if (MoiveMakerActivity.this.d.size() > 0) {
                    MoiveMakerActivity.this.O.setBackgroundResource(R.drawable.play2);
                    MoiveMakerActivity.this.a(MoiveMakerActivity.this.P, MoiveMakerActivity.this.u, false);
                    MoiveMakerActivity.this.n.removeCallbacks(MoiveMakerActivity.this.ab);
                    MoiveMakerActivity.this.L.setProgress(0);
                    if (MoiveMakerActivity.this.F != null && MoiveMakerActivity.this.F.isPlaying()) {
                        MoiveMakerActivity.currentSeek = MoiveMakerActivity.this.F.getCurrentPosition();
                        MoiveMakerActivity.this.F.pause();
                    }
                }
                MoiveMakerActivity.this.r ^= MoiveMakerActivity.aj;
            } catch (Exception unused) {
            }
        }
    }

    class g implements OnClickListener {
        g() {
        }

        @Override
        public void onClick(View view) {
            MoiveMakerActivity.this.O.setBackgroundResource(R.drawable.play2);
            MoiveMakerActivity.this.q = false;
            MoiveMakerActivity.this.a.setVisibility(8);
            MoiveMakerActivity.this.f.setClickable(MoiveMakerActivity.aj);
            MoiveMakerActivity.this.D.setVisibility(0);
            MoiveMakerActivity.this.E.setVisibility(8);
            MoiveMakerActivity.this.j.setVisibility(8);
            if (MoiveMakerActivity.this.r) {
                MoiveMakerActivity.this.u = -1;
                MoiveMakerActivity.this.a(MoiveMakerActivity.this.P, MoiveMakerActivity.this.u, false);
                MoiveMakerActivity.this.r = false;
                MoiveMakerActivity.this.n.removeCallbacks(MoiveMakerActivity.this.ab);
                MoiveMakerActivity.this.L.setProgress(0);
            }
            if (MoiveMakerActivity.this.F != null) {
                MoiveMakerActivity.this.F.pause();
                MoiveMakerActivity.this.F.seekTo(MoiveMakerActivity.this.w);
            }
            MoiveMakerActivity.this.j();
            MoiveMakerActivity.this.g.setBackgroundResource(R.drawable.time_normal);
        }
    }


    @SuppressLint({"StaticFieldLeak"})
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.s = false;
        setContentView(R.layout.phototovideo_moviemaker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView textView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        StringBuilder sb = new StringBuilder();
        sb.append("Preview(");
        sb.append(Utils.myUri.size());
        sb.append(" photos)");
        textView.setText(sb.toString());
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (aj || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(aj);
            supportActionBar.setDisplayShowTitleEnabled(false);
            Utils.filterIndex = -1;
            Utils.framePostion = -1;
            Utils.audioSelected = -1;
            mContext = this;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            Utils.width = displayMetrics.widthPixels;
            Utils.height = displayMetrics.heightPixels;
            this.j = (FrameLayout) findViewById(R.id.flRecycle);
            ImageLoader.getInstance().init(new Builder(this).defaultDisplayImageOptions(this.I).memoryCache(new WeakMemoryCache()).build());
            this.o = ImageLoader.getInstance();
            e();
            this.Rs = AnimationUtils.loadAnimation(mContext, R.anim.slide_top_out);
            this.Rs.setAnimationListener(this.ai);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb2.append("/");
            sb2.append(getResources().getString(R.string.MainFolderName));
            sb2.append("/");
            sb2.append(getResources().getString(R.string.PhotoToVideo));
            StringBuilder sb3 = new StringBuilder(String.valueOf(sb2.toString()));
            sb3.append("/");
            sb3.append("/temp");
            sb3.append("/slide_00001.jpg");
            File file = new File(sb3.toString());
            if (file.exists()) {
                this.P.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            }
            this.A = Utils.width - ((Utils.width * 15) / 480);
            this.k = new b();
            this.k.execute(new Void[0]);
            this.J = (RecyclerView) findViewById(R.id.recyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(1);
            linearLayoutManager.scrollToPosition(0);
            this.J.setLayoutManager(linearLayoutManager);
            this.J.setHasFixedSize(false);
            new AsyncTask<Void, Void, Void>() {


                public Void doInBackground(Void... voidArr) {
                    MoiveMakerActivity.this.ak = MoiveMakerActivity.aj;
                    MoiveMakerActivity.this.al = new SelectMusicAdapter(MoiveMakerActivity.this, MoiveMakerActivity.this.d());
                    return null;
                }


                public void onPostExecute(Void voidR) {
                    super.onPostExecute(voidR);
                    MoiveMakerActivity.this.ak = false;
                    ViewGroup.LayoutParams layoutParams = MoiveMakerActivity.this.j.getLayoutParams();
                    layoutParams.width = Utils.width;
                    Log.d("Size of array", new StringBuilder(String.valueOf(MoiveMakerActivity.this.v)).toString());
                    if (MoiveMakerActivity.this.v * 80 >= Utils.height / 2) {
                        layoutParams.height = Utils.height / 2;
                    } else {
                        layoutParams.height = MoiveMakerActivity.this.v * 80;
                    }
                    MoiveMakerActivity.this.j.setLayoutParams(layoutParams);
                    MoiveMakerActivity.this.J.setAdapter(MoiveMakerActivity.this.al);
                }
            }.execute(new Void[0]);
            ads = new Ads();
            ads.Interstitialload(this);
            return;
        }
        throw new AssertionError();
    }


    public void b() {
        ads.showInd(new Adclick() {
            @Override
            public void onclicl() {
                c();
            }
        });
    }


    public void c() {
        Intent intent = new Intent(getApplicationContext(), VideoPlayer.class);
        intent.setFlags(67108864);
        intent.putExtra("song", this.aa);
        startActivity(intent);
        finish();
    }

    @SuppressLint({"SimpleDateFormat"})
    public void ffmpegcommand() {
        String str;
        String[] strArr;
        String format = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(Calendar.getInstance().getTime());
        if (Utils.filterIndex > -1 || Utils.framePostion > -1) {
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb2.append("/");
            sb2.append(getResources().getString(R.string.MainFolderName));
            sb2.append("/");
            sb2.append(getResources().getString(R.string.PhotoToVideo));
            sb.append(String.valueOf(sb2.toString()));
            sb.append("/temp");
            str = sb.toString();
        } else {
            StringBuilder sb3 = new StringBuilder();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb4.append("/");
            sb4.append(getResources().getString(R.string.MainFolderName));
            sb4.append("/");
            sb4.append(getResources().getString(R.string.PhotoToVideo));
            sb3.append(String.valueOf(sb4.toString()));
            sb3.append("/temp");
            str = sb3.toString();
        }
        StringBuilder sb5 = new StringBuilder();
        StringBuilder sb6 = new StringBuilder();
        sb6.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb6.append("/");
        sb6.append(getResources().getString(R.string.MainFolderName));
        sb6.append("/");
        sb6.append(getResources().getString(R.string.PhotoToVideo));
        sb5.append(String.valueOf(sb6.toString()));
        sb5.append("/video_");
        sb5.append(format);
        sb5.append(".mp4");
        this.aa = sb5.toString();
        if (!this.c) {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("1/");
            sb7.append(this.Z);
            StringBuilder sb8 = new StringBuilder();
            sb8.append(String.valueOf(str));
            sb8.append("/slide_%05d.jpg");
            strArr = new String[]{"-y", "-r", sb7.toString(), "-i", sb8.toString(), "-vcodec", "libx264", "-r", "2", "-pix_fmt", "yuv420p", "-preset", "ultrafast", this.aa};
            Log.d("Command", Arrays.toString(strArr));
        } else if (this.F.getDuration() - this.w < Math.round(this.b) * 1000) {
            StringBuilder sb9 = new StringBuilder();
            sb9.append("1/");
            sb9.append(this.Z);
            StringBuilder sb10 = new StringBuilder();
            sb10.append(String.valueOf(str));
            sb10.append("/slide_%05d.jpg");
            strArr = new String[]{"-y", "-r", sb9.toString(), "-i", sb10.toString(), "-ss", String.valueOf(this.w / 1000), "-i", this.G, "-map", "0:0", "-map", "1:0", "-vcodec", "libx264", "-acodec", "aac", "-r", "2", "-pix_fmt", "yuv420p", "-strict", "experimental", "-preset", "ultrafast", this.aa};
            Log.d("Command", Arrays.toString(strArr));
        } else {
            StringBuilder sb11 = new StringBuilder();
            sb11.append("1/");
            sb11.append(this.Z);
            StringBuilder sb12 = new StringBuilder();
            sb12.append(String.valueOf(str));
            sb12.append("/slide_%05d.jpg");
            strArr = new String[]{"-hide_banner","-y", "-r", sb11.toString(), "-i", sb12.toString(), "-ss", String.valueOf(this.w / 1000), "-i", this.G, "-map", "0:0", "-map", "1:0", "-vcodec", "libx264", "-acodec", "aac", "-r", "2", "-pix_fmt", "yuv420p", "-strict", "experimental", "-shortest", "-preset", "ultrafast", this.aa};
            Log.e("commandss", Arrays.toString(strArr));
        }
        a(strArr, this.aa);
    }

    private void a(String[] strArr, final String str) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        String ffmpegCommand = UtilCommand.main(strArr);
        Log.e("commandss", "" + ffmpegCommand);
        FFmpeg.executeAsync(ffmpegCommand, new ExecuteCallback() {

            @Override
            public void apply(final long executionId, final int returnCode) {
                Log.e("TAGsss", String.format("FFmpeg process exited with rc %d.", returnCode));

                Log.e("TAGsss", "FFmpeg process output:"+returnCode);

                com.arthenica.mobileffmpeg.Config.printLastCommandOutput(Log.INFO);
                com.arthenica.mobileffmpeg.Config.printLastCommandOutput(Log.ERROR);

                progressDialog.dismiss();

                if (returnCode == RETURN_CODE_SUCCESS) {
                    MoiveMakerActivity.this.m();
                    MoiveMakerActivity.this.k();
                    MoiveMakerActivity.this.i();
                    MoiveMakerActivity.this.l();
                    MoiveMakerActivity.this.refreshGallery(str);

                    progressDialog.dismiss();
                    MoiveMakerActivity.this.s = false;
                    MoiveMakerActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(MoiveMakerActivity.this.aa))));
                    MoiveMakerActivity.this.b();
                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        MoiveMakerActivity.this.deleteFromGallery(str);
                        Toast.makeText(MoiveMakerActivity.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                } else {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        MoiveMakerActivity.this.deleteFromGallery(str);
                        Toast.makeText(MoiveMakerActivity.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }


            }
        });

        getWindow().clearFlags(16);
    }


    public void onPause() {
        super.onPause();
    }


    public ArrayList<MusicModel> d() {
        ArrayList<MusicModel> arrayList = new ArrayList<>();
        Cursor managedQuery = managedQuery(Media.EXTERNAL_CONTENT_URI, new String[]{"_display_name", "_data"}, null, null, "LOWER(title) ASC");
        if (managedQuery != null) {
            if (managedQuery.moveToFirst()) {
                do {
                    String string = managedQuery.getString(0);
                    if (string != null) {
                        String fileExt = getFileExt(string);
                        if ((fileExt != null && fileExt.contains("mp3")) || fileExt.contains("a")) {
                            arrayList.add(new MusicModel(string, managedQuery.getString(1)));
                        }
                    }
                } while (managedQuery.moveToNext());
            }
            this.v = arrayList.size();
        }
        return arrayList;
    }

    public static String getFileExt(String str) {
        try {
            return str.length() > 3 ? str.substring(str.lastIndexOf(".") + 1, str.length()) : str;
        } catch (Exception unused) {
            return str;
        }
    }

    private void e() {
        this.W = (TextView) findViewById(R.id.tvSeekUserSec);
        this.Y = (TextView) findViewById(R.id.tvEndVideo1);
        this.L = (SeekBar) findViewById(R.id.sbVideo);
        this.X = (TextView) findViewById(R.id.tvInterval);
        this.K = (SeekBar) findViewById(R.id.sbInterval);
        this.K.setOnSeekBarChangeListener(this);
        this.K.setProgress(20);
        this.K.setMax(100);
        this.P = (ImageView) findViewById(R.id.slide_1);
        this.Q = (ImageView) findViewById(R.id.slide_frame);
        if (Utils.isFromOnlineFrame) {
            ImageView imageView = this.Q;
            StringBuilder sb = new StringBuilder();
            sb.append(Utils.onlineFramePath);
            sb.append("/frame.png");
            imageView.setImageBitmap(BitmapCompression.getResizedBitmap(BitmapFactory.decodeFile(sb.toString()), Utils.width, Utils.width));
        }
        this.O = (Button) findViewById(R.id.btnPlay);
        this.P.setOnClickListener(this.ae);
        this.O.setOnClickListener(this.ae);
        this.i = (FrameLayout) findViewById(R.id.flPreview);
        this.N = (Button) findViewById(R.id.selectmusic);
        this.N.setOnClickListener(this.ag);
        this.T = (TextView) findViewById(R.id.tvMusicTrackName);
        this.V = (TextView) findViewById(R.id.tvTrackDuration);
        this.E = (RelativeLayout) findViewById(R.id.llSelctedMusic);
        this.e = (Button) findViewById(R.id.btnCloseMusic);
        this.e.setOnClickListener(this.ah);
        this.f = (Button) findViewById(R.id.btnFrame);
        this.f.setOnClickListener(this.ad);
        this.g = (Button) findViewById(R.id.btnSecond);
        this.g.setOnClickListener(this.af);
        this.C = (LinearLayout) findViewById(R.id.llFrameList);
        this.a = (HorizontalScrollView) findViewById(R.id.hsFrameList);
        this.M = (SeekBar) findViewById(R.id.sbMusic);
        this.M.setOnSeekBarChangeListener(this);
        this.U = (TextView) findViewById(R.id.tvStartVideo);
        this.S = (TextView) findViewById(R.id.tvEndVideo);
        this.D = (LinearLayout) findViewById(R.id.llSeekbar);
    }


    public void f() {
        this.D.setVisibility(8);
        this.E.setVisibility(8);
    }


    public void g() {
        final HsItem hsItem = (HsItem) this.B.get(this.x);
        runOnUiThread(new Runnable() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                sb.append("Utils w: ");
                sb.append(Utils.width);
                Log.d("word", sb.toString());
                MoiveMakerActivity.this.Q.setImageBitmap(BitmapCompression.decodeSampledBitmapFromLocal(MoiveMakerActivity.mContext, hsItem.path.replace("file:", "").replace("thumb", "frame"), Utils.width, Utils.width));
            }
        });
        StringBuilder sb = new StringBuilder(String.valueOf(new File(hsItem.path).getParentFile().getPath()));
        sb.append("/dimen.txt");
        String replace = sb.toString().replace("file:", "");
        FramePointSet framePointSet = new FramePointSet();
        framePointSet.left = 0;
        framePointSet.top = 0;
        framePointSet.width = Utils.width;
        framePointSet.height = Utils.height;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("fps w: ");
        sb2.append(Utils.width);
        Log.d("word", sb2.toString());
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(replace)));
            int[] intArray = toIntArray(bufferedReader.readLine());
            StringBuilder sb3 = new StringBuilder();
            sb3.append("flpreview width: ");
            sb3.append(this.i.getHeight());
            Log.d("word", sb3.toString());
            framePointSet.left = (this.A * intArray[0]) / 100;
            framePointSet.top = (this.A * intArray[1]) / 100;
            int i2 = (this.A * intArray[3]) / 100;
            framePointSet.width = this.A - (framePointSet.left + ((this.A * intArray[2]) / 100));
            framePointSet.height = this.A - (framePointSet.top + i2);
            StringBuilder sb4 = new StringBuilder();
            sb4.append("fps w: ");
            sb4.append(framePointSet.width);
            Log.d("word", sb4.toString());
            bufferedReader.close();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(framePointSet.width, framePointSet.height);
        layoutParams.setMargins(framePointSet.left, framePointSet.top, 0, 0);
        runOnUiThread(new Runnable() {
            public void run() {
                MoiveMakerActivity.this.P.setLayoutParams(layoutParams);
                MoiveMakerActivity.this.P.setScaleType(ScaleType.FIT_XY);
            }
        });
    }

    public int[] toIntArray(String str) {
        String[] split = str.split("\\s");
        int[] iArr = new int[split.length];
        for (int i2 = 0; i2 < split.length; i2++) {
            try {
                iArr[i2] = Integer.parseInt(split[i2]);
            } catch (NumberFormatException unused) {
                iArr[i2] = -1;
            }
        }
        return iArr;
    }


    public void a(String str) {
        this.h = new ArrayList<>();
        File file = new File(str);
        if (file.exists()) {
            File[] listFiles = file.listFiles(new MyFileNameFilter());
            if (listFiles != null) {
                int length = listFiles.length;
                if (length > 0) {
                    for (int i2 = 0; i2 < length; i2++) {
                        if (!listFiles[i2].getName().equals("slide_00000.jpg")) {
                            this.h.add(listFiles[i2].getAbsolutePath());
                        }
                    }
                }
            }
        }
    }


    public Bitmap a(boolean z2, boolean z3) {
        FramePointSet framePointSet;
        HsItem hsItem;
        this.i.getWidth();
        this.i.getHeight();
        Paint paint = new Paint();
        Bitmap createBitmap = Bitmap.createBitmap(this.z, this.z, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawRect(0.0f, 0.0f, (float) this.z, (float) this.z, paint);
        if (z2) {
            hsItem = (HsItem) this.B.get(Utils.framePostion);
            framePointSet = hsItem.path.contains(getPackageName()) ? a(hsItem) : a(this.z, this.z);
        } else {
            hsItem = null;
            framePointSet = null;
        }
        if (z2) {
            if (framePointSet == null) {
                Bitmap resizedBitmap = BitmapCompression.getResizedBitmap(Utils.bitmap);
                canvas.drawBitmap(resizedBitmap, (float) ((canvas.getWidth() / 2) - (resizedBitmap.getWidth() / 2)), (float) ((canvas.getHeight() / 2) - (resizedBitmap.getHeight() / 2)), null);
            } else {
                canvas.drawBitmap(Bitmap.createScaledBitmap(Utils.bitmap, framePointSet.width, framePointSet.height, false), (float) framePointSet.left, (float) framePointSet.top, null);
            }
            try {
                if (hsItem.path.contains(getPackageName())) {
                    canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapCompression.decodeSampledBitmapFromLocal(mContext, hsItem.path.replace("file://", ""), Utils.width, Utils.width), this.z, this.z, false), 0.0f, 0.0f, null);
                } else {
                    canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapCompression.decodeSampledBitmapFromAssets(mContext, hsItem.path.replace("assets://", ""), Utils.width, Utils.width), this.z, this.z, false), 0.0f, 0.0f, null);
                }
            } catch (IOException unused) {
            }
            Utils.clgstickerviewsList.iterator();
        } else {
            Bitmap resizedBitmap2 = BitmapCompression.getResizedBitmap(Utils.bitmap);
            canvas.drawBitmap(resizedBitmap2, (float) ((canvas.getWidth() / 2) - (resizedBitmap2.getWidth() / 2)), (float) ((canvas.getHeight() / 2) - (resizedBitmap2.getHeight() / 2)), null);
            Utils.clgstickerviewsList.iterator();
        }
        return createBitmap;
    }

    private FramePointSet a(HsItem hsItem) {
        StringBuilder sb = new StringBuilder(String.valueOf(new File(hsItem.path).getParentFile().getPath()));
        sb.append("/dimen.txt");
        String replace = sb.toString().replace("file:", "");
        FramePointSet framePointSet = new FramePointSet();
        framePointSet.left = 0;
        framePointSet.top = 0;
        framePointSet.width = Utils.width;
        framePointSet.height = Utils.height;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(replace.replace("file:", ""))));
            int[] intArray = toIntArray(bufferedReader.readLine());
            framePointSet.left = (Utils.width * intArray[0]) / 100;
            framePointSet.top = (Utils.width * intArray[1]) / 100;
            int i2 = (Utils.width * intArray[3]) / 100;
            framePointSet.width = Utils.width - (framePointSet.left + ((Utils.width * intArray[2]) / 100));
            framePointSet.height = Utils.width - (framePointSet.top + i2);
            bufferedReader.close();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return framePointSet;
    }


    public FramePointSet a(int i2, int i3) {
        FramePointSet framePointSet = new FramePointSet();
        switch (Utils.framePostion) {
            case 0:
                return null;
            case 1:
                int i4 = (i2 * 5) / 100;
                framePointSet.left = i4;
                int i5 = (i3 * 5) / 100;
                framePointSet.top = i5;
                framePointSet.width = i2 - (framePointSet.left + i4);
                framePointSet.height = i3 - (framePointSet.top + i5);
                return framePointSet;
            case 2:
                int i6 = (i2 * 5) / 100;
                framePointSet.left = i6;
                int i7 = (i3 * 5) / 100;
                framePointSet.top = i7;
                framePointSet.width = i2 - (framePointSet.left + i6);
                framePointSet.height = i3 - (framePointSet.top + i7);
                return framePointSet;
            case 3:
                int i8 = (i2 * 2) / 100;
                framePointSet.left = i8;
                int i9 = (i3 * 2) / 100;
                framePointSet.top = i9;
                framePointSet.width = i2 - (framePointSet.left + i8);
                framePointSet.height = i3 - (framePointSet.top + i9);
                return framePointSet;
            case 4:
                int i10 = (i2 * 5) / 100;
                framePointSet.left = i10;
                int i11 = (i3 * 5) / 100;
                framePointSet.top = i11;
                framePointSet.width = i2 - (framePointSet.left + i10);
                framePointSet.height = i3 - (framePointSet.top + i11);
                return framePointSet;
            case 5:
                int i12 = (i2 * 0) / 100;
                framePointSet.left = i12;
                int i13 = (i3 * 5) / 100;
                framePointSet.top = i13;
                framePointSet.width = i2 - (framePointSet.left + i12);
                framePointSet.height = i3 - (framePointSet.top + i13);
                return framePointSet;
            case 6:
                int i14 = (i2 * 0) / 100;
                framePointSet.left = i14;
                int i15 = (i3 * 3) / 100;
                framePointSet.top = i15;
                framePointSet.width = i2 - (framePointSet.left + i14);
                framePointSet.height = i3 - (framePointSet.top + i15);
                return framePointSet;
            case 7:
                int i16 = (i2 * 3) / 100;
                framePointSet.left = i16;
                int i17 = (i3 * 3) / 100;
                framePointSet.top = i17;
                framePointSet.width = i2 - (framePointSet.left + i16);
                framePointSet.height = i3 - (framePointSet.top + i17);
                return framePointSet;
            case 8:
                int i18 = (i2 * 1) / 100;
                framePointSet.left = i18;
                framePointSet.top = (i3 * 3) / 100;
                int i19 = (i3 * 0) / 100;
                framePointSet.width = i2 - (framePointSet.left + i18);
                framePointSet.height = i3 - (framePointSet.top + i19);
                return framePointSet;
            case 9:
                int i20 = (i2 * 5) / 100;
                framePointSet.left = i20;
                int i21 = (i3 * 5) / 100;
                framePointSet.top = i21;
                framePointSet.width = i2 - (framePointSet.left + i20);
                framePointSet.height = i3 - (framePointSet.top + i21);
                return framePointSet;
            case 10:
                int i22 = (i2 * 0) / 100;
                framePointSet.left = i22;
                int i23 = (i3 * 0) / 100;
                framePointSet.top = i23;
                framePointSet.width = i2 - (framePointSet.left + i22);
                framePointSet.height = i3 - (framePointSet.top + i23);
                return framePointSet;
            case 11:
                int i24 = (i2 * 3) / 100;
                framePointSet.left = i24;
                int i25 = (i3 * 5) / 100;
                framePointSet.top = i25;
                framePointSet.width = i2 - (framePointSet.left + i24);
                framePointSet.height = i3 - (framePointSet.top + i25);
                return framePointSet;
            case 12:
                int i26 = (i2 * 0) / 100;
                framePointSet.left = i26;
                int i27 = (i3 * 0) / 100;
                framePointSet.top = i27;
                framePointSet.width = i2 - (framePointSet.left + i26);
                framePointSet.height = i3 - (framePointSet.top + i27);
                return framePointSet;
            case 13:
                int i28 = (i2 * 0) / 100;
                framePointSet.left = i28;
                int i29 = (i3 * 0) / 100;
                framePointSet.top = i29;
                framePointSet.width = i2 - (framePointSet.left + i28);
                framePointSet.height = i3 - (framePointSet.top + i29);
                return framePointSet;
            case 14:
                int i30 = (i2 * 5) / 100;
                framePointSet.left = i30;
                int i31 = (i3 * 5) / 100;
                framePointSet.top = i31;
                framePointSet.width = i2 - (framePointSet.left + i30);
                framePointSet.height = i3 - (framePointSet.top + i31);
                return framePointSet;
            case 15:
                int i32 = (i2 * 2) / 100;
                framePointSet.left = i32;
                int i33 = (i3 * 2) / 100;
                framePointSet.top = i33;
                framePointSet.width = i2 - (framePointSet.left + i32);
                framePointSet.height = i3 - (framePointSet.top + i33);
                return framePointSet;
            default:
                return null;
        }
    }


    public void a(int i2, boolean z2) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.PhotoToVideo));
        if (new File(new StringBuilder(sb.toString()).toString()).exists()) {
            if (z2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                sb2.append("/");
                sb2.append(getResources().getString(R.string.MainFolderName));
                sb2.append("/");
                sb2.append(getResources().getString(R.string.PhotoToVideo));
                StringBuilder sb3 = new StringBuilder(sb2.toString());
                sb3.append("/temp");
                str = sb3.toString();
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                sb4.append("/");
                sb4.append(getResources().getString(R.string.MainFolderName));
                sb4.append("/");
                sb4.append(getResources().getString(R.string.PhotoToVideo));
                str = new StringBuilder(sb4.toString()).toString();
            }
            File file = new File(str);
            if (!file.exists()) {
                file.mkdir();
            }
            try {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("slide_");
                sb5.append(String.format(Locale.US, "%05d", new Object[]{Integer.valueOf(i2)}));
                sb5.append(".jpg");
                FileOutputStream fileOutputStream = new FileOutputStream(new File(str, sb5.toString()));
                try {
                    Log.d("bitmp w", new StringBuilder(String.valueOf(Utils.bitmap.getWidth())).toString());
                    Log.d("bitmp h", new StringBuilder(String.valueOf(Utils.bitmap.getHeight())).toString());
                    Utils.bitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2)))});
    }

    public void onProgressChanged(SeekBar seekBar, int i2, boolean z2) {
        float f2;
        if (seekBar == this.K) {
            float f3 = ((float) i2) / 10.0f;
            if (((double) Math.round(f3)) < 0.2d) {
                seekBar.setProgress(5);
                return;
            }
            double d2 = (double) f3;
            if (d2 - Math.floor(d2) < 0.2d) {
                f2 = (float) Math.floor(d2);
                Log.i("Progres val", String.valueOf(Math.floor((double) f2)));
            } else if (d2 - Math.floor(d2) <= 0.2d || d2 - Math.floor(d2) > 0.7d) {
                f2 = (float) Math.round(f3);
                Log.i("Progres val", String.valueOf(Math.round(f2)));
            } else {
                f2 = (float) (Math.floor(d2) + 0.5d);
                Log.i("Progres val", String.valueOf(Math.floor((double) f2) + 0.5d));
            }
            Log.d("file size", new StringBuilder(String.valueOf(this.d.size())).toString());
            this.Z = new StringBuilder(String.valueOf(f2)).toString();
            TextView textView = this.X;
            StringBuilder sb = new StringBuilder(String.valueOf(f2));
            sb.append(" Sec. per Photos");
            textView.setText(sb.toString());
            TextView textView2 = this.W;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Movie Length : ");
            sb2.append(((float) this.d.size()) * f2);
            sb2.append(" Sec.");
            textView2.setText(sb2.toString());
            this.Y.setText(new StringBuilder(String.valueOf(formatTimeUnit(((long) (((float) this.d.size()) * f2)) * 1000))).toString());
            this.b = ((float) this.d.size()) * f2;
            return;
        }
        this.w = i2;
        try {
            this.U.setText(formatTimeUnit((long) this.w));
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        if (seekBar != this.K) {
            if (this.F != null) {
                this.F.pause();
            }
            this.u = -1;
            a(this.P, this.u, false);
            this.r = false;
        }
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar != this.K && this.F != null) {
            this.F.start();
            this.F.seekTo(this.w);
            this.F.pause();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (this.d.size() <= 0) {
            runOnUiThread(new Runnable() {
                public void run() {
                    MoiveMakerActivity.this.h();
                }
            });
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getWindow().clearFlags(128);
        if (this.F != null) {
            if (this.F.isPlaying()) {
                this.F.stop();
                this.F.release();
            }
            this.F = null;
        }
        if (Utils.bitmap != null) {
            try {
                Utils.bitmap.recycle();
            } catch (Exception unused) {
            }
        }
        if (this.d != null) {
            this.d.clear();
        }
        System.gc();
    }


    @Override
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i3 == -1 && i2 == 99) {
            this.p = aj;
            try {
                if (this.d.size() > 0) {
                    a(this.P, this.u, (boolean) aj);
                    this.P.setImageBitmap((Bitmap) this.d.get(0));
                }
            } catch (Exception unused) {
            }
            this.r = false;
            this.P.setVisibility(0);
            this.T.setText(Utils.audioName);
            TextView textView = this.V;
            StringBuilder sb = new StringBuilder();
            sb.append("duration: ");
            sb.append(Utils.audioDuration);
            sb.append("s");
            textView.setText(sb.toString());
            this.E.setVisibility(0);
            this.N.setVisibility(8);
            this.G = intent.getData().toString();
            try {
                if (this.F == null) {
                    this.F = new MediaPlayer();
                    this.F.setDataSource(this.G);
                    this.F.setAudioStreamType(3);
                    this.F.prepare();
                } else {
                    this.F.setDataSource(this.G);
                    this.F.setAudioStreamType(3);
                    this.F.prepare();
                }
                this.F.start();
                this.M.setMax(this.F.getDuration());
                this.M.setProgress(0);
                this.w = 0;
                this.F.seekTo(0);
                this.F.pause();
                this.U.setText("00:00");
                try {
                    this.S.setText(formatTimeUnit((long) this.F.getDuration()));
                } catch (ParseException e2) {
                    e2.printStackTrace();
                }
                this.c = aj;
            } catch (IOException unused2) {
            }
        }
        if (i3 == 0) {
            this.c = false;
        }
    }


    public void h() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.y = displayMetrics.heightPixels;
        this.z = displayMetrics.widthPixels;
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.PhotoToVideo));
        StringBuilder sb2 = new StringBuilder(String.valueOf(sb.toString()));
        sb2.append("/temp");
        String sb3 = sb2.toString();
        if (this.d != null && this.d.size() > 0) {
            this.d.clear();
        }
        File file = new File(sb3);
        if (file.exists()) {
            File[] listFiles = file.listFiles(new MyFileNameFilter());
            if (listFiles != null) {
                int length = listFiles.length;
                if (length > 0) {
                    for (int i2 = 0; i2 < length; i2++) {
                        if (!listFiles[i2].getName().equals("slide_00000.jpg")) {
                            this.d.add(BitmapCompression.decodeFile(new File(listFiles[i2].getAbsolutePath()), this.z - 200, this.z + 0));
                            TextView textView = this.W;
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("Movie Length : ");
                            sb4.append(this.d.size() * 2);
                            sb4.append(" Sec.");
                            textView.setText(sb4.toString());
                            this.X.setText("2 Sec. per Photos");
                            this.Y.setText(new StringBuilder(String.valueOf(formatTimeUnit((long) (this.d.size() * 2 * 1000)))).toString());
                            this.Z = "2";
                            this.b = (float) (this.d.size() * 2);
                            this.L.setProgress(0);
                            this.L.setMax(this.d.size() * 2 * 1000);
                        }
                    }
                }
            }
        }
    }


    public void a(final ImageView imageView, final int i2, final boolean z2) {
        if (this.Z.equals("")) {
            this.Z = "2";
        }
        int parseFloat = (int) (Float.parseFloat(this.Z) * 1000.0f);
        imageView.setVisibility(4);
        if (this.u != i2) {
            if (this.d.size() > 0) {
                imageView.setImageBitmap((Bitmap) this.d.get(i2));
            }
        } else if (this.d.size() > 0) {
            imageView.setImageBitmap((Bitmap) this.d.get(i2 + 1));
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setInterpolator(new DecelerateInterpolator());
        alphaAnimation.setDuration(0);
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation2.setInterpolator(new AccelerateInterpolator());
        alphaAnimation2.setStartOffset((long) (parseFloat + 0));
        alphaAnimation2.setDuration(1);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(alphaAnimation2);
        animationSet.setRepeatCount(1);
        imageView.setAnimation(animationSet);
        animationSet.setAnimationListener(new AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                if (MoiveMakerActivity.this.u == i2) {
                    MoiveMakerActivity.this.P.setVisibility(0);
                    if (MoiveMakerActivity.this.p) {
                        MoiveMakerActivity.this.u = -1;
                    }
                    if (MoiveMakerActivity.this.d.size() > 0 && Utils.filterIndex == -1) {
                        MoiveMakerActivity.this.P.setImageBitmap((Bitmap) MoiveMakerActivity.this.d.get(MoiveMakerActivity.this.u + 1));
                    }
                } else if (MoiveMakerActivity.this.d.size() - 1 > i2) {
                    MoiveMakerActivity.this.u = i2;
                    MoiveMakerActivity.this.a(imageView, i2 + 1, z2);
                } else {
                    if (z2) {
                        MoiveMakerActivity.this.a(imageView, 0, z2);
                    }
                    if (MoiveMakerActivity.this.F != null) {
                        MoiveMakerActivity.this.F.pause();
                        MoiveMakerActivity.this.F.seekTo(MoiveMakerActivity.this.w);
                    }
                    MoiveMakerActivity.this.u = -1;
                    MoiveMakerActivity.this.r = false;
                    MoiveMakerActivity.this.P.setVisibility(0);
                    MoiveMakerActivity.this.O.setBackgroundResource(R.drawable.play2);
                    MoiveMakerActivity.this.n.removeCallbacks(MoiveMakerActivity.this.ab);
                    MoiveMakerActivity.this.L.setProgress(0);
                    if (MoiveMakerActivity.this.d.size() > 0 && Utils.filterIndex == -1) {
                        MoiveMakerActivity.this.P.setImageBitmap((Bitmap) MoiveMakerActivity.this.d.get(0));
                    }
                }
            }
        });
    }


    public String[] b(String str) {
        String[] strArr;
        try {
            strArr = getAssets().list(str);
        } catch (IOException e2) {
            e2.printStackTrace();
            strArr = null;
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            StringBuilder sb = new StringBuilder(String.valueOf(str));
            sb.append("/");
            sb.append(strArr[i2]);
            strArr[i2] = sb.toString();
        }
        return strArr;
    }


    public void i() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.PhotoToVideo));
        StringBuilder sb2 = new StringBuilder(String.valueOf(sb.toString()));
        sb2.append("/temp");
        File file = new File(sb2.toString());
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.getName().endsWith(".jpg") || file2.getName().endsWith(".png")) {
                        file2.delete();
                    }
                }
            }
        }
    }


    public void j() {
        this.N.setBackgroundResource(R.drawable.music_normal);
        this.f.setBackgroundResource(R.drawable.frame_normal);
        this.g.setBackgroundResource(R.drawable.time_normal);
    }


    public void k() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.PhotoToVideo));
        File file = new File(new StringBuilder(String.valueOf(sb.toString())).toString());
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


    public void l() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.PhotoToVideo));
        File file = new File(new StringBuilder(String.valueOf(sb.toString())).toString());
        if (file.exists()) {
            String str = "tempmusic";
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.getName().startsWith(str)) {
                        file2.delete();
                    }
                }
            }
        }
    }


    public void m() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.PhotoToVideo));
        StringBuilder sb2 = new StringBuilder(String.valueOf(sb.toString()));
        sb2.append("/temp");
        File file = new File(sb2.toString());
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    file2.delete();
                    if (VERSION.SDK_INT >= 19) {
                        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                        intent.setData(Uri.fromFile(file2));
                        sendBroadcast(intent);
                    } else {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("file://");
                        sb3.append(file2.getAbsolutePath());
                        sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse(sb3.toString())));
                    }
                }
            }
            file.delete();
        }
    }

    public void hideMusicList(String str) {
        this.j.setVisibility(8);
        this.p = aj;
        try {
            if (this.d.size() > 0) {
                this.P.setImageBitmap((Bitmap) this.d.get(0));
            }
        } catch (Exception unused) {
        }
        this.r = false;
        this.P.setVisibility(0);
        this.T.setText(Utils.audioName);
        TextView textView = this.V;
        StringBuilder sb = new StringBuilder();
        sb.append("duration: ");
        sb.append(Utils.audioDuration);
        sb.append("s");
        textView.setText(sb.toString());
        this.E.setVisibility(0);
        this.c = aj;
        this.G = str;
        try {
            if (this.F == null) {
                this.F = new MediaPlayer();
                this.F.setDataSource(this.G);
                this.F.setAudioStreamType(3);
                this.F.prepare();
            } else {
                this.F.reset();
                this.F.setDataSource(this.G);
                this.F.setAudioStreamType(3);
                this.F.prepare();
            }
            this.F.start();
            this.M.setMax(this.F.getDuration());
            this.M.setProgress(0);
            this.w = 0;
            this.F.seekTo(0);
            this.F.pause();
            this.U.setText("00:00");
            try {
                this.S.setText(formatTimeUnit((long) this.F.getDuration()));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
            this.a.setVisibility(8);
            this.D.setVisibility(8);
            this.E.setVisibility(0);
        } catch (Exception unused2) {
        }
    }


    public void o() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MoiveMakerActivity.this.finish();
            }
        }).create().show();
    }

    public void deleteFromGallery(String str) {
        String[] strArr = {"_id"};
        String[] strArr2 = {str};
        Uri uri = Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor query = contentResolver.query(uri, strArr, "_data = ?", strArr2, null);
        if (query.moveToFirst()) {
            try {
                contentResolver.delete(ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, query.getLong(query.getColumnIndexOrThrow("_id"))), null, null);
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            }
        } else {
            try {
                new File(str).delete();
                refreshGallery(str);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        query.close();
    }

    public void refreshGallery(String str) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(str)));
        sendBroadcast(intent);
    }

    @Override
    public void onBackPressed() {
        if (this.j.getVisibility() == 0) {
            this.j.setVisibility(8);
            this.N.setBackgroundResource(R.drawable.music_normal);
            return;
        }
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return aj;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            if (!this.s) {
                onBackPressed();
            }
            return aj;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (this.r) {
                this.u = -1;
                a(this.P, this.u, false);
                this.n.removeCallbacks(this.ab);
                this.r = false;
            }
            try {
                this.s = aj;
                if (this.F != null) {
                    this.F.pause();
                }
            } catch (Exception unused) {
            }
            if (this.d != null && this.d.size() > 0) {
                this.d.clear();
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.y = displayMetrics.heightPixels;
            this.z = displayMetrics.widthPixels;
            new a().execute(new Void[0]);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
