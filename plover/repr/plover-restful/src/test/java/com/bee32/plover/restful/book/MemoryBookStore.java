package com.bee32.plover.restful.book;

import com.bee32.plover.orm.entity.MapEntityRepository;
import com.bee32.plover.orm.test.bookstore.Book;

public class MemoryBookStore
        extends MapEntityRepository<Book, Integer> {

    private static final long serialVersionUID = 1L;

    public MemoryBookStore() {
        super(Book.class, Integer.class);
    }

    public void addBook(Book book) {
        getMap().put(book.getId(), book);
    }

}
