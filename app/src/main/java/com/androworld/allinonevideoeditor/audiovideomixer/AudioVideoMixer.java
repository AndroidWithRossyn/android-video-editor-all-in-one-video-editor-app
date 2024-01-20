package com.androworld.allinonevideoeditor.audiovideomixer;

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
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaScannerConnection;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androworld.allinonevideoeditor.Adclick;
import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.AudioSliceSeekBar;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.SelectMusicActivity;
import com.androworld.allinonevideoeditor.UtilCommand;
import com.androworld.allinonevideoeditor.VideoPlayer;
import com.androworld.allinonevideoeditor.VideoPlayerState;
import com.androworld.allinonevideoeditor.VideoSliceSeekBar;
import com.androworld.allinonevideoeditor.VideoSliceSeekBar.SeekBarChangeListener;
import com.androworld.allinonevideoeditor.listvideoandmyvideo.ListVideoAndMyAlbumActivity;
import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

@SuppressLint({"WrongConstant"})
public class AudioVideoMixer extends AppCompatActivity {
    static AudioSliceSeekBar a = null;
    static LinearLayout b = null;
    static LinearLayout c = null;
    static MediaPlayer d = null;
    static Boolean e = Boolean.valueOf(false);
    static ImageView f = null;
    static VideoSliceSeekBar g = null;
    static VideoView h = null;
    static final boolean n = true;

    public static TextView o;
    private static TextView p;

    public static a q = new a();
    ImageView i;
    ImageView j;
    Context k;
    String l;
    ProgressDialog m = null;

    public TextView r;

    public TextView s;

    public TextView t;
    private TextView u;

    public VideoPlayerState v = new VideoPlayerState();
    private Ads ads;

    private static class a extends Handler {
        private boolean a;
        private Runnable b;

        private a() {
            this.a = false;
            this.b = new Runnable() {
                public void run() {
                    a.this.a();
                }
            };
        }


        public void a() {
            if (!this.a) {
                this.a = AudioVideoMixer.n;
                sendEmptyMessage(0);
            }
        }

        @Override
        public void handleMessage(Message message) {
            this.a = false;
            AudioVideoMixer.g.videoPlayingProgress(AudioVideoMixer.h.getCurrentPosition());
            if (AudioVideoMixer.d != null && AudioVideoMixer.d.isPlaying()) {
                try {
                    AudioVideoMixer.a.videoPlayingProgress(AudioVideoMixer.d.getCurrentPosition());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!AudioVideoMixer.h.isPlaying() || AudioVideoMixer.h.getCurrentPosition() >= AudioVideoMixer.g.getRightProgress()) {
                try {
                    if (AudioVideoMixer.h.isPlaying()) {
                        AudioVideoMixer.h.pause();
                        AudioVideoMixer.e = Boolean.valueOf(false);

                    }
                    AudioVideoMixer.g.setSliceBlocked(false);
                    AudioVideoMixer.g.removeVideoStatusThumb();
                    if (AudioVideoMixer.d != null && AudioVideoMixer.d.isPlaying()) {
                        AudioVideoMixer.d.pause();
                        AudioVideoMixer.a.setSliceBlocked(false);
                        AudioVideoMixer.a.removeVideoStatusThumb();
                        return;
                    }
                    return;
                } catch (IllegalStateException e9) {
                    e9.printStackTrace();
                }
            }
            postDelayed(this.b, 50);
        }
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.audiovideomixeractivity);

