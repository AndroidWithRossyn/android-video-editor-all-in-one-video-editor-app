package com.androworld.allinonevideoeditor.audiocutter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.provider.MediaStore.Audio.Media;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androworld.allinonevideoeditor.Adclick;
import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.AudioPlayer;
import com.androworld.allinonevideoeditor.CustomEditText;
import com.androworld.allinonevideoeditor.CustomTextView;
import com.androworld.allinonevideoeditor.Helper;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.audiocutter.cutter.CheapSoundFile;
import com.androworld.allinonevideoeditor.audiocutter.cutter.CheapSoundFile.ProgressListener;
import com.androworld.allinonevideoeditor.audiocutter.cutter.MarkerView;
import com.androworld.allinonevideoeditor.audiocutter.cutter.MarkerView.MarkerListener;
import com.androworld.allinonevideoeditor.audiocutter.cutter.SeekTest;
import com.androworld.allinonevideoeditor.audiocutter.cutter.SongMetadataReader;
import com.androworld.allinonevideoeditor.audiocutter.cutter.WaveformView;
import com.androworld.allinonevideoeditor.audiocutter.cutter.WaveformView.WaveformListener;
import com.androworld.allinonevideoeditor.listmusicandmymusic.ListMusicAndMyMusicActivity;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MP3CutterActivity extends AppCompatActivity implements MarkerListener, WaveformListener {
    private int Rs;
    static final boolean l = true;
    public static String outputfilename;
    private int A;
    private Uri B;
    private boolean C;

    public WaveformView D;

    public MarkerView E;

    public MarkerView F;
    private TextView G;
    private ImageButton H;
    private ImageButton I;
    private ImageButton J;
    private ImageButton K;
    private ImageButton L;
    private boolean M;
    private String N = "";

    public int O;

    public int P;
    private int Q;

    public boolean S;

    public boolean T;

    public int U;
    private int V;

    public int W;
    private int X;
    private int Y;
    private int Z;
    AudioManager a;
    private OnClickListener aA = new OnClickListener() {
        @Override public void onClick(View view) {
            try {
                AudioManager audioManager = MP3CutterActivity.this.a;
                audioManager.adjustStreamVolume(3, -1, 1);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (NoSuchMethodError e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            } catch (NullPointerException e4) {
                e4.printStackTrace();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
    };
    private OnClickListener aB = new OnClickListener() {
        @Override public void onClick(View view) {
            try {
                AudioManager audioManager = MP3CutterActivity.this.a;
                AudioManager audioManager2 = MP3CutterActivity.this.a;
                AudioManager audioManager3 = MP3CutterActivity.this.a;
                AudioManager audioManager4 = MP3CutterActivity.this.a;
                audioManager.adjustStreamVolume(3, 1, 1);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (NoSuchMethodError e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            } catch (NullPointerException e4) {
                e4.printStackTrace();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
    };

    public int aa;

    public int ab;

    public Handler ac;

    public MediaPlayer ad;

    public boolean ae;
    private boolean af;

    public boolean ag;
    private float ah;
    private int ai;
    private int aj;
    private int ak;
    private long al;

    public float am;
    private int an;
    private int ao;
    private int ap;
    private int aq;
    private Typeface ar;

    public EditText as;

    public EditText at;
    private TextWatcher av = new TextWatcher() {
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void afterTextChanged(Editable editable) {
            if (MP3CutterActivity.this.as.hasFocus()) {
                try {
                    MP3CutterActivity.this.P = MP3CutterActivity.this.D.secondsToPixels(Double.parseDouble(MP3CutterActivity.this.as.getText().toString()));
                    MP3CutterActivity.this.g();
                } catch (NumberFormatException unused) {
                }
            }
            if (MP3CutterActivity.this.at.hasFocus()) {
                try {
                    MP3CutterActivity.this.O = MP3CutterActivity.this.D.secondsToPixels(Double.parseDouble(MP3CutterActivity.this.at.getText().toString()));
                    MP3CutterActivity.this.g();
                } catch (NumberFormatException unused2) {
                }
            }
        }
    };

    public Runnable aw = new Runnable() {
        public void run() {
            if (MP3CutterActivity.this.P != MP3CutterActivity.this.ab && !MP3CutterActivity.this.as.hasFocus()) {
                MP3CutterActivity.this.as.setText(MP3CutterActivity.this.d(MP3CutterActivity.this.P));
                if (MP3CutterActivity.this.d(MP3CutterActivity.this.P) == "") {
                    int parseFloat = (int) Float.parseFloat("00.00");
                    int i = parseFloat / 60;
                    if (i <= 9) {
                        MP3CutterActivity mP3CutterActivity = MP3CutterActivity.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("0");
                        sb.append(i);
                        mP3CutterActivity.f = sb.toString();
                    } else {
                        int i2 = i % 60;
                        if (i2 <= 9) {
                            MP3CutterActivity mP3CutterActivity2 = MP3CutterActivity.this;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("0");
                            sb2.append(i2);
                            mP3CutterActivity2.h = sb2.toString();
                        } else {
                            int i3 = parseFloat % 60;
                            if (i3 <= 9) {
                                MP3CutterActivity mP3CutterActivity3 = MP3CutterActivity.this;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("0");
                                sb3.append(i3);
                                mP3CutterActivity3.j = sb3.toString();
                            }
                        }
                    }
                } else {
                    int parseFloat2 = (int) Float.parseFloat(MP3CutterActivity.this.d(MP3CutterActivity.this.P));
                    int i4 = parseFloat2 / 3600;
                    if (i4 <= 9) {
                        MP3CutterActivity mP3CutterActivity4 = MP3CutterActivity.this;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("0");
                        sb4.append(i4);
                        mP3CutterActivity4.f = sb4.toString();
                    } else {
                        MP3CutterActivity mP3CutterActivity5 = MP3CutterActivity.this;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("");
                        sb5.append(i4);
                        mP3CutterActivity5.f = sb5.toString();
                    }
                    int i5 = (parseFloat2 / 60) % 60;
                    if (i5 <= 9) {
                        MP3CutterActivity mP3CutterActivity6 = MP3CutterActivity.this;
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("0");
                        sb6.append(i5);
                        mP3CutterActivity6.h = sb6.toString();
                    } else {
                        MP3CutterActivity mP3CutterActivity7 = MP3CutterActivity.this;
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append("");
                        sb7.append(i5);
                        mP3CutterActivity7.h = sb7.toString();
                    }
                    int i6 = parseFloat2 % 60;
                    if (i6 <= 9) {
                        MP3CutterActivity mP3CutterActivity8 = MP3CutterActivity.this;
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("0");
                        sb8.append(i6);
                        mP3CutterActivity8.j = sb8.toString();
                    } else {
                        MP3CutterActivity mP3CutterActivity9 = MP3CutterActivity.this;
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append("");
                        sb9.append(i6);
                        mP3CutterActivity9.j = sb9.toString();
                    }
                }
                MP3CutterActivity.this.ab = MP3CutterActivity.this.P;
            }
            if (MP3CutterActivity.this.O != MP3CutterActivity.this.aa && !MP3CutterActivity.this.at.hasFocus()) {
                MP3CutterActivity.this.at.setText(MP3CutterActivity.this.d(MP3CutterActivity.this.O));
                if (MP3CutterActivity.this.d(MP3CutterActivity.this.O) != "") {
                    int parseFloat3 = (int) Float.parseFloat(MP3CutterActivity.this.d(MP3CutterActivity.this.O - MP3CutterActivity.this.P));
                    int i7 = parseFloat3 / 3600;
                    if (i7 <= 9) {
                        MP3CutterActivity mP3CutterActivity10 = MP3CutterActivity.this;
                        StringBuilder sb10 = new StringBuilder();
                        sb10.append("0");
                        sb10.append(i7);
                        mP3CutterActivity10.g = sb10.toString();
                    } else {
                        MP3CutterActivity mP3CutterActivity11 = MP3CutterActivity.this;
                        StringBuilder sb11 = new StringBuilder();
                        sb11.append("");
                        sb11.append(i7);
                        mP3CutterActivity11.g = sb11.toString();
                    }
                    int i8 = (parseFloat3 / 60) % 60;
                    if (i8 <= 9) {
                        MP3CutterActivity mP3CutterActivity12 = MP3CutterActivity.this;
                        StringBuilder sb12 = new StringBuilder();
                        sb12.append("0");
                        sb12.append(i8);
                        mP3CutterActivity12.i = sb12.toString();
                    } else {
                        MP3CutterActivity mP3CutterActivity13 = MP3CutterActivity.this;
                        StringBuilder sb13 = new StringBuilder();
                        sb13.append("");
                        sb13.append(i8);
                        mP3CutterActivity13.i = sb13.toString();
                    }
                    int i9 = parseFloat3 % 60;
                    if (i9 <= 9) {
                        MP3CutterActivity mP3CutterActivity14 = MP3CutterActivity.this;
                        StringBuilder sb14 = new StringBuilder();
                        sb14.append("0");
                        sb14.append(i9);
                        mP3CutterActivity14.k = sb14.toString();
                    } else {
                        MP3CutterActivity mP3CutterActivity15 = MP3CutterActivity.this;
                        StringBuilder sb15 = new StringBuilder();
                        sb15.append("");
                        sb15.append(i9);
                        mP3CutterActivity15.k = sb15.toString();
                    }
                }
                MP3CutterActivity.this.aa = MP3CutterActivity.this.O;
            }
            MP3CutterActivity.this.ac.postDelayed(MP3CutterActivity.this.aw, 100);
        }
    };
    private OnClickListener ax = new OnClickListener() {
        @Override public void onClick(View view) {
            MP3CutterActivity.this.e(MP3CutterActivity.this.P);
        }
    };
    private OnClickListener ay = new OnClickListener() {
        @Override public void onClick(View view) {
            if (MP3CutterActivity.this.ae) {
                int currentPosition = MP3CutterActivity.this.ad.getCurrentPosition() - 5000;
                if (currentPosition < MP3CutterActivity.this.W) {
                    currentPosition = MP3CutterActivity.this.W;
                }
                MP3CutterActivity.this.ad.seekTo(currentPosition);
                return;
            }
            MP3CutterActivity.this.F.requestFocus();
            MP3CutterActivity.this.markerFocus(MP3CutterActivity.this.F);
        }
    };
    private OnClickListener az = new OnClickListener() {
        @Override public void onClick(View view) {
            try {
                if (MP3CutterActivity.this.ae) {
                    int currentPosition = BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT + MP3CutterActivity.this.ad.getCurrentPosition();
                    if (currentPosition > MP3CutterActivity.this.U) {
                        currentPosition = MP3CutterActivity.this.U;
                    }
                    MP3CutterActivity.this.ad.seekTo(currentPosition);
                    return;
                }
                MP3CutterActivity.this.E.requestFocus();
                MP3CutterActivity.this.markerFocus(MP3CutterActivity.this.E);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (NoSuchMethodError e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            } catch (NullPointerException e4) {
                e4.printStackTrace();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
    };
    String b;
    String c;
    long d;
    Dialog e;
    String f;
    String g;
    String h;
    String i;
    String j;
    String k;

    public long m;
    private long n;

    public boolean o;

    public ProgressDialog p;

    public CheapSoundFile q;

    public File r;
    private String s;
    private String t;
    private String u;
    private String v;
    private String w;
    private String x;
    private String y;

    public String z;
    private Ads ads;


    public void a(CharSequence charSequence, CharSequence charSequence2, Exception exc) {
    }

    public void markerDraw() {
    }

    public void markerEnter(MarkerView markerView) {
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.s = null;
        this.B = null;
        this.ad = null;
        this.ae = false;
        a();
        this.C = getIntent().getBooleanExtra("was_get_content_intent", false);
        this.z = Helper.audiopath;
        this.q = null;
        this.M = false;
        this.z.equals("record");
        this.ac = new Handler();
        this.ac.postDelayed(this.aw, 100);
        if (!this.z.equals("record")) {
            e();
        }
    }


    @Override public void onDestroy() {
        if (this.ad != null && this.ad.isPlaying()) {
            this.ad.stop();
        }
        this.ad = null;
        if (this.s != null) {
            try {
                if (!new File(this.s).delete()) {
                    a(new Exception(), (int) R.string.delete_tmp_error);
                }
                getContentResolver().delete(this.B, null, null);
            } catch (SecurityException e2) {
                a((Exception) e2, (int) R.string.delete_tmp_error);
            }
        }
        super.onDestroy();
    }


    @Override public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        e();
    }

    public void onActivityResult(Configuration configuration) {
        final int zoomLevel = this.D.getZoomLevel();
        super.onConfigurationChanged(configuration);
        a();
        this.ac.postDelayed(new Runnable() {
            public void run() {
                MP3CutterActivity.this.F.requestFocus();
                MP3CutterActivity.this.markerFocus(MP3CutterActivity.this.F);
                MP3CutterActivity.this.D.setZoomLevel(zoomLevel);
                MP3CutterActivity.this.D.recomputeHeights(MP3CutterActivity.this.am);
                MP3CutterActivity.this.g();
            }
        }, 500);
    }

    public void waveformDraw() {
        this.Rs = this.D.getMeasuredWidth();
        if (this.Y != this.Z && !this.M) {
            g();
        } else if (this.ae) {
            g();
        } else if (this.X != 0) {
            g();
        }
    }

    public void waveformTouchStart(float f2) {
        this.af = l;
        this.ah = f2;
        this.ak = this.Z;
        this.X = 0;
        this.al = System.currentTimeMillis();
    }

    public void waveformTouchMove(float f2) {
        this.Z = a((int) (((float) this.ak) + (this.ah - f2)));
        g();
    }

    public void waveformTouchEnd() {
        this.af = false;
        this.Y = this.Z;
        if (System.currentTimeMillis() - this.al >= 300) {
            return;
        }
        if (this.ae) {
            int pixelsToMillisecs = this.D.pixelsToMillisecs((int) (this.ah + ((float) this.Z)));
            if (pixelsToMillisecs < this.W || pixelsToMillisecs >= this.U) {
                n();
            } else {
                this.ad.seekTo(pixelsToMillisecs - this.V);
            }
        } else {
            e((int) (this.ah + ((float) this.Z)));
        }
    }

    public void waveformFling(float f2) {
        this.af = false;
        this.Y = this.Z;
        this.X = (int) (-f2);
        g();
    }

    public void markerTouchStart(MarkerView markerView, float f2) {
        this.af = l;
        this.ah = f2;
        this.aj = this.P;
        this.ai = this.O;
    }

    public void markerTouchMove(MarkerView markerView, float f2) {
        float f3 = f2 - this.ah;
        if (markerView == this.F) {
            this.P = a((int) (((float) this.aj) + f3));
            this.O = a((int) (((float) this.ai) + f3));
        } else {
            this.O = a((int) (((float) this.ai) + f3));
            if (this.O < this.P) {
                this.O = this.P;
            }
        }
        g();
    }

    public void markerTouchEnd(MarkerView markerView) {
        this.af = false;
        if (markerView == this.F) {
            j();
        } else {
            l();
        }
    }

    public void markerLeft(MarkerView markerView, int i2) {
        this.M = l;
        if (markerView == this.F) {
            int i3 = this.P;
            this.P = a(this.P - i2);
            this.O = a(this.O - (i3 - this.P));
            j();
        }
        if (markerView == this.E) {
            if (this.O == this.P) {
                this.P = a(this.P - i2);
                this.O = this.P;
            } else {
                this.O = a(this.O - i2);
            }
            l();
        }
        g();
    }

    public void markerRight(MarkerView markerView, int i2) {
        this.M = l;
        if (markerView == this.F) {
            int i3 = this.P;
            this.P += i2;
            if (this.P > this.Q) {
                this.P = this.Q;
            }
            this.O += this.P - i3;
            if (this.O > this.Q) {
                this.O = this.Q;
            }
            j();
        }
        if (markerView == this.E) {
            this.O += i2;
            if (this.O > this.Q) {
                this.O = this.Q;
            }
            l();
        }
        g();
    }

    public void markerKeyUp() {
        this.M = false;
        g();
    }

    public void markerFocus(MarkerView markerView) {
        this.M = false;
        if (markerView == this.F) {
            k();
        } else {
            m();
        }
        this.ac.postDelayed(new Runnable() {
            public void run() {
                MP3CutterActivity.this.g();
            }
        }, 100);
    }

    private void a() {
        setContentView( R.layout.mp3cutteractivity);
        LinearLayout sa = (LinearLayout) findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(MP3CutterActivity.this,sa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("MP3 Cutter");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (l || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(l);
            supportActionBar.setDisplayShowTitleEnabled(false);
            getApplicationContext();
            this.a = (AudioManager) getSystemService(AUDIO_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.am = displayMetrics.density;
            this.aq = (int) (46.0f * this.am);
            this.ap = (int) (48.0f * this.am);
            this.ao = (int) (this.am * 10.0f);
            this.an = (int) (10.0f * this.am);
            this.G = (TextView) findViewById(R.id.songname);
            this.ar = Typeface.createFromAsset(getAssets(), Helper.FontStyle);
            this.as = (EditText) findViewById(R.id.starttext);
            this.as.setTypeface(this.ar);
            this.as.addTextChangedListener(this.av);
            this.at = (EditText) findViewById(R.id.endtext);
            this.at.setTypeface(this.ar);
            this.at.addTextChangedListener(this.av);
            this.L = (ImageButton) findViewById(R.id.play);
            this.L.setOnClickListener(this.ax);
            this.K = (ImageButton) findViewById(R.id.rew);
            this.K.setOnClickListener(this.ay);
            this.J = (ImageButton) findViewById(R.id.ffwd);
            this.J.setOnClickListener(this.az);
            this.H = (ImageButton) findViewById(R.id.btnvolumdown);
            this.H.setOnClickListener(this.aA);
            this.I = (ImageButton) findViewById(R.id.btnvolumup);
            this.I.setOnClickListener(this.aB);
            h();
            this.D = (WaveformView) findViewById(R.id.waveform);
            this.D.setListener(this);
            this.Q = 0;
            this.ab = -1;
            this.aa = -1;
            if (this.q != null) {
                this.D.setSoundFile(this.q);
                this.D.recomputeHeights(this.am);
                this.Q = this.D.maxPos();
            }
            this.F = (MarkerView) findViewById(R.id.startmarker);
            this.F.setListener(this);
            this.F.setAlpha(255);
            this.F.setFocusable(l);
            this.F.setFocusableInTouchMode(l);
            this.T = l;
            this.E = (MarkerView) findViewById(R.id.endmarker);
            this.E.setListener(this);
            this.E.setAlpha(255);
            this.E.setFocusable(l);
            this.E.setFocusableInTouchMode(l);
            this.S = l;
            g();

            ads =  new Ads();
            ads.Interstitialload(this);

            return;
        }
        throw new AssertionError();
    }


    private void c() {
        ads.showInd(new Adclick() {
            @Override
            public void onclicl() {
                d();
            }
        });
  }


    public void d() {
        Intent intent = new Intent(this, AudioPlayer.class);
        Bundle bundle = new Bundle();
        bundle.putString("song", this.b);
        bundle.putBoolean("isfrom", l);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void e() {
        this.r = new File(this.z);
        this.t = a(this.z);
        SongMetadataReader songMetadataReader = new SongMetadataReader(this, this.z);
        this.u = songMetadataReader.mTitle;
        this.x = songMetadataReader.mArtist;
        this.w = songMetadataReader.mAlbum;
        this.A = songMetadataReader.mYear;
        this.v = songMetadataReader.mGenre;
        String str = this.u;
        if (this.x != null && this.x.length() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" - ");
            sb.append(this.x);
            str = sb.toString();
        }
        this.G.setText(str);
        this.G.setSelected(l);
        this.n = System.currentTimeMillis();
        this.m = System.currentTimeMillis();
        this.o = l;
        this.p = new ProgressDialog(this);
        this.p.setProgressStyle(1);
        this.p.setTitle(R.string.progress_dialog_loading);
        this.p.setCancelable(false);
        this.p.show();
        final ProgressListener r0 = new ProgressListener() {
            public boolean reportProgress(double d) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - MP3CutterActivity.this.m > 100) {
                    MP3CutterActivity.this.p.setProgress((int) (((double) MP3CutterActivity.this.p.getMax()) * d));
                    MP3CutterActivity.this.m = currentTimeMillis;
                }
                return MP3CutterActivity.this.o;
            }
        };
        this.ag = false;
        new Thread() {
            public void run() {
                MP3CutterActivity.this.ag = SeekTest.CanSeekAccurately(MP3CutterActivity.this.getPreferences(0));
                System.out.println("Seek test done, creating media player.");
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(MP3CutterActivity.this.r.getAbsolutePath());
                    AudioManager audioManager = MP3CutterActivity.this.a;
                    mediaPlayer.setAudioStreamType(3);
                    mediaPlayer.prepare();
                    MP3CutterActivity.this.ad = mediaPlayer;
                } catch (final IOException e) {
                    MP3CutterActivity.this.ac.post(new Runnable() {
                        public void run() {
                            MP3CutterActivity.this.a("ReadError", MP3CutterActivity.this.getResources().getText(R.string.read_error), e);
                        }
                    });
                }
            }
        }.start();
        new Thread() {
            public void run() {
                final String str;
                try {
                    MP3CutterActivity.this.q = CheapSoundFile.create(MP3CutterActivity.this.r.getAbsolutePath(), r0);
                    if (MP3CutterActivity.this.q == null) {
                        MP3CutterActivity.this.p.dismiss();
                        String[] split = MP3CutterActivity.this.r.getName().toLowerCase().split("\\.");
                        if (split.length < 2) {
                            str = MP3CutterActivity.this.getResources().getString(R.string.no_extension_error);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append(MP3CutterActivity.this.getResources().getString(R.string.bad_extension_error));
                            sb.append(" ");
                            sb.append(split[split.length - 1]);
                            str = sb.toString();
                        }
                        MP3CutterActivity.this.ac.post(new Runnable() {
                            public void run() {
                                MP3CutterActivity.this.a("UnsupportedExtension", str, new Exception());
                            }
                        });
                        return;
                    }
                    MP3CutterActivity.this.p.dismiss();
                    if (MP3CutterActivity.this.o) {
                        MP3CutterActivity.this.ac.post(new Runnable() {
                            public void run() {
                                MP3CutterActivity.this.f();
                            }
                        });
                    } else {
                        MP3CutterActivity.this.finish();
                    }
                } catch (final Exception e) {
                    MP3CutterActivity.this.p.dismiss();
                    e.printStackTrace();
                    MP3CutterActivity.this.ac.post(new Runnable() {
                        public void run() {
                            MP3CutterActivity.this.a("ReadError", MP3CutterActivity.this.getResources().getText(R.string.read_error), e);
                        }
                    });
                }
            }
        }.start();
    }


    public void f() {
        this.D.setSoundFile(this.q);
        this.D.recomputeHeights(this.am);
        this.Q = this.D.maxPos();
        this.ab = -1;
        this.aa = -1;
        this.af = false;
        this.Z = 0;
        this.Y = 0;
        this.X = 0;
        i();
        if (this.O > this.Q) {
            this.O = this.Q;
        }
        g();
    }


    public synchronized void g() {
        if (this.ae) {
            int currentPosition = this.ad.getCurrentPosition() + this.V;
            int millisecsToPixels = this.D.millisecsToPixels(currentPosition);
            this.D.setPlayback(millisecsToPixels);
            c(millisecsToPixels - (this.Rs / 2));
            if (currentPosition >= this.U) {
                n();
            }
        }
        int i2 = 0;
        if (!this.af) {
            if (this.X != 0) {
                int i3 = this.X;
                int i4 = this.X / 30;
                if (this.X > 80) {
                    this.X -= 80;
                } else if (this.X < -80) {
                    this.X += 80;
                } else {
                    this.X = 0;
                }
                this.Z += i4;
                if (this.Z + (this.Rs / 2) > this.Q) {
                    this.Z = this.Q - (this.Rs / 2);
                    this.X = 0;
                }
                if (this.Z < 0) {
                    this.Z = 0;
                    this.X = 0;
                }
                this.Y = this.Z;
            } else {
                int i5 = this.Y - this.Z;
                int i6 = i5 > 10 ? i5 / 10 : i5 > 0 ? 1 : i5 < -10 ? i5 / 10 : i5 < 0 ? -1 : 0;
                this.Z += i6;
            }
        }
        this.D.setParameters(this.P, this.O, this.Z);
        this.D.invalidate();
        MarkerView markerView = this.F;
        StringBuilder sb = new StringBuilder();
        sb.append(getResources().getText(R.string.start_marker));
        sb.append(" ");
        sb.append(d(this.P));
        markerView.setContentDescription(sb.toString());
        MarkerView markerView2 = this.E;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getResources().getText(R.string.end_marker));
        sb2.append(" ");
        sb2.append(d(this.O));
        markerView2.setContentDescription(sb2.toString());
        int i7 = (this.P - this.Z) - this.aq;
        if (this.F.getWidth() + i7 < 0) {
            if (this.T) {
                this.F.setAlpha(0);
                this.T = false;
            }
            i7 = 0;
        } else if (!this.T) {
            this.ac.postDelayed(new Runnable() {
                public void run() {
                    MP3CutterActivity.this.T = MP3CutterActivity.l;
                    MP3CutterActivity.this.F.setAlpha(255);
                }
            }, 0);
        }
        int width = ((this.O - this.Z) - this.E.getWidth()) + this.ap;
        if (this.E.getWidth() + width >= 0) {
            if (!this.S) {
                this.ac.postDelayed(new Runnable() {
                    public void run() {
                        MP3CutterActivity.this.S = MP3CutterActivity.l;
                        MP3CutterActivity.this.E.setAlpha(255);
                    }
                }, 0);
            }
            i2 = width;
        } else if (this.S) {
            this.E.setAlpha(0);
            this.S = false;
        }
        this.F.setLayoutParams(new LayoutParams(-2, -2, i7, this.ao));
        this.E.setLayoutParams(new LayoutParams(-2, -2, i2, (this.D.getMeasuredHeight() - this.E.getHeight()) - this.an));
    }

    private void h() {
        if (this.ae) {
            this.L.setImageResource(R.drawable.ic_playlist_pause);
            this.L.setContentDescription(getResources().getText(R.string.stop));
            return;
        }
        this.L.setImageResource(R.drawable.ic_playlist_play);
        this.L.setContentDescription(getResources().getText(R.string.play));
    }

    private void i() {
        this.P = this.D.secondsToPixels(0.0d);
        this.O = this.D.secondsToPixels(15.0d);
    }

    private int a(int i2) {
        if (i2 < 0) {
            return 0;
        }
        return i2 > this.Q ? this.Q : i2;
    }

    private void j() {
        b(this.P - (this.Rs / 2));
    }

    private void k() {
        c(this.P - (this.Rs / 2));
    }

    private void l() {
        b(this.O - (this.Rs / 2));
    }

    private void m() {
        c(this.O - (this.Rs / 2));
    }

    private void b(int i2) {
        c(i2);
        g();
    }

    private void c(int i2) {
        if (!this.af) {
            this.Y = i2;
            if (this.Y + (this.Rs / 2) > this.Q) {
                this.Y = this.Q - (this.Rs / 2);
            }
            if (this.Y < 0) {
                this.Y = 0;
            }
        }
    }


    public String d(int i2) {
        return (this.D == null || !this.D.isInitialized()) ? "" : a(this.D.pixelsToSeconds(i2));
    }

    private String a(double d2) {
        int i2 = (int) d2;
        int i3 = (int) ((100.0d * (d2 - ((double) i2))) + 0.5d);
        if (i3 >= 100) {
            i2++;
            i3 -= 100;
            if (i3 < 10) {
                i3 *= 10;
            }
        }
        if (i3 < 10) {
            StringBuilder sb = new StringBuilder();
            sb.append(i2);
            sb.append(".0");
            sb.append(i3);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(i2);
        sb2.append(".");
        sb2.append(i3);
        return sb2.toString();
    }


    public synchronized void n() {
        if (this.ad != null && this.ad.isPlaying()) {
            this.ad.pause();
        }
        this.D.setPlayback(-1);
        this.ae = false;
        h();
    }
  public synchronized void e(int i2) {
        try {
            if (this.ae) {
                n();
            } else if (this.ad != null) {
                this.W = this.D.pixelsToMillisecs(i2);
                if (i2 < this.P) {
                    this.U = this.D.pixelsToMillisecs(this.P);
                } else if (i2 > this.O) {
                    this.U = this.D.pixelsToMillisecs(this.Q);
                } else {
                    this.U = this.D.pixelsToMillisecs(this.O);
                }
                this.V = 0;
                int secondsToFrames = this.D.secondsToFrames(((double) this.W) * 0.001d);
                int secondsToFrames2 = this.D.secondsToFrames(((double) this.U) * 0.001d);
                int seekableFrameOffset = this.q.getSeekableFrameOffset(secondsToFrames);
                int seekableFrameOffset2 = this.q.getSeekableFrameOffset(secondsToFrames2);
                if (this.ag && seekableFrameOffset >= 0 && seekableFrameOffset2 >= 0) {
                    this.ad.reset();
                    MediaPlayer mediaPlayer = this.ad;
                    AudioManager audioManager = this.a;
                    mediaPlayer.setAudioStreamType(3);
                    this.ad.setDataSource(new FileInputStream(this.r.getAbsolutePath()).getFD(), (long) seekableFrameOffset, (long) (seekableFrameOffset2 - seekableFrameOffset));
                    this.ad.prepare();
                    this.V = this.W;
                    System.out.println("Exception trying to play file subset");
                    this.ad.reset();
                    MediaPlayer mediaPlayer2 = this.ad;
                    AudioManager audioManager2 = this.a;
                    mediaPlayer2.setAudioStreamType(3);
                    this.ad.setDataSource(this.r.getAbsolutePath());
                    this.ad.prepare();
                    this.V = 0;
                }
                this.ad.setOnCompletionListener(new OnCompletionListener() {
                    public synchronized void onCompletion(MediaPlayer mediaPlayer) {
                        MP3CutterActivity.this.n();
                    }
                });
                this.ae = l;
                if (this.V == 0) {
                    this.ad.seekTo(this.W);
                }
                this.ad.start();
                g();
                h();
            }
        } catch (Exception e2) {
            a(e2, (int) R.string.play_error);
        }
    }

    private void a(Exception exc, CharSequence charSequence) {
        CharSequence charSequence2;
        if (exc != null) {
            charSequence2 = getResources().getText(R.string.alert_title_failure);
            setResult(0, new Intent());
        } else {
            charSequence2 = getResources().getText(R.string.alert_title_success);
        }
        new AlertDialog.Builder(this).setTitle(charSequence2).setMessage(charSequence).setPositiveButton((int) R.string.alert_ok_button, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                MP3CutterActivity.this.finish();
            }
        }).setCancelable(false).show();
    }

    private void a(Exception exc, int i2) {
        a(exc, getResources().getText(i2));
    }

    private String a(String str) {
        return str.substring(str.lastIndexOf(46), str.length());
    }

    @Override public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ListMusicAndMyMusicActivity.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return l;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return l;
        }
        if (menuItem.getItemId() == R.id.Done) {
            this.e = new Dialog(this);
            this.e.setCanceledOnTouchOutside(false);
            this.e.requestWindowFeature(1);
            this.e.setContentView(R.layout.mp3cutter_enterfilename_popup);
            this.e.show();
            ((ImageView) this.e.findViewById(R.id.closePopup)).setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    MP3CutterActivity.this.e.dismiss();
                }
            });
            ((CustomTextView) this.e.findViewById(R.id.DailogName)).setText("MP3 Cutter");
            final CustomEditText customEditText = (CustomEditText) this.e.findViewById(R.id.message);
            ((CustomTextView) this.e.findViewById(R.id.sendBtn)).setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    MP3CutterActivity.this.c = customEditText.getText().toString();
                    if (MP3CutterActivity.this.c.isEmpty()) {
                        Toast.makeText(MP3CutterActivity.this, "Please Enter File Name", Toast.LENGTH_LONG).show();
                        return;
                    }
                    StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                    long availableBlocks = ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
                    File file = new File(MP3CutterActivity.this.z);
                    MP3CutterActivity.this.d = 0;
                    MP3CutterActivity.this.d = file.length() / 1024;
                    if ((availableBlocks / 1024) / 1024 >= MP3CutterActivity.this.d / 1024) {
                        MP3CutterActivity.this.e.dismiss();
                        MP3CutterActivity.this.a((CharSequence) MP3CutterActivity.this.c);
                        return;
                    }
                    Toast.makeText(MP3CutterActivity.this.getApplicationContext(), "Out of Memory!......", Toast.LENGTH_SHORT).show();
                    MP3CutterActivity.this.e.dismiss();
                }
            });
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void a(CharSequence charSequence) {
        final String a2 = a(charSequence, this.t);
        if (a2 == null) {
            a(new Exception(), (int) R.string.no_unique_filename);
            return;
        }
        this.y = a2;
        double pixelsToSeconds = this.D.pixelsToSeconds(this.P);
        double pixelsToSeconds2 = this.D.pixelsToSeconds(this.O);
        int secondsToFrames = this.D.secondsToFrames(pixelsToSeconds);
        int secondsToFrames2 = this.D.secondsToFrames(pixelsToSeconds2);
        int i2 = (int) ((pixelsToSeconds2 - pixelsToSeconds) + 0.5d);
        this.p = new ProgressDialog(this);
        this.p.setProgressStyle(0);
        this.p.setTitle(R.string.progress_dialog_saving);
        this.p.setIndeterminate(l);
        this.p.setCancelable(false);
        this.p.show();
        final int i3 = secondsToFrames;
        final int i4 = secondsToFrames2;
        final CharSequence charSequence2 = charSequence;
        final int i5 = i2;
        Thread r1 = new Thread() {
            public void run() {
                final Exception exc;
                final CharSequence charSequence;
                final File file = new File(a2);
                try {
                    MP3CutterActivity.this.q.WriteFile(file, i3, i4 - i3);
                    CheapSoundFile.create(a2, new ProgressListener() {
                        public boolean reportProgress(double d) {
                            return MP3CutterActivity.l;
                        }
                    });
                    MP3CutterActivity.this.p.dismiss();
                    MP3CutterActivity.this.ac.post(new Runnable() {
                        public void run() {
                            MP3CutterActivity.this.a(charSequence2, a2, file, i5);
                        }
                    });
                } catch (Exception e2) {
                    MP3CutterActivity.this.p.dismiss();
                    if (e2.getMessage().equals("No space left on device")) {
                        charSequence = MP3CutterActivity.this.getResources().getText(R.string.no_space_error);
                        exc = null;
                    } else {
                        exc = e2;
                        charSequence = MP3CutterActivity.this.getResources().getText(R.string.write_error);
                    }
                    MP3CutterActivity.this.ac.post(new Runnable() {
                        public void run() {
                            MP3CutterActivity.this.a("WriteError", charSequence, exc);
                        }
                    });
                }
            }
        };
        r1.start();
    }

    private String a(CharSequence charSequence, String str) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.AudioCutter));
        sb.append("/");
        String sb2 = sb.toString();
        File file = new File(sb2);
        if (!file.exists()) {
            file.mkdirs();
        }
        int i2 = 0;
        String str3 = "";
        for (int i3 = 0; i3 < charSequence.length(); i3++) {
            if (Character.isLetterOrDigit(charSequence.charAt(i3))) {
                StringBuilder sb3 = new StringBuilder(String.valueOf(str3));
                sb3.append(charSequence.charAt(i3));
                str3 = sb3.toString();
            }
        }
        while (i2 < 100) {
            if (i2 > 0) {
                StringBuilder sb4 = new StringBuilder(String.valueOf(sb2));
                sb4.append(str3);
                sb4.append(i2);
                sb4.append(str);
                str2 = sb4.toString();
            } else {
                StringBuilder sb5 = new StringBuilder(String.valueOf(sb2));
                sb5.append(str3);
                sb5.append(str);
                str2 = sb5.toString();
            }
            try {
                new RandomAccessFile(new File(str2), "r").close();
                i2++;
            } catch (Exception unused) {
                return str2;
            }
        }
        return null;
    }


    @SuppressLint({"WrongConstant"})
    public void a(CharSequence charSequence, String str, File file, int i2) {
        if (file.length() <= 512) {
            file.delete();
            new AlertDialog.Builder(this).setTitle((int) R.string.alert_title_failure).setMessage((int) R.string.too_small_error).setPositiveButton((int) R.string.alert_ok_button, (DialogInterface.OnClickListener) null).setCancelable(false).show();
            return;
        }
        long length = file.length();
        outputfilename = str;
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(getResources().getText(R.string.artist_name));
        String sb2 = sb.toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", str);
        contentValues.put("title", charSequence.toString());
        contentValues.put("_size", Long.valueOf(length));
        contentValues.put("mime_type", "audio/mpeg");
        contentValues.put("artist", sb2);
        contentValues.put("duration", Integer.valueOf(i2));
        setResult(-1, new Intent().setData(getContentResolver().insert(Media.getContentUriForPath(str), contentValues)));
        this.b = file.getAbsoluteFile().toString();
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(file.getAbsoluteFile()));
        getApplicationContext().sendBroadcast(intent);
        c();
    }
}
