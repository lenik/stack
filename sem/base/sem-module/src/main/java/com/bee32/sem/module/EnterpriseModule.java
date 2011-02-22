package com.bee32.sem.module;

import java.util.Collection;

import com.bee32.plover.arch.Module;
import com.bee32.plover.arch.credit.Credit;
import com.bee32.plover.orm.unit.IPersistenceUnitContribution;

/**
 * Module contributions for the menu:
 * <ul>
 * <li>(LOGO) PATH -> module, model
 * <li>FILE -> repr<br>
 * <li>VIEW -> model, renderer<br>
 * <li>BIZ -> model, piggy<br>
 * <li>NET -> internet<br>
 * <li>OPTS -> model, contrib<br>
 * <li>HELP -> model<br>
 */
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
        Collection<Class<?>> classes = contribution.getClasses();
        for (Class<?> c : classes) {
            // ZQ GRAB TO THE UNIT.

        }
    }

}
