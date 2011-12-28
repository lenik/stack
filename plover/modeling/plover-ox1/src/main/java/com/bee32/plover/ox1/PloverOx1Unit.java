package com.bee32.plover.ox1;

import com.bee32.icsf.principal.IcsfPrincipalUnit;
import com.bee32.plover.orm.PloverORMUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.ox1.meta.EntityColumn;
import com.bee32.plover.ox1.meta.EntityInfo;

@ImportUnit({ PloverORMUnit.class, IcsfPrincipalUnit.class })
public class PloverOx1Unit
        extends PersistenceUnit {

    @Override
    public int getPriority() {
        return SYSTEM_PRIORITY + 10;
    }

    @Override
    protected void preamble() {
        add(EntityInfo.class);
        add(EntityColumn.class);
    }

}
