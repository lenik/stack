package com.bee32.sem.module;

import com.bee32.plover.arch.Module;
import com.bee32.plover.arch.credit.Credit;
import com.bee32.plover.orm.unit.IPersistenceUnitContribution;

public abstract class EnterpriseModule
        extends Module
        implements IEnterpriseModule {

    public EnterpriseModule() {
        super();
    }

    public EnterpriseModule(String name) {
        super(name);
    }

    @Override
    public Credit getCredit() {
        return null;
    }

    @Override
    public String getCopyright() {
        return "(C) Copyright 2011 BEE32.com, all rights reserved.";
    }

    protected void contribute(IPersistenceUnitContribution contribution) {

    }

}
