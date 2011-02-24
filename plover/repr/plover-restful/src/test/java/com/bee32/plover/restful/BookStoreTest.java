package com.bee32.plover.restful;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.Dispatcher;

public class BookStoreTest
        extends Assert {

    /**
     * @see BookModule
     */
    static String bookModuleOid = "13/2/6/2/10001";

    Dispatcher dispatcher = Dispatcher.getInstance();
    ModuleManager mm = ModuleManager.getInstance();

    DispatchFilter dispatchFilter = new DispatchFilter();

    public BookStoreTest() {
    }

    @Test
    public void testDispatchToBookStore()
            throws DispatchException {
        Object bookStore = dispatcher.dispatch(mm, bookModuleOid + "/book");
        assertSame(SimpleBooks.store, bookStore);
    }

    @Test
    public void testDispatchToBook()
            throws DispatchException {
        Book book = SimpleBooks.helloWorld;
        Object got = dispatcher.dispatch(mm, bookModuleOid + "/book/World");
        assertSame(book, got);
    }

    @Test
    public void testReverseBookStore()
            throws DispatchException {
        String path = mm.getReversedPath(SimpleBooks.store);
        assertEquals(bookModuleOid + "/book", path);
    }

    @Test
    public void testReverseBook()
            throws DispatchException {
        String path = mm.getReversedPath(SimpleBooks.helloWorld);
        assertEquals(bookModuleOid + "/book/World", path);
    }

}
