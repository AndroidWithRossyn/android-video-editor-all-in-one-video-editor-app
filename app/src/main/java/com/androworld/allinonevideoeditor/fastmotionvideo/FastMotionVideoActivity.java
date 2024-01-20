package com.androworld.allinonevideoeditor.fastmotionvideo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androworld.allinonevideoeditor.Adclick;
import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.UtilCommand;
import com.androworld.allinonevideoeditor.VideoPlayer;
import com.androworld.allinonevideoeditor.VideoPlayerState;
import com.androworld.allinonevideoeditor.VideoSliceSeekBar;
import com.androworld.allinonevideoeditor.VideoSliceSeekBar.SeekBarChangeListener;
import com.androworld.allinonevideoeditor.listvideoandmyvideo.ListVideoAndMyAlbumActivity;
import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;


import com.xw.repo.BubbleSeekBar;
import com.xw.repo.BubbleSeekBar.OnProgressChangedListener;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

@SuppressLint({"WrongConstant"})
public class FastMotionVideoActivity extends AppCompatActivity {
    static final boolean BOOLEAN = true;
    String a;
    String b = null;
    Boolean c = Boolean.valueOf(false);
    String d = "00";
    ImageView e;
    VideoSliceSeekBar f;
    BubbleSeekBar g;
    int h;
    StringBuilder i = new StringBuilder();
    private CheckBox k;
    private PowerManager l;

    public TextView m;

    public TextView n;
    private TextView o;

    public VideoPlayerState p = new VideoPlayerState();
    private a q = new a();

