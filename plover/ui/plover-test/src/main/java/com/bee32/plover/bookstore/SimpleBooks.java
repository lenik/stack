package com.bee32.plover.bookstore;

import com.bee32.plover.orm.util.SampleContribution;

public class SimpleBooks
        extends SampleContribution {

    public static final Book tom = new Book("Tom", "A great story");
    public static final Book jerry = new Book("Jerry", "A wonderful cartoon book");
    public static final Book helloWorld = new Book("World", "Hello, world!");

    @Override
    protected void listSamples() {
        add(tom);
        add(jerry);
        add(helloWorld);
    }

}
