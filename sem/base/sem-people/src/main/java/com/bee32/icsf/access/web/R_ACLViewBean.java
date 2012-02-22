package com.bee32.icsf.access.web;

import com.bee32.icsf.access.alt.R_ACEDto;
import com.bee32.icsf.access.alt.R_ACL;
import com.bee32.icsf.access.alt.R_ACLDto;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class R_ACLViewBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public R_ACLViewBean() {
        super(R_ACL.class, R_ACLDto.class, 0);
    }

    /**
     * NOTICE: Don't use it: no enclosing support here.
     */
    final ListMBean<R_ACEDto> entriesMBean = ListMBean.fromEL(this, "openedObject.entries", R_ACEDto.class);

    public ListMBean<R_ACEDto> getEntriesMBean() {
        return entriesMBean;
    }

}
