package com.bee32.sem.process.verify.web;

import java.io.Serializable;
import java.util.Date;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.process.verify.VerifyEvalState;
import com.bee32.sem.process.verify.VerifyResult;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;

public class SingleVerifierSupportBean
        extends AbstractVerifySupportBean {

    private static final long serialVersionUID = 1L;

    private PrincipalDto verifier1Template;
    private boolean accepted1Template;
    private String rejectedReason1Template;

    public SingleVerifierSupportBean() {
        User currentUser = SessionUser.getInstance().getInternalUser();
        verifier1Template = new PrincipalDto().ref(currentUser);
    }

    @SuppressWarnings("unchecked")
    @Operation
    public <D extends EntityDto<E, K> & IVerifiableDto, E extends Entity<K>, K extends Serializable> //
    void reverify() {
        VerifiableSupportBean verifiableSupportBean = getBean(VerifiableSupportBean.class);
        if (!verifiableSupportBean.isCurrentUserResponsible()) {
            uiLogger.error("您不是该对象的审核责任人。");
            return;
        }

        Date verifyDate1 = new Date();

        for (IVerifiableDto verifiable : getVerifiables()) {
            D reloaded = reload((D) verifiable);

            SingleVerifierSupportDto context = (SingleVerifierSupportDto) reloaded.getVerifyContext();
            context.setVerifier1(verifier1Template);
            context.setVerifiedDate1(verifyDate1);
            context.setAccepted1(accepted1Template);
            context.setRejectedReason1(rejectedReason1Template);

            Entity<?> entity = reloaded.unmarshal(this);
            String entityLabel = entity.getEntryLabel();

            try {
                serviceFor(Entity.class).update(entity);
            } catch (Exception e) {
                uiLogger.error("更新 " + entityLabel + " 的审核资料失败", e);
                continue;
            }

            try {
                VerifyResult result = getVerifyService().verifyEntity(entity);
                VerifyEvalState evalState = result.getState();
                if (evalState == VerifyEvalState.VERIFIED)
                    uiLogger.info("审核 " + entityLabel + " 成功!");
                else if (evalState == VerifyEvalState.REJECTED)
                    uiLogger.info("拒绝 " + entityLabel + " 成功!");
                else if (evalState == VerifyEvalState.NOT_APPLICABLE)
                    uiLogger.info("审核不可用，请配置 " + entityLabel + " 的审核策略。");
                else if (evalState == VerifyEvalState.INVALID)
                    uiLogger.info("审核 " + entityLabel + " 无效。可能是您不具有所需的权限。");
                else if (evalState == VerifyEvalState.PENDING)
                    uiLogger.info("审核 " + entityLabel + " 进入挂起状态。");
                else
                    uiLogger.info("审核 " + entityLabel + " 无法完成，具体原因未知。");
            } catch (Exception e) {
                uiLogger.error("审核 " + entityLabel + " 失败", e);
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

    public SingleVerifierSupportDto getVerifyContextTemplate() {
        return (SingleVerifierSupportDto) super.getVerifyContext1();
    }

    public void loadTemplateFromSelection() {
        SingleVerifierSupportDto template = getVerifyContextTemplate();
        if (template == null) {
            rejectedReason1Template = "";
        } else {
            rejectedReason1Template = template.getRejectedReason1();
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
