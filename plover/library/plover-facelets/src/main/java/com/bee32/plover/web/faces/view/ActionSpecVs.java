package com.bee32.plover.web.faces.view;

import java.util.Enumeration;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.free.UnexpectedException;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.model.validation.ValidationSwitcher;

/**
 * Button name convention:
 * <ul>
 * <li>Force validation: action.validated action_V
 * <li>Suppress validation: action.immediate action_NV
 * <li>Otherwise the default validation.
 * </ul>
 */
public class ActionSpecVs
        extends ValidationSwitcher {

    public static final String REQUEST_VS_ATTRIBUTE = ActionSpecVs.class.getName();

    static final int DEFAULT = 0;
    static final int VALIDATE = 1;
    static final int NO_VALIDATE = 2;

    @Override
    public Boolean isValidationEnabled() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        if (ctx == null)
            return true; // enabled by system default.

        ExternalContext xctx = ctx.getExternalContext();
        HttpServletRequest req = (HttpServletRequest) xctx.getRequest();

        Integer reqvs = (Integer) req.getAttribute(REQUEST_VS_ATTRIBUTE);
        if (reqvs == null) {
            reqvs = getRequestValidation(req);
            req.setAttribute(REQUEST_VS_ATTRIBUTE, reqvs);
        }

        switch (reqvs) {
        case DEFAULT:
            return null;
        case VALIDATE:
            return true;
        case NO_VALIDATE:
            return false;
        default:
            throw new UnexpectedException();
        }
    }

    public static int getRequestValidation(HttpServletRequest req) {
        Enumeration<String> names = req.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (name.endsWith("-validated"))
                return VALIDATE;
            if (name.endsWith("-immediate"))
                return NO_VALIDATE;
        }
        return DEFAULT;
    }

}
