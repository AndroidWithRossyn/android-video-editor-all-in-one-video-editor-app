package com.androworld.allinonevideoeditor.videocollage.stickers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.Styleable;
import com.androworld.allinonevideoeditor.videocollage.utils.Utils;

@SuppressLint({"WrongConstant"})
public class ClgSingleFingerView extends LinearLayout {
    private boolean a;
    private boolean b;
    private int c;
    private int d;
    private Drawable e;
    private Drawable f;
    private Drawable g;
    private float h;
    private float i;
    private float j;
    private float k;
    private float l;
    public ImageView mPushDelete;
    public ImageView mPushView;
    public ImageView mView;

    public ClgSingleFingerView(Context context) {
        this(context, null, 0);
    }

    public void setDrawable(Drawable drawable) {
        this.e = drawable;
        this.i = (float) (this.e.getIntrinsicWidth() + 100);
        this.j = (float) (this.e.getIntrinsicHeight() + 100);
        a(1000, 1000);
    }

    public Drawable getBitmapDrawable() {
        return this.e;
    }

    public float getRotation() {
        return this.mView.getRotation();
    }

    public float getImageX() {
        return (float) ((LayoutParams) this.mView.getLayoutParams()).leftMargin;
    }

    public float getImageY() {
        return (float) ((LayoutParams) this.mView.getLayoutParams()).topMargin;
    }

    public int getImageWidth() {
        return ((LayoutParams) this.mView.getLayoutParams()).width;
    }

    public int getImageHeight() {
        return ((LayoutParams) this.mView.getLayoutParams()).height;
    }

