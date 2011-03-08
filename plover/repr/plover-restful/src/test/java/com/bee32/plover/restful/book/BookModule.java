package com.bee32.plover.restful.book;

import org.springframework.context.annotation.Configuration;

import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

@Configuration
@Oid({ 3, 12, PloverOids.repr, PloverOids.reprRestful, 10001 })
public class BookModule
        extends ERModule {

    @Override
    protected void preamble() {
        declare("book", SimpleBooks.store);
    }

}