    public VideoView r;
    private WakeLock s;
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
                this.b = FastMotionVideoActivity.BOOLEAN;
                sendEmptyMessage(0);
            }
        }

        @Override
        public void handleMessage(Message message) {
            this.b = false;
            FastMotionVideoActivity.this.f.videoPlayingProgress(FastMotionVideoActivity.this.r.getCurrentPosition());
            if (!FastMotionVideoActivity.this.r.isPlaying() || FastMotionVideoActivity.this.r.getCurrentPosition() >= FastMotionVideoActivity.this.f.getRightProgress()) {
                if (FastMotionVideoActivity.this.r.isPlaying()) {
                    FastMotionVideoActivity.this.r.pause();
                    FastMotionVideoActivity.this.c = Boolean.valueOf(false);
                    FastMotionVideoActivity.this.e.setBackgroundResource(R.drawable.play2);
                }
                FastMotionVideoActivity.this.f.setSliceBlocked(false);
                FastMotionVideoActivity.this.f.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.c, 50);
        }
    }

    @SuppressLint({"ClickableViewAccessibility", "InvalidWakeLockTag"})
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fastmotionvideoactivity);

        LinearLayout sa = (LinearLayout) findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(FastMotionVideoActivity.this, sa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Fast Motion Video");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            d();
            this.l = (PowerManager) getSystemService(Context.POWER_SERVICE);
            this.s = this.l.newWakeLock(6, "My Tag");
            Object lastNonConfigurationInstance = getLastNonConfigurationInstance();
            if (lastNonConfigurationInstance != null) {
                this.p = (VideoPlayerState) lastNonConfigurationInstance;
            } else {
                Bundle extras = getIntent().getExtras();
                this.p.setFilename(extras.getString("videofilename"));
                this.b = extras.getString("videofilename");
            }
            this.o.setText(new File(this.b).getName());
            this.r.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    FastMotionVideoActivity.this.c = Boolean.valueOf(false);
                    FastMotionVideoActivity.this.e.setBackgroundResource(R.drawable.play2);
                }
            });
            this.r.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (FastMotionVideoActivity.this.c.booleanValue()) {
                        FastMotionVideoActivity.this.r.pause();
                        FastMotionVideoActivity.this.c = Boolean.valueOf(false);
                        FastMotionVideoActivity.this.e.setBackgroundResource(R.drawable.play2);
                    }
                    return FastMotionVideoActivity.BOOLEAN;
                }
            });
            g();
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
        intent.putExtra("song", this.b);
        startActivity(intent);
        finish();
    }

    public void fastmotioncommand() {
        String[] strArr;
        String str = "";
        float f2 = this.h == 2 ? 0.5f : this.h == 3 ? 0.33333334f : this.h == 4 ? 0.25f : this.h == 5 ? 0.2f : this.h == 6 ? 0.16666667f : this.h == 7 ? 0.14285715f : this.h == 8 ? 0.125f : 2.0f;
        String valueOf = String.valueOf(this.p.getStart() / 1000);
        String valueOf2 = String.valueOf(this.p.getDuration() / 1000);
        String filename = this.p.getFilename();
        this.b = FileUtils.getTargetFileName(this, filename);
        if (this.k.isChecked()) {
            if (this.h == 2) {
                str = "atempo=2.0";
            } else if (this.h == 3) {
                str = "atempo=2.0";
            } else if (this.h == 4) {
                str = "atempo=2.0,atempo=2.0";
            } else if (this.h == 5) {
                str = "atempo=2.0,atempo=2.0";
            } else if (this.h == 6) {
                str = "atempo=2.0,atempo=2.0,atempo=2.0";
            } else if (this.h == 7) {
                str = "atempo=2.0,atempo=2.0,atempo=2.0";
            } else if (this.h == 8) {
                str = "atempo=2.0,atempo=2.0,atempo=2.0,atempo=2.0";
            }
        }
        try {
            if (this.k.isChecked()) {
                StringBuilder sb = new StringBuilder();
                sb.append("setpts=");
                sb.append(f2);
                sb.append("*PTS");
                strArr = new String[]{"-y", "-ss", valueOf, "-i", filename, "-filter:v", sb.toString(), "-filter:a", str, "-r", "15", "-b:v", "2500k", "-strict", "experimental", "-t", valueOf2, this.b};
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("setpts=");
                sb2.append(f2);
                sb2.append("*PTS");
                strArr = new String[]{"-y", "-ss", valueOf, "-i", filename, "-filter:v", sb2.toString(), "-an", "-r", "15", "-ab", "128k", "-vcodec", "mpeg4", "-b:v", "2500k", "-sample_fmt", "s16", "-strict", "experimental", "-t", valueOf2, this.b};
            }
            a(strArr, this.b);
        } catch (Exception unused) {
            File file = new File(this.b);
            if (file.exists()) {
                file.delete();
                finish();
                return;
            }
            Toast.makeText(this, "please select Quality", 0).show();
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

                Config.printLastCommandOutput(Log.INFO);

                progressDialog.dismiss();
                if (returnCode == RETURN_CODE_SUCCESS) {
                    progressDialog.dismiss();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(new File(FastMotionVideoActivity.this.b)));
                    FastMotionVideoActivity.this.sendBroadcast(intent);
                    FastMotionVideoActivity.this.b();
                    progressDialog.dismiss();
                    FastMotionVideoActivity.this.refreshGallery(str);
                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        FastMotionVideoActivity.this.deleteFromGallery(str);
                        Toast.makeText(FastMotionVideoActivity.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                } else {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        FastMotionVideoActivity.this.deleteFromGallery(str);
                        Toast.makeText(FastMotionVideoActivity.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }


            }
        });

        getWindow().clearFlags(16);
    }

    private void d() {
        this.o = (TextView) findViewById(R.id.Filename);
        this.m = (TextView) findViewById(R.id.left_pointer);
        this.n = (TextView) findViewById(R.id.right_pointer);
        this.e = (ImageView) findViewById(R.id.buttonply1);
        this.e.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FastMotionVideoActivity.this.c.booleanValue()) {
                    FastMotionVideoActivity.this.e.setBackgroundResource(R.drawable.play2);
                    FastMotionVideoActivity.this.c = Boolean.valueOf(false);
                } else {
                    FastMotionVideoActivity.this.e.setBackgroundResource(R.drawable.pause2);
                    FastMotionVideoActivity.this.c = Boolean.valueOf(FastMotionVideoActivity.BOOLEAN);
                }
                FastMotionVideoActivity.this.h();
            }
        });
        this.r = (VideoView) findViewById(R.id.videoView1);
        this.k = (CheckBox) findViewById(R.id.checkBox1);
        this.f = (VideoSliceSeekBar) findViewById(R.id.seek_bar);
        this.g = (BubbleSeekBar) findViewById(R.id.seekBar);
        this.k.setChecked(false);
        this.g.setOnProgressChangedListener(new OnProgressChangedListener() {

            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int i, float f, boolean fromUser) {
                FastMotionVideoActivity.this.i.delete(0, FastMotionVideoActivity.this.i.length());
                StringBuilder sb = FastMotionVideoActivity.this.i;
                sb.append("(listener) int:");
                sb.append(i);
                FastMotionVideoActivity.this.h = i;
            }

            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int i, float f) {
                //add
            }

            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int i, float f, boolean b) {
                //add
            }

        });
    }


    public void f() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FastMotionVideoActivity.this.finish();
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

    private void g() {
        this.r.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                FastMotionVideoActivity.this.f.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (FastMotionVideoActivity.this.f.getSelectedThumb() == 1) {
                            FastMotionVideoActivity.this.r.seekTo(FastMotionVideoActivity.this.f.getLeftProgress());
                        }
                        FastMotionVideoActivity.this.m.setText(FastMotionVideoActivity.getTimeForTrackFormat(i));
                        FastMotionVideoActivity.this.n.setText(FastMotionVideoActivity.getTimeForTrackFormat(i2));
                        FastMotionVideoActivity.this.d = FastMotionVideoActivity.getTimeForTrackFormat(i);
                        FastMotionVideoActivity.this.p.setStart(i);
                        FastMotionVideoActivity.this.a = FastMotionVideoActivity.getTimeForTrackFormat(i2);
                        FastMotionVideoActivity.this.p.setStop(i2);
                    }
                });
                FastMotionVideoActivity.this.a = FastMotionVideoActivity.getTimeForTrackFormat(mediaPlayer.getDuration());
                FastMotionVideoActivity.this.f.setMaxValue(mediaPlayer.getDuration());
                FastMotionVideoActivity.this.f.setLeftProgress(0);
                FastMotionVideoActivity.this.f.setRightProgress(mediaPlayer.getDuration());
                FastMotionVideoActivity.this.f.setProgressMinDiff(0);
            }
        });
        this.r.setVideoPath(this.p.getFilename());
        this.a = getTimeForTrackFormat(this.r.getDuration());
    }


    public void h() {
        if (this.r.isPlaying()) {
            this.r.pause();
            this.f.setSliceBlocked(false);
            this.f.removeVideoStatusThumb();
            return;
        }
        this.r.seekTo(this.f.getLeftProgress());
        this.r.start();
        this.f.videoPlayingProgress(this.f.getLeftProgress());
        this.q.a();
    }

    public static String getTimeForTrackFormat(int i2) {
        long j2 = (long) i2;
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2)))});
    }


    @Override
    public void onResume() {
        super.onResume();
        this.s.acquire();
        if (!this.r.isPlaying()) {
            this.c = Boolean.valueOf(false);
            this.e.setBackgroundResource(R.drawable.play2);
        }
        this.r.seekTo(this.p.getCurrentTime());
    }

    @Override
    public void onPause() {
        this.s.release();
        super.onPause();
        this.p.setCurrentTime(this.r.getCurrentPosition());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return BOOLEAN;
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
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return BOOLEAN;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (this.r.isPlaying()) {
                this.r.pause();
                this.e.setBackgroundResource(R.drawable.play2);
            }
            if (this.p.isValid()) {
                fastmotioncommand();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
