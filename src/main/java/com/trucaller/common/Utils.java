package com.trucaller.common;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class Utils {
    public static boolean isOk(Integer value) {
        return !(value == null || value.intValue() <= 0);
    }

    public static boolean isOk(Object value) {
        return !(value == null);
    }

    public static boolean isOk(Date value) {
        return !(value == null);
    }

    public static boolean isOk(Short value) {
        return !(value == null || value <= 0);
    }

    public static boolean isOk(Long value) {
        return !(value == null || value.longValue() <= 0);
    }

    public static boolean isOk(BigInteger value) {
        return !(value == null || value.intValue() <= 0);
    }

    public static boolean isOk(String str) {
        return !(str == null || str.isEmpty());
    }

    public static String generateSignature(String message){
       try {
           // Load the private key
           byte[] privateKeyBytes = Files.readAllBytes(Paths.get(Defs.privateKeyPath));
           PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
           KeyFactory keyFactory = KeyFactory.getInstance("RSA");
           PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
           // Sign the message using the private key
           Signature signature = Signature.getInstance("SHA256withRSA");
           signature.initSign(privateKey);
           signature.update(message.getBytes(StandardCharsets.UTF_8));
           byte[] signatureBytes = signature.sign();

           // Convert the signature to base64
           String signatureBase64 = Base64.getEncoder().encodeToString(signatureBytes);
           return signatureBase64;
       }catch (Exception ex){
           ex.printStackTrace();
           return null;
       }
    }
    public static Boolean verifiedSignature(String signatureBase64,String message){
        try {
            // Load the public key
            byte[] publicKeyBytes = Files.readAllBytes(Paths.get(Defs.publicKeyPath));
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            // Verify the signature using the public key
            Signature signatureVerifier = Signature.getInstance("SHA256withRSA");
            signatureVerifier.initVerify(publicKey);
            signatureVerifier.update(message.getBytes(StandardCharsets.UTF_8));
            byte[] signatureBytesVerifier = Base64.getDecoder().decode(signatureBase64);
            boolean verified = signatureVerifier.verify(signatureBytesVerifier);
            return verified;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static String getDateToString(Date date,String simpleDateFormat) {
        if (date == null) {
            return "00-00-0000";
        }
        return (new SimpleDateFormat(simpleDateFormat)).format(date);
    }


}
