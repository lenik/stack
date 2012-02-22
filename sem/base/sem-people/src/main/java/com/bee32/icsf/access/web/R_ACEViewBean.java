package com.bee32.icsf.access.web;

import com.bee32.icsf.access.alt.R_ACE;
import com.bee32.icsf.access.alt.R_ACEDto;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class R_ACEViewBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public R_ACEViewBean() {
        super(R_ACE.class, R_ACEDto.class, 0);
    }

    public void setResourceNodeData(ResourceNodeData data) {
        if (data == null)
            return;
        if (data.getResource() == null) {
            uiLogger.error("无效资源:该资源仅用于显示分组。");
            return;
        }
        R_ACEDto ace = getOpenedObject();
        ace.setResource(data.getResource());
    }

}
