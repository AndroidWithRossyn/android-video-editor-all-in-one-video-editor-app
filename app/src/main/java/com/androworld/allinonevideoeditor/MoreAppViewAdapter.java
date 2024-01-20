package com.androworld.allinonevideoeditor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.List;

public class MoreAppViewAdapter extends Adapter<MoreAppViewHolders> {
    private List<MoreAppItemObject> a;
    private Context b;

    public MoreAppViewAdapter(Context context, List<MoreAppItemObject> list) {
        this.a = list;
        this.b = context;
    }

    public MoreAppViewHolders onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MoreAppViewHolders(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.icon_moreapp, null), this.a);
    }

    public void onBindViewHolder(MoreAppViewHolders moreAppViewHolders, int i) {
        moreAppViewHolders.countryName.setText(((MoreAppItemObject) this.a.get(i)).getName());
        moreAppViewHolders.countryPhoto.setImageResource(((MoreAppItemObject) this.a.get(i)).getPhoto());
    }

    public int getItemCount() {
        return this.a.size();
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.b.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
