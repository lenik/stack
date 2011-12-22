package com.bee32.sem.process.verify.dto;

import java.util.Date;

import com.bee32.plover.orm.util.PartialDto;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.dto.EventDto;
import com.bee32.sem.process.verify.AbstractVerifyContext;
import com.bee32.sem.process.verify.VerifyState;

public abstract class VerifyContextDto<T extends AbstractVerifyContext>
        extends PartialDto<T> {

    private static final long serialVersionUID = 1L;

    public static final int VERIFY_EVENT = 1;

    private EventState verifyState;
    private String verifyError;
    private Date verifyEvalDate;
    private EventDto verifyEvent;

    private VerifyPolicyDto verifyPolicy;

    public VerifyContextDto() {
        super();
    }

    public VerifyContextDto(int selection) {
        super(selection);
    }

    @Override
    protected void __marshal(T source) {
        super.__marshal(source);

        verifyState = source.getVerifyState();
        verifyError = source.getVerifyError();
        verifyEvalDate = source.getVerifyEvalDate();

        if (selection.contains(VERIFY_EVENT))
            verifyEvent = mref(EventDto.class, source.getVerifyEvent());
    }

    @Override
    protected void __unmarshalTo(T target) {
        super.__unmarshalTo(target);

        // target.setVerifyState(verifyState);
        // target.setVerifyError(verifyError);
        // target.setVerifyEvalDate(verifyEvalDate);

        merge(target, "verifyTask", verifyEvent);
    }

    public EventState getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(EventState verifyState) {
        this.verifyState = verifyState;
    }

    public boolean isVerified() {
        return VerifyState.VERIFIED.equals(verifyState);
    }

    public String getVerifyError() {
        return verifyError;
    }

    public void setVerifyError(String verifyError) {
        this.verifyError = verifyError;
    }

    public Date getVerifyEvalDate() {
        return verifyEvalDate;
    }

    public void setVerifyEvalDate(Date verifyEvalDate) {
        this.verifyEvalDate = verifyEvalDate;
    }

    public VerifyPolicyDto getVerifyPolicy() {
        return verifyPolicy;
    }

    public void setVerifyPolicy(VerifyPolicyDto verifyPolicy) {
        this.verifyPolicy = verifyPolicy;
    }

}
