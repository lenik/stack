package com.bee32.plover.restful.book;

import javax.inject.Inject;

import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

@Oid({ 3, 12, PloverOids.repr, PloverOids.reprRestful, 10001 })
public class BookModule
        extends ERModule {

    @Inject
    BookStore bookStore;

    @Override
    protected void preamble() {
        // declare("book", bookStore);
        export(bookStore, "book");
    }

}
