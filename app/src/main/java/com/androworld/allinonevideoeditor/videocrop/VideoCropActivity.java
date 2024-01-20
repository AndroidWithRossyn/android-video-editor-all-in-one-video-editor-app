package com.androworld.allinonevideoeditor.videocrop;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.StatFs;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Media;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androworld.allinonevideoeditor.Adclick;
import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.Helper;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.UtilCommand;
import com.androworld.allinonevideoeditor.VideoPlayer;
import com.androworld.allinonevideoeditor.VideoPlayerState;
import com.androworld.allinonevideoeditor.VideoSliceSeekBar;
import com.androworld.allinonevideoeditor.VideoSliceSeekBar.SeekBarChangeListener;
import com.androworld.allinonevideoeditor.listvideoandmyvideo.ListVideoAndMyAlbumActivity;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.edmodo.cropper.CropImageView;
import com.edmodo.cropper.cropwindow.edge.Edge;


import java.io.File;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

@SuppressLint({"WrongConstant"})
public class VideoCropActivity extends AppCompatActivity {
    TextView Rs;
    static final boolean af = true;
    int A;
    int B;
    String C;
    String D;
    String E;
    String F;
    String G = "00";
    String H;
    ImageView I;
    ImageView J;
    ImageView K;
    ImageView L;
    ImageView M;
    ImageView N;
    ImageView O;
    ImageView P;
    ImageView Q;
    TextView S;
    TextView T;
    Bitmap U;
    View V;
    VideoPlayerState W = new VideoPlayerState();
    VideoSliceSeekBar X;
    a Y = new a();
    VideoView Z;
    CropImageView a;
    float aa;
    float ab;
    float ac;
    float ad;
    long ae;
    private int ag;
    private int ah;
    String b;
    ImageButton c;
    ImageButton d;
    ImageButton e;
    ImageButton f;
    ImageButton g;
    ImageButton h;
    ImageButton i;
    ImageButton j;
    ImageButton k;
    String l;
    int m;
    int n;
    int o;
    int p;
    int q;
    int r;
    int s;
    int t;
    int u;
    int v;
    int w;
    int x;
    int y;
    int z;
    private Ads ads;

    private class a extends Handler {
        private boolean b;
        private Runnable c;

        private a() {
            this.b = false;
            this.c = new Runnable() {
                public void run() {
                    a.this.a();
                }
            };
        }


        public void a() {
            if (!this.b) {
                this.b = VideoCropActivity.af;
                sendEmptyMessage(0);
            }
        }

        @Override
        public void handleMessage(Message message) {
            this.b = false;
            VideoCropActivity.this.X.videoPlayingProgress(VideoCropActivity.this.Z.getCurrentPosition());
            if (!VideoCropActivity.this.Z.isPlaying() || VideoCropActivity.this.Z.getCurrentPosition() >= VideoCropActivity.this.X.getRightProgress()) {
                if (VideoCropActivity.this.Z.isPlaying()) {
                    VideoCropActivity.this.Z.pause();
                    VideoCropActivity.this.V.setBackgroundResource(R.drawable.play2);
                    VideoCropActivity.this.Z.seekTo(100);
                }
                VideoCropActivity.this.X.setSliceBlocked(false);
                VideoCropActivity.this.X.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.c, 50);
        }
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.videocropactivity);

        LinearLayout sa = (LinearLayout) findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(VideoCropActivity.this, sa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView textView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        textView.setText("Video Crop");
        textView.setTypeface(Helper.txtface);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (af || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(af);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.E = getIntent().getStringExtra("videofilename");
            if (this.E != null) {
                this.U = ThumbnailUtils.createVideoThumbnail(this.E, 1);
            }
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.rl_container);
            LayoutParams layoutParams = (LayoutParams) frameLayout.getLayoutParams();
            this.x = FileUtils.getScreenWidth();
            layoutParams.width = this.x;
            layoutParams.height = this.x;
            frameLayout.setLayoutParams(layoutParams);
            this.a = (CropImageView) findViewById(R.id.cropperView);
            d();
            e();
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
        intent.putExtra("song", this.D);
        startActivity(intent);
        finish();
    }

    public void cropcommand() {
        h();
        getpath();
    }

