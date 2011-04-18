package com.bee32.sem.process.verify;

import java.util.Collection;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sem.process.verify.result.ErrorResult;

public class ManagedVerifyPolicy<C extends IVerifyContext>
        extends VerifyPolicy<IVerifyContext> {

    private static final long serialVersionUID = 1L;

    @Override
    public Collection<? extends Principal> getDeclaredResponsibles(IVerifyContext context) {
        return null;
    }

    @Override
    public ErrorResult evaluate(IVerifyContext context) {
        return null;
    }

    public static <C extends IVerifyContext> IVerifyPolicy<C> forEntityType(Class<? extends EntityBean> entityType) {
        return null;
    }

}
