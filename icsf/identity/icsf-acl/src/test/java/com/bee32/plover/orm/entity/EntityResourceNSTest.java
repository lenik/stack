package com.bee32.plover.orm.entity;

import org.junit.Assert;

import com.bee32.plover.orm.PloverORMUnit;
import com.bee32.plover.orm.config.SiteSessionFactoryBean;

public class EntityResourceNSTest
        extends Assert {

    public static void main(String[] args) {
        PloverORMUnit unit = new PloverORMUnit();
        SiteSessionFactoryBean.setForceUnit(unit);

        EntityResourceNS ns = new EntityResourceNS();
        for (EntityResource r : ns.getResources()) {
            System.out.print("ER " + r.getName());
            System.out.print(" => " + r.getAppearance().getDisplayName());
            System.out.println();
        }
    }

}
