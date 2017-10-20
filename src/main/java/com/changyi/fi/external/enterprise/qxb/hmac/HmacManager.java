package com.changyi.fi.external.enterprise.qxb.hmac;

import java.util.Arrays;

public class HmacManager {

    private HmacData iKey;

    private HmacData oKey;

    private HmacHasher hasher;

    public HmacManager init(HmacTool tool, String key) throws Exception {
        HmacData hmacData = new HmacData(key);
        this.hasher = new HmacHasher();
        int blockSize = this.hasher.getBlockSize();
        int size = blockSize * 4;
        if (hmacData.getSigBytes() > size) {
            hmacData = this.hasher.finalize(hmacData);
        }
        hmacData.clamp();
        this.iKey = (HmacData)hmacData.clone();
        this.oKey = (HmacData)hmacData.clone();
        if (this.iKey.words.length < blockSize) {
            this.iKey.words = Arrays.copyOf(this.iKey.words, blockSize);
        }
        if (this.oKey.words.length < blockSize) {
            this.oKey.words = Arrays.copyOf(this.oKey.words, blockSize);
        }
        for (int i = 0; i < blockSize; i++) {
            int oKey = (int)(this.oKey.words[i] ^ 1549556828);
            this.oKey.words[i] = oKey;
            int iKey = (int)(this.iKey.words[i] ^ 909522486);
            this.iKey.words[i] = iKey;
        }
        this.iKey.setSigBytes(size);
        this.oKey.setSigBytes(size);

        this.reset();
        return this;
    }

    public HmacData finalize(String data) {
        HmacData hmacData = this.hasher.finalize(data);
        this.hasher.reset();
        return this.hasher.finalize(this.oKey.concat(hmacData));
    }

    private void reset() {
        this.hasher.reset();
        this.hasher.update(this.iKey);
    }

}
