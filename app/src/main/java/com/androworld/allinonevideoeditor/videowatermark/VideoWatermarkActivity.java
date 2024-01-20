package com.androworld.allinonevideoeditor.videowatermark;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
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
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import com.androworld.allinonevideoeditor.Adclick;
import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.UtilCommand;
import com.androworld.allinonevideoeditor.VideoPlayer;
import com.androworld.allinonevideoeditor.VideoPlayerState;
import com.androworld.allinonevideoeditor.VideoSliceSeekBar;
import com.androworld.allinonevideoeditor.VideoSliceSeekBar.SeekBarChangeListener;
import com.androworld.allinonevideoeditor.listvideoandmyvideo.ListVideoAndMyAlbumActivity;
import com.androworld.allinonevideoeditor.videowatermark.addtext.DataBinder;
import com.androworld.allinonevideoeditor.videowatermark.addtext.DrawableSticker;
import com.androworld.allinonevideoeditor.videowatermark.addtext.FragmentSticker;
import com.androworld.allinonevideoeditor.videowatermark.addtext.FragmentText;
import com.androworld.allinonevideoeditor.videowatermark.addtext.Sticker;
import com.androworld.allinonevideoeditor.videowatermark.addtext.StickerData;
import com.androworld.allinonevideoeditor.videowatermark.addtext.StickerView;
import com.androworld.allinonevideoeditor.videowatermark.addtext.StickerView.OnStickerOperationListener;
import com.androworld.allinonevideoeditor.videowatermark.addtext.TextSticker;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;


import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout.TabProvider;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

@SuppressLint({"ResourceAsColor", "WrongConstant", "ResourceType"})
@TargetApi(16)
public class VideoWatermarkActivity extends AppCompatActivity {
    private WakeLock Rs;
    static final boolean H = true;
    public static int selectedPos;
    TextView view3;
    int anInt1;
    int anInt2;
    int anInt3;
    int anInt4;
    int anInt5;
    VideoSliceSeekBar videoSliceSeekBar;

    public ImageLoader loader;

    public LayoutInflater inflater = null;
    private File file1;
    private PowerManager manager;
    private TextView view4;

    public TextView textView;

    public TextView textView1;
    public String Output;

    public VideoPlayerState playerState = new VideoPlayerState();
    private a Q = new a();

    public EditText editText;

    public InputMethodManager inputMethodManager;

    public LinearLayout linearLayout;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;

    public CustomViewPager customViewPager;

    public TextSticker textSticker;

    public StickerView stickerView;
    String string;

    public RelativeLayout aa;
    private RelativeLayout ab;
    private RelativeLayout ac;
    public Activity activity;
    private ViewPager ad;
    private SmartTabLayout ae;
    String string1;
    String string2;
    ArrayAdapter<String> arrayAdapter = null;
    ImageAdapter adapter;
    View view = null;
    ImageView imageView;
    ImageView view1;
    ImageView imageView1;
    Bitmap bitmap;
    int anInt = 0;
    String[] strings;
    FrameLayout layout;
    GridView view2;
    String string3 = null;
    String string4 = null;
    String string5 = null;
    String string6 = null;
    ArrayList<HsItem> itemArrayList = new ArrayList<>();
    File file;
    String[] strings1;
    DisplayImageOptions imageOptions;
    public VideoView videoView;
    DisplayImageOptions imageOptions1;
    Boolean value = Boolean.valueOf(false);
    Bitmap bitmap1 = null;
    String string7 = "00";
    private Ads ads;

    private class ImageAdapter extends BaseAdapter {

        public class ViewHolder {
            ImageView a;

            public ViewHolder() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public ImageAdapter(Activity activity) {
            VideoWatermarkActivity.this.activity = activity;
            VideoWatermarkActivity.this.inflater = LayoutInflater.from(VideoWatermarkActivity.this.activity);
            VideoWatermarkActivity.this.inflater = VideoWatermarkActivity.this.activity.getLayoutInflater();
            VideoWatermarkActivity.this.anInt2 = (VideoWatermarkActivity.this.activity.getResources().getDisplayMetrics().widthPixels / 4) - 14;
        }

        public int getCount() {
            return VideoWatermarkActivity.this.itemArrayList.size();
        }

        public Object getItem(int i) {
            return VideoWatermarkActivity.this.itemArrayList.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            HsItem hsItem;
            View view2;
            Exception e;
            if (view == null) {
                try {
                    viewHolder = new ViewHolder();
                    try {
                        view2 = VideoWatermarkActivity.this.inflater.inflate(R.layout.videowatermark_sub_sticker_img_raw, null);
                        try {
                            view2.setLayoutParams(new LayoutParams(VideoWatermarkActivity.this.anInt2, VideoWatermarkActivity.this.anInt2));
                            viewHolder.a = (ImageView) view2.findViewById(R.id.ivpip_tiny);
                            viewHolder.a.setScaleType(ScaleType.CENTER_INSIDE);
                            view2.setTag(viewHolder);
                        } catch (Exception e2) {
                            e = e2;
                        }
                    } catch (Exception e3) {
                        Exception exc = e3;
                        view2 = view;
                        e = exc;
                        e.printStackTrace();
                        view = view2;
                        hsItem = (HsItem) VideoWatermarkActivity.this.itemArrayList.get(i);
                        if (hsItem.isAvailable) {
                        }
                        return view;
                    }
                } catch (Exception e4) {
                    view2 = view;
                    e = e4;
                    viewHolder = null;
                    e.printStackTrace();
                    view = view2;
                    hsItem = (HsItem) VideoWatermarkActivity.this.itemArrayList.get(i);
                    if (hsItem.isAvailable) {
                    }
                    return view;
                }
                view = view2;
            } else {
                try {
                    viewHolder = (ViewHolder) view.getTag();
                } catch (Exception e5) {
                    e5.printStackTrace();
                    viewHolder = null;
                }
            }
            hsItem = (HsItem) VideoWatermarkActivity.this.itemArrayList.get(i);
            if (hsItem.isAvailable) {
                VideoWatermarkActivity.this.loader.displayImage(hsItem.path, viewHolder.a, VideoWatermarkActivity.this.imageOptions1);
            } else {
                VideoWatermarkActivity.this.loader.displayImage(hsItem.path, viewHolder.a, VideoWatermarkActivity.this.imageOptions);
            }
            return view;
        }
    }

    private class a extends Handler {
        private boolean b;
        private Runnable c;

        class C0035a implements Runnable {
            C0035a() {
            }

            public void run() {
                a.this.a();
            }
        }

        private a() {
            this.b = false;
            this.c = new C0035a();
        }


        public void a() {
            if (!this.b) {
                this.b = VideoWatermarkActivity.H;
                sendEmptyMessage(0);
            }
        }

