package com.androworld.allinonevideoeditor.phototovideo.tablayout;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;

import androidx.fragment.app.Fragment;

import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.phototovideo.model.ImageSelect;
import com.androworld.allinonevideoeditor.phototovideo.util.Utils;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

public class FragmentPhoto extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    static Context a;
    static int b;
    ImageLoader c;
    int d;
    int e;

    public Cursor f;

    public LayoutInflater g;

    private class a extends BaseAdapter {
        public long getItemId(int i) {
            return (long) i;
        }

        public a(Context context) {
            a();
        }

        private void a() {
            ImageLoaderConfiguration build = new Builder(FragmentPhoto.this.getActivity()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY).displayer(new FadeInBitmapDisplayer(200)).build()).build();
            FragmentPhoto.this.c = ImageLoader.getInstance();
            FragmentPhoto.this.c.init(build);
        }

        public int getCount() {
            return FragmentPhoto.this.f.getCount();
        }

        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            View inflate = FragmentPhoto.this.g.inflate(R.layout.phototovideo_row_gridphoto, viewGroup, false);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.ivThumbImg);
            final ImageView imageView2 = (ImageView) inflate.findViewById(R.id.ivImgSelection);
            FragmentPhoto.this.f.moveToPosition(i);
            int columnIndex = FragmentPhoto.this.f.getColumnIndex("_data");
            int columnIndex2 = FragmentPhoto.this.f.getColumnIndex("_id");
            final String string = FragmentPhoto.this.f.getString(columnIndex);
            int size = Utils.myUri.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (string.equals(Utils.myUri.get(i2))) {
                    imageView2.setImageResource(R.drawable.select_image2);
                }
            }
            final int i3 = FragmentPhoto.this.f.getInt(columnIndex2);
            imageView2.setScaleType(ScaleType.FIT_XY);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setLayoutParams(new LayoutParams(FragmentPhoto.b, FragmentPhoto.b));
            imageView2.setLayoutParams(new LayoutParams(FragmentPhoto.b, FragmentPhoto.b));
            ImageLoader.getInstance().displayImage(Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, String.valueOf(i3)).toString(), (ImageAware) new ImageViewAware(imageView));
            imageView.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    int size = Utils.myUri.size();
                    boolean z = false;
                    for (int i = 0; i < size; i++) {
                        if (string.equals(Utils.myUri.get(i))) {
                            Utils.myUri.remove(i);
                            imageView2.setImageResource(R.drawable.album_gridimage_frame);
                            size--;
                            z = true;
                        }
                    }
                    if (!z) {
                        Utils.myUri.add(string);
                        ImageSelect imageSelect = new ImageSelect();
                        imageSelect.imgId = i3;
                        imageSelect.imgUri = string;
                        imageSelect.cropIndex = -1;
                        Utils.selectImageList.add(imageSelect);
                        imageView2.setImageResource(R.drawable.select_image2);
                    }
                }
            });
            return inflate;
        }
    }

    public static Fragment newInstance(int i, Context context) {
        Bundle bundle = new Bundle();
        a = context;
        bundle.putInt(ARG_PAGE, i);
        FragmentPhoto fragmentPhoto = new FragmentPhoto();
        fragmentPhoto.setArguments(bundle);
        b = (a.getResources().getDisplayMetrics().widthPixels - ((int) TypedValue.applyDimension(1, 8.0f, a.getResources().getDisplayMetrics()))) / 3;
        return fragmentPhoto;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("", "on attch");
    }

    @Override public void onResume() {
        super.onResume();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.g = layoutInflater;
        View inflate = layoutInflater.inflate(R.layout.phototovideo_tab_photo, viewGroup, false);
        this.f = getActivity().getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_id", "_data", "bucket_id"}, null, null, "datetaken DESC");
        this.e = this.f.getColumnIndexOrThrow("_id");
        this.d = this.f.getColumnIndexOrThrow("_data");
        ((GridView) inflate.findViewById(R.id.gridView1)).setAdapter(new a(getActivity()));
        return inflate;
    }
}
