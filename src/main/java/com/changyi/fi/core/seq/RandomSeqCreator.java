package com.changyi.fi.core.seq;

import java.util.Random;

public class RandomSeqCreator implements ISeqCreator {

    private Random random = new Random();

    public String createSeq(int digital) {
        String result = "";
        for (int i = 0; i < digital; i++) {
            result += createRandom();
        }
        return result;
    }

    private String createRandom() {
        return String.valueOf(random.nextInt(10));
    }
}
