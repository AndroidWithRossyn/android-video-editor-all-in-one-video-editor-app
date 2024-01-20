package com.androworld.allinonevideoeditor.videocollage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Media;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import com.androworld.allinonevideoeditor.Adclick;
import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.StartActivity;
import com.androworld.allinonevideoeditor.UtilCommand;
import com.androworld.allinonevideoeditor.VideoPlayer;
import com.androworld.allinonevideoeditor.videocollage.json.CollageFrameJson;
import com.androworld.allinonevideoeditor.videocollage.json.FrameInfo;
import com.androworld.allinonevideoeditor.videocollage.json.FrameRCInfo;
import com.androworld.allinonevideoeditor.videocollage.model.AudioTrackData;
import com.androworld.allinonevideoeditor.videocollage.model.BorderAttribute;
import com.androworld.allinonevideoeditor.videocollage.model.CollageData;
import com.androworld.allinonevideoeditor.videocollage.stickers.ClgSingleFingerView;
import com.androworld.allinonevideoeditor.videocollage.stickers.ClgTagView;
import com.androworld.allinonevideoeditor.videocollage.stickers.StickerData;
import com.androworld.allinonevideoeditor.videocollage.stickers.StickerObj;
import com.androworld.allinonevideoeditor.videocollage.utils.BorderFrameLayout;
import com.androworld.allinonevideoeditor.videocollage.utils.DrawImageCanvas;
import com.androworld.allinonevideoeditor.videocollage.utils.ImageViewTouchListener;
import com.androworld.allinonevideoeditor.videocollage.utils.Utils;
import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.bumptech.glide.load.Key;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