    @SuppressLint("InvalidWakeLockTag")
    public void getpath() {
        if (this.w == 90) {
            try {
                this.o = this.B;
                int i2 = this.z;
                this.u = this.B;
                this.v = this.A;
                this.m = this.y;
                this.n = this.z;
                this.s = this.y;
                this.t = this.A;
                this.ag = this.m - this.o;
                this.ah = this.v - i2;
                this.p = this.q - (this.ah + i2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if (this.w == 270) {
            try {
                int i3 = this.B;
                int i4 = this.z;
                this.u = this.B;
                this.v = this.A;
                this.m = this.y;
                this.n = this.z;
                this.s = this.y;
                this.t = this.A;
                this.ag = this.m - i3;
                this.ah = this.v - i4;
                this.o = this.r - (this.ag + i3);
                this.p = i4;
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        } else {
            try {
                this.o = this.z;
                this.p = this.B;
                this.u = this.A;
                this.v = this.B;
                this.m = this.z;
                this.n = this.y;
                this.s = this.A;
                this.t = this.y;
                this.ag = this.u - this.o;
                this.ah = this.n - this.p;
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        this.H = String.valueOf(this.W.getStart() / 1000);
        this.F = String.valueOf(this.W.getDuration() / 1000);
        this.l = this.E;
        if (this.l.contains(".3gp") || this.l.contains(".3GP")) {
            try {
                this.C = FileUtils.getTargetFileName(this, this.l.replace(".3gp", ".mp4"));
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        } else if (this.l.contains(".flv") || this.l.contains(".FLv")) {
            try {
                this.C = FileUtils.getTargetFileName(this, this.l.replace(".flv", ".mp4"));
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        } else if (this.l.contains(".mov") || this.l.contains(".MOV")) {
            try {
                this.C = FileUtils.getTargetFileName(this, this.l.replace(".mov", ".mp4"));
            } catch (Exception e7) {
                e7.printStackTrace();
            }
        } else if (this.l.contains(".wmv") || this.l.contains(".WMV")) {
            try {
                this.C = FileUtils.getTargetFileName(this, this.l.replace(".wmv", ".mp4"));
            } catch (Exception e8) {
                e8.printStackTrace();
            }
        } else {
            try {
                this.C = FileUtils.getTargetFileName(this, this.l);
            } catch (Exception e9) {
                e9.printStackTrace();
            }
        }
        this.D = FileUtils.getTargetFileName(this, this.C);
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long availableBlocks = ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        File file = new File(this.W.getFilename());
        this.ae = 0;
        this.ae = file.length() / 1024;
        if ((availableBlocks / 1024) / 1024 >= this.ae / 1024) {
            ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(1, "VK_LOCK").acquire();
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("crop=w=");
                sb.append(this.ag);
                sb.append(":h=");
                sb.append(this.ah);
                sb.append(":x=");
                sb.append(this.o);
                sb.append(":y=");
                sb.append(this.p);
                a(new String[]{"-y", "-ss", this.H, "-t", this.F, "-i", this.l, "-strict", "experimental", "-vf", sb.toString(), "-r", "15", "-ab", "128k", "-vcodec", "mpeg4", "-acodec", "copy", "-b:v", "2500k", "-sample_fmt", "s16", "-ss", "0", "-t", this.F, this.D}, this.D);
            } catch (Exception unused) {
                File file2 = new File(this.D);
                if (file2.exists()) {
                    file2.delete();
                    finish();
                    return;
                }
                Toast.makeText(this, "please select any option!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Out of Memory!......", 0).show();
        }
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

                com.arthenica.mobileffmpeg.Config.printLastCommandOutput(Log.INFO);

                progressDialog.dismiss();
                if (returnCode == RETURN_CODE_SUCCESS) {
                    progressDialog.dismiss();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(new File(VideoCropActivity.this.D)));
                    VideoCropActivity.this.sendBroadcast(intent);
                    VideoCropActivity.this.b();
                    VideoCropActivity.this.refreshGallery(str);

                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoCropActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoCropActivity.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                } else {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoCropActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoCropActivity.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }


            }
        });
    }


    @Override
    public void onResume() {
        this.Z.seekTo(this.W.getCurrentTime());
        super.onResume();
    }


    public void onPause() {
        super.onPause();
        this.W.setCurrentTime(this.Z.getCurrentPosition());
    }

    private void d() {
        this.Rs = (TextView) findViewById(R.id.left_pointer);
        this.S = (TextView) findViewById(R.id.right_pointer);
        this.X = (VideoSliceSeekBar) findViewById(R.id.seek_bar);
        this.T = (TextView) findViewById(R.id.Filename);
        this.T.setText(new File(this.E).getName());
        this.V = findViewById(R.id.buttonply);
        this.V.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VideoCropActivity.this.Z == null || !VideoCropActivity.this.Z.isPlaying()) {
                    VideoCropActivity.this.V.setBackgroundResource(R.drawable.pause2);
                } else {
                    VideoCropActivity.this.V.setBackgroundResource(R.drawable.play2);
                }
                VideoCropActivity.this.g();
            }
        });
        Object lastNonConfigurationInstance = getLastNonConfigurationInstance();
        if (lastNonConfigurationInstance != null) {
            this.W = (VideoPlayerState) lastNonConfigurationInstance;
        } else {
            this.W.setFilename(this.E);
        }
        this.O = (ImageView) findViewById(R.id.slideimbtn_seven);
        this.J = (ImageView) findViewById(R.id.slideimbtn_eight);
        this.K = (ImageView) findViewById(R.id.slideimbtn_five);
        this.Q = (ImageView) findViewById(R.id.slideimbtn_three);
        this.I = (ImageView) findViewById(R.id.slideimbtn_cland);
        this.N = (ImageView) findViewById(R.id.slideimbtn_port);
        this.P = (ImageView) findViewById(R.id.slideimbtn_square);
        this.M = (ImageView) findViewById(R.id.slideimbtn_o);
        this.L = (ImageView) findViewById(R.id.slideimbtn_45);
        this.c = (ImageButton) findViewById(R.id.imbtn_custom);
        this.c.setOnClickListener(setRatioOriginal());
        this.e = (ImageButton) findViewById(R.id.imgbtn_eight);
        this.e.setOnClickListener(setRatioEight());
        this.i = (ImageButton) findViewById(R.id.imgbtn_seven);
        this.i.setOnClickListener(setRatioSeven());
        this.f = (ImageButton) findViewById(R.id.imgbtn_five);
        this.f.setOnClickListener(setRatioFive());
        this.k = (ImageButton) findViewById(R.id.imgbtn_three);
        this.k.setOnClickListener(setRatioThree());
        this.j = (ImageButton) findViewById(R.id.imgbtn_square);
        this.j.setOnClickListener(setRatioSqaure());
        this.h = (ImageButton) findViewById(R.id.imgbtn_port);
        this.h.setOnClickListener(setRatioPort());
        this.d = (ImageButton) findViewById(R.id.imgbtn_cland);
        this.d.setOnClickListener(setRatioLand());
        this.g = (ImageButton) findViewById(R.id.imgbtn_45);
        this.g.setOnClickListener(setRatioNine());
    }

