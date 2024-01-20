package com.androworld.allinonevideoeditor.videocollage;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androworld.allinonevideoeditor.R;

public class SelectCollageAdapter extends BaseAdapter {
    int a;
    private final int[] b;
    private Context c;

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public SelectCollageAdapter(Context context, int[] iArr) {
        this.c = context;
        this.b = iArr;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((ListCollageAndMyAlbumActivity) this.c).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.a = displayMetrics.widthPixels;
    }

    public int getCount() {
        return this.b.length;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) this.c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view != null) {
            return view;
        }
        new View(this.c);
        View inflate = layoutInflater.inflate(R.layout.row_frame_select, null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.grid_image);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.lnr_main);
        linearLayout.getLayoutParams().width = this.a / 3;
        linearLayout.getLayoutParams().height = this.a / 3;
        imageView.setImageResource(this.b[i]);
        return inflate;
    }
}
