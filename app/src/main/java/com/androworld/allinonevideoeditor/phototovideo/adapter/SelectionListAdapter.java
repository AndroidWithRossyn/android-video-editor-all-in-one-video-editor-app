package com.androworld.allinonevideoeditor.phototovideo.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.phototovideo.SelectionListActivity;
import com.androworld.allinonevideoeditor.phototovideo.util.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.io.File;
import java.util.List;

@SuppressLint({"ResourceType"})
public class SelectionListAdapter extends BaseDynamicGridAdapter {
    Context a;
    int b = 180;
    DisplayImageOptions c = new Builder().cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED).cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Config.RGB_565).considerExifParams(true).build();
    ScaleType d = ScaleType.CENTER_INSIDE;
    public ImageLoader imageLoader = ImageLoader.getInstance();

    private class a {
        ImageView a;
        ImageView b;
        RelativeLayout c;

        private a(View view) {
            this.b = (ImageView) view.findViewById(R.id.ivThumbImg);
            this.c = (RelativeLayout) view.findViewById(R.id.relative);
            this.a = (ImageView) view.findViewById(R.id.btnDelete);
        }


        public void a(String str, final int i) {
            StringBuilder sb = new StringBuilder();
            sb.append("file://");
            sb.append(str);
            Uri parse = Uri.parse(sb.toString());
            this.b.setScaleType(SelectionListAdapter.this.d);
            try {
                int i2 = Utils.width;
                this.b.setLayoutParams(new LayoutParams(i2 / 2, i2 / 2));
                SelectionListAdapter.this.imageLoader.displayImage(parse.toString(), this.b, SelectionListAdapter.this.c);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.a.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    new AlertDialog.Builder(SelectionListAdapter.this.a).setTitle("Are you sure to delete image ?").setPositiveButton("delete", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialogInterface, int ir) {
                            SelectionListAdapter.this.deleteImage((String) Utils.createImageList.get(i));
                            Utils.createImageList.remove(i);
                            SelectionListAdapter.this.getItems().remove(i);
                            Utils.myUri.remove(i);
                            SelectionListAdapter.this.notifyDataSetChanged();
                            Toast.makeText(SelectionListAdapter.this.a, "delete Successfully", 0).show();
                            dialogInterface.dismiss();
                            Intent intent = new Intent(SelectionListAdapter.this.a, SelectionListActivity.class);
                            intent.addFlags(335544320);
                            SelectionListAdapter.this.a.startActivity(intent);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).setCancelable(true).show();
                }
            });
        }
    }

    public SelectionListAdapter(Context context, List<?> list, int i) {
        super(context, list, i);
        this.a = context;
    }

    public void setScaleType(ScaleType scaleType) {
        this.d = scaleType;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.phototovideo_row_selectimagelist, null);
            aVar = new a(view);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        aVar.a(getItem(i).toString(), i);
        return view;
    }

    public void refreshlist() {
        notifyDataSetChanged();
    }


    public void deleteImage(String str) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
            StringBuilder sb = new StringBuilder();
            sb.append("Deleted :");
            sb.append(file);
            Log.i("DELETE", sb.toString());
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor query = this.a.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        String string = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow("_data")) : null;
        query.close();
        return string;
    }
}
