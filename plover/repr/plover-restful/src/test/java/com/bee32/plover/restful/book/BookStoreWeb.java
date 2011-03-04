package com.bee32.plover.restful.book;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.restful.request.RestfulRequest;
import com.bee32.plover.velocity.Velocity;

public class BookStoreWeb {

    public String list(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        RestfulRequest rreq = (RestfulRequest) req;

        BookStore store = rreq.getObject();

        String list = Velocity.merge("list", store);

        resp.getWriter().println(list);
        return list;
    }

}
