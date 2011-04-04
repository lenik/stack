package com.bee32.plover.restful.book;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.orm.test.bookstore.Book;
import com.bee32.plover.restful.RestfulRequest;
import com.bee32.plover.velocity.VelocityUtil;

public class BookStoreWeb {

    public String list(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        RestfulRequest rreq = (RestfulRequest) req;
        BookStore store = rreq.getTarget();

        String list = VelocityUtil.merge("index", store);

        resp.getWriter().println(list);
        return list;
    }

    public void create(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        RestfulRequest rreq = (RestfulRequest) req;
        BookStore store = rreq.getTarget();

        String name = req.getParameter("name");
        String content = req.getParameter("content");

        Book book = new Book(name, content);
        store.addBook(book);

        resp.sendRedirect("~index");
    }

}
