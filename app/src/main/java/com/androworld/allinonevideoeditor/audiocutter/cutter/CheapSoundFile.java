package com.androworld.allinonevideoeditor.audiocutter.cutter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class CheapSoundFile {
    static Factory[] a = {CheapAAC.getFactory(), CheapAMR.getFactory(), CheapMP3.getFactory(), CheapWAV.getFactory()};
    static ArrayList<String> b = new ArrayList<>();
    static HashMap<String, Factory> c = new HashMap<>();
    private static final char[] d = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    protected File mInputFile = null;
    protected ProgressListener mProgressListener = null;

    public interface Factory {
        CheapSoundFile create();

        String[] getSupportedExtensions();
    }

    public interface ProgressListener {
        boolean reportProgress(double d);
    }

    public void WriteFile(File file, int i, int i2) throws IOException {
    }

    public int getAvgBitrateKbps() {
        return 0;
    }

    public int getChannels() {
        return 0;
    }

    public int getFileSizeBytes() {
        return 0;
    }

    public String getFiletype() {
        return "Unknown";
    }

    public int[] getFrameGains() {
        return null;
    }

    public int[] getFrameLens() {
        return null;
    }

    public int[] getFrameOffsets() {
        return null;
    }

    public int getNumFrames() {
        return 0;
    }

    public int getSampleRate() {
        return 0;
    }

    public int getSamplesPerFrame() {
        return 0;
    }

    public int getSeekableFrameOffset(int i) {
        return -1;
    }

    static {
        Factory[] factoryArr;
        String[] supportedExtensions;
        for (Factory factory : a) {
            for (String str : factory.getSupportedExtensions()) {
                b.add(str);
                c.put(str, factory);
            }
        }
    }

    public static CheapSoundFile create(String str, ProgressListener progressListener) throws FileNotFoundException, IOException {
        File file = new File(str);
        if (!file.exists()) {
            throw new FileNotFoundException(str);
        }
        String[] split = file.getName().toLowerCase().split("\\.");
        if (split.length < 2) {
            return null;
        }
        Factory factory = (Factory) c.get(split[split.length - 1]);
        if (factory == null) {
            return null;
        }
        CheapSoundFile create = factory.create();
        create.setProgressListener(progressListener);
        create.ReadFile(file);
        return create;
    }

    public static boolean isFilenameSupported(String str) {
        String[] split = str.toLowerCase().split("\\.");
        if (split.length < 2) {
            return false;
        }
        return c.containsKey(split[split.length - 1]);
    }

    public static String[] getSupportedExtensions() {
        return (String[]) b.toArray(new String[b.size()]);
    }

    protected CheapSoundFile() {
    }

    public void ReadFile(File file) throws FileNotFoundException, IOException {
        this.mInputFile = file;
    }

    public void setProgressListener(ProgressListener progressListener) {
        this.mProgressListener = progressListener;
    }

    public static String bytesToHex(byte[] bArr) {
        char[] cArr = new char[(bArr.length * 2)];
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = i + 1;
            cArr[i] = d[(bArr[i2] >>> 4) & 15];
            i = i3 + 1;
            cArr[i3] = d[bArr[i2] & 15];
        }
        return new String(cArr);
    }

    public String computeMd5OfFirst10Frames() throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        int[] frameOffsets = getFrameOffsets();
        int[] frameLens = getFrameLens();
        int i = 10;
        int length = frameLens.length;
        if (length <= 10) {
            i = length;
        }
        MessageDigest instance = MessageDigest.getInstance("MD5");
        FileInputStream fileInputStream = new FileInputStream(this.mInputFile);
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = frameOffsets[i3] - i2;
            int i5 = frameLens[i3];
            if (i4 > 0) {
                fileInputStream.skip((long) i4);
                i2 += i4;
            }
            byte[] bArr = new byte[i5];
            fileInputStream.read(bArr, 0, i5);
            instance.update(bArr);
            i2 += i5;
        }
        fileInputStream.close();
        return bytesToHex(instance.digest());
    }
}
