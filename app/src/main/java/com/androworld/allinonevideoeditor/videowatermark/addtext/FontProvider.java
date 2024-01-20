package com.androworld.allinonevideoeditor.videowatermark.addtext;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FontProvider {
    private final Map<String, String> a = new HashMap();
    private final List<String> b;
    private final Resources c;
    private final Map<String, Typeface> d = new HashMap();

    public String getDefaultFontName() {
        return "Helvetica";
    }

    public FontProvider(Resources resources) {
        this.c = resources;
        this.a.put("Text1", "Akadora.ttf");
        this.a.put("Text2", "Beatles.ttf");
        this.a.put("Text3", "Black.ttf");
        this.a.put("Text4", "Blkchry.ttf");
        this.a.put("Text5", "Calligraffitti-Regular.ttf");
        this.a.put("Text7", "Damion-Regular.ttf");
        this.a.put("Text8", "GrandHotel-Regular.ttf");
        this.a.put("Text10", "Grinched.ttf");
        this.a.put("Text11", "IndieFlower.ttf");
        this.a.put("Text12", "LeckerliOne-Regular.ttf");
        this.a.put("Text13", "Libertango.ttf");
        this.a.put("Text15", "LilyScriptOne-Regular.ttf");
        this.a.put("Text16", "MarckScript-Regular.ttf");
        this.a.put("Text18", "materialdrawerfont-font-v5.0.0.ttf");
        this.a.put("Text19", "MetalMacabre.ttf");
        this.a.put("Text20", "Niconne-Regular.ttf");
        this.a.put("Text22", "OleoScript-Regular.ttf");
        this.a.put("Text23", "ParryHotter.ttf");
        this.a.put("Text24", "PinyonScript-Regular.ttf");
        this.a.put("Text25", "Playball-Regular.ttf");
        this.b = new ArrayList(this.a.keySet());
    }

    public Typeface getTypeface(String str) {
        if (TextUtils.isEmpty(str)) {
            return Typeface.DEFAULT;
        }
        if (this.d.get(str) == null) {
            Map<String, Typeface> map = this.d;
            AssetManager assets = this.c.getAssets();
            StringBuilder sb = new StringBuilder();
            sb.append("fonts/");
            sb.append((String) this.a.get(str));
            map.put(str, Typeface.createFromAsset(assets, sb.toString()));
        }
        return (Typeface) this.d.get(str);
    }

    public List<String> getFontNames() {
        return this.b;
    }
}
