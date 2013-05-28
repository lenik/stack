package com.bee32.sem.process.state;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.color.Blue;

/**
 * 实体状态操作员
 *
 * 定义一个实体状态下的操作员。
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "entity_state_op_item", allocationSize = 1)
public class EntityStateOp
        extends EntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    EntityStateDef stateDef;
    Principal principal;
    Permission permission = new Permission(0);

    /**
     * 所属的状态定义。
     */
    @ManyToOne(optional = false)
    public EntityStateDef getStateDef() {
        return stateDef;
    }

    public void setStateDef(EntityStateDef stateDef) {
        this.stateDef = stateDef;
    }

    /**
     * 操作主体
     *
     * 该条目下定义的操作主体。
     */
    @ManyToOne(optional = false)
    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @Transient
    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

}
