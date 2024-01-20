package com.androworld.allinonevideoeditor.videocollage.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FrameInfo {
    @SerializedName("id")
    public String frameId;
    @SerializedName("type")
    public String frameType;
    @SerializedName("noofrc")
    public int noofRC;
    @SerializedName("values")
    public List<FrameRCInfo> rcValues;
}
