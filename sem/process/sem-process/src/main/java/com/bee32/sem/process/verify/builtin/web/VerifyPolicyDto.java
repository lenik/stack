package com.bee32.sem.process.verify.builtin.web;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.process.verify.VerifyPolicy;

public class VerifyPolicyDto
        extends EntityDto<VerifyPolicy<?>, Integer> {

    private static final long serialVersionUID = 1L;

    String name;
    String description;

    public VerifyPolicyDto() {
        super();
    }

    public VerifyPolicyDto(VerifyPolicy<?> source) {
        super(source);
    }

    @Override
    protected void _marshal(VerifyPolicy<?> source) {
        name = source.getName();
        description = source.getDescription();
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, VerifyPolicy<?> target) {
        target.setName(name);
        target.setDescription(description);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {
        name = map.getString("name");
        description = map.getString("description");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
