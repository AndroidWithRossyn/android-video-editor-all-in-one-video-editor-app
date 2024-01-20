package com.androworld.allinonevideoeditor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Video.Media;
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
import androidx.core.content.FileProvider;

import com.androworld.allinonevideoeditor.listvideoandmyvideo.ListVideoAndMyAlbumActivity;
import com.androworld.allinonevideoeditor.phototovideo.SelectImageAndMyVideoActivity;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant"})
public class VideoPlayer extends AppCompatActivity implements OnSeekBarChangeListener {
    static final boolean r = true;
    Bundle a;
    ImageView b;
    int c = 0;
    Handler d = new Handler();
    boolean e = false;
    int f = 0;
    SeekBar g;
    Uri h;
    TextView i;
    TextView j;
    TextView k;
    TextView l;
    String m = "";
    VideoView n;
    Uri o;
    Runnable p = new Runnable() {
        public void run() {
            if (VideoPlayer.this.n.isPlaying()) {
                int currentPosition = VideoPlayer.this.n.getCurrentPosition();
                VideoPlayer.this.g.setProgress(currentPosition);
                try {
                    VideoPlayer.this.i.setText(VideoPlayer.formatTimeUnit((long) currentPosition));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (currentPosition == VideoPlayer.this.c) {
                    VideoPlayer.this.g.setProgress(0);
                    VideoPlayer.this.i.setText("00:00");
                    VideoPlayer.this.d.removeCallbacks(VideoPlayer.this.p);
                    return;
                }
                VideoPlayer.this.d.postDelayed(VideoPlayer.this.p, 200);
                return;
            }
            VideoPlayer.this.g.setProgress(VideoPlayer.this.c);
            try {
                VideoPlayer.this.i.setText(VideoPlayer.formatTimeUnit((long) VideoPlayer.this.c));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
            VideoPlayer.this.d.removeCallbacks(VideoPlayer.this.p);
        }
    };
    OnClickListener q = new OnClickListener() {
        @Override public void onClick(View view) {
            if (VideoPlayer.this.e) {
                try {
                    VideoPlayer.this.n.pause();
                    VideoPlayer.this.d.removeCallbacks(VideoPlayer.this.p);
                    VideoPlayer.this.b.setBackgroundResource(R.drawable.play2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    VideoPlayer.this.n.seekTo(VideoPlayer.this.g.getProgress());
                    VideoPlayer.this.n.start();
                    VideoPlayer.this.d.postDelayed(VideoPlayer.this.p, 200);
                    VideoPlayer.this.n.setVisibility(0);
                    VideoPlayer.this.b.setBackgroundResource(R.drawable.pause2);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            VideoPlayer.this.e ^= VideoPlayer.r;
        }
    };
    private AdView s;
    private Ads ads;

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.videoplayer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("My Video");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (r || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(r);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.a = getIntent().getExtras();
            if (this.a != null) {
                this.m = this.a.getString("song");
                this.f = this.a.getInt("position", 0);
            }
            try {
                GetVideo(getApplicationContext(), this.m);
            } catch (Exception unused) {
            }
            this.k = (TextView) findViewById(R.id.Filename);
            this.n = (VideoView) findViewById(R.id.videoView);
            this.g = (SeekBar) findViewById(R.id.sbVideo);
            this.g.setOnSeekBarChangeListener(this);
            this.i = (TextView) findViewById(R.id.left_pointer);
            this.j = (TextView) findViewById(R.id.right_pointer);
            this.b = (ImageView) findViewById(R.id.btnPlayVideo);
            this.l = (TextView) findViewById(R.id.dur);
            this.k.setText(new File(this.m).getName());
            this.n.setVideoPath(this.m);
            this.n.seekTo(100);
            this.n.setOnErrorListener(new OnErrorListener() {
                public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    Toast.makeText(VideoPlayer.this.getApplicationContext(), "Video Player Not Supproting", 0).show();
                    return VideoPlayer.r;
                }
            });
            this.n.setOnPreparedListener(new OnPreparedListener() {
                public void onPrepared(MediaPlayer mediaPlayer) {
                    VideoPlayer.this.c = VideoPlayer.this.n.getDuration();
                    VideoPlayer.this.g.setMax(VideoPlayer.this.c);
                    VideoPlayer.this.i.setText("00:00");
                    try {
                        TextView textView = VideoPlayer.this.l;
                        StringBuilder sb = new StringBuilder();
                        sb.append("duration : ");
                        sb.append(VideoPlayer.formatTimeUnit((long) VideoPlayer.this.c));
                        textView.setText(sb.toString());
                        VideoPlayer.this.j.setText(VideoPlayer.formatTimeUnit((long) VideoPlayer.this.c));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.n.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    VideoPlayer.this.n.setVisibility(0);
                    VideoPlayer.this.b.setBackgroundResource(R.drawable.play2);
                    VideoPlayer.this.n.seekTo(0);
                    VideoPlayer.this.g.setProgress(0);
                    VideoPlayer.this.i.setText("00:00");
                    VideoPlayer.this.d.removeCallbacks(VideoPlayer.this.p);
                    VideoPlayer.this.e ^= VideoPlayer.r;
                }
            });
            this.b.setOnClickListener(this.q);
            LinearLayout s = (LinearLayout) findViewById(R.id.banner_AdView);
            ads = new Ads();
            ads.BannerAd(VideoPlayer.this,s);

        } else {
            throw new AssertionError();
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
            return false;
        }
        return r;
    }

    public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
        if (z) {
            this.n.seekTo(i2);
            try {
                this.i.setText(formatTimeUnit((long) i2));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2)))});
    }

