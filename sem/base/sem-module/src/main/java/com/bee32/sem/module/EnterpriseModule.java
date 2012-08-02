package com.bee32.sem.module;

import java.io.IOException;

import com.bee32.plover.arch.IAppProfile;
import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.credit.Credit;
import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.site.cfg.ISiteConfigBlock;
import com.bee32.plover.test.ServiceCollector;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.term.ITermProvider;

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

    static final Class<?>[] serviceClasses;
    static {
        serviceClasses = new Class<?>[] {
                //
                IModule.class, //
                MenuComposite.class, //
                IAppProfile.class, //
                ISiteConfigBlock.class, //
                ITermProvider.class, //
        };
    }

    public void collectServices()
            throws IOException {

        ServiceCollector<ServiceCollector<?>> collector = new ServiceCollector<ServiceCollector<?>>(false) {
            @Override
            protected void scan() {
                for (Class<?> serviceClass : serviceClasses) {
                    for (Class<?> serviceImpl : getExtensions(serviceClass, false))
                        publish(serviceClass, serviceImpl);
                }
            }
        };

        collector.collect();
    }

}
