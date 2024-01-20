package com.androworld.allinonevideoeditor.audiocompress;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore.Images.Media;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androworld.allinonevideoeditor.Adclick;
import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.AudioPlayer;
import com.androworld.allinonevideoeditor.Helper;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.UtilCommand;
import com.androworld.allinonevideoeditor.audiocutter.cutter.CheapSoundFile;
import com.androworld.allinonevideoeditor.audiocutter.cutter.CheapSoundFile.ProgressListener;
import com.androworld.allinonevideoeditor.audiocutter.cutter.MarkerView;
import com.androworld.allinonevideoeditor.audiocutter.cutter.MarkerView.MarkerListener;
import com.androworld.allinonevideoeditor.audiocutter.cutter.SeekTest;
import com.androworld.allinonevideoeditor.audiocutter.cutter.SongMetadataReader;
import com.androworld.allinonevideoeditor.audiocutter.cutter.WaveformView;
import com.androworld.allinonevideoeditor.audiocutter.cutter.WaveformView.WaveformListener;
import com.androworld.allinonevideoeditor.listmusicandmymusic.ListMusicAndMyMusicActivity;
import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;

import com.google.android.gms.ads.InterstitialAd;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

public class AudioCompressorActivity extends AppCompatActivity implements MarkerListener, WaveformListener {
    public boolean aBoolean;
    static final boolean l = true;
    private Uri uri;

    private WaveformView waveformView;

    public MarkerView D;

    public MarkerView E;
    private TextView F;
    private ImageButton G;
    private ImageButton H;
    private ImageButton I;
    private ImageButton J;
    private ImageButton K;
    private boolean L;
    private String M = "";

    public int N;

    public int O;
    private int P;
    private int Q;


    public boolean S;

    public int T;
    private int U;

    public int V;
    private int W;
    private int X;
    private int Y;

