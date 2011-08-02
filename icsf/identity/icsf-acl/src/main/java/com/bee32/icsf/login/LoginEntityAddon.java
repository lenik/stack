package com.bee32.icsf.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.AbstractEntityLifecycleAddon;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;

public class LoginEntityAddon
        extends AbstractEntityLifecycleAddon {

    static Logger logger = LoggerFactory.getLogger(LoginEntityAddon.class);

    User getContextUser() {
        User user = (User) SessionLoginInfo.getUserOpt();
        if (user != null)
            return user;
        else
            return User.admin;
    }

    @Override
    public void entityCreate(Entity<?> entity) {
        super.entityCreate(entity);

        Integer ownerId = entity.getOwnerId();

        // Ignore if the owner is already specified.
        if (ownerId != null)
            return;

        // EntityFlags ef = EntityAccessor.getFlags(owner); ...?
        User owner = getContextUser();
        if (owner == null)
            return;

        if (logger.isTraceEnabled())
            logger.trace("Set owner of new entity: " + owner.getName());

        EntityAccessor.setOwner(entity, owner);
    }

}
