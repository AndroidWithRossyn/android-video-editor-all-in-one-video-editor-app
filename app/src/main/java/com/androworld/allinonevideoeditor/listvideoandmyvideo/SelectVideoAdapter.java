package com.androworld.allinonevideoeditor.listvideoandmyvideo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androworld.allinonevideoeditor.Helper;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.audiovideomixer.AudioVideoMixer;
import com.androworld.allinonevideoeditor.fastmotionvideo.FastMotionVideoActivity;
import com.androworld.allinonevideoeditor.slowmotionvideo.SlowMotionVideoActivity;
import com.androworld.allinonevideoeditor.videocompress.VideoCompressor;
import com.androworld.allinonevideoeditor.videoconverter.VideoConverteractivity;
import com.androworld.allinonevideoeditor.videocrop.VideoCropActivity;
import com.androworld.allinonevideoeditor.videocutter.VideoCutter;
import com.androworld.allinonevideoeditor.videomirror.VideoMirrorActivity;
import com.androworld.allinonevideoeditor.videomute.VideoMuteActivity;
import com.androworld.allinonevideoeditor.videoreverse.VideoReverseActivity;
import com.androworld.allinonevideoeditor.videorotate.VideoRotateActivity;
import com.androworld.allinonevideoeditor.videosplitter.VideoSplitterActivity;
import com.androworld.allinonevideoeditor.videowatermark.VideoWatermarkActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class SelectVideoAdapter extends BaseAdapter {
    ImageLoader a;
    ArrayList<VideoData> b = new ArrayList<>();
    ArrayList<VideoData> c = new ArrayList<>();
    private LayoutInflater d;

    public final Context e;

    private class a {
        ImageView a;
        TextView b;
        TextView c;

        private a() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public SelectVideoAdapter(Context context, ArrayList<VideoData> arrayList, ImageLoader imageLoader) {
        this.e = context;
        this.a = imageLoader;
        this.d = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.c.addAll(arrayList);
        this.b.addAll(arrayList);
    }

    public int getCount() {
        return this.c.size();
    }

    public Object getItem(int i) {
        return this.c.get(i);
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        a aVar;
        if (view == null) {
            view = this.d.inflate(R.layout.row_video, null);
            aVar = new a();
            aVar.a = (ImageView) view.findViewById(R.id.image_preview);
            aVar.c = (TextView) view.findViewById(R.id.file_name);
            aVar.b = (TextView) view.findViewById(R.id.duration);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        this.a.displayImage(((VideoData) this.c.get(i)).videouri.toString(), aVar.a, new Builder().showImageForEmptyUri(0).cacheInMemory(true).showStubImage(R.color.trans).cacheOnDisk(true).resetViewBeforeLoading(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Config.ARGB_8888).delayBeforeLoading(100).postProcessor(new BitmapProcessor() {
            public Bitmap process(Bitmap bitmap) {
                return Bitmap.createScaledBitmap(bitmap, 100, 100, false);
            }
        }).displayer(new SimpleBitmapDisplayer()).build());
        view.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                if (Helper.ModuleId == 1) {
                    Intent intent = new Intent(SelectVideoAdapter.this.e, VideoCutter.class);
                    intent.setFlags(67108864);
                    intent.putExtra("path", ((VideoData) SelectVideoAdapter.this.c.get(i)).videoPath);
                    SelectVideoAdapter.this.e.startActivity(intent);
                } else if (Helper.ModuleId == 2) {
                    Intent intent2 = new Intent(SelectVideoAdapter.this.e, VideoCompressor.class);
                    intent2.setFlags(67108864);
                    intent2.putExtra("videouri", ((VideoData) SelectVideoAdapter.this.c.get(i)).videoPath);
                    SelectVideoAdapter.this.e.startActivity(intent2);
                } else if (Helper.ModuleId == 4) {
                    Intent intent3 = new Intent(SelectVideoAdapter.this.e, AudioVideoMixer.class);
                    intent3.setFlags(67108864);
                    Bundle bundle = new Bundle();
                    bundle.putString("song", ((VideoData) SelectVideoAdapter.this.c.get(i)).videoPath);
                    intent3.putExtras(bundle);
                    SelectVideoAdapter.this.e.startActivity(intent3);
                } else if (Helper.ModuleId == 5) {
                    Intent intent4 = new Intent(SelectVideoAdapter.this.e, VideoMuteActivity.class);
                    intent4.setFlags(67108864);
                    intent4.putExtra("videouri", ((VideoData) SelectVideoAdapter.this.c.get(i)).videoPath);
                    SelectVideoAdapter.this.e.startActivity(intent4);
                } else if (Helper.ModuleId == 8) {
                    Intent intent5 = new Intent(SelectVideoAdapter.this.e, VideoConverteractivity.class);
                    intent5.setFlags(67108864);
                    intent5.putExtra("videofilename", ((VideoData) SelectVideoAdapter.this.c.get(i)).videoPath);
                    SelectVideoAdapter.this.e.startActivity(intent5);
                } else if (Helper.ModuleId == 9) {
                    Intent intent6 = new Intent(SelectVideoAdapter.this.e, FastMotionVideoActivity.class);
                    intent6.setFlags(67108864);
                    intent6.putExtra("videofilename", ((VideoData) SelectVideoAdapter.this.c.get(i)).videoPath);
                    SelectVideoAdapter.this.e.startActivity(intent6);
                } else if (Helper.ModuleId == 10) {
                    Intent intent7 = new Intent(SelectVideoAdapter.this.e, SlowMotionVideoActivity.class);
                    intent7.setFlags(67108864);
                    intent7.putExtra("videofilename", ((VideoData) SelectVideoAdapter.this.c.get(i)).videoPath);
                    SelectVideoAdapter.this.e.startActivity(intent7);
                } else if (Helper.ModuleId == 11) {
                    Intent intent8 = new Intent(SelectVideoAdapter.this.e, VideoCropActivity.class);
                    intent8.setFlags(67108864);
                    intent8.putExtra("videofilename", ((VideoData) SelectVideoAdapter.this.c.get(i)).videoPath);
                    SelectVideoAdapter.this.e.startActivity(intent8);
                } else if (Helper.ModuleId == 13) {
                    Intent intent9 = new Intent(SelectVideoAdapter.this.e, VideoRotateActivity.class);
                    intent9.setFlags(67108864);
                    intent9.putExtra("videoPath", ((VideoData) SelectVideoAdapter.this.c.get(i)).videoPath);
                    SelectVideoAdapter.this.e.startActivity(intent9);
                } else if (Helper.ModuleId == 14) {
                    Intent intent10 = new Intent(SelectVideoAdapter.this.e, VideoMirrorActivity.class);
                    intent10.setFlags(67108864);
                    intent10.putExtra("videouri", ((VideoData) SelectVideoAdapter.this.c.get(i)).videoPath);
                    SelectVideoAdapter.this.e.startActivity(intent10);
                } else if (Helper.ModuleId == 15) {
                    Intent intent11 = new Intent(SelectVideoAdapter.this.e, VideoSplitterActivity.class);
                    intent11.setFlags(67108864);
                    intent11.putExtra("videouri", ((VideoData) SelectVideoAdapter.this.c.get(i)).videoPath);
                    SelectVideoAdapter.this.e.startActivity(intent11);
                } else if (Helper.ModuleId == 16) {
                    Intent intent12 = new Intent(SelectVideoAdapter.this.e, VideoReverseActivity.class);
                    intent12.setFlags(67108864);
                    intent12.putExtra("videouri", ((VideoData) SelectVideoAdapter.this.c.get(i)).videoPath);
                    SelectVideoAdapter.this.e.startActivity(intent12);
                } else if (Helper.ModuleId == 22) {
                    Intent intent13 = new Intent(SelectVideoAdapter.this.e, VideoWatermarkActivity.class);
                    intent13.setFlags(67108864);
                    intent13.putExtra("videopath", ((VideoData) SelectVideoAdapter.this.c.get(i)).videoPath);
                    SelectVideoAdapter.this.e.startActivity(intent13);
                }
            }
        });
        if (i % 2 == 0) {
            view.setBackgroundResource(R.drawable.divider_1);
        } else {
            view.setBackgroundResource(R.drawable.divider_2);
        }
        TextView textView = aVar.c;
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(((VideoData) this.c.get(i)).videoName);
        textView.setText(sb.toString());
        TextView textView2 = aVar.b;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("");
        sb2.append(((VideoData) this.c.get(i)).duration);
        textView2.setText(sb2.toString());
        return view;
    }

    public void filter(String str) {
        String lowerCase = str.toLowerCase(Locale.getDefault());
        this.c.clear();
        if (lowerCase.length() == 0) {
            this.c.addAll(this.b);
        } else {
            Iterator it = this.b.iterator();
            while (it.hasNext()) {
                VideoData videoData = (VideoData) it.next();
                if (videoData.videoName.toLowerCase(Locale.getDefault()).contains(lowerCase)) {
                    this.c.add(videoData);
                }
            }
        }
        notifyDataSetChanged();
    }
}
