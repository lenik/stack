package com.bee32.plover.arch.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class FriendData {

    public static String script(Class<?> clazz, String extension) {
        return script(clazz, extension, "utf-8");
    }

    public static String script(Class<?> clazz, String extension, String charset) {
        String resource = clazz.getName().replace('.', '/');
        resource = "/" + resource + "." + extension;
        URL url = clazz.getResource(resource);

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (InputStream in = url.openStream()) {
            int b;
            while ((b = in.read()) != -1) {
                buf.write(b);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        if (charset == null)
            charset = "utf-8";

        String s;
        try {
            s = new String(buf.toByteArray(), charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return s;
    }

}
