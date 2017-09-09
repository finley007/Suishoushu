package com.changyi.fi.external.enterprise.qxb.hmac;

import com.changyi.fi.core.CommonUtil;

import java.util.Arrays;

public class HmacData implements Cloneable {

    public HmacData(String key) {
        words = this.parse(key);
        sigBytes = key.length() > 0 ? key.length() : this.words.length * 4;
    }

    public HmacData(long[] words, int sigBytes) {
        this.words = words;
        this.sigBytes = sigBytes != 0 ? sigBytes : this.words.length * 4;
    }

    long[] words;

    int sigBytes;

    public long[] getWords() {
        return words;
    }

    public void setWords(long[] words) {
        this.words = words;
    }

    public int getSigBytes() {
        return sigBytes;
    }

    public void setSigBytes(int sigBytes) {
        this.sigBytes = sigBytes;
    }


    public long[] parse(String key) {
        int size = key.length();
        long[] temp = new long[size];
        for (int i = 0; i < size; i++) {
            temp[i >>> 2] |= (255 & key.charAt(i)) << 24 - i % 4 * 8;
        }
        int count = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] > 0) {
                count++;
            }
        }
        long[] result = new long[count];
        for (int i = 0; i < result.length; i++) {
            result[i] = temp[i];
        }
        return result;
    }

    public void clamp() {
        int n = this.sigBytes;
        int index = n >>> 2;
        if (index <= this.words.length - 1) {
            this.words[n >>> 2] &= 4294967295l << 32 - n % 4 * 8;
        } else {
            long[] newWords = Arrays.copyOf(this.words, index + 1);
            this.words = newWords;
        }
        int newLength = Double.valueOf(Math.ceil(n / 4)).intValue();
        if (newLength > this.words.length) {
            long[] newWords = Arrays.copyOf(this.words, newLength);
            this.words = newWords;
        }
    }

    public HmacData concat(HmacData data) {
        int newSize = this.sigBytes + data.sigBytes;
        if (this.sigBytes % 4 == 0) {
            while (newSize % 4 != 0) {
                newSize++;
            }
        }
        newSize = newSize >>> 2;
        if (this.words.length < newSize) {
            long[] newWords = Arrays.copyOf(this.words, newSize);
            this.words = newWords;
        }
        this.clamp();
        if (this.sigBytes % 4 != 0) {
            for (int i = 0; i < data.sigBytes; i++) {
                long value = data.words[i >>> 2] >>> 24 - i % 4 * 8 & 255;
                this.words[this.sigBytes + i >>> 2] |= value << 24 - (this.sigBytes + i) % 4 * 8;
            }
        } else {
            for (int i = 0; i < data.sigBytes; i += 4) {
                this.words[this.sigBytes + i >>> 2] = data.words[i >>> 2];
            }
        }
        this.sigBytes += data.sigBytes;
        return this;
    }

    protected Object clone() throws CloneNotSupportedException {
        long[] arr = Arrays.copyOf(this.words, this.words.length);
        HmacData data = new HmacData(arr, this.sigBytes);
        return data;
    }

    private long toUnsigned(long signed) {
        return CommonUtil.toUnsigned(signed);
    }

    public String stringify() {
        long[] words = this.words;
        int sigBytes = this.sigBytes;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sigBytes; i++) {
            long index = toUnsigned(words[i >>> 2]) >>> 24 - i % 4 * 8 & 255;
            sb.append(Long.toHexString(index >>> 4));
            sb.append(Long.toHexString(15 & index));
        }
        return sb.toString();
    }

}
