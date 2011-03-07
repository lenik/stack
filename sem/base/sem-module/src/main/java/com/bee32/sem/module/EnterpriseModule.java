package com.bee32.sem.module;

import com.bee32.plover.arch.credit.Credit;
import com.bee32.plover.orm.util.ERModule;

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
        extends ERModule
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

}
