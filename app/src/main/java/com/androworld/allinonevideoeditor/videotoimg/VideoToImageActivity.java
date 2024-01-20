package com.androworld.allinonevideoeditor.videotoimg;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.Helper;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.VideoPlayer;
import com.androworld.allinonevideoeditor.videotogif.ListVideoAndMyAlbumActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant"})
public class VideoToImageActivity extends AppCompatActivity implements OnSeekBarChangeListener {
    public static String PATH = "";
    public static ArrayList<Integer> myList = new ArrayList<>();
    static final boolean s = true;
    public static int time;
    Handler a = new Handler();
    VideoView b;
    int c = 0;
    TextView d;
    TextView e;
    TextView f;
    ImageView g;
    SeekBar h;
    int i = 0;
    boolean j = false;
    Bitmap k;
    File l;
    String m;
    File n;
    String o;
    String p;
    OnClickListener q = new OnClickListener() {
        @Override public void onClick(View view) {
            if (VideoToImageActivity.this.j) {
                try {
                    VideoToImageActivity.this.b.pause();
                    VideoToImageActivity.this.a.removeCallbacks(VideoToImageActivity.this.r);
                    VideoToImageActivity.this.g.setBackgroundResource(R.drawable.play2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    VideoToImageActivity.this.b.seekTo(VideoToImageActivity.this.h.getProgress());
                    VideoToImageActivity.this.b.start();
                    VideoToImageActivity.this.a.postDelayed(VideoToImageActivity.this.r, 200);
                    VideoToImageActivity.this.b.setVisibility(0);
                    VideoToImageActivity.this.g.setBackgroundResource(R.drawable.pause2);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            VideoToImageActivity.this.j ^= VideoToImageActivity.s;
        }
    };
    Runnable r = new Runnable() {
        public void run() {
            if (VideoToImageActivity.this.b.isPlaying()) {
                int currentPosition = VideoToImageActivity.this.b.getCurrentPosition();
                VideoToImageActivity.this.h.setProgress(currentPosition);
                try {
                    VideoToImageActivity.this.d.setText(VideoPlayer.formatTimeUnit((long) currentPosition));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (currentPosition == VideoToImageActivity.this.i) {
                    VideoToImageActivity.this.h.setProgress(0);
                    VideoToImageActivity.this.d.setText("00:00");
                    VideoToImageActivity.this.a.removeCallbacks(VideoToImageActivity.this.r);
                    return;
                }
                VideoToImageActivity.this.a.postDelayed(VideoToImageActivity.this.r, 200);
                return;
            }
            VideoToImageActivity.this.h.setProgress(VideoToImageActivity.this.i);
            try {
                VideoToImageActivity.this.d.setText(VideoPlayer.formatTimeUnit((long) VideoToImageActivity.this.i));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
            VideoToImageActivity.this.a.removeCallbacks(VideoToImageActivity.this.r);
        }
    };
    private FileOutputStream t;
    private Ads ads;

    private class a extends AsyncTask<Void, Void, Void> {
        private ProgressDialog b;

        private a() {
        }


        public void onPreExecute() {
            this.b = ProgressDialog.show(VideoToImageActivity.this, "Capture Image", "Please wait...", VideoToImageActivity.s);
            super.onPreExecute();
        }



        public Void doInBackground(Void... voidArr) {
            try {
                Thread.sleep(1000);
                VideoToImageActivity.this.storeImage();
                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile(new File(VideoToImageActivity.this.o)));
                VideoToImageActivity.this.sendBroadcast(intent);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (NoSuchMethodError e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            } catch (NullPointerException e4) {
                e4.printStackTrace();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
            return null;
        }



        public void onPostExecute(Void voidR) {
            this.b.dismiss();
            super.onPostExecute(voidR);
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.videotoimageactivity);

        LinearLayout sa = (LinearLayout) findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(VideoToImageActivity.this,sa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video To Image");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (s || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(s);
            supportActionBar.setDisplayShowTitleEnabled(false);
            myList.clear();
            this.b = (VideoView) findViewById(R.id.videoView_player);
            this.h = (SeekBar) findViewById(R.id.sbVideo);
            this.h.setOnSeekBarChangeListener(this);
            this.d = (TextView) findViewById(R.id.left_pointer);
            this.e = (TextView) findViewById(R.id.right_pointer);
            this.g = (ImageView) findViewById(R.id.btnPlayVideo);
            this.f = (TextView) findViewById(R.id.Filename);
            PATH = getIntent().getStringExtra("videouri");
            this.f.setText(new File(PATH).getName());
            this.b.setVideoPath(PATH);
            this.b.seekTo(100);
            this.b.setOnErrorListener(new OnErrorListener() {
                public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    Toast.makeText(VideoToImageActivity.this.getApplicationContext(), "Video Player Not Supproting", 0).show();
                    return VideoToImageActivity.s;
                }
            });
            this.b.setOnPreparedListener(new OnPreparedListener() {
                public void onPrepared(MediaPlayer mediaPlayer) {
                    VideoToImageActivity.this.i = VideoToImageActivity.this.b.getDuration();
                    VideoToImageActivity.this.h.setMax(VideoToImageActivity.this.i);
                    VideoToImageActivity.this.d.setText("00:00");
                    try {
                        VideoToImageActivity.this.e.setText(VideoPlayer.formatTimeUnit((long) VideoToImageActivity.this.i));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.b.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    VideoToImageActivity.this.b.setVisibility(0);
                    VideoToImageActivity.this.g.setBackgroundResource(R.drawable.play2);
                    VideoToImageActivity.this.b.seekTo(0);
                    VideoToImageActivity.this.h.setProgress(0);
                    VideoToImageActivity.this.d.setText("00:00");
                    VideoToImageActivity.this.a.removeCallbacks(VideoToImageActivity.this.r);
                    VideoToImageActivity.this.j ^= VideoToImageActivity.s;
                }
            });
            this.g.setOnClickListener(this.q);
            findViewById(R.id.imageView_capture).setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    VideoToImageActivity.time = VideoToImageActivity.this.b.getCurrentPosition() * 1000;
                    VideoToImageActivity.this.k = VideoToImageActivity.getVideoFrame(VideoToImageActivity.PATH);
                    new a().execute(new Void[0]);
                }
            });
            return;
        }
        throw new AssertionError();
    }

    public void storeImage() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.VideoToImage));
        this.l = new File(sb.toString());
        if (!this.l.exists()) {
            this.l.mkdirs();
        }
        Calendar instance = Calendar.getInstance();
        int i2 = instance.get(13);
        int i3 = instance.get(10);
        int i4 = instance.get(12);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(i3);
        sb2.append("-");
        sb2.append(i4);
        sb2.append("-");
        sb2.append(i2);
        this.p = sb2.toString();
        StringBuilder sb3 = new StringBuilder("Image");
        sb3.append(this.p);
        sb3.append(".jpg");
        this.m = sb3.toString();
        this.n = new File(this.l, this.m);
        this.o = this.n.getAbsolutePath();
        try {
            this.t = new FileOutputStream(this.n);
            this.k.compress(CompressFormat.PNG, 90, this.t);
            Context applicationContext = getApplicationContext();
            StringBuilder sb4 = new StringBuilder("Saved\n");
            sb4.append(this.o);
            Toast.makeText(applicationContext, sb4.toString(), Toast.LENGTH_LONG).show();
            this.t.flush();
            this.t.close();
        } catch (Exception unused) {
        }
    }