    @SuppressLint({"NewApi"})
    private void e() {
        this.Z = (VideoView) findViewById(R.id.videoview);
        this.Z.setVideoPath(this.E);
        this.b = getTimeForTrackFormat(this.Z.getDuration(), af);
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(this.E);
        this.r = Integer.valueOf(mediaMetadataRetriever.extractMetadata(18)).intValue();
        this.q = Integer.valueOf(mediaMetadataRetriever.extractMetadata(19)).intValue();
        if (VERSION.SDK_INT > 16) {
            this.w = Integer.valueOf(mediaMetadataRetriever.extractMetadata(24)).intValue();
        } else {
            this.w = 0;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.a.getLayoutParams();
        if (this.w == 90 || this.w == 270) {
            if (this.r >= this.q) {
                if (this.r >= this.x) {
                    layoutParams.height = this.x;
                    layoutParams.width = (int) (((float) this.x) / (((float) this.r) / ((float) this.q)));
                } else {
                    layoutParams.width = this.x;
                    layoutParams.height = (int) (((float) this.q) * (((float) this.x) / ((float) this.r)));
                }
            } else if (this.q >= this.x) {
                layoutParams.width = this.x;
                layoutParams.height = (int) (((float) this.x) / (((float) this.q) / ((float) this.r)));
            } else {
                layoutParams.width = (int) (((float) this.r) * (((float) this.x) / ((float) this.q)));
                layoutParams.height = this.x;
            }
        } else if (this.r >= this.q) {
            if (this.r >= this.x) {
                layoutParams.width = this.x;
                layoutParams.height = (int) (((float) this.x) / (((float) this.r) / ((float) this.q)));
            } else {
                layoutParams.width = this.x;
                layoutParams.height = (int) (((float) this.q) * (((float) this.x) / ((float) this.r)));
            }
        } else if (this.q >= this.x) {
            layoutParams.width = (int) (((float) this.x) / (((float) this.q) / ((float) this.r)));
            layoutParams.height = this.x;
        } else {
            layoutParams.width = (int) (((float) this.r) * (((float) this.x) / ((float) this.q)));
            layoutParams.height = this.x;
        }
        this.a.setLayoutParams(layoutParams);
        this.a.setImageBitmap(Bitmap.createBitmap(layoutParams.width, layoutParams.height, Config.ARGB_8888));
        try {
            SearchVideo(getApplicationContext(), this.E, layoutParams.width, layoutParams.height);
        } catch (Exception unused) {
        }
        this.Z.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                VideoCropActivity.this.X.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (VideoCropActivity.this.X.getSelectedThumb() == 1) {
                            VideoCropActivity.this.Z.seekTo(VideoCropActivity.this.X.getLeftProgress());
                        }
                        VideoCropActivity.this.Rs.setText(VideoCropActivity.getTimeForTrackFormat(i, VideoCropActivity.af));
                        VideoCropActivity.this.S.setText(VideoCropActivity.getTimeForTrackFormat(i2, VideoCropActivity.af));
                        VideoCropActivity.this.G = VideoCropActivity.getTimeForTrackFormat(i, VideoCropActivity.af);
                        VideoCropActivity.this.W.setStart(i);
                        VideoCropActivity.this.b = VideoCropActivity.getTimeForTrackFormat(i2, VideoCropActivity.af);
                        VideoCropActivity.this.W.setStop(i2);
                    }
                });
                VideoCropActivity.this.b = VideoCropActivity.getTimeForTrackFormat(mediaPlayer.getDuration(), VideoCropActivity.af);
                VideoCropActivity.this.X.setMaxValue(mediaPlayer.getDuration());
                VideoCropActivity.this.X.setLeftProgress(0);
                VideoCropActivity.this.X.setRightProgress(mediaPlayer.getDuration());
                VideoCropActivity.this.X.setProgressMinDiff(0);
                VideoCropActivity.this.Z.seekTo(100);
            }
        });
    }


    public void f() {
        this.c.setBackgroundResource(R.drawable.ic_crop_custom);
        this.e.setBackgroundResource(R.drawable.ic_crop_35);
        this.i.setBackgroundResource(R.drawable.ic_crop_34);
        this.f.setBackgroundResource(R.drawable.ic_crop_32);
        this.k.setBackgroundResource(R.drawable.ic_crop_23);
        this.j.setBackgroundResource(R.drawable.ic_crop_square);
        this.h.setBackgroundResource(R.drawable.ic_crop_portrait);
        this.d.setBackgroundResource(R.drawable.ic_crop_landscape);
        this.g.setBackgroundResource(R.drawable.ic_crop_45);
    }


    public void g() {
        if (this.Z.isPlaying()) {
            this.Z.pause();
            this.X.setSliceBlocked(false);
            this.X.removeVideoStatusThumb();
            return;
        }
        this.Z.seekTo(this.X.getLeftProgress());
        this.Z.start();
        this.X.videoPlayingProgress(this.X.getLeftProgress());
        this.Y.a();
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
            StringBuilder sb3 = new StringBuilder(String.valueOf(sb2));
            sb3.append("0");
            sb3.append(i4);
            str = sb3.toString();
        } else {
            StringBuilder sb4 = new StringBuilder(String.valueOf(sb2));
            sb4.append(i4);
            str = sb4.toString();
        }
        StringBuilder sb5 = new StringBuilder();
        sb5.append("Display Result");
        sb5.append(str);
        Log.e("", sb5.toString());
        return str;
    }

    private void h() {
        if (this.w == 90 || this.w == 270) {
            this.aa = (float) this.q;
            this.ab = (float) this.r;
            this.ac = (float) this.a.getWidth();
            this.ad = (float) this.a.getHeight();
            this.z = (int) ((Edge.LEFT.getCoordinate() * this.aa) / this.ac);
            this.A = (int) ((Edge.RIGHT.getCoordinate() * this.aa) / this.ac);
            this.B = (int) ((Edge.TOP.getCoordinate() * this.ab) / this.ad);
            this.y = (int) ((Edge.BOTTOM.getCoordinate() * this.ab) / this.ad);
            return;
        }
        this.aa = (float) this.r;
        this.ab = (float) this.q;
        this.ac = (float) this.a.getWidth();
        this.ad = (float) this.a.getHeight();
        this.z = (int) ((Edge.LEFT.getCoordinate() * this.aa) / this.ac);
        this.A = (int) ((Edge.RIGHT.getCoordinate() * this.aa) / this.ac);
        this.B = (int) ((Edge.TOP.getCoordinate() * this.ab) / this.ad);
        this.y = (int) ((Edge.BOTTOM.getCoordinate() * this.ab) / this.ad);
    }

    public OnClickListener setRatioOriginal() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoCropActivity.this.a.setFixedAspectRatio(false);
                VideoCropActivity.this.f();
                VideoCropActivity.this.c.setBackgroundResource(R.drawable.ic_crop_custom_press);
                VideoCropActivity.this.J.setVisibility(8);
                VideoCropActivity.this.O.setVisibility(8);
                VideoCropActivity.this.I.setVisibility(8);
                VideoCropActivity.this.K.setVisibility(8);
                VideoCropActivity.this.M.setVisibility(0);
                VideoCropActivity.this.N.setVisibility(8);
                VideoCropActivity.this.P.setVisibility(8);
                VideoCropActivity.this.Q.setVisibility(8);
                VideoCropActivity.this.L.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioSqaure() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoCropActivity.this.a.setFixedAspectRatio(VideoCropActivity.af);
                VideoCropActivity.this.a.setAspectRatio(10, 10);
                VideoCropActivity.this.f();
                VideoCropActivity.this.j.setBackgroundResource(R.drawable.ic_crop_square_press);
                VideoCropActivity.this.J.setVisibility(8);
                VideoCropActivity.this.O.setVisibility(8);
                VideoCropActivity.this.I.setVisibility(8);
                VideoCropActivity.this.K.setVisibility(8);
                VideoCropActivity.this.M.setVisibility(8);
                VideoCropActivity.this.N.setVisibility(8);
                VideoCropActivity.this.P.setVisibility(0);
                VideoCropActivity.this.Q.setVisibility(8);
                VideoCropActivity.this.L.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioPort() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoCropActivity.this.a.setFixedAspectRatio(VideoCropActivity.af);
                VideoCropActivity.this.a.setAspectRatio(8, 16);
                VideoCropActivity.this.f();
                VideoCropActivity.this.h.setBackgroundResource(R.drawable.ic_crop_portrait_press);
                VideoCropActivity.this.J.setVisibility(8);
                VideoCropActivity.this.O.setVisibility(8);
                VideoCropActivity.this.I.setVisibility(8);
                VideoCropActivity.this.K.setVisibility(8);
                VideoCropActivity.this.M.setVisibility(8);
                VideoCropActivity.this.N.setVisibility(0);
                VideoCropActivity.this.P.setVisibility(8);
                VideoCropActivity.this.Q.setVisibility(8);
                VideoCropActivity.this.L.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioLand() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoCropActivity.this.a.setFixedAspectRatio(VideoCropActivity.af);
                VideoCropActivity.this.a.setAspectRatio(16, 8);
                VideoCropActivity.this.f();
                VideoCropActivity.this.d.setBackgroundResource(R.drawable.ic_crop_landscape_press);
                VideoCropActivity.this.J.setVisibility(8);
                VideoCropActivity.this.O.setVisibility(8);
                VideoCropActivity.this.I.setVisibility(0);
                VideoCropActivity.this.K.setVisibility(8);
                VideoCropActivity.this.M.setVisibility(8);
                VideoCropActivity.this.N.setVisibility(8);
                VideoCropActivity.this.P.setVisibility(8);
                VideoCropActivity.this.Q.setVisibility(8);
                VideoCropActivity.this.L.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioThree() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoCropActivity.this.a.setFixedAspectRatio(VideoCropActivity.af);
                VideoCropActivity.this.a.setAspectRatio(3, 2);
                VideoCropActivity.this.f();
                VideoCropActivity.this.k.setBackgroundResource(R.drawable.ic_crop_23_press);
                VideoCropActivity.this.J.setVisibility(8);
                VideoCropActivity.this.O.setVisibility(8);
                VideoCropActivity.this.I.setVisibility(8);
                VideoCropActivity.this.K.setVisibility(8);
                VideoCropActivity.this.M.setVisibility(8);
                VideoCropActivity.this.N.setVisibility(8);
                VideoCropActivity.this.P.setVisibility(8);
                VideoCropActivity.this.Q.setVisibility(0);
                VideoCropActivity.this.L.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioFive() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoCropActivity.this.a.setFixedAspectRatio(VideoCropActivity.af);
                VideoCropActivity.this.a.setAspectRatio(2, 3);
                VideoCropActivity.this.f();
                VideoCropActivity.this.f.setBackgroundResource(R.drawable.ic_crop_32_press);
                VideoCropActivity.this.J.setVisibility(8);
                VideoCropActivity.this.O.setVisibility(8);
                VideoCropActivity.this.I.setVisibility(8);
                VideoCropActivity.this.K.setVisibility(0);
                VideoCropActivity.this.M.setVisibility(8);
                VideoCropActivity.this.N.setVisibility(8);
                VideoCropActivity.this.P.setVisibility(8);
                VideoCropActivity.this.Q.setVisibility(8);
                VideoCropActivity.this.L.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioSeven() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoCropActivity.this.a.setFixedAspectRatio(VideoCropActivity.af);
                VideoCropActivity.this.a.setAspectRatio(4, 3);
                VideoCropActivity.this.f();
                VideoCropActivity.this.i.setBackgroundResource(R.drawable.ic_crop_34_press);
                VideoCropActivity.this.J.setVisibility(8);
                VideoCropActivity.this.O.setVisibility(0);
                VideoCropActivity.this.I.setVisibility(8);
                VideoCropActivity.this.K.setVisibility(8);
                VideoCropActivity.this.M.setVisibility(8);
                VideoCropActivity.this.N.setVisibility(8);
                VideoCropActivity.this.P.setVisibility(8);
                VideoCropActivity.this.Q.setVisibility(8);
                VideoCropActivity.this.L.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioEight() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoCropActivity.this.a.setFixedAspectRatio(VideoCropActivity.af);
                VideoCropActivity.this.a.setAspectRatio(5, 3);
                VideoCropActivity.this.f();
                VideoCropActivity.this.e.setBackgroundResource(R.drawable.ic_crop_35_press);
                VideoCropActivity.this.J.setVisibility(0);
                VideoCropActivity.this.O.setVisibility(8);
                VideoCropActivity.this.I.setVisibility(8);
                VideoCropActivity.this.K.setVisibility(8);
                VideoCropActivity.this.M.setVisibility(8);
                VideoCropActivity.this.N.setVisibility(8);
                VideoCropActivity.this.P.setVisibility(8);
                VideoCropActivity.this.Q.setVisibility(8);
                VideoCropActivity.this.L.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioNine() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoCropActivity.this.a.setFixedAspectRatio(VideoCropActivity.af);
                VideoCropActivity.this.a.setAspectRatio(5, 4);
                VideoCropActivity.this.f();
                VideoCropActivity.this.g.setBackgroundResource(R.drawable.ic_crop_45_press);
                VideoCropActivity.this.J.setVisibility(8);
                VideoCropActivity.this.O.setVisibility(8);
                VideoCropActivity.this.I.setVisibility(8);
                VideoCropActivity.this.K.setVisibility(8);
                VideoCropActivity.this.M.setVisibility(8);
                VideoCropActivity.this.N.setVisibility(8);
                VideoCropActivity.this.P.setVisibility(8);
                VideoCropActivity.this.Q.setVisibility(8);
                VideoCropActivity.this.L.setVisibility(0);
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
            managedQuery.moveToFirst();
            Long.valueOf(managedQuery.getLong(managedQuery.getColumnIndexOrThrow("_id")));
            managedQuery.moveToNext();
        }
    }


    public void j() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VideoCropActivity.this.finish();
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
        super.onBackPressed();
        Intent intent = new Intent(this, ListVideoAndMyAlbumActivity.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return af;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return af;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (this.Z != null && this.Z.isPlaying()) {
                this.Z.pause();
            }
            cropcommand();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
