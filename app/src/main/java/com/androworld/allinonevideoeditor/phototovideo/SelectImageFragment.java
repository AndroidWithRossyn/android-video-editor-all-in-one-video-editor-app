package com.androworld.allinonevideoeditor.phototovideo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.phototovideo.tablayout.HomeTab;

public class SelectImageFragment extends Fragment {
    RelativeLayout a;

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.phototovideo_selectimagefragment, viewGroup, false);
        this.a = (RelativeLayout) inflate.findViewById(R.id.A12);
        this.a.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                SelectImageFragment.this.startActivityForResult(new Intent(SelectImageFragment.this.getActivity(), HomeTab.class), 99);
            }
        });
        return inflate;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
