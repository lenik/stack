package com.bee32.sem.process.verify.web;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.process.verify.VerifyEvalState;
import com.bee32.sem.process.verify.VerifyResult;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;
import com.bee32.sem.process.verify.dto.VerifyContextDto;
import com.bee32.sem.process.verify.service.IVerifyService;

public class SingleVerifierSupportBean<D extends EntityDto<E, K> & IVerifiableDto, E extends Entity<K>, K extends Serializable>
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    PrincipalDto verifier1Template = new PrincipalDto().ref(SessionUser.getInstance().getUser());
    boolean accepted1Template;
    String rejectedReason1Template;

    public List<? extends IVerifiableDto> getVerifiables() {
        return (List<? extends IVerifiableDto>) getSelection();
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

    @SuppressWarnings("unchecked")
    @Operation
    public//
    void reverify() {
        VerifiableSupportBean verifiableSupportBean = BEAN(VerifiableSupportBean.class);
        if (!verifiableSupportBean.isCurrentUserResponsible(getVerifiables())) {
            uiLogger.error("???????????????????????????????????????");
            return;
        }

        Date verifyDate1 = new Date();

        for (IVerifiableDto verifiable : getVerifiables()) {
            // Reload: (1) selection is mostly mref-dto, unmarshal mref will got lazyLoad() entity.
            D reloaded = reload((D) verifiable);

            SingleVerifierSupportDto context = (SingleVerifierSupportDto) reloaded.getVerifyContext();
            context.setVerifier1(verifier1Template);
            context.setVerifiedDate1(verifyDate1);
            context.setAccepted1(accepted1Template);
            context.setRejectedReason1(rejectedReason1Template);

            Entity<?> entity = reloaded.unmarshal();
            String entityLabel = entity.getEntryLabel();

            try {
                DATA(entity.getClass()).update(entity);
            } catch (Exception e) {
                uiLogger.error("?????? " + entityLabel + " ?????????????????????", e);
                continue;
            }

            IVerifyService service = BEAN(IVerifyService.class);
            try {
                VerifyResult result = service.verifyEntity(entity);
                VerifyEvalState evalState = result.getState();
                if (evalState == VerifyEvalState.VERIFIED)
                    uiLogger.info("?????? " + entityLabel + " ??????!");
                else if (evalState == VerifyEvalState.REJECTED)
                    uiLogger.info("?????? " + entityLabel + " ??????!");
                else if (evalState == VerifyEvalState.NOT_APPLICABLE)
                    uiLogger.error("??????????????????????????? " + entityLabel + " ??????????????????");
                else if (evalState == VerifyEvalState.INVALID)
                    uiLogger.error("?????? " + entityLabel + " ????????????????????????????????????????????????");
                else if (evalState == VerifyEvalState.PENDING)
                    uiLogger.warn("?????? " + entityLabel + " ?????????????????????");
                else
                    uiLogger.error("?????? " + entityLabel + " ????????????????????????????????????");
            } catch (Exception e) {
                uiLogger.error("?????? " + entityLabel + " ??????", e);
            }
        }
    }

    @Operation
    public void approve() {
        accepted1Template = true;
        reverify();
    }

    @Operation
    public void reject() {
        accepted1Template = false;
        reverify();
    }

    public void loadTemplate(List<? extends IVerifiableDto> verifiables) {
        if (verifiables.isEmpty()) {
            uiLogger.warn("??????????????????");
            return;
        }
        IVerifiableDto verifiable1 = verifiables.get(0);
        VerifyContextDto<?> verifyContext1 = verifiable1.getVerifyContext();
        // if (!(verifyContext instanceof SingleVerifierSupportDto))
        SingleVerifierSupportDto template1 = (SingleVerifierSupportDto) verifyContext1;
        if (template1 == null) {
            verifier1Template = new PrincipalDto().ref();
            accepted1Template = false;
            rejectedReason1Template = "";
        } else {
            verifier1Template = template1.getVerifier1();
            accepted1Template = template1.isAccepted1();
            rejectedReason1Template = template1.getRejectedReason1();
        }
    }

    public PrincipalDto getVerifier1Template() {
        return verifier1Template;
    }

    public void setVerifier1Template(PrincipalDto verifier1Template) {
        this.verifier1Template = verifier1Template;
    }

    public boolean isAccepted1Template() {
        return accepted1Template;
    }

    public void setAccepted1Template(boolean accepted1Template) {
        this.accepted1Template = accepted1Template;
    }

    public String getRejectedReason1Template() {
        return rejectedReason1Template;
    }

    public void setRejectedReason1Template(String rejectedReason1Template) {
        this.rejectedReason1Template = rejectedReason1Template;
    }

}
