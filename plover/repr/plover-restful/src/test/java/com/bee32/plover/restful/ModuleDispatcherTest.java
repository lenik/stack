package com.bee32.plover.restful;

import com.bee32.plover.disp.tree.OidDispatcher;

public class ModuleDispatcherTest
        extends StaplerTestCase {

    OidDispatcher dispatcher = new OidDispatcher();

    @Override
    protected Object getRoot() {
        return dispatcher;
    }

}
