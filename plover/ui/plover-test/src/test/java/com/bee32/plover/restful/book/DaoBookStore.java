package com.bee32.plover.restful.book;

import com.bee32.plover.inject.NotAComponent;
import com.bee32.plover.orm.entity.AbstractDao;
import com.bee32.plover.orm.test.bookstore.Book;

@NotAComponent
public class DaoBookStore
        extends AbstractDao<Book, Integer> {

    private static final long serialVersionUID = 1L;

    public DaoBookStore() {
        super(Book.class, Integer.class);
    }

    public void addBook(Book book) {
        save(book);
    }

}
