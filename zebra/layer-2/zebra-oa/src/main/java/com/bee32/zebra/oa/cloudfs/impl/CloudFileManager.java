package com.bee32.zebra.oa.cloudfs.impl;

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
public class CloudFileManager
        extends CoEntityManager {

    CloudFileMapper mapper;

    public CloudFileManager() {
        mapper = VhostDataService.getInstance().getMapper(CloudFileMapper.class);
    }

    public CloudFileMapper getMapper() {
        return mapper;
    }

}
