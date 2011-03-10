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
import com.bee32.plover.restful.DispatchFilter;
import com.bee32.plover.restful.ModuleManager;

public class BookStoreTest
        extends WiredDaoTestCase {

    static String bookModuleOid = OidUtil.getOid(BookModule.class).toPath();

    @Inject
    IModuleLoader moduleLoader;

    Dispatcher dispatcher = Dispatcher.getInstance();
    ModuleManager mm = ModuleManager.getInstance();

    DispatchFilter dispatchFilter = new DispatchFilter();

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
        Book book = SimpleBooks.helloWorld;
        Object got = DispatchUtil.dispatch(dispatcher, mm, bookModuleOid + "/book/World");

        // for MemoryBookStore, this should be exact same.
        assertEquals(book, got);
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
        assertEquals(bookModuleOid + "/book/World", path);
    }

}
