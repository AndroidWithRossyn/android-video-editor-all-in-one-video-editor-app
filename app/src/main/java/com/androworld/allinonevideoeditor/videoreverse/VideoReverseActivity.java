package com.androworld.allinonevideoeditor.videoreverse;

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
import com.androworld.allinonevideoeditor.StaticMethods;
import com.androworld.allinonevideoeditor.UtilCommand;
import com.androworld.allinonevideoeditor.VideoPlayer;
import com.androworld.allinonevideoeditor.listvideoandmyvideo.ListVideoAndMyAlbumActivity;
import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;


import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

@SuppressLint({"WrongConstant", "ResourceType"})
public class VideoReverseActivity extends AppCompatActivity {
    static final boolean A = true;
    public static VideoReverseActivity context;
    public static String videoPath;
    private StateObserver B = new StateObserver();

    public VideoView C;
    public boolean CompleteNotificationCreated = false;

    public String D;
    private String E;
    public int MP_DURATION;
    boolean a = A;
    ViewGroup b;
    RangeSeekBar<Integer> c;
    RangePlaySeekBar<Integer> d;
    WakeLock e;
    int f = 0;
    int g = 4;
    int h;
    int i;
    public boolean isInFront = A;
    int j;
    int k;
    int l;
    int m = 0;
    int n;
    RelativeLayout o;
    RelativeLayout p;
    public ProgressDialog prgDialog;
    RelativeLayout q;
    RelativeLayout r;
    TextView s;
    TextView t;
    TextView u;
    TextView v;
    public ImageView videoPlayBtn;
    TextView w;
    TextView x;
    TextView y;
    TextView z;
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
                this.b = VideoReverseActivity.A;
                sendEmptyMessage(0);
            }
        }

        @Override
        public void handleMessage(Message message) {
            this.b = false;
            VideoReverseActivity.this.d.setSelectedMaxValue(Integer.valueOf(VideoReverseActivity.this.C.getCurrentPosition()));
            if (!VideoReverseActivity.this.C.isPlaying() || VideoReverseActivity.this.C.getCurrentPosition() >= ((Integer) VideoReverseActivity.this.c.getSelectedMaxValue()).intValue()) {
                if (VideoReverseActivity.this.C.isPlaying()) {
                    VideoReverseActivity.this.C.pause();
                    VideoReverseActivity.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    VideoReverseActivity.this.C.seekTo(((Integer) VideoReverseActivity.this.c.getSelectedMinValue()).intValue());
                    VideoReverseActivity.this.d.setSelectedMinValue(VideoReverseActivity.this.c.getSelectedMinValue());
                    VideoReverseActivity.this.d.setVisibility(4);
                }
                if (!VideoReverseActivity.this.C.isPlaying()) {
                    VideoReverseActivity.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    VideoReverseActivity.this.d.setVisibility(4);
                    return;
                }
                return;
            }
            VideoReverseActivity.this.d.setVisibility(0);
            postDelayed(this.c, 50);
        }
    }

    public void intentToPreviewActivity() {
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.videoreverseactivity);

        LinearLayout sa = (LinearLayout) findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(VideoReverseActivity.this, sa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Reverse");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (A || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(A);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.a = A;
            FindId();
            context = this;
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

    @SuppressLint("InvalidWakeLockTag")
    public void FindId() {
        this.isInFront = A;
        this.g = e() / 100;
        this.f = 0;
        this.e = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(6, "VideoMerge");
        if (!this.e.isHeld()) {
            this.e.acquire();
        }
        this.E = getIntent().getStringExtra("videouri");
        videoPath = this.E;
        this.z = (TextView) findViewById(R.id.Filename);
        this.z.setText(new File(videoPath).getName());
        this.o = (RelativeLayout) findViewById(R.id.imgbtn_Original);
        this.p = (RelativeLayout) findViewById(R.id.imgbtn_Reversed);
        this.q = (RelativeLayout) findViewById(R.id.back_Original);
        this.r = (RelativeLayout) findViewById(R.id.back_Reversed);
        this.w = (TextView) findViewById(R.id.textformatValue);
        this.v = (TextView) findViewById(R.id.textsizeValue);
        this.y = (TextView) findViewById(R.id.textCompressPercentage);
        this.x = (TextView) findViewById(R.id.textcompressSize);
        this.C = (VideoView) findViewById(R.id.addcutsvideoview);
        this.videoPlayBtn = (ImageView) findViewById(R.id.videoplaybtn);
        this.s = (TextView) findViewById(R.id.left_pointer);
        this.t = (TextView) findViewById(R.id.mid_pointer);
        this.u = (TextView) findViewById(R.id.right_pointer);
        a(videoPath);
        if (this.m == 0) {
            a(7);
        }
        runOnUiThread(new Runnable() {
            public void run() {
                VideoReverseActivity.this.prgDialog = ProgressDialog.show(VideoReverseActivity.this, "", "Loading...", VideoReverseActivity.A);
            }
        });
        VideoSeekBar();
        this.o.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoReverseActivity.this.n = 0;
                VideoReverseActivity.this.a(6);
            }
        });
        this.p.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoReverseActivity.this.n = 1;
                VideoReverseActivity.this.a(7);
            }
        });
    }


    public void a(int i2) {
        this.m = i2;
        selectedButton();
        TextView textView = this.x;
        StringBuilder sb = new StringBuilder();
        sb.append(StaticMethods.ExpectedOutputSize(videoPath, timeInSecond(this.h, this.i), this.m));
        sb.append("");
        textView.setText(sb.toString());
        TextView textView2 = this.y;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("-");
        sb2.append(StaticMethods.SelectedCompressPercentage(videoPath, timeInSecond(this.h, this.i), this.m));
        sb2.append("%");
        textView2.setText(sb2.toString());
    }

    public void selectedButton() {
        if (this.m == 6) {
            this.q.setVisibility(0);
            this.r.setVisibility(8);
        }
        if (this.m == 7) {
            this.q.setVisibility(8);
            this.r.setVisibility(0);
        }
        if (this.m == 9) {
            this.q.setVisibility(8);
            this.r.setVisibility(8);
        }
        if (this.m == 0) {
            this.q.setVisibility(8);
            this.r.setVisibility(8);
        }
    }

    private void a(String str) {
        TextView textView = this.v;
        StringBuilder sb = new StringBuilder();
        sb.append("Size :- ");
        sb.append(StaticMethods.sizeInMBbyFilepath(str));
        textView.setText(sb.toString());
        TextView textView2 = this.w;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Format :- ");
        sb2.append(StaticMethods.FormatofVideo(str));
        textView2.setText(sb2.toString());
    }

    public int timeInSecond(int i2, int i3) {
        return (i3 - i2) / 1000;
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
                    intent.setData(Uri.fromFile(new File(VideoReverseActivity.this.D)));
                    VideoReverseActivity.this.sendBroadcast(intent);
                    VideoReverseActivity.this.b();
                    VideoReverseActivity.this.refreshGallery(str);

                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoReverseActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoReverseActivity.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                } else {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoReverseActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoReverseActivity.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }


            }
        });
        getWindow().clearFlags(16);
    }

    public void VideoSeekBar() {
        this.C.setVideoURI(Uri.parse(videoPath));
        this.C.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (VideoReverseActivity.this.a) {
                    VideoReverseActivity.this.h = 0;
                    VideoReverseActivity.this.i = mediaPlayer.getDuration();
                    VideoReverseActivity.this.MP_DURATION = mediaPlayer.getDuration();
                    VideoReverseActivity.this.seekLayout(0, VideoReverseActivity.this.MP_DURATION);
                    VideoReverseActivity.this.C.start();
                    VideoReverseActivity.this.C.pause();
                    VideoReverseActivity.this.C.seekTo(300);
                    return;
                }
                VideoReverseActivity.this.seekLayout(VideoReverseActivity.this.h, VideoReverseActivity.this.i);
                VideoReverseActivity.this.C.start();
                VideoReverseActivity.this.C.pause();
                VideoReverseActivity.this.C.seekTo(VideoReverseActivity.this.h);
            }
        });
        this.C.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                VideoReverseActivity.this.prgDialog.dismiss();
                return false;
            }
        });
        this.videoPlayBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoReverseActivity.this.d();
            }
        });
    }


    public void d() {
        if (this.C.isPlaying()) {
            this.C.pause();
            this.C.seekTo(((Integer) this.c.getSelectedMinValue()).intValue());
            this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
            this.d.setVisibility(4);
            return;
        }
        this.C.seekTo(((Integer) this.c.getSelectedMinValue()).intValue());
        this.C.start();
        this.d.setSelectedMaxValue(Integer.valueOf(this.C.getCurrentPosition()));
        this.B.a();
        this.videoPlayBtn.setBackgroundResource(R.drawable.pause2);
        this.d.setVisibility(0);
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroy() {
        this.f = 0;
        if (this.e.isHeld()) {
            this.e.release();
        }
        super.onDestroy();
    }

    public void seekLayout(int i2, int i3) {
        TextView textView = this.s;
        StringBuilder sb = new StringBuilder();
        sb.append(getTimeForTrackFormat(i2));
        sb.append("");
        textView.setText(sb.toString());
        TextView textView2 = this.u;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getTimeForTrackFormat(i3));
        sb2.append("");
        textView2.setText(sb2.toString());
        TextView textView3 = this.t;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getTimeForTrackFormat(i3 - i2));
        sb3.append("");
        textView3.setText(sb3.toString());
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
                if (VideoReverseActivity.this.C.isPlaying()) {
                    VideoReverseActivity.this.C.pause();
                    VideoReverseActivity.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                }
                if (VideoReverseActivity.this.i == num2.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num = Integer.valueOf(num2.intValue() + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
                    }
                    VideoReverseActivity.this.C.seekTo(num.intValue());
                } else if (VideoReverseActivity.this.h == num.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num2 = Integer.valueOf(num.intValue() + 1000);
                    }
                    VideoReverseActivity.this.C.seekTo(num.intValue());
                }
                VideoReverseActivity.this.c.setSelectedMaxValue(num2);
                VideoReverseActivity.this.c.setSelectedMinValue(num);
                VideoReverseActivity.this.s.setText(VideoReverseActivity.getTimeForTrackFormat(num.intValue()));
                VideoReverseActivity.this.u.setText(VideoReverseActivity.getTimeForTrackFormat(num2.intValue()));
                VideoReverseActivity.this.t.setText(VideoReverseActivity.getTimeForTrackFormat(num2.intValue() - num.intValue()));
                VideoReverseActivity.this.d.setSelectedMinValue(num);
                VideoReverseActivity.this.d.setSelectedMaxValue(num2);
                VideoReverseActivity.this.h = num.intValue();
                VideoReverseActivity.this.i = num2.intValue();
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
            if (this.C.isPlaying()) {
                this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                this.C.pause();
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


    public void g() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VideoReverseActivity.this.finish();
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
        this.isInFront = A;
        this.E = getIntent().getStringExtra("videouri");
        videoPath = this.E;
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
        return A;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return A;
        }
        if (menuItem.getItemId() == R.id.Done) {
            try {
                if (this.C.isPlaying()) {
                    this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    this.C.pause();
                }
            } catch (Exception unused) {
            }
            this.j = ((Integer) this.c.getSelectedMinValue()).intValue() / 1000;
            this.l = ((Integer) this.c.getSelectedMaxValue()).intValue() / 1000;
            this.k = this.l - this.j;
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb.append("/");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.VideoReverse));
            this.D = sb.toString();
            File file = new File(this.D);
            if (!file.exists()) {
                file.mkdirs();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(file.getAbsolutePath());
            sb2.append("/Rev_");
            sb2.append(System.currentTimeMillis());
            sb2.append(".mp4");
            this.D = sb2.toString();
            String[] strArr = new String[0];
            if (this.n == 0) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("");
                sb3.append(this.j);
                StringBuilder sb4 = new StringBuilder();
                sb4.append("");
                sb4.append(this.k);
                strArr = new String[]{"-ss", sb3.toString(), "-y", "-i", videoPath, "-t", sb4.toString(), "-vcodec", "mpeg4", "-b:v", "2097152", "-b:a", "48000", "-ac", "2", "-ar", "22050", "-vf", "reverse", this.D};
            } else if (this.n == 1) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("");
                sb5.append(this.j);
                StringBuilder sb6 = new StringBuilder();
                sb6.append("");
                sb6.append(this.k);
                strArr = new String[]{"-ss", sb5.toString(), "-y", "-i", videoPath, "-t", sb6.toString(), "-vcodec", "mpeg4", "-b:v", "2097152", "-b:a", "48000", "-ac", "2", "-ar", "22050", "-vf", "reverse", "-af", "areverse", this.D};
            }
            a(strArr, this.D);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