        LinearLayout s = (LinearLayout) findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(AudioVideoMixer.this, s);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Audio Video Mixer");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (n || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(n);
            supportActionBar.setDisplayShowTitleEnabled(false);
            AddAudio.status = 0;
            AddAudio.audioPath = "";
            e = Boolean.valueOf(false);
            this.k = this;
            this.u = (TextView) findViewById(R.id.Filename);
            b = (LinearLayout) findViewById(R.id.lnr_audio_select);
            c = (LinearLayout) findViewById(R.id.imgbtn_add);
            g();
            Object lastNonConfigurationInstance = getLastNonConfigurationInstance();
            if (lastNonConfigurationInstance != null) {
                try {
                    this.v = (VideoPlayerState) lastNonConfigurationInstance;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                try {
                    Bundle extras = getIntent().getExtras();
                    this.v.setFilename(extras.getString("song"));
                    extras.getString("song").split("/");
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            h();
            c.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    AudioVideoMixer.this.startActivity(new Intent(AudioVideoMixer.this, SelectMusicActivity.class));
                }
            });
            this.i = (ImageView) findViewById(R.id.imgbtn_close);
            this.i.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    AudioVideoMixer.b.setVisibility(8);
                    AddAudio.status = 0;
                    if (AudioVideoMixer.h != null && AudioVideoMixer.h.isPlaying()) {
                        try {
                            AudioVideoMixer.h.pause();
                            AudioVideoMixer.f.setBackgroundResource(R.drawable.play2);
                            AudioVideoMixer.e = Boolean.valueOf(false);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                    AddAudio.audioPath = "";
                    if (AudioVideoMixer.d != null && AudioVideoMixer.d.isPlaying()) {
                        try {
                            AudioVideoMixer.d.stop();
                            AudioVideoMixer.d.release();
                            AudioVideoMixer.d = null;
                        } catch (IllegalStateException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
            ads.Interstitialload(this);

            return;
        }
        throw new AssertionError();
    }


    public void d() {
        ads.showInd(new Adclick() {
            @Override
            public void onclicl() {
                e();
            }
        });
    }


    public void e() {
        Intent intent = new Intent(getApplicationContext(), VideoPlayer.class);
        intent.setFlags(67108864);
        intent.putExtra("song", this.l);
        startActivity(intent);
        finish();
    }

    private void f() {
        String format = new SimpleDateFormat("_HHmmss", Locale.US).format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.AudioVideoMixer));
        this.l = sb.toString();
        File file = new File(this.l);
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb2.append("/");
        sb2.append(getResources().getString(R.string.MainFolderName));
        sb2.append("/");
        sb2.append(getResources().getString(R.string.AudioVideoMixer));
        sb2.append("/audiovideomixer");
        sb2.append(format);
        sb2.append(".mkv");
        this.l = sb2.toString();
        int duration = this.v.getDuration() / 1000;
        a(new String[]{"-y", "-ss", String.valueOf(this.v.getStart() / 1000), "-t", String.valueOf(duration), "-i", this.v.getFilename(), "-ss", String.valueOf((a != null ? a.getLeftProgress() : 0) / 1000), "-i", AddAudio.audioPath, "-map", "0:0", "-map", "1:0", "-acodec", "copy", "-vcodec", "copy", "-preset", "ultrafast", "-ss", "0", "-t", String.valueOf(duration), this.l}, this.l);
    }

    private void a(String[] strArr, final String str) {
        this.m = new ProgressDialog(this.k);
        this.m.setMessage("Adding Audio...");
        this.m.setCancelable(false);
        this.m.setIndeterminate(n);
        this.m.show();
        String ffmpegCommand = UtilCommand.main(strArr);
        FFmpeg.executeAsync(ffmpegCommand, new ExecuteCallback() {

            @Override
            public void apply(final long executionId, final int returnCode) {
                Log.d("TAG", String.format("FFmpeg process exited with rc %d.", returnCode));

                Log.d("TAG", "FFmpeg process output:");

                Config.printLastCommandOutput(Log.INFO);

                m.dismiss();
                if (returnCode == RETURN_CODE_SUCCESS) {
                    if (AudioVideoMixer.this.m != null && AudioVideoMixer.this.m.isShowing()) {
                        AudioVideoMixer.this.m.dismiss();
                    }
                    MediaScannerConnection.scanFile(AudioVideoMixer.this.k, new String[]{AudioVideoMixer.this.l}, new String[]{"mkv"}, null);
                    AudioVideoMixer.this.d();
                    AudioVideoMixer.this.refreshGallery(str);

                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.d("ffmpegfailure", str);
                    new File(str).delete();
                    AudioVideoMixer.this.deleteFromGallery(str);
                    Toast.makeText(AudioVideoMixer.this, "Error Creating Video", 0).show();
                } else {
                    Log.d("ffmpegfailure", str);
                    new File(str).delete();
                    AudioVideoMixer.this.deleteFromGallery(str);
                    Toast.makeText(AudioVideoMixer.this, "Error Creating Video", 0).show();
                }


            }
        });
        getWindow().clearFlags(16);
    }

