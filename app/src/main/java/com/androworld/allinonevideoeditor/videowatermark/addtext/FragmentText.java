package com.androworld.allinonevideoeditor.videowatermark.addtext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.graphics.BitmapFactory;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androworld.allinonevideoeditor.Helper;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.videowatermark.addtext.RecyclerItemClickListener.OnItemClickListener;
import com.etiennelawlor.discreteslider.library.ui.DiscreteSlider;
import com.etiennelawlor.discreteslider.library.ui.DiscreteSlider.OnDiscreteSliderChangeListener;
import com.etiennelawlor.discreteslider.library.utilities.DisplayUtility;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.zcw.togglebutton.ToggleButton;
import com.zcw.togglebutton.ToggleButton.OnToggleChanged;

import java.util.ArrayList;
import java.util.List;

@SuppressLint({"WrongConstant"})
public class FragmentText extends Fragment {
    public static int bgColor = 0;
    public static boolean bgStatus = false;
    public static int currentBg = 1;
    public static int currentColor = 1;
    public static Typeface fontTypeface = null;
    public static FontsAdapter fontsAdapter = null;
    public static int opacityBGProgress = 100;
    public static int opacityTxtProgress = 100;
    public static int shadowColor = 2131099704;
    public static int shadowRadius = 2;
    public static int shadowX = 2;
    public static int shadowY = 2;
    public static ToggleButton toggleButton;
    public static int txtColor;
    int a = 4;
    DiscreteSlider b;
    EditText c;
    FontProvider d;
    List<String> e;
    int f = R.layout.videowatermark_fragment_empty;
    LinearLayoutManager g;
    int h;
    RecyclerView i;
    ArrayList<TextShadow> j = new ArrayList<>();
    RelativeLayout k;

    public static void dismissSoftKeyboard(InputMethodManager inputMethodManager, EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setFocusableInTouchMode(false);
        editText.setClickable(false);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        editText.clearFocus();
    }

