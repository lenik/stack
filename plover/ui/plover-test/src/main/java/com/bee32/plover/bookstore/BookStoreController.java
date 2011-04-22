package com.bee32.plover.bookstore;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.restful.IRESTfulRequest;
import com.bee32.plover.restful.IRESTfulResponse;
import com.bee32.plover.restful.MethodNames;
import com.bee32.plover.restful.util.IRESTfulController;
import com.bee32.plover.velocity.VelocityUtil;

@Component
@Lazy
public class BookStoreController
        implements IRESTfulController {

    @Inject
    public String content(IRESTfulRequest req, ServletResponse resp)
            throws IOException {
        BookStore store = (BookStore) req.getTarget();

        String list = VelocityUtil.merge("index", store);

        resp.getWriter().println(list);
        return list;
    }

    public Object create(IRESTfulRequest req, IRESTfulResponse resp)
            throws IOException {
        BookStore store = (BookStore) req.getTarget();

        String name = req.getParameter("name");
        String content = req.getParameter("content");

        Book book = new Book(name, content);
        store.addBook(book);

        resp.setMethod(MethodNames.INDEX);
        return store;
    }

}
