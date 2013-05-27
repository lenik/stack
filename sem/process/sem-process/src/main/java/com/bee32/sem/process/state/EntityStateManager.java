package com.bee32.sem.process.state;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.color.Blue;

@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "entity_state_manager_item", allocationSize = 1)
public class EntityStateManager
        extends EntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    EntityState entityState;
    Principal manager;

    @ManyToOne(optional = false)
    public EntityState getEntityState() {
        return entityState;
    }

    public void setEntityState(EntityState entityState) {
        this.entityState = entityState;
    }

    @ManyToOne(optional = false)
    public Principal getManager() {
        return manager;
    }

    public void setManager(Principal manager) {
        this.manager = manager;
    }

}
