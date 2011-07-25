package com.bee32.sem.inventory.web.business;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.util.EntityViewBean;

@Component
@Scope("view")
public class StockOrderAdminBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    public String getType() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        return req.getParameter("subject").toString();
    }

}
