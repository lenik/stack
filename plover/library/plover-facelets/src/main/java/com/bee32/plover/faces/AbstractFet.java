package com.bee32.plover.faces;

import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
public abstract class AbstractFet
        implements IFacesExceptionTranslator {

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public int handle(Throwable exception) {
        return FILTER;
    }

}
