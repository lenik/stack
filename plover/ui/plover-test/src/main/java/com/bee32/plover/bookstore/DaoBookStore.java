package com.bee32.plover.bookstore;

import com.bee32.plover.inject.NotAComponent;
import com.bee32.plover.orm.entity.EntityDao;

@NotAComponent
public class DaoBookStore
        extends EntityDao<Book, Integer> {

    public void addBook(Book book) {
        save(book);
    }

}
