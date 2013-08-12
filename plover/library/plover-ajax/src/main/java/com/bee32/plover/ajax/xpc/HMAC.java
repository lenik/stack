package com.bee32.plover.ajax.xpc;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 主机验证码
 *
 * <p lang="en">
 * HMAC
 */
public class HMAC {

    static final Logger logger = LoggerFactory.getLogger("HMAC");

    /**
     * HMAC 使用函数：
     *
     * <pre>
     *      k = sha1( string(secret) )
     *      hmac(k, m) = sha1( (k+opad) || sha1( (k+ipad) || message ) )
     * </pre>
     *
     * 对应的 Javascript 函数为 $.hmac(secret, m)
     *
     * @see /type-ublock-webapp/src/main/webapp/js/jquery.ajaxex.js
     */
    public static byte[] hmac(byte[] secret, byte[] message) {
        if (secret == null)
            throw new NullPointerException("secret");
        if (message == null)
            throw new NullPointerException("message");

        MessageDigest sha1;
        try {
            sha1 = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        byte opad = 0x50;
        byte ipad = 0x36;

        byte[] k = sha1.digest(secret);
        logger.debug("k = " + hex(k));
        byte[] ki = new byte[k.length];
        byte[] ko = new byte[k.length];
        for (int i = 0; i < k.length; i++) {
            ki[i] = (byte) (k[i] ^ ipad);
            ko[i] = (byte) (k[i] ^ opad);
        }

        sha1.update(ki);
        byte[] inner = sha1.digest(message);
        logger.debug("Inner: " + hex(inner));

        sha1.update(ko);
        byte[] outer = sha1.digest(inner);
        logger.debug("outer: " + hex(outer));

        return outer;
    }

    static final Charset utf8 = Charset.forName("utf-8");
    static final char[] tab = "0123456789abcdef".toCharArray();

    public static String hmac_utf8(String secret, String message) {
        if (secret == null)
            throw new NullPointerException("secret");
        if (message == null)
            throw new NullPointerException("message");

        byte[] _secret = secret.getBytes(utf8);
        byte[] _message = message.getBytes(utf8);
        byte[] _hmac = hmac(_secret, _message);
        return hex(_hmac);
    }

    static String hex(byte[] bin) {
        StringBuilder buf = new StringBuilder(bin.length * 2);
        for (int i = 0; i < bin.length; i++) {
            byte byt = bin[i];
            int lo = byt & 0x0f;
            int hi = (byt & 0xf0) >> 4;
            buf.append(tab[hi]);
            buf.append(tab[lo]);
        }
        return buf.toString();
    }

}
