package com.bee32.sem.process.state;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;

public class EntityStateOpDto
        extends EntityDto<EntityStateOp, Integer>
        implements IEnclosedObject<EntityStateDefDto> {

    private static final long serialVersionUID = 1L;

    EntityStateDefDto stateDef;
    PrincipalDto principal;

    @Override
    public EntityStateDefDto getEnclosingObject() {
        return stateDef;
    }

    @Override
    public void setEnclosingObject(EntityStateDefDto enclosingObject) {
        this.stateDef = enclosingObject;
    }

    @Override
    protected void _marshal(EntityStateOp source) {
        stateDef = mref(EntityStateDefDto.class, source.getStateDef());
        principal = mref(PrincipalDto.class, source.getPrincipal());
    }

    @Override
    protected void _unmarshalTo(EntityStateOp target) {
        merge(target, "stateDef", stateDef);
        merge(target, "principal", principal);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public EntityStateDefDto getStateDef() {
        return stateDef;
    }

    public void setStateDef(EntityStateDefDto stateDef) {
        this.stateDef = stateDef;
    }

    public PrincipalDto getPrincipal() {
        return principal;
    }

    public void setPrincipal(PrincipalDto principal) {
        this.principal = principal;
    }

}
