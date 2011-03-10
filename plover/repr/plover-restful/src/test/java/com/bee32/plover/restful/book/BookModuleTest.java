package com.bee32.plover.restful.book;

import org.junit.Test;

import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.restful.test.RestfulTestCase;

public class BookModuleTest
        extends RestfulTestCase {

    static String bookModuleOid = OidUtil.getOid(BookModule.class).toPath();

    // @Inject
    // BookStore store;

    public BookModuleTest() {
        // super(SimpleBooks.unit);
    }

    @Test
    public void testBookModuleCredit()
            throws Exception {
        setupH2Console();

        String creditUri = "/" + bookModuleOid + "/credit";
        String credit = rtl.httpGet(creditUri).getContent();

        System.out.println(credit);

        assertTrue(credit.contains("99"));
    }

    public static void main(String[] args)
            throws Exception {
        BookModuleTest test = new BookModuleTest();

        test.setUpLibraries();

        test.rtl.browse();
        test.rtl.readLine();
    }

}
