package com.bee32.zebra.tk.util;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.repr.req.HttpSnap;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.lily.model.base.CoObjectMask;

public class MaskBuilder {

    public static <M extends CoObjectMask> M fromRequest(M mask, HttpServletRequest req)
            throws ViewBuilderException {
        try {
            HttpSnap snap = (HttpSnap) req.getAttribute(HttpSnap.class.getName());
            if (snap == null)
                mask.populate(req.getParameterMap());
            else
                mask.populate(snap.getParameterMap());
        } catch (ParseException e) {
            throw new ViewBuilderException(e.getMessage(), e);
        }
        return mask;
    }

}
