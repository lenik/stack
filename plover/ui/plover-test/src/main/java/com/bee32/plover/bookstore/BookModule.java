package com.bee32.plover.bookstore;

import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

@Oid({ 3, 12, PloverOids.Repr, PloverOids.repr.Restful, 10001 })
public class BookModule
        extends ERModule {

    @Override
    protected void preamble() {
    }

    @Override
    protected void preamble2() {
        BookStore bookStore = getBean(BookStore.class);
        // declare("book", bookStore);
        export(bookStore, "book");
    }

}
