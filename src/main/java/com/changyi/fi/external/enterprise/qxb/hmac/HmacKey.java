package com.changyi.fi.external.enterprise.qxb.hmac;

public class HmacKey {

    public HmacKey(Word[] words, int sigBytes) {
        this.words = words;
        this.sigBytes = 0 != sigBytes ? sigBytes : 8 * words.length;
    }

    private Word[] words;

    private int sigBytes;

    public Word[] getWords() {
        return words;
    }

    public void setWords(Word[] words) {
        this.words = words;
    }

    public int getSigBytes() {
        return sigBytes;
    }

    public void setSigBytes(int sigBytes) {
        this.sigBytes = sigBytes;
    }

    public HmacData toX32() {
        long[] result = new long[this.words.length * 2];
        for (int i = 0; i < this.words.length; i++) {
            Word word = this.words[i];
            result[2*i] = word.getHigh();
            result[2*i+1] = word.getLow();
        }
        return new HmacData(result, this.sigBytes);
    }

}