@SuppressLint({"WrongConstant"})
public class VideoCollageMakerActivity extends AppCompatActivity implements OnSeekBarChangeListener {
    int Rs = 0;
    static ArrayList<FrameLayout> a = null;
    static final boolean aj = true;
    static int b = 0;
    static boolean c = false;
    static String d = "";
    ArrayList<ImageView> A;
    FrameLayout B;
    LinearLayout C;
    LinearLayout D;
    LinearLayout E;
    LinearLayout F;
    LinearLayout G;
    ListView H;
    Context I;
    MediaPlayer J = null;
    ProgressDialog K = null;
    PopupWindow L;
    int M = 0;
    RelativeLayout N;
    SeekBar O;
    SeekBar P;
    SeekBar Q;
    ArrayList<StickerObj> S = new ArrayList<>();
    TextView T;
    TextView U;
    TextView V;
    String W = "";
    OnClickListener X = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (VideoCollageMakerActivity.this.J != null && VideoCollageMakerActivity.this.J.isPlaying()) {
                try {
                    VideoCollageMakerActivity.this.J.pause();
                    VideoCollageMakerActivity.this.J.seekTo(VideoCollageMakerActivity.this.Rs);
                } catch (IllegalStateException e) {

                }
            }
            VideoCollageMakerActivity.b = Integer.parseInt(String.valueOf(view.getTag()));
            View inflate = ((LayoutInflater) VideoCollageMakerActivity.this.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.videocollage_selectpopupwindow, null);
            VideoCollageMakerActivity.this.L = new PopupWindow(inflate, -1, -1);
            VideoCollageMakerActivity.this.L.setOutsideTouchable(VideoCollageMakerActivity.aj);
            VideoCollageMakerActivity.this.L.setBackgroundDrawable(new BitmapDrawable());
            ((Button) inflate.findViewById(R.id.btn_selectimage)).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    VideoCollageMakerActivity.this.c(VideoCollageMakerActivity.b);
                    VideoCollageMakerActivity.this.L.dismiss();
                }
            });
            ((Button) inflate.findViewById(R.id.btn_selectvideo)).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    VideoCollageMakerActivity.this.b(VideoCollageMakerActivity.b);
                    VideoCollageMakerActivity.this.L.dismiss();
                }
            });
            VideoCollageMakerActivity.this.L.showAtLocation(VideoCollageMakerActivity.this.B, 17, 0, 0);
        }
    };
    OnClickListener Y = new OnClickListener() {
        @Override
        public void onClick(View view) {
            VideoCollageMakerActivity.this.f();
            VideoCollageMakerActivity.this.g();
            VideoCollageMakerActivity.this.G.setVisibility(0);
            VideoCollageMakerActivity.this.P.setProgress(((BorderFrameLayout) Utils.borderlayout.get(0)).getStrokeWidth());
            VideoCollageMakerActivity.this.P.setVisibility(0);
        }
    };
    OnClickListener Z = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (VideoCollageMakerActivity.this.J != null && VideoCollageMakerActivity.this.J.isPlaying()) {
                try {
                    VideoCollageMakerActivity.this.J.pause();
                    VideoCollageMakerActivity.this.J.seekTo(VideoCollageMakerActivity.this.Rs);
                } catch (IllegalStateException e) {

                }
            }
            if (VideoCollageMakerActivity.this.N.getVisibility() == 0) {
                try {
                    VideoCollageMakerActivity.this.N.startAnimation(AnimationUtils.loadAnimation(VideoCollageMakerActivity.this.getApplicationContext(), R.anim.bottomdown));
                    VideoCollageMakerActivity.this.N.setVisibility(8);
                } catch (NotFoundException e2) {
                    e2.printStackTrace();
                }
            }
            VideoCollageMakerActivity.b = Integer.parseInt(String.valueOf(view.getTag()));
            ((ImageView) VideoCollageMakerActivity.this.A.get(VideoCollageMakerActivity.b)).setImageBitmap(null);
            ((ImageView) VideoCollageMakerActivity.this.A.get(VideoCollageMakerActivity.b)).setBackgroundColor(Color.parseColor("#e8d9c8"));
            int i = 0;
            ((Button) VideoCollageMakerActivity.this.t.get(VideoCollageMakerActivity.b)).setVisibility(0);
            ((Button) VideoCollageMakerActivity.this.r.get(VideoCollageMakerActivity.b)).setVisibility(8);
            ((Button) VideoCollageMakerActivity.this.s.get(VideoCollageMakerActivity.b)).setVisibility(8);
            ((CollageData) Utils.collageData.get(VideoCollageMakerActivity.b)).setVideoUrl(null);
            ((CollageData) Utils.collageData.get(VideoCollageMakerActivity.b)).setIsImage(Boolean.valueOf(false));
            while (i < VideoCollageMakerActivity.this.i.size()) {
                try {
                    AudioTrackData audioTrackData = (AudioTrackData) VideoCollageMakerActivity.this.i.get(i);
                    if (audioTrackData.mapPosition == VideoCollageMakerActivity.b) {
                        try {
                            VideoCollageMakerActivity.this.i.remove(audioTrackData);
                            return;
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    } else {
                        i++;
                        continue;
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
        }
    };
    OnClickListener aa = new OnClickListener() {
        @Override
        public void onClick(View view) {
            VideoCollageMakerActivity.this.f();
            VideoCollageMakerActivity.this.g();
            VideoCollageMakerActivity.this.o.setBackgroundResource(R.drawable.color);
            VideoCollageMakerActivity.this.F.setVisibility(0);
            VideoCollageMakerActivity.this.k();
        }
    };
    OnClickListener ab = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (VideoCollageMakerActivity.this.J != null && VideoCollageMakerActivity.this.J.isPlaying()) {
                try {
                    VideoCollageMakerActivity.this.J.pause();
                    VideoCollageMakerActivity.this.J.seekTo(VideoCollageMakerActivity.this.Rs);
                } catch (IllegalStateException e) {

                }
            }
            VideoCollageMakerActivity.b = Integer.parseInt(String.valueOf(view.getTag()));
            int height = VideoCollageMakerActivity.this.v / ((CollageData) Utils.collageData.get(VideoCollageMakerActivity.b)).getHeight();
            int width = VideoCollageMakerActivity.this.v / ((CollageData) Utils.collageData.get(VideoCollageMakerActivity.b)).getWidth();
            Intent intent = new Intent(VideoCollageMakerActivity.this, VideoCropAndCutActivity.class);
            intent.putExtra("videopath", ((CollageData) Utils.collageData.get(VideoCollageMakerActivity.b)).getVideoUrl());
            intent.putExtra("ratio_x", height);
            intent.putExtra("ratio_y", width);
            intent.putExtra("frmpos", VideoCollageMakerActivity.b);
            VideoCollageMakerActivity.this.startActivityForResult(intent, 13);
        }
    };
    OnClickListener ac = new OnClickListener() {
        @Override
        public void onClick(View view) {
            VideoCollageMakerActivity.this.f();
            VideoCollageMakerActivity.this.g();
            VideoCollageMakerActivity.this.D.setVisibility(0);
            VideoCollageMakerActivity.this.O.setProgress(((BorderFrameLayout) Utils.borderlayout.get(0)).getColorAlpha());
            VideoCollageMakerActivity.this.O.setVisibility(0);
        }
    };
    OnClickListener ad = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (VideoCollageMakerActivity.this.J.isPlaying()) {
                try {
                    VideoCollageMakerActivity.this.J.pause();
                    VideoCollageMakerActivity.this.l.setBackgroundResource(R.drawable.play2);
                    return;
                } catch (IllegalStateException e) {

                }
            }
            VideoCollageMakerActivity.this.J.start();
            VideoCollageMakerActivity.this.l.setBackgroundResource(R.drawable.pause2);
        }
    };
    OnClickListener ae = new OnClickListener() {
        @Override
        public void onClick(View view) {
            VideoCollageMakerActivity.this.f();
            VideoCollageMakerActivity.this.g();
            VideoCollageMakerActivity.this.k.setBackgroundResource(R.drawable.sticker);
            VideoCollageMakerActivity.this.C.setVisibility(0);
            Intent intent = new Intent(VideoCollageMakerActivity.this, SelectStickerActivity.class);
            intent.putExtra("folderName", VideoCollageMakerActivity.this.es[0]);
            VideoCollageMakerActivity.this.startActivityForResult(intent, 4);
        }
    };
    OnClickListener af = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (VideoCollageMakerActivity.this.J != null && VideoCollageMakerActivity.this.J.isPlaying()) {
                try {
                    VideoCollageMakerActivity.this.J.pause();
                    VideoCollageMakerActivity.this.J = null;
                    VideoCollageMakerActivity.this.l.setBackgroundResource(R.drawable.play2);
                } catch (IllegalStateException e) {

                }
            }
            VideoCollageMakerActivity.this.j = 1;
            VideoCollageMakerActivity.this.f();
            VideoCollageMakerActivity.this.g();
            int size = Utils.collageData.size();
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                try {
                    if (((CollageData) Utils.collageData.get(i3)).getVideoUrl() != null) {
                        i++;
                    }
                    if (((CollageData) Utils.collageData.get(i3)).getIsImage().booleanValue()) {
                        i2++;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (i < size) {
                try {
                    Toast.makeText(VideoCollageMakerActivity.this, "Please Fill Videos or Images...", 0).show();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            } else if (i2 == size) {
                try {
                    Toast.makeText(VideoCollageMakerActivity.this, "Music Allowed to Video Collage Only...", 0).show();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            } else {
                try {
                    VideoCollageMakerActivity.this.n.setBackgroundResource(R.drawable.music1);
                    VideoCollageMakerActivity.this.E.setVisibility(0);
                    if (VideoCollageMakerActivity.this.J != null && VideoCollageMakerActivity.this.J.isPlaying()) {
                        try {
                            VideoCollageMakerActivity.this.J.pause();
                        } catch (IllegalStateException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (VideoCollageMakerActivity.c) {
                        try {
                            VideoCollageMakerActivity.this.N.setVisibility(0);
                        } catch (Exception e6) {
                            e6.printStackTrace();
                        }
                    } else {
                        try {
                            VideoCollageMakerActivity.this.i();
                        } catch (Exception e7) {
                            e7.printStackTrace();
                        }
                    }
                } catch (IllegalStateException e8) {
                    e8.printStackTrace();
                }
            }
        }
    };
    OnClickListener ag = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (VideoCollageMakerActivity.this.J != null && VideoCollageMakerActivity.this.J.isPlaying()) {
                try {
                    VideoCollageMakerActivity.this.J.pause();
                } catch (IllegalStateException e) {

                }
            }
            VideoCollageMakerActivity.this.J = null;
            VideoCollageMakerActivity.d = "";
            VideoCollageMakerActivity.c = false;
            VideoCollageMakerActivity.this.N.setVisibility(8);
        }
    };
    Runnable ah = new Runnable() {
        public void run() {
            VideoCollageMakerActivity.this.j();
            if (VideoCollageMakerActivity.this.K != null && VideoCollageMakerActivity.this.K.isShowing()) {
                try {
                    VideoCollageMakerActivity.this.K.dismiss();
                } catch (Exception e) {

                }
            }
            VideoCollageMakerActivity.c = false;
            VideoCollageMakerActivity.d = "";
            VideoCollageMakerActivity.this.finish();
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.fromFile(new File(VideoCollageMakerActivity.this.W)));
            VideoCollageMakerActivity.this.sendBroadcast(intent);
            VideoCollageMakerActivity.this.c();
        }
    };
    Runnable ai = new Runnable() {
        public void run() {
            VideoCollageMakerActivity.this.j();
            if (VideoCollageMakerActivity.this.K != null && VideoCollageMakerActivity.this.K.isShowing()) {
                try {
                    VideoCollageMakerActivity.this.K.dismiss();
                } catch (Exception e) {

                }
            }
            VideoCollageMakerActivity.c = false;
            VideoCollageMakerActivity.d = "";
            Toast.makeText(VideoCollageMakerActivity.this, "Collage Created Successfully !!!", 0).show();
            Intent intent = new Intent(VideoCollageMakerActivity.this, StartActivity.class);
            intent.addFlags(335544320);
            VideoCollageMakerActivity.this.startActivity(intent);
        }
    };
    String[] es;

    AbsoluteLayout f;
    a g;
    AlertDialog h;
    ArrayList<AudioTrackData> i;
    int j = 99;
    Button k;
    Button l;
    Button m;
    Button n;
    Button o;
    Button p;
    RelativeLayout q;
    ArrayList<Button> r;
    ArrayList<Button> s;
    ArrayList<Button> t;
    CollageFrameJson u = null;
    int v;
    int w;
    int x;
    Handler y = new Handler();
    String z = "";
    private Ads ads;

    private class a extends BaseAdapter {
        private LayoutInflater b;
        private final Context c;

        class C0034a {
            ImageButton a;
            RelativeLayout b;
            TextView c;

            C0034a() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public a(Context context) {
            this.c = context;
            this.b = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return VideoCollageMakerActivity.this.i.size();
        }

        public Object getItem(int i) {
            return VideoCollageMakerActivity.this.i.get(i);
        }

        @SuppressLint({"InflateParams"})
        public View getView(final int i, View view, ViewGroup viewGroup) {
            C0034a aVar;
            Exception e;
            if (view == null) {
                try {
                    View inflate = this.b.inflate(R.layout.videocollage_row_listaudiotrack, null);
                    try {
                        C0034a aVar2 = new C0034a();
                        try {
                            aVar2.a = (ImageButton) inflate.findViewById(R.id.imgbtn_chk);
                            aVar2.c = (TextView) inflate.findViewById(R.id.tvAudioTrack);
                            aVar2.b = (RelativeLayout) inflate.findViewById(R.id.rev_layout);
                            inflate.setTag(aVar2);
                            View view2 = inflate;
                            aVar = aVar2;
                            view = view2;
                        } catch (Exception e2) {
                            e = e2;
                            View view3 = inflate;
                            aVar = aVar2;
                            view = view3;

                            TextView textView = aVar.c;
                            StringBuilder sb = new StringBuilder();
                            sb.append("Track :");
                            sb.append(((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).mapPosition + 1);
                            textView.setText(sb.toString());
                            if (!((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected) {
                            }
                            aVar.b.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int i = 0;
                                    while (i < VideoCollageMakerActivity.this.i.size()) {
                                        try {
                                            if (i == i) {
                                                try {
                                                    ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = VideoCollageMakerActivity.aj;
                                                } catch (Exception e) {

                                                }
                                                i++;
                                            } else {
                                                try {
                                                    ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = false;
                                                } catch (Exception e2) {
                                                    e2.printStackTrace();
                                                }
                                                i++;
                                            }
                                        } catch (Exception e3) {
                                            e3.printStackTrace();
                                        }
                                    }
                                    a.this.notifyDataSetChanged();
                                }
                            });
                            aVar.a.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int i = 0;
                                    while (i < VideoCollageMakerActivity.this.i.size()) {
                                        try {
                                            if (i == i) {
                                                try {
                                                    ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = VideoCollageMakerActivity.aj;
                                                } catch (Exception e) {

                                                }
                                                i++;
                                            } else {
                                                try {
                                                    ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = false;
                                                } catch (Exception e2) {
                                                    e2.printStackTrace();
                                                }
                                                i++;
                                            }
                                        } catch (Exception e3) {
                                            e3.printStackTrace();
                                        }
                                    }
                                    a.this.notifyDataSetChanged();
                                }
                            });
                            return view;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        view = inflate;
                        aVar = null;

                        TextView textView2 = aVar.c;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Track :");
                        sb2.append(((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).mapPosition + 1);
                        textView2.setText(sb2.toString());
                        if (!((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected) {
                        }
                        aVar.b.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int i = 0;
                                while (i < VideoCollageMakerActivity.this.i.size()) {
                                    try {
                                        if (i == i) {
                                            try {
                                                ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = VideoCollageMakerActivity.aj;
                                            } catch (Exception e) {

                                            }
                                            i++;
                                        } else {
                                            try {
                                                ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = false;
                                            } catch (Exception e2) {
                                                e2.printStackTrace();
                                            }
                                            i++;
                                        }
                                    } catch (Exception e3) {
                                        e3.printStackTrace();
                                    }
                                }
                                a.this.notifyDataSetChanged();
                            }
                        });
                        aVar.a.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int i = 0;
                                while (i < VideoCollageMakerActivity.this.i.size()) {
                                    try {
                                        if (i == i) {
                                            try {
                                                ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = VideoCollageMakerActivity.aj;
                                            } catch (Exception e) {

                                            }
                                            i++;
                                        } else {
                                            try {
                                                ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = false;
                                            } catch (Exception e2) {
                                                e2.printStackTrace();
                                            }
                                            i++;
                                        }
                                    } catch (Exception e3) {
                                        e3.printStackTrace();
                                    }
                                }
                                a.this.notifyDataSetChanged();
                            }
                        });
                        return view;
                    }
                } catch (Exception e4) {
                    Exception exc = e4;
                    aVar = null;
                    e = exc;

                    TextView textView22 = aVar.c;
                    StringBuilder sb22 = new StringBuilder();
                    sb22.append("Track :");
                    sb22.append(((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).mapPosition + 1);
                    textView22.setText(sb22.toString());
                    if (!((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected) {
                    }
                    aVar.b.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int i = 0;
                            while (i < VideoCollageMakerActivity.this.i.size()) {
                                try {
                                    if (i == i) {
                                        try {
                                            ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = VideoCollageMakerActivity.aj;
                                        } catch (Exception e) {

                                        }
                                        i++;
                                    } else {
                                        try {
                                            ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = false;
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                        }
                                        i++;
                                    }
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                            a.this.notifyDataSetChanged();
                        }
                    });
                    aVar.a.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int i = 0;
                            while (i < VideoCollageMakerActivity.this.i.size()) {
                                try {
                                    if (i == i) {
                                        try {
                                            ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = VideoCollageMakerActivity.aj;
                                        } catch (Exception e) {

                                        }
                                        i++;
                                    } else {
                                        try {
                                            ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = false;
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                        }
                                        i++;
                                    }
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                            a.this.notifyDataSetChanged();
                        }
                    });
                    return view;
                }
            } else {
                try {
                    aVar = (C0034a) view.getTag();
                } catch (Exception e5) {
                    e5.printStackTrace();
                    aVar = null;
                }
            }
            TextView textView222 = aVar.c;
            StringBuilder sb222 = new StringBuilder();
            sb222.append("Track :");
            sb222.append(((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).mapPosition + 1);
            textView222.setText(sb222.toString());
            if (!((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected) {
                try {
                    aVar.a.setBackgroundResource(R.drawable.check);
                } catch (Exception e6) {
                    e6.printStackTrace();
                }
            } else {
                try {
                    aVar.a.setBackgroundResource(R.drawable.uncheck);
                } catch (Exception e7) {
                    e7.printStackTrace();
                }
            }
            aVar.b.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = 0;
                    while (i < VideoCollageMakerActivity.this.i.size()) {
                        try {
                            if (i == i) {
                                try {
                                    ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = VideoCollageMakerActivity.aj;
                                } catch (Exception e) {

                                }
                                i++;
                            } else {
                                try {
                                    ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = false;
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                i++;
                            }
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                    a.this.notifyDataSetChanged();
                }
            });
            aVar.a.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = 0;
                    while (i < VideoCollageMakerActivity.this.i.size()) {
                        try {
                            if (i == i) {
                                try {
                                    ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = VideoCollageMakerActivity.aj;
                                } catch (Exception e) {

                                }
                                i++;
                            } else {
                                try {
                                    ((AudioTrackData) VideoCollageMakerActivity.this.i.get(i)).isSelected = false;
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                i++;
                            }
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                    a.this.notifyDataSetChanged();
                }
            });
            return view;
        }
    }

    private class b extends AsyncTask<Void, Void, Boolean> {
        DrawImageCanvas a;

        private b() {
            this.a = null;
        }


        public void onPreExecute() {
            Iterator it = Utils.clgstickerviewsList.iterator();
            while (it.hasNext()) {
                try {
                    ((StickerData) it.next()).singlefview.hidePushView();
                } catch (Exception e) {

                }
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            VideoCollageMakerActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.a = new DrawImageCanvas(VideoCollageMakerActivity.this.I, displayMetrics.widthPixels, displayMetrics.widthPixels);
        }


        public Boolean doInBackground(Void... voidArr) {
            VideoCollageMakerActivity.this.z = VideoCollageMakerActivity.this.a(this.a.drawCanvas());
            return Boolean.valueOf(VideoCollageMakerActivity.aj);
        }


        public void onPostExecute(Boolean bool) {
            VideoCollageMakerActivity.this.y.postDelayed(VideoCollageMakerActivity.this.ai, 1000);
        }
    }

    public void onProgressChanged(SeekBar seekBar, int i2, boolean z2) {
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.videocollagemakeractivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Collage Maker");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (aj || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(aj);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.M = getIntent().getIntExtra("position", 0);
            Utils.position = this.M;
            if (Utils.collageData.size() > 0) {
                Utils.collageData.clear();
            }
            this.I = this;
            try {
                this.es = getAssets().list("stickers");
            } catch (IOException unused) {
            }
            e();
            a();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.u = (CollageFrameJson) new Gson().fromJson(loadJSONFromAsset(), new TypeToken<CollageFrameJson>() {
            }.getType());
            this.v = displayMetrics.widthPixels;
            this.A = new ArrayList<>();
            a = new ArrayList<>();
            this.t = new ArrayList<>();
            this.r = new ArrayList<>();
            this.s = new ArrayList<>();
            this.i = new ArrayList<>();
            if (Utils.borderParam.size() > 0) {
                try {
                    Utils.borderParam.clear();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.B.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                public boolean onPreDraw() {
                    VideoCollageMakerActivity.this.B.getViewTreeObserver().removeOnPreDrawListener(this);
                    int convertDpToPixel = VideoCollageMakerActivity.convertDpToPixel((int) VideoCollageMakerActivity.this.getResources().getDimension(R.dimen.collage_middle_padding), VideoCollageMakerActivity.this.getApplicationContext());
                    VideoCollageMakerActivity.this.w = VideoCollageMakerActivity.this.B.getWidth() - convertDpToPixel;
                    VideoCollageMakerActivity.this.x = VideoCollageMakerActivity.this.B.getHeight() - convertDpToPixel;
                    VideoCollageMakerActivity.this.f = new AbsoluteLayout(VideoCollageMakerActivity.this.getApplicationContext());
                    LinearLayout linearLayout = new LinearLayout(VideoCollageMakerActivity.this.getApplicationContext());
                    linearLayout.setLayoutParams(new LayoutParams(VideoCollageMakerActivity.this.v, VideoCollageMakerActivity.this.v));
                    linearLayout.setGravity(17);
                    linearLayout.setX(0.0f);
                    linearLayout.setY(0.0f);
                    VideoCollageMakerActivity.this.f.addView(linearLayout);
                    VideoCollageMakerActivity.this.B.addView(VideoCollageMakerActivity.this.f);
                    VideoCollageMakerActivity.this.a(VideoCollageMakerActivity.this.M);
                    return VideoCollageMakerActivity.aj;
                }
            });
            ads = new Ads();
            ads.Interstitialload(this);
            return;
        }
        throw new AssertionError();
    }

    public void c() {
        ads.showInd(new Adclick() {
            @Override
            public void onclicl() {
                d();
            }
        });
    }


    public void d() {
        Intent intent = new Intent(getApplicationContext(), VideoPlayer.class);
        intent.setFlags(67108864);
        intent.putExtra("song", this.W);
        startActivity(intent);
        finish();
    }

    public void collagecommand() {
        String str;
        String str2;
        int i2 = 0;
        int i3;
        int i4;
        String str3;
        String str4;
        int i5 = 0;
        int i6;
        int i7;
        String str5;
        String str6;
        int i8 = 0;
        int i9;
        int i10;
        String str7;
        String str8;
        int i11 = 0;
        int i12;
        int i13;
        String str9;
        String str10;
        int i14 = 0;
        int i15;
        int i16;
        String str11;
        String str12;
        int i17 = 0;
        int i18;
        int i19;
        String str13;
        String str14;
        int i20 = 0;
        int i21;
        int i22;
        String str15;
        String str16;
        int i23 = 0;
        int i24;
        int i25;
        String str17;
        String str18;
        int i26 = 0;
        int i27;
        int i28;
        String str19;
        String str20;
        int i29 = 0;
        int i30;
        int i31;
        String str21;
        String str22;
        int size = Utils.collageData.size();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        StringBuilder sb = new StringBuilder();
        sb.append(displayMetrics.widthPixels);
        sb.append("customViewPager");
        sb.append(displayMetrics.widthPixels);
        String sb2 = sb.toString();
        Iterator it = Utils.clgstickerviewsList.iterator();
        while (it.hasNext()) {
            try {
                ((StickerData) it.next()).singlefview.hidePushView();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        int strokeWidth = ((BorderFrameLayout) Utils.borderlayout.get(0)).getStrokeWidth() / 2;
        boolean z2 = aj;
        String format = String.format("#%06X", new Object[]{Integer.valueOf(((BorderFrameLayout) Utils.borderlayout.get(0)).getColor() & ViewCompat.MEASURED_SIZE_MASK)});
        String valueOf = String.valueOf(((((float) ((BorderFrameLayout) Utils.borderlayout.get(0)).getColorAlpha()) * 100.0f) / 255.0f) / 100.0f);
        if (valueOf.equals("1.0") || valueOf.equals("0.0")) {
            try {
                valueOf.replace(".0", "");
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        } else {
            try {
                valueOf.replace("0", "");
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb3.append("/");
        Resources resources = getResources();
        int i32 = R.string.MainFolderName;
        sb3.append(resources.getString(R.string.MainFolderName));
        sb3.append("/");
        sb3.append(getResources().getString(R.string.VideoCollage));
        String sb4 = sb3.toString();
        File file = new File(sb4);
        if (!file.exists()) {
            try {
                file.mkdir();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
        StringBuilder sb5 = new StringBuilder(String.valueOf(sb4));
        sb5.append("/COLLAGE_");
        sb5.append(System.currentTimeMillis());
        sb5.append(".mkv");
        this.W = sb5.toString();
        int i33 = 0;
        boolean z3 = true;
        int i34 = 99999;
        int i35 = 0;
        while (i35 < size) {
            try {
                if (((CollageData) Utils.collageData.get(i35)).getIsImage().booleanValue()) {
                    try {
                        ((FrameLayout) a.get(i35)).postInvalidate();
                        ((FrameLayout) a.get(i35)).setDrawingCacheEnabled(z2);
                        ((FrameLayout) a.get(i35)).buildDrawingCache();
                        Bitmap createBitmap = Bitmap.createBitmap(((FrameLayout) a.get(i35)).getDrawingCache());
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                        sb6.append("/");
                        sb6.append(getResources().getString(i32));
                        sb6.append("/");
                        sb6.append(getResources().getString(R.string.VideoCollage));
                        sb6.append("/TMPIMG");
                        String sb7 = sb6.toString();
                        File file2 = new File(sb7);
                        if (!file2.exists()) {
                            try {
                                file2.mkdir();
                            } catch (Exception e6) {
                                e6.printStackTrace();
                            }
                        }
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append(sb7);
                        sb8.append("/temp");
                        sb8.append(i35);
                        sb8.append(".jpg");
                        File file3 = new File(sb8.toString());
                        ((CollageData) Utils.collageData.get(i35)).setVideoUrl(file3.getAbsolutePath());
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file3);
                            try {
                                createBitmap.compress(CompressFormat.JPEG, 70, fileOutputStream);
                                fileOutputStream.flush();
                                fileOutputStream.close();
                            } catch (Exception e7) {
                                e7.printStackTrace();
                                i33++;
                            }
                        } catch (Exception e8) {
                            e8.printStackTrace();
                            i33++;
                        }
                        i33++;
                    } catch (NotFoundException e9) {
                        e9.printStackTrace();
                    }
                    i35++;
                    z2 = aj;
                    i32 = R.string.MainFolderName;
                } else {
                    if (z3) {
                        try {
                            i34 = ((CollageData) Utils.collageData.get(i35)).getDurationTime();
                            z3 = false;
                        } catch (Exception e10) {
                            try {
                                e10.printStackTrace();
                            } catch (Exception e11) {
                                e11.printStackTrace();
                            }
                        }
                    }
                    int durationTime = ((CollageData) Utils.collageData.get(i35)).getDurationTime();
                    if (durationTime < i34) {
                        i34 = durationTime;
                    }
                    i35++;
                    z2 = aj;
                    i32 = R.string.MainFolderName;
                }
            } catch (NotFoundException e12) {
                e12.printStackTrace();
            }
        }
        h();
        if (i33 == size) {
            new b().execute(new Void[0]);
        }
        ArrayList arrayList = new ArrayList();
        String str23 = "";
        String str24 = "";
        String str25 = "";
        String str26 = "out";
        switch (this.M) {
            case 1:
                String str27 = str23;
                String str28 = str24;
                String str29 = str25;
                int i36 = 2;
                StringBuilder sb9 = new StringBuilder();
                sb9.append(", drawbox=x=0:y=0:width=");
                int i37 = strokeWidth / 2;
                sb9.append(((CollageData) Utils.collageData.get(0)).getWidth() + i37);
                sb9.append(":height=");
                sb9.append(((CollageData) Utils.collageData.get(0)).getHeight());
                sb9.append(":thickness=");
                sb9.append(strokeWidth);
                sb9.append(":color=");
                sb9.append(format);
                sb9.append("@");
                sb9.append(valueOf);
                sb9.append(" [upperleft];");
                StringBuilder sb10 = new StringBuilder();
                sb10.append(", drawbox=x=");
                sb10.append(-i37);
                sb10.append(":y=0:width=");
                sb10.append(((CollageData) Utils.collageData.get(1)).getWidth() + i37);
                sb10.append(":height=");
                sb10.append(((CollageData) Utils.collageData.get(1)).getHeight());
                sb10.append(":thickness=");
                sb10.append(strokeWidth);
                sb10.append(":color=");
                sb10.append(format);
                sb10.append("@");
                sb10.append(valueOf);
                sb10.append(" [upperright];");
                String[] strArr = {sb9.toString(), sb10.toString()};
                arrayList.add("-y");
                String str30 = str27;
                String str31 = str28;
                for (int i38 = 0; i38 < size; i38++) {
                    String str32 = "";
                    try {
                        if (((CollageData) Utils.collageData.get(i38)).getIsImage().booleanValue()) {
                            try {
                                arrayList.add("-loop");
                                arrayList.add("1");
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i38)).getVideoUrl());
                                StringBuilder sb11 = new StringBuilder(String.valueOf(str30));
                                sb11.append(" [");
                                sb11.append(i38);
                                sb11.append(":v] trim=duration=");
                                sb11.append(i34);
                                sb11.append(" [v");
                                int i39 = i38 + 1;
                                sb11.append(i39);
                                sb11.append("];");
                                str2 = sb11.toString();
                                try {
                                    StringBuilder sb12 = new StringBuilder();
                                    sb12.append(" [v");
                                    sb12.append(i39);
                                    sb12.append("] setpts=PTS-STARTPTS, scale=");
                                    sb12.append(((CollageData) Utils.collageData.get(i38)).getWidth());
                                    sb12.append("x");
                                    sb12.append(((CollageData) Utils.collageData.get(i38)).getHeight());
                                    str32 = sb12.toString();
                                } catch (Exception e13) {
                                    try {
                                        str30 = str2;
                                        StringBuilder sb13 = new StringBuilder(String.valueOf(str31));
                                        sb13.append(str32);
                                        sb13.append(strArr[i38]);
                                        str31 = sb13.toString();
                                    } catch (Exception e14) {
                                        str30 = str2;
                                    }
                                }
                            } catch (Exception e15) {
                                str2 = str30;
                                str30 = str2;
                                StringBuilder sb132 = new StringBuilder(String.valueOf(str31));
                                sb132.append(str32);
                                sb132.append(strArr[i38]);
                                str31 = sb132.toString();
                            }
                            str30 = str2;
                        } else {
                            try {
                                arrayList.add("-ss");
                                arrayList.add(((CollageData) Utils.collageData.get(i38)).getStartTime());
                                arrayList.add("-t");
                                arrayList.add(String.valueOf(i34));
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i38)).getVideoUrl());
                                StringBuilder sb14 = new StringBuilder();
                                sb14.append(" [");
                                sb14.append(i38);
                                sb14.append(":v] setpts=PTS-STARTPTS,crop=w=");
                                sb14.append(((CollageData) Utils.collageData.get(i38)).getCrop_width());
                                sb14.append(":h=");
                                sb14.append(((CollageData) Utils.collageData.get(i38)).getCrop_height());
                                sb14.append(":x=");
                                sb14.append(((CollageData) Utils.collageData.get(i38)).getCrop_X());
                                sb14.append(":y=");
                                sb14.append(((CollageData) Utils.collageData.get(i38)).getCrop_Y());
                                sb14.append(", scale=");
                                sb14.append(((CollageData) Utils.collageData.get(i38)).getWidth());
                                sb14.append("x");
                                sb14.append(((CollageData) Utils.collageData.get(i38)).getHeight());
                                str32 = sb14.toString();
                            } catch (Exception e16) {
                                e16.printStackTrace();
                            }
                        }
                        StringBuilder sb1322 = new StringBuilder(String.valueOf(str31));
                        sb1322.append(str32);
                        sb1322.append(strArr[i38]);
                        str31 = sb1322.toString();
                    } catch (Exception e17) {
                        str2 = str30;
                        str30 = str2;
                    }
                }
                StringBuilder sb15 = new StringBuilder();
                sb15.append("nullsrc=size=");
                sb15.append(sb2);
                sb15.append(" [base];");
                sb15.append(str30);
                sb15.append(str31);
                sb15.append(" [base][upperleft] overlay=shortest=1 [tmp1]; [tmp1][upperright] overlay=shortest=1:x=");
                sb15.append(((CollageData) Utils.collageData.get(1)).getXPoint());
                String sb16 = sb15.toString();
                if (c) {
                    try {
                        arrayList.add("-ss");
                        arrayList.add(String.valueOf(this.Rs / 1000));
                        arrayList.add("-i");
                        arrayList.add(d);
                        i36 = 3;
                    } catch (Exception e18) {
                        e18.printStackTrace();
                    }
                }
                for (int i40 = 0; i40 < this.S.size(); i40++) {
                    try {
                        StickerObj stickerObj = (StickerObj) this.S.get(i40);
                        arrayList.add("-i");
                        arrayList.add(stickerObj.StickerPath);
                    } catch (Exception e19) {
                        e19.printStackTrace();
                    }
                }
                String str33 = str29;
                for (int i41 = 0; i41 < this.S.size(); i41++) {
                    try {
                        StickerObj stickerObj2 = (StickerObj) this.S.get(i41);
                        StringBuilder sb17 = new StringBuilder(String.valueOf(str33));
                        sb17.append("[");
                        sb17.append(str26);
                        sb17.append("];");
                        sb17.append("[");
                        sb17.append(str26);
                        sb17.append("]");
                        sb17.append("[");
                        sb17.append(i36);
                        sb17.append(":v] overlay=x=");
                        sb17.append(stickerObj2.StickerX);
                        sb17.append(":y=");
                        sb17.append(stickerObj2.StickerY);
                        str = sb17.toString();
                        try {
                            StringBuilder sb18 = new StringBuilder(String.valueOf(str26));
                            sb18.append(i41);
                            i36++;
                            str26 = sb18.toString();
                        } catch (Exception e20) {
                            str33 = str;
                        }
                    } catch (Exception e21) {
                        str = str33;
                        str33 = str;
                    }
                    str33 = str;
                }
                arrayList.add("-filter_complex");
                StringBuilder sb19 = new StringBuilder();
                sb19.append(sb16);
                sb19.append(str33);
                arrayList.add(sb19.toString());
                if (c) {
                    try {
                        arrayList.add("-map");
                        arrayList.add("2:a");
                    } catch (Exception e22) {
                        e22.printStackTrace();
                    }
                } else {
                    int i42 = 0;
                    while (i42 < this.i.size()) {
                        try {
                            try {
                                AudioTrackData audioTrackData = (AudioTrackData) this.i.get(i42);
                                if (audioTrackData.isSelected) {
                                    try {
                                        arrayList.add("-map");
                                        StringBuilder sb20 = new StringBuilder();
                                        sb20.append(audioTrackData.mapPosition);
                                        sb20.append(":a");
                                        arrayList.add(sb20.toString());
                                    } catch (Exception e23) {
                                        e23.printStackTrace();
                                    }
                                } else {
                                    i42++;
                                }
                            } catch (Exception e24) {
                                e24.printStackTrace();
                            }
                        } catch (Exception e25) {
                            e25.printStackTrace();
                        }
                    }
                }
                arrayList.add("-c:a");
                arrayList.add("copy");
                arrayList.add("-ab");
                arrayList.add("128k");
                arrayList.add("-c:v");
                arrayList.add("mpeg4");
                arrayList.add("-r");
                arrayList.add("15");
                arrayList.add("-b:v");
                arrayList.add("2500k");
                arrayList.add("-sample_fmt");
                arrayList.add("s16");
                arrayList.add("-strict");
                arrayList.add("experimental");
                arrayList.add("-ss");
                arrayList.add("0");
                arrayList.add("-t");
                arrayList.add(String.valueOf(i34));
                arrayList.add(this.W);
                a(arrayList, this.W);
                break;
            case 2:
                String str34 = str23;
                String str35 = str24;
                String str36 = str25;
                StringBuilder sb21 = new StringBuilder();
                sb21.append(", drawbox=x=0:y=0:width=");
                sb21.append(((CollageData) Utils.collageData.get(0)).getWidth());
                sb21.append(":height=");
                int i43 = strokeWidth / 2;
                sb21.append(((CollageData) Utils.collageData.get(0)).getHeight() + i43);
                sb21.append(":thickness=");
                sb21.append(strokeWidth);
                sb21.append(":color=");
                sb21.append(format);
                sb21.append("@");
                sb21.append(valueOf);
                sb21.append(" [upperleft];");
                StringBuilder sb22 = new StringBuilder();
                sb22.append(", drawbox=x=0:y=");
                sb22.append(-i43);
                sb22.append(":width=");
                sb22.append(((CollageData) Utils.collageData.get(1)).getWidth());
                sb22.append(":height=");
                sb22.append(((CollageData) Utils.collageData.get(1)).getHeight() + i43);
                sb22.append(":thickness=");
                sb22.append(strokeWidth);
                sb22.append(":color=");
                sb22.append(format);
                sb22.append("@");
                sb22.append(valueOf);
                sb22.append(" [upperright];");
                String[] strArr2 = {sb21.toString(), sb22.toString()};
                arrayList.add("-y");
                String str37 = str34;
                String str38 = str35;
                for (int i44 = 0; i44 < size; i44++) {
                    String str39 = "";
                    try {
                        if (((CollageData) Utils.collageData.get(i44)).getIsImage().booleanValue()) {
                            try {
                                arrayList.add("-loop");
                                arrayList.add("1");
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i44)).getVideoUrl());
                                StringBuilder sb23 = new StringBuilder(String.valueOf(str37));
                                sb23.append(" [");
                                sb23.append(i44);
                                sb23.append(":v] trim=duration=");
                                sb23.append(i34);
                                sb23.append(" [v");
                                int i45 = i44 + 1;
                                sb23.append(i45);
                                sb23.append("];");
                                str4 = sb23.toString();
                                try {
                                    StringBuilder sb24 = new StringBuilder();
                                    sb24.append(" [v");
                                    sb24.append(i45);
                                    sb24.append("] setpts=PTS-STARTPTS, scale=");
                                    sb24.append(((CollageData) Utils.collageData.get(i44)).getWidth());
                                    sb24.append("x");
                                    sb24.append(((CollageData) Utils.collageData.get(i44)).getHeight());
                                    str39 = sb24.toString();
                                } catch (Exception e26) {
                                    try {
                                        str37 = str4;
                                        StringBuilder sb25 = new StringBuilder(String.valueOf(str38));
                                        sb25.append(str39);
                                        sb25.append(strArr2[i44]);
                                        str38 = sb25.toString();
                                    } catch (Exception e27) {
                                        str37 = str4;
                                    }
                                }
                            } catch (Exception e28) {
                                str4 = str37;
                                str37 = str4;
                                StringBuilder sb252 = new StringBuilder(String.valueOf(str38));
                                sb252.append(str39);
                                sb252.append(strArr2[i44]);
                                str38 = sb252.toString();
                            }
                            str37 = str4;
                        } else {
                            try {
                                arrayList.add("-ss");
                                arrayList.add(((CollageData) Utils.collageData.get(i44)).getStartTime());
                                arrayList.add("-t");
                                arrayList.add(String.valueOf(i34));
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i44)).getVideoUrl());
                                StringBuilder sb26 = new StringBuilder();
                                sb26.append(" [");
                                sb26.append(i44);
                                sb26.append(":v] setpts=PTS-STARTPTS,crop=w=");
                                sb26.append(((CollageData) Utils.collageData.get(i44)).getCrop_width());
                                sb26.append(":h=");
                                sb26.append(((CollageData) Utils.collageData.get(i44)).getCrop_height());
                                sb26.append(":x=");
                                sb26.append(((CollageData) Utils.collageData.get(i44)).getCrop_X());
                                sb26.append(":y=");
                                sb26.append(((CollageData) Utils.collageData.get(i44)).getCrop_Y());
                                sb26.append(", scale=");
                                sb26.append(((CollageData) Utils.collageData.get(i44)).getWidth());
                                sb26.append("x");
                                sb26.append(((CollageData) Utils.collageData.get(i44)).getHeight());
                                str39 = sb26.toString();
                            } catch (Exception e29) {
                                e29.printStackTrace();
                            }
                        }
                        StringBuilder sb2522 = new StringBuilder(String.valueOf(str38));
                        sb2522.append(str39);
                        sb2522.append(strArr2[i44]);
                        str38 = sb2522.toString();
                    } catch (Exception e30) {
                        str4 = str37;
                        str37 = str4;
                    }
                }
                if (c) {
                    try {
                        arrayList.add("-ss");
                        arrayList.add(String.valueOf(this.Rs / 1000));
                        arrayList.add("-i");
                        arrayList.add(d);
                        i2 = 3;
                    } catch (Exception e31) {
                        e31.printStackTrace();
                    }
                    StringBuilder sb27 = new StringBuilder();
                    sb27.append("nullsrc=size=");
                    sb27.append(sb2);
                    sb27.append(" [base];");
                    sb27.append(str37);
                    sb27.append(str38);
                    sb27.append(" [base][upperleft] overlay=shortest=1 [tmp1]; [tmp1][upperright] overlay=shortest=1:y=");
                    sb27.append(((CollageData) Utils.collageData.get(1)).getYPoint());
                    String sb28 = sb27.toString();
                    for (i3 = 0; i3 < this.S.size(); i3++) {
                        try {
                            StickerObj stickerObj3 = (StickerObj) this.S.get(i3);
                            arrayList.add("-i");
                            arrayList.add(stickerObj3.StickerPath);
                        } catch (Exception e32) {
                            e32.printStackTrace();
                        }
                    }
                    String str40 = str36;
                    int i46 = i2;
                    for (i4 = 0; i4 < this.S.size(); i4++) {
                        try {
                            StickerObj stickerObj4 = (StickerObj) this.S.get(i4);
                            StringBuilder sb29 = new StringBuilder(String.valueOf(str40));
                            sb29.append("[");
                            sb29.append(str26);
                            sb29.append("];");
                            sb29.append("[");
                            sb29.append(str26);
                            sb29.append("]");
                            sb29.append("[");
                            sb29.append(i46);
                            sb29.append(":v] overlay=x=");
                            sb29.append(stickerObj4.StickerX);
                            sb29.append(":y=");
                            sb29.append(stickerObj4.StickerY);
                            str3 = sb29.toString();
                            try {
                                StringBuilder sb30 = new StringBuilder(String.valueOf(str26));
                                sb30.append(i4);
                                i46++;
                                str26 = sb30.toString();
                            } catch (Exception e33) {
                                str40 = str3;
                            }
                        } catch (Exception e34) {
                            str3 = str40;
                            str40 = str3;
                        }
                        str40 = str3;
                    }
                    arrayList.add("-filter_complex");
                    StringBuilder sb31 = new StringBuilder();
                    sb31.append(sb28);
                    sb31.append(str40);
                    arrayList.add(sb31.toString());
                    if (!c) {
                        try {
                            arrayList.add("-map");
                            arrayList.add("2:a");
                        } catch (Exception e35) {
                            e35.printStackTrace();
                        }
                    } else {
                        int i47 = 0;
                        while (i47 < this.i.size()) {
                            try {
                                try {
                                    AudioTrackData audioTrackData2 = (AudioTrackData) this.i.get(i47);
                                    if (audioTrackData2.isSelected) {
                                        try {
                                            arrayList.add("-map");
                                            StringBuilder sb32 = new StringBuilder();
                                            sb32.append(audioTrackData2.mapPosition);
                                            sb32.append(":a");
                                            arrayList.add(sb32.toString());
                                        } catch (Exception e36) {
                                            e36.printStackTrace();
                                        }
                                    } else {
                                        i47++;
                                    }
                                } catch (Exception e37) {
                                    e37.printStackTrace();
                                }
                            } catch (Exception e38) {
                                e38.printStackTrace();
                            }
                        }
                    }
                    arrayList.add("-c:a");
                    arrayList.add("copy");
                    arrayList.add("-ab");
                    arrayList.add("128k");
                    arrayList.add("-vcodec");
                    arrayList.add("mpeg4");
                    arrayList.add("-r");
                    arrayList.add("15");
                    arrayList.add("-b:v");
                    arrayList.add("2500k");
                    arrayList.add("-sample_fmt");
                    arrayList.add("s16");
                    arrayList.add("-strict");
                    arrayList.add("experimental");
                    arrayList.add("-ss");
                    arrayList.add("0");
                    arrayList.add("-t");
                    arrayList.add(String.valueOf(i34));
                    arrayList.add(this.W);
                    a(arrayList, this.W);
                    break;
                }
                i2 = 2;
                StringBuilder sb272 = new StringBuilder();
                sb272.append("nullsrc=size=");
                sb272.append(sb2);
                sb272.append(" [base];");
                sb272.append(str37);
                sb272.append(str38);
                sb272.append(" [base][upperleft] overlay=shortest=1 [tmp1]; [tmp1][upperright] overlay=shortest=1:y=");
                sb272.append(((CollageData) Utils.collageData.get(1)).getYPoint());
                String sb282 = sb272.toString();
                String str402 = str36;
                int i462 = i2;
                arrayList.add("-filter_complex");
                StringBuilder sb312 = new StringBuilder();
                sb312.append(sb282);
                sb312.append(str402);
                arrayList.add(sb312.toString());
                if (!c) {
                }
                arrayList.add("-c:a");
                arrayList.add("copy");
                arrayList.add("-ab");
                arrayList.add("128k");
                arrayList.add("-vcodec");
                arrayList.add("mpeg4");
                arrayList.add("-r");
                arrayList.add("15");
                arrayList.add("-b:v");
                arrayList.add("2500k");
                arrayList.add("-sample_fmt");
                arrayList.add("s16");
                arrayList.add("-strict");
                arrayList.add("experimental");
                arrayList.add("-ss");
                arrayList.add("0");
                arrayList.add("-t");
                arrayList.add(String.valueOf(i34));
                arrayList.add(this.W);
                a(arrayList, this.W);
            case 3:
                String str41 = str23;
                String str42 = str24;
                String str43 = str25;
                StringBuilder sb33 = new StringBuilder();
                sb33.append(", drawbox=x=0:y=0:width=");
                sb33.append(((CollageData) Utils.collageData.get(0)).getWidth());
                sb33.append(":height=");
                int i48 = strokeWidth / 2;
                sb33.append(((CollageData) Utils.collageData.get(0)).getHeight() + i48);
                sb33.append(":thickness=");
                sb33.append(strokeWidth);
                sb33.append(":color=");
                sb33.append(format);
                sb33.append("@");
                sb33.append(valueOf);
                sb33.append(" [upper];");
                StringBuilder sb34 = new StringBuilder();
                sb34.append(", drawbox=x=0:y=");
                int i49 = -i48;
                sb34.append(i49);
                sb34.append(":width=");
                sb34.append(((CollageData) Utils.collageData.get(1)).getWidth());
                sb34.append(":height=");
                sb34.append(((CollageData) Utils.collageData.get(1)).getHeight() + strokeWidth);
                sb34.append(":thickness=");
                sb34.append(strokeWidth);
                sb34.append(":color=");
                sb34.append(format);
                sb34.append("@");
                sb34.append(valueOf);
                sb34.append(" [middle];");
                StringBuilder sb35 = new StringBuilder();
                sb35.append(", drawbox=x=0:y=");
                sb35.append(i49);
                sb35.append(":width=");
                sb35.append(((CollageData) Utils.collageData.get(2)).getWidth());
                sb35.append(":height=");
                sb35.append(((CollageData) Utils.collageData.get(2)).getHeight() + i48);
                sb35.append(":thickness=");
                sb35.append(strokeWidth);
                sb35.append(":color=");
                sb35.append(format);
                sb35.append("@");
                sb35.append(valueOf);
                sb35.append(" [bottom];");
                String[] strArr3 = {sb33.toString(), sb34.toString(), sb35.toString()};
                arrayList.add("-y");
                String str44 = str41;
                String str45 = str42;
                for (int i50 = 0; i50 < size; i50++) {
                    String str46 = "";
                    try {
                        if (((CollageData) Utils.collageData.get(i50)).getIsImage().booleanValue()) {
                            try {
                                arrayList.add("-loop");
                                arrayList.add("1");
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i50)).getVideoUrl());
                                StringBuilder sb36 = new StringBuilder(String.valueOf(str44));
                                sb36.append(" [");
                                sb36.append(i50);
                                sb36.append(":v] trim=duration=");
                                sb36.append(i34);
                                sb36.append(" [v");
                                int i51 = i50 + 1;
                                sb36.append(i51);
                                sb36.append("];");
                                str6 = sb36.toString();
                                try {
                                    StringBuilder sb37 = new StringBuilder();
                                    sb37.append(" [v");
                                    sb37.append(i51);
                                    sb37.append("] setpts=PTS-STARTPTS, scale=");
                                    sb37.append(((CollageData) Utils.collageData.get(i50)).getWidth());
                                    sb37.append("x");
                                    sb37.append(((CollageData) Utils.collageData.get(i50)).getHeight());
                                    str46 = sb37.toString();
                                } catch (Exception e39) {
                                    try {
                                        str44 = str6;
                                        StringBuilder sb38 = new StringBuilder(String.valueOf(str45));
                                        sb38.append(str46);
                                        sb38.append(strArr3[i50]);
                                        str45 = sb38.toString();
                                    } catch (Exception e40) {
                                        str44 = str6;
                                    }
                                }
                            } catch (Exception e41) {
                                str6 = str44;
                                str44 = str6;
                                StringBuilder sb382 = new StringBuilder(String.valueOf(str45));
                                sb382.append(str46);
                                sb382.append(strArr3[i50]);
                                str45 = sb382.toString();
                            }
                            str44 = str6;
                        } else {
                            try {
                                arrayList.add("-ss");
                                arrayList.add(((CollageData) Utils.collageData.get(i50)).getStartTime());
                                arrayList.add("-t");
                                arrayList.add(String.valueOf(i34));
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i50)).getVideoUrl());
                                StringBuilder sb39 = new StringBuilder();
                                sb39.append(" [");
                                sb39.append(i50);
                                sb39.append(":v] setpts=PTS-STARTPTS, crop=w=");
                                sb39.append(((CollageData) Utils.collageData.get(i50)).getCrop_width());
                                sb39.append(":h=");
                                sb39.append(((CollageData) Utils.collageData.get(i50)).getCrop_height());
                                sb39.append(":x=");
                                sb39.append(((CollageData) Utils.collageData.get(i50)).getCrop_X());
                                sb39.append(":y=");
                                sb39.append(((CollageData) Utils.collageData.get(i50)).getCrop_Y());
                                sb39.append(",scale=");
                                sb39.append(((CollageData) Utils.collageData.get(i50)).getWidth());
                                sb39.append("x");
                                sb39.append(((CollageData) Utils.collageData.get(i50)).getHeight());
                                str46 = sb39.toString();
                            } catch (Exception e42) {
                                e42.printStackTrace();
                            }
                        }
                        StringBuilder sb3822 = new StringBuilder(String.valueOf(str45));
                        sb3822.append(str46);
                        sb3822.append(strArr3[i50]);
                        str45 = sb3822.toString();
                    } catch (Exception e43) {
                        str6 = str44;
                        str44 = str6;
                    }
                }
                if (c) {
                    try {
                        arrayList.add("-ss");
                        arrayList.add(String.valueOf(this.Rs / 1000));
                        arrayList.add("-i");
                        arrayList.add(d);
                        i5 = 4;
                    } catch (Exception e44) {
                        e44.printStackTrace();
                    }
                    StringBuilder sb40 = new StringBuilder();
                    sb40.append("nullsrc=size=");
                    sb40.append(sb2);
                    sb40.append(" [base];");
                    sb40.append(str44);
                    sb40.append(str45);
                    sb40.append(" [base][upper] overlay=shortest=1 [tmp1]; [tmp1][middle] overlay=shortest=1:y=");
                    sb40.append(((CollageData) Utils.collageData.get(1)).getYPoint());
                    sb40.append(" [tmp2]; [tmp2][bottom] overlay=shortest=1:y=");
                    sb40.append(((CollageData) Utils.collageData.get(2)).getYPoint());
                    String sb41 = sb40.toString();
                    for (i6 = 0; i6 < this.S.size(); i6++) {
                        try {
                            StickerObj stickerObj5 = (StickerObj) this.S.get(i6);
                            arrayList.add("-i");
                            arrayList.add(stickerObj5.StickerPath);
                        } catch (Exception e45) {
                            e45.printStackTrace();
                        }
                    }
                    String str47 = str43;
                    int i52 = i5;
                    for (i7 = 0; i7 < this.S.size(); i7++) {
                        try {
                            StickerObj stickerObj6 = (StickerObj) this.S.get(i7);
                            StringBuilder sb42 = new StringBuilder(String.valueOf(str47));
                            sb42.append("[");
                            sb42.append(str26);
                            sb42.append("];");
                            sb42.append("[");
                            sb42.append(str26);
                            sb42.append("]");
                            sb42.append("[");
                            sb42.append(i52);
                            sb42.append(":v] overlay=x=");
                            sb42.append(stickerObj6.StickerX);
                            sb42.append(":y=");
                            sb42.append(stickerObj6.StickerY);
                            str5 = sb42.toString();
                            try {
                                StringBuilder sb43 = new StringBuilder(String.valueOf(str26));
                                sb43.append(i7);
                                i52++;
                                str26 = sb43.toString();
                            } catch (Exception e46) {
                                str47 = str5;
                            }
                        } catch (Exception e47) {
                            str5 = str47;
                            str47 = str5;
                        }
                        str47 = str5;
                    }
                    arrayList.add("-filter_complex");
                    StringBuilder sb44 = new StringBuilder();
                    sb44.append(sb41);
                    sb44.append(str47);
                    arrayList.add(sb44.toString());
                    if (!c) {
                        try {
                            arrayList.add("-map");
                            arrayList.add("3:a");
                        } catch (Exception e48) {
                            e48.printStackTrace();
                        }
                    } else {
                        int i53 = 0;
                        while (i53 < this.i.size()) {
                            try {
                                try {
                                    AudioTrackData audioTrackData3 = (AudioTrackData) this.i.get(i53);
                                    if (audioTrackData3.isSelected) {
                                        try {
                                            arrayList.add("-map");
                                            StringBuilder sb45 = new StringBuilder();
                                            sb45.append(audioTrackData3.mapPosition);
                                            sb45.append(":a");
                                            arrayList.add(sb45.toString());
                                        } catch (Exception e49) {
                                            e49.printStackTrace();
                                        }
                                    } else {
                                        i53++;
                                    }
                                } catch (Exception e50) {
                                    e50.printStackTrace();
                                }
                            } catch (Exception e51) {
                                e51.printStackTrace();
                            }
                        }
                    }
                    arrayList.add("-c:a");
                    arrayList.add("copy");
                    arrayList.add("-ab");
                    arrayList.add("128k");
                    arrayList.add("-c:v");
                    arrayList.add("mpeg4");
                    arrayList.add("-r");
                    arrayList.add("15");
                    arrayList.add("-b:v");
                    arrayList.add("2500k");
                    arrayList.add("-sample_fmt");
                    arrayList.add("s16");
                    arrayList.add("-strict");
                    arrayList.add("experimental");
                    arrayList.add("-ss");
                    arrayList.add("0");
                    arrayList.add("-t");
                    arrayList.add(String.valueOf(i34));
                    arrayList.add(this.W);
                    a(arrayList, this.W);
                    break;
                }
                i5 = 3;
                StringBuilder sb402 = new StringBuilder();
                sb402.append("nullsrc=size=");
                sb402.append(sb2);
                sb402.append(" [base];");
                sb402.append(str44);
                sb402.append(str45);
                sb402.append(" [base][upper] overlay=shortest=1 [tmp1]; [tmp1][middle] overlay=shortest=1:y=");
                sb402.append(((CollageData) Utils.collageData.get(1)).getYPoint());
                sb402.append(" [tmp2]; [tmp2][bottom] overlay=shortest=1:y=");
                sb402.append(((CollageData) Utils.collageData.get(2)).getYPoint());
                String sb412 = sb402.toString();
                String str472 = str43;
                int i522 = i5;
                arrayList.add("-filter_complex");
                StringBuilder sb442 = new StringBuilder();
                sb442.append(sb412);
                sb442.append(str472);
                arrayList.add(sb442.toString());
                if (!c) {
                }
                arrayList.add("-c:a");
                arrayList.add("copy");
                arrayList.add("-ab");
                arrayList.add("128k");
                arrayList.add("-c:v");
                arrayList.add("mpeg4");
                arrayList.add("-r");
                arrayList.add("15");
                arrayList.add("-b:v");
                arrayList.add("2500k");
                arrayList.add("-sample_fmt");
                arrayList.add("s16");
                arrayList.add("-strict");
                arrayList.add("experimental");
                arrayList.add("-ss");
                arrayList.add("0");
                arrayList.add("-t");
                arrayList.add(String.valueOf(i34));
                arrayList.add(this.W);
                a(arrayList, this.W);
            case 4:
                String str48 = str23;
                String str49 = str24;
                String str50 = str25;
                StringBuilder sb46 = new StringBuilder();
                sb46.append(", drawbox=x=0:y=0:width=");
                int i54 = strokeWidth / 2;
                sb46.append(((CollageData) Utils.collageData.get(0)).getWidth() + i54);
                sb46.append(":height=");
                sb46.append(((CollageData) Utils.collageData.get(0)).getHeight());
                sb46.append(":thickness=");
                sb46.append(strokeWidth);
                sb46.append(":color=");
                sb46.append(format);
                sb46.append("@");
                sb46.append(valueOf);
                sb46.append(" [left];");
                StringBuilder sb47 = new StringBuilder();
                sb47.append(", drawbox=x=");
                int i55 = -i54;
                sb47.append(i55);
                sb47.append(":y=0:width=");
                sb47.append(((CollageData) Utils.collageData.get(1)).getWidth() + strokeWidth);
                sb47.append(":height=");
                sb47.append(((CollageData) Utils.collageData.get(1)).getHeight());
                sb47.append(":thickness=");
                sb47.append(strokeWidth);
                sb47.append(":color=");
                sb47.append(format);
                sb47.append("@");
                sb47.append(valueOf);
                sb47.append(" [middle];");
                StringBuilder sb48 = new StringBuilder();
                sb48.append(", drawbox=x=");
                sb48.append(i55);
                sb48.append(":y=0:width=");
                sb48.append(((CollageData) Utils.collageData.get(2)).getWidth() + i54);
                sb48.append(":height=");
                sb48.append(((CollageData) Utils.collageData.get(2)).getHeight());
                sb48.append(":thickness=");
                sb48.append(strokeWidth);
                sb48.append(":color=");
                sb48.append(format);
                sb48.append("@");
                sb48.append(valueOf);
                sb48.append(" [right];");
                String[] strArr4 = {sb46.toString(), sb47.toString(), sb48.toString()};
                arrayList.add("-y");
                String str51 = str48;
                String str52 = str49;
                for (int i56 = 0; i56 < size; i56++) {
                    String str53 = "";
                    try {
                        if (((CollageData) Utils.collageData.get(i56)).getIsImage().booleanValue()) {
                            try {
                                arrayList.add("-loop");
                                arrayList.add("1");
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i56)).getVideoUrl());
                                StringBuilder sb49 = new StringBuilder(String.valueOf(str51));
                                sb49.append(" [");
                                sb49.append(i56);
                                sb49.append(":v] trim=duration=");
                                sb49.append(i34);
                                sb49.append(" [v");
                                int i57 = i56 + 1;
                                sb49.append(i57);
                                sb49.append("];");
                                str8 = sb49.toString();
                                try {
                                    StringBuilder sb50 = new StringBuilder();
                                    sb50.append(" [v");
                                    sb50.append(i57);
                                    sb50.append("] setpts=PTS-STARTPTS, scale=");
                                    sb50.append(((CollageData) Utils.collageData.get(i56)).getWidth());
                                    sb50.append("x");
                                    sb50.append(((CollageData) Utils.collageData.get(i56)).getHeight());
                                    str53 = sb50.toString();
                                } catch (Exception e52) {
                                    try {
                                        str51 = str8;
                                        StringBuilder sb51 = new StringBuilder(String.valueOf(str52));
                                        sb51.append(str53);
                                        sb51.append(strArr4[i56]);
                                        str52 = sb51.toString();
                                    } catch (Exception e53) {
                                        str51 = str8;
                                    }
                                }
                            } catch (Exception e54) {
                                str8 = str51;
                                str51 = str8;
                                StringBuilder sb512 = new StringBuilder(String.valueOf(str52));
                                sb512.append(str53);
                                sb512.append(strArr4[i56]);
                                str52 = sb512.toString();
                            }
                            str51 = str8;
                        } else {
                            try {
                                arrayList.add("-ss");
                                arrayList.add(((CollageData) Utils.collageData.get(i56)).getStartTime());
                                arrayList.add("-t");
                                arrayList.add(String.valueOf(i34));
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i56)).getVideoUrl());
                                StringBuilder sb52 = new StringBuilder();
                                sb52.append(" [");
                                sb52.append(i56);
                                sb52.append(":v] setpts=PTS-STARTPTS, crop=w=");
                                sb52.append(((CollageData) Utils.collageData.get(i56)).getCrop_width());
                                sb52.append(":h=");
                                sb52.append(((CollageData) Utils.collageData.get(i56)).getCrop_height());
                                sb52.append(":x=");
                                sb52.append(((CollageData) Utils.collageData.get(i56)).getCrop_X());
                                sb52.append(":y=");
                                sb52.append(((CollageData) Utils.collageData.get(i56)).getCrop_Y());
                                sb52.append(",scale=");
                                sb52.append(((CollageData) Utils.collageData.get(i56)).getWidth());
                                sb52.append("x");
                                sb52.append(((CollageData) Utils.collageData.get(i56)).getHeight());
                                str53 = sb52.toString();
                            } catch (Exception e55) {
                                e55.printStackTrace();
                            }
                        }
                        StringBuilder sb5122 = new StringBuilder(String.valueOf(str52));
                        sb5122.append(str53);
                        sb5122.append(strArr4[i56]);
                        str52 = sb5122.toString();
                    } catch (Exception e56) {
                        str8 = str51;
                        str51 = str8;
                    }
                }
                if (c) {
                    try {
                        arrayList.add("-ss");
                        arrayList.add(String.valueOf(this.Rs / 1000));
                        arrayList.add("-i");
                        arrayList.add(d);
                        i8 = 4;
                    } catch (Exception e57) {
                        e57.printStackTrace();
                    }
                    StringBuilder sb53 = new StringBuilder();
                    sb53.append("nullsrc=size=");
                    sb53.append(sb2);
                    sb53.append(" [base];");
                    sb53.append(str51);
                    sb53.append(str52);
                    sb53.append(" [base][left] overlay=shortest=1 [tmp1]; [tmp1][middle] overlay=shortest=1:x=");
                    sb53.append(((CollageData) Utils.collageData.get(1)).getXPoint());
                    sb53.append(" [tmp2]; [tmp2][right] overlay=shortest=1:x=");
                    sb53.append(((CollageData) Utils.collageData.get(2)).getXPoint());
                    String sb54 = sb53.toString();
                    for (i9 = 0; i9 < this.S.size(); i9++) {
                        try {
                            StickerObj stickerObj7 = (StickerObj) this.S.get(i9);
                            arrayList.add("-i");
                            arrayList.add(stickerObj7.StickerPath);
                        } catch (Exception e58) {
                            e58.printStackTrace();
                        }
                    }
                    int i58 = i8;
                    String str54 = str50;
                    for (i10 = 0; i10 < this.S.size(); i10++) {
                        try {
                            StickerObj stickerObj8 = (StickerObj) this.S.get(i10);
                            StringBuilder sb55 = new StringBuilder(String.valueOf(str54));
                            sb55.append("[");
                            sb55.append(str26);
                            sb55.append("];");
                            sb55.append("[");
                            sb55.append(str26);
                            sb55.append("]");
                            sb55.append("[");
                            sb55.append(i58);
                            sb55.append(":v] overlay=x=");
                            sb55.append(stickerObj8.StickerX);
                            sb55.append(":y=");
                            sb55.append(stickerObj8.StickerY);
                            str7 = sb55.toString();
                            try {
                                StringBuilder sb56 = new StringBuilder(String.valueOf(str26));
                                sb56.append(i10);
                                i58++;
                                str26 = sb56.toString();
                            } catch (Exception e59) {
                                str54 = str7;
                            }
                        } catch (Exception e60) {
                            str7 = str54;
                            str54 = str7;
                        }
                        str54 = str7;
                    }
                    arrayList.add("-filter_complex");
                    StringBuilder sb57 = new StringBuilder();
                    sb57.append(sb54);
                    sb57.append(str54);
                    arrayList.add(sb57.toString());
                    if (!c) {
                        try {
                            arrayList.add("-map");
                            arrayList.add("3:a");
                        } catch (Exception e61) {
                            e61.printStackTrace();
                        }
                    } else {
                        int i59 = 0;
                        while (i59 < this.i.size()) {
                            try {
                                try {
                                    AudioTrackData audioTrackData4 = (AudioTrackData) this.i.get(i59);
                                    if (audioTrackData4.isSelected) {
                                        try {
                                            arrayList.add("-map");
                                            StringBuilder sb58 = new StringBuilder();
                                            sb58.append(audioTrackData4.mapPosition);
                                            sb58.append(":a");
                                            arrayList.add(sb58.toString());
                                        } catch (Exception e62) {
                                            e62.printStackTrace();
                                        }
                                    } else {
                                        i59++;
                                    }
                                } catch (Exception e63) {
                                    e63.printStackTrace();
                                }
                            } catch (Exception e64) {
                                e64.printStackTrace();
                            }
                        }
                    }
                    arrayList.add("-c:a");
                    arrayList.add("copy");
                    arrayList.add("-ab");
                    arrayList.add("128k");
                    arrayList.add("-c:v");
                    arrayList.add("mpeg4");
                    arrayList.add("-r");
                    arrayList.add("15");
                    arrayList.add("-b:v");
                    arrayList.add("2500k");
                    arrayList.add("-sample_fmt");
                    arrayList.add("s16");
                    arrayList.add("-strict");
                    arrayList.add("experimental");
                    arrayList.add("-ss");
                    arrayList.add("0");
                    arrayList.add("-t");
                    arrayList.add(String.valueOf(i34));
                    arrayList.add(this.W);
                    a(arrayList, this.W);
                    break;
                }
                i8 = 3;
                StringBuilder sb532 = new StringBuilder();
                sb532.append("nullsrc=size=");
                sb532.append(sb2);
                sb532.append(" [base];");
                sb532.append(str51);
                sb532.append(str52);
                sb532.append(" [base][left] overlay=shortest=1 [tmp1]; [tmp1][middle] overlay=shortest=1:x=");
                sb532.append(((CollageData) Utils.collageData.get(1)).getXPoint());
                sb532.append(" [tmp2]; [tmp2][right] overlay=shortest=1:x=");
                sb532.append(((CollageData) Utils.collageData.get(2)).getXPoint());
                String sb542 = sb532.toString();
                int i582 = i8;
                String str542 = str50;
                arrayList.add("-filter_complex");
                StringBuilder sb572 = new StringBuilder();
                sb572.append(sb542);
                sb572.append(str542);
                arrayList.add(sb572.toString());
                if (!c) {
                }
                arrayList.add("-c:a");
                arrayList.add("copy");
                arrayList.add("-ab");
                arrayList.add("128k");
                arrayList.add("-c:v");
                arrayList.add("mpeg4");
                arrayList.add("-r");
                arrayList.add("15");
                arrayList.add("-b:v");
                arrayList.add("2500k");
                arrayList.add("-sample_fmt");
                arrayList.add("s16");
                arrayList.add("-strict");
                arrayList.add("experimental");
                arrayList.add("-ss");
                arrayList.add("0");
                arrayList.add("-t");
                arrayList.add(String.valueOf(i34));
                arrayList.add(this.W);
                a(arrayList, this.W);
            case 5:
                String str55 = str23;
                String str56 = str24;
                String str57 = str25;
                StringBuilder sb59 = new StringBuilder();
                sb59.append(", drawbox=x=0:y=0:width=");
                int i60 = strokeWidth / 2;
                sb59.append(((CollageData) Utils.collageData.get(0)).getWidth() + i60);
                sb59.append(":height=");
                sb59.append(((CollageData) Utils.collageData.get(0)).getHeight() + i60);
                sb59.append(":thickness=");
                sb59.append(strokeWidth);
                sb59.append(":color=");
                sb59.append(format);
                sb59.append("@");
                sb59.append(valueOf);
                sb59.append(" [left];");
                StringBuilder sb60 = new StringBuilder();
                sb60.append(", drawbox=x=");
                int i61 = -i60;
                sb60.append(i61);
                sb60.append(":y=0:width=");
                sb60.append(((CollageData) Utils.collageData.get(1)).getWidth() + i60);
                sb60.append(":height=");
                sb60.append(((CollageData) Utils.collageData.get(1)).getHeight() + i60);
                sb60.append(":thickness=");
                sb60.append(strokeWidth);
                sb60.append(":color=");
                sb60.append(format);
                sb60.append("@");
                sb60.append(valueOf);
                sb60.append(" [middle];");
                StringBuilder sb61 = new StringBuilder();
                sb61.append(", drawbox=x=0:y=");
                sb61.append(i61);
                sb61.append(":width=");
                sb61.append(((CollageData) Utils.collageData.get(2)).getWidth());
                sb61.append(":height=");
                sb61.append(((CollageData) Utils.collageData.get(2)).getHeight() + i60);
                sb61.append(":thickness=");
                sb61.append(strokeWidth);
                sb61.append(":color=");
                sb61.append(format);
                sb61.append("@");
                sb61.append(valueOf);
                sb61.append(" [right];");
                String[] strArr5 = {sb59.toString(), sb60.toString(), sb61.toString()};
                arrayList.add("-y");
                String str58 = str55;
                String str59 = str56;
                for (int i62 = 0; i62 < size; i62++) {
                    String str60 = "";
                    try {
                        if (((CollageData) Utils.collageData.get(i62)).getIsImage().booleanValue()) {
                            try {
                                arrayList.add("-loop");
                                arrayList.add("1");
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i62)).getVideoUrl());
                                StringBuilder sb62 = new StringBuilder(String.valueOf(str58));
                                sb62.append(" [");
                                sb62.append(i62);
                                sb62.append(":v] trim=duration=");
                                sb62.append(i34);
                                sb62.append(" [v");
                                int i63 = i62 + 1;
                                sb62.append(i63);
                                sb62.append("];");
                                str10 = sb62.toString();
                                try {
                                    StringBuilder sb63 = new StringBuilder();
                                    sb63.append(" [v");
                                    sb63.append(i63);
                                    sb63.append("] setpts=PTS-STARTPTS,scale=");
                                    sb63.append(((CollageData) Utils.collageData.get(i62)).getWidth());
                                    sb63.append("x");
                                    sb63.append(((CollageData) Utils.collageData.get(i62)).getHeight());
                                    str60 = sb63.toString();
                                } catch (Exception e65) {
                                    try {
                                        str58 = str10;
                                        StringBuilder sb64 = new StringBuilder(String.valueOf(str59));
                                        sb64.append(str60);
                                        sb64.append(strArr5[i62]);
                                        str59 = sb64.toString();
                                    } catch (Exception e66) {
                                        str58 = str10;
                                    }
                                }
                            } catch (Exception e67) {
                                str10 = str58;
                                str58 = str10;
                                StringBuilder sb642 = new StringBuilder(String.valueOf(str59));
                                sb642.append(str60);
                                sb642.append(strArr5[i62]);
                                str59 = sb642.toString();
                            }
                            str58 = str10;
                        } else {
                            try {
                                arrayList.add("-ss");
                                arrayList.add(((CollageData) Utils.collageData.get(i62)).getStartTime());
                                arrayList.add("-t");
                                arrayList.add(String.valueOf(i34));
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i62)).getVideoUrl());
                                StringBuilder sb65 = new StringBuilder();
                                sb65.append(" [");
                                sb65.append(i62);
                                sb65.append(":v] setpts=PTS-STARTPTS,crop=w=");
                                sb65.append(((CollageData) Utils.collageData.get(i62)).getCrop_width());
                                sb65.append(":h=");
                                sb65.append(((CollageData) Utils.collageData.get(i62)).getCrop_height());
                                sb65.append(":x=");
                                sb65.append(((CollageData) Utils.collageData.get(i62)).getCrop_X());
                                sb65.append(":y=");
                                sb65.append(((CollageData) Utils.collageData.get(i62)).getCrop_Y());
                                sb65.append(", scale=");
                                sb65.append(((CollageData) Utils.collageData.get(i62)).getWidth());
                                sb65.append("x");
                                sb65.append(((CollageData) Utils.collageData.get(i62)).getHeight());
                                str60 = sb65.toString();
                            } catch (Exception e68) {
                                e68.printStackTrace();
                            }
                        }
                        StringBuilder sb6422 = new StringBuilder(String.valueOf(str59));
                        sb6422.append(str60);
                        sb6422.append(strArr5[i62]);
                        str59 = sb6422.toString();
                    } catch (Exception e69) {
                        str10 = str58;
                        str58 = str10;
                    }
                }
                if (c) {
                    try {
                        arrayList.add("-ss");
                        arrayList.add(String.valueOf(this.Rs / 1000));
                        arrayList.add("-i");
                        arrayList.add(d);
                        i11 = 4;
                    } catch (Exception e70) {
                        e70.printStackTrace();
                    }
                    StringBuilder sb66 = new StringBuilder();
                    sb66.append("nullsrc=size=");
                    sb66.append(sb2);
                    sb66.append(" [base];");
                    sb66.append(str58);
                    sb66.append(str59);
                    sb66.append(" [base][left] overlay=shortest=1 [tmp1]; [tmp1][middle] overlay=shortest=1:x=");
                    sb66.append(((CollageData) Utils.collageData.get(1)).getXPoint());
                    sb66.append(" [tmp2]; [tmp2][right] overlay=shortest=1:y=");
                    sb66.append(((CollageData) Utils.collageData.get(2)).getYPoint());
                    String sb67 = sb66.toString();
                    for (i12 = 0; i12 < this.S.size(); i12++) {
                        try {
                            StickerObj stickerObj9 = (StickerObj) this.S.get(i12);
                            arrayList.add("-i");
                            arrayList.add(stickerObj9.StickerPath);
                        } catch (Exception e71) {
                            e71.printStackTrace();
                        }
                    }
                    int i64 =
                            i11;
                    String str61 = str57;
                    for (i13 = 0; i13 < this.S.size(); i13++) {
                        try {
                            StickerObj stickerObj10 = (StickerObj) this.S.get(i13);
                            StringBuilder sb68 = new StringBuilder(String.valueOf(str61));
                            sb68.append("[");
                            sb68.append(str26);
                            sb68.append("];");
                            sb68.append("[");
                            sb68.append(str26);
                            sb68.append("]");
                            sb68.append("[");
                            sb68.append(i64);
                            sb68.append(":v] overlay=x=");
                            sb68.append(stickerObj10.StickerX);
                            sb68.append(":y=");
                            sb68.append(stickerObj10.StickerY);
                            str9 = sb68.toString();
                            try {
                                StringBuilder sb69 = new StringBuilder(String.valueOf(str26));
                                sb69.append(i13);
                                i64++;
                                str26 = sb69.toString();
                            } catch (Exception e72) {
                                str61 = str9;
                            }
                        } catch (Exception e73) {
                            str9 = str61;
                            str61 = str9;
                        }
                        str61 = str9;
                    }
                    arrayList.add("-filter_complex");
                    StringBuilder sb70 = new StringBuilder();
                    sb70.append(sb67);
                    sb70.append(str61);
                    arrayList.add(sb70.toString());
                    if (!c) {
                        try {
                            arrayList.add("-map");
                            arrayList.add("3:a");
                        } catch (Exception e74) {
                            e74.printStackTrace();
                        }
                    } else {
                        int i65 = 0;
                        while (i65 < this.i.size()) {
                            try {
                                try {
                                    AudioTrackData audioTrackData5 = (AudioTrackData) this.i.get(i65);
                                    if (audioTrackData5.isSelected) {
                                        try {
                                            arrayList.add("-map");
                                            StringBuilder sb71 = new StringBuilder();
                                            sb71.append(audioTrackData5.mapPosition);
                                            sb71.append(":a");
                                            arrayList.add(sb71.toString());
                                        } catch (Exception e75) {
                                            e75.printStackTrace();
                                        }
                                    } else {
                                        i65++;
                                    }
                                } catch (Exception e76) {
                                    e76.printStackTrace();
                                }
                            } catch (Exception e77) {
                                e77.printStackTrace();
                            }
                        }
                    }
                    arrayList.add("-c:a");
                    arrayList.add("copy");
                    arrayList.add("-ab");
                    arrayList.add("128k");
                    arrayList.add("-c:v");
                    arrayList.add("mpeg4");
                    arrayList.add("-r");
                    arrayList.add("15");
                    arrayList.add("-b:v");
                    arrayList.add("2500k");
                    arrayList.add("-sample_fmt");
                    arrayList.add("s16");
                    arrayList.add("-strict");
                    arrayList.add("experimental");
                    arrayList.add("-ss");
                    arrayList.add("0");
                    arrayList.add("-t");
                    arrayList.add(String.valueOf(i34));
                    arrayList.add(this.W);
                    a(arrayList, this.W);
                    break;
                }
                i11 = 3;
                StringBuilder sb662 = new StringBuilder();
                sb662.append("nullsrc=size=");
                sb662.append(sb2);
                sb662.append(" [base];");
                sb662.append(str58);
                sb662.append(str59);
                sb662.append(" [base][left] overlay=shortest=1 [tmp1]; [tmp1][middle] overlay=shortest=1:x=");
                sb662.append(((CollageData) Utils.collageData.get(1)).getXPoint());
                sb662.append(" [tmp2]; [tmp2][right] overlay=shortest=1:y=");
                sb662.append(((CollageData) Utils.collageData.get(2)).getYPoint());
                String sb672 = sb662.toString();
                int i642 = i11;
                String str612 = str57;
                arrayList.add("-filter_complex");
                StringBuilder sb702 = new StringBuilder();
                sb702.append(sb672);
                sb702.append(str612);
                arrayList.add(sb702.toString());
                if (!c) {
                }
                arrayList.add("-c:a");
                arrayList.add("copy");
                arrayList.add("-ab");
                arrayList.add("128k");
                arrayList.add("-c:v");
                arrayList.add("mpeg4");
                arrayList.add("-r");
                arrayList.add("15");
                arrayList.add("-b:v");
                arrayList.add("2500k");
                arrayList.add("-sample_fmt");
                arrayList.add("s16");
                arrayList.add("-strict");
                arrayList.add("experimental");
                arrayList.add("-ss");
                arrayList.add("0");
                arrayList.add("-t");
                arrayList.add(String.valueOf(i34));
                arrayList.add(this.W);
                a(arrayList, this.W);
            case 6:
                String str62 = str23;
                String str63 = str24;
                String str64 = str25;
                StringBuilder sb72 = new StringBuilder();
                sb72.append(", drawbox=x=0:y=0:width=");
                sb72.append(((CollageData) Utils.collageData.get(0)).getWidth());
                sb72.append(":height=");
                int i66 = strokeWidth / 2;
                sb72.append(((CollageData) Utils.collageData.get(0)).getHeight() + i66);
                sb72.append(":thickness=");
                sb72.append(strokeWidth);
                sb72.append(":color=");
                sb72.append(format);
                sb72.append("@");
                sb72.append(valueOf);
                sb72.append(" [left];");
                StringBuilder sb73 = new StringBuilder();
                sb73.append(", drawbox=x=0:y=");
                int i67 = -i66;
                sb73.append(i67);
                sb73.append(":width=");
                sb73.append(((CollageData) Utils.collageData.get(1)).getWidth() + i66);
                sb73.append(":height=");
                sb73.append(((CollageData) Utils.collageData.get(1)).getHeight() + i66);
                sb73.append(":thickness=");
                sb73.append(strokeWidth);
                sb73.append(":color=");
                sb73.append(format);
                sb73.append("@");
                sb73.append(valueOf);
                sb73.append(" [middle];");
                StringBuilder sb74 = new StringBuilder();
                sb74.append(", drawbox=x=");
                sb74.append(i67);
                sb74.append(":y=");
                sb74.append(i67);
                sb74.append(":width=");
                sb74.append(((CollageData) Utils.collageData.get(2)).getWidth() + i66);
                sb74.append(":height=");
                sb74.append(((CollageData) Utils.collageData.get(2)).getHeight() + i66);
                sb74.append(":thickness=");
                sb74.append(strokeWidth);
                sb74.append(":color=");
                sb74.append(format);
                sb74.append("@");
                sb74.append(valueOf);
                sb74.append(" [right];");
                String[] strArr6 = {sb72.toString(), sb73.toString(), sb74.toString()};
                arrayList.add("-y");
                String str65 = str62;
                String str66 = str63;
                for (int i68 = 0; i68 < size; i68++) {
                    String str67 = "";
                    try {
                        if (((CollageData) Utils.collageData.get(i68)).getIsImage().booleanValue()) {
                            try {
                                arrayList.add("-loop");
                                arrayList.add("1");
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i68)).getVideoUrl());
                                StringBuilder sb75 = new StringBuilder(String.valueOf(str65));
                                sb75.append(" [");
                                sb75.append(i68);
                                sb75.append(":v] trim=duration=");
                                sb75.append(i34);
                                sb75.append(" [v");
                                int i69 = i68 + 1;
                                sb75.append(i69);
                                sb75.append("];");
                                str12 = sb75.toString();
                                try {
                                    StringBuilder sb76 = new StringBuilder();
                                    sb76.append(" [v");
                                    sb76.append(i69);
                                    sb76.append("] setpts=PTS-STARTPTS, scale=");
                                    sb76.append(((CollageData) Utils.collageData.get(i68)).getWidth());
                                    sb76.append("x");
                                    sb76.append(((CollageData) Utils.collageData.get(i68)).getHeight());
                                    str67 = sb76.toString();
                                } catch (Exception e78) {
                                    try {
                                        str65 = str12;
                                        StringBuilder sb77 = new StringBuilder(String.valueOf(str66));
                                        sb77.append(str67);
                                        sb77.append(strArr6[i68]);
                                        str66 = sb77.toString();
                                    } catch (Exception e79) {
                                        str65 = str12;
                                    }
                                }
                            } catch (Exception e80) {
                                str12 = str65;
                                str65 = str12;
                                StringBuilder sb772 = new StringBuilder(String.valueOf(str66));
                                sb772.append(str67);
                                sb772.append(strArr6[i68]);
                                str66 = sb772.toString();
                            }
                            str65 = str12;
                        } else {
                            try {
                                arrayList.add("-ss");
                                arrayList.add(((CollageData) Utils.collageData.get(i68)).getStartTime());
                                arrayList.add("-t");
                                arrayList.add(String.valueOf(i34));
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i68)).getVideoUrl());
                                StringBuilder sb78 = new StringBuilder();
                                sb78.append(" [");
                                sb78.append(i68);
                                sb78.append(":v] setpts=PTS-STARTPTS,crop=w=");
                                sb78.append(((CollageData) Utils.collageData.get(i68)).getCrop_width());
                                sb78.append(":h=");
                                sb78.append(((CollageData) Utils.collageData.get(i68)).getCrop_height());
                                sb78.append(":x=");
                                sb78.append(((CollageData) Utils.collageData.get(i68)).getCrop_X());
                                sb78.append(":y=");
                                sb78.append(((CollageData) Utils.collageData.get(i68)).getCrop_Y());
                                sb78.append(", scale=");
                                sb78.append(((CollageData) Utils.collageData.get(i68)).getWidth());
                                sb78.append("x");
                                sb78.append(((CollageData) Utils.collageData.get(i68)).getHeight());
                                str67 = sb78.toString();
                            } catch (Exception e81) {
                                e81.printStackTrace();
                            }
                        }
                        StringBuilder sb7722 = new StringBuilder(String.valueOf(str66));
                        sb7722.append(str67);
                        sb7722.append(strArr6[i68]);
                        str66 = sb7722.toString();
                    } catch (Exception e82) {
                        str12 = str65;
                        str65 = str12;
                    }
                }
                if (c) {
                    try {
                        arrayList.add("-ss");
                        arrayList.add(String.valueOf(this.Rs / 1000));
                        arrayList.add("-i");
                        arrayList.add(d);
                        i14 = 4;
                    } catch (Exception e83) {
                        e83.printStackTrace();
                    }
                    StringBuilder sb79 = new StringBuilder();
                    sb79.append("nullsrc=size=");
                    sb79.append(sb2);
                    sb79.append(" [base];");
                    sb79.append(str65);
                    sb79.append(str66);
                    sb79.append(" [base][left] overlay=shortest=1 [tmp1]; [tmp1][middle] overlay=shortest=1:y=");
                    sb79.append(((CollageData) Utils.collageData.get(1)).getYPoint());
                    sb79.append(" [tmp2]; [tmp2][right] overlay=shortest=1:x=");
                    sb79.append(((CollageData) Utils.collageData.get(2)).getXPoint());
                    sb79.append(":y=");
                    sb79.append(((CollageData) Utils.collageData.get(2)).getYPoint());
                    String sb80 = sb79.toString();
                    for (i15 = 0; i15 < this.S.size(); i15++) {
                        try {
                            StickerObj stickerObj11 = (StickerObj) this.S.get(i15);
                            arrayList.add("-i");
                            arrayList.add(stickerObj11.StickerPath);
                        } catch (Exception e84) {
                            e84.printStackTrace();
                        }
                    }
                    int i70 = i14;
                    String str68 = str64;
                    for (i16 = 0; i16 < this.S.size(); i16++) {
                        try {
                            StickerObj stickerObj12 = (StickerObj) this.S.get(i16);
                            StringBuilder sb81 = new StringBuilder(String.valueOf(str68));
                            sb81.append("[");
                            sb81.append(str26);
                            sb81.append("];");
                            sb81.append("[");
                            sb81.append(str26);
                            sb81.append("]");
                            sb81.append("[");
                            sb81.append(i70);
                            sb81.append(":v] overlay=x=");
                            sb81.append(stickerObj12.StickerX);
                            sb81.append(":y=");
                            sb81.append(stickerObj12.StickerY);
                            str11 = sb81.toString();
                            try {
                                StringBuilder sb82 = new StringBuilder(String.valueOf(str26));
                                sb82.append(i16);
                                i70++;
                                str26 = sb82.toString();
                            } catch (Exception e85) {
                                str68 = str11;
                            }
                        } catch (Exception e86) {
                            str11 = str68;
                            str68 = str11;
                        }
                        str68 = str11;
                    }
                    arrayList.add("-filter_complex");
                    StringBuilder sb83 = new StringBuilder();
                    sb83.append(sb80);
                    sb83.append(str68);
                    arrayList.add(sb83.toString());
                    if (!c) {
                        try {
                            arrayList.add("-map");
                            arrayList.add("3:a");
                        } catch (Exception e87) {
                            e87.printStackTrace();
                        }
                    } else {
                        int i71 = 0;
                        while (i71 < this.i.size()) {
                            try {
                                try {
                                    AudioTrackData audioTrackData6 = (AudioTrackData) this.i.get(i71);
                                    if (audioTrackData6.isSelected) {
                                        try {
                                            arrayList.add("-map");
                                            StringBuilder sb84 = new StringBuilder();
                                            sb84.append(audioTrackData6.mapPosition);
                                            sb84.append(":a");
                                            arrayList.add(sb84.toString());
                                        } catch (Exception e88) {
                                            e88.printStackTrace();
                                        }
                                    } else {
                                        i71++;
                                    }
                                } catch (Exception e89) {
                                    e89.printStackTrace();
                                }
                            } catch (Exception e90) {
                                e90.printStackTrace();
                            }
                        }
                    }
                    arrayList.add("-c:a");
                    arrayList.add("copy");
                    arrayList.add("-ab");
                    arrayList.add("128k");
                    arrayList.add("-c:v");
                    arrayList.add("mpeg4");
                    arrayList.add("-r");
                    arrayList.add("15");
                    arrayList.add("-b:v");
                    arrayList.add("2500k");
                    arrayList.add("-sample_fmt");
                    arrayList.add("s16");
                    arrayList.add("-strict");
                    arrayList.add("experimental");
                    arrayList.add("-ss");
                    arrayList.add("0");
                    arrayList.add("-t");
                    arrayList.add(String.valueOf(i34));
                    arrayList.add(this.W);
                    a(arrayList, this.W);
                    break;
                }
                i14 = 3;
                StringBuilder sb792 = new StringBuilder();
                sb792.append("nullsrc=size=");
                sb792.append(sb2);
                sb792.append(" [base];");
                sb792.append(str65);
                sb792.append(str66);
                sb792.append(" [base][left] overlay=shortest=1 [tmp1]; [tmp1][middle] overlay=shortest=1:y=");
                sb792.append(((CollageData) Utils.collageData.get(1)).getYPoint());
                sb792.append(" [tmp2]; [tmp2][right] overlay=shortest=1:x=");
                sb792.append(((CollageData) Utils.collageData.get(2)).getXPoint());
                sb792.append(":y=");
                sb792.append(((CollageData) Utils.collageData.get(2)).getYPoint());
                String sb802 = sb792.toString();
                int i702 = i14;
                String str682 = str64;
                arrayList.add("-filter_complex");
                StringBuilder sb832 = new StringBuilder();
                sb832.append(sb802);
                sb832.append(str682);
                arrayList.add(sb832.toString());
                if (!c) {
                }
                arrayList.add("-c:a");
                arrayList.add("copy");
                arrayList.add("-ab");
                arrayList.add("128k");
                arrayList.add("-c:v");
                arrayList.add("mpeg4");
                arrayList.add("-r");
                arrayList.add("15");
                arrayList.add("-b:v");
                arrayList.add("2500k");
                arrayList.add("-sample_fmt");
                arrayList.add("s16");
                arrayList.add("-strict");
                arrayList.add("experimental");
                arrayList.add("-ss");
                arrayList.add("0");
                arrayList.add("-t");
                arrayList.add(String.valueOf(i34));
                arrayList.add(this.W);
                a(arrayList, this.W);
            case 7:
                String str69 = str23;
                String str70 = str24;
                String str71 = str25;
                StringBuilder sb85 = new StringBuilder();
                sb85.append(", drawbox=x=0:y=0:width=");
                int i72 = strokeWidth / 2;
                sb85.append(((CollageData) Utils.collageData.get(0)).getWidth() + i72);
                sb85.append(":height=");
                sb85.append(((CollageData) Utils.collageData.get(0)).getHeight() + i72);
                sb85.append(":thickness=");
                sb85.append(strokeWidth);
                sb85.append(":color=");
                sb85.append(format);
                sb85.append("@");
                sb85.append(valueOf);
                sb85.append(" [left];");
                StringBuilder sb86 = new StringBuilder();
                sb86.append(", drawbox=x=0:y=");
                int i73 = -i72;
                sb86.append(i73);
                sb86.append(":width=");
                sb86.append(((CollageData) Utils.collageData.get(1)).getWidth() + i72);
                sb86.append(":height=");
                sb86.append(((CollageData) Utils.collageData.get(1)).getHeight() + i72);
                sb86.append(":thickness=");
                sb86.append(strokeWidth);
                sb86.append(":color=");
                sb86.append(format);
                sb86.append("@");
                sb86.append(valueOf);
                sb86.append(" [middle];");
                StringBuilder sb87 = new StringBuilder();
                sb87.append(", drawbox=x=");
                sb87.append(i73);
                sb87.append(":y=0:width=");
                sb87.append(((CollageData) Utils.collageData.get(2)).getWidth() + i72);
                sb87.append(":height=");
                sb87.append(((CollageData) Utils.collageData.get(2)).getHeight());
                sb87.append(":thickness=");
                sb87.append(strokeWidth);
                sb87.append(":color=");
                sb87.append(format);
                sb87.append("@");
                sb87.append(valueOf);
                sb87.append(" [right];");
                String[] strArr7 = {sb85.toString(), sb86.toString(), sb87.toString()};
                arrayList.add("-y");
                String str72 = str69;
                String str73 = str70;
                for (int i74 = 0; i74 < size; i74++) {
                    String str74 = "";
                    try {
                        if (((CollageData) Utils.collageData.get(i74)).getIsImage().booleanValue()) {
                            try {
                                arrayList.add("-loop");
                                arrayList.add("1");
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i74)).getVideoUrl());
                                StringBuilder sb88 = new StringBuilder(String.valueOf(str72));
                                sb88.append(" [");
                                sb88.append(i74);
                                sb88.append(":v] trim=duration=");
                                sb88.append(i34);
                                sb88.append(" [v");
                                int i75 = i74 + 1;
                                sb88.append(i75);
                                sb88.append("];");
                                str14 = sb88.toString();
                                try {
                                    StringBuilder sb89 = new StringBuilder();
                                    sb89.append(" [v");
                                    sb89.append(i75);
                                    sb89.append("] setpts=PTS-STARTPTS, scale=");
                                    sb89.append(((CollageData) Utils.collageData.get(i74)).getWidth());
                                    sb89.append("x");
                                    sb89.append(((CollageData) Utils.collageData.get(i74)).getHeight());
                                    str74 = sb89.toString();
                                } catch (Exception e91) {
                                    try {
                                        str72 = str14;
                                        StringBuilder sb90 = new StringBuilder(String.valueOf(str73));
                                        sb90.append(str74);
                                        sb90.append(strArr7[i74]);
                                        str73 = sb90.toString();
                                    } catch (Exception e92) {
                                        str72 = str14;
                                    }
                                }
                            } catch (Exception e93) {
                                str14 = str72;
                                str72 = str14;
                                StringBuilder sb902 = new StringBuilder(String.valueOf(str73));
                                sb902.append(str74);
                                sb902.append(strArr7[i74]);
                                str73 = sb902.toString();
                            }
                            str72 = str14;
                        } else {
                            try {
                                arrayList.add("-ss");
                                arrayList.add(((CollageData) Utils.collageData.get(i74)).getStartTime());
                                arrayList.add("-t");
                                arrayList.add(String.valueOf(i34));
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i74)).getVideoUrl());
                                StringBuilder sb91 = new StringBuilder();
                                sb91.append(" [");
                                sb91.append(i74);
                                sb91.append(":v] setpts=PTS-STARTPTS, crop=w=");
                                sb91.append(((CollageData) Utils.collageData.get(i74)).getCrop_width());
                                sb91.append(":h=");
                                sb91.append(((CollageData) Utils.collageData.get(i74)).getCrop_height());
                                sb91.append(":x=");
                                sb91.append(((CollageData) Utils.collageData.get(i74)).getCrop_X());
                                sb91.append(":y=");
                                sb91.append(((CollageData) Utils.collageData.get(i74)).getCrop_Y());
                                sb91.append(",scale=");
                                sb91.append(((CollageData) Utils.collageData.get(i74)).getWidth());
                                sb91.append("x");
                                sb91.append(((CollageData) Utils.collageData.get(i74)).getHeight());
                                str74 = sb91.toString();
                            } catch (Exception e94) {
                                e94.printStackTrace();
                            }
                        }
                        StringBuilder sb9022 = new StringBuilder(String.valueOf(str73));
                        sb9022.append(str74);
                        sb9022.append(strArr7[i74]);
                        str73 = sb9022.toString();
                    } catch (Exception e95) {
                        str14 = str72;
                        str72 = str14;
                    }
                }
                if (c) {
                    try {
                        arrayList.add("-ss");
                        arrayList.add(String.valueOf(this.Rs / 1000));
                        arrayList.add("-i");
                        arrayList.add(d);
                        i17 = 4;
                    } catch (Exception e96) {
                        e96.printStackTrace();
                    }
                    StringBuilder sb92 = new StringBuilder();
                    sb92.append("nullsrc=size=");
                    sb92.append(sb2);
                    sb92.append(" [base];");
                    sb92.append(str72);
                    sb92.append(str73);
                    sb92.append(" [base][left] overlay=shortest=1 [tmp1]; [tmp1][middle] overlay=shortest=1:y=");
                    sb92.append(((CollageData) Utils.collageData.get(1)).getYPoint());
                    sb92.append(" [tmp2]; [tmp2][right] overlay=shortest=1:x=");
                    sb92.append(((CollageData) Utils.collageData.get(2)).getXPoint());
                    String sb93 = sb92.toString();
                    for (i18 = 0; i18 < this.S.size(); i18++) {
                        try {
                            StickerObj stickerObj13 = (StickerObj) this.S.get(i18);
                            arrayList.add("-i");
                            arrayList.add(stickerObj13.StickerPath);
                        } catch (Exception e97) {
                            e97.printStackTrace();
                        }
                    }
                    int i76 = i17;
                    String str75 = str71;
                    for (i19 = 0; i19 < this.S.size(); i19++) {
                        try {
                            StickerObj stickerObj14 = (StickerObj) this.S.get(i19);
                            StringBuilder sb94 = new StringBuilder(String.valueOf(str75));
                            sb94.append("[");
                            sb94.append(str26);
                            sb94.append("];");
                            sb94.append("[");
                            sb94.append(str26);
                            sb94.append("]");
                            sb94.append("[");
                            sb94.append(i76);
                            sb94.append(":v] overlay=x=");
                            sb94.append(stickerObj14.StickerX);
                            sb94.append(":y=");
                            sb94.append(stickerObj14.StickerY);
                            str13 = sb94.toString();
                            try {
                                StringBuilder sb95 = new StringBuilder(String.valueOf(str26));
                                sb95.append(i19);
                                i76++;
                                str26 = sb95.toString();
                            } catch (Exception e98) {
                                str75 = str13;
                            }
                        } catch (Exception e99) {
                            str13 = str75;
                            str75 = str13;
                        }
                        str75 = str13;
                    }
                    arrayList.add("-filter_complex");
                    StringBuilder sb96 = new StringBuilder();
                    sb96.append(sb93);
                    sb96.append(str75);
                    arrayList.add(sb96.toString());
                    if (!c) {
                        try {
                            arrayList.add("-map");
                            arrayList.add("3:a");
                        } catch (Exception e100) {
                            e100.printStackTrace();
                        }
                    } else {
                        int i77 = 0;
                        while (i77 < this.i.size()) {
                            try {
                                try {
                                    AudioTrackData audioTrackData7 = (AudioTrackData) this.i.get(i77);
                                    if (audioTrackData7.isSelected) {
                                        try {
                                            arrayList.add("-map");
                                            StringBuilder sb97 = new StringBuilder();
                                            sb97.append(audioTrackData7.mapPosition);
                                            sb97.append(":a");
                                            arrayList.add(sb97.toString());
                                        } catch (Exception e101) {
                                            e101.printStackTrace();
                                        }
                                    } else {
                                        i77++;
                                    }
                                } catch (Exception e102) {
                                    e102.printStackTrace();
                                }
                            } catch (Exception e103) {
                                e103.printStackTrace();
                            }
                        }
                    }
                    arrayList.add("-c:a");
                    arrayList.add("copy");
                    arrayList.add("-ab");
                    arrayList.add("128k");
                    arrayList.add("-c:v");
                    arrayList.add("mpeg4");
                    arrayList.add("-r");
                    arrayList.add("15");
                    arrayList.add("-b:v");
                    arrayList.add("2500k");
                    arrayList.add("-sample_fmt");
                    arrayList.add("s16");
                    arrayList.add("-strict");
                    arrayList.add("experimental");
                    arrayList.add("-ss");
                    arrayList.add("0");
                    arrayList.add("-t");
                    arrayList.add(String.valueOf(i34));
                    arrayList.add(this.W);
                    a(arrayList, this.W);
                    break;
                }
                i17 = 3;
                StringBuilder sb922 = new StringBuilder();
                sb922.append("nullsrc=size=");
                sb922.append(sb2);
                sb922.append(" [base];");
                sb922.append(str72);
                sb922.append(str73);
                sb922.append(" [base][left] overlay=shortest=1 [tmp1]; [tmp1][middle] overlay=shortest=1:y=");
                sb922.append(((CollageData) Utils.collageData.get(1)).getYPoint());
                sb922.append(" [tmp2]; [tmp2][right] overlay=shortest=1:x=");
                sb922.append(((CollageData) Utils.collageData.get(2)).getXPoint());
                String sb932 = sb922.toString();
                int i762 = i17;
                String str752 = str71;
                arrayList.add("-filter_complex");
                StringBuilder sb962 = new StringBuilder();
                sb962.append(sb932);
                sb962.append(str752);
                arrayList.add(sb962.toString());
                if (!c) {
                }
                arrayList.add("-c:a");
                arrayList.add("copy");
                arrayList.add("-ab");
                arrayList.add("128k");
                arrayList.add("-c:v");
                arrayList.add("mpeg4");
                arrayList.add("-r");
                arrayList.add("15");
                arrayList.add("-b:v");
                arrayList.add("2500k");
                arrayList.add("-sample_fmt");
                arrayList.add("s16");
                arrayList.add("-strict");
                arrayList.add("experimental");
                arrayList.add("-ss");
                arrayList.add("0");
                arrayList.add("-t");
                arrayList.add(String.valueOf(i34));
                arrayList.add(this.W);
                a(arrayList, this.W);
            case 8:
                String str76 = str23;
                String str77 = str24;
                String str78 = str25;
                StringBuilder sb98 = new StringBuilder();
                sb98.append(", drawbox=x=0:y=0:width=");
                int i78 = strokeWidth / 2;
                sb98.append(((CollageData) Utils.collageData.get(0)).getWidth() + i78);
                sb98.append(":height=");
                sb98.append(((CollageData) Utils.collageData.get(0)).getHeight());
                sb98.append(":thickness=");
                sb98.append(strokeWidth);
                sb98.append(":color=");
                sb98.append(format);
                sb98.append("@");
                sb98.append(valueOf);
                sb98.append(" [left];");
                StringBuilder sb99 = new StringBuilder();
                sb99.append(", drawbox=x=");
                int i79 = -i78;
                sb99.append(i79);
                sb99.append(":y=0:width=");
                sb99.append(((CollageData) Utils.collageData.get(1)).getWidth() + i78);
                sb99.append(":height=");
                sb99.append(((CollageData) Utils.collageData.get(1)).getHeight() + i78);
                sb99.append(":thickness=");
                sb99.append(strokeWidth);
                sb99.append(":color=");
                sb99.append(format);
                sb99.append("@");
                sb99.append(valueOf);
                sb99.append(" [middle];");
                StringBuilder sb100 = new StringBuilder();
                sb100.append(", drawbox=x=");
                sb100.append(i79);
                sb100.append(":y=");
                sb100.append(i79);
                sb100.append(":width=");
                sb100.append(((CollageData) Utils.collageData.get(2)).getWidth() + i78);
                sb100.append(":height=");
                sb100.append(((CollageData) Utils.collageData.get(2)).getHeight() + i78);
                sb100.append(":thickness=");
                sb100.append(strokeWidth);
                sb100.append(":color=");
                sb100.append(format);
                sb100.append("@");
                sb100.append(valueOf);
                sb100.append(" [right];");
                String[] strArr8 = {sb98.toString(), sb99.toString(), sb100.toString()};
                arrayList.add("-y");
                String str79 = str76;
                String str80 = str77;
                for (int i80 = 0; i80 < size; i80++) {
                    String str81 = "";
                    try {
                        if (((CollageData) Utils.collageData.get(i80)).getIsImage().booleanValue()) {
                            try {
                                arrayList.add("-loop");
                                arrayList.add("1");
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i80)).getVideoUrl());
                                StringBuilder sb101 = new StringBuilder(String.valueOf(str79));
                                sb101.append(" [");
                                sb101.append(i80);
                                sb101.append(":v] trim=duration=");
                                sb101.append(i34);
                                sb101.append(" [v");
                                int i81 = i80 + 1;
                                sb101.append(i81);
                                sb101.append("];");
                                str16 = sb101.toString();
                                try {
                                    StringBuilder sb102 = new StringBuilder();
                                    sb102.append(" [v");
                                    sb102.append(i81);
                                    sb102.append("] setpts=PTS-STARTPTS, scale=");
                                    sb102.append(((CollageData) Utils.collageData.get(i80)).getWidth());
                                    sb102.append("x");
                                    sb102.append(((CollageData) Utils.collageData.get(i80)).getHeight());
                                    str81 = sb102.toString();
                                } catch (Exception e104) {
                                    try {
                                        str79 = str16;
                                        StringBuilder sb103 = new StringBuilder(String.valueOf(str80));
                                        sb103.append(str81);
                                        sb103.append(strArr8[i80]);
                                        str80 = sb103.toString();
                                    } catch (Exception e105) {
                                        str79 = str16;
                                    }
                                }
                            } catch (Exception e106) {
                                str16 = str79;
                                str79 = str16;
                                StringBuilder sb1032 = new StringBuilder(String.valueOf(str80));
                                sb1032.append(str81);
                                sb1032.append(strArr8[i80]);
                                str80 = sb1032.toString();
                            }
                            str79 = str16;
                        } else {
                            try {
                                arrayList.add("-ss");
                                arrayList.add(((CollageData) Utils.collageData.get(i80)).getStartTime());
                                arrayList.add("-t");
                                arrayList.add(String.valueOf(i34));
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i80)).getVideoUrl());
                                StringBuilder sb104 = new StringBuilder();
                                sb104.append(" [");
                                sb104.append(i80);
                                sb104.append(":v] setpts=PTS-STARTPTS,crop=w=");
                                sb104.append(((CollageData) Utils.collageData.get(i80)).getCrop_width());
                                sb104.append(":h=");
                                sb104.append(((CollageData) Utils.collageData.get(i80)).getCrop_height());
                                sb104.append(":x=");
                                sb104.append(((CollageData) Utils.collageData.get(i80)).getCrop_X());
                                sb104.append(":y=");
                                sb104.append(((CollageData) Utils.collageData.get(i80)).getCrop_Y());
                                sb104.append(", scale=");
                                sb104.append(((CollageData) Utils.collageData.get(i80)).getWidth());
                                sb104.append("x");
                                sb104.append(((CollageData) Utils.collageData.get(i80)).getHeight());
                                str81 = sb104.toString();
                            } catch (Exception e107) {
                                e107.printStackTrace();
                            }
                        }
                        StringBuilder sb10322 = new StringBuilder(String.valueOf(str80));
                        sb10322.append(str81);
                        sb10322.append(strArr8[i80]);
                        str80 = sb10322.toString();
                    } catch (Exception e108) {
                        str16 = str79;

                        str79 = str16;
                    }
                }
                if (c) {
                    try {
                        arrayList.add("-ss");
                        arrayList.add(String.valueOf(this.Rs / 1000));
                        arrayList.add("-i");
                        arrayList.add(d);
                        i20 = 4;
                    } catch (Exception e109) {
                        e109.printStackTrace();
                    }
                    StringBuilder sb105 = new StringBuilder();
                    sb105.append("nullsrc=size=");
                    sb105.append(sb2);
                    sb105.append(" [base];");
                    sb105.append(str79);
                    sb105.append(str80);
                    sb105.append(" [base][left] overlay=shortest=1 [tmp1]; [tmp1][middle] overlay=shortest=1:x=");
                    sb105.append(((CollageData) Utils.collageData.get(1)).getXPoint());
                    sb105.append(" [tmp2]; [tmp2][right] overlay=shortest=1:x=");
                    sb105.append(((CollageData) Utils.collageData.get(2)).getXPoint());
                    sb105.append(":y=");
                    sb105.append(((CollageData) Utils.collageData.get(2)).getYPoint());
                    String sb106 = sb105.toString();
                    for (i21 = 0; i21 < this.S.size(); i21++) {
                        try {
                            StickerObj stickerObj15 = (StickerObj) this.S.get(i21);
                            arrayList.add("-i");
                            arrayList.add(stickerObj15.StickerPath);
                        } catch (Exception e110) {
                            e110.printStackTrace();
                        }
                    }
                    int i82 = i20;
                    String str82 = str78;
                    for (i22 = 0; i22 < this.S.size(); i22++) {
                        try {
                            StickerObj stickerObj16 = (StickerObj) this.S.get(i22);
                            StringBuilder sb107 = new StringBuilder(String.valueOf(str82));
                            sb107.append("[");
                            sb107.append(str26);
                            sb107.append("];");
                            sb107.append("[");
                            sb107.append(str26);
                            sb107.append("]");
                            sb107.append("[");
                            sb107.append(i82);
                            sb107.append(":v] overlay=x=");
                            sb107.append(stickerObj16.StickerX);
                            sb107.append(":y=");
                            sb107.append(stickerObj16.StickerY);
                            str15 = sb107.toString();
                            try {
                                StringBuilder sb108 = new StringBuilder(String.valueOf(str26));
                                sb108.append(i22);
                                i82++;
                                str26 = sb108.toString();
                            } catch (Exception e111) {

                                str82 = str15;
                            }
                        } catch (Exception e112) {
                            str15 = str82;

                            str82 = str15;
                        }
                        str82 = str15;
                    }
                    arrayList.add("-filter_complex");
                    StringBuilder sb109 = new StringBuilder();
                    sb109.append(sb106);
                    sb109.append(str82);
                    arrayList.add(sb109.toString());
                    if (!c) {
                        try {
                            arrayList.add("-map");
                            arrayList.add("3:a");
                        } catch (Exception e113) {
                            e113.printStackTrace();
                        }
                    } else {
                        int i83 = 0;
                        while (i83 < this.i.size()) {
                            try {
                                try {
                                    AudioTrackData audioTrackData8 = (AudioTrackData) this.i.get(i83);
                                    if (audioTrackData8.isSelected) {
                                        try {
                                            arrayList.add("-map");
                                            StringBuilder sb110 = new StringBuilder();
                                            sb110.append(audioTrackData8.mapPosition);
                                            sb110.append(":a");
                                            arrayList.add(sb110.toString());
                                        } catch (Exception e114) {
                                            e114.printStackTrace();
                                        }
                                    } else {
                                        i83++;
                                    }
                                } catch (Exception e115) {
                                    e115.printStackTrace();
                                }
                            } catch (Exception e116) {
                                e116.printStackTrace();
                            }
                        }
                    }
                    arrayList.add("-c:a");
                    arrayList.add("copy");
                    arrayList.add("-ab");
                    arrayList.add("128k");
                    arrayList.add("-c:v");
                    arrayList.add("mpeg4");
                    arrayList.add("-r");
                    arrayList.add("15");
                    arrayList.add("-b:v");
                    arrayList.add("2500k");
                    arrayList.add("-sample_fmt");
                    arrayList.add("s16");
                    arrayList.add("-strict");
                    arrayList.add("experimental");
                    arrayList.add("-ss");
                    arrayList.add("0");
                    arrayList.add("-t");
                    arrayList.add(String.valueOf(i34));
                    arrayList.add(this.W);
                    a(arrayList, this.W);
                    break;
                }
                i20 = 3;
                StringBuilder sb1052 = new StringBuilder();
                sb1052.append("nullsrc=size=");
                sb1052.append(sb2);
                sb1052.append(" [base];");
                sb1052.append(str79);
                sb1052.append(str80);
                sb1052.append(" [base][left] overlay=shortest=1 [tmp1]; [tmp1][middle] overlay=shortest=1:x=");
                sb1052.append(((CollageData) Utils.collageData.get(1)).getXPoint());
                sb1052.append(" [tmp2]; [tmp2][right] overlay=shortest=1:x=");
                sb1052.append(((CollageData) Utils.collageData.get(2)).getXPoint());
                sb1052.append(":y=");
                sb1052.append(((CollageData) Utils.collageData.get(2)).getYPoint());
                String sb1062 = sb1052.toString();
                int i822 = i20;
                String str822 = str78;
                arrayList.add("-filter_complex");
                StringBuilder sb1092 = new StringBuilder();
                sb1092.append(sb1062);
                sb1092.append(str822);
                arrayList.add(sb1092.toString());
                if (!c) {
                }
                arrayList.add("-c:a");
                arrayList.add("copy");
                arrayList.add("-ab");
                arrayList.add("128k");
                arrayList.add("-c:v");
                arrayList.add("mpeg4");
                arrayList.add("-r");
                arrayList.add("15");
                arrayList.add("-b:v");
                arrayList.add("2500k");
                arrayList.add("-sample_fmt");
                arrayList.add("s16");
                arrayList.add("-strict");
                arrayList.add("experimental");
                arrayList.add("-ss");
                arrayList.add("0");
                arrayList.add("-t");
                arrayList.add(String.valueOf(i34));
                arrayList.add(this.W);
                a(arrayList, this.W);
            case 9:
                String str83 = str23;
                String str84 = str24;
                String str85 = str25;
                StringBuilder sb111 = new StringBuilder();
                sb111.append(", drawbox=x=0:y=0:width=");
                sb111.append(((CollageData) Utils.collageData.get(0)).getWidth());
                sb111.append(":height=");
                int i84 = strokeWidth / 2;
                sb111.append(((CollageData) Utils.collageData.get(0)).getHeight() + i84);
                sb111.append(":thickness=");
                sb111.append(strokeWidth);
                sb111.append(":color=");
                sb111.append(format);
                sb111.append("@");
                sb111.append(valueOf);
                sb111.append(" [one];");
                StringBuilder sb112 = new StringBuilder();
                sb112.append(", drawbox=x=0:y=");
                int i85 = -i84;
                sb112.append(i85);
                sb112.append(":width=");
                sb112.append(((CollageData) Utils.collageData.get(1)).getWidth());
                sb112.append(":height=");
                sb112.append(((CollageData) Utils.collageData.get(1)).getHeight() + strokeWidth);
                sb112.append(":thickness=");
                sb112.append(strokeWidth);
                sb112.append(":color=");
                sb112.append(format);
                sb112.append("@");
                sb112.append(valueOf);
                sb112.append(" [two];");
                StringBuilder sb113 = new StringBuilder();
                sb113.append(", drawbox=x=0:y=");
                sb113.append(i85);
                sb113.append(":width=");
                sb113.append(((CollageData) Utils.collageData.get(2)).getWidth());
                sb113.append(":height=");
                sb113.append(((CollageData) Utils.collageData.get(2)).getHeight() + strokeWidth);
                sb113.append(":thickness=");
                sb113.append(strokeWidth);
                sb113.append(":color=");
                sb113.append(format);
                sb113.append("@");
                sb113.append(valueOf);
                sb113.append(" [three];");
                StringBuilder sb114 = new StringBuilder();
                sb114.append(", drawbox=x=0:y=");
                sb114.append(i85);
                sb114.append(":width=");
                sb114.append(((CollageData) Utils.collageData.get(3)).getWidth());
                sb114.append(":height=");
                sb114.append(((CollageData) Utils.collageData.get(3)).getHeight() + i84);
                sb114.append(":thickness=");
                sb114.append(strokeWidth);
                sb114.append(":color=");
                sb114.append(format);
                sb114.append("@");
                sb114.append(valueOf);
                sb114.append(" [four];");
                String[] strArr9 = {sb111.toString(), sb112.toString(), sb113.toString(), sb114.toString()};
                arrayList.add("-y");
                String str86 = str83;
                String str87 = str84;
                for (int i86 = 0; i86 < size; i86++) {
                    String str88 = "";
                    try {
                        if (((CollageData) Utils.collageData.get(i86)).getIsImage().booleanValue()) {
                            try {
                                arrayList.add("-loop");
                                arrayList.add("1");
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i86)).getVideoUrl());
                                StringBuilder sb115 = new StringBuilder(String.valueOf(str86));
                                sb115.append(" [");
                                sb115.append(i86);
                                sb115.append(":v] trim=duration=");
                                sb115.append(i34);
                                sb115.append(" [v");
                                int i87 = i86 + 1;
                                sb115.append(i87);
                                sb115.append("];");
                                str18 = sb115.toString();
                                try {
                                    StringBuilder sb116 = new StringBuilder();
                                    sb116.append(" [v");
                                    sb116.append(i87);
                                    sb116.append("] setpts=PTS-STARTPTS, scale=");
                                    sb116.append(((CollageData) Utils.collageData.get(i86)).getWidth());
                                    sb116.append("x");
                                    sb116.append(((CollageData) Utils.collageData.get(i86)).getHeight());
                                    str88 = sb116.toString();
                                } catch (Exception e117) {
                                    try {

                                        str86 = str18;
                                        StringBuilder sb117 = new StringBuilder(String.valueOf(str87));
                                        sb117.append(str88);
                                        sb117.append(strArr9[i86]);
                                        str87 = sb117.toString();
                                    } catch (Exception e118) {

                                        str86 = str18;
                                    }
                                }
                            } catch (Exception e119) {
                                str18 = str86;

                                str86 = str18;
                                StringBuilder sb1172 = new StringBuilder(String.valueOf(str87));
                                sb1172.append(str88);
                                sb1172.append(strArr9[i86]);
                                str87 = sb1172.toString();
                            }
                            str86 = str18;
                        } else {
                            try {
                                arrayList.add("-ss");
                                arrayList.add(((CollageData) Utils.collageData.get(i86)).getStartTime());
                                arrayList.add("-t");
                                arrayList.add(String.valueOf(i34));
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i86)).getVideoUrl());
                                StringBuilder sb118 = new StringBuilder();
                                sb118.append(" [");
                                sb118.append(i86);
                                sb118.append(":v] setpts=PTS-STARTPTS,crop=w=");
                                sb118.append(((CollageData) Utils.collageData.get(i86)).getCrop_width());
                                sb118.append(":h=");
                                sb118.append(((CollageData) Utils.collageData.get(i86)).getCrop_height());
                                sb118.append(":x=");
                                sb118.append(((CollageData) Utils.collageData.get(i86)).getCrop_X());
                                sb118.append(":y=");
                                sb118.append(((CollageData) Utils.collageData.get(i86)).getCrop_Y());
                                sb118.append(", scale=");
                                sb118.append(((CollageData) Utils.collageData.get(i86)).getWidth());
                                sb118.append("x");
                                sb118.append(((CollageData) Utils.collageData.get(i86)).getHeight());
                                str88 = sb118.toString();
                            } catch (Exception e120) {
                                e120.printStackTrace();
                            }
                        }
                        StringBuilder sb11722 = new StringBuilder(String.valueOf(str87));
                        sb11722.append(str88);
                        sb11722.append(strArr9[i86]);
                        str87 = sb11722.toString();
                    } catch (Exception e121) {
                        str18 = str86;

                        str86 = str18;
                    }
                }
                if (c) {
                    try {
                        arrayList.add("-ss");
                        arrayList.add(String.valueOf(this.Rs / 1000));
                        arrayList.add("-i");
                        arrayList.add(d);
                        i23 = 5;
                    } catch (Exception e122) {
                        e122.printStackTrace();
                    }
                    StringBuilder sb119 = new StringBuilder();
                    sb119.append("nullsrc=size=");
                    sb119.append(sb2);
                    sb119.append(" [base];");
                    sb119.append(str86);
                    sb119.append(str87);
                    sb119.append(" [base][one] overlay=shortest=1 [tmp1]; [tmp1][two] overlay=shortest=1:y=");
                    sb119.append(((CollageData) Utils.collageData.get(1)).getYPoint());
                    sb119.append(" [tmp2]; [tmp2][three] overlay=shortest=1:y=");
                    sb119.append(((CollageData) Utils.collageData.get(2)).getYPoint());
                    sb119.append(" [tmp3]; [tmp3][four] overlay=shortest=1:y=");
                    sb119.append(((CollageData) Utils.collageData.get(3)).getYPoint());
                    String sb120 = sb119.toString();
                    for (i24 = 0; i24 < this.S.size(); i24++) {
                        try {
                            StickerObj stickerObj17 = (StickerObj) this.S.get(i24);
                            arrayList.add("-i");
                            arrayList.add(stickerObj17.StickerPath);
                        } catch (Exception e123) {
                            e123.printStackTrace();
                        }
                    }
                    int i88 = i23;
                    String str89 = str85;
                    for (i25 = 0; i25 < this.S.size(); i25++) {
                        try {
                            StickerObj stickerObj18 = (StickerObj) this.S.get(i25);
                            StringBuilder sb121 = new StringBuilder(String.valueOf(str89));
                            sb121.append("[");
                            sb121.append(str26);
                            sb121.append("];");
                            sb121.append("[");
                            sb121.append(str26);
                            sb121.append("]");
                            sb121.append("[");
                            sb121.append(i88);
                            sb121.append(":v] overlay=x=");
                            sb121.append(stickerObj18.StickerX);
                            sb121.append(":y=");
                            sb121.append(stickerObj18.StickerY);
                            str17 = sb121.toString();
                            try {
                                StringBuilder sb122 = new StringBuilder(String.valueOf(str26));
                                sb122.append(i25);
                                i88++;
                                str26 = sb122.toString();
                            } catch (Exception e124) {

                                str89 = str17;
                            }
                        } catch (Exception e125) {
                            str17 = str89;

                            str89 = str17;
                        }
                        str89 = str17;
                    }
                    arrayList.add("-filter_complex");
                    StringBuilder sb123 = new StringBuilder();
                    sb123.append(sb120);
                    sb123.append(str89);
                    arrayList.add(sb123.toString());
                    if (!c) {
                        try {
                            arrayList.add("-map");
                            arrayList.add("4:a");
                        } catch (Exception e126) {
                            e126.printStackTrace();
                        }
                    } else {
                        int i89 = 0;
                        while (i89 < this.i.size()) {
                            try {
                                try {
                                    AudioTrackData audioTrackData9 = (AudioTrackData) this.i.get(i89);
                                    if (audioTrackData9.isSelected) {
                                        try {
                                            arrayList.add("-map");
                                            StringBuilder sb124 = new StringBuilder();
                                            sb124.append(audioTrackData9.mapPosition);
                                            sb124.append(":a");
                                            arrayList.add(sb124.toString());
                                        } catch (Exception e127) {
                                            e127.printStackTrace();
                                        }
                                    } else {
                                        i89++;
                                    }
                                } catch (Exception e128) {
                                    e128.printStackTrace();
                                }
                            } catch (Exception e129) {
                                e129.printStackTrace();
                            }
                        }
                    }
                    arrayList.add("-c:a");
                    arrayList.add("copy");
                    arrayList.add("-ab");
                    arrayList.add("128k");
                    arrayList.add("-c:v");
                    arrayList.add("mpeg4");
                    arrayList.add("-r");
                    arrayList.add("15");
                    arrayList.add("-b:v");
                    arrayList.add("2500k");
                    arrayList.add("-sample_fmt");
                    arrayList.add("s16");
                    arrayList.add("-strict");
                    arrayList.add("experimental");
                    arrayList.add("-ss");
                    arrayList.add("0");
                    arrayList.add("-t");
                    arrayList.add(String.valueOf(i34));
                    arrayList.add(this.W);
                    a(arrayList, this.W);
                    break;
                }
                i23 = 4;
                StringBuilder sb1192 = new StringBuilder();
                sb1192.append("nullsrc=size=");
                sb1192.append(sb2);
                sb1192.append(" [base];");
                sb1192.append(str86);
                sb1192.append(str87);
                sb1192.append(" [base][one] overlay=shortest=1 [tmp1]; [tmp1][two] overlay=shortest=1:y=");
                sb1192.append(((CollageData) Utils.collageData.get(1)).getYPoint());
                sb1192.append(" [tmp2]; [tmp2][three] overlay=shortest=1:y=");
                sb1192.append(((CollageData) Utils.collageData.get(2)).getYPoint());
                sb1192.append(" [tmp3]; [tmp3][four] overlay=shortest=1:y=");
                sb1192.append(((CollageData) Utils.collageData.get(3)).getYPoint());
                String sb1202 = sb1192.toString();
                int i882 = i23;
                String str892 = str85;
                arrayList.add("-filter_complex");
                StringBuilder sb1232 = new StringBuilder();
                sb1232.append(sb1202);
                sb1232.append(str892);
                arrayList.add(sb1232.toString());
                if (!c) {
                }
                arrayList.add("-c:a");
                arrayList.add("copy");
                arrayList.add("-ab");
                arrayList.add("128k");
                arrayList.add("-c:v");
                arrayList.add("mpeg4");
                arrayList.add("-r");
                arrayList.add("15");
                arrayList.add("-b:v");
                arrayList.add("2500k");
                arrayList.add("-sample_fmt");
                arrayList.add("s16");
                arrayList.add("-strict");
                arrayList.add("experimental");
                arrayList.add("-ss");
                arrayList.add("0");
                arrayList.add("-t");
                arrayList.add(String.valueOf(i34));
                arrayList.add(this.W);
                a(arrayList, this.W);
            case 10:
                String str90 = str23;
                String str91 = str24;
                String str92 = str25;
                StringBuilder sb125 = new StringBuilder();
                sb125.append(", drawbox=x=0:y=0:width=");
                int i90 = strokeWidth / 2;
                sb125.append(((CollageData) Utils.collageData.get(0)).getWidth() + i90);
                sb125.append(":height=");
                sb125.append(((CollageData) Utils.collageData.get(0)).getHeight());
                sb125.append(":thickness=");
                sb125.append(strokeWidth);
                sb125.append(":color=");
                sb125.append(format);
                sb125.append("@");
                sb125.append(valueOf);
                sb125.append(" [one];");
                StringBuilder sb126 = new StringBuilder();
                sb126.append(", drawbox=x=");
                int i91 = -i90;
                sb126.append(i91);
                sb126.append(":y=0:width=");
                sb126.append(((CollageData) Utils.collageData.get(1)).getWidth() + strokeWidth);
                sb126.append(":height=");
                sb126.append(((CollageData) Utils.collageData.get(1)).getHeight());
                sb126.append(":thickness=");
                sb126.append(strokeWidth);
                sb126.append(":color=");
                sb126.append(format);
                sb126.append("@");
                sb126.append(valueOf);
                sb126.append(" [two];");
                StringBuilder sb127 = new StringBuilder();
                sb127.append(", drawbox=x=");
                sb127.append(i91);
                sb127.append(":y=0:width=");
                sb127.append(((CollageData) Utils.collageData.get(2)).getWidth() + strokeWidth);
                sb127.append(":height=");
                sb127.append(((CollageData) Utils.collageData.get(2)).getHeight());
                sb127.append(":thickness=");
                sb127.append(strokeWidth);
                sb127.append(":color=");
                sb127.append(format);
                sb127.append("@");
                sb127.append(valueOf);
                sb127.append(" [three];");
                StringBuilder sb128 = new StringBuilder();
                sb128.append(", drawbox=x=");
                sb128.append(i91);
                sb128.append(":y=0:width=");
                sb128.append(((CollageData) Utils.collageData.get(3)).getWidth() + i90);
                sb128.append(":height=");
                sb128.append(((CollageData) Utils.collageData.get(3)).getHeight());
                sb128.append(":thickness=");
                sb128.append(strokeWidth);
                sb128.append(":color=");
                sb128.append(format);
                sb128.append("@");
                sb128.append(valueOf);
                sb128.append(" [four];");
                String[] strArr10 = {sb125.toString(), sb126.toString(), sb127.toString(), sb128.toString()};
                arrayList.add("-y");
                String str93 = str90;
                String str94 = str91;
                for (int i92 = 0; i92 < size; i92++) {
                    String str95 = "";
                    try {
                        if (((CollageData) Utils.collageData.get(i92)).getIsImage().booleanValue()) {
                            try {
                                arrayList.add("-loop");
                                arrayList.add("1");
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i92)).getVideoUrl());
                                StringBuilder sb129 = new StringBuilder(String.valueOf(str93));
                                sb129.append(" [");
                                sb129.append(i92);
                                sb129.append(":v] trim=duration=");
                                sb129.append(i34);
                                sb129.append(" [v");
                                int i93 = i92 + 1;
                                sb129.append(i93);
                                sb129.append("];");
                                str20 = sb129.toString();
                                try {
                                    StringBuilder sb130 = new StringBuilder();
                                    sb130.append(" [v");
                                    sb130.append(i93);
                                    sb130.append("] setpts=PTS-STARTPTS, scale=");
                                    sb130.append(((CollageData) Utils.collageData.get(i92)).getWidth());
                                    sb130.append("x");
                                    sb130.append(((CollageData) Utils.collageData.get(i92)).getHeight());
                                    str95 = sb130.toString();
                                } catch (Exception e130) {
                                    try {

                                        str93 = str20;
                                        StringBuilder sb131 = new StringBuilder(String.valueOf(str94));
                                        sb131.append(str95);
                                        sb131.append(strArr10[i92]);
                                        str94 = sb131.toString();
                                    } catch (Exception e131) {

                                        str93 = str20;
                                    }
                                }
                            } catch (Exception e132) {
                                str20 = str93;

                                str93 = str20;
                                StringBuilder sb1312 = new StringBuilder(String.valueOf(str94));
                                sb1312.append(str95);
                                sb1312.append(strArr10[i92]);
                                str94 = sb1312.toString();
                            }
                            str93 = str20;
                        } else {
                            try {
                                arrayList.add("-ss");
                                arrayList.add(((CollageData) Utils.collageData.get(i92)).getStartTime());
                                arrayList.add("-t");
                                arrayList.add(String.valueOf(i34));
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i92)).getVideoUrl());
                                StringBuilder sb133 = new StringBuilder();
                                sb133.append(" [");
                                sb133.append(i92);
                                sb133.append(":v] setpts=PTS-STARTPTS, crop=w=");
                                sb133.append(((CollageData) Utils.collageData.get(i92)).getCrop_width());
                                sb133.append(":h=");
                                sb133.append(((CollageData) Utils.collageData.get(i92)).getCrop_height());
                                sb133.append(":x=");
                                sb133.append(((CollageData) Utils.collageData.get(i92)).getCrop_X());
                                sb133.append(":y=");
                                sb133.append(((CollageData) Utils.collageData.get(i92)).getCrop_Y());
                                sb133.append(",scale=");
                                sb133.append(((CollageData) Utils.collageData.get(i92)).getWidth());
                                sb133.append("x");
                                sb133.append(((CollageData) Utils.collageData.get(i92)).getHeight());
                                str95 = sb133.toString();
                            } catch (Exception e133) {
                                e133.printStackTrace();
                            }
                        }
                        StringBuilder sb13122 = new StringBuilder(String.valueOf(str94));
                        sb13122.append(str95);
                        sb13122.append(strArr10[i92]);
                        str94 = sb13122.toString();
                    } catch (Exception e134) {
                        str20 = str93;

                        str93 = str20;
                    }
                }
                if (c) {
                    try {
                        arrayList.add("-ss");
                        arrayList.add(String.valueOf(this.Rs / 1000));
                        arrayList.add("-i");
                        arrayList.add(d);
                        i26 = 5;
                    } catch (Exception e135) {
                        e135.printStackTrace();
                    }
                    StringBuilder sb134 = new StringBuilder();
                    sb134.append("nullsrc=size=");
                    sb134.append(sb2);
                    sb134.append(" [base];");
                    sb134.append(str93);
                    sb134.append(str94);
                    sb134.append(" [base][one] overlay=shortest=1 [tmp1]; [tmp1][two] overlay=shortest=1:x=");
                    sb134.append(((CollageData) Utils.collageData.get(1)).getXPoint());
                    sb134.append(" [tmp2]; [tmp2][three] overlay=shortest=1:x=");
                    sb134.append(((CollageData) Utils.collageData.get(2)).getXPoint());
                    sb134.append(" [tmp3]; [tmp3][four] overlay=shortest=1:x=");
                    sb134.append(((CollageData) Utils.collageData.get(3)).getXPoint());
                    String sb135 = sb134.toString();
                    for (i27 = 0; i27 < this.S.size(); i27++) {
                        try {
                            StickerObj stickerObj19 = (StickerObj) this.S.get(i27);
                            arrayList.add("-i");
                            arrayList.add(stickerObj19.StickerPath);
                        } catch (Exception e136) {
                            e136.printStackTrace();
                        }
                    }
                    int i94 = i26;
                    String str96 = str92;
                    for (i28 = 0; i28 < this.S.size(); i28++) {
                        try {
                            StickerObj stickerObj20 = (StickerObj) this.S.get(i28);
                            StringBuilder sb136 = new StringBuilder(String.valueOf(str96));
                            sb136.append("[");
                            sb136.append(str26);
                            sb136.append("];");
                            sb136.append("[");
                            sb136.append(str26);
                            sb136.append("]");
                            sb136.append("[");
                            sb136.append(i94);
                            sb136.append(":v] overlay=x=");
                            sb136.append(stickerObj20.StickerX);
                            sb136.append(":y=");
                            sb136.append(stickerObj20.StickerY);
                            str19 = sb136.toString();
                            try {
                                StringBuilder sb137 = new StringBuilder(String.valueOf(str26));
                                sb137.append(i28);
                                i94++;
                                str26 = sb137.toString();
                            } catch (Exception e137) {

                                str96 = str19;
                            }
                        } catch (Exception e138) {
                            str19 = str96;

                            str96 = str19;
                        }
                        str96 = str19;
                    }
                    arrayList.add("-filter_complex");
                    StringBuilder sb138 = new StringBuilder();
                    sb138.append(sb135);
                    sb138.append(str96);
                    arrayList.add(sb138.toString());
                    if (!c) {
                        try {
                            arrayList.add("-map");
                            arrayList.add("4:a");
                        } catch (Exception e139) {
                            e139.printStackTrace();
                        }
                    } else {
                        int i95 = 0;
                        while (i95 < this.i.size()) {
                            try {
                                try {
                                    AudioTrackData audioTrackData10 = (AudioTrackData) this.i.get(i95);
                                    if (audioTrackData10.isSelected) {
                                        try {
                                            arrayList.add("-map");
                                            StringBuilder sb139 = new StringBuilder();
                                            sb139.append(audioTrackData10.mapPosition);
                                            sb139.append(":a");
                                            arrayList.add(sb139.toString());
                                        } catch (Exception e140) {
                                            e140.printStackTrace();
                                        }
                                    } else {
                                        i95++;
                                    }
                                } catch (Exception e141) {
                                    e141.printStackTrace();
                                }
                            } catch (Exception e142) {
                                e142.printStackTrace();
                            }
                        }
                    }
                    arrayList.add("-c:a");
                    arrayList.add("copy");
                    arrayList.add("-ab");
                    arrayList.add("128k");
                    arrayList.add("-c:v");
                    arrayList.add("mpeg4");
                    arrayList.add("-r");
                    arrayList.add("15");
                    arrayList.add("-b:v");
                    arrayList.add("2500k");
                    arrayList.add("-sample_fmt");
                    arrayList.add("s16");
                    arrayList.add("-strict");
                    arrayList.add("experimental");
                    arrayList.add("-ss");
                    arrayList.add("0");
                    arrayList.add("-t");
                    arrayList.add(String.valueOf(i34));
                    arrayList.add(this.W);
                    a(arrayList, this.W);
                    break;
                }
                i26 = 4;
                StringBuilder sb1342 = new StringBuilder();
                sb1342.append("nullsrc=size=");
                sb1342.append(sb2);
                sb1342.append(" [base];");
                sb1342.append(str93);
                sb1342.append(str94);
                sb1342.append(" [base][one] overlay=shortest=1 [tmp1]; [tmp1][two] overlay=shortest=1:x=");
                sb1342.append(((CollageData) Utils.collageData.get(1)).getXPoint());
                sb1342.append(" [tmp2]; [tmp2][three] overlay=shortest=1:x=");
                sb1342.append(((CollageData) Utils.collageData.get(2)).getXPoint());
                sb1342.append(" [tmp3]; [tmp3][four] overlay=shortest=1:x=");
                sb1342.append(((CollageData) Utils.collageData.get(3)).getXPoint());
                String sb1352 = sb1342.toString();
                int i942 = i26;
                String str962 = str92;
                arrayList.add("-filter_complex");
                StringBuilder sb1382 = new StringBuilder();
                sb1382.append(sb1352);
                sb1382.append(str962);
                arrayList.add(sb1382.toString());
                if (!c) {
                }
                arrayList.add("-c:a");
                arrayList.add("copy");
                arrayList.add("-ab");
                arrayList.add("128k");
                arrayList.add("-c:v");
                arrayList.add("mpeg4");
                arrayList.add("-r");
                arrayList.add("15");
                arrayList.add("-b:v");
                arrayList.add("2500k");
                arrayList.add("-sample_fmt");
                arrayList.add("s16");
                arrayList.add("-strict");
                arrayList.add("experimental");
                arrayList.add("-ss");
                arrayList.add("0");
                arrayList.add("-t");
                arrayList.add(String.valueOf(i34));
                arrayList.add(this.W);
                a(arrayList, this.W);
            case 11:
                StringBuilder sb140 = new StringBuilder();
                sb140.append(", drawbox=x=0:y=0:width=");
                String str97 = str23;
                int i96 = strokeWidth / 2;
                sb140.append(((CollageData) Utils.collageData.get(0)).getWidth() + i96);
                sb140.append(":height=");
                String str98 = str24;
                sb140.append(((CollageData) Utils.collageData.get(0)).getHeight() + i96);
                sb140.append(":thickness=");
                sb140.append(strokeWidth);
                sb140.append(":color=");
                sb140.append(format);
                sb140.append("@");
                sb140.append(valueOf);
                sb140.append(" [one];");
                StringBuilder sb141 = new StringBuilder();
                sb141.append(", drawbox=x=");
                int i97 = -i96;
                sb141.append(i97);
                sb141.append(":y=0:width=");
                String str99 = str25;
                sb141.append(((CollageData) Utils.collageData.get(1)).getWidth() + i96);
                sb141.append(":height=");
                sb141.append(((CollageData) Utils.collageData.get(1)).getHeight() + i96);
                sb141.append(":thickness=");
                sb141.append(strokeWidth);
                sb141.append(":color=");
                sb141.append(format);
                sb141.append("@");
                sb141.append(valueOf);
                sb141.append(" [two];");
                StringBuilder sb142 = new StringBuilder();
                sb142.append(", drawbox=x=0:y=");
                sb142.append(i97);
                sb142.append(":width=");
                sb142.append(((CollageData) Utils.collageData.get(2)).getWidth() + i96);
                sb142.append(":height=");
                sb142.append(((CollageData) Utils.collageData.get(2)).getHeight() + i96);
                sb142.append(":thickness=");
                sb142.append(strokeWidth);
                sb142.append(":color=");
                sb142.append(format);
                sb142.append("@");
                sb142.append(valueOf);
                sb142.append(" [three];");
                StringBuilder sb143 = new StringBuilder();
                sb143.append(", drawbox=x=");
                sb143.append(i97);
                sb143.append(":y=");
                sb143.append(i97);
                sb143.append(":width=");
                sb143.append(((CollageData) Utils.collageData.get(3)).getWidth() + i96);
                sb143.append(":height=");
                sb143.append(((CollageData) Utils.collageData.get(3)).getHeight() + i96);
                sb143.append(":thickness=");
                sb143.append(strokeWidth);
                sb143.append(":color=");
                sb143.append(format);
                sb143.append("@");
                sb143.append(valueOf);
                sb143.append(" [four];");
                String[] strArr11 = {sb140.toString(), sb141.toString(), sb142.toString(), sb143.toString()};
                arrayList.add("-y");
                String str100 = str97;
                String str101 = str98;
                for (int i98 = 0; i98 < size; i98++) {
                    String str102 = "";
                    try {
                        if (((CollageData) Utils.collageData.get(i98)).getIsImage().booleanValue()) {
                            try {
                                arrayList.add("-loop");
                                arrayList.add("1");
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i98)).getVideoUrl());
                                StringBuilder sb144 = new StringBuilder(String.valueOf(str100));
                                sb144.append(" [");
                                sb144.append(i98);
                                sb144.append(":v] trim=duration=");
                                sb144.append(i34);
                                sb144.append(" [v");
                                int i99 = i98 + 1;
                                sb144.append(i99);
                                sb144.append("];");
                                str22 = sb144.toString();
                                try {
                                    StringBuilder sb145 = new StringBuilder();
                                    sb145.append(" [v");
                                    sb145.append(i99);
                                    sb145.append("] setpts=PTS-STARTPTS, scale=");
                                    sb145.append(((CollageData) Utils.collageData.get(i98)).getWidth());
                                    sb145.append("x");
                                    sb145.append(((CollageData) Utils.collageData.get(i98)).getHeight());
                                    str102 = sb145.toString();
                                } catch (Exception e143) {
                                    try {

                                        str100 = str22;
                                        StringBuilder sb146 = new StringBuilder(String.valueOf(str101));
                                        sb146.append(str102);
                                        sb146.append(strArr11[i98]);
                                        str101 = sb146.toString();
                                    } catch (Exception e144) {

                                        str100 = str22;
                                    }
                                }
                            } catch (Exception e145) {
                                str22 = str100;

                                str100 = str22;
                                StringBuilder sb1462 = new StringBuilder(String.valueOf(str101));
                                sb1462.append(str102);
                                sb1462.append(strArr11[i98]);
                                str101 = sb1462.toString();
                            }
                            str100 = str22;
                        } else {
                            try {
                                arrayList.add("-ss");
                                arrayList.add(((CollageData) Utils.collageData.get(i98)).getStartTime());
                                arrayList.add("-t");
                                arrayList.add(String.valueOf(i34));
                                arrayList.add("-i");
                                arrayList.add(((CollageData) Utils.collageData.get(i98)).getVideoUrl());
                                StringBuilder sb147 = new StringBuilder();
                                sb147.append(" [");
                                sb147.append(i98);
                                sb147.append(":v] setpts=PTS-STARTPTS,crop=w=");
                                sb147.append(((CollageData) Utils.collageData.get(i98)).getCrop_width());
                                sb147.append(":h=");
                                sb147.append(((CollageData) Utils.collageData.get(i98)).getCrop_height());
                                sb147.append(":x=");
                                sb147.append(((CollageData) Utils.collageData.get(i98)).getCrop_X());
                                sb147.append(":y=");
                                sb147.append(((CollageData) Utils.collageData.get(i98)).getCrop_Y());
                                sb147.append(", scale=");
                                sb147.append(((CollageData) Utils.collageData.get(i98)).getWidth());
                                sb147.append("x");
                                sb147.append(((CollageData) Utils.collageData.get(i98)).getHeight());
                                str102 = sb147.toString();
                            } catch (Exception e146) {
                                e146.printStackTrace();
                            }
                        }
                        StringBuilder sb14622 = new StringBuilder(String.valueOf(str101));
                        sb14622.append(str102);
                        sb14622.append(strArr11[i98]);
                        str101 = sb14622.toString();
                    } catch (Exception e147) {
                        str22 = str100;

                        str100 = str22;
                    }
                }
                if (c) {
                    try {
                        arrayList.add("-ss");
                        arrayList.add(String.valueOf(this.Rs / 1000));
                        arrayList.add("-i");
                        arrayList.add(d);
                        i29 = 5;
                    } catch (Exception e148) {
                        e148.printStackTrace();
                    }
                    StringBuilder sb148 = new StringBuilder();
                    sb148.append("nullsrc=size=");
                    sb148.append(sb2);
                    sb148.append(" [base];");
                    sb148.append(str100);
                    sb148.append(str101);
                    sb148.append(" [base][one] overlay=shortest=1 [tmp1]; [tmp1][two] overlay=shortest=1:x=");
                    sb148.append(((CollageData) Utils.collageData.get(1)).getXPoint());
                    sb148.append(" [tmp2]; [tmp2][three] overlay=shortest=1:y=");
                    sb148.append(((CollageData) Utils.collageData.get(2)).getYPoint());
                    sb148.append(" [tmp3]; [tmp3][four] overlay=shortest=1:x=");
                    sb148.append(((CollageData) Utils.collageData.get(3)).getXPoint());
                    sb148.append(":y=");
                    sb148.append(((CollageData) Utils.collageData.get(3)).getYPoint());
                    String sb149 = sb148.toString();
                    for (i30 = 0; i30 < this.S.size(); i30++) {
                        try {
                            StickerObj stickerObj21 = (StickerObj) this.S.get(i30);
                            arrayList.add("-i");
                            arrayList.add(stickerObj21.StickerPath);
                        } catch (Exception e149) {
                            e149.printStackTrace();
                        }
                    }
                    int i100 = i29;
                    String str103 = str99;
                    for (i31 = 0; i31 < this.S.size(); i31++) {
                        try {
                            StickerObj stickerObj22 = (StickerObj) this.S.get(i31);
                            StringBuilder sb150 = new StringBuilder(String.valueOf(str103));
                            sb150.append("[");
                            sb150.append(str26);
                            sb150.append("];");
                            sb150.append("[");
                            sb150.append(str26);
                            sb150.append("]");
                            sb150.append("[");
                            sb150.append(i100);
                            sb150.append(":v] overlay=x=");
                            sb150.append(stickerObj22.StickerX);
                            sb150.append(":y=");
                            sb150.append(stickerObj22.StickerY);
                            str21 = sb150.toString();
                            try {
                                StringBuilder sb151 = new StringBuilder(String.valueOf(str26));
                                sb151.append(i31);
                                i100++;
                                str26 = sb151.toString();
                            } catch (Exception e150) {

                                str103 = str21;
                            }
                        } catch (Exception e151) {
                            str21 = str103;

                            str103 = str21;
                        }
                        str103 = str21;
                    }
                    arrayList.add("-filter_complex");
                    StringBuilder sb152 = new StringBuilder();
                    sb152.append(sb149);
                    sb152.append(str103);
                    arrayList.add(sb152.toString());
                    if (!c) {
                        try {
                            arrayList.add("-map");
                            arrayList.add("4:a");
                        } catch (Exception e152) {
                            e152.printStackTrace();
                        }
                    } else {
                        int i101 = 0;
                        while (i101 < this.i.size()) {
                            try {
                                try {
                                    AudioTrackData audioTrackData11 = (AudioTrackData) this.i.get(i101);
                                    if (audioTrackData11.isSelected) {
                                        try {
                                            arrayList.add("-map");
                                            StringBuilder sb153 = new StringBuilder();
                                            sb153.append(audioTrackData11.mapPosition);
                                            sb153.append(":a");
                                            arrayList.add(sb153.toString());
                                        } catch (Exception e153) {
                                            e153.printStackTrace();
                                        }
                                    } else {
                                        i101++;
                                    }
                                } catch (Exception e154) {
                                    e154.printStackTrace();
                                }
                            } catch (Exception e155) {
                                e155.printStackTrace();
                            }
                        }
                    }
                    arrayList.add("-c:a");
                    arrayList.add("copy");
                    arrayList.add("-ab");
                    arrayList.add("128k");
                    arrayList.add("-c:v");
                    arrayList.add("mpeg4");
                    arrayList.add("-r");
                    arrayList.add("15");
                    arrayList.add("-b:v");
                    arrayList.add("2500k");
                    arrayList.add("-sample_fmt");
                    arrayList.add("s16");
                    arrayList.add("-strict");
                    arrayList.add("experimental");
                    arrayList.add("-ss");
                    arrayList.add("0");
                    arrayList.add("-t");
                    arrayList.add(String.valueOf(i34));
                    arrayList.add(this.W);
                    a(arrayList, this.W);
                    break;
                }
                i29 = 4;
                StringBuilder sb1482 = new StringBuilder();
                sb1482.append("nullsrc=size=");
                sb1482.append(sb2);
                sb1482.append(" [base];");
                sb1482.append(str100);
                sb1482.append(str101);
                sb1482.append(" [base][one] overlay=shortest=1 [tmp1]; [tmp1][two] overlay=shortest=1:x=");
                sb1482.append(((CollageData) Utils.collageData.get(1)).getXPoint());
                sb1482.append(" [tmp2]; [tmp2][three] overlay=shortest=1:y=");
                sb1482.append(((CollageData) Utils.collageData.get(2)).getYPoint());
                sb1482.append(" [tmp3]; [tmp3][four] overlay=shortest=1:x=");
                sb1482.append(((CollageData) Utils.collageData.get(3)).getXPoint());
                sb1482.append(":y=");
                sb1482.append(((CollageData) Utils.collageData.get(3)).getYPoint());
                String sb1492 = sb1482.toString();
                int i1002 = i29;
                String str1032 = str99;
                arrayList.add("-filter_complex");
                StringBuilder sb1522 = new StringBuilder();
                sb1522.append(sb1492);
                sb1522.append(str1032);
                arrayList.add(sb1522.toString());
                if (!c) {
                }
                arrayList.add("-c:a");
                arrayList.add("copy");
                arrayList.add("-ab");
                arrayList.add("128k");
                arrayList.add("-c:v");
                arrayList.add("mpeg4");
                arrayList.add("-r");
                arrayList.add("15");
                arrayList.add("-b:v");
                arrayList.add("2500k");
                arrayList.add("-sample_fmt");
                arrayList.add("s16");
                arrayList.add("-strict");
                arrayList.add("experimental");
                arrayList.add("-ss");
                arrayList.add("0");
                arrayList.add("-t");
                arrayList.add(String.valueOf(i34));
                arrayList.add(this.W);
                a(arrayList, this.W);
        }
    }

    private void a(ArrayList<String> arrayList, final String str) {
        String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
        for (String println : strArr) {
            System.out.println(println);
        }
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
                    MediaScannerConnection.scanFile(VideoCollageMakerActivity.this.I, new String[]{VideoCollageMakerActivity.this.W}, new String[]{"mkv"}, null);
                    VideoCollageMakerActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(VideoCollageMakerActivity.this.W))));
                    VideoCollageMakerActivity.this.y.postDelayed(VideoCollageMakerActivity.this.ah, 1000);
                    VideoCollageMakerActivity.this.refreshGallery(str);

                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoCollageMakerActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoCollageMakerActivity.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }

                } else {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        VideoCollageMakerActivity.this.deleteFromGallery(str);
                        Toast.makeText(VideoCollageMakerActivity.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }

                }


            }
        });

        getWindow().clearFlags(16);
    }


    public void a() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.app_folder_name2));
        File file = new File(sb.toString());
        if (!file.exists()) {
            try {
                file.mkdir();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb2.append("/");
        sb2.append(getResources().getString(R.string.app_folder_name2));
        sb2.append("/TMPIMG");
        String sb3 = sb2.toString();
        File file2 = new File(sb3);
        if (!file2.exists()) {
            file2.mkdir();
        }
        try {
            StringBuilder sb4 = new StringBuilder(String.valueOf(sb3));
            sb4.append("/.nomedia");
            File file3 = new File(sb4.toString());
            if (!file3.exists()) {
                file3.createNewFile();
            }
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    public void onWindowFocusChanged(boolean z2) {
        super.onWindowFocusChanged(z2);
    }

    public static int convertDpToPixel(int i2, Context context) {
        return (int) (((float) i2) * (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f));
    }

    private void e() {
        this.B = (FrameLayout) findViewById(R.id.llContainer);
        this.N = (RelativeLayout) findViewById(R.id.rlSelctedMusic);
        this.Q = (SeekBar) findViewById(R.id.sbAudio);
        this.Q.setOnSeekBarChangeListener(this);
        this.T = (TextView) findViewById(R.id.tvStartAudio);
        this.U = (TextView) findViewById(R.id.tvEndAudio);
        this.V = (TextView) findViewById(R.id.tvAudioName);
        this.l = (Button) findViewById(R.id.btnPlayAudio);
        this.l.setOnClickListener(this.ad);
        this.q = (RelativeLayout) findViewById(R.id.btnClearAudio);
        this.q.setOnClickListener(this.ag);
        this.D = (LinearLayout) findViewById(R.id.ll_btnOpacity);
        this.G = (LinearLayout) findViewById(R.id.ll_btnBorder);
        this.E = (LinearLayout) findViewById(R.id.ll_btnMusic);
        this.F = (LinearLayout) findViewById(R.id.ll_btnColor);
        this.C = (LinearLayout) findViewById(R.id.ll_btnSticker);
        this.k = (Button) findViewById(R.id.btnSticker);
        this.k.setOnClickListener(this.ae);
        this.p = (Button) findViewById(R.id.btnBorder);
        this.p.setOnClickListener(this.Y);
        this.m = (Button) findViewById(R.id.btnOpacity);
        this.m.setOnClickListener(this.ac);
        this.o = (Button) findViewById(R.id.btnColor);
        this.o.setOnClickListener(this.aa);
        this.n = (Button) findViewById(R.id.btnMusic);
        this.n.setOnClickListener(this.af);
        this.P = (SeekBar) findViewById(R.id.seekBorderWidth);
        this.P.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                int size = Utils.borderlayout.size();
                for (int i2 = 0; i2 < size; i2++) {
                    try {
                        ((BorderFrameLayout) Utils.borderlayout.get(i2)).setStrokeWidth(i);
                    } catch (Exception e) {

                    }
                }
            }
        });
        this.O = (SeekBar) findViewById(R.id.seekOpacity);
        this.O.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                int size = Utils.borderlayout.size();
                for (int i2 = 0; i2 < size; i2++) {
                    try {
                        ((BorderFrameLayout) Utils.borderlayout.get(i2)).setColorAlpha(i);
                    } catch (Exception e) {

                    }
                }
            }
        });
    }


    public void f() {
        this.n.setBackgroundResource(R.drawable.music1);
        this.o.setBackgroundResource(R.drawable.color);
        this.k.setBackgroundResource(R.drawable.sticker);
        this.D.setVisibility(8);
        this.G.setVisibility(8);
        this.E.setVisibility(8);
        this.F.setVisibility(8);
        this.C.setVisibility(8);
    }


    public void g() {
        this.O.setVisibility(8);
        this.N.setVisibility(8);
        this.P.setVisibility(8);
    }

    private void h() {
        StringBuilder sb = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()));
        sb.append("/");
        sb.append(getResources().getString(R.string.app_folder_name2));
        sb.append("/TMPIMG/");
        File file = new File(sb.toString());
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        for (int i2 = 0; i2 < Utils.clgstickerviewsList.size(); i2++) {
            try {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("sticker");
                sb2.append(i2);
                sb2.append(".png");
                File file2 = new File(file, sb2.toString());
                if (file2.exists()) {
                    try {
                        file2.delete();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                ClgSingleFingerView clgSingleFingerView = ((StickerData) Utils.clgstickerviewsList.get(i2)).singlefview;
                int imageX = (int) clgSingleFingerView.getImageX();
                int imageY = (int) clgSingleFingerView.getImageY();
                int imageWidth = clgSingleFingerView.getImageWidth();
                int imageHeight = clgSingleFingerView.getImageHeight();
                int rotation = (int) clgSingleFingerView.getRotation();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) clgSingleFingerView.getBitmapDrawable();
                if (bitmapDrawable.getBitmap() != null) {
                    try {
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        Matrix matrix = new Matrix();
                        matrix.postRotate((float) rotation);
                        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, aj);
                        Bitmap createBitmap = Bitmap.createBitmap(createScaledBitmap, 0, 0, createScaledBitmap.getWidth(), createScaledBitmap.getHeight(), matrix, aj);
                        if (createScaledBitmap != null) {
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                                createBitmap.compress(CompressFormat.PNG, 90, fileOutputStream);
                                fileOutputStream.flush();
                                fileOutputStream.close();
                                try {
                                    this.S.add(new StickerObj(file2.getPath(), imageX, imageY));
                                } catch (Exception unused) {
                                }
                            } catch (Exception unused2) {
                            }
                        }
                    } catch (Exception e4) {
                        try {
                            e4.printStackTrace();
                        } catch (Exception e5) {
                        }
                    }
                }
            } catch (Exception e6) {

            }
        }
    }

    public String loadJSONFromAsset() {
        try {
            InputStream open = getAssets().open("collageframe.json");
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new String(bArr, Key.STRING_CHARSET_NAME);
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }


    public void a(int i2) {
        List<FrameRCInfo> list;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        List<FrameRCInfo> list2;
        Exception exc;
        List<FrameRCInfo> list3;
        VideoCollageMakerActivity videoCollageMakerActivity;
        CollageData collageData = new CollageData();
        Exception exc2;
        Exception exc3;
        VideoCollageMakerActivity videoCollageMakerActivity2 = this;
        int i10 = i2;
        if (Utils.borderlayout.size() > 0) {
            try {
                Utils.borderlayout.clear();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        String str = ((FrameInfo) videoCollageMakerActivity2.u.imgInfo.get(i10)).frameType;
        int i11 = ((FrameInfo) videoCollageMakerActivity2.u.imgInfo.get(i10)).noofRC;
        List<FrameRCInfo> list4 = ((FrameInfo) videoCollageMakerActivity2.u.imgInfo.get(i10)).rcValues;
        if (str.equals("r")) {
            try {
                int i12 = videoCollageMakerActivity2.x / i11;
                int i13 = videoCollageMakerActivity2.v / i11;
                int i14 = 0;
                int i15 = 0;
                i6 = 0;
                i5 = 0;
                i4 = 0;
                i3 = 0;
                while (i14 < i11) {
                    try {
                        int i16 = ((FrameRCInfo) list4.get(i14)).numRow;
                        int i17 = videoCollageMakerActivity2.w / i16;
                        int i18 = videoCollageMakerActivity2.v / i16;
                        int i19 = i4;
                        int i20 = i6;
                        int i21 = i15;
                        int i22 = 0;
                        while (i22 < i16) {
                            try {
                                list = list4;
                                try {
                                    LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                                    linearLayout.setLayoutParams(new LayoutParams(i17, i12));
                                    linearLayout.addView(videoCollageMakerActivity2.a(linearLayout, i21));
                                    linearLayout.setX((float) i20);
                                    linearLayout.setY((float) i5);
                                    videoCollageMakerActivity2.f.addView(linearLayout);
                                    CollageData collageData2 = new CollageData();
                                    collageData2.setWidth(i18);
                                    collageData2.setHeight(i13);
                                    collageData2.setXPoint(i19 + 1);
                                    collageData2.setYPoint(i3 + 1);
                                    collageData2.setIsImage(Boolean.valueOf(false));
                                    Utils.collageData.add(collageData2);
                                    i21++;
                                    i20 += i17;
                                    i19 += i18;
                                    BorderAttribute borderAttribute = new BorderAttribute();
                                    if (i22 == 0 && i22 < i16 - 1) {
                                        try {
                                            borderAttribute.setBorderRight(aj);
                                        } catch (Exception e3) {
                                            e3.printStackTrace();
                                        }
                                    }
                                    if (i22 > 0 && i22 < i16 - 1) {
                                        try {
                                            borderAttribute.setBorderLeft(aj);
                                            borderAttribute.setBorderRight(aj);
                                        } catch (Exception e4) {
                                            e4.printStackTrace();
                                        }
                                    }
                                    if (i22 > 0 && i22 == i16 - 1) {
                                        try {
                                            borderAttribute.setBorderLeft(aj);
                                        } catch (Exception e5) {
                                            e5.printStackTrace();
                                        }
                                    }
                                    if (i14 == 0 && i14 < i11 - 1) {
                                        try {
                                            borderAttribute.setBorderBottom(aj);
                                        } catch (Exception e6) {
                                            e6.printStackTrace();
                                        }
                                    }
                                    if (i14 > 0 && i14 < i11 - 1) {
                                        try {
                                            borderAttribute.setBorderTop(aj);
                                            borderAttribute.setBorderBottom(aj);
                                        } catch (Exception e7) {
                                            e7.printStackTrace();
                                        }
                                    }
                                    if (i14 > 0 && i14 == i11 - 1) {
                                        try {
                                            borderAttribute.setBorderTop(aj);
                                        } catch (Exception e8) {
                                            e8.printStackTrace();
                                        }
                                    }
                                    Utils.borderParam.add(borderAttribute);
                                    i22++;
                                } catch (Exception e9) {
                                    try {

                                        list4 = list;
                                        videoCollageMakerActivity2 = this;
                                    } catch (Exception e10) {
                                        exc3 = e10;
                                        i15 = i21;
                                        i6 = i20;
                                        i4 = i19;
                                    }
                                }
                            } catch (Exception e11) {
                                list = list4;

                                list4 = list;
                                videoCollageMakerActivity2 = this;
                            }
                            list4 = list;
                            videoCollageMakerActivity2 = this;
                        }
                        List<FrameRCInfo> list5 = list4;
                        i5 += i12;
                        i3 += i13;
                        i14++;
                        i15 = i21;
                        videoCollageMakerActivity2 = this;
                        i6 = 0;
                        i4 = 0;
                    } catch (Exception e12) {
                        list = list4;
                        exc3 = e12;
                        try {
                            exc3.printStackTrace();
                            list4 = list;
                            videoCollageMakerActivity2 = this;
                        } catch (Exception e13) {
                            exc2 = e13;
                            i7 = i15;
                            exc2.printStackTrace();
                            videoCollageMakerActivity2 = this;
                            int i23 = videoCollageMakerActivity2.w / i11;
                            int i24 = videoCollageMakerActivity2.v / i11;
                            int i25 = i7;
                            i8 = 0;
                            while (i8 < i11) {
                            }
                        }
                    }
                }
            } catch (Exception e14) {
                list = list4;
                exc2 = e14;
                i7 = 0;
                i6 = 0;
                i5 = 0;
                i4 = 0;
                i3 = 0;
                exc2.printStackTrace();
                videoCollageMakerActivity2 = this;
                int i232 = videoCollageMakerActivity2.w / i11;
                int i242 = videoCollageMakerActivity2.v / i11;
                int i252 = i7;
                i8 = 0;
                while (i8 < i11) {
                }
            }
        } else {
            list = list4;
            i7 = 0;
            i6 = 0;
            i5 = 0;
            i4 = 0;
            i3 = 0;
            int i2322 = videoCollageMakerActivity2.w / i11;
            int i2422 = videoCollageMakerActivity2.v / i11;
            int i2522 = i7;
            i8 = 0;
            while (i8 < i11) {
                List<FrameRCInfo> list6 = list;
                try {
                    int i26 = ((FrameRCInfo) list6.get(i8)).numRow;
                    int i27 = videoCollageMakerActivity2.x / i26;
                    int i28 = videoCollageMakerActivity2.v / i26;
                    int i29 = i3;
                    int i30 = i5;
                    int i31 = i2522;
                    int i32 = 0;
                    while (i32 < i26) {
                        list2 = list6;
                        try {
                            i9 = i11;
                            try {
                                LinearLayout linearLayout2 = new LinearLayout(getApplicationContext());
                                linearLayout2.setLayoutParams(new LayoutParams(i2322, i27));
                                linearLayout2.addView(videoCollageMakerActivity2.a(linearLayout2, i31));
                                linearLayout2.setX((float) i6);
                                linearLayout2.setY((float) i30);
                                videoCollageMakerActivity2.f.addView(linearLayout2);
                                collageData.setWidth(i2422);
                                collageData.setHeight(i28);
                                collageData.setXPoint(i4 + 1);
                                collageData.setYPoint(i29 + 1);
                            } catch (Exception e15) {
                                try {

                                    list6 = list2;
                                    i11 = i9;
                                    videoCollageMakerActivity2 = this;
                                } catch (Exception e16) {
                                    exc = e16;
                                    i2522 = i31;
                                    i5 = i30;
                                    i3 = i29;
                                }
                            }
                            try {
                                collageData.setIsImage(Boolean.valueOf(false));
                                Utils.collageData.add(collageData);
                                i31++;
                                BorderAttribute borderAttribute2 = new BorderAttribute();
                                if (i32 == 0 && i32 < i26 - 1) {
                                    try {
                                        borderAttribute2.setBorderBottom(aj);
                                    } catch (Exception e17) {
                                        e17.printStackTrace();
                                    }
                                }
                                if (i32 > 0 && i32 < i26 - 1) {
                                    try {
                                        borderAttribute2.setBorderTop(aj);
                                        borderAttribute2.setBorderBottom(aj);
                                    } catch (Exception e18) {
                                        e18.printStackTrace();
                                    }
                                }
                                if (i32 > 0 && i32 == i26 - 1) {
                                    try {
                                        borderAttribute2.setBorderTop(aj);
                                    } catch (Exception e19) {
                                        e19.printStackTrace();
                                    }
                                }
                                if (i8 == 0 && i8 < i9 - 1) {
                                    try {
                                        borderAttribute2.setBorderRight(aj);
                                    } catch (Exception e20) {
                                        e20.printStackTrace();
                                    }
                                }
                                if (i8 > 0 && i8 < i9 - 1) {
                                    try {
                                        borderAttribute2.setBorderLeft(aj);
                                        borderAttribute2.setBorderRight(aj);
                                    } catch (Exception e21) {
                                        e21.printStackTrace();
                                    }
                                }
                                if (i8 > 0 && i8 == i9 - 1) {
                                    try {
                                        borderAttribute2.setBorderLeft(aj);
                                    } catch (Exception e22) {
                                        e22.printStackTrace();
                                    }
                                }
                                Utils.borderParam.add(borderAttribute2);
                                i30 += i27;
                                i29 += i28;
                                i32++;
                            } catch (Exception e23) {

                                list6 = list2;
                                i11 = i9;
                                videoCollageMakerActivity2 = this;
                            }
                        } catch (Exception e24) {
                            i9 = i11;

                            list6 = list2;
                            i11 = i9;
                            videoCollageMakerActivity2 = this;
                        }
                        list6 = list2;
                        i11 = i9;
                        videoCollageMakerActivity2 = this;
                    }
                    int i33 = i11;
                    i6 += i2322;
                    i4 += i2422;
                    i8++;
                    i3 = 0;
                    i2522 = i31;
                    list3 = list6;
                    videoCollageMakerActivity = this;
                    i5 = 0;
                } catch (Exception e25) {
                    i9 = i11;
                    list2 = list6;
                    exc = e25;
                    exc.printStackTrace();
                    list3 = list2;
                    i11 = i9;
                    videoCollageMakerActivity = this;
                }
            }
        }
    }

    private View a(LinearLayout linearLayout, int i2) {
        View inflate = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.frame_container_layout, linearLayout, false);
        BorderFrameLayout borderFrameLayout = (BorderFrameLayout) inflate.findViewById(R.id.flBorder);
        borderFrameLayout.setTag(Integer.valueOf(i2));
        borderFrameLayout.setStrokeWidth(5);
        Utils.borderlayout.add(borderFrameLayout);
        a.add((FrameLayout) inflate.findViewById(R.id.flImageDraw));
        Button button = (Button) inflate.findViewById(R.id.btnAdd);
        Button button2 = (Button) inflate.findViewById(R.id.btnEdit);
        Button button3 = (Button) inflate.findViewById(R.id.btnClear);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.ivThumbImage);
        imageView.setTag(Integer.valueOf(i2));
        button.setTag(Integer.valueOf(i2));
        button2.setTag(Integer.valueOf(i2));
        button3.setTag(Integer.valueOf(i2));
        this.A.add(imageView);
        this.t.add(button);
        this.r.add(button2);
        this.s.add(button3);
        button.setOnClickListener(this.X);
        button2.setOnClickListener(this.ab);
        button3.setOnClickListener(this.Z);
        return inflate;
    }

    @SuppressLint({"DefaultLocale"})
    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2)))});
    }


    public void b(int i2) {
        Intent intent = new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*");
        startActivityForResult(intent, 10);
    }


    public void c(int i2) {
        Intent intent = new Intent("android.intent.action.PICK", Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 10);
    }


    public void i() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = getLayoutInflater().inflate(R.layout.dialogue_music_layout, null);
        builder.setView(inflate);
        this.h = builder.create();
        builder.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.cancel();
            }
        });
        this.H = (ListView) inflate.findViewById(R.id.lvAudioTrack);
        this.H.setDivider(null);
        Collections.sort(this.i, new Comparator<AudioTrackData>() {

            public int compare(AudioTrackData audioTrackData, AudioTrackData audioTrackData2) {
                return Integer.valueOf(audioTrackData.mapPosition).compareTo(Integer.valueOf(audioTrackData2.mapPosition));
            }
        });
        this.g = new a(this.I);
        this.H.setAdapter(this.g);
        ((ImageView) inflate.findViewById(R.id.imgbtn_close)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoCollageMakerActivity.this.h.dismiss();
            }
        });
        ((RelativeLayout) inflate.findViewById(R.id.btn_addmusic)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VideoCollageMakerActivity.this.g != null) {
                    VideoCollageMakerActivity.this.g.notifyDataSetChanged();
                }
                VideoCollageMakerActivity.this.h.dismiss();
                VideoCollageMakerActivity.this.startActivityForResult(new Intent(VideoCollageMakerActivity.this, SelectMusicActivity.class), 12);
            }
        });
        this.h.show();
        this.h.setCancelable(aj);
    }

    public Bitmap SearchVideoBitmap(Context context, String str) {
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
        if (count <= 0) {
            return null;
        }
        managedQuery.moveToFirst();
        return Thumbnails.getThumbnail(getContentResolver(), Long.valueOf(managedQuery.getLong(managedQuery.getColumnIndexOrThrow("_id"))).longValue(), 1, null);
    }


    @Override
    public void onActivityResult(int i2, int i3, Intent intent) {
        int i4 = i2;
        int i5 = i3;
        Intent intent2 = intent;
        super.onActivityResult(i2, i3, intent);
        if (i4 == 4 && i5 == -1) {
            try {
                FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.raw_image_sticker, this.B);
                ClgSingleFingerView clgSingleFingerView = (ClgSingleFingerView) frameLayout.getChildAt(frameLayout.getChildCount() - 1);
                clgSingleFingerView.setTag(new ClgTagView(Utils.clgstickerviewsList.size(), ""));
                Utils.clgstickerviewsList.add(new StickerData(clgSingleFingerView, Utils.clgstickerviewsList.size()));
                Utils.selectedPos = Utils.clgstickerviewsList.size() - 1;
                if (i4 == 4) {
                    try {
                        clgSingleFingerView.setDrawable(new BitmapDrawable(SelectStickerActivity.bmp));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    try {
                        SelectStickerActivity.bmp = null;
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                for (int i6 = 0; i6 < Utils.clgstickerviewsList.size(); i6++) {
                    try {
                        ((StickerData) Utils.clgstickerviewsList.get(i6)).singlefview.hidePushView();
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
                clgSingleFingerView.showPushView();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        } else if (i4 == 10) {
            if (i5 == -1) {
                try {
                    Uri data = intent.getData();
                    if (getMimeType(data).booleanValue()) {
                        Cursor query = getContentResolver().query(data, new String[]{"_data"}, null, null, null);
                        query.moveToFirst();
                        String string = query.getString(query.getColumnIndex("_data"));
                        CollageData collageData = new CollageData();
                        collageData.setWidth(((CollageData) Utils.collageData.get(b)).getWidth());
                        collageData.setHeight(((CollageData) Utils.collageData.get(b)).getHeight());
                        collageData.setXPoint(((CollageData) Utils.collageData.get(b)).getXPoint());
                        collageData.setYPoint(((CollageData) Utils.collageData.get(b)).getYPoint());
                        collageData.setVideoUrl(string);
                        collageData.setIsImage(Boolean.valueOf(aj));
                        Utils.collageData.remove(b);
                        Utils.collageData.add(b, collageData);
                        ((ImageView) this.A.get(b)).setImageURI(data);
                        ((ImageView) this.A.get(b)).setScaleType(ScaleType.MATRIX);
                        ((ImageView) this.A.get(b)).setOnTouchListener(new ImageViewTouchListener(this.I));
                        ((Button) this.t.get(b)).setVisibility(8);
                        ((Button) this.r.get(b)).setVisibility(0);
                        ((Button) this.s.get(b)).setVisibility(0);
                        return;
                    }
                    Log.i("Media type", String.valueOf(getMimeType(data)));
                    Cursor query2 = getContentResolver().query(data, new String[]{"_data"}, null, null, null);
                    query2.moveToFirst();
                    String string2 = query2.getString(query2.getColumnIndex("_data"));
                    CollageData collageData2 = new CollageData();
                    collageData2.setWidth(((CollageData) Utils.collageData.get(b)).getWidth());
                    collageData2.setHeight(((CollageData) Utils.collageData.get(b)).getHeight());
                    collageData2.setXPoint(((CollageData) Utils.collageData.get(b)).getXPoint());
                    collageData2.setYPoint(((CollageData) Utils.collageData.get(b)).getYPoint());
                    collageData2.setIsImage(Boolean.valueOf(false));
                    collageData2.setVideoUrl(string2);
                    Utils.collageData.remove(b);
                    Utils.collageData.add(b, collageData2);
                    ((ImageView) this.A.get(b)).setImageBitmap(ThumbnailUtils.createVideoThumbnail(string2, 1));
                    ((Button) this.t.get(b)).setVisibility(8);
                    ((Button) this.r.get(b)).setVisibility(0);
                    ((Button) this.s.get(b)).setVisibility(0);
                    int height = this.v / ((CollageData) Utils.collageData.get(b)).getHeight();
                    int width = this.v / ((CollageData) Utils.collageData.get(b)).getWidth();
                    Intent intent3 = new Intent(this, VideoCropAndCutActivity.class);
                    intent3.putExtra("videopath", string2);
                    intent3.putExtra("frmpos", b);
                    intent3.putExtra("ratio_x", height);
                    intent3.putExtra("ratio_y", width);
                    startActivityForResult(intent3, 11);
                    return;
                } catch (Exception e6) {
                    e6.printStackTrace();
                }
            }
            ((Button) this.t.get(b)).setVisibility(0);
            ((Button) this.r.get(b)).setVisibility(8);
            ((Button) this.s.get(b)).setVisibility(8);
            ((CollageData) Utils.collageData.get(b)).setVideoUrl(null);
            ((ImageView) this.A.get(b)).setImageBitmap(null);
            ((ImageView) this.A.get(b)).setBackgroundColor(Color.parseColor("#e8d9c8"));
        } else if (i4 == 11) {
            if (i5 == -1) {
                try {
                    b = intent2.getIntExtra("frmpos", 0);
                    String videoUrl = ((CollageData) Utils.collageData.get(b)).getVideoUrl();
                    if (((CollageData) Utils.collageData.get(b)).getIsImage().booleanValue()) {
                        try {
                            ImageView imageView = (ImageView) this.A.get(b);
                            StringBuilder sb = new StringBuilder();
                            sb.append("file://");
                            sb.append(videoUrl);
                            imageView.setImageURI(Uri.parse(sb.toString()));
                        } catch (Exception e7) {
                            e7.printStackTrace();
                        }
                    } else {
                        try {
                            this.i.add(new AudioTrackData(b, false));
                            Bitmap SearchVideoBitmap = SearchVideoBitmap(this, videoUrl);
                            CollageData collageData3 = (CollageData) Utils.collageData.get(b);
                            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                            mediaMetadataRetriever.setDataSource(videoUrl);
                            int intValue = Integer.valueOf(mediaMetadataRetriever.extractMetadata(18)).intValue();
                            int intValue2 = Integer.valueOf(mediaMetadataRetriever.extractMetadata(19)).intValue();
                            mediaMetadataRetriever.release();
                            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(SearchVideoBitmap, intValue, intValue2, false);
                            ((ImageView) this.A.get(b)).setImageBitmap(Bitmap.createBitmap(createScaledBitmap, collageData3.getCrop_X(), collageData3.getCrop_Y(), collageData3.getCrop_width(), collageData3.getCrop_height()));
                            collageData3.setCrop_height(intValue2);
                            collageData3.setCrop_width(intValue);
                            collageData3.setCrop_X(0);
                            collageData3.setCrop_Y(0);
                            ((ImageView) this.A.get(b)).setImageBitmap(createScaledBitmap);
                        } catch (IllegalArgumentException e8) {
                            e8.printStackTrace();
                        }
                    }
                    ((Button) this.t.get(b)).setVisibility(8);
                    ((Button) this.r.get(b)).setVisibility(0);
                    ((Button) this.s.get(b)).setVisibility(0);
                    return;
                } catch (IllegalArgumentException e9) {
                    try {
                        e9.printStackTrace();
                    } catch (IllegalArgumentException e10) {
                        e10.printStackTrace();
                    }
                }
            }
            ((Button) this.t.get(b)).setVisibility(0);
            ((Button) this.r.get(b)).setVisibility(8);
            ((Button) this.s.get(b)).setVisibility(8);
            ((CollageData) Utils.collageData.get(b)).setVideoUrl(null);
            ((ImageView) this.A.get(b)).setImageBitmap(null);
            ((ImageView) this.A.get(b)).setBackgroundColor(Color.parseColor("#e8d9c8"));
        } else if (i4 == 13) {
            if (i5 == -1) {
                try {
                    b = intent2.getIntExtra("frmpos", 0);
                    String stringExtra = intent2.getStringExtra("videopath");
                    ((CollageData) Utils.collageData.get(b)).setVideoUrl(stringExtra);
                    Bitmap SearchVideoBitmap2 = SearchVideoBitmap(this, stringExtra);
                    CollageData collageData4 = (CollageData) Utils.collageData.get(b);
                    MediaMetadataRetriever mediaMetadataRetriever2 = new MediaMetadataRetriever();
                    mediaMetadataRetriever2.setDataSource(stringExtra);
                    int intValue3 = Integer.valueOf(mediaMetadataRetriever2.extractMetadata(18)).intValue();
                    int intValue4 = Integer.valueOf(mediaMetadataRetriever2.extractMetadata(19)).intValue();
                    mediaMetadataRetriever2.release();
                    Bitmap createScaledBitmap2 = Bitmap.createScaledBitmap(SearchVideoBitmap2, intValue3, intValue4, false);
                    ((ImageView) this.A.get(b)).setImageBitmap(Bitmap.createBitmap(createScaledBitmap2, collageData4.getCrop_X(), collageData4.getCrop_Y(), collageData4.getCrop_width(), collageData4.getCrop_height()));
                    ((ImageView) this.A.get(b)).setImageBitmap(createScaledBitmap2);
                    ((Button) this.t.get(b)).setVisibility(8);
                    ((Button) this.r.get(b)).setVisibility(0);
                    ((Button) this.s.get(b)).setVisibility(0);
                } catch (IllegalArgumentException e11) {
                    e11.printStackTrace();
                }
            }
        } else if (i4 == 14) {
            if (i5 == -1) {
                try {
                    b = intent2.getIntExtra("frmpos", 0);
                    String replace = intent2.getStringExtra("muri").replace("file://", "");
                    ((CollageData) Utils.collageData.get(b)).setVideoUrl(replace);
                    ((ImageView) this.A.get(b)).setImageBitmap(null);
                    ((ImageView) this.A.get(b)).setScaleType(ScaleType.MATRIX);
                    ImageView imageView2 = (ImageView) this.A.get(b);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("file://");
                    sb2.append(replace);
                    imageView2.setImageURI(Uri.parse(sb2.toString()));
                    ((Button) this.t.get(b)).setVisibility(8);
                    ((Button) this.r.get(b)).setVisibility(0);
                    ((Button) this.s.get(b)).setVisibility(0);
                } catch (Exception e12) {
                    try {
                        e12.printStackTrace();
                    } catch (IndexOutOfBoundsException e13) {
                        e13.printStackTrace();
                    }
                }
            }
        } else if (i4 == 15) {
            if (i5 == -1) {
                try {
                    b = intent2.getIntExtra("frmpos", 0);
                    String replace2 = intent2.getStringExtra("outputresult").replace("file://", "");
                    ((CollageData) Utils.collageData.get(b)).setVideoUrl(replace2);
                    ((ImageView) this.A.get(b)).setImageBitmap(null);
                    ImageView imageView3 = (ImageView) this.A.get(b);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("file://");
                    sb3.append(replace2);
                    imageView3.setImageURI(Uri.parse(sb3.toString()));
                    ((Button) this.t.get(b)).setVisibility(8);
                    ((Button) this.r.get(b)).setVisibility(0);
                    ((Button) this.s.get(b)).setVisibility(0);
                } catch (Exception e14) {
                    e14.printStackTrace();
                }
            }
        } else if (i4 == 12) {
            if (i5 == -1) {
                d = intent.getData().toString();
                c = aj;
                Log.i("Media Url", d);
                this.J = new MediaPlayer();
                try {
                    this.J.setDataSource(d);
                    this.J.setAudioStreamType(3);
                    this.J.prepare();
                } catch (IllegalArgumentException e15) {
                    e15.printStackTrace();
                } catch (SecurityException e16) {
                    e16.printStackTrace();
                } catch (IllegalStateException e17) {
                    e17.printStackTrace();
                } catch (IOException e18) {
                    e18.printStackTrace();
                }
                this.V.setText(intent2.getStringExtra("audioname"));
                this.U.setText(formatTimeUnit((long) this.J.getDuration()));
                this.Q.setProgress(0);
                this.Q.setMax(this.J.getDuration());
                this.N.setVisibility(0);
                this.J.setOnCompletionListener(new OnCompletionListener() {
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        VideoCollageMakerActivity.this.J.seekTo(VideoCollageMakerActivity.this.Rs);
                        VideoCollageMakerActivity.this.l.setBackgroundResource(R.drawable.play2);
                    }
                });
                this.j = 99;
                return;
            }
            this.N.setVisibility(8);
            d = "";
            c = false;
            this.J = null;
            this.j = 99;
        }
    }


    public void j() {
        File[] listFiles;
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.app_folder_name2));
        sb.append("/TMPIMG");
        File file = new File(sb.toString());
        if (file.exists()) {
            try {
                for (File file2 : file.listFiles()) {
                    try {
                        if (file2.exists()) {
                            file2.delete();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }


    public String a(Bitmap bitmap) {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.app_folder_name2));
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdir();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("COLLIMG_");
        sb2.append(System.currentTimeMillis());
        sb2.append(".jpg");
        File file2 = new File(file, sb2.toString());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                bitmap.compress(CompressFormat.JPEG, 70, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                MediaScannerConnection.scanFile(this, new String[]{file2.getAbsolutePath()}, new String[]{"image/jpeg"}, null);
                addImageToGallery(file2.getAbsolutePath(), getApplicationContext());
                return file2.getAbsolutePath();
            } catch (Exception e2) {
                e2.printStackTrace();
                MediaScannerConnection.scanFile(this, new String[]{file2.getAbsolutePath()}, new String[]{"image/jpeg"}, null);
                addImageToGallery(file2.getAbsolutePath(), getApplicationContext());
                return file2.getAbsolutePath();
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            MediaScannerConnection.scanFile(this, new String[]{file2.getAbsolutePath()}, new String[]{"image/jpeg"}, null);
            addImageToGallery(file2.getAbsolutePath(), getApplicationContext());
            return file2.getAbsolutePath();
        }
    }

    public static void addImageToGallery(String str, Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        contentValues.put("mime_type", "image/jpeg");
        contentValues.put("_data", str);
        context.getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    public Boolean getMimeType(Uri uri) {
        if (getContentResolver().getType(uri).matches("image/.*")) {
            return Boolean.valueOf(aj);
        }
        return Boolean.valueOf(false);
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        if (this.J.isPlaying()) {
            this.J.pause();
        }
        this.l.setBackgroundResource(R.drawable.play2);
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        this.Rs = seekBar.getProgress();
        this.J.seekTo(this.Rs);
        this.T.setText(formatTimeUnit((long) this.Rs));
    }


    public void k() {
        new AmbilWarnaDialog(this.I, -16776961, new OnAmbilWarnaListener() {
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
            }

            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                int size = Utils.borderlayout.size();
                for (int i2 = 0; i2 < size; i2++) {
                    try {
                        ((BorderFrameLayout) Utils.borderlayout.get(i2)).setColor(i);
                    } catch (Exception e) {

                    }
                }
            }
        }).show();
    }

    public void m() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VideoCollageMakerActivity.this.finish();
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


    @Override
    public void onResume() {
        super.onResume();
    }


    public void onPause() {
        super.onPause();
        if (this.J != null && this.J.isPlaying()) {
            try {
                this.J.pause();
                this.l.setBackgroundResource(R.drawable.play2);
            } catch (IllegalStateException e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (this.J != null && this.J.isPlaying()) {
            try {
                this.J.pause();
            } catch (IllegalStateException e2) {
                e2.printStackTrace();
            }
        }
        if (this.L == null || !this.L.isShowing()) {
            try {
                getWindow().clearFlags(128);
                super.onBackPressed();
                return;
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        this.L.dismiss();
        Intent intent = new Intent(this, ListCollageAndMyAlbumActivity.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }


    @Override
    public void onDestroy() {
        getWindow().clearFlags(128);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return aj;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return aj;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (this.J != null && this.J.isPlaying()) {
                try {
                    this.J.pause();
                } catch (IllegalStateException e2) {
                    e2.printStackTrace();
                }
            }
            int size = Utils.collageData.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                try {
                    if (((CollageData) Utils.collageData.get(i3)).getVideoUrl() != null) {
                        i2++;
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            if (i2 < size) {
                try {
                    Toast.makeText(this, "Please add Video or Image...", 0).show();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            } else {
                try {
                    collagecommand();
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
