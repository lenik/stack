package com.bee32.plover.orm.unit;

import javax.free.IllegalUsageException;

import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.servlet.test.OuterWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;
import com.bee32.plover.servlet.test.WiredServletTestCase;

public class UsingWac
        extends OuterWac<WiredServletTestCase> {

    @Override
    public int getOrder() {
        return NORMAL_ORDER;
    }

    @Override
    protected void configureContext(ServletTestLibrary stl, WiredServletTestCase outer) {
        Class<?> outerType = outer.getClass();
        Using useUnit = outerType.getAnnotation(Using.class);
        if (useUnit == null)
            throw new IllegalUsageException("@Using isn't defined on " + getClass());

        PersistenceUnit unit = UsingUtil.getUsingUnit(getClass());
        CustomizedSessionFactoryBean.setForceUnit(unit);
    }

}
