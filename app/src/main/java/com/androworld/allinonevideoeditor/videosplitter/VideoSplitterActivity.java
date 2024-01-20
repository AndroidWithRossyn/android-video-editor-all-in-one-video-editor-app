package com.androworld.allinonevideoeditor.videosplitter;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;

import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.RangePlaySeekBar;
import com.androworld.allinonevideoeditor.RangeSeekBar;
import com.androworld.allinonevideoeditor.RangeSeekBar.OnRangeSeekBarChangeListener;
import com.androworld.allinonevideoeditor.StaticMethods;
import com.androworld.allinonevideoeditor.UtilCommand;
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
public class VideoSplitterActivity extends AppCompatActivity {
    public static VideoSplitterActivity context = null;
    static final boolean r = true;
    public static String videoPath;
    public boolean CompleteNotificationCreated = false;
    public int LIST_COLUMN_SIZE = 4;
    public int MP_DURATION;
    boolean a = r;
    ViewGroup b;
    RangeSeekBar<Integer> c;
    RangePlaySeekBar<Integer> d;
    TextView e;
    TextView f;
    TextView g;
    TextView h;
    TextView i;
    public boolean isInFront = r;
    TextView j;
    TextView k;
    TextView l;
    boolean m = false;
    public int maxtime;
    public int mintime;
    WakeLock n;
    SplitBaseAdapter o;
    ArrayList<String> p;
    public ProgressDialog prgDialog;
    Spinner q;
    private StateObserver s = new StateObserver();
    public int seekduration;
    public int seekend;
    public int seekstart;

