package com.changyi.fi.core.encrypt.impl;

import com.changyi.fi.core.encrypt.IEncryptor;
import com.changyi.fi.util.FIConstants;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Encryptor implements IEncryptor {

    public String sign(String content) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(FIConstants.EncryptorAlgorithm.MD5.getValue());
        md5.update(content.getBytes(FIConstants.DEFAULT_CHARSET));
        return new BigInteger(md5.digest()).toString(16);
    }

}
