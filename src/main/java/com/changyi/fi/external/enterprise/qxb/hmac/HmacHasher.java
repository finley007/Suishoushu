package com.changyi.fi.external.enterprise.qxb.hmac;

import com.changyi.fi.core.CommonUtil;

import java.util.Arrays;

public class HmacHasher {

    public HmacHasher() {
        this.reset();
    }

    private Object cfg;

    private HmacData data = new HmacData(new long[0], 0);

    private HmacKey hash;

    private int blockSize = 32;

    private int nDataBytes = 0;

    private int minBufferSize = 0;

    private Word[] words = new Word[80];

    private Word[] dic = {new Word(1116352408, 3609767458l),
                          new Word(1899447441, 602891725),
                          new Word(3049323471l, 3964484399l),
                          new Word(3921009573l, 2173295548l),
                          new Word(961987163, 4081628472l),
                          new Word(1508970993, 3053834265l),
                          new Word(2453635748l, 2937671579l),
                          new Word(2870763221l, 3664609560l),
                          new Word(3624381080l, 2734883394l),
                          new Word(310598401, 1164996542),
                          new Word(607225278, 1323610764),
                          new Word(1426881987, 3590304994l),
                            new Word(1925078388, 4068182383l),
                            new Word(2162078206l, 991336113),
                            new Word(2614888103l, 633803317),
                            new Word(3248222580l, 3479774868l),
                            new Word(3835390401l, 2666613458l),
                            new Word(4022224774l, 944711139),
                            new Word(264347078, 2341262773l),
                            new Word(604807628, 2007800933),
                            new Word(770255983, 1495990901),
                            new Word(1249150122, 1856431235),
                            new Word(1555081692, 3175218132l),
                            new Word(1996064986, 2198950837l),
                            new Word(2554220882l, 3999719339l),
                            new Word(2821834349l, 766784016),
                            new Word(2952996808l, 2566594879l),
                            new Word(3210313671l, 3203337956l),
                            new Word(3336571891l, 1034457026),
                            new Word(3584528711l, 2466948901l),
                            new Word(113926993, 3758326383l),
                            new Word(338241895, 168717936),
                            new Word(666307205, 1188179964),
                            new Word(773529912, 1546045734),
                            new Word(1294757372, 1522805485),
                            new Word(1396182291, 2643833823l),
                            new Word(1695183700, 2343527390l),
                            new Word(1986661051, 1014477480),
                            new Word(2177026350l, 1206759142),
                            new Word(2456956037l, 344077627),
                            new Word(2730485921l, 1290863460),
                            new Word(2820302411l, 3158454273l),
                            new Word(3259730800l, 3505952657l),
                            new Word(3345764771l, 106217008),
                            new Word(3516065817l, 3606008344l),
                            new Word(3600352804l, 1432725776),
                            new Word(4094571909l, 1467031594),
                            new Word(275423344, 851169720),
                            new Word(430227734, 3100823752l),
                            new Word(506948616, 1363258195),
                            new Word(659060556, 3750685593l),
                            new Word(883997877, 3785050280l),
                            new Word(958139571, 3318307427l),
                            new Word(1322822218, 3812723403l),
                            new Word(1537002063, 2003034995),
                            new Word(1747873779, 3602036899l),
                            new Word(1955562222, 1575990012),
                            new Word(2024104815, 1125592928),
                            new Word(2227730452l, 2716904306l),
                            new Word(2361852424l, 442776044),
                            new Word(2428436474l, 593698344),
                            new Word(2756734187l, 3733110249l),
                            new Word(3204031479l, 2999351573l),
                            new Word(3329325298l, 3815920427l),
                            new Word(3391569614l, 3928383900l),
                            new Word(3515267271l, 566280711),
                            new Word(3940187606l, 3454069534l),
                            new Word(4118630271l, 4000239992l),
                            new Word(116418474, 1914138554),
                            new Word(174292421, 2731055270l),
                            new Word(289380356, 3203993006l),
                            new Word(460393269, 320620315),
                            new Word(685471733, 587496836),
                            new Word(852142971, 1086792851),
                            new Word(1017036298, 365543100),
                            new Word(1126000580, 2618297676l),
                            new Word(1288033470, 3409855158l),
                            new Word(1501505948, 4234509866l),
                            new Word(1607167915, 987167468),
                            new Word(1816402316, 1246189591)};

    public Object getCfg() {
        return cfg;
    }

    public void setCfg(Object cfg) {
        this.cfg = cfg;
    }

    public HmacData getData() {
        return data;
    }

    public void setData(HmacData data) {
        this.data = data;
    }

    public HmacKey getHash() {
        return hash;
    }

