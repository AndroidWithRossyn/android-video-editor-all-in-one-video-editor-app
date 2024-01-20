package com.androworld.allinonevideoeditor.phototovideo.tablayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.phototovideo.DisplayAlbumActivity;
import com.androworld.allinonevideoeditor.phototovideo.model.AlbumImages;
import com.androworld.allinonevideoeditor.phototovideo.model.GallaryAlbum;
import com.androworld.allinonevideoeditor.phototovideo.model.ImageSelect;
import com.androworld.allinonevideoeditor.phototovideo.model.SelectBucketImage;
import com.androworld.allinonevideoeditor.phototovideo.util.PreferenceManager;
import com.androworld.allinonevideoeditor.phototovideo.util.Utils;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;

@SuppressLint({"WrongConstant"})
public class FragementAlbum extends Fragment {
    static Context a;
    RecyclerGallaryAlbumAdapter b;
    ArrayList<AlbumImages> c = null;
    ArrayList<GallaryAlbum> d;
    LayoutManager e;
    ImageLoader f = ImageLoader.getInstance();
    String g = "";
    RecyclerView h;
    SelectBucketImage i = null;
    int j;
    View k;

    public class RecyclerGallaryAlbumAdapter extends Adapter<RecyclerGallaryAlbumAdapter.holder> {
        ArrayList<GallaryAlbum> a = new ArrayList<>();
        ImageLoader b;

        class holder extends ViewHolder {
            ImageView m;
            TextView n;

            public holder(View view) {
                super(view);
                this.n = (TextView) view.findViewById(R.id.tvAlbumTitle);
                this.m = (ImageView) view.findViewById(R.id.ivThumb);
            }
        }

        public RecyclerGallaryAlbumAdapter(Context context, ArrayList<GallaryAlbum> arrayList, ImageLoader imageLoader) {
            this.a.addAll(arrayList);
            this.b = imageLoader;
        }

        public int getItemCount() {
            return this.a.size();
        }

