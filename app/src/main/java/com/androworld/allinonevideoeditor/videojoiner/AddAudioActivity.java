package com.androworld.allinonevideoeditor.videojoiner;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
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
import com.androworld.allinonevideoeditor.audiovideomixer.AddAudio;
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
public class AddAudioActivity extends AppCompatActivity {
    static AudioSliceSeekBar a = null;
    static LinearLayout b = null;
    static LinearLayout c = null;
    static MediaPlayer d = null;
    static Boolean e = Boolean.valueOf(false);
    static ImageView f = null;
    static VideoSliceSeekBar g = null;
    static VideoView h = null;
    static final boolean BOOLEAN = true;

    public static TextView textView;
    private static TextView q;

    public static a r = new a();
    ImageView i;
    ImageView j;
    Context k;
    String l;
    ProgressDialog m = null;
    Uri n;

    public TextView s;

    public TextView t;

    public TextView u;
    private TextView v;

    public VideoPlayerState w = new VideoPlayerState();
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
                this.a = AddAudioActivity.BOOLEAN;
                sendEmptyMessage(0);
            }
        }

        @Override public void handleMessage(Message message) {
            this.a = false;
            AddAudioActivity.g.videoPlayingProgress(AddAudioActivity.h.getCurrentPosition());
            if (AddAudioActivity.d != null && AddAudioActivity.d.isPlaying()) {
                try {
                    AddAudioActivity.a.videoPlayingProgress(AddAudioActivity.d.getCurrentPosition());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!AddAudioActivity.h.isPlaying() || AddAudioActivity.h.getCurrentPosition() >= AddAudioActivity.g.getRightProgress()) {
                try {
                    if (AddAudioActivity.h.isPlaying()) {
                        try {
                            AddAudioActivity.h.pause();
                            AddAudioActivity.e = Boolean.valueOf(false);
                        } catch (OutOfMemoryError e2) {
                            e2.printStackTrace();
                        } catch (ArrayIndexOutOfBoundsException e3) {
                            e3.printStackTrace();
                        } catch (ActivityNotFoundException e4) {
                            e4.printStackTrace();
                        } catch (NotFoundException e5) {
                            e5.printStackTrace();
                        } catch (NullPointerException e6) {
                            e6.printStackTrace();
                        } catch (StackOverflowError e7) {
                            e7.printStackTrace();
                        }
                    }
                    AddAudioActivity.g.setSliceBlocked(false);
                    AddAudioActivity.g.removeVideoStatusThumb();
                    if (AddAudioActivity.d != null && AddAudioActivity.d.isPlaying()) {
                        try {
                            AddAudioActivity.d.pause();
                            AddAudioActivity.a.setSliceBlocked(false);
                            AddAudioActivity.a.removeVideoStatusThumb();
                            return;
                        } catch (IllegalStateException e8) {
                            e8.printStackTrace();
                        }
                    }
                    return;
                } catch (IllegalStateException e9) {
                    e9.printStackTrace();
                }
            }
            postDelayed(this.b, 50);
        }
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.audiovideomixeractivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Audio Video Mixer");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            AddAudio.status = 0;
            AddAudio.audioPath = "";
            e = Boolean.valueOf(false);
            this.k = this;
            this.v = (TextView) findViewById(R.id.Filename);
            b = (LinearLayout) findViewById(R.id.lnr_audio_select);
            c = (LinearLayout) findViewById(R.id.imgbtn_add);
            g();
            Object lastNonConfigurationInstance = getLastNonConfigurationInstance();
            if (lastNonConfigurationInstance != null) {
                try {
                    this.w = (VideoPlayerState) lastNonConfigurationInstance;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                try {
                    Bundle extras = getIntent().getExtras();
                    this.w.setFilename(extras.getString("song"));
                    extras.getString("song").split("/");
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            h();
            c.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    AddAudioActivity.this.startActivity(new Intent(AddAudioActivity.this, SelectMusicActivity.class));
                }
            });
            this.i = (ImageView) findViewById(R.id.imgbtn_close);
            this.i.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    AddAudioActivity.b.setVisibility(8);
                    AddAudio.status = 0;
                    if (AddAudioActivity.h != null && AddAudioActivity.h.isPlaying()) {
                        try {
                            AddAudioActivity.h.pause();
                            AddAudioActivity.f.setBackgroundResource(R.drawable.play2);
                            AddAudioActivity.e = Boolean.valueOf(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    AddAudio.audioPath = "";
                    if (AddAudioActivity.d != null && AddAudioActivity.d.isPlaying()) {
                        try {
                            AddAudioActivity.d.stop();
                            AddAudioActivity.d.release();
                            AddAudioActivity.d = null;
                        } catch (IllegalStateException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
            ads =  new Ads();
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
        sb2.append(".mkv");
        this.l = sb2.toString();
        int duration = this.w.getDuration() / 1000;
        a(new String[]{"-y", "-ss", String.valueOf(this.w.getStart() / 1000), "-t", String.valueOf(duration), "-i", this.w.getFilename(), "-ss", String.valueOf((a != null ? a.getLeftProgress() : 0) / 1000), "-i", AddAudio.audioPath, "-map", "0:0", "-map", "1:0", "-acodec", "copy", "-vcodec", "copy", "-preset", "ultrafast", "-ss", "0", "-t", String.valueOf(duration), this.l}, this.l);
    }

    private void a(String[] strArr, final String str) {
            this.m = new ProgressDialog(this.k);
            this.m.setMessage("Adding Audio...");
            this.m.setCancelable(false);
            this.m.setIndeterminate(BOOLEAN);
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
                        if (AddAudioActivity.this.m != null && AddAudioActivity.this.m.isShowing()) {
                            AddAudioActivity.this.m.dismiss();
                        }
                        MediaScannerConnection.scanFile(AddAudioActivity.this.k, new String[]{AddAudioActivity.this.l}, new String[]{"mkv"}, null);
                        File file = new File(AddAudioActivity.this.w.getFilename());
                        if (file.exists()) {
                            file.delete();
                            try {
                                ContentResolver contentResolver = AddAudioActivity.this.getContentResolver();
                                Uri uri = AddAudioActivity.this.n;
                                StringBuilder sb = new StringBuilder();
                                sb.append("_data=\"");
                                sb.append(AddAudioActivity.this.w.getFilename());
                                sb.append("\"");
                                contentResolver.delete(uri, sb.toString(), null);
                            } catch (Exception unused) {
                            }
                        }
                        MediaScannerConnection.scanFile(AddAudioActivity.this.k, new String[]{AddAudioActivity.this.w.getFilename()}, new String[]{"mkv"}, null);
                        AddAudioActivity.this.d();
                        AddAudioActivity.this.refreshGallery(str);

                    } else if (returnCode == RETURN_CODE_CANCEL) {
                        Log.d("ffmpegfailure", str);
                        try {
                            new File(str).delete();
                            AddAudioActivity.this.deleteFromGallery(str);
                            Toast.makeText(AddAudioActivity.this, "Error Creating Video", 0).show();
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    } else {
                        Log.d("ffmpegfailure", str);
                        try {
                            new File(str).delete();
                            AddAudioActivity.this.deleteFromGallery(str);
                            Toast.makeText(AddAudioActivity.this, "Error Creating Video", 0).show();
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }


                }
            });

            getWindow().clearFlags(16);
    }

    private void g() {
        this.s = (TextView) findViewById(R.id.left_pointer);
        this.u = (TextView) findViewById(R.id.right_pointer);
        a = (AudioSliceSeekBar) findViewById(R.id.audioSeekBar);
        g = (VideoSliceSeekBar) findViewById(R.id.seekBar1);
        h = (VideoView) findViewById(R.id.videoView1);
        this.j = (ImageView) findViewById(R.id.ivScreen);
        f = (ImageView) findViewById(R.id.btnPlayVideo);
        this.t = (TextView) findViewById(R.id.tvStartAudio);
        textView = (TextView) findViewById(R.id.tvEndAudio);
        q = (TextView) findViewById(R.id.audio_name);
    }

    private void h() {
        h.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(final MediaPlayer mediaPlayer) {
                AddAudioActivity.g.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (AddAudioActivity.g.getSelectedThumb() == 1) {
                            AddAudioActivity.h.seekTo(AddAudioActivity.g.getLeftProgress());
                        }
                        AddAudioActivity.this.s.setText(AddAudioActivity.formatTimeUnit((long) i, AddAudioActivity.BOOLEAN));
                        AddAudioActivity.this.u.setText(AddAudioActivity.formatTimeUnit((long) i2, AddAudioActivity.BOOLEAN));
                        AddAudioActivity.this.w.setStart(i);
                        AddAudioActivity.this.w.setStop(i2);
                        if (AddAudioActivity.d != null && AddAudioActivity.d.isPlaying()) {
                            try {
                                AddAudioActivity.d.seekTo(AddAudioActivity.a.getLeftProgress());
                                AddAudioActivity.a.videoPlayingProgress(AddAudioActivity.a.getLeftProgress());
                                AddAudioActivity.d.start();
                            } catch (IllegalStateException ewq) {
                                ewq.printStackTrace();
                            }
                        }
                        mediaPlayer.setVolume(0.0f, 0.0f);
                    }
                });
                AddAudioActivity.g.setMaxValue(mediaPlayer.getDuration());
                AddAudioActivity.g.setLeftProgress(AddAudioActivity.this.w.getStart());
                AddAudioActivity.g.setRightProgress(AddAudioActivity.this.w.getStop());
                AddAudioActivity.g.setProgressMinDiff(0);
                AddAudioActivity.h.seekTo(100);
            }
        });
        h.setVideoPath(this.w.getFilename());
        this.v.setText(new File(this.w.getFilename()).getName());
        f.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                if (AddAudioActivity.d != null) {
                    try {
                        AddAudioActivity.d.start();
                    } catch (IllegalStateException ewqe) {
                        ewqe.printStackTrace();
                    }
                }
                if (AddAudioActivity.e.booleanValue()) {
                    try {
                        AddAudioActivity.f.setBackgroundResource(R.drawable.play2);
                        AddAudioActivity.e = Boolean.valueOf(false);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    try {
                        AddAudioActivity.f.setBackgroundResource(R.drawable.pause2);
                        AddAudioActivity.e = Boolean.valueOf(AddAudioActivity.BOOLEAN);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                AddAudioActivity.this.i();
            }
        });
        h.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                AddAudioActivity.h.seekTo(0);
                AddAudioActivity.f.setBackgroundResource(R.drawable.play2);
            }
        });
    }


    public void i() {
        if (h.isPlaying()) {
            try {
                h.pause();
                g.setSliceBlocked(BOOLEAN);
                g.removeVideoStatusThumb();
                if (d != null && d.isPlaying()) {
                        d.pause();
                        return;
                }
                return;
            } catch (IllegalStateException e3) {
                e3.printStackTrace();
            }
        }
        h.seekTo(g.getLeftProgress());
        h.start();
        g.videoPlayingProgress(g.getLeftProgress());
        r.a();
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


    @Override public void onResume() {
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
                    q.setText(split[split.length - 1]);
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
                        AddAudioActivity.a.setSeekBarChangeListener(new AudioSliceSeekBar.SeekBarChangeListener() {
                            public void SeekBarValueChanged(int i, int i2) {
                                if (AddAudioActivity.a.getSelectedThumb() == 1) {
                                    AddAudioActivity.d.seekTo(AddAudioActivity.a.getLeftProgress());
                                }
                                AddAudioActivity.this.t.setText(AddAudioActivity.formatTimeUnit((long) i, AddAudioActivity.BOOLEAN));
                                AddAudioActivity.textView.setText(AddAudioActivity.formatTimeUnit((long) i2, AddAudioActivity.BOOLEAN));
                                if (AddAudioActivity.h != null && AddAudioActivity.h.isPlaying()) {
                                    AddAudioActivity.h.seekTo(AddAudioActivity.g.getLeftProgress());
                                    AddAudioActivity.h.start();
                                    AddAudioActivity.g.videoPlayingProgress(AddAudioActivity.g.getLeftProgress());
                                    AddAudioActivity.r.a();
                                }
                            }
                        });
                        AddAudioActivity.a.setMaxValue(mediaPlayer.getDuration());
                        AddAudioActivity.a.setLeftProgress(0);
                        AddAudioActivity.a.setRightProgress(mediaPlayer.getDuration());
                        AddAudioActivity.a.setProgressMinDiff(0);
                        AddAudioActivity.this.t.setText("00:00");
                        try {
                            AddAudioActivity.textView.setText(AddAudioActivity.formatTimeUnit1((long) mediaPlayer.getDuration()));
                        } catch (Exception sde) {
                            sde.printStackTrace();
                        }
                    }
                });
                d.setOnErrorListener(new OnErrorListener() {
                    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                        return AddAudioActivity.BOOLEAN;
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

    @Override public void onBackPressed() {
        super.onBackPressed();
        AddAudio.status = 0;
        AddAudio.audioPath = "";
        if (d != null && d.isPlaying()) {
            d.stop();
            d.release();
            d = null;
        }
    }



    public void k() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                AddAudioActivity.this.finish();
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

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.skip, menu);
        return BOOLEAN;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
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
            finish();
            return BOOLEAN;
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
        if (menuItem.getItemId() == R.id.Skip) {
            Bundle extras = getIntent().getExtras();
            Intent intent = new Intent(getApplicationContext(), VideoPlayer.class);
            intent.setFlags(67108864);
            intent.putExtra("song", extras.getString("song"));
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
