package com.androworld.allinonevideoeditor.audiocutter.cutter;

import com.google.android.gms.dynamite.descriptors.com.google.android.gms.ads.dynamite.ModuleDescriptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CheapMP3 extends CheapSoundFile {
    private static int[] p = {0, 32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, ModuleDescriptor.MODULE_VERSION, 0};
    private static int[] q = {0, 8, 16, 24, 32, 40, 48, 56, 64, 80, 96, 112, 128, 144, 160, 0};
    private static int[] r = {44100, 48000, 32000, 0};
    private static int[] s = {22050, 24000, 16000, 0};
    private int d;
    private int[] e;
    private int[] f;
    private int[] g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;

    public String getFiletype() {
        return "MP3";
    }

    public int getSamplesPerFrame() {
        return 1152;
    }

    public static Factory getFactory() {
        return new Factory() {
            public CheapSoundFile create() {
                return new CheapMP3();
            }

            public String[] getSupportedExtensions() {
                return new String[]{"mp3"};
            }
        };
    }

    public int getNumFrames() {
        return this.d;
    }

    public int[] getFrameOffsets() {
        return this.e;
    }

    public int[] getFrameLens() {
        return this.f;
    }

    public int[] getFrameGains() {
        return this.g;
    }

    public int getFileSizeBytes() {
        return this.h;
    }

    public int getAvgBitrateKbps() {
        return this.i;
    }

    public int getSampleRate() {
        return this.j;
    }

    public int getChannels() {
        return this.k;
    }

    public int getSeekableFrameOffset(int i2) {
        if (i2 <= 0) {
            return 0;
        }
        if (i2 >= this.d) {
            return this.h;
        }
        return this.e[i2];
    }

    public void ReadFile(File file) throws FileNotFoundException, IOException {
        boolean z =false;
        int i2;
        int i3;
        int i4;
        int i5;
        super.ReadFile(file);
        this.d = 0;
        this.l = 64;
        this.e = new int[this.l];
        this.f = new int[this.l];
        this.g = new int[this.l];
        this.m = 0;
        this.n = 255;
        this.o = 0;
        this.h = (int) this.mInputFile.length();
        FileInputStream fileInputStream = new FileInputStream(this.mInputFile);
        byte b = 12;
        byte[] bArr = new byte[12];
        int i6 = 0;
        int i7 = 0;
        while (i6 < this.h - b) {
            while (i7 < b) {
                i7 += fileInputStream.read(bArr, i7, 12 - i7);
            }
            int i8 = 0;
            while (i8 < b && bArr[i8] != -1) {
                i8++;
            }
            if (this.mProgressListener != null && !this.mProgressListener.reportProgress((((double) i6) * 1.0d) / ((double) this.h))) {
                break;
            } else if (i8 > 0) {
                int i9 = 0;
                while (true) {
                    i5 = 12 - i8;
                    if (i9 >= i5) {
                        break;
                    }
                    bArr[i9] = bArr[i8 + i9];
                    i9++;
                }
                i6 += i8;
                i7 = i5;
            } else {
                if (bArr[1] == -6 || bArr[1] == -5) {
                    z = true;
                } else if (bArr[1] == -14 || bArr[1] == -13) {
                    z = true;
                } else {
                    int i10 = 0;
                    while (i10 < 11) {
                        int i11 = 1 + i10;
                        bArr[i10] = bArr[i11];
                        i10 = i11;
                    }
                    i6++;
                    i7 = 11;
                }
                if (z) {
                    i3 = p[(bArr[2] & 240) >> 4];
                    i2 = r[(bArr[2] & b) >> 2];
                } else {
                    i3 = q[(bArr[2] & 240) >> 4];
                    i2 = s[(bArr[2] & b) >> 2];
                }
                if (i3 == 0 || i2 == 0) {
                    for (int i12 = 0; i12 < 10; i12++) {
                        bArr[i12] = bArr[2 + i12];
                    }
                    i6 += 2;
                    i7 = 10;
                } else {
                    this.j = i2;
                    int i13 = (((144 * i3) * 1000) / i2) + ((bArr[2] & 2) >> 1);
                    if ((bArr[3] & 192) == 192) {
                        this.k = 1;
                        i4 = z ? ((bArr[10] & 1) << 7) + ((bArr[11] & 254) >> 1) : ((bArr[9] & 3) << 6) + ((bArr[10] & 252) >> 2);
                    } else {
                        this.k = 2;
                        i4 = z ? ((bArr[9] & Byte.MAX_VALUE) << 1) + ((bArr[10] & 128) >> 7) : 0;
                    }
                    this.m += i3;
                    this.e[this.d] = i6;
                    this.f[this.d] = i13;
                    this.g[this.d] = i4;
                    if (i4 < this.n) {
                        this.n = i4;
                    }
                    if (i4 > this.o) {
                        this.o = i4;
                    }
                    this.d++;
                    if (this.d == this.l) {
                        this.i = this.m / this.d;
                        int i14 = ((((this.h / this.i) * i2) / 144000) * 11) / 10;
                        if (i14 < this.l * 2) {
                            i14 = this.l * 2;
                        }
                        int[] iArr = new int[i14];
                        int[] iArr2 = new int[i14];
                        int[] iArr3 = new int[i14];
                        for (int i15 = 0; i15 < this.d; i15++) {
                            iArr[i15] = this.e[i15];
                            iArr2[i15] = this.f[i15];
                            iArr3[i15] = this.g[i15];
                        }
                        this.e = iArr;
                        this.f = iArr2;
                        this.g = iArr3;
                        this.l = i14;
                    }
                    fileInputStream.skip((long) (i13 - 12));
                    i6 += i13;
                    i7 = 0;
                }
                b = 12;
            }
        }
        if (this.d > 0) {
            this.i = this.m / this.d;
        } else {
            this.i = 0;
        }
    }

    public void WriteFile(File file, int i2, int i3) throws IOException {
        file.createNewFile();
        FileInputStream fileInputStream = new FileInputStream(this.mInputFile);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        int i4 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            int i6 = i2 + i5;
            if (this.f[i6] > i4) {
                i4 = this.f[i6];
            }
        }
        byte[] bArr = new byte[i4];
        int i7 = 0;
        for (int i8 = 0; i8 < i3; i8++) {
            int i9 = i2 + i8;
            int i10 = this.e[i9] - i7;
            int i11 = this.f[i9];
            if (i10 > 0) {
                fileInputStream.skip((long) i10);
                i7 += i10;
            }
            fileInputStream.read(bArr, 0, i11);
            fileOutputStream.write(bArr, 0, i11);
            i7 += i11;
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
