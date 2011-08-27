package com.bee32.plover.orm.ext;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity._EntityAccessor;

public class EntityAccessor
        extends _EntityAccessor {

    public static void setOwner(CEntity<?> entity, User owner) {
        entity.setOwner(owner);
    }

    public static void setAclId(CEntity<?> entity, Integer aclId) {
        entity.setAclId(aclId);
    }

}
