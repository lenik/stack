package com.tinylily.model.base.schema.impl;

import net.bodz.bas.repr.path.PathToken;

import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.model.base.schema.PhaseDef;

/**
 * 用于定义事件进展到什么程度。
 * 
 * @label 阶段
 */
@PathToken("phase")
public class PhaseDefManager
        extends FooManager {

    PhaseDefMapper mapper;

    public PhaseDefManager() {
        super(PhaseDef.class);
        mapper = VhostDataService.getInstance().getMapper(PhaseDefMapper.class);
    }

    public PhaseDefMapper getMapper() {
        return mapper;
    }

}
