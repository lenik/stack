package com.bee32.plover.bookstore;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;

import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.restful.RestfulRequest;
import com.bee32.plover.restful.request.RestfulRequestUtil;
import com.bee32.plover.servlet.mock.MockRestfulResponse;

public class BookStoreControllerTest
        extends WiredDaoTestCase {

    @Inject
    BookStore store;

    @Inject
    BookStoreController controller;

    @Test
    public void testList()
            throws IOException {
        RestfulRequest req = RestfulRequestUtil.wrapDispatched(store);
        MockRestfulResponse resp = new MockRestfulResponse();

        String list = controller.content(req, resp);
        System.out.println(list);
    }

    @Ignore
    @Test
    public void testCreateForm()
            throws IOException {
        RestfulRequest req = RestfulRequestUtil.wrapDispatched(store);
        MockRestfulResponse resp = new MockRestfulResponse();

        controller.create(req, resp);
        String form = resp.getContentAsString();
        System.out.println(form);
    }

}
