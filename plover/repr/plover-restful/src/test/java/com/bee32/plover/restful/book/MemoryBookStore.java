package com.bee32.plover.restful.book;

import com.bee32.plover.orm.entity.MapEntityRepository;

public class MemoryBookStore
        extends MapEntityRepository<Book, String> {

    private static final long serialVersionUID = 1L;

    public MemoryBookStore() {
        super(Book.class, String.class);
    }

    public void addBook(String name, Book book) {
        getMap().put(name, book);
    }

}
