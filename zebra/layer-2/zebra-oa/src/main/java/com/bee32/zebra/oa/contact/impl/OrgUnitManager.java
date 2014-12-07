package com.bee32.zebra.oa.contact.impl;

import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.repr.CoEntityManager;

/**
 * TITLE
 * 
 * @label LABEL
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
public class OrgUnitManager
        extends CoEntityManager {

    OrgUnitMapper mapper;

    public OrgUnitManager() {
        mapper = VhostDataService.getInstance().getMapper(OrgUnitMapper.class);
    }

    public OrgUnitMapper getMapper() {
        return mapper;
    }

}
