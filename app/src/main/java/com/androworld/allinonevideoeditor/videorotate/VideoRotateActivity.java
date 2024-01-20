package com.androworld.allinonevideoeditor.videorotate;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androworld.allinonevideoeditor.Adclick;
import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.CustomEditText;
import com.androworld.allinonevideoeditor.CustomTextView;
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


import java.io.File;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

@SuppressLint({"NewApi", "ResourceType"})
public class VideoRotateActivity extends AppCompatActivity {
    static final boolean H = true;
    RelativeLayout A;
    RelativeLayout B;
    RelativeLayout C;
    Dialog D;
    OnClickListener E = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (VideoRotateActivity.this.o.booleanValue()) {
                VideoRotateActivity.this.a.setBackgroundResource(R.drawable.play2);
                VideoRotateActivity.this.o = Boolean.valueOf(false);
            } else {
                VideoRotateActivity.this.a.setBackgroundResource(R.drawable.pause2);
                VideoRotateActivity.this.o = Boolean.valueOf(VideoRotateActivity.H);
            }
            VideoRotateActivity.this.e();
        }
    };
    Runnable F = new Runnable() {
        @SuppressLint({"SetTextI18n"})
        public void run() {
            if (VideoRotateActivity.this.u.isPlaying()) {
                int currentPosition = VideoRotateActivity.this.u.getCurrentPosition();
                VideoRotateActivity.this.p.setProgress(currentPosition);
                try {
                    TextView textView = VideoRotateActivity.this.r;
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(VideoRotateActivity.formatTimeUnit((long) currentPosition));
                    textView.setText(sb.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (currentPosition == VideoRotateActivity.this.b) {
                    VideoRotateActivity.this.p.setProgress(0);
                    VideoRotateActivity.this.r.setText("00:00");
                    VideoRotateActivity.this.l.removeCallbacks(VideoRotateActivity.this.F);
                    return;
                }
                VideoRotateActivity.this.l.postDelayed(VideoRotateActivity.this.F, 500);
                return;
            }
            VideoRotateActivity.this.p.setProgress(VideoRotateActivity.this.b);
            try {
                TextView textView2 = VideoRotateActivity.this.r;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("");
                sb2.append(VideoRotateActivity.formatTimeUnit((long) VideoRotateActivity.this.b));
                textView2.setText(sb2.toString());
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
            VideoRotateActivity.this.l.removeCallbacks(VideoRotateActivity.this.F);
        }
    };
    Runnable G = new Runnable() {
        public void run() {
            VideoRotateActivity.this.k.removeCallbacks(VideoRotateActivity.this.G);
            VideoRotateActivity.this.b();
        }
    };

    public VideoPlayerState I = new VideoPlayerState();
    private a J = new a();
    ImageView a;
    int b = 0;
    int c = 0;
    int d = 0;
    String e;
    String f;
    String g = "90";
    String h = "00";
    String i;
    String j = "";
    Handler k = new Handler();
    Handler l = new Handler();
    boolean m = false;
    Context n;
    Boolean o = Boolean.valueOf(false);
    SeekBar p;
    TextView q;
    TextView r;
    TextView s;
    VideoSliceSeekBar t;
    VideoView u;
    RelativeLayout v;
    RelativeLayout w;
    RelativeLayout x;
    RelativeLayout y;
    RelativeLayout z;
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
                this.b = VideoRotateActivity.H;
                sendEmptyMessage(0);
            }
        }

        @Override
        public void handleMessage(Message message) {
            this.b = false;
            VideoRotateActivity.this.t.videoPlayingProgress(VideoRotateActivity.this.u.getCurrentPosition());
            if (!VideoRotateActivity.this.u.isPlaying() || VideoRotateActivity.this.u.getCurrentPosition() >= VideoRotateActivity.this.t.getRightProgress()) {
                if (VideoRotateActivity.this.u.isPlaying()) {
                    VideoRotateActivity.this.u.pause();
                    VideoRotateActivity.this.a.setBackgroundResource(R.drawable.play2);
                    VideoRotateActivity.this.o = Boolean.valueOf(false);
                }
                VideoRotateActivity.this.t.setSliceBlocked(false);
                VideoRotateActivity.this.t.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.c, 50);
        }
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.videorotateactivity);

        LinearLayout sa = (LinearLayout) findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(VideoRotateActivity.this, sa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Rotate");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (H || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(H);
            supportActionBar.setDisplayShowTitleEnabled(false);
            Intent intent = getIntent();
            this.n = this;
            this.j = intent.getStringExtra("videoPath");
            this.t = (VideoSliceSeekBar) findViewById(R.id.sbVideo);
            this.u = (VideoView) findViewById(R.id.vvScreen);
            this.r = (TextView) findViewById(R.id.tvStartVideo);
            this.q = (TextView) findViewById(R.id.tvEndVideo);
            this.f = this.j.substring(this.j.lastIndexOf(".") + 1);
            MediaScannerConnection.scanFile(this, new String[]{new File(this.j).getAbsolutePath()}, new String[]{this.f}, null);
            d();
            this.a = (ImageView) findViewById(R.id.btnPlayVideo);
            this.a.setOnClickListener(this.E);
            this.v = (RelativeLayout) findViewById(R.id.btn_rotate180);
            this.w = (RelativeLayout) findViewById(R.id.btn_rotate90);
            this.x = (RelativeLayout) findViewById(R.id.btn_rotate270);
            this.y = (RelativeLayout) findViewById(R.id.btn_custom);
            this.z = (RelativeLayout) findViewById(R.id.back_rotate180);
            this.A = (RelativeLayout) findViewById(R.id.back_rotate90);
            this.B = (RelativeLayout) findViewById(R.id.back_rotate270);
            this.C = (RelativeLayout) findViewById(R.id.back_custom);
            this.s = (TextView) findViewById(R.id.Filename);
            this.s.setText(new File(this.j).getName());
            this.v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    VideoRotateActivity.this.z.setVisibility(0);
                    VideoRotateActivity.this.A.setVisibility(8);
                    VideoRotateActivity.this.B.setVisibility(8);
                    VideoRotateActivity.this.C.setVisibility(8);
                    VideoRotateActivity.this.g = "180";
                }
            });
            this.w.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    VideoRotateActivity.this.z.setVisibility(8);
                    VideoRotateActivity.this.A.setVisibility(0);
                    VideoRotateActivity.this.B.setVisibility(8);
                    VideoRotateActivity.this.C.setVisibility(8);
                    VideoRotateActivity.this.g = "90";
                }
            });
            this.x.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    VideoRotateActivity.this.z.setVisibility(8);
                    VideoRotateActivity.this.A.setVisibility(8);
                    VideoRotateActivity.this.B.setVisibility(0);
                    VideoRotateActivity.this.C.setVisibility(8);
                    VideoRotateActivity.this.g = "270";
                }
            });
            this.y.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    VideoRotateActivity.this.z.setVisibility(8);
                    VideoRotateActivity.this.A.setVisibility(8);
                    VideoRotateActivity.this.B.setVisibility(8);
                    VideoRotateActivity.this.C.setVisibility(0);
                    VideoRotateActivity.this.D = new Dialog(VideoRotateActivity.this);
                    VideoRotateActivity.this.D.setCanceledOnTouchOutside(false);
                    VideoRotateActivity.this.D.requestWindowFeature(1);
                    VideoRotateActivity.this.D.setContentView(R.layout.enterfilename_popup);
                    VideoRotateActivity.this.D.show();
                    ((ImageView) VideoRotateActivity.this.D.findViewById(R.id.closePopup)).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            VideoRotateActivity.this.D.dismiss();
                        }
                    });
                    ((TextView) VideoRotateActivity.this.D.findViewById(R.id.Name)).setText("Enter Degree");
                    final CustomEditText customEditText = (CustomEditText) VideoRotateActivity.this.D.findViewById(R.id.message);
                    ((CustomTextView) VideoRotateActivity.this.D.findViewById(R.id.sendBtn)).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (customEditText.getText().toString().length() == 0) {
                                Toast.makeText(VideoRotateActivity.this, "Please Enter Value Between 1 To 360", 0).show();
                                return;
                            }
                            int parseInt = Integer.parseInt(customEditText.getText().toString());
                            if (parseInt < 1 || parseInt > 360) {
                                Toast.makeText(VideoRotateActivity.this, "Please Enter Value Between 1 To 360", 0).show();
                                return;
                            }
                            VideoRotateActivity.this.g = customEditText.getText().toString();
                            VideoRotateActivity.this.D.dismiss();
                        }
                    });
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
        Intent intent = new Intent(getApplicationContext(), VideoPlayer.class);
        intent.setFlags(67108864);
        intent.putExtra("song", this.i);
        startActivity(intent);
        finish();
    }

    public void rotatecommand() {
        String valueOf = String.valueOf(this.I.getStart() / 1000);
        String valueOf2 = String.valueOf(this.I.getDuration() / 1000);
        Log.e("start", valueOf);
        Log.e("end", valueOf2);
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.VideoRotate));
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb2.append("/");
        sb2.append(getResources().getString(R.string.MainFolderName));
        sb2.append("/");
        sb2.append(getResources().getString(R.string.VideoRotate));
        sb2.append("/");
        sb2.append(System.currentTimeMillis());
        sb2.append(".mp4");
        this.i = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append("rotate=");
        sb3.append(this.g);
        sb3.append("*PI/180");
        a(new String[]{"-y", "-ss", valueOf, "-t", valueOf2, "-i", this.j, "-filter_complex", sb3.toString(), "-c:a", "copy", this.i}, this.i);
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
                    Intent intent = new Intent(VideoRotateActivity.this.getApplicationContext(), VideoPlayer.class);
                    intent.setFlags(67108864);
                    intent.putExtra("song", VideoRotateActivity.this.i);
                    VideoRotateActivity.this.startActivity(intent);
                    VideoRotateActivity.this.finish();
                    VideoRotateActivity.this.refreshGallery(str);

                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoRotateActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoRotateActivity.this.getApplicationContext(), "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                } else {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoRotateActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoRotateActivity.this.getApplicationContext(), "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }


            }
        });
        getWindow().clearFlags(16);
    }

    private void d() {
        this.u.setVideoPath(this.j);
        this.u.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                VideoRotateActivity.this.a.setBackgroundResource(R.drawable.play2);
                VideoRotateActivity.this.o = Boolean.valueOf(false);
            }
        });
        this.u.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                Toast.makeText(VideoRotateActivity.this.getApplicationContext(), "Video Player Not Supproting", 0).show();
                VideoRotateActivity.this.m = false;
                return VideoRotateActivity.H;
            }
        });
        this.u.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                VideoRotateActivity.this.t.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (VideoRotateActivity.this.t.getSelectedThumb() == 1) {
                            VideoRotateActivity.this.u.seekTo(VideoRotateActivity.this.t.getLeftProgress());
                        }
                        VideoRotateActivity.this.r.setText(VideoRotateActivity.getTimeForTrackFormat(i, VideoRotateActivity.H));
                        VideoRotateActivity.this.q.setText(VideoRotateActivity.getTimeForTrackFormat(i2, VideoRotateActivity.H));
                        VideoRotateActivity.this.h = VideoRotateActivity.getTimeForTrackFormat(i, VideoRotateActivity.H);
                        VideoRotateActivity.this.I.setStart(i);
                        VideoRotateActivity.this.e = VideoRotateActivity.getTimeForTrackFormat(i2, VideoRotateActivity.H);
                        VideoRotateActivity.this.I.setStop(i2);
                        VideoRotateActivity.this.d = i;
                        VideoRotateActivity.this.c = i2;
                    }
                });
                VideoRotateActivity.this.e = VideoRotateActivity.getTimeForTrackFormat(mediaPlayer.getDuration(), VideoRotateActivity.H);
                VideoRotateActivity.this.t.setMaxValue(mediaPlayer.getDuration());
                VideoRotateActivity.this.t.setLeftProgress(0);
                VideoRotateActivity.this.t.setRightProgress(mediaPlayer.getDuration());
                VideoRotateActivity.this.t.setProgressMinDiff(0);
                VideoRotateActivity.this.a.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (VideoRotateActivity.this.o.booleanValue()) {
                            VideoRotateActivity.this.a.setBackgroundResource(R.drawable.play2);
                            VideoRotateActivity.this.o = Boolean.valueOf(false);
                        } else {
                            VideoRotateActivity.this.a.setBackgroundResource(R.drawable.pause2);
                            VideoRotateActivity.this.o = Boolean.valueOf(VideoRotateActivity.H);
                        }
                        VideoRotateActivity.this.e();
                    }
                });
            }
        });
        this.e = getTimeForTrackFormat(this.u.getDuration(), H);
    }


    public void e() {
        if (this.u.isPlaying()) {
            this.u.pause();
            this.t.setSliceBlocked(false);
            this.t.removeVideoStatusThumb();
            return;
        }
        this.u.seekTo(this.t.getLeftProgress());
        this.u.start();
        this.a.setBackgroundResource(R.drawable.pause2);
        this.t.videoPlayingProgress(this.t.getLeftProgress());
        this.J.a();
    }

    @SuppressLint({"DefaultLocale"})
    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2)))});
    }

    public static String getTimeForTrackFormat(int i2, boolean z2) {
        int i3 = i2 / 3600000;
        int i4 = i2 / 60000;
        int i5 = (i2 - ((i4 * 60) * 1000)) / 1000;
        String str = (!z2 || i3 >= 10) ? "" : "0";
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(i3);
        sb2.append(":");
        sb.append(sb2.toString());
        String str2 = (!z2 || i4 >= 10) ? "" : "0";
        StringBuilder sb3 = new StringBuilder();
        sb.append(str2);
        sb3.append(sb.toString());
        sb3.append(i4 % 60);
        sb3.append(":");
        String sb4 = sb3.toString();
        if (i5 < 10) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb4);
            sb5.append("0");
            sb5.append(i5);
            return sb5.toString();
        }
        StringBuilder sb6 = new StringBuilder();
        sb6.append(sb4);
        sb6.append(i5);
        return sb6.toString();
    }


    public void g() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VideoRotateActivity.this.finish();
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
        Intent intent = new Intent(this, ListVideoAndMyAlbumActivity.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroy() {
        getWindow().clearFlags(128);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return H;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return H;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (this.u != null && this.u.isPlaying()) {
                this.u.pause();
            }
            rotatecommand();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
