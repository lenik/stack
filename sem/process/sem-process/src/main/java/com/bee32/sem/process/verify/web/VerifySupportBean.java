package com.bee32.sem.process.verify.web;

import javax.free.IllegalUsageException;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.process.verify.AbstractVerifyContext;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.service.IVerifyService;

/**
 * @see AbstractVerifyContext
 */
public abstract class VerifySupportBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    protected IVerifyService getVerifyService() {
        return getBean(IVerifyService.class);
    }

    public AbstractVerifyContext getRequestVerifyContext() {
        Entity<?> entity = getRequestEntity(false);
        if (entity == null)
            return null;
        if (!(entity instanceof IVerifiable))
            throw new IllegalUsageException(String.format("%s isn't applicable for non-verifiable entities.", //
                    VerifySupportBean.class.getSimpleName()));

        IVerifiable<?> verifiable = (IVerifiable<?>) entity;
        IVerifyContext _context = verifiable.getVerifyContext();
        if (_context instanceof AbstractVerifyContext)
            throw new UnsupportedOperationException(String.format(//
                    "Only %s or its descendents are supported by verify-support bean.", //
                    AbstractVerifyContext.class.getSimpleName()));
        AbstractVerifyContext context = (AbstractVerifyContext) _context;
        return context;
    }

    public boolean isVerified() {
        IVerifyService verifyService = getVerifyService();
        Entity<?> entity = getRequestEntity(false);
        if (!(entity instanceof IVerifiable))
            return false;
        IVerifiable<?> verifiable = (IVerifiable<?>) entity;
        boolean verified = verifyService.isVerified(verifiable);
        return verified;
    }

}
