package com.bee32.plover.orm;

import com.bee32.plover.orm.config.test.DefaultTestSessionFactoryBean;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.UsingUtil;
import com.bee32.plover.test.FeaturePlayerSupport;

public class PloverOrmFeaturePlayerSupport
        extends FeaturePlayerSupport {

    @Override
    public void setup(Class<?> playerClass) {
        PersistenceUnit usedUnit = UsingUtil.getUsingUnit(playerClass);

        logger.debug("Plover-ORM FPS: Force unit to " + usedUnit);

        DefaultTestSessionFactoryBean.setForceUnit(usedUnit);
    }

}
