package com.bee32.plover.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * JSON 工具
 *
 * <p lang="en">
 * JSON Utility
 */
public class JsonUtil {

    static Gson gson = new Gson();

    public static String dump(Object obj) {
        return dump(obj == null ? null : obj.getClass(), obj);
    }

    public static String dump(Type typeOfSrc, Object obj) {
        StringBuilder buf = new StringBuilder();
        dump(buf, typeOfSrc, obj);
        return buf.toString();
    }

    public static void dump(Appendable out, Object obj) {
        gson.toJson(obj, obj == null ? null : obj.getClass(), out);
    }

    public static void dump(Appendable out, Type typeOfSrc, Object obj) {
        gson.toJson(obj, typeOfSrc, out);
    }

    public static <T> T dump(HttpServletResponse resp, Object obj)
            throws IOException {
        dump(resp, obj == null ? null : obj.getClass(), obj);
        return null;
    }

    public static <T> T dump(HttpServletResponse resp, Type typeOfSrc, Object obj)
            throws IOException {

        resp.setContentType("application/json; charset=UTF-8");

        PrintWriter out = resp.getWriter();

        try {
            gson.toJson(obj, typeOfSrc, out);

            /*
             * Don't flush immediately. to make use of any servlet filter.
             */
            // resp.flushBuffer();
        } catch (RuntimeException e) {

            // gson.toJson automaticlly convert IOException to RuntimeException.
            // So, here we restore the original IOException.
            Throwable cause = e.getCause();
            if (cause instanceof IOException)
                throw (IOException) cause;
            else
                throw e;
        }
        return null;
    }

}