    public void setHash(HmacKey hash) {
        this.hash = hash;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public void reset() {
        Word[] words = { new Word(1779033703, 4089235720l),
                         new Word(3144134277l,2227873595l),
                         new Word(1013904242,4271175723l),
                         new Word(2773480762l,1595750129),
                         new Word(1359893119,2917565137l),
                         new Word(2600822924l,725511199),
                         new Word(528734635,4215389547l),
                         new Word(1541459225,327033209) };
        this.hash = new HmacKey(words, 0);
        for (int i = 0; i < 80; i++) {
            this.words[i] = new Word(0, 0);
        }
        this.nDataBytes = 0;
    }

    public void update(HmacData data) {
        this.append(data);
        this.process(null);
    }

    public HmacData finalize(Object obj) {
        this.append(obj);
        return this.doFinalSize();
    }

    private void append(Object obj) {
        HmacData data = null;
        if (obj instanceof String) {
            data = new HmacData(obj.toString());
        } else if (obj instanceof HmacData) {
            data = (HmacData)obj;
        }
        this.data.concat(data);
        this.nDataBytes += data.getSigBytes();
    }

    private HmacData process(HmacData data) {
        int steps = Double.valueOf(Math.ceil(this.data.getSigBytes() / (4 * this.blockSize))).intValue();
        steps = data == null ? steps : Math.max((0 | steps) - this.minBufferSize, 0);
        int length = steps * this.blockSize;
        int sigLength = Math.min(4 * length, this.data.getSigBytes());
        long[] newWords = new long[0];
        if (length > 0) {
            for (int i = 0; i < length; i += this.blockSize) {
                this.doProcessBlock(this.data.getWords(), i);
            }
            newWords = splice(this.data,0, length);
            this.data.sigBytes -= sigLength;
        }
        return new HmacData(newWords, sigLength);
    }

    private long[] splice(HmacData data, int index, int howmany) {
        long[] arr = data.getWords();
        if (howmany <= 0) {
            return arr;
        }
        int newLength = arr.length - howmany;
        if (newLength > 0) {
            if (arr.length <= (index + howmany)) {
                long[] newArr = new long[newLength];
                long[] returnArr = new long[arr.length - index];
                for (int i = 0; i < index; i++) {
                    newArr[i] = arr[i];
                }
                for (int i = index; i < arr.length; i++) {
                    returnArr[i - index] = arr[i];
                }
                data.setWords(newArr);
                return returnArr;
            } else {
                long[] newArr = new long[newLength];
                long[] returnArr = new long[howmany];
                for (int i = 0; i < index; i++) {
                    newArr[i] = arr[i];
                }
                for (int i = index + howmany; i < arr.length; i++) {
                    newArr[i - howmany] = arr[i];
                }
                for (int i = index; i < index + howmany; i++) {
                    returnArr[i - index] = arr[i];
                }
                data.setWords(newArr);
                return returnArr;
            }
        } else if (newLength == 0) {
            long[] returnArr = Arrays.copyOf(arr, arr.length);
            data.setWords(new long[0]);
            return returnArr;
        } else {
            return arr;
        }
    }

    private void doProcessBlock(long[] t, int e) {
        Word[] n = this.hash.getWords();
        Word r = n[0];
        Word i = n[1];
        Word o = n[2];
        Word a = n[3];
        Word s = n[4];
        Word u = n[5];
        Word f = n[6];
        Word h = n[7];
        long O = r.getHigh();
        long p = r.getHigh();
        long I = r.getLow();
        long d = r.getLow();
        long P = i.getHigh();
        long v = i.getHigh();
        long D = i.getLow();
        long g = i.getLow();
        long j = o.getHigh();
        long y = o.getHigh();
        long N = o.getLow();
        long m = o.getLow();
        long M = a.getHigh();
        long b = a.getHigh();
        long L = a.getLow();
        long _ = a.getLow();
        long R = s.getHigh();
        long w = s.getHigh();
        long $ = s.getLow();
        long x = s.getLow();
        long V = u.getHigh();
        long k = u.getHigh();
        long F = u.getLow();
        long S = u.getLow();
        long B = f.getHigh();
        long T = f.getHigh();
        long q = f.getLow();
        long E = f.getLow();
        long H = h.getHigh();
        long C = h.getHigh();
        long U = h.getLow();
        long A = h.getLow();
        for (int z = 0; z < 80; z++) {
            Word W = words[z];
            long G = 0l;
            long K = 0l;
            if (z < 16) {
                K = W.high = 0 | t[e + 2 * z];
                G = W.low = 0 | t[e + 2 * z + 1];
            } else {
                Word X = words[z - 15];
                long Q = X.high;
                long Y = X.low;
                long J = (int)((toUnsigned(Q) >>> 1 | Y << 31) ^ (toUnsigned(Q) >>> 8 | Y << 24) ^ toUnsigned(Q) >>> 7);
                long Z = (int)((toUnsigned(Y) >>> 1 | Q << 31) ^ (toUnsigned(Y) >>> 8 | Q << 24) ^ (toUnsigned(Y) >>> 7 | Q << 25));
                Word tt = words[z - 2];
                long et = tt.high;
                long nt = tt.low;
                long rt = (int)((toUnsigned(et) >>> 19 | nt << 13) ^ (et << 3 | toUnsigned(nt) >>> 29) ^ toUnsigned(et) >>> 6);
                long it = (int)((toUnsigned(nt) >>> 19 | et << 13) ^ (nt << 3 | toUnsigned(et) >>> 29) ^ (toUnsigned(nt) >>> 6 | et << 26));
                Word ot = words[z - 7];
                long at = ot.high;
                long st = ot.low;
                Word ut = words[z - 16];
                long ct = ut.high;
                long lt = ut.low;
                G = Z + st;
                K = J + at + (toUnsigned(G) < toUnsigned(Z) ? 1 : 0);
                G = G + it;
                K = K + rt + (toUnsigned(G) < toUnsigned(it) ? 1 : 0);
                G = G + lt;
                K = K + ct + (toUnsigned(G) < toUnsigned(lt) ? 1 : 0);
                W.high = K;
                W.low = G;
            }
            long ft = (int)(R & V ^ ~R & B);
            long ht = (int)($ & F ^ ~$ & q);
            long pt = (int)(O & P ^ O & j ^ P & j);
            long dt = (int)(I & D ^ I & N ^ D & N);
            long vt = (int)((toUnsigned(O) >>> 28 | I << 4) ^ (O << 30 | toUnsigned(I) >>> 2) ^ (O << 25 | toUnsigned(I) >>> 7));
            long gt = (int)((toUnsigned(I) >>> 28 | O << 4) ^ (I << 30 | toUnsigned(O) >>> 2) ^ (I << 25 | toUnsigned(O) >>> 7));
            long yt = (int)((toUnsigned(R) >>> 14 | $ << 18) ^ (toUnsigned(R) >>> 18 | $ << 14) ^ (R << 23 | toUnsigned($) >>> 9));
            long mt = (int)((toUnsigned($) >>> 14 | R << 18) ^ (toUnsigned($) >>> 18 | R << 14) ^ ($ << 23 | toUnsigned(R) >>> 9));
            Word bt = dic[z];
            long _t = bt.high;
            long wt = bt.low;
            long xt = U + mt;
            long kt = H + yt + (toUnsigned(xt) < toUnsigned(U) ? 1 : 0);
            xt = xt + ht;
            kt = kt + ft + (toUnsigned(xt) < toUnsigned(ht) ? 1 : 0);
            xt = xt + wt;
            kt = kt + _t + (toUnsigned(xt) < toUnsigned(wt) ? 1 : 0);
            xt = xt + G;
            kt = kt + K + (toUnsigned(xt) < toUnsigned(G) ? 1 : 0);
            long St = gt + dt;
            long Tt = vt + pt + (toUnsigned(St) < toUnsigned(gt) ? 1 : 0);
            H = B;
            U = q;
            B = V;
            q = F;
            V = R;
            F = $;
            $ = (int)(L + xt | 0);
            R = (int)(M + kt + (toUnsigned($) < toUnsigned(L) ? 1 : 0) | 0);
            M = j;
            L = N;
            j = P;
            N = D;
            P = O;
            D = I;
            I = (int)(xt + St | 0);
            O = (int)(kt + Tt + (toUnsigned(I) < toUnsigned(xt) ? 1 : 0) | 0);
        }
        d = r.low = d + I;
        r.high = p + O + (toUnsigned(d) < toUnsigned(I) ? 1 : 0);
        g = i.low = g + D;
        i.high = v + P + (toUnsigned(g) < toUnsigned(D) ? 1 : 0);
        m = o.low = m + N;
        o.high = y + j + (toUnsigned(m) < toUnsigned(N) ? 1 : 0);
        _ = a.low = _ + L;
        a.high = b + M + (toUnsigned(_) < toUnsigned(L) ? 1 : 0);
        x = s.low = x + $;
        s.high = w + R + (toUnsigned(x) < toUnsigned($) ? 1 : 0);
        S = u.low = S + F;
        u.high = k + V + (toUnsigned(S) < toUnsigned(F) ? 1 : 0);
        E = f.low = E + q;
        f.high = T + B + (toUnsigned(E) < toUnsigned(q) ? 1 : 0);
        A = h.low = A + U;
        h.high = C + H + (toUnsigned(A) < toUnsigned(U) ? 1 : 0);
    }

    private long toUnsigned(long signed) {
        return CommonUtil.toUnsigned(signed);
    }

    private HmacData doFinalSize() {
        int sigBytes = this.data.sigBytes * 8;
        int nDataBytes = this.nDataBytes * 8;
        int index1 = sigBytes >>> 5;
        int index2 = 30 + (sigBytes + 128 >>> 10 << 5);
        int index3 = 31 + (sigBytes + 128 >>> 10 << 5);
        int newLength = Math.max(Math.max(Math.max(index1, index2), index3), this.data.words.length);
        if (newLength > this.data.words.length) {
            long[] newArr = Arrays.copyOf(this.data.words, newLength + 1);
            this.data.words = newArr;
        }
        this.data.words[sigBytes >>> 5] |= 128 << 24 - sigBytes % 32;
        this.data.words[30 + (sigBytes + 128 >>> 10 << 5)] = Double.valueOf(Math.floor(8 * this.nDataBytes / 4294967296l)).longValue();
        this.data.words[31 + (sigBytes + 128 >>> 10 << 5)] = nDataBytes;
        this.data.sigBytes = 4 * this.data.words.length;
        this.process(null);
        return this.hash.toX32();
    }
}
