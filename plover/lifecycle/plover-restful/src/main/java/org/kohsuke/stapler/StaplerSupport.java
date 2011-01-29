package org.kohsuke.stapler;

import java.io.IOException;

import javax.servlet.ServletException;

public class StaplerSupport {

    public static void invoke(Stapler stapler, RequestImpl req, ResponseImpl rsp, Object node)
            throws IOException, ServletException {
        stapler.invoke(req, rsp, node);
    }

}
