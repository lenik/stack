package com.bee32.plover.restful.book;

import com.bee32.plover.orm.unit.PersistenceUnit;

public class SimpleBooks {

    public static final PersistenceUnit unit;
    static {
        unit = new PersistenceUnit();
        unit.addPersistedClass(Book.class);
    }

    public static final Book tom = new Book("Tom", "A great story");
    public static final Book jerry = new Book("Jerry", "A wonderful cartoon book");
    public static final Book helloWorld = new Book("World", "Hello, world!");;

    public static final BookStore store = new BookStore();

    public static void init() {
        store.addBook(tom);
        store.addBook(jerry);
        store.addBook(helloWorld);
    }

}
