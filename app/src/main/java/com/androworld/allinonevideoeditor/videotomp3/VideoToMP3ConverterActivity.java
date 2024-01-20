package com.androworld.allinonevideoeditor.videotomp3;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Media;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androworld.allinonevideoeditor.Adclick;
import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.AudioPlayer;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.UtilCommand;
import com.androworld.allinonevideoeditor.VideoPlayerState;
import com.androworld.allinonevideoeditor.VideoSliceSeekBar;
import com.androworld.allinonevideoeditor.VideoSliceSeekBar.SeekBarChangeListener;
import com.androworld.allinonevideoeditor.listvideowithmymusic.ListVideoAndMyMusicActivity;
import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

@SuppressLint({"WrongConstant"})
public class VideoToMP3ConverterActivity extends AppCompatActivity {
    static final boolean G = true;
    public static String Meta_File_path;
    Boolean A = Boolean.valueOf(false);
    int B = 0;
    int C = 0;
    int D;
    VideoSliceSeekBar E;
    VideoView F;
    public TextView Filename;
    ArrayList<String> a = new ArrayList<>();
    String b;
    String c;
    String d;
    String e;
    String f;
    String g;
    String h;
    String i = "00";
    public TextView iv_aac;
    public TextView iv_mp3;
    String[] j = {"None", "40\nCBR", "48\nCBR", "64\nCBR", "80\nCBR", "96\nCBR", "112\nCBR", "128\nCBR", "160\nCBR", "192\nCBR", "224\nCBR", "256\nCBR", "320\nCBR", "245\nVBR", "225\nVBR", "190\nVBR", "175\nVBR", "165\nVBR", "130\nVBR", "115\nVBR", "100\nVBR", "85\nVBR", " 65\nVBR"};
    Bundle k;
    ImageView l;
    HorizontalListView m;
    ImageView n;
    LinearLayout o;
    LinearLayout p;
    LinearLayout q;
    LinearLayout r;
    FontListAdapter s;
    ProgressDialog t = null;
    public TextView textViewLeft;
    public TextView textViewRight;
    public TextView txt_kbps;
    public TextView txt_selectformat;
    RelativeLayout u;
    RelativeLayout v;
    public VideoPlayerState videoPlayerState = new VideoPlayerState();
    public StateObserver videoStateObserver = new StateObserver();
    RelativeLayout w;
    RelativeLayout x;
    RelativeLayout y;
    RelativeLayout z;
    private Ads ads;

    public class StateObserver extends Handler {
        public boolean alreadyStarted = false;
        public Runnable observerWork;

        public StateObserver() {
            this.observerWork = new Runnable() {
                public void run() {
                    StateObserver.this.startVideoProgressObserving();
                }
            };
        }

        public void startVideoProgressObserving() {
            if (!this.alreadyStarted) {
                this.alreadyStarted = VideoToMP3ConverterActivity.G;
                sendEmptyMessage(0);
            }
        }

        @Override public void handleMessage(Message message) {
            this.alreadyStarted = false;
            VideoToMP3ConverterActivity.this.E.videoPlayingProgress(VideoToMP3ConverterActivity.this.F.getCurrentPosition());
            if (!VideoToMP3ConverterActivity.this.F.isPlaying() || VideoToMP3ConverterActivity.this.F.getCurrentPosition() >= VideoToMP3ConverterActivity.this.E.getRightProgress()) {
                if (VideoToMP3ConverterActivity.this.F.isPlaying()) {
                    VideoToMP3ConverterActivity.this.F.pause();
                    VideoToMP3ConverterActivity.this.A = Boolean.valueOf(false);
                    VideoToMP3ConverterActivity.this.l.setBackgroundResource(R.drawable.play2);
                }
                VideoToMP3ConverterActivity.this.E.setSliceBlocked(false);
                VideoToMP3ConverterActivity.this.E.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.observerWork, 50);
        }
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.videotomp3activity);

