package com.androworld.allinonevideoeditor.audiocutter.cutter;

import androidx.core.view.PointerIconCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class CheapAMR extends CheapSoundFile {
    private static int[] n = {12, 13, 15, 17, 19, 20, 26, 31, 5, 0, 0, 0, 0, 0, 0, 0};
    private static int[] o = {28753, 2785, 6594, 7413, 10444, 1269, 4423, 1556, 12820, 2498, 4833, 2498, 7864, 1884, 3153, 1802, 20193, 3031, 5857, 4014, 8970, 1392, 4096, 655, 13926, 3112, 4669, 2703, 6553, 901, 2662, 655, 23511, 2457, 5079, 4096, 8560, 737, 4259, 2088, 12288, 1474, 4628, 1433, 7004, 737, 2252, 1228, 17326, 2334, 5816, 3686, 8601, 778, 3809, 614, 9256, 1761, 3522, 1966, 5529, 737, 3194, 778};
    private static int[] p = {17333, -3431, 4235, 5276, 8325, -10422, 683, -8609, 10148, -4398, 1472, -4398, 5802, -6907, -2327, -7303, 14189, -2678, 3181, -180, 6972, -9599, 0, -16305, 10884, -2444, 1165, -3697, 4180, -13468, -3833, -16305, 15543, -4546, 1913, 0, 6556, -15255, 347, -5993, 9771, -9090, 1086, -9341, 4772, -15255, -5321, -10714, 12827, -5002, 3118, -938, 6598, -14774, -646, -16879, 7251, -7508, -1343, -6529, 2668, -15255, -2212, -2454, -14774};
    private static int[] q = {159, -3776, -22731, 206, -3394, -20428, 268, -3005, -18088, 349, -2615, -15739, 419, -2345, -14113, 482, -2138, -12867, 554, -1932, -11629, 637, -1726, -10387, 733, -1518, -9139, 842, -1314, -7906, 969, -1106, -6656, 1114, -900, -5416, 1281, -694, -4173, 1473, -487, -2931, 1694, -281, -1688, 1948, -75, -445, 2241, 133, 801, 2577, 339, 2044, 2963, 545, 3285, 3408, 752, 4530, 3919, 958, 5772, 4507, 1165, 7016, 5183, 1371, 8259, 5960, 1577, 9501, 6855, 1784, 10745, 7883, 1991, 11988, 9065, 2197, 13231, 10425, 2404, 14474, 12510, 2673, 16096, 16263, 3060, 18429, 21142, 3448, 20763, 27485, 3836, 23097};
    private static int[] r = {812, 128, 542, 140, 2873, 1135, 2266, 3402, 2067, 563, 12677, 647, 4132, 1798, 5601, 5285, 7689, 374, 3735, 441, 10912, 2638, 11807, 2494, 20490, 797, 5218, 675, 6724, 8354, 5282, 1696, 1488, 428, 5882, 452, 5332, 4072, 3583, 1268, 2469, 901, 15894, 1005, 14982, 3271, 10331, 4858, 3635, 2021, 2596, 835, 12360, 4892, 12206, 1704, 13432, 1604, 9118, 2341, 3968, 1538, 5479, 9936, 3795, 417, 1359, 414, 3640, 1569, 7995, 3541, 11405, 645, 8552, 635, 4056, 1377, 16608, 6124, 11420, 700, 2007, 607, 12415, 1578, 11119, 4654, 13680, 1708, 11990, 1229, 7996, 7297, 13231, 5715, 2428, 1159, 2073, 1941, 6218, 6121, 3546, 1804, 8925, 1802, 8679, 1580, 13935, 3576, 13313, 6237, 6142, 1130, 5994, 1734, 14141, 4662, 11271, 3321, 12226, 1551, 13931, 3015, 5081, 10464, 9444, 6706, 1689, 683, 1436, 1306, 7212, 3933, 4082, 2713, 7793, 704, 15070, 802, 6299, 5212, 4337, 5357, 6676, 541, 6062, 626, 13651, 3700, 11498, 2408, 16156, 716, 12177, 751, 8065, 11489, 6314, 2256, 4466, 496, 7293, 523, 10213, 3833, 8394, 3037, 8403, 966, 14228, 1880, 8703, 5409, 16395, 4863, 7420, 1979, 6089, 1230, 9371, 4398, 14558, 3363, 13559, 2873, 13163, 1465, 5534, 1678, 13138, 14771, 7338, 600, 1318, 548, 4252, 3539, 10044, 2364, 10587, 622, 13088, 669, 14126, 3526, 5039, 9784, 15338, 619, 3115, 590, 16442, 3013, 15542, 4168, 15537, 1611, 15405, 1228, 16023, 9299, 7534, 4976, 1990, 1213, 11447, 1157, 12512, 5519, 9475, 2644, 7716, 2034, 13280, 2239, 16011, 5093, 8066, 6761, 10083, 1413, 5002, 2347, 12523, 5975, 15126, 2899, 18264, 2289, 15827, 2527, 16265, 10254, 14651, 11319, 1797, 337, 3115, 397, 3510, 2928, 4592, 2670, 7519, 628, 11415, 656, 5946, 2435, 6544, 7367, 8238, 829, 4000, 863, 10032, 2492, 16057, 3551, 18204, 1054, 6103, 1454, 5884, 7900, 18752, 3468, 1864, 544, 9198, 683, 11623, 4160, 4594, 1644, 3158, 1157, 15953, 2560, 12349, 3733, 17420, 5260, 6106, 2004, 2917, 1742, 16467, 5257, 16787, 1680, 17205, 1759, 4773, 3231, 7386, 6035, 14342, 10012, 4035, 442, 4194, 458, 9214, 2242, 7427, 4217, 12860, 801, 11186, 825, 12648, 2084, 12956, 6554, 9505, 996, 6629, 985, 10537, 2502, 15289, 5006, 12602, 2055, 15484, 1653, 16194, 6921, 14231, 5790, 2626, 828, 5615, 1686, 13663, 5778, 3668, 1554, 11313, 2633, 9770, 1459, 14003, 4733, 15897, 6291, 6278, 1870, 7910, 2285, 16978, 4571, 16576, 3849, 15248, 2311, 16023, 3244, 14459, 17808, 11847, 2763, 1981, 1407, 1400, 876, 4335, 3547, 4391, 4210, 5405, 680, 17461, 781, 6501, 5118, 8091, 7677, 7355, 794, 8333, 1182, 15041, 3160, 14928, 3039, 20421, 880, 14545, 852, 12337, 14708, 6904, 1920, 4225, 933, 8218, 1087, 10659, 4084, 10082, 4533, 2735, 840, 20657, 1081, 16711, 5966, 15873, 4578, 10871, 2574, 3773, 1166, 14519, 4044, 20699, 2627, 15219, 2734, 15274, 2186, 6257, 3226, 13125, 19480, 7196, 930, 2462, 1618, 4515, 3092, 13852, 4277, 10460, 833, 17339, 810, 16891, 2289, 15546, 8217, 13603, 1684, 3197, 1834, 15948, 2820, 15812, 5327, 17006, 2438, 16788, 1326, 15671, 8156, 11726, 8556, 3762, 2053, 9563, 1317, 13561, 6790, 12227, 1936, 8180, 3550, 13287, 1778, 16299, 6599, 16291, 7758, 8521, 2551, 7225, 2645, 18269, 7489, 16885, 2248, 17882, 2884, 17265, 3328, 9417, 20162, 11042, 8320, 1286, 620, 1431, 583, 5993, 2289, 3978, 3626, 5144, 752, 13409, 830, 5553, 2860, 11764, 5908, 10737, 560, 5446, 564, 13321, 3008, 11946, 3683, 19887, 798, 9825, 728, 13663, 8748, 7391, 3053, 2515, 778, 6050, 833, 6469, 5074, 8305, 2463, 6141, 1865, 15308, 1262, 14408, 4547, 13663, 4515, 3137, 2983, 2479, 1259, 15088, 4647, 15382, 2607, 14492, 2392, 12462, 2537, 7539, 2949, 12909, 12060, 5468, 684, 3141, 722, 5081, 1274, 12732, 4200, 15302, 681, 7819, 592, 6534, 2021, 16478, 8737, 13364, 882, 5397, 899, 14656, 2178, 14741, 4227, 14270, 1298, 13929, 2029, 15477, 7482, 15815, 4572, 2521, 2013, 5062, 1804, 5159, 6582, 7130, 3597, 10920, 1611, 11729, 1708, 16903, 3455, 16268, 6640, 9306, PointerIconCompat.TYPE_CROSSHAIR, 9369, 2106, 19182, 5037, 12441, 4269, 15919, 1332, 15357, 3512, 11898, 14141, 16101, 6854, 2010, 737, 3779, 861, 11454, 2880, 3564, 3540, 9057, 1241, 12391, 896, 8546, 4629, 11561, 5776, 8129, 589, 8218, 588, 18728, 3755, 12973, 3149, 15729, 758, 16634, 754, 15222, 11138, 15871, 2208, 4673, 610, 10218, 678, 15257, 4146, 5729, 3327, 8377, 1670, 19862, 2321, 15450, 5511, 14054, 5481, 5728, 2888, 7580, 1346, 14384, 5325, 16236, 3950, 15118, 3744, 15306, 1435, 14597, 4070, 12301, 15696, 7617, 1699, 2170, 884, 4459, 4567, 18094, 3306, 12742, 815, 14926, 907, 15016, 4281, 15518, 8368, 17994, 1087, 2358, 865, 16281, 3787, 15679, 4596, 16356, 1534, 16584, 2210, 16833, 9697, 15929, 4513, 3277, 1085, 9643, 2187, 11973, 6068, 9199, 4462, 8955, 1629, 10289, 3062, 16481, 5155, 15466, 7066, 13678, 2543, 5273, 2277, 16746, 6213, 16655, 3408, 20304, 3363, 18688, 1985, 14172, 12867, 15154, 15703, 4473, PointerIconCompat.TYPE_GRAB, 1681, 886, 4311, 4301, 8952, 3657, 5893, 1147, 11647, 1452, 15886, 2227, 4582, 6644, 6929, 1205, 6220, 799, 12415, 3409, 15968, 3877, 19859, 2109, 9689, 2141, 14742, 8830, 14480, 2599, 1817, 1238, 7771, 813, 19079, 4410, 5554, 2064, 3687, 2844, 17435, 2256, 16697, 4486, 16199, 5388, 8028, 2763, 3405, 2119, 17426, 5477, 13698, 2786, 19879, 2720, 9098, 3880, 18172, 4833, 17336, 12207, 5116, 996, 4935, 988, 9888, 3081, 6014, 5371, 15881, 1667, 8405, 1183, 15087, 2366, 19777, 7002, 11963, 1562, 7279, 1128, 16859, 1532, 15762, 5381, 14708, 2065, 20105, 2155, 17158, 8245, 17911, 6318, 5467, 1504, 4100, 2574, 17421, 6810, 5673, 2888, 16636, 3382, 8975, 1831, 20159, 4737, 19550, 7294, 6658, 2781, 11472, 3321, 19397, 5054, 18878, 4722, 16439, 2373, 20430, 4386, 11353, 26526, 11593, 3068, 2866, 1566, 5108, 1070, 9614, 4915, 4939, 3536, 7541, 878, 20717, 851, 6938, 4395, 16799, 7733, 10137, PointerIconCompat.TYPE_ZOOM_OUT, 9845, 964, 15494, 3955, 15459, 3430, 18863, 982, 20120, 963, 16876, 12887, 14334, 4200, 6599, 1220, 9222, 814, 16942, 5134, 5661, 4898, 5488, 1798, 20258, 3962, 17005, 6178, 17929, 5929, 9365, 3420, 7474, 1971, 19537, 5177, 19003, 3006, 16454, 3788, 16070, 2367, 8664, 2743, 9445, 26358, 10856, 1287, 3555, PointerIconCompat.TYPE_VERTICAL_TEXT, 5606, 3622, 19453, 5512, 12453, 797, 20634, 911, 15427, 3066, 17037, 10275, 18883, 2633, 3913, 1268, 19519, 3371, 18052, 5230, 19291, 1678, 19508, 3172, 18072, 10754, 16625, 6845, 3134, 2298, 10869, 2437, 15580, 6913, 12597, 3381, 11116, 3297, 16762, 2424, 18853, 6715, 17171, 9887, 12743, 2605, 8937, 3140, 19033, 7764, 18347, 3880, 20475, 3682, 19602, 3380, 13044, 19373, 10526, 23124};
    private static int[] s = {0, 1, 3, 2, 5, 6, 4, 7};
    private static int[] t = {0, 3277, 6556, 8192, 9830, 11469, 12288, 13107, 13926, 14746, 15565, 16384, 17203, 18022, 18842, 19661};
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

    @Override
    public int getChannels() {
        return 1;
    }
    @Override
    public String getFiletype() {
        return "AMR";
    }
    @Override
    public int getSampleRate() {
        return 8000;
    }
    @Override
    public int getSamplesPerFrame() {
        return 40;
    }

    public static Factory getFactory() {
        return new Factory() {
            public CheapSoundFile create() {
                return new CheapAMR();
            }

            public String[] getSupportedExtensions() {
                return new String[]{"3gpp", "3gp", "amr"};
            }
        };
    }
    @Override
    public int getNumFrames() {
        return this.d;
    }
    @Override
    public int[] getFrameOffsets() {
        return this.e;
    }
    @Override
    public int[] getFrameLens() {
        return this.f;
    }
    @Override
    public int[] getFrameGains() {
        return this.g;
    }
    @Override
    public int getFileSizeBytes() {
        return this.h;
    }
    @Override
    public int getAvgBitrateKbps() {
        return this.i;
    }
    @Override
    public void ReadFile(File file) throws FileNotFoundException, IOException {
        super.ReadFile(file);
        this.d = 0;
        this.k = 64;
        this.e = new int[this.k];
        this.f = new int[this.k];
        this.g = new int[this.k];
        this.l = 1000000000;
        this.m = 0;
        this.i = 10;
        this.j = 0;
        this.h = (int) this.mInputFile.length();
        if (this.h < 128) {
            throw new IOException("File too small to parse");
        }
        FileInputStream fileInputStream = new FileInputStream(this.mInputFile);
        byte[] bArr = new byte[12];
        fileInputStream.read(bArr, 0, 6);
        this.j += 6;
        if (bArr[0] == 35 && bArr[1] == 33 && bArr[2] == 65 && bArr[3] == 77 && bArr[4] == 82 && bArr[5] == 10) {
            a(fileInputStream, this.h - 6);
        }
        fileInputStream.read(bArr, 6, 6);
        this.j += 6;
        if (bArr[4] == 102 && bArr[5] == 116 && bArr[6] == 121 && bArr[7] == 112 && bArr[8] == 51 && bArr[9] == 103 && bArr[10] == 112 && bArr[11] == 52) {
            int b = ((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8) | (255 & bArr[3]);
            if (b >= 4 && b <= this.h - 8) {
                int i2 = b - 12;
                fileInputStream.skip((long) i2);
                this.j += i2;
            }
            b(fileInputStream, this.h - b);
        }
    }

    private void b(InputStream inputStream, int i2) throws IOException {
        if (i2 >= 8) {
            byte[] bArr = new byte[8];
            inputStream.read(bArr, 0, 8);
            this.j += 8;
            int b = ((bArr[2] & 255) << 8) | ((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | (bArr[3] & 255);
            if (b <= i2 && b > 0) {
                if (bArr[4] == 109 && bArr[5] == 100 && bArr[6] == 97 && bArr[7] == 116) {
                    a(inputStream, b);
                    return;
                }
                int i3 = b - 8;
                inputStream.skip((long) i3);
                this.j += i3;
                b(inputStream, i2 - b);
            }
        }
    }


    public void a(InputStream inputStream, int i2) throws IOException {
        int[] iArr = new int[4];
        int i3 = 0;
        for (int i4 = 0; i4 < 4; i4++) {
            iArr[i4] = 0;
        }
        int[] iArr2 = new int[4];
        for (int i5 = 0; i5 < 4; i5++) {
            iArr2[i5] = -2381;
        }
        int i6 = i2;
        while (i6 > 0) {
            int a = a(inputStream, i6, iArr);
            i3 += a;
            i6 -= a;
            if (this.mProgressListener != null && !this.mProgressListener.reportProgress((((double) i3) * 1.0d) / ((double) i2))) {
                return;
            }
        }
    }

    public int a(InputStream inputStream, int i2, int[] iArr) throws IOException {
        int i3;
        int i4;
        int i5;
        int i6;
        int r9 = 0;
        InputStream inputStream2 = inputStream;
        int i7 = i2;
        int i8 = this.j;
        byte[] bArr = new byte[1];
        inputStream2.read(bArr, 0, 1);
        this.j++;
        int i9 = ((bArr[0] & 255) >> 3) % 15;
        int i10 = n[i9];
        int i11 = i10 + 1;
        if (i11 > i7) {
            return i7;
        }
        if (i10 == 0) {
            return 1;
        }
        byte[] bArr2 = new byte[i10];
        inputStream2.read(bArr2, 0, i10);
        this.j += i10;
        int i12 = i10 * 8;
        int[] iArr2 = new int[i12];
        int i13 = 0;
        int b2 = bArr2[0] & 255;
        for (int i14 = 0; i14 < i12; i14++) {
            iArr2[i14] = (b2 & 128) >> 7;
            if ((i14 & 7) == 7 && i14 < i12 - 1) {
                i13++;
                r9 = bArr2[i13] & 255;

            }
            b2 = r9;
        }
        int i16 = 40;
        int i17 = 5;
        if (i9 != 7) {
            switch (i9) {
                case 0:
                    this.i = 5;
                    int[] iArr3 = new int[4];
                    iArr3[0] = (iArr2[28] * 1) + (iArr2[29] * 2) + (iArr2[30] * 4) + (iArr2[31] * 8) + (iArr2[46] * 16) + (iArr2[47] * 32) + (64 * iArr2[48]) + (iArr2[49] * 128);
                    iArr3[1] = iArr3[0];
                    iArr3[2] = (iArr2[32] * 1) + (iArr2[33] * 2) + (iArr2[34] * 4) + (8 * iArr2[35]) + (iArr2[40] * 16) + (32 * iArr2[41]) + (64 * iArr2[42]) + (128 * iArr2[43]);
                    iArr3[3] = iArr3[2];
                    int i18 = 0;
                    for (int i19 = 4; i18 < i19; i19 = 4) {
                        int i20 = r[(iArr3[i18] * i19) + ((i18 & 1) * 2) + 1];
                        int i21 = i20;
                        double log = Math.log((double) i20) / Math.log(2.0d);
                        int i22 = (int) log;
                        int i23 = (((((i22 - 12) * 49320) + (((((int) ((log - ((double) i22)) * 32768.0d)) * 24660) >> 15) * 2)) * 8192) + 32768) >> 16;
                        int i24 = ((((385963008 + (iArr[0] * 5571)) + (iArr[1] * 4751)) + (iArr[2] * 2785)) + (iArr[3] * 1556)) >> 15;
                        iArr[3] = iArr[2];
                        iArr[2] = iArr[1];
                        iArr[1] = iArr[0];
                        iArr[0] = i23;
                        a(i8, i11, (i24 * i21) >> 24);
                        i18++;
                    }
                    break;
                case 1:
                    this.i = 5;
                    int[] iArr4 = {(iArr2[24] * 1) + (iArr2[25] * 2) + (iArr2[26] * 4) + (iArr2[36] * 8) + (iArr2[45] * 16) + (iArr2[55] * 32), (iArr2[27] * 1) + (iArr2[28] * 2) + (iArr2[29] * 4) + (iArr2[37] * 8) + (iArr2[46] * 16) + (iArr2[56] * 32), (iArr2[30] * 1) + (iArr2[31] * 2) + (iArr2[32] * 4) + (iArr2[38] * 8) + (iArr2[47] * 16) + (iArr2[57] * 32), (iArr2[33] * 1) + (iArr2[34] * 2) + (iArr2[35] * 4) + (8 * iArr2[39]) + (16 * iArr2[48]) + (32 * iArr2[58])};
                    for (int i25 = 0; i25 < 4; i25++) {
                        int i26 = ((((385963008 + (iArr[0] * 5571)) + (iArr[1] * 4751)) + (iArr[2] * 2785)) + (iArr[3] * 1556)) >> 15;
                        int i27 = p[iArr4[i25]];
                        int i28 = o[iArr4[i25]];
                        iArr[3] = iArr[2];
                        iArr[2] = iArr[1];
                        iArr[1] = iArr[0];
                        iArr[0] = i27;
                        a(i8, i11, (i26 * i28) >> 24);
                    }
                    break;
                default:
                    PrintStream printStream = System.out;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unsupported frame type: ");
                    sb.append(i9);
                    printStream.println(sb.toString());
                    a(i8, i11, 1);
                    break;
            }
        } else {
            this.i = 12;
            int[] iArr5 = new int[4];
            int[] iArr6 = new int[4];
            int[] iArr7 = new int[4];
            int[][] iArr8 = new int[4][];
            int i29 = 0;
            for (int i30 = 4; i29 < i30; i30 = 4) {
                iArr8[i29] = new int[10];
                i29++;
            }
            int[][] iArr9 = iArr8;
            int[] iArr10 = iArr7;
            a(iArr2, iArr5, iArr6, iArr10, iArr9);
            int i31 = 0;
            int i32 = 0;
            int i33 = 4;
            while (i31 < i33) {
                int[] iArr11 = new int[i16];
                for (int i34 = 0; i34 < i16; i34++) {
                    iArr11[i34] = 0;
                }
                for (int i35 = 0; i35 < i17; i35++) {
                    int i36 = ((iArr9[i31][i35] >> 3) & 1) == 0 ? 4096 : -4096;
                    int i37 = i35 + (s[iArr9[i31][i35] & 7] * 5);
                    int i38 = i35 + (s[iArr9[i31][i35 + 5] & 7] * 5);
                    iArr11[i37] = i36;
                    if (i38 < i37) {
                        i36 = -i36;
                    }
                    iArr11[i38] = iArr11[i38] + i36;
                }
                int i39 = iArr5[i31];
                if (i31 != 0) {
                    i3 = 2;
                    if (i31 != 2) {
                        int i40 = i32 - i17;
                        if (i40 < 18) {
                            i40 = 18;
                        }
                        if (i40 + 9 > 143) {
                            i40 = 134;
                        }
                        i32 = (i40 + ((i39 + 5) / 6)) - 1;
                        int i41 = (t[iArr6[i31]] >> i3) << i3;
                        int i42 = i41 <= 16383 ? 32767 : i41 * 2;
                        for (i4 = i32; i4 < i16; i4++) {
                            iArr11[i4] = iArr11[i4] + ((iArr11[i4 - i32] * i42) >> 15);
                        }
                        int i43 = 0;
                        for (i5 = 0; i5 < i16; i5++) {
                            i43 += iArr11[i5] * iArr11[i5];
                        }
                        double log2 = Math.log((double) (((((1073741823 > i43 || i43 < 0) ? Integer.MAX_VALUE : i43 * 2) + 32768) >> 16) * 52428)) / Math.log(2.0d);
                        int i44 = (int) log2;
                        int i45 = (((i3 * ((((iArr[0] * 44) + (iArr[1] * 37)) + (iArr[i3] * 22)) + (iArr[3] * 12))) + 783741) - (((i44 - 30) << 16) + (((int) ((log2 - ((double) i44)) * 32768.0d)) * i3))) / i3;
                        int i46 = i45 >> 16;
                        int[] iArr12 = iArr5;
                        int pow = (int) (Math.pow(2.0d, ((double) i46) + (((double) ((i45 >> 1) - (i46 << 15))) / 32768.0d)) + 0.5d);
                        int i47 = 3 * iArr10[i31];
                        i6 = (((pow > 2047 ? pow << 4 : 32767) * q[i47]) >> 15) << 1;
                        if ((i6 & -32768) == 0) {
                            i6 = 32767;
                        }
                        a(i8, i11, i6);
                        int i48 = q[i47 + 1];
                        iArr[3] = iArr[2];
                        iArr[2] = iArr[1];
                        iArr[1] = iArr[0];
                        iArr[0] = i48;
                        i31++;
                        iArr5 = iArr12;
                        i16 = 40;
                        i17 = 5;
                    }
                } else {
                    i3 = 2;
                }
                i32 = i39 < 463 ? ((i39 + 5) / 6) + 17 : i39 - 368;
                int i432 = 0;
                double log22 = Math.log((double) (((((1073741823 > i432 || i432 < 0) ? Integer.MAX_VALUE : i432 * 2) + 32768) >> 16) * 52428)) / Math.log(2.0d);
                int i442 = (int) log22;
                int i452 = (((i3 * ((((iArr[0] * 44) + (iArr[1] * 37)) + (iArr[i3] * 22)) + (iArr[3] * 12))) + 783741) - (((i442 - 30) << 16) + (((int) ((log22 - ((double) i442)) * 32768.0d)) * i3))) / i3;
                int i462 = i452 >> 16;
                int[] iArr122 = iArr5;
                int pow2 = (int) (Math.pow(2.0d, ((double) i462) + (((double) ((i452 >> 1) - (i462 << 15))) / 32768.0d)) + 0.5d);
                int i472 = 3 * iArr10[i31];
                i6 = (((pow2 > 2047 ? pow2 << 4 : 32767) * q[i472]) >> 15) << 1;
                a(i8, i11, i6);
                int i482 = q[i472 + 1];
                iArr[3] = iArr[2];
                iArr[2] = iArr[1];
                iArr[1] = iArr[0];
                iArr[0] = i482;
                i31++;
                iArr5 = iArr122;
                i33 = 4;
                i16 = 40;
                i17 = 5;
            }
        }
        return i11;
    }


    public void a(int i2, int i3, int i4) {
        this.e[this.d] = i2;
        this.f[this.d] = i3;
        this.g[this.d] = i4;
        if (i4 < this.l) {
            this.l = i4;
        }
        if (i4 > this.m) {
            this.m = i4;
        }
        this.d++;
        if (this.d == this.k) {
            int i5 = this.k * 2;
            int[] iArr = new int[i5];
            int[] iArr2 = new int[i5];
            int[] iArr3 = new int[i5];
            for (int i6 = 0; i6 < this.d; i6++) {
                iArr[i6] = this.e[i6];
                iArr2[i6] = this.f[i6];
                iArr3[i6] = this.g[i6];
            }
            this.e = iArr;
            this.f = iArr2;
            this.g = iArr3;
            this.k = i5;
        }
    }
    @Override
    public void WriteFile(File file, int i2, int i3) throws IOException {
        file.createNewFile();
        FileInputStream fileInputStream = new FileInputStream(this.mInputFile);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(new byte[]{35, 33, 65, 77, 82, 10}, 0, 6);
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
            if (i10 >= 0) {
                if (i10 > 0) {
                    fileInputStream.skip((long) i10);
                    i7 += i10;
                }
                fileInputStream.read(bArr, 0, i11);
                fileOutputStream.write(bArr, 0, i11);
                i7 += i11;
            }
        }
        fileInputStream.close();
        fileOutputStream.close();
    }


    public void a(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[][] iArr5) {
        iArr2[0] = (iArr[45] * 1) + (iArr[43] * 2) + (iArr[41] * 4) + (iArr[39] * 8) + (iArr[37] * 16) + (iArr[35] * 32) + (iArr[33] * 64) + (iArr[31] * 128) + (iArr[29] * 256);
        iArr2[1] = (iArr[242] * 1) + (iArr[79] * 2) + (iArr[77] * 4) + (iArr[75] * 8) + (iArr[73] * 16) + (iArr[71] * 32);
        iArr2[2] = (iArr[46] * 1) + (iArr[44] * 2) + (iArr[42] * 4) + (iArr[40] * 8) + (iArr[38] * 16) + (iArr[36] * 32) + (iArr[34] * 64) + (iArr[32] * 128) + (256 * iArr[30]);
        iArr2[3] = (iArr[243] * 1) + (iArr[80] * 2) + (iArr[78] * 4) + (iArr[76] * 8) + (iArr[74] * 16) + (32 * iArr[72]);
        iArr3[0] = (iArr[88] * 1) + (iArr[55] * 2) + (iArr[51] * 4) + (iArr[47] * 8);
        iArr3[1] = (iArr[89] * 1) + (iArr[56] * 2) + (iArr[52] * 4) + (iArr[48] * 8);
        iArr3[2] = (iArr[90] * 1) + (iArr[57] * 2) + (iArr[53] * 4) + (iArr[49] * 8);
        iArr3[3] = (iArr[91] * 1) + (iArr[58] * 2) + (iArr[54] * 4) + (iArr[50] * 8);
        iArr4[0] = (iArr[104] * 1) + (iArr[92] * 2) + (iArr[67] * 4) + (iArr[63] * 8) + (iArr[59] * 16);
        iArr4[1] = (iArr[105] * 1) + (iArr[93] * 2) + (iArr[68] * 4) + (iArr[64] * 8) + (iArr[60] * 16);
        iArr4[2] = (iArr[106] * 1) + (iArr[94] * 2) + (iArr[69] * 4) + (iArr[65] * 8) + (iArr[61] * 16);
        iArr4[3] = (iArr[107] * 1) + (iArr[95] * 2) + (iArr[70] * 4) + (iArr[66] * 8) + (16 * iArr[62]);
        iArr5[0][0] = (iArr[122] * 1) + (iArr[123] * 2) + (iArr[124] * 4) + (iArr[96] * 8);
        iArr5[0][1] = (iArr[125] * 1) + (iArr[126] * 2) + (iArr[127] * 4) + (iArr[100] * 8);
        iArr5[0][2] = (iArr[128] * 1) + (iArr[129] * 2) + (iArr[130] * 4) + (iArr[108] * 8);
        iArr5[0][3] = (iArr[131] * 1) + (iArr[132] * 2) + (iArr[133] * 4) + (iArr[112] * 8);
        iArr5[0][4] = (iArr[134] * 1) + (iArr[135] * 2) + (iArr[136] * 4) + (iArr[116] * 8);
        iArr5[0][5] = (iArr[182] * 1) + (iArr[183] * 2) + (iArr[184] * 4);
        iArr5[0][6] = (iArr[185] * 1) + (iArr[186] * 2) + (iArr[187] * 4);
        iArr5[0][7] = (iArr[188] * 1) + (iArr[189] * 2) + (iArr[190] * 4);
        iArr5[0][8] = (iArr[191] * 1) + (iArr[192] * 2) + (iArr[193] * 4);
        iArr5[0][9] = (iArr[194] * 1) + (iArr[195] * 2) + (iArr[196] * 4);
        iArr5[1][0] = (iArr[137] * 1) + (iArr[138] * 2) + (iArr[139] * 4) + (iArr[97] * 8);
        iArr5[1][1] = (iArr[140] * 1) + (iArr[141] * 2) + (iArr[142] * 4) + (iArr[101] * 8);
        iArr5[1][2] = (iArr[143] * 1) + (iArr[144] * 2) + (iArr[145] * 4) + (iArr[109] * 8);
        iArr5[1][3] = (iArr[146] * 1) + (iArr[147] * 2) + (iArr[148] * 4) + (iArr[113] * 8);
        iArr5[1][4] = (iArr[149] * 1) + (iArr[150] * 2) + (iArr[151] * 4) + (iArr[117] * 8);
        iArr5[1][5] = (iArr[197] * 1) + (iArr[198] * 2) + (iArr[199] * 4);
        iArr5[1][6] = (iArr[200] * 1) + (iArr[201] * 2) + (iArr[202] * 4);
        iArr5[1][7] = (iArr[203] * 1) + (iArr[204] * 2) + (iArr[205] * 4);
        iArr5[1][8] = (iArr[206] * 1) + (iArr[207] * 2) + (iArr[208] * 4);
        iArr5[1][9] = (iArr[209] * 1) + (iArr[210] * 2) + (iArr[211] * 4);
        iArr5[2][0] = (iArr[152] * 1) + (iArr[153] * 2) + (iArr[154] * 4) + (iArr[98] * 8);
        iArr5[2][1] = (iArr[155] * 1) + (iArr[156] * 2) + (iArr[157] * 4) + (iArr[102] * 8);
        iArr5[2][2] = (iArr[158] * 1) + (iArr[159] * 2) + (iArr[160] * 4) + (iArr[110] * 8);
        iArr5[2][3] = (iArr[161] * 1) + (iArr[162] * 2) + (iArr[163] * 4) + (iArr[114] * 8);
        iArr5[2][4] = (iArr[164] * 1) + (iArr[165] * 2) + (iArr[166] * 4) + (iArr[118] * 8);
        iArr5[2][5] = (iArr[212] * 1) + (iArr[213] * 2) + (iArr[214] * 4);
        iArr5[2][6] = (iArr[215] * 1) + (iArr[216] * 2) + (iArr[217] * 4);
        iArr5[2][7] = (iArr[218] * 1) + (iArr[219] * 2) + (iArr[220] * 4);
        iArr5[2][8] = (iArr[221] * 1) + (iArr[222] * 2) + (iArr[223] * 4);
        iArr5[2][9] = (iArr[224] * 1) + (iArr[225] * 2) + (iArr[226] * 4);
        iArr5[3][0] = (iArr[167] * 1) + (iArr[168] * 2) + (iArr[169] * 4) + (iArr[99] * 8);
        iArr5[3][1] = (iArr[170] * 1) + (iArr[171] * 2) + (iArr[172] * 4) + (iArr[103] * 8);
        iArr5[3][2] = (iArr[173] * 1) + (iArr[174] * 2) + (iArr[175] * 4) + (iArr[111] * 8);
        iArr5[3][3] = (iArr[176] * 1) + (iArr[177] * 2) + (iArr[178] * 4) + (iArr[115] * 8);
        iArr5[3][4] = (iArr[179] * 1) + (iArr[180] * 2) + (iArr[181] * 4) + (iArr[119] * 8);
        iArr5[3][5] = (iArr[227] * 1) + (iArr[228] * 2) + (iArr[229] * 4);
        iArr5[3][6] = (iArr[230] * 1) + (iArr[231] * 2) + (iArr[232] * 4);
        iArr5[3][7] = (iArr[233] * 1) + (iArr[234] * 2) + (iArr[235] * 4);
        iArr5[3][8] = (iArr[236] * 1) + (iArr[237] * 2) + (iArr[238] * 4);
        iArr5[3][9] = (1 * iArr[239]) + (2 * iArr[240]) + (4 * iArr[241]);
    }
}
