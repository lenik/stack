package com.bee32.icsf.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.AbstractEntityLifecycleAddon;
import com.bee32.plover.orm.entity.Entity;

public class LoginEntityAddon
        extends AbstractEntityLifecycleAddon {

    static Logger logger = LoggerFactory.getLogger(LoginEntityAddon.class);

    User getCurrentUser() {
        User user = (User) SessionLoginInfo.getUserOpt();
        if (user != null) {
            assert user.getId() != null;
            return user;
        }

        if (User.admin == null)
            return null;

        Integer adminId = User.admin.getId();
        if (adminId == null)
            return null;

        return User.admin;
    }

    static final int PRE_ADMIN = -1;

    @Override
    public void entityCreate(Entity<?> entity) {
        super.entityCreate(entity);

        int ownerId = entity.getOwnerId();

        // Ignore if the owner is already specified.
        if (ownerId > 0)
            return;

        // EntityFlags ef = EntityAccessor.getFlags(entity);
        User owner = User.admin;

        switch (ownerId) {
        case PRE_ADMIN:
            owner = User.admin;
            if (owner == null || owner.getId() == null)
                // defer to next time.
                return;
            else
                break;

        case 0:
            owner = getCurrentUser();
            break;
        }

        String ownerName;
        if (owner == null || owner.getId() == null) {
            ownerId = PRE_ADMIN;
            ownerName = "(pre-init) admin";
        } else {
            ownerId = owner.getId();
            ownerName = owner.getDisplayName();
        }

        logger.debug("Set owner of new entity: " + ownerName);
        entity.setOwnerId(ownerId);
    }

}
