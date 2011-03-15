package com.bee32.plover.orm.context;

import com.bee32.plover.inject.ConfigResourceObject;
import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("tx-context.xml")
public class TxContext
        extends ConfigResourceObject {

    public TxContext() {
        super();
    }

    public TxContext(ConfigResourceObject... parents) {
        super(parents);
    }

}
