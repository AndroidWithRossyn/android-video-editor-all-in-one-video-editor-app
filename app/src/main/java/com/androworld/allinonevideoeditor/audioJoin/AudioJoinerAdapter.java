package com.androworld.allinonevideoeditor.audioJoin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androworld.allinonevideoeditor.R;

import java.util.ArrayList;

public class AudioJoinerAdapter extends BaseAdapter {
    int a;
    private ArrayList<String> b = new ArrayList<>();
    private LayoutInflater c;
    private final Context d;

    private class a {
        TextView a;

        private a() {
        }

        a(AudioJoinerAdapter audioJoinerAdapter, AudioJoinerAdapter audioJoinerAdapter2, a aVar) {
            this();
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    @SuppressLint({"WrongConstant"})
    AudioJoinerAdapter(Context context, ArrayList<String> arrayList, int i) {
        this.d = context;
        this.c = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.b.addAll(arrayList);
        this.a = i;
    }

    public int getCount() {
        return this.b.size();
    }

    public Object getItem(int i) {
        return this.b.get(i);
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
        aVar.a.setText((CharSequence) this.b.get(i));
        return view;
    }
}
