package com.androworld.allinonevideoeditor.videocollage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Video.Media;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.VideoPlayerState;
import com.androworld.allinonevideoeditor.VideoSliceSeekBar;
import com.androworld.allinonevideoeditor.VideoSliceSeekBar.SeekBarChangeListener;
import com.androworld.allinonevideoeditor.videocollage.model.CollageData;
import com.androworld.allinonevideoeditor.videocollage.utils.Utils;
import com.edmodo.cropper.CropImageView;

import java.io.File;

@SuppressLint({"WrongConstant"})
public class VideoCropAndCutActivity extends AppCompatActivity {
    static final boolean I = true;
    ImageView A;
    CropImageView B;
    VideoSliceSeekBar C;
    VideoView D;
    RelativeLayout E;
    RelativeLayout F;
    RelativeLayout G;
    RelativeLayout H;

    public TextView J;

    public TextView K;

    public VideoPlayerState L = new VideoPlayerState();
    private b M = new b();
    int a;
    int b;
    int c;
    int d;
    int e;
    int f;
    int g;
    int h;
    int i;
    int j;
    int k;
    int l;
    int m;
    int n;
    int o;
    int p;
    int q;
    int r;
    int s;
    String t;
    String u;
    ProgressDialog v;
    int w = 0;
    int x = 1;
    String y = "00";
    ImageView z;

    @SuppressLint({"NewApi"})
    private class a extends AsyncTask<Void, Void, Void> {


        public void onProgressUpdate(Void... voidArr) {
        }

        public a() {
            VideoCropAndCutActivity.this.v = new ProgressDialog(VideoCropAndCutActivity.this);
            VideoCropAndCutActivity.this.v.setMessage("Please Wait");
            VideoCropAndCutActivity.this.v.setCancelable(false);
            VideoCropAndCutActivity.this.v.show();
        }