        @Override
        public void handleMessage(Message message) {
            this.b = false;
            VideoWatermarkActivity.this.videoSliceSeekBar.videoPlayingProgress(VideoWatermarkActivity.this.videoView.getCurrentPosition());
            if (!VideoWatermarkActivity.this.videoView.isPlaying() || VideoWatermarkActivity.this.videoView.getCurrentPosition() >= VideoWatermarkActivity.this.videoSliceSeekBar.getRightProgress()) {
                if (VideoWatermarkActivity.this.videoView.isPlaying()) {
                    VideoWatermarkActivity.this.videoView.pause();
                    VideoWatermarkActivity.this.value = Boolean.FALSE;
                    VideoWatermarkActivity.this.imageView.setBackgroundResource(R.drawable.pause2);
                }
                VideoWatermarkActivity.this.videoSliceSeekBar.setSliceBlocked(false);
                VideoWatermarkActivity.this.videoSliceSeekBar.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.c, 50);
        }
    }

    class b implements OnPreparedListener {

        class a implements SeekBarChangeListener {
            a() {
            }

            public void SeekBarValueChanged(int i, int i2) {
                if (VideoWatermarkActivity.this.videoSliceSeekBar.getSelectedThumb() == 1) {
                    VideoWatermarkActivity.this.videoView.seekTo(VideoWatermarkActivity.this.videoSliceSeekBar.getLeftProgress());
                }
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                sb.append("");
                Log.e("Left", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(i2);
                sb2.append("");
                Log.e("Right", sb2.toString());
                VideoWatermarkActivity.this.textView1.setText(VideoWatermarkActivity.getTimeForTrackFormat(i, VideoWatermarkActivity.H));
                VideoWatermarkActivity.this.textView.setText(VideoWatermarkActivity.getTimeForTrackFormat(i2, VideoWatermarkActivity.H));
                VideoWatermarkActivity.this.string7 = VideoWatermarkActivity.getTimeForTrackFormat(i, VideoWatermarkActivity.H);
                VideoWatermarkActivity.this.playerState.setStart(i);
                VideoWatermarkActivity.this.string1 = VideoWatermarkActivity.getTimeForTrackFormat(i2, VideoWatermarkActivity.H);
                VideoWatermarkActivity.this.playerState.setStop(i2);
            }
        }

        b() {
        }

        public void onPrepared(MediaPlayer mediaPlayer) {
            VideoWatermarkActivity.this.videoSliceSeekBar.setSeekBarChangeListener(new a());
            VideoWatermarkActivity.this.anInt3 = mediaPlayer.getVideoWidth();
            VideoWatermarkActivity.this.anInt5 = mediaPlayer.getVideoHeight();
            VideoWatermarkActivity.this.string1 = VideoWatermarkActivity.getTimeForTrackFormat(mediaPlayer.getDuration(), VideoWatermarkActivity.H);
            VideoWatermarkActivity.this.videoSliceSeekBar.setMaxValue(mediaPlayer.getDuration());
            if (VideoWatermarkActivity.this.anInt == 0) {
                VideoWatermarkActivity.this.videoSliceSeekBar.setLeftProgress(0);
                VideoWatermarkActivity.this.videoSliceSeekBar.setRightProgress(mediaPlayer.getDuration());
            }
            if (VideoWatermarkActivity.this.anInt == 1) {
                VideoWatermarkActivity.this.videoSliceSeekBar.setLeftProgress(VideoWatermarkActivity.this.anInt1);
                VideoWatermarkActivity.this.videoSliceSeekBar.setRightProgress(VideoWatermarkActivity.this.anInt4);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(mediaPlayer.getDuration());
            sb.append("");
            Log.e("right mp", sb.toString());
            VideoWatermarkActivity.this.videoSliceSeekBar.setProgressMinDiff(0);
            VideoWatermarkActivity.this.imageView.setOnClickListener(new c());
        }
    }

    class c implements OnClickListener {
        c() {
        }

        @Override
        public void onClick(View view) {
            if (VideoWatermarkActivity.this.value.booleanValue()) {
                VideoWatermarkActivity.this.imageView.setBackgroundResource(R.drawable.pause2);
                VideoWatermarkActivity.this.value = Boolean.FALSE;
            } else {
                VideoWatermarkActivity.this.imageView.setBackgroundResource(R.drawable.play2);
                VideoWatermarkActivity.this.value = Boolean.TRUE;
            }
            VideoWatermarkActivity.this.e();
        }
    }

    class d implements OnTouchListener {
        d() {
        }

        @SuppressLint({"ClickableViewAccessibility"})
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (VideoWatermarkActivity.this.value.booleanValue()) {
                VideoWatermarkActivity.this.videoView.pause();
                VideoWatermarkActivity.this.value = Boolean.FALSE;
                VideoWatermarkActivity.this.imageView.setBackgroundResource(R.drawable.pause2);
            }
            return VideoWatermarkActivity.H;
        }
    }

    public class fetchSticker extends AsyncTask<String, Void, Void> {
        public fetchSticker() {
            //add
        }


