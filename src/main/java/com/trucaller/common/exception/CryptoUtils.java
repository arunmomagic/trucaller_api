package com.trucaller.common.exception;

import com.trucaller.common.Defs;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class CryptoUtils {
    private static final String SIGNATURE_ALGORITHM = "SHA256WithRSA";
    private static final String KEY_SPEC = "RSA";

    public static PrivateKey getPrivateKey(byte[] keyData) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyData);
        KeyFactory kf = KeyFactory.getInstance(KEY_SPEC);
        return kf.generatePrivate(spec);
    }

    public static String sign(byte[] data) throws Exception {
        byte[] privateKeyBytes = Files.readAllBytes(Paths.get(Defs.privateKeyPath));
        PrivateKey key=getPrivateKey(privateKeyBytes);
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initSign(key)
        ;
        sig.update(data);
        return Base64.getEncoder().encodeToString(sig.sign());
    }

    public static byte[] prepareDataArray(String partnerId, Long customer, String premiumId, String transactionId) {
        return (partnerId + customer + premiumId + transactionId).getBytes();
    }
}
