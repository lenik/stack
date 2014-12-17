package com.tinylily.model.base.schema.impl;

import net.bodz.bas.repr.path.PathToken;

import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.repr.CoEntityManager;

/**
 * 定义优先次序。
 * 
 * @label 优先级
 */
@PathToken("priority")
public class PriorityDefManager
        extends CoEntityManager {

    PriorityDefMapper mapper;

    public PriorityDefManager() {
        mapper = VhostDataService.getInstance().getMapper(PriorityDefMapper.class);
    }

    public PriorityDefMapper getMapper() {
        return mapper;
    }

}
