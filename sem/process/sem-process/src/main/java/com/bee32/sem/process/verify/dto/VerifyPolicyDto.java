package com.bee32.sem.process.verify.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.process.verify.VerifyPolicy;

public class VerifyPolicyDto
        extends UIEntityDto<VerifyPolicy, Integer> {

    private static final long serialVersionUID = 1L;

    public VerifyPolicyDto() {
        super();
    }

    public VerifyPolicyDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void __marshal(VerifyPolicy source) {
        super.__marshal(source);
    }

    @Override
    protected void __unmarshalTo(VerifyPolicy target) {
        super.__unmarshalTo(target);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);
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
        throw new NotImplementedException();
    }

}
