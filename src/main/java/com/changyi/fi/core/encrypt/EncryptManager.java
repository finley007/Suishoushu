package com.changyi.fi.core.encrypt;

import com.changyi.fi.core.encrypt.impl.MD5Encryptor;
import com.changyi.fi.core.encrypt.impl.Sha1Encryptor;
import com.changyi.fi.core.exception.SystemException;
import com.changyi.fi.util.FIConstants;

public class EncryptManager {

    public static IEncryptor getEncryptor(FIConstants.EncryptorAlgorithm algorithm) throws Exception {
        if (algorithm == FIConstants.EncryptorAlgorithm.MD5) {
            return new MD5Encryptor();
        } else if (algorithm == FIConstants.EncryptorAlgorithm.SHA1) {
            return new Sha1Encryptor();
        } else {
            throw new SystemException("Unknown encryptor implementation");
        }
    }
}
