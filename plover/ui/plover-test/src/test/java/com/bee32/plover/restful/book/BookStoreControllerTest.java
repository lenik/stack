package com.bee32.plover.restful.book;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.restful.RestfulRequest;
import com.bee32.plover.restful.request.RestfulRequestUtil;

/**
 * This test runs in non-dispatch mode.
 *
 * So, the book store samples are not set.
 */
//@Ignore
public class BookStoreControllerTest
        extends WiredDaoTestCase {

    BookStoreController web = new BookStoreController();

    @Inject
    BookStore store;

    @Test
    public void testList()
            throws IOException {
        RestfulRequest req = RestfulRequestUtil.wrapDispatched(store);
        MockHttpServletResponse resp = new MockHttpServletResponse();

        String list = web.list(req, resp);
        System.out.println(list);
    }

}
