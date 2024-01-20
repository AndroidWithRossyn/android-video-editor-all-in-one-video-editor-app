package com.androworld.allinonevideoeditor.videoconverter;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
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
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.UtilCommand;
import com.androworld.allinonevideoeditor.VideoPlayerState;
import com.androworld.allinonevideoeditor.listvideoandmyvideo.ListVideoAndMyAlbumActivity;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;


import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

@SuppressLint({"WrongConstant"})
public class VideoConverteractivity extends AppCompatActivity implements OnSeekBarChangeListener {
    static String a = null;
    public static String outputformat = null;
    static final boolean r = true;
    FormateBaseAdapter b;
    ImageView c;
    ArrayList<String> d;
    Handler e = new Handler();
    Boolean f = Boolean.valueOf(false);
    ProgressDialog g;
    SeekBar h;
    int i = 0;
    int j = 0;
    Spinner k;
    TextView l;
    TextView m;
    TextView n;
    VideoView o;
    OnClickListener p = new OnClickListener() {
        @Override public void onClick(View view) {
            if (VideoConverteractivity.this.f.booleanValue()) {
                VideoConverteractivity.this.o.pause();
                VideoConverteractivity.this.e.removeCallbacks(VideoConverteractivity.this.q);
                VideoConverteractivity.this.c.setBackgroundResource(R.drawable.play2);
            } else {
                VideoConverteractivity.this.o.seekTo(VideoConverteractivity.this.h.getProgress());
                VideoConverteractivity.this.o.start();
                VideoConverteractivity.this.e.postDelayed(VideoConverteractivity.this.q, 500);
                VideoConverteractivity.this.c.setBackgroundResource(R.drawable.pause2);
            }
            VideoConverteractivity.this.f = Boolean.valueOf(VideoConverteractivity.this.f.booleanValue() ^ VideoConverteractivity.r);
        }
    };
    Runnable q = new Runnable() {
        public void run() {
            if (VideoConverteractivity.this.o.isPlaying()) {
                int currentPosition = VideoConverteractivity.this.o.getCurrentPosition();
                VideoConverteractivity.this.h.setProgress(currentPosition);
                try {
                    VideoConverteractivity.this.m.setText(VideoConverteractivity.formatTimeUnit((long) currentPosition));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (currentPosition == VideoConverteractivity.this.j) {
                    VideoConverteractivity.this.h.setProgress(0);
                    VideoConverteractivity.this.c.setBackgroundResource(R.drawable.play2);
                    VideoConverteractivity.this.m.setText("00:00");
                    VideoConverteractivity.this.e.removeCallbacks(VideoConverteractivity.this.q);
                    return;
                }
                VideoConverteractivity.this.e.postDelayed(VideoConverteractivity.this.q, 500);
                return;
            }
            VideoConverteractivity.this.h.setProgress(VideoConverteractivity.this.j);
            try {
                VideoConverteractivity.this.m.setText(VideoConverteractivity.formatTimeUnit((long) VideoConverteractivity.this.j));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
            VideoConverteractivity.this.e.removeCallbacks(VideoConverteractivity.this.q);
        }
    };
    private PowerManager s;

    public VideoPlayerState t = new VideoPlayerState();
    private WakeLock u;
    private Ads ads;

    @SuppressLint({"NewApi"})
    private class a {
        String a;

        public a() {
            VideoConverteractivity.this.g = new ProgressDialog(VideoConverteractivity.this);
            VideoConverteractivity.this.g.setCancelable(false);
            VideoConverteractivity.this.g.show();
        }

        public int a(String str, String str2, String str3, String str4) {
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("--");
            sb.append(str2);
            sb.append("--");
            sb.append(str3);
            sb.append("--");
            sb.append(str4);
            printStream.println(sb.toString());
            final String[] strArr = {"-i", str, "-vcodec", str4, "-acodec", "aac", str2};

            VideoConverteractivity.this.g.setMessage("Processing...");

            String ffmpegCommand = UtilCommand.main(strArr);
            com.arthenica.mobileffmpeg.FFmpeg.executeAsync(ffmpegCommand, new ExecuteCallback() {

                @Override
                public void apply(final long executionId, final int returnCode) {
                    Log.d("TAG", String.format("FFmpeg process exited with rc %d.", returnCode));

                    Log.d("TAG", "FFmpeg process output:");

                    Config.printLastCommandOutput(Log.INFO);

                    g.dismiss();
                    if (returnCode == RETURN_CODE_SUCCESS) {
                        a.this.b();

                    } else if (returnCode == RETURN_CODE_CANCEL) {
                        Toast.makeText(VideoConverteractivity.this, "Error Creating Video!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(VideoConverteractivity.this, "Error Creating Video!", Toast.LENGTH_LONG).show();
                    }


                }
            });

                return 0;
        }


        public void a() {
            this.a = VideoConverteractivity.this.t.getFilename();
            VideoConverteractivity.a = FileUtils.getTargetFileName(VideoConverteractivity.this, this.a);
            String extension = FileUtils.getExtension(this.a);
            String str = "copy";
            String str2 = "copy";
            if (VideoConverteractivity.outputformat.equalsIgnoreCase("avi") || VideoConverteractivity.outputformat.equalsIgnoreCase("mov")) {
                str = "mp2";
                str2 = "libx264";
            }
            if (VideoConverteractivity.outputformat.equalsIgnoreCase("wmv")) {
                str = "mp2";
                str2 = "wmv2";
            }
            if (extension.contains("wmv") && VideoConverteractivity.outputformat.equalsIgnoreCase("mp4")) {
                str = "mp2";
                str2 = "libx264";
            }
            if (VideoConverteractivity.outputformat.equalsIgnoreCase("mpg") || VideoConverteractivity.outputformat.equalsIgnoreCase("mpeg")) {
                str2 = "mpeg2video";
                str = "mp2";
            }
            if (extension.contains("mpg") || extension.contains("mpeg") || (extension.contains("wmv") && VideoConverteractivity.outputformat.equalsIgnoreCase("3gp"))) {
                str = "mp2";
                str2 = "h263";
            }
            a(this.a, VideoConverteractivity.a, str, str2);
        }

        @SuppressLint({"WrongConstant"})
        public void b() {
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.fromFile(new File(VideoConverteractivity.a)));
            VideoConverteractivity.this.sendBroadcast(intent);
        }
    }

    public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @SuppressLint({"ClickableViewAccessibility", "InvalidWakeLockTag"})
    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.videoconverteractivity);

        LinearLayout sa = (LinearLayout) findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(VideoConverteractivity.this,sa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Converter");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (r || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(r);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.d = new ArrayList<>();
//            this.d.add("avi");
            this.d.add("flv");
            this.d.add("mp4");
            this.d.add("mkv");
//            this.d.add("gif");
//            this.d.add("mov");
//            this.d.add("mpg");
//            this.d.add("mpeg");
            this.d.add("wmv");
            this.d.add("3gp");
            this.s = (PowerManager) getSystemService(Context.POWER_SERVICE);
            this.u = this.s.newWakeLock(6, "My Tag");
            Object lastNonConfigurationInstance = getLastNonConfigurationInstance();
            if (lastNonConfigurationInstance != null) {
                this.t = (VideoPlayerState) lastNonConfigurationInstance;
            } else {
                a();
                a = getIntent().getExtras().getString("videofilename");
                this.t.setFilename(a);
            }
            this.n.setText(new File(a).getName());
            this.o.setVideoPath(a);
            this.o.seekTo(100);
            this.o.setOnErrorListener(new OnErrorListener() {
                public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    Toast.makeText(VideoConverteractivity.this, "Video Player Not Supproting !", Toast.LENGTH_LONG).show();
                    return VideoConverteractivity.r;
                }
            });
            this.o.setOnPreparedListener(new OnPreparedListener() {
                public void onPrepared(MediaPlayer mediaPlayer) {
                    VideoConverteractivity.this.j = VideoConverteractivity.this.o.getDuration();
                    VideoConverteractivity.this.h.setMax(VideoConverteractivity.this.j);
                    VideoConverteractivity.this.m.setText("00:00");
                    try {
                        VideoConverteractivity.this.l.setText(VideoConverteractivity.formatTimeUnit((long) VideoConverteractivity.this.j));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.o.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    VideoConverteractivity.this.c.setBackgroundResource(R.drawable.pause2);
                    VideoConverteractivity.this.o.seekTo(0);
                    VideoConverteractivity.this.h.setProgress(0);
                    VideoConverteractivity.this.m.setText("00:00");
                    VideoConverteractivity.this.e.removeCallbacks(VideoConverteractivity.this.q);
                }
            });
            this.o.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
            int lastIndexOf = this.t.getFilename().lastIndexOf(".") + 1;
            if (this.d.contains(this.t.getFilename().substring(lastIndexOf).toLowerCase())) {
                this.d.remove(this.t.getFilename().substring(lastIndexOf).toLowerCase());
                outputformat = (String) this.d.get(0);
            }
            this.b = new FormateBaseAdapter(this, this.d, this.i);
            this.k.setAdapter(this.b);
            this.k.setSelection(0);
            return;
        }
        throw new AssertionError();
    }

