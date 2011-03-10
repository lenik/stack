package com.bee32.plover.restful.book;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.PersistenceUnitSelection;

@Service
public class SimpleBooks
        extends BookStore
        implements InitializingBean {

    public static final PersistenceUnit unit;
    static {
        unit = new PersistenceUnit("book-store");
        unit.addPersistedClass(Book.class);

        // XXX
        PersistenceUnitSelection.getSharedSelection().add(unit);
    }

    public static final Book tom = new Book("Tom", "A great story");
    public static final Book jerry = new Book("Jerry", "A wonderful cartoon book");
    public static final Book helloWorld = new Book("World", "Hello, world!");;

    @Override
    public void afterPropertiesSet() {
        this.addBook(tom);
        this.addBook(jerry);
        this.addBook(helloWorld);
    }

}
