package com.androworld.allinonevideoeditor.listvideoandmyvideo;

public class TimeUtils {

    public class MilliSeconds {
        public static final int ONE_HOUR = 3600000;
        public static final int ONE_MINUTE = 60000;
        public static final int ONE_SECOND = 1000;

        public MilliSeconds() {
        }
    }

    public static String toFormattedTime(int i) {
        int i2 = i / 3600000;
        int i3 = i - (3600000 * i2);
        int i4 = (i3 - ((i3 / 60000) * 60000)) / 1000;
        if (i2 > 0) {
            return String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf(i2), Integer.valueOf(0), Integer.valueOf(i4)});
        }
        return String.format("%02d:%02d", new Object[]{Integer.valueOf(0), Integer.valueOf(i4)});
    }
}
