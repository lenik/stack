package com.bee32.sem.process.verify.builtin.web;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.dto.PrincipalDto;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.process.verify.builtin.PassStep;

public class PassStepDto
        extends EntityDto<PassStep, Integer> {

    private static final long serialVersionUID = 1L;

    public boolean optional;
    private int order;

    private Long responsibleId;
    private PrincipalDto responsible;

    public PassStepDto() {
        super();
    }

    public PassStepDto(PassStep source) {
        super(source);
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

    public Long getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(Long responsibleId) {
        this.responsibleId = responsibleId;
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

        responsibleId = id(source.getResponsible());
        responsible = new PrincipalDto().marshal(source.getResponsible());
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, PassStep target) {
        target.setOptional(optional);
        target.setOrder(order);

        with(context, target)//
                .unmarshal(PassStep.responsibleProperty, responsibleId, responsible);
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);

        optional = map.getBoolean("optional");
        order = map.getInt("order");
        responsibleId = map.getLong("responsibleId");
    }

}