        public Void doInBackground(Void... voidArr) {
            int i;
            int i2;
            int i3 = 0;
            if (VideoCropAndCutActivity.this.w == 90) {
                try {
                    VideoCropAndCutActivity.this.o = VideoCropAndCutActivity.this.a;
                    int i4 = VideoCropAndCutActivity.this.c;
                    VideoCropAndCutActivity.this.g = VideoCropAndCutActivity.this.a;
                    VideoCropAndCutActivity.this.f = VideoCropAndCutActivity.this.b;
                    VideoCropAndCutActivity.this.q = VideoCropAndCutActivity.this.d;
                    VideoCropAndCutActivity.this.p = VideoCropAndCutActivity.this.c;
                    VideoCropAndCutActivity.this.i = VideoCropAndCutActivity.this.d;
                    VideoCropAndCutActivity.this.h = VideoCropAndCutActivity.this.b;
                    i = VideoCropAndCutActivity.this.q - VideoCropAndCutActivity.this.o;
                    try {
                        i3 = VideoCropAndCutActivity.this.f - i4;
                        VideoCropAndCutActivity.this.n = VideoCropAndCutActivity.this.m - (i4 + i3);
                    } catch (Exception e) {
                        e = e;
                    }
                } catch (Exception e2) {
                    i = 0;
                    String valueOf = String.valueOf(VideoCropAndCutActivity.this.L.getStart() / 1000);
                    String.valueOf(VideoCropAndCutActivity.this.L.getDuration() / 1000);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setStartTime(valueOf);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setDurationTime(VideoCropAndCutActivity.this.L.getDuration() / 1000);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_X(VideoCropAndCutActivity.this.o);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_Y(VideoCropAndCutActivity.this.n);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_width(i);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_height(i3);
                    return null;
                }
            } else if (VideoCropAndCutActivity.this.w == 270) {
                try {
                    int i5 = VideoCropAndCutActivity.this.a;
                    int i6 = VideoCropAndCutActivity.this.c;
                    VideoCropAndCutActivity.this.g = VideoCropAndCutActivity.this.a;
                    VideoCropAndCutActivity.this.f = VideoCropAndCutActivity.this.b;
                    VideoCropAndCutActivity.this.q = VideoCropAndCutActivity.this.d;
                    VideoCropAndCutActivity.this.p = VideoCropAndCutActivity.this.c;
                    VideoCropAndCutActivity.this.i = VideoCropAndCutActivity.this.d;
                    VideoCropAndCutActivity.this.h = VideoCropAndCutActivity.this.b;
                    int i7 = VideoCropAndCutActivity.this.q - i5;
                    try {
                        i3 = VideoCropAndCutActivity.this.f - i6;
                        VideoCropAndCutActivity.this.o = VideoCropAndCutActivity.this.l - (i5 + i7);
                        VideoCropAndCutActivity.this.n = i6;
                        i = i7;
                    } catch (Exception e3) {
                        i = i7;
                        String valueOf2 = String.valueOf(VideoCropAndCutActivity.this.L.getStart() / 1000);
                        String.valueOf(VideoCropAndCutActivity.this.L.getDuration() / 1000);
                        ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setStartTime(valueOf2);
                        ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setDurationTime(VideoCropAndCutActivity.this.L.getDuration() / 1000);
                        ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_X(VideoCropAndCutActivity.this.o);
                        ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_Y(VideoCropAndCutActivity.this.n);
                        ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_width(i);
                        ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_height(i3);
                        return null;
                    }
                } catch (Exception e4) {
                    i = 0;
                    String valueOf22 = String.valueOf(VideoCropAndCutActivity.this.L.getStart() / 1000);
                    String.valueOf(VideoCropAndCutActivity.this.L.getDuration() / 1000);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setStartTime(valueOf22);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setDurationTime(VideoCropAndCutActivity.this.L.getDuration() / 1000);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_X(VideoCropAndCutActivity.this.o);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_Y(VideoCropAndCutActivity.this.n);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_width(i);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_height(i3);
                    return null;
                }
            } else {
                try {
                    VideoCropAndCutActivity.this.o = VideoCropAndCutActivity.this.c;
                    VideoCropAndCutActivity.this.n = VideoCropAndCutActivity.this.a;
                    VideoCropAndCutActivity.this.g = VideoCropAndCutActivity.this.b;
                    VideoCropAndCutActivity.this.f = VideoCropAndCutActivity.this.a;
                    VideoCropAndCutActivity.this.q = VideoCropAndCutActivity.this.c;
                    VideoCropAndCutActivity.this.p = VideoCropAndCutActivity.this.d;
                    VideoCropAndCutActivity.this.i = VideoCropAndCutActivity.this.b;
                    VideoCropAndCutActivity.this.h = VideoCropAndCutActivity.this.d;
                    i2 = VideoCropAndCutActivity.this.g - VideoCropAndCutActivity.this.o;
                    try {
                        i3 = VideoCropAndCutActivity.this.p - VideoCropAndCutActivity.this.n;
                    } catch (Exception e5) {
                        i = i2;
                        String valueOf222 = String.valueOf(VideoCropAndCutActivity.this.L.getStart() / 1000);
                        String.valueOf(VideoCropAndCutActivity.this.L.getDuration() / 1000);
                        ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setStartTime(valueOf222);
                        ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setDurationTime(VideoCropAndCutActivity.this.L.getDuration() / 1000);
                        ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_X(VideoCropAndCutActivity.this.o);
                        ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_Y(VideoCropAndCutActivity.this.n);
                        ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_width(i);
                        ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_height(i3);
                        return null;
                    }
                } catch (Exception e6) {
                    i2 = 0;
                    i = i2;
                    String valueOf2222 = String.valueOf(VideoCropAndCutActivity.this.L.getStart() / 1000);
                    String.valueOf(VideoCropAndCutActivity.this.L.getDuration() / 1000);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setStartTime(valueOf2222);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setDurationTime(VideoCropAndCutActivity.this.L.getDuration() / 1000);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_X(VideoCropAndCutActivity.this.o);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_Y(VideoCropAndCutActivity.this.n);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_width(i);
                    ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_height(i3);
                    return null;
                }
                i = i2;
            }
            String valueOf22222 = String.valueOf(VideoCropAndCutActivity.this.L.getStart() / 1000);
            String.valueOf(VideoCropAndCutActivity.this.L.getDuration() / 1000);
            ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setStartTime(valueOf22222);
            ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setDurationTime(VideoCropAndCutActivity.this.L.getDuration() / 1000);
            ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_X(VideoCropAndCutActivity.this.o);
            ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_Y(VideoCropAndCutActivity.this.n);
            ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_width(i);
            ((CollageData) Utils.collageData.get(VideoCropAndCutActivity.this.r)).setCrop_height(i3);
            return null;
        }



        public void onPostExecute(Void voidR) {
            VideoCropAndCutActivity.this.v.dismiss();
            Intent intent = new Intent();
            intent.putExtra("videopath", VideoCropAndCutActivity.this.u);
            intent.putExtra("frmpos", VideoCropAndCutActivity.this.r);
            VideoCropAndCutActivity.this.setResult(-1, intent);
            VideoCropAndCutActivity.this.finish();
        }
    }