    public VideoView t;
    public int totalVideoDuration = 0;
    public int type = 0;
    private String u;
    private String v;
    public ImageView videoPlayBtn;
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
                this.b = VideoSplitterActivity.r;
                sendEmptyMessage(0);
            }
        }

        @Override
        public void handleMessage(Message message) {
            this.b = false;
            VideoSplitterActivity.this.d.setSelectedMaxValue(Integer.valueOf(VideoSplitterActivity.this.t.getCurrentPosition()));
            if (!VideoSplitterActivity.this.t.isPlaying() || VideoSplitterActivity.this.t.getCurrentPosition() >= ((Integer) VideoSplitterActivity.this.c.getSelectedMaxValue()).intValue()) {
                if (VideoSplitterActivity.this.t.isPlaying()) {
                    VideoSplitterActivity.this.t.pause();
                    VideoSplitterActivity.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    VideoSplitterActivity.this.t.seekTo(((Integer) VideoSplitterActivity.this.c.getSelectedMinValue()).intValue());
                    VideoSplitterActivity.this.d.setSelectedMinValue(VideoSplitterActivity.this.c.getSelectedMinValue());
                    VideoSplitterActivity.this.d.setVisibility(4);
                }
                if (!VideoSplitterActivity.this.t.isPlaying()) {
                    VideoSplitterActivity.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    VideoSplitterActivity.this.d.setVisibility(4);
                    return;
                }
                return;
            }
            VideoSplitterActivity.this.d.setVisibility(0);
            postDelayed(this.c, 50);
        }
    }

    public void intentToPreviewActivity() {
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.videosplitteractivity);

        LinearLayout sa = (LinearLayout) findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(VideoSplitterActivity.this, sa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Splitter");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (r || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(r);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.m = false;
            this.a = r;
            copyCreate();
            context = this;
            return;
        }
        throw new AssertionError();
    }

    @SuppressLint("InvalidWakeLockTag")
    public void copyCreate() {
        this.p = new ArrayList<>();
        this.p.add("10 Sec");
        this.p.add("20 Sec");
        this.p.add("30 Sec");
        this.p.add("40 Sec");
        this.p.add("50 Sec");
        this.p.add("60 Sec");
        this.isInFront = r;
        this.LIST_COLUMN_SIZE = b() / 100;
        this.totalVideoDuration = 0;
        this.n = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(6, "VideoMerge");
        if (!this.n.isHeld()) {
            this.n.acquire();
        }
        this.u = getIntent().getStringExtra("videouri");
        videoPath = this.u;
        this.l = (TextView) findViewById(R.id.Filename);
        this.t = (VideoView) findViewById(R.id.addcutsvideoview);
        this.videoPlayBtn = (ImageView) findViewById(R.id.videoplaybtn);
        this.i = (TextView) findViewById(R.id.textformatValue);
        this.h = (TextView) findViewById(R.id.textsizeValue);
        this.k = (TextView) findViewById(R.id.textCompressPercentage);
        this.j = (TextView) findViewById(R.id.textcompressSize);
        this.e = (TextView) findViewById(R.id.left_pointer);
        this.f = (TextView) findViewById(R.id.mid_pointer);
        this.g = (TextView) findViewById(R.id.right_pointer);
        this.l.setText(new File(videoPath).getName());
        a(videoPath);
        a(2);
        runOnUiThread(new Runnable() {
            public void run() {
                VideoSplitterActivity.this.prgDialog = ProgressDialog.show(VideoSplitterActivity.this, "", "Loading...", VideoSplitterActivity.r);
            }
        });
        VideoSeekBar();
        this.q = (Spinner) findViewById(R.id.sp_convert);
        this.o = new SplitBaseAdapter(this, this.p, 0);
        this.q.setAdapter(this.o);
        this.q.setSelection(0);
        this.q.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (adapterView.getItemAtPosition(i).toString() == "10 Sec") {
                    VideoSplitterActivity.this.a(1);
                } else if (adapterView.getItemAtPosition(i).toString() == "20 Sec") {
                    VideoSplitterActivity.this.a(2);
                } else if (adapterView.getItemAtPosition(i).toString() == "30 Sec") {
                    VideoSplitterActivity.this.a(3);
                } else if (adapterView.getItemAtPosition(i).toString() == "40 Sec") {
                    VideoSplitterActivity.this.a(4);
                } else if (adapterView.getItemAtPosition(i).toString() == "50 Sec") {
                    VideoSplitterActivity.this.a(5);
                } else if (adapterView.getItemAtPosition(i).toString() == "60 Sec") {
                    VideoSplitterActivity.this.a(6);
                }
            }
        });
    }

    public void executeCompressCommand() {
        String[] strArr = new String[0];
        this.seekstart = ((Integer) this.c.getSelectedMinValue()).intValue() / 1000;
        this.seekend = ((Integer) this.c.getSelectedMaxValue()).intValue() / 1000;
        this.seekduration = this.seekend - this.seekstart;
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.VideoSplitter));
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        String name = new File(videoPath).getName();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb2.append("/");
        sb2.append(getResources().getString(R.string.MainFolderName));
        sb2.append("/");
        sb2.append(getResources().getString(R.string.VideoSplitter));
        sb2.append("/");
        sb2.append(name.substring(0, name.lastIndexOf(".")));
        sb2.append("-part%2d");
        sb2.append(videoPath.substring(videoPath.lastIndexOf(".")));
        this.v = sb2.toString();
        if (this.p.get(this.q.getSelectedItemPosition()) == "10 Sec") {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("");
            sb3.append(this.seekstart);
            StringBuilder sb4 = new StringBuilder();
            sb4.append("");
            sb4.append(this.seekduration);
            strArr = new String[]{"-y", "-ss", sb3.toString(), "-t", sb4.toString(), "-i", videoPath, "-acodec", "copy", "-f", "segment", "-segment_time", "10", "-vcodec", "copy", "-reset_timestamps", "1", "-map", "0", "-preset", "ultrafast", this.v};
        } else if (this.p.get(this.q.getSelectedItemPosition()) == "20 Sec") {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("");
            sb5.append(this.seekstart);
            StringBuilder sb6 = new StringBuilder();
            sb6.append("");
            sb6.append(this.seekduration);
            strArr = new String[]{"-y", "-ss", sb5.toString(), "-t", sb6.toString(), "-i", videoPath, "-acodec", "copy", "-f", "segment", "-segment_time", "20", "-vcodec", "copy", "-reset_timestamps", "1", "-map", "0", "-preset", "ultrafast", this.v};
        } else if (this.p.get(this.q.getSelectedItemPosition()) == "30 Sec") {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("");
            sb7.append(this.seekstart);
            StringBuilder sb8 = new StringBuilder();
            sb8.append("");
            sb8.append(this.seekduration);
            strArr = new String[]{"-y", "-ss", sb7.toString(), "-t", sb8.toString(), "-i", videoPath, "-acodec", "copy", "-f", "segment", "-segment_time", "30", "-vcodec", "copy", "-reset_timestamps", "1", "-map", "0", "-preset", "ultrafast", this.v};
        } else if (this.p.get(this.q.getSelectedItemPosition()) == "40 Sec") {
            StringBuilder sb9 = new StringBuilder();
            sb9.append("");
            sb9.append(this.seekstart);
            StringBuilder sb10 = new StringBuilder();
            sb10.append("");
            sb10.append(this.seekduration);
            strArr = new String[]{"-y", "-ss", sb9.toString(), "-t", sb10.toString(), "-i", videoPath, "-acodec", "copy", "-f", "segment", "-segment_time", "40", "-vcodec", "copy", "-reset_timestamps", "1", "-map", "0", "-preset", "ultrafast", this.v};
        } else if (this.p.get(this.q.getSelectedItemPosition()) == "50 Sec") {
            StringBuilder sb11 = new StringBuilder();
            sb11.append("");
            sb11.append(this.seekstart);
            StringBuilder sb12 = new StringBuilder();
            sb12.append("");
            sb12.append(this.seekduration);
            strArr = new String[]{"-y", "-ss", sb11.toString(), "-t", sb12.toString(), "-i", videoPath, "-acodec", "copy", "-f", "segment", "-segment_time", "50", "-vcodec", "copy", "-reset_timestamps", "1", "-map", "0", "-preset", "ultrafast", this.v};
        } else if (this.p.get(this.q.getSelectedItemPosition()) == "60 Sec") {
            StringBuilder sb13 = new StringBuilder();
            sb13.append("");
            sb13.append(this.seekstart);
            StringBuilder sb14 = new StringBuilder();
            sb14.append("");
            sb14.append(this.seekduration);
            strArr = new String[]{"-y", "-ss", sb13.toString(), "-t", sb14.toString(), "-i", videoPath, "-acodec", "copy", "-f", "segment", "-segment_time", "60", "-vcodec", "copy", "-reset_timestamps", "1", "-map", "0", "-preset", "ultrafast", this.v};
        }
        a(strArr, this.v);
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
                    StringBuilder sb = new StringBuilder();
                    sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                    sb.append("/");
                    sb.append(VideoSplitterActivity.this.getResources().getString(R.string.MainFolderName));
                    sb.append("/");
                    sb.append(VideoSplitterActivity.this.getResources().getString(R.string.VideoSplitter));
                    sb.append("/");
                    File[] listFiles = new File(sb.toString()).listFiles();
                    for (File file : listFiles) {
                        if (file.isFile()) {
                            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                            intent.setData(Uri.fromFile(file));
                            VideoSplitterActivity.this.getApplicationContext().sendBroadcast(intent);
                        }
                    }
                    VideoSplitterActivity.this.refreshGallery(str);

                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoSplitterActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoSplitterActivity.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                } else {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoSplitterActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoSplitterActivity.this, "Error Creating Video", 0).show();
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
        TextView textView = this.j;
        StringBuilder sb = new StringBuilder();
        sb.append(StaticMethods.ExpectedOutputSize(videoPath, timeInSecond(this.mintime, this.maxtime), this.type));
        sb.append("");
        textView.setText(sb.toString());
        TextView textView2 = this.k;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("-");
        sb2.append(StaticMethods.SelectedCompressPercentage(videoPath, timeInSecond(this.mintime, this.maxtime), this.type));
        sb2.append("%");
        textView2.setText(sb2.toString());
    }

    private void a(String str) {
        TextView textView = this.h;
        StringBuilder sb = new StringBuilder();
        sb.append("Size :- ");
        sb.append(StaticMethods.sizeInMBbyFilepath(str));
        textView.setText(sb.toString());
        TextView textView2 = this.i;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Format :- ");
        sb2.append(StaticMethods.FormatofVideo(str));
        textView2.setText(sb2.toString());
    }

    public int timeInSecond(int i2, int i3) {
        return (i3 - i2) / 1000;
    }

    public void VideoSeekBar() {
        this.t.setVideoURI(Uri.parse(videoPath));
        this.t.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (VideoSplitterActivity.this.a) {
                    VideoSplitterActivity.this.mintime = 0;
                    VideoSplitterActivity.this.maxtime = mediaPlayer.getDuration();
                    VideoSplitterActivity.this.MP_DURATION = mediaPlayer.getDuration();
                    VideoSplitterActivity.this.seekLayout(0, VideoSplitterActivity.this.MP_DURATION);
                    VideoSplitterActivity.this.t.start();
                    VideoSplitterActivity.this.t.pause();
                    VideoSplitterActivity.this.t.seekTo(300);
                    return;
                }
                VideoSplitterActivity.this.seekLayout(VideoSplitterActivity.this.mintime, VideoSplitterActivity.this.maxtime);
                VideoSplitterActivity.this.t.start();
                VideoSplitterActivity.this.t.pause();
                VideoSplitterActivity.this.t.seekTo(VideoSplitterActivity.this.mintime);
            }
        });
        this.t.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                VideoSplitterActivity.this.prgDialog.dismiss();
                return false;
            }
        });
        this.videoPlayBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoSplitterActivity.this.a();
            }
        });
    }


    public void a() {
        if (this.t.isPlaying()) {
            this.t.pause();
            this.t.seekTo(((Integer) this.c.getSelectedMinValue()).intValue());
            this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
            this.d.setVisibility(4);
            return;
        }
        this.t.seekTo(((Integer) this.c.getSelectedMinValue()).intValue());
        this.t.start();
        this.d.setSelectedMaxValue(Integer.valueOf(this.t.getCurrentPosition()));
        this.s.a();
        this.videoPlayBtn.setBackgroundResource(R.drawable.pause2);
        this.d.setVisibility(0);
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroy() {
        this.totalVideoDuration = 0;
        if (this.n.isHeld()) {
            this.n.release();
        }
        super.onDestroy();
    }

    public void seekLayout(int i2, int i3) {
        TextView textView = this.e;
        StringBuilder sb = new StringBuilder();
        sb.append(getTimeForTrackFormat(i2));
        sb.append("");
        textView.setText(sb.toString());
        TextView textView2 = this.g;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getTimeForTrackFormat(i3));
        sb2.append("");
        textView2.setText(sb2.toString());
        TextView textView3 = this.f;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getTimeForTrackFormat(i3 - i2));
        sb3.append("");
        textView3.setText(sb3.toString());
        if (this.type != 9) {
            TextView textView4 = this.j;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(StaticMethods.ExpectedOutputSize(videoPath, timeInSecond(i2, i3), this.type));
            sb4.append("");
            textView4.setText(sb4.toString());
            TextView textView5 = this.k;
            StringBuilder sb5 = new StringBuilder();
            sb5.append("-");
            sb5.append(StaticMethods.SelectedCompressPercentage(videoPath, timeInSecond(i2, i3), this.type));
            sb5.append("%");
            textView5.setText(sb5.toString());
        }
        if (this.b != null) {
            this.b.removeAllViews();
            this.b = null;
            this.c = null;
            this.d = null;
        }
        this.b = (ViewGroup) findViewById(R.id.seekLayout);
        this.c = new RangeSeekBar<>(Integer.valueOf(0), Integer.valueOf(this.MP_DURATION), this);
        this.d = new RangePlaySeekBar<>(Integer.valueOf(0), Integer.valueOf(this.MP_DURATION), this);
        this.c.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> rangeSeekBar, Integer num, Integer num2, boolean z) {
                if (VideoSplitterActivity.this.t.isPlaying()) {
                    VideoSplitterActivity.this.t.pause();
                    VideoSplitterActivity.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                }
                if (VideoSplitterActivity.this.maxtime == num2.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num = Integer.valueOf(num2.intValue() + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
                    }
                    VideoSplitterActivity.this.t.seekTo(num.intValue());
                } else if (VideoSplitterActivity.this.mintime == num.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num2 = Integer.valueOf(num.intValue() + 1000);
                    }
                    VideoSplitterActivity.this.t.seekTo(num.intValue());
                }
                VideoSplitterActivity.this.c.setSelectedMaxValue(num2);
                VideoSplitterActivity.this.c.setSelectedMinValue(num);
                VideoSplitterActivity.this.e.setText(VideoSplitterActivity.getTimeForTrackFormat(num.intValue()));
                VideoSplitterActivity.this.g.setText(VideoSplitterActivity.getTimeForTrackFormat(num2.intValue()));
                VideoSplitterActivity.this.f.setText(VideoSplitterActivity.getTimeForTrackFormat(num2.intValue() - num.intValue()));
                if (VideoSplitterActivity.this.type != 9) {
                    TextView textView = VideoSplitterActivity.this.j;
                    StringBuilder sb = new StringBuilder();
                    sb.append(StaticMethods.ExpectedOutputSize(VideoSplitterActivity.videoPath, VideoSplitterActivity.this.timeInSecond(num.intValue(), num2.intValue()), VideoSplitterActivity.this.type));
                    sb.append("");
                    textView.setText(sb.toString());
                    TextView textView2 = VideoSplitterActivity.this.k;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("-");
                    sb2.append(StaticMethods.SelectedCompressPercentage(VideoSplitterActivity.videoPath, VideoSplitterActivity.this.timeInSecond(num.intValue(), VideoSplitterActivity.this.maxtime), VideoSplitterActivity.this.type));
                    sb2.append("%");
                    textView2.setText(sb2.toString());
                }
                VideoSplitterActivity.this.d.setSelectedMinValue(num);
                VideoSplitterActivity.this.d.setSelectedMaxValue(num2);
                VideoSplitterActivity.this.mintime = num.intValue();
                VideoSplitterActivity.this.maxtime = num2.intValue();
            }
        });
        this.b.addView(this.c);
        this.b.addView(this.d);
        this.c.setSelectedMinValue(Integer.valueOf(i2));
        this.c.setSelectedMaxValue(Integer.valueOf(i3));
        this.d.setSelectedMinValue(Integer.valueOf(i2));
        this.d.setSelectedMaxValue(Integer.valueOf(i3));
        this.d.setEnabled(false);
        this.d.setVisibility(4);
        this.prgDialog.dismiss();
    }

    private int b() {
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
            if (this.t.isPlaying()) {
                this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                this.t.pause();
            }
        } catch (Exception unused) {
        }
        this.isInFront = false;
        if (this.d != null && this.d.getVisibility() == 0) {
            this.d.setVisibility(4);
        }
    }


    public void onRestart() {
        super.onRestart();
    }

    public boolean comparesize(long j2, int i2) {
        if (j2 > (((long) i2) * (Long.parseLong(StaticMethods.bitRate(videoPath)) / 1024)) / PlaybackStateCompat.ACTION_PLAY_FROM_URI) {
            return r;
        }
        return false;
    }


    public void d() {
        new Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VideoSplitterActivity.this.finish();
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
        this.isInFront = r;
        this.m = false;
        this.u = getIntent().getStringExtra("videouri");
        videoPath = this.u;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ListVideoAndMyAlbumActivity.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return r;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return r;
        }
        if (menuItem.getItemId() == R.id.Done) {
            try {
                if (this.t.isPlaying()) {
                    this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    this.t.pause();
                }
            } catch (Exception unused) {
            }
            executeCompressCommand();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
