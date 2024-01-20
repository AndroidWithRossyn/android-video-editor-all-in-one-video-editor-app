package com.androworld.allinonevideoeditor.videomirror;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;

import com.androworld.allinonevideoeditor.Adclick;
import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.RangePlaySeekBar;
import com.androworld.allinonevideoeditor.RangeSeekBar;
import com.androworld.allinonevideoeditor.RangeSeekBar.OnRangeSeekBarChangeListener;
import com.androworld.allinonevideoeditor.UtilCommand;
import com.androworld.allinonevideoeditor.VideoPlayer;
import com.androworld.allinonevideoeditor.listvideoandmyvideo.ListVideoAndMyAlbumActivity;
import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;


import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

@SuppressLint({"WrongConstant", "ResourceType"})
public class VideoMirrorActivity extends AppCompatActivity {
    public static String videoPath = null;
    static final boolean y = true;

    public VideoView A;
    private String B;

    public String C;
    public int MP_DURATION;
    int a = 4;
    int b = 0;
    boolean c = y;
    ViewGroup d;
    int e;
    int f;
    int g;
    int h;
    int i;
    public boolean isInFront = y;
    RangeSeekBar<Integer> j;
    RangePlaySeekBar<Integer> k;
    TextView l;
    TextView m;
    TextView n;
    TextView o;
    WakeLock p;
    public ProgressDialog prgDialog;
    RelativeLayout q;
    RelativeLayout r;
    RelativeLayout s;
    RelativeLayout t;
    public int totalVideoDuration = 0;
    RelativeLayout u;
    RelativeLayout v;
    public ImageView videoPlayBtn;
    RelativeLayout w;
    RelativeLayout x;
    private StateObserver z = new StateObserver();
    private Ads ads;

    @SuppressLint({"HandlerLeak"})
    public class StateObserver extends Handler {
        private boolean b = false;
        private Runnable c = new Runnable() {
            public void run() {
                StateObserver.this.a();
            }
        };

        public StateObserver() {
        }


        public void a() {
            if (!this.b) {
                this.b = VideoMirrorActivity.y;
                sendEmptyMessage(0);
            }
        }

        @Override
        public void handleMessage(Message message) {
            this.b = false;
            VideoMirrorActivity.this.k.setSelectedMaxValue(Integer.valueOf(VideoMirrorActivity.this.A.getCurrentPosition()));
            if (!VideoMirrorActivity.this.A.isPlaying() || VideoMirrorActivity.this.A.getCurrentPosition() >= ((Integer) VideoMirrorActivity.this.j.getSelectedMaxValue()).intValue()) {
                if (VideoMirrorActivity.this.A.isPlaying()) {
                    VideoMirrorActivity.this.A.pause();
                    VideoMirrorActivity.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    VideoMirrorActivity.this.A.seekTo(((Integer) VideoMirrorActivity.this.j.getSelectedMinValue()).intValue());
                    VideoMirrorActivity.this.k.setSelectedMinValue(VideoMirrorActivity.this.j.getSelectedMinValue());
                    VideoMirrorActivity.this.k.setVisibility(4);
                }
                if (!VideoMirrorActivity.this.A.isPlaying()) {
                    VideoMirrorActivity.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    VideoMirrorActivity.this.k.setVisibility(4);
                    return;
                }
                return;
            }
            VideoMirrorActivity.this.k.setVisibility(0);
            postDelayed(this.c, 50);
        }
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.videomirroractivity);

        LinearLayout sa = (LinearLayout) findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(VideoMirrorActivity.this, sa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Mirror");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (y || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(y);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.c = y;
            copyCreate();
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
        intent.putExtra("song", this.C);
        startActivity(intent);
        finish();
    }