    private class b extends Handler {
        private boolean b;
        private Runnable c;

        private b() {
            this.b = false;
            this.c = new Runnable() {
                public void run() {
                    b.this.a();
                }
            };
        }


        public void a() {
            if (!this.b) {
                try {
                    this.b = VideoCropAndCutActivity.I;
                    sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override public void handleMessage(Message message) {
            this.b = false;
            VideoCropAndCutActivity.this.C.videoPlayingProgress(VideoCropAndCutActivity.this.D.getCurrentPosition());
            if (!VideoCropAndCutActivity.this.D.isPlaying() || VideoCropAndCutActivity.this.D.getCurrentPosition() >= VideoCropAndCutActivity.this.C.getRightProgress()) {
                try {
                    if (VideoCropAndCutActivity.this.D.isPlaying()) {
                        try {
                            VideoCropAndCutActivity.this.D.pause();
                            VideoCropAndCutActivity.this.z.setBackgroundResource(R.drawable.play2);
                            VideoCropAndCutActivity.this.A.setVisibility(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    VideoCropAndCutActivity.this.C.setSliceBlocked(false);
                    VideoCropAndCutActivity.this.C.removeVideoStatusThumb();
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            postDelayed(this.c, 50);
        }
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.videocollagecropactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Cut Crop");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (I || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(I);
            supportActionBar.setDisplayShowTitleEnabled(false);
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_container);
            LayoutParams layoutParams = (LayoutParams) relativeLayout.getLayoutParams();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.e = displayMetrics.widthPixels;
            this.s = displayMetrics.heightPixels;
            layoutParams.width = this.e;
            layoutParams.height = this.e;
            relativeLayout.setLayoutParams(layoutParams);
            this.u = getIntent().getStringExtra("videopath");
            this.B = (CropImageView) findViewById(R.id.cropperView);
            a();
            ((TextView) findViewById(R.id.textfilename)).setText(new File(this.u).getName());
            Object lastNonConfigurationInstance = getLastNonConfigurationInstance();
            if (lastNonConfigurationInstance != null) {
                try {
                    this.L = (VideoPlayerState) lastNonConfigurationInstance;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                try {
                    Bundle extras = getIntent().getExtras();
                    this.u = extras.getString("videopath");
                    this.L.setFilename(this.u);
                    this.r = extras.getInt("frmpos");
                    this.k = extras.getInt("ratio_x");
                    this.j = extras.getInt("ratio_y");
                    this.u.split("/");
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            b();
            this.B.setFixedAspectRatio(I);
            this.B.setAspectRatio(this.k, this.j);
            return;
        }
        throw new AssertionError();
    }

    @Override public void onBackPressed() {
        if (this.D != null && this.D.isPlaying()) {
            this.D.pause();
        }
        getWindow().clearFlags(128);
        setResult(0);
        finish();
    }


    public void onPause() {
        super.onPause();
        this.L.setCurrentTime(this.D.getCurrentPosition());
    }


    @Override public void onResume() {
        super.onResume();
        this.D.seekTo(this.L.getCurrentTime());
    }

    private void a() {
        this.G = (RelativeLayout) findViewById(R.id.back_Original);
        this.E = (RelativeLayout) findViewById(R.id.imgbtn_Original);
        this.H = (RelativeLayout) findViewById(R.id.back_Fit);
        this.F = (RelativeLayout) findViewById(R.id.imgbtn_Fit);
        this.E.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                VideoCropAndCutActivity.this.G.setVisibility(0);
                VideoCropAndCutActivity.this.H.setVisibility(8);
                VideoCropAndCutActivity.this.B.setFixedAspectRatio(false);
            }
        });
        this.F.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                VideoCropAndCutActivity.this.G.setVisibility(8);
                VideoCropAndCutActivity.this.H.setVisibility(0);
                VideoCropAndCutActivity.this.B.setFixedAspectRatio(VideoCropAndCutActivity.I);
                VideoCropAndCutActivity.this.B.setAspectRatio(VideoCropAndCutActivity.this.k, VideoCropAndCutActivity.this.j);
            }
        });
        this.K = (TextView) findViewById(R.id.left_pointer);
        this.J = (TextView) findViewById(R.id.right_pointer);
        this.C = (VideoSliceSeekBar) findViewById(R.id.seek_bar);
        this.A = (ImageView) findViewById(R.id.ivScreen);
        this.z = (ImageView) findViewById(R.id.buttonply);
        this.z.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                if (VideoCropAndCutActivity.this.D == null || !VideoCropAndCutActivity.this.D.isPlaying()) {
                    try {
                        VideoCropAndCutActivity.this.z.setBackgroundResource(R.drawable.pause2);
                        VideoCropAndCutActivity.this.A.setVisibility(8);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        VideoCropAndCutActivity.this.A.setVisibility(0);
                        VideoCropAndCutActivity.this.z.setBackgroundResource(R.drawable.play2);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                VideoCropAndCutActivity.this.d();
            }
        });
        Object lastNonConfigurationInstance = getLastNonConfigurationInstance();
        if (lastNonConfigurationInstance != null) {
            try {
                this.L = (VideoPlayerState) lastNonConfigurationInstance;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            try {
                this.L.setFilename(this.u);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    private void b() {
        this.D = (VideoView) findViewById(R.id.videoview);
        this.D.setVideoPath(this.u);
        this.t = getTimeForTrackFormat(this.D.getDuration(), I);
        this.D.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                VideoCropAndCutActivity.this.C.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (VideoCropAndCutActivity.this.C.getSelectedThumb() == 1) {
                            try {
                                VideoCropAndCutActivity.this.D.seekTo(VideoCropAndCutActivity.this.C.getLeftProgress());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        VideoCropAndCutActivity.this.K.setText(VideoCropAndCutActivity.getTimeForTrackFormat(i, VideoCropAndCutActivity.I));
                        VideoCropAndCutActivity.this.J.setText(VideoCropAndCutActivity.getTimeForTrackFormat(i2, VideoCropAndCutActivity.I));
                        VideoCropAndCutActivity.this.y = VideoCropAndCutActivity.getTimeForTrackFormat(i, VideoCropAndCutActivity.I);
                        VideoCropAndCutActivity.this.L.setStart(i);
                        VideoCropAndCutActivity.this.t = VideoCropAndCutActivity.getTimeForTrackFormat(i2, VideoCropAndCutActivity.I);
                        VideoCropAndCutActivity.this.L.setStop(i2);
                    }
                });
                VideoCropAndCutActivity.this.t = VideoCropAndCutActivity.getTimeForTrackFormat(mediaPlayer.getDuration(), VideoCropAndCutActivity.I);
                VideoCropAndCutActivity.this.C.setMaxValue(mediaPlayer.getDuration());
                VideoCropAndCutActivity.this.C.setLeftProgress(0);
                VideoCropAndCutActivity.this.C.setRightProgress(mediaPlayer.getDuration());
                VideoCropAndCutActivity.this.C.setProgressMinDiff(0);
            }
        });
        try {
            c();
        } catch (Exception unused) {
            Toast.makeText(getApplicationContext(), "Not supported video...", 0).show();
            finish();
        }
    }

    private void c() throws Exception {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(this.u);
        this.l = Integer.valueOf(mediaMetadataRetriever.extractMetadata(18)).intValue();
        this.m = Integer.valueOf(mediaMetadataRetriever.extractMetadata(19)).intValue();
        StringBuilder sb = new StringBuilder();
        sb.append("Width=");
        sb.append(this.l);
        sb.append("  height=");
        sb.append(this.m);
        Log.d("Width", sb.toString());
        if (VERSION.SDK_INT > 16) {
            try {
                this.w = Integer.valueOf(mediaMetadataRetriever.extractMetadata(24)).intValue();
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
        } else {
            try {
                this.w = 0;
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.B.getLayoutParams();
        if (this.w == 90 || this.w == 270) {
            try {
                if (this.l >= this.m) {
                    try {
                        layoutParams.height = this.e;
                        layoutParams.width = (int) (((float) this.e) / (((float) this.l) / ((float) this.m)));
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                } else {
                    try {
                        layoutParams.width = this.e;
                        layoutParams.height = (int) (((float) this.e) / (((float) this.m) / ((float) this.l)));
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        } else if (this.l >= this.m) {
            try {
                layoutParams.width = this.e;
                layoutParams.height = (int) (((float) this.e) / (((float) this.l) / ((float) this.m)));
            } catch (Exception e7) {
                e7.printStackTrace();
            }
        } else {
            try {
                layoutParams.width = (int) (((float) this.e) / (((float) this.m) / ((float) this.l)));
                layoutParams.height = this.e;
            } catch (Exception e8) {
                e8.printStackTrace();
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Width=");
        sb2.append(layoutParams.width);
        sb2.append("  height=");
        sb2.append(layoutParams.height);
        Log.d("===Width layout param", sb2.toString());
        this.B.setLayoutParams(layoutParams);
        this.B.setImageBitmap(Bitmap.createBitmap(layoutParams.width, layoutParams.height, Config.ARGB_8888));
        try {
            SearchVideo(getApplicationContext(), this.u, layoutParams.width, layoutParams.height);
        } catch (Exception unused) {
        }
    }


    public void d() {
        if (this.D.isPlaying()) {
            try {
                this.D.pause();
                this.C.setSliceBlocked(false);
                this.C.removeVideoStatusThumb();
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        this.D.seekTo(this.C.getLeftProgress());
        this.D.start();
        this.C.videoPlayingProgress(this.C.getLeftProgress());
        this.M.a();
    }

    public static String getTimeForTrackFormat(int i2, boolean z2) {
        String str;
        int i3 = i2 / 60000;
        int i4 = (i2 - ((i3 * 60) * 1000)) / 1000;
        StringBuilder sb = new StringBuilder(String.valueOf((!z2 || i3 >= 10) ? "" : "0"));
        sb.append(i3 % 60);
        sb.append(":");
        String sb2 = sb.toString();
        if (i4 < 10) {
            try {
                StringBuilder sb3 = new StringBuilder(String.valueOf(sb2));
                sb3.append("0");
                sb3.append(i4);
                str = sb3.toString();
            } catch (Exception e2) {
                e2.printStackTrace();
                str = sb2;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Display Result");
                sb4.append(str);
                Log.e("", sb4.toString());
                return str;
            }
        } else {
            try {
                StringBuilder sb5 = new StringBuilder(String.valueOf(sb2));
                sb5.append(i4);
                str = sb5.toString();
            } catch (Exception e3) {
                e3.printStackTrace();
                str = sb2;
                StringBuilder sb42 = new StringBuilder();
                sb42.append("Display Result");
                sb42.append(str);
                Log.e("", sb42.toString());
                return str;
            }
        }
        StringBuilder sb422 = new StringBuilder();
        sb422.append("Display Result");
        sb422.append(str);
        Log.e("", sb422.toString());
        return str;
    }

    private void e() {
        if (this.w == 90 || this.w == 270) {
            try {
                float f2 = (float) this.l;
                float height = (float) this.B.getHeight();
                int width = (int) (((float) this.m) / ((float) this.B.getWidth()));
                this.c = width;
                this.b = width;
                int i2 = (int) (f2 / height);
                this.a = i2;
                this.d = i2;
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        float f3 = (float) this.m;
        float height2 = (float) this.B.getHeight();
        int width2 = (int) (((float) this.l) / ((float) this.B.getWidth()));
        this.c = width2;
        this.b = width2;
        int i3 = (int) (f3 / height2);
        this.a = i3;
        this.d = i3;
    }

    public OnClickListener setRatioOriginal() {
        return new OnClickListener() {
            @Override public void onClick(View view) {
            }
        };
    }

    public OnClickListener setRatioFixed() {
        return new OnClickListener() {
            @Override public void onClick(View view) {
            }
        };
    }

    public void SearchVideo(Context context, String str, int i2, int i3) {
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        String[] strArr = {"_data", "_id"};
        StringBuilder sb = new StringBuilder();
        sb.append("%");
        sb.append(str);
        sb.append("%");
        Cursor managedQuery = managedQuery(uri, strArr, "_data  like ?", new String[]{sb.toString()}, " _id DESC");
        int count = managedQuery.getCount();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("count");
        sb2.append(count);
        Log.e("", sb2.toString());
        if (count > 0) {
            try {
                managedQuery.moveToFirst();
                Bitmap createScaledBitmap = Bitmap.createScaledBitmap(Thumbnails.getThumbnail(getContentResolver(), Long.valueOf(managedQuery.getLong(managedQuery.getColumnIndexOrThrow("_id"))).longValue(), 1, null), i2, i3, false);
                ViewGroup.LayoutParams layoutParams = this.A.getLayoutParams();
                layoutParams.width = i2;
                layoutParams.height = i3;
                this.A.setLayoutParams(layoutParams);
                this.A.setImageBitmap(createScaledBitmap);
                managedQuery.moveToNext();
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return I;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            finish();
            return I;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (this.D != null && this.D.isPlaying()) {
                try {
                    this.D.pause();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            e();
            new a().execute(new Void[0]);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
