package com.bee32.sem.process.verify.builtin.web;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.dto.PrincipalDto;
import com.bee32.plover.arch.util.ParameterMap;
import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.process.verify.builtin.PassStep;

public class PassStepDto
        extends EntityDto<PassStep, Integer> {

    private static final long serialVersionUID = 1L;

    public boolean optional;
    private int order;

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
    protected void _unmarshalTo(IUnmarshalContext context, PassStep target) {
        target.setOptional(optional);
        target.setOrder(order);

        with(context, target)//
                .unmarshal(responsibleProperty, responsible);
    }

    @Override
    public void _parse(ParameterMap map)
            throws ParseException, TypeConvertException {

        optional = map.getBoolean("optional");
        order = map.getInt("order");

        responsible = new PrincipalDto().ref(map.getLong("responsibleId"));
    }

    static final PropertyAccessor<PassStep, Principal> responsibleProperty = new PropertyAccessor<PassStep, Principal>(
            Principal.class) {

        @Override
        public Principal get(PassStep entity) {
            return entity.getResponsible();
        }

        @Override
        public void set(PassStep entity, Principal responsible) {
            entity.setResponsible(responsible);
        }

    };

}
