package com.bee32.plover.restful.book;

public class SimpleBooks {

    public static final Book tom = new Book("Tom", "A great story");
    public static final Book jerry = new Book("Jerry", "A wonderful cartoon book");
    public static final Book helloWorld = new Book("World", "Hello, world!");;

    public static final BookStore store = new BookStore();

    static {
        store.addBook(tom);
        store.addBook(jerry);
        store.addBook(helloWorld);
    }

}
