package com.bee32.sem.process.verify.web;

import java.util.List;

import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.EntityPeripheralBean;
import com.bee32.sem.process.verify.VerifyEvalState;
import com.bee32.sem.process.verify.dto.IVerifiableDto;
import com.bee32.sem.process.verify.dto.VerifyContextDto;
import com.bee32.sem.process.verify.service.IVerifyService;

public abstract class VerifyContextSupportBean
        extends EntityPeripheralBean {

    private static final long serialVersionUID = 1L;

    protected IVerifyService getVerifyService() {
        return getBean(IVerifyService.class);
    }

    public List<? extends IVerifiableDto> getVerifiables() {
        return (List<? extends IVerifiableDto>) getSelection(IVerifiableDto.class, EntityDto.class);
    }

    public IVerifiableDto getVerifiable1() {
        List<? extends IVerifiableDto> verifiables = getVerifiables();
        if (verifiables.isEmpty())
            return null;
        IVerifiableDto first = verifiables.get(0);
        return first;
    }

    public VerifyContextDto<?> getVerifyContext1() {
        IVerifiableDto first = getVerifiable1();
        if (first == null)
            return null;
        VerifyContextDto<?> verifyContext = first.getVerifyContext();
        return verifyContext;
    }

    public VerifyEvalState getMixedVerifyState() {
        List<? extends IVerifiableDto> verifiables = getVerifiables();
        VerifyEvalState[] statev = new VerifyEvalState[verifiables.size()];
        int index = 0;
        for (IVerifiableDto verifiable : verifiables)
            statev[index++] = verifiable.getVerifyContext().getVerifyEvalState();
        VerifyEvalState state = VerifyEvalState.meet(statev);
        return state;
    }

    public boolean isCurrentUserResponsible() {
        VerifyEvalState state = getMixedVerifyState();

        return true;
    }

}
