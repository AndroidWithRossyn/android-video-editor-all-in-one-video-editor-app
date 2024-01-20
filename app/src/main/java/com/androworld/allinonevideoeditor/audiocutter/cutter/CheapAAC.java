package com.androworld.allinonevideoeditor.audiocutter.cutter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class CheapAAC extends CheapSoundFile {
    public static final int kDINF = 1684631142;
    public static final int kFTYP = 1718909296;
    public static final int kHDLR = 1751411826;
    public static final int kMDAT = 1835295092;
    public static final int kMDHD = 1835296868;
    public static final int kMDIA = 1835297121;
    public static final int kMINF = 1835626086;
    public static final int kMOOV = 1836019574;
    public static final int kMP4A = 1836069985;
    public static final int kMVHD = 1836476516;
    public static final int kSMHD = 1936549988;
    public static final int kSTBL = 1937007212;
    public static final int kSTCO = 1937007471;
    public static final int kSTSC = 1937011555;
    public static final int kSTSD = 1937011556;
    public static final int kSTSZ = 1937011578;
    public static final int kSTTS = 1937011827;
    public static final int kTKHD = 1953196132;
    public static final int kTRAK = 1953653099;
    public static final int[] kRequiredAtoms = {kDINF, kHDLR, kMDHD, kMDIA, kMINF, kMOOV, kMVHD, kSMHD, kSTBL, kSTSD, kSTSZ, kSTTS, kTKHD, kTRAK};
    public static final int[] kSaveDataAtoms = {kDINF, kHDLR, kMDHD, kMVHD, kSMHD, kTKHD, kSTSD};
    private int d;
    private int[] e;
    private int[] f;
    private int[] g;
    private int h;
    private HashMap<Integer, a> i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;

    class a {
        public int a;
        public int b;
        public byte[] c;

        a() {
        }
    }

    public String getFiletype() {
        return "AAC";
    }

    public static Factory getFactory() {
        return new Factory() {
            public CheapSoundFile create() {
                return new CheapAAC();
            }

            public String[] getSupportedExtensions() {
                return new String[]{"aac", "m4a"};
            }
        };
    }

    public int getNumFrames() {
        return this.d;
    }

    public int getSamplesPerFrame() {
        return this.m;
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
        return this.h / (this.d * this.m);
    }

    public int getSampleRate() {
        return this.k;
    }

    public int getChannels() {
        return this.l;
    }

    public String atomToString(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append((char) ((i2 >> 24) & 255));
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append((char) ((i2 >> 16) & 255));
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb4);
        sb5.append((char) ((i2 >> 8) & 255));
        String sb6 = sb5.toString();
        StringBuilder sb7 = new StringBuilder();
        sb7.append(sb6);
        sb7.append((char) (i2 & 255));
        return sb7.toString();
    }

    public void ReadFile(File file) throws FileNotFoundException, IOException {
        int[] iArr;
        super.ReadFile(file);
        this.l = 0;
        this.k = 0;
        this.j = 0;
        this.m = 0;
        this.d = 0;
        this.o = 255;
        this.p = 0;
        this.n = 0;
        this.q = -1;
        this.r = -1;
        this.i = new HashMap<>();
        this.h = (int) this.mInputFile.length();
        if (this.h < 128) {
            throw new IOException("File too small to parse");
        }
        byte[] bArr = new byte[8];
        new FileInputStream(this.mInputFile).read(bArr, 0, 8);
        if (bArr[0] == 0 && bArr[4] == 102 && bArr[5] == 116 && bArr[6] == 121 && bArr[7] == 112) {
            e(new FileInputStream(this.mInputFile), this.h);
            if (this.q <= 0 || this.r <= 0) {
                throw new IOException("Didn't find mdat");
            }
            FileInputStream fileInputStream = new FileInputStream(this.mInputFile);
            fileInputStream.skip((long) this.q);
            this.n = this.q;
            c(fileInputStream, this.r);
            boolean z = false;
            for (int i2 : kRequiredAtoms) {
                if (!this.i.containsKey(Integer.valueOf(i2))) {
                    PrintStream printStream = System.out;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Missing atom: ");
                    sb.append(atomToString(i2));
                    printStream.println(sb.toString());
                    z = true;
                }
            }
            if (z) {
                throw new IOException("Could not parse MP4 file");
            }
            return;
        }
        throw new IOException("Unknown file format");
    }

    private void e(InputStream inputStream, int i2) throws IOException {
        while (i2 > 8) {
            int i3 = this.n;
            byte[] bArr = new byte[8];
            inputStream.read(bArr, 0, 8);
            byte b = (byte) (((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8) | (bArr[3] & 255));
            if (b > i2) {
                b = (byte) i2;
            }
            byte b2 = (byte) ((bArr[7] & 255) | ((bArr[4] & 255) << 24) | ((bArr[5] & 255) << 16) | ((bArr[6] & 255) << 8));
            a aVar = new a();
            aVar.a = this.n;
            aVar.b = b;
            this.i.put(Integer.valueOf(b2), aVar);
            this.n += 8;
            if (b2 == 1836019574 || b2 == 1953653099 || b2 == 1835297121 || b2 == 1835626086 || b2 == 1937007212) {
                e(inputStream, b);
            } else if (b2 == 1937011578) {
                b(inputStream, b - 8);
            } else if (b2 == 1937011827) {
                a(inputStream, b - 8);
            } else if (b2 == 1835295092) {
                this.q = this.n;
                this.r = b - 8;
            } else {
                for (int i4 : kSaveDataAtoms) {
                    if (i4 == b2) {
                        int i5 = b - 8;
                        byte[] bArr2 = new byte[i5];
                        inputStream.read(bArr2, 0, i5);
                        this.n += i5;
                        ((a) this.i.get(Integer.valueOf(b2))).c = bArr2;
                    }
                }
            }
            if (b2 == 1937011556) {
                a();
            }
            i2 -= b;
            int i6 = b - (this.n - i3);
            if (i6 < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Went over by ");
                sb.append(-i6);
                sb.append(" bytes");
                throw new IOException(sb.toString());
            }
            inputStream.skip((long) i6);
            this.n += i6;
        }
    }


    public void a(InputStream inputStream, int i2) throws IOException {
        byte[] bArr = new byte[16];
        inputStream.read(bArr, 0, 16);
        this.n += 16;
        this.m = ((bArr[12] & 255) << 24) | ((bArr[13] & 255) << 16) | ((bArr[14] & 255) << 8) | (bArr[15] & 255);
    }


    public void b(InputStream inputStream, int i2) throws IOException {
        byte[] bArr = new byte[12];
        inputStream.read(bArr, 0, 12);
        this.n += 12;
        this.d = (bArr[11] & 255) | ((bArr[8] & 255) << 24) | ((bArr[9] & 255) << 16) | ((bArr[10] & 255) << 8);
        this.e = new int[this.d];
        this.f = new int[this.d];
        this.g = new int[this.d];
        byte[] bArr2 = new byte[(this.d * 4)];
        inputStream.read(bArr2, 0, this.d * 4);
        this.n += this.d * 4;
        for (int i3 = 0; i3 < this.d; i3++) {
            int i4 = 4 * i3;
            this.f[i3] = (bArr2[i4 + 3] & 255) | ((bArr2[i4 + 0] & 255) << 24) | ((bArr2[i4 + 1] & 255) << 16) | ((bArr2[i4 + 2] & 255) << 8);
        }
    }


    public void a() {
        byte[] bArr = ((a) this.i.get(Integer.valueOf(kSTSD))).c;
        this.l = ((bArr[32] & 255) << 8) | (bArr[33] & 255);
        this.k = (bArr[41] & 255) | ((bArr[40] & 255) << 8);
    }


    public void c(InputStream inputStream, int i2) throws IOException {
        int i3 = this.n;
        int i4 = 0;
        while (i4 < this.d) {
            this.e[i4] = this.n;
            if ((this.n - i3) + this.f[i4] > i2 - 8) {
                this.g[i4] = 0;
            } else {
                d(inputStream, i4);
            }
            if (this.g[i4] < this.o) {
                this.o = this.g[i4];
            }
            if (this.g[i4] > this.p) {
                this.p = this.g[i4];
            }
            if (this.mProgressListener == null || this.mProgressListener.reportProgress((((double) this.n) * 1.0d) / ((double) this.h))) {
                i4++;
            } else {
                return;
            }
        }
    }


    public void d(InputStream inputStream, int i2) throws IOException {
        int i3;
        int i4;
        int i5;
        byte b;
        InputStream inputStream2 = inputStream;
        if (this.f[i2] < 4) {
            this.g[i2] = 0;
            inputStream2.skip((long) this.f[i2]);
            return;
        }
        int i6 = this.n;
        byte[] bArr = new byte[4];
        inputStream2.read(bArr, 0, 4);
        this.n += 4;
        switch ((224 & bArr[0]) >> 5) {
            case 0:
                this.g[i2] = ((bArr[0] & 1) << 7) | ((bArr[1] & 254) >> 1);
                break;
            case 1:
                int i7 = (96 & bArr[1]) >> 5;
                byte b2 = bArr[1];
                if (i7 == 2) {
                    b = (byte) (bArr[1] & 15);
                    i5 = (254 & bArr[2]) >> 1;
                    i4 = ((bArr[2] & 1) << 1) | ((128 & bArr[3]) >> 7);
                    i3 = 25;
                } else {
                    b = (byte) (((bArr[1] & 15) << 2) | ((192 & bArr[2]) >> 6));
                    i5 = -1;
                    i4 = (24 & bArr[2]) >> 3;
                    i3 = 21;
                }
                if (i4 == 1) {
                    int i8 = 0;
                    for (int i9 = 0; i9 < 7; i9++) {
                        if ((i5 & (1 << i9)) == 0) {
                            i8++;
                        }
                    }
                    i3 += b * (i8 + 1);
                }
                int i10 = ((i3 + 7) / 8) + 1;
                byte[] bArr2 = new byte[i10];
                bArr2[0] = bArr[0];
                bArr2[1] = bArr[1];
                bArr2[2] = bArr[2];
                bArr2[3] = bArr[3];
                int i11 = i10 - 4;
                inputStream2.read(bArr2, 4, i11);
                this.n += i11;
                int i12 = 0;
                for (int i13 = 0; i13 < 8; i13++) {
                    int i14 = i13 + i3;
                    int i15 = i14 / 8;
                    int i16 = 7 - (i14 % 8);
                    i12 += ((bArr2[i15] & (1 << i16)) >> i16) << (7 - i13);
                }
                this.g[i2] = i12;
                break;
            default:
                if (i2 <= 0) {
                    this.g[i2] = 0;
                    break;
                } else {
                    this.g[i2] = this.g[i2 - 1];
                    break;
                }
        }
        int i17 = this.f[i2] - (this.n - i6);
        inputStream2.skip((long) i17);
        this.n += i17;
    }

    public void StartAtom(FileOutputStream fileOutputStream, int i2) throws IOException {
        int i3 = ((a) this.i.get(Integer.valueOf(i2))).b;
        fileOutputStream.write(new byte[]{(byte) ((i3 >> 24) & 255), (byte) ((i3 >> 16) & 255), (byte) ((i3 >> 8) & 255), (byte) (i3 & 255), (byte) ((i2 >> 24) & 255), (byte) ((i2 >> 16) & 255), (byte) ((i2 >> 8) & 255), (byte) (i2 & 255)}, 0, 8);
    }

    public void WriteAtom(FileOutputStream fileOutputStream, int i2) throws IOException {
        a aVar = (a) this.i.get(Integer.valueOf(i2));
        StartAtom(fileOutputStream, i2);
        fileOutputStream.write(aVar.c, 0, aVar.b - 8);
    }

    public void SetAtomData(int i2, byte[] bArr) {
        a aVar = (a) this.i.get(Integer.valueOf(i2));
        if (aVar == null) {
            aVar = new a();
            this.i.put(Integer.valueOf(i2), aVar);
        }
        aVar.b = bArr.length + 8;
        aVar.c = bArr;
    }

    public void WriteFile(File file, int i2, int i3) throws IOException {
        int i4 = i3;
        file.createNewFile();
        FileInputStream fileInputStream = new FileInputStream(this.mInputFile);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        SetAtomData(kFTYP, new byte[]{77, 52, 65, 32, 0, 0, 0, 0, 77, 52, 65, 32, 109, 112, 52, 50, 105, 115, 111, 109, 0, 0, 0, 0});
        byte b = (byte) ((i4 >> 24) & 255);
        int i5 = 8;
        byte b2 = (byte) ((i4 >> 16) & 255);
        byte b3 = (byte) ((i4 >> 8) & 255);
        byte b4 = (byte) (i4 & 255);
        SetAtomData(kSTTS, new byte[]{0, 0, 0, 0, 0, 0, 0, 1, b, b2, b3, b4, (byte) ((this.m >> 24) & 255), (byte) ((this.m >> 16) & 255), (byte) ((this.m >> 8) & 255), (byte) (this.m & 255)});
        SetAtomData(kSTSC, new byte[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, b, b2, b3, b4, 0, 0, 0, 1});
        int i6 = 4;
        int i7 = 4 * i4;
        byte[] bArr = new byte[(12 + i7)];
        bArr[8] = b;
        bArr[9] = b2;
        bArr[10] = b3;
        bArr[11] = b4;
        int i8 = 0;
        while (i8 < i4) {
            int i9 = i6 * i8;
            int i10 = i2 + i8;
            bArr[12 + i9] = (byte) ((this.f[i10] >> 24) & 255);
            bArr[13 + i9] = (byte) ((this.f[i10] >> 16) & 255);
            bArr[14 + i9] = (byte) ((this.f[i10] >> 8) & 255);
            bArr[15 + i9] = (byte) (this.f[i10] & 255);
            i8++;
            i6 = 4;
        }
        SetAtomData(kSTSZ, bArr);
        int i11 = 144 + i7 + ((a) this.i.get(Integer.valueOf(kSTSD))).b + ((a) this.i.get(Integer.valueOf(kSTSC))).b + ((a) this.i.get(Integer.valueOf(kMVHD))).b + ((a) this.i.get(Integer.valueOf(kTKHD))).b + ((a) this.i.get(Integer.valueOf(kMDHD))).b + ((a) this.i.get(Integer.valueOf(kHDLR))).b + ((a) this.i.get(Integer.valueOf(kSMHD))).b + ((a) this.i.get(Integer.valueOf(kDINF))).b;
        SetAtomData(kSTCO, new byte[]{0, 0, 0, 0, 0, 0, 0, 1, (byte) ((i11 >> 24) & 255), (byte) ((i11 >> 16) & 255), (byte) ((i11 >> 8) & 255), (byte) (i11 & 255)});
        ((a) this.i.get(Integer.valueOf(kSTBL))).b = ((a) this.i.get(Integer.valueOf(kSTSD))).b + 8 + ((a) this.i.get(Integer.valueOf(kSTTS))).b + ((a) this.i.get(Integer.valueOf(kSTSC))).b + ((a) this.i.get(Integer.valueOf(kSTSZ))).b + ((a) this.i.get(Integer.valueOf(kSTCO))).b;
        ((a) this.i.get(Integer.valueOf(kMINF))).b = ((a) this.i.get(Integer.valueOf(kDINF))).b + 8 + ((a) this.i.get(Integer.valueOf(kSMHD))).b + ((a) this.i.get(Integer.valueOf(kSTBL))).b;
        ((a) this.i.get(Integer.valueOf(kMDIA))).b = ((a) this.i.get(Integer.valueOf(kMDHD))).b + 8 + ((a) this.i.get(Integer.valueOf(kHDLR))).b + ((a) this.i.get(Integer.valueOf(kMINF))).b;
        ((a) this.i.get(Integer.valueOf(kTRAK))).b = ((a) this.i.get(Integer.valueOf(kTKHD))).b + 8 + ((a) this.i.get(Integer.valueOf(kMDIA))).b;
        ((a) this.i.get(Integer.valueOf(kMOOV))).b = ((a) this.i.get(Integer.valueOf(kMVHD))).b + 8 + ((a) this.i.get(Integer.valueOf(kTRAK))).b;
        for (int i12 = 0; i12 < i4; i12++) {
            i5 += this.f[i2 + i12];
        }
        ((a) this.i.get(Integer.valueOf(kMDAT))).b = i5;
        WriteAtom(fileOutputStream, kFTYP);
        StartAtom(fileOutputStream, kMOOV);
        WriteAtom(fileOutputStream, kMVHD);
        StartAtom(fileOutputStream, kTRAK);
        WriteAtom(fileOutputStream, kTKHD);
        StartAtom(fileOutputStream, kMDIA);
        WriteAtom(fileOutputStream, kMDHD);
        WriteAtom(fileOutputStream, kHDLR);
        StartAtom(fileOutputStream, kMINF);
        WriteAtom(fileOutputStream, kDINF);
        WriteAtom(fileOutputStream, kSMHD);
        StartAtom(fileOutputStream, kSTBL);
        WriteAtom(fileOutputStream, kSTSD);
        WriteAtom(fileOutputStream, kSTTS);
        WriteAtom(fileOutputStream, kSTSC);
        WriteAtom(fileOutputStream, kSTSZ);
        WriteAtom(fileOutputStream, kSTCO);
        StartAtom(fileOutputStream, kMDAT);
        int i13 = 0;
        for (int i14 = 0; i14 < i4; i14++) {
            int i15 = i2 + i14;
            if (this.f[i15] > i13) {
                i13 = this.f[i15];
            }
        }
        byte[] bArr2 = new byte[i13];
        int i16 = 0;
        for (int i17 = 0; i17 < i4; i17++) {
            int i18 = i2 + i17;
            int i19 = this.e[i18] - i16;
            int i20 = this.f[i18];
            if (i19 >= 0) {
                if (i19 > 0) {
                    fileInputStream.skip((long) i19);
                    i16 += i19;
                }
                fileInputStream.read(bArr2, 0, i20);
                fileOutputStream.write(bArr2, 0, i20);
                i16 += i20;
            }
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
