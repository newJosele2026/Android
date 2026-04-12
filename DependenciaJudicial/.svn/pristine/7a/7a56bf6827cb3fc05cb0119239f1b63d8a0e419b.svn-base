package com.slt.dependenciajudicial.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Sergio on 25/10/2017.
 */

public class Security {

    public static String convertMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }

    public static String encodeString(String s) {

        byte[] data = new byte[0];

        try {
            data = s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {

            String base64Encoded = Base64.encodeToString(data, Base64.DEFAULT);

            return base64Encoded;
        }
    }

    public static String decodeString(String encoded) {

        byte[] dataDec = Base64.decode(encoded, Base64.DEFAULT);
        String decodedString = "";

        try {
            decodedString = new String(dataDec, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        } finally {

            return decodedString;
        }
    }

}
