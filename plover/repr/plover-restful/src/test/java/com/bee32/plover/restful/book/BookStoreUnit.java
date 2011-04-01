package com.bee32.plover.restful.book;

import com.bee32.plover.orm.test.bookstore.Book;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class BookStoreUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Book.class);
    }

}
