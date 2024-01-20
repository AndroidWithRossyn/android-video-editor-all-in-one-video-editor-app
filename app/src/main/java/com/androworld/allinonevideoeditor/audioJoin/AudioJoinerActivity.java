package com.androworld.allinonevideoeditor.audioJoin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore.Audio.Media;
import android.provider.MediaStore.Images;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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


import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

public class AudioJoinerActivity extends AppCompatActivity implements MarkerListener, WaveformListener {
    private int Rs;
    static final boolean n = true;
    private String A;
    private int B;
    private Uri C;
    private boolean D;

    public WaveformView E;

    public MarkerView F;

    public MarkerView G;
    private TextView H;
    private ImageButton I;
    private ImageButton J;
    private ImageButton K;
    private ImageButton L;
    private ImageButton M;
    private boolean N;
    private String O = "";

    public int P;

    public int Q;
    private int S;

    public boolean T;

    public boolean U;

    public int V;
    private int W;

    public int X;
    private int Y;
    private int Z;
    AudioManager a;
    private int aa;

    public int ab;

    public int ac;

    public Handler ad;

    public MediaPlayer ae;

    public boolean af;
    private boolean ag;

    public boolean ah;
    private float ai;
    private int aj;
    private int ak;
    private int al;
    private long am;

    public float an;
    private int ao;
    private int ap;
    private int aq;
    private int ar;

