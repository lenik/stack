package com.bee32.plover.restful.book;

import com.bee32.plover.arch.Module;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

@Oid({ 3, 12, PloverOids.repr, PloverOids.reprRestful, 10001 })
public class BookModule
        extends Module {

    public BookModule() {
        declare("book", SimpleBooks.store);
    }

}
