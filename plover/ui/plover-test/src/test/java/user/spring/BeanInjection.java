package user.spring;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.bee32.plover.orm.test.bookstore.Book;
import com.bee32.plover.test.WiredTestCase;

@ContextConfiguration("context1.xml")
// @TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
// @Transactional
@Ignore
public class BeanInjection
        extends WiredTestCase {

    @Autowired
    ApplicationContext context;

    @Inject
    Book book1;

    @Autowired
    Book book2;

    @Autowired
    @Named("book2")
    Book bookAuto;

    @Inject
    @Version("B")
    Book book10;

    @Test
    public void testBook1() {
        assertEquals("name1", book1.getName());
    }

    @Test
    public void testBook2() {
        assertEquals("name2", book2.getName());
    }

    @Test
    @Ignore
    public void testBook10() {
        assertEquals("name10B", book10.getName());
    }

    @Test
    public void testBookAuto() {
        assertEquals("name2", bookAuto.getName());
    }

    @Test
    public void testImplicitWire() {
        FooBean foo = new FooBean();
        Book fooBook = foo.getBook();
        assertNull(fooBook);
    }

    @Test
    public void testThruContext() {
        FooBean foo = context.getBean(FooBean.class);
        Book fooBook = foo.getBook();
        System.out.println("ctx/foo: " + fooBook);
    }

    @Test
    public void testGetBigBlob() {
        FooBean foo = context.getBean(FooBean.class);
        System.out.println("Internal blob = " + foo.blob);
        System.out.println("Start to get big blob");
        BigBlob big = foo.getBigBlob();
        System.out.println("Got big: " + big);
    }

    @Autowired
    @Named("magic")
    Book bookMagic;

    @Test
    public void testBookMagic() {
        System.out.println("magic: " + bookMagic);
        assertNotNull(bookMagic);
    }

}