    public Runnable at = new Runnable() {
        public void run() {
            if (AudioJoinerActivity.this.Q != AudioJoinerActivity.this.ac) {
                if (AudioJoinerActivity.this.d(AudioJoinerActivity.this.Q) == "") {
                    int parseFloat = (int) Float.parseFloat("00.00");
                    int i = parseFloat / 60;
                    if (i <= 9) {
                        AudioJoinerActivity audioJoinerActivity = AudioJoinerActivity.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("0");
                        sb.append(i);
                        audioJoinerActivity.c = sb.toString();
                    } else {
                        int i2 = i % 60;
                        if (i2 <= 9) {
                            AudioJoinerActivity audioJoinerActivity2 = AudioJoinerActivity.this;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("0");
                            sb2.append(i2);
                            audioJoinerActivity2.e = sb2.toString();
                        } else {
                            int i3 = parseFloat % 60;
                            if (i3 <= 9) {
                                AudioJoinerActivity audioJoinerActivity3 = AudioJoinerActivity.this;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("0");
                                sb3.append(i3);
                                audioJoinerActivity3.g = sb3.toString();
                            }
                        }
                    }
                } else {
                    int parseFloat2 = (int) Float.parseFloat(AudioJoinerActivity.this.d(AudioJoinerActivity.this.Q));
                    int i4 = parseFloat2 / 3600;
                    if (i4 <= 9) {
                        AudioJoinerActivity audioJoinerActivity4 = AudioJoinerActivity.this;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("0");
                        sb4.append(i4);
                        audioJoinerActivity4.c = sb4.toString();
                    } else {
                        AudioJoinerActivity audioJoinerActivity5 = AudioJoinerActivity.this;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("");
                        sb5.append(i4);
                        audioJoinerActivity5.c = sb5.toString();
                    }
                    int i5 = (parseFloat2 / 60) % 60;
                    if (i5 <= 9) {
                        AudioJoinerActivity audioJoinerActivity6 = AudioJoinerActivity.this;
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("0");
                        sb6.append(i5);
                        audioJoinerActivity6.e = sb6.toString();
                    } else {
                        AudioJoinerActivity audioJoinerActivity7 = AudioJoinerActivity.this;
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append("");
                        sb7.append(i5);
                        audioJoinerActivity7.e = sb7.toString();
                    }
                    int i6 = parseFloat2 % 60;
                    if (i6 <= 9) {
                        AudioJoinerActivity audioJoinerActivity8 = AudioJoinerActivity.this;
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("0");
                        sb8.append(i6);
                        audioJoinerActivity8.g = sb8.toString();
                    } else {
                        AudioJoinerActivity audioJoinerActivity9 = AudioJoinerActivity.this;
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append("");
                        sb9.append(i6);
                        audioJoinerActivity9.g = sb9.toString();
                    }
                }
                AudioJoinerActivity.this.ac = AudioJoinerActivity.this.Q;
            }
            if (AudioJoinerActivity.this.P != AudioJoinerActivity.this.ab) {
                if (AudioJoinerActivity.this.d(AudioJoinerActivity.this.P) != "") {
                    int parseFloat3 = (int) Float.parseFloat(AudioJoinerActivity.this.d(AudioJoinerActivity.this.P - AudioJoinerActivity.this.Q));
                    int i7 = parseFloat3 / 3600;
                    if (i7 <= 9) {
                        AudioJoinerActivity audioJoinerActivity10 = AudioJoinerActivity.this;
                        StringBuilder sb10 = new StringBuilder();
                        sb10.append("0");
                        sb10.append(i7);
                        audioJoinerActivity10.d = sb10.toString();
                    } else {
                        AudioJoinerActivity audioJoinerActivity11 = AudioJoinerActivity.this;
                        StringBuilder sb11 = new StringBuilder();
                        sb11.append("");
                        sb11.append(i7);
                        audioJoinerActivity11.d = sb11.toString();
                    }
                    int i8 = (parseFloat3 / 60) % 60;
                    if (i8 <= 9) {
                        AudioJoinerActivity audioJoinerActivity12 = AudioJoinerActivity.this;
                        StringBuilder sb12 = new StringBuilder();
                        sb12.append("0");
                        sb12.append(i8);
                        audioJoinerActivity12.f = sb12.toString();
                    } else {
                        AudioJoinerActivity audioJoinerActivity13 = AudioJoinerActivity.this;
                        StringBuilder sb13 = new StringBuilder();
                        sb13.append("");
                        sb13.append(i8);
                        audioJoinerActivity13.f = sb13.toString();
                    }
                    int i9 = parseFloat3 % 60;
                    if (i9 <= 9) {
                        AudioJoinerActivity audioJoinerActivity14 = AudioJoinerActivity.this;
                        StringBuilder sb14 = new StringBuilder();
                        sb14.append("0");
                        sb14.append(i9);
                        audioJoinerActivity14.h = sb14.toString();
                    } else {
                        AudioJoinerActivity audioJoinerActivity15 = AudioJoinerActivity.this;
                        StringBuilder sb15 = new StringBuilder();
                        sb15.append("");
                        sb15.append(i9);
                        audioJoinerActivity15.h = sb15.toString();
                    }
                }
                AudioJoinerActivity.this.ab = AudioJoinerActivity.this.P;
            }
            AudioJoinerActivity.this.ad.postDelayed(AudioJoinerActivity.this.at, 100);
        }
    };
    private OnClickListener au = new OnClickListener() {
        @Override
        public void onClick(View view) {
            AudioJoinerActivity.this.e(AudioJoinerActivity.this.Q);
        }
    };
    public int audiorate = 64;
    private OnClickListener av = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (AudioJoinerActivity.this.af) {
                int currentPosition = AudioJoinerActivity.this.ae.getCurrentPosition() - 5000;
                if (currentPosition < AudioJoinerActivity.this.X) {
                    currentPosition = AudioJoinerActivity.this.X;
                }
                AudioJoinerActivity.this.ae.seekTo(currentPosition);
                return;
            }
            AudioJoinerActivity.this.G.requestFocus();
            AudioJoinerActivity.this.markerFocus(AudioJoinerActivity.this.G);
        }
    };
    private OnClickListener aw = new OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                if (AudioJoinerActivity.this.af) {
                    int currentPosition = BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT + AudioJoinerActivity.this.ae.getCurrentPosition();
                    if (currentPosition > AudioJoinerActivity.this.V) {
                        currentPosition = AudioJoinerActivity.this.V;
                    }
                    AudioJoinerActivity.this.ae.seekTo(currentPosition);
                    return;
                }
                AudioJoinerActivity.this.F.requestFocus();
                AudioJoinerActivity.this.markerFocus(AudioJoinerActivity.this.F);
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
    private OnClickListener ax = new OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                AudioManager audioManager = AudioJoinerActivity.this.a;
                AudioManager audioManager2 = AudioJoinerActivity.this.a;
                AudioManager audioManager3 = AudioJoinerActivity.this.a;
                AudioManager audioManager4 = AudioJoinerActivity.this.a;
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
    private OnClickListener ay = new OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                AudioManager audioManager = AudioJoinerActivity.this.a;
                AudioManager audioManager2 = AudioJoinerActivity.this.a;
                AudioManager audioManager3 = AudioJoinerActivity.this.a;
                AudioManager audioManager4 = AudioJoinerActivity.this.a;
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

    public String az;
    RelativeLayout b;
    String c;
    String d;
    String e;
    String f;
    String g;
    String h;
    WakeLock i;
    AudioJoinerAdapter j;
    ArrayList<String> k;
    Spinner l;
    Cursor m;

    public long o;
    private long p;

    public boolean q;

    public ProgressDialog r;

    public CheapSoundFile s;

    public File t;
    private String u;
    private String v;
    private String w;
    private String x;
    private String y;
    private String z;
    private Ads ads;


    public void a(CharSequence charSequence, CharSequence charSequence2, Exception exc) {
    }

    public void markerDraw() {
    }

    public void markerEnter(MarkerView markerView) {
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.u = null;
        this.C = null;
        this.ae = null;
        this.af = false;
        a();
        this.D = getIntent().getBooleanExtra("was_get_content_intent", false);
        this.A = Helper.audiopath;
        this.s = null;
        this.N = false;
        this.A.equals("record");
        this.ad = new Handler();
        this.ad.postDelayed(this.at, 100);
        if (!this.A.equals("record")) {
            g();
        }
    }


    @Override
    public void onDestroy() {
        if (this.ae != null && this.ae.isPlaying()) {
            this.ae.stop();
        }
        this.ae = null;
        if (this.u != null) {
            try {
                if (!new File(this.u).delete()) {
                    a(new Exception(), (int) R.string.delete_tmp_error);
                }
                getContentResolver().delete(this.C, null, null);
            } catch (SecurityException e2) {
                a((Exception) e2, (int) R.string.delete_tmp_error);
            }
        }
        super.onDestroy();
    }

    public void onActivityResult(Configuration configuration) {
        final int zoomLevel = this.E.getZoomLevel();
        super.onConfigurationChanged(configuration);
        a();
        this.ad.postDelayed(new Runnable() {
            public void run() {
                AudioJoinerActivity.this.G.requestFocus();
                AudioJoinerActivity.this.markerFocus(AudioJoinerActivity.this.G);
                AudioJoinerActivity.this.E.setZoomLevel(zoomLevel);
                AudioJoinerActivity.this.E.recomputeHeights(AudioJoinerActivity.this.an);
                AudioJoinerActivity.this.i();
            }
        }, 500);
    }

    public void waveformDraw() {
        this.S = this.E.getMeasuredWidth();
        if (this.Z != this.aa && !this.N) {
            i();
        } else if (this.af) {
            i();
        } else if (this.Y != 0) {
            i();
        }
    }

    public void waveformTouchStart(float f2) {
        this.ag = n;
        this.ai = f2;
        this.al = this.aa;
        this.Y = 0;
        this.am = System.currentTimeMillis();
    }

    public void waveformTouchMove(float f2) {
        this.aa = a((int) (((float) this.al) + (this.ai - f2)));
        i();
    }

    public void waveformTouchEnd() {
        this.ag = false;
        this.Z = this.aa;
        if (System.currentTimeMillis() - this.am >= 300) {
            return;
        }
        if (this.af) {
            int pixelsToMillisecs = this.E.pixelsToMillisecs((int) (this.ai + ((float) this.aa)));
            if (pixelsToMillisecs < this.X || pixelsToMillisecs >= this.V) {
                p();
            } else {
                this.ae.seekTo(pixelsToMillisecs - this.W);
            }
        } else {
            e((int) (this.ai + ((float) this.aa)));
        }
    }

    public void waveformFling(float f2) {
        this.ag = false;
        this.Z = this.aa;
        this.Y = (int) (-f2);
        i();
    }

    public void markerTouchStart(MarkerView markerView, float f2) {
        this.ag = n;
        this.ai = f2;
        this.ak = this.Q;
        this.aj = this.P;
    }

    public void markerTouchMove(MarkerView markerView, float f2) {
        float f3 = f2 - this.ai;
        if (markerView == this.G) {
            this.Q = a((int) (((float) this.ak) + f3));
            this.P = a((int) (((float) this.aj) + f3));
        } else {
            this.P = a((int) (((float) this.aj) + f3));
            if (this.P < this.Q) {
                this.P = this.Q;
            }
        }
        i();
    }

    public void markerTouchEnd(MarkerView markerView) {
        this.ag = false;
        if (markerView == this.G) {
            l();
        } else {
            n();
        }
    }

    public void markerLeft(MarkerView markerView, int i2) {
        this.N = n;
        if (markerView == this.G) {
            int i3 = this.Q;
            this.Q = a(this.Q - i2);
            this.P = a(this.P - (i3 - this.Q));
            l();
        }
        if (markerView == this.F) {
            if (this.P == this.Q) {
                this.Q = a(this.Q - i2);
                this.P = this.Q;
            } else {
                this.P = a(this.P - i2);
            }
            n();
        }
        i();
    }

    public void markerRight(MarkerView markerView, int i2) {
        this.N = n;
        if (markerView == this.G) {
            int i3 = this.Q;
            this.Q += i2;
            if (this.Q > this.Rs) {
                this.Q = this.Rs;
            }
            this.P += this.Q - i3;
            if (this.P > this.Rs) {
                this.P = this.Rs;
            }
            l();
        }
        if (markerView == this.F) {
            this.P += i2;
            if (this.P > this.Rs) {
                this.P = this.Rs;
            }
            n();
        }
        i();
    }

    public void markerKeyUp() {
        this.N = false;
        i();
    }

    public void markerFocus(MarkerView markerView) {
        this.N = false;
        if (markerView == this.G) {
            m();
        } else {
            o();
        }
        this.ad.postDelayed(new Runnable() {
            public void run() {
                AudioJoinerActivity.this.i();
            }
        }, 100);
    }

    @SuppressLint("InvalidWakeLockTag")
    private void a() {
        setContentView(R.layout.audiojoineractivity);
        LinearLayout sa = (LinearLayout) findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(AudioJoinerActivity.this, sa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Audio Joiner");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (n || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(n);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.i = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(6, "VideoMerge");
            if (!this.i.isHeld()) {
                this.i.acquire();
            }
            this.k = new ArrayList<>();
            this.k.add("64 K/Bit");
            this.k.add("128 K/Bit");
            this.k.add("256 K/Bit");
            this.b = (RelativeLayout) findViewById(R.id.BtnAddMusic);
            this.b.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    AudioJoinerActivity.this.startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), 1);
                }
            });
            this.l = (Spinner) findViewById(R.id.sp_convert);
            this.j = new AudioJoinerAdapter(this, this.k, 0);
            this.l.setAdapter(this.j);
            this.l.setSelection(0);
            this.l.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (adapterView.getItemAtPosition(i).toString() == "64 K/Bit") {
                        AudioJoinerActivity.this.audiorate = 64;
                    } else if (adapterView.getItemAtPosition(i).toString() == "128 K/Bit") {
                        AudioJoinerActivity.this.audiorate = 128;
                    } else if (adapterView.getItemAtPosition(i).toString() == "256 K/Bit") {
                        AudioJoinerActivity.this.audiorate = 256;
                    }
                }
            });
            getApplicationContext();
            this.a = (AudioManager) getSystemService(AUDIO_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.an = displayMetrics.density;
            this.ar = (int) (46.0f * this.an);
            this.aq = (int) (48.0f * this.an);
            this.ap = (int) (this.an * 10.0f);
            this.ao = (int) (10.0f * this.an);
            this.H = (TextView) findViewById(R.id.songname);
            this.M = (ImageButton) findViewById(R.id.play);
            this.M.setOnClickListener(this.au);
            this.L = (ImageButton) findViewById(R.id.rew);
            this.L.setOnClickListener(this.av);
            this.K = (ImageButton) findViewById(R.id.ffwd);
            this.K.setOnClickListener(this.aw);
            this.I = (ImageButton) findViewById(R.id.btnvolumdown);
            this.I.setOnClickListener(this.ax);
            this.J = (ImageButton) findViewById(R.id.btnvolumup);
            this.J.setOnClickListener(this.ay);
            j();
            this.E = (WaveformView) findViewById(R.id.waveform);
            this.E.setListener(this);
            this.Rs = 0;
            this.ac = -1;
            this.ab = -1;
            if (this.s != null) {
                this.E.setSoundFile(this.s);
                this.E.recomputeHeights(this.an);
                this.Rs = this.E.maxPos();
            }
            this.G = (MarkerView) findViewById(R.id.startmarker);
            this.G.setListener(this);
            this.G.setAlpha(255);
            this.G.setFocusable(n);
            this.G.setFocusableInTouchMode(n);
            this.U = n;
            this.F = (MarkerView) findViewById(R.id.endmarker);
            this.F.setListener(this);
            this.F.setAlpha(255);
            this.F.setFocusable(n);
            this.F.setFocusableInTouchMode(n);
            this.T = n;
            i();
            ads = new Ads();
            ads.Interstitialload(this);
            return;
        }
        throw new AssertionError();
    }


    public void c() {
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
        bundle.putString("song", this.az);
        bundle.putBoolean("isfrom", n);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }


    public void f() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle((CharSequence) "Device not supported").setMessage((CharSequence) "FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AudioJoinerActivity.this.finish();
            }
        }).create().show();
    }

    private void g() {
        this.t = new File(this.A);
        this.v = a(this.A);
        SongMetadataReader songMetadataReader = new SongMetadataReader(this, this.A);
        this.w = songMetadataReader.mTitle;
        this.z = songMetadataReader.mArtist;
        this.y = songMetadataReader.mAlbum;
        this.B = songMetadataReader.mYear;
        this.x = songMetadataReader.mGenre;
        String str = this.w;
        if (this.z != null && this.z.length() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" - ");
            sb.append(this.z);
            str = sb.toString();
        }
        this.H.setText(str);
        this.H.setSelected(n);
        this.p = System.currentTimeMillis();
        this.o = System.currentTimeMillis();
        this.q = n;
        this.r = new ProgressDialog(this);
        this.r.setProgressStyle(1);
        this.r.setTitle(R.string.progress_dialog_loading);
        this.r.setCancelable(false);
        this.r.show();
        final ProgressListener r0 = new ProgressListener() {
            public boolean reportProgress(double d) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - AudioJoinerActivity.this.o > 100) {
                    AudioJoinerActivity.this.r.setProgress((int) (((double) AudioJoinerActivity.this.r.getMax()) * d));
                    AudioJoinerActivity.this.o = currentTimeMillis;
                }
                return AudioJoinerActivity.this.q;
            }
        };
        this.ah = false;
        new Thread() {
            public void run() {
                AudioJoinerActivity.this.ah = SeekTest.CanSeekAccurately(AudioJoinerActivity.this.getPreferences(0));
                System.out.println("Seek test done, creating media player.");
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(AudioJoinerActivity.this.t.getAbsolutePath());
                    AudioManager audioManager = AudioJoinerActivity.this.a;
                    mediaPlayer.setAudioStreamType(3);
                    mediaPlayer.prepare();
                    AudioJoinerActivity.this.ae = mediaPlayer;
                } catch (final IOException e) {
                    AudioJoinerActivity.this.ad.post(new Runnable() {
                        public void run() {
                            AudioJoinerActivity.this.a("ReadError", AudioJoinerActivity.this.getResources().getText(R.string.read_error), e);
                        }
                    });
                }
            }
        }.start();
        new Thread() {
            public void run() {
                final String str;
                try {
                    AudioJoinerActivity.this.s = CheapSoundFile.create(AudioJoinerActivity.this.t.getAbsolutePath(), r0);
                    if (AudioJoinerActivity.this.s == null) {
                        AudioJoinerActivity.this.r.dismiss();
                        String[] split = AudioJoinerActivity.this.t.getName().toLowerCase().split("\\.");
                        if (split.length < 2) {
                            str = AudioJoinerActivity.this.getResources().getString(R.string.no_extension_error);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append(AudioJoinerActivity.this.getResources().getString(R.string.bad_extension_error));
                            sb.append(" ");
                            sb.append(split[split.length - 1]);
                            str = sb.toString();
                        }
                        AudioJoinerActivity.this.ad.post(new Runnable() {
                            public void run() {
                                AudioJoinerActivity.this.a("UnsupportedExtension", str, new Exception());
                            }
                        });
                        return;
                    }
                    AudioJoinerActivity.this.r.dismiss();
                    if (AudioJoinerActivity.this.q) {
                        AudioJoinerActivity.this.ad.post(new Runnable() {
                            public void run() {
                                AudioJoinerActivity.this.h();
                            }
                        });
                    } else {
                        AudioJoinerActivity.this.finish();
                    }
                } catch (final Exception e) {
                    AudioJoinerActivity.this.r.dismiss();
                    e.printStackTrace();
                    AudioJoinerActivity.this.ad.post(new Runnable() {
                        public void run() {
                            AudioJoinerActivity.this.a("ReadError", AudioJoinerActivity.this.getResources().getText(R.string.read_error), e);
                        }
                    });
                }
            }
        }.start();
    }


    public void h() {
        this.E.setSoundFile(this.s);
        this.E.recomputeHeights(this.an);
        this.Rs = this.E.maxPos();
        this.ac = -1;
        this.ab = -1;
        this.ag = false;
        this.aa = 0;
        this.Z = 0;
        this.Y = 0;
        k();
        if (this.P > this.Rs) {
            this.P = this.Rs;
        }
        i();
    }


    public synchronized void i() {
        if (this.af) {
            int currentPosition = this.ae.getCurrentPosition() + this.W;
            int millisecsToPixels = this.E.millisecsToPixels(currentPosition);
            this.E.setPlayback(millisecsToPixels);
            c(millisecsToPixels - (this.S / 2));
            if (currentPosition >= this.V) {
                p();
            }
        }
        int i2 = 0;
        if (!this.ag) {
            if (this.Y != 0) {
                int i3 = this.Y;
                int i4 = this.Y / 30;
                if (this.Y > 80) {
                    this.Y -= 80;
                } else if (this.Y < -80) {
                    this.Y += 80;
                } else {
                    this.Y = 0;
                }
                this.aa += i4;
                if (this.aa + (this.S / 2) > this.Rs) {
                    this.aa = this.Rs - (this.S / 2);
                    this.Y = 0;
                }
                if (this.aa < 0) {
                    this.aa = 0;
                    this.Y = 0;
                }
                this.Z = this.aa;
            } else {
                int i5 = this.Z - this.aa;
                int i6 = i5 > 10 ? i5 / 10 : i5 > 0 ? 1 : i5 < -10 ? i5 / 10 : i5 < 0 ? -1 : 0;
                this.aa += i6;
            }
        }
        this.E.setParameters(this.Q, this.P, this.aa);
        this.E.invalidate();
        MarkerView markerView = this.G;
        StringBuilder sb = new StringBuilder();
        sb.append(getResources().getText(R.string.start_marker));
        sb.append(" ");
        sb.append(d(this.Q));
        markerView.setContentDescription(sb.toString());
        MarkerView markerView2 = this.F;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getResources().getText(R.string.end_marker));
        sb2.append(" ");
        sb2.append(d(this.P));
        markerView2.setContentDescription(sb2.toString());
        int i7 = (this.Q - this.aa) - this.ar;
        if (this.G.getWidth() + i7 < 0) {
            if (this.U) {
                this.G.setAlpha(0);
                this.U = false;
            }
            i7 = 0;
        } else if (!this.U) {
            this.ad.postDelayed(new Runnable() {
                public void run() {
                    AudioJoinerActivity.this.U = AudioJoinerActivity.n;
                    AudioJoinerActivity.this.G.setAlpha(255);
                }
            }, 0);
        }
        int width = ((this.P - this.aa) - this.F.getWidth()) + this.aq;
        if (this.F.getWidth() + width >= 0) {
            if (!this.T) {
                this.ad.postDelayed(new Runnable() {
                    public void run() {
                        AudioJoinerActivity.this.T = AudioJoinerActivity.n;
                        AudioJoinerActivity.this.F.setAlpha(255);
                    }
                }, 0);
            }
            i2 = width;
        } else if (this.T) {
            this.F.setAlpha(0);
            this.T = false;
        }
        this.G.setLayoutParams(new LayoutParams(-2, -2, i7, this.ap));
        this.F.setLayoutParams(new LayoutParams(-2, -2, i2, (this.E.getMeasuredHeight() - this.F.getHeight()) - this.ao));
    }

    private void j() {
        if (this.af) {
            this.M.setImageResource(R.drawable.ic_playlist_pause);
            this.M.setContentDescription(getResources().getText(R.string.stop));
            return;
        }
        this.M.setImageResource(R.drawable.ic_playlist_play);
        this.M.setContentDescription(getResources().getText(R.string.play));
    }

    private void k() {
        this.Q = this.E.secondsToPixels(0.0d);
        this.P = this.E.secondsToPixels(15.0d);
    }

    private int a(int i2) {
        if (i2 < 0) {
            return 0;
        }
        return i2 > this.Rs ? this.Rs : i2;
    }

    private void l() {
        b(this.Q - (this.S / 2));
    }

    private void m() {
        c(this.Q - (this.S / 2));
    }

    private void n() {
        b(this.P - (this.S / 2));
    }

    private void o() {
        c(this.P - (this.S / 2));
    }

    private void b(int i2) {
        c(i2);
        i();
    }

    private void c(int i2) {
        if (!this.ag) {
            this.Z = i2;
            if (this.Z + (this.S / 2) > this.Rs) {
                this.Z = this.Rs - (this.S / 2);
            }
            if (this.Z < 0) {
                this.Z = 0;
            }
        }
    }


    public String d(int i2) {
        return (this.E == null || !this.E.isInitialized()) ? "" : a(this.E.pixelsToSeconds(i2));
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


    public synchronized void p() {
        if (this.ae != null && this.ae.isPlaying()) {
            this.ae.pause();
        }
        this.E.setPlayback(-1);
        this.af = false;
        j();
    }


    public synchronized void e(int i2) {
        try {
            if (this.af) {
                p();
            } else if (this.ae != null) {
                this.X = this.E.pixelsToMillisecs(i2);
                if (i2 < this.Q) {
                    this.V = this.E.pixelsToMillisecs(this.Q);
                } else if (i2 > this.P) {
                    this.V = this.E.pixelsToMillisecs(this.Rs);
                } else {
                    this.V = this.E.pixelsToMillisecs(this.P);
                }
                this.W = 0;
                int secondsToFrames = this.E.secondsToFrames(((double) this.X) * 0.001d);
                int secondsToFrames2 = this.E.secondsToFrames(((double) this.V) * 0.001d);
                int seekableFrameOffset = this.s.getSeekableFrameOffset(secondsToFrames);
                int seekableFrameOffset2 = this.s.getSeekableFrameOffset(secondsToFrames2);
                if (this.ah && seekableFrameOffset >= 0 && seekableFrameOffset2 >= 0) {
                    this.ae.reset();
                    MediaPlayer mediaPlayer = this.ae;
                    AudioManager audioManager = this.a;
                    mediaPlayer.setAudioStreamType(3);
                    this.ae.setDataSource(new FileInputStream(this.t.getAbsolutePath()).getFD(), (long) seekableFrameOffset, (long) (seekableFrameOffset2 - seekableFrameOffset));
                    this.ae.prepare();
                    this.W = this.X;
                    System.out.println("Exception trying to play file subset");
                    this.ae.reset();
                    MediaPlayer mediaPlayer2 = this.ae;
                    AudioManager audioManager2 = this.a;
                    mediaPlayer2.setAudioStreamType(3);
                    this.ae.setDataSource(this.t.getAbsolutePath());
                    this.ae.prepare();
                    this.W = 0;
                }
                this.ae.setOnCompletionListener(new OnCompletionListener() {
                    public synchronized void onCompletion(MediaPlayer mediaPlayer) {
                        AudioJoinerActivity.this.p();
                    }
                });
                this.af = n;
                if (this.W == 0) {
                    this.ae.seekTo(this.X);
                }
                this.ae.start();
                i();
                j();
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
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AudioJoinerActivity.this.finish();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return n;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return n;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (this.ae != null && this.ae.isPlaying()) {
                try {
                    this.ae.pause();
                    this.M.setImageResource(R.drawable.ic_playlist_play);
                    this.M.setContentDescription(getResources().getText(R.string.play));
                } catch (IllegalStateException e2) {
                    e2.printStackTrace();
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb.append("/");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.AudioJoiner));
            this.az = sb.toString();
            File file = new File(this.az);
            if (!file.exists()) {
                file.mkdirs();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(file.getAbsolutePath());
            sb2.append("/JoinMP3_");
            sb2.append(System.currentTimeMillis());
            sb2.append(".mp3");
            this.az = sb2.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append("concat:");
            sb3.append(Helper.audiopath);
            sb3.append("|");
            sb3.append(Helper.audiopath1);
            a(new String[]{"-y", "-i", sb3.toString(), "-c:a", "copy", this.az, "-map_metadata", "0:1"}, this.az);
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
                    progressDialog.dismiss();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(new File(AudioJoinerActivity.this.az)));
                    AudioJoinerActivity.this.sendBroadcast(intent);
                    AudioJoinerActivity.this.c();
                    AudioJoinerActivity.this.refreshGallery(str);

                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        AudioJoinerActivity.this.deleteFromGallery(str);
                        Toast.makeText(AudioJoinerActivity.this.getApplicationContext(), "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                } else {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        AudioJoinerActivity.this.deleteFromGallery(str);
                        Toast.makeText(AudioJoinerActivity.this.getApplicationContext(), "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }


            }
        });
        getWindow().clearFlags(16);
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


    @Override
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        g();
        if (i2 == 1 && i3 == -1 && intent != null && intent.getData() != null) {
            try {
                this.m = getContentResolver().query(intent.getData(), new String[]{"_data"}, null, null, null);
                int columnIndexOrThrow = this.m.getColumnIndexOrThrow("_data");
                this.m.moveToFirst();
                Helper.audiopath1 = this.m.getString(columnIndexOrThrow).toString();
            } catch (NoSuchMethodError e2) {
                e2.printStackTrace();
            } catch (NullPointerException e3) {
                e3.printStackTrace();
            } catch (IllegalStateException e4) {
                e4.printStackTrace();
            } catch (NumberFormatException e5) {
                e5.printStackTrace();
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        }
    }

    public void refreshGallery(String str) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(str)));
        sendBroadcast(intent);
    }
}
