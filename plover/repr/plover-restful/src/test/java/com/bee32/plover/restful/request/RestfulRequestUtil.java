package com.bee32.plover.restful.request;

import org.springframework.mock.web.MockHttpServletRequest;

import com.bee32.plover.disp.DispatchContext;

public class RestfulRequestUtil {

    public static RestfulRequest wrap(Object obj) {
        MockHttpServletRequest req0 = new MockHttpServletRequest();

        RestfulRequest rreq = new RestfulRequest(req0);

        DispatchContext context = new DispatchContext(obj);
        rreq.setDispatchContext(context);

        return rreq;
    }

}
