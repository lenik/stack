package com.bee32.plover.orm.ext.basic;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.javascript.util.Javascripts;

public class HandlerUtil {

    protected static class WithContext {

        final HttpServletRequest req;
        final HttpServletResponse resp;

        public WithContext(HttpServletRequest req, HttpServletResponse resp) {
            if (req == null)
                throw new NullPointerException("req");

            if (resp == null)
                throw new NullPointerException("resp");

            this.req = req;
            this.resp = resp;
        }

        protected <T> T errorAlert(String message)
                throws IOException {
            Javascripts.alertAndBack(message).dump(req, resp);
            return null;
        }

    }

}
