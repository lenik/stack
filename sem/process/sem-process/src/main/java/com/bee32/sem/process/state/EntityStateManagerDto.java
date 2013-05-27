package com.bee32.sem.process.state;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;

public class EntityStateManagerDto
        extends EntityDto<EntityStateManager, Integer>
        implements IEnclosedObject<EntityStateDto> {

    private static final long serialVersionUID = 1L;

    EntityStateDto entityState;
    PrincipalDto manager;

    @Override
    public EntityStateDto getEnclosingObject() {
        return entityState;
    }

    @Override
    public void setEnclosingObject(EntityStateDto enclosingObject) {
        this.entityState = enclosingObject;
    }

    @Override
    protected void _marshal(EntityStateManager source) {
        entityState = mref(EntityStateDto.class, source.getEntityState());
        manager = mref(PrincipalDto.class, source.getManager());
    }

    @Override
    protected void _unmarshalTo(EntityStateManager target) {
        merge(target, "entityState", entityState);
        merge(target, "manager", manager);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public EntityStateDto getEntityState() {
        return entityState;
    }

    public void setEntityState(EntityStateDto entityState) {
        this.entityState = entityState;
    }

    public PrincipalDto getManager() {
        return manager;
    }

    public void setManager(PrincipalDto manager) {
        this.manager = manager;
    }

}