        public void onBindViewHolder(holder aVar, final int i) {
            if (Utils.width < 1) {
                DisplayMetrics displayMetrics = FragementAlbum.a.getResources().getDisplayMetrics();
                Utils.width = displayMetrics.widthPixels;
                Utils.height = displayMetrics.heightPixels;
            }
            FragementAlbum.this.j = 0;
            Picasso.with(FragementAlbum.a).load(((GallaryAlbum) this.a.get(i)).imgUri.toString()).into(aVar.m);
            String str = ((GallaryAlbum) this.a.get(i)).bucketName;
            int size = Utils.myUri.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str2 = (String) Utils.myUri.get(i2);
                StringBuilder sb = new StringBuilder();
                sb.append("/");
                sb.append(str);
                sb.append("/");
                if (str2.contains(sb.toString())) {
                    FragementAlbum.this.j++;
                }
            }
            if (str.length() > 30) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str.substring(0, 30));
                sb2.append("..");
                str = sb2.toString();
            }
            aVar.n.setText(str);
            aVar.itemView.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    FragementAlbum.this.functionToRun(i);
                }
            });
        }

        public holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new holder(LayoutInflater.from(FragementAlbum.a).inflate(R.layout.phototovideo_row_listgallary, viewGroup, false));
        }
    }

    class a extends AsyncTask<Void, Void, String> {
        a() {
        }



        public String doInBackground(Void... voidArr) {
            FragementAlbum.this.b();
            return null;
        }



        public void onPostExecute(String str) {
            super.onPostExecute(str);
            if (FragementAlbum.this.isAdded()) {
                FragementAlbum.this.getResources().getString(R.string.app_name);
            }
            FragementAlbum.this.b = new RecyclerGallaryAlbumAdapter(FragementAlbum.a, FragementAlbum.this.d, FragementAlbum.this.f);
            FragementAlbum.this.h.setAdapter(FragementAlbum.this.b);
        }
    }

    public static Fragment newInstance(int i2, Context context) {
        Bundle bundle = new Bundle();
        a = context;
        bundle.putInt(FragmentPhoto.ARG_PAGE, i2);
        FragementAlbum fragementAlbum = new FragementAlbum();
        fragementAlbum.setArguments(bundle);
        return fragementAlbum;
    }

    @Override public void onStart() {
        super.onStart();
        this.d = new ArrayList<>();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("", "on attch");
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.k = layoutInflater.inflate(R.layout.phototovideo_tab_album, viewGroup, false);
        a();
        return this.k;
    }

    private void a() {
        this.h = (RecyclerView) this.k.findViewById(R.id.recycler_view);
        this.e = new GridLayoutManager(a, 1);
        this.h.setLayoutManager(this.e);
        new a().execute(new Void[0]);
    }

    @Override public void onStop() {
        super.onStop();
        if (this.f != null) {
            this.f.clearDiskCache();
            this.f.clearMemoryCache();
        }
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
            this.g = query.getString(columnIndex2);
            this.i = new SelectBucketImage();
            this.c = new ArrayList<>();
            this.i.bucketid = this.g;
            do {
                GallaryAlbum gallaryAlbum = new GallaryAlbum();
                gallaryAlbum.bucketName = query.getString(columnIndex);
                gallaryAlbum.bucketId = query.getString(columnIndex2);
                int i2 = query.getInt(columnIndex3);
                Uri withAppendedPath = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, Integer.toString(i2));
                if (!arrayList.contains(gallaryAlbum.bucketId)) {
                    arrayList.add(gallaryAlbum.bucketId);
                    gallaryAlbum.imgUri = withAppendedPath;
                    gallaryAlbum.imgId = i2;
                    this.d.add(gallaryAlbum);
                    if (!this.g.equals(gallaryAlbum.bucketId)) {
                        this.i.bucketid = this.g;
                        this.i.imgUri = new ArrayList<>();
                        this.i.imgUri.addAll(this.c);
                        Utils.imageUri.add(this.i);
                        this.g = gallaryAlbum.bucketId;
                        this.i = new SelectBucketImage();
                        this.c = new ArrayList<>();
                    }
                }
                AlbumImages albumImages = new AlbumImages(withAppendedPath, Integer.valueOf(i2), -1);
                albumImages.setImgUri(withAppendedPath);
                albumImages.setImgId(Integer.valueOf(i2));
                albumImages.setImgPos(-1);
                this.c.add(albumImages);
            } while (query.moveToNext());
            this.i.bucketid = this.g;
            this.i.imgUri = new ArrayList<>();
            this.i.imgUri.addAll(this.c);
            Utils.imageUri.add(this.i);
        }
    }

    @Override public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (this.b != null && i2 == 0) {
            this.b.notifyDataSetChanged();
        } else if (this.b != null && i2 == 69) {
            this.b.notifyDataSetChanged();
        }
    }


    public void onBackPressed() {
        int size = Utils.imageUri.size();
        for (int i2 = 0; i2 < size; i2++) {
            int size2 = ((SelectBucketImage) Utils.imageUri.get(i2)).imgUri.size();
            for (int i3 = 0; i3 < size2; i3++) {
                int i4 = ((AlbumImages) ((SelectBucketImage) Utils.imageUri.get(i2)).imgUri.get(i3)).getImgPos();
                if (i4 >= 0) {
                    ImageSelect imageSelect = new ImageSelect();
                    int indexId = PreferenceManager.getIndexId();
                    imageSelect.indexId = indexId;
                    PreferenceManager.setIndexId(indexId + 1);
                    imageSelect.imgId = ((AlbumImages) ((SelectBucketImage) Utils.imageUri.get(i2)).imgUri.get(i3)).getImgId().intValue();
                    imageSelect.imgUri = ((AlbumImages) ((SelectBucketImage) Utils.imageUri.get(i2)).imgUri.get(i3)).getImgUri().toString();
                    imageSelect.cropIndex = -1;
                    imageSelect.imgPos = i4;
                    Utils.selectImageList.add(imageSelect);
                }
            }
        }
        HashSet hashSet = new HashSet();
        hashSet.addAll(Utils.selectImageList);
        Utils.selectImageList.clear();
        Utils.selectImageList.addAll(hashSet);
        HashSet hashSet2 = new HashSet();
        hashSet2.addAll(Utils.selectedImagesUri);
        Utils.selectedImagesUri.clear();
        Utils.selectedImagesUri.addAll(hashSet2);
        if (Utils.myUri.size() > 0) {
            Builder builder = new Builder(getActivity());
            builder.setTitle("Alert!!!");
            builder.setMessage("you are going to remove selection. Are you sure you want to back from gallery?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    FragementAlbum.this.getActivity().finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.create().show();
            return;
        }
        getActivity().finish();
    }

    @Override public void onDestroy() {
        getActivity().getWindow().clearFlags(128);
        super.onDestroy();
        if (this.f != null) {
            this.f.clearDiskCache();
            this.f.clearMemoryCache();
        }
    }

    @Override public void onResume() {
        super.onResume();
        if (this.f == null) {
            c();
        }
    }

    private void c() {
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(a).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY).displayer(new FadeInBitmapDisplayer(200)).build()).build();
        this.f = ImageLoader.getInstance();
        this.f.init(build);
    }

    public void functionToRun(int i2) {
        Intent intent = new Intent(a, DisplayAlbumActivity.class);
        intent.putExtra("bucketid", i2);
        startActivityForResult(intent, 0);
    }
}
