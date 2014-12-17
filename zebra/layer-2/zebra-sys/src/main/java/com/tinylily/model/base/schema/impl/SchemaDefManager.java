package com.tinylily.model.base.schema.impl;

import net.bodz.bas.repr.path.PathToken;

import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.repr.CoEntityManager;

/**
 * 用于定义特定用途的语境。
 * 
 * @label 模式
 */
@PathToken("schema")
public class SchemaDefManager
        extends CoEntityManager {

    SchemaDefMapper mapper;

    public SchemaDefManager() {
        mapper = VhostDataService.getInstance().getMapper(SchemaDefMapper.class);
    }

    public SchemaDefMapper getMapper() {
        return mapper;
    }

}
