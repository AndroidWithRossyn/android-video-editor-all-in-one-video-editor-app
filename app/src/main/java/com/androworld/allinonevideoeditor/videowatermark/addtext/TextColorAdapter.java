package com.androworld.allinonevideoeditor.videowatermark.addtext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.androworld.allinonevideoeditor.R;

import java.util.ArrayList;

@SuppressLint({"WrongConstant"})
public class TextColorAdapter extends BaseAdapter {
    String a = "";
    ArrayList<Integer> b;
    int c;
    private Activity d;

    public class Holder {
        ImageView a;
        ImageView b;

        public Holder() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public TextColorAdapter(Activity activity, ArrayList<Integer> arrayList, int i) {
        this.d = activity;
        this.b = arrayList;
        this.c = i;
    }

    public int getCount() {
        return this.b.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(this.d).inflate(R.layout.videowatermark_adaptertextcolor, null, false);
        Holder holder = new Holder();
        holder.b = (ImageView) inflate.findViewById(R.id.imageView);
        holder.a = (ImageView) inflate.findViewById(R.id.border);
        if (this.a.equals("")) {
            holder.a.setVisibility(8);
        } else if (this.a.equals(String.valueOf(i))) {
            holder.a.setVisibility(0);
        } else {
            holder.a.setVisibility(8);
        }
        if (this.c == 1) {
            try {
                Drawable drawable = this.d.getResources().getDrawable(R.drawable.stroke_rect);
                drawable.setColorFilter(new LightingColorFilter(((Integer) this.b.get(i)).intValue(), ((Integer) this.b.get(i)).intValue()));
                holder.b.setImageDrawable(drawable);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                holder.b.setImageResource(((Integer) this.b.get(i)).intValue());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return inflate;
    }

    public void setSelection(int i) {
        this.a = String.valueOf(i);
        notifyDataSetChanged();
    }
}