    @SuppressLint("InvalidWakeLockTag")
    public void copyCreate() {
        this.isInFront = y;
        this.a = e() / 100;
        this.totalVideoDuration = 0;
        this.p = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(6, "VideoMerge");
        if (!this.p.isHeld()) {
            this.p.acquire();
        }
        this.B = getIntent().getStringExtra("videouri");
        videoPath = this.B;
        this.o = (TextView) findViewById(R.id.Filename);
        this.A = (VideoView) findViewById(R.id.addcutsvideoview);
        this.videoPlayBtn = (ImageView) findViewById(R.id.videoplaybtn);
        this.l = (TextView) findViewById(R.id.left_pointer);
        this.m = (TextView) findViewById(R.id.mid_pointer);
        this.n = (TextView) findViewById(R.id.right_pointer);
        this.q = (RelativeLayout) findViewById(R.id.btn_rightmirror);
        this.r = (RelativeLayout) findViewById(R.id.back_rightmirror);
        this.s = (RelativeLayout) findViewById(R.id.btn_leftmirror);
        this.t = (RelativeLayout) findViewById(R.id.back_leftmirror);
        this.u = (RelativeLayout) findViewById(R.id.btn_TopMirror);
        this.v = (RelativeLayout) findViewById(R.id.back_TopMirror);
        this.x = (RelativeLayout) findViewById(R.id.btn_BottumMirror);
        this.w = (RelativeLayout) findViewById(R.id.back_BottumMirror);
        this.o.setText(new File(videoPath).getName());
        runOnUiThread(new Runnable() {
            public void run() {
                VideoMirrorActivity.this.prgDialog = ProgressDialog.show(VideoMirrorActivity.this, "", "Loading...", VideoMirrorActivity.y);
            }
        });
        VideoSeekBar();
        this.q.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoMirrorActivity.this.r.setVisibility(0);
                VideoMirrorActivity.this.t.setVisibility(8);
                VideoMirrorActivity.this.v.setVisibility(8);
                VideoMirrorActivity.this.w.setVisibility(8);
                VideoMirrorActivity.this.b = 1;
            }
        });
        this.s.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoMirrorActivity.this.r.setVisibility(8);
                VideoMirrorActivity.this.t.setVisibility(0);
                VideoMirrorActivity.this.v.setVisibility(8);
                VideoMirrorActivity.this.w.setVisibility(8);
                VideoMirrorActivity.this.b = 2;
            }
        });
        this.u.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoMirrorActivity.this.r.setVisibility(8);
                VideoMirrorActivity.this.t.setVisibility(8);
                VideoMirrorActivity.this.v.setVisibility(0);
                VideoMirrorActivity.this.w.setVisibility(8);
                VideoMirrorActivity.this.b = 3;
            }
        });
        this.x.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoMirrorActivity.this.r.setVisibility(8);
                VideoMirrorActivity.this.t.setVisibility(8);
                VideoMirrorActivity.this.v.setVisibility(8);
                VideoMirrorActivity.this.w.setVisibility(0);
                VideoMirrorActivity.this.b = 4;
            }
        });
    }

    private void a(String[] strArr, final String str) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String ffmpegCommand = UtilCommand.main(strArr);
        Log.e("sssss",""+ffmpegCommand);

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
                    intent.setData(Uri.fromFile(new File(VideoMirrorActivity.this.C)));
                    VideoMirrorActivity.this.sendBroadcast(intent);
                    VideoMirrorActivity.this.b();

                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(VideoMirrorActivity.this.C).delete();
                        VideoMirrorActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoMirrorActivity.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                } else {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(VideoMirrorActivity.this.C).delete();
                        VideoMirrorActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoMirrorActivity.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
                VideoMirrorActivity.this.refreshGallery(str);


            }
        });
        getWindow().clearFlags(16);
    }

    public String[] cmdleft(String str, String str2) {
        boolean aa = aa(str, 1280, 720);
        int aaa = aaa(str);
        String str3 = "scale=iw/2:-1";
        if (aa) {
            str3 = "scale=640:-1";
        }
        String str4 = "scale=-1:ih/2";
        if (aa) {
            str4 = "scale=-1:640";
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add("-y");
        arrayList.add("-i");
        arrayList.add(str);
        arrayList.add("-filter_complex");
        if (aaa == 90) {
            StringBuilder sb = new StringBuilder();
            sb.append("transpose=1,");
            sb.append(str4);
            sb.append(",split[tmp],pad=2*iw:0[left]; [tmp]hflip[right]; [left][right] overlay=W/2:0");
            arrayList.add(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(aaa);
            Log.e("rotate", sb2.toString());
        } else if (aaa == 180) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("transpose=1:landscape,rotate=PI,");
            sb3.append(str3);
            sb3.append(",split[tmp],pad=2*iw:0[left]; [tmp]hflip[right]; [left][right] overlay=W/2:0");
            arrayList.add(sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append("");
            sb4.append(aaa);
            Log.e("rotate", sb4.toString());
        } else if (aaa != 270) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str3);
            sb5.append(",split[tmp],pad=2*iw:0[left]; [tmp]hflip[right]; [left][right] overlay=W/2:0");
            arrayList.add(sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append("");
            sb6.append(aaa);
            Log.e("rotate", sb6.toString());
        } else {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("transpose=2,");
            sb7.append(str4);
            sb7.append(",split[tmp],pad=2*iw:0[left]; [tmp]hflip[right]; [left][right] overlay=W/2:0");
            arrayList.add(sb7.toString());
            StringBuilder sb8 = new StringBuilder();
            sb8.append("");
            sb8.append(aaa);
            Log.e("rotate", sb8.toString());
        }
        arrayList.add("-vcodec");
        arrayList.add("libx264");
        arrayList.add("-strict");
        arrayList.add("experimental");
        arrayList.add("-threads");
        arrayList.add("5");
        arrayList.add("-preset");
        arrayList.add("ultrafast");
        arrayList.add(str2);
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public String[] cmdtop(String str, String str2) {
        boolean aa = aa(str, 1280, 720);
        int aaa = aaa(str);
        String str3 = "scale=-1:ih/2";
        if (aa) {
            str3 = "scale=640:-1";
        }
        String str4 = "scale=-1:ih/2";
        if (aa) {
            str4 = "scale=-1:640";
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add("-i");
        arrayList.add(str);
        arrayList.add("-filter_complex");
        if (aaa == 90) {
            StringBuilder sb = new StringBuilder();
            sb.append("transpose=1,");
            sb.append(str4);
            sb.append(",split[tmp],pad=0:2*ih[top]; [tmp]vflip[bottom]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(aaa);
            Log.e("rotate", sb2.toString());
        } else if (aaa == 180) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("transpose=1:landscape,rotate=PI,");
            sb3.append(str3);
            sb3.append(",split[tmp],pad=0:2*ih[top]; [tmp]vflip[bottom]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append("");
            sb4.append(aaa);
            Log.e("rotate", sb4.toString());
        } else if (aaa != 270) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str3);
            sb5.append(",split[tmp],pad=0:2*ih[top]; [tmp]vflip[bottom]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append("");
            sb6.append(aaa);
            Log.e("rotate", sb6.toString());
        } else {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("transpose=2,");
            sb7.append(str4);
            sb7.append(",split[tmp],pad=0:2*ih[top]; [tmp]vflip[bottom]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb7.toString());
            StringBuilder sb8 = new StringBuilder();
            sb8.append("");
            sb8.append(aaa);
            Log.e("rotate", sb8.toString());
        }
        arrayList.add("-vcodec");
        arrayList.add("libx264");
        arrayList.add("-strict");
        arrayList.add("experimental");
        arrayList.add("-threads");
        arrayList.add("5");
        arrayList.add("-preset");
        arrayList.add("ultrafast");
        arrayList.add(str2);
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public String[] cmddown(String str, String str2) {
        boolean aa = aa(str, 1280, 720);
        String str3 = "scale=-1:ih/2";
        if (aa) {
            str3 = "scale=640:-1";
        }
        String str4 = "scale=-1:ih/2";
        if (aa) {
            str4 = "scale=-1:640";
        }
        int aaa = aaa(str);
        ArrayList arrayList = new ArrayList();
        arrayList.add("-i");
        arrayList.add(str);
        arrayList.add("-filter_complex");
        if (aaa == 90) {
            StringBuilder sb = new StringBuilder();
            sb.append("transpose=1,");
            sb.append(str4);
            sb.append(",split[bottom],vflip,pad=0:2*ih[top]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(aaa);
            Log.e("rotate", sb2.toString());
        } else if (aaa == 180) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("transpose=1:landscape,rotate=PI,");
            sb3.append(str3);
            sb3.append(",split[bottom],vflip,pad=0:2*ih[top]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append("");
            sb4.append(aaa);
            Log.e("rotate", sb4.toString());
        } else if (aaa != 270) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str3);
            sb5.append(",split[bottom],vflip,pad=0:2*ih[top]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append("");
            sb6.append(aaa);
            Log.e("rotate", sb6.toString());
        } else {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("transpose=2,");
            sb7.append(str4);
            sb7.append(",split[bottom],vflip,pad=0:2*ih[top]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb7.toString());
            StringBuilder sb8 = new StringBuilder();
            sb8.append("");
            sb8.append(aaa);
            Log.e("rotate", sb8.toString());
        }
        arrayList.add("-vcodec");
        arrayList.add("libx264");
        arrayList.add("-strict");
        arrayList.add("experimental");
        arrayList.add("-threads");
        arrayList.add("5");
        arrayList.add("-preset");
        arrayList.add("ultrafast");
        arrayList.add(str2);
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static int aaa(String str) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str);
        String extractMetadata = mediaMetadataRetriever.extractMetadata(24);
        if (extractMetadata == null) {
            return 0;
        }
        return Integer.valueOf(extractMetadata).intValue();
    }

    public static boolean aa(String str, int i2, int i3) {
        Point bb = bb(new File(str));
        if (bb == null || ((bb.x <= i2 || bb.y <= i3) && (bb.y <= i2 || bb.x <= i3))) {
            return false;
        }
        return y;
    }

    public static Point bb(File file) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
        String extractMetadata = mediaMetadataRetriever.extractMetadata(19);
        String extractMetadata2 = mediaMetadataRetriever.extractMetadata(18);
        if (extractMetadata == null || extractMetadata2 == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(extractMetadata);
        sb.append(" ");
        sb.append(extractMetadata2);
        Log.e("size", sb.toString());
        return new Point(Integer.parseInt(extractMetadata), Integer.parseInt(extractMetadata2));
    }

    public void VideoSeekBar() {
        this.A.setVideoURI(Uri.parse(videoPath));
        this.A.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (VideoMirrorActivity.this.c) {
                    VideoMirrorActivity.this.e = 0;
                    VideoMirrorActivity.this.f = mediaPlayer.getDuration();
                    VideoMirrorActivity.this.MP_DURATION = mediaPlayer.getDuration();
                    VideoMirrorActivity.this.seekLayout(0, VideoMirrorActivity.this.MP_DURATION);
                    VideoMirrorActivity.this.A.start();
                    VideoMirrorActivity.this.A.pause();
                    VideoMirrorActivity.this.A.seekTo(300);
                    return;
                }
                VideoMirrorActivity.this.seekLayout(VideoMirrorActivity.this.e, VideoMirrorActivity.this.f);
                VideoMirrorActivity.this.A.start();
                VideoMirrorActivity.this.A.pause();
                VideoMirrorActivity.this.A.seekTo(VideoMirrorActivity.this.e);
            }
        });
        this.A.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                VideoMirrorActivity.this.prgDialog.dismiss();
                return false;
            }
        });
        this.videoPlayBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoMirrorActivity.this.d();
            }
        });
    }


    public void d() {
        if (this.A.isPlaying()) {
            this.A.pause();
            this.A.seekTo(((Integer) this.j.getSelectedMinValue()).intValue());
            this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
            this.k.setVisibility(4);
            return;
        }
        this.A.seekTo(((Integer) this.j.getSelectedMinValue()).intValue());
        this.A.start();
        this.k.setSelectedMaxValue(Integer.valueOf(this.A.getCurrentPosition()));
        this.z.a();
        this.videoPlayBtn.setBackgroundResource(R.drawable.pause2);
        this.k.setVisibility(0);
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroy() {
        this.totalVideoDuration = 0;
        if (this.p.isHeld()) {
            this.p.release();
        }
        super.onDestroy();
    }

    public void seekLayout(int i2, int i3) {
        TextView textView = this.l;
        StringBuilder sb = new StringBuilder();
        sb.append(getTimeForTrackFormat(i2));
        sb.append("");
        textView.setText(sb.toString());
        TextView textView2 = this.n;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getTimeForTrackFormat(i3));
        sb2.append("");
        textView2.setText(sb2.toString());
        TextView textView3 = this.m;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getTimeForTrackFormat(i3 - i2));
        sb3.append("");
        textView3.setText(sb3.toString());
        if (this.d != null) {
            this.d.removeAllViews();
            this.d = null;
            this.j = null;
            this.k = null;
        }
        this.d = (ViewGroup) findViewById(R.id.seekLayout);
        this.j = new RangeSeekBar<>(Integer.valueOf(0), Integer.valueOf(this.MP_DURATION), this);
        this.k = new RangePlaySeekBar<>(Integer.valueOf(0), Integer.valueOf(this.MP_DURATION), this);
        this.j.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> rangeSeekBar, Integer num, Integer num2, boolean z) {
                if (VideoMirrorActivity.this.A.isPlaying()) {
                    VideoMirrorActivity.this.A.pause();
                    VideoMirrorActivity.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                }
                if (VideoMirrorActivity.this.f == num2.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num = Integer.valueOf(num2.intValue() + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
                    }
                    VideoMirrorActivity.this.A.seekTo(num.intValue());
                } else if (VideoMirrorActivity.this.e == num.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num2 = Integer.valueOf(num.intValue() + 1000);
                    }
                    VideoMirrorActivity.this.A.seekTo(num.intValue());
                }
                VideoMirrorActivity.this.j.setSelectedMaxValue(num2);
                VideoMirrorActivity.this.j.setSelectedMinValue(num);
                VideoMirrorActivity.this.l.setText(VideoMirrorActivity.getTimeForTrackFormat(num.intValue()));
                VideoMirrorActivity.this.n.setText(VideoMirrorActivity.getTimeForTrackFormat(num2.intValue()));
                VideoMirrorActivity.this.m.setText(VideoMirrorActivity.getTimeForTrackFormat(num2.intValue() - num.intValue()));
                VideoMirrorActivity.this.k.setSelectedMinValue(num);
                VideoMirrorActivity.this.k.setSelectedMaxValue(num2);
                VideoMirrorActivity.this.e = num.intValue();
                VideoMirrorActivity.this.f = num2.intValue();
            }
        });
        this.d.addView(this.j);
        this.d.addView(this.k);
        this.j.setSelectedMinValue(Integer.valueOf(i2));
        this.j.setSelectedMaxValue(Integer.valueOf(i3));
        this.k.setSelectedMinValue(Integer.valueOf(i2));
        this.k.setSelectedMaxValue(Integer.valueOf(i3));
        this.k.setEnabled(false);
        this.k.setVisibility(4);
        this.prgDialog.dismiss();
    }

    private int e() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.heightPixels;
        int i3 = displayMetrics.widthPixels;
        return i3 <= i2 ? i3 : i2;
    }

    @SuppressLint({"DefaultLocale"})
    public static String getTimeForTrackFormat(int i2) {
        long j2 = (long) i2;
        return String.format("%02d:%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toHours(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(j2))), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2)))});
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    public void onPause() {
        super.onPause();
        this.c = false;
        try {
            if (this.A.isPlaying()) {
                this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                this.A.pause();
            }
        } catch (Exception unused) {
        }
        this.isInFront = false;
        if (this.k != null && this.k.getVisibility() == 0) {
            this.k.setVisibility(4);
        }
    }


    public void onRestart() {
        super.onRestart();
    }


    public void g() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VideoMirrorActivity.this.finish();
            }
        }).create().show();
    }

    public void deleteFromGallery(String str) {
        String[] strArr = {"_id"};
        String[] strArr2 = {str};
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor query = contentResolver.query(uri, strArr, "_data = ?", strArr2, null);
        if (query.moveToFirst()) {
            try {
                contentResolver.delete(ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, query.getLong(query.getColumnIndexOrThrow("_id"))), null, null);
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
    public void onResume() {
        super.onResume();
        this.isInFront = y;
        this.B = getIntent().getStringExtra("videouri");
        videoPath = this.B;
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
        return y;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return y;
        }
        if (menuItem.getItemId() == R.id.Done) {
            String[] strArr = new String[0];
            try {
                if (this.A.isPlaying()) {
                    this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    this.A.pause();
                }
            } catch (Exception unused) {
            }
            this.g = ((Integer) this.j.getSelectedMinValue()).intValue() / 1000;
            this.i = ((Integer) this.j.getSelectedMaxValue()).intValue() / 1000;
            this.h = this.i - this.g;
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb.append("/");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.VideoMirror));
            File file = new File(sb.toString());
            if (!file.exists()) {
                file.mkdirs();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb2.append("/");
            sb2.append(getResources().getString(R.string.MainFolderName));
            sb2.append("/");
            sb2.append(getResources().getString(R.string.VideoMirror));
            sb2.append("/Video_");
            sb2.append(System.currentTimeMillis());
            sb2.append(".mp4");
            this.C = sb2.toString();
            if (this.b != 0) {
                if (this.b == 1) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("");
                    sb3.append(this.g);
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("");
                    sb4.append(this.h);
                    strArr = new String[]{"-y", "-i", videoPath, "-strict", "experimental", "-vf", "crop=iw/2:ih:0:0,split[tmp],pad=2*iw[left]; [tmp]hflip[right]; [left][right] overlay=W/2", "-vb", "20M", "-r", "15", "-ss", sb3.toString(), "-t", sb4.toString(), this.C};
                } else if (this.b == 2) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("");
                    sb3.append(this.g);
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("");
                    sb4.append(this.h);
                    strArr = new String[]{"-y", "-i", videoPath, "-strict", "experimental", "-vf", "crop=iw/2:ih:0:0,split[tmp],pad=2*iw[right]; [tmp]hflip[left]; [left][right] overlay=W/2", "-vb", "20M", "-r", "15", "-ss", sb3.toString(), "-t", sb4.toString(), this.C};
                } else if (this.b == 3) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("");
                    sb3.append(this.g);
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("");
                    sb4.append(this.h);
                    strArr = new String[]{"-y", "-i", videoPath, "-strict", "experimental", "-vf", "crop=iw/2:ih:0:0,split[tmp],pad=2*iw[top]; [tmp]hflip[bottom]; [top][bottom] overlay=W/2", "-vb", "20M", "-r", "15", "-ss", sb3.toString(), "-t", sb4.toString(), this.C};
//                    strArr = cmdtop(videoPath, this.C);
                } else if (this.b == 4) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("");
                    sb3.append(this.g);
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("");
                    sb4.append(this.h);
                    strArr = new String[]{"-y", "-i", videoPath, "-strict", "experimental", "-vf", "crop=iw/2:ih:0:0,split[tmp],pad=2*iw[bottom]; [tmp]hflip[top]; [top][bottom] overlay=W/2", "-vb", "20M", "-r", "15", "-ss", sb3.toString(), "-t", sb4.toString(), this.C};

                    //                    strArr = cmddown(videoPath, this.C);
                }
                a(strArr, this.C);
            } else {
                Toast.makeText(this, "Select Option From List!", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
