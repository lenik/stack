package com.tinylily.model.base.schema.impl;

import net.bodz.bas.repr.path.PathToken;

import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.repr.CoEntityManager;

/**
 * 用于定义事件进展到什么程度。
 * 
 * @label 阶段
 */
@PathToken("phase")
public class PhaseDefManager
        extends CoEntityManager {

    PhaseDefMapper mapper;

    public PhaseDefManager() {
        mapper = VhostDataService.getInstance().getMapper(PhaseDefMapper.class);
    }

    public PhaseDefMapper getMapper() {
        return mapper;
    }

}
