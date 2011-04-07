package com.bee32.plover.bookstore;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.restful.test.RestfulTestCase;

@Using(BookStoreUnit.class)
public class BookModuleTest
        extends RestfulTestCase {

    static String bookModuleOid = OidUtil.getOid(BookModule.class).toPath();

    @Inject
    BookStore store;

    public BookModuleTest() {
    }

    @Test
    public void testBookModuleCredit()
            throws Exception {
        setupH2Console();

        String creditUri = "/" + bookModuleOid + "/credit";
        String credit = stl.httpGet(creditUri).getContent();

        System.out.println(credit);

        assertTrue(credit.contains("99"));
    }

    public static void main(String[] args)
            throws Exception {
        new BookModuleTest().browseAndWait("/" + bookModuleOid + "/");
    }

}
