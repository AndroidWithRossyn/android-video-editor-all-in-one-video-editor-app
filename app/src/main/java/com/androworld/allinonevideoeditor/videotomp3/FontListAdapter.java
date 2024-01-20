package com.androworld.allinonevideoeditor.videotomp3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.androworld.allinonevideoeditor.R;

import java.util.ArrayList;

@SuppressLint({"WrongConstant"})
public class FontListAdapter extends BaseAdapter {
    ArrayList<String> a;
    Context b = null;
    private LayoutInflater c;

    private class a {
        FrameLayout a;
        ImageView b;
        TextView c;

        private a() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public FontListAdapter(Context context, ArrayList<String> arrayList) {
        this.b = context;
        this.a = arrayList;
        this.c = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return this.a.size();
    }

    public Object getItem(int i) {
        return this.a.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = this.c.inflate(R.layout.videotomp3_fontlistitem, null);
        a aVar = new a();
        aVar.c = (TextView) inflate.findViewById(R.id.Text);
        aVar.b = (ImageView) inflate.findViewById(R.id.selected);
        aVar.a = (FrameLayout) inflate.findViewById(R.id.font_item_view);
        inflate.setTag(aVar);
        a aVar2 = (a) inflate.getTag();
        aVar2.c.setText((CharSequence) this.a.get(i));
        if (FileUtils.Bitrate == i) {
            aVar2.c.setBackgroundResource(R.drawable.bg_round_press);
            aVar2.c.setTextColor(Color.parseColor("#ffffff"));
        } else {
            aVar2.c.setBackgroundResource(R.drawable.bg_round);
            aVar2.c.setTextColor(Color.parseColor("#0f9d58"));
        }
        return inflate;
    }
}
