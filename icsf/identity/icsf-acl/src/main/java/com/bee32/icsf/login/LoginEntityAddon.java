package com.bee32.icsf.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.AbstractEntityLifecycleAddon;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.c.CEntityAccessor;

/**
 * Inject current logon user to entity.owner.
 */
public class LoginEntityAddon
        extends AbstractEntityLifecycleAddon {

    static Logger logger = LoggerFactory.getLogger(LoginEntityAddon.class);

    public static User getContextUser() {
        User user = SessionUser.getInstance().getInternalUserOpt();
        if (user != null)
            return user;
        else
            return User.admin;
    }

    @Override
    public void entityCreate(Entity<?> entity) {
        super.entityCreate(entity);

        if (entity instanceof CEntity<?>) {
            CEntity<?> c = (CEntity<?>) entity;
            injectOwner(c);
        }
    }

    void injectOwner(CEntity<?> entity) {
        User owner = entity.getOwner();

        // Ignore if the owner is already specified.
        if (owner != null)
            return;

        // EntityFlags ef = EntityAccessor.getFlags(owner); ...?
        owner = getContextUser();
        if (owner == null)
            return;

        if (logger.isTraceEnabled())
            logger.trace("Set owner of new entity: " + owner.getName());

        CEntityAccessor.setOwner(entity, owner);
    }

}
