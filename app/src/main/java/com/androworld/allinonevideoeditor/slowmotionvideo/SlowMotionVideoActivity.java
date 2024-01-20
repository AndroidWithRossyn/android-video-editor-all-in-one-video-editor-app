package com.androworld.allinonevideoeditor.slowmotionvideo;

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
public class SlowMotionVideoActivity extends AppCompatActivity {
    static boolean j = false;
    static final boolean k = true;
    String a;
    String b = null;
    String c = "00";
    Boolean d = Boolean.valueOf(false);
    ImageView e;
    VideoSliceSeekBar f;
    BubbleSeekBar g;
    int h;
    StringBuilder i = new StringBuilder();
    private CheckBox l;
    private PowerManager m;

    public TextView n;

    public TextView o;
    private TextView p;

    public VideoPlayerState q = new VideoPlayerState();
    private a r = new a();

    public VideoView s;
    private WakeLock t;
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
                this.b = SlowMotionVideoActivity.k;
                sendEmptyMessage(0);
            }
        }

        @Override
        public void handleMessage(Message message) {
            this.b = false;
            SlowMotionVideoActivity.this.f.videoPlayingProgress(SlowMotionVideoActivity.this.s.getCurrentPosition());
            if (!SlowMotionVideoActivity.this.s.isPlaying() || SlowMotionVideoActivity.this.s.getCurrentPosition() >= SlowMotionVideoActivity.this.f.getRightProgress()) {
                if (SlowMotionVideoActivity.this.s.isPlaying()) {
                    SlowMotionVideoActivity.this.s.pause();
                    SlowMotionVideoActivity.this.d = Boolean.valueOf(false);
                    SlowMotionVideoActivity.this.e.setBackgroundResource(R.drawable.play2);
                }
                SlowMotionVideoActivity.this.f.setSliceBlocked(false);
                SlowMotionVideoActivity.this.f.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.c, 50);
        }
    }

    @SuppressLint({"ClickableViewAccessibility", "InvalidWakeLockTag"})
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.slowmotionvideoactivity);

        LinearLayout sa = (LinearLayout) findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(SlowMotionVideoActivity.this, sa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Slow Motion Video");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (k || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(k);
            supportActionBar.setDisplayShowTitleEnabled(false);
            d();
            this.m = (PowerManager) getSystemService(Context.POWER_SERVICE);
            this.t = this.m.newWakeLock(6, "My Tag");
            Object lastNonConfigurationInstance = getLastNonConfigurationInstance();
            if (lastNonConfigurationInstance != null) {
                this.q = (VideoPlayerState) lastNonConfigurationInstance;
            } else {
                Bundle extras = getIntent().getExtras();
                this.q.setFilename(extras.getString("videofilename"));
                this.b = extras.getString("videofilename");
                extras.getString("videofilename").split("/");
            }
            this.p.setText(new File(this.b).getName());
            this.s.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    SlowMotionVideoActivity.this.d = Boolean.valueOf(false);
                    SlowMotionVideoActivity.this.e.setBackgroundResource(R.drawable.play2);
                }
            });
            this.s.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (SlowMotionVideoActivity.this.d.booleanValue()) {
                        SlowMotionVideoActivity.this.s.pause();
                        SlowMotionVideoActivity.this.d = Boolean.valueOf(false);
                        SlowMotionVideoActivity.this.e.setBackgroundResource(R.drawable.play2);
                    }
                    return SlowMotionVideoActivity.k;
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

    public void slowmotioncommand() {
        String[] strArr;
        String str = "";
        float f2 = 2.0f;
        if (this.h != 2) {
            if (this.h == 3) {
                f2 = 3.0f;
            } else if (this.h == 4) {
                f2 = 4.0f;
            } else if (this.h == 5) {
                f2 = 5.0f;
            } else if (this.h == 6) {
                f2 = 6.0f;
            } else if (this.h == 7) {
                f2 = 7.0f;
            } else if (this.h == 8) {
                f2 = 8.0f;
            }
        }
        String valueOf = String.valueOf(this.q.getStart() / 1000);
        String.valueOf(this.q.getStop() / 1000);
        String valueOf2 = String.valueOf(this.q.getDuration() / 1000);
        String filename = this.q.getFilename();
        this.b = FileUtils.getTargetFileName(this, filename);
        if (this.l.isChecked()) {
            j = k;
            if (this.h == 2) {
                str = "atempo=0.5";
            } else if (this.h == 3) {
                str = "atempo=0.5";
            } else if (this.h == 4) {
                str = "atempo=0.5,atempo=0.5";
            } else if (this.h == 5) {
                str = "atempo=0.5,atempo=0.5";
            } else if (this.h == 6) {
                str = "atempo=0.5,atempo=0.5,atempo=0.5";
            } else if (this.h == 7) {
                str = "atempo=0.5,atempo=0.5,atempo=0.5";
            } else if (this.h == 8) {
                str = "atempo=0.5,atempo=0.5,atempo=0.5,atempo=0.5";
            }
        }
        try {
            if (this.l.isChecked()) {
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
            Toast.makeText(this, "please select Quality", Toast.LENGTH_LONG).show();
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
                    intent.setData(Uri.fromFile(new File(SlowMotionVideoActivity.this.b)));
                    SlowMotionVideoActivity.this.sendBroadcast(intent);
                    SlowMotionVideoActivity.this.b();
                    SlowMotionVideoActivity.this.refreshGallery(str);

                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        SlowMotionVideoActivity.this.deleteFromGallery(str);
                        Toast.makeText(SlowMotionVideoActivity.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                } else {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        SlowMotionVideoActivity.this.deleteFromGallery(str);
                        Toast.makeText(SlowMotionVideoActivity.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }


            }
        });

        getWindow().clearFlags(16);
    }

    private void d() {
        this.p = (TextView) findViewById(R.id.Filename);
        this.n = (TextView) findViewById(R.id.left_pointer);
        this.o = (TextView) findViewById(R.id.right_pointer);
        this.e = (ImageView) findViewById(R.id.buttonply1);
        this.e.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SlowMotionVideoActivity.this.d.booleanValue()) {
                    SlowMotionVideoActivity.this.e.setBackgroundResource(R.drawable.play2);
                    SlowMotionVideoActivity.this.d = Boolean.valueOf(false);
                } else {
                    SlowMotionVideoActivity.this.e.setBackgroundResource(R.drawable.pause2);
                    SlowMotionVideoActivity.this.d = Boolean.valueOf(SlowMotionVideoActivity.k);
                }
                SlowMotionVideoActivity.this.h();
            }
        });
        this.s = (VideoView) findViewById(R.id.videoView1);
        this.l = (CheckBox) findViewById(R.id.checkBox1);
        this.f = (VideoSliceSeekBar) findViewById(R.id.seek_bar);
        this.g = (BubbleSeekBar) findViewById(R.id.seekBar);
        this.l.setChecked(false);
        this.g.setOnProgressChangedListener(new OnProgressChangedListener() {

            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int i, float f) {
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }

            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int i, float f, boolean fromUser) {
                SlowMotionVideoActivity.this.i.delete(0, SlowMotionVideoActivity.this.i.length());
                StringBuilder sb = SlowMotionVideoActivity.this.i;
                sb.append("(listener) int:");
                sb.append(i);
                SlowMotionVideoActivity.this.h = i;
            }
        });
    }


    public void f() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SlowMotionVideoActivity.this.finish();
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
        this.s.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                SlowMotionVideoActivity.this.f.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (SlowMotionVideoActivity.this.f.getSelectedThumb() == 1) {
                            SlowMotionVideoActivity.this.s.seekTo(SlowMotionVideoActivity.this.f.getLeftProgress());
                        }
                        SlowMotionVideoActivity.this.n.setText(SlowMotionVideoActivity.getTimeForTrackFormat(i));
                        SlowMotionVideoActivity.this.o.setText(SlowMotionVideoActivity.getTimeForTrackFormat(i2));
                        SlowMotionVideoActivity.this.c = SlowMotionVideoActivity.getTimeForTrackFormat(i);
                        SlowMotionVideoActivity.this.q.setStart(i);
                        SlowMotionVideoActivity.this.a = SlowMotionVideoActivity.getTimeForTrackFormat(i2);
                        SlowMotionVideoActivity.this.q.setStop(i2);
                    }
                });
                SlowMotionVideoActivity.this.a = SlowMotionVideoActivity.getTimeForTrackFormat(mediaPlayer.getDuration());
                SlowMotionVideoActivity.this.f.setMaxValue(mediaPlayer.getDuration());
                SlowMotionVideoActivity.this.f.setLeftProgress(0);
                SlowMotionVideoActivity.this.f.setRightProgress(mediaPlayer.getDuration());
                SlowMotionVideoActivity.this.f.setProgressMinDiff(0);
            }
        });
        this.s.setVideoPath(this.q.getFilename());
        this.a = getTimeForTrackFormat(this.s.getDuration());
    }


    public void h() {
        if (this.s.isPlaying()) {
            this.s.pause();
            this.f.setSliceBlocked(false);
            this.f.removeVideoStatusThumb();
            return;
        }
        this.s.seekTo(this.f.getLeftProgress());
        this.s.start();
        this.f.videoPlayingProgress(this.f.getLeftProgress());
        this.r.a();
    }

    public static String getTimeForTrackFormat(int i2) {
        long j2 = (long) i2;
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2)))});
    }


    @Override
    public void onResume() {
        super.onResume();
        this.t.acquire();
        if (!this.s.isPlaying()) {
            this.d = Boolean.valueOf(false);
            this.e.setBackgroundResource(R.drawable.play2);
        }
        this.s.seekTo(this.q.getCurrentTime());
    }


    public void onPause() {
        this.t.release();
        super.onPause();
        this.q.setCurrentTime(this.s.getCurrentPosition());
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
        return k;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return k;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (this.s.isPlaying()) {
                this.s.pause();
                this.e.setBackgroundResource(R.drawable.play2);
            }
            if (this.q.isValid()) {
                slowmotioncommand();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