    public ClgSingleFingerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public ClgSingleFingerView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.a = true;
        this.d = 0;
        this.c = 0;
        this.b = false;
        this.h = TypedValue.applyDimension(1, 1.0f, context.getResources().getDisplayMetrics());
        a(context, attributeSet);
        View inflate = View.inflate(context, R.layout.test_image_view_with_delete, null);
        addView(inflate, -1, -1);
        this.mPushView = (ImageView) inflate.findViewById(R.id.push_view);
        this.mPushDelete = (ImageView) inflate.findViewById(R.id.push_delete);
        this.mView = (ImageView) inflate.findViewById(R.id.view);
        this.mPushView.setOnTouchListener(new b(this.mView));
        this.mPushDelete.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    try {
                        ClgSingleFingerView.this.setVisibility(4);
                        ClgTagView clgTagView = (ClgTagView) ClgSingleFingerView.this.getTag();
                        int i = 0;
                        while (i < Utils.clgstickerviewsList.size()) {
                            try {
                                if (((StickerData) Utils.clgstickerviewsList.get(i)).pos == clgTagView.getPos()) {
                                    Utils.clgstickerviewsList.remove(i);
                                }
                                i++;
                            } catch (Exception unused) {
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
        this.mView.setOnTouchListener(new a(this.mPushView, this.mPushDelete));
    }

    private void a(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.SingleFingerView);
            if (obtainStyledAttributes != null) {
                try {
                    int indexCount = obtainStyledAttributes.getIndexCount();
                    int i2 = 0;
                    while (i2 < indexCount) {
                        try {
                            int index = obtainStyledAttributes.getIndex(i2);
                            if (index == 9) {
                                try {
                                    this.a = obtainStyledAttributes.getBoolean(index, true);
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                i2++;
                            } else if (index == 0) {
                                try {
                                    this.e = obtainStyledAttributes.getDrawable(index);
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                                i2++;
                            } else if (index == 2) {
                                try {
                                    this.j = obtainStyledAttributes.getDimension(index, this.h * 200.0f);
                                } catch (Exception e4) {
                                    e4.printStackTrace();
                                }
                                i2++;
                            } else if (index == 1) {
                                try {
                                    this.i = obtainStyledAttributes.getDimension(index, this.h * 200.0f);
                                } catch (Exception e5) {
                                    e5.printStackTrace();
                                }
                                i2++;
                            } else if (index == 3) {
                                try {
                                    this.f = obtainStyledAttributes.getDrawable(index);
                                } catch (Exception e6) {
                                    e6.printStackTrace();
                                }
                                i2++;
                            } else if (index == 4) {
                                try {
                                    this.g = obtainStyledAttributes.getDrawable(index);
                                } catch (Exception e7) {
                                    e7.printStackTrace();
                                }
                                i2++;
                            } else if (index == 5) {
                                try {
                                    this.k = obtainStyledAttributes.getDimension(index, this.h * 50.0f);
                                } catch (Exception e8) {
                                    e8.printStackTrace();
                                }
                                i2++;
                            } else if (index == 6) {
                                try {
                                    this.l = obtainStyledAttributes.getDimension(index, this.h * 50.0f);
                                } catch (Exception e9) {
                                    e9.printStackTrace();
                                }
                                i2++;
                            } else if (index == 8) {
                                try {
                                    this.d = (int) obtainStyledAttributes.getDimension(index, this.h * 0.0f);
                                } catch (Exception e10) {
                                    e10.printStackTrace();
                                }
                                i2++;
                            } else {
                                if (index == 7) {
                                    try {
                                        this.c = (int) obtainStyledAttributes.getDimension(index, this.h * 0.0f);
                                    } catch (Exception e11) {
                                        e11.printStackTrace();
                                    }
                                }
                                i2++;
                            }
                        } catch (Exception e12) {
                            e12.printStackTrace();
                        }
                    }
                } catch (Exception e13) {
                    e13.printStackTrace();
                }
            }
        }
    }

    private void a(int i2, int i3) {
        int i4;
        int i5;
        if (this.e != null) {
            try {
                this.mView.setBackgroundDrawable(this.e);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (this.g != null) {
            try {
                this.mPushDelete.setBackgroundDrawable(this.g);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (this.f != null) {
            try {
                this.mPushView.setBackgroundDrawable(this.f);
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        LayoutParams layoutParams = (LayoutParams) this.mView.getLayoutParams();
        layoutParams.width = (int) this.i;
        layoutParams.height = (int) this.j;
        int i6 = 0;
        if (this.a) {
            try {
                i4 = (i2 / 2) - (layoutParams.width / 2);
                try {
                    i6 = (i3 / 2) - (layoutParams.height / 2);
                } catch (Exception e5) {
                    layoutParams.leftMargin = i4;
                    layoutParams.topMargin = i6;
                    this.mView.setLayoutParams(layoutParams);
                    LayoutParams layoutParams2 = (LayoutParams) this.mPushView.getLayoutParams();
                    layoutParams2.width = (int) this.k;
                    layoutParams2.height = (int) this.l;
                    layoutParams2.leftMargin = (int) ((((float) layoutParams.leftMargin) + this.i) - (this.k / 2.0f));
                    layoutParams2.topMargin = (int) ((((float) layoutParams.topMargin) + this.j) - (this.l / 2.0f));
                    this.mPushView.setLayoutParams(layoutParams2);
                    LayoutParams layoutParams3 = (LayoutParams) this.mPushDelete.getLayoutParams();
                    layoutParams3.width = (int) this.k;
                    layoutParams3.height = (int) this.l;
                    layoutParams3.leftMargin = (int) (((float) (layoutParams.leftMargin + layoutParams.width)) - (this.k / 2.0f));
                    layoutParams3.topMargin = (int) (((float) layoutParams.topMargin) + (this.l / 2.0f));
                    this.mPushDelete.setLayoutParams(layoutParams3);
                }
            } catch (Exception e6) {
                i4 = 0;
                layoutParams.leftMargin = i4;
                layoutParams.topMargin = i6;
                this.mView.setLayoutParams(layoutParams);
                LayoutParams layoutParams22 = (LayoutParams) this.mPushView.getLayoutParams();
                layoutParams22.width = (int) this.k;
                layoutParams22.height = (int) this.l;
                layoutParams22.leftMargin = (int) ((((float) layoutParams.leftMargin) + this.i) - (this.k / 2.0f));
                layoutParams22.topMargin = (int) ((((float) layoutParams.topMargin) + this.j) - (this.l / 2.0f));
                this.mPushView.setLayoutParams(layoutParams22);
                LayoutParams layoutParams32 = (LayoutParams) this.mPushDelete.getLayoutParams();
                layoutParams32.width = (int) this.k;
                layoutParams32.height = (int) this.l;
                layoutParams32.leftMargin = (int) (((float) (layoutParams.leftMargin + layoutParams.width)) - (this.k / 2.0f));
                layoutParams32.topMargin = (int) (((float) layoutParams.topMargin) + (this.l / 2.0f));
                this.mPushDelete.setLayoutParams(layoutParams32);
            }
        } else {
            try {
                if (this.d > 0) {
                    try {
                        i4 = this.d;
                    } catch (Exception e7) {
                        e7.printStackTrace();
                    }
                    if (this.c > 0) {
                        try {
                            i6 = this.c;
                        } catch (Exception e8) {
                            e8.printStackTrace();
                        }
                    }
                }
                i4 = 0;
                try {
                    if (this.c > 0) {
                    }
                } catch (Exception e9) {
                    Exception exc = e9;
                    i5 = i4;
                }
            } catch (Exception e10) {
                i5 = 0;
                i4 = i5;
                layoutParams.leftMargin = i4;
                layoutParams.topMargin = i6;
                this.mView.setLayoutParams(layoutParams);
                LayoutParams layoutParams222 = (LayoutParams) this.mPushView.getLayoutParams();
                layoutParams222.width = (int) this.k;
                layoutParams222.height = (int) this.l;
                layoutParams222.leftMargin = (int) ((((float) layoutParams.leftMargin) + this.i) - (this.k / 2.0f));
                layoutParams222.topMargin = (int) ((((float) layoutParams.topMargin) + this.j) - (this.l / 2.0f));
                this.mPushView.setLayoutParams(layoutParams222);
                LayoutParams layoutParams322 = (LayoutParams) this.mPushDelete.getLayoutParams();
                layoutParams322.width = (int) this.k;
                layoutParams322.height = (int) this.l;
                layoutParams322.leftMargin = (int) (((float) (layoutParams.leftMargin + layoutParams.width)) - (this.k / 2.0f));
                layoutParams322.topMargin = (int) (((float) layoutParams.topMargin) + (this.l / 2.0f));
                this.mPushDelete.setLayoutParams(layoutParams322);
            }
        }
        layoutParams.leftMargin = i4;
        layoutParams.topMargin = i6;
        this.mView.setLayoutParams(layoutParams);
        LayoutParams layoutParams2222 = (LayoutParams) this.mPushView.getLayoutParams();
        layoutParams2222.width = (int) this.k;
        layoutParams2222.height = (int) this.l;
        layoutParams2222.leftMargin = (int) ((((float) layoutParams.leftMargin) + this.i) - (this.k / 2.0f));
        layoutParams2222.topMargin = (int) ((((float) layoutParams.topMargin) + this.j) - (this.l / 2.0f));
        this.mPushView.setLayoutParams(layoutParams2222);
        LayoutParams layoutParams3222 = (LayoutParams) this.mPushDelete.getLayoutParams();
        layoutParams3222.width = (int) this.k;
        layoutParams3222.height = (int) this.l;
        layoutParams3222.leftMargin = (int) (((float) (layoutParams.leftMargin + layoutParams.width)) - (this.k / 2.0f));
        layoutParams3222.topMargin = (int) (((float) layoutParams.topMargin) + (this.l / 2.0f));
        this.mPushDelete.setLayoutParams(layoutParams3222);
    }


    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        b(i2, i3);
    }

    private void b(int i2, int i3) {
        int i4;
        int i5 = 0;
        if (getLayoutParams() != null && !this.b) {
            try {
                this.b = true;
                if (getLayoutParams().width == -1) {
                    try {
                        i4 = MeasureSpec.getSize(i2);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        i4 = 0;
                        if (getLayoutParams().height == -1) {
                        }
                        a(i4, i5);
                    }
                } else {
                    try {
                        i4 = getLayoutParams().width;
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        i4 = 0;
                        if (getLayoutParams().height == -1) {
                        }
                        a(i4, i5);
                    }
                }
                if (getLayoutParams().height == -1) {
                    try {
                        i5 = MeasureSpec.getSize(i3);
                    } catch (Exception e4) {
                        e4.printStackTrace();
                        i5 = 0;
                        a(i4, i5);
                    }
                } else {
                    try {
                        i5 = getLayoutParams().height;
                    } catch (Exception e5) {
                        e5.printStackTrace();
                        i5 = 0;
                        a(i4, i5);
                    }
                }
                a(i4, i5);
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            hidePushView();
        }
        return super.onTouchEvent(motionEvent);
    }

    public void hidePushView() {
        this.mPushView.setVisibility(8);
        this.mPushDelete.setVisibility(8);
    }

    public void showPushView() {
        this.mPushView.setVisibility(0);
        this.mPushDelete.setVisibility(0);
    }
}