        LinearLayout s = (LinearLayout) findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(VideoToMP3ConverterActivity.this,s);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video To MP3");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (G || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(G);
            supportActionBar.setDisplayShowTitleEnabled(false);
            for (String add : this.j) {
                this.a.add(add);
            }
            this.k = getIntent().getExtras();
            this.D = 1;
            if (this.k != null) {
                this.h = this.k.getString("videopath");
                StringBuilder sb = new StringBuilder();
                sb.append("=== videopath");
                sb.append(this.h);
                Log.e("", sb.toString());
                this.videoPlayerState.setFilename(this.h);
            }
            try {
                ThumbVideo(getApplicationContext(), this.h);
            } catch (Exception unused) {
            }
            this.E = (VideoSliceSeekBar) findViewById(R.id.seek_bar);
            this.F = (VideoView) findViewById(R.id.videoView);
            this.l = (ImageView) findViewById(R.id.btnPlayVideo);
            this.textViewLeft = (TextView) findViewById(R.id.left_pointer);
            this.textViewRight = (TextView) findViewById(R.id.right_pointer);
            this.u = (RelativeLayout) findViewById(R.id.rev_format);
            this.Filename = (TextView) findViewById(R.id.Filename);
            this.Filename.setText(new File(this.h).getName());
            this.w = (RelativeLayout) findViewById(R.id.imgbtn_bitrate);
            this.z = (RelativeLayout) findViewById(R.id.back_bitrate);
            this.x = (RelativeLayout) findViewById(R.id.imgbtn_Format);
            this.y = (RelativeLayout) findViewById(R.id.back_Format);
            this.m = (HorizontalListView) findViewById(R.id.hs_Bitrate);
            this.iv_aac = (TextView) findViewById(R.id.iv_aac);
            this.iv_mp3 = (TextView) findViewById(R.id.iv_mp3);
            this.r = (LinearLayout) findViewById(R.id.llBitrate);
            this.q = (LinearLayout) findViewById(R.id.llFormate);
            this.s = new FontListAdapter(getApplicationContext(), this.a);
            this.m.setAdapter((ListAdapter) this.s);
            this.x.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    VideoToMP3ConverterActivity.this.r.setVisibility(8);
                    VideoToMP3ConverterActivity.this.q.setVisibility(0);
                    VideoToMP3ConverterActivity.this.z.setVisibility(8);
                    VideoToMP3ConverterActivity.this.y.setVisibility(0);
                }
            });
            this.w.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    VideoToMP3ConverterActivity.this.r.setVisibility(0);
                    VideoToMP3ConverterActivity.this.q.setVisibility(8);
                    VideoToMP3ConverterActivity.this.z.setVisibility(0);
                    VideoToMP3ConverterActivity.this.y.setVisibility(8);
                }
            });
            this.iv_aac.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    VideoToMP3ConverterActivity.this.d = "AAC";
                    VideoToMP3ConverterActivity.this.iv_aac.setBackgroundResource(R.drawable.bg_round_press);
                    VideoToMP3ConverterActivity.this.iv_aac.setTextColor(Color.parseColor("#ffffff"));
                    VideoToMP3ConverterActivity.this.iv_mp3.setBackgroundResource(R.drawable.bg_round);
                    VideoToMP3ConverterActivity.this.iv_mp3.setTextColor(Color.parseColor("#0f9d58"));
                }
            });
            this.iv_mp3.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    VideoToMP3ConverterActivity.this.d = "MP3";
                    VideoToMP3ConverterActivity.this.iv_aac.setBackgroundResource(R.drawable.bg_round);
                    VideoToMP3ConverterActivity.this.iv_aac.setTextColor(Color.parseColor("#0f9d58"));
                    VideoToMP3ConverterActivity.this.iv_mp3.setBackgroundResource(R.drawable.bg_round_press);
                    VideoToMP3ConverterActivity.this.iv_mp3.setTextColor(Color.parseColor("#ffffff"));
                }
            });
            this.m.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    FileUtils.Bitrate = i;
                    VideoToMP3ConverterActivity.this.s.notifyDataSetChanged();
                    if (i == 0) {
                        try {
                            VideoToMP3ConverterActivity.this.g = "None";
                            VideoToMP3ConverterActivity.this.f = "None";
                            VideoToMP3ConverterActivity.this.e = "None";
                            VideoToMP3ConverterActivity.this.txt_kbps.setText("None");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (i == 1) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 40 (CBR)");
                            VideoToMP3ConverterActivity.this.g = "-ab";
                            VideoToMP3ConverterActivity.this.f = "40k";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    } else if (i == 2) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 48 (CBR)");
                            VideoToMP3ConverterActivity.this.g = "-ab";
                            VideoToMP3ConverterActivity.this.f = "48k";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    } else if (i == 3) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 64 (CBR)");
                            VideoToMP3ConverterActivity.this.g = "-ab";
                            VideoToMP3ConverterActivity.this.f = "64k";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                    } else if (i == 4) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 80 (CBR)");
                            VideoToMP3ConverterActivity.this.g = "-ab";
                            VideoToMP3ConverterActivity.this.f = "80k";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e5) {
                            e5.printStackTrace();
                        }
                    } else if (i == 5) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 96 (CBR)");
                            VideoToMP3ConverterActivity.this.g = "-ab";
                            VideoToMP3ConverterActivity.this.f = "96k";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e6) {
                            e6.printStackTrace();
                        }
                    } else if (i == 6) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 112 (CBR)");
                            VideoToMP3ConverterActivity.this.g = "-ab";
                            VideoToMP3ConverterActivity.this.f = "112k";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e7) {
                            e7.printStackTrace();
                        }
                    } else if (i == 7) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 128 (CBR)");
                            VideoToMP3ConverterActivity.this.g = "-ab";
                            VideoToMP3ConverterActivity.this.f = "128k";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e8) {
                            e8.printStackTrace();
                        }
                    } else if (i == 8) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 160 (CBR)");
                            VideoToMP3ConverterActivity.this.g = "-ab";
                            VideoToMP3ConverterActivity.this.f = "160k";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e9) {
                            e9.printStackTrace();
                        }
                    } else if (i == 9) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 192 (CBR)");
                            VideoToMP3ConverterActivity.this.g = "-ab";
                            VideoToMP3ConverterActivity.this.f = "192k";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e10) {
                            e10.printStackTrace();
                        }
                    } else if (i == 10) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 224 (CBR)");
                            VideoToMP3ConverterActivity.this.g = "-ab";
                            VideoToMP3ConverterActivity.this.f = "224k";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e11) {
                            e11.printStackTrace();
                        }
                    } else if (i == 11) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 256 (CBR)");
                            VideoToMP3ConverterActivity.this.g = "-ab";
                            VideoToMP3ConverterActivity.this.f = "256k";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e12) {
                            e12.printStackTrace();
                        }
                    } else if (i == 12) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 320 (CBR)");
                            VideoToMP3ConverterActivity.this.g = "-ab";
                            VideoToMP3ConverterActivity.this.f = "320k";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e13) {
                            e13.printStackTrace();
                        }
                    } else if (i == 13) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 245 (VBR)");
                            VideoToMP3ConverterActivity.this.g = "-q:a";
                            VideoToMP3ConverterActivity.this.f = "0";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e14) {
                            e14.printStackTrace();
                        }
                    } else if (i == 14) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 225 (VBR)");
                            VideoToMP3ConverterActivity.this.g = "-q:a";
                            VideoToMP3ConverterActivity.this.f = "1";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e15) {
                            e15.printStackTrace();
                        }
                    } else if (i == 15) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 190 (VBR)");
                            VideoToMP3ConverterActivity.this.g = "-q:a";
                            VideoToMP3ConverterActivity.this.f = "2";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e16) {
                            e16.printStackTrace();
                        }
                    } else if (i == 16) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 175 (VBR)");
                            VideoToMP3ConverterActivity.this.g = "-q:a";
                            VideoToMP3ConverterActivity.this.f = "3";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e17) {
                            e17.printStackTrace();
                        }
                    } else if (i == 17) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 165 (VBR)");
                            VideoToMP3ConverterActivity.this.g = "-q:a";
                            VideoToMP3ConverterActivity.this.f = "4";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e18) {
                            e18.printStackTrace();
                        }
                    } else if (i == 18) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 130 (VBR)");
                            VideoToMP3ConverterActivity.this.g = "-q:a";
                            VideoToMP3ConverterActivity.this.f = "5";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e19) {
                            e19.printStackTrace();
                        }
                    } else if (i == 19) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 115 (VBR)");
                            VideoToMP3ConverterActivity.this.g = "-q:a";
                            VideoToMP3ConverterActivity.this.f = "6";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e20) {
                            e20.printStackTrace();
                        }
                    } else if (i == 20) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 100 (VBR)");
                            VideoToMP3ConverterActivity.this.g = "-q:a";
                            VideoToMP3ConverterActivity.this.f = "7";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e21) {
                            e21.printStackTrace();
                        }
                    } else if (i == 21) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 85 (VBR)");
                            VideoToMP3ConverterActivity.this.g = "-q:a";
                            VideoToMP3ConverterActivity.this.f = "8";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e22) {
                            e22.printStackTrace();
                        }
                    } else if (i == 22) {
                        try {
                            VideoToMP3ConverterActivity.this.txt_kbps.setText(" 65 (VBR)");
                            VideoToMP3ConverterActivity.this.g = "-q:a";
                            VideoToMP3ConverterActivity.this.f = "9";
                            VideoToMP3ConverterActivity.this.e = "-vn";
                        } catch (Exception e23) {
                            e23.printStackTrace();
                        }
                    }
                }
            });
            this.txt_selectformat = (TextView) findViewById(R.id.txt_selectformat);
            this.txt_selectformat.setText("MP3");
            initVideoView();
            this.txt_kbps = (TextView) findViewById(R.id.txt_kbps);
            this.txt_kbps.setText(" None ");
            this.d = "MP3";
            this.g = "None";
            this.p = (LinearLayout) findViewById(R.id.lnr_pop);
            this.u.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                }
            });
            this.o = (LinearLayout) findViewById(R.id.lnr_popkbps);
            this.o.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                }
            });
            this.v = (RelativeLayout) findViewById(R.id.rev_bitrate);
            this.v.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                }
            });
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
        Intent intent = new Intent(this, AudioPlayer.class);
        Bundle bundle = new Bundle();
        bundle.putString("song", this.c);
        bundle.putBoolean("isfrom", G);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void d() {
        String valueOf = String.valueOf(this.B / 1000);
        String valueOf2 = String.valueOf((this.C / 1000) - (this.B / 1000));
        String[] strArr = !this.g.equals("None") ? new String[]{"-y", "-i", this.h, "-vn", "-acodec", "copy", this.g, this.f, this.e, "-strict", "experimental", "-ss", valueOf, "-t", valueOf2, this.c} : this.d.equals("MP3") ? new String[]{"-y", "-i", this.h, "-vn", "-acodec", "copy", "-strict", "experimental", "-ss", valueOf, "-t", valueOf2, this.c} : this.d.equals("AAC") ? new String[]{"-y", "-ss", valueOf, "-t", valueOf2, "-i", this.h, "-vn", "-acodec", "copy", "-strict", "experimental", this.c} : null;
        for (int i2 = 0; i2 < strArr.length; i2++) {
            StringBuilder sb = new StringBuilder();
            sb.append(i2);
            sb.append(strArr[i2]);
            Log.e("", sb.toString());
        }
        a(strArr, this.c);
    }

    private void a(String[] strArr, final String str) {
            this.t = new ProgressDialog(this);
            this.t.setProgressStyle(1);
            this.t.setIndeterminate(G);
            this.t.setMessage("Please wait...");
            this.t.setCancelable(false);
            this.t.show();
            String ffmpegCommand = UtilCommand.main(strArr);
            FFmpeg.executeAsync(ffmpegCommand, new ExecuteCallback() {

                @Override
                public void apply(final long executionId, final int returnCode) {
                    Log.d("TAG", String.format("FFmpeg process exited with rc %d.", returnCode));

                    Log.d("TAG", "FFmpeg process output:");

                    Config.printLastCommandOutput(Log.INFO);

                    t.dismiss();
                    if (returnCode == RETURN_CODE_SUCCESS) {
                        VideoToMP3ConverterActivity.this.t.dismiss();
                        if (VideoToMP3ConverterActivity.this.d.equals("MP3")) {
                            StringBuilder sb = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getPath()));
                            sb.append("/VEditor/VideoToMP3/");
                            File file = new File(sb.toString());
                            String substring = VideoToMP3ConverterActivity.this.c.substring(VideoToMP3ConverterActivity.this.c.lastIndexOf("/") + 1);
                            String substring2 = substring.substring(0, substring.lastIndexOf("."));
                            if (file.exists()) {
                                File file2 = new File(file, substring);
                                StringBuilder sb2 = new StringBuilder(String.valueOf(substring2));
                                sb2.append(".mp3");
                                File file3 = new File(file, sb2.toString());
                                if (file2.exists()) {
                                    file2.renameTo(file3);
                                }
                                VideoToMP3ConverterActivity.this.c = file3.getPath();
                            }
                        }
                        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                        intent.setData(Uri.fromFile(new File(VideoToMP3ConverterActivity.this.c)));
                        VideoToMP3ConverterActivity.this.sendBroadcast(intent);
                        VideoToMP3ConverterActivity.this.scanMedia(VideoToMP3ConverterActivity.this.c);
                        Intent intent2 = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                        intent2.setData(Uri.fromFile(new File(VideoToMP3ConverterActivity.this.c)));
                        VideoToMP3ConverterActivity.this.sendBroadcast(intent2);
                        VideoToMP3ConverterActivity.this.b();
                        VideoToMP3ConverterActivity.this.refreshGallery(str);

                    } else if (returnCode == RETURN_CODE_CANCEL) {
                        Log.d("ffmpegfailure", str);
                        try {
                            new File(str).delete();
                            VideoToMP3ConverterActivity.this.deleteFromGallery(str);
                            Toast.makeText(VideoToMP3ConverterActivity.this, "Error Creating Video", 0).show();
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    } else {
                        Log.d("ffmpegfailure", str);
                        try {
                            new File(str).delete();
                            VideoToMP3ConverterActivity.this.deleteFromGallery(str);
                            Toast.makeText(VideoToMP3ConverterActivity.this, "Error Creating Video", 0).show();
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }


                }
            });

            getWindow().clearFlags(16);
    }

    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2)))});
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public void initVideoView() {
        this.F.setVideoPath(this.h);
        try {
            this.F.seekTo(200);
        } catch (Exception unused) {
        }
        this.F.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                Toast.makeText(VideoToMP3ConverterActivity.this.getApplicationContext(), "Video Player Not Supproting", 0).show();
                return VideoToMP3ConverterActivity.G;
            }
        });
        this.F.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                VideoToMP3ConverterActivity.this.F.pause();
                VideoToMP3ConverterActivity.this.l.setBackgroundResource(R.drawable.play2);
                VideoToMP3ConverterActivity.this.A = Boolean.valueOf(false);
            }
        });
        this.F.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (VideoToMP3ConverterActivity.this.A.booleanValue()) {
                    VideoToMP3ConverterActivity.this.F.pause();
                    VideoToMP3ConverterActivity.this.A = Boolean.valueOf(false);
                    VideoToMP3ConverterActivity.this.l.setBackgroundResource(R.drawable.play2);
                }
                return VideoToMP3ConverterActivity.G;
            }
        });
        this.F.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                VideoToMP3ConverterActivity.this.E.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (VideoToMP3ConverterActivity.this.E.getSelectedThumb() == 1) {
                            VideoToMP3ConverterActivity.this.F.seekTo(VideoToMP3ConverterActivity.this.E.getLeftProgress());
                        }
                        try {
                            VideoToMP3ConverterActivity.this.textViewLeft.setText(VideoToMP3ConverterActivity.formatTimeUnit((long) i));
                            VideoToMP3ConverterActivity.this.textViewRight.setText(VideoToMP3ConverterActivity.formatTimeUnit((long) i2));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        VideoToMP3ConverterActivity.this.i = VideoToMP3ConverterActivity.getTimeForTrackFormat(i, VideoToMP3ConverterActivity.G);
                        VideoToMP3ConverterActivity.this.videoPlayerState.setStart(i);
                        VideoToMP3ConverterActivity.this.b = VideoToMP3ConverterActivity.getTimeForTrackFormat(i2, VideoToMP3ConverterActivity.G);
                        VideoToMP3ConverterActivity.this.videoPlayerState.setStop(i2);
                        VideoToMP3ConverterActivity.this.B = i;
                        VideoToMP3ConverterActivity.this.C = i2;
                    }
                });
                VideoToMP3ConverterActivity.this.b = VideoToMP3ConverterActivity.getTimeForTrackFormat(mediaPlayer.getDuration(), VideoToMP3ConverterActivity.G);
                VideoToMP3ConverterActivity.this.E.setMaxValue(mediaPlayer.getDuration());
                VideoToMP3ConverterActivity.this.E.setLeftProgress(0);
                VideoToMP3ConverterActivity.this.E.setRightProgress(mediaPlayer.getDuration());
                VideoToMP3ConverterActivity.this.E.setProgressMinDiff(0);
                VideoToMP3ConverterActivity.this.F.seekTo(200);
                VideoToMP3ConverterActivity.this.l.setOnClickListener(new OnClickListener() {
                    @Override public void onClick(View view) {
                        if (VideoToMP3ConverterActivity.this.A.booleanValue()) {
                            VideoToMP3ConverterActivity.this.l.setBackgroundResource(R.drawable.play2);
                            VideoToMP3ConverterActivity.this.A = Boolean.valueOf(false);
                        } else {
                            VideoToMP3ConverterActivity.this.l.setBackgroundResource(R.drawable.pause2);
                            VideoToMP3ConverterActivity.this.A = Boolean.valueOf(VideoToMP3ConverterActivity.G);
                        }
                        VideoToMP3ConverterActivity.this.performVideoViewClick();
                    }
                });
            }
        });
        this.b = getTimeForTrackFormat(this.F.getDuration(), G);
    }


    @Override public void onResume() {
        super.onResume();
        this.d = "MP3";
        this.g = "None";
    }

    public void ThumbVideo(Context context, String str) {
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        String[] strArr = {"_data", "_id"};
        StringBuilder sb = new StringBuilder();
        sb.append("%");
        sb.append(str);
        sb.append("%");
        Cursor managedQuery = managedQuery(uri, strArr, "_data  like ?", new String[]{sb.toString()}, " _id DESC");
        int count = managedQuery.getCount();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("count");
        sb2.append(count);
        Log.e("", sb2.toString());
        if (count > 0) {
            try {
                managedQuery.moveToFirst();
                for (int i2 = 0; i2 < count; i2++) {
                    try {
                        Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, getLong(managedQuery));
                        managedQuery.getString(managedQuery.getColumnIndex("_data"));
                        Bitmap thumbnail = Thumbnails.getThumbnail(getContentResolver(), Long.valueOf(managedQuery.getLong(managedQuery.getColumnIndexOrThrow("_id"))).longValue(), 1, null);
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Bitmap");
                        sb3.append(thumbnail);
                        Log.e("", sb3.toString());
                        managedQuery.moveToNext();
                    } catch (IllegalArgumentException e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            }
        }
    }

    public static String getLong(Cursor cursor) {
        return String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow("_id")));
    }

    public static String getTimeForTrackFormat(int i2, boolean z2) {
        int i3 = i2 / 3600000;
        int i4 = i2 / 60000;
        int i5 = (i2 - ((i4 * 60) * 1000)) / 1000;
        StringBuilder sb = new StringBuilder(String.valueOf((!z2 || i3 >= 10) ? "" : "0"));
        sb.append(i3);
        sb.append(":");
        StringBuilder sb2 = new StringBuilder(String.valueOf(sb.toString()));
        sb2.append((!z2 || i4 >= 10) ? "" : "0");
        StringBuilder sb3 = new StringBuilder(String.valueOf(sb2.toString()));
        sb3.append(i4 % 60);
        sb3.append(":");
        String sb4 = sb3.toString();
        if (i5 < 10) {
            StringBuilder sb5 = new StringBuilder(String.valueOf(sb4));
            sb5.append("0");
            sb5.append(i5);
            return sb5.toString();
        }
        StringBuilder sb6 = new StringBuilder(String.valueOf(sb4));
        sb6.append(i5);
        return sb6.toString();
    }

    public void performVideoViewClick() {
        if (this.F.isPlaying()) {
            this.F.pause();
            this.E.setSliceBlocked(false);
            this.E.removeVideoStatusThumb();
            return;
        }
        this.F.seekTo(this.E.getLeftProgress());
        this.F.start();
        this.E.videoPlayingProgress(this.E.getLeftProgress());
        this.videoStateObserver.startVideoProgressObserving();
    }

    public void scanMedia(String str) {
        String substring = str.substring(str.lastIndexOf("/") + 1);
        String substring2 = substring.substring(0, substring.lastIndexOf("."));
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", str);
        contentValues.put("title", substring2);
        contentValues.put("_size", Integer.valueOf(str.length()));
        contentValues.put("mime_type", "audio/mp3");
        contentValues.put("artist", getResources().getString(R.string.app_name));
        contentValues.put("is_ringtone", Boolean.valueOf(G));
        contentValues.put("is_notification", Boolean.valueOf(false));
        contentValues.put("is_alarm", Boolean.valueOf(false));
        contentValues.put("is_music", Boolean.valueOf(false));
        Uri contentUriForPath = Audio.Media.getContentUriForPath(str);
        StringBuilder sb = new StringBuilder();
        sb.append("=====Enter ====");
        sb.append(contentUriForPath);
        Log.e("", sb.toString());
        ContentResolver contentResolver = getContentResolver();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("_data=\"");
        sb2.append(str);
        sb2.append("\"");
        contentResolver.delete(contentUriForPath, sb2.toString(), null);
        getApplicationContext().getContentResolver().insert(contentUriForPath, contentValues);
    }

    @Override public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (intent != null) {
            Uri data = intent.getData();
            StringBuilder sb = new StringBuilder();
            sb.append("File Path :");
            sb.append(data);
            Log.e("", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Final Image Path :");
            sb2.append(getRealPathFromURI(data));
            Log.e("", sb2.toString());
            String realPathFromURI = getRealPathFromURI(data);
            Meta_File_path = realPathFromURI;
            this.n.setImageBitmap(rotateBitmapOrientation(realPathFromURI));
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor managedQuery = managedQuery(uri, new String[]{"_data"}, null, null, null);
        int columnIndexOrThrow = managedQuery.getColumnIndexOrThrow("_data");
        managedQuery.moveToFirst();
        return managedQuery.getString(columnIndexOrThrow);
    }

    public Bitmap rotateBitmapOrientation(String str) {
        ExifInterface exifInterface;
        Options options = new Options();
        int i2 = 1;
        options.inJustDecodeBounds = G;
        BitmapFactory.decodeFile(str, options);
        Bitmap decodeFile = BitmapFactory.decodeFile(str, new Options());
        try {
            exifInterface = new ExifInterface(str);
        } catch (IOException e2) {
            e2.printStackTrace();
            exifInterface = null;
        }
        String attribute = exifInterface.getAttribute("Orientation");
        if (attribute != null) {
            i2 = Integer.parseInt(attribute);
        }
        int i3 = 0;
        if (i2 == 6) {
            i3 = 90;
        }
        if (i2 == 3) {
            i3 = 180;
        }
        if (i2 == 8) {
            i3 = 270;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate((float) i3, ((float) decodeFile.getWidth()) / 2.0f, ((float) decodeFile.getHeight()) / 2.0f);
        return Bitmap.createBitmap(decodeFile, 0, 0, options.outWidth, options.outHeight, matrix, G);
    }


    public void f() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                VideoToMP3ConverterActivity.this.finish();
            }
        }).create().show();
    }

    public void deleteFromGallery(String str) {
        String[] strArr = {"_id"};
        String[] strArr2 = {str};
        Uri uri = Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor query = contentResolver.query(uri, strArr, "_data = ?", strArr2, null);
        if (query.moveToFirst()) {
            try {
                contentResolver.delete(ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, query.getLong(query.getColumnIndexOrThrow("_id"))), null, null);
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
        Intent intent = new Intent(this, ListVideoAndMyMusicActivity.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return G;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return G;
        }
        if (menuItem.getItemId() == R.id.Done) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb.append("/");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.VideoToMP3));
            sb.append("/");
            File file = new File(sb.toString());
            if (!file.exists()) {
                file.mkdirs();
            }
            new SimpleDateFormat("_HHmmss", Locale.US).format(new Date());
            if (this.F.isPlaying()) {
                this.F.pause();
                this.l.setBackgroundResource(R.drawable.play2);
                this.A = Boolean.valueOf(false);
            }
            try {
                if (this.d.equals("MP3")) {
                    this.c = FileUtils.getTargetFileName(this, this.h);
                } else if (this.d.equals("AAC")) {
                    String substring = this.h.substring(this.h.lastIndexOf("/") + 1);
                    String substring2 = substring.substring(0, substring.lastIndexOf("."));
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                    sb2.append("/");
                    sb2.append(getResources().getString(R.string.MainFolderName));
                    sb2.append("/");
                    sb2.append(getResources().getString(R.string.VideoToMP3));
                    sb2.append("/");
                    sb2.append(substring2);
                    sb2.append(System.currentTimeMillis());
                    sb2.append(".aac");
                    this.c = sb2.toString();
                }
            } catch (Exception unused) {
            }
            d();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
