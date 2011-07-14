package com.bee32.plover.bookstore;

import com.bee32.plover.orm.util.SamplesContribution;

public class SimpleBooks
        extends SamplesContribution {

    public static final Book tom = new Book("Tom", "A great story");
    public static final Book jerry = new Book("Jerry", "A wonderful cartoon book");
    public static final Book helloWorld = new Book("World", "Hello, world!");

    @Override
    protected void preamble() {
        addNormalSample(SimpleBooks.tom);
        addNormalSample(SimpleBooks.jerry);
        addNormalSample(SimpleBooks.helloWorld);
    }

}
