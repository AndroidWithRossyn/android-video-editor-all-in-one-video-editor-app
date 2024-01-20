package com.androworld.allinonevideoeditor.videomute;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.support.v4.media.session.PlaybackStateCompat;
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
import com.androworld.allinonevideoeditor.StartActivity;
import com.androworld.allinonevideoeditor.StaticMethods;
import com.androworld.allinonevideoeditor.UtilCommand;
import com.androworld.allinonevideoeditor.VideoPlayer;
import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

@SuppressLint({"WrongConstant", "ResourceType"})
public class VideoMuteActivity extends AppCompatActivity {
    public static VideoMuteActivity context = null;
    static final boolean u = true;
    public static String videoPath;
    public boolean CompleteNotificationCreated = false;
    public int LIST_COLUMN_SIZE = 4;
    public int MP_DURATION;
    boolean a = u;
    RelativeLayout b;
    RelativeLayout c;
    RelativeLayout d;
    RelativeLayout e;
    RelativeLayout f;
    RelativeLayout g;
    ViewGroup h;
    RangeSeekBar<Integer> i;
    public boolean isInFront = u;
    RangePlaySeekBar<Integer> j;
    TextView k;
    TextView l;
    TextView m;
    public int maxtime;
    public int mintime;
    TextView n;
    TextView o;
    TextView p;
    public ProgressDialog prgDialog;
    TextView q;
    public int quality = 1;
    TextView r;
    boolean s = false;
    public int seekduration;
    public int seekend;
    public int seekstart;
    WakeLock t;
    public int totalVideoDuration = 0;
    public int type = 0;
    private StateObserver v = new StateObserver();
    public ImageView videoPlayBtn;

    public VideoView w;
    private String x;

