package com.androworld.allinonevideoeditor.videojoiner;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap.Config;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Video.Media;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.androworld.allinonevideoeditor.Ads;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.videojoiner.model.AlbumImages;
import com.androworld.allinonevideoeditor.videojoiner.model.GallaryAlbum;
import com.androworld.allinonevideoeditor.videojoiner.model.SelectBucketImage;
import com.androworld.allinonevideoeditor.videojoiner.util.FileUtils;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class SelectVideoFragment extends Fragment {
    public static Uri images;
    public static ImageLoader imgLoader;
    RecyclerGallaryAlbumAdapter a;
    ArrayList<AlbumImages> b = null;
    ArrayList<GallaryAlbum> c = new ArrayList<>();
    String d = "";
    ListView e;
    SelectBucketImage f = null;
    int g;
    private AdView h;
    private Ads ads;

    public class RecyclerGallaryAlbumAdapter extends BaseAdapter {
        ArrayList<GallaryAlbum> a = new ArrayList<>();
        ImageLoader b;
        LayoutInflater c = null;

        public class Holder {
            ImageView a;
            LinearLayout b;
            TextView c;

            public Holder() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public RecyclerGallaryAlbumAdapter(Context context, ArrayList<GallaryAlbum> arrayList, ImageLoader imageLoader) {
            this.a.addAll(arrayList);
            this.b = imageLoader;
            this.c = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return this.a.size();
        }

        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        public View getView(final int i, View view, ViewGroup viewGroup) {
            Holder holder = new Holder();
            View inflate = this.c.inflate(R.layout.phototovideo_row_listgallary, null);
            holder.c = (TextView) inflate.findViewById(R.id.tvAlbumTitle);
            holder.b = (LinearLayout) inflate.findViewById(R.id.layList2);
            holder.a = (ImageView) inflate.findViewById(R.id.ivThumb);
            int i2 = i % 2;
            if (i2 == 0) {
                holder.b.setBackgroundResource(R.drawable.album_bg);
            }
            if (i2 != 0) {
                holder.b.setBackgroundResource(R.drawable.album_bg2);
            }
            if (FileUtils.width < 1) {
                DisplayMetrics displayMetrics = SelectVideoFragment.this.getActivity().getResources().getDisplayMetrics();
                FileUtils.width = displayMetrics.widthPixels;
                FileUtils.height = displayMetrics.heightPixels;
            }
            SelectVideoFragment.this.g = 0;
            this.b.displayImage(((GallaryAlbum) this.a.get(i)).imgUri.toString(), holder.a, new Builder().showImageOnLoading(17170445).resetViewBeforeLoading(true).showImageForEmptyUri((int) R.drawable.videothumb_images).showImageOnFail((int) R.drawable.videothumb_images).cacheInMemory(true).cacheOnDisc(true).bitmapConfig(Config.RGB_565).build());
            String str = ((GallaryAlbum) this.a.get(i)).bucketName;
            int size = FileUtils.myUri.size();
            for (int i3 = 0; i3 < size; i3++) {
                String str2 = (String) FileUtils.myUri.get(i3);
                StringBuilder sb = new StringBuilder();
                sb.append("/");
                sb.append(str);
                sb.append("/");
                if (str2.contains(sb.toString())) {
                    SelectVideoFragment.this.g++;
                }
            }
            if (str.length() > 30) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str.substring(0, 30));
                sb2.append("..");
                str = sb2.toString();
            }
            holder.c.setText(str);
            inflate.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    SelectVideoFragment.this.functionToRun(i);
                }
            });
            return inflate;
        }
    }

    class a extends AsyncTask<Void, Void, String> {
        a() {
        }



        public String doInBackground(Void... voidArr) {
            SelectVideoFragment.this.b();
            return null;
        }



        public void onPostExecute(String str) {
            super.onPostExecute(str);
            SelectVideoFragment.this.a = new RecyclerGallaryAlbumAdapter(SelectVideoFragment.this.getActivity(), SelectVideoFragment.this.c, SelectVideoFragment.imgLoader);
            SelectVideoFragment.this.e.setAdapter(SelectVideoFragment.this.a);
        }
    }

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.videojoinerfragment, viewGroup, false);
        a();
        this.e = (ListView) inflate.findViewById(R.id.listView);
        new a().execute(new Void[0]);

        LinearLayout s = (LinearLayout) inflate.findViewById(R.id.banner_AdView);
        ads = new Ads();
        ads.BannerAd(getActivity(),s);

        return inflate;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void a() {
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(new Builder().cacheInMemory(true).cacheOnDisc(true).resetViewBeforeLoading(true).build()).build());
    }


    public void b() {
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        String[] strArr = {"_id", "_data", "bucket_id", "bucket_display_name"};
        ArrayList arrayList = new ArrayList();
        Cursor query = getActivity().getContentResolver().query(uri, strArr, "bucket_display_name != ?", new String[]{getResources().getString(R.string.app_name)}, "bucket_display_name ASC,_id DESC");
        if (query.moveToFirst()) {
            int columnIndex = query.getColumnIndex("bucket_display_name");
            int columnIndex2 = query.getColumnIndex("bucket_id");
            int columnIndex3 = query.getColumnIndex("_id");
            this.d = query.getString(columnIndex2);
            this.f = new SelectBucketImage();
            this.b = new ArrayList<>();
            this.f.bucketid = this.d;
            do {
                GallaryAlbum gallaryAlbum = new GallaryAlbum();
                gallaryAlbum.bucketName = query.getString(columnIndex);
                gallaryAlbum.bucketId = query.getString(columnIndex2);
                int i = query.getInt(columnIndex3);
                Uri withAppendedPath = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, Integer.toString(i));
                if (!arrayList.contains(gallaryAlbum.bucketId)) {
                    arrayList.add(gallaryAlbum.bucketId);
                    gallaryAlbum.imgUri = withAppendedPath;
                    gallaryAlbum.imgId = i;
                    this.c.add(gallaryAlbum);
                    if (!this.d.equals(gallaryAlbum.bucketId)) {
                        this.f.bucketid = this.d;
                        this.f.imgUri = new ArrayList<>();
                        this.f.imgUri.addAll(this.b);
                        FileUtils.imageUri.add(this.f);
                        this.d = gallaryAlbum.bucketId;
                        this.f = new SelectBucketImage();
                        this.b = new ArrayList<>();
                    }
                }
                AlbumImages albumImages = new AlbumImages(withAppendedPath, Integer.valueOf(i), -1);
                albumImages.setImgUri(withAppendedPath);
                albumImages.setImgId(Integer.valueOf(i));
                albumImages.setImgPos(-1);
                this.b.add(albumImages);
            } while (query.moveToNext());
            this.f.bucketid = this.d;
            this.f.imgUri = new ArrayList<>();
            this.f.imgUri.addAll(this.b);
            FileUtils.imageUri.add(this.f);
        }
    }

    public void functionToRun(int i) {
        Intent intent = new Intent(getActivity(), GallaryPhotosActivity.class);
        intent.putExtra("bucketid", i);
        startActivityForResult(intent, 0);
    }
}
