package com.bee32.plover.orm;

import com.bee32.plover.orm.config.SiteSessionFactoryBean;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.UsingUtil;
import com.bee32.plover.test.AbstractFps;

public class AllowUsingFps
        extends AbstractFps {

    @Override
    public void setup(Class<?> playerClass) {
        PersistenceUnit usedUnit = UsingUtil.getUsingUnit(playerClass);

        logger.debug("Plover-ORM FPS: Force unit to " + usedUnit);

        SiteSessionFactoryBean.setForceUnit(usedUnit);
    }

}
