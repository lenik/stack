package com.bee32.sem.process.verify.builtin.web;

import java.util.List;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyState;

public class VerifyPolicyDto
        extends EntityDto<VerifyPolicy<?, VerifyState>, Integer> {

    private static final long serialVersionUID = 1L;

    String name;
    String description;

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

    @Override
    protected void _marshal(VerifyPolicy<?, VerifyState> source) {
        name = source.getName();
        description = source.getDescription();
    }

    @Override
    protected void _unmarshalTo(VerifyPolicy<?, VerifyState> target) {
        target.setName(name);
        target.setDescription(description);
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);
        name = map.getString("name");
        description = map.getString("description");
    }

    public static List<VerifyPolicyDto> marshalList(Iterable<? extends VerifyPolicy<?, ?>> entities) {
        return marshalList(VerifyPolicyDto.class, entities);
    }

}
