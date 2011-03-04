package com.bee32.plover.restful;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.arch.ModuleLoader;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.Dispatcher;
import com.bee32.plover.disp.util.DispatchUtil;

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
        if (ModuleLoader.getModuleMap().isEmpty())
            throw new Error("No module found, check you test environ.");
    }

    @Test
    public void testDispatchToBookStore()
            throws DispatchException {
        Object bookStore = DispatchUtil.dispatch(dispatcher, mm, bookModuleOid + "/book");
        assertSame(SimpleBooks.store, bookStore);
    }

    @Test
    public void testDispatchToBook()
            throws DispatchException {
        Book book = SimpleBooks.helloWorld;
        Object got = DispatchUtil.dispatch(dispatcher, mm, bookModuleOid + "/book/World");
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
