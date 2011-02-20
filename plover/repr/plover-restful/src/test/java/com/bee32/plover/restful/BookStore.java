package com.bee32.plover.restful;

import com.bee32.plover.orm.entity.MapEntityRepository;

public class BookStore
        extends MapEntityRepository<Book, String> {

    private static final long serialVersionUID = 1L;

    public BookStore() {
        super(Book.class, String.class);
    }

    public void addBook(Book book) {
        addBook(book.getName(), book);
    }

    public void addBook(String name, Book book) {
        getMap().put(name, book);
    }

    public Book getBook(String name) {
        return retrieve(name);
    }

    public void removeBook(String name) {
        deleteByKey(name);
    }

    public void clear() {
        deleteAll();
    }

}
