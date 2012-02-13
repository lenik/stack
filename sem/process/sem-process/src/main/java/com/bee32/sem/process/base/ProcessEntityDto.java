package com.bee32.sem.process.base;

import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;

public abstract class ProcessEntityDto<E extends ProcessEntity>
        extends MomentIntervalDto<E>
        implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    SingleVerifierSupportDto verifyContext;

    public ProcessEntityDto() {
        super();
    }

    public ProcessEntityDto(int fmask) {
        super(fmask);
    }

    @Override
    public ProcessEntityDto<E> populate(Object source) {
        if (source instanceof ProcessEntityDto) {
            ProcessEntityDto<?> o = (ProcessEntityDto<?>) source;
            _populate(o);
        } else
            super.populate(source);
        return this;
    }

    protected void _populate(ProcessEntityDto<?> o) {
        super._populate(o);
        // vc..
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);
        verifyContext = marshal(SingleVerifierSupportDto.class, source.getVerifyContext());
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);
        merge(target, "verifyContext", verifyContext);
    }

    @Override
    public SingleVerifierSupportDto getVerifyContext() {
        return verifyContext;
    }

    public void setVerifyContext(SingleVerifierSupportDto verifyContext) {
        if (verifyContext == null)
            throw new NullPointerException("verifyContext");
        this.verifyContext = verifyContext;
    }

}
