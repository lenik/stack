package com.bee32.plover.restful.book;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.restful.RestfulRequest;
import com.bee32.plover.restful.request.RestfulRequestUtil;

public class BookStoreControllerTest
        extends WiredDaoTestCase {

    BookStoreController controller = new BookStoreController();

    @Inject
    BookStore store;

    @Test
    public void testList()
            throws IOException {
        RestfulRequest req = RestfulRequestUtil.wrapDispatched(store);
        MockHttpServletResponse resp = new MockHttpServletResponse();

        String list = controller.content(req, resp);
        System.out.println(list);
    }

    @Ignore
    @Test
    public void testCreateForm()
            throws IOException {
        RestfulRequest req = RestfulRequestUtil.wrapDispatched(store);
        MockHttpServletResponse resp = new MockHttpServletResponse();

        controller.create(req, resp);
        String form = resp.getContentAsString();
        System.out.println(form);
    }

}
