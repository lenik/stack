package com.bee32.icsf.principal;

import com.bee32.plover.orm.PloverORMUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;

/**
 * ICSF 安全主体数据单元
 *
 * <p lang="en">
 * ICSF Principal Unit
 */
@ImportUnit(PloverORMUnit.class)
public class IcsfPrincipalUnit
        extends PersistenceUnit {

    @Override
    public int getPriority() {
        return SYSTEM_PRIORITY + 10;
    }

    @Override
    protected void preamble() {
        /**
         * Principal > TreeEntity > CEntity(owner).
         *
         * This means: the type system require both icsf-principal & plover-ox1 be in the same
         * module.
         *
         * And, cuz tree-entity need principal to work, so plover-ox1 unit must import
         * icsf-principal.
         */
        add(Principal.class);
        add(User.class);
        add(Group.class);
        add(Role.class);
        add(UserEmail.class);
        add(UserOption.class);
        add(UserPreference.class);
    }

}
