package com.bee32.plover.bookstore;

import com.bee32.plover.orm.entity.MapEntityRepository;

public class MemoryBookStore
        extends MapEntityRepository<Book, Integer> {

    private static final long serialVersionUID = 1L;

    public void addBook(Book book) {
        getMap().put(book.getId(), book);
    }

}
