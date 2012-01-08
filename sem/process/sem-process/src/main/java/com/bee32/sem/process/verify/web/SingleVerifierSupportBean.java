package com.bee32.sem.process.verify.web;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityPeripheralBean;
import com.bee32.sem.process.verify.builtin.ISingleVerifier;
import com.bee32.sem.process.verify.dto.VerifyContextDto;
import com.bee32.sem.process.verify.service.IVerifyService;

public class SingleVerifierSupportBean
        extends EntityPeripheralBean {

    private static final long serialVersionUID = 1L;

    protected IVerifyService getVerifyService() {
        return getBean(IVerifyService.class);
    }

    protected VerifyContextDto<? extends ISingleVerifier> getVerifyContextDto() {

    }

    public void verify() {
        try {
            StockOrderVerifySupportDto context = stockOrder.getVerifyContext();
            context.setVerifier1(DTOs.marshal(UserDto.class, currentUser));
            context.setVerifiedDate1(new Date());
            context.setAccepted1(true);
            context.setRejectedReason1(rejectedReason);

            verifyService.verifyEntity(stockOrder.unmarshal());
            uiLogger.info("审核成功!");
        } catch (Exception e) {
            uiLogger.error("审核错误.", e);
        }
    }

    public void reject() {
        if (stockOrder == null || stockOrder.getId() == null) {
            uiLogger.error("当前没有单据!");
            return;
        }

        try {
            User currentUser = SessionUser.getInstance().getInternalUser();

            StockOrderVerifySupportDto context = stockOrder.getVerifyContext();
            context.setVerifier1(DTOs.marshal(UserDto.class, currentUser));
            context.setVerifiedDate1(new Date());
            context.setAccepted1(false);
            context.setRejectedReason1(rejectedReason);

            IVerifyService verifyService = getBean(IVerifyService.class);
            verifyService.verifyEntity(stockOrder.unmarshal());
            uiLogger.warn("拒绝成功!");
        } catch (Exception e) {
            uiLogger.error("审核错误.", e);
        }
    }

}