        public Void doInBackground(String... strArr) {
            int i = 0;
            while (i <= 391) {
                try {
                    ArrayList<StickerData> arrayList = DataBinder.stickerList;
                    StringBuilder sb = new StringBuilder();
                    sb.append("itemArrayList");
                    sb.append(i);
                    arrayList.add(new StickerData(sb.toString(), false));
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        public void onPreExecute() {
            DataBinder.stickerList.clear();
            super.onPreExecute();
        }


        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
        }
    }

    @SuppressLint({"ClickableViewAccessibility", "InvalidWakeLockTag"})
    @Override
    public void onCreate(Bundle bundle) {
        Object obj;
        super.onCreate(bundle);
        setContentView(R.layout.videowatermarkactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Watermark");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (H || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(H);
            supportActionBar.setDisplayShowTitleEnabled(false);
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb.append("/");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.VideoWatermark));
            File file = new File(sb.toString());
            if (!file.exists()) {
                file.mkdirs();
            }
            this.string2 = getIntent().getExtras().getString("pos");
            this.textView1 = (TextView) findViewById(R.id.left_pointer);
            g();
            fgdfg();
            h();
            if (this.bitmap1 == null) {
                try {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                    sb2.append("/");
                    sb2.append(getResources().getString(R.string.MainFolderName));
                    sb2.append("/");
                    sb2.append(getResources().getString(R.string.VideoWatermark));
                    sb2.append("/myvideo.mp4");
                    this.file = new File(sb2.toString());
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                    sb3.append("/");
                    sb3.append(getResources().getString(R.string.MainFolderName));
                    sb3.append("/");
                    sb3.append(getResources().getString(R.string.VideoWatermark).toString());
                    this.file1 = new File(sb3.toString(), "temp_photo.jpg");
                    this.textView = (TextView) findViewById(R.id.right_pointer);
                    this.anInt2 = getResources().getDisplayMetrics().widthPixels;
                    this.imageView = (ImageView) findViewById(R.id.buttonply);
                    this.videoSliceSeekBar = (VideoSliceSeekBar) findViewById(R.id.seek_bar);
                    this.manager = (PowerManager) getSystemService(Context.POWER_SERVICE);
                    this.Rs = this.manager.newWakeLock(6, "My Tag");
                    obj = getLastNonConfigurationInstance();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    obj = null;
                    if (obj != null) {
                    }
                    this.view3 = (TextView) findViewById(R.id.Filename);
                    this.view3.setText(new File(this.string6).getName());
                    this.videoView = (VideoView) findViewById(R.id.videoView1);
                    this.videoView.setOnTouchListener(new d());
                    this.videoView.setVisibility(0);
                    d();
                    this.linearLayout2 = (LinearLayout) findViewById(R.id.stickerCross);
                    this.linearLayout2.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //add
                        }
                    });
                    this.linearLayout1 = (LinearLayout) findViewById(R.id.stickerCheck);
                    this.linearLayout1.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (int i = 0; i < 391; i++) {
                                try {
                                    if (((StickerData) DataBinder.stickerList.get(i)).isSelected()) {
                                        try {
                                            VideoWatermarkActivity.this.stickerView.addSticker(new DrawableSticker(ContextCompat.getDrawable(VideoWatermarkActivity.this.getApplicationContext(), VideoWatermarkActivity.this.getResources().getIdentifier(((StickerData) DataBinder.stickerList.get(i)).getName(), "drawable", VideoWatermarkActivity.this.getPackageName()))));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                            VideoWatermarkActivity.this.stickerLayout();
                        }
                    });
                    this.linearLayout1.performClick();

                    ads = new Ads();
                    ads.Interstitialload(this);

                    return;
                }
            } else {
                try {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                    sb4.append("/");
                    sb4.append(getResources().getString(R.string.MainFolderName));
                    sb4.append("/");
                    sb4.append(getResources().getString(R.string.VideoWatermark));
                    sb4.append("/myvideo.mp4");
                    this.file = new File(sb4.toString());
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                    sb5.append("/");
                    sb5.append(getResources().getString(R.string.MainFolderName));
                    sb5.append("/");
                    sb5.append(getResources().getString(R.string.VideoWatermark).toString());
                    this.file1 = new File(sb5.toString(), "temp_photo.jpg");
                    this.textView = (TextView) findViewById(R.id.right_pointer);
                    this.anInt2 = getResources().getDisplayMetrics().widthPixels;
                    this.imageView = (ImageView) findViewById(R.id.buttonply);
                    this.videoSliceSeekBar = (VideoSliceSeekBar) findViewById(R.id.seek_bar);
                    this.manager = (PowerManager) getSystemService(Context.POWER_SERVICE);
                    this.Rs = this.manager.newWakeLock(6, "My Tag");
                    obj = getLastNonConfigurationInstance();
                } catch (Exception e3) {
                    e3.printStackTrace();
                    obj = null;
                    if (obj != null) {
                    }
                    this.view3 = (TextView) findViewById(R.id.Filename);
                    this.view3.setText(new File(this.string6).getName());
                    this.videoView = (VideoView) findViewById(R.id.videoView1);
                    this.videoView.setOnTouchListener(new d());
                    this.videoView.setVisibility(0);
                    d();
                    this.linearLayout2 = (LinearLayout) findViewById(R.id.stickerCross);
                    this.linearLayout2.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //add
                        }
                    });
                    this.linearLayout1 = (LinearLayout) findViewById(R.id.stickerCheck);
                    this.linearLayout1.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (int i = 0; i < 391; i++) {
                                try {
                                    if (((StickerData) DataBinder.stickerList.get(i)).isSelected()) {
                                        VideoWatermarkActivity.this.stickerView.addSticker(new DrawableSticker(ContextCompat.getDrawable(VideoWatermarkActivity.this.getApplicationContext(), VideoWatermarkActivity.this.getResources().getIdentifier(((StickerData) DataBinder.stickerList.get(i)).getName(), "drawable", VideoWatermarkActivity.this.getPackageName()))));
                                    }
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                            VideoWatermarkActivity.this.stickerLayout();
                        }
                    });
                    this.linearLayout1.performClick();
                    ads = new Ads();
                    ads.Interstitialload(this);
                    return;
                }
            }
            if (obj != null) {
                try {
                    this.playerState = (VideoPlayerState) obj;
                    Log.e("lastState", "not null");
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            } else {
                try {
                    this.string6 = getIntent().getExtras().getString("videopath");
                    this.playerState.setFilename(this.string6);
                    Log.e("lastState", "null");
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
            }
            this.view3 = (TextView) findViewById(R.id.Filename);
            this.view3.setText(new File(this.string6).getName());
            this.videoView = (VideoView) findViewById(R.id.videoView1);
            this.videoView.setOnTouchListener(new d());
            this.videoView.setVisibility(0);
            d();
            this.linearLayout2 = (LinearLayout) findViewById(R.id.stickerCross);
            this.linearLayout2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //add
                }
            });
            this.linearLayout1 = (LinearLayout) findViewById(R.id.stickerCheck);
            this.linearLayout1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < 391; i++) {
                        try {
                            if (((StickerData) DataBinder.stickerList.get(i)).isSelected()) {
                                try {
                                    VideoWatermarkActivity.this.stickerView.addSticker(new DrawableSticker(ContextCompat.getDrawable(VideoWatermarkActivity.this.getApplicationContext(), VideoWatermarkActivity.this.getResources().getIdentifier(((StickerData) DataBinder.stickerList.get(i)).getName(), "drawable", VideoWatermarkActivity.this.getPackageName()))));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    VideoWatermarkActivity.this.stickerLayout();
                }
            });
            this.linearLayout1.performClick();
            ads = new Ads();
            ads.Interstitialload(this);
            return;
        }
        throw new AssertionError();
    }


    public void b() {
        ads = new Ads();
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
        intent.putExtra("song", this.Output);
        startActivity(intent);
        finish();
    }

    public void ffmpegcommand() {
        String valueOf = String.valueOf(this.playerState.getStart() / 1000);
        String valueOf2 = String.valueOf(this.playerState.getDuration() / 1000);
        String filename = this.playerState.getFilename();
        Log.e("input", filename);
        String substring = filename.substring(filename.lastIndexOf("/") + 1);
        Log.e("inputf", substring);
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.VideoWatermark));
        sb.append("/watermarkvid");
        sb.append(System.currentTimeMillis());
        sb.append(substring);
        this.string6 = sb.toString();
        a(filename, this.string6, this.string5, valueOf, valueOf2, this.string3, this.string4);
    }


    public void a(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        Log.e("filename", str);
        Log.e("path", str2);
        Log.e("imgpath", str3);
        Log.e("str", str4);
        Log.e("duration", str5);
        Log.e("imgx", str6);
        Log.e("imgy", str7);
        this.Output = str2;
        StringBuilder sb = new StringBuilder();
        sb.append("movie=");
        sb.append(str3);
        sb.append(" [watermark]; [in][watermark] overlay=");
        sb.append(str6);
        sb.append(":");
        sb.append(str7);
        sb.append(" [out]");
        a(new String[]{"-bitmap1", "-ss", str4, "-file", str5, "-imageView1", str, "-vf", sb.toString(), "-string2:string", "copy", "-strict", "experimental", "-preset", "ultrafast", "-ss", "0", "-file", str5, this.Output}, this.Output);
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

                com.arthenica.mobileffmpeg.Config.printLastCommandOutput(Log.INFO);

                progressDialog.dismiss();
                if (returnCode == RETURN_CODE_SUCCESS) {
                    progressDialog.dismiss();
                    VideoWatermarkActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(VideoWatermarkActivity.this.Output))));
                    VideoWatermarkActivity.this.b();

                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoWatermarkActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoWatermarkActivity.this.getApplicationContext(), "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                } else {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoWatermarkActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoWatermarkActivity.this.getApplicationContext(), "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                VideoWatermarkActivity.this.refreshGallery(str);

            }
        });

    }

    private void d() {
        this.videoView.setOnPreparedListener(new b());
        this.videoView.setVideoPath(this.string6);
        this.videoView.start();
        this.string1 = getTimeForTrackFormat(this.videoView.getDuration(), H);
    }


    public void e() {
        if (this.videoView.isPlaying()) {
            this.videoView.pause();
            this.videoSliceSeekBar.setSliceBlocked(false);
            this.videoSliceSeekBar.removeVideoStatusThumb();
            return;
        }
        this.videoView.seekTo(this.videoSliceSeekBar.getLeftProgress());
        this.videoView.start();
        this.videoSliceSeekBar.videoPlayingProgress(this.videoSliceSeekBar.getLeftProgress());
        this.Q.a();
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

    private void fgdfg() {
        this.inputMethodManager = (InputMethodManager) getSystemService("input_method");
        this.editText = (EditText) findViewById(R.id.addTxtEditText);
        this.linearLayout = (LinearLayout) findViewById(R.id.textFullLayout);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.tabTextSticker);
        viewGroup.addView(LayoutInflater.from(this).inflate(R.layout.videowatermark_viewpagertextsticker, viewGroup, false));
        this.customViewPager = (CustomViewPager) findViewById(R.id.viewPagerTextSticker);
        SmartTabLayout smartTabLayout = (SmartTabLayout) findViewById(R.id.viewpagertextsticker);
        this.customViewPager.setPagingEnabled(false);
        final LayoutInflater from = LayoutInflater.from(smartTabLayout.getContext());
        smartTabLayout.setCustomTabView(new TabProvider() {
            public View createTabView(ViewGroup viewGroup, int i, PagerAdapter pagerAdapter) {
                View inflate = from.inflate(R.layout.videowatermark_customtabtextstickerlayout, viewGroup, false);
                ImageView imageView = (ImageView) inflate.findViewById(R.id.imgIcon);
                switch (i) {
                    case 0:
                        imageView.setImageResource(R.drawable.btn_keyboard);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.btn_font_style);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.btn_font_color);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.btn_font_adjust);
                        break;
                    default:
                        StringBuilder sb = new StringBuilder();
                        sb.append("Invalid position: ");
                        sb.append(i);
                        throw new IllegalStateException(sb.toString());
                }
                return inflate;
            }
        });
        FragmentPagerItems fragmentPagerItems = new FragmentPagerItems(this.activity);
        fragmentPagerItems.add(FragmentPagerItem.of("FragmentText", FragmentText.class));
        fragmentPagerItems.add(FragmentPagerItem.of("FragmentText", FragmentText.class));
        fragmentPagerItems.add(FragmentPagerItem.of("FragmentText", FragmentText.class));
        fragmentPagerItems.add(FragmentPagerItem.of("FragmentText", FragmentText.class));
        this.customViewPager.setAdapter(new FragmentPagerItemAdapter(getSupportFragmentManager(), fragmentPagerItems));
        smartTabLayout.setViewPager(this.customViewPager);
        this.customViewPager.setCurrentItem(1, H);
        this.customViewPager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
                //add
            }

            public void onPageScrolled(int i, float f, int i2) {
                //add
            }

            public void onPageSelected(int i) {
                if (i == 0) {
                    FragmentText.showSoftKeyboard(VideoWatermarkActivity.this.inputMethodManager, VideoWatermarkActivity.this.editText);
                } else {
                    FragmentText.dismissSoftKeyboard(VideoWatermarkActivity.this.inputMethodManager, VideoWatermarkActivity.this.editText);
                }
            }
        });
        this.stickerView.setOnStickerOperationListener(new OnStickerOperationListener() {
            public void onStickerDragFinished(Sticker sticker) {
                //add
            }

            public void onStickerFlipped(Sticker sticker) {
                //add
            }

            public void onStickerZoomFinished(Sticker sticker) {
                //add
            }

            public void onStickerClicked(Sticker sticker) {
                if (sticker instanceof TextSticker) {
                    try {
                        VideoWatermarkActivity.this.stickerView.replace(sticker);
                        VideoWatermarkActivity.this.stickerView.invalidate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            public void onStickerDeleted(Sticker sticker) {
                if (sticker instanceof TextSticker) {
                    VideoWatermarkActivity.this.textSticker = null;
                    VideoWatermarkActivity.this.stickerView.invalidate();
                }
            }

            public void onStickerDoubleTapped(Sticker sticker) {
                if (sticker instanceof TextSticker) {
                    try {
                        TextSticker textSticker = (TextSticker) sticker;
                        VideoWatermarkActivity.this.linearLayout.setVisibility(0);
                        VideoWatermarkActivity.this.editText.setText(textSticker.getText().toString());
                        VideoWatermarkActivity.this.editText.setTypeface(textSticker.getTypeface());
                        VideoWatermarkActivity.this.editText.setTextColor(textSticker.getTextColor());
                        VideoWatermarkActivity.this.editText.getPaint().setAlpha(textSticker.getAlpha());
                        VideoWatermarkActivity.this.customViewPager.setCurrentItem(1, VideoWatermarkActivity.H);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @SuppressLint({"WrongConstant"})
    public void addTextSticker(View view) {
        View findViewById = findViewById(R.id.viewBG);
        String obj = this.editText.getText().toString();
        int measuredWidth = this.editText.getMeasuredWidth();
        int measuredHeight = this.editText.getMeasuredHeight();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) findViewById.getLayoutParams();
        layoutParams.height = measuredHeight;
        layoutParams.width = measuredWidth;
        findViewById.setLayoutParams(layoutParams);
        findViewById.requestLayout();
        if (!this.editText.getText().toString().equals("")) {
            if (this.textSticker != null) {
                this.textSticker.setText(obj);
                this.textSticker.setTypeface(this.editText.getTypeface());
                this.textSticker.setTextColor(this.editText.getCurrentTextColor());
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_background);
                if (FragmentText.bgStatus) {
                    try {
                        if (FragmentText.currentBg == 0) {
                            try {
                                Drawable drawable2 = this.activity.getResources().getDrawable(R.drawable.stroke_rect);
                                drawable2.setColorFilter(new LightingColorFilter(FragmentText.bgColor, FragmentText.bgColor));
                                int intrinsicWidth = drawable2.getIntrinsicWidth();
                                if (intrinsicWidth <= 0) {
                                    intrinsicWidth = 1;
                                }
                                int intrinsicHeight = drawable2.getIntrinsicHeight();
                                if (intrinsicHeight <= 0) {
                                    intrinsicHeight = 1;
                                }
                                Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Config.ARGB_8888);
                                Canvas canvas = new Canvas(createBitmap);
                                drawable2.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                                drawable2.draw(canvas);
                                Drawable bitmapDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(createBitmap, measuredWidth, measuredHeight, H));
                                try {
                                    bitmapDrawable.setAlpha(Math.round((((float) FragmentText.opacityBGProgress) / 100.0f) * 255.0f));
                                    drawable = bitmapDrawable;
                                } catch (NotFoundException e2) {
                                    drawable = bitmapDrawable;
                                    this.textSticker.setDrawable(drawable);
                                    this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                    this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                    this.textSticker.resizeText();
                                    this.stickerView.invalidate();
                                    StickerView.isStickerTouch = H;
                                    findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                    findViewById.requestLayout();
                                }
                            } catch (NotFoundException e3) {
                                this.textSticker.setDrawable(drawable);
                                this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                this.textSticker.resizeText();
                                this.stickerView.invalidate();
                                StickerView.isStickerTouch = H;
                                findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                findViewById.requestLayout();
                            }
                        } else {
                            try {
                                BitmapDrawable bitmapDrawable2 = new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), FragmentText.bgColor));
                                bitmapDrawable2.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
                                findViewById.setBackgroundDrawable(bitmapDrawable2);
                                Bitmap createBitmap2 = Bitmap.createBitmap(measuredWidth, measuredHeight, Config.ARGB_8888);
                                Canvas canvas2 = new Canvas(createBitmap2);
                                findViewById.layout(findViewById.getLeft(), findViewById.getTop(), findViewById.getRight(), findViewById.getBottom());
                                findViewById.draw(canvas2);
                                Drawable bitmapDrawable3 = new BitmapDrawable(getResources(), createBitmap2);
                                try {
                                    bitmapDrawable3.setAlpha(Math.round((((float) FragmentText.opacityBGProgress) / 100.0f) * 255.0f));
                                    drawable = bitmapDrawable3;
                                } catch (OutOfMemoryError e4) {
                                    drawable = bitmapDrawable3;
                                    this.textSticker.setDrawable(drawable);
                                    this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                    this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                    this.textSticker.resizeText();
                                    this.stickerView.invalidate();
                                    StickerView.isStickerTouch = H;
                                    findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                    findViewById.requestLayout();
                                } catch (ArrayIndexOutOfBoundsException e5) {
                                    drawable = bitmapDrawable3;
                                    this.textSticker.setDrawable(drawable);
                                    this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                    this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                    this.textSticker.resizeText();
                                    this.stickerView.invalidate();
                                    StickerView.isStickerTouch = H;
                                    findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                    findViewById.requestLayout();
                                } catch (ActivityNotFoundException e6) {
                                    drawable = bitmapDrawable3;
                                    this.textSticker.setDrawable(drawable);
                                    this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                    this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                    this.textSticker.resizeText();
                                    this.stickerView.invalidate();
                                    StickerView.isStickerTouch = H;
                                    findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                    findViewById.requestLayout();
                                } catch (NotFoundException e7) {
                                    drawable = bitmapDrawable3;
                                    this.textSticker.setDrawable(drawable);
                                    this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                    this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                    this.textSticker.resizeText();
                                    this.stickerView.invalidate();
                                    StickerView.isStickerTouch = H;
                                    findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                    findViewById.requestLayout();
                                } catch (NullPointerException e8) {
                                    drawable = bitmapDrawable3;
                                    this.textSticker.setDrawable(drawable);
                                    this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                    this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                    this.textSticker.resizeText();
                                    this.stickerView.invalidate();
                                    StickerView.isStickerTouch = H;
                                    findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                    findViewById.requestLayout();
                                } catch (StackOverflowError e9) {
                                    drawable = bitmapDrawable3;
                                    this.textSticker.setDrawable(drawable);
                                    this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                    this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                    this.textSticker.resizeText();
                                    this.stickerView.invalidate();
                                    StickerView.isStickerTouch = H;
                                    findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                    findViewById.requestLayout();
                                } catch (IllegalArgumentException e10) {
                                    drawable = bitmapDrawable3;
                                    this.textSticker.setDrawable(drawable);
                                    this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                    this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                    this.textSticker.resizeText();
                                    this.stickerView.invalidate();
                                    StickerView.isStickerTouch = H;
                                    findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                    findViewById.requestLayout();
                                }
                            } catch (OutOfMemoryError e11) {
                                this.textSticker.setDrawable(drawable);
                                this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                this.textSticker.resizeText();
                                this.stickerView.invalidate();
                                StickerView.isStickerTouch = H;
                                findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                findViewById.requestLayout();
                            } catch (ArrayIndexOutOfBoundsException e12) {
                                this.textSticker.setDrawable(drawable);
                                this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                this.textSticker.resizeText();
                                this.stickerView.invalidate();
                                StickerView.isStickerTouch = H;
                                findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                findViewById.requestLayout();
                            } catch (ActivityNotFoundException e13) {
                                this.textSticker.setDrawable(drawable);
                                this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                this.textSticker.resizeText();
                                this.stickerView.invalidate();
                                StickerView.isStickerTouch = H;
                                findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                findViewById.requestLayout();
                            } catch (NotFoundException e14) {
                                this.textSticker.setDrawable(drawable);
                                this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                this.textSticker.resizeText();
                                this.stickerView.invalidate();
                                StickerView.isStickerTouch = H;
                                findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                findViewById.requestLayout();
                            } catch (NullPointerException e15) {
                                this.textSticker.setDrawable(drawable);
                                this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                this.textSticker.resizeText();
                                this.stickerView.invalidate();
                                StickerView.isStickerTouch = H;
                                findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                findViewById.requestLayout();
                            } catch (StackOverflowError e16) {
                                this.textSticker.setDrawable(drawable);
                                this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                this.textSticker.resizeText();
                                this.stickerView.invalidate();
                                StickerView.isStickerTouch = H;
                                findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                findViewById.requestLayout();
                                if (this.linearLayout.getVisibility() != 0) {
                                }
                            } catch (IllegalArgumentException e17) {
                                this.textSticker.setDrawable(drawable);
                                this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                                this.textSticker.resizeText();
                                this.stickerView.invalidate();
                                StickerView.isStickerTouch = H;
                                findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                findViewById.requestLayout();
                                if (this.linearLayout.getVisibility() != 0) {
                                }
                            }
                        }
                    } catch (NotFoundException e18) {
                        e18.printStackTrace();
                    }
                }
                this.textSticker.setDrawable(drawable);
                this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                this.textSticker.setAlpha(this.editText.getPaint().getAlpha());
                this.textSticker.resizeText();
                this.stickerView.invalidate();
            } else {
                try {
                    Drawable drawable3 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.transparent_background);
                    if (FragmentText.bgStatus) {
                        try {
                            if (FragmentText.currentBg == 0) {
                                try {
                                    Drawable drawable4 = this.activity.getResources().getDrawable(R.drawable.stroke_rect);
                                    drawable4.setColorFilter(new LightingColorFilter(FragmentText.bgColor, FragmentText.bgColor));
                                    int intrinsicWidth2 = drawable4.getIntrinsicWidth();
                                    if (intrinsicWidth2 <= 0) {
                                        intrinsicWidth2 = 1;
                                    }
                                    int intrinsicHeight2 = drawable4.getIntrinsicHeight();
                                    if (intrinsicHeight2 <= 0) {
                                        intrinsicHeight2 = 1;
                                    }
                                    Bitmap createBitmap3 = Bitmap.createBitmap(intrinsicWidth2, intrinsicHeight2, Config.ARGB_8888);
                                    Canvas canvas3 = new Canvas(createBitmap3);
                                    drawable4.setBounds(0, 0, canvas3.getWidth(), canvas3.getHeight());
                                    drawable4.draw(canvas3);
                                    Drawable bitmapDrawable4 = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(createBitmap3, measuredWidth, measuredHeight, H));
                                    try {
                                        bitmapDrawable4.setAlpha(Math.round((((float) FragmentText.opacityBGProgress) / 100.0f) * 255.0f));
                                        drawable3 = bitmapDrawable4;
                                    } catch (NotFoundException e19) {
                                        drawable3 = bitmapDrawable4;
                                        TextSticker textSticker = new TextSticker(getApplicationContext());
                                        textSticker.setDrawable(drawable3);
                                        textSticker.setText(obj);
                                        textSticker.resizeText();
                                        this.stickerView.addSticker(textSticker);
                                        this.textSticker = textSticker;
                                        this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                        this.textSticker.setAlpha(Math.round((((float) FragmentText.opacityTxtProgress) / 100.0f) * 255.0f));
                                        this.stickerView.invalidate();
                                        StickerView.isStickerTouch = H;
                                        findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                        findViewById.requestLayout();
                                    }
                                } catch (NotFoundException e20) {
                                    TextSticker textSticker2 = new TextSticker(getApplicationContext());
                                    textSticker2.setDrawable(drawable3);
                                    textSticker2.setText(obj);
                                    textSticker2.resizeText();
                                    this.stickerView.addSticker(textSticker2);
                                    this.textSticker = textSticker2;
                                    this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                    this.textSticker.setAlpha(Math.round((((float) FragmentText.opacityTxtProgress) / 100.0f) * 255.0f));
                                    this.stickerView.invalidate();
                                    StickerView.isStickerTouch = H;
                                    findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                    findViewById.requestLayout();
                                }
                            } else {
                                try {
                                    BitmapDrawable bitmapDrawable5 = new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), FragmentText.bgColor));
                                    bitmapDrawable5.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
                                    findViewById.setBackgroundDrawable(bitmapDrawable5);
                                    Bitmap createBitmap4 = Bitmap.createBitmap(measuredWidth, measuredHeight, Config.ARGB_8888);
                                    Canvas canvas4 = new Canvas(createBitmap4);
                                    findViewById.layout(findViewById.getLeft(), findViewById.getTop(), findViewById.getRight(), findViewById.getBottom());
                                    findViewById.draw(canvas4);
                                    Drawable bitmapDrawable6 = new BitmapDrawable(getResources(), createBitmap4);
                                    try {
                                        bitmapDrawable6.setAlpha(Math.round((((float) FragmentText.opacityBGProgress) / 100.0f) * 255.0f));
                                        drawable3 = bitmapDrawable6;
                                    } catch (Exception e21) {
                                        drawable3 = bitmapDrawable6;
                                        TextSticker textSticker22 = new TextSticker(getApplicationContext());
                                        textSticker22.setDrawable(drawable3);
                                        textSticker22.setText(obj);
                                        textSticker22.resizeText();
                                        this.stickerView.addSticker(textSticker22);
                                        this.textSticker = textSticker22;
                                        this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                        this.textSticker.setAlpha(Math.round((((float) FragmentText.opacityTxtProgress) / 100.0f) * 255.0f));
                                        this.stickerView.invalidate();
                                        StickerView.isStickerTouch = H;
                                        findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                        findViewById.requestLayout();
                                    }
                                } catch (Exception e23) {
                                    TextSticker textSticker2222 = new TextSticker(getApplicationContext());
                                    textSticker2222.setDrawable(drawable3);
                                    textSticker2222.setText(obj);
                                    textSticker2222.resizeText();
                                    this.stickerView.addSticker(textSticker2222);
                                    this.textSticker = textSticker2222;
                                    this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                                    this.textSticker.setAlpha(Math.round((((float) FragmentText.opacityTxtProgress) / 100.0f) * 255.0f));
                                    this.stickerView.invalidate();
                                    StickerView.isStickerTouch = H;
                                    findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
                                    findViewById.requestLayout();
                                }
                            }
                        } catch (NotFoundException e24) {
                        }
                    }
                    TextSticker textSticker22222 = new TextSticker(getApplicationContext());
                    textSticker22222.setDrawable(drawable3);
                    textSticker22222.setText(obj);
                    textSticker22222.resizeText();
                    this.stickerView.addSticker(textSticker22222);
                    this.textSticker = textSticker22222;
                    if (FragmentText.fontTypeface != null) {
                        try {
                            this.textSticker.setTypeface(FragmentText.fontTypeface);
                        } catch (Exception e25) {
                            e25.printStackTrace();
                        }
                    }
                    this.textSticker.setTextShadowColor(FragmentText.shadowRadius, FragmentText.shadowX, FragmentText.shadowY, FragmentText.shadowColor);
                    if (FragmentText.currentColor == 0) {
                        try {
                            this.textSticker.setTextColor(FragmentText.txtColor);
                        } catch (Exception e26) {
                            e26.printStackTrace();
                        }
                    }
                    this.textSticker.setAlpha(Math.round((((float) FragmentText.opacityTxtProgress) / 100.0f) * 255.0f));
                    this.stickerView.invalidate();
                } catch (NotFoundException e27) {
                    e27.printStackTrace();
                }
            }
            StickerView.isStickerTouch = H;
            findViewById.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
            findViewById.requestLayout();
        }
        if (this.linearLayout.getVisibility() != 0) {
            try {
                this.linearLayout.setVisibility(8);
                if (this.customViewPager.getCurrentItem() == 0) {
                    try {
                        FragmentText.dismissSoftKeyboard(this.inputMethodManager, this.editText);
                    } catch (Exception e28) {
                        e28.printStackTrace();
                    }
                }
                this.customViewPager.setCurrentItem(1);
            } catch (Exception e29) {
                e29.printStackTrace();
            }
        }
    }

    private void g() {
        String[] strArr;
        File[] listFiles;
        this.stickerView = (StickerView) findViewById(R.id.sticker_view);
        StickerView.isStickerTouch = false;
        this.ac = (RelativeLayout) findViewById(R.id.stickerFullLayout);
        this.layout = (FrameLayout) findViewById(R.id.flEditor);
        this.view = findViewById(R.id.flEditor);
        this.ab = (RelativeLayout) findViewById(R.id.mainLayout);
        this.aa = (RelativeLayout) findViewById(R.id.frme);
        this.editText = (EditText) findViewById(R.id.addTxtEditText);
        this.view4 = (TextView) findViewById(R.id.stickerCount);
        this.imageView1 = (ImageView) findViewById(R.id.sticker);
        this.imageView1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoWatermarkActivity.this.fillStickerData();
            }
        });
        this.view1 = (ImageView) findViewById(R.id.addtext);
        this.view1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoWatermarkActivity.this.linearLayout.setVisibility(0);
                FragmentText.setDefaultValues(VideoWatermarkActivity.this.editText, VideoWatermarkActivity.this.activity);
            }
        });
        try {
            this.strings = getAssets().list("stickers");
        } catch (IOException unused) {
        }
        this.string = "punjabi_turbuns";
        this.itemArrayList.clear();
        StringBuilder sb = new StringBuilder();
        sb.append("stickers/");
        sb.append(this.strings[0]);
        this.strings1 = a(sb.toString());
        for (String str : this.strings1) {
            try {
                ArrayList<HsItem> arrayList = this.itemArrayList;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("assets://");
                sb2.append("");
                arrayList.add(new HsItem(sb2.toString(), H));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getFilesDir());
        sb3.append("/Stickers/");
        sb3.append(this.string);
        File file = new File(sb3.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        for (File file2 : file.listFiles()) {
            try {
                ArrayList<HsItem> arrayList2 = this.itemArrayList;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("file://");
                sb4.append(file2.getAbsolutePath());
                arrayList2.add(new HsItem(sb4.toString(), H));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        i();
        this.loader = ImageLoader.getInstance();
        this.view2 = (GridView) findViewById(R.id.horizontal_gridView);
        this.adapter = new ImageAdapter(this);
        this.view2.setAdapter(this.adapter);
        if (this.string2 != null) {
            try {
                Integer valueOf = Integer.valueOf(Integer.parseInt(this.string2));
                HsItem hsItem = (HsItem) this.itemArrayList.get(valueOf.intValue());
                new File(hsItem.path).getName();
                if (hsItem.isAvailable) {
                    this.bitmap = BitmapFactory.decodeStream(getAssets().open(this.strings1[valueOf.intValue()]));
                    LayoutInflater.from(this);
                    this.aa.setVisibility(8);
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("");
                    sb5.append(selectedPos);
                    Log.e("sticker", sb5.toString());
                    this.bitmap = BitmapFactory.decodeFile(hsItem.path.replace("file://", ""));
                    setResult(-1);
                }
                Log.e("position", this.string2);
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        this.view2.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                HsItem hsItem = (HsItem) VideoWatermarkActivity.this.itemArrayList.get(i);
                new File(hsItem.path).getName();
                VideoWatermarkActivity.this.arrayAdapter.notifyDataSetChanged();
                if (hsItem.isAvailable) {
                    try {
                        VideoWatermarkActivity.this.bitmap = BitmapFactory.decodeStream(VideoWatermarkActivity.this.getAssets().open(VideoWatermarkActivity.this.strings1[i]));
                        LayoutInflater.from(VideoWatermarkActivity.this);
                        VideoWatermarkActivity.this.aa.setVisibility(8);
                        StringBuilder sb = new StringBuilder();
                        sb.append("");
                        sb.append(VideoWatermarkActivity.selectedPos);
                        Log.e("sticker", sb.toString());
                    } catch (Exception unused) {
                        VideoWatermarkActivity.this.bitmap = BitmapFactory.decodeFile(hsItem.path.replace("file://", ""));
                    }
                    VideoWatermarkActivity.this.setResult(-1);
                }
            }
        });
        this.bitmap = null;
    }

    public void fillStickerData() {
        this.ac.setVisibility(0);
        this.ab.setVisibility(8);
        FragmentPagerItems fragmentPagerItems = new FragmentPagerItems(this.activity);
        fragmentPagerItems.add(FragmentPagerItem.of("Fragment_Sticker1", FragmentSticker.class));
        fragmentPagerItems.add(FragmentPagerItem.of("Fragment_Sticker2", FragmentSticker.class));
        fragmentPagerItems.add(FragmentPagerItem.of("Fragment_Sticker3", FragmentSticker.class));
        fragmentPagerItems.add(FragmentPagerItem.of("Fragment_Sticker4", FragmentSticker.class));
        fragmentPagerItems.add(FragmentPagerItem.of("Fragment_Sticker5", FragmentSticker.class));
        fragmentPagerItems.add(FragmentPagerItem.of("Fragment_Sticker6", FragmentSticker.class));
        fragmentPagerItems.add(FragmentPagerItem.of("Fragment_Sticker7", FragmentSticker.class));
        fragmentPagerItems.add(FragmentPagerItem.of("Fragment_Sticker8", FragmentSticker.class));
        fragmentPagerItems.add(FragmentPagerItem.of("Fragment_Sticker9", FragmentSticker.class));
        fragmentPagerItems.add(FragmentPagerItem.of("Fragment_Sticker10", FragmentSticker.class));
        this.ad.setAdapter(new FragmentPagerItemAdapter(getSupportFragmentManager(), fragmentPagerItems));
        this.ae.setViewPager(this.ad);
    }

    public void stickerCounting(String str) {
        this.view4.setText(str);
    }

    public void stickerLayout() {
        this.ac.setVisibility(8);
        this.ab.setVisibility(0);
        this.view4.setText("0");
        DataBinder.stickerValue = 0;
        new fetchSticker().execute(new String[0]);
    }

    private void h() {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.tab);
        viewGroup.addView(LayoutInflater.from(this).inflate(R.layout.videowatermark_demo_custom_tab_icons, viewGroup, false));
        this.ad = (ViewPager) findViewById(R.id.viewpager);
        this.ae = (SmartTabLayout) findViewById(R.id.viewpagertab);
        final LayoutInflater from = LayoutInflater.from(this.ae.getContext());
        this.ae.setCustomTabView(new TabProvider() {
            public View createTabView(ViewGroup viewGroup, int i, PagerAdapter pagerAdapter) {
                ImageView imageView = (ImageView) from.inflate(R.layout.videowatermark_customtabiconc, viewGroup, false);
                switch (i) {
                    case 0:
                        imageView.setImageDrawable(VideoWatermarkActivity.this.getResources().getDrawable(R.drawable.sticker_icon_a));
                        break;
                    case 1:
                        imageView.setImageDrawable(VideoWatermarkActivity.this.getResources().getDrawable(R.drawable.sticker_icon_b));
                        break;
                    case 2:
                        imageView.setImageDrawable(VideoWatermarkActivity.this.getResources().getDrawable(R.drawable.sticker_icon_c));
                        break;
                    case 3:
                        imageView.setImageDrawable(VideoWatermarkActivity.this.getResources().getDrawable(R.drawable.sticker_icon_d));
                        break;
                    case 4:
                        imageView.setImageDrawable(VideoWatermarkActivity.this.getResources().getDrawable(R.drawable.sticker_icon_e));
                        break;
                    case 5:
                        imageView.setImageDrawable(VideoWatermarkActivity.this.getResources().getDrawable(R.drawable.sticker_icon_f));
                        break;
                    case 6:
                        imageView.setImageDrawable(VideoWatermarkActivity.this.getResources().getDrawable(R.drawable.sticker_icon_g));
                        break;
                    case 7:
                        imageView.setImageDrawable(VideoWatermarkActivity.this.getResources().getDrawable(R.drawable.sticker_icon_h));
                        break;
                    case 8:
                        imageView.setImageDrawable(VideoWatermarkActivity.this.getResources().getDrawable(R.drawable.sticker_icon_i));
                        break;
                    case 9:
                        imageView.setImageDrawable(VideoWatermarkActivity.this.getResources().getDrawable(R.drawable.sticker_icon_j));
                        break;
                    default:
                        StringBuilder sb = new StringBuilder();
                        sb.append("Invalid position: ");
                        sb.append(i);
                        throw new IllegalStateException(sb.toString());
                }
                return imageView;
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        this.Rs.acquire();
        Log.i("VideoView", "In on resume");
        this.videoView.seekTo(this.playerState.getCurrentTime());
    }


    public void onPause() {
        this.Rs.release();
        super.onPause();
        Log.i("VideoView", "In on pause");
        this.playerState.setCurrentTime(this.videoView.getCurrentPosition());
    }

    private void a(File file) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }

    @Override
    public void onBackPressed() {
        if (this.ac.getVisibility() == 8 && this.linearLayout.getVisibility() == 8) {
            super.onBackPressed();
        } else if (this.ac.getVisibility() == 0) {
            stickerLayout();
        } else if (this.linearLayout.getVisibility() == 0) {
            textLayout();
        }
    }

    public void textLayout() {
        if (this.customViewPager.getCurrentItem() == 0) {
            FragmentText.dismissSoftKeyboard(this.inputMethodManager, this.editText);
        }
        this.linearLayout.setVisibility(8);
        this.customViewPager.setCurrentItem(1, H);
    }

    private String[] a(String str) {
        String[] strArr;
        try {
            strArr = getAssets().list(str);
        } catch (IOException e2) {
            e2.printStackTrace();
            strArr = null;
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("/");
                sb.append(strArr[i2]);
                strArr[i2] = sb.toString();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        return strArr;
    }

    private void i() {
        this.imageOptions1 = new DisplayImageOptions.Builder().cacheOnDisk(H).imageScaleType(ImageScaleType.NONE).showImageOnLoading(0).bitmapConfig(Config.ARGB_4444).build();
        this.imageOptions = new DisplayImageOptions.Builder().cacheOnDisk(H).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Config.ARGB_4444).build();
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(this.imageOptions).memoryCache(new WeakMemoryCache()).build());
    }

    private void j() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append("/VideoWaterMark/TMPIMG/");
        String sb2 = sb.toString();
        File file = new File(sb2);
        if (!file.exists()) {
            file.mkdirs();
        }
        String str = "tmp.png";
        File file2 = new File(file, str);
        if (file2.exists()) {
            file2.delete();
        }
        try {
            if (this.bitmap1 != null) {
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                this.bitmap1.compress(CompressFormat.PNG, 90, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append(str);
                this.string5 = sb3.toString();
            }
        } catch (Exception unused) {
        }
    }


    public void l() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VideoWatermarkActivity.this.finish();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return H;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            Intent intent = new Intent(this, ListVideoAndMyAlbumActivity.class);
            intent.setFlags(67108864);
            startActivity(intent);
            finish();
            return H;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (!this.stickerView.isLocked()) {
                this.stickerView.setLocked(H);
            }
            this.videoView.pause();
            a(this.file1);
            if (this.playerState.isValid()) {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                try {
                    if (VERSION.SDK_INT >= 14) {
                        mediaMetadataRetriever.setDataSource(this.playerState.getFilename(), new HashMap());
                    } else {
                        mediaMetadataRetriever.setDataSource(this.playerState.getFilename());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                StringBuilder sb = new StringBuilder();
                sb.append(this.anInt5);
                sb.append("");
                Log.e("Height", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.anInt3);
                sb2.append("");
                Log.e("Width", sb2.toString());
                float f2 = (float) this.anInt3;
                float f3 = (float) this.anInt5;
                float width = (float) this.videoView.getWidth();
                float height = (float) this.videoView.getHeight();
                int x2 = (int) ((this.stickerView.getX() * f2) / width);
                int y2 = (int) ((this.stickerView.getY() * f3) / height);
                float width2 = (float) this.stickerView.getWidth();
                float height2 = (float) this.stickerView.getHeight();
                this.layout.getDrawingCache();
                this.layout.setDrawingCacheEnabled(H);
                this.layout.buildDrawingCache();
                this.layout.getDrawingCache();
                Bitmap createBitmap = Bitmap.createBitmap(this.layout.getDrawingCache());
                this.layout.destroyDrawingCache();
                this.layout.setDrawingCacheEnabled(false);
                this.bitmap1 = Bitmap.createScaledBitmap(createBitmap, (int) ((f2 * width2) / width), ((int) ((f3 * height2) / height)) + 2, H);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(x2);
                sb3.append("");
                this.string3 = sb3.toString();
                StringBuilder sb4 = new StringBuilder();
                sb4.append(y2);
                sb4.append("");
                this.string4 = sb4.toString();
                j();
                ffmpegcommand();
                if (this.stickerView.isLocked()) {
                    this.stickerView.setLocked(false);
                }
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
