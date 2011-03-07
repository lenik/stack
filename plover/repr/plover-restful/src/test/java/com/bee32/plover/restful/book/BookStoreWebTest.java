package com.bee32.plover.restful.book;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import com.bee32.plover.restful.request.RestfulRequest;
import com.bee32.plover.restful.request.RestfulRequestUtil;

/**
 * This test runs in non-dispatch mode.
 */
public class BookStoreWebTest
        extends Assert {

    BookStoreWeb web = new BookStoreWeb();

    @Test
    public void testList()
            throws IOException {
        RestfulRequest req = RestfulRequestUtil.wrapDispatched(SimpleBooks.store);
        MockHttpServletResponse resp = new MockHttpServletResponse();

        String list = web.list(req, resp);
        System.out.println(list);
    }

}
