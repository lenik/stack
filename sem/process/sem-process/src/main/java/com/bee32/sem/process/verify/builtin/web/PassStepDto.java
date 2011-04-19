package com.bee32.sem.process.verify.builtin.web;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.dto.PrincipalDto;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.process.verify.builtin.PassStep;

public class PassStepDto
        extends EntityDto<PassStep, Integer> {

    private static final long serialVersionUID = 1L;

    private int order;
    private PrincipalDto<Principal> responsible;
    public boolean optional;

    public PassStepDto() {
        super();
    }

    public PassStepDto(PassStep source) {
        super(source);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public PrincipalDto<Principal> getResponsible() {
        return responsible;
    }

    public void setResponsible(PrincipalDto<Principal> responsible) {
        this.responsible = responsible;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    protected void _marshal(PassStep source) {
        order = source.getOrder();
        responsible = new PrincipalDto<Principal>().marshal(source.getResponsible());
        optional = source.isOptional();
    }

    @Override
    protected void _unmarshalTo(PassStep target) {
        target.setOrder(order);
        target.setOptional(optional);
        if (responsible != null)
            target.setResponsible(responsible.unmarshal());
    }

}
