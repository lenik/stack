package com.bee32.icsf.login;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.free.EncodeException;
import javax.free.HexCodec;

/**
 * <p lang="zh-cn">
 * MD5 实用工具
 */
public class MD5Util {

    static final MessageDigest MD5;
    static {
        try {
            MD5 = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    static final Charset encoding = Charset.forName("utf-8");
    static final HexCodec hexCodec = new HexCodec("");

    public static synchronized String md5(String text) {
        if (text == null)
            throw new NullPointerException("text");

        byte[] bin = text.getBytes(encoding);
        byte[] digest = MD5.digest(bin);
        String hex;
        try {
            hex = hexCodec.encode(digest);
        } catch (EncodeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return hex;
    }

}
