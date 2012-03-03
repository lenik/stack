package com.bee32.plover.bookstore;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.Dispatcher;
import com.bee32.plover.disp.util.DispatchUtil;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.SamplesLoader;
import com.bee32.plover.orm.util.SuperSamplePackage.Normals;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.restful.ModuleIndex;

@Using(BookStoreUnit.class)
public class BookStoreTest
        extends WiredDaoTestCase {

    static String bookModuleOid = OidUtil.getOid(BookModule.class).toPath();

    @Inject
    ModuleIndex mm;

    @Inject
    SamplesLoader samplesLoader;

    /**
     * The dispatcher is self-managed, not have to be injected.
     */
    Dispatcher dispatcher = Dispatcher.getInstance();

    @Inject
    BookStore bookStore;

    @Inject
    SimpleBooks books;

    @Before
    public void loadSamples() {
        samplesLoader.loadSamples(Normals.class);
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

        int id = books.helloWorld.getId();
        Object got = DispatchUtil.dispatch(dispatcher, mm, bookModuleOid + "/book/" + id);

        // for MemoryBookStore, this should be exact same.
        assertEquals(books.helloWorld, got);
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

        String path = mm.getReversedPath(books.helloWorld);

        int id = books.helloWorld.getId();

        assertEquals(bookModuleOid + "/book/" + id, path);
    }

}
