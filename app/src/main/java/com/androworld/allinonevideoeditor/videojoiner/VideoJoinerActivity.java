package com.androworld.allinonevideoeditor.videojoiner;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
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
import com.androworld.allinonevideoeditor.VideoSliceSeekBar;
import com.androworld.allinonevideoeditor.VideoSliceSeekBar.SeekBarChangeListener;
import com.androworld.allinonevideoeditor.videojoiner.model.VideoPlayerState;
import com.androworld.allinonevideoeditor.videojoiner.util.FileUtils;
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

@SuppressLint({"WrongConstant"})
public class VideoJoinerActivity extends AppCompatActivity implements OnClickListener {
    static final boolean r = true;
    int a = 0;
    int b = 0;
    Handler c = new Handler();
    String d;
    ImageView e;
    ImageView f;
    TextView g;
    TextView h;
    TextView i;
    TextView j;
    VideoSliceSeekBar k;
    VideoSliceSeekBar l;
    int m = 0;
    int n = 0;
    VideoView o;
    VideoView p;
    Runnable q = new Runnable() {
        public void run() {
            VideoJoinerActivity.this.b();
        }
    };

    public VideoPlayerState s = new VideoPlayerState();

    public VideoPlayerState t = new VideoPlayerState();
    private a u = new a();
    private b v = new b();
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
                this.b = VideoJoinerActivity.r;
                sendEmptyMessage(0);
            }
        }

        @Override
        public void handleMessage(Message message) {
            this.b = false;
            VideoJoinerActivity.this.k.videoPlayingProgress(VideoJoinerActivity.this.o.getCurrentPosition());
            if (!VideoJoinerActivity.this.o.isPlaying() || VideoJoinerActivity.this.o.getCurrentPosition() >= VideoJoinerActivity.this.k.getRightProgress()) {
                if (VideoJoinerActivity.this.o.isPlaying()) {
                    VideoJoinerActivity.this.o.pause();
                    VideoJoinerActivity.this.e.setBackgroundResource(R.drawable.play2);
                }
                VideoJoinerActivity.this.k.setSliceBlocked(false);
                VideoJoinerActivity.this.k.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.c, 50);
        }
    }

    private class b extends Handler {
        private boolean b;
        private Runnable c;

        private b() {
            this.b = false;
            this.c = new Runnable() {
                public void run() {
                    b.this.a();
                }
            };
        }


        public void a() {
            if (!this.b) {
                this.b = VideoJoinerActivity.r;
                sendEmptyMessage(0);
            }
        }

        @Override
        public void handleMessage(Message message) {
            this.b = false;
            VideoJoinerActivity.this.l.videoPlayingProgress(VideoJoinerActivity.this.p.getCurrentPosition());
            if (!VideoJoinerActivity.this.p.isPlaying() || VideoJoinerActivity.this.p.getCurrentPosition() >= VideoJoinerActivity.this.l.getRightProgress()) {
                if (VideoJoinerActivity.this.p.isPlaying()) {
                    VideoJoinerActivity.this.p.pause();
                    VideoJoinerActivity.this.f.setBackgroundResource(R.drawable.play2);
                }
                VideoJoinerActivity.this.l.setSliceBlocked(false);
                VideoJoinerActivity.this.l.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.c, 50);
        }
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.videojoineractivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Joiner");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (r || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(r);
            supportActionBar.setDisplayShowTitleEnabled(false);
            i();
            this.o.setVideoPath((String) FileUtils.myUri.get(0));
            this.p.setVideoPath((String) FileUtils.myUri.get(1));
            this.o.seekTo(100);
            this.p.seekTo(100);
            d();
            f();
            this.o.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    VideoJoinerActivity.this.e.setBackgroundResource(R.drawable.play2);
                }
            });
            this.p.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    VideoJoinerActivity.this.f.setBackgroundResource(R.drawable.play2);
                }
            });
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
        Intent intent = new Intent(this, AddAudioActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("song", this.d);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2)))});
    }

    private void d() {
        this.o.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                VideoJoinerActivity.this.k.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (VideoJoinerActivity.this.k.getSelectedThumb() == 1) {
                            VideoJoinerActivity.this.o.seekTo(VideoJoinerActivity.this.k.getLeftProgress());
                        }
                        VideoJoinerActivity.this.i.setText(VideoJoinerActivity.formatTimeUnit((long) i));
                        VideoJoinerActivity.this.g.setText(VideoJoinerActivity.formatTimeUnit((long) i2));
                        VideoJoinerActivity.this.s.setStart(i);
                        VideoJoinerActivity.this.s.setStop(i2);
                        VideoJoinerActivity.this.m = i / 1000;
                        VideoJoinerActivity.this.a = i2 / 1000;
                    }
                });
                VideoJoinerActivity.this.k.setMaxValue(mediaPlayer.getDuration());
                VideoJoinerActivity.this.k.setLeftProgress(0);
                VideoJoinerActivity.this.k.setRightProgress(mediaPlayer.getDuration());
                VideoJoinerActivity.this.k.setProgressMinDiff(0);
            }
        });
    }

    private void e() {
        if (this.p.isPlaying()) {
            this.p.pause();
            this.f.setBackgroundResource(R.drawable.play2);
        }
        if (this.o.isPlaying()) {
            this.o.pause();
            this.k.setSliceBlocked(false);
            this.e.setBackgroundResource(R.drawable.play2);
            this.k.removeVideoStatusThumb();
            return;
        }
        this.o.seekTo(this.k.getLeftProgress());
        this.o.start();
        this.k.videoPlayingProgress(this.k.getLeftProgress());
        this.e.setBackgroundResource(R.drawable.pause2);
        this.u.a();
    }

    private void f() {
        this.p.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                VideoJoinerActivity.this.l.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (VideoJoinerActivity.this.l.getSelectedThumb() == 1) {
                            VideoJoinerActivity.this.p.seekTo(VideoJoinerActivity.this.l.getLeftProgress());
                        }
                        VideoJoinerActivity.this.j.setText(VideoJoinerActivity.formatTimeUnit((long) i));
                        VideoJoinerActivity.this.h.setText(VideoJoinerActivity.formatTimeUnit((long) i2));
                        VideoJoinerActivity.this.t.setStart(i);
                        VideoJoinerActivity.this.t.setStop(i2);
                        VideoJoinerActivity.this.n = i / 1000;
                        VideoJoinerActivity.this.b = i2 / 1000;
                    }
                });
                VideoJoinerActivity.this.l.setMaxValue(mediaPlayer.getDuration());
                VideoJoinerActivity.this.l.setLeftProgress(0);
                VideoJoinerActivity.this.l.setRightProgress(mediaPlayer.getDuration());
                VideoJoinerActivity.this.l.setProgressMinDiff(0);
            }
        });
    }

    private void g() {
        if (this.o.isPlaying()) {
            this.o.pause();
            this.e.setBackgroundResource(R.drawable.play2);
        }
        if (this.p.isPlaying()) {
            this.p.pause();
            this.l.setSliceBlocked(false);
            this.f.setBackgroundResource(R.drawable.play2);
            this.l.removeVideoStatusThumb();
            return;
        }
        this.p.seekTo(this.l.getLeftProgress());
        this.p.start();
        this.l.videoPlayingProgress(this.l.getLeftProgress());
        this.f.setBackgroundResource(R.drawable.pause2);
        this.v.a();
    }

    @Override
    public void onClick(View view) {
        if (view == this.e) {
            e();
        }
        if (view == this.f) {
            g();
        }
    }

    private void h() {
        String format = new SimpleDateFormat("_HHmmss", Locale.US).format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.VideoJoiner));
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb2.append("/");
        sb2.append(getResources().getString(R.string.MainFolderName));
        sb2.append("/");
        sb2.append(getResources().getString(R.string.VideoJoiner));
        sb2.append("/videojoiner");
        sb2.append(format);
        sb2.append(".mp4");
        this.d = sb2.toString();
        a(new String[]{"-y", "-ss", String.valueOf(this.m), "-t", String.valueOf(this.a), "-i", (String) FileUtils.myUri.get(0), "-ss", String.valueOf(this.n), "-t", String.valueOf(this.b), "-i", (String) FileUtils.myUri.get(1), "-strict", "experimental", "-filter_complex", "[0:v]scale=320x240,setsar=1:1[v0];[1:v]scale=320x240,setsar=1:1[v1];[v0][v1] concat=n=2:v=1", "-ab", "48000", "-ac", "2", "-ar", "22050", "-s", "320x240", "-r", "15", "-b", "2097k", "-vcodec", "mpeg4", this.d}, this.d);
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
                    intent.setData(Uri.fromFile(new File(VideoJoinerActivity.this.d)));
                    VideoJoinerActivity.this.sendBroadcast(intent);
                    try {
                        MediaScannerConnection.scanFile(VideoJoinerActivity.this, new String[]{VideoJoinerActivity.this.d}, null, new OnScanCompletedListener() {
                            public void onScanCompleted(String str, Uri uri) {
                                VideoJoinerActivity.this.c.postDelayed(VideoJoinerActivity.this.q, 100);
                            }
                        });
                    } catch (Exception unused) {
                        VideoJoinerActivity.this.c.postDelayed(VideoJoinerActivity.this.q, 100);
                    }

                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoJoinerActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoJoinerActivity.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                } else {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoJoinerActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoJoinerActivity.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                VideoJoinerActivity.this.refreshGallery(str);

            }
        });


        getWindow().clearFlags(16);
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

    private void i() {
        this.e = (ImageView) findViewById(R.id.buttonply1);
        this.f = (ImageView) findViewById(R.id.buttonply2);
        this.k = (VideoSliceSeekBar) findViewById(R.id.seek_bar1);
        this.l = (VideoSliceSeekBar) findViewById(R.id.seek_bar2);
        this.i = (TextView) findViewById(R.id.left_pointer1);
        this.j = (TextView) findViewById(R.id.left_pointer2);
        this.g = (TextView) findViewById(R.id.right_pointer1);
        this.h = (TextView) findViewById(R.id.right_pointer2);
        this.o = (VideoView) findViewById(R.id.videoView1);
        this.p = (VideoView) findViewById(R.id.videoView2);
        this.e.setOnClickListener(this);
        this.f.setOnClickListener(this);
    }

    public void k() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VideoJoinerActivity.this.finish();
            }
        }).create().show();
    }


    public void onPause() {
        super.onPause();
        if (this.o.isPlaying()) {
            this.o.pause();
            this.e.setBackgroundResource(R.drawable.play2);
        } else if (this.p.isPlaying()) {
            this.p.pause();
            this.f.setBackgroundResource(R.drawable.play2);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), ListVideoAndMyAlbumActivity.class);
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
            if (this.o.isPlaying()) {
                this.o.pause();
                this.e.setBackgroundResource(R.drawable.play2);
            } else if (this.p.isPlaying()) {
                this.p.pause();
                this.f.setBackgroundResource(R.drawable.play2);
            }
            h();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