    public static Bitmap getVideoFrame(String str) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str);
        return mediaMetadataRetriever.getFrameAtTime((long) time);
    }

    public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
        if (z) {
            this.b.seekTo(i2);
            try {
                this.d.setText(formatTimeUnit((long) i2));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2)))});
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return s;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            return s;
        }
        if (itemId == R.id.shareapp) {
            StringBuilder sb = new StringBuilder();
            sb.append(Helper.share_string);
            sb.append(Helper.package_name);
            String sb2 = sb.toString();
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT", sb2);
            startActivity(intent);
        } else if (itemId == R.id.moreapp) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Helper.account_string)));
            } catch (ActivityNotFoundException unused) {
                Toast.makeText(getApplicationContext(), " unable to find market app", Toast.LENGTH_LONG).show();
            }
        } else if (itemId == R.id.rateus) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Helper.package_name)));
            } catch (ActivityNotFoundException unused2) {
                Toast.makeText(getApplicationContext(), " unable to find market app", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override public void onBackPressed() {
        Intent intent = new Intent(this, ListVideoAndMyAlbumActivity.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }


    public void onPause() {
        this.c = this.b.getCurrentPosition();
        super.onPause();
    }


    @Override public void onResume() {
        this.b.seekTo(this.c);
        super.onResume();
    }
}
