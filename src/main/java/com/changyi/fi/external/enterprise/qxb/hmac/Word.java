package com.changyi.fi.external.enterprise.qxb.hmac;

public class Word {

    public Word(long high, long low) {
        this.high = high;
        this.low = low;
    }

    long high;

    long low;

    public long getHigh() {
        return high;
    }

    public void setHigh(long high) {
        this.high = high;
    }

    public long getLow() {
        return low;
    }

    public void setLow(long low) {
        this.low = low;
    }
}
