package com.bee32.zebra.erp.site;

import net.bodz.bas.fn.EvalException;
import net.bodz.bas.fn.IEvaluable;

public class ErpSiteResolver
        implements IEvaluable<ErpSite> {

    @Override
    public ErpSite eval()
            throws EvalException {
        return new ErpSite();
    }

}
