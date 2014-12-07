package com.bee32.zebra.erp.stock.impl;

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
public class ArtifactManager
        extends CoEntityManager {

    ArtifactMapper mapper;

    public ArtifactManager() {
        mapper = VhostDataService.getInstance().getMapper(ArtifactMapper.class);
    }

    public ArtifactMapper getMapper() {
        return mapper;
    }

}
