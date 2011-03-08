package com.bee32.plover.restful.book;

import java.util.Map;

import org.junit.Test;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.ServiceModuleLoader;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.Dispatcher;
import com.bee32.plover.disp.util.DispatchUtil;
import com.bee32.plover.orm.util.hibernate.HibernateUnitDao;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.restful.DispatchFilter;
import com.bee32.plover.restful.ModuleManager;
import com.bee32.plover.test.WiredAssembledTestCase;

public class BookStoreTest
        extends WiredAssembledTestCase {

    static String bookModuleOid = OidUtil.getOid(BookModule.class).toPath();

    HibernateUnitDao hl;

    Dispatcher dispatcher = Dispatcher.getInstance();
    ModuleManager mm = ModuleManager.getInstance();

    DispatchFilter dispatchFilter = new DispatchFilter();

    public BookStoreTest() {
        install(hl = new HibernateUnitDao(SimpleBooks.unit));

        Map<String, IModule> map = ServiceModuleLoader.getModuleMap();
        if (map.isEmpty())
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
        SimpleBooks.store.setSessionFactory(hl.getSessionFactory());
        SimpleBooks.init();

        Book book = SimpleBooks.helloWorld;
        Object got = DispatchUtil.dispatch(dispatcher, mm, bookModuleOid + "/book/World");

        // for MemoryBookStore, this should be exact same.
        assertEquals(book, got);
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
        SimpleBooks.store.setSessionFactory(hl.getSessionFactory());
        SimpleBooks.init();

        String path = mm.getReversedPath(SimpleBooks.helloWorld);
        assertEquals(bookModuleOid + "/book/World", path);
    }

}
