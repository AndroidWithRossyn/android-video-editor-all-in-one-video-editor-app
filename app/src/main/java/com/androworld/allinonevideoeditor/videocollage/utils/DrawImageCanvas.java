package com.androworld.allinonevideoeditor.videocollage.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;

import com.androworld.allinonevideoeditor.videocollage.model.BorderAttribute;
import com.androworld.allinonevideoeditor.videocollage.model.CollageData;
import com.androworld.allinonevideoeditor.videocollage.stickers.ClgSingleFingerView;
import com.androworld.allinonevideoeditor.videocollage.stickers.StickerData;

public class DrawImageCanvas {
    Paint a;
    Bitmap b;
    Context c;
    Paint d = new Paint();

    public DrawImageCanvas(Context context, int i, int i2) {
        this.c = context;
        this.b = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        this.d.setColor(((BorderFrameLayout) Utils.borderlayout.get(0)).getColor());
        this.d.setStyle(Style.STROKE);
        this.d.setStrokeWidth((float) ((BorderFrameLayout) Utils.borderlayout.get(0)).getStrokeWidth());
        this.d.setAlpha(((BorderFrameLayout) Utils.borderlayout.get(0)).getColorAlpha());
        this.a = new Paint();
        this.a.setColor(((BorderFrameLayout) Utils.borderlayout.get(0)).getColor());
        this.a.setStyle(Style.STROKE);
        this.a.setStrokeWidth((float) ((BorderFrameLayout) Utils.borderlayout.get(0)).getStrokeWidth());
        this.a.setAlpha(((BorderFrameLayout) Utils.borderlayout.get(0)).getColorAlpha());
    }

