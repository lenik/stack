package com.bee32.sem.process.verify.dto;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.process.verify.VerifyPolicy;

public class VerifyPolicyDto
        extends UIEntityDto<VerifyPolicy, Integer> {

    private static final long serialVersionUID = 1L;

    String name;

    public VerifyPolicyDto() {
        super();
    }

    public VerifyPolicyDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void __marshal(VerifyPolicy source) {
        super.__marshal(source);
        name = source.getName();
    }

    @Override
    protected void __unmarshalTo(VerifyPolicy target) {
        super.__unmarshalTo(target);
        target.setName(name);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);
        name = map.getString("name");
    }

    @Override
    protected void _marshal(VerifyPolicy source) {
    }

    @Override
    protected void _unmarshalTo(VerifyPolicy target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    @NLength(max = VerifyPolicy.NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null)
            name = TextUtil.normalizeSpace(name);
        this.name = name;
    }

}
