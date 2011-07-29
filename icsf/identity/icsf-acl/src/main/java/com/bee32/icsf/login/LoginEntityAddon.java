package com.bee32.icsf.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.AbstractEntityLifecycleAddon;
import com.bee32.plover.orm.entity.Entity;

public class LoginEntityAddon
        extends AbstractEntityLifecycleAddon {

    static Logger logger = LoggerFactory.getLogger(LoginEntityAddon.class);

    @Override
    public void entityCreate(Entity<?> entity) {
        super.entityCreate(entity);

        IUserPrincipal owner = SessionLoginInfo.getUserOpt();
        if (owner == null)
            owner = User.admin;

        if (owner != null) {
            Integer ownerId = owner.getId();
            if (ownerId == null) {
                // Until now, the necessary user/dacl hasn't been created yet.
                return;
            }

            logger.debug("Set owner of new entity: " + owner.getDisplayName());
            entity.setOwnerId(ownerId);
        }
    }

}