    private void g() {
        this.r = (TextView) findViewById(R.id.left_pointer);
        this.t = (TextView) findViewById(R.id.right_pointer);
        a = (AudioSliceSeekBar) findViewById(R.id.audioSeekBar);
        g = (VideoSliceSeekBar) findViewById(R.id.seekBar1);
        h = (VideoView) findViewById(R.id.videoView1);
        this.j = (ImageView) findViewById(R.id.ivScreen);
        f = (ImageView) findViewById(R.id.btnPlayVideo);
        this.s = (TextView) findViewById(R.id.tvStartAudio);
        o = (TextView) findViewById(R.id.tvEndAudio);
        p = (TextView) findViewById(R.id.audio_name);
    }

    private void h() {
        h.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(final MediaPlayer mediaPlayer) {
                AudioVideoMixer.g.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (AudioVideoMixer.g.getSelectedThumb() == 1) {
                            AudioVideoMixer.h.seekTo(AudioVideoMixer.g.getLeftProgress());
                        }
                        AudioVideoMixer.this.r.setText(AudioVideoMixer.formatTimeUnit((long) i, AudioVideoMixer.n));
                        AudioVideoMixer.this.t.setText(AudioVideoMixer.formatTimeUnit((long) i2, AudioVideoMixer.n));
                        AudioVideoMixer.this.v.setStart(i);
                        AudioVideoMixer.this.v.setStop(i2);
                        if (AudioVideoMixer.d != null && AudioVideoMixer.d.isPlaying()) {
                            try {
                                AudioVideoMixer.d.seekTo(AudioVideoMixer.a.getLeftProgress());
                                AudioVideoMixer.a.videoPlayingProgress(AudioVideoMixer.a.getLeftProgress());
                                AudioVideoMixer.d.start();
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                        }
                        mediaPlayer.setVolume(0.0f, 0.0f);
                    }
                });
                AudioVideoMixer.g.setMaxValue(mediaPlayer.getDuration());
                AudioVideoMixer.g.setLeftProgress(AudioVideoMixer.this.v.getStart());
                AudioVideoMixer.g.setRightProgress(AudioVideoMixer.this.v.getStop());
                AudioVideoMixer.g.setProgressMinDiff(0);
                AudioVideoMixer.h.seekTo(100);
            }
        });
        h.setVideoPath(this.v.getFilename());
        this.u.setText(new File(this.v.getFilename()).getName());
        f.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AudioVideoMixer.d != null) {
                    try {
                        AudioVideoMixer.d.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
                if (AudioVideoMixer.e.booleanValue()) {
                    try {
                        AudioVideoMixer.f.setBackgroundResource(R.drawable.play2);
                        AudioVideoMixer.e = Boolean.valueOf(false);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    try {
                        AudioVideoMixer.f.setBackgroundResource(R.drawable.pause2);
                        AudioVideoMixer.e = Boolean.valueOf(AudioVideoMixer.n);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                AudioVideoMixer.this.i();
            }
        });
        h.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                AudioVideoMixer.h.seekTo(0);
                AudioVideoMixer.f.setBackgroundResource(R.drawable.play2);
            }
        });
    }


    public void i() {
        if (h.isPlaying()) {
            try {
                h.pause();
                g.setSliceBlocked(n);
                g.removeVideoStatusThumb();
                if (d != null && d.isPlaying()) {
                    try {
                        d.pause();
                        return;
                    } catch (IllegalStateException e2) {
                        e2.printStackTrace();
                    }
                }
                return;
            } catch (IllegalStateException e3) {
                e3.printStackTrace();
            }
        }
        h.seekTo(g.getLeftProgress());
        h.start();
        g.videoPlayingProgress(g.getLeftProgress());
        q.a();
        if (d != null && d.isPlaying()) {
            try {
                d.seekTo(a.getLeftProgress());
                a.videoPlayingProgress(a.getLeftProgress());
                d.start();
            } catch (IllegalStateException e4) {
                e4.printStackTrace();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            if (AddAudio.status == 1) {
                d = new MediaPlayer();
                f.setBackgroundResource(R.drawable.play2);
                e = Boolean.valueOf(false);
                h();
                b.setVisibility(0);
                try {
                    String[] split = AddAudio.audioPath.split("/");
                    p.setText(split[split.length - 1]);
                    Log.v("audiopath", AddAudio.audioPath);
                    d.setDataSource(AddAudio.audioPath);
                    d.prepare();
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                } catch (SecurityException e3) {
                    e3.printStackTrace();
                } catch (IllegalStateException e4) {
                    e4.printStackTrace();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
                d.setOnPreparedListener(new OnPreparedListener() {
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        AudioVideoMixer.a.setSeekBarChangeListener(new AudioSliceSeekBar.SeekBarChangeListener() {
                            public void SeekBarValueChanged(int i, int i2) {
                                if (AudioVideoMixer.a.getSelectedThumb() == 1) {
                                    AudioVideoMixer.d.seekTo(AudioVideoMixer.a.getLeftProgress());
                                }
                                AudioVideoMixer.this.s.setText(AudioVideoMixer.formatTimeUnit((long) i, AudioVideoMixer.n));
                                AudioVideoMixer.o.setText(AudioVideoMixer.formatTimeUnit((long) i2, AudioVideoMixer.n));
                                if (AudioVideoMixer.h != null && AudioVideoMixer.h.isPlaying()) {
                                    AudioVideoMixer.h.seekTo(AudioVideoMixer.g.getLeftProgress());
                                    AudioVideoMixer.h.start();
                                    AudioVideoMixer.g.videoPlayingProgress(AudioVideoMixer.g.getLeftProgress());
                                    AudioVideoMixer.q.a();
                                }
                            }
                        });
                        AudioVideoMixer.a.setMaxValue(mediaPlayer.getDuration());
                        AudioVideoMixer.a.setLeftProgress(0);
                        AudioVideoMixer.a.setRightProgress(mediaPlayer.getDuration());
                        AudioVideoMixer.a.setProgressMinDiff(0);
                        AudioVideoMixer.this.s.setText("00:00");
                        try {
                            AudioVideoMixer.o.setText(AudioVideoMixer.formatTimeUnit1((long) mediaPlayer.getDuration()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                d.setOnErrorListener(new OnErrorListener() {
                    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                        return AudioVideoMixer.n;
                    }
                });
                return;
            }
            f.setBackgroundResource(R.drawable.play2);
            e = Boolean.valueOf(false);
            AddAudio.status = 0;
            AddAudio.audioPath = "";
            b.setVisibility(8);
        } catch (Exception unused) {
        }
    }

    @SuppressLint({"NewApi"})
    public static String formatTimeUnit1(long j2) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2)))});
    }

    @SuppressLint({"NewApi"})
    public static String formatTimeUnit(long j2, boolean z) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2)))});
    }


    public void k() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AudioVideoMixer.this.finish();
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
    public void onBackPressed() {
        super.onBackPressed();
        AddAudio.status = 0;
        AddAudio.audioPath = "";
        if (d != null && d.isPlaying()) {
            d.stop();
            d.release();
            d = null;
        }
        Intent intent = new Intent(this, ListVideoAndMyAlbumActivity.class);
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
            AddAudio.status = 0;
            AddAudio.audioPath = "";
            if (d != null && d.isPlaying()) {
                try {
                    d.stop();
                    d.release();
                    d = null;
                    Log.e("", "back  button working...");
                } catch (IllegalStateException e2) {
                    e2.printStackTrace();
                }
            }
            Intent intent = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent.setFlags(67108864);
            startActivity(intent);
            finish();
            return n;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (h != null && h.isPlaying()) {
                try {
                    h.pause();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            if (d != null) {
                try {
                    d.pause();
                } catch (IllegalStateException e4) {
                    e4.printStackTrace();
                }
            }
            f();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
