package com.bee32.plover.bookstore;

import com.bee32.plover.orm.unit.PersistenceUnit;

public class BookStoreUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Book.class);
    }

}
