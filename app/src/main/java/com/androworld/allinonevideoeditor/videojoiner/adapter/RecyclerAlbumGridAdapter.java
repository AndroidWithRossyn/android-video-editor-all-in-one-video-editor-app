package com.androworld.allinonevideoeditor.videojoiner.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.videojoiner.model.AlbumImages;
import com.androworld.allinonevideoeditor.videojoiner.model.SelectBucketImage;
import com.androworld.allinonevideoeditor.videojoiner.util.FileUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

@SuppressLint({"WrongConstant"})
public class RecyclerAlbumGridAdapter extends BaseAdapter {
    public static int count;
    int a = 0;
    Context b;
    ImageLoader c;
    int d;
    private LayoutInflater e;

    public class Holder {
        ImageView a;
        ImageView b;

        public Holder() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public RecyclerAlbumGridAdapter(Context context, int i, ImageLoader imageLoader) {
        this.e = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        count = 0;
        this.a = i;
        this.c = imageLoader;
        this.b = context;
        this.d = FileUtils.width / 3;
    }

    public int getCount() {
        try {
            return ((SelectBucketImage) FileUtils.imageUri.get(this.a)).imgUri.size();
        } catch (Exception unused) {
            return 0;
        }
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        final Holder holder = new Holder();
        View inflate = this.e.inflate(R.layout.phototovideo_row_gridphoto, null);
        holder.b = (ImageView) inflate.findViewById(R.id.ivThumbImg);
        holder.a = (ImageView) inflate.findViewById(R.id.ivImgSelection);
        holder.b.setLayoutParams(new LayoutParams(-1, FileUtils.width / 3));
        holder.a.setImageResource(R.drawable.album_gridimage_frame);
        String uri = ((AlbumImages) ((SelectBucketImage) FileUtils.imageUri.get(this.a)).imgUri.get(i)).getImgUri().toString();
        int size = FileUtils.myUri.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (((String) FileUtils.myUri.get(i2)).equals(getRealPathFromURI(((AlbumImages) ((SelectBucketImage) FileUtils.imageUri.get(this.a)).imgUri.get(i)).getImgUri()))) {
                count++;
                holder.a.setImageResource(R.drawable.album_gridimage_frameselect);
            }
        }
        Picasso.with(this.b).load(uri.toString()).resize(this.d, this.d).into(holder.b);
        holder.b.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                Log.v("size", String.valueOf(FileUtils.myUri.size()));
                if (((AlbumImages) ((SelectBucketImage) FileUtils.imageUri.get(RecyclerAlbumGridAdapter.this.a)).imgUri.get(i)).getImgPos() >= 0) {
                    holder.a.setImageResource(R.drawable.album_gridimage_frame);
                    int size = FileUtils.myUri.size();
                    for (int i = 0; i < size; i++) {
                        if (((String) FileUtils.myUri.get(i)).equals(RecyclerAlbumGridAdapter.this.getRealPathFromURI(((AlbumImages) ((SelectBucketImage) FileUtils.imageUri.get(RecyclerAlbumGridAdapter.this.a)).imgUri.get(i)).getImgUri()))) {
                            FileUtils.myUri.remove(i);
                            size--;
                        }
                    }
                    ((AlbumImages) ((SelectBucketImage) FileUtils.imageUri.get(RecyclerAlbumGridAdapter.this.a)).imgUri.get(i)).setImgPos(-1);
                    RecyclerAlbumGridAdapter.count--;
                    return;
                }
                int size2 = FileUtils.myUri.size();
                boolean z = false;
                for (int i2 = 0; i2 < size2; i2++) {
                    if (((String) FileUtils.myUri.get(i2)).equals(RecyclerAlbumGridAdapter.this.getRealPathFromURI(((AlbumImages) ((SelectBucketImage) FileUtils.imageUri.get(RecyclerAlbumGridAdapter.this.a)).imgUri.get(i)).getImgUri()))) {
                        FileUtils.myUri.remove(i2);
                        holder.a.setImageResource(R.drawable.album_gridimage_frame);
                        size2--;
                        z = true;
                    }
                }
                if (!z) {
                    if (FileUtils.myUri.size() < 2) {
                        FileUtils.myUri.add(RecyclerAlbumGridAdapter.this.getRealPathFromURI(((AlbumImages) ((SelectBucketImage) FileUtils.imageUri.get(RecyclerAlbumGridAdapter.this.a)).imgUri.get(i)).getImgUri()));
                        holder.a.setImageResource(R.drawable.album_gridimage_frameselect);
                        RecyclerAlbumGridAdapter.count++;
                        return;
                    }
                    Toast.makeText(RecyclerAlbumGridAdapter.this.b, "Select Maximum two Videos", 0).show();
                }
            }
        });
        return inflate;
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor query = this.b.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        String string = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow("_data")) : null;
        query.close();
        return string;
    }
}
