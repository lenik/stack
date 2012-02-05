package com.bee32.sem.process.verify.builtin.dto;

import com.bee32.sem.process.verify.builtin.IJudgeNumber;
import com.bee32.sem.process.verify.builtin.SingleVerifierSupport;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;

public class SingleVerifierWithNumberSupportDto
        extends SingleVerifierSupportDto
        implements IJudgeNumber {

    private static final long serialVersionUID = 1L;

    String numberDescription;
    Number judgeNumber;

    public SingleVerifierWithNumberSupportDto() {
        super();
    }

    public SingleVerifierWithNumberSupportDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(SingleVerifierSupport _source) {
        super._marshal(_source);
        SingleVerifierWithNumberSupport source = (SingleVerifierWithNumberSupport) _source;
        numberDescription = source.getNumberDescription();
        judgeNumber = source.getJudgeNumber();
    }

    @Override
    protected void _unmarshalTo(SingleVerifierSupport target) {
        super._unmarshalTo(target);
        // Read-only: target.setJudgeNumber();
    }

    @Override
    public String getNumberDescription() {
        return numberDescription;
    }

    @Override
    public Number getJudgeNumber() {
        return judgeNumber;
    }

}
