package com.bee32.plover.restful.request;

import org.springframework.mock.web.MockHttpServletRequest;

import com.bee32.plover.disp.util.Arrival;
import com.bee32.plover.disp.util.IArrival;
import com.bee32.plover.restful.RESTfulRequest;

public class RESTfulRequestUtil {

    public static RESTfulRequest wrapDispatched(Object obj) {
        MockHttpServletRequest req0 = new MockHttpServletRequest();

        RESTfulRequest rreq = new RESTfulRequest(req0);

        IArrival arrival = new Arrival(obj);
        rreq.setArrival(arrival);

        return rreq;
    }

}
