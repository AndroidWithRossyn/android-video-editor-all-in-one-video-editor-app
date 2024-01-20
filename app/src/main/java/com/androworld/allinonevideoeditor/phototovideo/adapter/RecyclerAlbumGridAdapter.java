package com.androworld.allinonevideoeditor.phototovideo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.phototovideo.model.AlbumImages;
import com.androworld.allinonevideoeditor.phototovideo.model.SelectBucketImage;
import com.androworld.allinonevideoeditor.phototovideo.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

public class RecyclerAlbumGridAdapter extends Adapter<RecyclerAlbumGridAdapter.holder> {
    public static int count;
    int a = 0;
    Context b;
    ImageLoader c;
    int d;
    private LayoutInflater e;

    class holder extends ViewHolder {
        ImageView m;
        ImageView n;

        public holder(View view) {
            super(view);
            this.n = (ImageView) view.findViewById(R.id.ivThumbImg);
            this.m = (ImageView) view.findViewById(R.id.ivImgSelection);
            this.n.setLayoutParams(new LayoutParams(-1, Utils.width / 3));
            this.m.setLayoutParams(new LayoutParams(-1, Utils.width / 3));
        }
    }

    @SuppressLint({"WrongConstant"})
    public RecyclerAlbumGridAdapter(Context context, int i, ImageLoader imageLoader) {
        this.e = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        count = 0;
        this.a = i;
        this.c = imageLoader;
        this.b = context;
        this.d = Utils.width / 3;
    }

    public int getItemCount() {
        try {
            return ((SelectBucketImage) Utils.imageUri.get(this.a)).imgUri.size();
        } catch (Exception unused) {
            return 0;
        }
    }

    public void onBindViewHolder(final holder aVar, final int i) {
        aVar.m.setImageResource(R.drawable.album_gridimage_frame);
        String uri = ((AlbumImages) ((SelectBucketImage) Utils.imageUri.get(this.a)).imgUri.get(i)).getImgUri().toString();
        int size = Utils.myUri.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (((String) Utils.myUri.get(i2)).equals(getRealPathFromURI(((AlbumImages) ((SelectBucketImage) Utils.imageUri.get(this.a)).imgUri.get(i)).getImgUri()))) {
                count++;
                aVar.m.setImageResource(R.drawable.select_image2);
            }
        }
        Picasso.with(this.b).load(uri.toString()).resize(this.d, this.d).into(aVar.n);
        aVar.n.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                if (((AlbumImages) ((SelectBucketImage) Utils.imageUri.get(RecyclerAlbumGridAdapter.this.a)).imgUri.get(i)).getImgPos() >= 0) {
                    aVar.m.setImageResource(R.drawable.album_gridimage_frame);
                    int size = Utils.myUri.size();
                    for (int i = 0; i < size; i++) {
                        if (((String) Utils.myUri.get(i)).equals(RecyclerAlbumGridAdapter.this.getRealPathFromURI(((AlbumImages) ((SelectBucketImage) Utils.imageUri.get(RecyclerAlbumGridAdapter.this.a)).imgUri.get(i)).getImgUri()))) {
                            Utils.myUri.remove(i);
                            size--;
                        }
                    }
                    ((AlbumImages) ((SelectBucketImage) Utils.imageUri.get(RecyclerAlbumGridAdapter.this.a)).imgUri.get(i)).setImgPos(-1);
                    RecyclerAlbumGridAdapter.count--;
                    return;
                }
                int size2 = Utils.myUri.size();
                boolean z = false;
                for (int i2 = 0; i2 < size2; i2++) {
                    if (((String) Utils.myUri.get(i2)).equals(RecyclerAlbumGridAdapter.this.getRealPathFromURI(((AlbumImages) ((SelectBucketImage) Utils.imageUri.get(RecyclerAlbumGridAdapter.this.a)).imgUri.get(i)).getImgUri()))) {
                        Utils.myUri.remove(i2);
                        aVar.m.setImageResource(R.drawable.album_gridimage_frame);
                        size2--;
                        z = true;
                    }
                }
                if (!z) {
                    RecyclerAlbumGridAdapter.count = 0;
                    Utils.myUri.add(RecyclerAlbumGridAdapter.this.getRealPathFromURI(((AlbumImages) ((SelectBucketImage) Utils.imageUri.get(RecyclerAlbumGridAdapter.this.a)).imgUri.get(i)).getImgUri()));
                    aVar.m.setImageResource(R.drawable.select_image2);
                    RecyclerAlbumGridAdapter.count++;
                }
            }
        });
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor query = this.b.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        String string = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow("_data")) : null;
        query.close();
        return string;
    }

    public holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new holder(this.e.inflate(R.layout.phototovideo_row_gridphoto, null));
    }
}
