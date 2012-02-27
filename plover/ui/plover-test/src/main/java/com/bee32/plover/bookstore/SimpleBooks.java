package com.bee32.plover.bookstore;

import com.bee32.plover.orm.util.SampleList;
import com.bee32.plover.orm.util.NormalSamples;

public class SimpleBooks
        extends NormalSamples {

    public static final Book tom = new Book("Tom", "A great story");
    public static final Book jerry = new Book("Jerry", "A wonderful cartoon book");
    public static final Book helloWorld = new Book("World", "Hello, world!");

    @Override
    protected void getSamples(SampleList samples) {
        samples.add(tom);
        samples.add(jerry);
        samples.add(helloWorld);
    }

}
