package com.trucaller.common;

import java.text.SimpleDateFormat;

public class Defs {
    public static Integer BadRequest=400;
    public static Integer oK=200;
    public static Integer Unauthorized=401;
    public static Integer Conflict=409;
    public static Integer InternalServerError=500;
    public static String BadRequest_MSG="Signature is corrupted";
    public static String Unauthorized_MSG="Signature verification failed";
    public static String Conflict_MSG="Transaction already performed";
    public static String InternalServerError_MSG="Error while granting premium";
    public static String privateKeyPath="E:\\Trucaller\\trucaller_api\\src\\main\\resources\\mykey.pkcs8";
    public static String publicKeyPath="E:\\Trucaller\\trucaller_api\\src\\main\\resources\\mykey_pub.der";
    public static String logDate= "dd_MM_yyyy";
    public static String partnerId="momagic";
}
