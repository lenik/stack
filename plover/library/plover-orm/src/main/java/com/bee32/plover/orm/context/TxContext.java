package com.bee32.plover.orm.context;

import com.bee32.plover.inject.cref.ContextRef;
import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("tx-context.xml")
public class TxContext
        extends ContextRef {

    public TxContext() {
        super();
    }

    public TxContext(ContextRef... parents) {
        super(parents);
    }

}
