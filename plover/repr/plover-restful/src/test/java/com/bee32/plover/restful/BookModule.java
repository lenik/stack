package com.bee32.plover.restful;

import com.bee32.plover.arch.Module;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

@Oid({ 13, 2, PloverOids.REPR, 3001 })
public class BookModule
        extends Module {

    public BookModule() {
        declare("book", SimpleBooks.store);
        SimpleBooks.store.setParent(this);
    }

}
