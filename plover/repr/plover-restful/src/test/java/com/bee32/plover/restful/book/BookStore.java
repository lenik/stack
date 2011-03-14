package com.bee32.plover.restful.book;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.util.IStruct;

public class BookStore
        extends DaoBookStore {

    public Book getBook(String name) {
        return retrieve(name);
    }

    public void removeBook(String name) {
        deleteByKey(name);
    }

    public void clear() {
        deleteAll();
    }

    @Override
    public boolean populate(Book instance, IStruct struct)
            throws BuildException {
        instance.setName((String) struct.getScalar("name"));
        instance.setContent((String) struct.getScalar("content"));
        return true;
    }

    {
        addNormalSample(SimpleBooks.tom);
        addNormalSample(SimpleBooks.jerry);
        addNormalSample(SimpleBooks.helloWorld);
    }

}
