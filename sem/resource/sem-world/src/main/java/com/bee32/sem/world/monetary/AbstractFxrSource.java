package com.bee32.sem.world.monetary;

import com.bee32.plover.inject.ServiceTemplate;
import com.bee32.sem.misc.i18n.ICurrencyAware;

@ServiceTemplate
public abstract class AbstractFxrSource
        implements IFxrSource, ICurrencyAware {

}