    public int Z;
    AudioManager a;
    private OnClickListener aA = new OnClickListener() {
        @Override public void onClick(View view) {
            try {
                AudioManager audioManager = AudioCompressorActivity.this.a;
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

    public String aB;

    public int aa;

    public Handler ab;

    public MediaPlayer ac;

    public boolean ad;
    private boolean ae;

    public boolean af;
    private float ag;
    private int ah;
    private int ai;
    private int aj;
    private long ak;

    public float al;
    private int am;
    private int an;
    private int ao;
    private int ap;
    private Typeface aq;

    public EditText ar;

    public EditText as;
    private InterstitialAd at;
    private TextWatcher au = new TextWatcher() {
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            //add
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            //add
        }

        public void afterTextChanged(Editable editable) {
            if (AudioCompressorActivity.this.ar.hasFocus()) {
                try {
                    AudioCompressorActivity.this.O = AudioCompressorActivity.this.waveformView.secondsToPixels(Double.parseDouble(AudioCompressorActivity.this.ar.getText().toString()));
                    AudioCompressorActivity.this.g();
                } catch (NumberFormatException unused) {
                }
            }
            if (AudioCompressorActivity.this.as.hasFocus()) {
                try {
                    AudioCompressorActivity.this.N = AudioCompressorActivity.this.waveformView.secondsToPixels(Double.parseDouble(AudioCompressorActivity.this.as.getText().toString()));
                    AudioCompressorActivity.this.g();
                } catch (NumberFormatException unused2) {
                }
            }
        }
    };
    public int audiorate = 64;

    public Runnable av = new Runnable() {
        public void run() {
            if (AudioCompressorActivity.this.O != AudioCompressorActivity.this.aa && !AudioCompressorActivity.this.ar.hasFocus()) {
                AudioCompressorActivity.this.ar.setText(AudioCompressorActivity.this.D(AudioCompressorActivity.this.O));
                if (AudioCompressorActivity.this.D(AudioCompressorActivity.this.O) == "") {
                    int parseFloat = (int) Float.parseFloat("00.00");
                    int i = parseFloat / 60;
                    if (i <= 9) {
                        AudioCompressorActivity audioCompressorActivity = AudioCompressorActivity.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("0");
                        sb.append(i);
                        audioCompressorActivity.b = sb.toString();
                    } else {
                        int i2 = i % 60;
                        if (i2 <= 9) {
                            AudioCompressorActivity audioCompressorActivity2 = AudioCompressorActivity.this;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("0");
                            sb2.append(i2);
                            audioCompressorActivity2.d = sb2.toString();
                        } else {
                            int i3 = parseFloat % 60;
                            if (i3 <= 9) {
                                AudioCompressorActivity audioCompressorActivity3 = AudioCompressorActivity.this;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("0");
                                sb3.append(i3);
                                audioCompressorActivity3.f = sb3.toString();
                            }
                        }
                    }
                } else {
                    int parseFloat2 = (int) Float.parseFloat(AudioCompressorActivity.this.D(AudioCompressorActivity.this.O));
                    int i4 = parseFloat2 / 3600;
                    if (i4 <= 9) {
                        AudioCompressorActivity audioCompressorActivity4 = AudioCompressorActivity.this;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("0");
                        sb4.append(i4);
                        audioCompressorActivity4.b = sb4.toString();
                    } else {
                        AudioCompressorActivity audioCompressorActivity5 = AudioCompressorActivity.this;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("");
                        sb5.append(i4);
                        audioCompressorActivity5.b = sb5.toString();
                    }
                    int i5 = (parseFloat2 / 60) % 60;
                    if (i5 <= 9) {
                        AudioCompressorActivity audioCompressorActivity6 = AudioCompressorActivity.this;
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("0");
                        sb6.append(i5);
                        audioCompressorActivity6.d = sb6.toString();
                    } else {
                        AudioCompressorActivity audioCompressorActivity7 = AudioCompressorActivity.this;
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append("");
                        sb7.append(i5);
                        audioCompressorActivity7.d = sb7.toString();
                    }
                    int i6 = parseFloat2 % 60;
                    if (i6 <= 9) {
                        AudioCompressorActivity audioCompressorActivity8 = AudioCompressorActivity.this;
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("0");
                        sb8.append(i6);
                        audioCompressorActivity8.f = sb8.toString();
                    } else {
                        AudioCompressorActivity audioCompressorActivity9 = AudioCompressorActivity.this;
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append("");
                        sb9.append(i6);
                        audioCompressorActivity9.f = sb9.toString();
                    }
                }
                AudioCompressorActivity.this.aa = AudioCompressorActivity.this.O;
            }
            if (AudioCompressorActivity.this.N != AudioCompressorActivity.this.Z && !AudioCompressorActivity.this.as.hasFocus()) {
                AudioCompressorActivity.this.as.setText(AudioCompressorActivity.this.D(AudioCompressorActivity.this.N));
                if (AudioCompressorActivity.this.D(AudioCompressorActivity.this.N) != "") {
                    int parseFloat3 = (int) Float.parseFloat(AudioCompressorActivity.this.D(AudioCompressorActivity.this.N - AudioCompressorActivity.this.O));
                    int i7 = parseFloat3 / 3600;
                    if (i7 <= 9) {
                        AudioCompressorActivity audioCompressorActivity10 = AudioCompressorActivity.this;
                        StringBuilder sb10 = new StringBuilder();
                        sb10.append("0");
                        sb10.append(i7);
                        audioCompressorActivity10.c = sb10.toString();
                    } else {
                        AudioCompressorActivity audioCompressorActivity11 = AudioCompressorActivity.this;
                        StringBuilder sb11 = new StringBuilder();
                        sb11.append("");
                        sb11.append(i7);
                        audioCompressorActivity11.c = sb11.toString();
                    }
                    int i8 = (parseFloat3 / 60) % 60;
                    if (i8 <= 9) {
                        AudioCompressorActivity audioCompressorActivity12 = AudioCompressorActivity.this;
                        StringBuilder sb12 = new StringBuilder();
                        sb12.append("0");
                        sb12.append(i8);
                        audioCompressorActivity12.e = sb12.toString();
                    } else {
                        AudioCompressorActivity audioCompressorActivity13 = AudioCompressorActivity.this;
                        StringBuilder sb13 = new StringBuilder();
                        sb13.append("");
                        sb13.append(i8);
                        audioCompressorActivity13.e = sb13.toString();
                    }
                    int i9 = parseFloat3 % 60;
                    if (i9 <= 9) {
                        AudioCompressorActivity audioCompressorActivity14 = AudioCompressorActivity.this;
                        StringBuilder sb14 = new StringBuilder();
                        sb14.append("0");
                        sb14.append(i9);
                        audioCompressorActivity14.g = sb14.toString();
                    } else {
                        AudioCompressorActivity audioCompressorActivity15 = AudioCompressorActivity.this;
                        StringBuilder sb15 = new StringBuilder();
                        sb15.append("");
                        sb15.append(i9);
                        audioCompressorActivity15.g = sb15.toString();
                    }
                }
                AudioCompressorActivity.this.Z = AudioCompressorActivity.this.N;
            }
            AudioCompressorActivity.this.ab.postDelayed(AudioCompressorActivity.this.av, 100);
        }
    };
    private OnClickListener aw = new OnClickListener() {
        @Override public void onClick(View view) {
            AudioCompressorActivity.this.e(AudioCompressorActivity.this.O);
        }
    };
    private OnClickListener ax = new OnClickListener() {
        @Override public void onClick(View view) {
            if (AudioCompressorActivity.this.ad) {
                int currentPosition = AudioCompressorActivity.this.ac.getCurrentPosition() - 5000;
                if (currentPosition < AudioCompressorActivity.this.V) {
                    currentPosition = AudioCompressorActivity.this.V;
                }
                AudioCompressorActivity.this.ac.seekTo(currentPosition);
                return;
            }
            AudioCompressorActivity.this.E.requestFocus();
            AudioCompressorActivity.this.markerFocus(AudioCompressorActivity.this.E);
        }
    };
    private OnClickListener ay = new OnClickListener() {
        @Override public void onClick(View view) {
            try {
                if (AudioCompressorActivity.this.ad) {
                    int currentPosition = BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT + AudioCompressorActivity.this.ac.getCurrentPosition();
                    if (currentPosition > AudioCompressorActivity.this.T) {
                        currentPosition = AudioCompressorActivity.this.T;
                    }
                    AudioCompressorActivity.this.ac.seekTo(currentPosition);
                    return;
                }
                AudioCompressorActivity.this.D.requestFocus();
                AudioCompressorActivity.this.markerFocus(AudioCompressorActivity.this.D);
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
    private OnClickListener az = new OnClickListener() {
        @Override public void onClick(View view) {
            try {
                AudioManager audioManager = AudioCompressorActivity.this.a;
                AudioManager audioManager2 = AudioCompressorActivity.this.a;
                AudioManager audioManager3 = AudioCompressorActivity.this.a;
                AudioManager audioManager4 = AudioCompressorActivity.this.a;
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
    String b;
    String c;
    String d;
    String e;
    String f;
    String g;
    WakeLock h;
    AudioCompressorAdapter i;
    ArrayList<String> j;
    Spinner k;

    public long m;
    private long n;

    private boolean o;

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
    private Ads ads;


    public void a(CharSequence charSequence, CharSequence charSequence2, Exception exc) {
        //add
    }

    public void markerDraw() {
        //add
    }

    public void markerEnter(MarkerView markerView) {
        //add
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.s = null;
        this.uri = null;
        this.ac = null;
        this.ad = false;
        a();
         getIntent().getBooleanExtra("was_get_content_intent", false);
        this.y = Helper.audiopath;
        this.q = null;
        this.L = false;
        this.y.equals("record");
        this.ab = new Handler();
        this.ab.postDelayed(this.av, 100);
        if (!this.y.equals("record")) {
            e();
        }
    }


    @Override
    public void onDestroy() {
        if (this.ac != null && this.ac.isPlaying()) {
            this.ac.stop();
        }
        this.ac = null;
        if (this.s != null) {
            try {
                if (!new File(this.s).delete()) {
                    a(new Exception(), R.string.delete_tmp_error);
                }
                getContentResolver().delete(this.uri, null, null);
            } catch (SecurityException e2) {
                a((Exception) e2, R.string.delete_tmp_error);
            }
        }
        super.onDestroy();
    }


    @Override public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        e();
    }

    public void onActivityResult(Configuration configuration) {
        final int zoomLevel = this.waveformView.getZoomLevel();
        super.onConfigurationChanged(configuration);
        a();
        this.ab.postDelayed(new Runnable() {
            public void run() {
                AudioCompressorActivity.this.E.requestFocus();
                AudioCompressorActivity.this.markerFocus(AudioCompressorActivity.this.E);
                AudioCompressorActivity.this.waveformView.setZoomLevel(zoomLevel);
                AudioCompressorActivity.this.waveformView.recomputeHeights(AudioCompressorActivity.this.al);
                AudioCompressorActivity.this.g();
            }
        }, 500);
    }

    public void waveformDraw() {
        this.Q = this.waveformView.getMeasuredWidth();
        if (this.X != this.Y && !this.L) {
            g();
        } else if (this.ad) {
            g();
        } else if (this.W != 0) {
            g();
        }
    }

    public void waveformTouchStart(float f2) {
        this.ae = l;
        this.ag = f2;
        this.aj = this.Y;
        this.W = 0;
        this.ak = System.currentTimeMillis();
    }

    public void waveformTouchMove(float f2) {
        this.Y = a((int) (((float) this.aj) + (this.ag - f2)));
        g();
    }

    public void waveformTouchEnd() {
        this.ae = false;
        this.X = this.Y;
        if (System.currentTimeMillis() - this.ak >= 300) {
            return;
        }
        if (this.ad) {
            int pixelsToMillisecs = this.waveformView.pixelsToMillisecs((int) (this.ag + ((float) this.Y)));
            if (pixelsToMillisecs < this.V || pixelsToMillisecs >= this.T) {
                n();
            } else {
                this.ac.seekTo(pixelsToMillisecs - this.U);
            }
        } else {
            e((int) (this.ag + ((float) this.Y)));
        }
    }

    public void waveformFling(float f2) {
        this.ae = false;
        this.X = this.Y;
        this.W = (int) (-f2);
        g();
    }

    public void markerTouchStart(MarkerView markerView, float f2) {
        this.ae = l;
        this.ag = f2;
        this.ai = this.O;
        this.ah = this.N;
    }

    public void markerTouchMove(MarkerView markerView, float f2) {
        float f3 = f2 - this.ag;
        if (markerView == this.E) {
            this.O = a((int) (((float) this.ai) + f3));
            this.N = a((int) (((float) this.ah) + f3));
        } else {
            this.N = a((int) (((float) this.ah) + f3));
            if (this.N < this.O) {
                this.N = this.O;
            }
        }
        g();
    }

    public void markerTouchEnd(MarkerView markerView) {
        this.ae = false;
        if (markerView == this.E) {
            j();
        } else {
            l();
        }
    }

    public void markerLeft(MarkerView markerView, int i2) {
        this.L = l;
        if (markerView == this.E) {
            int i3 = this.O;
            this.O = a(this.O - i2);
            this.N = a(this.N - (i3 - this.O));
            j();
        }
        if (markerView == this.D) {
            if (this.N == this.O) {
                this.O = a(this.O - i2);
                this.N = this.O;
            } else {
                this.N = a(this.N - i2);
            }
            l();
        }
        g();
    }

    public void markerRight(MarkerView markerView, int i2) {
        this.L = l;
        if (markerView == this.E) {
            int i3 = this.O;
            this.O += i2;
            if (this.O > this.P) {
                this.O = this.P;
            }
            this.N += this.O - i3;
            if (this.N > this.P) {
                this.N = this.P;
            }
            j();
        }
        if (markerView == this.D) {
            this.N += i2;
            if (this.N > this.P) {
                this.N = this.P;
            }
            l();
        }
        g();
    }

    public void markerKeyUp() {
        this.L = false;
        g();
    }

    public void markerFocus(MarkerView markerView) {
        this.L = false;
        if (markerView == this.E) {
            k();
        } else {
            m();
        }
        this.ab.postDelayed(new Runnable() {
            public void run() {
                AudioCompressorActivity.this.g();
            }
        }, 100);
    }

    @SuppressLint("InvalidWakeLockTag")
    private void a() {
        setContentView(R.layout.audiocompressoractivity);
        LinearLayout sa = (LinearLayout) findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(AudioCompressorActivity.this,sa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Audio Compressor");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (l || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(l);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.h = ((PowerManager) getSystemService(POWER_SERVICE)).newWakeLock(6, "VideoMerge");
            if (!this.h.isHeld()) {
                this.h.acquire();
            }
            this.j = new ArrayList<>();
            this.j.add("64 K/Bit");
            this.j.add("128 K/Bit");
            this.j.add("256 K/Bit");
            this.k = (Spinner) findViewById(R.id.sp_convert);
            this.i = new AudioCompressorAdapter(this, this.j, 0);
            this.k.setAdapter(this.i);
            this.k.setSelection(0);
            this.k.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //add
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (adapterView.getItemAtPosition(i).toString() == "64 K/Bit") {
                        AudioCompressorActivity.this.audiorate = 64;
                    } else if (adapterView.getItemAtPosition(i).toString() == "128 K/Bit") {
                        AudioCompressorActivity.this.audiorate = 128;
                    } else if (adapterView.getItemAtPosition(i).toString() == "256 K/Bit") {
                        AudioCompressorActivity.this.audiorate = 256;
                    }
                }
            });
            getApplicationContext();
            this.a = (AudioManager) getSystemService(AUDIO_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.al = displayMetrics.density;
            this.ap = (int) (46.0f * this.al);
            this.ao = (int) (48.0f * this.al);
            this.an = (int) (this.al * 10.0f);
            this.am = (int) (10.0f * this.al);
            this.F = (TextView) findViewById(R.id.songname);
            this.aq = Typeface.createFromAsset(getAssets(), Helper.FontStyle);
            this.ar = (EditText) findViewById(R.id.starttext);
            this.ar.setTypeface(this.aq);
            this.ar.addTextChangedListener(this.au);
            this.as = (EditText) findViewById(R.id.endtext);
            this.as.setTypeface(this.aq);
            this.as.addTextChangedListener(this.au);
            this.K = (ImageButton) findViewById(R.id.play);
            this.K.setOnClickListener(this.aw);
            this.J = (ImageButton) findViewById(R.id.rew);
            this.J.setOnClickListener(this.ax);
            this.I = (ImageButton) findViewById(R.id.ffwd);
            this.I.setOnClickListener(this.ay);
            this.G = (ImageButton) findViewById(R.id.btnvolumdown);
            this.G.setOnClickListener(this.az);
            this.H = (ImageButton) findViewById(R.id.btnvolumup);
            this.H.setOnClickListener(this.aA);
            h();
            this.waveformView = (WaveformView) findViewById(R.id.waveform);
            this.waveformView.setListener(this);
            this.P = 0;
            this.aa = -1;
            this.Z = -1;
            if (this.q != null) {
                this.waveformView.setSoundFile(this.q);
                this.waveformView.recomputeHeights(this.al);
                this.P = this.waveformView.maxPos();
            }
            this.E = (MarkerView) findViewById(R.id.startmarker);
            this.E.setListener(this);
            this.E.setAlpha(255);
            this.E.setFocusable(l);
            this.E.setFocusableInTouchMode(l);
            this.S = l;
            this.D = (MarkerView) findViewById(R.id.endmarker);
            this.D.setListener(this);
            this.D.setAlpha(255);
            this.D.setFocusable(l);
            this.D.setFocusableInTouchMode(l);
            this.aBoolean = l;
            g();
            ads =  new Ads();
            ads.Interstitialload(this);
            return;
        }
        throw new AssertionError();
    }



    public void c() {
        ads.showInd(new Adclick() {
            @Override
            public void onclicl() {
                D();
            }
        });
    }


    public void D() {
        Intent intent = new Intent(this, AudioPlayer.class);
        Bundle bundle = new Bundle();
        bundle.putString("song", this.aB);
        bundle.putBoolean("isfrom", l);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void e() {
        this.r = new File(this.y);
        this.t = a(this.y);
        SongMetadataReader songMetadataReader = new SongMetadataReader(this, this.y);
        this.u = songMetadataReader.mTitle;
        this.x = songMetadataReader.mArtist;
        this.w = songMetadataReader.mAlbum;
        int z = songMetadataReader.mYear;
        this.v = songMetadataReader.mGenre;
        String str = this.u;
        if (this.x != null && this.x.length() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" - ");
            sb.append(this.x);
            str = sb.toString();
        }
        this.F.setText(str);
        this.F.setSelected(l);
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
                if (currentTimeMillis - AudioCompressorActivity.this.m > 100) {
                    AudioCompressorActivity.this.p.setProgress((int) (((double) AudioCompressorActivity.this.p.getMax()) * d));
                    AudioCompressorActivity.this.m = currentTimeMillis;
                }
                return AudioCompressorActivity.this.o;
            }
        };
        this.af = false;
        new Thread() {
            @Override
            public void run() {
                AudioCompressorActivity.this.af = SeekTest.CanSeekAccurately(AudioCompressorActivity.this.getPreferences(0));
                System.out.println("Seek test done, creating media player.");
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(AudioCompressorActivity.this.r.getAbsolutePath());
                    AudioManager audioManager = AudioCompressorActivity.this.a;
                    mediaPlayer.setAudioStreamType(3);
                    mediaPlayer.prepare();
                    AudioCompressorActivity.this.ac = mediaPlayer;
                } catch (final IOException e) {
                    AudioCompressorActivity.this.ab.post(new Runnable() {
                        public void run() {
                            AudioCompressorActivity.this.a("ReadError", AudioCompressorActivity.this.getResources().getText(R.string.read_error), e);
                        }
                    });
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                final String str;
                try {
                    AudioCompressorActivity.this.q = CheapSoundFile.create(AudioCompressorActivity.this.r.getAbsolutePath(), r0);
                    if (AudioCompressorActivity.this.q == null) {
                        AudioCompressorActivity.this.p.dismiss();
                        String[] split = AudioCompressorActivity.this.r.getName().toLowerCase().split("\\.");
                        if (split.length < 2) {
                            str = AudioCompressorActivity.this.getResources().getString(R.string.no_extension_error);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append(AudioCompressorActivity.this.getResources().getString(R.string.bad_extension_error));
                            sb.append(" ");
                            sb.append(split[split.length - 1]);
                            str = sb.toString();
                        }
                        AudioCompressorActivity.this.ab.post(new Runnable() {
                            public void run() {
                                AudioCompressorActivity.this.a("UnsupportedExtension", str, new Exception());
                            }
                        });
                        return;
                    }
                    AudioCompressorActivity.this.p.dismiss();
                    if (AudioCompressorActivity.this.o) {
                        AudioCompressorActivity.this.ab.post(new Runnable() {
                            public void run() {
                                AudioCompressorActivity.this.f();
                            }
                        });
                    } else {
                        AudioCompressorActivity.this.finish();
                    }
                } catch (final Exception e) {
                    AudioCompressorActivity.this.p.dismiss();
                    e.printStackTrace();
                    AudioCompressorActivity.this.ab.post(new Runnable() {
                        public void run() {
                            AudioCompressorActivity.this.a("ReadError", AudioCompressorActivity.this.getResources().getText(R.string.read_error),
                                    e);
                        }
                    });
                }
            }
        }.start();
    }


    public void f() {
        this.waveformView.setSoundFile(this.q);
        this.waveformView.recomputeHeights(this.al);
        this.P = this.waveformView.maxPos();
        this.aa = -1;
        this.Z = -1;
        this.ae = false;
        this.Y = 0;
        this.X = 0;
        this.W = 0;
        i();
        if (this.N > this.P) {
            this.N = this.P;
        }
        g();
    }


    public synchronized void g() {
        if (this.ad) {
            int currentPosition = this.ac.getCurrentPosition() + this.U;
            int millisecsToPixels = this.waveformView.millisecsToPixels(currentPosition);
            this.waveformView.setPlayback(millisecsToPixels);
            c(millisecsToPixels - (this.Q / 2));
            if (currentPosition >= this.T) {
                n();
            }
        }
        int i2 = 0;
        if (!this.ae) {
            if (this.W != 0) {
                int i3 = this.W;
                int i4 = this.W / 30;
                if (this.W > 80) {
                    this.W -= 80;
                } else if (this.W < -80) {
                    this.W += 80;
                } else {
                    this.W = 0;
                }
                this.Y += i4;
                if (this.Y + (this.Q / 2) > this.P) {
                    this.Y = this.P - (this.Q / 2);
                    this.W = 0;
                }
                if (this.Y < 0) {
                    this.Y = 0;
                    this.W = 0;
                }
                this.X = this.Y;
            } else {
                int i5 = this.X - this.Y;
                int i6 = i5 > 10 ? i5 / 10 : i5 > 0 ? 1 : i5 < -10 ? i5 / 10 : i5 < 0 ? -1 : 0;
                this.Y += i6;
            }
        }
        this.waveformView.setParameters(this.O, this.N, this.Y);
        this.waveformView.invalidate();
        MarkerView markerView = this.E;
        StringBuilder sb = new StringBuilder();
        sb.append(getResources().getText(R.string.start_marker));
        sb.append(" ");
        sb.append(D(this.O));
        markerView.setContentDescription(sb.toString());
        MarkerView markerView2 = this.D;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getResources().getText(R.string.end_marker));
        sb2.append(" ");
        sb2.append(D(this.N));
        markerView2.setContentDescription(sb2.toString());
        int i7 = (this.O - this.Y) - this.ap;
        if (this.E.getWidth() + i7 < 0) {
            if (this.S) {
                this.E.setAlpha(0);
                this.S = false;
            }
            i7 = 0;
        } else if (!this.S) {
            this.ab.postDelayed(new Runnable() {
                public void run() {
                    AudioCompressorActivity.this.S = AudioCompressorActivity.l;
                    AudioCompressorActivity.this.E.setAlpha(255);
                }
            }, 0);
        }
        int width = ((this.N - this.Y) - this.D.getWidth()) + this.ao;
        if (this.D.getWidth() + width >= 0) {
            if (!this.aBoolean) {
                this.ab.postDelayed(new Runnable() {
                    public void run() {
                        AudioCompressorActivity.this.aBoolean = AudioCompressorActivity.l;
                        AudioCompressorActivity.this.D.setAlpha(255);
                    }
                }, 0);
            }
            i2 = width;
        } else if (this.aBoolean) {
            this.D.setAlpha(0);
            this.aBoolean = false;
        }
        this.E.setLayoutParams(new LayoutParams(-2, -2, i7, this.an));
        this.D.setLayoutParams(new LayoutParams(-2, -2, i2, (this.waveformView.getMeasuredHeight() - this.D.getHeight()) - this.am));
    }

    private void h() {
        if (this.ad) {
            this.K.setImageResource(R.drawable.ic_playlist_pause);
            this.K.setContentDescription(getResources().getText(R.string.stop));
            return;
        }
        this.K.setImageResource(R.drawable.ic_playlist_play);
        this.K.setContentDescription(getResources().getText(R.string.play));
    }

    private void i() {
        this.O = this.waveformView.secondsToPixels(0.0d);
        this.N = this.waveformView.secondsToPixels(15.0d);
    }

    private int a(int i2) {
        if (i2 < 0) {
            return 0;
        }
        return i2 > this.P ? this.P : i2;
    }

    private void j() {
        b(this.O - (this.Q / 2));
    }

    private void k() {
        c(this.O - (this.Q / 2));
    }

    private void l() {
        b(this.N - (this.Q / 2));
    }

    private void m() {
        c(this.N - (this.Q / 2));
    }

    private void b(int i2) {
        c(i2);
        g();
    }

    private void c(int i2) {
        if (!this.ae) {
            this.X = i2;
            if (this.X + (this.Q / 2) > this.P) {
                this.X = this.P - (this.Q / 2);
            }
            if (this.X < 0) {
                this.X = 0;
            }
        }
    }


    public String D(int i2) {
        return (this.waveformView == null || !this.waveformView.isInitialized()) ? "" : a(this.waveformView.pixelsToSeconds(i2));
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
        if (this.ac != null && this.ac.isPlaying()) {
            this.ac.pause();
        }
        this.waveformView.setPlayback(-1);
        this.ad = false;
        h();
    }

    public synchronized void e(int i2) {
        try {
            if (this.ad) {
                n();
            } else if (this.ac != null) {
                this.V = this.waveformView.pixelsToMillisecs(i2);
                if (i2 < this.O) {
                    this.T = this.waveformView.pixelsToMillisecs(this.O);
                } else if (i2 > this.N) {
                    this.T = this.waveformView.pixelsToMillisecs(this.P);
                } else {
                    this.T = this.waveformView.pixelsToMillisecs(this.N);
                }
                this.U = 0;
                int secondsToFrames = this.waveformView.secondsToFrames(((double) this.V) * 0.001d);
                int secondsToFrames2 = this.waveformView.secondsToFrames(((double) this.T) * 0.001d);
                int seekableFrameOffset = this.q.getSeekableFrameOffset(secondsToFrames);
                int seekableFrameOffset2 = this.q.getSeekableFrameOffset(secondsToFrames2);
                if (this.af && seekableFrameOffset >= 0 && seekableFrameOffset2 >= 0) {
                    this.ac.reset();
                    MediaPlayer mediaPlayer = this.ac;
                    AudioManager audioManager = this.a;
                    mediaPlayer.setAudioStreamType(3);
                    this.ac.setDataSource(new FileInputStream(this.r.getAbsolutePath()).getFD(), (long) seekableFrameOffset, (long) (seekableFrameOffset2 - seekableFrameOffset));
                    this.ac.prepare();
                    this.U = this.V;
                    System.out.println("Exception trying to play file subset");
                    this.ac.reset();
                    MediaPlayer mediaPlayer2 = this.ac;
                    AudioManager audioManager2 = this.a;
                    mediaPlayer2.setAudioStreamType(3);
                    this.ac.setDataSource(this.r.getAbsolutePath());
                    this.ac.prepare();
                    this.U = 0;
                }
                this.ac.setOnCompletionListener(new OnCompletionListener() {
                    public synchronized void onCompletion(MediaPlayer mediaPlayer) {
                        AudioCompressorActivity.this.n();
                    }
                });
                this.ad = l;
                if (this.U == 0) {
                    this.ac.seekTo(this.V);
                }
                this.ac.start();
                g();
                h();
            }
        } catch (Exception e2) {
            a(e2, R.string.play_error);
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
        new AlertDialog.Builder(this).setTitle(charSequence2).setMessage(charSequence).setPositiveButton(R.string.alert_ok_button, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                AudioCompressorActivity.this.finish();
            }
        }).setCancelable(false).show();
    }

    private void a(Exception exc, int i2) {
        a(exc, getResources().getText(i2));
    }

    private String a(String str) {
        return str.substring(str.lastIndexOf(46), str.length());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ListMusicAndMyMusicActivity.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return l;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return l;
        }
        if (menuItem.getItemId() == R.id.Done) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb.append("/");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.AudioCompressor));
            this.aB = sb.toString();
            File file = new File(this.aB);
            if (!file.exists()) {
                file.mkdirs();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(file.getAbsolutePath());
            sb2.append("/COM_");
            sb2.append(System.currentTimeMillis());
            sb2.append(".mp3");
            this.aB = sb2.toString();
            String[] strArr = new String[0];
            if (this.audiorate == 64) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(this.b);
                sb3.append(":");
                sb3.append(this.d);
                sb3.append(":");
                sb3.append(this.f);
                StringBuilder sb4 = new StringBuilder();
                sb4.append(this.c);
                sb4.append(":");
                sb4.append(this.e);
                sb4.append(":");
                sb4.append(this.g);
                strArr = new String[]{"-y", "-ss", sb3.toString(), "-t", sb4.toString(), "-i", Helper.audiopath, "-b:a", "64k", this.aB};
            } else if (this.audiorate == 128) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append(this.b);
                sb5.append(":");
                sb5.append(this.d);
                sb5.append(":");
                sb5.append(this.f);
                StringBuilder sb6 = new StringBuilder();
                sb6.append(this.c);
                sb6.append(":");
                sb6.append(this.e);
                sb6.append(":");
                sb6.append(this.g);
                strArr = new String[]{"-y", "-ss", sb5.toString(), "-t", sb6.toString(), "-i", Helper.audiopath, "-b:a", "128k", this.aB};
            } else if (this.audiorate == 256) {
                StringBuilder sb7 = new StringBuilder();
                sb7.append(this.b);
                sb7.append(":");
                sb7.append(this.d);
                sb7.append(":");
                sb7.append(this.f);
                StringBuilder sb8 = new StringBuilder();
                sb8.append(this.c);
                sb8.append(":");
                sb8.append(this.e);
                sb8.append(":");
                sb8.append(this.g);
                strArr = new String[]{"-y", "-ss", sb7.toString(), "-t", sb8.toString(), "-i", Helper.audiopath, "-b:a", "256k", this.aB};
            }
            a(strArr, this.aB);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void a(String[] strArr, final String str) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
            String ffmpegCommand = UtilCommand.main(strArr);
            FFmpeg.executeAsync(ffmpegCommand, new ExecuteCallback() {

                @Override
                public void apply(final long executionId, final int returnCode) {
                    Log.d("TAG", String.format("FFmpeg process exited with rc %d.", returnCode));

                    Log.d("TAG", "FFmpeg process output:");

                    Config.printLastCommandOutput(Log.INFO);

                    progressDialog.dismiss();
                    if (returnCode == RETURN_CODE_SUCCESS) {
                        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                        intent.setData(Uri.fromFile(new File(AudioCompressorActivity.this.aB)));
                        AudioCompressorActivity.this.sendBroadcast(intent);
                        AudioCompressorActivity.this.c();
                        AudioCompressorActivity.this.refreshGallery(str);

                    } else if (returnCode == RETURN_CODE_CANCEL) {
                        Log.d("ffmpegfailure", str);
                        try {
                            if (new File(str).delete()){
                                //add
                            }
                            AudioCompressorActivity.this.deleteFromGallery(str);
                            Toast.makeText(AudioCompressorActivity.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                        } catch (Exception th) {
                            th.printStackTrace();
                        }
                    } else {
                        Log.d("ffmpegfailure", str);
                        try {
                            if (new File(str).delete()){
                                //add
                            }
                            AudioCompressorActivity.this.deleteFromGallery(str);
                            Toast.makeText(AudioCompressorActivity.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                        } catch (Exception th) {
                            th.printStackTrace();
                        }
                    }


                }
            });

            getWindow().clearFlags(16);
    }


    public void pload() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                AudioCompressorActivity.this.finish();
            }
        }).create().show();
    }

    public void deleteFromGallery(String str) {
        String[] strArr = {"_id"};
        String[] strArr2 = {str};
        Uri contentUri = Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor query = contentResolver.query(contentUri, strArr, "_data = ?", strArr2, null);
        if (query.moveToFirst()) {
            try {
                contentResolver.delete(ContentUris.withAppendedId(contentUri, query.getLong(query.getColumnIndexOrThrow("_id"))), null, null);
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
}
