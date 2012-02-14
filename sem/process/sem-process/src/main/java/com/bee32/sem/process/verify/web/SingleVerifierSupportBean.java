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

    PrincipalDto verifier1Template = new PrincipalDto().ref(SessionUser.getInstance().getUser().getId());
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
        VerifiableSupportBean verifiableSupportBean = ctx.bean.getBean(VerifiableSupportBean.class);
        if (!verifiableSupportBean.isCurrentUserResponsible(getVerifiables())) {
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

            Entity<?> entity = reloaded.unmarshal();
            String entityLabel = entity.getEntryLabel();

            try {
                ctx.data.access(Entity.class).update(entity);
            } catch (Exception e) {
                uiLogger.error("更新 " + entityLabel + " 的审核资料失败", e);
                continue;
            }

            IVerifyService service = ctx.bean.getBean(IVerifyService.class);
            try {
                VerifyResult result = service.verifyEntity(entity);
                VerifyEvalState evalState = result.getState();
                if (evalState == VerifyEvalState.VERIFIED)
                    uiLogger.info("审核 " + entityLabel + " 成功!");
                else if (evalState == VerifyEvalState.REJECTED)
                    uiLogger.info("拒绝 " + entityLabel + " 成功!");
                else if (evalState == VerifyEvalState.NOT_APPLICABLE)
                    uiLogger.error("审核不可用，请配置 " + entityLabel + " 的审核策略。");
                else if (evalState == VerifyEvalState.INVALID)
                    uiLogger.error("审核 " + entityLabel + " 无效。可能是您不具有所需的权限。");
                else if (evalState == VerifyEvalState.PENDING)
                    uiLogger.warn("审核 " + entityLabel + " 进入挂起状态。");
                else
                    uiLogger.error("审核 " + entityLabel + " 无法完成，具体原因未知。");
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

    public void loadTemplate(List<? extends IVerifiableDto> verifiables) {
        if (verifiables.isEmpty()) {
            uiLogger.warn("没有选中记录");
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
