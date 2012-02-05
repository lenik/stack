package com.bee32.sem.process.verify.builtin.dto;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.sem.process.verify.builtin.PassStep;

public class PassStepDto
        extends CEntityDto<PassStep, Integer> {

    private static final long serialVersionUID = 1L;

    public boolean optional;
    private int order;

    private PrincipalDto responsible;

    public PassStepDto() {
        super();
    }

    public PassStepDto(int fmask) {
        super(fmask);
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public PrincipalDto getResponsible() {
        return responsible;
    }

    public void setResponsible(PrincipalDto responsible) {
        this.responsible = responsible;
    }

    @Override
    protected void _marshal(PassStep source) {
        optional = source.isOptional();
        order = source.getOrder();

        responsible = new PrincipalDto().marshal(source.getResponsible());
    }

    @Override
    protected void _unmarshalTo(PassStep target) {
        target.setOptional(optional);
        target.setOrder(order);

        merge(target, "responsible", responsible);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {

        optional = map.getBoolean("optional");
        order = map.getInt("order");

        responsible = new PrincipalDto().parseRef("responsibleId");
    }

}
