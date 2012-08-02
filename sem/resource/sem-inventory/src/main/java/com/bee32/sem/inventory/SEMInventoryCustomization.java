package com.bee32.sem.inventory;

import com.bee32.sem.module.AbstractModuleCustomization;
import com.bee32.sem.term.ITermProvider;

public class SEMInventoryCustomization
        extends AbstractModuleCustomization {

    public SEMInventoryCustomization() {
        super(SEMInventoryModule.class);
    }

    @Override
    public ITermProvider getTermProvider() {
        return new SEMInventoryTerms();
    }
}
