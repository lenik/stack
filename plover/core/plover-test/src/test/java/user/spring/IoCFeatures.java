package user.spring;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bee32.plover.restful.book.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("context1.xml")
// @TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
// @Transactional
public class IoCFeatures
        extends Assert {

    @Autowired
    ApplicationContext context;

    @Inject
    Book book1;

    @Autowired
    Book book2;

    @Autowired
    @Named("book2")
    Book bookAuto;

    @Test
    public void testBook1() {
        assertEquals("name1", book1.getName());
    }

    @Test
    public void testBook2() {
        assertEquals("name2", book2.getName());
    }

    @Test
    public void testBookAuto() {
        assertEquals("name2", bookAuto.getName());
    }

    @Test
    public void testImplicitWire() {
        FooBean foo = new FooBean();
        Book fooBook = foo.getBook1();
        assertNull(fooBook);
    }

    @Test
    public void testThruContext() {
        if (context == null)
            throw new NullPointerException("context");

        FooBean foo = context.getBean(FooBean.class);
        Book fooBook = foo.getBook1();
        System.out.println("ctx/foo: " + fooBook);
    }

}
