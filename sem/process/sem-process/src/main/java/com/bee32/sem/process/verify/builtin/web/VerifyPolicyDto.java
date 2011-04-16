package com.bee32.sem.process.verify.builtin.web;

import java.util.List;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.DataTransferObject;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyState;

public class VerifyPolicyDto
        extends DataTransferObject<VerifyPolicy<?, VerifyState>> {

    private static final long serialVersionUID = 1L;

    String name;
    String description;

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
        name = map.getString("name");
        description = map.getString("description");
    }

    public static List<VerifyPolicyDto> marshalList(Iterable<? extends VerifyPolicy<?, ?>> entities) {
        return marshalList(VerifyPolicyDto.class, entities);
    }

}
