package com.androworld.allinonevideoeditor.phototovideo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.phototovideo.model.MusicModel;
import com.androworld.allinonevideoeditor.phototovideo.util.Utils;

import java.util.ArrayList;

public class SelectMusicAdapter extends Adapter<SelectMusicAdapter.holder> {
    ArrayList<MusicModel> a;
    private LayoutInflater b;

    class holder extends ViewHolder {
        LinearLayout m;
        ToggleButton n;
        TextView o;

        public holder(View view) {
            super(view);
            this.o = (TextView) view.findViewById(R.id.row_title);
            this.n = (ToggleButton) view.findViewById(R.id.toggleButton1);
            this.m = (LinearLayout) view.findViewById(R.id.musicPanel);
        }
    }

    @SuppressLint({"WrongConstant"})
    public SelectMusicAdapter(Context context, ArrayList<MusicModel> arrayList) {
        this.b = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.a = arrayList;
    }

    public int getItemCount() {
        return this.a.size();
    }

    public void onBindViewHolder(final holder aVar, final int i) {
        aVar.o.setText(((MusicModel) this.a.get(i)).name);
        LayoutParams layoutParams = aVar.m.getLayoutParams();
        layoutParams.width = Utils.width;
        layoutParams.height = 80;
        aVar.m.setLayoutParams(layoutParams);
        if (Utils.audioSelected == i) {
            aVar.n.setChecked(true);
        } else {
            aVar.n.setChecked(false);
        }
        aVar.itemView.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                aVar.n.setChecked(true);
                Utils.audioSelected = i;
                Utils.audioName = ((MusicModel) SelectMusicAdapter.this.a.get(i)).name;
                ((MoiveMakerActivity) MoiveMakerActivity.mContext).hideMusicList(((MusicModel) SelectMusicAdapter.this.a.get(i)).path);
            }
        });
    }

    public holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = this.b.inflate(R.layout.phototovideo_row_selectmusic, null);
        inflate.setLayoutParams(new LayoutParams(Utils.width, -2));
        return new holder(inflate);
    }
}
