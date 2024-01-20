package com.androworld.allinonevideoeditor;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

public class MoreAppViewHolders extends ViewHolder implements OnClickListener {
    public static Context ctx;
    public TextView countryName;
    public ImageView countryPhoto;
    List<MoreAppItemObject> m;

    public MoreAppViewHolders(View view, List<MoreAppItemObject> list) {
        super(view);
        view.setOnClickListener(this);
        ctx = view.getContext();
        this.m = list;
        this.countryName = (TextView) view.findViewById(R.id.country_name);
        this.countryPhoto = (ImageView) view.findViewById(R.id.imageView1);
    }

    @Override public void onClick(View view) {
        try {
            ctx.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(((MoreAppItemObject) this.m.get(getPosition())).getLink())));
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(ctx, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
}
