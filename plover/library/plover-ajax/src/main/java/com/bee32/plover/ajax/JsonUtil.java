package com.bee32.plover.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class JsonUtil {

    static Gson gson = new Gson();

    public static void dump(HttpServletResponse resp, Object obj)
            throws IOException {

        resp.setContentType("application/json; charset=UTF-8");

        PrintWriter out = resp.getWriter();

        try {
            gson.toJson(obj, out);

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
    }

}
