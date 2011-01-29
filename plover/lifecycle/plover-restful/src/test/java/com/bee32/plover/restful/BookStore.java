package com.bee32.plover.restful;

import java.util.Map;
import java.util.TreeMap;

public class BookStore {

    Map<String, Book> books;

    public BookStore() {
        books = new TreeMap<String, Book>();
    }

    public int count() {
        return books.size();
    }

    public Book getBook(String name) {
        return books.get(name);
    }

    public void addBook(String name, Book book) {
        books.put(name, book);
    }

    public Book removeBook(String name) {
        return books.remove(name);
    }

    public void clear() {
        books.clear();
    }

}
