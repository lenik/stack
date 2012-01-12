package com.bee32.sem.process.verify.web;

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
        extends VerifyContextSupportBean {

    private static final long serialVersionUID = 1L;

    @Override
    public SingleVerifierSupportDto getVerifyContext1() {
        return (SingleVerifierSupportDto) super.getVerifyContext1();
    }

    @SuppressWarnings("unchecked")
    @Operation
    public void reverify() {
        UserDto verifier1 = SessionUser.getInstance().getUser();
        Date verifyDate1 = new Date();

        for (IVerifiableDto verifiable : getVerifiables()) {
            SingleVerifierSupportDto context = (SingleVerifierSupportDto) verifiable.getVerifyContext();
            context.setVerifier1(verifier1);
            context.setVerifiedDate1(verifyDate1);

            EntityDto<?, ?> entityDto = (EntityDto<?, ?>) verifiable;
            Entity<?> entity = entityDto.unmarshal();

            try {
                serviceFor(Entity.class).update(entity);
            } catch (Exception e) {
                uiLogger.error("更新 " + entity.getEntryText() + " 的审核资料失败", e);
                continue;
            }

            try {
                VerifyResult result = getVerifyService().verifyEntity(entity);
                VerifyEvalState evalState = result.getState();
                if (evalState == VerifyEvalState.VERIFIED)
                    uiLogger.info("审核 " + entity.getEntryText() + " 成功!");
                else if (evalState == VerifyEvalState.REJECTED)
                    uiLogger.info("拒绝 " + entity.getEntryText() + " 成功!");
                else if (evalState == VerifyEvalState.NOT_APPLICABLE)
                    uiLogger.info("审核不可用，请配置 " + entity.getEntryText() + " 的审核策略。");
                else if (evalState == VerifyEvalState.INVALID)
                    uiLogger.info("审核 " + entity.getEntryText() + " 无效。可能是您不具有所需的权限。");
                else if (evalState == VerifyEvalState.PENDING)
                    uiLogger.info("审核 " + entity.getEntryText() + " 进入挂起状态。");
                else
                    uiLogger.info("审核 " + entity.getEntryText() + " 无法完成，具体原因未知。");
            } catch (Exception e) {
                uiLogger.error("审核 " + entity.getEntryLabel() + " 失败", e);
            }
        }
    }

    @Operation
    public void approve() {
        for (IVerifiableDto verifiable : getVerifiables()) {
            SingleVerifierSupportDto context = (SingleVerifierSupportDto) verifiable.getVerifyContext();
            context.setAccepted1(true);
        }
        // rejectedReason1 = null;
        reverify();
    }

    @Operation
    public void reject() {
        for (IVerifiableDto verifiable : getVerifiables()) {
            SingleVerifierSupportDto context = (SingleVerifierSupportDto) verifiable.getVerifyContext();
            context.setAccepted1(false);
            // context.setRejectedReason1(rejectedReason1);
        }
        reverify();
    }

}