    public void GetVideo(Context context, String str) {
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        String[] strArr = {"_id", "_data", "_display_name", "_size", "duration", "date_added", "album"};
        StringBuilder sb = new StringBuilder();
        sb.append("%");
        sb.append(str);
        sb.append("%");
        String[] strArr2 = {sb.toString()};
        Cursor managedQuery = managedQuery(uri, strArr, "_data  like ?", strArr2, " _id DESC");
        if (managedQuery.moveToFirst()) {
            try {
                Uri withAppendedPath = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, getLong(managedQuery));
                this.o = withAppendedPath;
                this.h = withAppendedPath;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static String getLong(Cursor cursor) {
        return String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow("_id")));
    }

    public void Delete() {
        new AlertDialog.Builder(this).setMessage("Are you sure you want to delete this file ?").setPositiveButton("delete", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                File file = new File(VideoPlayer.this.m);
                if (file.exists()) {
                    file.delete();
                    try {
                        ContentResolver contentResolver = VideoPlayer.this.getContentResolver();
                        Uri uri = VideoPlayer.this.o;
                        StringBuilder sb = new StringBuilder();
                        sb.append("_data=\"");
                        sb.append(VideoPlayer.this.m);
                        sb.append("\"");
                        contentResolver.delete(uri, sb.toString(), null);
                    } catch (Exception unused) {
                    }
                }
                VideoPlayer.this.onBackPressed();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setCancelable(r).show();
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        if (Helper.ModuleId == 1) {
            Intent intent = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent.setFlags(67108864);
            startActivity(intent);
            finish();
        } else if (Helper.ModuleId == 2) {
            Intent intent2 = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent2.setFlags(67108864);
            startActivity(intent2);
            finish();
        } else if (Helper.ModuleId == 4) {
            Intent intent3 = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent3.setFlags(67108864);
            startActivity(intent3);
            finish();
        } else if (Helper.ModuleId == 5) {
            Intent intent4 = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent4.setFlags(67108864);
            startActivity(intent4);
            finish();
        } else if (Helper.ModuleId == 6) {
            Intent intent5 = new Intent(this, com.androworld.allinonevideoeditor.videojoiner.ListVideoAndMyAlbumActivity.class);
            intent5.setFlags(67108864);
            startActivity(intent5);
            finish();
        } else if (Helper.ModuleId == 8) {
            Intent intent6 = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent6.setFlags(67108864);
            startActivity(intent6);
            finish();
        } else if (Helper.ModuleId == 9) {
            Intent intent7 = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent7.setFlags(67108864);
            startActivity(intent7);
            finish();
        } else if (Helper.ModuleId == 10) {
            Intent intent8 = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent8.setFlags(67108864);
            startActivity(intent8);
            finish();
        } else if (Helper.ModuleId == 11) {
            Intent intent9 = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent9.setFlags(67108864);
            startActivity(intent9);
            finish();
        } else if (Helper.ModuleId == 13) {
            Intent intent10 = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent10.setFlags(67108864);
            startActivity(intent10);
            finish();
        } else if (Helper.ModuleId == 14) {
            Intent intent11 = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent11.setFlags(67108864);
            startActivity(intent11);
            finish();
        } else if (Helper.ModuleId == 15) {
            Intent intent12 = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent12.setFlags(67108864);
            startActivity(intent12);
            finish();
        } else if (Helper.ModuleId == 16) {
            Intent intent13 = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent13.setFlags(67108864);
            startActivity(intent13);
            finish();
        } else if (Helper.ModuleId == 22) {
            Intent intent14 = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent14.setFlags(67108864);
            startActivity(intent14);
            finish();
        } else if (Helper.ModuleId == 21) {
            Intent intent15 = new Intent(this, SelectImageAndMyVideoActivity.class);
            intent15.setFlags(67108864);
            startActivity(intent15);
            finish();
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_deleteshare, menu);
        return r;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return r;
        }
        if (menuItem.getItemId() == R.id.Delete) {
            if (this.n.isPlaying()) {
                try {
                    this.n.pause();
                    this.d.removeCallbacks(this.p);
                    this.b.setBackgroundResource(R.drawable.play2);
                    this.e = false;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            Delete();
        } else if (menuItem.getItemId() == R.id.Share) {
            if (this.n.isPlaying()) {
                this.n.pause();
                this.d.removeCallbacks(this.p);
                this.b.setBackgroundResource(R.drawable.play2);
                this.e = false;
            }
            try {
                File file = new File(VideoPlayer.this.m);

                Uri screenshotUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("video/*");
                intent.putExtra("android.intent.extra.STREAM", screenshotUri);
                startActivity(Intent.createChooser(intent, "Share File"));

            } catch (Exception unused) {
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
