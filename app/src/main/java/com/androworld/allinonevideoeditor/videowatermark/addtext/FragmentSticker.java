package com.androworld.allinonevideoeditor.videowatermark.addtext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.videowatermark.VideoWatermarkActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.ads.dynamite.ModuleDescriptor;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import java.util.ArrayList;
import java.util.List;

public class FragmentSticker extends Fragment {
    int a = 0;
    GridView b;
    ArrayList<String> c;
    int d = 0;
    StickerAdapter e;
    ArrayList<String> f = new ArrayList<>();

    public class StickerAdapter extends BaseAdapter {
        Context a;
        LayoutInflater b;
        List<Integer> c = new ArrayList();
        ArrayList<String> d;

        private class a {
            ImageView a;

            private a() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public StickerAdapter(Context context, ArrayList<String> arrayList) {
            this.d = arrayList;
            this.b = LayoutInflater.from(context);
            this.a = context;
        }

        public int getCount() {
            return this.d.size();
        }

        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            int i2 = 0;
            View inflate = this.b.inflate(R.layout.videowatermark_adaptersticker, viewGroup, false);
            a aVar = new a();
            aVar.a = (ImageView) inflate.findViewById(R.id.sticker_img);
            while (i2 < DataBinder.stickerList.size()) {
                try {
                    if (((StickerData) DataBinder.stickerList.get(i2)).getName().equals(this.d.get(i))) {
                        try {
                            if (((StickerData) DataBinder.stickerList.get(i2)).isSelected()) {
                                try {
                                    aVar.a.setBackgroundResource(R.color.Sticker_Selected_Color);
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            } else {
                                try {
                                    aVar.a.setBackgroundResource(R.color.Sticker_BG_Color);
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                            Glide.with(FragmentSticker.this.getActivity()).load(Integer.valueOf(FragmentSticker.this.getResources().getIdentifier((String) this.d.get(i), "drawable", FragmentSticker.this.getActivity().getPackageName()))).into(aVar.a);
                            return inflate;
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                    } else {
                        i2++;
                        continue;
                    }
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
            }
            Glide.with(FragmentSticker.this.getActivity()).load(Integer.valueOf(FragmentSticker.this.getResources().getIdentifier((String) this.d.get(i), "drawable", FragmentSticker.this.getActivity().getPackageName()))).into(aVar.a);
            return inflate;
        }
    }

    public class fetchSticker extends AsyncTask<String, Void, Void> {
        public fetchSticker() {
        }


        public Void doInBackground(String... strArr) {
            try {
                for (int i = FragmentSticker.this.d; i <= FragmentSticker.this.a; i++) {
                    ArrayList<String> arrayList = FragmentSticker.this.f;
                    StringBuilder sb = new StringBuilder();
                    sb.append("s");
                    sb.append(i);
                    arrayList.add(sb.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        public void onPreExecute() {
            FragmentSticker.this.f.clear();
            super.onPreExecute();
        }


        public void onPostExecute(Void voidR) {
            FragmentSticker.this.e = new StickerAdapter(FragmentSticker.this.getActivity(), FragmentSticker.this.f);
            FragmentSticker.this.b.setAdapter(FragmentSticker.this.e);
            super.onPostExecute(voidR);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.videowatermark_fragment_sticker, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        int position = FragmentPagerItem.getPosition(getArguments());
        this.b = (GridView) view.findViewById(R.id.gridView);
        fillSticker(position);
        this.c = new ArrayList<>();
        this.b.setOnItemClickListener(new OnItemClickListener() {
            @SuppressLint({"WrongConstant"})
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                int i2 = 0;
                while (i2 < DataBinder.stickerList.size()) {
                    try {
                        if (!((StickerData) DataBinder.stickerList.get(i2)).getName().equals(FragmentSticker.this.f.get(i))) {
                            i2++;
                        } else if (((StickerData) DataBinder.stickerList.get(i2)).isSelected()) {
                            try {
                                ((StickerData) DataBinder.stickerList.get(i2)).setSelected(false);
                                DataBinder.stickerValue--;
                                if (FragmentSticker.this.getActivity() instanceof VideoWatermarkActivity) {
                                    try {
                                        ((VideoWatermarkActivity) FragmentSticker.this.getActivity()).stickerCounting(String.valueOf(DataBinder.stickerValue));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                FragmentSticker.this.e.notifyDataSetChanged();
                                return;
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        } else if (DataBinder.stickerValue == 10) {
                            try {
                                Toast.makeText(FragmentSticker.this.getActivity(), "Can't select more than 10 sticker.", 0).show();
                                return;
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        } else {
                            try {
                                ((StickerData) DataBinder.stickerList.get(i2)).setSelected(true);
                                DataBinder.stickerValue++;
                                if (FragmentSticker.this.getActivity() instanceof VideoWatermarkActivity) {
                                    try {
                                        ((VideoWatermarkActivity) FragmentSticker.this.getActivity()).stickerCounting(String.valueOf(DataBinder.stickerValue));
                                    } catch (Exception e4) {
                                        e4.printStackTrace();
                                    }
                                }
                                FragmentSticker.this.e.notifyDataSetChanged();
                                return;
                            } catch (Exception e5) {
                                e5.printStackTrace();
                            }
                        }
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
            }
        });
    }

    public void fillSticker(int i) {
        this.f.clear();
        if (i == 0) {
            this.d = 1;
            this.a = 20;
        }
        if (i == 1) {
            this.d = 81;
            this.a = 100;
        }
        if (i == 2) {
            this.d = 106;
            this.a = 125;
        }
        if (i == 3) {
            this.d = 170;
            this.a = 189;
        }
        if (i == 4) {
            this.d = 235;
            this.a = 254;
        }
        if (i == 5) {
            this.d = 284;
            this.a = 303;
        }
        if (i == 6) {
            this.d = 308;
            this.a = 319;
        }
        if (i == 7) {
            this.d = ModuleDescriptor.MODULE_VERSION;
            this.a = 339;
        }
        if (i == 8) {
            this.d = 340;
            this.a = 359;
        }
        if (i == 9) {
            this.d = 372;
            this.a = 391;
        }
        new fetchSticker().execute(new String[0]);
    }
}
