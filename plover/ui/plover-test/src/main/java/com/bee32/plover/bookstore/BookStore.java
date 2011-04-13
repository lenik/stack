package com.bee32.plover.bookstore;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.util.IStruct;

public class BookStore
        extends DaoBookStore {

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

}
