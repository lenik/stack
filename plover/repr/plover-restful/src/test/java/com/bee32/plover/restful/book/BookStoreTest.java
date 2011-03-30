package com.bee32.plover.restful.book;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.Dispatcher;
import com.bee32.plover.disp.util.DispatchUtil;
import com.bee32.plover.orm.util.SamplesLoader;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.restful.ModuleManager;

public class BookStoreTest
        extends WiredDaoTestCase {

    static String bookModuleOid = OidUtil.getOid(BookModule.class).toPath();

    @Inject
    ModuleManager mm;

    @Inject
    SamplesLoader samplesLoader;

    /**
     * The dispatcher is self-managed, not have to be injected.
     */
    Dispatcher dispatcher = Dispatcher.getInstance();

    @Inject
    BookStore bookStore;

    public BookStoreTest() {
        super(SimpleBooks.unit);
    }

    @Before
    public void loadSamples() {
        samplesLoader.loadNormalSamples();
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

        int id = SimpleBooks.helloWorld.getId();
        Object got = DispatchUtil.dispatch(dispatcher, mm, bookModuleOid + "/book/" + id);

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

        int id = SimpleBooks.helloWorld.getId();

        assertEquals(bookModuleOid + "/book/" + id, path);
    }

}
