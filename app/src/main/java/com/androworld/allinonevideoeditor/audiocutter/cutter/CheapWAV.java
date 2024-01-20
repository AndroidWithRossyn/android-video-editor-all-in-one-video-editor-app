package com.androworld.allinonevideoeditor.audiocutter.cutter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CheapWAV extends CheapSoundFile {
    private int d;
    private int[] e;
    private int[] f;
    private int[] g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;

    public String getFiletype() {
        return "WAV";
    }

    public static Factory getFactory() {
        return new Factory() {
            public CheapSoundFile create() {
                return new CheapWAV();
            }

            public String[] getSupportedExtensions() {
                return new String[]{"wav"};
            }
        };
    }

    public int getNumFrames() {
        return this.d;
    }

    public int getSamplesPerFrame() {
        return this.j / 50;
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
        return this.i;
    }

    public int getAvgBitrateKbps() {
        return ((this.j * this.k) * 2) / 1024;
    }

    public int getSampleRate() {
        return this.j;
    }

    public int getChannels() {
        return this.k;
    }

    public void ReadFile(File file) throws FileNotFoundException, IOException {
        byte b = 0;
        byte[] bArr;
        super.ReadFile(file);
        this.i = (int) this.mInputFile.length();
        if (this.i < 128) {
            throw new IOException("File too small to parse");
        }
        FileInputStream fileInputStream = new FileInputStream(this.mInputFile);
        byte[] bArr2 = new byte[12];
        fileInputStream.read(bArr2, 0, 12);
        this.l += 12;
        if (bArr2[0] == 82) {
            byte b2 = 1;
            if (bArr2[1] == 73 && bArr2[2] == 70 && bArr2[3] == 70 && bArr2[8] == 87 && bArr2[9] == 65 && bArr2[10] == 86 && bArr2[11] == 69) {
                this.k = 0;
                this.j = 0;
                while (this.l + 8 <= this.i) {
                    byte[] bArr3 = new byte[8];
                    fileInputStream.read(bArr3, 0, 8);
                    this.l += 8;
                    int b3 = ((bArr3[7] & 255) << 24) | ((bArr3[6] & 255) << 16) | ((bArr3[5] & 255) << 8) | (bArr3[4] & 255);
                    if (bArr3[0] == 102 && bArr3[b] == 109 && bArr3[2] == 116 && bArr3[3] == 32) {
                        if (b3 < 16 || b3 > 1024) {
                            throw new IOException("WAV file has bad fmt chunk");
                        }
                        byte[] bArr4 = new byte[b3];
                        fileInputStream.read(bArr4, 0, b3);
                        this.l += b3;
                        int b4 = ((bArr4[b] & 255) << 8) | (bArr4[0] & 255);
                        this.k = ((bArr4[3] & 255) << 8) | (bArr4[2] & 255);
                        this.j = (bArr4[4] & 255) | ((bArr4[7] & 255) << 24) | ((bArr4[6] & 255) << 16) | ((bArr4[5] & 255) << 8);
                        if (b4 != b) {
                            throw new IOException("Unsupported WAV file encoding");
                        }
                    } else if (bArr3[0] != 100 || bArr3[b] != 97 || bArr3[2] != 116 || bArr3[3] != 97) {
                        fileInputStream.skip((long) b3);
                        this.l += b3;
                    } else if (this.k == 0 || this.j == 0) {
                        throw new IOException("Bad WAV file: data chunk before fmt chunk");
                    } else {
                        this.h = ((this.j * this.k) / 50) * 2;
                        this.d = ((this.h - b) + b3) / this.h;
                        this.e = new int[this.d];
                        this.f = new int[this.d];
                        this.g = new int[this.d];
                        byte[] bArr5 = new byte[this.h];
                        int i2 = 0;
                        int i3 = 0;
                        while (i2 < b3) {
                            int i4 = this.h;
                            if (i2 + i4 > b3) {
                                i2 = b3 - i4;
                            }
                            fileInputStream.read(bArr5, 0, i4);
                            int i5 = 0;
                            for (int i6 = b; i6 < i4; i6 += this.k * 4) {
                                int abs = Math.abs(bArr5[i6]);
                                if (abs > i5) {
                                    i5 = abs;
                                }
                            }
                            this.e[i3] = this.l;
                            this.f[i3] = i4;
                            this.g[i3] = i5;
                            i3 += b;
                            this.l += i4;
                            i2 += i4;
                            if (this.mProgressListener != null) {
                                bArr = bArr5;
                                if (!this.mProgressListener.reportProgress((((double) i2) * 1.0d) / ((double) b3))) {
                                    break;
                                }
                            } else {
                                bArr = bArr5;
                            }
                            bArr5 = bArr;
                            b = 1;
                        }
                    }
                    b2 = 1;
                }
                return;
            }
        }
        throw new IOException("Not a WAV file");
    }

    public void WriteFile(File file, int i2, int i3) throws IOException {
        int i4 = i3;
        file.createNewFile();
        FileInputStream fileInputStream = new FileInputStream(this.mInputFile);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        long j2 = 0;
        int i5 = 0;
        while (i5 < i4) {
            i5++;
            j2 += (long) this.f[i2 + i5];
        }
        long j3 = j2 + 36;
        long j4 = (long) this.j;
        long j5 = (long) (this.j * 2 * this.k);
        long j6 = j2;
        fileOutputStream.write(new byte[]{82, 73, 70, 70, (byte) ((int) (j3 & 255)), (byte) ((int) ((j3 >> 8) & 255)), (byte) ((int) ((j3 >> 16) & 255)), (byte) ((int) ((j3 >> 24) & 255)), 87, 65, 86, 69, 102, 109, 116, 32, 16, 0, 0, 0, 1, 0, (byte) this.k, 0, (byte) ((int) (j4 & 255)), (byte) ((int) ((j4 >> 8) & 255)), (byte) ((int) ((j4 >> 16) & 255)), (byte) ((int) ((j4 >> 24) & 255)), (byte) ((int) (j5 & 255)), (byte) ((int) ((j5 >> 8) & 255)), (byte) ((int) ((j5 >> 16) & 255)), (byte) ((int) ((j5 >> 24) & 255)), (byte) (this.k * 2), 0, 16, 0, 100, 97, 116, 97, (byte) ((int) (j6 & 255)), (byte) ((int) ((j6 >> 8) & 255)), (byte) ((int) ((j6 >> 16) & 255)), (byte) ((int) ((j6 >> 24) & 255))}, 0, 44);
        byte[] bArr = new byte[this.h];
        int i6 = 0;
        for (int i7 = 0; i7 < i4; i7++) {
            int i8 = i2 + i7;
            int i9 = this.e[i8] - i6;
            int i10 = this.f[i8];
            if (i9 >= 0) {
                if (i9 > 0) {
                    fileInputStream.skip((long) i9);
                    i6 += i9;
                }
                fileInputStream.read(bArr, 0, i10);
                fileOutputStream.write(bArr, 0, i10);
                i6 += i10;
            }
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
