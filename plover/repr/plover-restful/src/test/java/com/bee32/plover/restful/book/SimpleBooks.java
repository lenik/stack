package com.bee32.plover.restful.book;

import com.bee32.plover.orm.test.bookstore.Book;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.PersistenceUnitSelection;
import com.bee32.plover.orm.util.EntitySamplesContribution;

public class SimpleBooks
        extends EntitySamplesContribution {

    public static final PersistenceUnit unit;
    static {
        unit = new PersistenceUnit("book-store");
        unit.addPersistedClass(Book.class);

        // XXX
        PersistenceUnitSelection.getSharedSelection().add(unit);
    }

    public static final Book tom = new Book("Tom", "A great story");
    public static final Book jerry = new Book("Jerry", "A wonderful cartoon book");
    public static final Book helloWorld = new Book("World", "Hello, world!");

    @Override
    protected void preamble() {
        addNormalSample(SimpleBooks.tom);
        addNormalSample(SimpleBooks.jerry);
        addNormalSample(SimpleBooks.helloWorld);
    }

}
