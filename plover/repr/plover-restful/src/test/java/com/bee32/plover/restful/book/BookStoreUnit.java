package com.bee32.plover.restful.book;

import com.bee32.plover.orm.test.bookstore.Book;
import com.bee32.plover.orm.unit.PUnit;

public class BookStoreUnit
        extends PUnit {

    @Override
    protected void preamble() {
        add(Book.class);
    }

}
