package com.changyi.fi.core.seq;

public class SeqCreatorBuilder {

    public final static String SEQ_CREATOR_RANDOWM = "RANDOM";

    public static ISeqCreator build(String type) {
        if (SEQ_CREATOR_RANDOWM.equals(type)) {
            return new RandomSeqCreator();
        } else {
            return new RandomSeqCreator();
        }
    }
}
