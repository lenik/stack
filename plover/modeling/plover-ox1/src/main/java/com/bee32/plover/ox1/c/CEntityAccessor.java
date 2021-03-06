package com.bee32.plover.ox1.c;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityAccessor;

public class CEntityAccessor
        extends EntityAccessor {

    public static void setOwner(CEntity<?> entity, User owner) {
        entity.setOwner(owner);
    }

    public static void setAclId(CEntity<?> entity, Integer aclId) {
        entity.setAclId(aclId);
    }

}
