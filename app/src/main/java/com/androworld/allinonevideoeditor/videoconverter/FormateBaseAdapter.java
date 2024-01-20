package com.androworld.allinonevideoeditor.videoconverter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androworld.allinonevideoeditor.R;

import java.util.ArrayList;

public class FormateBaseAdapter extends BaseAdapter {
    ArrayList a = new ArrayList();
    int b;
    private LayoutInflater c;
    private final Context d;

    private class a {
        TextView a;

        private a() {
        }

        a(FormateBaseAdapter formateBaseAdapter, FormateBaseAdapter formateBaseAdapter2, a aVar) {
            this();
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    @SuppressLint({"WrongConstant"})
    public FormateBaseAdapter(Context context, ArrayList arrayList, int i) {
        this.d = context;
        this.c = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.a.addAll(arrayList);
        this.b = i;
    }

    public int getCount() {
        return this.a.size();
    }

    public Object getItem(int i) {
        return this.a.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar;
        if (view == null) {
            view = this.c.inflate(R.layout.videosplit_row_list, null);
            aVar = new a(this, this, null);
            aVar.a = (TextView) view.findViewById(R.id.textFormat);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        aVar.a.setText((CharSequence) this.a.get(i));
        return view;
    }
}