    private void a() {
        this.n = (TextView) findViewById(R.id.Filename);
        this.o = (VideoView) findViewById(R.id.videoView1);
        this.c = (ImageView) findViewById(R.id.buttonply);
        this.c.setOnClickListener(this.p);
        this.m = (TextView) findViewById(R.id.left_pointer);
        this.l = (TextView) findViewById(R.id.right_pointer);
        this.h = (SeekBar) findViewById(R.id.sbVideo);
        this.h.setOnSeekBarChangeListener(this);
        this.k = (Spinner) findViewById(R.id.sp_convert);
    }

    @SuppressLint({"DefaultLocale"})
    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2)))});
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        this.o.seekTo(progress);
        try {
            this.m.setText(formatTimeUnit((long) progress));
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
    }



    public void c() {
        new Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                VideoConverteractivity.this.finish();
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

    @Override public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ListVideoAndMyAlbumActivity.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }


    public void onPause() {
        super.onPause();
        this.u.release();
        Log.i("VideoView", "In on pause");
    }


    @Override public void onResume() {
        super.onResume();
        this.u.acquire();
        Log.i("VideoView", "In on resume");
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return r;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return r;
        }
        if (menuItem.getItemId() == R.id.Done) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb.append("/");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.VideoConverter));
            File file = new File(sb.toString());
            if (!file.exists()) {
                file.mkdirs();
            }
            if (this.o.isPlaying()) {
                this.o.pause();
                this.e.removeCallbacks(this.q);
                this.f = Boolean.valueOf(false);
                this.c.setBackgroundResource(R.drawable.play2);
            }
            outputformat = (String) this.d.get(this.k.getSelectedItemPosition());
            if (outputformat != null) {
                getClass();
                new a().a();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
