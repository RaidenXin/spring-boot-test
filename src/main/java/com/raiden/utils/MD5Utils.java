package com.raiden.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public static String encode(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = md5.digest(str.getBytes("utf-8"));

        StringBuffer buf = new StringBuffer(StringUtils.EMPTY);
        int i;
        for (int offset = 0; offset < bytes.length; offset++) {
            i = bytes[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16){
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }
}