    public String y;
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
                this.b = VideoMuteActivity.u;
                sendEmptyMessage(0);
            }
        }

        @Override
        public void handleMessage(Message message) {
            this.b = false;
            VideoMuteActivity.this.j.setSelectedMaxValue(Integer.valueOf(VideoMuteActivity.this.w.getCurrentPosition()));
            if (!VideoMuteActivity.this.w.isPlaying() || VideoMuteActivity.this.w.getCurrentPosition() >= ((Integer) VideoMuteActivity.this.i.getSelectedMaxValue()).intValue()) {
                if (VideoMuteActivity.this.w.isPlaying()) {
                    VideoMuteActivity.this.w.pause();
                    VideoMuteActivity.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    VideoMuteActivity.this.w.seekTo(((Integer) VideoMuteActivity.this.i.getSelectedMinValue()).intValue());
                    VideoMuteActivity.this.j.setSelectedMinValue(VideoMuteActivity.this.i.getSelectedMinValue());
                    VideoMuteActivity.this.j.setVisibility(4);
                }
                if (!VideoMuteActivity.this.w.isPlaying()) {
                    VideoMuteActivity.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    VideoMuteActivity.this.j.setVisibility(4);
                    return;
                }
                return;
            }
            VideoMuteActivity.this.j.setVisibility(0);
            postDelayed(this.c, 50);
        }
    }

    public void intentToPreviewActivity() {
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.videocompressactivity);


        LinearLayout s = (LinearLayout) findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(VideoMuteActivity.this, s);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Mute");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (u || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(u);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.s = false;
            this.a = u;
            copyCreate();
            context = this;
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
        intent.putExtra("song", this.y);
        startActivity(intent);
        finish();
    }

    @SuppressLint("InvalidWakeLockTag")
    public void copyCreate() {
        this.isInFront = u;
        this.LIST_COLUMN_SIZE = e() / 100;
        this.totalVideoDuration = 0;
        this.t = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(6, "VideoMerge");
        if (!this.t.isHeld()) {
            this.t.acquire();
        }
        this.x = getIntent().getStringExtra("videouri");
        videoPath = this.x;
        this.r = (TextView) findViewById(R.id.Filename);
        this.w = (VideoView) findViewById(R.id.addcutsvideoview);
        this.videoPlayBtn = (ImageView) findViewById(R.id.videoplaybtn);
        this.c = (RelativeLayout) findViewById(R.id.btnlow);
        this.b = (RelativeLayout) findViewById(R.id.btnmedium);
        this.d = (RelativeLayout) findViewById(R.id.btnhigh);
        this.f = (RelativeLayout) findViewById(R.id.back_low);
        this.e = (RelativeLayout) findViewById(R.id.back_medium);
        this.g = (RelativeLayout) findViewById(R.id.back_high);
        this.o = (TextView) findViewById(R.id.textformatValue);
        this.n = (TextView) findViewById(R.id.textsizeValue);
        this.q = (TextView) findViewById(R.id.textCompressPercentage);
        this.p = (TextView) findViewById(R.id.textcompressSize);
        this.k = (TextView) findViewById(R.id.left_pointer);
        this.l = (TextView) findViewById(R.id.mid_pointer);
        this.m = (TextView) findViewById(R.id.right_pointer);
        this.r.setText(new File(videoPath).getName());
        a(videoPath);
        if (this.type == 0) {
            a(7);
        }
        runOnUiThread(new Runnable() {
            public void run() {
                VideoMuteActivity.this.prgDialog = ProgressDialog.show(VideoMuteActivity.this, "", "Loading...", VideoMuteActivity.u);
            }
        });
        VideoSeekBar();
        this.c.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoMuteActivity.this.quality = 0;
                VideoMuteActivity.this.a(6);
            }
        });
        this.b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoMuteActivity.this.quality = 1;
                VideoMuteActivity.this.a(7);
            }
        });
        this.d.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoMuteActivity.this.quality = 2;
                VideoMuteActivity.this.a(8);
            }
        });
    }

    public void executeCompressCommand() {
        String[] strArr;
        String[] strArr2 = new String[0];
        String format = new SimpleDateFormat("_HHmmss", Locale.US).format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.VideoMute));
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb2.append("/");
        sb2.append(getResources().getString(R.string.MainFolderName));
        sb2.append("/");
        sb2.append(getResources().getString(R.string.VideoMute));
        sb2.append("/videomute");
        sb2.append(format);
        sb2.append(".mp4");
        this.y = sb2.toString();
        if (this.quality == 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("");
            sb3.append(this.seekstart);
            StringBuilder sb4 = new StringBuilder();
            sb4.append("");
            sb4.append(this.seekduration);
            strArr = new String[]{"-ss", sb3.toString(), "-y", "-i", videoPath, "-t", sb4.toString(), "-vcodec", "mpeg4", "-b:v", "2097152", "-b:a", "48000", "-ac", "2", "-ar", "22050", "-c", "copy", "-an", this.y};
        } else if (this.quality == 2) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("");
            sb5.append(this.seekstart);
            StringBuilder sb6 = new StringBuilder();
            sb6.append("");
            sb6.append(this.seekduration);
            strArr = new String[]{"-ss", sb5.toString(), "-y", "-i", videoPath, "-t", sb6.toString(), "-vcodec", "mpeg4", "-b:v", "2097152", "-b:a", "48000", "-ac", "2", "-ar", "22050", "-c", "copy", "-an", this.y};
        } else {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("");
            sb7.append(this.seekstart);
            StringBuilder sb8 = new StringBuilder();
            sb8.append("");
            sb8.append(this.seekduration);
            strArr = new String[]{"-ss", sb7.toString(), "-y", "-i", videoPath, "-t", sb8.toString(), "-vcodec", "mpeg4", "-b:v", "2097152", "-b:a", "48000", "-ac", "2", "-ar", "22050", "-c", "copy", "-an", this.y};
        }
        a(strArr, this.y);
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
                    intent.setData(Uri.fromFile(new File(VideoMuteActivity.this.y)));
                    VideoMuteActivity.this.sendBroadcast(intent);
                    VideoMuteActivity.this.b();
                    VideoMuteActivity.this.refreshGallery(str);

                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoMuteActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoMuteActivity.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                } else {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoMuteActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoMuteActivity.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }


            }
        });
        getWindow().clearFlags(16);
    }


    public void a(int i2) {
        this.type = i2;
        selectedButton();
        TextView textView = this.p;
        StringBuilder sb = new StringBuilder();
        sb.append(StaticMethods.ExpectedOutputSize(videoPath, timeInSecond(this.mintime, this.maxtime), this.type));
        sb.append("");
        textView.setText(sb.toString());
        TextView textView2 = this.q;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("-");
        sb2.append(StaticMethods.SelectedCompressPercentage(videoPath, timeInSecond(this.mintime, this.maxtime), this.type));
        sb2.append("%");
        textView2.setText(sb2.toString());
    }

    private void a(String str) {
        TextView textView = this.n;
        StringBuilder sb = new StringBuilder();
        sb.append("Size :- ");
        sb.append(StaticMethods.sizeInMBbyFilepath(str));
        textView.setText(sb.toString());
        TextView textView2 = this.o;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Format :- ");
        sb2.append(StaticMethods.FormatofVideo(str));
        textView2.setText(sb2.toString());
    }

    public int timeInSecond(int i2, int i3) {
        return (i3 - i2) / 1000;
    }

    public void VideoSeekBar() {
        this.w.setVideoURI(Uri.parse(videoPath));
        this.w.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (VideoMuteActivity.this.a) {
                    VideoMuteActivity.this.mintime = 0;
                    VideoMuteActivity.this.maxtime = mediaPlayer.getDuration();
                    VideoMuteActivity.this.MP_DURATION = mediaPlayer.getDuration();
                    VideoMuteActivity.this.seekLayout(0, VideoMuteActivity.this.MP_DURATION);
                    VideoMuteActivity.this.w.start();
                    VideoMuteActivity.this.w.pause();
                    VideoMuteActivity.this.w.seekTo(300);
                    return;
                }
                VideoMuteActivity.this.seekLayout(VideoMuteActivity.this.mintime, VideoMuteActivity.this.maxtime);
                VideoMuteActivity.this.w.start();
                VideoMuteActivity.this.w.pause();
                VideoMuteActivity.this.w.seekTo(VideoMuteActivity.this.mintime);
            }
        });
        this.w.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                VideoMuteActivity.this.prgDialog.dismiss();
                return false;
            }
        });
        this.videoPlayBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoMuteActivity.this.d();
            }
        });
    }


    public void d() {
        if (this.w.isPlaying()) {
            this.w.pause();
            this.w.seekTo(((Integer) this.i.getSelectedMinValue()).intValue());
            this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
            this.j.setVisibility(4);
            return;
        }
        this.w.seekTo(((Integer) this.i.getSelectedMinValue()).intValue());
        this.w.start();
        this.j.setSelectedMaxValue(Integer.valueOf(this.w.getCurrentPosition()));
        this.v.a();
        this.videoPlayBtn.setBackgroundResource(R.drawable.pause2);
        this.j.setVisibility(0);
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroy() {
        this.totalVideoDuration = 0;
        if (this.t.isHeld()) {
            this.t.release();
        }
        super.onDestroy();
    }

    public void seekLayout(int i2, int i3) {
        TextView textView = this.k;
        StringBuilder sb = new StringBuilder();
        sb.append(getTimeForTrackFormat(i2));
        sb.append("");
        textView.setText(sb.toString());
        TextView textView2 = this.m;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getTimeForTrackFormat(i3));
        sb2.append("");
        textView2.setText(sb2.toString());
        TextView textView3 = this.l;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getTimeForTrackFormat(i3 - i2));
        sb3.append("");
        textView3.setText(sb3.toString());
        if (this.type != 9) {
            TextView textView4 = this.p;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(StaticMethods.ExpectedOutputSize(videoPath, timeInSecond(i2, i3), this.type));
            sb4.append("");
            textView4.setText(sb4.toString());
            TextView textView5 = this.q;
            StringBuilder sb5 = new StringBuilder();
            sb5.append("-");
            sb5.append(StaticMethods.SelectedCompressPercentage(videoPath, timeInSecond(i2, i3), this.type));
            sb5.append("%");
            textView5.setText(sb5.toString());
        }
        if (this.h != null) {
            this.h.removeAllViews();
            this.h = null;
            this.i = null;
            this.j = null;
        }
        this.h = (ViewGroup) findViewById(R.id.seekLayout);
        this.i = new RangeSeekBar<>(Integer.valueOf(0), Integer.valueOf(this.MP_DURATION), this);
        this.j = new RangePlaySeekBar<>(Integer.valueOf(0), Integer.valueOf(this.MP_DURATION), this);
        this.i.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> rangeSeekBar, Integer num, Integer num2, boolean z) {
                if (VideoMuteActivity.this.w.isPlaying()) {
                    VideoMuteActivity.this.w.pause();
                    VideoMuteActivity.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                }
                if (VideoMuteActivity.this.maxtime == num2.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num = Integer.valueOf(num2.intValue() + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
                    }
                    VideoMuteActivity.this.w.seekTo(num.intValue());
                } else if (VideoMuteActivity.this.mintime == num.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num2 = Integer.valueOf(num.intValue() + 1000);
                    }
                    VideoMuteActivity.this.w.seekTo(num.intValue());
                }
                VideoMuteActivity.this.i.setSelectedMaxValue(num2);
                VideoMuteActivity.this.i.setSelectedMinValue(num);
                VideoMuteActivity.this.k.setText(VideoMuteActivity.getTimeForTrackFormat(num.intValue()));
                VideoMuteActivity.this.m.setText(VideoMuteActivity.getTimeForTrackFormat(num2.intValue()));
                VideoMuteActivity.this.l.setText(VideoMuteActivity.getTimeForTrackFormat(num2.intValue() - num.intValue()));
                if (VideoMuteActivity.this.type != 9) {
                    TextView textView = VideoMuteActivity.this.p;
                    StringBuilder sb = new StringBuilder();
                    sb.append(StaticMethods.ExpectedOutputSize(VideoMuteActivity.videoPath, VideoMuteActivity.this.timeInSecond(num.intValue(), num2.intValue()), VideoMuteActivity.this.type));
                    sb.append("");
                    textView.setText(sb.toString());
                    TextView textView2 = VideoMuteActivity.this.q;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("-");
                    sb2.append(StaticMethods.SelectedCompressPercentage(VideoMuteActivity.videoPath, VideoMuteActivity.this.timeInSecond(num.intValue(), VideoMuteActivity.this.maxtime), VideoMuteActivity.this.type));
                    sb2.append("%");
                    textView2.setText(sb2.toString());
                }
                VideoMuteActivity.this.j.setSelectedMinValue(num);
                VideoMuteActivity.this.j.setSelectedMaxValue(num2);
                VideoMuteActivity.this.mintime = num.intValue();
                VideoMuteActivity.this.maxtime = num2.intValue();
            }
        });
        this.h.addView(this.i);
        this.h.addView(this.j);
        this.i.setSelectedMinValue(Integer.valueOf(i2));
        this.i.setSelectedMaxValue(Integer.valueOf(i3));
        this.j.setSelectedMinValue(Integer.valueOf(i2));
        this.j.setSelectedMaxValue(Integer.valueOf(i3));
        this.j.setEnabled(false);
        this.j.setVisibility(4);
        this.prgDialog.dismiss();
    }

    private int e() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.heightPixels;
        int i3 = displayMetrics.widthPixels;
        return i3 <= i2 ? i3 : i2;
    }

    public static String getTimeForTrackFormat(int i2) {
        long j2 = (long) i2;
        return String.format("%02d:%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toHours(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(j2))), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2)))});
    }


    @Override
    public void onStart() {
        super.onStart();
        if (this.CompleteNotificationCreated) {
            intentToPreviewActivity();
            ((NotificationManager) context.getSystemService("notification")).cancelAll();
        }
        this.CompleteNotificationCreated = false;
    }

    public void onPause() {
        super.onPause();
        this.a = false;
        try {
            if (this.w.isPlaying()) {
                this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                this.w.pause();
            }
        } catch (Exception unused) {
        }
        this.isInFront = false;
        if (this.j != null && this.j.getVisibility() == 0) {
            this.j.setVisibility(4);
        }
    }


    public void onRestart() {
        super.onRestart();
    }

    public boolean comparesize(long j2, int i2) {
        if (j2 > (((long) i2) * (Long.parseLong(StaticMethods.bitRate(videoPath)) / 1024)) / PlaybackStateCompat.ACTION_PLAY_FROM_URI) {
            return u;
        }
        return false;
    }

    public void selectedButton() {
        if (this.type == 6) {
            this.f.setVisibility(0);
            this.e.setVisibility(8);
            this.g.setVisibility(8);
        }
        if (this.type == 7) {
            this.f.setVisibility(8);
            this.e.setVisibility(0);
            this.g.setVisibility(8);
        }
        if (this.type == 8) {
            this.f.setVisibility(8);
            this.e.setVisibility(8);
            this.g.setVisibility(0);
        }
        if (this.type == 9) {
            this.f.setVisibility(8);
            this.e.setVisibility(8);
            this.g.setVisibility(8);
        }
        if (this.type == 0) {
            this.f.setVisibility(8);
            this.e.setVisibility(8);
            this.g.setVisibility(8);
        }
    }


    public void g() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VideoMuteActivity.this.finish();
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
        this.isInFront = u;
        this.s = false;
        this.x = getIntent().getStringExtra("videouri");
        videoPath = this.x;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return u;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            Intent intent = new Intent(this, StartActivity.class);
            intent.setFlags(67108864);
            startActivity(intent);
            finish();
            return u;
        }
        if (menuItem.getItemId() == R.id.Done) {
            try {
                if (this.w.isPlaying()) {
                    this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    this.w.pause();
                }
            } catch (Exception unused) {
            }
            this.seekstart = ((Integer) this.i.getSelectedMinValue()).intValue() / 1000;
            this.seekend = ((Integer) this.i.getSelectedMaxValue()).intValue() / 1000;
            this.seekduration = this.seekend - this.seekstart;
            executeCompressCommand();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
