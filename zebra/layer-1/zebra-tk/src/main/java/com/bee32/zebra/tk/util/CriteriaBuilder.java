package com.bee32.zebra.tk.util;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.repr.req.HttpSnap;
import net.bodz.bas.repr.viz.ViewBuilderException;

import com.tinylily.model.base.CoObjectCriteria;

public class CriteriaBuilder {

    public static <C extends CoObjectCriteria> C fromRequest(C criteria, HttpServletRequest req)
            throws ViewBuilderException {
        try {
            HttpSnap snap = (HttpSnap) req.getAttribute(HttpSnap.class.getName());
            if (snap == null)
                criteria.populate(req.getParameterMap());
            else
                criteria.populate(snap.getParameterMap());
        } catch (ParseException e) {
            throw new ViewBuilderException(e.getMessage(), e);
        }
        return criteria;
    }

}
