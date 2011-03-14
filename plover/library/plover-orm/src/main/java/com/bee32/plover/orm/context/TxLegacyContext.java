package com.bee32.plover.orm.context;

import com.bee32.plover.inject.LegacyContext;
import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("tx-context.xml")
public class TxLegacyContext
        extends LegacyContext {

}