    public Bitmap drawCanvas() {
        int i;
        float f;
        float f2;
        Canvas canvas = new Canvas(this.b);
        int size = Utils.collageData.size();
        int i2 = 0;
        while (i2 < size) {
            try {
                int width = ((CollageData) Utils.collageData.get(i2)).getWidth();
                int height = ((CollageData) Utils.collageData.get(i2)).getHeight();
                int xPoint = ((CollageData) Utils.collageData.get(i2)).getXPoint();
                int yPoint = ((CollageData) Utils.collageData.get(i2)).getYPoint();
                float f3 = (float) xPoint;
                float f4 = (float) yPoint;
                canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(((CollageData) Utils.collageData.get(i2)).getVideoUrl()), width, height, false), f3, f4, null);
                int strokeWidth = ((BorderFrameLayout) Utils.borderlayout.get(0)).getStrokeWidth();
                if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderTop()) {
                    try {
                        this.a.setStrokeWidth((float) (((BorderFrameLayout) Utils.borderlayout.get(0)).getStrokeWidth() / 2));
                        if (!((BorderAttribute) Utils.borderParam.get(i2)).getBorderLeft() || !((BorderAttribute) Utils.borderParam.get(i2)).getBorderRight()) {
                            f = f4;
                            f2 = f3;
                            i = yPoint;
                            if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderLeft()) {
                                try {
                                    canvas.drawLine((float) ((strokeWidth / 4) + xPoint), f, (float) ((xPoint + width) - (strokeWidth / 2)), f, this.a);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderRight()) {
                                try {
                                    canvas.drawLine((float) ((strokeWidth / 2) + xPoint), f, (float) ((xPoint + width) - (strokeWidth / 4)), f, this.a);
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            } else {
                                try {
                                    canvas.drawLine((float) ((strokeWidth / 2) + xPoint), f, (float) ((xPoint + width) - (strokeWidth / 2)), f, this.a);
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                        } else {
                            try {
                                f = f4;
                                f2 = f3;
                                i = yPoint;
                                try {
                                    canvas.drawLine((float) ((strokeWidth / 4) + xPoint), f4, (float) ((xPoint + width) - (strokeWidth / 4)), f, this.a);
                                } catch (Exception e4) {
                                }
                            } catch (Exception e5) {
                                f = f4;
                                f2 = f3;
                                i = yPoint;
                                if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderLeft()) {
                                }
                                if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderRight()) {
                                }
                                if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderBottom()) {
                                }
                                i2++;
                            }
                        }
                    } catch (Exception e7) {
                        f = f4;
                        f2 = f3;
                        i = yPoint;
                        if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderLeft()) {
                        }
                        if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderRight()) {
                        }
                        if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderBottom()) {
                        }
                        i2++;
                    }
                } else {
                    f = f4;
                    f2 = f3;
                    i = yPoint;
                    if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderLeft() && ((BorderAttribute) Utils.borderParam.get(i2)).getBorderRight()) {
                        try {
                            canvas.drawLine((float) ((strokeWidth / 4) + xPoint), f, (float) ((xPoint + width) - (strokeWidth / 4)), f, this.d);
                        } catch (Exception e8) {
                            e8.printStackTrace();
                        }
                    } else if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderLeft()) {
                        try {
                            canvas.drawLine((float) ((strokeWidth / 4) + xPoint), f, (float) ((xPoint + width) - (strokeWidth / 2)), f, this.d);
                        } catch (Exception e9) {
                            e9.printStackTrace();
                        }
                    } else if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderRight()) {
                        try {
                            canvas.drawLine((float) ((strokeWidth / 2) + xPoint), f, (float) ((xPoint + width) - (strokeWidth / 4)), f, this.d);
                        } catch (Exception e10) {
                            e10.printStackTrace();
                        }
                    } else {
                        try {
                            canvas.drawLine((float) ((strokeWidth / 2) + xPoint), f, (float) ((xPoint + width) - (strokeWidth / 2)), f, this.d);
                        } catch (Exception e11) {
                            e11.printStackTrace();
                        }
                    }
                }
                if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderLeft()) {
                    try {
                        this.a.setStrokeWidth((float) (((BorderFrameLayout) Utils.borderlayout.get(0)).getStrokeWidth() / 2));
                        canvas.drawLine(f2, f, f2, (float) ((i + height) - 1), this.a);
                    } catch (Exception e12) {
                        e12.printStackTrace();
                    }
                } else {
                    try {
                        canvas.drawLine(f2, f, f2, (float) ((i + height) - 1), this.d);
                    } catch (Exception e13) {
                        e13.printStackTrace();
                    }
                }
                if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderRight()) {
                    try {
                        this.a.setStrokeWidth((float) (((BorderFrameLayout) Utils.borderlayout.get(0)).getStrokeWidth() / 2));
                        float f5 = (float) ((xPoint + width) - 1);
                        canvas.drawLine(f5, f, f5, (float) ((i + height) - 1), this.a);
                    } catch (Exception e14) {
                        e14.printStackTrace();
                    }
                } else {
                    float f6 = (float) ((xPoint + width) - 1);
                    try {
                        canvas.drawLine(f6, f, f6, (float) ((i + height) - 1), this.d);
                    } catch (Exception e15) {
                        e15.printStackTrace();
                    }
                }
                if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderBottom()) {
                    try {
                        this.a.setStrokeWidth((float) (((BorderFrameLayout) Utils.borderlayout.get(0)).getStrokeWidth() / 2));
                        if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderLeft() && ((BorderAttribute) Utils.borderParam.get(i2)).getBorderRight()) {
                            try {
                                float f7 = (float) ((i + height) - 1);
                                canvas.drawLine((float) ((strokeWidth / 4) + xPoint), f7, (float) ((xPoint + width) - (strokeWidth / 4)), f7, this.a);
                            } catch (Exception e16) {
                                e16.printStackTrace();
                            }
                        } else if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderLeft()) {
                            try {
                                float f8 = (float) ((i + height) - 1);
                                canvas.drawLine((float) ((strokeWidth / 4) + xPoint), f8, (float) ((xPoint + width) - (strokeWidth / 2)), f8, this.a);
                            } catch (Exception e17) {
                                e17.printStackTrace();
                            }
                        } else if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderRight()) {
                            try {
                                float f9 = (float) ((i + height) - 1);
                                canvas.drawLine((float) ((strokeWidth / 2) + xPoint), f9, (float) ((xPoint + width) - (strokeWidth / 4)), f9, this.a);
                            } catch (Exception e18) {
                                e18.printStackTrace();
                            }
                        } else {
                            try {
                                float f10 = (float) ((i + height) - 1);
                                canvas.drawLine((float) ((strokeWidth / 2) + xPoint), f10, (float) ((xPoint + width) - (strokeWidth / 2)), f10, this.a);
                            } catch (Exception e19) {
                                e19.printStackTrace();
                            }
                        }
                    } catch (Exception e20) {
                        e20.printStackTrace();
                    }
                } else if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderLeft() && ((BorderAttribute) Utils.borderParam.get(i2)).getBorderRight()) {
                    try {
                        float f11 = (float) ((i + height) - 1);
                        canvas.drawLine((float) ((strokeWidth / 4) + xPoint), f11, (float) ((xPoint + width) - (strokeWidth / 4)), f11, this.d);
                    } catch (Exception e21) {
                        e21.printStackTrace();
                    }
                } else if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderLeft()) {
                    try {
                        float f12 = (float) ((i + height) - 1);
                        canvas.drawLine((float) ((strokeWidth / 4) + xPoint), f12, (float) ((xPoint + width) - (strokeWidth / 2)), f12, this.d);
                    } catch (Exception e22) {
                        e22.printStackTrace();
                    }
                } else if (((BorderAttribute) Utils.borderParam.get(i2)).getBorderRight()) {
                    try {
                        float f13 = (float) ((i + height) - 1);
                        canvas.drawLine((float) ((strokeWidth / 2) + xPoint), f13, (float) ((xPoint + width) - (strokeWidth / 4)), f13, this.d);
                    } catch (Exception e23) {
                        e23.printStackTrace();
                    }
                } else {
                    try {
                        float f14 = (float) ((i + height) - 1);
                        canvas.drawLine((float) ((strokeWidth / 2) + xPoint), f14, (float) ((xPoint + width) - (strokeWidth / 2)), f14, this.d);
                    } catch (Exception e24) {
                        e24.printStackTrace();
                    }
                }
                i2++;
            } catch (Exception e25) {
                e25.printStackTrace();
            }
        }
        for (int i3 = 0; i3 < Utils.clgstickerviewsList.size(); i3++) {
            try {
                ClgSingleFingerView clgSingleFingerView = ((StickerData) Utils.clgstickerviewsList.get(i3)).singlefview;
                int imageX = (int) clgSingleFingerView.getImageX();
                int imageY = (int) clgSingleFingerView.getImageY();
                com.androworld.allinonevideoeditor.videocollage.BitmapUtils.Utils.width = clgSingleFingerView.getImageWidth();
                int imageHeight = clgSingleFingerView.getImageHeight();
                int rotation = (int) clgSingleFingerView.getRotation();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) clgSingleFingerView.getBitmapDrawable();
                if (bitmapDrawable.getBitmap() != null) {
                    try {
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        Matrix matrix = new Matrix();
                        matrix.postRotate((float) rotation);
                        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, com.androworld.allinonevideoeditor.videocollage.BitmapUtils.Utils.width, imageHeight, true);
                        canvas.drawBitmap(Bitmap.createBitmap(createScaledBitmap, 0, 0, createScaledBitmap.getWidth(), createScaledBitmap.getHeight(), matrix, true), (float) imageX, (float) imageY, null);
                    } catch (Exception e26) {
                        e26.printStackTrace();
                    }
                }
            } catch (Exception e27) {
                e27.printStackTrace();
            }
        }
        return this.b;
    }
}
