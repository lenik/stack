package com.bee32.plover.restful.book;

import com.bee32.plover.orm.entity.HibernateEntityRepository;

public class HibernateBookStore
        extends HibernateEntityRepository<Book, String> {

    private static final long serialVersionUID = 1L;

    public HibernateBookStore() {
        super(Book.class, String.class);
    }

    public void addBook(String name, Book book) {
        save(book);
    }

}
