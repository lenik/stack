package com.bee32.sem.process.verify.web;

import java.util.ArrayList;
import java.util.List;

import javax.free.IllegalUsageException;

import com.bee32.plover.orm.util.EntityPeripheralBean;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.process.verify.AbstractVerifyContext;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.service.IVerifyService;

/**
 * @see AbstractVerifyContext
 */
public abstract class VerifySupportBean
        extends EntityPeripheralBean {

    private static final long serialVersionUID = 1L;

    protected IVerifyService getVerifyService() {
        return getBean(IVerifyService.class);
    }

    public List<? extends IVerifiable<?>> getContextVerifiables() {
        List<? extends EntityViewBean> contextViewBeans = getContextViewBeans();
        if (contextViewBeans.isEmpty())
            throw new IllegalUsageException("No context view bean available.");

        EntityViewBean contextViewBean = contextViewBeans.get(0);
        List<?> selectionList = contextViewBean.getSelection();
        if (selectionList.isEmpty())
            return null;

        List<IVerifiable<?>> verifiables = new ArrayList<IVerifiable<?>>();
        for (Object obj : selectionList) {
            if (obj instanceof IVerifiable<?>) {
                IVerifiable<?> verifiable = (IVerifiable<?>) obj;
                verifiables.add(verifiable);
            }
        }
        return verifiables;
    }

}
