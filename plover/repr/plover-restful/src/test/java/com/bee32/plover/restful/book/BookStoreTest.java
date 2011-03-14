package com.bee32.plover.restful.book;

import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.IModuleLoader;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.Dispatcher;
import com.bee32.plover.disp.util.DispatchUtil;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.restful.ModuleManager;

public class BookStoreTest
        extends WiredDaoTestCase {

    static String bookModuleOid = OidUtil.getOid(BookModule.class).toPath();

    @Inject
    IModuleLoader moduleLoader;

    @Inject
    ModuleManager mm;

    /**
     * The dispatcher is self-managed, not have to be injected.
     */
    Dispatcher dispatcher = Dispatcher.getInstance();

    @Inject
    BookStore bookStore;

    public BookStoreTest() {
        super(SimpleBooks.unit);
    }

    @Override
    public void afterPropertiesSet() {
        Map<String, IModule> map = moduleLoader.getModuleMap();
        if (map.isEmpty())
            throw new Error("No module found, check you test environ.");

        super.afterPropertiesSet();
    }

    @Test
    public void testDispatchToBookStore()
            throws DispatchException {
        Object dispatchedStore = DispatchUtil.dispatch(dispatcher, mm, bookModuleOid + "/book");
        assertSame(bookStore, dispatchedStore);
    }

    @Test
    public void testDispatchToBook()
            throws DispatchException {
        for (Book book:bookStore.list())
            System.out.println(book);
        bookStore.getChild("123");
        Object got = DispatchUtil.dispatch(dispatcher, mm, bookModuleOid + "/book/123");

        // for MemoryBookStore, this should be exact same.
        assertEquals(SimpleBooks.helloWorld, got);
    }

    @Test
    public void testReverseBookStore()
            throws DispatchException {
        String path = mm.getReversedPath(bookStore);
        assertEquals(bookModuleOid + "/book", path);
    }

    @Test
    public void testReverseBook()
            throws DispatchException {
        String path = mm.getReversedPath(SimpleBooks.helloWorld);
        assertEquals(bookModuleOid + "/book/123", path);
    }

}
