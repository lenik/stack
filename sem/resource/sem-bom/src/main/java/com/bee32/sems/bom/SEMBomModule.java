package com.bee32.sems.bom;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;
import com.bee32.sems.bom.dao.ComponentRepositoryHibernate;
import com.bee32.sems.bom.dao.ProductRepositoryHibernate;

@Oid({ 3, 15, SEMOids.Resource, SEMOids.resource.Store })
public class SEMBomModule
        extends EnterpriseModule {

    @Override
    protected void preamble() {
        export(ComponentRepositoryHibernate.class, "component");
        export(ProductRepositoryHibernate.class, "product");
    }

}
