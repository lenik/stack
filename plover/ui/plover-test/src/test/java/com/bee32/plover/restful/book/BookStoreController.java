package com.bee32.plover.restful.book;

import java.io.IOException;

import javax.servlet.ServletResponse;

import com.bee32.plover.orm.test.bookstore.Book;
import com.bee32.plover.restful.IRestfulRequest;
import com.bee32.plover.velocity.VelocityUtil;

public class BookStoreController {

    public String content(IRestfulRequest req, ServletResponse resp)
            throws IOException {
        BookStore store = (BookStore) req.getTarget();

        String list = VelocityUtil.merge("index", store);

        resp.getWriter().println(list);
        return list;
    }

    public Object create(IRestfulRequest req, ServletResponse resp)
            throws IOException {
        BookStore store = (BookStore) req.getTarget();

        String name = req.getParameter("name");
        String content = req.getParameter("content");

        Book book = new Book(name, content);
        store.addBook(book);

        return store;
    }

}
