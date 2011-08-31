package com.bee32.plover.ox1;

import com.bee32.plover.orm.PloverORMUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.ox1.meta.EntityColumn;
import com.bee32.plover.ox1.meta.EntityInfo;
import com.bee32.plover.ox1.principal.Group;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.Role;
import com.bee32.plover.ox1.principal.User;
import com.bee32.plover.ox1.principal.UserEmail;
import com.bee32.plover.ox1.principal.UserOption;
import com.bee32.plover.ox1.principal.UserPreference;

@ImportUnit(PloverORMUnit.class)
public class PloverOx1Unit
        extends PersistenceUnit {

    @Override
    public int getPriority() {
        return SYSTEM_PRIORITY + 10;
    }

    @Override
    protected void preamble() {
        add(Principal.class);
        add(User.class);
        add(Group.class);
        add(Role.class);
        add(UserEmail.class);
        add(UserOption.class);
        add(UserPreference.class);

        add(EntityInfo.class);
        add(EntityColumn.class);
    }

}
