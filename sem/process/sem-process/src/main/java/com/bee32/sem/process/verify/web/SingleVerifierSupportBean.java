package com.bee32.sem.process.verify.web;

import java.util.Date;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;

public class SingleVerifierSupportBean
        extends VerifyContextSupportBean {

    private static final long serialVersionUID = 1L;

    UserDto verifier1;
    Date verifyDate1;
    boolean accepted1;
    String rejectedReason1;

    public SingleVerifierSupportBean() {
        SingleVerifierSupportDto context = getVerifyContextDto1();
        verifier1 = context.getVerifier1();
        verifyDate1 = context.getVerifiedDate1();
        accepted1 = context.isAccepted1();
        rejectedReason1 = context.getRejectedReason1();
    }

    @Override
    protected SingleVerifierSupportDto getVerifyContextDto1() {
        return (SingleVerifierSupportDto) super.getVerifyContextDto1();
    }

    public void doVerify() {
        verifier1 = SessionUser.getInstance().getUser();
        verifyDate1 = new Date();

        for (IVerifiableDto verifiable : getVerifiables()) {
            SingleVerifierSupportDto context = (SingleVerifierSupportDto) verifiable.getVerifyContext();
            context.setVerifier1(verifier1);
            context.setVerifiedDate1(verifyDate1);
            context.setAccepted1(accepted1);
            context.setRejectedReason1(rejectedReason1);

            EntityDto<?, ?> entityDto = (EntityDto<?, ?>) verifiable;
            Entity<?> entity = entityDto.unmarshal();

            getVerifyService().verifyEntity(entity);
            try {
                uiLogger.info("审核 " + entity.getEntryText() + " 成功!");
            } catch (Exception e) {
                uiLogger.error("审核 " + entity.getEntryText() + " 失败", e);
            }
        }
    }

    public void doApprove() {
        accepted1 = true;
        // rejectedReason1 = null;
        doVerify();
    }

    public void doReject() {
        accepted1 = false;
        doVerify();
    }

    /*************************************************************************/

    public UserDto getVerifier1() {
        return verifier1;
    }

    public void setVerifier1(UserDto verifier1) {
        this.verifier1 = verifier1;
    }

    public Date getVerifyDate1() {
        return verifyDate1;
    }

    public void setVerifyDate1(Date verifyDate1) {
        this.verifyDate1 = verifyDate1;
    }

    public boolean isAccepted1() {
        return accepted1;
    }

    public void setAccepted1(boolean accepted1) {
        this.accepted1 = accepted1;
    }

    public String getRejectedReason1() {
        return rejectedReason1;
    }

    public void setRejectedReason1(String rejectedReason1) {
        this.rejectedReason1 = rejectedReason1;
    }

}
