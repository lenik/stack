package com.bee32.plover.orm.context;

import com.bee32.plover.inject.TemplateContext;
import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("tx-context.xml")
public class TxTemplateContext
        extends TemplateContext {

}