    public static void showSoftKeyboard(InputMethodManager inputMethodManager, EditText editText) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setFocusableInTouchMode(true);
        editText.setClickable(true);
        inputMethodManager.toggleSoftInputFromWindow(editText.getApplicationWindowToken(), 2, 0);
        editText.requestFocus();
    }

    public static void setDefaultValues(EditText editText, Activity activity) {
        bgStatus = false;
        if (toggleButton != null) {
            try {
                toggleButton.setToggleOff();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        editText.setHintTextColor(-1);
        editText.setShadowLayer(8.0f, 6.0f, 6.0f, ViewCompat.MEASURED_STATE_MASK);
        editText.setSelection(editText.length());
        editText.setTypeface(Helper.txtface);
        if (!bgStatus) {
            editText.setBackgroundColor(0);
            editText.getBackground().setAlpha(Math.round((((float) opacityBGProgress) / 100.0f) * 255.0f));
        } else if (currentBg == 0) {
            editText.setBackgroundColor(bgColor);
            editText.getBackground().setAlpha(Math.round((((float) opacityBGProgress) / 100.0f) * 255.0f));
        } else {
            BitmapDrawable bitmapDrawable = new BitmapDrawable(BitmapFactory.decodeResource(activity.getResources(), bgColor));
            bitmapDrawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
            editText.setBackgroundDrawable(bitmapDrawable);
            editText.getBackground().setAlpha(Math.round((((float) opacityBGProgress) / 100.0f) * 255.0f));
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.h = FragmentPagerItem.getPosition(getArguments());
        switch (this.h) {
            case 0:
                this.f = R.layout.videowatermark_fragment_empty;
                break;
            case 1:
                this.f = R.layout.videowatermark_fragment_text1;
                break;
            case 2:
                this.f = R.layout.videowatermark_fragment_text2;
                break;
            case 3:
                this.f = R.layout.videowatermark_fragment_text3;
                break;
        }
        return layoutInflater.inflate(this.f, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        View findViewById = getActivity().findViewById(R.id.addTxtEditText);
        if (findViewById instanceof EditText) {
            this.c = (EditText) findViewById;
        }
        int i2 = this.h;
        if (this.h == 1) {
            fragment1(view);
        }
        if (this.h == 2) {
            fragment2(view);
        }
        if (this.h == 3) {
            fragment3(view);
        }
    }

    public void fragment1(View view) {
        fontTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Akadora.ttf");
        this.i = (RecyclerView) view.findViewById(R.id.recyclerViewFont);
        this.g = new LinearLayoutManager(getActivity(), 1, false);
        this.i.setLayoutManager(this.g);
        this.d = new FontProvider(getActivity().getResources());
        this.e = this.d.getFontNames();
        fontsAdapter = new FontsAdapter(getActivity(), this.e, this.d);
        this.i.setAdapter(fontsAdapter);
        this.i.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new OnItemClickListener() {
            public void onItemClick(View view, int i) {
                if (FragmentText.this.e != null && FragmentText.this.e.size() > 0) {
                    try {
                        Typeface typeface = FragmentText.this.d.getTypeface((String) FragmentText.this.e.get(i));
                        FragmentText.fontsAdapter.setSelection(i);
                        FragmentText.fontTypeface = typeface;
                        FragmentText.this.c.setTypeface(typeface);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }));
    }

    public void fragment2(View view) {
        toggleButton = (ToggleButton) view.findViewById(R.id.toggleButton);
        toggleButton.setToggleOff();
        toggleButton.setOnToggleChanged(new OnToggleChanged() {
            public void onToggle(boolean z) {
                FragmentText.bgStatus = z;
                if (!FragmentText.bgStatus) {
                    try {
                        FragmentText.this.c.setBackgroundColor(0);
                        FragmentText.this.c.getBackground().setAlpha(Math.round((((float) FragmentText.opacityBGProgress) / 100.0f) * 255.0f));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (FragmentText.currentBg == 0) {
                    try {
                        FragmentText.this.c.setBackgroundColor(FragmentText.bgColor);
                        FragmentText.this.c.getBackground().setAlpha(Math.round((((float) FragmentText.opacityBGProgress) / 100.0f) * 255.0f));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    try {
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(BitmapFactory.decodeResource(FragmentText.this.getResources(), FragmentText.bgColor));
                        bitmapDrawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
                        FragmentText.this.c.setBackgroundDrawable(bitmapDrawable);
                        FragmentText.this.c.getBackground().setAlpha(Math.round((((float) FragmentText.opacityBGProgress) / 100.0f) * 255.0f));
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }
        });
        final TextView textView = (TextView) view.findViewById(R.id.txtOpacity);
        ((SeekBar) view.findViewById(R.id.textOpacitySeekbar)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                textView.setText(String.valueOf(i));
                FragmentText.opacityTxtProgress = i;
                float f = (((float) i) / 100.0f) * 255.0f;
                FragmentText.this.c.setTextColor(FragmentText.this.c.getTextColors().withAlpha(Math.round(f)));
                FragmentText.this.c.setHintTextColor(FragmentText.this.c.getTextColors().withAlpha(Math.round(f)));
            }
        });
        final TextView textView2 = (TextView) view.findViewById(R.id.bgOpacity);
        ((SeekBar) view.findViewById(R.id.textOpacityBGSeekbar)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                FragmentText.opacityBGProgress = i;
                textView2.setText(String.valueOf(i));
                FragmentText.this.c.getBackground().setAlpha(Math.round((((float) i) / 100.0f) * 255.0f));
            }
        });
        final TextColorAdapter textColorAdapter = new TextColorAdapter(getActivity(), DataBinder.fetchTextStickerColor(), 1);
        Gallery gallery = (Gallery) view.findViewById(R.id.recyclerViewTxtColor);
        gallery.setAdapter(textColorAdapter);
        gallery.setSelection(0);
        gallery.setSpacing(1);
        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                FragmentText.currentColor = 0;
                FragmentText.txtColor = ((Integer) DataBinder.fetchTextStickerColor().get(i)).intValue();
                FragmentText.this.c.setTextColor(((Integer) DataBinder.fetchTextStickerColor().get(i)).intValue());
                FragmentText.this.c.setTextColor(FragmentText.this.c.getTextColors().withAlpha(Math.round((((float) FragmentText.opacityTxtProgress) / 100.0f) * 255.0f)));
                FragmentText.this.c.setHintTextColor(((Integer) DataBinder.fetchTextStickerColor().get(i)).intValue());
                FragmentText.this.c.setHintTextColor(FragmentText.this.c.getTextColors().withAlpha(Math.round((((float) FragmentText.opacityTxtProgress) / 100.0f) * 255.0f)));
                textColorAdapter.setSelection(i);
            }
        });
        final TextColorAdapter textColorAdapter2 = new TextColorAdapter(getActivity(), DataBinder.fetchTextStickerColor(), 1);
        Gallery gallery2 = (Gallery) view.findViewById(R.id.recyclerViewTxtBGColor);
        gallery2.setAdapter(textColorAdapter2);
        gallery2.setSelection(2);
        gallery2.setSpacing(1);
        gallery2.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                FragmentText.currentBg = 0;
                FragmentText.bgColor = ((Integer) DataBinder.fetchTextStickerColor().get(i)).intValue();
                if (FragmentText.bgStatus) {
                    try {
                        FragmentText.this.c.setBackgroundColor(FragmentText.bgColor);
                        FragmentText.this.c.getBackground().setAlpha(Math.round((((float) FragmentText.opacityBGProgress) / 100.0f) * 255.0f));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                textColorAdapter2.setSelection(i);
            }
        });
    }

    public void fragment3(View view) {
        this.b = (DiscreteSlider) view.findViewById(R.id.discreteSlider);
        this.k = (RelativeLayout) view.findViewById(R.id.tickMarkLabelsRelativeLayout);
        this.j.add(new TextShadow(0, -20, -20));
        this.j.add(new TextShadow(8, 0, 0));
        this.j.add(new TextShadow(8, 0, -6));
        this.j.add(new TextShadow(8, 6, -6));
        this.j.add(new TextShadow(8, 6, 0));
        this.j.add(new TextShadow(8, 6, 6));
        this.j.add(new TextShadow(8, 0, 6));
        this.j.add(new TextShadow(8, -6, 6));
        this.j.add(new TextShadow(8, -6, 0));
        this.j.add(new TextShadow(8, -6, -6));
        this.b.setOnDiscreteSliderChangeListener(new OnDiscreteSliderChangeListener() {
            public void onPositionChanged(int i) {
                FragmentText.shadowRadius = ((TextShadow) FragmentText.this.j.get(i)).getRadius();
                FragmentText.shadowX = ((TextShadow) FragmentText.this.j.get(i)).getLeft();
                FragmentText.shadowY = ((TextShadow) FragmentText.this.j.get(i)).getRight();
                FragmentText.this.a = i;
                FragmentText.this.c.setShadowLayer((float) FragmentText.shadowRadius, (float) FragmentText.shadowX, (float) FragmentText.shadowY, FragmentText.shadowColor);
                int childCount = FragmentText.this.k.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    try {
                        TextView textView = (TextView) FragmentText.this.k.getChildAt(i2);
                        if (i2 == i) {
                            textView.setVisibility(0);
                        } else {
                            textView.setVisibility(4);
                        }
                        textView.setShadowLayer((float) FragmentText.shadowRadius, (float) FragmentText.shadowX, (float) FragmentText.shadowY, R.color.Stroke);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        this.k.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                FragmentText.this.k.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                FragmentText.this.a();
            }
        });
        final TextColorAdapter textColorAdapter = new TextColorAdapter(getActivity(), DataBinder.fetchTextStickerColor(), 1);
        Gallery gallery = (Gallery) view.findViewById(R.id.recyclerViewTxtShadow);
        gallery.setAdapter(textColorAdapter);
        gallery.setSelection(1);
        gallery.setSpacing(1);
        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                FragmentText.shadowColor = ((Integer) DataBinder.fetchTextStickerColor().get(i)).intValue();
                FragmentText.this.c.setShadowLayer((float) FragmentText.shadowRadius, (float) FragmentText.shadowX, (float) FragmentText.shadowY, FragmentText.shadowColor);
                textColorAdapter.setSelection(i);
            }
        });
    }


    public void a() {
        int tickMarkCount = this.b.getTickMarkCount();
        float tickMarkRadius = this.b.getTickMarkRadius();
        int measuredWidth = this.k.getMeasuredWidth();
        int dp2px = DisplayUtility.dp2px(getContext(), 32);
        int dp2px2 = ((measuredWidth - (DisplayUtility.dp2px(getContext(), 32) + dp2px)) - ((int) (tickMarkRadius + tickMarkRadius))) / (tickMarkCount - 1);
        String[] strArr = {"A", "A", "A", "A", "A", "A", "A", "A", "A", "A"};
        int dp2px3 = DisplayUtility.dp2px(getContext(), 40);
        for (int i2 = 0; i2 < tickMarkCount; i2++) {
            try {
                TextView textView = new TextView(getContext());
                LayoutParams layoutParams = new LayoutParams(dp2px3, -2);
                textView.setText(strArr[i2]);
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                textView.setTextSize(35.0f);
                textView.setTypeface(Helper.txtface);
                textView.setGravity(17);
                shadowRadius = ((TextShadow) this.j.get(this.a)).getRadius();
                shadowX = ((TextShadow) this.j.get(this.a)).getLeft();
                shadowY = ((TextShadow) this.j.get(this.a)).getRight();
                if (i2 == this.a) {
                    try {
                        textView.setVisibility(0);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    try {
                        textView.setVisibility(4);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                textView.setShadowLayer((float) shadowRadius, (float) shadowX, (float) shadowY, -1);
                layoutParams.setMargins(((((int) tickMarkRadius) + dp2px) + (i2 * dp2px2)) - (dp2px3 / 2), 0, 0, 0);
                textView.setLayoutParams(layoutParams);
                this.k.addView(textView);
            } catch (NotFoundException e4) {
                e4.printStackTrace();
            }
        }
    }
}
